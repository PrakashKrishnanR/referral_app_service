package com.loyalty.backend.controller;

import com.loyalty.backend.entity.Referral;
import com.loyalty.backend.exception.BadRequestException;
import com.loyalty.backend.entity.AuthProvider;
import com.loyalty.backend.entity.User;
import com.loyalty.backend.payload.ApiResponse;
import com.loyalty.backend.payload.AuthResponse;
import com.loyalty.backend.payload.LoginRequest;
import com.loyalty.backend.payload.SignUpRequest;
import com.loyalty.backend.repository.ReferralRepository;
import com.loyalty.backend.repository.UserRepository;
import com.loyalty.backend.security.formLogin.TokenProvider;
import com.loyalty.backend.service.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ReferralRepository refRepository;

    private static final String APP_STORE_URL = "itms-apps://apps.apple.com/app/your-app-id";

    private static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=your.package.name";

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(new AuthResponse(token, user.getName(), user.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true,
                        "User registered successfully"));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@Valid @RequestBody String token) {
        if (tokenProvider.validateToken(token)) {
            return ResponseEntity.ok(new ApiResponse(true, "Token is valid"));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Token is invalid"));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            fileStorageService.storeFile(file);
        } catch (Exception e) {
            throw new BadRequestException("Could not store file " + file.getOriginalFilename() + ". Please try again!");
        }
        return ResponseEntity.ok(new ApiResponse(true, "File uploaded successfully"));
    }


    @GetMapping("updateReferral/{referralCode}")
    public RedirectView updateReferral(@PathVariable String referralCode, HttpHeaders headers) {
        Referral referral = refRepository.findByReferralCode(referralCode).orElseThrow(() -> new RuntimeException("No referaal code found") );
        referral.setUseCount(referral.getUseCount()+1);
        refRepository.save(referral);

        String userAgent = headers.getFirst(HttpHeaders.USER_AGENT);
        String redirectUrl = null;
        if (userAgent != null) {
            if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
                // Redirect to the App Store
                redirectUrl = APP_STORE_URL;
            } else if (userAgent.contains("Android")) {
                // Redirect to Google Play
                redirectUrl = PLAY_STORE_URL;
            }
        }

        return new RedirectView(redirectUrl);


    }
}
