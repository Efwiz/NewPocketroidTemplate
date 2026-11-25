package org.jetbrains.kotlin.name;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0002\b\u001c\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u0011\u0010\u0013\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\nR\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\nR\u0011\u0010#\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\nR\u0011\u0010%\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0010\u0010'\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0018X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00100\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00103\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/name/JvmNames;", "", "()V", "JVM_DEFAULT_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getJVM_DEFAULT_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", "JVM_DEFAULT_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getJVM_DEFAULT_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID", "getJVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID", "JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME", "getJVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME", "JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID", "getJVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID", "JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME", "getJVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME", "JVM_MULTIFILE_CLASS", "getJVM_MULTIFILE_CLASS", "JVM_MULTIFILE_CLASS_ID", "getJVM_MULTIFILE_CLASS_ID", "JVM_MULTIFILE_CLASS_SHORT", "", "getJVM_MULTIFILE_CLASS_SHORT", "()Ljava/lang/String;", "JVM_NAME", "JVM_NAME_CLASS_ID", "JVM_NAME_SHORT", "getJVM_NAME_SHORT", "JVM_OVERLOADS_CLASS_ID", "getJVM_OVERLOADS_CLASS_ID", "JVM_OVERLOADS_FQ_NAME", "getJVM_OVERLOADS_FQ_NAME", "JVM_PACKAGE_NAME", "getJVM_PACKAGE_NAME", "JVM_PACKAGE_NAME_SHORT", "getJVM_PACKAGE_NAME_SHORT", "JVM_RECORD_ANNOTATION_CLASS_ID", "JVM_RECORD_ANNOTATION_FQ_NAME", "JVM_SYNTHETIC_ANNOTATION_CLASS_ID", "JVM_SYNTHETIC_ANNOTATION_FQ_NAME", "MULTIFILE_PART_NAME_DELIMITER", "STRICTFP_ANNOTATION_CLASS_ID", "STRICTFP_ANNOTATION_FQ_NAME", "SYNCHRONIZED_ANNOTATION_CLASS_ID", "SYNCHRONIZED_ANNOTATION_FQ_NAME", "TRANSIENT_ANNOTATION_CLASS_ID", "TRANSIENT_ANNOTATION_FQ_NAME", "VOLATILE_ANNOTATION_CLASS_ID", "VOLATILE_ANNOTATION_FQ_NAME", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmNames {
    public static final JvmNames INSTANCE = new JvmNames();
    private static final ClassId JVM_DEFAULT_CLASS_ID;
    private static final FqName JVM_DEFAULT_FQ_NAME;
    private static final ClassId JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID;
    private static final FqName JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME;
    private static final ClassId JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID;
    private static final FqName JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME;
    private static final FqName JVM_MULTIFILE_CLASS;
    private static final ClassId JVM_MULTIFILE_CLASS_ID;
    private static final String JVM_MULTIFILE_CLASS_SHORT;
    public static final FqName JVM_NAME;
    public static final ClassId JVM_NAME_CLASS_ID;
    private static final String JVM_NAME_SHORT;
    private static final ClassId JVM_OVERLOADS_CLASS_ID;
    private static final FqName JVM_OVERLOADS_FQ_NAME;
    private static final FqName JVM_PACKAGE_NAME;
    private static final String JVM_PACKAGE_NAME_SHORT;
    public static final ClassId JVM_RECORD_ANNOTATION_CLASS_ID;
    public static final FqName JVM_RECORD_ANNOTATION_FQ_NAME;
    public static final ClassId JVM_SYNTHETIC_ANNOTATION_CLASS_ID;
    public static final FqName JVM_SYNTHETIC_ANNOTATION_FQ_NAME;
    public static final String MULTIFILE_PART_NAME_DELIMITER = "__";
    public static final ClassId STRICTFP_ANNOTATION_CLASS_ID;
    public static final FqName STRICTFP_ANNOTATION_FQ_NAME;
    public static final ClassId SYNCHRONIZED_ANNOTATION_CLASS_ID;
    public static final FqName SYNCHRONIZED_ANNOTATION_FQ_NAME;
    public static final ClassId TRANSIENT_ANNOTATION_CLASS_ID;
    public static final FqName TRANSIENT_ANNOTATION_FQ_NAME;
    public static final ClassId VOLATILE_ANNOTATION_CLASS_ID;
    public static final FqName VOLATILE_ANNOTATION_FQ_NAME;

    private JvmNames() {
    }

    static {
        FqName fqName = new FqName("kotlin.jvm.JvmName");
        JVM_NAME = fqName;
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JVM_NAME_CLASS_ID = classId;
        String strAsString = fqName.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        JVM_NAME_SHORT = strAsString;
        FqName fqName2 = new FqName("kotlin.jvm.JvmMultifileClass");
        JVM_MULTIFILE_CLASS = fqName2;
        JVM_MULTIFILE_CLASS_ID = new ClassId(new FqName("kotlin.jvm"), Name.identifier("JvmMultifileClass"));
        String strAsString2 = fqName2.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        JVM_MULTIFILE_CLASS_SHORT = strAsString2;
        FqName fqName3 = new FqName("kotlin.jvm.JvmPackageName");
        JVM_PACKAGE_NAME = fqName3;
        String strAsString3 = fqName3.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
        JVM_PACKAGE_NAME_SHORT = strAsString3;
        FqName fqName4 = new FqName("kotlin.jvm.JvmDefault");
        JVM_DEFAULT_FQ_NAME = fqName4;
        ClassId classId2 = ClassId.topLevel(fqName4);
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        JVM_DEFAULT_CLASS_ID = classId2;
        FqName fqName5 = new FqName("kotlin.jvm.JvmDefaultWithoutCompatibility");
        JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME = fqName5;
        FqName fqName6 = new FqName("kotlin.jvm.JvmDefaultWithCompatibility");
        JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME = fqName6;
        ClassId classId3 = ClassId.topLevel(fqName5);
        Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(...)");
        JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID = classId3;
        ClassId classId4 = ClassId.topLevel(fqName6);
        Intrinsics.checkNotNullExpressionValue(classId4, "topLevel(...)");
        JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID = classId4;
        FqName fqName7 = new FqName("kotlin.jvm.JvmOverloads");
        JVM_OVERLOADS_FQ_NAME = fqName7;
        ClassId classId5 = ClassId.topLevel(fqName7);
        Intrinsics.checkNotNullExpressionValue(classId5, "topLevel(...)");
        JVM_OVERLOADS_CLASS_ID = classId5;
        FqName fqName8 = new FqName("kotlin.jvm.JvmSynthetic");
        JVM_SYNTHETIC_ANNOTATION_FQ_NAME = fqName8;
        ClassId classId6 = ClassId.topLevel(fqName8);
        Intrinsics.checkNotNullExpressionValue(classId6, "topLevel(...)");
        JVM_SYNTHETIC_ANNOTATION_CLASS_ID = classId6;
        FqName fqName9 = new FqName("kotlin.jvm.JvmRecord");
        JVM_RECORD_ANNOTATION_FQ_NAME = fqName9;
        ClassId classId7 = ClassId.topLevel(fqName9);
        Intrinsics.checkNotNullExpressionValue(classId7, "topLevel(...)");
        JVM_RECORD_ANNOTATION_CLASS_ID = classId7;
        FqName fqName10 = new FqName("kotlin.jvm.Synchronized");
        SYNCHRONIZED_ANNOTATION_FQ_NAME = fqName10;
        ClassId classId8 = ClassId.topLevel(fqName10);
        Intrinsics.checkNotNullExpressionValue(classId8, "topLevel(...)");
        SYNCHRONIZED_ANNOTATION_CLASS_ID = classId8;
        FqName fqName11 = new FqName("kotlin.jvm.Strictfp");
        STRICTFP_ANNOTATION_FQ_NAME = fqName11;
        ClassId classId9 = ClassId.topLevel(fqName11);
        Intrinsics.checkNotNullExpressionValue(classId9, "topLevel(...)");
        STRICTFP_ANNOTATION_CLASS_ID = classId9;
        FqName fqName12 = new FqName("kotlin.jvm.Volatile");
        VOLATILE_ANNOTATION_FQ_NAME = fqName12;
        ClassId classId10 = ClassId.topLevel(fqName12);
        Intrinsics.checkNotNullExpressionValue(classId10, "topLevel(...)");
        VOLATILE_ANNOTATION_CLASS_ID = classId10;
        FqName fqName13 = new FqName("kotlin.jvm.Transient");
        TRANSIENT_ANNOTATION_FQ_NAME = fqName13;
        ClassId classId11 = ClassId.topLevel(fqName13);
        Intrinsics.checkNotNullExpressionValue(classId11, "topLevel(...)");
        TRANSIENT_ANNOTATION_CLASS_ID = classId11;
    }

    public final String getJVM_NAME_SHORT() {
        return JVM_NAME_SHORT;
    }

    public final FqName getJVM_MULTIFILE_CLASS() {
        return JVM_MULTIFILE_CLASS;
    }

    public final ClassId getJVM_MULTIFILE_CLASS_ID() {
        return JVM_MULTIFILE_CLASS_ID;
    }

    public final String getJVM_MULTIFILE_CLASS_SHORT() {
        return JVM_MULTIFILE_CLASS_SHORT;
    }

    public final FqName getJVM_PACKAGE_NAME() {
        return JVM_PACKAGE_NAME;
    }

    public final String getJVM_PACKAGE_NAME_SHORT() {
        return JVM_PACKAGE_NAME_SHORT;
    }

    public final FqName getJVM_DEFAULT_FQ_NAME() {
        return JVM_DEFAULT_FQ_NAME;
    }

    public final ClassId getJVM_DEFAULT_CLASS_ID() {
        return JVM_DEFAULT_CLASS_ID;
    }

    public final FqName getJVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME() {
        return JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME;
    }

    public final FqName getJVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME() {
        return JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME;
    }

    public final ClassId getJVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID() {
        return JVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID;
    }

    public final ClassId getJVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID() {
        return JVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID;
    }

    public final FqName getJVM_OVERLOADS_FQ_NAME() {
        return JVM_OVERLOADS_FQ_NAME;
    }

    public final ClassId getJVM_OVERLOADS_CLASS_ID() {
        return JVM_OVERLOADS_CLASS_ID;
    }
}
