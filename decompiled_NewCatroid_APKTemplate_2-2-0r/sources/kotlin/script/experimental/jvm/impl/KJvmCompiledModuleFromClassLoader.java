package kotlin.script.experimental.jvm.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledModule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledModuleFromClassLoader;", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;", "moduleClassLoader", "Ljava/lang/ClassLoader;", "(Ljava/lang/ClassLoader;)V", "getModuleClassLoader", "()Ljava/lang/ClassLoader;", "createClassLoader", "baseClassLoader", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KJvmCompiledModuleFromClassLoader implements KJvmCompiledModule {
    private final ClassLoader moduleClassLoader;

    public KJvmCompiledModuleFromClassLoader(ClassLoader moduleClassLoader) {
        Intrinsics.checkNotNullParameter(moduleClassLoader, "moduleClassLoader");
        this.moduleClassLoader = moduleClassLoader;
    }

    public final ClassLoader getModuleClassLoader() {
        return this.moduleClassLoader;
    }

    @Override // kotlin.script.experimental.jvm.impl.KJvmCompiledModule
    public ClassLoader createClassLoader(ClassLoader baseClassLoader) {
        if (baseClassLoader == null) {
            return this.moduleClassLoader;
        }
        return new DualClassLoader(this.moduleClassLoader, baseClassLoader);
    }
}
