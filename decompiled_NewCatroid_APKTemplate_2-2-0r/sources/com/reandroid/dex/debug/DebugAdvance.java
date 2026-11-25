package com.reandroid.dex.debug;

import com.reandroid.dex.base.Le128;

/* JADX INFO: loaded from: classes11.dex */
public abstract class DebugAdvance extends DebugElement {
    private final Le128 advance;

    DebugAdvance(DebugElementType<?> debugElementType, Le128 le128) {
        super(1, debugElementType);
        this.advance = le128;
        addChild(1, le128);
    }

    public int getAdvance() {
        return this.advance.get();
    }

    public void setAdvance(int i) {
        this.advance.set(i);
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public boolean isValid() {
        return (isRemoved() || getAdvance() == 0) ? false : true;
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void merge(DebugElement debugElement) {
        super.merge(debugElement);
        this.advance.set(((DebugAdvance) debugElement).advance.get());
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DebugAdvance debugAdvance = (DebugAdvance) obj;
        return getFlag() == debugAdvance.getFlag() && this.advance.get() == debugAdvance.advance.get();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public int hashCode() {
        return (getFlag() * 31) + this.advance.get();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public String toString() {
        return "advance=" + this.advance;
    }
}
