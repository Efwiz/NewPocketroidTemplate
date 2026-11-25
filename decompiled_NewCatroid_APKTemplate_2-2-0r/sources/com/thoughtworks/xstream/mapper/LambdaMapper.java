package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.core.util.Types;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/LambdaMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class LambdaMapper extends MapperWrapper {
    public LambdaMapper(Mapper mapper) {
        super(mapper);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String serializedClass(Class cls) {
        Class<?> cls2 = null;
        if (Types.isLambdaType(cls)) {
            if (Serializable.class.isAssignableFrom(cls)) {
                Class<?>[] interfaces = cls.getInterfaces();
                if (interfaces.length > 1) {
                    for (int i = 0; cls2 == null && i < interfaces.length; i++) {
                        Class<?> cls3 = interfaces[i];
                        for (Method method : cls3.getMethods()) {
                            if (!method.isDefault() && !Modifier.isStatic(method.getModifiers())) {
                                cls2 = cls3;
                                break;
                            }
                        }
                    }
                } else {
                    cls2 = interfaces[0];
                }
            } else {
                cls2 = Mapper.Null.class;
            }
        }
        if (cls2 != null) {
            cls = cls2;
        }
        return super.serializedClass(cls);
    }
}
