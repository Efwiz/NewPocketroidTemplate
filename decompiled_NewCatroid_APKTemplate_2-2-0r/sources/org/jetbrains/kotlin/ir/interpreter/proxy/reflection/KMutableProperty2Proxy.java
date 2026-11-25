package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty2;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;

/* JADX INFO: compiled from: KProperty2Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u00012\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ&\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u0016R,\u0010\t\u001a\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00030\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KMutableProperty2Proxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KProperty2Proxy;", "Lkotlin/reflect/KMutableProperty2;", "", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "setter", "Lkotlin/reflect/KMutableProperty2$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty2$Setter;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "receiver1", "receiver2", "value", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KMutableProperty2Proxy extends KProperty2Proxy implements KMutableProperty2<Object, Object, Object> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KMutableProperty2Proxy(KPropertyState state, CallInterceptor callInterceptor) {
        super(state, callInterceptor);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
    }

    @Override // kotlin.reflect.KMutableProperty
    public KMutableProperty2.Setter<Object, Object, Object> getSetter() {
        IrSimpleFunction setter = getState().getProperty().getSetter();
        Intrinsics.checkNotNull(setter);
        return new KMutableProperty2Proxy$setter$1(this, setter);
    }

    @Override // kotlin.reflect.KMutableProperty2
    public void set(Object receiver1, Object receiver2, Object value) {
        getSetter().call(receiver1, receiver2, value);
    }
}
