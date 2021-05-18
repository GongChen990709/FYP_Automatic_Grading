package FYP19.AutoGrading.JSONConverter;

import FYP19.AutoGrading.JSONConverter.interfaces.JsonFormatErrorException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *  only analysing once, but can load many times with different data
 */

public class JSONClassConverter {
    String className;
    //Storing the dataType analysing result for a class (all of its functions)
    Map<String, JSONFunctionConverter> classFunction;
    //    Map<String, List<Class<?>>> classFunctionParametersType;
    Map<String, List<List<Class<?>>>> classFunctionParametersType;

    public JSONClassConverter(JSONObject dataTypeObject){
        className = dataTypeObject.getString("className");
        JSONArray allFunctionsArray = dataTypeObject.getJSONArray("allFunctions");
        classFunction = new HashMap<>();
        classFunctionParametersType = new HashMap<>();
        for (Object o : allFunctionsArray) {
            JSONFunctionConverter converter = new JSONFunctionConverter((JSONObject) o);
            classFunction.put(converter.getFunctionName(), converter);
            classFunctionParametersType.put(converter.getFunctionName(),converter.getParameterClasses());
        }
    }

    //returning the Map storing the testData(maybe just part of the functions in one class will be tested)
    public Map<String, List<List<Object>>> batchTestDataLoader(JSONObject dataObject){
        String CNInData = dataObject.getString("className");
        if (!CNInData.equals(this.className)) throw new JsonFormatErrorException("inconsistent className for batch analysis");
        JSONArray testBatchArray = dataObject.getJSONArray("testBatch");
        Map<String, List<List<Object>>> testMethodWithTestData = new HashMap<>(testBatchArray.size());
        for (Object json : testBatchArray) {
            JSONObject jsonObj = (JSONObject) json;
            //getting the functionName ready to test
            String functionName = jsonObj.getString("functionName");
            //getting the testData and putting it into the Map
            testMethodWithTestData.put(functionName, this.classFunction.get(functionName).JSONTestDataLoader(jsonObj));
        }
        return testMethodWithTestData;
    }

    //getting the test method parametersType (maybe just part of the functions in one class will be tested)
    public Map<String, List<List<Class<?>>>> batchTestMethodParametersTypeLoader(JSONObject dataObject){
        String CNInData = dataObject.getString("className");
        if (!CNInData.equals(this.className)) throw new JsonFormatErrorException("inconsistent className for batchType analysis");
        JSONArray testBatchArray = dataObject.getJSONArray("testBatch");
        Map<String, List<List<Class<?>>>> testMethodWithParametersType = new HashMap<>(testBatchArray.size());
        for (Object json : testBatchArray){
            JSONObject jsonObj = (JSONObject) json;
            String functionName = jsonObj.getString("functionName");
            testMethodWithParametersType.put(functionName, this.classFunction.get(functionName).JSONTestDataTypeLoader(jsonObj));
        }
        return testMethodWithParametersType;
    }

    public List<String> batchTestMethodName(JSONObject dataObject){
        String constructorName = dataObject.getString("constructorName");
        String CNInData = dataObject.getString("className");
        if (!CNInData.equals(this.className)) throw new JsonFormatErrorException("inconsistent className for batchType analysis");
        JSONArray testBatchArray = dataObject.getJSONArray("testBatch");
        List<String> methodName = new LinkedList<String>();
        for (Object json : testBatchArray){
            JSONObject jsonObj = (JSONObject) json;
            String functionName = jsonObj.getString("functionName");
            methodName.add(functionName);
        }
        methodName.remove(constructorName);
        return methodName;
    }

    public String getConstructorName(JSONObject dataObject){
        return dataObject.getString("constructorName");
    }

    public String getClassName(){
        return this.className;
    }



}
