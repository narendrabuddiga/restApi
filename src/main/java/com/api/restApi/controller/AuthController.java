// package com.api.restApi.controller;

// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;

// import jakarta.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.ResponseCookie;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.api.restApi.entity.request.LoginRequest;
// import com.api.restApi.security.jwt.JwtUtils;
// import com.api.restApi.service.UserDetailsImpl;

// //for Angular Client (withCredentials)
// //@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
// @CrossOrigin(origins = "*", maxAge = 3600)
// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {
//     @Autowired
//     AuthenticationManager authenticationManager;

//     // @Autowired
//     // UserRepository userRepository;

//     // @Autowired
//     // RoleRepository roleRepository;

//     @Autowired
//     PasswordEncoder encoder;

//     @Autowired
//     JwtUtils jwtUtils;

//     @PostMapping("/signin")
//     public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

//         Authentication authentication = authenticationManager
//                 .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                         loginRequest.getPassword()));

//         SecurityContextHolder.getContext().setAuthentication(authentication);

//         UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

//         ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

//         List<String> roles = userDetails.getAuthorities().stream()
//                 .map(item -> item.getAuthority())
//                 .collect(Collectors.toList());

//         return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(null);
//     }

//     @PostMapping("/signout")
//     public ResponseEntity<?> logoutUser() {
//         ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//         return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//                 .body("You've been signed out!");
//     }
// }