package com.reandroid.dex.smali.model;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValue extends Smali {
    public DexValueType<?> getValueType() {
        return null;
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
    }

    public static SmaliValue create(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        int iPosition = smaliReader.position();
        byte b = smaliReader.get(iPosition);
        if (b == 46) {
            SmaliDirective smaliDirective = SmaliDirective.parse(smaliReader, false);
            if (smaliDirective == SmaliDirective.ENUM) {
                return new SmaliValueEnum();
            }
            if (smaliDirective == SmaliDirective.SUB_ANNOTATION) {
                return new SmaliValueAnnotation();
            }
            throw new SmaliParseException("Unrecognized value", smaliReader);
        }
        if (b == 123) {
            return new SmaliValueArray();
        }
        if (b == 39) {
            return new SmaliValueChar();
        }
        if (b == 110) {
            return new SmaliValueNull();
        }
        if (b == 116 || b == 102) {
            return new SmaliValueBoolean();
        }
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
        return new SmaliValueKey();
    }

    public static SmaliValue createDefaultFor(TypeKey typeKey) {
        if (typeKey.isTypeArray() || !typeKey.isPrimitive()) {
            return new SmaliValueNull();
        }
        if (TypeKey.TYPE_I.equals(typeKey)) {
            return new SmaliValueInteger();
        }
        if (TypeKey.TYPE_J.equals(typeKey)) {
            return new SmaliValueLong();
        }
        if (TypeKey.TYPE_D.equals(typeKey)) {
            return new SmaliValueDouble();
        }
        if (TypeKey.TYPE_F.equals(typeKey)) {
            return new SmaliValueFloat();
        }
        if (TypeKey.TYPE_S.equals(typeKey)) {
            return new SmaliValueShort();
        }
        if (TypeKey.TYPE_B.equals(typeKey)) {
            return new SmaliValueByte();
        }
        if (TypeKey.TYPE_C.equals(typeKey)) {
            return new SmaliValueChar();
        }
        if (TypeKey.TYPE_Z.equals(typeKey)) {
            return new SmaliValueBoolean();
        }
        throw new IllegalArgumentException("Undefined: " + typeKey);
    }
}
