package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractKtDiagnosticFactory;

/* JADX INFO: compiled from: KtDiagnosticFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001f\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0000¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000e\u0010\f\u0082\u0001\u0005\u000f\u0010\u0011\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation;", "F", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "", "deprecatingFeature", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;)V", "getDeprecatingFeature", "()Lorg/jetbrains/kotlin/config/LanguageFeature;", "getErrorFactory", "()Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "getWarningFactory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation3;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtDiagnosticFactoryForDeprecation<F extends AbstractKtDiagnosticFactory> {
    private final LanguageFeature deprecatingFeature;
    private final F errorFactory;
    private final F warningFactory;

    public /* synthetic */ KtDiagnosticFactoryForDeprecation(LanguageFeature languageFeature, AbstractKtDiagnosticFactory abstractKtDiagnosticFactory, AbstractKtDiagnosticFactory abstractKtDiagnosticFactory2, DefaultConstructorMarker defaultConstructorMarker) {
        this(languageFeature, abstractKtDiagnosticFactory, abstractKtDiagnosticFactory2);
    }

    private KtDiagnosticFactoryForDeprecation(LanguageFeature languageFeature, F f, F f2) {
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
}
