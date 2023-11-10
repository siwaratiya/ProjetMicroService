package tn.esprit.userservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import tn.esprit.userservice.Dtos.AccountDto;
import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.MsgReponseStatusDto;
import tn.esprit.userservice.Dtos.ReponseStatus;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Mappers.AccountMapper;
import tn.esprit.userservice.Services.Class.FileService;
import tn.esprit.userservice.Services.Interfaces.IAccountService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/account")
public class AccountController {
    private final  IAccountService iAccountService;
    @Autowired
    public AccountController(@Qualifier("account-service") IAccountService iAccountService)
    {this.iAccountService = iAccountService;}

    @GetMapping
    public List<AccountDto> SelectAll () {
        List<Account>  accounts = iAccountService.selectAll () ;
        return accounts.stream().map(account -> AccountMapper.mapToDto(account)) .collect(Collectors.toList());
    }
    @GetMapping("{id}")
    public ResponseEntity<AccountDto> SelectById (@PathVariable long id) {
        Account account = iAccountService.selectById (id);
        return ResponseEntity.ok(AccountMapper.mapToDto(account));    }
    @GetMapping("select-by-username/{username}")
    public  ResponseEntity<AccountDto> selectbyUsername( @PathVariable("username") String  username){
        return ResponseEntity.ok(AccountMapper.mapToDto(iAccountService.selectbyUsername(username)));}
    @GetMapping("select-by-usernames/{usernames}")
    public  List<AccountDto> selectbyMultipleUsername( @PathVariable("usernames") String[] usernames){
        final List<Account>  accounts = iAccountService.selectbyMultipleUsername(usernames) ;
        return accounts.stream().map(account -> AccountMapper.mapToDto(account)) .collect(Collectors.toList());
    }
    @PostMapping
    public AccountDto Insert(@Validated @RequestBody AccountDto accountDto) {
        Account account =  AccountMapper.mapToEntity(accountDto);
        return AccountMapper.mapToDto(  iAccountService.insert(account));}
    @PutMapping("{id}")
    public  ResponseEntity<AccountDto> update( @PathVariable long id ,@RequestBody AccountDto accountDto){
        Account account =  AccountMapper.mapToEntity(accountDto);
        return ResponseEntity.ok(
                AccountMapper.mapToDto( iAccountService.update( id,  account))
        );}
    @DeleteMapping("{id}")
    public  ResponseEntity<HttpStatus> delete(@PathVariable  Long id ){
        iAccountService.delete( id );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); }









    @PostMapping("/register")
    public ResponseEntity<MsgReponseStatusDto> register(/*@Validated*/  @RequestBody AuthenticationRequestDto request) throws IOException, InterruptedException, MessagingException {
        return ResponseEntity.ok(iAccountService.register(request));}

    @GetMapping ("/confirm-email/{username}/{code}")
    public RedirectView confirmEmail(@PathVariable("username") String username , @PathVariable("code")  String code) {
        if ( iAccountService.confirmEmail( username , code).getStatus() == ReponseStatus.SUCCESSFUL)
        { return new RedirectView(FileService.pageHomeLink+FileService.pathSignIn); }
        return new RedirectView(FileService.pageHomeLink+FileService.pathError);  }

    @PutMapping("/update-role-and-activate/{username}/{role}/{enabled}")
    public  ResponseEntity<MsgReponseStatusDto>  updateRoleAndActivate (@PathVariable("username")String username , @PathVariable("role") Roles role, @PathVariable("enabled") boolean enabled){
        return ResponseEntity.ok( iAccountService.updateRoleAndActivate(  username ,  role,  enabled));}

    @PutMapping("/mail-code-forgot-password/{username}/{email}")
    public  ResponseEntity<MsgReponseStatusDto> sendMailCodeForgotPassword( @PathVariable("username")String username , @PathVariable("email") String email) throws IOException, InterruptedException,MessagingException {
        return ResponseEntity.ok( iAccountService.sendMailCodeForgotPassword( username ,  email));}

    @PutMapping("/update-forgot-password/{username}/{code}/{newpassword}")
    public  ResponseEntity<MsgReponseStatusDto>  updateForgotPassword (@PathVariable("username")String username , @PathVariable("code")String code , @PathVariable("newpassword") String newPassword){
        return ResponseEntity.ok( iAccountService.updateForgotPassword( username, code,  newPassword) );}

    @PutMapping("update-password/{username}/{currentpassword}/{newpassword}")
    public ResponseEntity<MsgReponseStatusDto>  updatePassword(@PathVariable("username")  String usename,
                                                               @PathVariable("currentpassword")  String currentPassword,
                                                               @PathVariable("newpassword")  String newPassword){
        return  ResponseEntity.ok((MsgReponseStatusDto) iAccountService.updatePassword(usename,currentPassword,newPassword));}

    @PutMapping("update-role-permission/{username}/{currentRole}/{newRole}")
    public ResponseEntity<MsgReponseStatusDto>  permissionUpdateRole(@PathVariable("username")  String usename,
                                                                     @PathVariable("currentRole")  Roles currentRole,
                                                                     @PathVariable("newRole")  Roles newRole) throws IOException, InterruptedException, MessagingException{
        return  ResponseEntity.ok((MsgReponseStatusDto) iAccountService.permissionUpdateRole(usename,currentRole , newRole));}

    @PutMapping("update-role/{username}/{role}")
    public RedirectView  updateRole(@PathVariable("username")  String usename, @PathVariable("role")  Roles role) throws IOException, InterruptedException, MessagingException{
        if ( iAccountService.updateRole(usename,role).getStatus() == ReponseStatus.SUCCESSFUL)
        { return new RedirectView(FileService.pageHomeLink+FileService.pathSignIn); }
        return new RedirectView(FileService.pageHomeLink+FileService.pathError);  }
}