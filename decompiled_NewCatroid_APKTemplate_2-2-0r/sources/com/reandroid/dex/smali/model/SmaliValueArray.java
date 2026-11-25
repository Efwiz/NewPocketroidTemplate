package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.value.DexValueType;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliValueArray extends SmaliValue implements Iterable<SmaliValue> {
    private final SmaliSet<SmaliValue> values;

    public SmaliValueArray() {
        SmaliSet<SmaliValue> smaliSet = new SmaliSet<>();
        this.values = smaliSet;
        smaliSet.setParent(this);
    }

    public SmaliSet<SmaliValue> getValues() {
        return this.values;
    }

    public boolean contains(SmaliValue smaliValue) {
        return this.values.contains(smaliValue);
    }

    public SmaliValue remove(int i) {
        return (SmaliValue) this.values.remove(i);
    }

    public boolean remove(SmaliValue smaliValue) {
        return this.values.remove(smaliValue);
    }

    public void clear() {
        this.values.clear();
    }

    public boolean add(SmaliValue smaliValue) {
        return this.values.add(smaliValue);
    }

    public SmaliValue get(int i) {
        return (SmaliValue) this.values.get(i);
    }

    public int size() {
        return this.values.size();
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override // java.lang.Iterable
    public Iterator<SmaliValue> iterator() {
        return this.values.iterator();
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue
    public DexValueType<?> getValueType() {
        return DexValueType.ARRAY;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append('{');
        boolean z = false;
        for (SmaliValue smaliValue : this) {
            if (z) {
                smaliWriter.append(',');
            } else {
                smaliWriter.indentPlus();
            }
            smaliWriter.newLine();
            smaliValue.append(smaliWriter);
            z = true;
        }
        if (z) {
            smaliWriter.indentMinus();
            smaliWriter.newLine();
        }
        smaliWriter.append('}');
    }

    @Override // com.reandroid.dex.smali.model.SmaliValue, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespaces();
        SmaliParseException.expect(smaliReader, '{');
        smaliReader.skipWhitespaces();
        while (true) {
            SmaliValue smaliValueCreateNext = createNext(smaliReader);
            if (smaliValueCreateNext != null) {
                add(smaliValueCreateNext);
                smaliValueCreateNext.parse(smaliReader);
                smaliReader.skipWhitespacesOrComment();
                if (smaliReader.get() == 44) {
                    smaliReader.skip(1);
                }
            } else {
                smaliReader.skipWhitespaces();
                smaliReader.readASCII();
                return;
            }
        }
    }

    private SmaliValue createNext(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        if (smaliReader.get() == 125) {
            return null;
        }
        return SmaliValue.create(smaliReader);
    }
}
