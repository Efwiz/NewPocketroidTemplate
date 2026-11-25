package org.jetbrains.kotlin.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: Cache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\r\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/container/ConstructorInfo;", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Ljava/lang/reflect/Constructor;", "parameters", "", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Constructor;Ljava/util/List;)V", "getConstructor", "()Ljava/lang/reflect/Constructor;", "getParameters", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConstructorInfo {
    private final Constructor<?> constructor;
    private final List<Type> parameters;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ConstructorInfo copy$default(ConstructorInfo constructorInfo, Constructor constructor, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            constructor = constructorInfo.constructor;
        }
        if ((i & 2) != 0) {
            list = constructorInfo.parameters;
        }
        return constructorInfo.copy(constructor, list);
    }

    public final Constructor<?> component1() {
        return this.constructor;
    }

    public final List<Type> component2() {
        return this.parameters;
    }

    public final ConstructorInfo copy(Constructor<?> constructor, List<? extends Type> parameters) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        return new ConstructorInfo(constructor, parameters);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConstructorInfo)) {
            return false;
        }
        ConstructorInfo constructorInfo = (ConstructorInfo) other;
        return Intrinsics.areEqual(this.constructor, constructorInfo.constructor) && Intrinsics.areEqual(this.parameters, constructorInfo.parameters);
    }

    public int hashCode() {
        return (this.constructor.hashCode() * 31) + this.parameters.hashCode();
    }

    public String toString() {
        return "ConstructorInfo(constructor=" + this.constructor + ", parameters=" + this.parameters + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConstructorInfo(Constructor<?> constructor, List<? extends Type> parameters) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.constructor = constructor;
        this.parameters = parameters;
    }

    public final Constructor<?> getConstructor() {
        return this.constructor;
    }

    public final List<Type> getParameters() {
        return this.parameters;
    }
}
