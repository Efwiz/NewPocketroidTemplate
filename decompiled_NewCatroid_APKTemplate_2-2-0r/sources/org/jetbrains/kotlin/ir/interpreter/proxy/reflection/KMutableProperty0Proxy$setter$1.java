package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KParameter;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterAssertionError;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: KProperty0Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00060\u0001R\u00020\u00022\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J%\u0010\u0005\u001a\u00020\u00062\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\b\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\u00020\u00062\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u000bH\u0016J\u0013\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0096\u0002¨\u0006\u000f"}, d2 = {"org/jetbrains/kotlin/ir/interpreter/proxy/reflection/KMutableProperty0Proxy$setter$1", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy$Setter;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KMutableProperty0$Setter;", "", "call", "", "args", "", "([Ljava/lang/Object;)V", "callBy", "", "Lkotlin/reflect/KParameter;", "invoke", "p1", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KMutableProperty0Proxy$setter$1 extends AbstractKPropertyProxy.Setter implements KMutableProperty0.Setter<Object> {
    final /* synthetic */ KMutableProperty0Proxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KMutableProperty0Proxy$setter$1(KMutableProperty0Proxy kMutableProperty0Proxy, IrSimpleFunction irSimpleFunction) {
        super(kMutableProperty0Proxy, irSimpleFunction);
        this.this$0 = kMutableProperty0Proxy;
    }

    @Override // kotlin.reflect.KCallable
    public /* bridge */ /* synthetic */ Object call(Object[] objArr) throws InterpreterAssertionError {
        m7214call(objArr);
        return Unit.INSTANCE;
    }

    @Override // kotlin.reflect.KCallable
    public /* bridge */ /* synthetic */ Object callBy(Map map) {
        m7215callBy((Map<KParameter, ? extends Object>) map);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) throws InterpreterAssertionError {
        invoke2(obj);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public void invoke2(Object p1) throws InterpreterAssertionError {
        m7214call(p1);
    }

    /* JADX INFO: renamed from: call, reason: collision with other method in class */
    public void m7214call(Object... args) throws InterpreterAssertionError {
        IrClass irClass;
        Intrinsics.checkNotNullParameter(args, "args");
        this.this$0.checkArguments(1, args.length);
        if (this.this$0.getState().getReceiver() != null) {
            State receiver = this.this$0.getState().getReceiver();
            if (!((receiver == null || (irClass = receiver.getIrClass()) == null || !IrUtilsKt.isObject(irClass)) ? false : true)) {
                State receiver2 = this.this$0.getState().getReceiver();
                Intrinsics.checkNotNull(receiver2);
                State stateConvertToState$ir_interpreter = this.this$0.getEnvironment().convertToState$ir_interpreter(ArraysKt.single(args), this.this$0.getPropertyType());
                if (UtilsKt.getExtensionReceiver(getSetter()) == null || CallInterceptor.interceptProxy$default(this.this$0.getCallInterceptor(), getSetter(), CollectionsKt.listOf((Object[]) new State[]{receiver2, stateConvertToState$ir_interpreter}), null, 4, null) == null) {
                    receiver2.setField(this.this$0.getState().getProperty().getSymbol(), stateConvertToState$ir_interpreter);
                    return;
                }
                return;
            }
            throw new InterpreterAssertionError("Cannot interpret set method on property of object".toString());
        }
        throw new InterpreterAssertionError("Cannot interpret set method on top level properties".toString());
    }

    /* JADX INFO: renamed from: callBy, reason: collision with other method in class */
    public void m7215callBy(Map<KParameter, ? extends Object> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
