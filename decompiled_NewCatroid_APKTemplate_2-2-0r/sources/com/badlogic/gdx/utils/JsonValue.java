package com.badlogic.gdx.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.NoSuchElementException;
import okhttp3.HttpUrl;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class JsonValue implements Iterable<JsonValue> {
    public JsonValue child;
    private double doubleValue;
    private long longValue;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f125name;
    public JsonValue next;
    public JsonValue parent;
    public JsonValue prev;
    public int size;
    private String stringValue;
    private ValueType type;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonValue$PrettyPrintSettings.class
      classes8.dex
     */
    public static class PrettyPrintSettings {
        public JsonWriter.OutputType outputType;
        public int singleLineColumns;
        public boolean wrapNumericArrays;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonValue$ValueType.class
      classes8.dex
     */
    public enum ValueType {
        object,
        array,
        stringValue,
        doubleValue,
        longValue,
        booleanValue,
        nullValue
    }

    public JsonValue(ValueType valueType) {
        this.type = valueType;
    }

    public JsonValue(String str) {
        set(str);
    }

    public JsonValue(double d) {
        set(d, (String) null);
    }

    public JsonValue(long j) {
        set(j, (String) null);
    }

    public JsonValue(double d, String str) {
        set(d, str);
    }

    public JsonValue(long j, String str) {
        set(j, str);
    }

    public JsonValue(boolean z) {
        set(z);
    }

    public JsonValue get(int i) {
        JsonValue jsonValue = this.child;
        while (jsonValue != null && i > 0) {
            i--;
            jsonValue = jsonValue.next;
        }
        return jsonValue;
    }

    public JsonValue get(String str) {
        JsonValue jsonValue = this.child;
        while (jsonValue != null) {
            String str2 = jsonValue.f125name;
            if (str2 != null && str2.equalsIgnoreCase(str)) {
                break;
            }
            jsonValue = jsonValue.next;
        }
        return jsonValue;
    }

    public boolean has(String str) {
        return get(str) != null;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.badlogic.gdx.utils.JsonValue$JsonIterator] */
    public JsonIterator iterator(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            JsonIterator jsonIterator = new JsonIterator();
            jsonIterator.entry = null;
            return jsonIterator;
        }
        return jsonValue.iterator2();
    }

    public JsonValue require(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue != null) {
            return jsonValue;
        }
        throw new IllegalArgumentException("Child not found with index: " + i);
    }

    public JsonValue require(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue != null) {
            return jsonValue;
        }
        throw new IllegalArgumentException("Child not found with name: " + str);
    }

    public JsonValue remove(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            return null;
        }
        JsonValue jsonValue2 = jsonValue.prev;
        if (jsonValue2 == null) {
            JsonValue jsonValue3 = jsonValue.next;
            this.child = jsonValue3;
            if (jsonValue3 != null) {
                jsonValue3.prev = null;
            }
        } else {
            jsonValue2.next = jsonValue.next;
            JsonValue jsonValue4 = jsonValue.next;
            if (jsonValue4 != null) {
                jsonValue4.prev = jsonValue2;
            }
        }
        this.size--;
        return jsonValue;
    }

    public JsonValue remove(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            return null;
        }
        JsonValue jsonValue2 = jsonValue.prev;
        if (jsonValue2 == null) {
            JsonValue jsonValue3 = jsonValue.next;
            this.child = jsonValue3;
            if (jsonValue3 != null) {
                jsonValue3.prev = null;
            }
        } else {
            jsonValue2.next = jsonValue.next;
            JsonValue jsonValue4 = jsonValue.next;
            if (jsonValue4 != null) {
                jsonValue4.prev = jsonValue2;
            }
        }
        this.size--;
        return jsonValue;
    }

    public void remove() {
        JsonValue jsonValue = this.parent;
        if (jsonValue == null) {
            throw new IllegalStateException();
        }
        JsonValue jsonValue2 = this.prev;
        if (jsonValue2 == null) {
            JsonValue jsonValue3 = this.next;
            jsonValue.child = jsonValue3;
            if (jsonValue3 != null) {
                jsonValue3.prev = null;
            }
        } else {
            jsonValue2.next = this.next;
            JsonValue jsonValue4 = this.next;
            if (jsonValue4 != null) {
                jsonValue4.prev = jsonValue2;
            }
        }
        jsonValue.size--;
    }

    public boolean notEmpty() {
        return this.size > 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    @Deprecated
    public int size() {
        return this.size;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonValue$1.class
      classes8.dex
     */
    /* JADX INFO: renamed from: com.badlogic.gdx.utils.JsonValue$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType;

        static {
            int[] iArr = new int[ValueType.values().length];
            $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType = iArr;
            try {
                iArr[ValueType.stringValue.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.doubleValue.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.longValue.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.booleanValue.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[ValueType.nullValue.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public String asString() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return this.stringValue;
        }
        if (i == 2) {
            String str = this.stringValue;
            return str != null ? str : Double.toString(this.doubleValue);
        }
        if (i == 3) {
            String str2 = this.stringValue;
            return str2 != null ? str2 : Long.toString(this.longValue);
        }
        if (i == 4) {
            return this.longValue != 0 ? "true" : "false";
        }
        if (i == 5) {
            return null;
        }
        throw new IllegalStateException("Value cannot be converted to string: " + this.type);
    }

    public float asFloat() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return Float.parseFloat(this.stringValue);
        }
        if (i == 2) {
            return (float) this.doubleValue;
        }
        if (i == 3) {
            return this.longValue;
        }
        if (i == 4) {
            return this.longValue != 0 ? 1.0f : 0.0f;
        }
        throw new IllegalStateException("Value cannot be converted to float: " + this.type);
    }

    public double asDouble() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return Double.parseDouble(this.stringValue);
        }
        if (i == 2) {
            return this.doubleValue;
        }
        if (i == 3) {
            return this.longValue;
        }
        if (i == 4) {
            return this.longValue != 0 ? 1.0d : 0.0d;
        }
        throw new IllegalStateException("Value cannot be converted to double: " + this.type);
    }

    public long asLong() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return Long.parseLong(this.stringValue);
        }
        if (i == 2) {
            return (long) this.doubleValue;
        }
        if (i == 3) {
            return this.longValue;
        }
        if (i == 4) {
            return this.longValue != 0 ? 1L : 0L;
        }
        throw new IllegalStateException("Value cannot be converted to long: " + this.type);
    }

    public int asInt() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return Integer.parseInt(this.stringValue);
        }
        if (i == 2) {
            return (int) this.doubleValue;
        }
        if (i == 3) {
            return (int) this.longValue;
        }
        if (i == 4) {
            return this.longValue != 0 ? 1 : 0;
        }
        throw new IllegalStateException("Value cannot be converted to int: " + this.type);
    }

    public boolean asBoolean() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return this.stringValue.equalsIgnoreCase("true");
        }
        if (i == 2) {
            return this.doubleValue != 0.0d;
        }
        if (i == 3) {
            return this.longValue != 0;
        }
        if (i == 4) {
            return this.longValue != 0;
        }
        throw new IllegalStateException("Value cannot be converted to boolean: " + this.type);
    }

    public byte asByte() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return Byte.parseByte(this.stringValue);
        }
        if (i == 2) {
            return (byte) this.doubleValue;
        }
        if (i == 3) {
            return (byte) this.longValue;
        }
        if (i == 4) {
            return this.longValue != 0 ? (byte) 1 : (byte) 0;
        }
        throw new IllegalStateException("Value cannot be converted to byte: " + this.type);
    }

    public short asShort() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i == 1) {
            return Short.parseShort(this.stringValue);
        }
        if (i == 2) {
            return (short) this.doubleValue;
        }
        if (i == 3) {
            return (short) this.longValue;
        }
        if (i == 4) {
            return this.longValue != 0 ? (short) 1 : (short) 0;
        }
        throw new IllegalStateException("Value cannot be converted to short: " + this.type);
    }

    public char asChar() {
        int i;
        int i2 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        if (i2 == 1) {
            if (this.stringValue.length() == 0) {
                return (char) 0;
            }
            return this.stringValue.charAt(0);
        }
        if (i2 == 2) {
            i = (int) this.doubleValue;
        } else {
            if (i2 != 3) {
                if (i2 == 4) {
                    return this.longValue != 0 ? (char) 1 : (char) 0;
                }
                throw new IllegalStateException("Value cannot be converted to char: " + this.type);
            }
            i = (int) this.longValue;
        }
        return (char) i;
    }

    public String[] asStringArray() {
        String string;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        String[] strArr = new String[this.size];
        JsonValue jsonValue = this.child;
        int i = 0;
        while (jsonValue != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i2 == 1) {
                string = jsonValue.stringValue;
            } else if (i2 == 2) {
                string = this.stringValue;
                if (string == null) {
                    string = Double.toString(jsonValue.doubleValue);
                }
            } else if (i2 == 3) {
                string = this.stringValue;
                if (string == null) {
                    string = Long.toString(jsonValue.longValue);
                }
            } else if (i2 == 4) {
                string = jsonValue.longValue != 0 ? "true" : "false";
            } else {
                if (i2 != 5) {
                    throw new IllegalStateException("Value cannot be converted to string: " + jsonValue.type);
                }
                string = null;
            }
            strArr[i] = string;
            jsonValue = jsonValue.next;
            i++;
        }
        return strArr;
    }

    public float[] asFloatArray() {
        float f;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        float[] fArr = new float[this.size];
        JsonValue jsonValue = this.child;
        int i = 0;
        while (jsonValue != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i2 == 1) {
                f = Float.parseFloat(jsonValue.stringValue);
            } else if (i2 == 2) {
                f = (float) jsonValue.doubleValue;
            } else if (i2 == 3) {
                f = jsonValue.longValue;
            } else {
                if (i2 != 4) {
                    throw new IllegalStateException("Value cannot be converted to float: " + jsonValue.type);
                }
                f = jsonValue.longValue != 0 ? 1.0f : 0.0f;
            }
            fArr[i] = f;
            jsonValue = jsonValue.next;
            i++;
        }
        return fArr;
    }

    public double[] asDoubleArray() {
        double d;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        double[] dArr = new double[this.size];
        JsonValue jsonValue = this.child;
        int i = 0;
        while (jsonValue != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i2 == 1) {
                d = Double.parseDouble(jsonValue.stringValue);
            } else if (i2 == 2) {
                d = jsonValue.doubleValue;
            } else if (i2 == 3) {
                d = jsonValue.longValue;
            } else {
                if (i2 != 4) {
                    throw new IllegalStateException("Value cannot be converted to double: " + jsonValue.type);
                }
                d = jsonValue.longValue != 0 ? 1.0d : 0.0d;
            }
            dArr[i] = d;
            jsonValue = jsonValue.next;
            i++;
        }
        return dArr;
    }

    public long[] asLongArray() {
        long j;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        long[] jArr = new long[this.size];
        JsonValue jsonValue = this.child;
        int i = 0;
        while (jsonValue != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i2 == 1) {
                j = Long.parseLong(jsonValue.stringValue);
            } else if (i2 == 2) {
                j = (long) jsonValue.doubleValue;
            } else if (i2 == 3) {
                j = jsonValue.longValue;
            } else {
                if (i2 != 4) {
                    throw new IllegalStateException("Value cannot be converted to long: " + jsonValue.type);
                }
                j = 0;
                if (jsonValue.longValue != 0) {
                    j = 1;
                }
            }
            jArr[i] = j;
            jsonValue = jsonValue.next;
            i++;
        }
        return jArr;
    }

    public int[] asIntArray() {
        int i;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        int[] iArr = new int[this.size];
        JsonValue jsonValue = this.child;
        int i2 = 0;
        while (jsonValue != null) {
            int i3 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i3 == 1) {
                i = Integer.parseInt(jsonValue.stringValue);
            } else if (i3 == 2) {
                i = (int) jsonValue.doubleValue;
            } else if (i3 == 3) {
                i = (int) jsonValue.longValue;
            } else {
                if (i3 != 4) {
                    throw new IllegalStateException("Value cannot be converted to int: " + jsonValue.type);
                }
                i = jsonValue.longValue != 0 ? 1 : 0;
            }
            iArr[i2] = i;
            jsonValue = jsonValue.next;
            i2++;
        }
        return iArr;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x002e  */
    /* JADX WARN: Code duplicated, block: B:17:0x0030  */
    public boolean[] asBooleanArray() {
        boolean z;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        boolean[] zArr = new boolean[this.size];
        JsonValue jsonValue = this.child;
        int i = 0;
        while (jsonValue != null) {
            int i2 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i2 == 1) {
                z = Boolean.parseBoolean(jsonValue.stringValue);
            } else if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 4) {
                        throw new IllegalStateException("Value cannot be converted to boolean: " + jsonValue.type);
                    }
                    if (jsonValue.longValue != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else if (jsonValue.longValue == 0) {
                    z = true;
                } else {
                    z = false;
                }
            } else if (jsonValue.doubleValue == 0.0d) {
                z = true;
            } else {
                z = false;
            }
            zArr[i] = z;
            jsonValue = jsonValue.next;
            i++;
        }
        return zArr;
    }

    public byte[] asByteArray() {
        byte b;
        int i;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        byte[] bArr = new byte[this.size];
        JsonValue jsonValue = this.child;
        int i2 = 0;
        while (jsonValue != null) {
            int i3 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i3 != 1) {
                if (i3 == 2) {
                    i = (int) jsonValue.doubleValue;
                } else if (i3 == 3) {
                    i = (int) jsonValue.longValue;
                } else {
                    if (i3 != 4) {
                        throw new IllegalStateException("Value cannot be converted to byte: " + jsonValue.type);
                    }
                    b = jsonValue.longValue != 0 ? (byte) 1 : (byte) 0;
                }
                b = (byte) i;
            } else {
                b = Byte.parseByte(jsonValue.stringValue);
            }
            bArr[i2] = b;
            jsonValue = jsonValue.next;
            i2++;
        }
        return bArr;
    }

    public short[] asShortArray() {
        short s;
        int i;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        short[] sArr = new short[this.size];
        JsonValue jsonValue = this.child;
        int i2 = 0;
        while (jsonValue != null) {
            int i3 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i3 != 1) {
                if (i3 == 2) {
                    i = (int) jsonValue.doubleValue;
                } else if (i3 == 3) {
                    i = (int) jsonValue.longValue;
                } else {
                    if (i3 != 4) {
                        throw new IllegalStateException("Value cannot be converted to short: " + jsonValue.type);
                    }
                    s = jsonValue.longValue != 0 ? (short) 1 : (short) 0;
                }
                s = (short) i;
            } else {
                s = Short.parseShort(jsonValue.stringValue);
            }
            sArr[i2] = s;
            jsonValue = jsonValue.next;
            i2++;
        }
        return sArr;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0030  */
    public char[] asCharArray() {
        char cCharAt;
        int i;
        if (this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }
        char[] cArr = new char[this.size];
        JsonValue jsonValue = this.child;
        int i2 = 0;
        while (jsonValue != null) {
            int i3 = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[jsonValue.type.ordinal()];
            if (i3 != 1) {
                if (i3 == 2) {
                    i = (int) jsonValue.doubleValue;
                } else if (i3 == 3) {
                    i = (int) jsonValue.longValue;
                } else {
                    if (i3 != 4) {
                        throw new IllegalStateException("Value cannot be converted to char: " + jsonValue.type);
                    }
                    if (jsonValue.longValue != 0) {
                        cCharAt = 1;
                    } else {
                        cCharAt = 0;
                    }
                }
                cCharAt = (char) i;
            } else if (jsonValue.stringValue.length() == 0) {
                cCharAt = 0;
            } else {
                cCharAt = jsonValue.stringValue.charAt(0);
            }
            cArr[i2] = cCharAt;
            jsonValue = jsonValue.next;
            i2++;
        }
        return cArr;
    }

    public boolean hasChild(String str) {
        return getChild(str) != null;
    }

    public JsonValue getChild(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            return null;
        }
        return jsonValue.child;
    }

    public String getString(String str, String str2) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? str2 : jsonValue.asString();
    }

    public float getFloat(String str, float f) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? f : jsonValue.asFloat();
    }

    public double getDouble(String str, double d) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? d : jsonValue.asDouble();
    }

    public long getLong(String str, long j) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? j : jsonValue.asLong();
    }

    public int getInt(String str, int i) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? i : jsonValue.asInt();
    }

    public boolean getBoolean(String str, boolean z) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? z : jsonValue.asBoolean();
    }

    public byte getByte(String str, byte b) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? b : jsonValue.asByte();
    }

    public short getShort(String str, short s) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? s : jsonValue.asShort();
    }

    public char getChar(String str, char c) {
        JsonValue jsonValue = get(str);
        return (jsonValue == null || !jsonValue.isValue() || jsonValue.isNull()) ? c : jsonValue.asChar();
    }

    public String getString(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asString();
    }

    public float getFloat(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asFloat();
    }

    public double getDouble(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asDouble();
    }

    public long getLong(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asLong();
    }

    public int getInt(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asInt();
    }

    public boolean getBoolean(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asBoolean();
    }

    public byte getByte(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asByte();
    }

    public short getShort(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asShort();
    }

    public char getChar(String str) {
        JsonValue jsonValue = get(str);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Named value not found: " + str);
        }
        return jsonValue.asChar();
    }

    public String getString(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asString();
    }

    public float getFloat(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asFloat();
    }

    public double getDouble(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asDouble();
    }

    public long getLong(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asLong();
    }

    public int getInt(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asInt();
    }

    public boolean getBoolean(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asBoolean();
    }

    public byte getByte(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asByte();
    }

    public short getShort(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asShort();
    }

    public char getChar(int i) {
        JsonValue jsonValue = get(i);
        if (jsonValue == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.f125name);
        }
        return jsonValue.asChar();
    }

    public ValueType type() {
        return this.type;
    }

    public void setType(ValueType valueType) {
        if (valueType == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        this.type = valueType;
    }

    public boolean isArray() {
        return this.type == ValueType.array;
    }

    public boolean isObject() {
        return this.type == ValueType.object;
    }

    public boolean isString() {
        return this.type == ValueType.stringValue;
    }

    public boolean isNumber() {
        return this.type == ValueType.doubleValue || this.type == ValueType.longValue;
    }

    public boolean isDouble() {
        return this.type == ValueType.doubleValue;
    }

    public boolean isLong() {
        return this.type == ValueType.longValue;
    }

    public boolean isBoolean() {
        return this.type == ValueType.booleanValue;
    }

    public boolean isNull() {
        return this.type == ValueType.nullValue;
    }

    public boolean isValue() {
        int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()];
        return i == 1 || i == 2 || i == 3 || i == 4 || i == 5;
    }

    public String name() {
        return this.f125name;
    }

    public void setName(String str) {
        this.f125name = str;
    }

    public JsonValue parent() {
        return this.parent;
    }

    public JsonValue child() {
        return this.child;
    }

    public void addChild(String str, JsonValue jsonValue) {
        if (str == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        jsonValue.f125name = str;
        addChild(jsonValue);
    }

    public void addChild(JsonValue jsonValue) {
        if (this.type == ValueType.object && jsonValue.f125name == null) {
            throw new IllegalStateException("An object child requires a name: " + jsonValue);
        }
        jsonValue.parent = this;
        jsonValue.next = null;
        this.size++;
        JsonValue jsonValue2 = this.child;
        if (jsonValue2 == null) {
            jsonValue.prev = null;
            this.child = jsonValue;
            return;
        }
        while (true) {
            JsonValue jsonValue3 = jsonValue2.next;
            if (jsonValue3 == null) {
                jsonValue2.next = jsonValue;
                jsonValue.prev = jsonValue2;
                return;
            }
            jsonValue2 = jsonValue3;
        }
    }

    public JsonValue next() {
        return this.next;
    }

    public void setNext(JsonValue jsonValue) {
        this.next = jsonValue;
    }

    public JsonValue prev() {
        return this.prev;
    }

    public void setPrev(JsonValue jsonValue) {
        this.prev = jsonValue;
    }

    public void set(String str) {
        this.stringValue = str;
        this.type = str == null ? ValueType.nullValue : ValueType.stringValue;
    }

    public void set(double d, String str) {
        this.doubleValue = d;
        this.longValue = (long) d;
        this.stringValue = str;
        this.type = ValueType.doubleValue;
    }

    public void set(long j, String str) {
        this.longValue = j;
        this.doubleValue = j;
        this.stringValue = str;
        this.type = ValueType.longValue;
    }

    public void set(boolean z) {
        this.longValue = z ? 1L : 0L;
        this.type = ValueType.booleanValue;
    }

    public String toJson(JsonWriter.OutputType outputType) {
        if (isValue()) {
            return asString();
        }
        StringBuilder stringBuilder = new StringBuilder(512);
        json(this, stringBuilder, outputType);
        return stringBuilder.toString();
    }

    private void json(JsonValue jsonValue, StringBuilder stringBuilder, JsonWriter.OutputType outputType) {
        if (jsonValue.isObject()) {
            if (jsonValue.child == null) {
                stringBuilder.append("{}");
                return;
            }
            stringBuilder.length();
            stringBuilder.append('{');
            for (JsonValue jsonValue2 = jsonValue.child; jsonValue2 != null; jsonValue2 = jsonValue2.next) {
                stringBuilder.append(outputType.quoteName(jsonValue2.f125name));
                stringBuilder.append(':');
                json(jsonValue2, stringBuilder, outputType);
                if (jsonValue2.next != null) {
                    stringBuilder.append(',');
                }
            }
            stringBuilder.append('}');
            return;
        }
        if (jsonValue.isArray()) {
            if (jsonValue.child == null) {
                stringBuilder.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                return;
            }
            stringBuilder.length();
            stringBuilder.append('[');
            for (JsonValue jsonValue3 = jsonValue.child; jsonValue3 != null; jsonValue3 = jsonValue3.next) {
                json(jsonValue3, stringBuilder, outputType);
                if (jsonValue3.next != null) {
                    stringBuilder.append(',');
                }
            }
            stringBuilder.append(']');
            return;
        }
        if (jsonValue.isString()) {
            stringBuilder.append(outputType.quoteValue(jsonValue.asString()));
            return;
        }
        if (jsonValue.isDouble()) {
            double dAsDouble = jsonValue.asDouble();
            double dAsLong = jsonValue.asLong();
            if (dAsDouble == dAsLong) {
                dAsDouble = dAsLong;
            }
            stringBuilder.append(dAsDouble);
            return;
        }
        if (jsonValue.isLong()) {
            stringBuilder.append(jsonValue.asLong());
        } else if (jsonValue.isBoolean()) {
            stringBuilder.append(jsonValue.asBoolean());
        } else {
            if (jsonValue.isNull()) {
                stringBuilder.append("null");
                return;
            }
            throw new SerializationException("Unknown object type: " + jsonValue);
        }
    }

    @Override // java.lang.Iterable
    /* JADX INFO: renamed from: iterator, reason: merged with bridge method [inline-methods] */
    public Iterator<JsonValue> iterator2() {
        return new JsonIterator();
    }

    public String toString() {
        if (isValue()) {
            return this.f125name == null ? asString() : this.f125name + ": " + asString();
        }
        return (this.f125name == null ? "" : this.f125name + ": ") + prettyPrint(JsonWriter.OutputType.minimal, 0);
    }

    public String trace() {
        JsonValue jsonValue = this.parent;
        String str = HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        if (jsonValue == null) {
            if (this.type == ValueType.array) {
                return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
            }
            return this.type == ValueType.object ? "{}" : "";
        }
        if (jsonValue.type == ValueType.array) {
            JsonValue jsonValue2 = this.parent.child;
            int i = 0;
            while (jsonValue2 != null) {
                if (jsonValue2 != this) {
                    jsonValue2 = jsonValue2.next;
                    i++;
                } else {
                    str = "[" + i + "]";
                    break;
                }
            }
        } else if (this.f125name.indexOf(46) != -1) {
            str = ".\"" + this.f125name.replace("\"", "\\\"") + "\"";
        } else {
            str = "." + this.f125name;
        }
        return this.parent.trace() + str;
    }

    public String prettyPrint(JsonWriter.OutputType outputType, int i) {
        PrettyPrintSettings prettyPrintSettings = new PrettyPrintSettings();
        prettyPrintSettings.outputType = outputType;
        prettyPrintSettings.singleLineColumns = i;
        return prettyPrint(prettyPrintSettings);
    }

    public String prettyPrint(PrettyPrintSettings prettyPrintSettings) {
        StringBuilder stringBuilder = new StringBuilder(512);
        prettyPrint(this, stringBuilder, 0, prettyPrintSettings);
        return stringBuilder.toString();
    }

    private void prettyPrint(JsonValue jsonValue, StringBuilder stringBuilder, int i, PrettyPrintSettings prettyPrintSettings) {
        JsonWriter.OutputType outputType = prettyPrintSettings.outputType;
        if (jsonValue.isObject()) {
            if (jsonValue.child == null) {
                stringBuilder.append("{}");
                return;
            }
            boolean z = !isFlat(jsonValue);
            int length = stringBuilder.length();
            loop0: while (true) {
                stringBuilder.append(z ? "{\n" : "{ ");
                JsonValue jsonValue2 = jsonValue.child;
                while (true) {
                    if (jsonValue2 == null) {
                        break loop0;
                    }
                    if (z) {
                        indent(i, stringBuilder);
                    }
                    stringBuilder.append(outputType.quoteName(jsonValue2.f125name));
                    stringBuilder.append(": ");
                    prettyPrint(jsonValue2, stringBuilder, i + 1, prettyPrintSettings);
                    if ((!z || outputType != JsonWriter.OutputType.minimal) && jsonValue2.next != null) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(z ? '\n' : ' ');
                    if (z || stringBuilder.length() - length <= prettyPrintSettings.singleLineColumns) {
                        jsonValue2 = jsonValue2.next;
                    }
                }
                stringBuilder.setLength(length);
                z = true;
            }
            if (z) {
                indent(i - 1, stringBuilder);
            }
            stringBuilder.append('}');
            return;
        }
        if (jsonValue.isArray()) {
            if (jsonValue.child == null) {
                stringBuilder.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                return;
            }
            boolean z2 = !isFlat(jsonValue);
            boolean z3 = prettyPrintSettings.wrapNumericArrays || !isNumeric(jsonValue);
            int length2 = stringBuilder.length();
            loop2: while (true) {
                stringBuilder.append(z2 ? "[\n" : "[ ");
                JsonValue jsonValue3 = jsonValue.child;
                while (true) {
                    if (jsonValue3 == null) {
                        break loop2;
                    }
                    if (z2) {
                        indent(i, stringBuilder);
                    }
                    prettyPrint(jsonValue3, stringBuilder, i + 1, prettyPrintSettings);
                    if ((!z2 || outputType != JsonWriter.OutputType.minimal) && jsonValue3.next != null) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(z2 ? '\n' : ' ');
                    if (!z3 || z2 || stringBuilder.length() - length2 <= prettyPrintSettings.singleLineColumns) {
                        jsonValue3 = jsonValue3.next;
                    }
                }
                stringBuilder.setLength(length2);
                z2 = true;
            }
            if (z2) {
                indent(i - 1, stringBuilder);
            }
            stringBuilder.append(']');
            return;
        }
        if (jsonValue.isString()) {
            stringBuilder.append(outputType.quoteValue(jsonValue.asString()));
            return;
        }
        if (jsonValue.isDouble()) {
            double dAsDouble = jsonValue.asDouble();
            double dAsLong = jsonValue.asLong();
            if (dAsDouble == dAsLong) {
                dAsDouble = dAsLong;
            }
            stringBuilder.append(dAsDouble);
            return;
        }
        if (jsonValue.isLong()) {
            stringBuilder.append(jsonValue.asLong());
        } else if (jsonValue.isBoolean()) {
            stringBuilder.append(jsonValue.asBoolean());
        } else {
            if (jsonValue.isNull()) {
                stringBuilder.append("null");
                return;
            }
            throw new SerializationException("Unknown object type: " + jsonValue);
        }
    }

    public void prettyPrint(JsonWriter.OutputType outputType, Writer writer) throws IOException {
        PrettyPrintSettings prettyPrintSettings = new PrettyPrintSettings();
        prettyPrintSettings.outputType = outputType;
        prettyPrint(this, writer, 0, prettyPrintSettings);
    }

    private void prettyPrint(JsonValue jsonValue, Writer writer, int i, PrettyPrintSettings prettyPrintSettings) throws IOException {
        JsonWriter.OutputType outputType = prettyPrintSettings.outputType;
        if (jsonValue.isObject()) {
            if (jsonValue.child == null) {
                writer.append("{}");
                return;
            }
            boolean z = !isFlat(jsonValue) || jsonValue.size > 6;
            writer.append((CharSequence) (z ? "{\n" : "{ "));
            for (JsonValue jsonValue2 = jsonValue.child; jsonValue2 != null; jsonValue2 = jsonValue2.next) {
                if (z) {
                    indent(i, writer);
                }
                writer.append((CharSequence) outputType.quoteName(jsonValue2.f125name));
                writer.append(": ");
                prettyPrint(jsonValue2, writer, i + 1, prettyPrintSettings);
                if ((!z || outputType != JsonWriter.OutputType.minimal) && jsonValue2.next != null) {
                    writer.append(',');
                }
                writer.append(z ? '\n' : ' ');
            }
            if (z) {
                indent(i - 1, writer);
            }
            writer.append('}');
            return;
        }
        if (jsonValue.isArray()) {
            if (jsonValue.child == null) {
                writer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                return;
            }
            boolean zIsFlat = isFlat(jsonValue);
            writer.append((CharSequence) (!zIsFlat ? "[\n" : "[ "));
            for (JsonValue jsonValue3 = jsonValue.child; jsonValue3 != null; jsonValue3 = jsonValue3.next) {
                if (!zIsFlat) {
                    indent(i, writer);
                }
                prettyPrint(jsonValue3, writer, i + 1, prettyPrintSettings);
                if ((zIsFlat || outputType != JsonWriter.OutputType.minimal) && jsonValue3.next != null) {
                    writer.append(',');
                }
                writer.append(!zIsFlat ? '\n' : ' ');
            }
            if (!zIsFlat) {
                indent(i - 1, writer);
            }
            writer.append(']');
            return;
        }
        if (jsonValue.isString()) {
            writer.append((CharSequence) outputType.quoteValue(jsonValue.asString()));
            return;
        }
        if (jsonValue.isDouble()) {
            double dAsDouble = jsonValue.asDouble();
            double dAsLong = jsonValue.asLong();
            if (dAsDouble == dAsLong) {
                dAsDouble = dAsLong;
            }
            writer.append((CharSequence) Double.toString(dAsDouble));
            return;
        }
        if (jsonValue.isLong()) {
            writer.append((CharSequence) Long.toString(jsonValue.asLong()));
        } else if (jsonValue.isBoolean()) {
            writer.append((CharSequence) Boolean.toString(jsonValue.asBoolean()));
        } else {
            if (jsonValue.isNull()) {
                writer.append("null");
                return;
            }
            throw new SerializationException("Unknown object type: " + jsonValue);
        }
    }

    private static boolean isFlat(JsonValue jsonValue) {
        for (JsonValue jsonValue2 = jsonValue.child; jsonValue2 != null; jsonValue2 = jsonValue2.next) {
            if (jsonValue2.isObject() || jsonValue2.isArray()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNumeric(JsonValue jsonValue) {
        for (JsonValue jsonValue2 = jsonValue.child; jsonValue2 != null; jsonValue2 = jsonValue2.next) {
            if (!jsonValue2.isNumber()) {
                return false;
            }
        }
        return true;
    }

    private static void indent(int i, StringBuilder stringBuilder) {
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append('\t');
        }
    }

    private static void indent(int i, Writer writer) throws IOException {
        for (int i2 = 0; i2 < i; i2++) {
            writer.append('\t');
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonValue$JsonIterator.class
      classes8.dex
     */
    public class JsonIterator implements Iterator<JsonValue>, Iterable<JsonValue> {
        JsonValue current;
        JsonValue entry;

        @Override // java.lang.Iterable
        public Iterator<JsonValue> iterator() {
            return this;
        }

        public JsonIterator() {
            this.entry = JsonValue.this.child;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.entry != null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public JsonValue next() {
            JsonValue jsonValue = this.entry;
            this.current = jsonValue;
            if (jsonValue == null) {
                throw new NoSuchElementException();
            }
            this.entry = jsonValue.next;
            return this.current;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.current.prev == null) {
                JsonValue.this.child = this.current.next;
                if (JsonValue.this.child != null) {
                    JsonValue.this.child.prev = null;
                }
            } else {
                this.current.prev.next = this.current.next;
                if (this.current.next != null) {
                    this.current.next.prev = this.current.prev;
                }
            }
            JsonValue.this.size--;
        }
    }
}
