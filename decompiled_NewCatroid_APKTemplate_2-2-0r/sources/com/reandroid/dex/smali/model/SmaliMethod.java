package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.common.RegistersTable;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.ProtoKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliMethod extends SmaliDef implements RegistersTable {
    private final SmaliCodeSet codeSet;
    private Integer locals;
    private final SmaliParamSet paramSet;
    private ProtoKey protoKey;

    @Override // com.reandroid.dex.common.RegistersTable
    public boolean ensureLocalRegistersCount(int i) {
        return true;
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public void setParameterRegistersCount(int i) {
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public void setRegistersCount(int i) {
    }

    public SmaliMethod() {
        SmaliParamSet smaliParamSet = new SmaliParamSet();
        this.paramSet = smaliParamSet;
        SmaliCodeSet smaliCodeSet = new SmaliCodeSet();
        this.codeSet = smaliCodeSet;
        smaliParamSet.setParent(this);
        smaliCodeSet.setParent(this);
    }

    @Override // com.reandroid.dex.smali.model.SmaliDef
    public MethodKey getKey() {
        TypeKey defining = getDefining();
        if (defining != null) {
            return getKey(defining);
        }
        return null;
    }

    public MethodKey getKey(TypeKey typeKey) {
        ProtoKey protoKey = getProtoKey();
        if (protoKey == null) {
            return null;
        }
        return new MethodKey(typeKey, getName(), protoKey.getParameterNames(), protoKey.getReturnType());
    }

    public boolean hasInstructions() {
        return getInstructions().hasNext();
    }

    public Iterator<SmaliInstruction> getInstructions() {
        return getCodeSet().getInstructions();
    }

    public boolean hasDebugElements() {
        return getDebugElements().hasNext();
    }

    public Iterator<SmaliDebugElement> getDebugElements() {
        return getCodeSet().getDebugElements();
    }

    public Integer getLocals() {
        return this.locals;
    }

    public void setLocals(Integer num) {
        this.locals = num;
    }

    public ProtoKey getProtoKey() {
        return this.protoKey;
    }

    public void setProtoKey(ProtoKey protoKey) {
        this.protoKey = protoKey;
    }

    public SmaliParamSet getParamSet() {
        return this.paramSet;
    }

    public Iterator<SmaliMethodParameter> getParameters() {
        return getParamSet().iterator();
    }

    public SmaliCodeSet getCodeSet() {
        return this.codeSet;
    }

    public Iterator<SmaliCodeTryItem> getTryItems() {
        return getCodeSet().getTryItems();
    }

    @Override // com.reandroid.dex.smali.model.SmaliDef, com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.METHOD;
    }

    public boolean isConstructor() {
        return Modifier.contains(getAccessFlags(), AccessFlag.CONSTRUCTOR);
    }

    public boolean isDirect() {
        return isConstructor() || isStatic() || isPrivate();
    }

    public boolean isVirtual() {
        return !isDirect();
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        Modifier.append(smaliWriter, getAccessFlags());
        smaliWriter.append((CharSequence) getName());
        getProtoKey().append(smaliWriter);
        smaliWriter.indentPlus();
        Integer locals = getLocals();
        if (locals != null) {
            smaliWriter.newLine();
            SmaliDirective.LOCALS.append(smaliWriter);
            smaliWriter.appendInteger(locals.intValue());
        }
        getParamSet().append(smaliWriter);
        if (hasAnnotation()) {
            smaliWriter.newLine();
            getAnnotation().append(smaliWriter);
        }
        getCodeSet().append(smaliWriter);
        smaliWriter.indentMinus();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, getSmaliDirective());
        setAccessFlags(AccessFlag.parse(smaliReader));
        parseName(smaliReader);
        parseProto(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        while (parseNoneCode(smaliReader)) {
            smaliReader.skipWhitespacesOrComment();
        }
        getCodeSet().parse(smaliReader);
        SmaliParseException.expect(smaliReader, getSmaliDirective(), true);
    }

    private boolean parseNoneCode(SmaliReader smaliReader) throws IOException {
        SmaliDirective smaliDirective = SmaliDirective.parse(smaliReader, false);
        if (smaliDirective == SmaliDirective.LOCALS) {
            parseLocals(smaliReader);
            return true;
        }
        if (smaliDirective == SmaliDirective.ANNOTATION) {
            getOrCreateAnnotation().parse(smaliReader);
            return true;
        }
        if (smaliDirective != SmaliDirective.PARAM) {
            return false;
        }
        getParamSet().parse(smaliReader);
        return true;
    }

    private void parseLocals(SmaliReader smaliReader) throws IOException {
        SmaliParseException.expect(smaliReader, SmaliDirective.LOCALS);
        smaliReader.skipSpaces();
        setLocals(Integer.valueOf(smaliReader.readInteger()));
    }

    private void parseName(SmaliReader smaliReader) {
        smaliReader.skipWhitespaces();
        setName(smaliReader.readString(smaliReader.indexOf('(') - smaliReader.position()));
    }

    private void parseProto(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespaces();
        setProtoKey(ProtoKey.read(smaliReader));
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public int getRegistersCount() {
        return getLocalRegistersCount() + getParameterRegistersCount();
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public int getParameterRegistersCount() {
        int i = !isStatic() ? 1 : 0;
        ProtoKey protoKey = getProtoKey();
        return protoKey != null ? i + protoKey.getParameterRegistersCount() : i;
    }

    @Override // com.reandroid.dex.common.RegistersTable
    public int getLocalRegistersCount() {
        Integer locals = getLocals();
        if (locals != null) {
            return locals.intValue();
        }
        return 0;
    }

    @Override // com.reandroid.dex.smali.model.Smali
    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        TypeKey defining = getDefining();
        if (defining != null) {
            sb.append(defining);
            sb.append(", ");
        }
        sb.append("method = ");
        sb.append(getName());
        sb.append(getProtoKey());
        return sb.toString();
    }
}
