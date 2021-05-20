package FYP19.AutoGrading.JSONConverter.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonObjectToFile {
    public void convert(JSONObject object, String destination) throws IOException {
        String content = JSON.toJSONString(object);
        FileOutputStream out = new FileOutputStream(destination);
        File file = new File(destination);
        if (!file.exists()) file.createNewFile();
        out.write(content.getBytes(StandardCharsets.UTF_8));
    }
}
