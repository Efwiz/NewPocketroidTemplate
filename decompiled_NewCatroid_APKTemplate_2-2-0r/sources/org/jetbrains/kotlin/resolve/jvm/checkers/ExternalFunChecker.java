package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: ExternalFunChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/ExternalFunChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExternalFunChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        String str;
        PsiElement modifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).isExternal()) {
            BindingTrace trace = context.getTrace();
            if (!(descriptor instanceof FunctionDescriptor)) {
                if (descriptor instanceof PropertyDescriptor) {
                    str = "property";
                } else {
                    str = descriptor instanceof ClassDescriptor ? "class" : "non-function declaration";
                }
                KtModifierList modifierList = declaration.getModifierList();
                if (modifierList == null || (modifier = modifierList.getModifier(KtTokens.EXTERNAL_KEYWORD)) == null) {
                    return;
                }
                trace.report(Errors.WRONG_MODIFIER_TARGET.on(modifier, KtTokens.EXTERNAL_KEYWORD, str));
                return;
            }
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            if (DescriptorUtils.isInterface(functionDescriptor.getContainingModule())) {
                trace.report(ErrorsJvm.EXTERNAL_DECLARATION_IN_INTERFACE.on(declaration));
            } else if (functionDescriptor.getModality() == Modality.ABSTRACT) {
                if (declaration instanceof KtPropertyAccessor) {
                    trace.report(ErrorsJvm.EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT.on(((KtPropertyAccessor) declaration).getProperty()));
                } else {
                    trace.report(ErrorsJvm.EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT.on(declaration));
                }
            }
            if (!(descriptor instanceof ConstructorDescriptor) && (declaration instanceof KtDeclarationWithBody) && ((KtDeclarationWithBody) declaration).hasBody()) {
                trace.report(ErrorsJvm.EXTERNAL_DECLARATION_CANNOT_HAVE_BODY.on(declaration));
            }
            if (InlineUtil.isInline(descriptor)) {
                trace.report(ErrorsJvm.EXTERNAL_DECLARATION_CANNOT_BE_INLINED.on(declaration));
            }
        }
    }
}
