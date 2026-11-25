package org.jetbrains.kotlin.utils;

import com.reandroid.apk.ApkUtil;
import java.io.File;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.util.lang.JavaVersion;

/* JADX INFO: compiled from: KotlinPaths.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\"\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001:\u000245J'\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0012\u0010)\u001a\n\u0012\u0006\b\u0001\u0012\u00020+0*\"\u00020+H\u0016¢\u0006\u0002\u0010,J\u001c\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\f\u0010)\u001a\b\u0012\u0004\u0012\u00020+0-H\u0016J/\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0006\u0010.\u001a\u00020/2\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020+0*\"\u00020+H\u0016¢\u0006\u0002\u00101J\u0010\u00102\u001a\u00020\u00032\u0006\u00102\u001a\u00020+H&J\u0012\u00103\u001a\u0004\u0018\u00010\u00032\u0006\u00102\u001a\u00020+H&R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0012\u0010\f\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005R\u0014\u0010\u000e\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0014\u0010\u0010\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0014\u0010\u0012\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0014\u0010\u0014\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0012\u0010\u0016\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0014\u0010\u0018\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0014\u0010\u001a\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u0014\u0010\u001c\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0005R\u0014\u0010\u001e\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0005R\u0014\u0010 \u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0005R\u0014\u0010\"\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0005R\u0014\u0010$\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0005R\u0014\u0010&\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00066À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinPaths;", "", "allOpenPluginJarPath", "Ljava/io/File;", "getAllOpenPluginJarPath", "()Ljava/io/File;", "compilerClasspath", "", "getCompilerClasspath", "()Ljava/util/List;", "compilerPath", "getCompilerPath", "homePath", "getHomePath", "jsKotlinTestJarPath", "getJsKotlinTestJarPath", "jsStdLibJarPath", "getJsStdLibJarPath", "jsStdLibSrcJarPath", "getJsStdLibSrcJarPath", "kotlinTestPath", "getKotlinTestPath", "libPath", "getLibPath", "lombokPluginJarPath", "getLombokPluginJarPath", "noArgPluginJarPath", "getNoArgPluginJarPath", "reflectPath", "getReflectPath", "samWithReceiverJarPath", "getSamWithReceiverJarPath", "scriptRuntimePath", "getScriptRuntimePath", "stdlibPath", "getStdlibPath", "stdlibSourcesPath", "getStdlibSourcesPath", "trove4jJarPath", "getTrove4jJarPath", "classPath", "jars", "", "Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;", "([Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;)Ljava/util/List;", "Lkotlin/sequences/Sequence;", ApkUtil.DEF_MODULE_NAME, "Lorg/jetbrains/kotlin/utils/KotlinPaths$ClassPaths;", "additionalJars", "(Lorg/jetbrains/kotlin/utils/KotlinPaths$ClassPaths;[Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;)Ljava/util/List;", "jar", "sourcesJar", "ClassPaths", "Jar", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinPaths {
    File getHomePath();

    File getLibPath();

    File jar(Jar jar);

    File sourcesJar(Jar jar);

    default File getStdlibPath() {
        return jar(Jar.StdLib);
    }

    default File getReflectPath() {
        return jar(Jar.Reflect);
    }

    default File getScriptRuntimePath() {
        return jar(Jar.ScriptRuntime);
    }

    default File getKotlinTestPath() {
        return jar(Jar.KotlinTest);
    }

    default File getStdlibSourcesPath() {
        File fileSourcesJar = sourcesJar(Jar.StdLib);
        Intrinsics.checkNotNull(fileSourcesJar);
        return fileSourcesJar;
    }

    default File getJsStdLibJarPath() {
        return jar(Jar.JsStdLib);
    }

    default File getJsStdLibSrcJarPath() {
        File fileSourcesJar = sourcesJar(Jar.JsStdLib);
        Intrinsics.checkNotNull(fileSourcesJar);
        return fileSourcesJar;
    }

    default File getJsKotlinTestJarPath() {
        return jar(Jar.JsKotlinTest);
    }

    default File getAllOpenPluginJarPath() {
        return jar(Jar.AllOpenPlugin);
    }

    default File getNoArgPluginJarPath() {
        return jar(Jar.NoArgPlugin);
    }

    default File getLombokPluginJarPath() {
        return jar(Jar.LombokPlugin);
    }

    default File getSamWithReceiverJarPath() {
        return jar(Jar.SamWithReceiver);
    }

    default File getTrove4jJarPath() {
        return jar(Jar.Trove4j);
    }

    default List<File> getCompilerClasspath() {
        return classPath(ClassPaths.Compiler, new Jar[0]);
    }

    default File getCompilerPath() {
        return jar(Jar.Compiler);
    }

    /* JADX INFO: compiled from: KotlinPaths.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001a\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;", "", "baseName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getBaseName", "()Ljava/lang/String;", "StdLib", "StdLibJdk7", "StdLibJdk8", "Reflect", "ScriptRuntime", "KotlinTest", "JsStdLib", "JsKotlinTest", "AllOpenPlugin", "NoArgPlugin", "LombokPlugin", "SamWithReceiver", "SerializationPlugin", "Trove4j", "Compiler", "ScriptingPlugin", "ScriptingImpl", "ScriptingLib", "ScriptingJvmLib", "CoroutinesCore", "KotlinDaemon", "MainKts", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Jar {
        StdLib(PathUtil.KOTLIN_JAVA_STDLIB_NAME),
        StdLibJdk7(PathUtil.KOTLIN_JAVA_RUNTIME_JDK7_NAME),
        StdLibJdk8(PathUtil.KOTLIN_JAVA_RUNTIME_JDK8_NAME),
        Reflect(PathUtil.KOTLIN_JAVA_REFLECT_NAME),
        ScriptRuntime(PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME),
        KotlinTest(PathUtil.KOTLIN_TEST_NAME),
        JsStdLib(PathUtil.JS_LIB_NAME),
        JsKotlinTest(PathUtil.KOTLIN_TEST_JS_NAME),
        AllOpenPlugin(PathUtil.ALLOPEN_PLUGIN_NAME),
        NoArgPlugin(PathUtil.NOARG_PLUGIN_NAME),
        LombokPlugin(PathUtil.LOMBOK_PLUGIN_NAME),
        SamWithReceiver(PathUtil.SAM_WITH_RECEIVER_PLUGIN_NAME),
        SerializationPlugin(PathUtil.SERIALIZATION_PLUGIN_NAME),
        Trove4j(PathUtil.TROVE4J_NAME),
        Compiler("kotlin-compiler"),
        ScriptingPlugin(PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME),
        ScriptingImpl(PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME),
        ScriptingLib(PathUtil.KOTLIN_SCRIPTING_COMMON_NAME),
        ScriptingJvmLib(PathUtil.KOTLIN_SCRIPTING_JVM_NAME),
        CoroutinesCore(PathUtil.KOTLINX_COROUTINES_CORE_NAME),
        KotlinDaemon("kotlin-daemon"),
        MainKts(PathUtil.MAIN_KTS_NAME);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String baseName;

        public static EnumEntries<Jar> getEntries() {
            return $ENTRIES;
        }

        Jar(String str) {
            this.baseName = str;
        }

        public final String getBaseName() {
            return this.baseName;
        }
    }

    /* JADX INFO: compiled from: KotlinPaths.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001b\b\u0012\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005B#\b\u0012\u0012\u0006\u0010\u0006\u001a\u00020\u0000\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0007B/\b\u0012\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004\u0012\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\n0\t¢\u0006\u0002\u0010\u000bB\u0017\b\u0002\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\n¢\u0006\u0002\u0010\rR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinPaths$ClassPaths;", "", "jars", "", "Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;", "(Ljava/lang/String;I[Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;)V", "baseClassPath", "(Ljava/lang/String;ILorg/jetbrains/kotlin/utils/KotlinPaths$ClassPaths;[Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;)V", "gen", "Lkotlin/Function0;", "", "(Ljava/lang/String;I[Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;Lkotlin/jvm/functions/Function0;)V", "contents", "(Ljava/lang/String;ILjava/util/List;)V", "getContents", "()Ljava/util/List;", "Empty", "StdLib", "Compiler", "CompilerWithScripting", "MainKts", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ClassPaths {
        Empty(null, 1, null),
        StdLib(new Jar[]{Jar.StdLib}, new Function0<List<? extends Jar>>() { // from class: org.jetbrains.kotlin.utils.KotlinPaths.ClassPaths.1
            @Override // kotlin.jvm.functions.Function0
            public final List<? extends Jar> invoke() {
                if (JavaVersion.current().compareTo(JavaVersion.compose(8)) >= 0) {
                    return kotlin.collections.CollectionsKt.listOf((Object[]) new Jar[]{Jar.StdLibJdk7, Jar.StdLibJdk8});
                }
                return JavaVersion.current().compareTo(JavaVersion.compose(7)) >= 0 ? kotlin.collections.CollectionsKt.listOf(Jar.StdLibJdk7) : kotlin.collections.CollectionsKt.emptyList();
            }
        }),
        Compiler(StdLib, Jar.Compiler, Jar.Reflect, Jar.ScriptRuntime, Jar.Trove4j, Jar.KotlinDaemon),
        CompilerWithScripting(Compiler, Jar.ScriptingPlugin, Jar.ScriptingImpl, Jar.ScriptingLib, Jar.ScriptingJvmLib),
        MainKts(StdLib, Jar.MainKts, Jar.ScriptRuntime, Jar.Reflect);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final List<Jar> contents;

        public static EnumEntries<ClassPaths> getEntries() {
            return $ENTRIES;
        }

        ClassPaths(List list) {
            this.contents = list;
        }

        /* synthetic */ ClassPaths(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? kotlin.collections.CollectionsKt.emptyList() : list);
        }

        public final List<Jar> getContents() {
            return this.contents;
        }

        ClassPaths(ClassPaths classPaths, Jar... jarArr) {
            this(kotlin.collections.CollectionsKt.plus((Collection) classPaths.contents, (Object[]) jarArr));
        }

        ClassPaths(Jar[] jarArr, Function0 function0) {
            this(kotlin.collections.CollectionsKt.plus((Collection) ArraysKt.asList(jarArr), (Iterable) function0.invoke()));
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.utils.KotlinPaths$classPath$1, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinPaths.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<Jar, File> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "jar(Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;)Ljava/io/File;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "jar";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(KotlinPaths.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final File invoke(Jar p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((KotlinPaths) this.receiver).jar(p0);
        }
    }

    default List<File> classPath(Sequence<? extends Jar> jars) {
        Intrinsics.checkNotNullParameter(jars, "jars");
        return SequencesKt.toList(SequencesKt.map(jars, new AnonymousClass1(this)));
    }

    default List<File> classPath(ClassPaths base, Jar... additionalJars) {
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(additionalJars, "additionalJars");
        return classPath(SequencesKt.plus(kotlin.collections.CollectionsKt.asSequence(base.getContents()), (Object[]) additionalJars));
    }

    default List<File> classPath(Jar... jars) {
        Intrinsics.checkNotNullParameter(jars, "jars");
        return classPath(ArraysKt.asSequence(jars));
    }
}
