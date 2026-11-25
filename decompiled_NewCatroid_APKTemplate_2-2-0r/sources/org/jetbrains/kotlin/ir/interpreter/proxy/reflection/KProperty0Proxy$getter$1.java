package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty0;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;

/* JADX INFO: compiled from: KProperty0Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00060\u0001R\u00020\u00022\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J'\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u0007\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\bJ \u0010\t\u001a\u0004\u0018\u00010\u00042\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\nH\u0016J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0004H\u0096\u0002¨\u0006\r"}, d2 = {"org/jetbrains/kotlin/ir/interpreter/proxy/reflection/KProperty0Proxy$getter$1", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy$Getter;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lkotlin/reflect/KProperty0$Getter;", "", "call", "args", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_DESCRIPTOR, "callBy", "", "Lkotlin/reflect/KParameter;", "invoke", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KProperty0Proxy$getter$1 extends AbstractKPropertyProxy.Getter implements KProperty0.Getter<Object> {
    final /* synthetic */ KProperty0Proxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KProperty0Proxy$getter$1(KProperty0Proxy kProperty0Proxy, IrSimpleFunction irSimpleFunction) {
        super(kProperty0Proxy, irSimpleFunction);
        this.this$0 = kProperty0Proxy;
    }

    @Override // kotlin.jvm.functions.Function0
    public Object invoke() {
        return call(new Object[0]);
    }

    @Override // kotlin.reflect.KCallable
    public Object call(Object... args) {
        IrPropertySymbol symbol;
        Intrinsics.checkNotNullParameter(args, "args");
        this.this$0.checkArguments(0, args.length);
        IrProperty irProperty = (IrProperty) IrFakeOverrideUtilsKt.resolveFakeOverride$default(this.this$0.getState().getProperty(), false, null, 3, null);
        if (irProperty == null || (symbol = irProperty.getSymbol()) == null) {
            symbol = this.this$0.getState().getProperty().getSymbol();
        }
        State receiver = this.this$0.getState().getReceiver();
        if (receiver == null) {
            return CallInterceptor.interceptProxy$default(this.this$0.getCallInterceptor(), getGetter(), CollectionsKt.emptyList(), null, 4, null);
        }
        State field = receiver.getField(symbol);
        return (field == null || StateKt.isNull(receiver)) ? CallInterceptor.interceptProxy$default(this.this$0.getCallInterceptor(), getGetter(), CollectionsKt.listOf(receiver), null, 4, null) : field;
    }

    @Override // kotlin.reflect.KCallable
    public Object callBy(Map<KParameter, ? extends Object> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }
}
