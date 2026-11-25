package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Util;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes8.dex */
public class ClosureSerializer extends Serializer {
    private static Method readResolve;

    public static class Closure {
    }

    public ClosureSerializer() {
        if (readResolve == null) {
            try {
                Method declaredMethod = SerializedLambda.class.getDeclaredMethod("readResolve", new Class[0]);
                readResolve = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Exception e) {
                throw new KryoException("Unable to obtain SerializedLambda#readResolve via reflection.", e);
            }
        }
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, Object obj) {
        SerializedLambda serializedLambda = toSerializedLambda(obj);
        int capturedArgCount = serializedLambda.getCapturedArgCount();
        output.writeVarInt(capturedArgCount, true);
        for (int i = 0; i < capturedArgCount; i++) {
            kryo.writeClassAndObject(output, serializedLambda.getCapturedArg(i));
        }
        try {
            kryo.writeClass(output, Class.forName(serializedLambda.getCapturingClass().replace('/', '.')));
            output.writeString(serializedLambda.getFunctionalInterfaceClass());
            output.writeString(serializedLambda.getFunctionalInterfaceMethodName());
            output.writeString(serializedLambda.getFunctionalInterfaceMethodSignature());
            output.writeVarInt(serializedLambda.getImplMethodKind(), true);
            output.writeString(serializedLambda.getImplClass());
            output.writeString(serializedLambda.getImplMethodName());
            output.writeString(serializedLambda.getImplMethodSignature());
            output.writeString(serializedLambda.getInstantiatedMethodType());
        } catch (ClassNotFoundException e) {
            throw new KryoException("Error writing closure.", e);
        }
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public Object read(Kryo kryo, Input input, Class cls) {
        int varInt = input.readVarInt(true);
        Object[] objArr = new Object[varInt];
        for (int i = 0; i < varInt; i++) {
            objArr[i] = kryo.readClassAndObject(input);
        }
        try {
            return readResolve.invoke(new SerializedLambda(kryo.readClass(input).getType(), input.readString(), input.readString(), input.readString(), input.readVarInt(true), input.readString(), input.readString(), input.readString(), input.readString(), objArr), new Object[0]);
        } catch (Exception e) {
            throw new KryoException("Error reading closure.", e);
        }
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public Object copy(Kryo kryo, Object obj) {
        try {
            return readResolve.invoke(toSerializedLambda(obj), new Object[0]);
        } catch (Exception e) {
            throw new KryoException("Error copying closure.", e);
        }
    }

    private SerializedLambda toSerializedLambda(Object obj) {
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod("writeReplace", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(obj, new Object[0]);
            try {
                return (SerializedLambda) objInvoke;
            } catch (Exception e) {
                throw new KryoException("writeReplace must return a SerializedLambda: " + (objInvoke == null ? null : Util.className(objInvoke.getClass())), e);
            }
        } catch (Exception e2) {
            if (obj instanceof Serializable) {
                throw new KryoException("Error serializing closure.", e2);
            }
            throw new KryoException("Closure must implement java.io.Serializable.", e2);
        }
    }
}
