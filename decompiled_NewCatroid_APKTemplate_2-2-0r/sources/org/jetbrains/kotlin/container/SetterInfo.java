package org.jetbrains.kotlin.container;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Cache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/container/SetterInfo;", "", "method", "Ljava/lang/reflect/Method;", "parameters", "", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Method;Ljava/util/List;)V", "getMethod", "()Ljava/lang/reflect/Method;", "getParameters", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SetterInfo {
    private final Method method;
    private final List<Type> parameters;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SetterInfo copy$default(SetterInfo setterInfo, Method method, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            method = setterInfo.method;
        }
        if ((i & 2) != 0) {
            list = setterInfo.parameters;
        }
        return setterInfo.copy(method, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Method getMethod() {
        return this.method;
    }

    public final List<Type> component2() {
        return this.parameters;
    }

    public final SetterInfo copy(Method method, List<? extends Type> parameters) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        return new SetterInfo(method, parameters);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SetterInfo)) {
            return false;
        }
        SetterInfo setterInfo = (SetterInfo) other;
        return Intrinsics.areEqual(this.method, setterInfo.method) && Intrinsics.areEqual(this.parameters, setterInfo.parameters);
    }

    public int hashCode() {
        return (this.method.hashCode() * 31) + this.parameters.hashCode();
    }

    public String toString() {
        return "SetterInfo(method=" + this.method + ", parameters=" + this.parameters + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SetterInfo(Method method, List<? extends Type> parameters) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.method = method;
        this.parameters = parameters;
    }

    public final Method getMethod() {
        return this.method;
    }

    public final List<Type> getParameters() {
        return this.parameters;
    }
}
