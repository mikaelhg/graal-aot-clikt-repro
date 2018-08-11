# Graal Clikt native-image bug repro

Obviously, edit the `build.gradle.kts` file and point to your actual `native-image` instead of
`/home/mikael/local/graalvm/bin/native-image`.

```text
$ jg gradle clean build graalNativeImage
Build on Server(pid: 26974, port: 45715)
SendBuildRequest [
-task=com.oracle.svm.hosted.NativeImageGeneratorRunner
-imagecp
/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/svm/builder/pointsto.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/svm/builder/objectfile.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/svm/builder/svm.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/jvmci/graal.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/jvmci/jvmci-hotspot.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/jvmci/jvmci-api.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/jvmci/graal-management.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/boot/graal-sdk.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/boot/graaljs-scriptengine.jar:/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/svm/library-support.jar:/home/mikael/devel/temp/graal-aot-clickt-repro/build/classes/kotlin/main:/home/mikael/devel/temp/graal-aot-clickt-repro/build/resources/main:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.2.60/kotlin-stdlib-jdk8-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-reflect/1.2.60/kotlin-reflect-1.2.60.jar:/home/mikael/.gradle/caches/modules-2/files-2.1/com.github.ajalt/clikt/1.3.0/eef12567763b8f1fa4f3096b7febfcd74842831f/clikt-1.3.0.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.2.60/kotlin-stdlib-jdk7-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/1.2.60/kotlin-stdlib-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-common/1.2.60/kotlin-stdlib-common-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar
-H:Path=/home/mikael/devel/temp/graal-aot-clickt-repro
-H:IncludeResources=.*
-H:+ReportUnsupportedElementsAtRuntime
-H:CLibraryPath=/home/mikael/local/graalvm-ce-1.0.0-rc5/jre/lib/svm/clibraries/linux-amd64
-H:ReflectionConfigurationFiles=/home/mikael/devel/temp/graal-aot-clickt-repro/reflection.json
-H:Class=bug.ReproKt
-H:Name=bug.reprokt
]
   classlist:     833.33 ms

> Task :graalNativeImage
/home/mikael/local/graalvm/bin/native-image bug.ReproKt --verbose -H:ReflectionConfigurationFiles=reflection.json -H:IncludeResources=.* -H:+ReportUnsupportedElementsAtRuntime -J-Dgraal.InlineDuringParsingMaxDepth=10 -cp build/classes/kotlin/main/:build/resources/main/:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.2.60/kotlin-stdlib-jdk8-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-reflect/1.2.60/kotlin-reflect-1.2.60.jar:/home/mikael/.gradle/caches/modules-2/files-2.1/com.github.ajalt/clikt/1.3.0/eef12567763b8f1fa4f3096b7febfcd74842831f/clikt-1.3.0.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.2.60/kotlin-stdlib-jdk7-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/1.2.60/kotlin-stdlib-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-common/1.2.60/kotlin-stdlib-common-1.2.60.jar:/home/mikael/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar

       (cap):     695.67 ms
       setup:   1,256.57 ms
  (typeflow):   6,357.65 ms
   (objects):   5,444.75 ms
  (features):      66.51 ms
    analysis:  12,039.13 ms
    universe:     475.55 ms
     (parse):     386.07 ms
     compile:     394.36 ms
fatal error: org.graalvm.compiler.debug.GraalError: java.lang.NullPointerException
        at method: void com.github.ajalt.clikt.core.CliktCommand.main(List)
        at com.oracle.svm.hosted.code.CompileQueue.defaultParseFunction(CompileQueue.java:747)
        at com.oracle.svm.hosted.code.CompileQueue.doParse(CompileQueue.java:649)
        at com.oracle.svm.hosted.code.CompileQueue$ParseTask.run(CompileQueue.java:297)
        at com.oracle.graal.pointsto.util.CompletionExecutor.lambda$execute$0(CompletionExecutor.java:174)
        at java.util.concurrent.ForkJoinTask$RunnableExecuteAction.exec(ForkJoinTask.java:1402)
        at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
        at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
        at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
        at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)
Caused by: java.lang.NullPointerException
        at org.graalvm.compiler.nodes.IfNode.prepareForSwap(IfNode.java:666)
        at org.graalvm.compiler.nodes.IfNode.simplify(IfNode.java:304)
        at org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.tryCanonicalize(CanonicalizerPhase.java:350)
        at org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.processNode(CanonicalizerPhase.java:262)
        at org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.processWorkSet(CanonicalizerPhase.java:241)
        at org.graalvm.compiler.phases.common.CanonicalizerPhase$Instance.run(CanonicalizerPhase.java:211)
        at org.graalvm.compiler.phases.common.CanonicalizerPhase.run(CanonicalizerPhase.java:125)
        at org.graalvm.compiler.phases.common.CanonicalizerPhase.run(CanonicalizerPhase.java:66)
        at org.graalvm.compiler.phases.BasePhase.apply(BasePhase.java:197)
        at org.graalvm.compiler.phases.BasePhase.apply(BasePhase.java:139)
        at com.oracle.svm.hosted.code.CompileQueue.defaultParseFunction(CompileQueue.java:718)
        ... 8 more
Error: Processing image build request failed

BUILD SUCCESSFUL in 17s
9 actionable tasks: 9 executed
```