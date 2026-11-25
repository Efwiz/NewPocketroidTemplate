package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: InlineConstTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ConstantRef;", "", "owner", "", "name", "constType", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getConstType", "()Ljava/lang/String;", "setConstType", "(Ljava/lang/String;)V", "getName", "setName", "getOwner", "setOwner", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConstantRef {
    private String constType;
    private String name;
    private String owner;

    public static /* synthetic */ ConstantRef copy$default(ConstantRef constantRef, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = constantRef.owner;
        }
        if ((i & 2) != 0) {
            str2 = constantRef.name;
        }
        if ((i & 4) != 0) {
            str3 = constantRef.constType;
        }
        return constantRef.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getOwner() {
        return this.owner;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getConstType() {
        return this.constType;
    }

    public final ConstantRef copy(String owner, String name2, String constType) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(constType, "constType");
        return new ConstantRef(owner, name2, constType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConstantRef)) {
            return false;
        }
        ConstantRef constantRef = (ConstantRef) other;
        return Intrinsics.areEqual(this.owner, constantRef.owner) && Intrinsics.areEqual(this.name, constantRef.name) && Intrinsics.areEqual(this.constType, constantRef.constType);
    }

    public int hashCode() {
        return (((this.owner.hashCode() * 31) + this.name.hashCode()) * 31) + this.constType.hashCode();
    }

    public String toString() {
        return "ConstantRef(owner=" + this.owner + ", name=" + this.name + ", constType=" + this.constType + ')';
    }

    public ConstantRef(String owner, String name2, String constType) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(constType, "constType");
        this.owner = owner;
        this.name = name2;
        this.constType = constType;
    }

    public final String getConstType() {
        return this.constType;
    }

    public final String getName() {
        return this.name;
    }

    public final String getOwner() {
        return this.owner;
    }

    public final void setConstType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.constType = str;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final void setOwner(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.owner = str;
    }
}
