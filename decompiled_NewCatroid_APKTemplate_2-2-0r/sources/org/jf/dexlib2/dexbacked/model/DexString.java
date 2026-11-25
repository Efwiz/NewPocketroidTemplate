package org.jf.dexlib2.dexbacked.model;

/* JADX INFO: loaded from: classes6.dex */
public class DexString {
    private final int index;
    private final DexStringSection stringSection;
    private String value;

    public DexString(DexStringSection dexStringSection, int i, String str) {
        this.stringSection = dexStringSection;
        this.index = i;
        this.value = str;
    }

    DexStringSection getStringSection() {
        return this.stringSection;
    }

    public int getIndex() {
        return this.index;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DexString dexString = (DexString) obj;
        return this.index == dexString.index && this.stringSection == dexString.stringSection;
    }

    public int hashCode() {
        return this.index;
    }

    public String toString() {
        return this.index + ": " + this.value;
    }
}
