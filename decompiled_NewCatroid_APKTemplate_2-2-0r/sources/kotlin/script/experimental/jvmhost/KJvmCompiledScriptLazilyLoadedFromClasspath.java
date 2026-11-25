package kotlin.script.experimental.jvmhost;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.jvm.JvmScriptEvaluationKt;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScriptKt;

/* JADX INFO: compiled from: jvmScriptSaving.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\"\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b0\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0096@¢\u0006\u0002\u0010\u001eJ\u0006\u0010\u001f\u001a\u00020\rR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\"\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006 "}, d2 = {"Lkotlin/script/experimental/jvmhost/KJvmCompiledScriptLazilyLoadedFromClasspath;", "Lkotlin/script/experimental/api/CompiledScript;", "scriptClassFQName", "", "classPath", "", "Ljava/io/File;", "(Ljava/lang/String;Ljava/util/List;)V", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "loadedScript", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;", "otherScripts", "getOtherScripts", "()Ljava/util/List;", "resultField", "Lkotlin/Pair;", "Lkotlin/script/experimental/api/KotlinType;", "getResultField", "()Lkotlin/Pair;", "sourceLocationId", "getSourceLocationId", "()Ljava/lang/String;", "getClass", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/reflect/KClass;", "scriptEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getScriptOrError", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KJvmCompiledScriptLazilyLoadedFromClasspath implements CompiledScript {
    private final List<File> classPath;
    private KJvmCompiledScript loadedScript;
    private final String scriptClassFQName;

    /* JADX WARN: Multi-variable type inference failed */
    public KJvmCompiledScriptLazilyLoadedFromClasspath(String scriptClassFQName, List<? extends File> classPath) {
        Intrinsics.checkNotNullParameter(scriptClassFQName, "scriptClassFQName");
        Intrinsics.checkNotNullParameter(classPath, "classPath");
        this.scriptClassFQName = scriptClassFQName;
        this.classPath = classPath;
    }

    public final KJvmCompiledScript getScriptOrError() {
        KJvmCompiledScript kJvmCompiledScript = this.loadedScript;
        if (kJvmCompiledScript != null) {
            return kJvmCompiledScript;
        }
        throw new RuntimeException("Compiled script is not loaded yet");
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public Object getClass(ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<? extends KClass<?>>> continuation) {
        if (this.loadedScript == null) {
            ScriptEvaluationConfiguration scriptEvaluationConfiguration2 = scriptEvaluationConfiguration == null ? new ScriptEvaluationConfiguration(null, 1, null) : scriptEvaluationConfiguration;
            ClassLoader classLoader = (ClassLoader) scriptEvaluationConfiguration2.get(JvmScriptEvaluationKt.getBaseClassLoader(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)));
            Object obj = scriptEvaluationConfiguration2.get(JvmScriptEvaluationKt.getLoadDependencies(JvmScriptEvaluationKt.getJvm(ScriptEvaluationConfiguration.INSTANCE)));
            Intrinsics.checkNotNull(obj);
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            List<File> listTake = this.classPath;
            if (!zBooleanValue) {
                listTake = CollectionsKt.take(listTake, 1);
            }
            List<File> list = listTake;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((File) it.next()).toURI().toURL());
            }
            this.loadedScript = KJvmCompiledScriptKt.createScriptFromClassLoader(this.scriptClassFQName, new URLClassLoader((URL[]) arrayList.toArray(new URL[0]), classLoader));
        }
        return getScriptOrError().getClass(scriptEvaluationConfiguration, continuation);
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public ScriptCompilationConfiguration getCompilationConfiguration() {
        return getScriptOrError().getCompilationConfiguration();
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public String getSourceLocationId() {
        return getScriptOrError().getSourceLocationId();
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public List<CompiledScript> getOtherScripts() {
        return getScriptOrError().getOtherScripts();
    }

    @Override // kotlin.script.experimental.api.CompiledScript
    public Pair<String, KotlinType> getResultField() {
        return getScriptOrError().getResultField();
    }
}
