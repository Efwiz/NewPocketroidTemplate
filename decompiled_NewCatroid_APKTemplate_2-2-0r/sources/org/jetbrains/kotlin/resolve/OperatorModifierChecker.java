package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.CheckResult;
import org.jetbrains.kotlin.util.OperatorChecks;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: OperatorModifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ(\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OperatorModifierChecker;", "", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkSupportsFeature", ConfigConstants.CONFIG_FEATURE_SECTION, "Lorg/jetbrains/kotlin/config/LanguageFeature;", "modifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OperatorModifierChecker {
    public static final OperatorModifierChecker INSTANCE = new OperatorModifierChecker();

    private OperatorModifierChecker() {
    }

    public final void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DiagnosticSink diagnosticHolder, LanguageVersionSettings languageVersionSettings) {
        KtModifierList modifierList;
        PsiElement modifier;
        String error;
        DiagnosticFactory2<PsiElement, FunctionDescriptor, String> diagnosticFactory2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        FunctionDescriptor functionDescriptor = descriptor instanceof FunctionDescriptor ? (FunctionDescriptor) descriptor : null;
        if (functionDescriptor == null || !functionDescriptor.isOperator() || (modifierList = declaration.getModifierList()) == null || (modifier = modifierList.getModifier(KtTokens.OPERATOR_KEYWORD)) == null) {
            return;
        }
        CheckResult checkResultCheck = OperatorChecks.INSTANCE.check(functionDescriptor);
        if (checkResultCheck.getIsSuccess()) {
            if (((ImmutableSet) OperatorConventions.REM_TO_MOD_OPERATION_NAMES.keySet()).contains(functionDescriptor.getShortName())) {
                checkSupportsFeature(LanguageFeature.OperatorRem, languageVersionSettings, diagnosticHolder, modifier);
            } else if (Intrinsics.areEqual(functionDescriptor.getShortName(), OperatorNameConventions.PROVIDE_DELEGATE)) {
                checkSupportsFeature(LanguageFeature.OperatorProvideDelegate, languageVersionSettings, diagnosticHolder, modifier);
            } else if (DescriptorUtilKt.isTypedEqualsInValueClass(functionDescriptor)) {
                checkSupportsFeature(LanguageFeature.CustomEqualsInValueClasses, languageVersionSettings, diagnosticHolder, modifier);
            }
            if (((ImmutableSet) OperatorConventions.REM_TO_MOD_OPERATION_NAMES.values()).contains(functionDescriptor.getShortName()) && languageVersionSettings.supportsFeature(LanguageFeature.OperatorRem)) {
                if (!KotlinBuiltIns.isUnderKotlinPackage(descriptor) && languageVersionSettings.supportsFeature(LanguageFeature.ProhibitOperatorMod)) {
                    diagnosticFactory2 = Errors.FORBIDDEN_BINARY_MOD;
                } else {
                    diagnosticFactory2 = Errors.DEPRECATED_BINARY_MOD;
                }
                Name name2 = OperatorConventions.REM_TO_MOD_OPERATION_NAMES.inverse().get(functionDescriptor.getShortName());
                Intrinsics.checkNotNull(name2);
                diagnosticHolder.report(diagnosticFactory2.on(modifier, functionDescriptor, name2.asString()));
                return;
            }
            return;
        }
        CheckResult.IllegalSignature illegalSignature = checkResultCheck instanceof CheckResult.IllegalSignature ? (CheckResult.IllegalSignature) checkResultCheck : null;
        if (illegalSignature == null || (error = illegalSignature.getError()) == null) {
            error = "illegal function name";
        }
        diagnosticHolder.report(Errors.INAPPLICABLE_OPERATOR_MODIFIER.on(modifier, error));
    }

    private final void checkSupportsFeature(LanguageFeature feature, LanguageVersionSettings languageVersionSettings, DiagnosticSink diagnosticHolder, PsiElement modifier) {
        if (languageVersionSettings.supportsFeature(feature)) {
            return;
        }
        diagnosticHolder.report(Errors.UNSUPPORTED_FEATURE.on(modifier, TuplesKt.to(feature, languageVersionSettings)));
    }
}
