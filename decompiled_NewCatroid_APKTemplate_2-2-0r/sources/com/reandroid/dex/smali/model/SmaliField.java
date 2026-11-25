package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliField extends SmaliDef {
    private TypeKey type;
    private SmaliValue value;

    @Override // com.reandroid.dex.smali.model.SmaliDef
    public FieldKey getKey() {
        TypeKey defining = getDefining();
        if (defining != null) {
            return getKey(defining);
        }
        return null;
    }

    public FieldKey getKey(TypeKey typeKey) {
        TypeKey type = getType();
        if (type == null) {
            return null;
        }
        return new FieldKey(typeKey.getTypeName(), getName(), type.getTypeName());
    }

    public TypeKey getType() {
        return this.type;
    }

    public void setType(TypeKey typeKey) {
        this.type = typeKey;
    }

    public SmaliValue getValue() {
        return this.value;
    }

    public void setValue(SmaliValue smaliValue) {
        this.value = smaliValue;
        if (smaliValue != null) {
            smaliValue.setParent(this);
        }
    }

    void fixUninitializedFinalValue() {
        if (getValue() == null && isStatic() && isFinal()) {
            SmaliClass smaliClass = getSmaliClass();
            FieldKey key = getKey();
            if (smaliClass == null || key == null || isInitializedInStaticConstructor(smaliClass, key)) {
                return;
            }
            setValue(SmaliValue.createDefaultFor(key.getType()));
        }
    }

    private boolean isInitializedInStaticConstructor(SmaliClass smaliClass, FieldKey fieldKey) {
        SmaliMethod staticConstructor = smaliClass.getStaticConstructor();
        if (staticConstructor == null) {
            return false;
        }
        Iterator<SmaliInstruction> instructions = staticConstructor.getInstructions();
        while (instructions.hasNext()) {
            SmaliInstruction next = instructions.next();
            if (fieldKey.equals(next.getKey())) {
                return next.getOpcode().isFieldStaticPut();
            }
        }
        return false;
    }

    @Override // com.reandroid.dex.smali.model.SmaliDef, com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.FIELD;
    }

    public boolean isInstance() {
        return !isStatic();
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        Modifier.append(smaliWriter, getAccessFlags());
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(':');
        getType().append(smaliWriter);
        SmaliValue value = getValue();
        if (value != null) {
            smaliWriter.append(" = ");
            value.append(smaliWriter);
        }
        SmaliAnnotationSet annotation = getAnnotation();
        if (annotation == null || annotation.isEmpty()) {
            return;
        }
        smaliWriter.indentPlus();
        smaliWriter.newLine();
        annotation.append(smaliWriter);
        smaliWriter.indentMinus();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        SmaliParseException.expect(smaliReader, getSmaliDirective());
        setAccessFlags(AccessFlag.parse(smaliReader));
        parseName(smaliReader);
        setType(TypeKey.read(smaliReader));
        parseValue(smaliReader);
        parseAnnotationSet(smaliReader);
    }

    private void parseName(SmaliReader smaliReader) {
        smaliReader.skipWhitespaces();
        setName(smaliReader.readString(smaliReader.indexOf(':') - smaliReader.position()));
        smaliReader.skip(1);
    }

    private void parseValue(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespaces();
        if (!smaliReader.finished() && smaliReader.get() == 61) {
            smaliReader.skip(1);
            smaliReader.skipWhitespaces();
            SmaliValue smaliValueCreate = SmaliValue.create(smaliReader);
            setValue(smaliValueCreate);
            smaliValueCreate.parse(smaliReader);
        }
    }

    private void parseAnnotationSet(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        if (SmaliDirective.parse(smaliReader, false) != SmaliDirective.ANNOTATION) {
            getSmaliDirective().skipEnd(smaliReader);
            return;
        }
        int iPosition = smaliReader.position();
        SmaliAnnotationSet smaliAnnotationSet = new SmaliAnnotationSet();
        smaliAnnotationSet.parse(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        if (getSmaliDirective().isEnd(smaliReader)) {
            setAnnotation(smaliAnnotationSet);
            SmaliDirective.parse(smaliReader);
        } else {
            smaliReader.position(iPosition);
        }
    }

    @Override // com.reandroid.dex.smali.model.Smali
    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        TypeKey defining = getDefining();
        if (defining != null) {
            sb.append(defining);
            sb.append(", ");
        }
        sb.append("field = ");
        sb.append(getName());
        sb.append(':');
        sb.append(getType());
        SmaliValue value = getValue();
        if (value != null) {
            sb.append(" = ");
            sb.append(value.toDebugString());
        }
        return sb.toString();
    }
}
