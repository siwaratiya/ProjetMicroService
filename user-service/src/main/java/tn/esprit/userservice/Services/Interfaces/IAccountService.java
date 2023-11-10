package tn.esprit.userservice.Services.Interfaces;


import tn.esprit.userservice.Dtos.AuthenticationRequestDto;
import tn.esprit.userservice.Dtos.MsgReponseStatusDto;
import tn.esprit.userservice.Entitys.Account;
import tn.esprit.userservice.Entitys.Roles;
import tn.esprit.userservice.Libs.IGenericCRUD;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface IAccountService extends IGenericCRUD<Account,Long> {
    Account selectbyUsername(String  Username);
    List<Account> selectbyMultipleUsername(String[] usernames);
    MsgReponseStatusDto register(AuthenticationRequestDto request) throws IOException, InterruptedException, MessagingException;
    MsgReponseStatusDto confirmEmail(String username ,   String code);
    MsgReponseStatusDto sendMailCodeForgotPassword(String username , String email) throws IOException, InterruptedException, MessagingException ;
    MsgReponseStatusDto updateForgotPassword(String username, String code, String newPassword);
    MsgReponseStatusDto updatePassword(String usename,String currentPassword, String newPassword);
    MsgReponseStatusDto updateRoleAndActivate(String username , Roles role, boolean enabled);
    MsgReponseStatusDto permissionUpdateRole(String username,Roles currentRole,Roles newRole) throws IOException, InterruptedException, MessagingException;
    MsgReponseStatusDto updateRole(String username,Roles role);
}