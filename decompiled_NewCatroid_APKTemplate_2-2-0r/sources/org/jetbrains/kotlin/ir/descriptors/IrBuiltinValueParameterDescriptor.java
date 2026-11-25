package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: IrBuiltinFunctionDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinValueParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrBuiltinValueParameterDescriptor extends ValueParameterDescriptor {

    /* JADX INFO: compiled from: IrBuiltinFunctionDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean isLateInit(IrBuiltinValueParameterDescriptor irBuiltinValueParameterDescriptor) {
            return ValueParameterDescriptor.DefaultImpls.isLateInit(irBuiltinValueParameterDescriptor);
        }
    }
}
