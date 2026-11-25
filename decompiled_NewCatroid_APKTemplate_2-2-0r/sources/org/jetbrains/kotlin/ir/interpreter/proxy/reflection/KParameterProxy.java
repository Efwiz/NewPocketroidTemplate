package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KParameterState;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KParameterProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010%\u001a\u00020\u00142\b\u0010&\u001a\u0004\u0018\u00010'H\u0096\u0002J\b\u0010(\u001a\u00020\u0010H\u0016J\b\u0010)\u001a\u00020\u001cH\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KParameterProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lkotlin/reflect/KParameter;", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KParameterState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KParameterState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "index", "", "getIndex", "()I", "isOptional", "", "()Z", "isVararg", Namer.METADATA_CLASS_KIND, "Lkotlin/reflect/KParameter$Kind;", "getKind", "()Lkotlin/reflect/KParameter$Kind;", "name", "", "getName", "()Ljava/lang/String;", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KParameterState;", ModuleXmlParser.TYPE, "Lkotlin/reflect/KType;", "getType", "()Lkotlin/reflect/KType;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "toString", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KParameterProxy implements KParameter, ReflectionProxy {
    private final CallInterceptor callInterceptor;
    private final KParameterState state;

    public KParameterProxy(KParameterState state, CallInterceptor callInterceptor) {
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
    public KParameterState getState() {
        return this.state;
    }

    @Override // kotlin.reflect.KParameter
    public int getIndex() {
        return getState().getIndex();
    }

    @Override // kotlin.reflect.KParameter
    public String getName() {
        if (getKind() == KParameter.Kind.VALUE) {
            return getState().getIrParameter().getName().asString();
        }
        return null;
    }

    @Override // kotlin.reflect.KParameter
    public KType getType() {
        return getState().getType(getCallInterceptor());
    }

    @Override // kotlin.reflect.KParameter
    public KParameter.Kind getKind() {
        return getState().getKind();
    }

    @Override // kotlin.reflect.KParameter
    public boolean isOptional() {
        return getState().getIrParameter().getDefaultValue() != null;
    }

    @Override // kotlin.reflect.KParameter
    public boolean isVararg() {
        return getState().getIrParameter().getVarargElementType() != null;
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
        if (other instanceof KParameterProxy) {
            return Intrinsics.areEqual(getState(), ((KParameterProxy) other).getState());
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
