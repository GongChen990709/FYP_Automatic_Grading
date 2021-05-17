package FYP19.Resolver.JSONConverter.jsonNodeImps;




import FYP19.Resolver.JSONConverter.JSONNode;
import FYP19.Resolver.JSONConverter.interfaces.IJSONNode;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.LinkedList;

/**
 * node containing info about "self_defined" dataType
 */

public class SelfDefined extends JSONNode {
    private LinkedList<IJSONNode> membersNodes;
    private String definitionPath;

    public SelfDefined(String dataType, String typeName) {
        super(dataType, typeName);
    }

    @Override
    public IJSONNode forward(JSONObject source) {
        JSONArray members = source.getJSONArray("members");
        this.definitionPath = source.getString("definitionPath");
        for (int i = 0; i < members.size(); i++) {
            this.membersNodes.add(i, JSONNode.convert((JSONObject) members.get(i)));
        }
        return this;
    }

    @Override
    public Object backward(JSONObject data) {
        return null;
    }

    @Override
    public Class<?> getContentClass() {
        return null;
    }
}