package org.apache.xerces.impl.dv.xs;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.datatype.Duration;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

/* JADX INFO: loaded from: classes13.dex */
class DayTimeDurationDV extends DurationDV {
    DayTimeDurationDV() {
    }

    @Override // org.apache.xerces.impl.dv.xs.DurationDV, org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            return parse(str, 2);
        } catch (Exception unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, "dayTimeDuration"});
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.DurationDV, org.apache.xerces.impl.dv.xs.AbstractDateTimeDV
    protected Duration getDuration(AbstractDateTimeDV.DateTimeData dateTimeData) {
        int i = (dateTimeData.day < 0 || dateTimeData.hour < 0 || dateTimeData.minute < 0 || dateTimeData.second < 0.0d) ? -1 : 1;
        return datatypeFactory.newDuration(i == 1, (BigInteger) null, (BigInteger) null, dateTimeData.day != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.day * i) : null, dateTimeData.hour != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.hour * i) : null, dateTimeData.minute != Integer.MIN_VALUE ? BigInteger.valueOf(dateTimeData.minute * i) : null, dateTimeData.second != -2.147483648E9d ? new BigDecimal(String.valueOf(((double) i) * dateTimeData.second)) : null);
    }
}
