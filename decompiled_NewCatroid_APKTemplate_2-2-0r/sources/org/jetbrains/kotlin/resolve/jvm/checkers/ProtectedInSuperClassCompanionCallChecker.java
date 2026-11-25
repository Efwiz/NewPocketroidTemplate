package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: ProtectedInSuperClassCompanionCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/ProtectedInSuperClassCompanionCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProtectedInSuperClassCompanionCallChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor original = resolvedCall.getResultingDescriptor().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if (Intrinsics.areEqual(original.getVisibilityImpl(), DescriptorVisibilities.PROTECTED) && !AnnotationUtilKt.hasJvmStaticAnnotation(original)) {
            DeclarationDescriptor containingDeclaration = original.getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
                if (classDescriptor.getIsCompanionObject()) {
                    DeclarationDescriptor containingDeclaration2 = classDescriptor.getDeclarationDescriptor();
                    ClassDescriptor classDescriptor2 = containingDeclaration2 instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration2 : null;
                    if (classDescriptor2 == null) {
                        return;
                    }
                    Sequence sequenceFilter = SequencesKt.filter(DescriptorUtilsKt.getParentsWithSelf(context.getScope().getOwnerDescriptor()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.ProtectedInSuperClassCompanionCallChecker$check$$inlined$filterIsInstance$1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(Object obj) {
                            return Boolean.valueOf(obj instanceof ClassDescriptor);
                        }
                    });
                    Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
                    Iterator it = sequenceFilter.iterator();
                    while (it.hasNext()) {
                        if (DescriptorUtils.isSubclass((ClassDescriptor) it.next(), classDescriptor2)) {
                            if (SequencesKt.contains(sequenceFilter, containingDeclaration) || SequencesKt.contains(sequenceFilter, classDescriptor2)) {
                                return;
                            }
                            context.getTrace().report(ErrorsJvm.SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC.on(reportOn));
                            return;
                        }
                    }
                }
            }
        }
    }
}
