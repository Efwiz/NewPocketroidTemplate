package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.util.Base64;
import org.apache.xerces.impl.dv.util.ByteListImpl;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes13.dex */
public class Base64BinaryDV extends TypeValidator {

    private static final class XBase64 extends ByteListImpl {
        public XBase64(byte[] bArr) {
            super(bArr);
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            if (!(obj instanceof XBase64)) {
                return false;
            }
            byte[] bArr = ((XBase64) obj).data;
            int length = this.data.length;
            if (length != bArr.length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (this.data[i] != bArr[i]) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            int i = 0;
            for (int i2 = 0; i2 < this.data.length; i2++) {
                i = (i * 37) + (this.data[i2] & 255);
            }
            return i;
        }

        @Override // java.util.AbstractCollection
        public synchronized String toString() {
            if (this.canonical == null) {
                this.canonical = Base64.encode(this.data);
            }
            return this.canonical;
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        byte[] bArrDecode = Base64.decode(str);
        if (bArrDecode != null) {
            return new XBase64(bArrDecode);
        }
        throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, SchemaSymbols.ATTVAL_BASE64BINARY});
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public short getAllowedFacets() {
        return (short) 2079;
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public int getDataLength(Object obj) {
        return ((XBase64) obj).getLength();
    }
}
