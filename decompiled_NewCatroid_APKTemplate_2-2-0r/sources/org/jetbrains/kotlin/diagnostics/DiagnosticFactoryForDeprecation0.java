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
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u0012*\b\b\u0000\u0010\u0001*\u00020\u00022 \u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00050\u0003:\u0001\u0012B)\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\nJ!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000¢\u0006\u0002\u0010\u000fJ\u0019\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u000e\u001a\u00028\u0000¢\u0006\u0002\u0010\u0011¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation0;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation;", "Lorg/jetbrains/kotlin/diagnostics/SimpleDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;)V", "on", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/diagnostics/SimpleDiagnostic;", "onError", "(Lcom/intellij/psi/PsiElement;)Lorg/jetbrains/kotlin/diagnostics/SimpleDiagnostic;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticFactoryForDeprecation0<E extends PsiElement> extends DiagnosticFactoryForDeprecation<E, SimpleDiagnostic<E>, DiagnosticFactory0<E>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final <E extends PsiElement> DiagnosticFactoryForDeprecation0<E> create(LanguageFeature languageFeature) {
        return INSTANCE.create(languageFeature);
    }

    @JvmStatic
    public static final <E extends PsiElement> DiagnosticFactoryForDeprecation0<E> create(LanguageFeature languageFeature, PositioningStrategy<? super E> positioningStrategy) {
        return INSTANCE.create(languageFeature, positioningStrategy);
    }

    /* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0001\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\nH\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation0$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation0;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "positioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final <E extends PsiElement> DiagnosticFactoryForDeprecation0<E> create(LanguageFeature featureForError) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            return create$default(this, featureForError, null, 2, null);
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DiagnosticFactoryForDeprecation0 create$default(Companion companion, LanguageFeature languageFeature, PositioningStrategy positioningStrategy, int i, Object obj) {
            if ((i & 2) != 0) {
                positioningStrategy = PositioningStrategies.DEFAULT;
            }
            return companion.create(languageFeature, positioningStrategy);
        }

        @JvmStatic
        public final <E extends PsiElement> DiagnosticFactoryForDeprecation0<E> create(LanguageFeature featureForError, PositioningStrategy<? super E> positioningStrategy) {
            Intrinsics.checkNotNullParameter(featureForError, "featureForError");
            Intrinsics.checkNotNullParameter(positioningStrategy, "positioningStrategy");
            DiagnosticFactory0 diagnosticFactory0Create = DiagnosticFactory0.create(Severity.WARNING, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create, "create(...)");
            DiagnosticFactory0 diagnosticFactory0Create2 = DiagnosticFactory0.create(Severity.ERROR, positioningStrategy);
            Intrinsics.checkNotNullExpressionValue(diagnosticFactory0Create2, "create(...)");
            return new DiagnosticFactoryForDeprecation0<>(featureForError, diagnosticFactory0Create, diagnosticFactory0Create2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiagnosticFactoryForDeprecation0(LanguageFeature featureForError, DiagnosticFactory0<E> warningFactory, DiagnosticFactory0<E> errorFactory) {
        super(featureForError, warningFactory, errorFactory, null);
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(warningFactory, "warningFactory");
        Intrinsics.checkNotNullParameter(errorFactory, "errorFactory");
    }

    public final SimpleDiagnostic<E> on(LanguageVersionSettings languageVersionSettings, E element) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(element, "element");
        SimpleDiagnostic<E> simpleDiagnosticOn = chooseFactory(languageVersionSettings).on(element);
        Intrinsics.checkNotNullExpressionValue(simpleDiagnosticOn, "on(...)");
        return simpleDiagnosticOn;
    }

    public final SimpleDiagnostic<E> onError(E element) {
        Intrinsics.checkNotNullParameter(element, "element");
        SimpleDiagnostic<E> simpleDiagnosticOn = getErrorFactory().on(element);
        Intrinsics.checkNotNullExpressionValue(simpleDiagnosticOn, "on(...)");
        return simpleDiagnosticOn;
    }
}
