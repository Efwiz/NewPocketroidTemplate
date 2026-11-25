package org.jetbrains.kotlin.backend.common.ir;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.descriptors.DescriptorUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;

/* JADX INFO: compiled from: IrUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a)\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u001a\u001c\u0010\b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f\u001a0\u0010\r\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015\u001a\f\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\u0017\u001a\n\u0010\u0018\u001a\u00020\u0019*\u00020\u0017\u001a*\u0010\u001a\u001a\u00020\u0019*\u0004\u0018\u00010\u000e2\u0006\u0010\u001b\u001a\u00020\u00192\b\b\u0002\u0010\u001c\u001a\u00020\u00192\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u000f\u001a\n\u0010\u001e\u001a\u00020\u0019*\u00020\u001f\u001a\u0012\u0010 \u001a\u00020\n*\u00020!2\u0006\u0010\u001d\u001a\u00020\u000f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\""}, d2 = {"addDispatchReceiver", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "builder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/builders/declarations/IrValueParameterBuilder;", "", "Lkotlin/ExtensionFunctionType;", "addExtensionReceiver", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "createArrayOfExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "startOffset", "", "endOffset", "arrayElementType", "arrayElements", "", "getAdapteeFromAdaptedForReferenceFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isInlineFunWithReifiedParameter", "", "isPure", "anyVariable", "checkFields", "context", "isUnconditional", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "returnType", "Lorg/jetbrains/kotlin/ir/declarations/IrReturnTarget;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrUtilsKt {
    public static final IrType returnType(IrReturnTarget irReturnTarget, CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(irReturnTarget, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (irReturnTarget instanceof IrConstructor) {
            return context.getIrBuiltIns().getUnitType();
        }
        if (irReturnTarget instanceof IrFunction) {
            return ((IrFunction) irReturnTarget).getReturnType();
        }
        if (irReturnTarget instanceof IrReturnableBlock) {
            return ((IrReturnableBlock) irReturnTarget).getType();
        }
        throw new IllegalStateException(("Unknown ReturnTarget: " + irReturnTarget).toString());
    }

    public static final IrValueParameter addDispatchReceiver(IrSimpleFunction irSimpleFunction, Function1<? super IrValueParameterBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        builder.invoke(irValueParameterBuilder);
        irValueParameterBuilder.setIndex(-1);
        irValueParameterBuilder.setName(DescriptorUtilsKt.getSynthesizedName("this"));
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction);
        irSimpleFunction.setDispatchReceiverParameter(irValueParameterBuildValueParameter);
        return irValueParameterBuildValueParameter;
    }

    public static /* synthetic */ IrValueParameter addExtensionReceiver$default(IrSimpleFunction irSimpleFunction, IrType irType, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            irDeclarationOrigin = IrDeclarationOrigin.DEFINED.INSTANCE;
        }
        return addExtensionReceiver(irSimpleFunction, irType, irDeclarationOrigin);
    }

    public static final IrValueParameter addExtensionReceiver(IrSimpleFunction irSimpleFunction, IrType type, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        irValueParameterBuilder.setType(type);
        irValueParameterBuilder.setOrigin(origin);
        irValueParameterBuilder.setIndex(-1);
        irValueParameterBuilder.setName(DescriptorUtilsKt.getSynthesizedName("receiver"));
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction);
        irSimpleFunction.setExtensionReceiverParameter(irValueParameterBuildValueParameter);
        return irValueParameterBuildValueParameter;
    }

    public static /* synthetic */ boolean isPure$default(IrExpression irExpression, boolean z, boolean z2, CommonBackendContext commonBackendContext, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = true;
        }
        if ((i & 4) != 0) {
            commonBackendContext = null;
        }
        return isPure(irExpression, z, z2, commonBackendContext);
    }

    private static final boolean isPure$isPureImpl(IrExpression irExpression, boolean z, boolean z2, CommonBackendContext commonBackendContext) {
        if (irExpression instanceof IrConst) {
            return true;
        }
        if (irExpression instanceof IrGetValue) {
            if (z) {
                return true;
            }
            IrValueDeclaration owner = ((IrGetValue) irExpression).getSymbol().getOwner();
            if (!(owner instanceof IrVariable) || !((IrVariable) owner).getIsVar()) {
                return true;
            }
        } else if (irExpression instanceof IrTypeOperatorCall) {
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
            if ((irTypeOperatorCall.getOperator() == IrTypeOperator.INSTANCEOF || irTypeOperatorCall.getOperator() == IrTypeOperator.REINTERPRET_CAST || irTypeOperatorCall.getOperator() == IrTypeOperator.NOT_INSTANCEOF) && isPure(irTypeOperatorCall.getArgument(), z, z2, commonBackendContext)) {
                return true;
            }
        } else if (!(irExpression instanceof IrCall)) {
            if (irExpression instanceof IrGetObjectValue) {
                return IrTypePredicatesKt.isUnit(irExpression.getType());
            }
            if (irExpression instanceof IrVararg) {
                List<IrVarargElement> elements = ((IrVararg) irExpression).getElements();
                if ((elements instanceof Collection) && elements.isEmpty()) {
                    return true;
                }
                for (IrVarargElement irVarargElement : elements) {
                    IrExpression irExpression2 = irVarargElement instanceof IrExpression ? (IrExpression) irVarargElement : null;
                    if (irExpression2 == null || !isPure(irExpression2, z, z2, commonBackendContext)) {
                    }
                }
                return true;
            }
        } else if (commonBackendContext != null && commonBackendContext.isSideEffectFree((IrCall) irExpression)) {
            IrCall irCall = (IrCall) irExpression;
            int valueArgumentsCount = irCall.getValueArgumentsCount();
            for (int i = 0; i < valueArgumentsCount; i++) {
                if (!isPure(irCall.getValueArgument(i), z, z2, commonBackendContext)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static final boolean isPure(IrExpression irExpression, boolean z, boolean z2, CommonBackendContext commonBackendContext) {
        if (irExpression == null || isPure$isPureImpl(irExpression, z, z2, commonBackendContext)) {
            return true;
        }
        if (!z2 || !(irExpression instanceof IrGetField)) {
            return false;
        }
        IrGetField irGetField = (IrGetField) irExpression;
        if (irGetField.getSymbol().getOwner().getIsFinal() || z) {
            return isPure$default(irGetField.getReceiver(), z, false, null, 6, null);
        }
        return false;
    }

    public static final IrExpression createArrayOfExpression(CommonBackendContext commonBackendContext, int i, int i2, IrType arrayElementType, List<? extends IrExpression> arrayElements) {
        Intrinsics.checkNotNullParameter(commonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(arrayElementType, "arrayElementType");
        Intrinsics.checkNotNullParameter(arrayElements, "arrayElements");
        IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(commonBackendContext.getIr().getSymbols().getArray(), arrayElementType);
        IrVarargImpl irVarargImpl = new IrVarargImpl(i, i2, irSimpleTypeTypeWith, arrayElementType, arrayElements);
        IrCallImpl irCallImpl = new IrCallImpl(i, i2, irSimpleTypeTypeWith, commonBackendContext.getIr().getSymbols().getArrayOf(), 1, 1, null, null, 192, null);
        irCallImpl.putTypeArgument(0, arrayElementType);
        irCallImpl.putValueArgument(0, irVarargImpl);
        return irCallImpl;
    }

    public static final boolean isInlineFunWithReifiedParameter(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction.getIsInline()) {
            List<IrTypeParameter> typeParameters = irFunction.getTypeParameters();
            if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
                Iterator<T> it = typeParameters.iterator();
                while (it.hasNext()) {
                    if (((IrTypeParameter) it.next()).getIsReified()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final IrFunction getAdapteeFromAdaptedForReferenceFunction(IrFunction irFunction) {
        List<IrStatement> statements;
        IrExpression value;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (!Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE)) {
            return null;
        }
        IrBody body = irFunction.getBody();
        if (body == null || (statements = org.jetbrains.kotlin.ir.util.IrUtilsKt.getStatements(body)) == null || (value = (IrStatement) CollectionsKt.singleOrNull((List) statements)) == null) {
            getAdapteeFromAdaptedForReferenceFunction$unknownStructure(irFunction);
            throw null;
        }
        if (value instanceof IrTypeOperatorCall) {
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) value;
            if (irTypeOperatorCall.getOperator() != IrTypeOperator.IMPLICIT_COERCION_TO_UNIT) {
                getAdapteeFromAdaptedForReferenceFunction$unknownStructure(irFunction);
                throw null;
            }
            value = irTypeOperatorCall.getArgument();
        } else if (value instanceof IrReturn) {
            value = ((IrReturn) value).getValue();
        }
        if (!(value instanceof IrReturnableBlock)) {
            if (!(value instanceof IrFunctionAccessExpression)) {
                getAdapteeFromAdaptedForReferenceFunction$unknownStructure(irFunction);
                throw null;
            }
            return ((IrFunctionAccessExpression) value).getSymbol().getOwner();
        }
        IrFunction inlineFunction = org.jetbrains.kotlin.ir.util.IrInlineUtilsKt.getInlineFunction((IrReturnableBlock) value);
        if (inlineFunction != null) {
            return inlineFunction;
        }
        getAdapteeFromAdaptedForReferenceFunction$unknownStructure(irFunction);
        throw null;
    }

    private static final Void getAdapteeFromAdaptedForReferenceFunction$unknownStructure(IrFunction irFunction) {
        throw new UnsupportedOperationException("Unknown structure of ADAPTER_FOR_CALLABLE_REFERENCE: " + DumpIrTreeKt.dump$default(irFunction, null, 1, null));
    }

    public static final boolean isUnconditional(IrBranch irBranch) {
        Intrinsics.checkNotNullParameter(irBranch, "<this>");
        IrExpression condition = irBranch.getCondition();
        IrConst irConst = condition instanceof IrConst ? (IrConst) condition : null;
        if (irConst != null) {
            return Intrinsics.areEqual(irConst.getValue(), (Object) true);
        }
        return false;
    }
}
