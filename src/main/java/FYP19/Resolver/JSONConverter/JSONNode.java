package FYP19.Resolver.JSONConverter;

import FYP19.Resolver.JSONConverter.interfaces.IJSONNode;
import FYP19.Resolver.JSONConverter.interfaces.JsonFormatErrorException;
import FYP19.Resolver.JSONConverter.jsonNodeImps.ArrayNode;
import FYP19.Resolver.JSONConverter.jsonNodeImps.BasicNode;
import FYP19.Resolver.JSONConverter.jsonNodeImps.GenericsNode;
import FYP19.Resolver.JSONConverter.jsonNodeImps.SelfDefined;
import com.alibaba.fastjson.JSONObject;


/**
 * all of three dataType nodes contain the attributes of "dataType",typeName"
 * But they also have some special attributes respectively
 */
public abstract class JSONNode implements IJSONNode {
    public static final JSONNode VOID = null;

    protected final String dataType;
    protected final String typeName;
    protected Class<?> classType;

    protected JSONNode(String dataType, String typeName) {
        this.dataType = dataType;
        this.typeName = typeName;
        this.classType = null;
    }
    /**
     *
     * @param source jsonObject that will be analysed
     * @return a node containing dataType info
     */
    public static IJSONNode convert(JSONObject source){
        try{
            IJSONNode newNode;
            String dataType = source.getString("dataType");
            String typeName = source.getString("typeName");
            //judging which dataType the jsonObject refer to, then creating the corresponding dataType node
            switch (dataType){
                case "basic": newNode = new BasicNode(dataType,typeName); break;
                case "generics": newNode = new GenericsNode(dataType,typeName); break;
                case "self_defined": newNode = new SelfDefined(dataType,typeName); break;
                case "array": newNode = new ArrayNode(dataType, typeName); break;
                default:throw new JsonFormatErrorException("No such data type!");
                //each kind of dataType node has a different forward method(as well as backward method)
            }
            return newNode.forward(source);
        }catch (JsonFormatErrorException e){
            throw new JsonFormatErrorException(e.getLocalizedMessage());
        }
    }

    public static boolean isVoid(JSONNode node) {
        return node == VOID;
    }
}
