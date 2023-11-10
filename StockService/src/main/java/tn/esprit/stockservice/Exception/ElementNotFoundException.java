package tn.esprit.stockservice.Exception;

public class ElementNotFoundException extends RuntimeException{
    public  ElementNotFoundException(String message){
        super(message);
    }
}