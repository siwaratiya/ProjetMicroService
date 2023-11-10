package tn.esprit.userservice.Services.Class;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.userservice.Configures.MyConfigInitParameters;
import tn.esprit.userservice.Dtos.*;
import tn.esprit.userservice.Dtos.mail.BodyContent;
import tn.esprit.userservice.Dtos.mail.Msg;
import tn.esprit.userservice.Dtos.mail.TypeBody;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Entitys.Attachment;
import tn.esprit.userservice.Entitys.Category;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Exceptions.RessourceNotFoundException;
import tn.esprit.userservice.Libs.GenericCRUDService;
import tn.esprit.userservice.Libs.IObjectMapperConvert;
import tn.esprit.userservice.Repositorys.AccountRepository;
import tn.esprit.userservice.Services.Interfaces.*;

import javax.mail.MessagingException;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service("account-service")
public class AccountService extends GenericCRUDService<Account,Long> implements IAccountService {
    private final AccountRepository accountRepository;
    private final IFileService ifileService;
    private final IAttachmentService iAttachmentService;
    private final IObjectMapperConvert iObjectMapperConvert;
    //private final PasswordEncoder passwordEncoder;
    private  final IsmtpMailService mailSender;
    private  final IKeyCloakService iKeyCloakService;
    @Autowired
    public AccountService(AccountRepository accountRepository ,
                          IFileService ifileService,
                          IAttachmentService iAttachmentService,
                          IsmtpMailService mailSender,
                          IObjectMapperConvert iObjectMapperConvert,
                          IKeyCloakService iKeyCloakService)
    {super(accountRepository);
        this.accountRepository = accountRepository;
        this.ifileService = ifileService;
        this.iAttachmentService = iAttachmentService;
        this. mailSender = mailSender;
        this. iObjectMapperConvert = iObjectMapperConvert;
        this. iKeyCloakService = iKeyCloakService;}




    @Transactional
    public Account insert(Account object) {
        object.setCreatedAt(   LocalDateTime.now() );
        MultipartFile multipartFileUser,multipartFileCover = null;
        Attachment attachmentPhoto,attachmentCover  = null;
        try {
            multipartFileUser = ifileService.importFileToMultipartFile(FileService.defaultUserPhoto);
            multipartFileCover = ifileService.importFileToMultipartFile(FileService.defaultCoverPhoto);
        } catch (IOException e) {
            throw new tn.esprit.userservice.Exceptions.IOException(e.getMessage());
        }
        try {
            attachmentPhoto = iAttachmentService.saveAttachment(multipartFileUser);
            attachmentPhoto.setCategory(Category.PHOTOPROFILE);
            attachmentCover = iAttachmentService.saveAttachment(multipartFileCover);
            attachmentCover.setCategory(Category.COVERPICTURE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        object.addAttachment(attachmentPhoto);
        object.addAttachment(attachmentCover);
        return  accountRepository.save(object);
    }
    public Account update(Long id,  Account object) {
        Account account = accountRepository.findById(id).
                orElseThrow(()-> new RessourceNotFoundException("Service Account : update Account not existe with id : "+id))  ;
        //username;
        //role;
        //password;
        account.setFirstname(object.getFirstname());
        account.setLastname(object.getLastname());
        account.setUsername(object.getUsername());
        account.setCode(object.getCode());
        account.setEnabled(object.isEnabled());
        account.setCin(object.getCin());
        account.setDateOfBirth(object.getDateOfBirth());
        account.setPhone(object.getPhone());
        account.setEmail(object.getEmail());
        account.setLinkedIn(object.getLinkedIn());
        account.setGithub(object.getGithub());
        account.setGender(object.getGender());
        account.setState(object.getState());
        account.setCity(object.getCity());
        account.setZipCode(object.getZipCode());
        account.setAddress(object.getAddress());
        account = accountRepository.save(account);
        return account ;
    }
    @Override
    public Account selectbyUsername(String Username) {
        return accountRepository.findAccountByUsername(Username).
                orElseThrow(()-> new RessourceNotFoundException("Service Account ( selectbyUsername )  : cannot found account  : "+Username));
    }
    @Override
    public List<Account> selectbyMultipleUsername(String[] usernames) {
        return  accountRepository .findAccountDtosByUsernameIn(usernames) ;
    }























    @Override
    public MsgReponseStatusDto register(AuthenticationRequestDto request) throws IOException, InterruptedException, MessagingException {

        if( iKeyCloakService.isCorrectUserName (request.getUsername() ) &&  accountRepository.isCorrectUserName( request.getUsername()  ))
        {   return MsgReponseStatusDto.builder()
                .title("Register User")
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("other username found").build();}
        String  code = this.getRandomNumber( 100 , 999 ) +"-"+this.getRandomNumber( 100 , 999 ) +"-"+this.getRandomNumber(  100 , 999) +"-"+this.getRandomNumber(  100 , 999  );
        //        User user = User.builder()
        //                .username(request.getUsername())
        //                .password(passwordEncoder.encode(request.getPassword()))
        //                .enabled(true) //false
        //                .code(code)
        //                .role(Roles.Patient)
        //                .build();
        Account account = Account.builder()
                .createdAt(LocalDateTime.now())
                .username(request.getUsername())
                .password(request.getPassword())//.password(passwordEncoder.encode(request.getPassword()))
                .enabled(false) //false
                .code(code)
                .role(Roles.Patient)
                .email(request.getEmail())
                .build();
        String file =  ifileService.Edit_ConfirmMailPage(account.getUsername(),
                MyConfigInitParameters.staticLinkServiceUser +"/account/confirm-email/"+ account.getUsername()+"/"+code);
        Msg msg = Msg.builder().subject("Confirmation Address Mail")
                .email(account.getEmail())
                .text("body")
                .bodyContents(new ArrayList<BodyContent>(){{add(BodyContent.builder()
                        .content(file).
                        type(TypeBody.HTML).build());}}).build();

        mailSender.connect();
        mailSender.sendingMultiBodyContent(msg);
        this.insert(account);
        iKeyCloakService.insert(UserDto.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .enabled(false)
                .build());
        iKeyCloakService.assignRealmRoleToUser(account.getUsername(),Roles.Patient.toString());
        return MsgReponseStatusDto.builder()
                .title("Register User")
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("To complete next step you should verify confirmation mail").build();
    }
    @Override
    public MsgReponseStatusDto confirmEmail(String username ,   String code)
    {
        Account accountuser =  accountRepository.findAccountByCodeAndUsername( code, username).orElse(null);
        List<UserRepresentation> userRepresentation = iKeyCloakService.selectByUsername( username);
        if ( accountuser != null && !userRepresentation.isEmpty()){
            accountuser.setEnabled(true);
            accountuser.setCode("");
            this.update(accountuser.getId()  ,accountuser);
            userRepresentation.get(0).setEnabled(true);
            iKeyCloakService.update(userRepresentation.get(0).getId(), userRepresentation.get(0) );
            return MsgReponseStatusDto.builder()
                    .title("Confirmation Email User")
                    .status(ReponseStatus.SUCCESSFUL)
                    .datestamp(LocalDate.now())
                    .timestamp(LocalTime.now())
                    .message("you can login authentication").build();
        }
        return MsgReponseStatusDto.builder()
                .title("Confirmation Email User")
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Perhaps we can't find  your register").build(); }

    @Override
    public MsgReponseStatusDto sendMailCodeForgotPassword(String username , String email) throws IOException, InterruptedException, MessagingException {

        if ( !accountRepository.isCorrectEmailAndUsername(username, email)||!iKeyCloakService.isCorrectUserName (username)  ){
            return MsgReponseStatusDto.builder()
                    .title("Message")
                    .status(ReponseStatus.ERROR)
                    .datestamp(LocalDate.now())
                    .timestamp(LocalTime.now())
                    .message("Your mail is  not correct").build();}
        final String code =  this.getRandomNumber( 100 , 999 ) +"-"+
                this.getRandomNumber( 100 , 999 ) +"-"+
                this.getRandomNumber(  100 , 999) +"-"+
                this.getRandomNumber(  100 , 999  );
        Account  account = accountRepository.findUserByUsernameAndEmail( username ,  email ).orElse(null);
        account.setCode(code);
        accountRepository.save(account);

        String file =  ifileService.Edit_forgotPasswordPage(account.getUsername(), code );
        Msg msg = Msg.builder().subject("Forgot Password")
                .email(email)
                .text("body")
                .bodyContents(new ArrayList<BodyContent>(){{add(BodyContent.builder().content(file).type(TypeBody.HTML).build());}}).build();
        mailSender.connect();
        mailSender.sendingMultiBodyContent(msg);


        return MsgReponseStatusDto.builder()
                .title("Message")
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Your mail is correct so you see your email for complete next step").build();
    }

    @Override
    public MsgReponseStatusDto updateForgotPassword(String username, String code, String newPassword) {
        if (   accountRepository.isCorrectCode( code ) && !newPassword.isEmpty()) {
            Account account= accountRepository.findAccountByCodeAndUsername( code, username).orElse(null);
            if ((account.getCode()=="" || account.getCode().isEmpty())||(!iKeyCloakService.isCorrectUserName (username)) ){
                return MsgReponseStatusDto.builder()
                        .status(ReponseStatus.ERROR)
                        .datestamp(LocalDate.now())
                        .timestamp(LocalTime.now())
                        .message("you didn't  sent  Forgot Password in last moment").build();
            }
            //user.setPassword(   passwordEncoder.encode(newPassword)   );
            account.setPassword(newPassword);
            account.setCode("");
            accountRepository.save(account);
            List<UserRepresentation> userRepresentation = iKeyCloakService.selectByUsername( username);
            iKeyCloakService.update( userRepresentation.get(0).getId() , account) ;

            return MsgReponseStatusDto.builder()
                    .status(ReponseStatus.SUCCESSFUL)
                    .datestamp(LocalDate.now())
                    .timestamp(LocalTime.now())
                    .message("Your code is correct and we change password").build();}
        else { return MsgReponseStatusDto.builder()
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Your code is not correct or new password is empty").build();}
    }
    @Override
    @Transactional
    public MsgReponseStatusDto updatePassword(String username,String currentPassword, String newPassword) {
        if( newPassword.isEmpty() )
        {   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.ERROR)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("New Password empty").build();}

        if( !accountRepository. isCorrectUserName( username )||(!iKeyCloakService.isCorrectUserName (username)))
        {   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Cannot found username verify you enter correct")
                .build();}

        Account account = accountRepository.findAccountByUsername( username ).get();
//        boolean matches = passwordEncoder.matches(currentPassword , user.getPassword());
        //user.setPassword(passwordEncoder.encode(newPassword));
        account.setPassword(newPassword);
        List<UserRepresentation> userRepresentation = iKeyCloakService.selectByUsername( username);
        iKeyCloakService.update( userRepresentation.get(0).getId() , account) ;

        accountRepository.save(account);
        return MsgReponseStatusDto.builder()
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Successful update password")
                .build();
    }
    @Override
    @Transactional
    public MsgReponseStatusDto updateRoleAndActivate(String username , Roles role, boolean enabled) {
        if( !accountRepository. isCorrectUserName( username ))
        {   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Cannot found username verify you enter correct").build();}
        Account account = accountRepository.findAccountByUsername( username ).orElse(null);
        account.setRole(role);
        account.setEnabled(enabled);
        accountRepository.save(account);
        return MsgReponseStatusDto.builder()
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Successful update role and state enable user").build();
    }

    @Override
    @Transactional
    public MsgReponseStatusDto permissionUpdateRole(String username , Roles currentRole , Roles newRole) throws IOException, InterruptedException, MessagingException {
        if( !accountRepository. isCorrectUserName( username ))
        {   return MsgReponseStatusDto.builder()
                .status(ReponseStatus.UNSUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Cannot found username verify you enter correct").build();}

        String file =  ifileService.Edit_PermissionRolePage ( username ,  currentRole,newRole,
                MyConfigInitParameters.staticLinkServiceUser +"/account/update-role/"+username+"/"+newRole);

        mailSender.connect();
        List<Account> accountList =  accountRepository.findAccountsByRole(Roles.Chief_Service);
        for ( Account account : accountList ){
            Msg msg = Msg.builder().subject("Request for User Profile Role Change")
                    .email(account.getEmail())
                    .text("body")
                    .bodyContents(new ArrayList<BodyContent>(){{add(BodyContent.builder()
                            .content(file).
                            type(TypeBody.HTML).build());}}).build();
            mailSender.sendingMultiBodyContent(msg);
        }
        return MsgReponseStatusDto.builder()
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Successful update role and state enable user").build();
    }
    @Override
    @Transactional
    public MsgReponseStatusDto updateRole(String username,Roles role){
        if( !accountRepository. isCorrectUserName( username )||(!iKeyCloakService.isCorrectUserName (username))){
            return MsgReponseStatusDto.builder()
                    .status(ReponseStatus.UNSUCCESSFUL)
                    .datestamp(LocalDate.now())
                    .timestamp(LocalTime.now())
                    .message("Cannot found username verify you enter correct").build();}
        Account account = accountRepository.findAccountByUsername( username ).get();
        account.setRole(role);
        iKeyCloakService.assignRealmRoleToUser(username, role.toString());
        return MsgReponseStatusDto.builder()
                .status(ReponseStatus.SUCCESSFUL)
                .datestamp(LocalDate.now())
                .timestamp(LocalTime.now())
                .message("Successful update role and state enable user").build();
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}