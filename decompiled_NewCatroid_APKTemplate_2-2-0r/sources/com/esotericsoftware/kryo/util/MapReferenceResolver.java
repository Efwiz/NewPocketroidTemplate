package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ReferenceResolver;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes8.dex */
public class MapReferenceResolver implements ReferenceResolver {
    private static final int DEFAULT_CAPACITY = 2048;
    protected Kryo kryo;
    private final int maximumCapacity;
    protected final ArrayList<Object> readObjects;
    protected final IdentityObjectIntMap<Object> writtenObjects;

    public MapReferenceResolver() {
        this(2048);
    }

    public MapReferenceResolver(int i) {
        this.writtenObjects = new IdentityObjectIntMap<>();
        this.readObjects = new ArrayList<>();
        this.maximumCapacity = i;
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public void setKryo(Kryo kryo) {
        this.kryo = kryo;
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public int addWrittenObject(Object obj) {
        int i = this.writtenObjects.size;
        this.writtenObjects.put(obj, i);
        return i;
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public int getWrittenId(Object obj) {
        return this.writtenObjects.get(obj, -1);
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public int nextReadId(Class cls) {
        int size = this.readObjects.size();
        this.readObjects.add(null);
        return size;
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public void setReadObject(int i, Object obj) {
        this.readObjects.set(i, obj);
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public Object getReadObject(Class cls, int i) {
        return this.readObjects.get(i);
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public void reset() {
        int size = this.readObjects.size();
        this.readObjects.clear();
        if (size > this.maximumCapacity) {
            this.readObjects.trimToSize();
            this.readObjects.ensureCapacity(this.maximumCapacity);
        }
        this.writtenObjects.clear(this.maximumCapacity);
    }

    @Override // com.esotericsoftware.kryo.ReferenceResolver
    public boolean useReferences(Class cls) {
        return (Util.isWrapperClass(cls) || Util.isEnum(cls)) ? false : true;
    }
}
