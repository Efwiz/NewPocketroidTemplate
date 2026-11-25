package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.load.java.DeprecationCausedByFunctionNInfo;
import org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfoKt;

/* JADX INFO: compiled from: BadInheritedJavaSignaturesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002¨\u0006\u0003"}, d2 = {"findFirstBadJavaSignatureOverridden", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptor", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BadInheritedJavaSignaturesCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final DeclarationDescriptor findFirstBadJavaSignatureOverridden(DeclarationDescriptor declarationDescriptor) {
        Object obj = null;
        if (!(declarationDescriptor instanceof CallableDescriptor)) {
            return null;
        }
        Collection<? extends CallableDescriptor> overriddenDescriptors = ((CallableDescriptor) declarationDescriptor).getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        for (Object obj2 : overriddenDescriptors) {
            if (((CallableDescriptor) obj2).getUserData(DescriptorBasedDeprecationInfoKt.getDEPRECATED_FUNCTION_KEY()) instanceof DeprecationCausedByFunctionNInfo) {
                obj = obj2;
                break;
            }
        }
        return (DeclarationDescriptor) obj;
    }
}
