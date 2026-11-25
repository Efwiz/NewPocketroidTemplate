package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassValueReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: CustomEnumEntriesMigrationCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0002J\u0018\u0010\u000f\u001a\u00020\f*\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0010\u001a\u00020\rH\u0002J\u0010\u0010\u0011\u001a\u00020\f*\u0006\u0012\u0002\b\u00030\u0006H\u0002J\u001c\u0010\u0012\u001a\u00020\f*\u00020\r2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CustomEnumEntriesMigrationCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "isCallToExternalEntriesInsideEnum", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "contextExpression", "isCallViaCompanionOnEnumClassQualifier", "descriptor", "isExtensionWithEnumClassQualifier", "isReferenceToMemberEntriesWithoutExpectedFunctionalType", "expression", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomEnumEntriesMigrationCallChecker implements CallChecker {
    public static final CustomEnumEntriesMigrationCallChecker INSTANCE = new CustomEnumEntriesMigrationCallChecker();

    private CustomEnumEntriesMigrationCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) resultingDescriptor;
            if (Intrinsics.areEqual(propertyDescriptor.getShortName(), StandardNames.ENUM_ENTRIES)) {
                if (isExtensionWithEnumClassQualifier(resolvedCall) || isCallViaCompanionOnEnumClassQualifier(resolvedCall, propertyDescriptor)) {
                    context.getTrace().report(Errors.DEPRECATED_ACCESS_TO_ENUM_ENTRY_COMPANION_PROPERTY.on(reportOn));
                    return;
                }
                if (isCallToExternalEntriesInsideEnum(propertyDescriptor, reportOn)) {
                    Collection<Diagnostic> collectionForElement = context.getTrace().getContext().getDiagnostics().forElement(reportOn);
                    if (!(collectionForElement instanceof Collection) || !collectionForElement.isEmpty()) {
                        Iterator<T> it = collectionForElement.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(((Diagnostic) it.next()).getFactory(), Errors.DEPRECATED_ACCESS_TO_ENTRY_PROPERTY_FROM_ENUM)) {
                                return;
                            }
                        }
                    }
                    context.getTrace().report(Errors.DEPRECATED_ACCESS_TO_ENTRY_PROPERTY_FROM_ENUM.on(reportOn));
                    return;
                }
                if (isReferenceToMemberEntriesWithoutExpectedFunctionalType(propertyDescriptor, reportOn, context)) {
                    context.getTrace().report(Errors.DEPRECATED_ACCESS_TO_ENUM_ENTRY_PROPERTY_AS_REFERENCE.on(reportOn));
                }
            }
        }
    }

    private final boolean isExtensionWithEnumClassQualifier(ResolvedCall<?> resolvedCall) {
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        return extensionReceiver != null && (extensionReceiver instanceof ClassValueReceiver) && DescriptorUtils.isEnumClass(((ClassValueReceiver) extensionReceiver).getClassQualifier().getDescriptor());
    }

    private final boolean isCallViaCompanionOnEnumClassQualifier(ResolvedCall<?> resolvedCall, PropertyDescriptor propertyDescriptor) {
        DeclarationDescriptor declarationDescriptor;
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (DescriptorUtilsKt.isCompanionObject(containingDeclaration) && (declarationDescriptor = containingDeclaration.getContainingModule()) != null && (declarationDescriptor instanceof ClassDescriptor) && DescriptorUtils.isEnumClass(declarationDescriptor)) {
            return EnumEntryVsCompanionPriorityCallCheckerKt.isQualifierFor(resolvedCall.getDispatchReceiver(), (ClassDescriptor) declarationDescriptor);
        }
        return false;
    }

    private final boolean isCallToExternalEntriesInsideEnum(PropertyDescriptor propertyDescriptor, PsiElement psiElement) {
        PsiElement myParent = psiElement.getLightParent();
        if (!DescriptorUtils.isEnumClass(propertyDescriptor.getContainingModule()) && (!(myParent instanceof KtDotQualifiedExpression) || ((KtDotQualifiedExpression) myParent).getReceiverExpression() == psiElement)) {
            for (PsiElement psiElement2 : PsiUtilsKt.getParentsWithSelf(psiElement)) {
                if ((psiElement2 instanceof KtClass) && ((KtClass) psiElement2).isEnum()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isReferenceToMemberEntriesWithoutExpectedFunctionalType(PropertyDescriptor propertyDescriptor, PsiElement psiElement, CallCheckerContext callCheckerContext) {
        KotlinType expectedType = callCheckerContext.getResolutionContext().expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        return (psiElement.getLightParent() instanceof KtCallableReferenceExpression) && DescriptorUtils.isEnumClass(propertyDescriptor.getContainingModule()) && (TypeUtils.noExpectedType(expectedType) || !FunctionTypesKt.isFunctionType(expectedType));
    }
}
