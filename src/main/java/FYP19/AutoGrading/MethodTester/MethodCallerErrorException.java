package FYP19.AutoGrading.MethodTester;



public class MethodCallerErrorException extends ReflectiveOperationException {
    public MethodCallerErrorException() {
        super();
    }
    public MethodCallerErrorException(String s) {
        super(s);
    }
}
