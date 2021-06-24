package FYP19.AutoGrading.ReflectionImp;


import FYP19.AutoGrading.MethodTester.MethodCallerErrorException;
import FYP19.AutoGrading.ReflectionImp.interfaces.ReflectionCallerErrorException;

import java.util.List;


public class ReflectionCaller {
    private ReflectionLoader loader;
    private Object reflectObj;

//    public ReflectionCaller(String className) throws ReflectionCallerErrorException {
//        this(className, new LinkedList<>());
//    }

    public ReflectionCaller(String className, List<Class<?>> parametersType, Object... parameters ) throws ReflectionCallerErrorException {
        try{
            this.loader = new ReflectionLoader(className);
            this.reflectObj = loader.instantiation(parametersType, parameters);
        } catch (ReflectiveOperationException e){
            throw new ReflectionCallerErrorException(e.getMessage());
        }
    }

    public ReflectionCaller(String classPath, String className, List<Class<?>> parametersType, Object... parameters ) throws ReflectionCallerErrorException {
        try{
            this.loader = new ReflectionLoader(classPath, className);
            this.reflectObj = loader.instantiation(parametersType, parameters);
        } catch (ReflectiveOperationException e){
            throw new ReflectionCallerErrorException(e.getMessage());
        }
    }

    public Object methodCaller(String singleMethod, List<Class<?>> parametersType, Object... parameters) throws MethodCallerErrorException {
        try {
            return loader.invoke(reflectObj,singleMethod, parametersType, parameters);
        }catch (ReflectiveOperationException e){
            throw new MethodCallerErrorException(e.getMessage());
        }
    }
}
