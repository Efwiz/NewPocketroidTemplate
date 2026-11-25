package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010H\u0016J\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/EnumIntrinsics;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "canHandleFunctionWithName", "", "fqName", "", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getListOfAcceptableFunctions", "", "unwind", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumIntrinsics extends IntrinsicBase {
    public static final EnumIntrinsics INSTANCE = new EnumIntrinsics();

    private EnumIntrinsics() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.emptyList();
    }

    public final boolean canHandleFunctionWithName(String fqName, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(origin, "origin");
        if (Intrinsics.areEqual(origin, IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE)) {
            return true;
        }
        return StringsKt.startsWith$default(fqName, "kotlin.Enum.", false, 2, (Object) null) && !Intrinsics.areEqual(fqName, "kotlin.Enum.<init>");
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<Instruction> unwind(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        String strAsString = irFunction.getName().asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode != -823812830) {
            if (iHashCode == 231605032 && strAsString.equals("valueOf")) {
                return EnumValueOf.INSTANCE.unwind(irFunction, environment);
            }
        } else if (strAsString.equals(CompoundEntry.NAME_values)) {
            return EnumValues.INSTANCE.unwind(irFunction, environment);
        }
        return super.unwind(irFunction, environment);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        CallStack callStack = environment.getCallStack();
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        State stateLoadState = callStack.loadState(dispatchReceiverParameter.getSymbol());
        String strAsString = irFunction.getName().asString();
        switch (strAsString.hashCode()) {
            case -1776922004:
                if (strAsString.equals("toString")) {
                    State field = stateLoadState.getField(UtilsKt.getOriginalPropertyByName(stateLoadState.getIrClass(), "name").getSymbol());
                    Intrinsics.checkNotNull(field);
                    callStack.pushState(field);
                    return;
                }
                return;
            case -1412718016:
                if (strAsString.equals("compareTo")) {
                    IrPropertySymbol symbol = UtilsKt.getOriginalPropertyByName(stateLoadState.getIrClass(), "ordinal").getSymbol();
                    State stateLoadState2 = callStack.loadState(((IrValueParameter) CollectionsKt.single((List) irFunction.getValueParameters())).getSymbol());
                    IrPropertySymbol irPropertySymbol = symbol;
                    State field2 = stateLoadState.getField(irPropertySymbol);
                    Intrinsics.checkNotNull(field2);
                    int iAsInt = StateKt.asInt(field2);
                    State field3 = stateLoadState2.getField(irPropertySymbol);
                    Intrinsics.checkNotNull(field3);
                    callStack.pushState(environment.convertToState$ir_interpreter(Integer.valueOf(Intrinsics.compare(iAsInt, StateKt.asInt(field3))), irFunction.getReturnType()));
                    return;
                }
                return;
            case -1295482945:
                if (strAsString.equals(Namer.EQUALS_METHOD_NAME)) {
                    callStack.pushState(environment.convertToState$ir_interpreter(Boolean.valueOf(stateLoadState == callStack.loadState(((IrValueParameter) CollectionsKt.single((List) irFunction.getValueParameters())).getSymbol())), irFunction.getReturnType()));
                    return;
                }
                return;
            case -931074368:
                if (!strAsString.equals("<get-ordinal>")) {
                    return;
                }
                break;
            case -823812830:
                if (strAsString.equals(CompoundEntry.NAME_values)) {
                    EnumValues.INSTANCE.evaluate(irFunction, environment);
                    return;
                }
                return;
            case 147696667:
                if (strAsString.equals("hashCode")) {
                    callStack.pushState(environment.convertToState$ir_interpreter(Integer.valueOf(stateLoadState.hashCode()), irFunction.getReturnType()));
                    return;
                }
                return;
            case 231605032:
                if (strAsString.equals("valueOf")) {
                    EnumValueOf.INSTANCE.evaluate(irFunction, environment);
                    return;
                }
                return;
            case 804168992:
                if (!strAsString.equals("<get-name>")) {
                    return;
                }
                break;
            default:
                return;
        }
        IrProperty property = UtilsKt.getProperty(irFunction);
        Intrinsics.checkNotNull(property);
        State field4 = stateLoadState.getField(property.getSymbol());
        Intrinsics.checkNotNull(field4);
        callStack.pushState(field4);
    }
}
