package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty0;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;

/* JADX INFO: compiled from: KProperty0Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0010\u0018\u00002\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\n\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u0016J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u0096\u0002R\u001c\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KProperty0Proxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KProperty0;", "", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "getter", "Lkotlin/reflect/KProperty0$Getter;", "getGetter", "()Lkotlin/reflect/KProperty0$Getter;", "get", "getDelegate", "invoke", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KProperty0Proxy extends AbstractKPropertyProxy implements KProperty0<Object> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KProperty0Proxy(KPropertyState state, CallInterceptor callInterceptor) {
        super(state, callInterceptor);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
    }

    @Override // kotlin.reflect.KProperty
    public KProperty.Getter<Object> getGetter() {
        IrSimpleFunction getter = getState().getProperty().getGetter();
        Intrinsics.checkNotNull(getter);
        return new KProperty0Proxy$getter$1(this, getter);
    }

    @Override // kotlin.reflect.KProperty0
    public Object get() {
        return getGetter().call(new Object[0]);
    }

    @Override // kotlin.reflect.KProperty0
    public Object getDelegate() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.jvm.functions.Function0
    public Object invoke() {
        return getGetter().call(new Object[0]);
    }
}
