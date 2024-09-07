package pl.blackwater.rpg.data.exceptions;

public class RPGUserNotFoundException extends Exception{

    public RPGUserNotFoundException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
