package com.xiaobai.classcommunication.Dao;

public interface MailserviceIml1 {

    boolean sendSimpleMail(String to, String subject, String content);

    boolean sendHtmlMail(String to, String subject, String content);

    boolean sendAttachmentsMail(String to, String subject, String content, String filePath);
}
