package com.apis.service;

import com.apis.entity.ConfirmationToken;
import com.apis.exception.TicketingProjectException;
import org.springframework.mail.SimpleMailMessage;

public interface ConfirmationTokenService {

    ConfirmationToken save(ConfirmationToken confirmationToken);

    void sendEmail(SimpleMailMessage email);

    ConfirmationToken readByToken(String token) throws TicketingProjectException;

    void delete(ConfirmationToken confirmationToken);

}
