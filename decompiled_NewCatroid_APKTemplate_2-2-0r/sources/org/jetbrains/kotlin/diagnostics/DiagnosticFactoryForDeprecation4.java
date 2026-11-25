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
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001a*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0004*\b\b\u0002\u0010\u0005*\u00020\u0004*\b\b\u0003\u0010\u0006*\u00020\u0004*\b\b\u0004\u0010\u0007*\u00020\u00042P\u0012\u0004\u0012\u0002H\u0001\u0012\"\u0012 \u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\t\u0012\"\u0012 \u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\n0\b:\u0001\u001aBY\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012$\u0010\r\u001a \u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\n\u0012$\u0010\u000e\u001a \u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u0003\u0012\u0004\u0012\u00028\u00040\n¢\u0006\u0002\u0010\u000fJA\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00028\u00012\u0006\u0010\u0016\u001a\u00028\u00022\u0006\u0010\u0017\u001a\u00028\u00032\u0006\u0010\u0018\u001a\u00028\u0004¢\u0006\u0002\u0010\u0019¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation4;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "A", "", "B", "C", "D", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters4;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory4;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory4;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory4;)V", "on", "Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "c", "d", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/psi/PsiElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticFactoryForDeprecation4<E extends PsiElement, A, B, C, D> extends DiagnosticFactoryForDeprecation<E, DiagnosticWithParameters4<E, A, B, C, D>, DiagnosticFactory4<E, A, B, C, D>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final <E extends PsiElement, A, B, C, D> DiagnosticFactoryForDeprecation4<E, A, B, C, D> create(LanguageFeature languageFeature) {
        return INSTANCE.create(languageFeature);
    }

    @JvmStatic
    public static final <E extends PsiElement, A, B, C, D> DiagnosticFactoryForDeprecation4<E, A, B, C, D> create(LanguageFeature languageFeature, PositioningStrategy<? super E> positioningStrategy) {
        return INSTANCE.create(languageFeature, positioningStrategy);
    }

    /* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jp\u0010\u0003\u001a \u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\u0004\"\b\b\u0005\u0010\u0005*\u00020\n\"\b\b\u0006\u0010\u0006*\u00020\u0001\"\b\b\u0007\u0010\u0007*\u00020\u0001\"\b\b\b\u0010\b*\u00020\u0001\"\b\b\t\u0010\t*\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000eH\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation4$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation4;", ExifInterface.LONGITUDE_EAST, "A", "B", "C", "D", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final <E extends PsiElement, A, B, C, D> DiagnosticFactoryForDeprecation4<E, A, B, C, D> create(LanguageFeature featureForError) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            return create$default(this, featureForError, null, 2, null);
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DiagnosticFactoryForDeprecation4 create$default(Companion companion, LanguageFeature languageFeature, PositioningStrategy positioningStrategy, int i, Object obj) {
            if ((i & 2) != 0) {
                positioningStrategy = PositioningStrategies.DEFAULT;
            }
            return companion.create(languageFeature, positioningStrategy);
        }

        @JvmStatic
        public final <E extends PsiElement, A, B, C, D> DiagnosticFactoryForDeprecation4<E, A, B, C, D> create(LanguageFeature featureForError, PositioningStrategy<? super E> positioningStrategy) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
            DiagnosticFactory4 diagnosticFactory4Create = DiagnosticFactory4.create(Severity.WARNING, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory4Create, "create(...)");
            DiagnosticFactory4 diagnosticFactory4Create2 = DiagnosticFactory4.create(Severity.ERROR, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory4Create2, "create(...)");
            return new DiagnosticFactoryForDeprecation4<>(featureForError, diagnosticFactory4Create, diagnosticFactory4Create2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiagnosticFactoryForDeprecation4(LanguageFeature featureForError, DiagnosticFactory4<E, A, B, C, D> warningFactory, DiagnosticFactory4<E, A, B, C, D> errorFactory) {
        super(featureForError, warningFactory, errorFactory, null);
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(warningFactory, "warningFactory");
        Intrinsics.checkNotNullParameter(errorFactory, "errorFactory");
    }

    public final ParametrizedDiagnostic<E> on(LanguageVersionSettings languageVersionSettings, E element, A a, B b, C c, D d) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(d, "d");
        ParametrizedDiagnostic<E> parametrizedDiagnosticOn = chooseFactory(languageVersionSettings).on(element, a, b, c, d);
        Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn, "on(...)");
        return parametrizedDiagnosticOn;
    }
}
