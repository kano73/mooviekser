package org.movier.service;

import org.movier.model.entity.EmailValidation;
import org.movier.model.entity.MyUser;
import org.movier.repository.EmailValidationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EmailValidationService {
    private final EmailValidationRepository emailValidationRepository;
    private final MailSenderService mailSenderService;

    public EmailValidationService(EmailValidationRepository emailValidationRepository, MailSenderService mailSenderService) {

        this.emailValidationRepository = emailValidationRepository;
        this.mailSenderService = mailSenderService;
    }

    public MyUser findByToken(String token) {
        return emailValidationRepository.findByToken(token).getLast().getUser();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void prepareEmailValidation(MyUser user) {
        if(emailValidationRepository.findByUser(user) != null) {
            emailValidationRepository.deleteByUser(user);
        }

        String uuid;
        do{
            uuid = UUID.randomUUID().toString();
        }while (!emailValidationRepository.findByToken(uuid).isEmpty());

        String text = "To confirm your email address, please click on the following link: http://localhost:8080/verify?token="+uuid;

        EmailValidation emailValidation = new EmailValidation();
        emailValidation.setUser(user);
        emailValidation.setToken(uuid);

        emailValidationRepository.save(emailValidation);

        try{
            mailSenderService.sendEmail(user.getEmail(),"Email Validation",text);
        }catch(Exception e){
            throw new RuntimeException("unable to send mail " ,e);
        }
    }

    public void deleteByUser(MyUser user) {
        emailValidationRepository.deleteByUser(user);
    }
}
