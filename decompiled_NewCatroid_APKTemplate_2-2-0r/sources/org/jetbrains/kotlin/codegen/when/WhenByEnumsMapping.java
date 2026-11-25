package org.jetbrains.kotlin.codegen.when;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.resolve.constants.EnumValue;

/* JADX INFO: loaded from: classes3.dex */
public class WhenByEnumsMapping {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String MAPPINGS_CLASS_NAME_POSTFIX = "$WhenMappings";
    public static final String MAPPING_ARRAY_FIELD_PREFIX = "$EnumSwitchMapping$";
    private final ClassDescriptor enumClassDescriptor;
    private final int fieldNumber;
    private final boolean isPublicAbi;
    private final Map<EnumValue, Integer> map;
    private final String mappingsClassInternalName;
    private final String outerClassInternalNameForExpression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "outerClassInternalNameForExpression";
                break;
            case 2:
            case 3:
                objArr[0] = "value";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/codegen/when/WhenByEnumsMapping";
                break;
            default:
                objArr[0] = "enumClassDescriptor";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getFieldName";
                break;
            case 5:
                objArr[1] = "getEnumClassDescriptor";
                break;
            case 6:
                objArr[1] = "getOuterClassInternalNameForExpression";
                break;
            case 7:
                objArr[1] = "getMappingsClassInternalName";
                break;
            case 8:
                objArr[1] = "enumValuesToIntMapping";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/codegen/when/WhenByEnumsMapping";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "getIndexByEntry";
                break;
            case 3:
                objArr[2] = "putFirstTime";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public WhenByEnumsMapping(ClassDescriptor classDescriptor, String str, int i, boolean z) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.map = new LinkedHashMap();
        this.enumClassDescriptor = classDescriptor;
        this.outerClassInternalNameForExpression = str;
        this.mappingsClassInternalName = str + MAPPINGS_CLASS_NAME_POSTFIX;
        this.fieldNumber = i;
        this.isPublicAbi = z;
    }

    public int getIndexByEntry(EnumValue enumValue) {
        if (enumValue == null) {
            $$$reportNull$$$0(2);
        }
        return this.map.get(enumValue).intValue();
    }

    public void putFirstTime(EnumValue enumValue, int i) {
        if (enumValue == null) {
            $$$reportNull$$$0(3);
        }
        if (this.map.containsKey(enumValue)) {
            return;
        }
        this.map.put(enumValue, Integer.valueOf(i));
    }

    public int size() {
        return this.map.size();
    }

    public String getFieldName() {
        String str = MAPPING_ARRAY_FIELD_PREFIX + this.fieldNumber;
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return str;
    }

    public ClassDescriptor getEnumClassDescriptor() {
        ClassDescriptor classDescriptor = this.enumClassDescriptor;
        if (classDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        return classDescriptor;
    }

    public String getOuterClassInternalNameForExpression() {
        String str = this.outerClassInternalNameForExpression;
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return str;
    }

    public String getMappingsClassInternalName() {
        String str = this.mappingsClassInternalName;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }

    public boolean isPublicAbi() {
        return this.isPublicAbi;
    }

    public Iterable<Map.Entry<EnumValue, Integer>> enumValuesToIntMapping() {
        Set<Map.Entry<EnumValue, Integer>> setEntrySet = this.map.entrySet();
        if (setEntrySet == null) {
            $$$reportNull$$$0(8);
        }
        return setEntrySet;
    }
}
