package com.thoughtworks.xstream.converters.javabean;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/JavaBeanProvider.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public interface JavaBeanProvider {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/javabean/JavaBeanProvider$Visitor.class
      classes11.dex
     */
    public interface Visitor {
        boolean shouldVisit(String str, Class cls);

        void visit(String str, Class cls, Class cls2, Object obj);
    }

    boolean canInstantiate(Class cls);

    Class getPropertyType(Object obj, String str);

    Object newInstance(Class cls);

    boolean propertyDefinedInClass(String str, Class cls);

    void visitSerializableProperties(Object obj, Visitor visitor);

    void writeProperty(Object obj, String str, Object obj2);
}
