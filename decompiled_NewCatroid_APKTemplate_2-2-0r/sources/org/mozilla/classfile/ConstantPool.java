package org.mozilla.classfile;

import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.UintMap;

/* JADX INFO: loaded from: classes6.dex */
final class ConstantPool {
    static final byte CONSTANT_Class = 7;
    static final byte CONSTANT_Double = 6;
    static final byte CONSTANT_Fieldref = 9;
    static final byte CONSTANT_Float = 4;
    static final byte CONSTANT_Integer = 3;
    static final byte CONSTANT_InterfaceMethodref = 11;
    static final byte CONSTANT_InvokeDynamic = 18;
    static final byte CONSTANT_Long = 5;
    static final byte CONSTANT_MethodHandle = 15;
    static final byte CONSTANT_MethodType = 16;
    static final byte CONSTANT_Methodref = 10;
    static final byte CONSTANT_NameAndType = 12;
    static final byte CONSTANT_String = 8;
    static final byte CONSTANT_Utf8 = 1;
    private static final int ConstantPoolSize = 256;
    private static final int MAX_UTF_ENCODING_SIZE = 65535;
    private ClassFileWriter cfw;
    private UintMap itsStringConstHash = new UintMap();
    private ObjToIntMap itsUtf8Hash = new ObjToIntMap();
    private ObjToIntMap itsFieldRefHash = new ObjToIntMap();
    private ObjToIntMap itsMethodRefHash = new ObjToIntMap();
    private ObjToIntMap itsClassHash = new ObjToIntMap();
    private ObjToIntMap itsConstantHash = new ObjToIntMap();
    private UintMap itsConstantData = new UintMap();
    private UintMap itsPoolTypes = new UintMap();
    private int itsTopIndex = 1;
    private byte[] itsPool = new byte[256];
    private int itsTop = 0;

    ConstantPool(ClassFileWriter classFileWriter) {
        this.cfw = classFileWriter;
    }

    int write(byte[] bArr, int i) {
        int iPutInt16 = ClassFileWriter.putInt16((short) this.itsTopIndex, bArr, i);
        System.arraycopy(this.itsPool, 0, bArr, iPutInt16, this.itsTop);
        return iPutInt16 + this.itsTop;
    }

    int getWriteSize() {
        return this.itsTop + 2;
    }

    int addConstant(int i) {
        ensure(5);
        byte[] bArr = this.itsPool;
        int i2 = this.itsTop;
        int i3 = i2 + 1;
        this.itsTop = i3;
        bArr[i2] = 3;
        this.itsTop = ClassFileWriter.putInt32(i, bArr, i3);
        this.itsPoolTypes.put(this.itsTopIndex, 3);
        int i4 = this.itsTopIndex;
        this.itsTopIndex = i4 + 1;
        return (short) i4;
    }

    int addConstant(long j) {
        ensure(9);
        byte[] bArr = this.itsPool;
        int i = this.itsTop;
        int i2 = i + 1;
        this.itsTop = i2;
        bArr[i] = 5;
        this.itsTop = ClassFileWriter.putInt64(j, bArr, i2);
        int i3 = this.itsTopIndex;
        this.itsTopIndex = i3 + 2;
        this.itsPoolTypes.put(i3, 5);
        return i3;
    }

    int addConstant(float f) {
        ensure(5);
        byte[] bArr = this.itsPool;
        int i = this.itsTop;
        this.itsTop = i + 1;
        bArr[i] = 4;
        this.itsTop = ClassFileWriter.putInt32(Float.floatToIntBits(f), this.itsPool, this.itsTop);
        this.itsPoolTypes.put(this.itsTopIndex, 4);
        int i2 = this.itsTopIndex;
        this.itsTopIndex = i2 + 1;
        return i2;
    }

    int addConstant(double d) {
        ensure(9);
        byte[] bArr = this.itsPool;
        int i = this.itsTop;
        this.itsTop = i + 1;
        bArr[i] = 6;
        this.itsTop = ClassFileWriter.putInt64(Double.doubleToLongBits(d), this.itsPool, this.itsTop);
        int i2 = this.itsTopIndex;
        this.itsTopIndex = i2 + 2;
        this.itsPoolTypes.put(i2, 6);
        return i2;
    }

    int addConstant(String str) {
        int iAddUtf8 = addUtf8(str) & 65535;
        int i = this.itsStringConstHash.getInt(iAddUtf8, -1);
        if (i == -1) {
            i = this.itsTopIndex;
            this.itsTopIndex = i + 1;
            ensure(3);
            byte[] bArr = this.itsPool;
            int i2 = this.itsTop;
            int i3 = i2 + 1;
            this.itsTop = i3;
            bArr[i2] = 8;
            this.itsTop = ClassFileWriter.putInt16(iAddUtf8, bArr, i3);
            this.itsStringConstHash.put(iAddUtf8, i);
        }
        this.itsPoolTypes.put(i, 8);
        return i;
    }

    int addConstant(Object obj) {
        if ((obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short)) {
            return addConstant(((Number) obj).intValue());
        }
        if (obj instanceof Character) {
            return addConstant((int) ((Character) obj).charValue());
        }
        if (obj instanceof Boolean) {
            return addConstant(((Boolean) obj).booleanValue() ? 1 : 0);
        }
        if (obj instanceof Float) {
            return addConstant(((Float) obj).floatValue());
        }
        if (obj instanceof Long) {
            return addConstant(((Long) obj).longValue());
        }
        if (obj instanceof Double) {
            return addConstant(((Double) obj).doubleValue());
        }
        if (obj instanceof String) {
            return addConstant((String) obj);
        }
        if (obj instanceof ClassFileWriter.MHandle) {
            return addMethodHandle((ClassFileWriter.MHandle) obj);
        }
        throw new IllegalArgumentException("value " + obj);
    }

    boolean isUnderUtfEncodingLimit(String str) {
        int length = str.length();
        if (length * 3 <= 65535) {
            return true;
        }
        return length <= 65535 && length == getUtfEncodingLimit(str, 0, length);
    }

    int getUtfEncodingLimit(String str, int i, int i2) {
        int i3 = 65535;
        if ((i2 - i) * 3 <= 65535) {
            return i2;
        }
        while (i != i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt == 0 || cCharAt > 127) {
                i3 = cCharAt < 2047 ? i3 - 2 : i3 - 3;
            } else {
                i3--;
            }
            if (i3 < 0) {
                return i;
            }
            i++;
        }
        return i2;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0013  */
    short addUtf8(String str) {
        boolean z;
        int i = this.itsUtf8Hash.get(str, -1);
        if (i == -1) {
            int length = str.length();
            if (length > 65535) {
                z = true;
            } else {
                ensure((length * 3) + 3);
                int i2 = this.itsTop;
                this.itsPool[i2] = 1;
                int i3 = i2 + 3;
                char[] charBuffer = this.cfw.getCharBuffer(length);
                z = false;
                str.getChars(0, length, charBuffer, 0);
                for (int i4 = 0; i4 != length; i4++) {
                    char c = charBuffer[i4];
                    if (c != 0 && c <= 127) {
                        this.itsPool[i3] = (byte) c;
                        i3++;
                    } else if (c > 2047) {
                        byte[] bArr = this.itsPool;
                        bArr[i3] = (byte) ((c >> '\f') | 224);
                        int i5 = i3 + 2;
                        bArr[i3 + 1] = (byte) (((c >> 6) & 63) | 128);
                        i3 += 3;
                        bArr[i5] = (byte) ((c & MangleConstant.Q_MARK) | 128);
                    } else {
                        byte[] bArr2 = this.itsPool;
                        int i6 = i3 + 1;
                        bArr2[i3] = (byte) ((c >> 6) | 192);
                        i3 += 2;
                        bArr2[i6] = (byte) ((c & MangleConstant.Q_MARK) | 128);
                    }
                }
                int i7 = this.itsTop;
                int i8 = i3 - (i7 + 3);
                if (i8 > 65535) {
                    z = true;
                } else {
                    byte[] bArr3 = this.itsPool;
                    bArr3[i7 + 1] = (byte) (i8 >>> 8);
                    bArr3[i7 + 2] = (byte) i8;
                    this.itsTop = i3;
                    i = this.itsTopIndex;
                    this.itsTopIndex = i + 1;
                    this.itsUtf8Hash.put(str, i);
                }
            }
            if (z) {
                throw new IllegalArgumentException("Too big string");
            }
        }
        setConstantData(i, str);
        this.itsPoolTypes.put(i, 1);
        return (short) i;
    }

    private short addNameAndType(String str, String str2) {
        short sAddUtf8 = addUtf8(str);
        short sAddUtf9 = addUtf8(str2);
        ensure(5);
        byte[] bArr = this.itsPool;
        int i = this.itsTop;
        int i2 = i + 1;
        this.itsTop = i2;
        bArr[i] = 12;
        int iPutInt16 = ClassFileWriter.putInt16(sAddUtf8, bArr, i2);
        this.itsTop = iPutInt16;
        this.itsTop = ClassFileWriter.putInt16(sAddUtf9, this.itsPool, iPutInt16);
        this.itsPoolTypes.put(this.itsTopIndex, 12);
        int i3 = this.itsTopIndex;
        this.itsTopIndex = i3 + 1;
        return (short) i3;
    }

    short addClass(String str) {
        String str2;
        int i = this.itsClassHash.get(str, -1);
        if (i == -1) {
            if (str.indexOf(46) > 0) {
                String slashedForm = ClassFileWriter.getSlashedForm(str);
                int i2 = this.itsClassHash.get(slashedForm, -1);
                if (i2 != -1) {
                    this.itsClassHash.put(str, i2);
                }
                str2 = slashedForm;
                i = i2;
            } else {
                str2 = str;
            }
            if (i == -1) {
                short sAddUtf8 = addUtf8(str2);
                ensure(3);
                byte[] bArr = this.itsPool;
                int i3 = this.itsTop;
                int i4 = i3 + 1;
                this.itsTop = i4;
                bArr[i3] = 7;
                this.itsTop = ClassFileWriter.putInt16(sAddUtf8, bArr, i4);
                i = this.itsTopIndex;
                this.itsTopIndex = i + 1;
                this.itsClassHash.put(str2, i);
                if (!str.equals(str2)) {
                    this.itsClassHash.put(str, i);
                }
            }
        }
        setConstantData(i, str);
        this.itsPoolTypes.put(i, 7);
        return (short) i;
    }

    short addFieldRef(String str, String str2, String str3) {
        FieldOrMethodRef fieldOrMethodRef = new FieldOrMethodRef(str, str2, str3);
        int i = this.itsFieldRefHash.get(fieldOrMethodRef, -1);
        if (i == -1) {
            short sAddNameAndType = addNameAndType(str2, str3);
            short sAddClass = addClass(str);
            ensure(5);
            byte[] bArr = this.itsPool;
            int i2 = this.itsTop;
            int i3 = i2 + 1;
            this.itsTop = i3;
            bArr[i2] = 9;
            int iPutInt16 = ClassFileWriter.putInt16(sAddClass, bArr, i3);
            this.itsTop = iPutInt16;
            this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
            i = this.itsTopIndex;
            this.itsTopIndex = i + 1;
            this.itsFieldRefHash.put(fieldOrMethodRef, i);
        }
        setConstantData(i, fieldOrMethodRef);
        this.itsPoolTypes.put(i, 9);
        return (short) i;
    }

    short addMethodRef(String str, String str2, String str3) {
        FieldOrMethodRef fieldOrMethodRef = new FieldOrMethodRef(str, str2, str3);
        int i = this.itsMethodRefHash.get(fieldOrMethodRef, -1);
        if (i == -1) {
            short sAddNameAndType = addNameAndType(str2, str3);
            short sAddClass = addClass(str);
            ensure(5);
            byte[] bArr = this.itsPool;
            int i2 = this.itsTop;
            int i3 = i2 + 1;
            this.itsTop = i3;
            bArr[i2] = 10;
            int iPutInt16 = ClassFileWriter.putInt16(sAddClass, bArr, i3);
            this.itsTop = iPutInt16;
            this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
            i = this.itsTopIndex;
            this.itsTopIndex = i + 1;
            this.itsMethodRefHash.put(fieldOrMethodRef, i);
        }
        setConstantData(i, fieldOrMethodRef);
        this.itsPoolTypes.put(i, 10);
        return (short) i;
    }

    short addInterfaceMethodRef(String str, String str2, String str3) {
        short sAddNameAndType = addNameAndType(str2, str3);
        short sAddClass = addClass(str);
        ensure(5);
        byte[] bArr = this.itsPool;
        int i = this.itsTop;
        int i2 = i + 1;
        this.itsTop = i2;
        bArr[i] = 11;
        int iPutInt16 = ClassFileWriter.putInt16(sAddClass, bArr, i2);
        this.itsTop = iPutInt16;
        this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
        setConstantData(this.itsTopIndex, new FieldOrMethodRef(str, str2, str3));
        this.itsPoolTypes.put(this.itsTopIndex, 11);
        int i3 = this.itsTopIndex;
        this.itsTopIndex = i3 + 1;
        return (short) i3;
    }

    short addInvokeDynamic(String str, String str2, int i) {
        ConstantEntry constantEntry = new ConstantEntry(18, i, str, str2);
        int i2 = this.itsConstantHash.get(constantEntry, -1);
        if (i2 == -1) {
            short sAddNameAndType = addNameAndType(str, str2);
            ensure(5);
            byte[] bArr = this.itsPool;
            int i3 = this.itsTop;
            int i4 = i3 + 1;
            this.itsTop = i4;
            bArr[i3] = 18;
            int iPutInt16 = ClassFileWriter.putInt16(i, bArr, i4);
            this.itsTop = iPutInt16;
            this.itsTop = ClassFileWriter.putInt16(sAddNameAndType, this.itsPool, iPutInt16);
            i2 = this.itsTopIndex;
            this.itsTopIndex = i2 + 1;
            this.itsConstantHash.put(constantEntry, i2);
            setConstantData(i2, str2);
            this.itsPoolTypes.put(i2, 18);
        }
        return (short) i2;
    }

    short addMethodHandle(ClassFileWriter.MHandle mHandle) {
        short sAddMethodRef;
        int i = this.itsConstantHash.get(mHandle, -1);
        if (i == -1) {
            if (mHandle.tag <= 4) {
                sAddMethodRef = addFieldRef(mHandle.owner, mHandle.f1061name, mHandle.desc);
            } else if (mHandle.tag == 9) {
                sAddMethodRef = addInterfaceMethodRef(mHandle.owner, mHandle.f1061name, mHandle.desc);
            } else {
                sAddMethodRef = addMethodRef(mHandle.owner, mHandle.f1061name, mHandle.desc);
            }
            ensure(4);
            byte[] bArr = this.itsPool;
            int i2 = this.itsTop;
            int i3 = i2 + 1;
            this.itsTop = i3;
            bArr[i2] = 15;
            this.itsTop = i2 + 2;
            bArr[i3] = mHandle.tag;
            this.itsTop = ClassFileWriter.putInt16(sAddMethodRef, this.itsPool, this.itsTop);
            i = this.itsTopIndex;
            this.itsTopIndex = i + 1;
            this.itsConstantHash.put(mHandle, i);
            this.itsPoolTypes.put(i, 15);
        }
        return (short) i;
    }

    Object getConstantData(int i) {
        return this.itsConstantData.getObject(i);
    }

    void setConstantData(int i, Object obj) {
        this.itsConstantData.put(i, obj);
    }

    byte getConstantType(int i) {
        return (byte) this.itsPoolTypes.getInt(i, 0);
    }

    private void ensure(int i) {
        int i2 = this.itsTop;
        int i3 = i2 + i;
        byte[] bArr = this.itsPool;
        if (i3 > bArr.length) {
            int length = bArr.length * 2;
            if (i2 + i > length) {
                length = i2 + i;
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            this.itsPool = bArr2;
        }
    }
}
