package FYP19.AutoGrading.AssignmentTester;

import FYP19.AutoGrading.JSONConverter.JSONClassConverter;
import FYP19.AutoGrading.MethodTester.MethodTester;
import FYP19.AutoGrading.ReflectionImp.interfaces.ReflectionCallerErrorException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, batchSize = 10000)
@State(Scope.Thread)
@Fork(2)
public class AssignmentTimeTester {
    @Param({"default"})
    public String dataType;
    @Param({"default"})
    public String data;
    @Param({"default"})
    public String classPath;
    @Param({"default"})
    public String className;

    public JSONClassConverter testClass;
    public Map<String, List<List<Object>>> testData;
    public Map<String, List<List<Class<?>>>> testDataType;
    public String constructorName;
    public List<String> testMethodName;

    @Setup
    public void assignmentTester(){
        JSONObject dataTypeObject = JSON.parseObject(dataType);
        this.testClass = new JSONClassConverter(dataTypeObject);

        JSONObject dataObject = JSON.parseObject(data);
        this.testData = testClass.batchTestDataLoader(dataObject);
        this.testDataType = testClass.batchTestMethodParametersTypeLoader(dataObject);
        this.constructorName = testClass.getConstructorName(dataObject);
        this.testMethodName = testClass.batchTestMethodName(dataObject);
    }

    @Benchmark
    public void classTest() throws ReflectionCallerErrorException {
        MethodTester classTester = new MethodTester(classPath, className, constructorName, testData, testDataType);
        classTester.testMethod(this.testMethodName);
    }
}
