package kotlin.script.experimental.jvm.util;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KClass;
import kotlin.script.experimental.jvm.impl.PathUtilKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmClasspathUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a&\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010!\u001a\u00020 2\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020 0#H\u0002\u001a\u001d\u0010$\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%\"\n\b\u0000\u0010'\u0018\u0001*\u00020(H\u0086\b\u001a&\u0010$\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%2\u0006\u0010)\u001a\u00020 2\u000e\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00020(0+\u001a\u001e\u0010$\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%2\u000e\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00020(0+\u001a \u0010,\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%2\u0006\u0010-\u001a\u00020 2\b\b\u0002\u0010.\u001a\u00020/\u001a\u000e\u00100\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%\u001a\u001e\u00101\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%2\u0006\u0010)\u001a\u00020 2\u0006\u00102\u001a\u00020\u0002\u001a\u001b\u00103\u001a\b\u0012\u0004\u0012\u00020 0\u00012\u0006\u0010!\u001a\u00020 H\u0002¢\u0006\u0002\u00104\u001a\u001b\u00105\u001a\b\u0012\u0004\u0012\u00020 0\u00012\u0006\u0010!\u001a\u00020 H\u0002¢\u0006\u0002\u00104\u001a\u001b\u00106\u001a\b\u0012\u0004\u0012\u00020 0\u00012\u0006\u0010!\u001a\u00020 H\u0002¢\u0006\u0002\u00104\u001aC\u00107\u001a\b\u0012\u0004\u0012\u00020&0%2\u0012\u00108\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001\"\u00020\u00022\b\b\u0002\u0010)\u001a\u00020 2\b\b\u0002\u00109\u001a\u00020/2\b\b\u0002\u0010.\u001a\u00020/¢\u0006\u0002\u0010:\u001aE\u0010;\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%2\u0012\u00108\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001\"\u00020\u00022\b\b\u0002\u0010)\u001a\u00020 2\b\b\u0002\u00109\u001a\u00020/2\b\b\u0002\u0010.\u001a\u00020/¢\u0006\u0002\u0010:\u001a9\u0010<\u001a\b\u0012\u0004\u0012\u00020&0%2\u0012\u00108\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001\"\u00020\u00022\b\b\u0002\u0010)\u001a\u00020 2\b\b\u0002\u00109\u001a\u00020/¢\u0006\u0002\u0010=\u001a\u0014\u0010>\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010\u001f*\u00020 H\u0002\u001a\u0010\u0010?\u001a\b\u0012\u0004\u0012\u00020&0\u001f*\u00020 \u001a3\u0010@\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%*\b\u0012\u0004\u0012\u00020&0%2\u0012\u00108\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001\"\u00020\u0002H\u0000¢\u0006\u0002\u0010A\u001a\u0012\u0010B\u001a\u00020/*\u00020&2\u0006\u0010C\u001a\u00020\u0002\u001a\f\u0010D\u001a\u00020/*\u00020&H\u0000\u001a\u0012\u0010E\u001a\u00020/*\u00020&2\u0006\u0010C\u001a\u00020\u0002\u001a\u001a\u0010F\u001a\b\u0012\u0004\u0012\u00020&0\u001f*\u00020 2\u0006\u0010G\u001a\u00020\u0002H\u0000\u001a3\u0010H\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%*\b\u0012\u0004\u0012\u00020&0%2\u0012\u00108\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001\"\u00020\u0002H\u0000¢\u0006\u0002\u0010A\u001a3\u0010I\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%*\b\u0012\u0004\u0012\u00020&0%2\u0012\u00108\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001\"\u00020\u0002H\u0000¢\u0006\u0002\u0010A\u001a\u000e\u0010J\u001a\u0004\u0018\u00010&*\u00020KH\u0000\u001a\u001a\u0010L\u001a\b\u0012\u0004\u0012\u00020&0\u001f*\u00020&2\u0006\u0010M\u001a\u00020&H\u0002\"\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0006\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0002X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0016\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0018\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0019\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001a\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006N²\u0006\u0012\u0010O\u001a\n P*\u0004\u0018\u00010&0&X\u008a\u0084\u0002"}, d2 = {"JAR_COLLECTIONS_CLASSES_PATHS", "", "", "[Ljava/lang/String;", "JAR_COLLECTIONS_KEY_PATHS", "JAR_COLLECTIONS_LIB_PATHS", "JAR_MANIFEST_RESOURCE_NAME", "KOTLIN_COMPILER_CLASSPATH_PROPERTY", "KOTLIN_COMPILER_EMBEDDABLE_JAR", "KOTLIN_COMPILER_JAR", "KOTLIN_COMPILER_JAR_PROPERTY", "KOTLIN_COMPILER_NAME", "KOTLIN_JAVA_REFLECT_JAR", "KOTLIN_JAVA_SCRIPT_RUNTIME_JAR", "KOTLIN_JAVA_STDLIB_JAR", "KOTLIN_REFLECT_JAR_PROPERTY", "KOTLIN_RUNTIME_JAR_PROPERTY", "KOTLIN_SCRIPTING_COMMON_JAR", "KOTLIN_SCRIPTING_COMPILER_EMBEDDABLE_JAR", "KOTLIN_SCRIPTING_COMPILER_IMPL_EMBEDDABLE_JAR", "KOTLIN_SCRIPTING_COMPILER_IMPL_JAR", "KOTLIN_SCRIPTING_COMPILER_JAR", "KOTLIN_SCRIPTING_JVM_JAR", "KOTLIN_SCRIPT_CLASSPATH_PROPERTY", "KOTLIN_SCRIPT_RUNTIME_JAR_PROPERTY", "KOTLIN_STDLIB_JAR_PROPERTY", "TROVE4J_JAR", "validClasspathFilesExtensions", "", "validJarCollectionFilesExtensions", "allRelatedClassLoaders", "Lkotlin/sequences/Sequence;", "Ljava/lang/ClassLoader;", "clsLoader", "visited", "", "classpathFromClass", "", "Ljava/io/File;", ExifInterface.GPS_DIRECTION_TRUE, "", "classLoader", "klass", "Lkotlin/reflect/KClass;", "classpathFromClassloader", "currentClassLoader", "unpackJarCollections", "", "classpathFromClasspathProperty", "classpathFromFQN", "fqn", "getParentClassLoaders", "(Ljava/lang/ClassLoader;)[Ljava/lang/ClassLoader;", "getParentsForNewClassLoader", "getParentsForOldClassLoader", "scriptCompilationClasspathFromContext", "keyNames", "wholeClasspath", "([Ljava/lang/String;Ljava/lang/ClassLoader;ZZ)Ljava/util/List;", "scriptCompilationClasspathFromContextOrNull", "scriptCompilationClasspathFromContextOrStdlib", "([Ljava/lang/String;Ljava/lang/ClassLoader;Z)Ljava/util/List;", "classPathFromGetUrlsMethodOrNull", "classPathFromTypicalResourceUrls", "filterIfContainsAll", "(Ljava/util/List;[Ljava/lang/String;)Ljava/util/List;", "hasParentNamed", "baseName", "isValidClasspathFile", "matchMaybeVersionedFile", "rawClassPathFromKeyResourcePath", "keyResourcePath", "takeIfContainsAll", "takeIfContainsAny", "toValidClasspathFileOrNull", "Ljava/net/URL;", "unpackJarCollection", "rootTempDir", PathUtil.KOTLIN_SCRIPTING_JVM_NAME, "unpackJarCollectionsDir", JvmProtoBufUtil.PLATFORM_TYPE_ID}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmClasspathUtilKt {
    private static final String[] JAR_COLLECTIONS_CLASSES_PATHS;
    private static final String[] JAR_COLLECTIONS_KEY_PATHS;
    private static final String[] JAR_COLLECTIONS_LIB_PATHS;
    public static final String JAR_MANIFEST_RESOURCE_NAME = "META-INF/MANIFEST.MF";
    public static final String KOTLIN_COMPILER_CLASSPATH_PROPERTY = "kotlin.compiler.classpath";
    private static final String KOTLIN_COMPILER_EMBEDDABLE_JAR = "kotlin-compiler-embeddable.jar";
    public static final String KOTLIN_COMPILER_JAR = "kotlin-compiler.jar";
    public static final String KOTLIN_COMPILER_JAR_PROPERTY = "kotlin.compiler.jar";
    public static final String KOTLIN_COMPILER_NAME = "kotlin-compiler";
    public static final String KOTLIN_JAVA_REFLECT_JAR = "kotlin-reflect.jar";
    public static final String KOTLIN_JAVA_SCRIPT_RUNTIME_JAR = "kotlin-script-runtime.jar";
    public static final String KOTLIN_JAVA_STDLIB_JAR = "kotlin-stdlib.jar";
    public static final String KOTLIN_REFLECT_JAR_PROPERTY = "kotlin.java.reflect.jar";
    public static final String KOTLIN_RUNTIME_JAR_PROPERTY = "kotlin.java.runtime.jar";
    public static final String KOTLIN_SCRIPTING_COMMON_JAR = "kotlin-scripting-common.jar";
    public static final String KOTLIN_SCRIPTING_COMPILER_EMBEDDABLE_JAR = "kotlin-scripting-compiler-embeddable.jar";
    public static final String KOTLIN_SCRIPTING_COMPILER_IMPL_EMBEDDABLE_JAR = "kotlin-scripting-compiler-impl-embeddable.jar";
    public static final String KOTLIN_SCRIPTING_COMPILER_IMPL_JAR = "kotlin-scripting-compiler-impl.jar";
    public static final String KOTLIN_SCRIPTING_COMPILER_JAR = "kotlin-scripting-compiler.jar";
    public static final String KOTLIN_SCRIPTING_JVM_JAR = "kotlin-scripting-jvm.jar";
    public static final String KOTLIN_SCRIPT_CLASSPATH_PROPERTY = "kotlin.script.classpath";
    public static final String KOTLIN_SCRIPT_RUNTIME_JAR_PROPERTY = "kotlin.script.runtime.jar";
    public static final String KOTLIN_STDLIB_JAR_PROPERTY = "kotlin.java.stdlib.jar";
    public static final String TROVE4J_JAR = "trove4j.jar";
    private static final Set<String> validClasspathFilesExtensions = SetsKt.setOf((Object[]) new String[]{"jar", ArchiveStreamFactory.ZIP, SuffixConstants.EXTENSION_java});
    private static final Set<String> validJarCollectionFilesExtensions = SetsKt.setOf((Object[]) new String[]{"jar", "war", ArchiveStreamFactory.ZIP});

    static {
        String[] strArr = {"BOOT-INF/classes", "WEB-INF/classes"};
        JAR_COLLECTIONS_CLASSES_PATHS = strArr;
        String[] strArr2 = {"BOOT-INF/lib", "WEB-INF/lib"};
        JAR_COLLECTIONS_LIB_PATHS = strArr2;
        JAR_COLLECTIONS_KEY_PATHS = (String[]) ArraysKt.plus((Object[]) strArr, (Object[]) strArr2);
    }

    public static /* synthetic */ List classpathFromClassloader$default(ClassLoader classLoader, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return classpathFromClassloader(classLoader, z);
    }

    public static final List<File> classpathFromClassloader(final ClassLoader currentClassLoader, final boolean z) {
        Intrinsics.checkNotNullParameter(currentClassLoader, "currentClassLoader");
        final HashSet hashSet = new HashSet();
        final Lazy lazy = LazyKt.lazy(JvmClasspathUtilKt$classpathFromClassloader$unpackJarCollectionsDir$2.INSTANCE);
        List<File> list = SequencesKt.toList(SequencesKt.filter(SequencesKt.flatMap(allRelatedClassLoaders$default(currentClassLoader, null, 2, null), new Function1<ClassLoader, Sequence<? extends File>>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classpathFromClassloader.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Sequence<File> invoke(ClassLoader classLoader) {
                Sequence<File> sequenceClassPathFromTypicalResourceUrls;
                String file;
                Intrinsics.checkNotNullParameter(classLoader, "classLoader");
                Sequence sequenceEmptySequence = SequencesKt.emptySequence();
                if (z) {
                    for (String str : JvmClasspathUtilKt.JAR_COLLECTIONS_KEY_PATHS) {
                        URL resource = classLoader.getResource(str);
                        if (resource != null && (file = resource.getFile()) != null) {
                            Intrinsics.checkNotNull(file);
                            if (file.length() > 0) {
                                Sequence sequenceAsSequence = ArraysKt.asSequence(JvmClasspathUtilKt.JAR_COLLECTIONS_KEY_PATHS);
                                final ClassLoader classLoader2 = currentClassLoader;
                                Sequence sequenceFlatMap = SequencesKt.flatMap(sequenceAsSequence, new Function1<String, Sequence<? extends URL>>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt$classpathFromClassloader$1$jarCollections$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Sequence<URL> invoke(String it) throws IOException {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        Enumeration<URL> resources = classLoader2.getResources(it);
                                        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
                                        return SequencesKt.asSequence(CollectionsKt.iterator(resources));
                                    }
                                });
                                final HashSet<File> hashSet2 = hashSet;
                                Sequence sequenceMapNotNull = SequencesKt.mapNotNull(sequenceFlatMap, new Function1<URL, File>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt$classpathFromClassloader$1$jarCollections$2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final File invoke(URL url) throws IOException {
                                        Intrinsics.checkNotNull(url);
                                        File containingJarOrNull = PathUtilKt.toContainingJarOrNull(url);
                                        if (containingJarOrNull != null) {
                                            HashSet<File> hashSet3 = hashSet2;
                                            if (JvmClasspathUtilKt.validJarCollectionFilesExtensions.contains(FilesKt.getExtension(containingJarOrNull)) && hashSet3.add(containingJarOrNull)) {
                                                return containingJarOrNull;
                                            }
                                        }
                                        return null;
                                    }
                                });
                                final Lazy<File> lazy2 = lazy;
                                sequenceEmptySequence = SequencesKt.plus(sequenceEmptySequence, SequencesKt.filter(SequencesKt.flatMap(sequenceMapNotNull, new Function1<File, Sequence<? extends File>>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classpathFromClassloader.1.2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    /* JADX WARN: Multi-variable type inference failed */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Sequence<File> invoke(File it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        File fileClasspathFromClassloader$lambda$0 = JvmClasspathUtilKt.classpathFromClassloader$lambda$0(lazy2);
                                        Intrinsics.checkNotNullExpressionValue(fileClasspathFromClassloader$lambda$0, "access$classpathFromClassloader$lambda$0(...)");
                                        return JvmClasspathUtilKt.unpackJarCollection(it, fileClasspathFromClassloader$lambda$0);
                                    }
                                }), new Function1<File, Boolean>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classpathFromClassloader.1.3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Boolean invoke(File it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        return Boolean.valueOf(JvmClasspathUtilKt.isValidClasspathFile(it));
                                    }
                                }));
                                break;
                            }
                        }
                    }
                }
                if (!(classLoader instanceof URLClassLoader)) {
                    Sequence<File> sequenceClassPathFromGetUrlsMethodOrNull = JvmClasspathUtilKt.classPathFromGetUrlsMethodOrNull(classLoader);
                    sequenceClassPathFromTypicalResourceUrls = sequenceClassPathFromGetUrlsMethodOrNull == null ? JvmClasspathUtilKt.classPathFromTypicalResourceUrls(classLoader) : sequenceClassPathFromGetUrlsMethodOrNull;
                } else {
                    URL[] uRLs = ((URLClassLoader) classLoader).getURLs();
                    Intrinsics.checkNotNullExpressionValue(uRLs, "getURLs(...)");
                    sequenceClassPathFromTypicalResourceUrls = SequencesKt.mapNotNull(ArraysKt.asSequence(uRLs), new Function1<URL, File>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classpathFromClassloader.1.4
                        @Override // kotlin.jvm.functions.Function1
                        public final File invoke(URL url) {
                            Intrinsics.checkNotNull(url);
                            return JvmClasspathUtilKt.toValidClasspathFileOrNull(url);
                        }
                    });
                }
                return SequencesKt.plus(sequenceEmptySequence, (Sequence) sequenceClassPathFromTypicalResourceUrls);
            }
        }), new Function1<File, Boolean>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classpathFromClassloader.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(hashSet.add(it));
            }
        }));
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final File classpathFromClassloader$lambda$0(Lazy<? extends File> lazy) {
        return lazy.getValue();
    }

    public static final File toValidClasspathFileOrNull(URL url) throws IOException {
        Intrinsics.checkNotNullParameter(url, "<this>");
        File containingJarOrNull = PathUtilKt.toContainingJarOrNull(url);
        if (containingJarOrNull == null) {
            containingJarOrNull = PathUtilKt.toFileOrNull(url);
        }
        if (containingJarOrNull == null || !isValidClasspathFile(containingJarOrNull)) {
            return null;
        }
        return containingJarOrNull;
    }

    public static final boolean isValidClasspathFile(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return file.isDirectory() || (file.isFile() && validClasspathFilesExtensions.contains(FilesKt.getExtension(file)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence<File> classPathFromGetUrlsMethodOrNull(ClassLoader classLoader) {
        Sequence sequenceAsSequence;
        try {
            Method method = classLoader.getClass().getMethod("getUrls", new Class[0]);
            method.setAccessible(true);
            Object objInvoke = method.invoke(classLoader, new Object[0]);
            List list = objInvoke instanceof List ? (List) objInvoke : null;
            if (list == null || (sequenceAsSequence = CollectionsKt.asSequence(list)) == null) {
                return null;
            }
            Sequence sequenceFilter = SequencesKt.filter(sequenceAsSequence, new Function1<Object, Boolean>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt$classPathFromGetUrlsMethodOrNull$$inlined$filterIsInstance$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof URL);
                }
            });
            Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            if (sequenceFilter != null) {
                return SequencesKt.mapNotNull(sequenceFilter, new Function1<URL, File>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classPathFromGetUrlsMethodOrNull.1
                    @Override // kotlin.jvm.functions.Function1
                    public final File invoke(URL it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return JvmClasspathUtilKt.toValidClasspathFileOrNull(it);
                    }
                });
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static final Sequence<File> rawClassPathFromKeyResourcePath(ClassLoader classLoader, String keyResourcePath) throws IOException {
        Intrinsics.checkNotNullParameter(classLoader, "<this>");
        Intrinsics.checkNotNullParameter(keyResourcePath, "keyResourcePath");
        final ClassLoaderResourceRootFIlePathCalculator classLoaderResourceRootFIlePathCalculator = new ClassLoaderResourceRootFIlePathCalculator(keyResourcePath);
        Enumeration<URL> resources = classLoader.getResources(keyResourcePath);
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        return SequencesKt.mapNotNull(SequencesKt.asSequence(CollectionsKt.iterator(resources)), new Function1<URL, File>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.rawClassPathFromKeyResourcePath.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final File invoke(URL url) throws IOException {
                URL jarFileURL;
                if (Intrinsics.areEqual(url.getProtocol(), "jar")) {
                    URLConnection uRLConnectionOpenConnection = url.openConnection();
                    JarURLConnection jarURLConnection = uRLConnectionOpenConnection instanceof JarURLConnection ? (JarURLConnection) uRLConnectionOpenConnection : null;
                    if (jarURLConnection == null || (jarFileURL = jarURLConnection.getJarFileURL()) == null) {
                        return null;
                    }
                    return PathUtilKt.toFileOrNull(jarFileURL);
                }
                Intrinsics.checkNotNull(url);
                File fileOrNull = PathUtilKt.toFileOrNull(url);
                if (fileOrNull != null) {
                    return classLoaderResourceRootFIlePathCalculator.invoke(fileOrNull);
                }
                return null;
            }
        });
    }

    public static final Sequence<File> classPathFromTypicalResourceUrls(ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "<this>");
        return SequencesKt.filter(SequencesKt.distinct(SequencesKt.plus((Sequence) rawClassPathFromKeyResourcePath(classLoader, ""), (Sequence) rawClassPathFromKeyResourcePath(classLoader, "META-INF/MANIFEST.MF"))), new Function1<File, Boolean>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classPathFromTypicalResourceUrls.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(JvmClasspathUtilKt.isValidClasspathFile(it));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence<File> unpackJarCollection(File file, File file2) throws IOException {
        JarEntry nextJarEntry;
        File fileCreateTempFile = File.createTempFile(FilesKt.getNameWithoutExtension(file), null, file2);
        fileCreateTempFile.delete();
        fileCreateTempFile.mkdir();
        try {
            ArrayList arrayList = new ArrayList();
            JarInputStream jarInputStream = new JarInputStream(new FileInputStream(file));
            try {
                JarInputStream jarInputStream2 = jarInputStream;
                for (String str : JAR_COLLECTIONS_CLASSES_PATHS) {
                    arrayList.add(new File(fileCreateTempFile, str));
                }
                do {
                    nextJarEntry = jarInputStream2.getNextJarEntry();
                    if (nextJarEntry != null) {
                        try {
                            if (!nextJarEntry.isDirectory()) {
                                File file3 = new File(fileCreateTempFile, nextJarEntry.getName());
                                for (String str2 : JAR_COLLECTIONS_LIB_PATHS) {
                                    String name2 = nextJarEntry.getName();
                                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                                    if (StringsKt.startsWith$default(name2, str2 + '/', false, 2, (Object) null)) {
                                        arrayList.add(file3);
                                        break;
                                    }
                                }
                                file3.getParentFile().mkdirs();
                                FileOutputStream fileOutputStream = new FileOutputStream(file3);
                                try {
                                    FileOutputStream fileOutputStream2 = fileOutputStream;
                                    ByteStreamsKt.copyTo$default(jarInputStream2, fileOutputStream2, 0, 2, null);
                                    fileOutputStream2.flush();
                                    Unit unit = Unit.INSTANCE;
                                    CloseableKt.closeFinally(fileOutputStream, null);
                                } catch (Throwable th) {
                                    try {
                                        throw th;
                                    } catch (Throwable th2) {
                                        CloseableKt.closeFinally(fileOutputStream, th);
                                        throw th2;
                                    }
                                }
                            }
                            jarInputStream2.closeEntry();
                        } catch (Throwable th3) {
                            jarInputStream2.closeEntry();
                            throw th3;
                        }
                    }
                } while (nextJarEntry != null);
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(jarInputStream, null);
                return CollectionsKt.asSequence(arrayList);
            } catch (Throwable th4) {
                try {
                    throw th4;
                } catch (Throwable th5) {
                    CloseableKt.closeFinally(jarInputStream, th4);
                    throw th5;
                }
            }
        } catch (Throwable th6) {
            Intrinsics.checkNotNull(fileCreateTempFile);
            FilesKt.deleteRecursively(fileCreateTempFile);
            throw th6;
        }
    }

    public static final List<File> classpathFromClasspathProperty() {
        List listEmptyList;
        String property = System.getProperty(SystemProperties.JAVA_CLASS_PATH);
        if (property != null) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("\\%s", Arrays.copyOf(new Object[]{Character.valueOf(File.pathSeparatorChar)}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            List<String> listSplit = new Regex(str).split(property, 0);
            if (listSplit != null) {
                if (!listSplit.isEmpty()) {
                    ListIterator<String> listIterator = listSplit.listIterator(listSplit.size());
                    while (true) {
                        if (listIterator.hasPrevious()) {
                            if (listIterator.previous().length() != 0) {
                                listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                                break;
                            }
                        } else {
                            listEmptyList = CollectionsKt.emptyList();
                            break;
                        }
                    }
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                    break;
                }
                if (listEmptyList != null) {
                    List list = listEmptyList;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new File((String) it.next()));
                    }
                    return arrayList;
                }
            }
        }
        return null;
    }

    public static final List<File> classpathFromClass(ClassLoader classLoader, KClass<? extends Object> klass) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        Intrinsics.checkNotNullParameter(klass, "klass");
        String qualifiedName = klass.getQualifiedName();
        Intrinsics.checkNotNull(qualifiedName);
        return classpathFromFQN(classLoader, qualifiedName);
    }

    public static final List<File> classpathFromClass(KClass<? extends Object> klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        ClassLoader classLoader = JvmClassMappingKt.getJavaClass((KClass) klass).getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
        return classpathFromClass(classLoader, klass);
    }

    public static final /* synthetic */ <T> List<File> classpathFromClass() {
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return classpathFromClass(Reflection.getOrCreateKotlinClass(Object.class));
    }

    public static final List<File> classpathFromFQN(ClassLoader classLoader, String fqn) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        List<File> list = SequencesKt.toList(SequencesKt.filter(rawClassPathFromKeyResourcePath(classLoader, StringsKt.replace$default(fqn, '.', '/', false, 4, (Object) null) + ".class"), new Function1<File, Boolean>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.classpathFromFQN.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(JvmClasspathUtilKt.isValidClasspathFile(it));
            }
        }));
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public static final boolean matchMaybeVersionedFile(File file, String baseName) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(baseName, "baseName");
        if (!Intrinsics.areEqual(file.getName(), baseName) && !Intrinsics.areEqual(file.getName(), StringsKt.removeSuffix(baseName, (CharSequence) ".jar"))) {
            Regex regex = new Regex(Regex.INSTANCE.escape(StringsKt.removeSuffix(baseName, (CharSequence) ".jar")) + "(-\\d.*)?\\.jar");
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (!regex.matches(name2)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean hasParentNamed(File file, String baseName) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(baseName, "baseName");
        if (!Intrinsics.areEqual(FilesKt.getNameWithoutExtension(file), baseName)) {
            File parentFile = file.getParentFile();
            if (!(parentFile != null ? hasParentNamed(parentFile, baseName) : false)) {
                return false;
            }
        }
        return true;
    }

    static /* synthetic */ Sequence allRelatedClassLoaders$default(ClassLoader classLoader, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new HashSet();
        }
        return allRelatedClassLoaders(classLoader, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence<ClassLoader> allRelatedClassLoaders(ClassLoader classLoader, final Set<ClassLoader> set) {
        if (!set.add(classLoader)) {
            return SequencesKt.emptySequence();
        }
        ClassLoader parent = classLoader.getParent();
        if (parent != null) {
            return SequencesKt.plus((Sequence<? extends ClassLoader>) SequencesKt.flatMap(SequencesKt.sequenceOf(parent), new Function1<ClassLoader, Sequence<? extends ClassLoader>>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.allRelatedClassLoaders.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Sequence<ClassLoader> invoke(ClassLoader it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return JvmClasspathUtilKt.allRelatedClassLoaders(it, set);
                }
            }), classLoader);
        }
        try {
            return SequencesKt.plus((Sequence<? extends ClassLoader>) SequencesKt.flatMap(ArraysKt.asSequence(getParentClassLoaders(classLoader)), new Function1<ClassLoader, Sequence<? extends ClassLoader>>() { // from class: kotlin.script.experimental.jvm.util.JvmClasspathUtilKt.allRelatedClassLoaders.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Sequence<ClassLoader> invoke(ClassLoader it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return JvmClasspathUtilKt.allRelatedClassLoaders(it, set);
                }
            }), classLoader);
        } catch (Throwable unused) {
            return SequencesKt.sequenceOf(classLoader);
        }
    }

    private static final ClassLoader[] getParentClassLoaders(ClassLoader classLoader) {
        try {
            try {
                return getParentsForNewClassLoader(classLoader);
            } catch (NoSuchFieldException unused) {
                return new ClassLoader[0];
            }
        } catch (NoSuchMethodException unused2) {
            return getParentsForOldClassLoader(classLoader);
        }
    }

    private static final ClassLoader[] getParentsForOldClassLoader(ClassLoader classLoader) throws IllegalAccessException, NoSuchFieldException {
        Field declaredField = classLoader.getClass().getDeclaredField("myParents");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(classLoader);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<java.lang.ClassLoader>");
        return (ClassLoader[]) obj;
    }

    private static final ClassLoader[] getParentsForNewClassLoader(ClassLoader classLoader) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method declaredMethod = classLoader.getClass().getDeclaredMethod("getAllParents", new Class[0]);
        declaredMethod.setAccessible(true);
        Object objInvoke = declaredMethod.invoke(classLoader, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Array<java.lang.ClassLoader>");
        return (ClassLoader[]) objInvoke;
    }

    public static final List<File> filterIfContainsAll(List<? extends File> list, String... keyNames) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(keyNames, "keyNames");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList = new ArrayList();
        for (File file : list) {
            for (String str : keyNames) {
                if (matchMaybeVersionedFile(file, str) || (file.isDirectory() && hasParentNamed(file, str))) {
                    linkedHashSet.add(str);
                    arrayList.add(file);
                    break;
                }
            }
        }
        if (!linkedHashSet.containsAll(ArraysKt.asList(keyNames))) {
            arrayList = null;
        }
        return arrayList;
    }

    public static /* synthetic */ List scriptCompilationClasspathFromContextOrNull$default(String[] strArr, ClassLoader classLoader, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            classLoader = Thread.currentThread().getContextClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "getContextClassLoader(...)");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        return scriptCompilationClasspathFromContextOrNull(strArr, classLoader, z, z2);
    }

    private static final List<File> scriptCompilationClasspathFromContextOrNull$takeAndFilter(List<? extends File> list, boolean z, String[] strArr) {
        if (list.isEmpty()) {
            return null;
        }
        if (z) {
            return takeIfContainsAll(list, (String[]) Arrays.copyOf(strArr, strArr.length));
        }
        return filterIfContainsAll(list, (String[]) Arrays.copyOf(strArr, strArr.length));
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0057  */
    public static final List<File> scriptCompilationClasspathFromContextOrNull(String[] keyNames, ClassLoader classLoader, boolean z, boolean z2) {
        ArrayList arrayList;
        List<File> listScriptCompilationClasspathFromContextOrNull$takeAndFilter;
        Intrinsics.checkNotNullParameter(keyNames, "keyNames");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        String property = System.getProperty(KOTLIN_SCRIPT_CLASSPATH_PROPERTY);
        if (property != null) {
            String str = property;
            String pathSeparator = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
            List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{pathSeparator}, false, 0, 6, (Object) null);
            if (listSplit$default != null) {
                List list = listSplit$default;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new File((String) it.next()));
                }
                arrayList = arrayList2;
            } else {
                arrayList = null;
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            return arrayList;
        }
        List<File> listClasspathFromClassloader = classpathFromClassloader(classLoader, z2);
        if (listClasspathFromClassloader != null && (listScriptCompilationClasspathFromContextOrNull$takeAndFilter = scriptCompilationClasspathFromContextOrNull$takeAndFilter(listClasspathFromClassloader, z, keyNames)) != null) {
            return listScriptCompilationClasspathFromContextOrNull$takeAndFilter;
        }
        List<File> listClasspathFromClasspathProperty = classpathFromClasspathProperty();
        if (listClasspathFromClasspathProperty != null) {
            return scriptCompilationClasspathFromContextOrNull$takeAndFilter(listClasspathFromClasspathProperty, z, keyNames);
        }
        return null;
    }

    public static /* synthetic */ List scriptCompilationClasspathFromContextOrStdlib$default(String[] strArr, ClassLoader classLoader, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            classLoader = Thread.currentThread().getContextClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "getContextClassLoader(...)");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return scriptCompilationClasspathFromContextOrStdlib(strArr, classLoader, z);
    }

    public static final List<File> scriptCompilationClasspathFromContextOrStdlib(String[] keyNames, ClassLoader classLoader, boolean z) {
        Intrinsics.checkNotNullParameter(keyNames, "keyNames");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        List<File> listScriptCompilationClasspathFromContextOrNull$default = scriptCompilationClasspathFromContextOrNull$default((String[]) Arrays.copyOf(keyNames, keyNames.length), classLoader, z, false, 8, null);
        return listScriptCompilationClasspathFromContextOrNull$default == null ? KotlinJars.INSTANCE.getKotlinScriptStandardJars() : listScriptCompilationClasspathFromContextOrNull$default;
    }

    public static /* synthetic */ List scriptCompilationClasspathFromContext$default(String[] strArr, ClassLoader classLoader, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            classLoader = Thread.currentThread().getContextClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "getContextClassLoader(...)");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        return scriptCompilationClasspathFromContext(strArr, classLoader, z, z2);
    }

    public static final List<File> scriptCompilationClasspathFromContext(String[] keyNames, ClassLoader classLoader, boolean z, boolean z2) throws ClasspathExtractionException {
        Intrinsics.checkNotNullParameter(keyNames, "keyNames");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        List<File> listScriptCompilationClasspathFromContextOrNull = scriptCompilationClasspathFromContextOrNull((String[]) Arrays.copyOf(keyNames, keyNames.length), classLoader, z, z2);
        if (listScriptCompilationClasspathFromContextOrNull != null) {
            return listScriptCompilationClasspathFromContextOrNull;
        }
        throw new ClasspathExtractionException("Unable to get script compilation classpath from context, please specify explicit classpath via \"kotlin.script.classpath\" property");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<File> takeIfContainsAll(List<? extends File> list, String... keyNames) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(keyNames, "keyNames");
        for (String str : keyNames) {
            List<? extends File> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (matchMaybeVersionedFile((File) it.next(), str)) {
                    }
                }
            }
            return null;
        }
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<File> takeIfContainsAny(List<? extends File> list, String... keyNames) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(keyNames, "keyNames");
        for (String str : keyNames) {
            List<? extends File> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (matchMaybeVersionedFile((File) it.next(), str)) {
                        return list;
                    }
                }
            }
        }
        return null;
    }
}
