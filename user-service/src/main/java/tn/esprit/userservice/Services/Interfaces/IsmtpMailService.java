package tn.esprit.userservice.Services.Interfaces;



import tn.esprit.userservice.Dtos.mail.Msg;

import javax.mail.MessagingException;

public interface IsmtpMailService {
    void connect ();
    void sending( Msg msg ) throws MessagingException ;
    void sendingSimple( Msg msg ) throws MessagingException ;
    void sendingWithViewHTML( Msg msg ) throws MessagingException ;
    void sendingWithDocument( Msg msg ) throws MessagingException ;
    void sendingWithDocuments( Msg msg ) throws MessagingException;
    void sendingWithStreamDocuments( Msg msg ) throws MessagingException;
    void sendingMultiBodyContent( Msg msg ) throws MessagingException;
}
