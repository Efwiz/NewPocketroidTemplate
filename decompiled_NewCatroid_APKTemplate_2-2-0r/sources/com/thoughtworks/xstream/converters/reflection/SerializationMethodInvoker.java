package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.core.Caching;
import com.thoughtworks.xstream.core.util.SerializationMembers;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/SerializationMethodInvoker.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SerializationMethodInvoker implements Caching {
    SerializationMembers serializationMembers = new SerializationMembers();

    public Object callReadResolve(Object obj) {
        return this.serializationMembers.callReadResolve(obj);
    }

    public Object callWriteReplace(Object obj) {
        return this.serializationMembers.callWriteReplace(obj);
    }

    public boolean supportsReadObject(Class cls, boolean z) {
        return this.serializationMembers.supportsReadObject(cls, z);
    }

    public void callReadObject(Class cls, Object obj, ObjectInputStream objectInputStream) throws Throwable {
        this.serializationMembers.callReadObject(cls, obj, objectInputStream);
    }

    public boolean supportsWriteObject(Class cls, boolean z) {
        return this.serializationMembers.supportsWriteObject(cls, z);
    }

    public void callWriteObject(Class cls, Object obj, ObjectOutputStream objectOutputStream) throws Throwable {
        this.serializationMembers.callWriteObject(cls, obj, objectOutputStream);
    }

    @Override // com.thoughtworks.xstream.core.Caching
    public void flushCache() {
        this.serializationMembers.flushCache();
    }
}
