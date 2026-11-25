package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class RowValidatorAggregator {
    private static final int CAPACITY = 256;
    private List<RowValidator> validators = new ArrayList();

    public void addValidator(RowValidator rowValidator) {
        if (rowValidator != null) {
            this.validators.add(rowValidator);
        }
    }

    public boolean isValid(String[] strArr) {
        Iterator<RowValidator> it = this.validators.iterator();
        while (it.hasNext()) {
            if (!it.next().isValid(strArr)) {
                return false;
            }
        }
        return true;
    }

    public void validate(String[] strArr) throws CsvValidationException {
        StringBuilder sb = new StringBuilder(256);
        Iterator<RowValidator> it = this.validators.iterator();
        while (it.hasNext()) {
            try {
                it.next().validate(strArr);
            } catch (CsvValidationException e) {
                sb.append(e.getMessage()).append("\n");
            }
        }
        if (sb.length() > 0) {
            throw new CsvValidationException(sb.toString());
        }
    }
}
