package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/ReflectionConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ReflectionConverter extends AbstractReflectionConverter {
    private Class type;

    public ReflectionConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    public ReflectionConverter(Mapper mapper, ReflectionProvider reflectionProvider, Class cls) {
        this(mapper, reflectionProvider);
        this.type = cls;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        Class cls2 = this.type;
        return ((cls2 != null && cls2 == cls) || (cls2 == null && cls != null)) && canAccess(cls);
    }
}
