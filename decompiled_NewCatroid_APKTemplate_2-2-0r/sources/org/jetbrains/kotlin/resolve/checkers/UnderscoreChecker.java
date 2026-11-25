package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.FunctionExpressionDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;

/* JADX INFO: compiled from: UnderscoreChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J,\u0010\u000b\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007J*\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/UnderscoreChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkIdentifier", "identifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "allowSingleUnderscore", "", "checkNamed", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnderscoreChecker implements DeclarationChecker {
    public static final UnderscoreChecker INSTANCE = new UnderscoreChecker();

    public final void checkIdentifier(PsiElement psiElement, DiagnosticSink diagnosticHolder, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        checkIdentifier$default(this, psiElement, diagnosticHolder, languageVersionSettings, false, 8, null);
    }

    public final void checkNamed(KtNamedDeclaration declaration, DiagnosticSink diagnosticHolder, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        checkNamed$default(this, declaration, diagnosticHolder, languageVersionSettings, false, 8, null);
    }

    private UnderscoreChecker() {
    }

    public static /* synthetic */ void checkIdentifier$default(UnderscoreChecker underscoreChecker, PsiElement psiElement, DiagnosticSink diagnosticSink, LanguageVersionSettings languageVersionSettings, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        underscoreChecker.checkIdentifier(psiElement, diagnosticSink, languageVersionSettings, z);
    }

    public final void checkIdentifier(PsiElement identifier, DiagnosticSink diagnosticHolder, LanguageVersionSettings languageVersionSettings, boolean allowSingleUnderscore) {
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (identifier != null) {
            String text = identifier.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            if (text.length() == 0) {
                return;
            }
            int i = 0;
            boolean z = allowSingleUnderscore && Intrinsics.areEqual(identifier.getText(), "_");
            if (!z) {
                String text2 = identifier.getText();
                Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
                String str = text2;
                while (true) {
                    if (i >= str.length()) {
                        diagnosticHolder.report(Errors.UNDERSCORE_IS_RESERVED.on(identifier));
                        return;
                    } else if (str.charAt(i) != '_') {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            if (!z || languageVersionSettings.supportsFeature(LanguageFeature.SingleUnderscoreForParameterName)) {
                return;
            }
            diagnosticHolder.report(Errors.UNSUPPORTED_FEATURE.on(identifier, TuplesKt.to(LanguageFeature.SingleUnderscoreForParameterName, languageVersionSettings)));
        }
    }

    public static /* synthetic */ void checkNamed$default(UnderscoreChecker underscoreChecker, KtNamedDeclaration ktNamedDeclaration, DiagnosticSink diagnosticSink, LanguageVersionSettings languageVersionSettings, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        underscoreChecker.checkNamed(ktNamedDeclaration, diagnosticSink, languageVersionSettings, z);
    }

    public final void checkNamed(KtNamedDeclaration declaration, DiagnosticSink diagnosticHolder, LanguageVersionSettings languageVersionSettings, boolean allowSingleUnderscore) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        checkIdentifier(declaration.mo7392getNameIdentifier(), diagnosticHolder, languageVersionSettings, allowSingleUnderscore);
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(declaration instanceof KtProperty) || (descriptor instanceof VariableDescriptor)) {
            if (declaration instanceof KtCallableDeclaration) {
                for (KtParameter ktParameter : ((KtCallableDeclaration) declaration).getValueParameters()) {
                    Intrinsics.checkNotNull(ktParameter);
                    checkNamed(ktParameter, context.getTrace(), context.getLanguageVersionSettings(), descriptor instanceof FunctionExpressionDescriptor);
                }
            }
            if (declaration instanceof KtTypeParameterListOwner) {
                for (KtTypeParameter ktTypeParameter : ((KtTypeParameterListOwner) declaration).getTypeParameters()) {
                    Intrinsics.checkNotNull(ktTypeParameter);
                    checkNamed$default(this, ktTypeParameter, context.getTrace(), context.getLanguageVersionSettings(), false, 8, null);
                }
            }
            if (declaration instanceof KtNamedDeclaration) {
                checkNamed$default(this, (KtNamedDeclaration) declaration, context.getTrace(), context.getLanguageVersionSettings(), false, 8, null);
            }
        }
    }
}
