package org.jetbrains.kotlin.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u0018*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0004*\b\b\u0002\u0010\u0005*\u00020\u0004*\b\b\u0003\u0010\u0006*\u00020\u00042D\u0012\u0004\u0012\u0002H\u0001\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\t0\u0007:\u0001\u0018BM\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u001e\u0010\f\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\t\u0012\u001e\u0010\r\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\t¢\u0006\u0002\u0010\u000eJ9\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00028\u00012\u0006\u0010\u0015\u001a\u00028\u00022\u0006\u0010\u0016\u001a\u00028\u0003¢\u0006\u0002\u0010\u0017¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation3;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "A", "", "B", "C", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters3;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;)V", "on", "Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "c", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/psi/PsiElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticFactoryForDeprecation3<E extends PsiElement, A, B, C> extends DiagnosticFactoryForDeprecation<E, DiagnosticWithParameters3<E, A, B, C>, DiagnosticFactory3<E, A, B, C>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final <E extends PsiElement, A, B, C> DiagnosticFactoryForDeprecation3<E, A, B, C> create(LanguageFeature languageFeature) {
        return INSTANCE.create(languageFeature);
    }

    @JvmStatic
    public static final <E extends PsiElement, A, B, C> DiagnosticFactoryForDeprecation3<E, A, B, C> create(LanguageFeature languageFeature, PositioningStrategy<? super E> positioningStrategy) {
        return INSTANCE.create(languageFeature, positioningStrategy);
    }

    /* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J`\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\u0004\"\b\b\u0004\u0010\u0005*\u00020\t\"\b\b\u0005\u0010\u0006*\u00020\u0001\"\b\b\u0006\u0010\u0007*\u00020\u0001\"\b\b\u0007\u0010\b*\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00050\rH\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation3$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation3;", ExifInterface.LONGITUDE_EAST, "A", "B", "C", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final <E extends PsiElement, A, B, C> DiagnosticFactoryForDeprecation3<E, A, B, C> create(LanguageFeature featureForError) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            return create$default(this, featureForError, null, 2, null);
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DiagnosticFactoryForDeprecation3 create$default(Companion companion, LanguageFeature languageFeature, PositioningStrategy positioningStrategy, int i, Object obj) {
            if ((i & 2) != 0) {
                positioningStrategy = PositioningStrategies.DEFAULT;
            }
            return companion.create(languageFeature, positioningStrategy);
        }

        @JvmStatic
        public final <E extends PsiElement, A, B, C> DiagnosticFactoryForDeprecation3<E, A, B, C> create(LanguageFeature featureForError, PositioningStrategy<? super E> positioningStrategy) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
            DiagnosticFactory3 diagnosticFactory3Create = DiagnosticFactory3.create(Severity.WARNING, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory3Create, "create(...)");
            DiagnosticFactory3 diagnosticFactory3Create2 = DiagnosticFactory3.create(Severity.ERROR, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory3Create2, "create(...)");
            return new DiagnosticFactoryForDeprecation3<>(featureForError, diagnosticFactory3Create, diagnosticFactory3Create2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiagnosticFactoryForDeprecation3(LanguageFeature featureForError, DiagnosticFactory3<E, A, B, C> warningFactory, DiagnosticFactory3<E, A, B, C> errorFactory) {
        super(featureForError, warningFactory, errorFactory, null);
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(warningFactory, "warningFactory");
        Intrinsics.checkNotNullParameter(errorFactory, "errorFactory");
    }

    public final ParametrizedDiagnostic<E> on(LanguageVersionSettings languageVersionSettings, E element, A a, B b, C c) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        ParametrizedDiagnostic<E> parametrizedDiagnosticOn = chooseFactory(languageVersionSettings).on(element, a, b, c);
        Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn, "on(...)");
        return parametrizedDiagnosticOn;
    }
}
