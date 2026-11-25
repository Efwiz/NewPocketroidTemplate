package org.jetbrains.kotlin.resolve.inline;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: inlineOnly.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0002\u001a\f\u0010\u0007\u001a\u00020\u0005*\u00020\u0006H\u0002\u001a\n\u0010\b\u001a\u00020\u0005*\u00020\t\u001a\n\u0010\n\u001a\u00020\u0005*\u00020\t\u001a\n\u0010\u000b\u001a\u00020\u0005*\u00020\t\u001a\n\u0010\f\u001a\u00020\u0005*\u00020\t\u001a\f\u0010\r\u001a\u00020\u0005*\u00020\tH\u0002\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u000e"}, d2 = {"INLINE_ONLY_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getINLINE_ONLY_ANNOTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "hasInlineOnlyAnnotation", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "hasReifiedParameters", "isEffectivelyInlineOnly", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "isInlineOnly", "isInlineOnlyPrivateInBytecode", "isInlineWithReified", "isPrivateInlineSuspend", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineOnlyKt {
    private static final FqName INLINE_ONLY_ANNOTATION_FQ_NAME = new FqName("kotlin.internal.InlineOnly");

    public static final FqName getINLINE_ONLY_ANNOTATION_FQ_NAME() {
        return INLINE_ONLY_ANNOTATION_FQ_NAME;
    }

    public static final boolean isEffectivelyInlineOnly(MemberDescriptor memberDescriptor) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        return isInlineWithReified(memberDescriptor) || isInlineOnlyPrivateInBytecode(memberDescriptor);
    }

    public static final boolean isInlineOnlyPrivateInBytecode(MemberDescriptor memberDescriptor) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        return isInlineOnly(memberDescriptor) || isPrivateInlineSuspend(memberDescriptor);
    }

    public static final boolean isInlineOnly(MemberDescriptor memberDescriptor) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        if ((memberDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) memberDescriptor).isInline()) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptor;
            if (!hasInlineOnlyAnnotation(callableMemberDescriptor)) {
                CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(callableMemberDescriptor);
                Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
                if (hasInlineOnlyAnnotation(directMember)) {
                }
            }
            return true;
        }
        return false;
    }

    private static final boolean isPrivateInlineSuspend(MemberDescriptor memberDescriptor) {
        if (memberDescriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) memberDescriptor;
            if (functionDescriptor.isSuspend() && functionDescriptor.isInline() && Intrinsics.areEqual(functionDescriptor.getVisibility(), DescriptorVisibilities.PRIVATE)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isInlineWithReified(MemberDescriptor memberDescriptor) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        if (memberDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptor;
            if (!hasReifiedParameters(callableMemberDescriptor)) {
                CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(callableMemberDescriptor);
                Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
                if (hasReifiedParameters(directMember)) {
                }
            }
            return true;
        }
        return false;
    }

    private static final boolean hasReifiedParameters(CallableMemberDescriptor callableMemberDescriptor) {
        List<TypeParameterDescriptor> typeParameters = callableMemberDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((TypeParameterDescriptor) it.next()).isReified()) {
                return true;
            }
        }
        return false;
    }

    private static final boolean hasInlineOnlyAnnotation(CallableMemberDescriptor callableMemberDescriptor) {
        return callableMemberDescriptor.getAnnotations().hasAnnotation(INLINE_ONLY_ANNOTATION_FQ_NAME);
    }
}
