package org.jetbrains.kotlin.descriptors.runtime.components;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJava;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;

/* JADX INFO: compiled from: RuntimeModuleData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeModuleData;", "", "deserialization", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "packagePartScopeCache", "Lorg/jetbrains/kotlin/descriptors/runtime/components/PackagePartScopeCache;", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;Lorg/jetbrains/kotlin/descriptors/runtime/components/PackagePartScopeCache;)V", "getDeserialization", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getPackagePartScopeCache", "()Lorg/jetbrains/kotlin/descriptors/runtime/components/PackagePartScopeCache;", "Companion", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RuntimeModuleData {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final DeserializationComponents deserialization;
    private final PackagePartScopeCache packagePartScopeCache;

    public /* synthetic */ RuntimeModuleData(DeserializationComponents deserializationComponents, PackagePartScopeCache packagePartScopeCache, DefaultConstructorMarker defaultConstructorMarker) {
        this(deserializationComponents, packagePartScopeCache);
    }

    private RuntimeModuleData(DeserializationComponents deserializationComponents, PackagePartScopeCache packagePartScopeCache) {
        this.deserialization = deserializationComponents;
        this.packagePartScopeCache = packagePartScopeCache;
    }

    public final DeserializationComponents getDeserialization() {
        return this.deserialization;
    }

    public final PackagePartScopeCache getPackagePartScopeCache() {
        return this.packagePartScopeCache;
    }

    public final ModuleDescriptor getModule() {
        return this.deserialization.getModuleDescriptor();
    }

    /* JADX INFO: compiled from: RuntimeModuleData.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeModuleData$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeModuleData;", "classLoader", "Ljava/lang/ClassLoader;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final RuntimeModuleData create(ClassLoader classLoader) {
            Intrinsics.checkNotNullParameter(classLoader, "classLoader");
            ReflectKotlinClassFinder reflectKotlinClassFinder = new ReflectKotlinClassFinder(classLoader);
            ClassLoader classLoader2 = Unit.class.getClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader2, "getClassLoader(...)");
            DeserializationComponentsForJava.Companion.ModuleData moduleDataCreateModuleData = DeserializationComponentsForJava.INSTANCE.createModuleData(reflectKotlinClassFinder, new ReflectKotlinClassFinder(classLoader2), new ReflectJavaClassFinder(classLoader), "runtime module for " + classLoader, RuntimeErrorReporter.INSTANCE, RuntimeSourceElementFactory.INSTANCE);
            return new RuntimeModuleData(moduleDataCreateModuleData.getDeserializationComponentsForJava().getComponents(), new PackagePartScopeCache(moduleDataCreateModuleData.getDeserializedDescriptorResolver(), reflectKotlinClassFinder), null);
        }
    }
}
