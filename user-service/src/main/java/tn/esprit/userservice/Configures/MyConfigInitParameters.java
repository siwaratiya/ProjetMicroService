package tn.esprit.userservice.Configures;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tn.esprit.userservice.Mappers.AttachmentMapper;
import tn.esprit.userservice.Services.Class.FileService;

@Configuration
public class MyConfigInitParameters {
    //@Value("${server.servlet.context-path}")
    //private String pathServiceMail;

    @Value("${myApp.file.forgotPassword_HTML}")
    private String file_forgotPassword_HTML;
    @Value("${myApp.file.ConfirmMail_HTML}")
    private String file_ConfirmMail_HTML;
    @Value("${myApp.file.PermissionRole_HTML}")
    private String file_PermissionRole_HTML;
    @Value("${myApp.file.defaultUserPhoto}")
    private String file_defaultUserPhoto;
    @Value("${myApp.file.defaultCoverPhoto}")
    private String file_defaultCoverPhoto;
    @Value("${myApp.link.WebPage}")
    private String pageHomeWebPage;
    @Value("${myApp.link.Path.signIn}")
    private String pathLinkSignIn;
    @Value("${myApp.link.Path.update_password_forgot}")
    private String pathLinkPasswordForgot;
    @Value("${myApp.link.Path.error}")
    private String pathLinkError;
    @Value("${myApp.link.Path.AttachementDownload}")
    private String pathAttachementDownload;
    @Value("${myApp.link.GlobalBackEnd}")
    private String linkGlobalBackEnd;
    @Value("${server.servlet.context-path}")
    private String pathServiceUser;
    public static String staticLinkServiceUser;

    @Bean
    public String configure() {
        AttachmentMapper.host_ContextPath = linkGlobalBackEnd + pathServiceUser +pathAttachementDownload;
        //http://localhost:8099/biochar/user-service/attachment/download/
        FileService.link_forgotPassword_HTML=file_forgotPassword_HTML;
        FileService.link_ConfirmMail_HTML=file_ConfirmMail_HTML;
        FileService.link_PermissionRole_HTML=file_PermissionRole_HTML;
        FileService.defaultUserPhoto=file_defaultUserPhoto;
        FileService.defaultCoverPhoto=file_defaultCoverPhoto;
        FileService.pageHomeLink= pageHomeWebPage;
        FileService.pathSignIn= pathLinkSignIn;
        FileService.pathError=pathLinkError;
        FileService.pathLinkPasswordForgot=pathLinkPasswordForgot;
        MyConfigInitParameters.staticLinkServiceUser=linkGlobalBackEnd+pathServiceUser;
        //http://localhost:8099/management-offers/mail-service/attachment/download/
        //MyConfigInitParameters.staticLinkServiceMail = linkGlobalBackEnd + pathServiceMail;
        return "configured";
    }

}