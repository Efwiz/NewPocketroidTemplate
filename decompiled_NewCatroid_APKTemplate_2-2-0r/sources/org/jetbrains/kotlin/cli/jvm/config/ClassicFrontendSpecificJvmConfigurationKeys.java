package org.jetbrains.kotlin.cli.jvm.config;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;

/* JADX INFO: compiled from: ClassicFrontendSpecificJvmConfigurationKeys.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/config/ClassicFrontendSpecificJvmConfigurationKeys;", "", "()V", "JAVA_CLASSES_TRACKER", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassicFrontendSpecificJvmConfigurationKeys {
    public static final ClassicFrontendSpecificJvmConfigurationKeys INSTANCE = new ClassicFrontendSpecificJvmConfigurationKeys();
    public static final CompilerConfigurationKey<JavaClassesTracker> JAVA_CLASSES_TRACKER;

    private ClassicFrontendSpecificJvmConfigurationKeys() {
    }

    static {
        CompilerConfigurationKey<JavaClassesTracker> compilerConfigurationKeyCreate = CompilerConfigurationKey.create("Java classes tracker");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate, "create(...)");
        JAVA_CLASSES_TRACKER = compilerConfigurationKeyCreate;
    }
}
