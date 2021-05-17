package FYP19.Resolver.JSONConverter.interfaces;

import com.alibaba.fastjson.JSONObject;

/**
 * basic, generics, self-defined, all of these dataType node can achieve:
 * forward: dataType analysing
 * backward: data loading
 */
public interface IJSONNode {
    IJSONNode forward(JSONObject source);
    Object backward(JSONObject data) throws JsonFormatErrorException;
    Class<?> getContentClass();
}
