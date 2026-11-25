package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.OperatorNames;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: NumberOperatorCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u00015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\"\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u0010\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u0010\u0010!\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0018\u0010\"\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u001dH\u0002J\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0018\u0010&\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010'\u001a\u00020\u001fH\u0016J\u0010\u0010(\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010)\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010+\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010,\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010-\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010.\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J\u0010\u0010/\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\rH\u0002J(\u00100\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f2\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fH\u0002J#\u0010\u001b\u001a\u00020\u0011*\u00020\u001d2\u0012\u00102\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e03\"\u00020\u000e¢\u0006\u0002\u00104R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f0\nj\u0002`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/NumberOperatorCallsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "memberToTransformer", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/SimpleMemberKey;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/MemberToTransformer;", "booleanNegate", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "e", "buildInt", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", RegisterSpec.PREFIX, "convertResultToPrimitiveType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "irBinaryOp", "call", "intrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "toInt32", "", "toBoolean", "transformAdd", "transformCrement", "correspondingBinaryOp", "transformDecrement", "transformDiv", "transformFunctionAccess", "doNotIntrinsify", "transformHashCode", "transformIncrement", "transformMul", "transformRangeTo", "transformRangeUntil", "transformRem", "transformSub", "transformUnaryMinus", "withLongCoercion", "default", "arguments", "", "(Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "BinaryOp", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NumberOperatorCallsTransformer implements CallsTransformer {
    private final JsIntrinsics intrinsics;
    private final IrBuiltIns irBuiltIns;
    private final HashMap<SimpleMemberKey, Function1<IrFunctionAccessExpression, IrExpression>> memberToTransformer;

    public NumberOperatorCallsTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.intrinsics = context.getIntrinsics();
        IrBuiltIns irBuiltIns = context.getIrBuiltIns();
        this.irBuiltIns = irBuiltIns;
        HashMap<SimpleMemberKey, Function1<IrFunctionAccessExpression, IrExpression>> map = new HashMap<>();
        List<IrType> listListOf = CollectionsKt.listOf((Object[]) new IrType[]{irBuiltIns.getIntType(), irBuiltIns.getShortType(), irBuiltIns.getByteType(), irBuiltIns.getFloatType(), irBuiltIns.getDoubleType()});
        for (IrType irType : listListOf) {
            CallsLoweringUtilsKt.add(map, irType, OperatorNames.INSTANCE.getUNARY_PLUS(), this.intrinsics.getJsUnaryPlus());
            CallsLoweringUtilsKt.add(map, irType, OperatorNames.INSTANCE.getUNARY_MINUS(), new NumberOperatorCallsTransformer$memberToTransformer$1$1(this));
        }
        CallsLoweringUtilsKt.add(map, this.irBuiltIns.getStringType(), OperatorNames.INSTANCE.getADD(), this.intrinsics.getJsPlus());
        IrType intType = this.irBuiltIns.getIntType();
        CallsLoweringUtilsKt.add(map, intType, OperatorNames.INSTANCE.getSHL(), this.intrinsics.getJsBitShiftL());
        CallsLoweringUtilsKt.add(map, intType, OperatorNames.INSTANCE.getSHR(), this.intrinsics.getJsBitShiftR());
        CallsLoweringUtilsKt.add(map, intType, OperatorNames.INSTANCE.getSHRU(), new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.NumberOperatorCallsTransformer$memberToTransformer$1$2$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                NumberOperatorCallsTransformer numberOperatorCallsTransformer = this.this$0;
                return numberOperatorCallsTransformer.irBinaryOp(call, numberOperatorCallsTransformer.intrinsics.getJsBitShiftRU(), true);
            }
        });
        CallsLoweringUtilsKt.add(map, intType, OperatorNames.INSTANCE.getAND(), this.intrinsics.getJsBitAnd());
        CallsLoweringUtilsKt.add(map, intType, OperatorNames.INSTANCE.getOR(), this.intrinsics.getJsBitOr());
        CallsLoweringUtilsKt.add(map, intType, OperatorNames.INSTANCE.getXOR(), this.intrinsics.getJsBitXor());
        CallsLoweringUtilsKt.add(map, intType, OperatorNames.INSTANCE.getINV(), this.intrinsics.getJsBitNot());
        IrType booleanType = this.irBuiltIns.getBooleanType();
        CallsLoweringUtilsKt.add(map, booleanType, OperatorNames.INSTANCE.getAND(), new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.NumberOperatorCallsTransformer$memberToTransformer$1$3$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                NumberOperatorCallsTransformer numberOperatorCallsTransformer = this.this$0;
                return numberOperatorCallsTransformer.toBoolean(IrUtilsKt.irCall(call, numberOperatorCallsTransformer.intrinsics.getJsBitAnd(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null));
            }
        });
        CallsLoweringUtilsKt.add(map, booleanType, OperatorNames.INSTANCE.getOR(), new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.NumberOperatorCallsTransformer$memberToTransformer$1$3$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                NumberOperatorCallsTransformer numberOperatorCallsTransformer = this.this$0;
                return numberOperatorCallsTransformer.toBoolean(IrUtilsKt.irCall(call, numberOperatorCallsTransformer.intrinsics.getJsBitOr(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null));
            }
        });
        CallsLoweringUtilsKt.add(map, booleanType, OperatorNames.INSTANCE.getXOR(), new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.NumberOperatorCallsTransformer$memberToTransformer$1$3$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression call) {
                Intrinsics.checkNotNullParameter(call, "call");
                NumberOperatorCallsTransformer numberOperatorCallsTransformer = this.this$0;
                return numberOperatorCallsTransformer.toBoolean(IrUtilsKt.irCall(call, numberOperatorCallsTransformer.intrinsics.getJsBitXor(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null));
            }
        });
        CallsLoweringUtilsKt.add(map, booleanType, OperatorNames.INSTANCE.getNOT(), this.intrinsics.getJsNot());
        CallsLoweringUtilsKt.add(map, booleanType, NumberOperatorCallsTransformerKt.HASH_CODE_NAME, this.intrinsics.getJsGetBooleanHashCode());
        for (IrType irType2 : listListOf) {
            CallsLoweringUtilsKt.add(map, irType2, OperatorNameConventions.RANGE_TO, new NumberOperatorCallsTransformer$memberToTransformer$1$4(this));
            CallsLoweringUtilsKt.add(map, irType2, OperatorNameConventions.RANGE_UNTIL, new NumberOperatorCallsTransformer$memberToTransformer$1$5(this));
            CallsLoweringUtilsKt.add(map, irType2, NumberOperatorCallsTransformerKt.HASH_CODE_NAME, new NumberOperatorCallsTransformer$memberToTransformer$1$6(this));
        }
        for (IrType irType3 : listListOf) {
            CallsLoweringUtilsKt.add(map, irType3, OperatorNames.INSTANCE.getINC(), new NumberOperatorCallsTransformer$memberToTransformer$1$7(this));
            CallsLoweringUtilsKt.add(map, irType3, OperatorNames.INSTANCE.getDEC(), new NumberOperatorCallsTransformer$memberToTransformer$1$8(this));
        }
        for (IrType irType4 : listListOf) {
            CallsLoweringUtilsKt.add(map, irType4, OperatorNames.INSTANCE.getADD(), withLongCoercion(new NumberOperatorCallsTransformer$memberToTransformer$1$9(this)));
            CallsLoweringUtilsKt.add(map, irType4, OperatorNames.INSTANCE.getSUB(), withLongCoercion(new NumberOperatorCallsTransformer$memberToTransformer$1$10(this)));
            CallsLoweringUtilsKt.add(map, irType4, OperatorNames.INSTANCE.getMUL(), withLongCoercion(new NumberOperatorCallsTransformer$memberToTransformer$1$11(this)));
            CallsLoweringUtilsKt.add(map, irType4, OperatorNames.INSTANCE.getDIV(), withLongCoercion(new NumberOperatorCallsTransformer$memberToTransformer$1$12(this)));
            CallsLoweringUtilsKt.add(map, irType4, OperatorNames.INSTANCE.getMOD(), withLongCoercion(new NumberOperatorCallsTransformer$memberToTransformer$1$13(this)));
            CallsLoweringUtilsKt.add(map, irType4, OperatorNames.INSTANCE.getREM(), withLongCoercion(new NumberOperatorCallsTransformer$memberToTransformer$1$14(this)));
        }
        this.memberToTransformer = map;
    }

    private final IrConstImpl<Integer> buildInt(int v) {
        return JsIrBuilder.INSTANCE.buildInt(this.irBuiltIns.getIntType(), v);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        IrFunction owner = call.getSymbol().getOwner();
        IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            Function1<IrFunctionAccessExpression, IrExpression> function1 = this.memberToTransformer.get(new SimpleMemberKey(dispatchReceiverParameter.getType(), owner.getName()));
            if (function1 != null) {
                return function1.invoke(call);
            }
        }
        return call;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformRangeTo(IrFunctionAccessExpression call) {
        if (call.getValueArgumentsCount() != 1) {
            return call;
        }
        IrType type = call.getSymbol().getOwner().getValueParameters().get(0).getType();
        if (!IrTypePredicatesKt.isByte(type) && !IrTypePredicatesKt.isShort(type) && !IrTypePredicatesKt.isInt(type)) {
            if (IrTypePredicatesKt.isLong(type)) {
                call = IrUtilsKt.irCall(call, this.intrinsics.getJsNumberRangeToLong(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
            }
        } else {
            call = IrUtilsKt.irCall(call, this.intrinsics.getJsNumberRangeToNumber(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        }
        return call;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformRangeUntil(IrFunctionAccessExpression call) {
        if (call.getValueArgumentsCount() != 1) {
            return call;
        }
        IrFunction owner = call.getSymbol().getOwner();
        Map<Pair<IrType, IrType>, IrSimpleFunctionSymbol> rangeUntilFunctions = this.intrinsics.getRangeUntilFunctions();
        IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = rangeUntilFunctions.get(TuplesKt.to(dispatchReceiverParameter.getType(), owner.getValueParameters().get(0).getType()));
        if (irSimpleFunctionSymbol == null) {
            throw new IllegalStateException(("No 'until' function found for descriptor: " + owner).toString());
        }
        IrCall irCall = IrUtilsKt.irCall(call, irSimpleFunctionSymbol, (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        irCall.setExtensionReceiver(irCall.getDispatchReceiver());
        irCall.setDispatchReceiver(null);
        return irCall;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformHashCode(IrFunctionAccessExpression call) {
        IrValueParameter dispatchReceiverParameter = call.getSymbol().getOwner().getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        IrType type = dispatchReceiverParameter.getType();
        if (IrTypePredicatesKt.isByte(type) || IrTypePredicatesKt.isShort(type) || IrTypePredicatesKt.isInt(type)) {
            IrExpression dispatchReceiver = call.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            return dispatchReceiver;
        }
        if (!IrTypePredicatesKt.isFloat(type) && !IrTypePredicatesKt.isDouble(type)) {
            return call;
        }
        return IrUtilsKt.irCall(call, this.intrinsics.getJsGetNumberHashCode(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
    }

    static /* synthetic */ IrExpression irBinaryOp$default(NumberOperatorCallsTransformer numberOperatorCallsTransformer, IrFunctionAccessExpression irFunctionAccessExpression, IrSimpleFunctionSymbol irSimpleFunctionSymbol, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return numberOperatorCallsTransformer.irBinaryOp(irFunctionAccessExpression, irSimpleFunctionSymbol, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression irBinaryOp(IrFunctionAccessExpression call, IrSimpleFunctionSymbol intrinsic, boolean toInt32) {
        IrCall irCall = IrUtilsKt.irCall(call, intrinsic, (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        if (toInt32) {
            return toInt32(irCall);
        }
        return irCall;
    }

    /* JADX INFO: compiled from: NumberOperatorCallsTransformer.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0015\u001a\u00020\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0013\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/NumberOperatorCallsTransformer$BinaryOp;", "", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)V", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "lhs", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getLhs", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "result", "getResult", "rhs", "getRhs", "canAddOrSubOverflow", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BinaryOp {
        private final IrFunction function;
        private final IrType lhs;
        private final Name name;
        private final IrType result;
        private final IrType rhs;

        public BinaryOp(IrFunctionAccessExpression call) {
            Intrinsics.checkNotNullParameter(call, "call");
            IrFunction owner = call.getSymbol().getOwner();
            this.function = owner;
            this.name = owner.getName();
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            this.lhs = dispatchReceiverParameter.getType();
            this.rhs = owner.getValueParameters().get(0).getType();
            this.result = owner.getReturnType();
        }

        public final IrFunction getFunction() {
            return this.function;
        }

        public final Name getName() {
            return this.name;
        }

        public final IrType getLhs() {
            return this.lhs;
        }

        public final IrType getRhs() {
            return this.rhs;
        }

        public final IrType getResult() {
            return this.result;
        }

        public final boolean canAddOrSubOverflow() {
            return IrTypePredicatesKt.isInt(this.result) && (IrTypePredicatesKt.isInt(this.lhs) || IrTypePredicatesKt.isInt(this.rhs));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformAdd(IrFunctionAccessExpression call) {
        return irBinaryOp(call, this.intrinsics.getJsPlus(), new BinaryOp(call).canAddOrSubOverflow());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformSub(IrFunctionAccessExpression call) {
        return irBinaryOp(call, this.intrinsics.getJsMinus(), new BinaryOp(call).canAddOrSubOverflow());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformMul(IrFunctionAccessExpression call) {
        BinaryOp binaryOp = new BinaryOp(call);
        if (IrTypePredicatesKt.isInt(binaryOp.getResult())) {
            if (IrTypePredicatesKt.isInt(binaryOp.getLhs()) && IrTypePredicatesKt.isInt(binaryOp.getRhs())) {
                return irBinaryOp$default(this, call, this.intrinsics.getJsImul(), false, 4, null);
            }
            return irBinaryOp(call, this.intrinsics.getJsMult(), true);
        }
        return irBinaryOp(call, this.intrinsics.getJsMult(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformDiv(IrFunctionAccessExpression call) {
        return irBinaryOp(call, this.intrinsics.getJsDiv(), IrTypePredicatesKt.isInt(new BinaryOp(call).getResult()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformRem(IrFunctionAccessExpression call) {
        return irBinaryOp(call, this.intrinsics.getJsMod(), IrTypePredicatesKt.isInt(new BinaryOp(call).getResult()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformIncrement(IrFunctionAccessExpression call) {
        return transformCrement(call, this.intrinsics.getJsPlus());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformDecrement(IrFunctionAccessExpression call) {
        return transformCrement(call, this.intrinsics.getJsMinus());
    }

    private final IrExpression transformCrement(IrFunctionAccessExpression call, IrSimpleFunctionSymbol correspondingBinaryOp) {
        IrCall irCall = IrUtilsKt.irCall(call, correspondingBinaryOp, (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
        irCall.putValueArgument(1, buildInt(1));
        return convertResultToPrimitiveType(irCall, call.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformUnaryMinus(IrFunctionAccessExpression call) {
        return convertResultToPrimitiveType(IrUtilsKt.irCall(call, this.intrinsics.getJsUnaryMinus(), (12 & 4) != 0 ? false : true, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null), call.getType());
    }

    private final IrExpression convertResultToPrimitiveType(IrExpression e, IrType type) {
        if (IrTypePredicatesKt.isInt(type)) {
            return toInt32(e);
        }
        if (IrTypePredicatesKt.isByte(type)) {
            return call(this.intrinsics.getJsNumberToByte(), e);
        }
        return IrTypePredicatesKt.isShort(type) ? call(this.intrinsics.getJsNumberToShort(), e) : e;
    }

    private final Function1<IrFunctionAccessExpression, IrExpression> withLongCoercion(final Function1<? super IrFunctionAccessExpression, ? extends IrExpression> function1) {
        return new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.NumberOperatorCallsTransformer.withLongCoercion.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrFunctionAccessExpression irFunctionAccessExpression) {
                IrCall call = irFunctionAccessExpression;
                Intrinsics.checkNotNullParameter(call, "call");
                irFunctionAccessExpression.getValueArgumentsCount();
                boolean z = false;
                IrExpression valueArgument = call.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument);
                if (IrTypePredicatesKt.isLong(valueArgument.getType())) {
                    IrExpression dispatchReceiver = irFunctionAccessExpression.getDispatchReceiver();
                    Intrinsics.checkNotNull(dispatchReceiver);
                    IrType type = dispatchReceiver.getType();
                    if (IrTypePredicatesKt.isDouble(type)) {
                        IrCallImpl irCallImpl = new IrCallImpl(irFunctionAccessExpression.getStartOffset(), irFunctionAccessExpression.getEndOffset(), NumberOperatorCallsTransformer.this.intrinsics.getLongToDouble().getOwner().getReturnType(), NumberOperatorCallsTransformer.this.intrinsics.getLongToDouble(), 0, 0, null, null, 192, null);
                        irCallImpl.setDispatchReceiver(valueArgument);
                        Unit unit = Unit.INSTANCE;
                        call.putValueArgument(0, irCallImpl);
                    } else if (IrTypePredicatesKt.isFloat(type)) {
                        IrCallImpl irCallImpl2 = new IrCallImpl(irFunctionAccessExpression.getStartOffset(), irFunctionAccessExpression.getEndOffset(), NumberOperatorCallsTransformer.this.intrinsics.getLongToFloat().getOwner().getReturnType(), NumberOperatorCallsTransformer.this.intrinsics.getLongToFloat(), 0, 0, null, null, 192, null);
                        irCallImpl2.setDispatchReceiver(valueArgument);
                        Unit unit2 = Unit.INSTANCE;
                        call.putValueArgument(0, irCallImpl2);
                    } else if (!IrTypePredicatesKt.isLong(type)) {
                        IrCallImpl irCallImpl3 = new IrCallImpl(irFunctionAccessExpression.getStartOffset(), irFunctionAccessExpression.getEndOffset(), NumberOperatorCallsTransformer.this.intrinsics.getJsNumberToLong().getOwner().getReturnType(), NumberOperatorCallsTransformer.this.intrinsics.getJsNumberToLong(), 0, 1, null, null, 192, null);
                        irCallImpl3.putValueArgument(0, irFunctionAccessExpression.getDispatchReceiver());
                        call.setDispatchReceiver(irCallImpl3);
                        IrFunction owner = irFunctionAccessExpression.getSymbol().getOwner();
                        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) owner;
                        List<IrDeclaration> declarations = NumberOperatorCallsTransformer.this.intrinsics.getLongClassSymbol().getOwner().getDeclarations();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : declarations) {
                            if (obj instanceof IrSimpleFunction) {
                                arrayList.add(obj);
                            }
                        }
                        Object obj2 = null;
                        for (Object obj3 : arrayList) {
                            IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) obj3;
                            if (Intrinsics.areEqual(irSimpleFunction2.getName().asString(), irSimpleFunction.getName().asString()) && irSimpleFunction2.getValueParameters().size() == irSimpleFunction.getValueParameters().size()) {
                                List listZip = CollectionsKt.zip(irSimpleFunction2.getValueParameters(), irSimpleFunction.getValueParameters());
                                if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
                                    Iterator it = listZip.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            Pair pair = (Pair) it.next();
                                            if (!Intrinsics.areEqual(((IrValueParameter) pair.component1()).getType(), ((IrValueParameter) pair.component2()).getType())) {
                                            }
                                        }
                                    }
                                }
                                if (z) {
                                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                                }
                                z = true;
                                obj2 = obj3;
                            }
                        }
                        if (!z) {
                            throw new NoSuchElementException("Collection contains no element matching the predicate.");
                        }
                        call = IrUtilsKt.irCall(irFunctionAccessExpression, ((IrSimpleFunction) obj2).getSymbol(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null);
                    }
                }
                IrExpression dispatchReceiver2 = call.getDispatchReceiver();
                Intrinsics.checkNotNull(dispatchReceiver2);
                if (IrTypePredicatesKt.isLong(dispatchReceiver2.getType())) {
                    return call;
                }
                return function1.invoke(call);
            }
        };
    }

    public final IrCall call(IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrExpression... arguments) {
        Intrinsics.checkNotNullParameter(irSimpleFunctionSymbol, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunctionSymbol, irSimpleFunctionSymbol.getOwner().getReturnType(), null, null, null, 28, null);
        int length = arguments.length;
        for (int i = 0; i < length; i++) {
            irCallBuildCall$default.putValueArgument(i, arguments[i]);
        }
        return irCallBuildCall$default;
    }

    private final IrCall booleanNegate(IrExpression e) {
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.intrinsics.getJsNot(), this.irBuiltIns.getBooleanType(), null, null, null, 28, null);
        irCallBuildCall$default.putValueArgument(0, e);
        return irCallBuildCall$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrCall toBoolean(IrExpression e) {
        return booleanNegate(booleanNegate(e));
    }

    private final IrCall toInt32(IrExpression e) {
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.intrinsics.getJsBitOr(), this.irBuiltIns.getIntType(), null, null, null, 28, null);
        irCallBuildCall$default.putValueArgument(0, e);
        irCallBuildCall$default.putValueArgument(1, buildInt(0));
        return irCallBuildCall$default;
    }
}
