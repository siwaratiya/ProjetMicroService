package tn.esprit.stockservice.Exception;

public class NoProductException extends  RuntimeException{
    public NoProductException(String message){
        super(message);
    }
}
