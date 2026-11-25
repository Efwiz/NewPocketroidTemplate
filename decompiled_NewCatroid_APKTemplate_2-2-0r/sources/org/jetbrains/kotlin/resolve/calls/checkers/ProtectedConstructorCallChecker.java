package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: ProtectedConstructorCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/ProtectedConstructorCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProtectedConstructorCallChecker implements CallChecker {
    public static final ProtectedConstructorCallChecker INSTANCE = new ProtectedConstructorCallChecker();

    private ProtectedConstructorCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ClassConstructorDescriptor underlyingConstructorDescriptor;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        ConstructorDescriptor constructorDescriptor = resultingDescriptor instanceof ConstructorDescriptor ? (ConstructorDescriptor) resultingDescriptor : null;
        if (constructorDescriptor == null) {
            return;
        }
        ClassifierDescriptor original = constructorDescriptor.getContainingModule().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        DeclarationDescriptor ownerDescriptor = context.getScope().getOwnerDescriptor();
        TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = constructorDescriptor instanceof TypeAliasConstructorDescriptor ? (TypeAliasConstructorDescriptor) constructorDescriptor : null;
        ClassConstructorDescriptor classConstructorDescriptor = (typeAliasConstructorDescriptor == null || (underlyingConstructorDescriptor = typeAliasConstructorDescriptor.getUnderlyingConstructorDescriptor()) == null) ? constructorDescriptor : underlyingConstructorDescriptor;
        if (Intrinsics.areEqual(classConstructorDescriptor.getVisibilityImpl().normalize(), DescriptorVisibilities.PROTECTED)) {
            ConstructorDescriptor constructorDescriptor2 = constructorDescriptor;
            if (DescriptorVisibilityUtils.isVisibleWithAnyReceiver(constructorDescriptor2, ownerDescriptor, context.getLanguageVersionSettings())) {
                KtExpression calleeExpression = resolvedCall.getCall().getCalleeExpression();
                if (calleeExpression instanceof KtConstructorCalleeExpression) {
                    if (((KtConstructorCalleeExpression) calleeExpression).getLightParent() instanceof KtSuperTypeCallEntry) {
                        return;
                    }
                } else if (calleeExpression instanceof KtConstructorDelegationReferenceExpression) {
                    return;
                }
                Iterator<DeclarationDescriptor> it = DescriptorUtilsKt.getParentsWithSelf(ownerDescriptor).iterator();
                while (it.hasNext()) {
                    if (it.next().getOriginal() == original) {
                        return;
                    }
                }
                if (Intrinsics.areEqual(DescriptorVisibilityUtils.findInvisibleMember(DescriptorVisibilities.FALSE_IF_PROTECTED, constructorDescriptor2, ownerDescriptor, context.getLanguageVersionSettings()), classConstructorDescriptor.getOriginal())) {
                    context.getTrace().report(Errors.PROTECTED_CONSTRUCTOR_NOT_IN_SUPER_CALL.on(reportOn, constructorDescriptor));
                }
            }
        }
    }
}
