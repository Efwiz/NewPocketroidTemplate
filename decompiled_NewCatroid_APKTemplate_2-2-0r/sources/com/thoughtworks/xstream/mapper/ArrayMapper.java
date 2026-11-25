package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.core.util.Primitives;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/ArrayMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ArrayMapper extends MapperWrapper {
    public ArrayMapper(Mapper mapper) {
        super(mapper);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String serializedClass(Class cls) {
        String strSerializedClass;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            if (!cls.isArray()) {
                strSerializedClass = null;
                break;
            }
            strSerializedClass = super.serializedClass(cls);
            if (!cls.getName().equals(strSerializedClass)) {
                break;
            }
            cls = cls.getComponentType();
            stringBuffer.append("-array");
        }
        if (strSerializedClass == null) {
            strSerializedClass = boxedTypeName(cls);
        }
        if (strSerializedClass == null) {
            strSerializedClass = super.serializedClass(cls);
        }
        return stringBuffer.length() > 0 ? new StringBuffer().append(strSerializedClass).append((Object) stringBuffer).toString() : strSerializedClass;
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Class realClass(String str) {
        int i = 0;
        while (str.endsWith("-array")) {
            str = str.substring(0, str.length() - 6);
            i++;
        }
        if (i > 0) {
            Class<?> clsPrimitiveType = Primitives.primitiveType(str);
            if (clsPrimitiveType == null) {
                clsPrimitiveType = super.realClass(str);
            }
            while (clsPrimitiveType.isArray()) {
                clsPrimitiveType = clsPrimitiveType.getComponentType();
                i++;
            }
            return super.realClass(arrayType(i, clsPrimitiveType));
        }
        return super.realClass(str);
    }

    private String arrayType(int i, Class cls) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append('[');
        }
        if (cls.isPrimitive()) {
            stringBuffer.append(Primitives.representingChar(cls));
            return stringBuffer.toString();
        }
        stringBuffer.append('L').append(cls.getName()).append(';');
        return stringBuffer.toString();
    }

    private String boxedTypeName(Class cls) {
        if (Primitives.isBoxed(cls)) {
            return cls.getName();
        }
        return null;
    }
}
