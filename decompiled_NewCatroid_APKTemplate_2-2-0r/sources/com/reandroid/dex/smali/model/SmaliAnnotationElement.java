package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliAnnotationElement extends Smali {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f251name;
    private SmaliValue value;

    public String getName() {
        return this.f251name;
    }

    public void setName(String str) {
        this.f251name = str;
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

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(" = ");
        getValue().append(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespaces();
        int iIndexOfWhiteSpace = smaliReader.indexOfWhiteSpace();
        int iIndexOf = smaliReader.indexOf('=');
        if (iIndexOfWhiteSpace >= iIndexOf) {
            iIndexOfWhiteSpace = iIndexOf;
        }
        setName(smaliReader.readString(iIndexOfWhiteSpace - smaliReader.position()));
        smaliReader.skipWhitespaces();
        smaliReader.readASCII();
        smaliReader.skipWhitespaces();
        SmaliValue smaliValueCreate = SmaliValue.create(smaliReader);
        setValue(smaliValueCreate);
        smaliValueCreate.parse(smaliReader);
    }
}
