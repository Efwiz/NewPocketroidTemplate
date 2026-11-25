package org.jetbrains.kotlin.load.java.sam;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassConstructorDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.synthetic.SyntheticExtensionsUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JavaBasedSamConversionResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/sam/JavaBasedSamConversionOracle;", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "()V", "isJavaApplicableCandidate", "", "candidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isPossibleSamType", "samType", "Lorg/jetbrains/kotlin/types/KotlinType;", "shouldRunSamConversionForFunction", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaBasedSamConversionOracle implements SamConversionOracle {
    public static final JavaBasedSamConversionOracle INSTANCE = new JavaBasedSamConversionOracle();

    private JavaBasedSamConversionOracle() {
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean shouldRunSamConversionForFunction(CallableDescriptor candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        CallableDescriptor original = candidate.getOriginal();
        FunctionDescriptor functionDescriptor = original instanceof FunctionDescriptor ? (FunctionDescriptor) original : null;
        if (functionDescriptor == null) {
            return false;
        }
        if ((functionDescriptor instanceof TypeAliasConstructorDescriptor) && (((TypeAliasConstructorDescriptor) functionDescriptor).getUnderlyingConstructorDescriptor() instanceof JavaClassConstructorDescriptor)) {
            return true;
        }
        return SyntheticExtensionsUtilsKt.hasJavaOriginInHierarchy(functionDescriptor);
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean isPossibleSamType(KotlinType samType) {
        Intrinsics.checkNotNullParameter(samType, "samType");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = samType.getConstructor().getThis$0();
        return (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && (((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).isFun() || (classifierDescriptorMo7434getDeclarationDescriptor instanceof JavaClassDescriptor));
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean isJavaApplicableCandidate(CallableDescriptor candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return shouldRunSamConversionForFunction(candidate);
    }
}
