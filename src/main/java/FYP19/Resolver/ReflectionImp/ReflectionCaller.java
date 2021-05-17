package FYP19.Resolver.ReflectionImp;


import FYP19.Resolver.MethodTester.MethodCallerErrorException;
import FYP19.Resolver.ReflectionImp.interfaces.ReflectionCallerErrorException;

import java.util.List;


public class ReflectionCaller {
    private ReflectionLoader loader;
    private Object reflectObj;


    public ReflectionCaller(String className, List<Class<?>> parametersType, Object... parameters ) throws ReflectionCallerErrorException {
        try{
            this.loader = new ReflectionLoader(className);
            this.reflectObj = loader.instantiation(transform(parametersType), parameters);
        } catch (ReflectiveOperationException e){
            throw new ReflectionCallerErrorException(e.getMessage());
        }
    }

    public ReflectionCaller(String classPath, String className, List<Class<?>> parametersType, Object... parameters ) throws ReflectionCallerErrorException {
        try{
            this.loader = new ReflectionLoader(classPath, className);
            this.reflectObj = loader.instantiation(transform(parametersType), parameters);
        } catch (ReflectiveOperationException e){
            throw new ReflectionCallerErrorException(e.getMessage());
        }
    }

    public Object methodCaller(String singleMethod, List<Class<?>> parametersType, Object... parameters) throws MethodCallerErrorException {
        try {
            return loader.invoke(reflectObj,singleMethod, transform(parametersType), parameters);
        }catch (ReflectiveOperationException e){
            throw new MethodCallerErrorException(e.getMessage());
        }
    }

    private Class<?>[] transform(List<Class<?>> parametersType) {
        Class<?>[] classes = new Class[parametersType.size()];
        parametersType.toArray(classes);
        return classes;
    }
}
