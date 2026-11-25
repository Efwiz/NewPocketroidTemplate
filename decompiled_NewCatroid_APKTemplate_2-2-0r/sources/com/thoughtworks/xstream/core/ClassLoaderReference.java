package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.core.util.CompositeClassLoader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ClassLoaderReference.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public final class ClassLoaderReference {
    private transient ClassLoader reference;

    public ClassLoaderReference(ClassLoader classLoader) {
        setReference(classLoader);
    }

    public ClassLoader getReference() {
        return this.reference;
    }

    public void setReference(ClassLoader classLoader) {
        if (classLoader instanceof com.thoughtworks.xstream.core.util.ClassLoaderReference) {
            classLoader = ((com.thoughtworks.xstream.core.util.ClassLoaderReference) classLoader).getReference();
        }
        this.reference = classLoader;
    }

    private Object readResolve() {
        this.reference = new CompositeClassLoader();
        return this;
    }
}
