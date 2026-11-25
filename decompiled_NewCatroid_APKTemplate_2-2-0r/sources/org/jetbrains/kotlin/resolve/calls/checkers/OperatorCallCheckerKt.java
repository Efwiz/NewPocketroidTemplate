package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: OperatorCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005\u001a(\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\n\u0010\u000e\u001a\u00020\u000b*\u00020\u0003¨\u0006\u000f"}, d2 = {"checkModConvention", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "modifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "shouldWarnAboutDeprecatedModFromBuiltIns", "", "warnAboutDeprecatedOrForbiddenMod", "reportOn", "isOperatorMod", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OperatorCallCheckerKt {
    public static final boolean isOperatorMod(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        return functionDescriptor.isOperator() && ((ImmutableSet) OperatorConventions.REM_TO_MOD_OPERATION_NAMES.values()).contains(functionDescriptor.getName());
    }

    public static final boolean shouldWarnAboutDeprecatedModFromBuiltIns(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return languageVersionSettings.supportsFeature(LanguageFeature.OperatorRem) && languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_1) >= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkModConvention(FunctionDescriptor functionDescriptor, LanguageVersionSettings languageVersionSettings, DiagnosticSink diagnosticSink, PsiElement psiElement) {
        if (isOperatorMod(functionDescriptor)) {
            if (KotlinBuiltIns.isUnderKotlinPackage(functionDescriptor)) {
                if (shouldWarnAboutDeprecatedModFromBuiltIns(languageVersionSettings)) {
                    warnAboutDeprecatedOrForbiddenMod(functionDescriptor, diagnosticSink, psiElement, languageVersionSettings);
                }
            } else if (languageVersionSettings.supportsFeature(LanguageFeature.OperatorRem)) {
                warnAboutDeprecatedOrForbiddenMod(functionDescriptor, diagnosticSink, psiElement, languageVersionSettings);
            }
        }
    }

    private static final void warnAboutDeprecatedOrForbiddenMod(FunctionDescriptor functionDescriptor, DiagnosticSink diagnosticSink, PsiElement psiElement, LanguageVersionSettings languageVersionSettings) {
        DiagnosticFactory2<PsiElement, FunctionDescriptor, String> diagnosticFactory2;
        if (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitOperatorMod)) {
            diagnosticFactory2 = Errors.FORBIDDEN_BINARY_MOD_AS_REM;
        } else {
            diagnosticFactory2 = Errors.DEPRECATED_BINARY_MOD_AS_REM;
        }
        Name name2 = OperatorConventions.REM_TO_MOD_OPERATION_NAMES.inverse().get(functionDescriptor.getName());
        Intrinsics.checkNotNull(name2);
        diagnosticSink.report(diagnosticFactory2.on(psiElement, functionDescriptor, name2.asString()));
    }
}
