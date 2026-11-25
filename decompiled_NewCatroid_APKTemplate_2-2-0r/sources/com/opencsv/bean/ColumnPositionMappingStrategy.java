package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.IntFunction;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: classes10.dex */
public class ColumnPositionMappingStrategy<T> extends AbstractMappingStrategy<String, Integer, ComplexFieldMapEntry<String, Integer, T>, T> {
    private FieldMapByPosition<T> fieldMap;
    private Comparator<Integer> writeOrder;
    private boolean columnsExplicitlySet = false;
    private Integer[] columnIndexForWriting = null;

    @Override // com.opencsv.bean.MappingStrategy
    public void captureHeader(CSVReader cSVReader) throws IOException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("type.unset"));
        }
        this.fieldMap.setMaxIndex(((String[]) ObjectUtils.defaultIfNull(cSVReader.peek(), ArrayUtils.EMPTY_STRING_ARRAY)).length - 1);
        if (this.columnsExplicitlySet) {
            return;
        }
        this.headerIndex.clear();
        for (FieldMapByPositionEntry<T> fieldMapByPositionEntry : this.fieldMap) {
            Field field = fieldMapByPositionEntry.getField().getField();
            if (field.getAnnotation(CsvCustomBindByPosition.class) != null || field.getAnnotation(CsvBindAndSplitByPosition.class) != null || field.getAnnotation(CsvBindAndJoinByPosition.class) != null || field.getAnnotation(CsvBindByPosition.class) != null) {
                this.headerIndex.put(fieldMapByPositionEntry.getPosition(), field.getName().toUpperCase().trim());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.opencsv.bean.AbstractMappingStrategy
    public Integer chooseMultivaluedFieldIndexFromHeaderIndex(int i) {
        return Integer.valueOf(i);
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected BeanField<T, Integer> findField(int i) {
        Integer[] numArr = this.columnIndexForWriting;
        if (numArr != null) {
            if (i < numArr.length) {
                return this.fieldMap.get(numArr[i]);
            }
            return null;
        }
        return this.fieldMap.get(Integer.valueOf(i));
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy, com.opencsv.bean.MappingStrategy
    public String[] generateHeader(T t) throws CsvRequiredFieldEmptyException {
        Integer[] numArr = new Integer[super.generateHeader(t).length];
        this.columnIndexForWriting = numArr;
        Arrays.setAll(numArr, new IntFunction() { // from class: com.opencsv.bean.ColumnPositionMappingStrategy$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return Integer.valueOf(i);
            }
        });
        Arrays.sort(this.columnIndexForWriting, this.writeOrder);
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    public String getColumnName(int i) {
        return this.headerIndex.getByPosition(i);
    }

    public String[] getColumnMapping() {
        return this.headerIndex.getHeaderIndex();
    }

    public void setColumnMapping(String... strArr) {
        if (strArr != null) {
            this.headerIndex.initializeHeaderIndex(strArr);
        } else {
            this.headerIndex.clear();
        }
        this.columnsExplicitlySet = true;
        if (getType() != null) {
            loadFieldMap();
        }
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected void loadAnnotatedFieldMap(ListValuedMap<Class<?>, Field> listValuedMap) {
        for (Map.Entry<Class<?>, Field> entry : listValuedMap.entries()) {
            Class<?> key = entry.getKey();
            Field value = entry.getValue();
            if (value.isAnnotationPresent(CsvCustomBindByPosition.class)) {
                CsvCustomBindByPosition csvCustomBindByPosition = (CsvCustomBindByPosition) value.getAnnotation(CsvCustomBindByPosition.class);
                BeanField<T, Integer> beanFieldInstantiateCustomConverter = instantiateCustomConverter(csvCustomBindByPosition.converter());
                beanFieldInstantiateCustomConverter.setType(key);
                beanFieldInstantiateCustomConverter.setField(value);
                beanFieldInstantiateCustomConverter.setRequired(csvCustomBindByPosition.required());
                this.fieldMap.put(Integer.valueOf(csvCustomBindByPosition.position()), beanFieldInstantiateCustomConverter);
            } else if (value.isAnnotationPresent(CsvBindAndSplitByPosition.class)) {
                CsvBindAndSplitByPosition csvBindAndSplitByPosition = (CsvBindAndSplitByPosition) value.getAnnotation(CsvBindAndSplitByPosition.class);
                boolean zRequired = csvBindAndSplitByPosition.required();
                String strLocale = csvBindAndSplitByPosition.locale();
                String strWriteLocale = csvBindAndSplitByPosition.writeLocaleEqualsReadLocale() ? strLocale : csvBindAndSplitByPosition.writeLocale();
                String strSplitOn = csvBindAndSplitByPosition.splitOn();
                String strWriteDelimiter = csvBindAndSplitByPosition.writeDelimiter();
                Class<? extends Collection> clsCollectionType = csvBindAndSplitByPosition.collectionType();
                Class<?> clsElementType = csvBindAndSplitByPosition.elementType();
                Class<? extends AbstractCsvConverter> clsConverter = csvBindAndSplitByPosition.converter();
                this.fieldMap.put(Integer.valueOf(csvBindAndSplitByPosition.position()), new BeanFieldSplit(key, value, zRequired, this.errorLocale, determineConverter(value, clsElementType, strLocale, strWriteLocale, clsConverter), strSplitOn, strWriteDelimiter, clsCollectionType, csvBindAndSplitByPosition.capture(), csvBindAndSplitByPosition.format()));
            } else if (value.isAnnotationPresent(CsvBindAndJoinByPosition.class)) {
                CsvBindAndJoinByPosition csvBindAndJoinByPosition = (CsvBindAndJoinByPosition) value.getAnnotation(CsvBindAndJoinByPosition.class);
                boolean zRequired2 = csvBindAndJoinByPosition.required();
                String strLocale2 = csvBindAndJoinByPosition.locale();
                String strWriteLocale2 = csvBindAndJoinByPosition.writeLocaleEqualsReadLocale() ? strLocale2 : csvBindAndJoinByPosition.writeLocale();
                Class<?> clsElementType2 = csvBindAndJoinByPosition.elementType();
                Class<? extends MultiValuedMap> clsMapType = csvBindAndJoinByPosition.mapType();
                Class<? extends AbstractCsvConverter> clsConverter2 = csvBindAndJoinByPosition.converter();
                this.fieldMap.putComplex(csvBindAndJoinByPosition.position(), (BeanField) new BeanFieldJoinIntegerIndex(key, value, zRequired2, this.errorLocale, determineConverter(value, clsElementType2, strLocale2, strWriteLocale2, clsConverter2), clsMapType, csvBindAndJoinByPosition.capture(), csvBindAndJoinByPosition.format()));
            } else {
                CsvBindByPosition csvBindByPosition = (CsvBindByPosition) value.getAnnotation(CsvBindByPosition.class);
                boolean zRequired3 = csvBindByPosition.required();
                String strLocale3 = csvBindByPosition.locale();
                String strWriteLocale3 = csvBindByPosition.writeLocaleEqualsReadLocale() ? strLocale3 : csvBindByPosition.writeLocale();
                this.fieldMap.put(Integer.valueOf(csvBindByPosition.position()), new BeanFieldSingleValue(key, value, zRequired3, this.errorLocale, determineConverter(value, value.getType(), strLocale3, strWriteLocale3, null), csvBindByPosition.capture(), csvBindByPosition.format()));
            }
        }
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> listValuedMap) {
        for (Map.Entry<Class<?>, Field> entry : listValuedMap.entries()) {
            Class<?> key = entry.getKey();
            Field value = entry.getValue();
            CsvConverter csvConverterDetermineConverter = determineConverter(value, value.getType(), null, null, null);
            int[] byName = this.headerIndex.getByName(value.getName());
            if (byName.length != 0) {
                this.fieldMap.put(Integer.valueOf(byName[0]), new BeanFieldSingleValue(key, value, false, this.errorLocale, csvConverterDetermineConverter, null, null));
            }
        }
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected Set<Class<? extends Annotation>> getBindingAnnotations() {
        return new HashSet(Arrays.asList(CsvBindByPosition.class, CsvCustomBindByPosition.class, CsvBindAndJoinByPosition.class, CsvBindAndSplitByPosition.class));
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected void initializeFieldMap() {
        FieldMapByPosition<T> fieldMapByPosition = new FieldMapByPosition<>(this.errorLocale);
        this.fieldMap = fieldMapByPosition;
        fieldMapByPosition.setColumnOrderOnWrite(this.writeOrder);
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected void verifyLineLength(int i) throws CsvRequiredFieldEmptyException {
        if (this.headerIndex.isEmpty()) {
            return;
        }
        StringBuilder sb = null;
        while (i <= this.headerIndex.findMaxIndex()) {
            BeanField<T, Integer> beanFieldFindField = findField(i);
            if (beanFieldFindField != null && beanFieldFindField.isRequired()) {
                if (sb == null) {
                    sb = new StringBuilder(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("multiple.required.field.empty"));
                }
                sb.append(StringUtil.SPACE);
                sb.append(beanFieldFindField.getField().getName());
            }
            i++;
        }
        if (sb != null) {
            throw new CsvRequiredFieldEmptyException(this.type, sb.toString());
        }
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    public String findHeader(int i) {
        return Integer.toString(i);
    }

    @Override // com.opencsv.bean.AbstractMappingStrategy
    protected FieldMap<String, Integer, ? extends ComplexFieldMapEntry<String, Integer, T>, T> getFieldMap() {
        return this.fieldMap;
    }

    public void setColumnOrderOnWrite(Comparator<Integer> comparator) {
        this.writeOrder = comparator;
        FieldMapByPosition<T> fieldMapByPosition = this.fieldMap;
        if (fieldMapByPosition != null) {
            fieldMapByPosition.setColumnOrderOnWrite(comparator);
        }
    }
}
