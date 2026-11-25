package org.jetbrains.kotlin.ir.interpreter;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.interpreter.state.State;

/* JADX INFO: compiled from: CallInterceptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b`\u0018\u00002\u00020\u0001J4\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0018H&J,\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0018H&J\u001e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0018H&J\u001e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0018H&J\u0010\u0010\"\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020#H&J.\u0010$\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\b\u0002\u0010&\u001a\u0006\u0012\u0002\b\u00030'H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006(À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getEnvironment", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "getInterpreter", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "interceptCall", "", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "args", "", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "defaultAction", "Lkotlin/Function0;", "interceptConstructor", "constructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "interceptEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "interceptGetObjectValue", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "interceptJavaStaticField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "interceptProxy", "valueArguments", "expectedResultClass", "Ljava/lang/Class;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallInterceptor {
    IrInterpreterEnvironment getEnvironment();

    IrInterpreter getInterpreter();

    IrBuiltIns getIrBuiltIns();

    void interceptCall(IrCall call, IrFunction irFunction, List<? extends State> args, Function0<Unit> defaultAction);

    void interceptConstructor(IrFunctionAccessExpression constructorCall, List<? extends State> args, Function0<Unit> defaultAction);

    void interceptEnumEntry(IrEnumEntry enumEntry, Function0<Unit> defaultAction);

    void interceptGetObjectValue(IrGetObjectValue expression, Function0<Unit> defaultAction);

    void interceptJavaStaticField(IrGetField expression);

    Object interceptProxy(IrFunction irFunction, List<? extends State> valueArguments, Class<?> expectedResultClass);

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Object interceptProxy$default(CallInterceptor callInterceptor, IrFunction irFunction, List list, Class cls, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: interceptProxy");
        }
        if ((i & 4) != 0) {
            cls = Object.class;
        }
        return callInterceptor.interceptProxy(irFunction, list, cls);
    }
}
