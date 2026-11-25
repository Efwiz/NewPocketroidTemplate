package org.apache.commons.beanutils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes12.dex */
public class DynaProperty implements Serializable {
    private static final int BOOLEAN_TYPE = 1;
    private static final int BYTE_TYPE = 2;
    private static final int CHAR_TYPE = 3;
    private static final int DOUBLE_TYPE = 4;
    private static final int FLOAT_TYPE = 5;
    private static final int INT_TYPE = 6;
    private static final int LONG_TYPE = 7;
    private static final int SHORT_TYPE = 8;
    protected transient Class<?> contentType;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f432name;
    protected transient Class<?> type;

    public DynaProperty(String str) {
        this(str, Object.class);
    }

    public DynaProperty(String str, Class<?> cls) {
        this.f432name = str;
        this.type = cls;
        if (cls == null || !cls.isArray()) {
            return;
        }
        this.contentType = cls.getComponentType();
    }

    public DynaProperty(String str, Class<?> cls, Class<?> cls2) {
        this.f432name = str;
        this.type = cls;
        this.contentType = cls2;
    }

    public String getName() {
        return this.f432name;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Class<?> getContentType() {
        return this.contentType;
    }

    public boolean isIndexed() {
        Class<?> cls = this.type;
        if (cls == null) {
            return false;
        }
        return cls.isArray() || List.class.isAssignableFrom(this.type);
    }

    public boolean isMapped() {
        Class<?> cls = this.type;
        if (cls == null) {
            return false;
        }
        return Map.class.isAssignableFrom(cls);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0041  */
    public boolean equals(Object obj) {
        boolean z = true;
        boolean z2 = obj == this;
        if (z2 || !(obj instanceof DynaProperty)) {
            return z2;
        }
        DynaProperty dynaProperty = (DynaProperty) obj;
        String str = this.f432name;
        if (str != null ? !str.equals(dynaProperty.f432name) : dynaProperty.f432name != null) {
            z = false;
        } else {
            Class<?> cls = this.type;
            if (cls != null ? !cls.equals(dynaProperty.type) : dynaProperty.type != null) {
                z = false;
            } else {
                Class<?> cls2 = this.contentType;
                Class<?> cls3 = dynaProperty.contentType;
                if (cls2 != null ? !cls2.equals(cls3) : cls3 != null) {
                    z = false;
                }
            }
        }
        return z;
    }

    public int hashCode() {
        String str = this.f432name;
        int iHashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        Class<?> cls = this.type;
        int iHashCode2 = (iHashCode + (cls == null ? 0 : cls.hashCode())) * 31;
        Class<?> cls2 = this.contentType;
        return iHashCode2 + (cls2 != null ? cls2.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DynaProperty[name=");
        sb.append(this.f432name);
        sb.append(",type=");
        sb.append(this.type);
        if (isMapped() || isIndexed()) {
            sb.append(" <").append(this.contentType).append(">");
        }
        sb.append("]");
        return sb.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        writeAnyClass(this.type, objectOutputStream);
        if (isMapped() || isIndexed()) {
            writeAnyClass(this.contentType, objectOutputStream);
        }
        objectOutputStream.defaultWriteObject();
    }

    private void writeAnyClass(Class<?> cls, ObjectOutputStream objectOutputStream) throws IOException {
        int i;
        if (Boolean.TYPE.equals(cls)) {
            i = 1;
        } else if (Byte.TYPE.equals(cls)) {
            i = 2;
        } else if (Character.TYPE.equals(cls)) {
            i = 3;
        } else if (Double.TYPE.equals(cls)) {
            i = 4;
        } else if (Float.TYPE.equals(cls)) {
            i = 5;
        } else if (Integer.TYPE.equals(cls)) {
            i = 6;
        } else if (Long.TYPE.equals(cls)) {
            i = 7;
        } else {
            i = Short.TYPE.equals(cls) ? 8 : 0;
        }
        if (i == 0) {
            objectOutputStream.writeBoolean(false);
            objectOutputStream.writeObject(cls);
        } else {
            objectOutputStream.writeBoolean(true);
            objectOutputStream.writeInt(i);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        this.type = readAnyClass(objectInputStream);
        if (isMapped() || isIndexed()) {
            this.contentType = readAnyClass(objectInputStream);
        }
        objectInputStream.defaultReadObject();
    }

    private Class<?> readAnyClass(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        if (objectInputStream.readBoolean()) {
            switch (objectInputStream.readInt()) {
                case 1:
                    return Boolean.TYPE;
                case 2:
                    return Byte.TYPE;
                case 3:
                    return Character.TYPE;
                case 4:
                    return Double.TYPE;
                case 5:
                    return Float.TYPE;
                case 6:
                    return Integer.TYPE;
                case 7:
                    return Long.TYPE;
                case 8:
                    return Short.TYPE;
                default:
                    throw new StreamCorruptedException("Invalid primitive type. Check version of beanutils used to serialize is compatible.");
            }
        }
        return (Class) objectInputStream.readObject();
    }
}
