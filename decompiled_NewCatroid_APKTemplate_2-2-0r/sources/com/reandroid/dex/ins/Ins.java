package com.reandroid.dex.ins;

import com.reandroid.dex.base.DexException;
import com.reandroid.dex.common.RegisterFormat;
import com.reandroid.dex.data.FixedDexContainerWithTool;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.data.MethodDef;
import com.reandroid.dex.debug.DebugElement;
import com.reandroid.dex.debug.DebugLineNumber;
import com.reandroid.dex.debug.DebugSequence;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.InstanceIterator;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class Ins extends FixedDexContainerWithTool implements SmaliFormat {
    private ExtraLineList extraLineList;
    private final Opcode<?> opcode;
    private Ins targetIns;

    public boolean equals(Object obj) {
        return obj == this;
    }

    public int getOutSize() {
        return 0;
    }

    public void replaceKeys(Key key, Key key2) {
    }

    Ins(int i, Opcode<?> opcode) {
        super(i);
        this.opcode = opcode;
        this.extraLineList = ExtraLineList.EMPTY;
    }

    Ins(Opcode<?> opcode) {
        this(1, opcode);
    }

    public MethodDef getMethodDef() {
        InstructionList instructionList = getInstructionList();
        if (instructionList != null) {
            return instructionList.getMethodDef();
        }
        return null;
    }

    public MethodKey getMethodKey() {
        MethodDef methodDef = getMethodDef();
        if (methodDef != null) {
            return methodDef.getKey();
        }
        return null;
    }

    public Ins edit() {
        MethodDef methodDef = getMethodDef();
        InstructionList instructionList = methodDef.getInstructionList();
        methodDef.edit();
        InstructionList instructionList2 = methodDef.getInstructionList();
        return instructionList != instructionList2 ? instructionList2.get(getIndex()) : this;
    }

    public DebugSequence getOrCreateDebugSequence() {
        InstructionList instructionList = getInstructionList();
        if (instructionList != null) {
            return instructionList.getOrCreateDebugSequence();
        }
        return null;
    }

    public DebugSequence getDebugSequence() {
        InstructionList instructionList = getInstructionList();
        if (instructionList != null) {
            return instructionList.getDebugSequence();
        }
        return null;
    }

    InsBlockList getInsBlockList() {
        return (InsBlockList) getParentInstance(InsBlockList.class);
    }

    public InstructionList getInstructionList() {
        return (InstructionList) getParentInstance(InstructionList.class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateTargetAddress() {
        if (this instanceof Label) {
            Ins targetIns = getTargetIns();
            if (targetIns == null) {
                throw new NullPointerException("Null target: " + this + ", " + getMethodKey());
            }
            ((Label) this).setTargetAddress(targetIns.getAddress());
        }
    }

    public void transferExtraLinesTo(Ins ins) {
        ins.extraLineList = ExtraLineList.add(this.extraLineList, ins.getExtraLines());
        Iterator<ExtraLine> extraLines = ins.getExtraLines();
        while (extraLines.hasNext()) {
            ExtraLine next = extraLines.next();
            next.setTargetIns(null);
            next.setTargetIns(ins);
        }
        Ins ins2 = this.targetIns;
        if (ins2 != null && (ins instanceof Label)) {
            ins.setTargetIns(ins2);
        }
        clearExtraLines();
    }

    public void replace(Ins ins) {
        InstructionList instructionList;
        if (ins == null || ins == this || (instructionList = getInstructionList()) == null) {
            return;
        }
        instructionList.replace(this, ins);
    }

    public <T1 extends Ins> T1 replace(Opcode<T1> opcode) {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null) {
            throw new DexException("Missing parent InstructionList");
        }
        return (T1) instructionList.replace(this, opcode);
    }

    public <T1 extends Ins> T1 createNext(Opcode<T1> opcode) {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null) {
            throw new DexException("Parent " + getClass().getSimpleName() + " == null");
        }
        return (T1) instructionList.createAt(getIndex() + 1, opcode);
    }

    public void moveTo(int i) {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null) {
            throw new DexException("Parent " + getClass().getSimpleName() + " == null");
        }
        instructionList.moveTo(this, i);
    }

    public boolean is(Opcode<?> opcode) {
        return opcode == getOpcode();
    }

    public Opcode<?> getOpcode() {
        return this.opcode;
    }

    public RegisterFormat getRegisterFormat() {
        return getOpcode().getRegisterFormat();
    }

    public int getCodeUnits() {
        return countBytes() / 2;
    }

    public int getAddress() {
        InsBlockList insBlockList = getInsBlockList();
        if (insBlockList != null) {
            return insBlockList.addressOf(this);
        }
        return -1;
    }

    void linkTargetIns() {
        if (this.targetIns == null) {
            setTargetIns(findTargetIns());
        }
        if ((this instanceof Label) && this.targetIns == null) {
            throw new NullPointerException("Missing target: " + this + ", " + getMethodKey());
        }
    }

    void unLinkTargetIns() {
        if (this.targetIns != null) {
            setTargetIns(null);
        }
        clearExtraLines();
    }

    public Ins getTargetIns() {
        Ins insEnsureTargetNotRemoved = ensureTargetNotRemoved();
        if (insEnsureTargetNotRemoved != null) {
            return insEnsureTargetNotRemoved;
        }
        setTargetIns(findTargetIns());
        return this.targetIns;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setTargetIns(Ins ins) {
        if (ins == this || ins == this.targetIns) {
            return;
        }
        this.targetIns = ins;
        if (ins != null) {
            Label label = (Label) this;
            label.setTargetAddress(ins.getAddress());
            ins.addExtraLine(label);
        }
    }

    private Ins ensureTargetNotRemoved() {
        Ins ins = this.targetIns;
        if (ins == null || !ins.isRemoved()) {
            return ins;
        }
        this.targetIns = null;
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Ins findTargetIns() {
        InsBlockList insBlockList;
        if (!(this instanceof Label) || (insBlockList = getInsBlockList()) == null) {
            return null;
        }
        int targetAddress = ((Label) this).getTargetAddress();
        Ins atAddress = insBlockList.getAtAddress(targetAddress);
        if (targetAddress == 0 && atAddress == this) {
            return null;
        }
        return atAddress;
    }

    public void addExtraLine(ExtraLine extraLine) {
        if (extraLine != this) {
            this.extraLineList = ExtraLineList.add(this.extraLineList, extraLine);
        }
    }

    public DebugLineNumber getDebugLineNumber() {
        return (DebugLineNumber) CollectionUtil.getFirst(getDebugLineNumbers());
    }

    public Iterator<DebugLineNumber> getDebugLineNumbers() {
        return InstanceIterator.of(getExtraLines(), DebugLineNumber.class);
    }

    public boolean removeDebugElement(DebugElement debugElement) {
        DebugSequence debugSequence = getDebugSequence();
        if (debugSequence != null) {
            return debugSequence.remove(debugElement);
        }
        return false;
    }

    public Iterator<DebugElement> getDebugElements() {
        return InstanceIterator.of(getExtraLines(), DebugElement.class);
    }

    public Iterator<ExtraLine> getExtraLines() {
        return this.extraLineList.iterator();
    }

    public <T1> Iterator<T1> getExtraLines(Class<T1> cls) {
        return this.extraLineList.iterator(cls);
    }

    public void clearExtraLines() {
        this.extraLineList = ExtraLineList.EMPTY;
    }

    public boolean hasExtraLines() {
        return !this.extraLineList.isEmpty();
    }

    private void appendExtraLines(SmaliWriter smaliWriter) throws IOException {
        Iterator<ExtraLine> extraLines = getExtraLines();
        Object obj = null;
        ExtraLine next = null;
        boolean z = false;
        while (extraLines.hasNext()) {
            next = extraLines.next();
            if (!next.isEqualExtraLine(obj)) {
                smaliWriter.newLine();
                next.appendExtra(smaliWriter);
                if (!z) {
                    z = next.getSortOrder() == 3;
                }
                obj = next;
            }
        }
        if (!z || next.getSortOrder() < 3) {
            return;
        }
        smaliWriter.newLine();
    }

    public Iterator<IdItem> usedIds() {
        return EmptyIterator.of();
    }

    public boolean isRemoved() {
        return getParent() == null;
    }

    public void merge(Ins ins) {
        throw new RuntimeException("merge method not implemented, opcode = " + getOpcode());
    }

    public void fromSmali(SmaliInstruction smaliInstruction) throws IOException {
        throw new RuntimeException("fromSmali method not implemented, opcode = " + getOpcode());
    }

    void validateOpcode(SmaliInstruction smaliInstruction) throws IOException {
        if (getOpcode() != smaliInstruction.getOpcode()) {
            throw new IOException("Mismatch opcode " + getOpcode() + " vs " + smaliInstruction.getOpcode());
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public final void append(SmaliWriter smaliWriter) throws IOException {
        appendExtraLines(smaliWriter);
        smaliWriter.newLine();
        appendCode(smaliWriter);
    }

    public void appendCode(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getOpcode().getName());
        smaliWriter.append(StringUtil.SPACE);
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        SmaliWriter smaliWriter = new SmaliWriter(stringWriter);
        try {
            appendCode(smaliWriter);
            smaliWriter.close();
            return stringWriter.toString().trim();
        } catch (Throwable th) {
            return th.toString();
        }
    }

    static int toSigned(int i, int i2) {
        return i <= i2 / 2 ? i : (i - i2) - 1;
    }
}
