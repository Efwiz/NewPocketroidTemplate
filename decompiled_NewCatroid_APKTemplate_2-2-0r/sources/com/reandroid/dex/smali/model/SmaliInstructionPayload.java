package com.reandroid.dex.smali.model;

import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.Smali;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SmaliInstructionPayload<T extends Smali> extends SmaliInstruction implements SmaliRegion {
    private final SmaliSet<T> entries;
    private final SmaliInstructionOperand operand;

    @Override // com.reandroid.dex.smali.model.SmaliInstruction
    public abstract int getCodeUnits();

    @Override // com.reandroid.dex.smali.model.SmaliInstruction
    public abstract Opcode<?> getOpcode();

    public abstract T newEntry(SmaliReader smaliReader) throws IOException;

    public SmaliInstructionPayload(SmaliInstructionOperand smaliInstructionOperand) {
        this.operand = smaliInstructionOperand;
        SmaliSet<T> smaliSet = new SmaliSet<>();
        this.entries = smaliSet;
        smaliInstructionOperand.setParent(this);
        smaliSet.setParent(this);
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstruction
    public SmaliInstructionOperand getOperand() {
        return this.operand;
    }

    public SmaliSet<T> getEntries() {
        return this.entries;
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstruction, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        getOperand().append(smaliWriter);
        smaliWriter.indentPlus();
        getEntries().append(smaliWriter);
        smaliWriter.indentMinus();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.model.SmaliInstruction, com.reandroid.dex.smali.model.SmaliCode, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        setOrigin(smaliReader.getCurrentOrigin());
        SmaliDirective smaliDirective = getSmaliDirective();
        SmaliParseException.expect(smaliReader, smaliDirective);
        smaliReader.skipSpaces();
        parseOperand(getOpcode(), smaliReader);
        smaliReader.skipWhitespacesOrComment();
        SmaliSet<T> entries = getEntries();
        while (!smaliDirective.isEnd(smaliReader)) {
            Smali smaliNewEntry = newEntry(smaliReader);
            entries.add(smaliNewEntry);
            smaliNewEntry.parse(smaliReader);
            smaliReader.skipWhitespacesOrComment();
        }
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, getSmaliDirective(), true);
    }

    void parseOperand(Opcode<?> opcode, SmaliReader smaliReader) throws IOException {
        getOperand().parse(opcode, smaliReader);
    }
}
