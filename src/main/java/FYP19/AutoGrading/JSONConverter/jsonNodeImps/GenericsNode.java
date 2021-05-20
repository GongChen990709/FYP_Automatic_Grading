package FYP19.AutoGrading.JSONConverter.jsonNodeImps;

import FYP19.AutoGrading.JSONConverter.JSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.IJSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.JsonFormatErrorException;
import FYP19.AutoGrading.ReflectionImp.ReflectionLoader;
import FYP19.AutoGrading.ReflectionImp.interfaces.InvokeErrorException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class GenericsNode extends JSONNode{
    private String implementName;
    private String fullClassName;
    private List<IJSONNode> membersNodes;

    public GenericsNode(String dataType, String typeName) {
        super(dataType, typeName);
        this.membersNodes = new LinkedList<>();
    }

    @Override
    public IJSONNode forward(JSONObject source) throws JsonFormatErrorException {
        try {
            JSONArray members = source.getJSONArray("members");
            this.implementName = source.getString("implementName");
            this.fullClassName = source.getString("fullClassName");
            //For list, map, stack, queue, they just need to return the first layer fullClassName
            this.classType = Class.forName(this.fullClassName);
            //Analysing the dataType of child nodes for the current node
            for (int i = 0; i < members.size(); i++) {
                this.membersNodes.add(i, JSONNode.convert((JSONObject) members.get(i)));
            }
        } catch (Exception e) {
            throw new JsonFormatErrorException(e.getLocalizedMessage());
        }
        return this;
    }

    @Override
    public Object backward(JSONObject data) throws JsonFormatErrorException {
        try {
            //for "generics" type which is not "array"
            ReflectionLoader loader = new ReflectionLoader(this.fullClassName);//ClassNotFoundException
            Object instance = loader.instantiation(new LinkedList<>());//InstantiationErrorException

            JSONArray value = data.getJSONArray("value");
            JSONArray key = data.getJSONArray("key");//only for map
            List<Class<?>> listStack = new LinkedList<>();
            listStack.add(Object.class);
            List<Class<?>> map = new LinkedList<>();
            map.add(Object.class);
            map.add(Object.class);
            switch (this.typeName) {
                case "list": {
                    autoList(loader, instance, "add", value, listStack);
                    break;
                } //InvokeErrorException, this error shouldn’t exist
                case "stack": {
                    autoList(loader, instance, "push", value, listStack);
                    break;
                } //InvokeErrorException, this error shouldn't exist
                case "map": {
                    //if (key.size()!=value.size()) throw new JsonFormatErrorException("The Map doesn't have equal number of key and value");
                    autoMap(loader, instance, "put", key, value, map);
                    break;//InvokeErrorException, this error shouldn't exist
                }
                default:
                    throw new JsonFormatErrorException();
            }
            return instance;
        } catch (Exception e) {
            throw new JsonFormatErrorException("JSON loading error");
        }

    }

    @Override
    public Class<?> getContentClass() {
        return this.classType;
    }

    // Object invoke(Object reflectObj, String methodName, Class<?>[] parametersType, Object... parameters)
    private void autoList(ReflectionLoader loader, Object instance, String methodName, JSONArray value, List<Class<?>> parametersType) throws InvokeErrorException {
        for (Object o : value) {
            //for autoList, it uses methods that only have generic dataType
            loader.invoke(instance, methodName, parametersType,
                    membersNodes.get(0).backward((JSONObject) o)
            );//InvokeErrorException
        }
    }

    private void autoMap(ReflectionLoader loader, Object instance, String methodName, JSONArray key, JSONArray value, List<Class<?>> parametersType) throws InvokeErrorException {
        for (int i = 0; i < key.size(); i++) {
            loader.invoke(instance, methodName, parametersType,
                    membersNodes.get(0).backward((JSONObject) key.get(i)),
                    membersNodes.get(1).backward((JSONObject) value.get(i))
            );
        }
    }
}
