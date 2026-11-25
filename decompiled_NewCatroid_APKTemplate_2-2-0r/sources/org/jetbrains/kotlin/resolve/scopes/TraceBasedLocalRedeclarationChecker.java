package org.jetbrains.kotlin.resolve.scopes;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.OverloadChecker;

/* JADX INFO: compiled from: LocalRedeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0014J\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u000fH\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/TraceBasedLocalRedeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/scopes/AbstractLocalRedeclarationChecker;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "overloadChecker", "Lorg/jetbrains/kotlin/resolve/OverloadChecker;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/OverloadChecker;)V", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "handleConflictingOverloads", "", "first", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "second", "handleRedeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TraceBasedLocalRedeclarationChecker extends AbstractLocalRedeclarationChecker {
    private final BindingTrace trace;

    public final BindingTrace getTrace() {
        return this.trace;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TraceBasedLocalRedeclarationChecker(BindingTrace trace, OverloadChecker overloadChecker) {
        super(overloadChecker);
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(overloadChecker, "overloadChecker");
        this.trace = trace;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.AbstractLocalRedeclarationChecker
    protected void handleRedeclaration(DeclarationDescriptor first, DeclarationDescriptor second) {
        Unit unit;
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        BindingTrace bindingTrace = this.trace;
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(first);
        Unit unit2 = null;
        if (psiElementDescriptorToDeclaration != null) {
            Diagnostic diagnosticOn = Errors.REDECLARATION.on(psiElementDescriptorToDeclaration, CollectionsKt.listOf((Object[]) new DeclarationDescriptor[]{first, second}));
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            bindingTrace.report(diagnosticOn);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit != null) {
            BindingTrace bindingTrace2 = this.trace;
            PsiElement psiElementDescriptorToDeclaration2 = DescriptorToSourceUtils.descriptorToDeclaration(second);
            if (psiElementDescriptorToDeclaration2 != null) {
                Diagnostic diagnosticOn2 = Errors.REDECLARATION.on(psiElementDescriptorToDeclaration2, CollectionsKt.listOf((Object[]) new DeclarationDescriptor[]{first, second}));
                Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                bindingTrace2.report(diagnosticOn2);
                unit2 = Unit.INSTANCE;
            }
            if (unit2 == null) {
                throw new AssertionError("No declaration for " + second);
            }
            return;
        }
        throw new AssertionError("No declaration for " + first);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.AbstractLocalRedeclarationChecker
    protected void handleConflictingOverloads(CallableMemberDescriptor first, CallableMemberDescriptor second) {
        Unit unit;
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        BindingTrace bindingTrace = this.trace;
        CallableMemberDescriptor callableMemberDescriptor = first;
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor);
        Unit unit2 = null;
        if (psiElementDescriptorToDeclaration != null) {
            Diagnostic diagnosticOn = Errors.CONFLICTING_OVERLOADS.on(psiElementDescriptorToDeclaration, CollectionsKt.listOf((Object[]) new CallableMemberDescriptor[]{first, second}));
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            bindingTrace.report(diagnosticOn);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit != null) {
            BindingTrace bindingTrace2 = this.trace;
            CallableMemberDescriptor callableMemberDescriptor2 = second;
            PsiElement psiElementDescriptorToDeclaration2 = DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor2);
            if (psiElementDescriptorToDeclaration2 != null) {
                Diagnostic diagnosticOn2 = Errors.CONFLICTING_OVERLOADS.on(psiElementDescriptorToDeclaration2, CollectionsKt.listOf((Object[]) new CallableMemberDescriptor[]{first, second}));
                Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                bindingTrace2.report(diagnosticOn2);
                unit2 = Unit.INSTANCE;
            }
            if (unit2 == null) {
                throw new AssertionError("No declaration for " + callableMemberDescriptor2);
            }
            return;
        }
        throw new AssertionError("No declaration for " + callableMemberDescriptor);
    }
}
