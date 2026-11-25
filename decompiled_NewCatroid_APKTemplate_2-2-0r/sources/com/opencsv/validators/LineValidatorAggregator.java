package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class LineValidatorAggregator {
    private static final int CAPACITY = 256;
    private List<LineValidator> validators = new ArrayList();

    public void addValidator(LineValidator lineValidator) {
        if (lineValidator != null) {
            this.validators.add(lineValidator);
        }
    }

    public boolean isValid(String str) {
        Iterator<LineValidator> it = this.validators.iterator();
        while (it.hasNext()) {
            if (!it.next().isValid(str)) {
                return false;
            }
        }
        return true;
    }

    public void validate(String str) throws CsvValidationException {
        StringBuilder sb = new StringBuilder(256);
        Iterator<LineValidator> it = this.validators.iterator();
        while (it.hasNext()) {
            try {
                it.next().validate(str);
            } catch (CsvValidationException e) {
                sb.append(e.getMessage()).append("\n");
            }
        }
        if (sb.length() > 0) {
            throw new CsvValidationException(sb.toString());
        }
    }
}
