package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: KProperty1Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u00002\u00020\u00012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010\r\u001a\u0004\u0018\u00010\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u0016J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u0016J\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0014H\u0004R$\u0010\t\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KProperty1Proxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KProperty1;", "", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "getter", "Lkotlin/reflect/KProperty1$Getter;", "getGetter", "()Lkotlin/reflect/KProperty1$Getter;", "get", "receiver", "getDelegate", "invoke", "p1", "getActualType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KProperty1Proxy extends AbstractKPropertyProxy implements KProperty1<Object, Object> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KProperty1Proxy(KPropertyState state, CallInterceptor callInterceptor) {
        super(state, callInterceptor);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
    }

    protected final IrType getActualType(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        if (IrTypesKt.getClassOrNull(irValueParameter.getType()) == null) {
            State field = getState().getField(IrTypesKt.getClassifierOrFail(irValueParameter.getType()));
            Intrinsics.checkNotNull(field, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState");
            return ((KTypeState) field).getIrType();
        }
        return irValueParameter.getType();
    }

    @Override // kotlin.reflect.KProperty
    public KProperty.Getter<Object> getGetter() {
        IrSimpleFunction getter = getState().getProperty().getGetter();
        Intrinsics.checkNotNull(getter);
        return new KProperty1Proxy$getter$1(this, getter);
    }

    @Override // kotlin.reflect.KProperty1
    public Object get(Object receiver) {
        return getGetter().call(receiver);
    }

    @Override // kotlin.reflect.KProperty1
    public Object getDelegate(Object receiver) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object p1) {
        return getGetter().call(p1);
    }
}
