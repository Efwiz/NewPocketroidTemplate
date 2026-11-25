package org.jetbrains.kotlin.ir.interpreter.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Common;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.Wrapper;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CommonProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0013\u0010\u0013\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0010H\u0016J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\f\u0010\u0016\u001a\u00020\f*\u00020\u0017H\u0002R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/CommonProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/Proxy;", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/Common;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/Common;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/Common;", "defaultEquals", "", "other", "", "defaultHashCode", "", "defaultToString", "", Namer.EQUALS_METHOD_NAME, "hashCode", "toString", "wasAlreadyCalled", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonProxy implements Proxy {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CallInterceptor callInterceptor;
    private final Common state;

    public /* synthetic */ CommonProxy(Common common, CallInterceptor callInterceptor, DefaultConstructorMarker defaultConstructorMarker) {
        this(common, callInterceptor);
    }

    private CommonProxy(Common common, CallInterceptor callInterceptor) {
        this.state = common;
        this.callInterceptor = callInterceptor;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public CallInterceptor getCallInterceptor() {
        return this.callInterceptor;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public Common getState() {
        return this.state;
    }

    private final boolean defaultEquals(Object other) {
        return (other instanceof Proxy) && getState() == ((Proxy) other).getState();
    }

    private final int defaultHashCode() {
        return System.identityHashCode(getState());
    }

    private final String defaultToString() {
        StringBuilder sbAppend = new StringBuilder().append(UtilsKt.internalName(getState().getIrClass())).append('@');
        String string = Integer.toString(hashCode(), CharsKt.checkRadix(16));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return sbAppend.append(StringsKt.padStart(string, 8, ExternalAnnotationProvider.NULLABLE)).toString();
    }

    private final boolean wasAlreadyCalled(IrFunction irFunction) {
        IrValueParameter dispatchReceiverParameter = UtilsKt.getLastOverridden(irFunction).getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        IrValueParameterSymbol symbol = dispatchReceiverParameter.getSymbol();
        CallStack callStack = getCallInterceptor().getEnvironment().getCallStack();
        IrValueParameterSymbol irValueParameterSymbol = symbol;
        if (callStack.containsStateInMemory(irValueParameterSymbol) && callStack.loadState(irValueParameterSymbol) == getState()) {
            return true;
        }
        return Intrinsics.areEqual(irFunction, getCallInterceptor().getEnvironment().getCallStack().getCurrentFrameOwner$ir_interpreter());
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        IrSimpleFunction equalsFunction = getState().getEqualsFunction();
        if (IrUtilsKt.isFakeOverriddenFromAny(equalsFunction) || wasAlreadyCalled(equalsFunction)) {
            return defaultEquals(other);
        }
        Intrinsics.checkNotNull(UtilsKt.getDispatchReceiver(equalsFunction));
        arrayList.add(getState());
        arrayList.add(other instanceof Proxy ? ((Proxy) other).getState() : (State) other);
        Object objInterceptProxy$default = CallInterceptor.interceptProxy$default(getCallInterceptor(), equalsFunction, arrayList, null, 4, null);
        Intrinsics.checkNotNull(objInterceptProxy$default, "null cannot be cast to non-null type kotlin.Boolean");
        return ((Boolean) objInterceptProxy$default).booleanValue();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        IrSimpleFunction hashCodeFunction = getState().getHashCodeFunction();
        if (IrUtilsKt.isFakeOverriddenFromAny(hashCodeFunction) || wasAlreadyCalled(hashCodeFunction)) {
            return defaultHashCode();
        }
        Intrinsics.checkNotNull(UtilsKt.getDispatchReceiver(hashCodeFunction));
        arrayList.add(getState());
        Object objInterceptProxy$default = CallInterceptor.interceptProxy$default(getCallInterceptor(), hashCodeFunction, arrayList, null, 4, null);
        Intrinsics.checkNotNull(objInterceptProxy$default, "null cannot be cast to non-null type kotlin.Int");
        return ((Integer) objInterceptProxy$default).intValue();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public String toString() {
        if (IrTypeUtilsKt.isUnsigned(IrUtilsKt.getDefaultType(getState().getIrClass()))) {
            return UtilsKt.unsignedToString(getState());
        }
        ArrayList arrayList = new ArrayList();
        IrSimpleFunction toStringFunction = getState().getToStringFunction();
        if (IrUtilsKt.isFakeOverriddenFromAny(toStringFunction) || wasAlreadyCalled(toStringFunction)) {
            return defaultToString();
        }
        Intrinsics.checkNotNull(UtilsKt.getDispatchReceiver(toStringFunction));
        arrayList.add(getState());
        Object objInterceptProxy$default = CallInterceptor.interceptProxy$default(getCallInterceptor(), toStringFunction, arrayList, null, 4, null);
        Intrinsics.checkNotNull(objInterceptProxy$default, "null cannot be cast to non-null type kotlin.String");
        return (String) objInterceptProxy$default;
    }

    /* JADX INFO: compiled from: CommonProxy.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u00020\u0001*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\bH\u0000¢\u0006\u0002\b\tJ\u0014\u0010\n\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/CommonProxy$Companion;", "", "()V", "asProxy", "Lorg/jetbrains/kotlin/ir/interpreter/state/Common;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "extendFrom", "Ljava/lang/Class;", "asProxy$ir_interpreter", "fallbackIfMethodNotFound", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/CommonProxy;", "method", "Ljava/lang/reflect/Method;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Object asProxy$ir_interpreter$default(Companion companion, Common common, CallInterceptor callInterceptor, Class cls, int i, Object obj) {
            if ((i & 2) != 0) {
                cls = null;
            }
            return companion.asProxy$ir_interpreter(common, callInterceptor, cls);
        }

        public final Object asProxy$ir_interpreter(Common common, final CallInterceptor callInterceptor, Class<?> cls) {
            Intrinsics.checkNotNullParameter(common, "<this>");
            Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
            final CommonProxy commonProxy = new CommonProxy(common, callInterceptor, null);
            Object objNewProxyInstance = java.lang.reflect.Proxy.newProxyInstance(common.getClass().getClassLoader(), cls == null ? true : Intrinsics.areEqual(cls, Object.class) ? new Class[]{Proxy.class} : new Class[]{cls, Proxy.class}, new InvocationHandler() { // from class: org.jetbrains.kotlin.ir.interpreter.proxy.CommonProxy$Companion$$ExternalSyntheticLambda0
                @Override // java.lang.reflect.InvocationHandler
                public final Object invoke(Object obj, Method method, Object[] objArr) {
                    return CommonProxy.Companion.asProxy$lambda$1(commonProxy, callInterceptor, obj, method, objArr);
                }
            });
            Intrinsics.checkNotNullExpressionValue(objNewProxyInstance, "newProxyInstance(...)");
            return objNewProxyInstance;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Object asProxy$lambda$1(CommonProxy commonProxy, CallInterceptor callInterceptor, Object obj, Method method, Object[] objArr) {
            Intrinsics.checkNotNullParameter(commonProxy, "$commonProxy");
            Intrinsics.checkNotNullParameter(callInterceptor, "$callInterceptor");
            if (Intrinsics.areEqual(method.getDeclaringClass(), Proxy.class) && Intrinsics.areEqual(method.getName(), "getState")) {
                return commonProxy.getState();
            }
            if (Intrinsics.areEqual(method.getDeclaringClass(), Proxy.class) && Intrinsics.areEqual(method.getName(), "getCallInterceptor")) {
                return commonProxy.getCallInterceptor();
            }
            if (Intrinsics.areEqual(method.getName(), Namer.EQUALS_METHOD_NAME)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Intrinsics.checkNotNullExpressionValue(parameterTypes, "getParameterTypes(...)");
                Object objSingle = ArraysKt.single(parameterTypes);
                Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
                if (ProxyKt.isObject((Class) objSingle)) {
                    Intrinsics.checkNotNull(objArr);
                    return Boolean.valueOf(commonProxy.equals(ArraysKt.single(objArr)));
                }
            }
            if (Intrinsics.areEqual(method.getName(), "hashCode")) {
                Class<?>[] parameterTypes2 = method.getParameterTypes();
                Intrinsics.checkNotNullExpressionValue(parameterTypes2, "getParameterTypes(...)");
                if (parameterTypes2.length == 0) {
                    return Integer.valueOf(commonProxy.hashCode());
                }
            }
            if (Intrinsics.areEqual(method.getName(), "toString")) {
                Class<?>[] parameterTypes3 = method.getParameterTypes();
                Intrinsics.checkNotNullExpressionValue(parameterTypes3, "getParameterTypes(...)");
                if (parameterTypes3.length == 0) {
                    return commonProxy.toString();
                }
            }
            Common state = commonProxy.getState();
            Intrinsics.checkNotNull(method);
            IrFunction irFunction = state.getIrFunction(method);
            if (irFunction == null) {
                return CommonProxy.INSTANCE.fallbackIfMethodNotFound(commonProxy, method);
            }
            int i = 0;
            List<? extends State> listMutableListOf = CollectionsKt.mutableListOf(commonProxy.getState());
            List<? extends State> list = listMutableListOf;
            List<IrValueParameter> valueParameters = irFunction.getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            for (Object obj2 : valueParameters) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                arrayList.add(callInterceptor.getEnvironment().convertToState$ir_interpreter(objArr[i], ((IrValueParameter) obj2).getType()));
                i = i2;
            }
            CollectionsKt.addAll(list, arrayList);
            Class<?> returnType = method.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            return callInterceptor.interceptProxy(irFunction, listMutableListOf, returnType);
        }

        private final Object fallbackIfMethodNotFound(CommonProxy commonProxy, Method method) {
            if (Intrinsics.areEqual(method.getName(), "toArray")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Intrinsics.checkNotNullExpressionValue(parameterTypes, "getParameterTypes(...)");
                if (parameterTypes.length == 0) {
                    Wrapper superWrapperClass = commonProxy.getState().getSuperWrapperClass();
                    if (superWrapperClass == null) {
                        return new Object[0];
                    }
                    Object value = superWrapperClass.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.collections.Collection<*>");
                    return ((Collection) value).toArray(new Object[0]);
                }
            }
            throw new AssertionError("Cannot find method " + method + " in " + commonProxy.getState());
        }
    }
}
