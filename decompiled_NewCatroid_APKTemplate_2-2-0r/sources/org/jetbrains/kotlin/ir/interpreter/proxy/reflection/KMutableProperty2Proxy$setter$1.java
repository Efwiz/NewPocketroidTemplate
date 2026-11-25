package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KParameter;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: KProperty2Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00060\u0001R\u00020\u00022\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J%\u0010\u0005\u001a\u00020\u00062\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\b\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\u00020\u00062\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u000bH\u0016J'\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u00042\b\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u0096\u0002¨\u0006\u0011"}, d2 = {"org/jetbrains/kotlin/ir/interpreter/proxy/reflection/KMutableProperty2Proxy$setter$1", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy$Setter;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KMutableProperty2$Setter;", "", "call", "", "args", "", "([Ljava/lang/Object;)V", "callBy", "", "Lkotlin/reflect/KParameter;", "invoke", "p1", "p2", "p3", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KMutableProperty2Proxy$setter$1 extends AbstractKPropertyProxy.Setter implements KMutableProperty2.Setter<Object, Object, Object> {
    final /* synthetic */ KMutableProperty2Proxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KMutableProperty2Proxy$setter$1(KMutableProperty2Proxy kMutableProperty2Proxy, IrSimpleFunction irSimpleFunction) {
        super(kMutableProperty2Proxy, irSimpleFunction);
        this.this$0 = kMutableProperty2Proxy;
    }

    @Override // kotlin.reflect.KCallable
    public /* bridge */ /* synthetic */ Object call(Object[] objArr) {
        m7218call(objArr);
        return Unit.INSTANCE;
    }

    @Override // kotlin.reflect.KCallable
    public /* bridge */ /* synthetic */ Object callBy(Map map) {
        m7219callBy((Map<KParameter, ? extends Object>) map);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(Object obj, Object obj2, Object obj3) {
        invoke2(obj, obj2, obj3);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public void invoke2(Object p1, Object p2, Object p3) {
        m7218call(p1, p2, p3);
    }

    /* JADX INFO: renamed from: call, reason: collision with other method in class */
    public void m7218call(Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        this.this$0.checkArguments(3, args.length);
        throw new NotImplementedError("An operation is not implemented: " + ("Not yet implemented, receiver1 = " + args[0] + ", receiver2 = " + args[1] + ", value = " + args[2]));
    }

    /* JADX INFO: renamed from: callBy, reason: collision with other method in class */
    public void m7219callBy(Map<KParameter, ? extends Object> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
