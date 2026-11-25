package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ProgramSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassMember;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "memberName", "", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/lang/String;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getMemberName", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassMember extends ProgramSymbol {
    private final ClassId classId;
    private final String memberName;

    public static /* synthetic */ ClassMember copy$default(ClassMember classMember, ClassId classId, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            classId = classMember.classId;
        }
        if ((i & 2) != 0) {
            str = classMember.memberName;
        }
        return classMember.copy(classId, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getMemberName() {
        return this.memberName;
    }

    public final ClassMember copy(ClassId classId, String memberName) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(memberName, "memberName");
        return new ClassMember(classId, memberName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassMember)) {
            return false;
        }
        ClassMember classMember = (ClassMember) other;
        return Intrinsics.areEqual(this.classId, classMember.classId) && Intrinsics.areEqual(this.memberName, classMember.memberName);
    }

    public int hashCode() {
        return (this.classId.hashCode() * 31) + this.memberName.hashCode();
    }

    public String toString() {
        return "ClassMember(classId=" + this.classId + ", memberName=" + this.memberName + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassMember(ClassId classId, String memberName) {
        super(null);
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(memberName, "memberName");
        this.classId = classId;
        this.memberName = memberName;
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    public final String getMemberName() {
        return this.memberName;
    }
}
