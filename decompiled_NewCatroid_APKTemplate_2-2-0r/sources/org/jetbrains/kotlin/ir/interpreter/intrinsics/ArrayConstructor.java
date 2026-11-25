package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.CompoundInstruction;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.Wrapper;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KFunctionState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/ArrayConstructor;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getListOfAcceptableFunctions", "", "", "unwind", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayConstructor extends IntrinsicBase {
    public static final ArrayConstructor INSTANCE = new ArrayConstructor();

    private ArrayConstructor() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.listOf((Object[]) new String[]{"kotlin.Array.<init>", "kotlin.ByteArray.<init>", "kotlin.CharArray.<init>", "kotlin.ShortArray.<init>", "kotlin.IntArray.<init>", "kotlin.LongArray.<init>", "kotlin.FloatArray.<init>", "kotlin.DoubleArray.<init>", "kotlin.BooleanArray.<init>"});
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<Instruction> unwind(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        if (irFunction.getValueParameters().size() == 1) {
            return super.unwind(irFunction, environment);
        }
        CallStack callStack = environment.getCallStack();
        List<Instruction> mutableList = CollectionsKt.toMutableList((Collection) super.unwind(irFunction, environment));
        int iAsInt = StateKt.asInt(callStack.loadState(irFunction.getValueParameters().get(0).getSymbol()));
        IrValueParameterSymbol symbol = irFunction.getValueParameters().get(1).getSymbol();
        IrValueParameterSymbol irValueParameterSymbol = symbol;
        State stateLoadState = callStack.loadState(irValueParameterSymbol);
        KFunctionState kFunctionStateConvertGetterToKFunctionState = stateLoadState instanceof KFunctionState ? (KFunctionState) stateLoadState : null;
        if (kFunctionStateConvertGetterToKFunctionState == null) {
            Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState");
            kFunctionStateConvertGetterToKFunctionState = ((KPropertyState) stateLoadState).convertGetterToKFunctionState(environment);
        }
        callStack.rewriteState(irValueParameterSymbol, kFunctionStateConvertGetterToKFunctionState);
        for (int i = iAsInt - 1; -1 < i; i--) {
            IrFunction owner = kFunctionStateConvertGetterToKFunctionState.getInvokeSymbol().getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            IrCall irCallCreateCall$default = IrTreeBuildUtilsKt.createCall$default((IrSimpleFunction) owner, null, 1, null);
            irCallCreateCall$default.setDispatchReceiver(IrTreeBuildUtilsKt.createGetValue(symbol.getOwner()));
            irCallCreateCall$default.putValueArgument(0, IrUtilsKt.toIrConst$default(Integer.valueOf(i), environment.getIrBuiltIns().getIntType(), 0, 0, 6, null));
            mutableList.add(new CompoundInstruction(irCallCreateCall$default));
        }
        return mutableList;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Object obj;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        int iAsInt = StateKt.asInt(environment.getCallStack().loadState(irFunction.getValueParameters().get(0).getSymbol()));
        ArrayList arrayList = new ArrayList(iAsInt);
        for (int i = 0; i < iAsInt; i++) {
            if (IrTypePredicatesKt.isCharArray(irFunction.getReturnType())) {
                obj = (char) 0;
            } else {
                obj = IrTypePredicatesKt.isBooleanArray(irFunction.getReturnType()) ? false : 0;
            }
            arrayList.add(obj);
        }
        ArrayList arrayList2 = arrayList;
        if (irFunction.getValueParameters().size() == 2) {
            while (true) {
                iAsInt--;
                if (-1 >= iAsInt) {
                    break;
                }
                Object objPopState = environment.getCallStack().popState();
                if (objPopState instanceof Wrapper) {
                    objPopState = ((Wrapper) objPopState).getValue();
                } else if (objPopState instanceof Primitive) {
                    Primitive primitive = (Primitive) objPopState;
                    if (!IrTypePredicatesKt.isArray(primitive.getType()) && !UtilsKt.isPrimitiveArray(primitive.getType())) {
                        objPopState = primitive.getValue();
                    }
                }
                arrayList2.set(iAsInt, objPopState);
            }
        }
        State stateLoadState = environment.getCallStack().loadState(irFunction.getSymbol());
        Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState");
        environment.getCallStack().pushState(UtilsKt.toPrimitiveStateArray(arrayList2, ((KTypeState) stateLoadState).getIrType()));
    }
}
