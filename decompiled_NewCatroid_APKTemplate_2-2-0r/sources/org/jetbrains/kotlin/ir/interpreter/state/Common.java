package org.jetbrains.kotlin.ir.interpreter.state;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.stack.Variable;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: Common.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B'\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007j\u0002`\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020#J\u0010\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020(J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020*H\u0002J\u0006\u0010-\u001a\u00020#J\b\u0010.\u001a\u00020*H\u0016R$\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007j\u0002`\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR.\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0011j\u0004\u0018\u0001`\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR \u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u001e0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\r¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/Common;", "Lorg/jetbrains/kotlin/ir/interpreter/state/Complex;", "Lorg/jetbrains/kotlin/ir/interpreter/state/StateWithClosure;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "fields", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Fields;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/util/Map;)V", "getFields", "()Ljava/util/Map;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "outerClass", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Field;", "getOuterClass", "()Lkotlin/Pair;", "setOuterClass", "(Lkotlin/Pair;)V", "superWrapperClass", "Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "getSuperWrapperClass", "()Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "setSuperWrapperClass", "(Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;)V", "upValues", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Variable;", "getUpValues", "createToStringIrCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getEqualsFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getHashCodeFunction", "getIrFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "method", "Ljava/lang/reflect/Method;", "getKotlinName", "", "declaringClassName", "methodName", "getToStringFunction", "toString", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Common implements Complex, StateWithClosure {
    private final Map<IrSymbol, State> fields;
    private final IrClass irClass;
    private Pair<? extends IrSymbol, ? extends State> outerClass;
    private Wrapper superWrapperClass;
    private final Map<IrSymbol, Variable> upValues;

    private Common(IrClass irClass, Map<IrSymbol, State> map) {
        this.irClass = irClass;
        this.fields = map;
        this.upValues = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public Map<IrSymbol, State> getFields() {
        return this.fields;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure
    public Map<IrSymbol, Variable> getUpValues() {
        return this.upValues;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public Wrapper getSuperWrapperClass() {
        return this.superWrapperClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public void setSuperWrapperClass(Wrapper wrapper) {
        this.superWrapperClass = wrapper;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public Pair<IrSymbol, State> getOuterClass() {
        return this.outerClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public void setOuterClass(Pair<? extends IrSymbol, ? extends State> pair) {
        this.outerClass = pair;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Common(IrClass irClass) {
        this(irClass, new LinkedHashMap());
        Intrinsics.checkNotNullParameter(irClass, "irClass");
    }

    private final String getKotlinName(String declaringClassName, String methodName) {
        return (Intrinsics.areEqual(declaringClassName, CommonClassNames.JAVA_LANG_CHAR_SEQUENCE) && Intrinsics.areEqual(methodName, "charAt")) ? "get" : methodName;
    }

    public final IrFunction getIrFunction(Method method) {
        Intrinsics.checkNotNullParameter(method, "method");
        String name2 = method.getDeclaringClass().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String name3 = method.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        String kotlinName = getKotlinName(name2, name3);
        Iterator<T> it = getIrClass().getDeclarations().iterator();
        boolean z = false;
        Object obj = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if ((irDeclaration instanceof IrDeclarationWithName) && Intrinsics.areEqual(((IrDeclarationWithName) irDeclaration).getName().asString(), kotlinName)) {
                if (!z) {
                    z = true;
                    obj = next;
                }
            }
            obj = null;
            break;
        }
        IrDeclaration irDeclaration2 = (IrDeclaration) obj;
        if (irDeclaration2 instanceof IrProperty) {
            return ((IrProperty) irDeclaration2).getGetter();
        }
        if (irDeclaration2 instanceof IrFunction) {
            return (IrFunction) irDeclaration2;
        }
        return null;
    }

    public final IrSimpleFunction getEqualsFunction() {
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(getIrClass())) {
            if (IrUtilsKt.isEquals(irSimpleFunction2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            return IrFakeOverrideUtilsKt.getTarget(irSimpleFunction);
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final IrSimpleFunction getHashCodeFunction() {
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(getIrClass())) {
            if (IrUtilsKt.isHashCode(irSimpleFunction2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            return IrFakeOverrideUtilsKt.getTarget(irSimpleFunction);
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final IrSimpleFunction getToStringFunction() {
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(getIrClass())) {
            if (IrUtilsKt.isToString(irSimpleFunction2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z) {
            return IrFakeOverrideUtilsKt.getTarget(irSimpleFunction);
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final IrCall createToStringIrCall() {
        return IrTreeBuildUtilsKt.createCall$default(getToStringFunction(), null, 1, null);
    }

    public String toString() {
        return "Common(obj='" + UtilsKt.getFqName(getIrClass()) + "', values=" + getFields() + ')';
    }
}
