package org.apache.xerces.impl.dv.xs;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.datatype.Duration;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

/* JADX INFO: loaded from: classes13.dex */
class YearMonthDurationDV extends DurationDV {
    YearMonthDurationDV() {
    }

    @Override // org.apache.xerces.impl.dv.xs.DurationDV, org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            return parse(str, 1);
        } catch (Exception unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, "yearMonthDuration"});
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.DurationDV, org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected Duration getDuration(AbstractDateTimeDV.DateTimeData dateTimeData) {
        int i = (dateTimeData.year < 0 || dateTimeData.month < 0) ? -1 : 1;
        return datatypeFactory.newDuration(i == 1, dateTimeData.year != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.year * i) : null, dateTimeData.month != Integer.MIN_VALUE ? BigInteger.valueOf(i * dateTimeData.month) : null, (BigInteger) null, (BigInteger) null, (BigInteger) null, (BigDecimal) null);
    }
}
