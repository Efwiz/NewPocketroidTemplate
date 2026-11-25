package com.reandroid.arsc.value;

/* JADX INFO: loaded from: classes10.dex */
public class EntryHeader extends ValueHeader {
    private static final short HEADER_SIZE_SCALAR = 8;

    public EntryHeader() {
        super(8);
    }

    @Override // com.reandroid.arsc.value.ValueHeader
    public String toString() {
        if (isNull()) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int size = getSize();
        int size2 = readSize();
        if (size != 8) {
            sb.append("size=").append(size);
        }
        if (size != size2) {
            sb.append(" readSize=").append(size2);
        }
        if (isComplex()) {
            sb.append(" complex");
        }
        if (isPublic()) {
            sb.append(" public");
        }
        if (isWeak()) {
            sb.append(" weak");
        }
        if (isCompact()) {
            sb.append(" compact");
        }
        String name2 = getName();
        if (name2 != null) {
            sb.append(" name=").append(name2);
        } else {
            sb.append(" key=").append(getKey());
        }
        return sb.toString();
    }
}
