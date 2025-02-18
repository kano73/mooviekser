package org.movier.service;

import org.movier.exceptions.MailCanNotBeSentException;
import org.movier.exceptions.MyUserNotFoundException;
import org.movier.model.entity.AdminInvitation;
import org.movier.model.entity.EmailValidation;
import org.movier.model.entity.MyUser;
import org.movier.repository.EmailValidationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {
    private final EmailValidationRepository emailValidationRepository;
    private final MailSenderService mailSenderService;

    public EmailService(EmailValidationRepository emailValidationRepository, MailSenderService mailSenderService) {
        this.emailValidationRepository = emailValidationRepository;
        this.mailSenderService = mailSenderService;
    }

    public MyUser findByToken(String token) {
        return emailValidationRepository.findByToken(token)
                .orElseThrow(()->new MyUserNotFoundException("no user found for provided token"))
                .getUser();
    }

    public void prepareEmailValidation(MyUser user) {
        if(emailValidationRepository.findByUser(user) != null) {
            emailValidationRepository.deleteByUser(user);
        }

        String uuid;
        do{
            uuid = UUID.randomUUID().toString();
        }while (emailValidationRepository.findByToken(uuid).isPresent());

        EmailValidation emailValidation = new EmailValidation();
        emailValidation.setUser(user);
        emailValidation.setToken(uuid);

        emailValidationRepository.save(emailValidation);

        try{
            String text = "To confirm your email address," +
                    " please click on the following link: http://localhost:8080/verify?token=";
            mailSenderService.sendEmail(user.getEmail(),"Email Validation", text +uuid);
        }catch(Exception e){
            throw new MailCanNotBeSentException("unable to send mail ");
        }
    }

    public void deleteByUser(MyUser user) {
        emailValidationRepository.deleteByUser(user);
    }

    public void sendAdminInvitation(AdminInvitation adminInvitation) {
        MyUser user = adminInvitation.getUser();
        try{
            String text = "You was invited to be an admin! \n" +
                    " To accept invitation, please follow " +
                    "this link: http://localhost:8080/newAdminRegister?token="+adminInvitation.getToken();
            mailSenderService.sendEmail(user.getEmail(),"Email Validation", text );
        }catch(Exception e){
            throw new MailCanNotBeSentException("unable to send mail ");
        }
    }
}
