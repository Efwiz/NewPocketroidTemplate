package org.jetbrains.kotlin.resolve.sam;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: SamConversionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = SamConversionResolverImpl.SamConversionResolverWithoutReceiverConversion.class)
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0006J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "resolveFunctionTypeIfSamInterface", "Lorg/jetbrains/kotlin/types/SimpleType;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Empty", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SamConversionResolver extends PlatformSpecificExtension<SamConversionResolver> {
    SimpleType resolveFunctionTypeIfSamInterface(ClassDescriptor classDescriptor);

    /* JADX INFO: compiled from: SamConversionResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver$Empty;", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "()V", "resolveFunctionTypeIfSamInterface", "Lorg/jetbrains/kotlin/types/SimpleType;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements SamConversionResolver {
        public static final Empty INSTANCE = new Empty();

        @Override // org.jetbrains.kotlin.resolve.sam.SamConversionResolver
        public SimpleType resolveFunctionTypeIfSamInterface(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return null;
        }

        private Empty() {
        }
    }
}
