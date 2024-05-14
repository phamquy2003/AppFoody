package com.henrryd.appfoody2.other;

import android.util.Log;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MAILSender extends Thread{
    private final String email;
    private final String subject;
    private final String message;

    public MAILSender(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }
    @Override
    public void run() {
        super.run();
        String host = "smtp.gmail.com";//local host mail
        int port = 587;
        final String username = "anhquyk797@gmail.com";
        final String password = "nqlh brzd jflo cktr";

        // Cấu hình các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//cần phải xác thực trước khi gửi mail
        props.put("mail.smtp.starttls.enable", "true");//TLS cung cấp mã hóa và đảm bảo rằng giao tiếp an toàn.
        props.put("mail.smtp.host", host); // host của mail
        props.put("mail.smtp.port", port); // cổng kết nối

        //Tạo thêm luồng hoạt động mới truy cap den email chu
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Tạo tin nhắn email
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            // Gửi email
            Transport.send(mimeMessage);
            Log.d("emailSender", "Email sent successfully.");

        } catch (MessagingException e) {
            Log.d("emailSender", "Email sent fail." + e.toString());
            e.printStackTrace();
        }

        Log.e("Thongbao", "gui xong roi");
    }
}
