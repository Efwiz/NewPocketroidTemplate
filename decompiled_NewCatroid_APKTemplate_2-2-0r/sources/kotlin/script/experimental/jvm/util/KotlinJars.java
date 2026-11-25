package kotlin.script.experimental.jvm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.script.experimental.jvm.impl.PathUtilKt;
import kotlin.script.templates.standard.ScriptTemplateWithArgs;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmClasspathUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010%\u001a\u00020&H\u0002J\u001a\u0010'\u001a\u0004\u0018\u00010\u00052\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)H\u0002J,\u0010+\u001a\u0004\u0018\u00010\u00052\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)2\u0006\u0010,\u001a\u00020)2\n\b\u0002\u0010-\u001a\u0004\u0018\u00010.J0\u0010+\u001a\u0004\u0018\u00010\u00052\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)2\n\u0010/\u001a\u0006\u0012\u0002\b\u0003002\n\b\u0002\u0010-\u001a\u0004\u0018\u00010.R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\u000b\u0010\u0007R#\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\t\u001a\u0004\b\u000e\u0010\u0007R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0007R\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\t\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0018\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\t\u001a\u0004\b\u0019\u0010\u0016R\u001d\u0010\u001b\u001a\u0004\u0018\u00010\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\t\u001a\u0004\b\u001c\u0010\u0016R\u001b\u0010\u001e\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b \u0010\t\u001a\u0004\b\u001f\u0010\u0016R\u001d\u0010!\u001a\u0004\u0018\u00010\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\t\u001a\u0004\b\"\u0010\u0016¨\u00061"}, d2 = {"Lkotlin/script/experimental/jvm/util/KotlinJars;", "", "()V", "compilerClasspath", "", "Ljava/io/File;", "getCompilerClasspath", "()Ljava/util/List;", "compilerClasspath$delegate", "Lkotlin/Lazy;", "compilerWithScriptingClasspath", "getCompilerWithScriptingClasspath", "compilerWithScriptingClasspath$delegate", "explicitCompilerClasspath", "getExplicitCompilerClasspath", "explicitCompilerClasspath$delegate", "kotlinScriptStandardJars", "getKotlinScriptStandardJars", "kotlinScriptStandardJarsWithReflect", "getKotlinScriptStandardJarsWithReflect", "reflectOrNull", "getReflectOrNull", "()Ljava/io/File;", "reflectOrNull$delegate", "scriptRuntime", "getScriptRuntime", "scriptRuntime$delegate", "scriptRuntimeOrNull", "getScriptRuntimeOrNull", "scriptRuntimeOrNull$delegate", "stdlib", "getStdlib", "stdlib$delegate", "stdlibOrNull", "getStdlibOrNull", "stdlibOrNull$delegate", "findCompilerClasspath", "withScripting", "", "getExplicitLib", "propertyName", "", "jarName", "getLib", "markerClassName", "classLoader", "Ljava/lang/ClassLoader;", "markerClass", "Lkotlin/reflect/KClass;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJars {
    public static final KotlinJars INSTANCE = new KotlinJars();

    /* JADX INFO: renamed from: explicitCompilerClasspath$delegate, reason: from kotlin metadata */
    private static final Lazy explicitCompilerClasspath = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$explicitCompilerClasspath$2
        @Override // kotlin.jvm.functions.Function0
        public final List<? extends File> invoke() {
            String property = System.getProperty(JvmClasspathUtilKt.KOTLIN_COMPILER_CLASSPATH_PROPERTY);
            if (property != null) {
                String str = property;
                String pathSeparator = File.pathSeparator;
                Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
                List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{pathSeparator}, false, 0, 6, (Object) null);
                if (listSplit$default != null) {
                    List list = listSplit$default;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new File((String) it.next()));
                    }
                    return arrayList;
                }
            }
            String property2 = System.getProperty(JvmClasspathUtilKt.KOTLIN_COMPILER_JAR_PROPERTY);
            if (property2 == null) {
                return null;
            }
            File file = new File(property2);
            if (!file.exists()) {
                file = null;
            }
            if (file != null) {
                return CollectionsKt.listOf(file);
            }
            return null;
        }
    });

    /* JADX INFO: renamed from: compilerClasspath$delegate, reason: from kotlin metadata */
    private static final Lazy compilerClasspath = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$compilerClasspath$2
        @Override // kotlin.jvm.functions.Function0
        public final List<? extends File> invoke() {
            return KotlinJars.INSTANCE.findCompilerClasspath(false);
        }
    });

    /* JADX INFO: renamed from: compilerWithScriptingClasspath$delegate, reason: from kotlin metadata */
    private static final Lazy compilerWithScriptingClasspath = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$compilerWithScriptingClasspath$2
        @Override // kotlin.jvm.functions.Function0
        public final List<? extends File> invoke() {
            return KotlinJars.INSTANCE.findCompilerClasspath(true);
        }
    });

    /* JADX INFO: renamed from: stdlibOrNull$delegate, reason: from kotlin metadata */
    private static final Lazy stdlibOrNull = LazyKt.lazy(new Function0<File>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$stdlibOrNull$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            String property = System.getProperty(JvmClasspathUtilKt.KOTLIN_STDLIB_JAR_PROPERTY);
            if (property != null) {
                File file = new File(property);
                if (!file.exists()) {
                    file = null;
                }
                if (file != null) {
                    return file;
                }
            }
            return KotlinJars.getLib$default(KotlinJars.INSTANCE, JvmClasspathUtilKt.KOTLIN_RUNTIME_JAR_PROPERTY, "kotlin-stdlib.jar", Reflection.getOrCreateKotlinClass(JvmStatic.class), (ClassLoader) null, 8, (Object) null);
        }
    });

    /* JADX INFO: renamed from: stdlib$delegate, reason: from kotlin metadata */
    private static final Lazy stdlib = LazyKt.lazy(new Function0<File>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$stdlib$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() throws Exception {
            File stdlibOrNull2 = KotlinJars.INSTANCE.getStdlibOrNull();
            if (stdlibOrNull2 != null) {
                return stdlibOrNull2;
            }
            throw new Exception("Unable to find kotlin stdlib, please specify it explicitly via \"kotlin.java.stdlib.jar\" property");
        }
    });

    /* JADX INFO: renamed from: reflectOrNull$delegate, reason: from kotlin metadata */
    private static final Lazy reflectOrNull = LazyKt.lazy(new Function0<File>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$reflectOrNull$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return KotlinJars.getLib$default(KotlinJars.INSTANCE, JvmClasspathUtilKt.KOTLIN_REFLECT_JAR_PROPERTY, "kotlin-reflect.jar", "kotlin.reflect.full.KClasses", (ClassLoader) null, 8, (Object) null);
        }
    });

    /* JADX INFO: renamed from: scriptRuntimeOrNull$delegate, reason: from kotlin metadata */
    private static final Lazy scriptRuntimeOrNull = LazyKt.lazy(new Function0<File>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$scriptRuntimeOrNull$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return KotlinJars.getLib$default(KotlinJars.INSTANCE, JvmClasspathUtilKt.KOTLIN_SCRIPT_RUNTIME_JAR_PROPERTY, "kotlin-script-runtime.jar", Reflection.getOrCreateKotlinClass(ScriptTemplateWithArgs.class), (ClassLoader) null, 8, (Object) null);
        }
    });

    /* JADX INFO: renamed from: scriptRuntime$delegate, reason: from kotlin metadata */
    private static final Lazy scriptRuntime = LazyKt.lazy(new Function0<File>() { // from class: kotlin.script.experimental.jvm.util.KotlinJars$scriptRuntime$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() throws Exception {
            File scriptRuntimeOrNull2 = KotlinJars.INSTANCE.getScriptRuntimeOrNull();
            if (scriptRuntimeOrNull2 != null) {
                return scriptRuntimeOrNull2;
            }
            throw new Exception("Unable to find kotlin script runtime, please specify it explicitly via \"kotlin.script.runtime.jar\" property");
        }
    });

    private KotlinJars() {
    }

    private final List<File> getExplicitCompilerClasspath() {
        return (List) explicitCompilerClasspath.getValue();
    }

    public final List<File> getCompilerClasspath() {
        return (List) compilerClasspath.getValue();
    }

    public final List<File> getCompilerWithScriptingClasspath() {
        return (List) compilerWithScriptingClasspath.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:38:0x00f6  */
    public final List<File> findCompilerClasspath(boolean withScripting) throws FileNotFoundException {
        List listEmptyList;
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"kotlin-compiler.jar", "kotlin-compiler-embeddable.jar"});
        List listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"kotlin-stdlib.jar", "kotlin-reflect.jar", "kotlin-script-runtime.jar", "trove4j.jar"});
        if (withScripting) {
            listEmptyList = CollectionsKt.listOf((Object[]) new String[]{"kotlin-scripting-compiler.jar", JvmClasspathUtilKt.KOTLIN_SCRIPTING_COMPILER_EMBEDDABLE_JAR, "kotlin-scripting-compiler-impl.jar", JvmClasspathUtilKt.KOTLIN_SCRIPTING_COMPILER_IMPL_EMBEDDABLE_JAR, "kotlin-scripting-common.jar", "kotlin-scripting-jvm.jar"});
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) listListOf, (Iterable) listListOf2), (Iterable) listEmptyList);
        ArrayList explicitCompilerClasspath2 = getExplicitCompilerClasspath();
        if (explicitCompilerClasspath2 == null) {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            Intrinsics.checkNotNullExpressionValue(contextClassLoader, "getContextClassLoader(...)");
            List<File> listClasspathFromFQN = JvmClasspathUtilKt.classpathFromFQN(contextClassLoader, "org.jetbrains.kotlin.cli.jvm.K2JVMCompiler");
            if (listClasspathFromFQN == null) {
                ClassLoader contextClassLoader2 = Thread.currentThread().getContextClassLoader();
                Intrinsics.checkNotNullExpressionValue(contextClassLoader2, "getContextClassLoader(...)");
                List<File> listClasspathFromClassloader$default = JvmClasspathUtilKt.classpathFromClassloader$default(contextClassLoader2, false, 2, null);
                if (listClasspathFromClassloader$default != null) {
                    if (listClasspathFromClassloader$default.isEmpty()) {
                        listClasspathFromClassloader$default = null;
                    }
                    listClasspathFromFQN = listClasspathFromClassloader$default;
                } else {
                    listClasspathFromFQN = null;
                }
                if (listClasspathFromFQN == null) {
                    listClasspathFromFQN = JvmClasspathUtilKt.classpathFromClasspathProperty();
                }
            }
            if (listClasspathFromFQN != null) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : listClasspathFromFQN) {
                    File file = (File) obj;
                    List list = listPlus;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (JvmClasspathUtilKt.matchMaybeVersionedFile(file, (String) it.next())) {
                                arrayList.add(obj);
                                break;
                            }
                        }
                    }
                }
                explicitCompilerClasspath2 = arrayList;
                if (explicitCompilerClasspath2.isEmpty()) {
                    explicitCompilerClasspath2 = null;
                }
            } else {
                explicitCompilerClasspath2 = null;
            }
        }
        if (explicitCompilerClasspath2 != null) {
            if (getExplicitCompilerClasspath() == null) {
                List<File> list2 = explicitCompilerClasspath2;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    for (File file2 : list2) {
                        List list3 = listListOf;
                        if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                            Iterator it2 = list3.iterator();
                            while (it2.hasNext()) {
                                if (JvmClasspathUtilKt.matchMaybeVersionedFile(file2, (String) it2.next())) {
                                }
                            }
                        }
                    }
                }
            }
            return explicitCompilerClasspath2;
        }
        throw new FileNotFoundException("Cannot find kotlin compiler jar, set kotlin.compiler.classpath property to proper location");
    }

    public static /* synthetic */ File getLib$default(KotlinJars kotlinJars, String str, String str2, KClass kClass, ClassLoader classLoader, int i, Object obj) {
        if ((i & 8) != 0) {
            classLoader = null;
        }
        return kotlinJars.getLib(str, str2, (KClass<?>) kClass, classLoader);
    }

    public final File getLib(String propertyName, String jarName, KClass<?> markerClass, ClassLoader classLoader) {
        File fileTryGetResourcePathForClassByName;
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        Intrinsics.checkNotNullParameter(jarName, "jarName");
        Intrinsics.checkNotNullParameter(markerClass, "markerClass");
        File explicitLib = getExplicitLib(propertyName, jarName);
        if (explicitLib != null) {
            return explicitLib;
        }
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        if (Intrinsics.areEqual(classLoader, JvmClassMappingKt.getJavaClass((KClass) markerClass).getClassLoader())) {
            fileTryGetResourcePathForClassByName = null;
        } else {
            String name2 = JvmClassMappingKt.getJavaClass((KClass) markerClass).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            Intrinsics.checkNotNull(classLoader);
            fileTryGetResourcePathForClassByName = PathUtilKt.tryGetResourcePathForClassByName(name2, classLoader);
        }
        if (fileTryGetResourcePathForClassByName == null) {
            fileTryGetResourcePathForClassByName = PathUtilKt.tryGetResourcePathForClass(JvmClassMappingKt.getJavaClass((KClass) markerClass));
        }
        if (fileTryGetResourcePathForClassByName == null || !fileTryGetResourcePathForClassByName.exists()) {
            return null;
        }
        return fileTryGetResourcePathForClassByName;
    }

    public static /* synthetic */ File getLib$default(KotlinJars kotlinJars, String str, String str2, String str3, ClassLoader classLoader, int i, Object obj) {
        if ((i & 8) != 0) {
            classLoader = null;
        }
        return kotlinJars.getLib(str, str2, str3, classLoader);
    }

    public final File getLib(String propertyName, String jarName, String markerClassName, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        Intrinsics.checkNotNullParameter(jarName, "jarName");
        Intrinsics.checkNotNullParameter(markerClassName, "markerClassName");
        File explicitLib = getExplicitLib(propertyName, jarName);
        if (explicitLib != null) {
            return explicitLib;
        }
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        Intrinsics.checkNotNull(classLoader);
        File fileTryGetResourcePathForClassByName = PathUtilKt.tryGetResourcePathForClassByName(markerClassName, classLoader);
        if (fileTryGetResourcePathForClassByName == null || !fileTryGetResourcePathForClassByName.exists()) {
            return null;
        }
        return fileTryGetResourcePathForClassByName;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0019  */
    /* JADX WARN: Code duplicated, block: B:13:0x001f  */
    /* JADX WARN: Code duplicated, block: B:16:0x002b  */
    /* JADX WARN: Code duplicated, block: B:27:0x0039 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:29:? A[RETURN, SYNTHETIC] */
    private final File getExplicitLib(String propertyName, String jarName) {
        List<File> explicitCompilerClasspath2;
        Iterator<T> it;
        Object next;
        File file;
        String property = System.getProperty(propertyName);
        if (property == null) {
            explicitCompilerClasspath2 = getExplicitCompilerClasspath();
            if (explicitCompilerClasspath2 != null) {
                return null;
            }
            it = explicitCompilerClasspath2.iterator();
            do {
                if (it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!JvmClasspathUtilKt.matchMaybeVersionedFile((File) next, jarName));
            file = (File) next;
            return file != null ? null : null;
        }
        file = new File(property);
        if (!file.exists()) {
            file = null;
        }
        if (file == null) {
            explicitCompilerClasspath2 = getExplicitCompilerClasspath();
            if (explicitCompilerClasspath2 != null) {
                return null;
            }
            it = explicitCompilerClasspath2.iterator();
            do {
                if (it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!JvmClasspathUtilKt.matchMaybeVersionedFile((File) next, jarName));
            file = (File) next;
            if (file != null || !file.exists()) {
            }
        }
        return file;
    }

    public final File getStdlibOrNull() {
        return (File) stdlibOrNull.getValue();
    }

    public final File getStdlib() {
        return (File) stdlib.getValue();
    }

    public final File getReflectOrNull() {
        return (File) reflectOrNull.getValue();
    }

    public final File getScriptRuntimeOrNull() {
        return (File) scriptRuntimeOrNull.getValue();
    }

    public final File getScriptRuntime() {
        return (File) scriptRuntime.getValue();
    }

    public final List<File> getKotlinScriptStandardJars() {
        return CollectionsKt.filterNotNull(CollectionsKt.listOf((Object[]) new File[]{getStdlibOrNull(), getScriptRuntimeOrNull()}));
    }

    public final List<File> getKotlinScriptStandardJarsWithReflect() {
        return CollectionsKt.filterNotNull(CollectionsKt.listOf((Object[]) new File[]{getStdlibOrNull(), getScriptRuntimeOrNull(), getReflectOrNull()}));
    }
}
