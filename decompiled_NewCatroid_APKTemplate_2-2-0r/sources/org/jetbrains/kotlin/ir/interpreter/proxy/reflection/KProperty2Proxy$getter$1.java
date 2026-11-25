package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty2;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.State;

/* JADX INFO: compiled from: KProperty2Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00060\u0001R\u00020\u00022\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J'\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u0007\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\bJ \u0010\t\u001a\u0004\u0018\u00010\u00042\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\nH\u0016J\u001f\u0010\f\u001a\u0004\u0018\u00010\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0096\u0002¨\u0006\u000f"}, d2 = {"org/jetbrains/kotlin/ir/interpreter/proxy/reflection/KProperty2Proxy$getter$1", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy$Getter;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KProperty2$Getter;", "", "call", "args", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_DESCRIPTOR, "callBy", "", "Lkotlin/reflect/KParameter;", "invoke", "p1", "p2", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KProperty2Proxy$getter$1 extends AbstractKPropertyProxy.Getter implements KProperty2.Getter<Object, Object, Object> {
    final /* synthetic */ KProperty2Proxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KProperty2Proxy$getter$1(KProperty2Proxy kProperty2Proxy, IrSimpleFunction irSimpleFunction) {
        super(kProperty2Proxy, irSimpleFunction);
        this.this$0 = kProperty2Proxy;
    }

    @Override // kotlin.jvm.functions.Function2
    public Object invoke(Object p1, Object p2) {
        return call(p1, p2);
    }

    @Override // kotlin.reflect.KCallable
    public Object call(Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        this.this$0.checkArguments(2, args.length);
        IrValueParameter dispatchReceiverParameter = getGetter().getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        IrValueParameter extensionReceiverParameter = getGetter().getExtensionReceiverParameter();
        Intrinsics.checkNotNull(extensionReceiverParameter);
        return CallInterceptor.interceptProxy$default(this.this$0.getCallInterceptor(), getGetter(), CollectionsKt.listOf((Object[]) new State[]{this.this$0.getEnvironment().convertToState$ir_interpreter(args[0], dispatchReceiverParameter.getType()), this.this$0.getEnvironment().convertToState$ir_interpreter(args[1], extensionReceiverParameter.getType())}), null, 4, null);
    }

    @Override // kotlin.reflect.KCallable
    public Object callBy(Map<KParameter, ? extends Object> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
