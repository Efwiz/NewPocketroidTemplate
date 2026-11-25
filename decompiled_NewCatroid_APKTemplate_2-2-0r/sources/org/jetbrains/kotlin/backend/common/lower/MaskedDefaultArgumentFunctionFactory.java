package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.descriptors.DescriptorUtilsKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: DefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0004¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/MaskedDefaultArgumentFunctionFactory;", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "generateDefaultArgumentStubFrom", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "original", "useConstructorMarker", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class MaskedDefaultArgumentFunctionFactory extends DefaultArgumentFunctionFactory {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MaskedDefaultArgumentFunctionFactory(CommonBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentFunctionFactory
    protected final void generateDefaultArgumentStubFrom(IrFunction irFunction, IrFunction original, boolean z) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(original, "original");
        copyAttributesFrom(irFunction, original);
        IrUtilsKt.copyTypeParametersFrom$default(irFunction, original, null, null, 6, null);
        copyReturnTypeFrom(irFunction, original);
        copyReceiversFrom(irFunction, original);
        copyValueParametersFrom(irFunction, original);
        int size = (original.getValueParameters().size() + 31) / 32;
        for (int i = 0; i < size; i++) {
            DeclarationBuildersKt.addValueParameter(irFunction, DescriptorUtilsKt.getSynthesizedString("mask" + i), getContext().getIrBuiltIns().getIntType(), IrDeclarationOrigin.MASK_FOR_DEFAULT_FUNCTION.INSTANCE);
        }
        if (z) {
            DeclarationBuildersKt.addValueParameter(irFunction, DescriptorUtilsKt.getSynthesizedString("marker"), IrTypesKt.makeNullable(IrTypesKt.getDefaultType(getContext().getIr().getSymbols().getDefaultConstructorMarker())), IrDeclarationOrigin.DEFAULT_CONSTRUCTOR_MARKER.INSTANCE);
        } else if (getContext().getIr().shouldGenerateHandlerParameterForDefaultBodyFun()) {
            DeclarationBuildersKt.addValueParameter(irFunction, DescriptorUtilsKt.getSynthesizedString("handler"), getContext().getIrBuiltIns().getAnyNType(), IrDeclarationOrigin.METHOD_HANDLER_IN_DEFAULT_FUNCTION.INSTANCE);
        }
        getContext().remapMultiFieldValueClassStructure(original, irFunction, MapsKt.toMap(CollectionsKt.zip(IrUtilsKt.getExplicitParameters(original), IrUtilsKt.getExplicitParameters(irFunction))));
    }
}
