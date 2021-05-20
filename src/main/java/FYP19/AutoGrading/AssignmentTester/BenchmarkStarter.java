package FYP19.AutoGrading.AssignmentTester;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkStarter {

    private void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AssignmentTimeTester.class.getSimpleName())
                .forks(1)
                .param("dataType",args[0])
                .param("data",args[1])
                .param("classPath",args[2])
                .param("className",args[3])
                .threads(1)
                .jvmArgs("-server")

//                .resultFormat(ResultFormatType.JSON)
//                    .output("AssignmentResultTester.sampleLog")
                .build();
        new Runner(opt).run();
    }

    public void benchMarkStart(String dataType, String data, String classPath, String className) throws RunnerException {
        String[] args = {dataType, data, classPath, className};
        this.main(args);
    }

}
