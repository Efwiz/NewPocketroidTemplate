package org.jetbrains.kotlin.codegen;

import org.jetbrains.kotlin.builtins.CompanionObjectMapping;
import org.jetbrains.kotlin.builtins.CompanionObjectMappingUtilsKt;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class FieldInfo {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KotlinType fieldKotlinType;
    private final String fieldName;
    private final Type fieldType;
    private final boolean isStatic;
    private final Type ownerType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "typeMapper";
                break;
            case 2:
            default:
                objArr[0] = "classDescriptor";
                break;
            case 4:
                objArr[0] = "name";
                break;
            case 5:
            case 8:
                objArr[0] = "owner";
                break;
            case 6:
            case 9:
            case 12:
                objArr[0] = "fieldType";
                break;
            case 7:
            case 10:
            case 13:
                objArr[0] = "fieldName";
                break;
            case 11:
                objArr[0] = "ownerType";
                break;
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/codegen/FieldInfo";
                break;
        }
        switch (i) {
            case 14:
                objArr[1] = "getFieldType";
                break;
            case 15:
                objArr[1] = "getOwnerType";
                break;
            case 16:
                objArr[1] = "getOwnerInternalName";
                break;
            case 17:
                objArr[1] = "getFieldName";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/codegen/FieldInfo";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "createSingletonViaInstance";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "createForHiddenField";
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "<init>";
                break;
            case 14:
            case 15:
            case 16:
            case 17:
                break;
            default:
                objArr[2] = "createForSingleton";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public static FieldInfo createForSingleton(ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(1);
        }
        if (!classDescriptor.getKind().isSingleton() || DescriptorUtils.isEnumEntry(classDescriptor)) {
            throw new UnsupportedOperationException("Can't create singleton field for class: " + classDescriptor);
        }
        if (DescriptorUtils.isNonCompanionObject(classDescriptor) || CompanionObjectMappingUtilsKt.isMappedIntrinsicCompanionObject(CompanionObjectMapping.INSTANCE, classDescriptor)) {
            return createSingletonViaInstance(classDescriptor, kotlinTypeMapper, JvmAbi.INSTANCE_FIELD);
        }
        Type typeMapClass = kotlinTypeMapper.mapClass((ClassDescriptor) DescriptorUtils.getParentOfType(classDescriptor, ClassDescriptor.class));
        SimpleType defaultType = classDescriptor.getDefaultType();
        return new FieldInfo(typeMapClass, kotlinTypeMapper.mapType(defaultType), defaultType, classDescriptor.getShortName().asString(), true);
    }

    public static FieldInfo createSingletonViaInstance(ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper, String str) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        Type typeMapClass = kotlinTypeMapper.mapClass(classDescriptor);
        SimpleType defaultType = classDescriptor.getDefaultType();
        return new FieldInfo(typeMapClass, kotlinTypeMapper.mapType(defaultType), defaultType, str, true);
    }

    public static FieldInfo createForHiddenField(Type type, Type type2, String str) {
        if (type == null) {
            $$$reportNull$$$0(5);
        }
        if (type2 == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return createForHiddenField(type, type2, null, str);
    }

    public static FieldInfo createForHiddenField(Type type, Type type2, KotlinType kotlinType, String str) {
        if (type == null) {
            $$$reportNull$$$0(8);
        }
        if (type2 == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        return new FieldInfo(type, type2, kotlinType, str, false);
    }

    private FieldInfo(Type type, Type type2, KotlinType kotlinType, String str, boolean z) {
        if (type == null) {
            $$$reportNull$$$0(11);
        }
        if (type2 == null) {
            $$$reportNull$$$0(12);
        }
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        this.ownerType = type;
        this.fieldType = type2;
        this.fieldKotlinType = kotlinType;
        this.fieldName = str;
        this.isStatic = z;
    }

    public Type getFieldType() {
        Type type = this.fieldType;
        if (type == null) {
            $$$reportNull$$$0(14);
        }
        return type;
    }

    public KotlinType getFieldKotlinType() {
        return this.fieldKotlinType;
    }

    public Type getOwnerType() {
        Type type = this.ownerType;
        if (type == null) {
            $$$reportNull$$$0(15);
        }
        return type;
    }

    public String getOwnerInternalName() {
        String internalName = this.ownerType.getInternalName();
        if (internalName == null) {
            $$$reportNull$$$0(16);
        }
        return internalName;
    }

    public String getFieldName() {
        String str = this.fieldName;
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        return str;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public String toString() {
        return String.format("%s %s.%s : %s", this.isStatic ? "GETSTATIC" : "GETFIELD", this.ownerType.getInternalName(), this.fieldName, this.fieldType);
    }
}
