package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrExpressionBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX INFO: compiled from: JvmOverloadsAnnotationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\u0015\u001a\u00020\b*\u00020\u00162\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmOverloadsAnnotationLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "generateWrapper", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "target", "numDefaultParametersToExpect", "", "generateWrappers", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "lower", "generateNewValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "oldFunction", "generateWrapperHeader", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JvmOverloadsAnnotationLowering implements ClassLoweringPass {
    private final JvmBackendContext context;

    public JvmOverloadsAnnotationLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrFunction) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (IrUtilsKt.hasAnnotation((IrFunction) obj2, JvmNames.INSTANCE.getJVM_OVERLOADS_FQ_NAME())) {
                arrayList2.add(obj2);
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            generateWrappers((IrFunction) it.next(), irClass);
        }
    }

    private final void generateWrappers(IrFunction target, IrClass irClass) {
        List<IrValueParameter> valueParameters = target.getValueParameters();
        int i = 0;
        if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                if (((IrValueParameter) it.next()).getDefaultValue() != null && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        while (true) {
            i--;
            if (-1 >= i) {
                return;
            } else {
                IrDeclarationsKt.addMember(irClass, generateWrapper(target, i));
            }
        }
    }

    private final IrFunction generateWrapper(IrFunction target, int numDefaultParametersToExpect) {
        IrCallImpl irCallImplFromSymbolOwner;
        IrExpressionBodyImpl irExpressionBodyImpl;
        int i;
        IrFunction irFunctionGenerateWrapperHeader = generateWrapperHeader(this.context.getIrFactory(), target, numDefaultParametersToExpect);
        boolean z = target instanceof IrConstructor;
        if (z) {
            irCallImplFromSymbolOwner = IrDelegatingConstructorCallImpl.Companion.fromSymbolOwner$default(IrDelegatingConstructorCallImpl.INSTANCE, -1, -1, this.context.getIrBuiltIns().getUnitType(), ((IrConstructor) target).getSymbol(), 0, 0, 48, null);
        } else if (target instanceof IrSimpleFunction) {
            IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
            IrType returnType = target.getReturnType();
            IrSimpleFunctionSymbol symbol = ((IrSimpleFunction) target).getSymbol();
            irCallImplFromSymbolOwner = companion.fromSymbolOwner(-1, -1, returnType, symbol, (240 & 16) != 0 ? symbol.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? symbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        } else {
            throw new IllegalStateException(("unknown function kind: " + RenderIrElementKt.render$default(target, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        for (IrTypeParameter irTypeParameter : IrUtilsKt.getAllTypeParameters(irFunctionGenerateWrapperHeader)) {
            irCallImplFromSymbolOwner.putTypeArgument(irTypeParameter.getIndex(), IrTypesKt.getDefaultType(irTypeParameter));
        }
        IrValueParameter dispatchReceiverParameter = irFunctionGenerateWrapperHeader.getDispatchReceiverParameter();
        irCallImplFromSymbolOwner.setDispatchReceiver(dispatchReceiverParameter != null ? new IrGetValueImpl(-1, -1, dispatchReceiverParameter.getSymbol(), null, 8, null) : null);
        IrValueParameter extensionReceiverParameter = irFunctionGenerateWrapperHeader.getExtensionReceiverParameter();
        irCallImplFromSymbolOwner.setExtensionReceiver(extensionReceiverParameter != null ? new IrGetValueImpl(-1, -1, extensionReceiverParameter.getSymbol(), null, 8, null) : null);
        Iterator<IrValueParameter> it = target.getValueParameters().iterator();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            i2++;
            if (it.next().getDefaultValue() == null) {
                i = i3 + 1;
                irCallImplFromSymbolOwner.putValueArgument(i2, new IrGetValueImpl(-1, -1, irFunctionGenerateWrapperHeader.getValueParameters().get(i3).getSymbol(), null, 8, null));
            } else if (i4 < numDefaultParametersToExpect) {
                i4++;
                i = i3 + 1;
                irCallImplFromSymbolOwner.putValueArgument(i2, new IrGetValueImpl(-1, -1, irFunctionGenerateWrapperHeader.getValueParameters().get(i3).getSymbol(), null, 8, null));
            } else {
                irCallImplFromSymbolOwner.putValueArgument(i2, null);
            }
            i3 = i;
        }
        if (z) {
            irExpressionBodyImpl = new IrBlockBodyImpl(-1, -1, CollectionsKt.listOf(irCallImplFromSymbolOwner));
        } else {
            irExpressionBodyImpl = new IrExpressionBodyImpl(-1, -1, irCallImplFromSymbolOwner);
        }
        irFunctionGenerateWrapperHeader.setBody(irExpressionBodyImpl);
        return irFunctionGenerateWrapperHeader;
    }

    private final IrFunction generateWrapperHeader(IrFactory irFactory, IrFunction irFunction, int i) {
        IrSimpleFunction irSimpleFunctionBuildFunction;
        if (!(irFunction instanceof IrConstructor)) {
            if (irFunction instanceof IrSimpleFunction) {
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.JVM_OVERLOADS_WRAPPER.INSTANCE);
                irFunctionBuilder.setName(irFunction.getName());
                irFunctionBuilder.setVisibility(irFunction.getVisibility());
                irFunctionBuilder.setModality(this.context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.GenerateJvmOverloadsAsFinal) ? Modality.FINAL : ((IrSimpleFunction) irFunction).getModality());
                irFunctionBuilder.setReturnType(irFunction.getReturnType());
                irFunctionBuilder.setInline(irFunction.getIsInline());
                irFunctionBuilder.setSuspend(((IrSimpleFunction) irFunction).getIsSuspend());
                irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            } else {
                throw new IllegalStateException(("Unknown kind of IrFunction: " + irFunction).toString());
            }
        } else {
            IrFunctionBuilder irFunctionBuilder2 = new IrFunctionBuilder();
            irFunctionBuilder2.setOrigin(JvmLoweredDeclarationOrigin.JVM_OVERLOADS_WRAPPER.INSTANCE);
            irFunctionBuilder2.setName(irFunction.getName());
            irFunctionBuilder2.setVisibility(irFunction.getVisibility());
            irFunctionBuilder2.setReturnType(irFunction.getReturnType());
            irFunctionBuilder2.setInline(irFunction.getIsInline());
            irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder2);
        }
        irSimpleFunctionBuildFunction.setParent(irFunction.getParent());
        IrUtilsKt.copyAnnotationsFrom(irSimpleFunctionBuildFunction, irFunction);
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, irFunction, null, null, 6, null);
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(dispatchReceiverParameter != null ? IrUtilsKt.copyTo$default(dispatchReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
        irSimpleFunctionBuildFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? IrUtilsKt.copyTo$default(extensionReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
        irSimpleFunctionBuildFunction.setValueParameters(CollectionsKt.plus((Collection) irSimpleFunctionBuildFunction.getValueParameters(), (Iterable) generateNewValueParameters(irSimpleFunctionBuildFunction, irFunction, i)));
        return irSimpleFunctionBuildFunction;
    }

    private final List<IrValueParameter> generateNewValueParameters(IrFunction irFunction, IrFunction irFunction2, int i) {
        int i2;
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        for (IrValueParameter irValueParameter : irFunction2.getValueParameters()) {
            if (irValueParameter.getDefaultValue() != null && i3 < i) {
                i3++;
                i2 = i4 + 1;
                arrayList.add(IrUtilsKt.copyTo$default(irValueParameter, irFunction, null, i4, 0, 0, null, null, null, null, null, irValueParameter.getIsCrossinline(), irValueParameter.getIsNoinline(), false, 4602, null));
            } else if (irValueParameter.getDefaultValue() == null) {
                i2 = i4 + 1;
                arrayList.add(IrUtilsKt.copyTo$default(irValueParameter, irFunction, null, i4, 0, 0, null, null, null, null, null, false, false, false, 8186, null));
            }
            i4 = i2;
        }
        return arrayList;
    }
}
