package org.jetbrains.kotlin.builtins;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'UBYTEARRAY' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: UnsignedType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/builtins/UnsignedArrayType;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/name/ClassId;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "typeName", "Lorg/jetbrains/kotlin/name/Name;", "getTypeName", "()Lorg/jetbrains/kotlin/name/Name;", "UBYTEARRAY", "USHORTARRAY", "UINTARRAY", "ULONGARRAY", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsignedArrayType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ UnsignedArrayType[] $VALUES;
    public static final UnsignedArrayType UBYTEARRAY;
    public static final UnsignedArrayType UINTARRAY;
    public static final UnsignedArrayType ULONGARRAY;
    public static final UnsignedArrayType USHORTARRAY;
    private final ClassId classId;
    private final Name typeName;

    private static final /* synthetic */ UnsignedArrayType[] $values() {
        return new UnsignedArrayType[]{UBYTEARRAY, USHORTARRAY, UINTARRAY, ULONGARRAY};
    }

    public static EnumEntries<UnsignedArrayType> getEntries() {
        return $ENTRIES;
    }

    public static UnsignedArrayType valueOf(String str) {
        return (UnsignedArrayType) Enum.valueOf(UnsignedArrayType.class, str);
    }

    public static UnsignedArrayType[] values() {
        return (UnsignedArrayType[]) $VALUES.clone();
    }

    private UnsignedArrayType(String str, int i, ClassId classId) {
        super(str, i);
        this.classId = classId;
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        this.typeName = shortClassName;
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    static {
        ClassId classIdFromString = ClassId.fromString("kotlin/UByteArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        UBYTEARRAY = new UnsignedArrayType("UBYTEARRAY", 0, classIdFromString);
        ClassId classIdFromString2 = ClassId.fromString("kotlin/UShortArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString2, "fromString(...)");
        USHORTARRAY = new UnsignedArrayType("USHORTARRAY", 1, classIdFromString2);
        ClassId classIdFromString3 = ClassId.fromString("kotlin/UIntArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString3, "fromString(...)");
        UINTARRAY = new UnsignedArrayType("UINTARRAY", 2, classIdFromString3);
        ClassId classIdFromString4 = ClassId.fromString("kotlin/ULongArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString4, "fromString(...)");
        ULONGARRAY = new UnsignedArrayType("ULONGARRAY", 3, classIdFromString4);
        UnsignedArrayType[] unsignedArrayTypeArr$values = $values();
        $VALUES = unsignedArrayTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(unsignedArrayTypeArr$values);
    }

    public final Name getTypeName() {
        return this.typeName;
    }
}
