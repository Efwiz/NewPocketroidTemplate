package com.reandroid.dex.smali.model;

import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliCodeSet extends SmaliSet<SmaliCode> {
    private int addressOffset;
    private SmaliNullInstruction nullInstruction;

    public int getAddressOffset() {
        return this.addressOffset;
    }

    public void setAddressOffset(int i) {
        this.addressOffset = i;
    }

    public void updateAddresses() {
        int addressOffset = getAddressOffset();
        Iterator<SmaliInstruction> instructions = getInstructions();
        while (instructions.hasNext()) {
            SmaliInstruction next = instructions.next();
            next.setAddress(addressOffset);
            addressOffset += next.getCodeUnits();
        }
        SmaliInstruction nullInstruction = getNullInstruction();
        if (nullInstruction != null) {
            nullInstruction.setAddress(addressOffset);
        }
    }

    public Iterator<SmaliInstruction> getInstructions(final SmaliLabel smaliLabel) {
        if (smaliLabel != null) {
            return FilterIterator.of(getInstructions(), new Predicate() { // from class: com.reandroid.dex.smali.model.SmaliCodeSet$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((SmaliInstruction) obj).hasLabelOperand(smaliLabel);
                }
            });
        }
        return EmptyIterator.of();
    }

    public Iterator<SmaliInstruction> getInstructions() {
        return iterator(SmaliInstruction.class);
    }

    public Iterator<SmaliCodeTryItem> getTryItems() {
        return iterator(SmaliCodeTryItem.class);
    }

    public Iterator<SmaliDebug> getDebugs() {
        return iterator(SmaliDebug.class);
    }

    public Iterator<SmaliDebugElement> getDebugElements() {
        return iterator(SmaliDebugElement.class);
    }

    public Iterator<SmaliMethodParameter> getMethodParameters() {
        return iterator(SmaliMethodParameter.class);
    }

    public void clearInstructions() {
        removeInstances(SmaliDebug.class);
    }

    public void clearDebugs() {
        removeInstances(SmaliDebug.class);
    }

    public SmaliInstruction getNullInstruction() {
        SmaliNullInstruction smaliNullInstruction = this.nullInstruction;
        if (!needsNullInstruction()) {
            if (smaliNullInstruction == null) {
                return smaliNullInstruction;
            }
            smaliNullInstruction.setParent(null);
            this.nullInstruction = null;
            return null;
        }
        if (smaliNullInstruction != null) {
            return smaliNullInstruction;
        }
        SmaliNullInstruction smaliNullInstruction2 = new SmaliNullInstruction();
        smaliNullInstruction2.setParent(this);
        this.nullInstruction = smaliNullInstruction2;
        return smaliNullInstruction2;
    }

    private boolean needsNullInstruction() {
        if (isEmpty()) {
            return false;
        }
        return !(get(size() - 1) instanceof SmaliInstruction);
    }

    @Override // com.reandroid.dex.smali.model.SmaliSet, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        if (isEmpty()) {
            return;
        }
        smaliWriter.newLine();
        smaliWriter.appendAll(iterator());
    }

    @Override // com.reandroid.dex.smali.model.SmaliSet, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        super.parse(smaliReader);
        updateAddresses();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.smali.model.SmaliSet
    public SmaliCode createNext(SmaliReader smaliReader) {
        SmaliDirective smaliDirective = SmaliDirective.parse(smaliReader, false);
        if (smaliDirective != null) {
            return createFor(smaliDirective);
        }
        smaliReader.skipWhitespaces();
        if (smaliReader.get() == 58) {
            return new SmaliLabel();
        }
        if (Opcode.parseSmali(smaliReader, false) != null) {
            return new SmaliInstruction();
        }
        return null;
    }

    private static SmaliCode createFor(SmaliDirective smaliDirective) {
        if (smaliDirective == SmaliDirective.LINE) {
            return new SmaliLineNumber();
        }
        if (smaliDirective == SmaliDirective.CATCH || smaliDirective == SmaliDirective.CATCH_ALL) {
            return new SmaliCodeTryItem();
        }
        if (smaliDirective == SmaliDirective.PARAM) {
            return new SmaliMethodParameter();
        }
        if (smaliDirective == SmaliDirective.END_LOCAL) {
            return new SmaliDebugEndLocal();
        }
        if (smaliDirective == SmaliDirective.LOCAL) {
            return new SmaliDebugLocal();
        }
        if (smaliDirective == SmaliDirective.RESTART_LOCAL) {
            return new SmaliDebugRestartLocal();
        }
        if (smaliDirective == SmaliDirective.ARRAY_DATA) {
            return new SmaliPayloadArray();
        }
        if (smaliDirective == SmaliDirective.PACKED_SWITCH) {
            return new SmaliPayloadPackedSwitch();
        }
        if (smaliDirective == SmaliDirective.SPARSE_SWITCH) {
            return new SmaliPayloadSparseSwitch();
        }
        if (smaliDirective == SmaliDirective.PROLOGUE) {
            return new SmaliDebugPrologue();
        }
        if (smaliDirective == SmaliDirective.EPILOGUE) {
            return new SmaliDebugEpilogue();
        }
        return null;
    }
}
