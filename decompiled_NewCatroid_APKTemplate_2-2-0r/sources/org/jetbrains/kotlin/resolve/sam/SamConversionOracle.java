package org.jetbrains.kotlin.resolve.sam;

import kotlin.Metadata;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SamConversionOracle.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = SamConversionOracleDefault.class)
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "", "isJavaApplicableCandidate", "", "candidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isPossibleSamType", "samType", "Lorg/jetbrains/kotlin/types/KotlinType;", "shouldRunSamConversionForFunction", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SamConversionOracle {
    boolean isJavaApplicableCandidate(CallableDescriptor candidate);

    boolean isPossibleSamType(KotlinType samType);

    boolean shouldRunSamConversionForFunction(CallableDescriptor candidate);
}
