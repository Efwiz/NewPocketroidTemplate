package kotlin.script.experimental.jvm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptDependency;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0004R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lkotlin/script/experimental/jvm/JvmDependencyFromClassLoader;", "Lkotlin/script/experimental/api/ScriptDependency;", "classLoaderGetter", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Ljava/lang/ClassLoader;", "Lkotlin/script/experimental/jvm/ClassLoaderByConfiguration;", "(Lkotlin/jvm/functions/Function1;)V", "getClassLoaderGetter", "()Lkotlin/jvm/functions/Function1;", "getClassLoader", "configuration", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDependencyFromClassLoader implements ScriptDependency {
    private final Function1<ScriptCompilationConfiguration, ClassLoader> classLoaderGetter;

    /* JADX WARN: Multi-variable type inference failed */
    public JvmDependencyFromClassLoader(Function1<? super ScriptCompilationConfiguration, ? extends ClassLoader> classLoaderGetter) {
        Intrinsics.checkNotNullParameter(classLoaderGetter, "classLoaderGetter");
        this.classLoaderGetter = classLoaderGetter;
    }

    public final Function1<ScriptCompilationConfiguration, ClassLoader> getClassLoaderGetter() {
        return this.classLoaderGetter;
    }

    public final ClassLoader getClassLoader(ScriptCompilationConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        return this.classLoaderGetter.invoke(configuration);
    }
}
