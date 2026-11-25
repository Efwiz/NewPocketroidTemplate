package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: InlineCache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/MethodId;", "", "ownerInternalName", "", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/commons/Method;)V", "getMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getOwnerInternalName", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class MethodId {
    private final Method method;
    private final String ownerInternalName;

    public static /* synthetic */ MethodId copy$default(MethodId methodId, String str, Method method, int i, Object obj) {
        if ((i & 1) != 0) {
            str = methodId.ownerInternalName;
        }
        if ((i & 2) != 0) {
            method = methodId.method;
        }
        return methodId.copy(str, method);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getOwnerInternalName() {
        return this.ownerInternalName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Method getMethod() {
        return this.method;
    }

    public final MethodId copy(String ownerInternalName, Method method) {
        Intrinsics.checkNotNullParameter(ownerInternalName, "ownerInternalName");
        Intrinsics.checkNotNullParameter(method, "method");
        return new MethodId(ownerInternalName, method);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MethodId)) {
            return false;
        }
        MethodId methodId = (MethodId) other;
        return Intrinsics.areEqual(this.ownerInternalName, methodId.ownerInternalName) && Intrinsics.areEqual(this.method, methodId.method);
    }

    public int hashCode() {
        return (this.ownerInternalName.hashCode() * 31) + this.method.hashCode();
    }

    public String toString() {
        return "MethodId(ownerInternalName=" + this.ownerInternalName + ", method=" + this.method + ')';
    }

    public MethodId(String ownerInternalName, Method method) {
        Intrinsics.checkNotNullParameter(ownerInternalName, "ownerInternalName");
        Intrinsics.checkNotNullParameter(method, "method");
        this.ownerInternalName = ownerInternalName;
        this.method = method;
    }

    public final Method getMethod() {
        return this.method;
    }

    public final String getOwnerInternalName() {
        return this.ownerInternalName;
    }
}
