package FYP19.Resolver.ReflectionImp;




import FYP19.Resolver.ReflectionImp.interfaces.InstantiationErrorException;
import FYP19.Resolver.ReflectionImp.interfaces.InvokeErrorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;



public class ReflectionLoader {
    private final Class<?> clz;
    //loading the specified class
    public ReflectionLoader(String fullClassName) throws ClassNotFoundException {
        this.clz = Class.forName(fullClassName);//ClassNotFoundException
    }

    public ReflectionLoader(String classPath, String className) throws ClassNotFoundException {
        SelfClassLoader selfClassLoader = new SelfClassLoader(classPath);
        this.clz = selfClassLoader.loadClass(className);
    }
    //Instantiation of a refection object(having parameters) for the specified class
    public Object instantiation(Class<?>[] parametersType, Object... parameters) throws InstantiationErrorException {
        try {
            Constructor<?> constructor = this.clz.getConstructor(parametersType);//NoSuchMethodException
            return constructor.newInstance(parameters);//IllegalAccessException, InvocationTargetException, InstantiationException
        } catch (ReflectiveOperationException e) {
            throw new InstantiationErrorException(e.getMessage());
        }
    }
    //Executing the specified method in the refection object
    public Object invoke(Object reflectObj, String methodName, Class<?>[] parametersType, Object... parameters) throws InvokeErrorException {
        try{
            Method function = this.clz.getMethod(methodName, parametersType);//NoSuchMethodException
            return function.invoke(reflectObj,parameters);//InvocationTargetException, IllegalAccessException
        } catch (ReflectiveOperationException e){
            throw new InvokeErrorException(e.getMessage());
        }
    }
}
