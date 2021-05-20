package FYP19.AutoGrading.ReflectionImp;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class SelfClassLoader extends ClassLoader {
    private static SelfClassLoader loader = null;
    private String loadingPath;

    public static SelfClassLoader getClassLoader(String path) {
        loader = (SelfClassLoader.loader == null)? new SelfClassLoader(path) : loader.setPath(path);
        return loader;
    }

    private SelfClassLoader setPath(String path) {
        this.loadingPath = path;
        return this;
    }

    private SelfClassLoader(String path){
        this.loadingPath = path;
    }

    @Override
    protected Class<?> findClass (String name) throws  ClassNotFoundException {
        File file = new File(this.loadingPath, getFileName(name));
        try (FileInputStream fin = new FileInputStream(file); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int len;
            while ((len = fin.read()) != -1){
                baos.write(len);
            }
            byte[] data = baos.toByteArray();
            return defineClass(name, data,0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private String getFileName(String name){
        int index = name.lastIndexOf('.');
        if (index == -1){
            return name + ".class";
        } else {
            return name.substring(index+1) + ".class";
        }
    }

}
