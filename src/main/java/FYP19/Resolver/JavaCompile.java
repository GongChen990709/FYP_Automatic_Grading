package FYP19.Resolver;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class JavaCompile {

    public int compile(String filePath,String desPath){
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        return javaCompiler.run(null,null,null, filePath, "-d", desPath);
    }
}
