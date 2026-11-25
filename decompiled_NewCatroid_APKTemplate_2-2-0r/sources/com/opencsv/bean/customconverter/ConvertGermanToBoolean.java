package com.opencsv.bean.customconverter;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.ResourceBundle;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes10.dex */
public class ConvertGermanToBoolean<T, I> extends AbstractBeanField<T, I> {
    protected static final String FALSCH = "falsch";
    protected static final String WAHR = "wahr";

    @Override // com.opencsv.bean.AbstractBeanField
    protected Object convert(String str) throws CsvDataTypeMismatchException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new BooleanConverter(new String[]{WAHR, LanguageCodeUtil.JA, "j", SchemaSymbols.ATTVAL_TRUE_1, "w"}, new String[]{FALSCH, "nein", "n", "0", "f"}).convert(Boolean.class, str.trim());
        } catch (ConversionException e) {
            CsvDataTypeMismatchException csvDataTypeMismatchException = new CsvDataTypeMismatchException(str, this.field.getType(), ResourceBundle.getBundle("convertGermanToBoolean", this.errorLocale).getString("input.not.boolean"));
            csvDataTypeMismatchException.initCause(e);
            throw csvDataTypeMismatchException;
        }
    }

    @Override // com.opencsv.bean.AbstractBeanField
    protected String convertToWrite(Object obj) throws CsvDataTypeMismatchException {
        if (obj == null) {
            return "";
        }
        try {
            return ((Boolean) obj).booleanValue() ? WAHR : FALSCH;
        } catch (ClassCastException e) {
            CsvDataTypeMismatchException csvDataTypeMismatchException = new CsvDataTypeMismatchException(ResourceBundle.getBundle("convertGermanToBoolean", this.errorLocale).getString("field.not.boolean"));
            csvDataTypeMismatchException.initCause(e);
            throw csvDataTypeMismatchException;
        }
    }
}
