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

/* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u0014*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042,\u0012\u0004\u0012\u0002H\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00070\u0005:\u0001\u0014B5\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\fJ)\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00028\u0001¢\u0006\u0002\u0010\u0013¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation1;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "A", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticWithParameters1;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;)V", "on", "Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "a", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/psi/PsiElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticFactoryForDeprecation1<E extends PsiElement, A> extends DiagnosticFactoryForDeprecation<E, DiagnosticWithParameters1<E, A>, DiagnosticFactory1<E, A>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final <E extends PsiElement, A> DiagnosticFactoryForDeprecation1<E, A> create(LanguageFeature languageFeature) {
        return INSTANCE.create(languageFeature);
    }

    @JvmStatic
    public static final <E extends PsiElement, A> DiagnosticFactoryForDeprecation1<E, A> create(LanguageFeature languageFeature, PositioningStrategy<? super E> positioningStrategy) {
        return INSTANCE.create(languageFeature, positioningStrategy);
    }

    /* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J@\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\u0004\"\b\b\u0002\u0010\u0005*\u00020\u0007\"\b\b\u0003\u0010\u0006*\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000bH\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation1$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation1;", ExifInterface.LONGITUDE_EAST, "A", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final <E extends PsiElement, A> DiagnosticFactoryForDeprecation1<E, A> create(LanguageFeature featureForError) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            return create$default(this, featureForError, null, 2, null);
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DiagnosticFactoryForDeprecation1 create$default(Companion companion, LanguageFeature languageFeature, PositioningStrategy positioningStrategy, int i, Object obj) {
            if ((i & 2) != 0) {
                positioningStrategy = PositioningStrategies.DEFAULT;
            }
            return companion.create(languageFeature, positioningStrategy);
        }

        @JvmStatic
        public final <E extends PsiElement, A> DiagnosticFactoryForDeprecation1<E, A> create(LanguageFeature featureForError, PositioningStrategy<? super E> positioningStrategy) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
            DiagnosticFactory1 diagnosticFactory1Create = DiagnosticFactory1.create(Severity.WARNING, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create, "create(...)");
            DiagnosticFactory1 diagnosticFactory1Create2 = DiagnosticFactory1.create(Severity.ERROR, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory1Create2, "create(...)");
            return new DiagnosticFactoryForDeprecation1<>(featureForError, diagnosticFactory1Create, diagnosticFactory1Create2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiagnosticFactoryForDeprecation1(LanguageFeature featureForError, DiagnosticFactory1<E, A> warningFactory, DiagnosticFactory1<E, A> errorFactory) {
        super(featureForError, warningFactory, errorFactory, null);
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(warningFactory, "warningFactory");
        Intrinsics.checkNotNullParameter(errorFactory, "errorFactory");
    }

    public final ParametrizedDiagnostic<E> on(LanguageVersionSettings languageVersionSettings, E element, A a) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(a, "a");
        ParametrizedDiagnostic<E> parametrizedDiagnosticOn = chooseFactory(languageVersionSettings).on(element, a);
        Intrinsics.checkNotNullExpressionValue(parametrizedDiagnosticOn, "on(...)");
        return parametrizedDiagnosticOn;
    }
}
