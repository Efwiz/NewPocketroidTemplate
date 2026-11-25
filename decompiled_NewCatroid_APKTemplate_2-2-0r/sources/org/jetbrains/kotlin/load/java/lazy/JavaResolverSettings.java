package org.jetbrains.kotlin.load.java.lazy;

import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\bf\u0018\u0000 \f2\u00020\u0001:\u0002\f\rR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;", "", "correctNullabilityForNotNullTypeParameter", "", "getCorrectNullabilityForNotNullTypeParameter", "()Z", "enhancePrimitiveArrays", "getEnhancePrimitiveArrays", "ignoreNullabilityForErasedValueParameters", "getIgnoreNullabilityForErasedValueParameters", "typeEnhancementImprovementsInStrictMode", "getTypeEnhancementImprovementsInStrictMode", "Companion", "Default", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaResolverSettings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: renamed from: getCorrectNullabilityForNotNullTypeParameter */
    boolean get$correctNullabilityForNotNullTypeParameter();

    /* JADX INFO: renamed from: getEnhancePrimitiveArrays */
    boolean get$enhancePrimitiveArrays();

    /* JADX INFO: renamed from: getIgnoreNullabilityForErasedValueParameters */
    boolean get$ignoreNullabilityForErasedValueParameters();

    /* JADX INFO: renamed from: getTypeEnhancementImprovementsInStrictMode */
    boolean get$typeEnhancementImprovementsInStrictMode();

    /* JADX INFO: compiled from: context.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings$Default;", "Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;", "()V", "correctNullabilityForNotNullTypeParameter", "", "getCorrectNullabilityForNotNullTypeParameter", "()Z", "enhancePrimitiveArrays", "getEnhancePrimitiveArrays", "ignoreNullabilityForErasedValueParameters", "getIgnoreNullabilityForErasedValueParameters", "typeEnhancementImprovementsInStrictMode", "getTypeEnhancementImprovementsInStrictMode", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements JavaResolverSettings {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
        /* JADX INFO: renamed from: getCorrectNullabilityForNotNullTypeParameter */
        public boolean get$correctNullabilityForNotNullTypeParameter() {
            return false;
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
        /* JADX INFO: renamed from: getEnhancePrimitiveArrays */
        public boolean get$enhancePrimitiveArrays() {
            return false;
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
        /* JADX INFO: renamed from: getIgnoreNullabilityForErasedValueParameters */
        public boolean get$ignoreNullabilityForErasedValueParameters() {
            return false;
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
        /* JADX INFO: renamed from: getTypeEnhancementImprovementsInStrictMode */
        public boolean get$typeEnhancementImprovementsInStrictMode() {
            return false;
        }

        private Default() {
        }
    }

    /* JADX INFO: compiled from: context.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;", "correctNullabilityForNotNullTypeParameter", "", "typeEnhancementImprovementsInStrictMode", "ignoreNullabilityForErasedValueParameters", "enhancePrimitiveArrays", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final JavaResolverSettings create(final boolean correctNullabilityForNotNullTypeParameter, final boolean typeEnhancementImprovementsInStrictMode, final boolean ignoreNullabilityForErasedValueParameters, final boolean enhancePrimitiveArrays) {
            return new JavaResolverSettings() { // from class: org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings$Companion$create$1
                @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
                /* JADX INFO: renamed from: getCorrectNullabilityForNotNullTypeParameter, reason: from getter */
                public boolean get$correctNullabilityForNotNullTypeParameter() {
                    return correctNullabilityForNotNullTypeParameter;
                }

                @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
                /* JADX INFO: renamed from: getTypeEnhancementImprovementsInStrictMode, reason: from getter */
                public boolean get$typeEnhancementImprovementsInStrictMode() {
                    return typeEnhancementImprovementsInStrictMode;
                }

                @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
                /* JADX INFO: renamed from: getIgnoreNullabilityForErasedValueParameters, reason: from getter */
                public boolean get$ignoreNullabilityForErasedValueParameters() {
                    return ignoreNullabilityForErasedValueParameters;
                }

                @Override // org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings
                /* JADX INFO: renamed from: getEnhancePrimitiveArrays, reason: from getter */
                public boolean get$enhancePrimitiveArrays() {
                    return enhancePrimitiveArrays;
                }
            };
        }
    }
}
