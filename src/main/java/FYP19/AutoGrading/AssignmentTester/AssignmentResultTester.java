package FYP19.AutoGrading.AssignmentTester;


import FYP19.AutoGrading.JSONConverter.JSONClassConverter;
import FYP19.AutoGrading.MethodTester.MethodTester;
import FYP19.AutoGrading.ReflectionImp.interfaces.ReflectionCallerErrorException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class AssignmentResultTester {
    private JSONClassConverter testClass;

    private Map<String, List<List<Object>>> testData;
    private Map<String, List<List<Class<?>>>> testDataType;
    private String constructorName;
    private List<String> testMethodName;


    public AssignmentResultTester(String dataType){
        JSONObject dataTypeObject = JSON.parseObject(dataType);
        this.testClass = new JSONClassConverter(dataTypeObject);
    }


    public void dataUpdate(String data){
        JSONObject dataObject = JSON.parseObject(data);
        this.testData = testClass.batchTestDataLoader(dataObject);
        this.testDataType = testClass.batchTestMethodParametersTypeLoader(dataObject);
        this.constructorName = testClass.getConstructorName(dataObject);
        this.testMethodName = testClass.batchTestMethodName(dataObject);
    }


    public Map<String,List<Object>> classTest(String classPath, String className) throws ReflectionCallerErrorException {
        MethodTester classTester = new MethodTester(classPath, className, constructorName, testData, testDataType);
        return classTester.testMethod(this.testMethodName);
    }

    public Map<String,List<Boolean>> resultCompare(Map<String, List<Object>> studentResult, Map<String, List<Object>> teacherResult){
        Map<String,List<Boolean>> compareResult = new HashMap<>(this.testMethodName.size());
        String methodName;
        for (int i = 0; i < this.testMethodName.size(); i++) {
            methodName = this.testMethodName.get(i);
            compareResult.put(methodName, resultCompare(studentResult.get(methodName),teacherResult.get(methodName)));
        }
        return compareResult;
    }

    private List<Boolean> resultCompare(List<Object> studentFunctionResult, List<Object> teacherFunctionResult){
        List<Boolean> functionCompareResult = new LinkedList<>();
        for (int i = 0; i < teacherFunctionResult.size(); i++) {
            functionCompareResult.add(teacherFunctionResult.get(i).equals(studentFunctionResult.get(i)));
        }
        return functionCompareResult;
    }

    public Map<String, List<List<Object>>> getTestData(){
        return this.testData;
    }

    public Map<String, List<List<Class<?>>>> getTestDataType(){
        return this.testDataType;
    }

    public List<String> getTestMethodName(){
        return this.testMethodName;
    }



}
