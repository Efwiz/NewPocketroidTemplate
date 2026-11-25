package com.opencsv.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class HeaderColumnNameMappingStrategy<T> extends HeaderNameBaseMappingStrategy<T> {
    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected void loadAnnotatedFieldMap(ListValuedMap<Class<?>, Field> listValuedMap) {
        for (Map.Entry<Class<?>, Field> entry : listValuedMap.entries()) {
            Class<?> key = entry.getKey();
            Field value = entry.getValue();
            if (value.isAnnotationPresent(CsvCustomBindByName.class)) {
                CsvCustomBindByName csvCustomBindByName = (CsvCustomBindByName) value.getAnnotation(CsvCustomBindByName.class);
                String strTrim = csvCustomBindByName.column().toUpperCase().trim();
                if (StringUtils.isEmpty(strTrim)) {
                    strTrim = value.getName().toUpperCase();
                }
                BeanField<T, String> beanFieldInstantiateCustomConverter = instantiateCustomConverter(((CsvCustomBindByName) value.getAnnotation(CsvCustomBindByName.class)).converter());
                beanFieldInstantiateCustomConverter.setType(key);
                beanFieldInstantiateCustomConverter.setField(value);
                beanFieldInstantiateCustomConverter.setRequired(csvCustomBindByName.required());
                this.fieldMap.put(strTrim, beanFieldInstantiateCustomConverter);
            } else if (value.isAnnotationPresent(CsvBindAndSplitByName.class)) {
                CsvBindAndSplitByName csvBindAndSplitByName = (CsvBindAndSplitByName) value.getAnnotation(CsvBindAndSplitByName.class);
                boolean zRequired = csvBindAndSplitByName.required();
                String strTrim2 = csvBindAndSplitByName.column().toUpperCase().trim();
                String strLocale = csvBindAndSplitByName.locale();
                String strWriteLocale = csvBindAndSplitByName.writeLocaleEqualsReadLocale() ? strLocale : csvBindAndSplitByName.writeLocale();
                String strSplitOn = csvBindAndSplitByName.splitOn();
                String strWriteDelimiter = csvBindAndSplitByName.writeDelimiter();
                Class<? extends Collection> clsCollectionType = csvBindAndSplitByName.collectionType();
                Class<?> clsElementType = csvBindAndSplitByName.elementType();
                Class<? extends AbstractCsvConverter> clsConverter = csvBindAndSplitByName.converter();
                String strCapture = csvBindAndSplitByName.capture();
                String str = csvBindAndSplitByName.format();
                CsvConverter csvConverterDetermineConverter = determineConverter(value, clsElementType, strLocale, strWriteLocale, clsConverter);
                if (StringUtils.isEmpty(strTrim2)) {
                    this.fieldMap.put(value.getName().toUpperCase(), new BeanFieldSplit(key, value, zRequired, this.errorLocale, csvConverterDetermineConverter, strSplitOn, strWriteDelimiter, clsCollectionType, strCapture, str));
                } else {
                    this.fieldMap.put(strTrim2, new BeanFieldSplit(key, value, zRequired, this.errorLocale, csvConverterDetermineConverter, strSplitOn, strWriteDelimiter, clsCollectionType, strCapture, str));
                }
            } else if (value.isAnnotationPresent(CsvBindAndJoinByName.class)) {
                CsvBindAndJoinByName csvBindAndJoinByName = (CsvBindAndJoinByName) value.getAnnotation(CsvBindAndJoinByName.class);
                boolean zRequired2 = csvBindAndJoinByName.required();
                String strColumn = csvBindAndJoinByName.column();
                String strLocale2 = csvBindAndJoinByName.locale();
                String strWriteLocale2 = csvBindAndJoinByName.writeLocaleEqualsReadLocale() ? strLocale2 : csvBindAndJoinByName.writeLocale();
                Class<?> clsElementType2 = csvBindAndJoinByName.elementType();
                Class<? extends MultiValuedMap> clsMapType = csvBindAndJoinByName.mapType();
                Class<? extends AbstractCsvConverter> clsConverter2 = csvBindAndJoinByName.converter();
                String strCapture2 = csvBindAndJoinByName.capture();
                String str2 = csvBindAndJoinByName.format();
                CsvConverter csvConverterDetermineConverter2 = determineConverter(value, clsElementType2, strLocale2, strWriteLocale2, clsConverter2);
                if (StringUtils.isEmpty(strColumn)) {
                    this.fieldMap.putComplex(value.getName(), (BeanField) new BeanFieldJoinStringIndex(key, value, zRequired2, this.errorLocale, csvConverterDetermineConverter2, clsMapType, strCapture2, str2));
                } else {
                    this.fieldMap.putComplex(strColumn, (BeanField) new BeanFieldJoinStringIndex(key, value, zRequired2, this.errorLocale, csvConverterDetermineConverter2, clsMapType, strCapture2, str2));
                }
            } else {
                CsvBindByName csvBindByName = (CsvBindByName) value.getAnnotation(CsvBindByName.class);
                boolean zRequired3 = csvBindByName.required();
                String strTrim3 = csvBindByName.column().toUpperCase().trim();
                String strLocale3 = csvBindByName.locale();
                String strWriteLocale3 = csvBindByName.writeLocaleEqualsReadLocale() ? strLocale3 : csvBindByName.writeLocale();
                String strCapture3 = csvBindByName.capture();
                String str3 = csvBindByName.format();
                CsvConverter csvConverterDetermineConverter3 = determineConverter(value, value.getType(), strLocale3, strWriteLocale3, null);
                if (StringUtils.isEmpty(strTrim3)) {
                    this.fieldMap.put(value.getName().toUpperCase(), new BeanFieldSingleValue(key, value, zRequired3, this.errorLocale, csvConverterDetermineConverter3, strCapture3, str3));
                } else {
                    this.fieldMap.put(strTrim3, new BeanFieldSingleValue(key, value, zRequired3, this.errorLocale, csvConverterDetermineConverter3, strCapture3, str3));
                }
            }
        }
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected Set<Class<? extends Annotation>> getBindingAnnotations() {
        return new HashSet(Arrays.asList(CsvBindByName.class, CsvCustomBindByName.class, CsvBindAndSplitByName.class, CsvBindAndJoinByName.class));
    }
}
