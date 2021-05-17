package FYP19.Resolver.JSONConverter;


import FYP19.Resolver.JSONConverter.interfaces.IJSONNode;
import FYP19.Resolver.JSONConverter.interfaces.JsonFormatErrorException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.LinkedList;
import java.util.List;

/**
 * Obtaining two jsonObjects, one is for dataType specification, another is for dataValue specification
 * tips: definition of dataType of data stored in jsonArray is "Object"
 * JSONFunctionConverter represents the analysis result for a function
 */

public class JSONFunctionConverter {
//    private final JSONObject jsonTypeSource;//Converting json file into a jsonObject
    private final String functionName;
    private final String className;
//    private final boolean isAllGenerics;
    private List<IJSONNode> parameterNodes;//Storing the analysing result for parameterType
    private IJSONNode returnNode;//Storing the analysing result for returnType
    private List<Class<?>> parameterClasses;
    private Class<?> returnClass;

    /**
     *
     * @param jsonTypeSource inputDataType and returnDataType declaration
     */
    public JSONFunctionConverter(JSONObject jsonTypeSource) {
        //Getting the functionName ready to analyse
        this.functionName = jsonTypeSource.getString("functionName");
        //Getting the className ready to analyse
        this.className = jsonTypeSource.getString("className");
        //Getting the boolean value indicating if all of the parameters of the method are generics
//        this.isAllGenerics = Boolean.parseBoolean(jsonTypeSource.getString("isAllGenerics"));

        if (jsonTypeSource.get("returnType").equals("null")){
            this.returnNode = JSONNode.VOID;
        } else {
            //Getting the jsonObject storing returnType from the org jsonObject(top jsonObject)
            JSONObject returnType = jsonTypeSource.getJSONObject("returnType");
            //Analysing the jsonObjects and storing info into nodes, which represent returnType
            this.returnNode = JSONNode.convert(returnType);
            this.returnClass = this.returnNode.getContentClass();
        }

        //Getting the jsonArray(series of jsonObject indicating the parameterType is waiting for analysing)
        JSONArray parameters = jsonTypeSource.getJSONArray("parameters");
        this.parameterNodes = new LinkedList<>();
        this.parameterClasses = new LinkedList<>();
        for (Object source : parameters) {
            IJSONNode newNode = JSONNode.convert((JSONObject) source);
            //Analysing the jsonObjects and storing info into nodes, which represent parameterType
            this.parameterNodes.add(newNode);
            //if all parameters are generics, setting all parameterClasses as "Object" class
            this.parameterClasses.add(newNode.getContentClass());
        }
    }

    /**
     *
     * @param jsonDataSource inputData declaration
     * @return a List storing function inputData in order
     */
    public List<List<Object>> JSONTestDataLoader(JSONObject jsonDataSource){
        //Getting the jsonObject from data json file
        String FNInData = jsonDataSource.getString("functionName");
        String CNInData = jsonDataSource.getString("className");
        if (!FNInData.equals(this.functionName) || !CNInData.equals(this.className)) throw new JsonFormatErrorException("inconsistent functionName or className");

//        if (jsonDataSource.get("batch").equals("null")){
//            return null;
//        }

        JSONArray batch = jsonDataSource.getJSONArray("batch");
        List<List<Object>> batchData = new LinkedList<>();
        for (int i = 0; i < batch.size(); i++) {
            batchData.add(parameterLoader((JSONObject) batch.get(i)));
        }
        return batchData;
    }

//If there is no parameter, a list with size=0 will be returned
    private List<Object> parameterLoader( JSONObject batchContent){
        //Getting the jsonArray(series of jsonObject indicating the parameterValue is waiting for loading)
        JSONArray parametersValue = batchContent.getJSONArray("parametersValue");
        List<Object> parameterData = new LinkedList<>();
        for (int i = 0; i < this.parameterNodes.size(); i++) {
            parameterData.add(i, parameterNodes.get(i).backward((JSONObject) parametersValue.get(i)));
        }
        return parameterData;
    }


    public List<Class<?>> getParameterClasses(){
        return this.parameterClasses;
    }

    public Class<?> getReturnClass(){
        return this.returnClass;
    }

    public String getFunctionName(){
        return this.functionName;
    }

    public String getClassName(){
        return this.className;
    }

}





