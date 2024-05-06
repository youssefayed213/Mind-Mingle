package com.example.mindmingle.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
public class SmsService {
    @Value("ACae1107914c91243520ced68cee2c3973") // Read from application.properties or application.yml
    private String twilioAccountSid;

    @Value("546865e31e2c6e43c0f48ad85a170b5b") // Read from application.properties or application.yml
    private String twilioAuthToken;

    @Value("+13185209248") // Your Twilio phone number (read from application.properties or application.yml)
    private String twilioFromNumber;



    public  void sendSms(String recipientPhoneNumber, String messageBody) {
        twilioAccountSid = "ACae1107914c91243520ced68cee2c3973";
        twilioAuthToken = "546865e31e2c6e43c0f48ad85a170b5b";
        twilioFromNumber ="+13185209248";
        System.out.println("Twilio Account SID: " + twilioAccountSid);
        System.out.println("Twilio Auth Token: " + twilioAuthToken);
        System.out.println("Twilio From Number: " + twilioFromNumber);
        Twilio.init(twilioAccountSid, twilioAuthToken);
        Message message = Message.creator(
                new PhoneNumber(recipientPhoneNumber),
                new PhoneNumber(twilioFromNumber),
                messageBody
        ).create();

        System.out.println("Message SID: " + message.getSid());
    }
}
