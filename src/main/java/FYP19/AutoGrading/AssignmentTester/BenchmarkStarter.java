package FYP19.AutoGrading.AssignmentTester;

import FYP19.AutoGrading.JSONConverter.utils.JsonFileReader;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkStarter {

    private void main(String[] args) throws RunnerException {
        String dataType = JsonFileReader.readJsonFile(args[0]);
        String data = JsonFileReader.readJsonFile(args[1]);
        String classPath=args[2];
        String className=args[3];
        Options opt = new OptionsBuilder()
                .include(AssignmentTimeTester.class.getSimpleName())
                .forks(1)
                .param("dataType",dataType)
                .param("data",data)
                .param("classPath",classPath)
                .param("className",className)
                .threads(1)
//                .jvmArgs("-server")

                .resultFormat(ResultFormatType.JSON)
                .output("AssignmentTimeTester.sampleLog")
                .build();
        new Runner(opt).run();
    }

    public void benchMarkStart(String dataType, String data, String classPath, String className) throws RunnerException {
        String[] args = {dataType, data, classPath, className};
        this.main(args);
    }

}
