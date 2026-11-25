package org.jetbrains.kotlin.load.java.sam;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: JavaBasedSamConversionResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/sam/JavaBasedSamConversionResolver;", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "()V", "resolveFunctionTypeIfSamInterface", "Lorg/jetbrains/kotlin/types/SimpleType;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaBasedSamConversionResolver implements SamConversionResolver {
    public static final JavaBasedSamConversionResolver INSTANCE = new JavaBasedSamConversionResolver();

    private JavaBasedSamConversionResolver() {
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionResolver
    public SimpleType resolveFunctionTypeIfSamInterface(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (classDescriptor instanceof JavaClassDescriptor) {
            return ((JavaClassDescriptor) classDescriptor).getDefaultFunctionTypeForSamInterface();
        }
        return null;
    }
}
