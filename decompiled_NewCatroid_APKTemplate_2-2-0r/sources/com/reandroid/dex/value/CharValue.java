package com.reandroid.dex.value;

import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueChar;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes11.dex */
public class CharValue extends PrimitiveValueBlock {
    public CharValue() {
        super(DexValueType.CHAR);
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public Number getData() {
        int signedValue = (int) getSignedValue();
        if ((signedValue & 255) == signedValue) {
            return Byte.valueOf((byte) signedValue);
        }
        if ((65535 & signedValue) == signedValue) {
            return Short.valueOf((short) signedValue);
        }
        return Integer.valueOf(signedValue);
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public void setData(Number number) {
        number.getClass();
        if (number instanceof Integer) {
            set((char) ((Integer) number).intValue());
        } else if (number instanceof Short) {
            set((char) (((Short) number).shortValue() & 65535));
        } else if (number instanceof Byte) {
            set((char) (((Byte) number).byteValue() & 255));
        }
        throw new NumberFormatException("Invalid '" + number.getClass().getSimpleName() + "' value for char " + number);
    }

    public char get() {
        return (char) (getUnsigned() & WebSocketProtocol.PAYLOAD_SHORT_MAX);
    }

    public void set(char c) {
        setUnsignedValue(c & 65535);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.CHAR;
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public String getHex() {
        return HexUtil.toSignedHex((int) get()) + "C";
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public String getAsString() {
        return DexUtils.quoteChar(get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.TYPE_C;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        set(((SmaliValueChar) smaliValue).getValue());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        DexUtils.appendSingleQuotedChar(smaliWriter, get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public String toString() {
        return getAsString();
    }
}
