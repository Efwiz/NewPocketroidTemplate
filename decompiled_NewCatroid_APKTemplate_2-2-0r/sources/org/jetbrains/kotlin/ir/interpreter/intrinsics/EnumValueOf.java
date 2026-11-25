package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.SimpleInstruction;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Complex;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/EnumValueOf;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getEnumClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getEnumEntryByName", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getListOfAcceptableFunctions", "", "", "unwind", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumValueOf extends IntrinsicBase {
    public static final EnumValueOf INSTANCE = new EnumValueOf();

    private EnumValueOf() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.listOf("kotlin.enumValueOf");
    }

    private final IrClass getEnumClass(IrFunction irFunction, IrInterpreterEnvironment environment) {
        if (Intrinsics.areEqual(UtilsKt.getFqName(irFunction), "kotlin.enumValueOf")) {
            State stateLoadState = environment.getCallStack().loadState(((IrTypeParameter) CollectionsKt.first((List) irFunction.getTypeParameters())).getSymbol());
            Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState");
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(((KTypeState) stateLoadState).getIrType());
            Intrinsics.checkNotNull(classOrNull);
            return classOrNull.getOwner();
        }
        IrDeclarationParent parent = irFunction.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        return (IrClass) parent;
    }

    private final IrEnumEntry getEnumEntryByName(IrFunction irFunction, IrInterpreterEnvironment environment) {
        IrClass enumClass = getEnumClass(irFunction, environment);
        String strAsString = StateKt.asString(environment.getCallStack().loadState(((IrValueParameter) CollectionsKt.first((List) irFunction.getValueParameters())).getSymbol()));
        List<IrDeclaration> declarations = enumClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrEnumEntry) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        Object obj2 = null;
        boolean z = false;
        Object obj3 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                obj2 = obj3;
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((IrEnumEntry) next).getName().asString(), strAsString)) {
                if (z) {
                    break;
                }
                z = true;
                obj3 = next;
            }
        }
        IrEnumEntry irEnumEntry = (IrEnumEntry) obj2;
        if (irEnumEntry == null) {
            ExceptionUtilsKt.handleUserException(new IllegalArgumentException("No enum constant " + UtilsKt.getFqName(enumClass) + '.' + strAsString), environment);
        }
        return irEnumEntry;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<Instruction> unwind(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        IrEnumEntry enumEntryByName = getEnumEntryByName(irFunction, environment);
        return enumEntryByName == null ? CollectionsKt.emptyList() : CollectionsKt.plus((Collection<? extends SimpleInstruction>) super.unwind(irFunction, environment), new SimpleInstruction(enumEntryByName));
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        IrEnumEntry enumEntryByName = getEnumEntryByName(irFunction, environment);
        Intrinsics.checkNotNull(enumEntryByName);
        CallStack callStack = environment.getCallStack();
        Complex complex = environment.getMapOfEnums$ir_interpreter().get(enumEntryByName.getSymbol());
        Intrinsics.checkNotNull(complex);
        callStack.pushState(complex);
    }
}
