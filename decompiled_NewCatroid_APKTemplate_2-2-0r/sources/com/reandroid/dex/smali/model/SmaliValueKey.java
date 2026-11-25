package com.reandroid.dex.smali.model;

import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodHandleKey;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueKey extends SmaliValue {
    private Key value;

    public Key getValue() {
        return this.value;
    }

    public void setValue(Key key) {
        this.value = key;
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        Key value = getValue();
        if (value instanceof StringKey) {
            return DexValueType.STRING;
        }
        if (value instanceof TypeKey) {
            return DexValueType.TYPE;
        }
        if (value instanceof MethodKey) {
            return DexValueType.METHOD;
        }
        if (value instanceof FieldKey) {
            return DexValueType.FIELD;
        }
        if (value instanceof MethodHandleKey) {
            return DexValueType.METHOD_HANDLE;
        }
        return null;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendOptional(getValue());
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        if (smaliReader.get() == 34) {
            setValue(StringKey.read(smaliReader));
            return;
        }
        if (smaliReader.indexOfBeforeLineEnd(':') >= 0) {
            setValue(FieldKey.read(smaliReader));
        } else if (smaliReader.indexOfBeforeLineEnd('(') >= 0) {
            setValue(MethodKey.read(smaliReader));
        } else {
            setValue(TypeKey.read(smaliReader));
        }
    }
}
