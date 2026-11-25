package com.reandroid.dex.model;

import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.AnnotationValue;
import com.reandroid.dex.value.ArrayValue;
import com.reandroid.dex.value.ByteValue;
import com.reandroid.dex.value.CharValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.dex.value.DoubleValue;
import com.reandroid.dex.value.FloatValue;
import com.reandroid.dex.value.IntValue;
import com.reandroid.dex.value.LongValue;
import com.reandroid.dex.value.PrimitiveValueBlock;
import com.reandroid.dex.value.SectionValue;
import com.reandroid.dex.value.ShortValue;
import com.reandroid.dex.value.StringValue;
import com.reandroid.dex.value.TypeValue;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DexValue extends Dex {
    private final Dex declaring;
    private final DexValueBlock<?> dexValueBlock;

    public DexValue(Dex dex, DexValueBlock<?> dexValueBlock) {
        this.declaring = dex;
        this.dexValueBlock = dexValueBlock;
    }

    public Key getKey() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof SectionValue) {
            return ((SectionValue) dexValueBlock).getKey();
        }
        return null;
    }

    public void setKey(Key key) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof SectionValue) {
            ((SectionValue) dexValueBlock).setItem(key);
        }
    }

    public TypeKey getTypeKey() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof TypeValue) {
            return ((TypeValue) dexValueBlock).getKey();
        }
        return null;
    }

    public String getString() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof StringValue) {
            return ((StringValue) dexValueBlock).getString();
        }
        return null;
    }

    public void setString(String str) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof StringValue) {
            ((StringValue) dexValueBlock).setString(str);
        }
    }

    public Integer getInteger() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof IntValue) {
            return Integer.valueOf(((IntValue) dexValueBlock).get());
        }
        return null;
    }

    public void setInteger(int i) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof IntValue) {
            ((IntValue) dexValueBlock).set(i);
        }
    }

    public Byte getByte() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof ByteValue) {
            return Byte.valueOf(((ByteValue) dexValueBlock).get());
        }
        return null;
    }

    public void setByte(byte b) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof ByteValue) {
            ((ByteValue) dexValueBlock).set(b);
        }
    }

    public Short getShort() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof ShortValue) {
            return Short.valueOf(((ShortValue) dexValueBlock).get());
        }
        return null;
    }

    public void setShort(short s) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof ShortValue) {
            ((ShortValue) dexValueBlock).set(s);
        }
    }

    public Character getCharacter() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof CharValue) {
            return Character.valueOf(((CharValue) dexValueBlock).get());
        }
        return null;
    }

    public void setCharacter(char c) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof CharValue) {
            ((CharValue) dexValueBlock).set(c);
        }
    }

    public Long getLong() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof LongValue) {
            return Long.valueOf(((LongValue) dexValueBlock).get());
        }
        return null;
    }

    public void setLong(long j) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof LongValue) {
            ((LongValue) dexValueBlock).set(j);
        }
    }

    public Double getDouble() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof DoubleValue) {
            return Double.valueOf(((DoubleValue) dexValueBlock).get());
        }
        return null;
    }

    public void setDouble(double d) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof DoubleValue) {
            ((DoubleValue) dexValueBlock).set(d);
        }
    }

    public Float getFloat() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof FloatValue) {
            return Float.valueOf(((FloatValue) dexValueBlock).get());
        }
        return null;
    }

    public void setFloat(float f) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof FloatValue) {
            ((FloatValue) dexValueBlock).set(f);
        }
    }

    public Number getNumber() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof PrimitiveValueBlock) {
            return ((PrimitiveValueBlock) dexValueBlock).getData();
        }
        return null;
    }

    public void setNumber(Number number) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof PrimitiveValueBlock) {
            ((PrimitiveValueBlock) dexValueBlock).setData(number);
        }
    }

    public Object getData() {
        return getDexValueBlock().getData();
    }

    public void setData(Object obj) {
        getDexValueBlock().setData(obj);
    }

    public DexAnnotation getAnnotation() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        if (dexValueBlock instanceof AnnotationValue) {
            return DexAnnotation.create(getDeclaring(), ((AnnotationValue) dexValueBlock).get());
        }
        return null;
    }

    public String getAsString() {
        return getDexValueBlock().getAsString();
    }

    public int getAsInteger() {
        return getAsInteger(0);
    }

    public int getAsInteger(int i) {
        DexValueBlock<?> dexValueBlock = getDexValueBlock();
        return dexValueBlock instanceof PrimitiveValueBlock ? (int) ((PrimitiveValueBlock) dexValueBlock).getSignedValue() : i;
    }

    public DexValueType<?> getValueType() {
        return getDexValueBlock().getValueType();
    }

    public DexValueBlock<?> getDexValueBlock() {
        return this.dexValueBlock;
    }

    public Dex getDeclaring() {
        return this.declaring;
    }

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        Key key2 = getKey();
        if (key2 != null) {
            return key2.uses(key);
        }
        return false;
    }

    @Override // com.reandroid.dex.model.Dex
    public DexClassRepository getClassRepository() {
        return getDeclaring().getClassRepository();
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        throw new RuntimeException("Method not implemented");
    }

    public int getIndex() {
        return getDexValueBlock().getIndex();
    }

    public boolean is(DexValueType<?> dexValueType) {
        return getValueType() == dexValueType;
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getDexValueBlock().append(smaliWriter);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && getDexValueBlock() == ((DexValue) obj).getDexValueBlock();
    }

    public int hashCode() {
        return getDexValueBlock().hashCode();
    }

    @Override // com.reandroid.dex.model.Dex
    public String toString() {
        return getAsString();
    }

    public static DexValue create(Dex dex, DexValueBlock<?> dexValueBlock) {
        if (dex == null || dexValueBlock == null) {
            return null;
        }
        if (dexValueBlock instanceof ArrayValue) {
            return new DexValueArray(dex, (ArrayValue) dexValueBlock);
        }
        return new DexValue(dex, dexValueBlock);
    }
}
