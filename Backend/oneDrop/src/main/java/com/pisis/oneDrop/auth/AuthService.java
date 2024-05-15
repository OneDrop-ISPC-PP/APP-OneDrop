package com.pisis.oneDrop.auth;


import com.pisis.oneDrop.auth.dtos.UserReadDto;
import com.pisis.oneDrop.auth.dtos.UserUpdateDto;
import com.pisis.oneDrop.auth.entities.*;
import com.pisis.oneDrop.auth.jwt.JwtService;
import com.pisis.oneDrop.exceptions.customsExceptions.AlreadyExistException;
import com.pisis.oneDrop.exceptions.customsExceptions.InvalidJwtException;
import com.pisis.oneDrop.exceptions.customsExceptions.InvalidValueException;
import com.pisis.oneDrop.exceptions.customsExceptions.NotFoundException;
import com.pisis.oneDrop.utils.MailManager;
import com.pisis.oneDrop.utils.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private MailManager mailManager;
    @Autowired
    private Validator validator;

    @Autowired
    private UserMapper userMapper;

    public UserReadDto editUserRoleById(Integer idUser, Role newRole){
        Optional<User> userOp = userRepository.findById(idUser);
        if (userOp.isPresent()) {
            User user = userOp.get();
            user.setRole(newRole);
            userRepository.save(user);
            return userMapper.toReadDto(user);
        } else {
            throw new NotFoundException("No se encontro usuario por ID: "+idUser);
        }
    }
    public User getUserById(Integer idUser){
        Optional<User> user = userRepository.findById(idUser);
        if (user.isEmpty()) throw new NotFoundException("No existe el usuario por ID "+idUser);
        return user.get();
    }

    public UserReadDto findUserById(Integer idUser){
        return userMapper.toReadDto(getUserById(idUser));
    }
    public UserReadDtoArray getAllUsers (String dni, String fullName, Integer page, Integer size, String sortBy){
        Page<User> results;
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (dni != null) {
            results = userRepository.findAllByDniContains(dni, pageable);
        } else if (dni == null && !fullName.equals("")){
            results = userRepository.getAllByFullName(fullName, pageable);
        } else {
            results = userRepository.findAll(pageable);
        }
        Page pagedResults = results.map(entity -> userMapper.toReadDto(entity));

        return UserReadDtoArray.builder()
                .users(pagedResults.getContent())
                .total_results(pagedResults.getTotalElements())
                .results_per_page(size)
                .current_page(page)
                .pages(pagedResults.getTotalPages())
                .sort_by(sortBy)
                .build();
    }
    public UserReadDtoArray getAllByRole (Role role, String dni, String fullName, Integer page, Integer size, String sortBy){
        Page<User> results;
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (dni != null) {
            results = userRepository.findAllByRoleAndDniContains(role,dni, pageable);
        } else if (dni == null && !fullName.equals("")){
            results = userRepository.getAllByRoleAndFullName(role,fullName, pageable);
        } else {
            results = userRepository.findAllByRole(role, pageable);
        }
        Page pagedResults = results.map(entity -> userMapper.toReadDto(entity));

        return UserReadDtoArray.builder()
                .users(pagedResults.getContent())
                .total_results(pagedResults.getTotalElements())
                .results_per_page(size)
                .current_page(page)
                .pages(pagedResults.getTotalPages())
                .sort_by(sortBy)
                .build();
    }
    public AuthResponse register(RegisterRequest registerRequest) {
        if (! registerRequest.getPassword1().equals(registerRequest.getPassword2())) {
            throw new InvalidValueException("Passwords no coinciden!");
        }
        validateNewUsername(registerRequest.getUsername());
        validateNewDni(registerRequest.getDni());
        validateNewEmail(registerRequest.getEmail());

        User user = new User().builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword1()))
                .nombre(registerRequest.getNombre())
                .apellido(registerRequest.getApellido())
                .telefono(registerRequest.getTelefono())
                .dni(registerRequest.getDni())
                .email(registerRequest.getEmail())
                .nacimiento(registerRequest.getNacimiento())
                .sexo(registerRequest.getSexo())
                .role(createRoleByEmail(registerRequest.getEmail()))
                .build();


        userRepository.save(user);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getUsername() , registerRequest.getPassword1()));

        String sendStatus = mailManager.sendEmail(user.getEmail(), "Test servidor backend java", "Hola, GRACIAS POR REGISTRARTE "+user.getUsername()+"!");
        mailManager.sendEmail("onedropispc2024@gmail.com", "Nuevo registro en OneDrop", "Se registro el usuario "+user.getUsername()+"!");
        // log.info("NUEVO USUARIO => "+user.getUsername());

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername() , loginRequest.getPassword()));
        UserDetails userDetails = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(()->new NotFoundException(("User not found")));
        String token = jwtService.getToken(userDetails);


        // String sendStatus = mailManager.sendEmail(userDetails., "Test servidor backend one drop", "Hola, longggiiin ");

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public LoguedUserDetails getLoguedUserDetails(HttpHeaders headers) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User loguedUser = (User) securityContext.getAuthentication().getPrincipal();
        String token = jwtService.getTokenFromHeader(headers);

        return LoguedUserDetails
                .builder()
                .token(token)
                .id(loguedUser.getId())
                .username(loguedUser.getUsername())
                .nombre(loguedUser.getNombre())
                .apellido(loguedUser.getApellido())
                .telefono(loguedUser.getTelefono())
                .dni(loguedUser.getDni())
                .email((loguedUser.getEmail()))
                .role(loguedUser.getRole())
                .sexo(loguedUser.getSexo())
                .nacimiento(loguedUser.getNacimiento())
                .authorities(loguedUser.getAuthorities())
                .build();
    }

    public LoguedUserDetails editUserDetails (HttpHeaders headers, UserUpdateDto userUpdateDto){
        User loguedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // username, email y dni no se pueden editar. Rol solo puede ser editado por un admin mediante editUserDetailsById(String idUser, Role newRole) // todo rol puede ser editado por si mismo, debera tener un metodo especial que solo los admin puedan acceder y cambien solamente el rol => editUserDetailsById(String idUser, Role newRole)

        if(userUpdateDto.getNombre() != null){
            validator.stringMinSize("Nombre", 2, userUpdateDto.getNombre());
            validator.stringOnlyLetters("Nombre", userUpdateDto.getNombre());
            loguedUser.setNombre(userUpdateDto.getNombre());
        }
        if(userUpdateDto.getApellido() != null){
            validator.stringMinSize("Apellido", 2, userUpdateDto.getApellido());
            validator.stringOnlyLetters("Apellido", userUpdateDto.getApellido());
            loguedUser.setApellido(userUpdateDto.getApellido());
        }
        if(userUpdateDto.getTelefono() != null){
            validator.validPhoneNumber(userUpdateDto.getTelefono());
            loguedUser.setTelefono(userUpdateDto.getTelefono());
        }
        // guardar loguedUser
        userRepository.save(loguedUser);
        return getLoguedUserDetails(headers);
    }

    public Boolean restorePassword(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new NotFoundException("Email no registrado");
        String tokenToRestore = jwtService.createTokenForRestorePassword(user.get().getUsername());
        // mailManager.sendEmailToRestorePassword(email , tokenToRestore);
        log.warn(">> token enviado para restaurar cuenta: "+email+", token: " +tokenToRestore+" <<");
        return true;
    }


    public AuthResponse setNewPassword(RestorePassRequest restorePassRequest){
        if(!restorePassRequest.getPassword1().equals(restorePassRequest.getPassword2())) throw new InvalidValueException("Passwords no coinciden");
        if (jwtService.isTokenExpired(restorePassRequest.getToken())) throw new InvalidJwtException("Token expirado, vuelve a solicitar envio del token");

        String username = jwtService.getUsernameFromToken(restorePassRequest.getToken());
        User user = userRepository.findByUsername(username).get();

        user.setPassword(passwordEncoder.encode(validator.stringMinSize("Password",5, restorePassRequest.getPassword1())));
        userRepository.save(user);
        return login(new LoginRequest(username,restorePassRequest.getPassword1()));
    }

    public void validateNewUsername(String username){
        if(userRepository.existsByUsername(validator.stringOnlyLettersAndNumbers("Username", username))) throw new AlreadyExistException("Username ya en uso!");
    }
    public void validateNewEmail(String email){
        // TODO VALIDAR TIPOS DE DATOS INPUTS email
        if(userRepository.existsByEmail(email)) throw new AlreadyExistException("Email ya en uso!");
    }
    public boolean existByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean existById(Integer id){
        return userRepository.existsById(id);
    }
    public void validateNewDni(String dni){
        validator.stringMinSize("Dni",8, dni);
        if(userRepository.existsByDni(validator.stringOnlyNumbers("Dni" , dni))) throw new AlreadyExistException("Dni ya en uso!");
    }

    public Role createRoleByEmail ( String email){
        // TODO EL ROL USER DEBE SER EL INICIAL, Y SOLO EL ADMIN PODRA VALIDAR Y CAMBIAR A ROL MEDICO
        Role role = Role.USUARIO;
        if (email.contains("@admin")) role = Role.ADMIN;
        return role;
    }

}