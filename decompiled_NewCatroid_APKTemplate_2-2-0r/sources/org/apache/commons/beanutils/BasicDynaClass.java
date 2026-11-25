package org.apache.commons.beanutils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes12.dex */
public class BasicDynaClass implements DynaClass, Serializable {
    protected static Class<?>[] constructorTypes = {DynaClass.class};
    protected transient Constructor<?> constructor;
    protected Object[] constructorValues;
    protected Class<?> dynaBeanClass;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f431name;
    protected DynaProperty[] properties;
    protected HashMap<String, DynaProperty> propertiesMap;

    public BasicDynaClass() {
        this(null, null, null);
    }

    public BasicDynaClass(String str, Class<?> cls) {
        this(str, cls, null);
    }

    /* JADX WARN: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r3v0, types: [java.lang.Class<?>] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public BasicDynaClass(java.lang.String r2, java.lang.Class r3, org.apache.commons.beanutils.DynaProperty[] r4) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.constructor = r0
            java.lang.Object[] r0 = new java.lang.Object[]{r1}
            r1.constructorValues = r0
            java.lang.Class<org.apache.commons.beanutils.BasicDynaBean> r0 = org.apache.commons.beanutils.BasicDynaBean.class
            r1.dynaBeanClass = r0
            java.lang.Class r0 = r1.getClass()
            java.lang.String r0 = r0.getName()
            r1.f431name = r0
            r0 = 0
            org.apache.commons.beanutils.DynaProperty[] r0 = new org.apache.commons.beanutils.DynaProperty[r0]
            r1.properties = r0
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r1.propertiesMap = r0
            if (r2 == 0) goto L2a
            r1.f431name = r2
        L2a:
            if (r3 != 0) goto L2e
            java.lang.Class<org.apache.commons.beanutils.BasicDynaBean> r3 = org.apache.commons.beanutils.BasicDynaBean.class
        L2e:
            r1.setDynaBeanClass(r3)
            if (r4 == 0) goto L36
            r1.setProperties(r4)
        L36:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.beanutils.BasicDynaClass.<init>(java.lang.String, java.lang.Class, org.apache.commons.beanutils.DynaProperty[]):void");
    }

    @Override // org.apache.commons.beanutils.DynaClass
    public String getName() {
        return this.f431name;
    }

    @Override // org.apache.commons.beanutils.DynaClass
    public DynaProperty getDynaProperty(String str) {
        if (str == null) {
            throw new IllegalArgumentException("No property name specified");
        }
        return this.propertiesMap.get(str);
    }

    @Override // org.apache.commons.beanutils.DynaClass
    public DynaProperty[] getDynaProperties() {
        return this.properties;
    }

    @Override // org.apache.commons.beanutils.DynaClass
    public DynaBean newInstance() throws IllegalAccessException, InstantiationException {
        try {
            if (this.constructor == null) {
                setDynaBeanClass(this.dynaBeanClass);
            }
            return (DynaBean) this.constructor.newInstance(this.constructorValues);
        } catch (InvocationTargetException e) {
            throw new InstantiationException(e.getTargetException().getMessage());
        }
    }

    public Class<?> getDynaBeanClass() {
        return this.dynaBeanClass;
    }

    protected void setDynaBeanClass(Class<?> cls) {
        if (cls.isInterface()) {
            throw new IllegalArgumentException("Class " + cls.getName() + " is an interface, not a class");
        }
        if (!DynaBean.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Class " + cls.getName() + " does not implement DynaBean");
        }
        try {
            this.constructor = cls.getConstructor(constructorTypes);
            this.dynaBeanClass = cls;
        } catch (NoSuchMethodException unused) {
            throw new IllegalArgumentException("Class " + cls.getName() + " does not have an appropriate constructor");
        }
    }

    protected void setProperties(DynaProperty[] dynaPropertyArr) {
        this.properties = dynaPropertyArr;
        this.propertiesMap.clear();
        for (DynaProperty dynaProperty : dynaPropertyArr) {
            this.propertiesMap.put(dynaProperty.getName(), dynaProperty);
        }
    }
}
