package com.reandroid.dex.debug;

import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliDebugElement;
import com.reandroid.dex.smali.model.SmaliDebugRegister;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
abstract class DebugRegisterNumber extends DebugElement {
    private final Ule128Item registerNumber;

    DebugRegisterNumber(int i, int i2) {
        super(i + 1, i2);
        Ule128Item ule128Item = new Ule128Item();
        this.registerNumber = ule128Item;
        addChild(1, ule128Item);
    }

    DebugRegisterNumber(int i, DebugElementType<?> debugElementType) {
        this(i, debugElementType.getFlag());
    }

    public int getRegisterNumber() {
        return this.registerNumber.get();
    }

    public void setRegister(int i) {
        this.registerNumber.set(i);
    }

    @Override // com.reandroid.dex.debug.DebugElement, com.reandroid.dex.ins.ExtraLine
    public void appendExtra(SmaliWriter smaliWriter) throws IOException {
        if (isValid()) {
            getSmaliDirective().append(smaliWriter);
            smaliWriter.appendRegister(getRegisterNumber());
        }
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void merge(DebugElement debugElement) {
        super.merge(debugElement);
        this.registerNumber.set(((DebugRegisterNumber) debugElement).registerNumber.get());
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void fromSmali(SmaliDebugElement smaliDebugElement) throws IOException {
        super.fromSmali(smaliDebugElement);
        setRegister(((SmaliDebugRegister) smaliDebugElement).getRegister().getValue());
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DebugRegisterNumber debugRegisterNumber = (DebugRegisterNumber) obj;
        return getFlag() == debugRegisterNumber.getFlag() && this.registerNumber.get() == debugRegisterNumber.registerNumber.get();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public int hashCode() {
        return (getFlag() * 31) + this.registerNumber.get();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public String toString() {
        return getElementType() + " v" + getRegisterNumber();
    }
}
