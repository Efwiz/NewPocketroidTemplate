package kotlin.script.experimental.jvm.impl;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledModule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledModuleInMemory;", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;", "compilerOutputFiles", "", "", "", "getCompilerOutputFiles", "()Ljava/util/Map;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KJvmCompiledModuleInMemory extends KJvmCompiledModule {
    Map<String, byte[]> getCompilerOutputFiles();
}
