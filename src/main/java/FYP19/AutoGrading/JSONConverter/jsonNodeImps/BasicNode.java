package FYP19.AutoGrading.JSONConverter.jsonNodeImps;

import FYP19.AutoGrading.JSONConverter.JSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.IJSONNode;
import FYP19.AutoGrading.JSONConverter.interfaces.JsonFormatErrorException;
import com.alibaba.fastjson.JSONObject;


/**
 * node containing info about "basic" dataType
 */
public class BasicNode extends JSONNode {

    public BasicNode(String dataType, String typeName) {
        super(dataType, typeName);
    }

    @Override
    public IJSONNode forward(JSONObject source) {
        try{
            switch (this.typeName){
                case "Double": this.classType = Double.class; break;
                case "Byte": this.classType = Byte.class; break;
                case "Float": this.classType = Float.class; break;
                case "Boolean": this.classType = Boolean.class; break;
                case "Char": this.classType = Character.class; break;
                case "Integer": this.classType = Integer.class; break;
                case "Short": this.classType = Short.class; break;
                case "Long": this.classType = Long.class; break;
                case "String": this.classType = String.class; break;

                case "double": this.classType = double.class; break;
                case "byte": this.classType = byte.class; break;
                case "float": this.classType = float.class; break;
                case "boolean": this.classType = boolean.class; break;
                case "char": this.classType = char.class; break;
                case "int": this.classType = int.class; break;
                case "short": this.classType = short.class; break;
                case "long": this.classType = long.class; break;
                default: throw new JsonFormatErrorException("No such \"basic\" type!");
            }
        } catch (RuntimeException e){
            throw new JsonFormatErrorException(e.getLocalizedMessage());
        }
        return this;
    }

    @Override
    public Object backward(JSONObject data) throws JsonFormatErrorException{
        try{
            String dataValue = data.getString("value");
            int radix = data.getIntValue("radix");
            radix = (radix == 0)? 10 : radix;
            switch (this.typeName){
                case "Double":
                case "double": return Double.parseDouble(dataValue);//NumberFormatException
                case "Byte":
                case "byte": return  Byte.parseByte(dataValue);//NumberFormatException
                case "Float":
                case "float": return Float.parseFloat(dataValue);//NumberFormatException
                case "Boolean":
                case "boolean": return  Boolean.parseBoolean(dataValue);
                case "String": return  dataValue;
                case "Character":
                case "char": return dataValue.charAt(0);//NullPointerException(if dataValue = null)
                case "Integer":
                case "int": return Integer.parseInt(dataValue, radix);//NumberFormatException
                case "Short":
                case "short": return  Short.parseShort(dataValue, radix);//NumberFormatException
                case "Long":
                case "long": return  Long.parseLong(dataValue, radix);//NumberFormatException
                default: throw new JsonFormatErrorException("No such \"basic\" type!");
            }
        } catch (RuntimeException e){
            throw new JsonFormatErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public Class<?> getContentClass() {
        return this.classType;
    }
}
