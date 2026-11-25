package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.objenesis.instantiator.ObjectInstantiator;

/* JADX INFO: loaded from: classes8.dex */
public class Registration {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f132id;
    private ObjectInstantiator instantiator;
    private Serializer serializer;
    private final Class type;
    private final boolean typeNameAscii;

    public Registration(Class cls, Serializer serializer, int i) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.type = cls;
        this.serializer = serializer;
        this.f132id = i;
        this.typeNameAscii = Util.isAscii(cls.getName());
    }

    public Class getType() {
        return this.type;
    }

    public boolean isTypeNameAscii() {
        return this.typeNameAscii;
    }

    public int getId() {
        return this.f132id;
    }

    public Serializer getSerializer() {
        return this.serializer;
    }

    public void setSerializer(Serializer serializer) {
        if (serializer == null) {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
        this.serializer = serializer;
        if (Log.TRACE) {
            Log.trace("kryo", "Update registered serializer: " + this.type.getName() + " (" + serializer.getClass().getName() + Constant.AFTER_QUTO);
        }
    }

    public ObjectInstantiator getInstantiator() {
        return this.instantiator;
    }

    public void setInstantiator(ObjectInstantiator objectInstantiator) {
        if (objectInstantiator == null) {
            throw new IllegalArgumentException("instantiator cannot be null.");
        }
        this.instantiator = objectInstantiator;
    }

    public String toString() {
        return "[" + this.f132id + ", " + Util.className(this.type) + "]";
    }
}
