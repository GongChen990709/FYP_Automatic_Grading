package FYP19.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtils {
    public static void deleteFilesByDirectory(String directoryPath){
        File dir = new File(directoryPath);
        File[] fileList = dir.listFiles();
        for(File file : fileList){
            file.delete();
        }
        dir.delete();
    }


    public static void downloadFileByPath(String filePath, HttpServletRequest request, HttpServletResponse response){
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition","attachment;fileName="+fileName);
        File file = new File(filePath);
        try {
            InputStream input = new FileInputStream(file);
            OutputStream output = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int index = 0;
            while((index=input.read(buffer))!=-1){
                output.write(buffer,0,index);
                output.flush();
            }
            output.close();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



