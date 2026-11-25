package org.jetbrains.kotlin.resolve.sam;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.storage.CacheWithNullableValues;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: SamConversionResolverImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u000eB\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0012\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00020\nH\u0016R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolverImpl;", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "samWithReceiverResolvers", "", "Lorg/jetbrains/kotlin/resolve/sam/SamWithReceiverResolver;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Ljava/lang/Iterable;)V", "functionTypesForSamInterfaces", "Lorg/jetbrains/kotlin/storage/CacheWithNullableValues;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/types/SimpleType;", "resolveFunctionTypeIfSamInterface", "classDescriptor", "SamConversionResolverWithoutReceiverConversion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamConversionResolverImpl implements SamConversionResolver {
    private final CacheWithNullableValues<ClassDescriptor, SimpleType> functionTypesForSamInterfaces;
    private final Iterable<SamWithReceiverResolver> samWithReceiverResolvers;

    /* JADX WARN: Multi-variable type inference failed */
    public SamConversionResolverImpl(StorageManager storageManager, Iterable<? extends SamWithReceiverResolver> samWithReceiverResolvers) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(samWithReceiverResolvers, "samWithReceiverResolvers");
        this.samWithReceiverResolvers = samWithReceiverResolvers;
        this.functionTypesForSamInterfaces = storageManager.createCacheWithNullableValues();
    }

    /* JADX INFO: compiled from: SamConversionResolverImpl.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolverImpl$SamConversionResolverWithoutReceiverConversion;", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/storage/StorageManager;)V", "resolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolverImpl;", "getResolver", "()Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolverImpl;", "resolveFunctionTypeIfSamInterface", "Lorg/jetbrains/kotlin/types/SimpleType;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SamConversionResolverWithoutReceiverConversion implements SamConversionResolver {
        private final SamConversionResolverImpl resolver;

        public SamConversionResolverWithoutReceiverConversion(StorageManager storageManager) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            this.resolver = new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList());
        }

        public final SamConversionResolverImpl getResolver() {
            return this.resolver;
        }

        @Override // org.jetbrains.kotlin.resolve.sam.SamConversionResolver
        public SimpleType resolveFunctionTypeIfSamInterface(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return this.resolver.resolveFunctionTypeIfSamInterface(classDescriptor);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionResolver
    public SimpleType resolveFunctionTypeIfSamInterface(final ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return this.functionTypesForSamInterfaces.computeIfAbsent(classDescriptor, new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl.resolveFunctionTypeIfSamInterface.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                FunctionDescriptor singleAbstractMethodOrNull = SamConversionResolverImplKt.getSingleAbstractMethodOrNull(classDescriptor);
                if (singleAbstractMethodOrNull == null) {
                    return null;
                }
                Iterable iterable = this.samWithReceiverResolvers;
                boolean z = false;
                if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                    Iterator it = iterable.iterator();
                    while (it.hasNext()) {
                        if (((SamWithReceiverResolver) it.next()).shouldConvertFirstSamParameterToReceiver(singleAbstractMethodOrNull)) {
                            z = true;
                            break;
                        }
                    }
                }
                return SamConversionResolverImplKt.getFunctionTypeForAbstractMethod(singleAbstractMethodOrNull, z);
            }
        });
    }
}
