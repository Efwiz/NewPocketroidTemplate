package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

/* JADX INFO: loaded from: classes13.dex */
public class IntegerDV extends DecimalDV {
    @Override // org.apache.xerces.impl.dv.xs.DecimalDV, org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            return new DecimalDV.XDecimal(str, true);
        } catch (NumberFormatException unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, "integer"});
        }
    }
}
