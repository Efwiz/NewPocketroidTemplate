package org.jetbrains.kotlin.ir.interpreter.state.reflection;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KParameterProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeProxy;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KPropertyState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006B!\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!J\b\u0010#\u001a\u00020$H\u0016J\u0006\u0010%\u001a\u00020\u001cJ\u0006\u0010&\u001a\u00020\u001cJ\u0006\u0010'\u001a\u00020\u001cJ\u0006\u0010(\u001a\u00020\u001cJ\u0006\u0010)\u001a\u00020\u001cJ\u0006\u0010*\u001a\u00020\u001cJ\b\u0010+\u001a\u00020,H\u0016R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "propertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "receiver", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "(Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;Lorg/jetbrains/kotlin/ir/interpreter/state/State;)V", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/interpreter/state/State;)V", "_parameters", "", "Lkotlin/reflect/KParameter;", "_returnType", "Lkotlin/reflect/KType;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getProperty", "()Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getReceiver", "()Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "convertGetterToKFunctionState", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KFunctionState;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getParameters", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "getReturnType", "hashCode", "", "isKMutableProperty0", "isKMutableProperty1", "isKMutableProperty2", "isKProperty0", "isKProperty1", "isKProperty2", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KPropertyState extends ReflectionState {
    private List<? extends KParameter> _parameters;
    private KType _returnType;
    private final IrClass irClass;
    private final IrProperty property;
    private final State receiver;

    public KPropertyState(IrProperty property, IrClass irClass, State state) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        this.property = property;
        this.irClass = irClass;
        this.receiver = state;
    }

    public /* synthetic */ KPropertyState(IrProperty irProperty, IrClass irClass, State state, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irProperty, irClass, (i & 4) != 0 ? null : state);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    public final IrProperty getProperty() {
        return this.property;
    }

    public final State getReceiver() {
        return this.receiver;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KPropertyState(IrPropertyReference propertyReference, State state) {
        Intrinsics.checkNotNullParameter(propertyReference, "propertyReference");
        IrProperty owner = propertyReference.getSymbol().getOwner();
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(propertyReference.getType());
        Intrinsics.checkNotNull(classOrNull);
        this(owner, classOrNull.getOwner(), state);
    }

    public final KFunctionState convertGetterToKFunctionState(IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(environment, "environment");
        IrSimpleFunction getter = this.property.getGetter();
        Intrinsics.checkNotNull(getter);
        return new KFunctionState(getter, environment.getIrBuiltIns().functionN(1), environment, null, 8, null);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0042  */
    public final List<KParameter> getParameters(CallInterceptor callInterceptor) {
        int i;
        KParameterProxy kParameterProxy;
        IrValueParameter extensionReceiverParameter;
        IrValueParameter dispatchReceiverParameter;
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        List list = this._parameters;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            return list;
        }
        IrClass owner = callInterceptor.getEnvironment().getKParameterClass$ir_interpreter().getOwner();
        IrSimpleFunction getter = this.property.getGetter();
        KParameterProxy kParameterProxy2 = null;
        if (getter == null || (dispatchReceiverParameter = getter.getDispatchReceiverParameter()) == null) {
            i = 0;
            kParameterProxy = null;
        } else {
            if (this.receiver != null) {
                dispatchReceiverParameter = null;
            }
            if (dispatchReceiverParameter != null) {
                kParameterProxy = new KParameterProxy(new KParameterState(owner, dispatchReceiverParameter, 0, KParameter.Kind.INSTANCE), callInterceptor);
                i = 1;
            } else {
                i = 0;
                kParameterProxy = null;
            }
        }
        IrSimpleFunction getter2 = this.property.getGetter();
        if (getter2 != null && (extensionReceiverParameter = getter2.getExtensionReceiverParameter()) != null) {
            kParameterProxy2 = new KParameterProxy(new KParameterState(owner, extensionReceiverParameter, i, KParameter.Kind.EXTENSION_RECEIVER), callInterceptor);
        }
        List<KParameter> listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new KParameterProxy[]{kParameterProxy, kParameterProxy2});
        this._parameters = listListOfNotNull;
        Intrinsics.checkNotNull(listListOfNotNull);
        return listListOfNotNull;
    }

    public final KType getReturnType(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        KType kType = this._returnType;
        if (kType != null) {
            Intrinsics.checkNotNull(kType);
            return kType;
        }
        IrClass owner = callInterceptor.getEnvironment().getKTypeClass$ir_interpreter().getOwner();
        IrSimpleFunction getter = this.property.getGetter();
        Intrinsics.checkNotNull(getter);
        KTypeProxy kTypeProxy = new KTypeProxy(new KTypeState(getter.getReturnType(), owner), callInterceptor);
        this._returnType = kTypeProxy;
        Intrinsics.checkNotNull(kTypeProxy);
        return kTypeProxy;
    }

    public final boolean isKProperty0() {
        return Intrinsics.areEqual(getIrClass().getName().asString(), "KProperty0");
    }

    public final boolean isKProperty1() {
        return Intrinsics.areEqual(getIrClass().getName().asString(), "KProperty1");
    }

    public final boolean isKProperty2() {
        return Intrinsics.areEqual(getIrClass().getName().asString(), "KProperty2");
    }

    public final boolean isKMutableProperty0() {
        return Intrinsics.areEqual(getIrClass().getName().asString(), "KMutableProperty0");
    }

    public final boolean isKMutableProperty1() {
        return Intrinsics.areEqual(getIrClass().getName().asString(), "KMutableProperty1");
    }

    public final boolean isKMutableProperty2() {
        return Intrinsics.areEqual(getIrClass().getName().asString(), "KMutableProperty2");
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState");
        KPropertyState kPropertyState = (KPropertyState) other;
        return Intrinsics.areEqual(this.property, kPropertyState.property) && Intrinsics.areEqual(this.receiver, kPropertyState.receiver);
    }

    public int hashCode() {
        int iHashCode = this.property.hashCode() * 31;
        State state = this.receiver;
        return iHashCode + (state != null ? state.hashCode() : 0);
    }

    public String toString() {
        return renderProperty(this.property);
    }
}
