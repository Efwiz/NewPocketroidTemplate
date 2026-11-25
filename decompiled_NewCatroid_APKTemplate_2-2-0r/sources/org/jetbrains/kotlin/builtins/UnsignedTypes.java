package org.jetbrains.kotlin.builtins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: UnsignedType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0012\u001a\u00020\u0005J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0012\u001a\u00020\u0005J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\tJ\u000e\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\tJ\u000e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u000e\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0012\u0010 \u001a\u0004\u0018\u00010\f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0012\u0010 \u001a\u0004\u0018\u00010\f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0010\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u001c\u001a\u00020\u001dR*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\t0\u0004j\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\t`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR*\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/builtins/UnsignedTypes;", "", "()V", "arrayClassIdToUnsignedClassId", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lkotlin/collections/HashMap;", "arrayClassesShortNames", "", "Lorg/jetbrains/kotlin/name/Name;", "unsignedArrayTypeNames", "unsignedArrayTypeToArrayCall", "Lorg/jetbrains/kotlin/builtins/UnsignedArrayType;", "getUnsignedArrayTypeToArrayCall", "()Ljava/util/HashMap;", "unsignedClassIdToArrayClassId", "unsignedTypeNames", "getUnsignedArrayClassIdByUnsignedClassId", "arrayClassId", "getUnsignedClassIdByArrayClassId", "isShortNameOfUnsignedArray", "", "name", "isShortNameOfUnsignedType", "isUnsignedArrayClass", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isUnsignedArrayType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isUnsignedClass", "isUnsignedType", "toUnsignedArrayType", "toUnsignedType", "Lorg/jetbrains/kotlin/builtins/UnsignedType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsignedTypes {
    public static final UnsignedTypes INSTANCE = new UnsignedTypes();
    private static final HashMap<ClassId, ClassId> arrayClassIdToUnsignedClassId;
    private static final Set<Name> arrayClassesShortNames;
    private static final Set<Name> unsignedArrayTypeNames;
    private static final HashMap<UnsignedArrayType, Name> unsignedArrayTypeToArrayCall;
    private static final HashMap<ClassId, ClassId> unsignedClassIdToArrayClassId;
    private static final Set<Name> unsignedTypeNames;

    private UnsignedTypes() {
    }

    static {
        UnsignedType[] unsignedTypeArrValues = UnsignedType.values();
        ArrayList arrayList = new ArrayList(unsignedTypeArrValues.length);
        for (UnsignedType unsignedType : unsignedTypeArrValues) {
            arrayList.add(unsignedType.getTypeName());
        }
        unsignedTypeNames = CollectionsKt.toSet(arrayList);
        UnsignedArrayType[] unsignedArrayTypeArrValues = UnsignedArrayType.values();
        ArrayList arrayList2 = new ArrayList(unsignedArrayTypeArrValues.length);
        for (UnsignedArrayType unsignedArrayType : unsignedArrayTypeArrValues) {
            arrayList2.add(unsignedArrayType.getTypeName());
        }
        unsignedArrayTypeNames = CollectionsKt.toSet(arrayList2);
        arrayClassIdToUnsignedClassId = new HashMap<>();
        unsignedClassIdToArrayClassId = new HashMap<>();
        unsignedArrayTypeToArrayCall = MapsKt.hashMapOf(TuplesKt.to(UnsignedArrayType.UBYTEARRAY, Name.identifier("ubyteArrayOf")), TuplesKt.to(UnsignedArrayType.USHORTARRAY, Name.identifier("ushortArrayOf")), TuplesKt.to(UnsignedArrayType.UINTARRAY, Name.identifier("uintArrayOf")), TuplesKt.to(UnsignedArrayType.ULONGARRAY, Name.identifier("ulongArrayOf")));
        UnsignedType[] unsignedTypeArrValues2 = UnsignedType.values();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (UnsignedType unsignedType2 : unsignedTypeArrValues2) {
            linkedHashSet.add(unsignedType2.getArrayClassId().getShortClassName());
        }
        arrayClassesShortNames = linkedHashSet;
        for (UnsignedType unsignedType3 : UnsignedType.values()) {
            arrayClassIdToUnsignedClassId.put(unsignedType3.getArrayClassId(), unsignedType3.getClassId());
            unsignedClassIdToArrayClassId.put(unsignedType3.getClassId(), unsignedType3.getArrayClassId());
        }
    }

    public final HashMap<UnsignedArrayType, Name> getUnsignedArrayTypeToArrayCall() {
        return unsignedArrayTypeToArrayCall;
    }

    public final boolean isShortNameOfUnsignedArray(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return arrayClassesShortNames.contains(name2);
    }

    public final boolean isShortNameOfUnsignedType(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return unsignedTypeNames.contains(name2);
    }

    public final ClassId getUnsignedClassIdByArrayClassId(ClassId arrayClassId) {
        Intrinsics.checkNotNullParameter(arrayClassId, "arrayClassId");
        return arrayClassIdToUnsignedClassId.get(arrayClassId);
    }

    public final ClassId getUnsignedArrayClassIdByUnsignedClassId(ClassId arrayClassId) {
        Intrinsics.checkNotNullParameter(arrayClassId, "arrayClassId");
        return unsignedClassIdToArrayClassId.get(arrayClassId);
    }

    @JvmStatic
    public static final boolean isUnsignedType(KotlinType type) {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor;
        Intrinsics.checkNotNullParameter(type, "type");
        if (TypeUtils.noExpectedType(type) || (classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0()) == null) {
            return false;
        }
        return INSTANCE.isUnsignedClass(classifierDescriptorMo7434getDeclarationDescriptor);
    }

    public final UnsignedType toUnsignedType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (KotlinBuiltIns.isUByte(type)) {
            return UnsignedType.UBYTE;
        }
        if (KotlinBuiltIns.isUShort(type)) {
            return UnsignedType.USHORT;
        }
        if (KotlinBuiltIns.isUInt(type)) {
            return UnsignedType.UINT;
        }
        if (KotlinBuiltIns.isULong(type)) {
            return UnsignedType.ULONG;
        }
        return null;
    }

    public final boolean isUnsignedClass(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        return (containingDeclaration instanceof PackageFragmentDescriptor) && Intrinsics.areEqual(((PackageFragmentDescriptor) containingDeclaration).getFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME) && unsignedTypeNames.contains(descriptor.getShortName());
    }

    @JvmStatic
    public static final boolean isUnsignedArrayType(KotlinType type) {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor;
        Intrinsics.checkNotNullParameter(type, "type");
        if (TypeUtils.noExpectedType(type) || (classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0()) == null) {
            return false;
        }
        return INSTANCE.isUnsignedArrayClass(classifierDescriptorMo7434getDeclarationDescriptor);
    }

    @JvmStatic
    public static final UnsignedArrayType toUnsignedArrayType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (KotlinBuiltIns.isUByteArray(type)) {
            return UnsignedArrayType.UBYTEARRAY;
        }
        if (KotlinBuiltIns.isUShortArray(type)) {
            return UnsignedArrayType.USHORTARRAY;
        }
        if (KotlinBuiltIns.isUIntArray(type)) {
            return UnsignedArrayType.UINTARRAY;
        }
        if (KotlinBuiltIns.isULongArray(type)) {
            return UnsignedArrayType.ULONGARRAY;
        }
        return null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @JvmStatic
    public static final UnsignedArrayType toUnsignedArrayType(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!INSTANCE.isUnsignedArrayClass(descriptor)) {
            return null;
        }
        String strAsString = descriptor.getShortName().asString();
        switch (strAsString.hashCode()) {
            case -1714415512:
                if (strAsString.equals("ULongArray")) {
                    return UnsignedArrayType.ULONGARRAY;
                }
                return null;
            case 401290588:
                if (strAsString.equals("UByteArray")) {
                    return UnsignedArrayType.UBYTEARRAY;
                }
                return null;
            case 1114677618:
                if (strAsString.equals("UShortArray")) {
                    return UnsignedArrayType.USHORTARRAY;
                }
                return null;
            case 1587005215:
                if (strAsString.equals("UIntArray")) {
                    return UnsignedArrayType.UINTARRAY;
                }
                return null;
            default:
                return null;
        }
    }

    public final boolean isUnsignedArrayClass(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        return (containingDeclaration instanceof PackageFragmentDescriptor) && Intrinsics.areEqual(((PackageFragmentDescriptor) containingDeclaration).getFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME) && unsignedArrayTypeNames.contains(descriptor.getShortName());
    }
}
