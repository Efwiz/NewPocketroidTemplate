package com.thoughtworks.xstream.converters.reflection;

import java.lang.reflect.Field;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/ReflectionProviderWrapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ReflectionProviderWrapper implements ReflectionProvider {
    protected final ReflectionProvider wrapped;

    public ReflectionProviderWrapper(ReflectionProvider reflectionProvider) {
        this.wrapped = reflectionProvider;
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public boolean fieldDefinedInClass(String str, Class cls) {
        return this.wrapped.fieldDefinedInClass(str, cls);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Field getField(Class cls, String str) {
        return this.wrapped.getField(cls, str);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Field getFieldOrNull(Class cls, String str) {
        return this.wrapped.getFieldOrNull(cls, str);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Class getFieldType(Object obj, String str, Class cls) {
        return this.wrapped.getFieldType(obj, str, cls);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public Object newInstance(Class cls) {
        return this.wrapped.newInstance(cls);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public void visitSerializableFields(Object obj, ReflectionProvider.Visitor visitor) {
        this.wrapped.visitSerializableFields(obj, visitor);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionProvider
    public void writeField(Object obj, String str, Object obj2, Class cls) {
        this.wrapped.writeField(obj, str, obj2, cls);
    }
}
