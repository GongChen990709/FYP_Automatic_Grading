package FYP19.AutoGrading.JSONConverter.interfaces;

public class JsonFormatErrorException extends RuntimeException{
    public JsonFormatErrorException(){
        super();
    }

    public JsonFormatErrorException(String s){
        super(s);
    }
}
