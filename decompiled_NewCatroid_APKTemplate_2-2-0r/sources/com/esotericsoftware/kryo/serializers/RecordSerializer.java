package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.minlog.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: classes8.dex */
public class RecordSerializer<T> extends ImmutableSerializer<T> {
    private static final Method GET_NAME;
    private static final Method GET_RECORD_COMPONENTS;
    private static final Method GET_TYPE;
    private static final Method IS_RECORD;

    static {
        Method declaredMethod;
        Method method;
        Method method2;
        Method method3;
        try {
            Class<?> cls = Class.forName("java.lang.reflect.RecordComponent");
            declaredMethod = Class.class.getDeclaredMethod("isRecord", new Class[0]);
            method2 = Class.class.getMethod("getRecordComponents", new Class[0]);
            method3 = cls.getMethod("getName", new Class[0]);
            method = cls.getMethod("getType", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            declaredMethod = null;
            method = null;
            method2 = null;
            method3 = null;
        }
        IS_RECORD = declaredMethod;
        GET_RECORD_COMPONENTS = method2;
        GET_NAME = method3;
        GET_TYPE = method;
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, T t) {
        Class<?> cls = t.getClass();
        if (!isRecord(cls)) {
            throw new KryoException(t + " is not a record");
        }
        for (RecordComponent recordComponent : recordComponents(cls, Comparator.comparing(new RecordSerializer$$ExternalSyntheticLambda0()))) {
            Class<?> clsType = recordComponent.type();
            String strName = recordComponent.name();
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Write property: " + strName + " (" + clsType.getName() + Constant.AFTER_QUTO);
                }
                if (recordComponent.type().isPrimitive()) {
                    kryo.writeObject(output, componentValue(t, recordComponent));
                } else {
                    kryo.writeObjectOrNull(output, componentValue(t, recordComponent), clsType);
                }
            } catch (KryoException e) {
                e.addTrace(strName + " (" + clsType.getName() + Constant.AFTER_QUTO);
                throw e;
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(strName + " (" + clsType.getName() + Constant.AFTER_QUTO);
                throw kryoException;
            }
        }
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public T read(Kryo kryo, Input input, Class<? extends T> cls) {
        if (!isRecord(cls)) {
            throw new KryoException("Not a record (" + cls + Constant.AFTER_QUTO);
        }
        RecordComponent[] recordComponentArrRecordComponents = recordComponents(cls, Comparator.comparing(new RecordSerializer$$ExternalSyntheticLambda0()));
        Object[] objArr = new Object[recordComponentArrRecordComponents.length];
        for (RecordComponent recordComponent : recordComponentArrRecordComponents) {
            String strName = recordComponent.name();
            try {
                if (Log.TRACE) {
                    Log.trace("kryo", "Read property: " + strName + " (" + cls.getName() + Constant.AFTER_QUTO);
                }
                objArr[recordComponent.index()] = recordComponent.type().isPrimitive() ? kryo.readObject(input, recordComponent.type()) : kryo.readObjectOrNull(input, recordComponent.type());
            } catch (KryoException e) {
                e.addTrace(strName + " (" + cls.getName() + Constant.AFTER_QUTO);
                throw e;
            } catch (Throwable th) {
                KryoException kryoException = new KryoException(th);
                kryoException.addTrace(strName + " (" + cls.getName() + Constant.AFTER_QUTO);
                throw kryoException;
            }
        }
        Arrays.sort(recordComponentArrRecordComponents, Comparator.comparing(new Function() { // from class: com.esotericsoftware.kryo.serializers.RecordSerializer$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((RecordSerializer.RecordComponent) obj).index());
            }
        }));
        return (T) invokeCanonicalConstructor(cls, recordComponentArrRecordComponents, objArr);
    }

    private boolean isRecord(Class<?> cls) {
        try {
            return ((Boolean) IS_RECORD.invoke(cls, new Object[0])).booleanValue();
        } catch (Throwable unused) {
            throw new KryoException("Could not determine type (" + cls + Constant.AFTER_QUTO);
        }
    }

    static final class RecordComponent {
        private final int index;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f135name;
        private final Class<?> type;

        RecordComponent(String str, Class<?> cls, int i) {
            this.f135name = str;
            this.type = cls;
            this.index = i;
        }

        String name() {
            return this.f135name;
        }

        Class<?> type() {
            return this.type;
        }

        int index() {
            return this.index;
        }
    }

    private static <T> RecordComponent[] recordComponents(Class<T> cls, Comparator<RecordComponent> comparator) {
        try {
            Object[] objArr = (Object[]) GET_RECORD_COMPONENTS.invoke(cls, new Object[0]);
            RecordComponent[] recordComponentArr = new RecordComponent[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                recordComponentArr[i] = new RecordComponent((String) GET_NAME.invoke(obj, new Object[0]), (Class) GET_TYPE.invoke(obj, new Object[0]), i);
            }
            if (comparator != null) {
                Arrays.sort(recordComponentArr, comparator);
            }
            return recordComponentArr;
        } catch (Throwable th) {
            KryoException kryoException = new KryoException(th);
            kryoException.addTrace("Could not retrieve record components (" + cls.getName() + Constant.AFTER_QUTO);
            throw kryoException;
        }
    }

    private static Object componentValue(Object obj, RecordComponent recordComponent) {
        try {
            return obj.getClass().getDeclaredMethod(recordComponent.name(), new Class[0]).invoke(obj, new Object[0]);
        } catch (Throwable th) {
            KryoException kryoException = new KryoException(th);
            kryoException.addTrace("Could not retrieve record components (" + obj.getClass().getName() + Constant.AFTER_QUTO);
            throw kryoException;
        }
    }

    private static <T> T invokeCanonicalConstructor(Class<T> cls, RecordComponent[] recordComponentArr, Object[] objArr) {
        try {
            return cls.getConstructor((Class[]) Arrays.stream(recordComponentArr).map(new Function() { // from class: com.esotericsoftware.kryo.serializers.RecordSerializer$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((RecordSerializer.RecordComponent) obj).type();
                }
            }).toArray(new IntFunction() { // from class: com.esotericsoftware.kryo.serializers.RecordSerializer$$ExternalSyntheticLambda3
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return RecordSerializer.lambda$invokeCanonicalConstructor$0(i);
                }
            })).newInstance(objArr);
        } catch (Throwable th) {
            KryoException kryoException = new KryoException(th);
            kryoException.addTrace("Could not construct type (" + cls.getName() + Constant.AFTER_QUTO);
            throw kryoException;
        }
    }

    static /* synthetic */ Class[] lambda$invokeCanonicalConstructor$0(int i) {
        return new Class[i];
    }
}
