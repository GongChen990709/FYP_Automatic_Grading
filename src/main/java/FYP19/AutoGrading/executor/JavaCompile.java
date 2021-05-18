package FYP19.AutoGrading.executor;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class JavaCompile {

    public int compile(String filePath,String desPath){
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        return javaCompiler.run(null,null,null, filePath, "-d", desPath);
    }

    public int compile(String quoteClassPath, String filePath, String desPath){
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        return javaCompiler.run(null,null,null, "-classpath", quoteClassPath, filePath, "-d", desPath);
    }
}
