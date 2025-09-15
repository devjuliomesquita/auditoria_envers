package com.juliomesquita.demoauditoria.infra.configs.security.authentication;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    private final UserRepository userRepository;
//    private final TokenRepository tokenRepository;
//    private final ProfileRepository profileRepository;
//    private final ProfileFactory profileFactory;
//    private final TokenFactory tokenFactory;
//    private final UserFactory userFactory;
//
//    public AuthenticationService(JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository, TokenRepository tokenRepository, ProfileRepository profileRepository, ProfileFactory profileFactory, TokenFactory tokenFactory, UserFactory userFactory) {
//        this.jwtService = jwtService;
//        this.authenticationManager = authenticationManager;
//        this.userRepository = userRepository;
//        this.tokenRepository = tokenRepository;
//        this.profileRepository = profileRepository;
//        this.profileFactory = profileFactory;
//        this.tokenFactory = tokenFactory;
//        this.userFactory = userFactory;
//    }
//
//    @Transactional
//    public AuthenticationResponse register(RegisterRequest request) {
//        Profile profile = this.checkProfile(request.cpf());
//
//        User user = this.userFactory.createUser(request);
//        user.setProfile(profile);
//        User userSaved = this.userRepository.save(user);
//        String jwtToken = this.createToken(userSaved);
//        Token token = this.tokenFactory.createToken(userSaved, jwtToken);
//        String refreshToken = this.jwtService.generateRefreshToken(userSaved);
//        this.revokeTokens(userSaved);
//        this.tokenRepository.save(token);
//        return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
//    }
//
//    public AuthenticationResponse login(AuthenticationRequest login) {
//        this.authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                login.cpf(),
//                login.password()
//            )
//        );
//        User userSaved = this.userRepository.findByCpf(login.cpf())
//            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
//        String jwtToken = this.createToken(userSaved);
//        Token token = this.tokenFactory.createToken(userSaved, jwtToken);
//        String refreshToken = this.jwtService.generateRefreshToken(userSaved);
//        this.revokeTokens(userSaved);
//        this.tokenRepository.save(token);
//        return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
//    }
//
//    private void revokeTokens(User user){
//        List<Token> tokenList = this.tokenRepository.findByUserIdAndExpiredFalseAndRevokedFalse(user.getId());
//        if(tokenList.isEmpty()){
//            return;
//        }
//        tokenList.forEach(t -> {
//            t.setRevoked(true);
//            t.setExpired(true);
//        });
//        this.tokenRepository.saveAll(tokenList);
//    }
//    private ProfileEnt checkProfile(String cpf){
//        if(cpf.equalsIgnoreCase("60734641346")){
//            Optional<ProfileEnt> profile = this.profileRepository.findByName("backoffice");
//            if(profile.isEmpty()){
//                List<ProfileEnt> profileList = this.profileFactory.createProfiles();
//                List<ProfileEnt> profilesSaved = this.profileRepository.saveAll(profileList);
//                return profilesSaved.stream()
//                    .filter(p -> p.getName().equals("backoffice"))
//                    .findFirst()
//                    .orElseThrow();
//            }
//            return profile.get();
//        }
//        return this.profileRepository.findByName("user").orElseThrow();
//    }
//
//    private String createToken(UserEnt user){
//        Map<String, Object> extraClaims = new HashMap<>();
//        extraClaims.put("Id", user.getId());
//        extraClaims.put("Tag", "User");
//        extraClaims.put("Profile", user.getProfile().getName());
//        extraClaims.put("Permissions", user.getProfile().getAuthoraties());
//
//        return this.jwtService.generateAccessToken(extraClaims, user);
//    }
}
