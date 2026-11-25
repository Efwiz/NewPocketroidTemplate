package org.jetbrains.kotlin.backend.common.bridges;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;

/* JADX INFO: compiled from: impl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00030\b¨\u0006\t"}, d2 = {"generateBridgesForFunctionDescriptor", "", "Lorg/jetbrains/kotlin/backend/common/bridges/Bridge;", "Signature", "Lorg/jetbrains/kotlin/backend/common/bridges/DescriptorBasedFunctionHandle;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "signature", "Lkotlin/Function1;", "backend-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImplKt {
    public static final <Signature> Set<Bridge<Signature, DescriptorBasedFunctionHandle>> generateBridgesForFunctionDescriptor(FunctionDescriptor descriptor, final Function1<? super FunctionDescriptor, ? extends Signature> signature) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        return BridgesKt.generateBridges(new DescriptorBasedFunctionHandle(descriptor), new Function1<DescriptorBasedFunctionHandle, Signature>() { // from class: org.jetbrains.kotlin.backend.common.bridges.ImplKt.generateBridgesForFunctionDescriptor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Signature invoke(DescriptorBasedFunctionHandle it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return signature.invoke(it.getDescriptor());
            }
        });
    }
}
