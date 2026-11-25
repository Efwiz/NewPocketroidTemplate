package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes10.dex */
public abstract class HeaderNameBaseMappingStrategy<T> extends AbstractMappingStrategy<String, String, ComplexFieldMapEntry<String, String, T>, T> {
    protected FieldMapByName<T> fieldMap = null;
    protected Comparator<String> writeOrder = null;

    @Override // com.opencsv.bean.MappingStrategy
    public void captureHeader(CSVReader cSVReader) throws CsvRequiredFieldEmptyException, IOException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("type.unset"));
        }
        String[] strArr = (String[]) ObjectUtils.defaultIfNull(cSVReader.readNextSilently(), ArrayUtils.EMPTY_STRING_ARRAY);
        this.headerIndex.initializeHeaderIndex(strArr);
        List<FieldMapByNameEntry<T>> listDetermineMissingRequiredHeaders = this.fieldMap.determineMissingRequiredHeaders(strArr);
        if (listDetermineMissingRequiredHeaders.isEmpty()) {
            return;
        }
        String[] strArr2 = new String[listDetermineMissingRequiredHeaders.size()];
        ArrayList arrayList = new ArrayList(listDetermineMissingRequiredHeaders.size());
        for (int i = 0; i < listDetermineMissingRequiredHeaders.size(); i++) {
            FieldMapByNameEntry<T> fieldMapByNameEntry = listDetermineMissingRequiredHeaders.get(i);
            if (fieldMapByNameEntry.isRegexPattern()) {
                strArr2[i] = String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("matching"), fieldMapByNameEntry.getName());
            } else {
                strArr2[i] = fieldMapByNameEntry.getName();
            }
            arrayList.add(fieldMapByNameEntry.getField().getField());
        }
        CsvRequiredFieldEmptyException csvRequiredFieldEmptyException = new CsvRequiredFieldEmptyException(this.type, arrayList, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("header.required.field.absent"), String.join(", ", strArr2), String.join(",", strArr)));
        csvRequiredFieldEmptyException.setLine(strArr);
        throw csvRequiredFieldEmptyException;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.opencsv.bean.AbstractMappingStrategy
    public String chooseMultivaluedFieldIndexFromHeaderIndex(int i) {
        String[] headerIndex = this.headerIndex.getHeaderIndex();
        if (i >= headerIndex.length) {
            return null;
        }
        return headerIndex[i];
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    public void verifyLineLength(int i) throws CsvRequiredFieldEmptyException {
        if (!this.headerIndex.isEmpty() && i != this.headerIndex.getHeaderIndexLength()) {
            throw new CsvRequiredFieldEmptyException(this.type, ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("header.data.mismatch"));
        }
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected BeanField<T, String> findField(int i) throws CsvBadConverterException {
        String columnName = getColumnName(i);
        if (columnName == null) {
            return null;
        }
        String strTrim = columnName.trim();
        if (strTrim.isEmpty()) {
            return null;
        }
        return this.fieldMap.get(strTrim.toUpperCase());
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> listValuedMap) {
        for (Map.Entry<Class<?>, Field> entry : listValuedMap.entries()) {
            if (!Serializable.class.isAssignableFrom(entry.getKey()) || !CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME.equals(entry.getValue().getName())) {
                this.fieldMap.put(entry.getValue().getName().toUpperCase(), new BeanFieldSingleValue(entry.getKey(), entry.getValue(), false, this.errorLocale, determineConverter(entry.getValue(), entry.getValue().getType(), null, null, null), null, null));
            }
        }
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected void initializeFieldMap() {
        FieldMapByName<T> fieldMapByName = new FieldMapByName<>(this.errorLocale);
        this.fieldMap = fieldMapByName;
        fieldMapByName.setColumnOrderOnWrite(this.writeOrder);
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected FieldMap<String, String, ? extends ComplexFieldMapEntry<String, String, T>, T> getFieldMap() {
        return this.fieldMap;
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    public String findHeader(int i) {
        return this.headerIndex.getByPosition(i);
    }

    public void setColumnOrderOnWrite(Comparator<String> comparator) {
        this.writeOrder = comparator;
        FieldMapByName<T> fieldMapByName = this.fieldMap;
        if (fieldMapByName != null) {
            fieldMapByName.setColumnOrderOnWrite(comparator);
        }
    }
}
