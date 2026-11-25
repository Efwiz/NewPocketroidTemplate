package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: SuspendInFunInterfaceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/SuspendInFunInterfaceChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspendInFunInterfaceChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        PsiElement funKeyword;
        FunctionDescriptor singleAbstractMethodOrNull;
        KtModifierList modifierList;
        PsiElement modifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((declaration instanceof KtClass) && (descriptor instanceof ClassDescriptor)) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if (classDescriptor.isFun() && (funKeyword = ((KtClass) declaration).getFunKeyword()) != null && (singleAbstractMethodOrNull = SamConversionResolverImplKt.getSingleAbstractMethodOrNull(classDescriptor)) != null && singleAbstractMethodOrNull.isSuspend()) {
                if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.SuspendFunctionsInFunInterfaces) && ((Boolean) context.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getUseIR())).booleanValue()) {
                    return;
                }
                SourceElement source = singleAbstractMethodOrNull.getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                PsiElement psi = PsiSourceElementKt.getPsi(source);
                KtNamedFunction ktNamedFunction = psi instanceof KtNamedFunction ? (KtNamedFunction) psi : null;
                if (ktNamedFunction != null && (modifierList = ktNamedFunction.getModifierList()) != null && (modifier = modifierList.getModifier(KtTokens.SUSPEND_KEYWORD)) != null) {
                    funKeyword = modifier;
                }
                context.getTrace().report(Errors.FUN_INTERFACE_WITH_SUSPEND_FUNCTION.on(funKeyword));
            }
        }
    }
}
