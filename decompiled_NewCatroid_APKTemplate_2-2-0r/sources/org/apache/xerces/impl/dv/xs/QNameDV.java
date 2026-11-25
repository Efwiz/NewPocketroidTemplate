package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.datatypes.XSQName;

/* JADX INFO: loaded from: classes13.dex */
public class QNameDV extends TypeValidator {
    private static final String EMPTY_STRING = "".intern();

    private static final class XQName extends QName implements XSQName {
        public XQName(String str, String str2, String str3, String str4) {
            setValues(str, str2, str3, str4);
        }

        @Override // org.apache.xerces.xni.QName
        public boolean equals(Object obj) {
            if (!(obj instanceof QName)) {
                return false;
            }
            QName qName = (QName) obj;
            return this.uri == qName.uri && this.localpart == qName.localpart;
        }

        @Override // org.apache.xerces.xs.datatypes.XSQName
        public javax.xml.namespace.QName getJAXPQName() {
            return new javax.xml.namespace.QName(this.uri, this.localpart, this.prefix);
        }

        @Override // org.apache.xerces.xs.datatypes.XSQName
        public QName getXNIQName() {
            return this;
        }

        @Override // org.apache.xerces.xni.QName
        public String toString() {
            return this.rawname;
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        String symbol;
        String strSubstring;
        int iIndexOf = str.indexOf(":");
        if (iIndexOf > 0) {
            symbol = validationContext.getSymbol(str.substring(0, iIndexOf));
            strSubstring = str.substring(iIndexOf + 1);
        } else {
            symbol = EMPTY_STRING;
            strSubstring = str;
        }
        if (symbol.length() > 0 && !XMLChar.isValidNCName(symbol)) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, SchemaSymbols.ATTVAL_QNAME});
        }
        if (!XMLChar.isValidNCName(strSubstring)) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, SchemaSymbols.ATTVAL_QNAME});
        }
        String uri = validationContext.getURI(symbol);
        if (symbol.length() <= 0 || uri != null) {
            return new XQName(symbol, validationContext.getSymbol(strSubstring), validationContext.getSymbol(str), uri);
        }
        throw new InvalidDatatypeValueException("UndeclaredPrefix", new Object[]{str, symbol});
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public short getAllowedFacets() {
        return (short) 2079;
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public int getDataLength(Object obj) {
        return ((XQName) obj).rawname.length();
    }
}
