package FYP19.AutoGrading.JSONConverter.jsonNodeImps;

import FYP19.AutoGrading.JSONConverter.JSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.IJSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.JsonFormatErrorException;
import com.alibaba.fastjson.JSONObject;

public class ObjectNode extends JSONNode {
    public ObjectNode(String dataType, String typeName){
        super(dataType, typeName);
    }

    @Override
    public IJSONNode forward(JSONObject source) {
        this.classType = Object.class;
        return this;
    }

    @Override
    public Object backward(JSONObject data) throws JsonFormatErrorException {
        JSONObject valueType = data.getJSONObject("valueType");
        JSONObject valueData = data.getJSONObject("valueData");
        IJSONNode node = JSONNode.convert(valueType);
        return node.backward(valueData);
    }

    @Override
    public Class<?> getContentClass() {
        return this.classType;
    }
}
