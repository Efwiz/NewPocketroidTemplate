package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.lazy.DelegationFilter;

/* JADX INFO: compiled from: JvmDelegationFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmDelegationFilter;", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "()V", "filter", "", "interfaceMember", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "isBuiltInMemberMappedToJavaDefault", "isJavaDefaultMethod", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDelegationFilter implements DelegationFilter {
    public static final JvmDelegationFilter INSTANCE = new JvmDelegationFilter();

    private JvmDelegationFilter() {
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.DelegationFilter
    public boolean filter(CallableMemberDescriptor interfaceMember, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(interfaceMember, "interfaceMember");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (!languageVersionSettings.supportsFeature(LanguageFeature.NoDelegationToJavaDefaultInterfaceMembers)) {
            return true;
        }
        CallableMemberDescriptor callableMemberDescriptorUnwrapFakeOverride = DescriptorUtils.unwrapFakeOverride(interfaceMember);
        Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorUnwrapFakeOverride, "unwrapFakeOverride(...)");
        return (isJavaDefaultMethod(callableMemberDescriptorUnwrapFakeOverride) || JvmAnnotationUtilKt.hasJvmDefaultAnnotation(callableMemberDescriptorUnwrapFakeOverride) || isBuiltInMemberMappedToJavaDefault(callableMemberDescriptorUnwrapFakeOverride)) ? false : true;
    }

    private final boolean isJavaDefaultMethod(CallableMemberDescriptor interfaceMember) {
        return (interfaceMember instanceof JavaMethodDescriptor) && ((JavaMethodDescriptor) interfaceMember).getModality() != Modality.ABSTRACT;
    }

    private final boolean isBuiltInMemberMappedToJavaDefault(CallableMemberDescriptor interfaceMember) {
        return interfaceMember.getModality() != Modality.ABSTRACT && KotlinBuiltIns.isBuiltIn(interfaceMember) && interfaceMember.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME());
    }
}
