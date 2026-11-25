package com.thoughtworks.xstream.core.util;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/ClassLoaderReference.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ClassLoaderReference extends ClassLoader {
    private transient ClassLoader reference;

    public ClassLoaderReference(ClassLoader classLoader) {
        this.reference = classLoader;
    }

    @Override // java.lang.ClassLoader
    public Class loadClass(String str) throws ClassNotFoundException {
        return this.reference.loadClass(str);
    }

    public ClassLoader getReference() {
        return this.reference;
    }

    public void setReference(ClassLoader classLoader) {
        this.reference = classLoader;
    }

    private Object writeReplace() {
        return new Replacement();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/ClassLoaderReference$Replacement.class
      classes11.dex
     */
    static class Replacement {
        Replacement() {
        }

        private Object readResolve() {
            return new ClassLoaderReference(new CompositeClassLoader());
        }
    }
}
