package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KParameter;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.State;

/* JADX INFO: compiled from: KProperty1Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00060\u0001R\u00020\u00022\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J%\u0010\u0005\u001a\u00020\u00062\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\b\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\u00020\u00062\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u000bH\u0016J\u001d\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0096\u0002¨\u0006\u0010"}, d2 = {"org/jetbrains/kotlin/ir/interpreter/proxy/reflection/KMutableProperty1Proxy$setter$1", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy$Setter;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KMutableProperty1$Setter;", "", "call", "", "args", "", "([Ljava/lang/Object;)V", "callBy", "", "Lkotlin/reflect/KParameter;", "invoke", "p1", "p2", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KMutableProperty1Proxy$setter$1 extends AbstractKPropertyProxy.Setter implements KMutableProperty1.Setter<Object, Object> {
    final /* synthetic */ KMutableProperty1Proxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KMutableProperty1Proxy$setter$1(KMutableProperty1Proxy kMutableProperty1Proxy, IrSimpleFunction irSimpleFunction) {
        super(kMutableProperty1Proxy, irSimpleFunction);
        this.this$0 = kMutableProperty1Proxy;
    }

    @Override // kotlin.reflect.KCallable
    public /* bridge */ /* synthetic */ Object call(Object[] objArr) {
        m7216call(objArr);
        return Unit.INSTANCE;
    }

    @Override // kotlin.reflect.KCallable
    public /* bridge */ /* synthetic */ Object callBy(Map map) {
        m7217callBy((Map<KParameter, ? extends Object>) map);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(Object obj, Object obj2) {
        invoke2(obj, obj2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public void invoke2(Object p1, Object p2) {
        m7216call(p1, p2);
    }

    /* JADX INFO: renamed from: call, reason: collision with other method in class */
    public void m7216call(Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        this.this$0.checkArguments(2, args.length);
        IrValueParameter dispatchReceiverParameter = getSetter().getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null) {
            dispatchReceiverParameter = getSetter().getExtensionReceiverParameter();
        }
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        CallInterceptor.interceptProxy$default(this.this$0.getCallInterceptor(), getSetter(), CollectionsKt.listOf((Object[]) new State[]{this.this$0.getEnvironment().convertToState$ir_interpreter(args[0], this.this$0.getActualType(dispatchReceiverParameter)), this.this$0.getEnvironment().convertToState$ir_interpreter(args[1], this.this$0.getActualType((IrValueParameter) CollectionsKt.single((List) getSetter().getValueParameters())))}), null, 4, null);
    }

    /* JADX INFO: renamed from: callBy, reason: collision with other method in class */
    public void m7217callBy(Map<KParameter, ? extends Object> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
