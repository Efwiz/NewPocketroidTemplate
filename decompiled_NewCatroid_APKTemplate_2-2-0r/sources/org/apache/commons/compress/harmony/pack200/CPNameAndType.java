package org.apache.commons.compress.harmony.pack200;

/* JADX INFO: loaded from: classes13.dex */
public class CPNameAndType extends ConstantPoolEntry implements Comparable {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final CPUTF8 f451name;
    private final CPSignature signature;

    public CPNameAndType(CPUTF8 cputf8, CPSignature cPSignature) {
        this.f451name = cputf8;
        this.signature = cPSignature;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (!(obj instanceof CPNameAndType)) {
            return 0;
        }
        CPNameAndType cPNameAndType = (CPNameAndType) obj;
        int iCompareTo = this.signature.compareTo(cPNameAndType.signature);
        return iCompareTo == 0 ? this.f451name.compareTo(cPNameAndType.f451name) : iCompareTo;
    }

    public String getName() {
        return this.f451name.getUnderlyingString();
    }

    public int getNameIndex() {
        return this.f451name.getIndex();
    }

    public int getTypeIndex() {
        return this.signature.getIndex();
    }

    public String toString() {
        return this.f451name + ":" + this.signature;
    }
}
