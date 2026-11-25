package org.jetbrains.kotlin.resolve.jvm.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JvmDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "", "name", "", "desc", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/MemberKind;", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/MemberKind;)V", "getDesc", "()Ljava/lang/String;", "getKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/MemberKind;", "getName", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class RawSignature {
    private final String desc;
    private final MemberKind kind;
    private final String name;

    public static /* synthetic */ RawSignature copy$default(RawSignature rawSignature, String str, String str2, MemberKind memberKind, int i, Object obj) {
        if ((i & 1) != 0) {
            str = rawSignature.name;
        }
        if ((i & 2) != 0) {
            str2 = rawSignature.desc;
        }
        if ((i & 4) != 0) {
            memberKind = rawSignature.kind;
        }
        return rawSignature.copy(str, str2, memberKind);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getDesc() {
        return this.desc;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final MemberKind getKind() {
        return this.kind;
    }

    public final RawSignature copy(String name2, String desc, MemberKind kind) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new RawSignature(name2, desc, kind);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RawSignature)) {
            return false;
        }
        RawSignature rawSignature = (RawSignature) other;
        return Intrinsics.areEqual(this.name, rawSignature.name) && Intrinsics.areEqual(this.desc, rawSignature.desc) && this.kind == rawSignature.kind;
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + this.desc.hashCode()) * 31) + this.kind.hashCode();
    }

    public String toString() {
        return "RawSignature(name=" + this.name + ", desc=" + this.desc + ", kind=" + this.kind + ')';
    }

    public RawSignature(String name2, String desc, MemberKind kind) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.name = name2;
        this.desc = desc;
        this.kind = kind;
    }

    public final String getDesc() {
        return this.desc;
    }

    public final MemberKind getKind() {
        return this.kind;
    }

    public final String getName() {
        return this.name;
    }
}
