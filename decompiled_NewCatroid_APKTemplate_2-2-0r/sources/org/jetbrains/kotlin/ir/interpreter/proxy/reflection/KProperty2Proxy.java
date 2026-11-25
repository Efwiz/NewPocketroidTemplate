package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty2;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;

/* JADX INFO: compiled from: KProperty2Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0010\u0018\u00002\u00020\u00012\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001e\u0010\r\u001a\u0004\u0018\u00010\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001e\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001f\u0010\u0011\u001a\u0004\u0018\u00010\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u00032\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003H\u0096\u0002R,\u0010\t\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KProperty2Proxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KProperty2;", "", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "getter", "Lkotlin/reflect/KProperty2$Getter;", "getGetter", "()Lkotlin/reflect/KProperty2$Getter;", "get", "receiver1", "receiver2", "getDelegate", "invoke", "p1", "p2", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KProperty2Proxy extends AbstractKPropertyProxy implements KProperty2<Object, Object, Object> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KProperty2Proxy(KPropertyState state, CallInterceptor callInterceptor) {
        super(state, callInterceptor);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
    }

    @Override // kotlin.reflect.KProperty
    public KProperty.Getter<Object> getGetter() {
        IrSimpleFunction getter = getState().getProperty().getGetter();
        Intrinsics.checkNotNull(getter);
        return new KProperty2Proxy$getter$1(this, getter);
    }

    @Override // kotlin.reflect.KProperty2
    public Object get(Object receiver1, Object receiver2) {
        return getGetter().call(receiver1, receiver2);
    }

    @Override // kotlin.reflect.KProperty2
    public Object getDelegate(Object receiver1, Object receiver2) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.jvm.functions.Function2
    public Object invoke(Object p1, Object p2) {
        return getGetter().call(p1, p2);
    }
}
