package com.andresouza.dscatalog.servicies;

import com.andresouza.dscatalog.dto.EmailDTO;
import com.andresouza.dscatalog.entities.PasswordRecovery;
import com.andresouza.dscatalog.entities.User;
import com.andresouza.dscatalog.repositories.PasswordRecoveryRepository;
import com.andresouza.dscatalog.repositories.UserRepository;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoveryRepository passwordRecoveryRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createRecoverToken (EmailDTO body){

        User user = userRepository.findByEmail(body.getEmail());
        if(user == null){
         throw new ResourceNotFoundException("Email não encontrado");
        }

        String token = UUID.randomUUID().toString();

        PasswordRecovery entity = new PasswordRecovery();
        entity.setEmail(body.getEmail());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes*60L));

        entity = passwordRecoveryRepository.save(entity);

        String text = "Acesse o link para definir uma nova senha\n\n" +
                recoverUri + token + ". validade de " + tokenMinutes + " minutos";

        emailService.sendEmail(body.getEmail(), "Recuperação de Senha", text);

    }
}
