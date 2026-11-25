package org.jetbrains.kotlin.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryWithPsiElement;

/* JADX INFO: compiled from: DiagnosticFactoryForDeprecation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0004*\u0014\b\u0002\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00062\u00020\u0007B\u001f\b\u0004\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00028\u0002\u0012\u0006\u0010\u000b\u001a\u00028\u0002¢\u0006\u0002\u0010\fJ\u000f\u0010\u0013\u001a\u00028\u0002*\u00020\u0014¢\u0006\u0002\u0010\u0015R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u000b\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\n\u001a\u00028\u0002¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0012\u0010\u0010\u0082\u0001\u0005\u0016\u0017\u0018\u0019\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "D", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "F", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;", "", "deprecatingFeature", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;)V", "getDeprecatingFeature", "()Lorg/jetbrains/kotlin/config/LanguageFeature;", "getErrorFactory", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;", "getWarningFactory", "chooseFactory", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryWithPsiElement;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation0;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation1;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation2;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation3;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation4;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DiagnosticFactoryForDeprecation<E extends PsiElement, D extends Diagnostic, F extends DiagnosticFactoryWithPsiElement<E, D>> {
    private final LanguageFeature deprecatingFeature;
    private final F errorFactory;
    private final F warningFactory;

    public /* synthetic */ DiagnosticFactoryForDeprecation(LanguageFeature languageFeature, DiagnosticFactoryWithPsiElement diagnosticFactoryWithPsiElement, DiagnosticFactoryWithPsiElement diagnosticFactoryWithPsiElement2, DefaultConstructorMarker defaultConstructorMarker) {
        this(languageFeature, diagnosticFactoryWithPsiElement, diagnosticFactoryWithPsiElement2);
    }

    private DiagnosticFactoryForDeprecation(LanguageFeature languageFeature, F f, F f2) {
        this.deprecatingFeature = languageFeature;
        this.warningFactory = f;
        this.errorFactory = f2;
    }

    public final LanguageFeature getDeprecatingFeature() {
        return this.deprecatingFeature;
    }

    public final F getWarningFactory() {
        return this.warningFactory;
    }

    public final F getErrorFactory() {
        return this.errorFactory;
    }

    public final F chooseFactory(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "<this>");
        return languageVersionSettings.supportsFeature(this.deprecatingFeature) ? this.errorFactory : this.warningFactory;
    }
}
