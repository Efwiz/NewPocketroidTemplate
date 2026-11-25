package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.KotlinClassInfo;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClasspathSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/PackageFacadeKotlinClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassSnapshot;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classAbiHash", "", "classMemberLevelSnapshot", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "packageMemberNames", "", "", "(Lorg/jetbrains/kotlin/name/ClassId;JLorg/jetbrains/kotlin/incremental/KotlinClassInfo;Ljava/util/Set;)V", "getClassAbiHash", "()J", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getClassMemberLevelSnapshot", "()Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "getPackageMemberNames", "()Ljava/util/Set;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageFacadeKotlinClassSnapshot extends KotlinClassSnapshot {
    private final long classAbiHash;
    private final ClassId classId;
    private final KotlinClassInfo classMemberLevelSnapshot;
    private final Set<String> packageMemberNames;

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.AccessibleClassSnapshot
    public ClassId getClassId() {
        return this.classId;
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.AccessibleClassSnapshot
    public long getClassAbiHash() {
        return this.classAbiHash;
    }

    @Override // org.jetbrains.kotlin.incremental.classpathDiff.KotlinClassSnapshot
    public KotlinClassInfo getClassMemberLevelSnapshot() {
        return this.classMemberLevelSnapshot;
    }

    public final Set<String> getPackageMemberNames() {
        return this.packageMemberNames;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageFacadeKotlinClassSnapshot(ClassId classId, long j, KotlinClassInfo kotlinClassInfo, Set<String> packageMemberNames) {
        super(null);
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(packageMemberNames, "packageMemberNames");
        this.classId = classId;
        this.classAbiHash = j;
        this.classMemberLevelSnapshot = kotlinClassInfo;
        this.packageMemberNames = packageMemberNames;
    }
}
