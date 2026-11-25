package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.org.objectweb.asm.ClassReader;

/* JADX INFO: compiled from: BasicClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0018\u0010\u0019\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0012\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0014\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/BasicClassInfo;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "kotlinClassHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "supertypes", "", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "accessFlags", "", "isAnonymous", "", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;Ljava/util/List;IZ)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "()Z", "isKotlinClass", "isLocal", "isPrivate", "isSynthetic", "getKotlinClassHeader", "()Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "getSupertypes", "()Ljava/util/List;", "flagEnabled", "flagToCheck", "Companion", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BasicClassInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int accessFlags;
    private final ClassId classId;
    private final boolean isAnonymous;
    private final boolean isKotlinClass;
    private final boolean isLocal;
    private final boolean isPrivate;
    private final boolean isSynthetic;
    private final KotlinClassHeader kotlinClassHeader;
    private final List<JvmClassName> supertypes;

    private final boolean flagEnabled(int accessFlags, int flagToCheck) {
        return (accessFlags & flagToCheck) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BasicClassInfo(ClassId classId, KotlinClassHeader kotlinClassHeader, List<? extends JvmClassName> supertypes, int i, boolean z) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        this.classId = classId;
        this.kotlinClassHeader = kotlinClassHeader;
        this.supertypes = supertypes;
        this.accessFlags = i;
        this.isAnonymous = z;
        boolean zFlagEnabled = true;
        boolean z2 = kotlinClassHeader != null;
        this.isKotlinClass = z2;
        this.isPrivate = flagEnabled(i, 2);
        this.isLocal = classId.isLocal();
        if (z2) {
            Intrinsics.checkNotNull(kotlinClassHeader);
            if (kotlinClassHeader.getKind() != KotlinClassHeader.Kind.SYNTHETIC_CLASS && !flagEnabled(i, 4096)) {
                zFlagEnabled = false;
            }
        } else {
            zFlagEnabled = flagEnabled(i, 4096);
        }
        this.isSynthetic = zFlagEnabled;
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    public final KotlinClassHeader getKotlinClassHeader() {
        return this.kotlinClassHeader;
    }

    public final List<JvmClassName> getSupertypes() {
        return this.supertypes;
    }

    /* JADX INFO: renamed from: isAnonymous, reason: from getter */
    public final boolean getIsAnonymous() {
        return this.isAnonymous;
    }

    /* JADX INFO: renamed from: isKotlinClass, reason: from getter */
    public final boolean getIsKotlinClass() {
        return this.isKotlinClass;
    }

    /* JADX INFO: renamed from: isPrivate, reason: from getter */
    public final boolean getIsPrivate() {
        return this.isPrivate;
    }

    /* JADX INFO: renamed from: isLocal, reason: from getter */
    public final boolean getIsLocal() {
        return this.isLocal;
    }

    /* JADX INFO: renamed from: isSynthetic, reason: from getter */
    public final boolean getIsSynthetic() {
        return this.isSynthetic;
    }

    /* JADX INFO: compiled from: BasicClassInfo.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/BasicClassInfo$Companion;", "", "()V", "compute", "Lorg/jetbrains/kotlin/incremental/classpathDiff/BasicClassInfo;", "classContents", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BasicClassInfo compute(byte[] classContents) {
            Intrinsics.checkNotNullParameter(classContents, "classContents");
            KotlinClassHeaderClassVisitor kotlinClassHeaderClassVisitor = new KotlinClassHeaderClassVisitor();
            InnerClassesClassVisitor innerClassesClassVisitor = new InnerClassesClassVisitor(kotlinClassHeaderClassVisitor);
            BasicClassInfoClassVisitor basicClassInfoClassVisitor = new BasicClassInfoClassVisitor(innerClassesClassVisitor);
            new ClassReader(classContents).accept(basicClassInfoClassVisitor, 3);
            String className = basicClassInfoClassVisitor.getClassName();
            FileBasedKotlinClass.InnerClassesInfo innerClassesInfo = innerClassesClassVisitor.getInnerClassesInfo();
            ClassId classIdResolveNameByInternalName = FileBasedKotlinClass.resolveNameByInternalName(className, innerClassesInfo);
            Intrinsics.checkNotNullExpressionValue(classIdResolveNameByInternalName, "resolveNameByInternalName(...)");
            KotlinClassHeader kotlinClassHeader = kotlinClassHeaderClassVisitor.getKotlinClassHeader();
            List<JvmClassName> supertypes = basicClassInfoClassVisitor.getSupertypes();
            int accessFlags = basicClassInfoClassVisitor.getAccessFlags();
            FileBasedKotlinClass.OuterAndInnerName outerAndInnerName = innerClassesInfo.get(className);
            boolean z = false;
            if (outerAndInnerName != null && outerAndInnerName.innerSimpleName == null) {
                z = true;
            }
            return new BasicClassInfo(classIdResolveNameByInternalName, kotlinClassHeader, supertypes, accessFlags, z);
        }
    }
}
