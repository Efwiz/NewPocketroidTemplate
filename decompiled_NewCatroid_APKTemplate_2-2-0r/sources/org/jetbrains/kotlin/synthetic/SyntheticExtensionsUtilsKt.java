package org.jetbrains.kotlin.synthetic;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.sam.SamAdapterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: syntheticExtensionsUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\n\u0010\u0006\u001a\u00020\u0005*\u00020\u0007\u001a\u001a\u0010\b\u001a\u00020\u0005\"\b\b\u0000\u0010\t*\u00020\n*\b\u0012\u0004\u0012\u0002H\t0\u000b\u001a\n\u0010\f\u001a\u00020\u0005*\u00020\u0001¨\u0006\r"}, d2 = {"syntheticVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "originalDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "isUsedForExtension", "", "hasJavaOriginInHierarchy", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isResolvedWithSamConversions", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "isVisibleOutside", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SyntheticExtensionsUtilsKt {
    public static final boolean hasJavaOriginInHierarchy(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        if (functionDescriptor.getOriginal().getOverriddenDescriptors().isEmpty()) {
            if ((functionDescriptor instanceof JavaCallableMemberDescriptor) || (functionDescriptor.getContainingDeclaration() instanceof JavaClassDescriptor)) {
                return true;
            }
        } else {
            Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOriginal().getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
            if (!collection.isEmpty()) {
                for (FunctionDescriptor functionDescriptor2 : collection) {
                    Intrinsics.checkNotNull(functionDescriptor2);
                    if (hasJavaOriginInHierarchy(functionDescriptor2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final boolean isVisibleOutside(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<this>");
        return (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PRIVATE) || Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PRIVATE_TO_THIS) || Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.INVISIBLE_FAKE)) ? false : true;
    }

    public static final DescriptorVisibility syntheticVisibility(final DeclarationDescriptorWithVisibility originalDescriptor, final boolean z) {
        Intrinsics.checkNotNullParameter(originalDescriptor, "originalDescriptor");
        final DescriptorVisibility visibility = originalDescriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        if (!Intrinsics.areEqual(visibility, DescriptorVisibilities.PUBLIC)) {
            return new DescriptorVisibility() { // from class: org.jetbrains.kotlin.synthetic.SyntheticExtensionsUtilsKt.syntheticVisibility.1
                @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
                /* JADX INFO: renamed from: mustCheckInImports, reason: collision with other method in class */
                public /* bridge */ /* synthetic */ boolean mo7471mustCheckInImports() {
                    return ((Boolean) mustCheckInImports()).booleanValue();
                }

                @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
                public Visibility getDelegate() {
                    return visibility.getDelegate();
                }

                @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
                public boolean isVisible(ReceiverValue receiver, DeclarationDescriptorWithVisibility what, DeclarationDescriptor from, boolean useSpecialRulesForPrivateSealedConstructors) {
                    Intrinsics.checkNotNullParameter(what, "what");
                    Intrinsics.checkNotNullParameter(from, "from");
                    DescriptorVisibility descriptorVisibility = visibility;
                    if (z) {
                        receiver = DescriptorVisibilities.ALWAYS_SUITABLE_RECEIVER;
                    }
                    return descriptorVisibility.isVisible(receiver, originalDescriptor, from, useSpecialRulesForPrivateSealedConstructors);
                }

                public Void mustCheckInImports() {
                    throw new UnsupportedOperationException("Should never be called for this visibility");
                }

                @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
                public DescriptorVisibility normalize() {
                    return visibility.normalize();
                }

                @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
                public String getInternalDisplayName() {
                    return visibility.getInternalDisplayName() + " for synthetic extension";
                }

                @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
                public String getExternalDisplayName() {
                    return getInternalDisplayName();
                }
            };
        }
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        return PUBLIC;
    }

    public static final <D extends CallableDescriptor> boolean isResolvedWithSamConversions(ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return ((resolvedCall instanceof NewResolvedCallImpl) && !((NewResolvedCallImpl) resolvedCall).getResolvedCallAtom().getArgumentsWithConversion().isEmpty()) || (resolvedCall.getResultingDescriptor() instanceof SamAdapterDescriptor) || (resolvedCall.getResultingDescriptor() instanceof SamConstructorDescriptor) || (resolvedCall.getResultingDescriptor() instanceof SamAdapterExtensionFunctionDescriptor);
    }
}
