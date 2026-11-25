package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import java.io.IOException;
import java.lang.Number;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SmaliValueNumber<T extends Number> extends SmaliValue {
    public abstract T getNumber();

    public abstract int getWidth();

    public abstract void setNumber(T t);

    public abstract int unsignedInt();

    public abstract long unsignedLong();

    public static <T1 extends Number> SmaliValueNumber<T1> createFor(T1 t1) {
        return (SmaliValueNumber<T1>) createUnchecked(t1);
    }

    private static SmaliValueNumber<?> createUnchecked(Number number) {
        number.getClass();
        if (number instanceof Byte) {
            return new SmaliValueByte(((Byte) number).byteValue());
        }
        if (number instanceof Short) {
            return new SmaliValueShort(((Short) number).shortValue());
        }
        if (number instanceof Integer) {
            return new SmaliValueInteger(((Integer) number).intValue());
        }
        if (number instanceof Long) {
            return new SmaliValueLong(((Long) number).longValue());
        }
        if (number instanceof Float) {
            return new SmaliValueFloat(((Float) number).floatValue());
        }
        if (number instanceof Double) {
            return new SmaliValueDouble(((Double) number).doubleValue());
        }
        throw new RuntimeException("Unrecognized number class: " + number.getClass());
    }

    public static SmaliValueNumber<?> createNumber(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        int iPosition = smaliReader.position();
        byte b = smaliReader.get(iPosition);
        if (b == 45 || b == 43) {
            iPosition++;
            b = smaliReader.get(iPosition);
        }
        if (b == 48 && smaliReader.get(iPosition + 1) == 120) {
            byte b2 = smaliReader.get(smaliReader.indexOfWhiteSpaceOrComment() - 1);
            if (b2 == 116) {
                return new SmaliValueByte();
            }
            if (b2 == 83 || b2 == 115) {
                return new SmaliValueShort();
            }
            if (b2 == 76) {
                return new SmaliValueLong();
            }
            return new SmaliValueInteger();
        }
        if (smaliReader.startsWith(new byte[]{73, 110, 102, 105, 110, 105, 116, 121}, iPosition)) {
            if (smaliReader.get(8 + iPosition) == 102) {
                return new SmaliValueFloat();
            }
            return new SmaliValueDouble();
        }
        if (smaliReader.startsWith(new byte[]{78, 97, 78}, iPosition)) {
            if (smaliReader.get(3 + iPosition) == 102) {
                return new SmaliValueFloat();
            }
            return new SmaliValueDouble();
        }
        if (b <= 57 && b >= 48) {
            if (smaliReader.get(smaliReader.indexOfWhiteSpaceOrComment() - 1) == 102) {
                return new SmaliValueFloat();
            }
            return new SmaliValueDouble();
        }
        throw new SmaliParseException("Unrecognized number format", smaliReader);
    }
}
