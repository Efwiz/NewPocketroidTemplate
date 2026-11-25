package org.jetbrains.kotlin.resolve.sam;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SamConversionOracle.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracleDefault;", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "()V", "isJavaApplicableCandidate", "", "candidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isPossibleSamType", "samType", "Lorg/jetbrains/kotlin/types/KotlinType;", "shouldRunSamConversionForFunction", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamConversionOracleDefault implements SamConversionOracle {
    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean isJavaApplicableCandidate(CallableDescriptor candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean shouldRunSamConversionForFunction(CallableDescriptor candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean isPossibleSamType(KotlinType samType) {
        Intrinsics.checkNotNullParameter(samType, "samType");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = samType.getConstructor().mo7434getDeclarationDescriptor();
        return (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && ((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).isFun();
    }
}
