package kotlin.script.experimental.jvm.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledModule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;", "", "createClassLoader", "Ljava/lang/ClassLoader;", "baseClassLoader", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KJvmCompiledModule {
    ClassLoader createClassLoader(ClassLoader baseClassLoader);
}
