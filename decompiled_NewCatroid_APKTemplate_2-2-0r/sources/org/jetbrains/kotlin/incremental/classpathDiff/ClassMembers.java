package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ProgramSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassMembers;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "memberNames", "", "", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/Set;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getMemberNames", "()Ljava/util/Set;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassMembers {
    private final ClassId classId;
    private final Set<String> memberNames;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ClassMembers copy$default(ClassMembers classMembers, ClassId classId, Set set, int i, Object obj) {
        if ((i & 1) != 0) {
            classId = classMembers.classId;
        }
        if ((i & 2) != 0) {
            set = classMembers.memberNames;
        }
        return classMembers.copy(classId, set);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    public final Set<String> component2() {
        return this.memberNames;
    }

    public final ClassMembers copy(ClassId classId, Set<String> memberNames) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(memberNames, "memberNames");
        return new ClassMembers(classId, memberNames);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassMembers)) {
            return false;
        }
        ClassMembers classMembers = (ClassMembers) other;
        return Intrinsics.areEqual(this.classId, classMembers.classId) && Intrinsics.areEqual(this.memberNames, classMembers.memberNames);
    }

    public int hashCode() {
        return (this.classId.hashCode() * 31) + this.memberNames.hashCode();
    }

    public String toString() {
        return "ClassMembers(classId=" + this.classId + ", memberNames=" + this.memberNames + ')';
    }

    public ClassMembers(ClassId classId, Set<String> memberNames) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(memberNames, "memberNames");
        this.classId = classId;
        this.memberNames = memberNames;
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    public final Set<String> getMemberNames() {
        return this.memberNames;
    }
}
