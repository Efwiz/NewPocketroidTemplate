package org.jf.dexlib2.dexbacked.model;

/* JADX INFO: loaded from: classes6.dex */
public class DexTypeString {
    private final DexString dexString;
    private final int index;

    public DexTypeString(DexString dexString, int i) {
        this.dexString = dexString;
        this.index = i;
    }

    DexString getDexString() {
        return this.dexString;
    }

    public int getIndex() {
        return this.index;
    }

    public String getValue() {
        return this.dexString.getValue();
    }

    public void setValue(String str) {
        this.dexString.setValue(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DexTypeString dexTypeString = (DexTypeString) obj;
        return this.index == dexTypeString.index && dexTypeString.getDexString().getStringSection() == this.dexString.getStringSection();
    }

    public int hashCode() {
        return this.index;
    }

    public String toString() {
        return this.index + ": " + getValue();
    }
}
