package com.reandroid.dex.smali.model;

import com.reandroid.dex.key.ProtoKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliMethodParameter extends SmaliDebug implements SmaliRegion {
    private SmaliAnnotationSet annotationSet;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private StringKey f255name;
    private final SmaliRegisterSet registerSet = new SmaliRegisterSet();

    public SmaliRegister getSmaliRegister() {
        SmaliRegisterSet registerSet = getRegisterSet();
        if (registerSet.isEmpty()) {
            return null;
        }
        return registerSet.get(0);
    }

    SmaliRegisterSet getRegisterSet() {
        return this.registerSet;
    }

    public StringKey getNameKey() {
        return this.f255name;
    }

    public void setName(StringKey stringKey) {
        this.f255name = stringKey;
    }

    public String getName() {
        StringKey nameKey = getNameKey();
        if (nameKey != null) {
            return nameKey.getString();
        }
        return null;
    }

    public boolean hasAnnotations() {
        SmaliAnnotationSet annotationSet = getAnnotationSet();
        return (annotationSet == null || annotationSet.isEmpty()) ? false : true;
    }

    public SmaliAnnotationSet getAnnotationSet() {
        return this.annotationSet;
    }

    public void setAnnotationSet(SmaliAnnotationSet smaliAnnotationSet) {
        this.annotationSet = smaliAnnotationSet;
        if (smaliAnnotationSet != null) {
            smaliAnnotationSet.setParent(this);
        }
    }

    public int getDefinitionIndex() {
        ProtoKey protoKey;
        SmaliRegister smaliRegister;
        SmaliMethod smaliMethod = (SmaliMethod) getParentInstance(SmaliMethod.class);
        if (smaliMethod == null || (protoKey = smaliMethod.getProtoKey()) == null || (smaliRegister = getSmaliRegister()) == null) {
            return -1;
        }
        int number = smaliRegister.getNumber();
        if (!smaliMethod.isStatic()) {
            number--;
        }
        return protoKey.getParameterIndex(number);
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.PARAM;
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        SmaliDirective smaliDirective = getSmaliDirective();
        smaliDirective.append(smaliWriter);
        getRegisterSet().append(smaliWriter);
        StringKey nameKey = getNameKey();
        if (nameKey != null) {
            smaliWriter.append(", ");
            nameKey.append(smaliWriter);
        }
        SmaliAnnotationSet annotationSet = getAnnotationSet();
        if (annotationSet != null) {
            smaliWriter.indentPlus();
            smaliWriter.newLine();
            annotationSet.append(smaliWriter);
            smaliWriter.indentMinus();
            smaliDirective.appendEnd(smaliWriter);
        }
    }

    @Override // com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.model.SmaliCode, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        super.parse(smaliReader);
        getRegisterSet().parse(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        if (smaliReader.get() == 44) {
            smaliReader.skip(1);
            smaliReader.skipWhitespacesOrComment();
        }
        parseName(smaliReader);
        parseAnnotationSet(smaliReader);
    }

    private void parseName(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        if (smaliReader.get() == 34) {
            setName(StringKey.read(smaliReader));
        }
    }

    private void parseAnnotationSet(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        if (SmaliDirective.parse(smaliReader, false) != SmaliDirective.ANNOTATION) {
            return;
        }
        int iPosition = smaliReader.position();
        SmaliAnnotationSet smaliAnnotationSet = new SmaliAnnotationSet();
        smaliAnnotationSet.parse(smaliReader);
        smaliReader.skipWhitespacesOrComment();
        if (getSmaliDirective().isEnd(smaliReader)) {
            setAnnotationSet(smaliAnnotationSet);
            SmaliDirective.parse(smaliReader);
        } else {
            smaliReader.position(iPosition);
        }
    }
}
