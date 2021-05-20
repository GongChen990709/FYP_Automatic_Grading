package FYP19.AutoGrading.MethodTester;



import FYP19.AutoGrading.ReflectionImp.ReflectionCaller;
import FYP19.AutoGrading.ReflectionImp.interfaces.ReflectionCallerErrorException;

import java.util.*;


/**
 * batchTesting for the specified method in the specified class
 */
public class MethodTester {
    //Storing the class waiting for test
    private ReflectionCaller testClass;
    //Each "String" in the map represents a method waiting for test
    //Each List<Object> stored in a "List<List<Object>>" represents a testing dataSet(parameters value)
    private Map<String, List<List<Object>>> testMethodWithTestData;

    private Map<String, List<List<Class<?>>>> testMethodWithParametersType;


    //No-parameter instantiation for the specified testClass
    public MethodTester(String classPath, String className, String constructorName, Map<String,List<List<Object>>> testMethodWithTestData, Map<String, List<List<Class<?>>>> testMethodWithParametersType) throws ReflectionCallerErrorException {
        this.testMethodWithTestData = testMethodWithTestData;
        this.testMethodWithParametersType = testMethodWithParametersType;
        this.testClass = new ReflectionCaller(classPath, className, this.testMethodWithParametersType.get(constructorName).get(0), this.testMethodWithTestData.get(constructorName).get(0).toArray());
    }

    //Testing a serial methods with the testing dataSets stored in the map, return the testing results
    public Map<String,List<Object>> testMethod(List<String> testMethodName) {
        Map<String,List<Object>> testResult = new HashMap<>(testMethodName.size());
        for (int i = 0; i < testMethodName.size(); i++) {
            String singleMethod = testMethodName.get(i);
            testResult.put(singleMethod, testMethod(singleMethod));
        }
        return testResult;
    }


    private List<Object> testMethod(String singleMethod) {
        List<Object> testResult = new LinkedList<>();
        List<List<Object>> parameters = testMethodWithTestData.get(singleMethod);
        List<List<Class<?>>> parametersType = testMethodWithParametersType.get(singleMethod);
        for (int i = 0; i < parameters.size(); i++) {
            Object currentResult;
            try {
                currentResult = testClass.methodCaller(singleMethod, parametersType.get(i), parameters.get(i).toArray());
            } catch (MethodCallerErrorException e){
                // TODO: 2021/4/28 add exception handler
                currentResult = new Exception("error");
            }
            testResult.add(i, currentResult);
        }
        return testResult;
    }
}














//    public List<Object> testMethod(String singleMethod) {
//        List<Object> testResult = new LinkedList<>();
//        List<List<Object>> parameters = testMethodWithTestData.get(singleMethod);
//        List<Class<?>> parametersType = testMethodWithParametersType.get(singleMethod);
//        for (int i = 0; i < parameters.size(); i++) {
//            Object currentResult;
//            try {
//                currentResult = testClass.methodCaller(singleMethod, parametersType, parameters.get(i).toArray());
//            } catch (MethodCallerErrorException e){
//                // TODO: 2021/4/28 add exception handler
//                currentResult = new Exception("error");
//            }
//            testResult.add(i, currentResult);
//        }
//        return testResult;
//    }




