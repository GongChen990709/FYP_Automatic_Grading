package FYP19.AutoGrading.JSONConverter;


import FYP19.AutoGrading.JSONConverter.interfaces.IJSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.JsonFormatErrorException;
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
    private List<List<IJSONNode>> parameterNodes = new LinkedList<>();
    private List<IJSONNode> returnNode = new LinkedList<>();
    private List<List<Class<?>>> parameterClasses = new LinkedList<>();
    private List<Class<?>> returnClass = new LinkedList<>();

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

        JSONArray returnTypeArray = jsonTypeSource.getJSONArray("returnTypes");
        for (int i = 0; i < returnTypeArray.size(); i++) {
            if (((JSONObject)returnTypeArray.get(i)).get("returnType").equals("null")){
                this.returnNode.add(i, JSONNode.VOID);
            } else {
                //Getting the jsonObject storing returnType from the org jsonObject(top jsonObject)
                JSONObject returnType = ((JSONObject) returnTypeArray.get(i)).getJSONObject("returnType");
                //Analysing the jsonObjects and storing info into nodes, which represent returnType
                this.returnNode.add(i, JSONNode.convert(returnType));
                this.returnClass.add(i, this.returnNode.get(i).getContentClass());
            }
        }

        JSONArray polymorphism = jsonTypeSource.getJSONArray("overLoad");
        for (Object source : polymorphism) {
            List<IJSONNode> eachParameters = new LinkedList<>();
            List<Class<?>> eachParaClasses = new LinkedList<>();
            JSONArray parameters = ((JSONObject)source).getJSONArray("parameters");
            for (Object each: parameters) {
                IJSONNode newNode = JSONNode.convert((JSONObject) each);
                eachParameters.add(newNode);
                eachParaClasses.add(newNode.getContentClass());
            }
            //Analysing the jsonObjects and storing info into nodes, which represent parameterType
            this.parameterNodes.add(eachParameters);
            //if all parameters are generics, setting all parameterClasses as "Object" class
            this.parameterClasses.add(eachParaClasses);
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

    //dataType for each batch
    public List<List<Class<?>>> JSONTestDataTypeLoader(JSONObject jsonDataSource){
        //Getting the jsonObject from data json file
        String FNInData = jsonDataSource.getString("functionName");
        String CNInData = jsonDataSource.getString("className");
        if (!FNInData.equals(this.functionName) || !CNInData.equals(this.className)) throw new JsonFormatErrorException("inconsistent functionName or className");

        JSONArray batch = jsonDataSource.getJSONArray("batch");
        List<List<Class<?>>> batchDataType = new LinkedList<>();
        for (int i = 0; i < batch.size(); i++) {
            batchDataType.add(parameterTypeLoader((JSONObject) batch.get(i)));
        }
        return batchDataType;
    }


    //If there is no parameter, a list with size=0 will be returned
    private List<Object> parameterLoader(JSONObject batchContent){
        //Getting the jsonArray(series of jsonObject indicating the parameterValue is waiting for loading)
        JSONArray parametersValue = batchContent.getJSONArray("parametersValue");
        int typeNumber = batchContent.getIntValue("typeNumber");
        List<Object> parameterData = new LinkedList<>();
        for (int i = 0; i < this.parameterNodes.get(typeNumber).size(); i++) {
            parameterData.add(i, parameterNodes.get(typeNumber).get(i).backward((JSONObject) parametersValue.get(i)));
        }
        return parameterData;
    }

    private List<Class<?>> parameterTypeLoader(JSONObject batchContent){
        int typeNumber = batchContent.getIntValue("typeNumber");
        return parameterClasses.get(typeNumber);
    }

    public List<List<Class<?>>> getParameterClasses(){
        return this.parameterClasses;
    }

    public List<Class<?>> getReturnClass(){
        return this.returnClass;
    }

    public String getFunctionName(){
        return this.functionName;
    }

    public String getClassName(){
        return this.className;
    }


}





