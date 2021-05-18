package FYP19.AutoGrading.JSONConverter.jsonNodeImps;

import FYP19.AutoGrading.JSONConverter.JSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.IJSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.JsonFormatErrorException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class ArrayNode extends JSONNode {
    private String implementName;
    private String fullClassName;
    private List<IJSONNode> membersNodes;

    public ArrayNode(String dataType, String typeName) {
        super(dataType, typeName);
    }

    @Override
    public IJSONNode forward(JSONObject source) {
        try {
            JSONArray members = source.getJSONArray("members");
            this.implementName = source.getString("implementName");
            this.fullClassName = source.getString("fullClassName");
            //Analysing the dataType of child nodes for the current node
            this.membersNodes = new LinkedList<>();
            this.membersNodes.add(JSONNode.convert((JSONObject) members.get(0)));
            //For "array" type, using the dataType of the child node to create a new array for current node to get the corresponding arrayType
            this.classType = Array.newInstance(membersNodes.get(0).getContentClass(), 1).getClass();
        } catch (Exception e) {
            throw new JsonFormatErrorException(e.getLocalizedMessage());
        }
        return this;
    }

    @Override
    public Object backward(JSONObject data) throws JsonFormatErrorException {
        try {
            JSONArray value = data.getJSONArray("value");
            int dimension = data.getIntValue("dimension");
            return autoArray(dimension, value);
        } catch (Exception e) {
            throw new JsonFormatErrorException("JSON loading error");
        }
    }

    @Override
    public Class<?> getContentClass() {
        return this.classType;
    }

    private Object autoArray(int dimension, JSONArray value) {
        Object[] instance = new Object[dimension];
        //Getting the instantiation of the content of the current array
        for (int i = 0; i < dimension; i++) {
            instance[i] = this.membersNodes.get(0).backward((JSONObject) value.get(i));
        }
        //Creating a new array based on the content dataType
        Object array = Array.newInstance(membersNodes.get(0).getContentClass(), dimension);
        //putting contents into the new array
        for (int i = 0; i < dimension; i++) {
            Array.set(array, i, instance[i]);
        }
        return array;
    }

/*    private Object autoArray2(int dimension, JSONArray value) {
        Object array = null;
        for (int i = 0; i < dimension; i++) {
            Object instance = this.membersNodes.get(0).backward((JSONObject) value.get(i));
            if (array == null) array = Array.newInstance(instance.getClass(), dimension);
            Array.set(array, i, instance);
        }
        return array;
    }*/
}
