package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: AutoboxingTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J4\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\tH\u0016J\u0014\u0010\u0016\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000bH\u0014J\u0014\u0010\u0018\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0014J\u001c\u0010\u001b\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016J\u0014\u0010\u001d\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000eH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/AutoboxingTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/AbstractValueUsageLowering;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "replaceTypesInsideInlinedFunctionBlock", "", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;Z)V", "processingReturnStack", "", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "buildSafeCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", ParameterDescription.NAME_PREFIX, "actualType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "resultType", "call", "Lkotlin/Function1;", "visitCall", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitReturn", "useAsResult", "enclosing", "useAsReturnValue", "returnTarget", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "useExpressionAsType", "expectedType", "useReturnableExpressionAsType", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AutoboxingTransformer extends AbstractValueUsageLowering {
    private List<IrReturn> processingReturnStack;

    public /* synthetic */ AutoboxingTransformer(JsCommonBackendContext jsCommonBackendContext, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsCommonBackendContext, (i & 2) != 0 ? false : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoboxingTransformer(JsCommonBackendContext context, boolean z) {
        super(context, z);
        Intrinsics.checkNotNullParameter(context, "context");
        this.processingReturnStack = new ArrayList();
    }

    private final IrExpression useReturnableExpressionAsType(IrExpression irExpression, IrType irType) {
        IrType actualType = getActualType(irExpression);
        return (IrTypePredicatesKt.isUnit(actualType) && IrTypePredicatesKt.isUnit(irType)) ? irExpression : useExpressionAsType(irExpression, actualType, irType);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        UtilsKt.push(this.processingReturnStack, expression);
        IrExpression irExpressionVisitReturn = super.visitReturn(expression);
        UtilsKt.pop(this.processingReturnStack);
        return irExpressionVisitReturn;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsResult(IrExpression irExpression, IrExpression enclosing) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(enclosing, "enclosing");
        IrReturn irReturn = (IrReturn) CollectionsKt.lastOrNull((List) this.processingReturnStack);
        if (Intrinsics.areEqual(irReturn != null ? irReturn.getValue() : null, enclosing) && (enclosing instanceof IrReturnableBlock)) {
            return useReturnableExpressionAsType(irExpression, enclosing.getType());
        }
        return useAs(irExpression, enclosing.getType());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsReturnValue(IrExpression irExpression, IrReturnTargetSymbol returnTarget) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(returnTarget, "returnTarget");
        if (returnTarget instanceof IrSimpleFunctionSymbol) {
            return useReturnableExpressionAsType(irExpression, ((IrSimpleFunctionSymbol) returnTarget).getOwner().getReturnType());
        }
        if (returnTarget instanceof IrConstructorSymbol) {
            return useReturnableExpressionAsType(irExpression, getIrBuiltIns().getUnitType());
        }
        if (returnTarget instanceof IrReturnableBlockSymbol) {
            return useReturnableExpressionAsType(irExpression, ((IrReturnableBlockSymbol) returnTarget).getOwner().getType());
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.AbstractValueUsageLowering
    public IrExpression useExpressionAsType(IrExpression irExpression, final IrType actualType, final IrType expectedType) {
        final IrSimpleFunctionSymbol unboxIntrinsic;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (IrTypePredicatesKt.isNothing(IrTypesKt.makeNotNull(actualType))) {
            return irExpression;
        }
        if (IrTypePredicatesKt.isUnit(actualType) && ((!IrTypePredicatesKt.isUnit(expectedType) || (irExpression instanceof IrCall)) && !AutoboxingTransformerKt.isGetUnit(irExpression, getIrBuiltIns()))) {
            return JsIrBuilder.INSTANCE.buildComposite(actualType, CollectionsKt.listOf((Object[]) new IrExpression[]{irExpression, JsIrBuilder.INSTANCE.buildGetObjectValue(actualType, getContext().getIrBuiltIns().getUnitClass())}));
        }
        if (IrTypePredicatesKt.isUnit(expectedType)) {
            return irExpression;
        }
        IrClass inlinedClass = getIcUtils().getInlinedClass(actualType);
        IrClass inlinedClass2 = getIcUtils().getInlinedClass(expectedType);
        if (((actualType instanceof IrDynamicType) && IrTypePredicatesKt.isChar(IrTypesKt.makeNotNull(expectedType))) || (IrTypePredicatesKt.isChar(IrTypesKt.makeNotNull(actualType)) && (expectedType instanceof IrDynamicType))) {
            return irExpression;
        }
        if (inlinedClass == null && inlinedClass2 == null) {
            return irExpression;
        }
        if (inlinedClass != null && inlinedClass2 == null) {
            unboxIntrinsic = getIcUtils().getBoxIntrinsic();
        } else {
            if (inlinedClass != null || inlinedClass2 == null) {
                return irExpression;
            }
            unboxIntrinsic = getIcUtils().getUnboxIntrinsic();
        }
        return buildSafeCall(irExpression, actualType, expectedType, new Function1<IrExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.AutoboxingTransformer.useExpressionAsType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrExpression arg) {
                Intrinsics.checkNotNullParameter(arg, "arg");
                JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = unboxIntrinsic;
                IrType irType = expectedType;
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(jsIrBuilder, irSimpleFunctionSymbol, irType, CollectionsKt.listOf((Object[]) new IrType[]{actualType, irType}), null, null, 24, null);
                irCallBuildCall$default.putValueArgument(0, arg);
                return irCallBuildCall$default;
            }
        });
    }

    private final IrExpression buildSafeCall(IrExpression arg, IrType actualType, IrType resultType, Function1<? super IrExpression, ? extends IrExpression> call) {
        if (!IrTypeUtilsKt.isNullable(actualType) || !IrTypeUtilsKt.isNullable(resultType)) {
            return call.invoke(arg);
        }
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrVariable irVariableBuildVar = jsIrBuilder.buildVar(actualType, null, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : null, (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : arg, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(jsIrBuilder, getIrBuiltIns().getEqeqSymbol(), null, null, null, null, 30, null);
        irCallBuildCall$default.putValueArgument(0, JsIrBuilder.buildGetValue$default(jsIrBuilder, irVariableBuildVar.getSymbol(), null, 2, null));
        irCallBuildCall$default.putValueArgument(1, jsIrBuilder.buildNull(getIrBuiltIns().getNothingNType()));
        Unit unit = Unit.INSTANCE;
        return jsIrBuilder.buildBlock(resultType, CollectionsKt.listOf((Object[]) new IrElement[]{irVariableBuildVar, JsIrBuilder.buildIfElse$default(jsIrBuilder, resultType, irCallBuildCall$default, jsIrBuilder.buildNull(getIrBuiltIns().getNothingNType()), call.invoke(JsIrBuilder.buildGetValue$default(jsIrBuilder, irVariableBuildVar.getSymbol(), null, 2, null)), 0, 0, 0, 0, 240, null)}));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.SYNTHETIC_NOT_AUTOBOXED_CHECK.INSTANCE)) {
            transformChildrenVoid(expression);
            return expression;
        }
        return super.visitCall(expression);
    }
}
