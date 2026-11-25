package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.CompoundInstruction;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KFunctionState;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/AssertIntrinsic;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getListOfAcceptableFunctions", "", "", "unwind", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AssertIntrinsic extends IntrinsicBase {
    public static final AssertIntrinsic INSTANCE = new AssertIntrinsic();

    private AssertIntrinsic() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.listOf("kotlin.PreconditionsKt.assert");
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<Instruction> unwind(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        if (irFunction.getValueParameters().size() == 1) {
            return super.unwind(irFunction, environment);
        }
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.last((List) irFunction.getValueParameters());
        State stateLoadState = environment.getCallStack().loadState(irValueParameter.getSymbol());
        Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KFunctionState");
        IrFunction owner = ((KFunctionState) stateLoadState).getInvokeSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        IrCall irCallCreateCall$default = IrTreeBuildUtilsKt.createCall$default((IrSimpleFunction) owner, null, 1, null);
        irCallCreateCall$default.setDispatchReceiver(IrTreeBuildUtilsKt.createGetValue(irValueParameter));
        return CollectionsKt.plus((Collection<? extends CompoundInstruction>) super.unwind(irFunction, environment), new CompoundInstruction(irCallCreateCall$default));
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        if (StateKt.asBoolean(environment.getCallStack().loadState(((IrValueParameter) CollectionsKt.first((List) irFunction.getValueParameters())).getSymbol()))) {
            return;
        }
        int size = irFunction.getValueParameters().size();
        if (size == 1) {
            ExceptionUtilsKt.handleUserException(new AssertionError("Assertion failed"), environment);
        } else {
            if (size != 2) {
                return;
            }
            ExceptionUtilsKt.handleUserException(new AssertionError(StateKt.asString(environment.getCallStack().popState())), environment);
        }
    }
}
