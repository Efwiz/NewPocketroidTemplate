package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SmaliCodeExceptionHandler extends SmaliCode implements SmaliRegion {
    private final SmaliLabel catchLabel;
    private final SmaliLabel end;
    private final SmaliLabel start;

    public void appendType(SmaliWriter smaliWriter) throws IOException {
    }

    void parseType(SmaliReader smaliReader) throws IOException {
    }

    public SmaliCodeExceptionHandler() {
        SmaliLabel smaliLabel = new SmaliLabel();
        this.start = smaliLabel;
        SmaliLabel smaliLabel2 = new SmaliLabel();
        this.end = smaliLabel2;
        SmaliLabel smaliLabel3 = new SmaliLabel();
        this.catchLabel = smaliLabel3;
        smaliLabel.setParent(this);
        smaliLabel2.setParent(this);
        smaliLabel3.setParent(this);
    }

    public int getAddress() {
        SmaliCodeTryItem tryItem = getTryItem();
        if (tryItem == null) {
            return -1;
        }
        return tryItem.getAddress();
    }

    public SmaliLabel getStart() {
        return this.start;
    }

    public SmaliLabel getEnd() {
        return this.end;
    }

    public SmaliLabel getCatchLabel() {
        return this.catchLabel;
    }

    SmaliCodeTryItem getTryItem() {
        return (SmaliCodeTryItem) getParentInstance(SmaliCodeTryItem.class);
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        appendType(smaliWriter);
        smaliWriter.append('{');
        getStart().append(smaliWriter);
        smaliWriter.append(" .. ");
        getEnd().append(smaliWriter);
        smaliWriter.append('}');
        smaliWriter.append(StringUtil.SPACE);
        getCatchLabel().append(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.model.SmaliCode, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipSpaces();
        SmaliParseException.expect(smaliReader, getSmaliDirective());
        parseType(smaliReader);
        smaliReader.skipSpaces();
        SmaliParseException.expect(smaliReader, '{');
        getStart().parse(smaliReader);
        smaliReader.skipSpaces();
        SmaliParseException.expect(smaliReader, '.');
        SmaliParseException.expect(smaliReader, '.');
        getEnd().parse(smaliReader);
        smaliReader.skipSpaces();
        SmaliParseException.expect(smaliReader, '}');
        getCatchLabel().parse(smaliReader);
    }
}
