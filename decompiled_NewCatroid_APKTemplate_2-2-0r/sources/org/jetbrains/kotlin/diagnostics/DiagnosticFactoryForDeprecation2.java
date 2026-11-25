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
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u0016*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0004*\b\b\u0002\u0010\u0005*\u00020\u000428\u0012\u0004\u0012\u0002H\u0001\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00050\u0007\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00050\b0\u0006:\u0001\u0016BA\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\b\u0012\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\b¢\u0006\u0002\u0010\rJ1\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u00012\u0006\u0010\u0014\u001a\u00028\u0002¢\u0006\u0002\u0010\u0015¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation2;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "A", "", "B", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters2;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;)V", "on", "Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/psi/PsiElement;Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticFactoryForDeprecation2<E extends PsiElement, A, B> extends DiagnosticFactoryForDeprecation<E, DiagnosticWithParameters2<E, A, B>, DiagnosticFactory2<E, A, B>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final <E extends PsiElement, A, B> DiagnosticFactoryForDeprecation2<E, A, B> create(LanguageFeature languageFeature) {
        return INSTANCE.create(languageFeature);
    }

    @JvmStatic
    public static final <E extends PsiElement, A, B> DiagnosticFactoryForDeprecation2<E, A, B> create(LanguageFeature languageFeature, PositioningStrategy<? super E> positioningStrategy) {
        return INSTANCE.create(languageFeature, positioningStrategy);
    }

    /* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JP\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\u0004\"\b\b\u0003\u0010\u0005*\u00020\b\"\b\b\u0004\u0010\u0006*\u00020\u0001\"\b\b\u0005\u0010\u0007*\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\fH\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation2$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation2;", ExifInterface.LONGITUDE_EAST, "A", "B", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final <E extends PsiElement, A, B> DiagnosticFactoryForDeprecation2<E, A, B> create(LanguageFeature featureForError) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            return create$default(this, featureForError, null, 2, null);
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DiagnosticFactoryForDeprecation2 create$default(Companion companion, LanguageFeature languageFeature, PositioningStrategy positioningStrategy, int i, Object obj) {
            if ((i & 2) != 0) {
                positioningStrategy = PositioningStrategies.DEFAULT;
            }
            return companion.create(languageFeature, positioningStrategy);
        }

        @JvmStatic
        public final <E extends PsiElement, A, B> DiagnosticFactoryForDeprecation2<E, A, B> create(LanguageFeature featureForError, PositioningStrategy<? super E> positioningStrategy) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
            DiagnosticFactory2 diagnosticFactory2Create = DiagnosticFactory2.create(Severity.WARNING, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory2Create, "create(...)");
            DiagnosticFactory2 diagnosticFactory2Create2 = DiagnosticFactory2.create(Severity.ERROR, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory2Create2, "create(...)");
            return new DiagnosticFactoryForDeprecation2<>(featureForError, diagnosticFactory2Create, diagnosticFactory2Create2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiagnosticFactoryForDeprecation2(LanguageFeature featureForError, DiagnosticFactory2<E, A, B> warningFactory, DiagnosticFactory2<E, A, B> errorFactory) {
        super(featureForError, warningFactory, errorFactory, null);
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(warningFactory, "warningFactory");
        Intrinsics.checkNotNullParameter(errorFactory, "errorFactory");
    }

    public final ParametrizedDiagnostic<E> on(LanguageVersionSettings languageVersionSettings, E element, A a, B b) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        ParametrizedDiagnostic<E> parametrizedDiagnosticOn = chooseFactory(languageVersionSettings).on(element, a, b);
        Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn, "on(...)");
        return parametrizedDiagnosticOn;
    }
}
