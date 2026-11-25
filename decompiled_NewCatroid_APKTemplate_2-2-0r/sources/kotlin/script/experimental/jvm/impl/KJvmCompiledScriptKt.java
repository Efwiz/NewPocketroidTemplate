package kotlin.script.experimental.jvm.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.JvmDependencyFromClassLoader;
import kotlin.script.experimental.jvm.JvmScriptEvaluationKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledScript.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\u001a\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010\b\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001\u001a\n\u0010\t\u001a\u00020\u0004*\u00020\u0004\u001a\u0012\u0010\n\u001a\u00020\u0007*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f\u001a\"\u0010\r\u001a\u0004\u0018\u00010\u0007*\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00072\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007H\u0002\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"KOTLIN_SCRIPT_METADATA_EXTENSION_WITH_DOT", "", "KOTLIN_SCRIPT_METADATA_PATH", "createScriptFromClassLoader", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;", "scriptClassFQName", "classLoader", "Ljava/lang/ClassLoader;", "scriptMetadataPath", "copyWithoutModule", "getOrCreateActualClassloader", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "makeClassLoaderFromDependencies", "Lkotlin/script/experimental/api/CompiledScript;", "baseClassLoader", "lastClassLoader", "toBytes", "", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KJvmCompiledScriptKt {
    public static final String KOTLIN_SCRIPT_METADATA_EXTENSION_WITH_DOT = ".kotlin_script";
    public static final String KOTLIN_SCRIPT_METADATA_PATH = "META-INF/kotlin/script";

    public static final ClassLoader getOrCreateActualClassloader(KJvmCompiledScript kJvmCompiledScript, ScriptEvaluationConfiguration evaluationConfiguration) throws MalformedURLException {
        Intrinsics.checkNotNullParameter(kJvmCompiledScript, "<this>");
        Intrinsics.checkNotNullParameter(evaluationConfiguration, "evaluationConfiguration");
        ClassLoader classLoader = (ClassLoader) evaluationConfiguration.get(JvmScriptEvaluationKt.getActualClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)));
        if (classLoader != null) {
            return classLoader;
        }
        KJvmCompiledModule compiledModule$kotlin_scripting_jvm = kJvmCompiledScript.getCompiledModule$kotlin_scripting_jvm();
        if (compiledModule$kotlin_scripting_jvm == null) {
            throw new IllegalStateException("Illegal call sequence, actualClassloader should be set before calling function on the class without module");
        }
        ClassLoader classLoaderMakeClassLoaderFromDependencies = (ClassLoader) evaluationConfiguration.get(JvmScriptEvaluationKt.getBaseClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)));
        ClassLoader classLoader2 = (ClassLoader) evaluationConfiguration.get(JvmScriptEvaluationKt.getLastSnippetClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)));
        if (classLoader2 == null) {
            classLoader2 = classLoaderMakeClassLoaderFromDependencies;
        }
        if (!Intrinsics.areEqual(evaluationConfiguration.get(JvmScriptEvaluationKt.getLoadDependencies(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE))), (Object) false)) {
            classLoaderMakeClassLoaderFromDependencies = makeClassLoaderFromDependencies(kJvmCompiledScript, classLoaderMakeClassLoaderFromDependencies, classLoader2);
        }
        return compiledModule$kotlin_scripting_jvm.createClassLoader(classLoaderMakeClassLoaderFromDependencies);
    }

    private static final ClassLoader makeClassLoaderFromDependencies(CompiledScript compiledScript, ClassLoader classLoader, ClassLoader classLoader2) throws MalformedURLException {
        Sequence<Pair> sequenceFlatMap = SequencesKt.flatMap(makeClassLoaderFromDependencies$recursiveScriptsSeq(new LinkedHashSet(), SequencesKt.emptySequence(), compiledScript), new Function1<CompiledScript, Sequence<? extends Pair<? extends ScriptCompilationConfiguration, ? extends ScriptDependency>>>() { // from class: kotlin.script.experimental.jvm.impl.KJvmCompiledScriptKt$makeClassLoaderFromDependencies$dependenciesWithConfigurations$1
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<Pair<ScriptCompilationConfiguration, ScriptDependency>> invoke(final CompiledScript script) {
                Sequence sequenceAsSequence;
                Sequence<Pair<ScriptCompilationConfiguration, ScriptDependency>> map;
                Intrinsics.checkNotNullParameter(script, "script");
                List list = (List) script.getCompilationConfiguration().get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE));
                return (list == null || (sequenceAsSequence = CollectionsKt.asSequence(list)) == null || (map = SequencesKt.map(sequenceAsSequence, new Function1<ScriptDependency, Pair<? extends ScriptCompilationConfiguration, ? extends ScriptDependency>>() { // from class: kotlin.script.experimental.jvm.impl.KJvmCompiledScriptKt$makeClassLoaderFromDependencies$dependenciesWithConfigurations$1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Pair<ScriptCompilationConfiguration, ScriptDependency> invoke(ScriptDependency it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return TuplesKt.to(script.getCompilationConfiguration(), it);
                    }
                })) == null) ? SequencesKt.emptySequence() : map;
            }
        });
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<URL> it = makeClassLoaderFromDependencies$recursiveClassPath(classLoader, SequencesKt.emptySequence(), classLoader2).iterator();
        while (it.hasNext()) {
            linkedHashSet.add(it.next());
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (Pair pair : sequenceFlatMap) {
            ScriptCompilationConfiguration scriptCompilationConfiguration = (ScriptCompilationConfiguration) pair.component1();
            ScriptDependency scriptDependency = (ScriptDependency) pair.component2();
            ClassLoader dualClassLoader = null;
            if (scriptDependency instanceof JvmDependency) {
                List<File> classpath = ((JvmDependency) scriptDependency).getClasspath();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it2 = classpath.iterator();
                while (it2.hasNext()) {
                    URL url = ((File) it2.next()).toURI().toURL();
                    Intrinsics.checkNotNull(url);
                    if (!linkedHashSet.add(url)) {
                        url = null;
                    }
                    if (url != null) {
                        arrayList.add(url);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (arrayList2.isEmpty()) {
                    arrayList2 = null;
                }
                dualClassLoader = arrayList2 != null ? new URLClassLoader((URL[]) arrayList2.toArray(new URL[0]), classLoader2) : null;
            } else if (scriptDependency instanceof JvmDependencyFromClassLoader) {
                ClassLoader classLoader3 = ((JvmDependencyFromClassLoader) scriptDependency).getClassLoader(scriptCompilationConfiguration);
                dualClassLoader = linkedHashSet2.add(classLoader3) ? new DualClassLoader(classLoader3, classLoader2) : null;
            }
            if (dualClassLoader != null) {
                classLoader2 = dualClassLoader;
            }
        }
        return classLoader2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [kotlin.sequences.Sequence, kotlin.sequences.Sequence<? extends kotlin.script.experimental.api.CompiledScript>] */
    /* JADX WARN: Type inference failed for: r2v1, types: [kotlin.sequences.Sequence<kotlin.script.experimental.api.CompiledScript>] */
    /* JADX WARN: Type inference failed for: r2v3, types: [kotlin.sequences.Sequence] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    private static final Sequence<CompiledScript> makeClassLoaderFromDependencies$recursiveScriptsSeq(Set<CompiledScript> set, Sequence<? extends CompiledScript> sequence, CompiledScript compiledScript) {
        if (set.add(compiledScript)) {
            Sequence sequenceAsSequence = CollectionsKt.asSequence(compiledScript.getOtherScripts());
            Sequence sequencePlus = SequencesKt.plus((Sequence<? extends CompiledScript>) sequence, compiledScript);
            Iterator it = sequenceAsSequence.iterator();
            sequence = sequencePlus;
            while (it.hasNext()) {
                sequence = makeClassLoaderFromDependencies$recursiveScriptsSeq(set, sequence, (CompiledScript) it.next());
            }
        }
        return sequence;
    }

    private static final Sequence<URL> makeClassLoaderFromDependencies$recursiveClassPath(ClassLoader classLoader, Sequence<URL> sequence, ClassLoader classLoader2) {
        if (classLoader2 == null ? true : Intrinsics.areEqual(classLoader2, classLoader)) {
            return sequence;
        }
        if (classLoader2 instanceof DualClassLoader) {
            DualClassLoader dualClassLoader = (DualClassLoader) classLoader2;
            return SequencesKt.plus((Sequence) makeClassLoaderFromDependencies$recursiveClassPath(classLoader, sequence, dualClassLoader.getParent()), (Sequence) makeClassLoaderFromDependencies$recursiveClassPath(classLoader, SequencesKt.emptySequence(), dualClassLoader.getFallbackClassLoader()));
        }
        if (!(classLoader2 instanceof URLClassLoader)) {
            return makeClassLoaderFromDependencies$recursiveClassPath(classLoader, sequence, classLoader2.getParent());
        }
        URLClassLoader uRLClassLoader = (URLClassLoader) classLoader2;
        URL[] uRLs = uRLClassLoader.getURLs();
        Intrinsics.checkNotNullExpressionValue(uRLs, "getURLs(...)");
        return makeClassLoaderFromDependencies$recursiveClassPath(classLoader, SequencesKt.plus((Sequence) sequence, (Object[]) uRLs), uRLClassLoader.getParent());
    }

    public static final String scriptMetadataPath(String scriptClassFQName) {
        Intrinsics.checkNotNullParameter(scriptClassFQName, "scriptClassFQName");
        return "META-INF/kotlin/script/" + scriptClassFQName + KOTLIN_SCRIPT_METADATA_EXTENSION_WITH_DOT;
    }

    public static final KJvmCompiledScript copyWithoutModule(KJvmCompiledScript kJvmCompiledScript) {
        Intrinsics.checkNotNullParameter(kJvmCompiledScript, "<this>");
        return new KJvmCompiledScript(kJvmCompiledScript.getData(), null);
    }

    public static final byte[] toBytes(KJvmCompiledScript kJvmCompiledScript) throws Throwable {
        Intrinsics.checkNotNullParameter(kJvmCompiledScript, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream2.writeObject(kJvmCompiledScript);
                objectOutputStream2.flush();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                try {
                    objectOutputStream2.close();
                } catch (IOException unused) {
                }
                return byteArray;
            } catch (Throwable th) {
                th = th;
                objectOutputStream = objectOutputStream2;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static final KJvmCompiledScript createScriptFromClassLoader(String scriptClassFQName, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(scriptClassFQName, "scriptClassFQName");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        InputStream resourceAsStream = classLoader.getResourceAsStream(scriptMetadataPath(scriptClassFQName));
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Cannot find metadata for script " + scriptClassFQName);
        }
        ObjectInputStream objectInputStream = new ObjectInputStream(resourceAsStream);
        try {
            Object object = objectInputStream.readObject();
            Intrinsics.checkNotNull(object, "null cannot be cast to non-null type kotlin.script.experimental.jvm.impl.KJvmCompiledScript");
            KJvmCompiledScript kJvmCompiledScript = (KJvmCompiledScript) object;
            CloseableKt.closeFinally(objectInputStream, null);
            kJvmCompiledScript.setCompiledModule$kotlin_scripting_jvm(new KJvmCompiledModuleFromClassLoader(classLoader));
            return kJvmCompiledScript;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(objectInputStream, th);
                throw th2;
            }
        }
    }
}
