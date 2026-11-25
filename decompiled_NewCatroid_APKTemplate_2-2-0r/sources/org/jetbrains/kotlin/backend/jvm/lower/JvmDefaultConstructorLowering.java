package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JvmDefaultConstructorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmDefaultConstructorLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JvmDefaultConstructorLowering implements ClassLoweringPass {
    private final JvmBackendContext context;

    public JvmDefaultConstructorLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        IrConstructor next;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (irClass.getKind() != ClassKind.CLASS || Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.LOCAL) || irClass.getIsValue() || irClass.getIsInner() || irClass.getModality() == Modality.SEALED) {
            return;
        }
        Iterator<IrConstructor> it = IrUtilsKt.getConstructors(irClass).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!next.getIsPrimary());
        IrConstructor irConstructor = next;
        if (irConstructor == null || DescriptorVisibilities.isPrivate(irConstructor.getVisibility())) {
            return;
        }
        IrConstructor irConstructor2 = this.context.getMultiFieldValueClassReplacements().getOriginalConstructorForConstructorReplacement().get(irConstructor);
        if (irConstructor2 == null) {
            irConstructor2 = irConstructor;
        }
        if (InlineClassAbiKt.hasMangledParameters$default(irConstructor2, false, false, 3, null) || irConstructor.getValueParameters().isEmpty()) {
            return;
        }
        List<IrValueParameter> valueParameters = irConstructor.getValueParameters();
        if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
            Iterator<T> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                if (!IrUtilsKt.hasDefaultValue((IrValueParameter) it2.next())) {
                    return;
                }
            }
        }
        Iterator<IrConstructor> it3 = IrUtilsKt.getConstructors(irClass).iterator();
        while (it3.hasNext()) {
            if (it3.next().getValueParameters().isEmpty()) {
                return;
            }
        }
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setVisibility(irConstructor.getVisibility());
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClass);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, irConstructorBuildConstructor.getSymbol(), irConstructorBuildConstructor.getStartOffset(), irConstructorBuildConstructor.getEndOffset());
        IrUtilsKt.copyAnnotationsFrom(irConstructorBuildConstructor, irConstructor);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset());
        IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder, irConstructor);
        IrDelegatingConstructorCall irDelegatingConstructorCall2 = irDelegatingConstructorCall;
        IrUtilsKt.passTypeArgumentsFrom$default(irDelegatingConstructorCall2, irClass, 0, 2, null);
        IrUtilsKt.passTypeArgumentsFrom(irDelegatingConstructorCall2, irConstructor, irClass.getTypeParameters().size());
        irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
        irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
    }
}
