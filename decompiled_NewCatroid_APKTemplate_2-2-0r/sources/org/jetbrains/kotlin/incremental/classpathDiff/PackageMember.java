package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ProgramSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/PackageMember;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbol;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "memberName", "", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/lang/String;)V", "getMemberName", "()Ljava/lang/String;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PackageMember extends ProgramSymbol {
    private final String memberName;
    private final FqName packageFqName;

    public static /* synthetic */ PackageMember copy$default(PackageMember packageMember, FqName fqName, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            fqName = packageMember.packageFqName;
        }
        if ((i & 2) != 0) {
            str = packageMember.memberName;
        }
        return packageMember.copy(fqName, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getMemberName() {
        return this.memberName;
    }

    public final PackageMember copy(FqName packageFqName, String memberName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(memberName, "memberName");
        return new PackageMember(packageFqName, memberName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PackageMember)) {
            return false;
        }
        PackageMember packageMember = (PackageMember) other;
        return Intrinsics.areEqual(this.packageFqName, packageMember.packageFqName) && Intrinsics.areEqual(this.memberName, packageMember.memberName);
    }

    public int hashCode() {
        return (this.packageFqName.hashCode() * 31) + this.memberName.hashCode();
    }

    public String toString() {
        return "PackageMember(packageFqName=" + this.packageFqName + ", memberName=" + this.memberName + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageMember(FqName packageFqName, String memberName) {
        super(null);
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(memberName, "memberName");
        this.packageFqName = packageFqName;
        this.memberName = memberName;
    }

    public final String getMemberName() {
        return this.memberName;
    }

    public final FqName getPackageFqName() {
        return this.packageFqName;
    }
}
