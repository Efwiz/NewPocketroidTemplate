package org.jetbrains.kotlin.ir.interpreter.state.reflection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KType;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeProxy;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KTypeParameterState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeParameterState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "irTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "_upperBounds", "", "Lkotlin/reflect/KType;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrTypeParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getUpperBounds", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "hashCode", "", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KTypeParameterState extends ReflectionState {
    private List<? extends KType> _upperBounds;
    private final IrClass irClass;
    private final IrTypeParameter irTypeParameter;

    public KTypeParameterState(IrTypeParameter irTypeParameter, IrClass irClass) {
        Intrinsics.checkNotNullParameter(irTypeParameter, "irTypeParameter");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        this.irTypeParameter = irTypeParameter;
        this.irClass = irClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    public final IrTypeParameter getIrTypeParameter() {
        return this.irTypeParameter;
    }

    public final List<KType> getUpperBounds(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        List list = this._upperBounds;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            return list;
        }
        IrClass owner = callInterceptor.getEnvironment().getKTypeClass$ir_interpreter().getOwner();
        List<IrType> superTypes = this.irTypeParameter.getSuperTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(new KTypeProxy(new KTypeState((IrType) it.next(), owner), callInterceptor));
        }
        ArrayList arrayList2 = arrayList;
        this._upperBounds = arrayList2;
        Intrinsics.checkNotNull(arrayList2);
        return arrayList2;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeParameterState");
        return Intrinsics.areEqual(this.irTypeParameter, ((KTypeParameterState) other).irTypeParameter);
    }

    public int hashCode() {
        return this.irTypeParameter.hashCode();
    }

    public String toString() {
        String strAsString = this.irTypeParameter.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }
}
