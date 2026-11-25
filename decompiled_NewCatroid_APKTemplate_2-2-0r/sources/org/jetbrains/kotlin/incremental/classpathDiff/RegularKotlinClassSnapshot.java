package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.KotlinClassInfo;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: ClasspathSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\t¢\u0006\u0002\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0019\u0010\r\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/RegularKotlinClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassSnapshot;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classAbiHash", "", "classMemberLevelSnapshot", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "supertypes", "", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "companionObjectName", "", "constantsInCompanionObject", "(Lorg/jetbrains/kotlin/name/ClassId;JLorg/jetbrains/kotlin/incremental/KotlinClassInfo;Ljava/util/List;Ljava/lang/String;Ljava/util/List;)V", "getClassAbiHash", "()J", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getClassMemberLevelSnapshot", "()Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "getCompanionObjectName", "()Ljava/lang/String;", "getConstantsInCompanionObject", "()Ljava/util/List;", "getSupertypes", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RegularKotlinClassSnapshot extends KotlinClassSnapshot {
    private final long classAbiHash;
    private final ClassId classId;
    private final KotlinClassInfo classMemberLevelSnapshot;
    private final String companionObjectName;
    private final List<String> constantsInCompanionObject;
    private final List<JvmClassName> supertypes;

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

    public final List<JvmClassName> getSupertypes() {
        return this.supertypes;
    }

    public final String getCompanionObjectName() {
        return this.companionObjectName;
    }

    public final List<String> getConstantsInCompanionObject() {
        return this.constantsInCompanionObject;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public RegularKotlinClassSnapshot(ClassId classId, long j, KotlinClassInfo kotlinClassInfo, List<? extends JvmClassName> supertypes, String str, List<String> list) {
        super(null);
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        this.classId = classId;
        this.classAbiHash = j;
        this.classMemberLevelSnapshot = kotlinClassInfo;
        this.supertypes = supertypes;
        this.companionObjectName = str;
        this.constantsInCompanionObject = list;
    }
}
