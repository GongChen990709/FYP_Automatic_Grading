package FYP19.AutoGrading.executor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Executor2 implements Serializable {

    private List<String> commandBuilder(String dataType, String data, String classPath, String className){
        return commandBuild("java", "-jar", "Assignment_Grading_BenchMarkTest.jar", dataType, data, classPath, className);
    }
//Assignment_Grading_BenchMarkTest
    private List<String> commandBuild(String... command){
        List<String> commandString = new LinkedList<>();
        commandString.addAll(Arrays.asList(command));
        return commandString;
    }

    public String[] resultShower(String dataType, String data, String classPath, String className, String beginPath) throws IOException, InterruptedException {
        Process p = commandRunning(commandBuilder(dataType,data,classPath,className), beginPath);
        p.waitFor();
        return null;
    }

    private Process commandRunning(List<String> command, String path) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command); //1
        builder.directory(new File(path));//2
        return builder.start();//3
    }
}
