package com.thoughtworks.xstream.converters.reflection;

import java.lang.reflect.Field;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/ReflectionProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface ReflectionProvider {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/ReflectionProvider$Visitor.class
      classes11.dex
     */
    public interface Visitor {
        void visit(String str, Class cls, Class cls2, Object obj);
    }

    boolean fieldDefinedInClass(String str, Class cls);

    Field getField(Class cls, String str);

    Field getFieldOrNull(Class cls, String str);

    Class getFieldType(Object obj, String str, Class cls);

    Object newInstance(Class cls);

    void visitSerializableFields(Object obj, Visitor visitor);

    void writeField(Object obj, String str, Object obj2, Class cls);
}
