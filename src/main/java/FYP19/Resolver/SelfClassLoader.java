package FYP19.Resolver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SelfClassLoader extends ClassLoader {
    private String loadingPath;

    public SelfClassLoader(String path){
        this.loadingPath = path;
    }

    @Override
    protected Class<?> findClass (String name) throws  ClassNotFoundException {
        File file = new File(this.loadingPath,getFileName(name));
        try {
            System.out.println(file);
            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            try{
                while ((len = fin.read()) != -1){
                    baos.write(len);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            byte[] data = baos.toByteArray();
            fin.close();
            baos.close();
            System.out.println(name);
            return defineClass(name,data,0,data.length);


        }catch (IOException e){
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
