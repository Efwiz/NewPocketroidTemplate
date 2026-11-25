package com.reandroid.arsc.coder;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.apkeditor.Options;
import com.reandroid.arsc.value.ValueType;
import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: loaded from: classes10.dex */
public class CommonType {
    public static final CommonType ANIM;
    public static final CommonType ANIMATOR;
    public static final CommonType BOOL;
    public static final CommonType COLOR;
    private static final String[] COMPLEX_TYPES;
    public static final CommonType DIMEN;
    public static final CommonType DRAWABLE;
    public static final CommonType FONT;
    public static final CommonType FRACTION;
    public static final CommonType ID;
    public static final CommonType INTEGER;
    public static final CommonType INTERPOLATOR;
    public static final CommonType LAYOUT;
    public static final CommonType MENU;
    public static final CommonType MIPMAP;
    public static final CommonType NAVIGATION;
    public static final CommonType RAW;
    public static final CommonType STRING;
    public static final CommonType TRANSITION;
    private static final Map<String, CommonType> TYPE_MAP;
    private static final CommonType[] VALUES;
    public static final CommonType XML;
    private final int hash;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f224name;
    private final ValueType[] valueTypes;

    public boolean equals(Object obj) {
        return obj == this;
    }

    static {
        HashMap map = new HashMap();
        TYPE_MAP = map;
        CommonType commonType = new CommonType("anim", new ValueType[]{ValueType.STRING});
        map.put(commonType.f224name, commonType);
        ANIM = commonType;
        CommonType commonType2 = new CommonType("animator", new ValueType[]{ValueType.STRING});
        map.put(commonType2.f224name, commonType2);
        ANIMATOR = commonType2;
        CommonType commonType3 = new CommonType("bool", new ValueType[]{ValueType.BOOLEAN});
        map.put(commonType3.f224name, commonType3);
        BOOL = commonType3;
        CommonType commonType4 = new CommonType(TypedValues.Custom.S_COLOR, new ValueType[]{ValueType.COLOR_ARGB8, ValueType.COLOR_RGB8, ValueType.COLOR_RGB4, ValueType.COLOR_ARGB4});
        map.put(commonType4.f224name, commonType4);
        COLOR = commonType4;
        CommonType commonType5 = new CommonType("dimen", new ValueType[]{ValueType.DIMENSION, ValueType.FRACTION, ValueType.FLOAT, ValueType.DEC});
        map.put(commonType5.f224name, commonType5);
        DIMEN = commonType5;
        CommonType commonType6 = new CommonType("drawable", new ValueType[]{ValueType.COLOR_ARGB8, ValueType.COLOR_RGB8, ValueType.COLOR_RGB4, ValueType.COLOR_ARGB4, ValueType.STRING});
        map.put(commonType6.f224name, commonType6);
        DRAWABLE = commonType6;
        CommonType commonType7 = new CommonType("fraction", new ValueType[]{ValueType.FRACTION});
        map.put(commonType7.f224name, commonType7);
        FRACTION = commonType7;
        CommonType commonType8 = new CommonType("font", new ValueType[]{ValueType.STRING});
        map.put(commonType8.f224name, commonType8);
        FONT = commonType8;
        CommonType commonType9 = new CommonType("id", new ValueType[]{ValueType.BOOLEAN, ValueType.STRING});
        map.put(commonType9.f224name, commonType9);
        ID = commonType9;
        CommonType commonType10 = new CommonType("integer", new ValueType[]{ValueType.DEC, ValueType.HEX});
        map.put(commonType10.f224name, commonType10);
        INTEGER = commonType10;
        CommonType commonType11 = new CommonType("interpolator", new ValueType[]{ValueType.STRING});
        map.put(commonType11.f224name, commonType11);
        INTERPOLATOR = commonType11;
        CommonType commonType12 = new CommonType("layout", new ValueType[]{ValueType.STRING});
        map.put(commonType12.f224name, commonType12);
        LAYOUT = commonType12;
        CommonType commonType13 = new CommonType(LineReader.MENU, new ValueType[]{ValueType.STRING});
        map.put(commonType13.f224name, commonType13);
        MENU = commonType13;
        CommonType commonType14 = new CommonType("mipmap", new ValueType[]{ValueType.COLOR_ARGB8, ValueType.COLOR_RGB8, ValueType.COLOR_RGB4, ValueType.COLOR_ARGB4, ValueType.STRING});
        map.put(commonType14.f224name, commonType14);
        MIPMAP = commonType14;
        CommonType commonType15 = new CommonType("navigation", new ValueType[]{ValueType.STRING});
        map.put(commonType15.f224name, commonType15);
        NAVIGATION = commonType15;
        CommonType commonType16 = new CommonType(Options.TYPE_RAW, new ValueType[]{ValueType.STRING});
        map.put(commonType16.f224name, commonType16);
        RAW = commonType16;
        CommonType commonType17 = new CommonType("string", new ValueType[]{ValueType.STRING});
        map.put(commonType17.f224name, commonType17);
        STRING = commonType17;
        CommonType commonType18 = new CommonType("transition", new ValueType[]{ValueType.STRING});
        map.put(commonType18.f224name, commonType18);
        TRANSITION = commonType18;
        CommonType commonType19 = new CommonType("xml", new ValueType[]{ValueType.STRING});
        map.put(commonType19.f224name, commonType19);
        XML = commonType19;
        VALUES = new CommonType[]{commonType, commonType2, commonType3, commonType4, commonType5, commonType6, commonType7, commonType8, commonType9, commonType10, commonType11, commonType12, commonType13, commonType14, commonType15, commonType16, commonType17, commonType18, commonType19};
        COMPLEX_TYPES = new String[]{ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED, "array", "plurals", "style"};
    }

    private CommonType(String str, ValueType[] valueTypeArr) {
        this.f224name = str;
        this.valueTypes = valueTypeArr;
        int iHashCode = str.hashCode();
        this.hash = (iHashCode * 31) + iHashCode;
    }

    public String name() {
        return this.f224name;
    }

    public ValueType[] valueTypes() {
        return this.valueTypes;
    }

    public boolean isDifferent(ValueType valueType) {
        if (valueType == null || valueType == ValueType.NULL || valueType.isReference()) {
            return false;
        }
        return !contains(valueType);
    }

    public boolean contains(ValueType valueType) {
        for (ValueType valueType2 : this.valueTypes) {
            if (valueType2 == valueType) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.hash;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f224name);
        sb.append(", types = [");
        ValueType[] valueTypeArr = this.valueTypes;
        for (int i = 0; i < valueTypeArr.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(valueTypeArr[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static CommonType[] getValues() {
        return (CommonType[]) VALUES.clone();
    }

    public static CommonType valueOf(String str) {
        return TYPE_MAP.get(str);
    }

    public static ValueType[] getExpectedTypes(String str) {
        CommonType commonTypeValueOf = valueOf(str);
        if (commonTypeValueOf != null) {
            return commonTypeValueOf.valueTypes();
        }
        return null;
    }

    public static boolean isCommonTypeName(String str) {
        return TYPE_MAP.containsKey(str) || isComplexTypeName(str);
    }

    public static boolean isComplexTypeName(String str) {
        for (String str2 : COMPLEX_TYPES) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
