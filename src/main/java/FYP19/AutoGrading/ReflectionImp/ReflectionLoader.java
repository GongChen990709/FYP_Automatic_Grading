package FYP19.AutoGrading.ReflectionImp;




import FYP19.AutoGrading.ReflectionImp.interfaces.InstantiationErrorException;
import FYP19.AutoGrading.ReflectionImp.interfaces.InvokeErrorException;

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
        SelfClassLoader selfClassLoader = SelfClassLoader.getClassLoader(classPath);
        this.clz = selfClassLoader.loadClass(className);
    }
    //Instantiation of a refection object(having parameters) for the specified class
    public Object instantiation(List<Class<?>> parametersType, Object... parameters) throws InstantiationErrorException  {
        try {
            Constructor<?> constructor = this.clz.getConstructor(transform(parametersType));//NoSuchMethodException
            return constructor.newInstance(parameters);//IllegalAccessException, InvocationTargetException, InstantiationException
        } catch (ReflectiveOperationException e) {
            throw new InstantiationErrorException(e.getMessage());
        }
    }
    //Executing the specified method in the refection object
    public Object invoke(Object reflectObj, String methodName, List<Class<?>> parametersType, Object... parameters) throws InvokeErrorException {
        try{
            Method function = this.clz.getMethod(methodName, transform(parametersType));//NoSuchMethodException
            return function.invoke(reflectObj,parameters);//InvocationTargetException, IllegalAccessException
        } catch (ReflectiveOperationException e){
            throw new InvokeErrorException(e.getMessage());
        }
    }

    public Class<?> getClz(){
        return this.clz;
    }

    private Class<?>[] transform(List<Class<?>> parametersType) {
        Class<?>[] classes = new Class[parametersType.size()];
        parametersType.toArray(classes);
        return classes;
    }

}
