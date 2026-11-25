package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KTypeProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u001b\u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0096\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020!H\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KTypeProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lkotlin/reflect/KType;", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "arguments", "Lkotlin/reflect/KTypeProjection;", "getArguments", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "classifier", "Lkotlin/reflect/KClassifier;", "getClassifier", "()Lkotlin/reflect/KClassifier;", "isMarkedNullable", "", "()Z", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeState;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KTypeProxy implements KType, ReflectionProxy {
    private final CallInterceptor callInterceptor;
    private final KTypeState state;

    public KTypeProxy(KTypeState state, CallInterceptor callInterceptor) {
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
    public KTypeState getState() {
        return this.state;
    }

    @Override // kotlin.reflect.KType
    public KClassifier getClassifier() {
        return getState().getClassifier(getCallInterceptor());
    }

    @Override // kotlin.reflect.KType
    public List<KTypeProjection> getArguments() {
        return getState().getArguments(getCallInterceptor());
    }

    @Override // kotlin.reflect.KType
    public boolean isMarkedNullable() {
        return IrTypePredicatesKt.isMarkedNullable(getState().getIrType());
    }

    @Override // kotlin.reflect.KAnnotatedElement
    public List<Annotation> getAnnotations() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof KTypeProxy) {
            return Intrinsics.areEqual(getState(), ((KTypeProxy) other).getState());
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
