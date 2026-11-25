package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ProgramSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassSymbol;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassSymbol extends ProgramSymbol {
    private final ClassId classId;

    public static /* synthetic */ ClassSymbol copy$default(ClassSymbol classSymbol, ClassId classId, int i, Object obj) {
        if ((i & 1) != 0) {
            classId = classSymbol.classId;
        }
        return classSymbol.copy(classId);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    public final ClassSymbol copy(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return new ClassSymbol(classId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ClassSymbol) && Intrinsics.areEqual(this.classId, ((ClassSymbol) other).classId);
    }

    public int hashCode() {
        return this.classId.hashCode();
    }

    public String toString() {
        return "ClassSymbol(classId=" + this.classId + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassSymbol(ClassId classId) {
        super(null);
        Intrinsics.checkNotNullParameter(classId, "classId");
        this.classId = classId;
    }

    public final ClassId getClassId() {
        return this.classId;
    }
}
