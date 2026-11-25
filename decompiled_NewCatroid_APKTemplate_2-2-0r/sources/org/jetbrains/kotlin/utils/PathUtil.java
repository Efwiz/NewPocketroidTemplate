package org.jetbrains.kotlin.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.jps.model.java.impl.JavaSdkUtil;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.PathManager;

/* JADX INFO: compiled from: PathUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0011\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010c\u001a\b\u0012\u0004\u0012\u00020I0d2\u0006\u0010e\u001a\u00020IH\u0007J\u000e\u0010f\u001a\b\u0012\u0004\u0012\u00020I0dH\u0007J\u0016\u0010g\u001a\b\u0012\u0004\u0012\u00020I0d2\u0006\u0010h\u001a\u00020IH\u0007J\u0016\u0010i\u001a\b\u0012\u0004\u0012\u00020I0d2\u0006\u0010j\u001a\u00020\u0004H\u0007J\u0014\u0010k\u001a\u00020I2\n\u0010l\u001a\u0006\u0012\u0002\b\u00030mH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010(\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020)8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0019\u00106\u001a\b\u0012\u0004\u0012\u00020\u000407¢\u0006\n\n\u0002\u0010:\u001a\u0004\b8\u00109R\u0011\u0010;\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b<\u0010+R\u0011\u0010=\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b>\u0010+R\u000e\u0010?\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020IX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010Q\u001a\u00020I8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bR\u0010SR\u0014\u0010T\u001a\u00020I8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bU\u0010SR\u001a\u0010V\u001a\u00020W8FX\u0087\u0004¢\u0006\f\u0012\u0004\bX\u0010\u0002\u001a\u0004\bY\u0010ZR\u001a\u0010[\u001a\u00020W8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\\\u0010\u0002\u001a\u0004\b]\u0010ZR\u001a\u0010^\u001a\u00020W8FX\u0087\u0004¢\u0006\f\u0012\u0004\b_\u0010\u0002\u001a\u0004\b`\u0010ZR\u0011\u0010a\u001a\u00020I8F¢\u0006\u0006\u001a\u0004\bb\u0010S¨\u0006n"}, d2 = {"Lorg/jetbrains/kotlin/utils/PathUtil;", "", "()V", "ALLOPEN_PLUGIN_JAR_NAME", "", "ALLOPEN_PLUGIN_NAME", "ANDROID_EXTENSIONS_RUNTIME_PLUGIN_JAR_NAME", "HOME_FOLDER_NAME", "JS_ENGINES_JAR", "JS_ENGINES_NAME", "JS_LIB_10_JAR_NAME", "JS_LIB_JAR_NAME", "JS_LIB_NAME", "JS_LIB_SRC_JAR_NAME", "KOTLINX_COROUTINES_CORE_JAR", "KOTLINX_COROUTINES_CORE_NAME", "KOTLIN_COMPILER_JAR", "KOTLIN_COMPILER_NAME", "KOTLIN_DAEMON_JAR", "KOTLIN_DAEMON_NAME", "KOTLIN_JAVA_REFLECT_JAR", "KOTLIN_JAVA_REFLECT_NAME", "KOTLIN_JAVA_RUNTIME_JDK7_JAR", "KOTLIN_JAVA_RUNTIME_JDK7_NAME", "KOTLIN_JAVA_RUNTIME_JDK7_SRC_JAR", "KOTLIN_JAVA_RUNTIME_JDK8_JAR", "KOTLIN_JAVA_RUNTIME_JDK8_NAME", "KOTLIN_JAVA_RUNTIME_JDK8_SRC_JAR", "KOTLIN_JAVA_RUNTIME_JRE7_JAR", "KOTLIN_JAVA_RUNTIME_JRE7_NAME", "KOTLIN_JAVA_RUNTIME_JRE7_SRC_JAR", "KOTLIN_JAVA_RUNTIME_JRE8_JAR", "KOTLIN_JAVA_RUNTIME_JRE8_NAME", "KOTLIN_JAVA_RUNTIME_JRE8_SRC_JAR", "KOTLIN_JAVA_SCRIPT_RUNTIME_JAR", "KOTLIN_JAVA_SCRIPT_RUNTIME_NAME", "KOTLIN_JAVA_STDLIB_JAR", "KOTLIN_JAVA_STDLIB_NAME", "KOTLIN_JAVA_STDLIB_SRC_JAR", "KOTLIN_JAVA_STDLIB_SRC_JAR_OLD", "KOTLIN_JS_LIBRARY_JAR_PATTERN", "Ljava/util/regex/Pattern;", "getKOTLIN_JS_LIBRARY_JAR_PATTERN", "()Ljava/util/regex/Pattern;", "KOTLIN_REFLECT_SRC_JAR", "KOTLIN_RUNTIME_JAR_PATTERN", "KOTLIN_SCRIPTING_COMMON_JAR", "KOTLIN_SCRIPTING_COMMON_NAME", "KOTLIN_SCRIPTING_COMPILER_IMPL_JAR", "KOTLIN_SCRIPTING_COMPILER_IMPL_NAME", "KOTLIN_SCRIPTING_COMPILER_PLUGIN_JAR", "KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME", "KOTLIN_SCRIPTING_JVM_JAR", "KOTLIN_SCRIPTING_JVM_NAME", "KOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS", "", "getKOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "KOTLIN_STDLIB_COMMON_JAR_PATTERN", "getKOTLIN_STDLIB_COMMON_JAR_PATTERN", "KOTLIN_STDLIB_JS_JAR_PATTERN", "getKOTLIN_STDLIB_JS_JAR_PATTERN", "KOTLIN_TEST_JAR", "KOTLIN_TEST_JS_JAR", "KOTLIN_TEST_JS_NAME", "KOTLIN_TEST_NAME", "KOTLIN_TEST_SRC_JAR", "LOMBOK_PLUGIN_NAME", "MAIN_KTS_NAME", "NOARG_PLUGIN_JAR_NAME", "NOARG_PLUGIN_NAME", "NO_PATH", "Ljava/io/File;", "PARCELIZE_RUNTIME_PLUGIN_JAR_NAME", "SAM_WITH_RECEIVER_PLUGIN_JAR_NAME", "SAM_WITH_RECEIVER_PLUGIN_NAME", "SERIALIZATION_PLUGIN_JAR_NAME", "SERIALIZATION_PLUGIN_NAME", "TROVE4J_JAR", "TROVE4J_NAME", "compilerPathForCompilerJar", "getCompilerPathForCompilerJar", "()Ljava/io/File;", "compilerPathForIdeaPlugin", "getCompilerPathForIdeaPlugin", "kotlinPathsForCompiler", "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "getKotlinPathsForCompiler$annotations", "getKotlinPathsForCompiler", "()Lorg/jetbrains/kotlin/utils/KotlinPaths;", "kotlinPathsForDistDirectory", "getKotlinPathsForDistDirectory$annotations", "getKotlinPathsForDistDirectory", "kotlinPathsForIdeaPlugin", "getKotlinPathsForIdeaPlugin$annotations", "getKotlinPathsForIdeaPlugin", "pathUtilJar", "getPathUtilJar", "getJdkClassesRoots", "", "jdkHome", "getJdkClassesRootsFromCurrentJre", "getJdkClassesRootsFromJdkOrJre", "javaRoot", "getJdkClassesRootsFromJre", "javaHome", "getResourcePathForClass", "aClass", "Ljava/lang/Class;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PathUtil {
    public static final String ALLOPEN_PLUGIN_JAR_NAME = "allopen-compiler-plugin.jar";
    public static final String ALLOPEN_PLUGIN_NAME = "allopen-compiler-plugin";
    public static final String ANDROID_EXTENSIONS_RUNTIME_PLUGIN_JAR_NAME = "android-extensions-runtime.jar";
    public static final String HOME_FOLDER_NAME = "kotlinc";
    public static final String JS_ENGINES_JAR = "js.engines.jar";
    public static final String JS_ENGINES_NAME = "js.engines";
    public static final String JS_LIB_10_JAR_NAME = "kotlin-jslib.jar";
    public static final String JS_LIB_JAR_NAME = "kotlin-stdlib-js.jar";
    public static final String JS_LIB_NAME = "kotlin-stdlib-js";
    public static final String JS_LIB_SRC_JAR_NAME = "kotlin-stdlib-js-sources.jar";
    public static final String KOTLINX_COROUTINES_CORE_JAR = "kotlinx-coroutines-core-jvm.jar";
    public static final String KOTLINX_COROUTINES_CORE_NAME = "kotlinx-coroutines-core-jvm";
    public static final String KOTLIN_COMPILER_JAR = "kotlin-compiler.jar";
    public static final String KOTLIN_COMPILER_NAME = "kotlin-compiler";
    public static final String KOTLIN_DAEMON_JAR = "kotlin-scripting-jvm.jar";
    public static final String KOTLIN_DAEMON_NAME = "kotlin-daemon";
    public static final String KOTLIN_JAVA_REFLECT_JAR = "kotlin-reflect.jar";
    public static final String KOTLIN_JAVA_REFLECT_NAME = "kotlin-reflect";
    public static final String KOTLIN_JAVA_RUNTIME_JDK7_JAR = "kotlin-stdlib-jdk7.jar";
    public static final String KOTLIN_JAVA_RUNTIME_JDK7_NAME = "kotlin-stdlib-jdk7";
    public static final String KOTLIN_JAVA_RUNTIME_JDK7_SRC_JAR = "kotlin-stdlib-jdk7-sources.jar";
    public static final String KOTLIN_JAVA_RUNTIME_JDK8_JAR = "kotlin-stdlib-jdk8.jar";
    public static final String KOTLIN_JAVA_RUNTIME_JDK8_NAME = "kotlin-stdlib-jdk8";
    public static final String KOTLIN_JAVA_RUNTIME_JDK8_SRC_JAR = "kotlin-stdlib-jdk8-sources.jar";
    public static final String KOTLIN_JAVA_RUNTIME_JRE7_JAR = "kotlin-stdlib-jre7.jar";
    public static final String KOTLIN_JAVA_RUNTIME_JRE7_NAME = "kotlin-stdlib-jre7";
    public static final String KOTLIN_JAVA_RUNTIME_JRE7_SRC_JAR = "kotlin-stdlib-jre7-sources.jar";
    public static final String KOTLIN_JAVA_RUNTIME_JRE8_JAR = "kotlin-stdlib-jre8.jar";
    public static final String KOTLIN_JAVA_RUNTIME_JRE8_NAME = "kotlin-stdlib-jre8";
    public static final String KOTLIN_JAVA_RUNTIME_JRE8_SRC_JAR = "kotlin-stdlib-jre8-sources.jar";
    public static final String KOTLIN_JAVA_SCRIPT_RUNTIME_JAR = "kotlin-script-runtime.jar";
    public static final String KOTLIN_JAVA_SCRIPT_RUNTIME_NAME = "kotlin-script-runtime";
    public static final String KOTLIN_JAVA_STDLIB_JAR = "kotlin-stdlib.jar";
    public static final String KOTLIN_JAVA_STDLIB_NAME = "kotlin-stdlib";
    public static final String KOTLIN_JAVA_STDLIB_SRC_JAR = "kotlin-stdlib-sources.jar";
    public static final String KOTLIN_JAVA_STDLIB_SRC_JAR_OLD = "kotlin-runtime-sources.jar";
    private static final Pattern KOTLIN_JS_LIBRARY_JAR_PATTERN;
    public static final String KOTLIN_REFLECT_SRC_JAR = "kotlin-reflect-sources.jar";
    public static final Pattern KOTLIN_RUNTIME_JAR_PATTERN;
    public static final String KOTLIN_SCRIPTING_COMMON_JAR = "kotlin-scripting-common.jar";
    public static final String KOTLIN_SCRIPTING_COMMON_NAME = "kotlin-scripting-common";
    public static final String KOTLIN_SCRIPTING_COMPILER_IMPL_JAR = "kotlin-scripting-compiler-impl.jar";
    public static final String KOTLIN_SCRIPTING_COMPILER_IMPL_NAME = "kotlin-scripting-compiler-impl";
    public static final String KOTLIN_SCRIPTING_COMPILER_PLUGIN_JAR = "kotlin-scripting-compiler.jar";
    public static final String KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME = "kotlin-scripting-compiler";
    public static final String KOTLIN_SCRIPTING_JVM_JAR = "kotlin-scripting-jvm.jar";
    public static final String KOTLIN_SCRIPTING_JVM_NAME = "kotlin-scripting-jvm";
    private static final Pattern KOTLIN_STDLIB_COMMON_JAR_PATTERN;
    private static final Pattern KOTLIN_STDLIB_JS_JAR_PATTERN;
    public static final String KOTLIN_TEST_JAR = "kotlin-test.jar";
    public static final String KOTLIN_TEST_JS_JAR = "kotlin-test-js.jar";
    public static final String KOTLIN_TEST_JS_NAME = "kotlin-test-js";
    public static final String KOTLIN_TEST_NAME = "kotlin-test";
    public static final String KOTLIN_TEST_SRC_JAR = "kotlin-test-sources.jar";
    public static final String LOMBOK_PLUGIN_NAME = "lombok-compiler-plugin";
    public static final String MAIN_KTS_NAME = "kotlin-main-kts";
    public static final String NOARG_PLUGIN_JAR_NAME = "noarg-compiler-plugin.jar";
    public static final String NOARG_PLUGIN_NAME = "noarg-compiler-plugin";
    private static final File NO_PATH;
    public static final String PARCELIZE_RUNTIME_PLUGIN_JAR_NAME = "parcelize-runtime.jar";
    public static final String SAM_WITH_RECEIVER_PLUGIN_JAR_NAME = "sam-with-receiver-compiler-plugin.jar";
    public static final String SAM_WITH_RECEIVER_PLUGIN_NAME = "sam-with-receiver-compiler-plugin";
    public static final String SERIALIZATION_PLUGIN_JAR_NAME = "kotlinx-serialization-compiler-plugin.jar";
    public static final String SERIALIZATION_PLUGIN_NAME = "kotlinx-serialization-compiler-plugin";
    public static final String TROVE4J_JAR = "trove4j.jar";
    public static final String TROVE4J_NAME = "trove4j";
    public static final PathUtil INSTANCE = new PathUtil();
    private static final String[] KOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS = {"kotlin-scripting-compiler.jar", "kotlin-scripting-compiler-impl.jar", "kotlin-scripting-common.jar", "kotlin-scripting-jvm.jar"};

    @JvmStatic
    public static /* synthetic */ void getKotlinPathsForCompiler$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getKotlinPathsForDistDirectory$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getKotlinPathsForIdeaPlugin$annotations() {
    }

    private PathUtil() {
    }

    public final String[] getKOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS() {
        return KOTLIN_SCRIPTING_PLUGIN_CLASSPATH_JARS;
    }

    static {
        Pattern patternCompile = Pattern.compile("kotlin-(stdlib|runtime)(-\\d[\\d.]+(-.+)?)?\\.jar");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        KOTLIN_RUNTIME_JAR_PATTERN = patternCompile;
        Pattern patternCompile2 = Pattern.compile("kotlin-stdlib-js.*\\.jar");
        Intrinsics.checkNotNullExpressionValue(patternCompile2, "compile(...)");
        KOTLIN_STDLIB_JS_JAR_PATTERN = patternCompile2;
        Pattern patternCompile3 = Pattern.compile("kotlin-stdlib-common.*\\.jar");
        Intrinsics.checkNotNullExpressionValue(patternCompile3, "compile(...)");
        KOTLIN_STDLIB_COMMON_JAR_PATTERN = patternCompile3;
        Pattern patternCompile4 = Pattern.compile("kotlin-js-library.*\\.jar");
        Intrinsics.checkNotNullExpressionValue(patternCompile4, "compile(...)");
        KOTLIN_JS_LIBRARY_JAR_PATTERN = patternCompile4;
        NO_PATH = new File("<no_path>");
    }

    public final Pattern getKOTLIN_STDLIB_JS_JAR_PATTERN() {
        return KOTLIN_STDLIB_JS_JAR_PATTERN;
    }

    public final Pattern getKOTLIN_STDLIB_COMMON_JAR_PATTERN() {
        return KOTLIN_STDLIB_COMMON_JAR_PATTERN;
    }

    public final Pattern getKOTLIN_JS_LIBRARY_JAR_PATTERN() {
        return KOTLIN_JS_LIBRARY_JAR_PATTERN;
    }

    public static final KotlinPaths getKotlinPathsForIdeaPlugin() {
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return getKotlinPathsForDistDirectory();
        }
        return new KotlinPathsFromHomeDir(INSTANCE.getCompilerPathForIdeaPlugin());
    }

    public static final KotlinPaths getKotlinPathsForCompiler() {
        PathUtil pathUtil = INSTANCE;
        if (pathUtil.getPathUtilJar().isFile()) {
            String name2 = pathUtil.getPathUtilJar().getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (kotlin.text.StringsKt.startsWith$default(name2, "kotlin-compiler", false, 2, (Object) null)) {
                return new KotlinPathsFromHomeDir(pathUtil.getCompilerPathForCompilerJar());
            }
        }
        return getKotlinPathsForDistDirectory();
    }

    public static final KotlinPaths getKotlinPathsForDistDirectory() {
        return new KotlinPathsFromHomeDir(new File("dist", HOME_FOLDER_NAME));
    }

    private final File getCompilerPathForCompilerJar() {
        File pathUtilJar = getPathUtilJar();
        if (!pathUtilJar.exists()) {
            return NO_PATH;
        }
        if (Intrinsics.areEqual(pathUtilJar.getName(), "kotlin-compiler.jar")) {
            File parentFile = pathUtilJar.getParentFile().getParentFile();
            Intrinsics.checkNotNullExpressionValue(parentFile, "getParentFile(...)");
            return parentFile;
        }
        return NO_PATH;
    }

    private final File getCompilerPathForIdeaPlugin() {
        File pathUtilJar = getPathUtilJar();
        if (!pathUtilJar.exists()) {
            return NO_PATH;
        }
        if (Intrinsics.areEqual(pathUtilJar.getName(), "kotlin-plugin.jar")) {
            return new File(pathUtilJar.getParentFile().getParentFile(), HOME_FOLDER_NAME);
        }
        return NO_PATH;
    }

    public final File getPathUtilJar() {
        return getResourcePathForClass(PathUtil.class);
    }

    @JvmStatic
    public static final File getResourcePathForClass(Class<?> aClass) {
        Intrinsics.checkNotNullParameter(aClass, "aClass");
        StringBuilder sb = new StringBuilder("/");
        String name2 = aClass.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String string = sb.append(kotlin.text.StringsKt.replace$default(name2, '.', '/', false, 4, (Object) null)).append(".class").toString();
        String resourceRoot = PathManager.getResourceRoot(aClass, string);
        if (resourceRoot == null) {
            throw new IllegalStateException("Resource not found: " + string);
        }
        File absoluteFile = new File(resourceRoot).getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
        return absoluteFile;
    }

    @JvmStatic
    public static final List<File> getJdkClassesRootsFromCurrentJre() {
        String property = System.getProperty("java.home");
        Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
        return getJdkClassesRootsFromJre(property);
    }

    @JvmStatic
    public static final List<File> getJdkClassesRootsFromJre(String javaHome) {
        Intrinsics.checkNotNullParameter(javaHome, "javaHome");
        List<Path> jdkClassesRoots = JavaSdkUtil.getJdkClassesRoots(Paths.get(javaHome, new String[0]), true);
        Intrinsics.checkNotNullExpressionValue(jdkClassesRoots, "getJdkClassesRoots(...)");
        List<Path> list = jdkClassesRoots;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Path) it.next()).toFile());
        }
        return arrayList;
    }

    @JvmStatic
    public static final List<File> getJdkClassesRoots(File jdkHome) {
        Intrinsics.checkNotNullParameter(jdkHome, "jdkHome");
        List<Path> jdkClassesRoots = JavaSdkUtil.getJdkClassesRoots(jdkHome.toPath(), false);
        Intrinsics.checkNotNullExpressionValue(jdkClassesRoots, "getJdkClassesRoots(...)");
        List<Path> list = jdkClassesRoots;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Path) it.next()).toFile());
        }
        return arrayList;
    }

    @JvmStatic
    public static final List<File> getJdkClassesRootsFromJdkOrJre(File javaRoot) {
        Intrinsics.checkNotNullParameter(javaRoot, "javaRoot");
        List<Path> jdkClassesRoots = JavaSdkUtil.getJdkClassesRoots(javaRoot.toPath(), !new File(javaRoot, "jre/lib").exists());
        Intrinsics.checkNotNullExpressionValue(jdkClassesRoots, "getJdkClassesRoots(...)");
        List<Path> list = jdkClassesRoots;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Path) it.next()).toFile());
        }
        return arrayList;
    }
}
