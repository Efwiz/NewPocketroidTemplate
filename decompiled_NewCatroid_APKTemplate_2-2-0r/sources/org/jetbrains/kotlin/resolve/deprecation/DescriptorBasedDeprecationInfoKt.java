package org.jetbrains.kotlin.resolve.deprecation;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;

/* JADX INFO: compiled from: DescriptorBasedDeprecationInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"DEPRECATED_FUNCTION_KEY", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "getDEPRECATED_FUNCTION_KEY", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorBasedDeprecationInfoKt {
    private static final CallableDescriptor.UserDataKey<DescriptorBasedDeprecationInfo> DEPRECATED_FUNCTION_KEY = new CallableDescriptor.UserDataKey<DescriptorBasedDeprecationInfo>() { // from class: org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfoKt$DEPRECATED_FUNCTION_KEY$1
    };

    public static final CallableDescriptor.UserDataKey<DescriptorBasedDeprecationInfo> getDEPRECATED_FUNCTION_KEY() {
        return DEPRECATED_FUNCTION_KEY;
    }
}
