package com.pi.users.email;

import jakarta.mail.MessagingException;

public interface EmailSender {
    public void send(String to , String email);
    public void sendSetPasswordEmail(String email)throws MessagingException;
}
