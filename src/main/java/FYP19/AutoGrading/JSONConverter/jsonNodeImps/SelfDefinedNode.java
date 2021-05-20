package FYP19.AutoGrading.JSONConverter.jsonNodeImps;

import FYP19.AutoGrading.JSONConverter.JSONFunctionConverter;
import FYP19.AutoGrading.JSONConverter.JSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.IJSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.JsonFormatErrorException;
import FYP19.AutoGrading.ReflectionImp.ReflectionLoader;
import FYP19.AutoGrading.ReflectionImp.interfaces.InstantiationErrorException;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class SelfDefinedNode extends JSONNode {
    private JSONFunctionConverter constructor;
    private String classPath;
    private String className;
    private ReflectionLoader loader;

    public SelfDefinedNode(String dataType, String typeName) {
        super(dataType, typeName);
    }

    @Override
    public IJSONNode forward(JSONObject source) {
        this.classPath = source.getString("classPath");
        this.className = source.getString("className");
        JSONObject constructorInit = source.getJSONObject("constructorInit");
        this.constructor = new JSONFunctionConverter(constructorInit);
        try{
            this.loader = new ReflectionLoader(this.classPath, this.className);
            this.classType = loader.getClz();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public Object backward(JSONObject data) throws JsonFormatErrorException{
        JSONObject constructData = data.getJSONObject("constructData");
        List<List<Object>> constructDataList = constructor.JSONTestDataLoader(constructData);
        List<List<Class<?>>> constructorDataTypeList = constructor.JSONTestDataTypeLoader(constructData);
        List<Object> loadData = constructDataList.get(0);
        List<Class<?>> loadDataType = constructorDataTypeList.get(0);
        Object result;
        try {
            result = loader.instantiation(loadDataType, loadData.toArray());
        } catch (InstantiationErrorException e){
            throw new JsonFormatErrorException("selfDefined object instantiation error!");
        }
        return result;
    }

    @Override
    public Class<?> getContentClass() {
        return this.classType;
    }


}
