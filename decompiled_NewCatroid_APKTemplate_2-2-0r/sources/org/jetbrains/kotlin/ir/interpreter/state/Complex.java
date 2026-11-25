package org.jetbrains.kotlin.ir.interpreter.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;

/* JADX INFO: compiled from: Complex.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b`\u0018\u00002\u00020\u0001J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0011H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u001c\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!H\u0016R,\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0003j\u0004\u0018\u0001`\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\"À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/Complex;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "outerClass", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Field;", "getOuterClass", "()Lkotlin/Pair;", "setOuterClass", "(Lkotlin/Pair;)V", "superWrapperClass", "Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "getSuperWrapperClass", "()Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "setSuperWrapperClass", "(Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;)V", "getIrFunctionByIrCall", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getIrFunctionFromGivenClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "owner", "getRealFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "irClassFqName", "", "loadOuterClassesInto", "", "callStack", "Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "receiver", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Complex extends State {
    Pair<IrSymbol, State> getOuterClass();

    Wrapper getSuperWrapperClass();

    void setOuterClass(Pair<? extends IrSymbol, ? extends State> pair);

    void setSuperWrapperClass(Wrapper wrapper);

    default String irClassFqName() {
        return UtilsKt.getFqName(getIrClass());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private default IrFunction getIrFunctionFromGivenClass(IrClass irClass, IrFunction owner) {
        Object next;
        boolean zAreEqual;
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrProperty) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            IrSimpleFunction getter = ((IrProperty) it.next()).getGetter();
            if (getter != null) {
                arrayList2.add(getter);
            }
        }
        ArrayList arrayList3 = arrayList2;
        List<IrDeclaration> declarations2 = irClass.getDeclarations();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : declarations2) {
            if (obj2 instanceof IrProperty) {
                arrayList4.add(obj2);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        Iterator it2 = arrayList4.iterator();
        while (it2.hasNext()) {
            IrSimpleFunction setter = ((IrProperty) it2.next()).getSetter();
            if (setter != null) {
                arrayList5.add(setter);
            }
        }
        ArrayList arrayList6 = arrayList5;
        List<IrDeclaration> declarations3 = irClass.getDeclarations();
        ArrayList arrayList7 = new ArrayList();
        for (Object obj3 : declarations3) {
            if (obj3 instanceof IrFunction) {
                arrayList7.add(obj3);
            }
        }
        Iterator it3 = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayList6), (Iterable) arrayList7).iterator();
        while (it3.hasNext()) {
            next = it3.next();
            IrMemberWithContainerSource irMemberWithContainerSource = (IrFunction) next;
            if ((irMemberWithContainerSource instanceof IrSimpleFunction) && (owner instanceof IrSimpleFunction)) {
                IrOverridableDeclaration irOverridableDeclaration = (IrOverridableDeclaration) irMemberWithContainerSource;
                IrOverridableDeclaration irOverridableDeclaration2 = (IrOverridableDeclaration) owner;
                zAreEqual = AdditionalIrUtilsKt.overrides(irOverridableDeclaration, irOverridableDeclaration2) || AdditionalIrUtilsKt.overrides(irOverridableDeclaration2, irOverridableDeclaration);
            } else {
                zAreEqual = Intrinsics.areEqual(irMemberWithContainerSource, owner);
            }
            if (zAreEqual) {
                return (IrFunction) next;
            }
        }
        next = null;
        return (IrFunction) next;
    }

    default IrSimpleFunction getRealFunction(IrSimpleFunction owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default = IrFakeOverrideUtilsKt.resolveFakeOverride$default(owner, false, null, 3, null);
        Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        return (IrSimpleFunction) irOverridableDeclarationResolveFakeOverride$default;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    default IrFunction getIrFunctionByIrCall(IrCall expression) {
        IrClass irClass;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        if (superQualifierSymbol == null || (irClass = superQualifierSymbol.getOwner()) == null) {
            irClass = getIrClass();
        }
        IrFunction irFunctionFromGivenClass = getIrFunctionFromGivenClass(irClass, expression.getSymbol().getOwner());
        if (irFunctionFromGivenClass == null) {
            return null;
        }
        return (IrFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default((IrSimpleFunction) irFunctionFromGivenClass, false, null, 3, null);
    }

    static /* synthetic */ void loadOuterClassesInto$default(Complex complex, CallStack callStack, IrValueSymbol irValueSymbol, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: loadOuterClassesInto");
        }
        if ((i & 2) != 0) {
            irValueSymbol = null;
        }
        complex.loadOuterClassesInto(callStack, irValueSymbol);
    }

    private static <T> List<T> loadOuterClassesInto$takeFromEndWhile(List<? extends T> list, Function1<? super T, Boolean> function1) {
        ArrayList arrayList = new ArrayList();
        for (int lastIndex = CollectionsKt.getLastIndex(list); -1 < lastIndex && function1.invoke(list.get(lastIndex)).booleanValue(); lastIndex--) {
            arrayList.add(list.get(lastIndex));
        }
        return arrayList;
    }

    default void loadOuterClassesInto(CallStack callStack, final IrValueSymbol receiver) {
        Intrinsics.checkNotNullParameter(callStack, "callStack");
        for (Pair pair : loadOuterClassesInto$takeFromEndWhile(SequencesKt.toList(SequencesKt.generateSequence(getOuterClass(), new Function1<Pair<? extends IrSymbol, ? extends State>, Pair<? extends IrSymbol, ? extends State>>() { // from class: org.jetbrains.kotlin.ir.interpreter.state.Complex.loadOuterClassesInto.1
            @Override // kotlin.jvm.functions.Function1
            public final Pair<IrSymbol, State> invoke(Pair<? extends IrSymbol, ? extends State> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                State second = it.getSecond();
                Complex complex = second instanceof Complex ? (Complex) second : null;
                if (complex != null) {
                    return complex.getOuterClass();
                }
                return null;
            }
        })), new Function1<Pair<? extends IrSymbol, ? extends State>, Boolean>() { // from class: org.jetbrains.kotlin.ir.interpreter.state.Complex.loadOuterClassesInto.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Pair<? extends IrSymbol, ? extends State> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(receiver == null || !Intrinsics.areEqual(it.getFirst(), receiver));
            }
        })) {
            IrSymbol irSymbol = (IrSymbol) pair.component1();
            State state = (State) pair.component2();
            callStack.storeState(irSymbol, state);
            StateWithClosure stateWithClosure = state instanceof StateWithClosure ? (StateWithClosure) state : null;
            if (stateWithClosure != null) {
                callStack.loadUpValues(stateWithClosure);
            }
        }
    }
}
