package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import java.io.Serializable;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.jvm.impl.KJvmCompiledModuleInMemory;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledModuleInMemoryImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \r2\u00020\u00012\u00020\u0002:\u0001\rB\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\u0007J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0016R \u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmCompiledModuleInMemoryImpl;", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledModuleInMemory;", "Ljava/io/Serializable;", "compilerOutputFiles", "", "", "", "(Ljava/util/Map;)V", "getCompilerOutputFiles", "()Ljava/util/Map;", "createClassLoader", "Ljava/lang/ClassLoader;", "baseClassLoader", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KJvmCompiledModuleInMemoryImpl implements KJvmCompiledModuleInMemory, Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 0;
    private final Map<String, byte[]> compilerOutputFiles;

    public KJvmCompiledModuleInMemoryImpl(Map<String, byte[]> compilerOutputFiles) {
        Intrinsics.checkNotNullParameter(compilerOutputFiles, "compilerOutputFiles");
        this.compilerOutputFiles = compilerOutputFiles;
    }

    @Override // kotlin.script.experimental.jvm.impl.KJvmCompiledModuleInMemory
    public Map<String, byte[]> getCompilerOutputFiles() {
        return this.compilerOutputFiles;
    }

    @Override // kotlin.script.experimental.jvm.impl.KJvmCompiledModule
    public ClassLoader createClassLoader(ClassLoader baseClassLoader) {
        return new CompiledScriptClassLoader(baseClassLoader, getCompilerOutputFiles());
    }

    /* JADX INFO: compiled from: KJvmCompiledModuleInMemoryImpl.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmCompiledModuleInMemoryImpl$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        private static /* synthetic */ void getSerialVersionUID$annotations() {
        }

        private Companion() {
        }
    }
}
