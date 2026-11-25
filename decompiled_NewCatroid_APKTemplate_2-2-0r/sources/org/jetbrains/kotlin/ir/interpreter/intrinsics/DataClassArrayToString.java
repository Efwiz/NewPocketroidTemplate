package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/DataClassArrayToString;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "arrayToString", "", "array", "", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getListOfAcceptableFunctions", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassArrayToString extends IntrinsicBase {
    public static final DataClassArrayToString INSTANCE = new DataClassArrayToString();

    private DataClassArrayToString() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.listOf("kotlin.internal.ir.dataClassArrayMemberToString");
    }

    private final String arrayToString(Object array) {
        if (array == null) {
            return "null";
        }
        if (array instanceof Object[]) {
            String string = Arrays.toString((Object[]) array);
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
        if (array instanceof byte[]) {
            String string2 = Arrays.toString((byte[]) array);
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            return string2;
        }
        if (array instanceof short[]) {
            String string3 = Arrays.toString((short[]) array);
            Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
            return string3;
        }
        if (array instanceof int[]) {
            String string4 = Arrays.toString((int[]) array);
            Intrinsics.checkNotNullExpressionValue(string4, "toString(...)");
            return string4;
        }
        if (array instanceof long[]) {
            String string5 = Arrays.toString((long[]) array);
            Intrinsics.checkNotNullExpressionValue(string5, "toString(...)");
            return string5;
        }
        if (array instanceof char[]) {
            String string6 = Arrays.toString((char[]) array);
            Intrinsics.checkNotNullExpressionValue(string6, "toString(...)");
            return string6;
        }
        if (array instanceof boolean[]) {
            String string7 = Arrays.toString((boolean[]) array);
            Intrinsics.checkNotNullExpressionValue(string7, "toString(...)");
            return string7;
        }
        if (array instanceof float[]) {
            String string8 = Arrays.toString((float[]) array);
            Intrinsics.checkNotNullExpressionValue(string8, "toString(...)");
            return string8;
        }
        if (!(array instanceof double[])) {
            ExceptionUtilsKt.stop(new Function0<Object>() { // from class: org.jetbrains.kotlin.ir.interpreter.intrinsics.DataClassArrayToString.arrayToString.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return "Only arrays are supported in `dataClassArrayMemberToString` call";
                }
            });
            throw null;
        }
        String string9 = Arrays.toString((double[]) array);
        Intrinsics.checkNotNullExpressionValue(string9, "toString(...)");
        return string9;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        State stateLoadState = environment.getCallStack().loadState(((IrValueParameter) CollectionsKt.single((List) irFunction.getValueParameters())).getSymbol());
        Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Primitive<*>");
        environment.getCallStack().pushState(environment.convertToState$ir_interpreter(arrayToString(((Primitive) stateLoadState).getValue()), irFunction.getReturnType()));
    }
}
