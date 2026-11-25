package org.apache.commons.collections.functors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.FunctorException;

/* JADX INFO: loaded from: classes12.dex */
public class PrototypeFactory {
    static /* synthetic */ Class class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeCloneFactory;
    static /* synthetic */ Class class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeSerializationFactory;

    public static Factory getInstance(Object obj) {
        if (obj == null) {
            return ConstantFactory.NULL_INSTANCE;
        }
        try {
            try {
                return new PrototypeCloneFactory(obj, obj.getClass().getMethod("clone", null));
            } catch (NoSuchMethodException unused) {
                obj.getClass().getConstructor(obj.getClass());
                return new InstantiateFactory(obj.getClass(), new Class[]{obj.getClass()}, new Object[]{obj});
            }
        } catch (NoSuchMethodException unused2) {
            if (obj instanceof Serializable) {
                return new PrototypeSerializationFactory((Serializable) obj);
            }
            throw new IllegalArgumentException("The prototype must be cloneable via a public clone method");
        }
    }

    private PrototypeFactory() {
    }

    static class PrototypeCloneFactory implements Factory, Serializable {
        private static final long serialVersionUID = 5604271422565175555L;
        private transient Method iCloneMethod;
        private final Object iPrototype;

        private PrototypeCloneFactory(Object obj, Method method) {
            this.iPrototype = obj;
            this.iCloneMethod = method;
        }

        private void findCloneMethod() {
            try {
                this.iCloneMethod = this.iPrototype.getClass().getMethod("clone", null);
            } catch (NoSuchMethodException unused) {
                throw new IllegalArgumentException("PrototypeCloneFactory: The clone method must exist and be public ");
            }
        }

        @Override // org.apache.commons.collections.Factory
        public Object create() {
            if (this.iCloneMethod == null) {
                findCloneMethod();
            }
            try {
                return this.iCloneMethod.invoke(this.iPrototype, null);
            } catch (IllegalAccessException e) {
                throw new FunctorException("PrototypeCloneFactory: Clone method must be public", e);
            } catch (InvocationTargetException e2) {
                throw new FunctorException("PrototypeCloneFactory: Clone method threw an exception", e2);
            }
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Class clsClass$;
            if (PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeCloneFactory == null) {
                clsClass$ = PrototypeFactory.class$("org.apache.commons.collections.functors.PrototypeFactory$PrototypeCloneFactory");
                PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeCloneFactory = clsClass$;
            } else {
                clsClass$ = PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeCloneFactory;
            }
            FunctorUtils.checkUnsafeSerialization(clsClass$);
            objectOutputStream.defaultWriteObject();
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            Class clsClass$;
            if (PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeCloneFactory == null) {
                clsClass$ = PrototypeFactory.class$("org.apache.commons.collections.functors.PrototypeFactory$PrototypeCloneFactory");
                PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeCloneFactory = clsClass$;
            } else {
                clsClass$ = PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeCloneFactory;
            }
            FunctorUtils.checkUnsafeSerialization(clsClass$);
            objectInputStream.defaultReadObject();
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    static class PrototypeSerializationFactory implements Factory, Serializable {
        private static final long serialVersionUID = -8704966966139178833L;
        private final Serializable iPrototype;

        private PrototypeSerializationFactory(Serializable serializable) {
            this.iPrototype = serializable;
        }

        /* JADX WARN: Code duplicated, block: B:38:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // org.apache.commons.collections.Factory
        public Object create() throws Throwable {
            Throwable th;
            ClassNotFoundException e;
            IOException e2;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
            ByteArrayInputStream byteArrayInputStream = null;
            try {
                try {
                    new ObjectOutputStream(byteArrayOutputStream).writeObject(this.iPrototype);
                    ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    try {
                        Object object = new ObjectInputStream(byteArrayInputStream2).readObject();
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException unused) {
                        }
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException unused2) {
                        }
                        return object;
                    } catch (IOException e3) {
                        e2 = e3;
                        throw new FunctorException(e2);
                    } catch (ClassNotFoundException e4) {
                        e = e4;
                        throw new FunctorException(e);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (0 != 0) {
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    try {
                        byteArrayOutputStream.close();
                        throw th;
                    } catch (IOException unused4) {
                        throw th;
                    }
                }
            } catch (IOException e5) {
                e2 = e5;
            } catch (ClassNotFoundException e6) {
                e = e6;
            } catch (Throwable th3) {
                th = th3;
                if (0 != 0) {
                    byteArrayInputStream.close();
                }
                byteArrayOutputStream.close();
                throw th;
            }
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Class clsClass$;
            if (PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeSerializationFactory == null) {
                clsClass$ = PrototypeFactory.class$("org.apache.commons.collections.functors.PrototypeFactory$PrototypeSerializationFactory");
                PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeSerializationFactory = clsClass$;
            } else {
                clsClass$ = PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeSerializationFactory;
            }
            FunctorUtils.checkUnsafeSerialization(clsClass$);
            objectOutputStream.defaultWriteObject();
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            Class clsClass$;
            if (PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeSerializationFactory == null) {
                clsClass$ = PrototypeFactory.class$("org.apache.commons.collections.functors.PrototypeFactory$PrototypeSerializationFactory");
                PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeSerializationFactory = clsClass$;
            } else {
                clsClass$ = PrototypeFactory.class$org$apache$commons$collections$functors$PrototypeFactory$PrototypeSerializationFactory;
            }
            FunctorUtils.checkUnsafeSerialization(clsClass$);
            objectInputStream.defaultReadObject();
        }
    }
}
