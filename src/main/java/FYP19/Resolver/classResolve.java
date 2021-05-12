package FYP19.Resolver;

public class classResolve {
    String a;
    Class<?> b;

    public classResolve(String string){
        a = string;
    }

    public Class<?> getsClass(){
        try {
            b = Class.forName(a);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }



}
