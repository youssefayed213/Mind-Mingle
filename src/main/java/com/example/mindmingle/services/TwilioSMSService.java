package com.example.mindmingle.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioSMSService{

    // Twilio credentials
    private final String ACCOUNT_SID = "AC9cbb48ee8e608af6e03aae411beb20b8";
    private final String AUTH_TOKEN = "1699573e0a1b763280a77b49b10529a6";
    private final String TWILIO_PHONE_NUMBER = "+12514510211";

    public void sendSMS(String phoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                message
        ).create();
    }
}
