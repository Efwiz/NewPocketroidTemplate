package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: EnumEntryVsCompanionPriorityCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/EnumEntryVsCompanionPriorityCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "containsEntryWithName", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumEntryVsCompanionPriorityCallChecker implements CallChecker {
    public static final EnumEntryVsCompanionPriorityCallChecker INSTANCE = new EnumEntryVsCompanionPriorityCallChecker();

    private EnumEntryVsCompanionPriorityCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        if (callableDescriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) callableDescriptor;
            Name name2 = propertyDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
                if (classDescriptor.getIsCompanionObject()) {
                    DeclarationDescriptor containingDeclaration2 = classDescriptor.getContainingDeclaration();
                    Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
                    if (containingDeclaration2 instanceof ClassDescriptor) {
                        ClassDescriptor classDescriptor2 = (ClassDescriptor) containingDeclaration2;
                        if (classDescriptor2.getKind() == ClassKind.ENUM_CLASS && containsEntryWithName(classDescriptor2, name2) && EnumEntryVsCompanionPriorityCallCheckerKt.isQualifierFor(resolvedCall.getDispatchReceiver(), classDescriptor2)) {
                            context.getResolutionContext().trace.report(Errors.DEPRECATED_ACCESS_TO_ENUM_COMPANION_PROPERTY.on(reportOn, (PropertyDescriptor) callableDescriptor));
                        }
                    }
                }
            }
        }
    }

    private final boolean containsEntryWithName(ClassDescriptor classDescriptor, Name name2) {
        ClassifierDescriptor contributedClassifier = classDescriptor.getUnsubstitutedMemberScope().mo7465getContributedClassifier(name2, NoLookupLocation.FOR_ALREADY_TRACKED);
        return (contributedClassifier instanceof ClassDescriptor) && ((ClassDescriptor) contributedClassifier).getKind() == ClassKind.ENUM_ENTRY;
    }
}
