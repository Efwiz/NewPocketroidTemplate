package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtInstanceExpressionWithLabel;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;

/* JADX INFO: compiled from: ConstructorHeaderCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/ConstructorHeaderCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstructorHeaderCallChecker implements CallChecker {
    public static final ConstructorHeaderCallChecker INSTANCE = new ConstructorHeaderCallChecker();

    private ConstructorHeaderCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassDescriptor classDescriptorForImplicitReceiver = ConstructorHeaderCallCheckerKt.getClassDescriptorForImplicitReceiver(resolvedCall.getDispatchReceiver());
        ClassDescriptor classDescriptorForImplicitReceiver2 = ConstructorHeaderCallCheckerKt.getClassDescriptorForImplicitReceiver(resolvedCall.getExtensionReceiver());
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        ClassDescriptor classDescriptor = null;
        if (callElement instanceof KtInstanceExpressionWithLabel) {
            Object obj = context.getTrace().get(BindingContext.REFERENCE_TARGET, ((KtInstanceExpressionWithLabel) callElement).getInstanceReference());
            if (obj instanceof ClassDescriptor) {
                classDescriptor = (ClassDescriptor) obj;
            }
        }
        if (classDescriptorForImplicitReceiver == null && classDescriptorForImplicitReceiver2 == null && classDescriptor == null) {
            return;
        }
        Set of = SetsKt.setOf((Object[]) new ClassDescriptor[]{classDescriptorForImplicitReceiver, classDescriptorForImplicitReceiver2, classDescriptor});
        for (HierarchicalScope hierarchicalScope : ScopeUtilsKt.getParentsWithSelf(context.getScope())) {
            if (hierarchicalScope instanceof LexicalScope) {
                LexicalScope lexicalScope = (LexicalScope) hierarchicalScope;
                if (lexicalScope.getKind() == LexicalScopeKind.CONSTRUCTOR_HEADER) {
                    DeclarationDescriptor ownerDescriptor = lexicalScope.getOwnerDescriptor();
                    Intrinsics.checkNotNull(ownerDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
                    if (of.contains(((ClassConstructorDescriptor) ownerDescriptor).getDeclarationDescriptor())) {
                        context.getTrace().report(Errors.INSTANCE_ACCESS_BEFORE_SUPER_CALL.on(reportOn, resolvedCall.getResultingDescriptor()));
                        return;
                    }
                } else {
                    continue;
                }
            }
        }
    }
}
