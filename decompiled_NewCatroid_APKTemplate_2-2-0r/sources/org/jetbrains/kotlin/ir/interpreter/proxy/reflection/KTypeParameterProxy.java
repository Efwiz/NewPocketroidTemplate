package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVariance;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeParameterState;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: KTypeParameterProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u001c\u001a\u00020\u000b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KTypeParameterProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lkotlin/reflect/KTypeParameter;", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeParameterState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeParameterState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "isReified", "", "()Z", "name", "", "getName", "()Ljava/lang/String;", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeParameterState;", "upperBounds", "", "Lkotlin/reflect/KType;", "getUpperBounds", "()Ljava/util/List;", "variance", "Lkotlin/reflect/KVariance;", "getVariance", "()Lkotlin/reflect/KVariance;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KTypeParameterProxy implements KTypeParameter, ReflectionProxy {
    private final CallInterceptor callInterceptor;
    private final KTypeParameterState state;

    /* JADX INFO: compiled from: KTypeParameterProxy.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KTypeParameterProxy(KTypeParameterState state, CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        this.state = state;
        this.callInterceptor = callInterceptor;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public CallInterceptor getCallInterceptor() {
        return this.callInterceptor;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public KTypeParameterState getState() {
        return this.state;
    }

    @Override // kotlin.reflect.KTypeParameter
    public String getName() {
        String strAsString = getState().getIrTypeParameter().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // kotlin.reflect.KTypeParameter
    public List<KType> getUpperBounds() {
        return getState().getUpperBounds(getCallInterceptor());
    }

    @Override // kotlin.reflect.KTypeParameter
    public KVariance getVariance() {
        int i = WhenMappings.$EnumSwitchMapping$0[getState().getIrTypeParameter().getVariance().ordinal()];
        if (i == 1) {
            return KVariance.INVARIANT;
        }
        if (i == 2) {
            return KVariance.IN;
        }
        if (i == 3) {
            return KVariance.OUT;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // kotlin.reflect.KTypeParameter
    /* JADX INFO: renamed from: isReified */
    public boolean getIsReified() {
        return getState().getIrTypeParameter().getIsReified();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof KTypeParameterProxy) {
            return Intrinsics.areEqual(getState(), ((KTypeParameterProxy) other).getState());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public int hashCode() {
        return getState().hashCode();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public String toString() {
        return getState().toString();
    }
}
