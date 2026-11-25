package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: UnsupportedUntilRangeDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/UnsupportedUntilRangeDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsupportedUntilRangeDeclarationChecker implements DeclarationChecker {
    public static final UnsupportedUntilRangeDeclarationChecker INSTANCE = new UnsupportedUntilRangeDeclarationChecker();

    private UnsupportedUntilRangeDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtModifierList modifierList;
        PsiElement modifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.RangeUntilOperator) || !(descriptor instanceof FunctionDescriptor)) {
            return;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
        if (!functionDescriptor.isOperator() || !Intrinsics.areEqual(functionDescriptor.getShortName(), OperatorNameConventions.RANGE_UNTIL) || (modifierList = declaration.getModifierList()) == null || (modifier = modifierList.getModifier(KtTokens.OPERATOR_KEYWORD)) == null) {
            return;
        }
        context.getTrace().report(Errors.UNSUPPORTED_FEATURE.on(modifier, TuplesKt.to(LanguageFeature.RangeUntilOperator, context.getLanguageVersionSettings())));
    }
}
