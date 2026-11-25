package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.IdentifierChecker;

/* JADX INFO: compiled from: JsIdentifierChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsIdentifierChecker;", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "checkDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkIdentifier", "simpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIdentifierChecker implements IdentifierChecker {
    private final LanguageVersionSettings languageVersionSettings;

    @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
    public void checkDeclaration(KtDeclaration declaration, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
    }

    public JsIdentifierChecker(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
    public void checkIdentifier(KtSimpleNameExpression simpleNameExpression, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(simpleNameExpression, "simpleNameExpression");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.JsAllowInvalidCharsIdentifiersEscaping)) {
            return;
        }
        List<String> listSplit$default = StringsKt.split$default((CharSequence) simpleNameExpression.getReferencedName(), new char[]{'.'}, false, 0, 6, (Object) null);
        if ((listSplit$default instanceof Collection) && listSplit$default.isEmpty()) {
            return;
        }
        for (String str : listSplit$default) {
            if (!Intrinsics.areEqual(NameSuggestion.INSTANCE.sanitizeName(str), str)) {
                PsiElement identifier = simpleNameExpression.getIdentifier();
                if (identifier == null) {
                    return;
                }
                diagnosticHolder.report(Errors.INVALID_CHARACTERS.on(identifier, "contains illegal characters"));
                return;
            }
        }
    }
}
