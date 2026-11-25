package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/JsPrimitives;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getListOfAcceptableFunctions", "", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPrimitives extends IntrinsicBase {
    public static final JsPrimitives INSTANCE = new JsPrimitives();

    private JsPrimitives() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.listOf((Object[]) new String[]{"kotlin.Long.<init>", "kotlin.Char.<init>"});
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        String fqName = UtilsKt.getFqName(irFunction);
        if (Intrinsics.areEqual(fqName, "kotlin.Long.<init>")) {
            int iAsInt = StateKt.asInt(environment.getCallStack().loadState(irFunction.getValueParameters().get(0).getSymbol()));
            environment.getCallStack().pushState(environment.convertToState$ir_interpreter(Long.valueOf((((long) StateKt.asInt(environment.getCallStack().loadState(irFunction.getValueParameters().get(1).getSymbol()))) << 32) + ((long) iAsInt)), irFunction.getReturnType()));
        } else if (Intrinsics.areEqual(fqName, "kotlin.Char.<init>")) {
            environment.getCallStack().pushState(environment.convertToState$ir_interpreter(Character.valueOf((char) StateKt.asInt(environment.getCallStack().loadState(irFunction.getValueParameters().get(0).getSymbol()))), irFunction.getReturnType()));
        }
    }
}
