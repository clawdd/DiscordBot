package Exceptions;

public class UndefinedSymbolException extends Exception{

    public UndefinedSymbolException (String token) {

        super("Undefined symbol: " + token);

    }
}
