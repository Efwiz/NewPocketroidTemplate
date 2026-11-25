package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.ObjectMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/* JADX INFO: loaded from: classes8.dex */
public class JavaSerializer extends Serializer {
    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, Object obj) {
        try {
            ObjectMap graphContext = kryo.getGraphContext();
            ObjectOutputStream objectOutputStream = (ObjectOutputStream) graphContext.get(this);
            if (objectOutputStream == null) {
                objectOutputStream = new ObjectOutputStream(output);
                graphContext.put(this, objectOutputStream);
            }
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        } catch (Exception e) {
            throw new KryoException("Error during Java serialization.", e);
        }
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public Object read(Kryo kryo, Input input, Class cls) {
        try {
            ObjectMap graphContext = kryo.getGraphContext();
            ObjectInputStream objectInputStreamWithKryoClassLoader = (ObjectInputStream) graphContext.get(this);
            if (objectInputStreamWithKryoClassLoader == null) {
                objectInputStreamWithKryoClassLoader = new ObjectInputStreamWithKryoClassLoader(input, kryo);
                graphContext.put(this, objectInputStreamWithKryoClassLoader);
            }
            return objectInputStreamWithKryoClassLoader.readObject();
        } catch (Exception e) {
            throw new KryoException("Error during Java deserialization.", e);
        }
    }

    private static class ObjectInputStreamWithKryoClassLoader extends ObjectInputStream {
        private final Kryo kryo;

        ObjectInputStreamWithKryoClassLoader(InputStream inputStream, Kryo kryo) throws IOException {
            super(inputStream);
            this.kryo = kryo;
        }

        @Override // java.io.ObjectInputStream
        protected Class resolveClass(ObjectStreamClass objectStreamClass) {
            try {
                return Class.forName(objectStreamClass.getName(), false, this.kryo.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new KryoException("Class not found: " + objectStreamClass.getName(), e);
            }
        }
    }
}
