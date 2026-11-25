package com.opencsv.bean;

import com.opencsv.ICSVParser;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRecursionException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.lang.Comparable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

/* JADX INFO: loaded from: classes10.dex */
public abstract class AbstractMappingStrategy<I, K extends Comparable<K>, C extends ComplexFieldMapEntry<I, K, T>, T> implements MappingStrategy<T> {
    private static final Set<Class> FORBIDDEN_CLASSES_FOR_RECURSION = new HashSet(Arrays.asList(Byte.TYPE, Short.TYPE, Integer.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Long.TYPE, Character.TYPE));
    private RecursiveType recursiveTypeTree;
    protected Class<? extends T> type;
    protected final HeaderIndex headerIndex = new HeaderIndex();
    private MultiValuedMap<Class<?>, Field> ignoredFields = new ArrayListValuedHashMap();
    protected Locale errorLocale = Locale.getDefault();

    protected abstract K chooseMultivaluedFieldIndexFromHeaderIndex(int i);

    protected abstract BeanField<T, K> findField(int i);

    public abstract String findHeader(int i);

    protected abstract FieldMap<I, K, ? extends C, T> getFieldMap();

    protected abstract void initializeFieldMap();

    protected void loadAnnotatedFieldMap(ListValuedMap<Class<?>, Field> listValuedMap) {
    }

    protected abstract void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> listValuedMap);

    protected abstract void verifyLineLength(int i) throws CsvRequiredFieldEmptyException;

    protected Set<Class<? extends Annotation>> getBindingAnnotations() {
        return Collections.emptySet();
    }

    protected Map<Class<?>, Object> createBean() throws CsvBeanIntrospectionException, IllegalStateException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("type.unset"));
        }
        HashMap map = new HashMap();
        try {
            T tNewInstance = this.type.newInstance();
            map.put(this.type, tNewInstance);
            createSubordinateBeans(this.recursiveTypeTree, map, tNewInstance);
            return map;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            CsvBeanIntrospectionException csvBeanIntrospectionException = new CsvBeanIntrospectionException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("bean.instantiation.impossible"));
            csvBeanIntrospectionException.initCause(e);
            throw csvBeanIntrospectionException;
        }
    }

    private static void createSubordinateBeans(RecursiveType recursiveType, Map<Class<?>, Object> map, Object obj) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Map.Entry<FieldAccess<Object>, RecursiveType> entry : recursiveType.getRecursiveMembers().entrySet()) {
            Object field = entry.getKey().getField(obj);
            if (field == null) {
                field = entry.getValue().type.newInstance();
                entry.getKey().setField(obj, field);
            }
            map.put(entry.getValue().getType(), field);
            createSubordinateBeans(entry.getValue(), map, field);
        }
    }

    protected Map<Class<?>, Object> indexBean(T t) throws IllegalAccessException, InvocationTargetException {
        HashMap map = new HashMap();
        map.put(this.type, t);
        indexSubordinateBeans(this.recursiveTypeTree, map, t);
        return map;
    }

    private static void indexSubordinateBeans(RecursiveType recursiveType, Map<Class<?>, Object> map, Object obj) throws IllegalAccessException, InvocationTargetException {
        for (Map.Entry<FieldAccess<Object>, RecursiveType> entry : recursiveType.getRecursiveMembers().entrySet()) {
            Object field = obj == null ? null : entry.getKey().getField(obj);
            map.put(entry.getValue().getType(), field);
            indexSubordinateBeans(entry.getValue(), map, field);
        }
    }

    @Override // com.opencsv.bean.MappingStrategy
    public String[] generateHeader(T t) throws CsvRequiredFieldEmptyException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("type.before.header"));
        }
        if (this.headerIndex.isEmpty()) {
            String[] strArrGenerateHeader = getFieldMap().generateHeader(t);
            this.headerIndex.initializeHeaderIndex(strArrGenerateHeader);
            return strArrGenerateHeader;
        }
        return this.headerIndex.getHeaderIndex();
    }

    String getColumnName(int i) {
        return this.headerIndex.getByPosition(i);
    }

    public Class<? extends T> getType() {
        return this.type;
    }

    @Override // com.opencsv.bean.MappingStrategy
    public T populateNewBean(String[] strArr) throws CsvBeanIntrospectionException, CsvValidationException, CsvRequiredFieldEmptyException, CsvConstraintViolationException, CsvDataTypeMismatchException {
        verifyLineLength(strArr.length);
        Map<Class<?>, Object> mapCreateBean = createBean();
        for (int i = 0; i < strArr.length; i++) {
            setFieldValue(mapCreateBean, strArr[i], i);
        }
        return (T) mapCreateBean.get(this.type);
    }

    @Override // com.opencsv.bean.MappingStrategy
    public void setType(Class<? extends T> cls) throws CsvBadConverterException {
        this.type = cls;
        loadFieldMap();
    }

    @Override // com.opencsv.bean.MappingStrategy
    public void ignoreFields(MultiValuedMap<Class<?>, Field> multiValuedMap) throws IllegalArgumentException {
        if (multiValuedMap == null) {
            this.ignoredFields = new ArrayListValuedHashMap();
        } else {
            this.ignoredFields = multiValuedMap;
            final MapIterator<Class<?>, Field> mapIterator = multiValuedMap.mapIterator();
            mapIterator.forEachRemaining(new Consumer() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2734lambda$ignoreFields$0$comopencsvbeanAbstractMappingStrategy(mapIterator, (Class) obj);
                }
            });
        }
        if (this.type != null) {
            loadFieldMap();
        }
    }

    /* JADX INFO: renamed from: lambda$ignoreFields$0$com-opencsv-bean-AbstractMappingStrategy, reason: not valid java name */
    /* synthetic */ void m2734lambda$ignoreFields$0$comopencsvbeanAbstractMappingStrategy(MapIterator mapIterator, Class cls) {
        Field field = (Field) mapIterator.getValue();
        if (cls == null || field == null || !field.getDeclaringClass().isAssignableFrom(cls)) {
            throw new IllegalArgumentException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("ignore.field.inconsistent"));
        }
    }

    private List<Field> filterIgnoredFields(final Class<?> cls, Field[] fieldArr) {
        return (List) Stream.of((Object[]) fieldArr).filter(new Predicate() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2733xac3e5b60(cls, (Field) obj);
            }
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: lambda$filterIgnoredFields$1$com-opencsv-bean-AbstractMappingStrategy, reason: not valid java name */
    /* synthetic */ boolean m2733xac3e5b60(Class cls, Field field) {
        return (this.ignoredFields.containsMapping(cls, field) || field.isAnnotationPresent(CsvIgnore.class)) ? false : true;
    }

    protected void loadFieldMap() throws CsvBadConverterException {
        initializeFieldMap();
        this.recursiveTypeTree = loadRecursiveClasses(this.type, new HashSet());
        Map<Boolean, ListValuedMap<Class<?>, Field>> mapPartitionFields = partitionFields();
        if (!mapPartitionFields.get(Boolean.TRUE).isEmpty()) {
            loadAnnotatedFieldMap(mapPartitionFields.get(Boolean.TRUE));
        } else {
            loadUnadornedFieldMap(mapPartitionFields.get(Boolean.FALSE));
        }
    }

    protected RecursiveType loadRecursiveClasses(Class<?> cls, Set<Class<?>> set) {
        if (FORBIDDEN_CLASSES_FOR_RECURSION.contains(cls)) {
            throw new CsvRecursionException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("recursion.on.primitive"), cls);
        }
        if (set.contains(cls)) {
            throw new CsvRecursionException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("recursive.type.encountered.twice"), cls.toString()), cls);
        }
        set.add(cls);
        RecursiveType recursiveType = new RecursiveType(cls);
        for (final Field field : filterIgnoredFields(cls, FieldUtils.getFieldsWithAnnotation(cls, CsvRecurse.class))) {
            Stream<Class<? extends Annotation>> stream = getBindingAnnotations().stream();
            field.getClass();
            if (stream.anyMatch(new Predicate() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return field.isAnnotationPresent((Class) obj);
                }
            })) {
                throw new CsvRecursionException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("recursion.binding.mutually.exclusive"), field.getType());
            }
            recursiveType.addRecursiveMember(new FieldAccess<>(field), loadRecursiveClasses(field.getType(), set));
        }
        return recursiveType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: assembleCompleteFieldList, reason: merged with bridge method [inline-methods] */
    public void m2732x67691045(RecursiveType recursiveType, final ListValuedMap<Class<?>, Field> listValuedMap) {
        listValuedMap.putAll(recursiveType.type, filterIgnoredFields(recursiveType.type, FieldUtils.getAllFields(recursiveType.type)));
        recursiveType.getRecursiveMembers().values().forEach(new Consumer() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m2732x67691045(listValuedMap, (AbstractMappingStrategy.RecursiveType) obj);
            }
        });
    }

    protected Map<Boolean, ListValuedMap<Class<?>, Field>> partitionFields() {
        ArrayListValuedHashMap arrayListValuedHashMap = new ArrayListValuedHashMap();
        m2732x67691045(this.recursiveTypeTree, arrayListValuedHashMap);
        final Set<Class<? extends Annotation>> bindingAnnotations = getBindingAnnotations();
        final TreeMap treeMap = new TreeMap();
        treeMap.put(Boolean.TRUE, new ArrayListValuedHashMap());
        treeMap.put(Boolean.FALSE, new ArrayListValuedHashMap());
        arrayListValuedHashMap.entries().stream().filter(new Predicate() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractMappingStrategy.lambda$partitionFields$3((Map.Entry) obj);
            }
        }).forEach(new Consumer() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractMappingStrategy.lambda$partitionFields$5(bindingAnnotations, treeMap, (Map.Entry) obj);
            }
        });
        return treeMap;
    }

    static /* synthetic */ boolean lambda$partitionFields$3(Map.Entry entry) {
        return !((Field) entry.getValue()).isSynthetic();
    }

    static /* synthetic */ void lambda$partitionFields$5(Set set, Map map, final Map.Entry entry) {
        if (set.stream().anyMatch(new Predicate() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Field) entry.getValue()).isAnnotationPresent((Class) obj);
            }
        })) {
            ((ListValuedMap) map.get(Boolean.TRUE)).put(entry.getKey(), entry.getValue());
        } else {
            ((ListValuedMap) map.get(Boolean.FALSE)).put(entry.getKey(), entry.getValue());
        }
    }

    protected BeanField<T, K> instantiateCustomConverter(Class<? extends AbstractBeanField<T, K>> cls) throws CsvBadConverterException {
        try {
            AbstractBeanField<T, K> abstractBeanFieldNewInstance = cls.newInstance();
            abstractBeanFieldNewInstance.setErrorLocale(this.errorLocale);
            return abstractBeanFieldNewInstance;
        } catch (IllegalAccessException | InstantiationException e) {
            CsvBadConverterException csvBadConverterException = new CsvBadConverterException(cls, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("custom.converter.invalid"), cls.getCanonicalName()));
            csvBadConverterException.initCause(e);
            throw csvBadConverterException;
        }
    }

    @Override // com.opencsv.bean.MappingStrategy
    public void setErrorLocale(Locale locale) {
        this.errorLocale = (Locale) ObjectUtils.defaultIfNull(locale, Locale.getDefault());
        if (getFieldMap() != null) {
            getFieldMap().setErrorLocale(this.errorLocale);
            getFieldMap().values().forEach(new Consumer() { // from class: com.opencsv.bean.AbstractMappingStrategy$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2735lambda$setErrorLocale$6$comopencsvbeanAbstractMappingStrategy((BeanField) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$setErrorLocale$6$com-opencsv-bean-AbstractMappingStrategy, reason: not valid java name */
    /* synthetic */ void m2735lambda$setErrorLocale$6$comopencsvbeanAbstractMappingStrategy(BeanField beanField) {
        beanField.setErrorLocale(this.errorLocale);
    }

    protected void setFieldValue(Map<Class<?>, Object> map, String str, int i) throws CsvValidationException, CsvRequiredFieldEmptyException, CsvConstraintViolationException, CsvDataTypeMismatchException {
        BeanField<T, K> beanFieldFindField = findField(i);
        if (beanFieldFindField != null) {
            beanFieldFindField.setFieldValue(map.get(beanFieldFindField.getType()), str, findHeader(i));
        }
    }

    @Override // com.opencsv.bean.MappingStrategy
    public String[] transmuteBean(T t) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        int iFindMaxIndex = this.headerIndex.findMaxIndex() + 1;
        ArrayList arrayList = new ArrayList(Math.max(iFindMaxIndex, 0));
        try {
            Map<Class<?>, Object> mapIndexBean = indexBean(t);
            int i = 0;
            while (i < iFindMaxIndex) {
                BeanField<T, K> beanFieldFindField = findField(i);
                Comparable comparableChooseMultivaluedFieldIndexFromHeaderIndex = chooseMultivaluedFieldIndexFromHeaderIndex(i);
                String[] strArrWrite = beanFieldFindField != null ? beanFieldFindField.write(mapIndexBean.get(beanFieldFindField.getType()), comparableChooseMultivaluedFieldIndexFromHeaderIndex) : ArrayUtils.EMPTY_STRING_ARRAY;
                if (strArrWrite.length == 0) {
                    arrayList.add("");
                    i++;
                } else {
                    arrayList.add(StringUtils.defaultString(strArrWrite[0]));
                    int i2 = i + 1;
                    BeanField<T, K> beanFieldFindField2 = findField(i2);
                    Comparable comparableChooseMultivaluedFieldIndexFromHeaderIndex2 = chooseMultivaluedFieldIndexFromHeaderIndex(i2);
                    int i3 = 1;
                    while (i3 < strArrWrite.length && i2 < iFindMaxIndex && Objects.equals(beanFieldFindField, beanFieldFindField2) && Objects.equals(comparableChooseMultivaluedFieldIndexFromHeaderIndex, comparableChooseMultivaluedFieldIndexFromHeaderIndex2)) {
                        arrayList.add(StringUtils.defaultString(strArrWrite[i3]));
                        i3++;
                        i2 = i + i3;
                        beanFieldFindField2 = findField(i2);
                        comparableChooseMultivaluedFieldIndexFromHeaderIndex2 = chooseMultivaluedFieldIndexFromHeaderIndex(i2);
                    }
                    if (i2 < iFindMaxIndex) {
                        BeanField<T, K> beanFieldFindField3 = findField(i2);
                        Comparable comparableChooseMultivaluedFieldIndexFromHeaderIndex3 = chooseMultivaluedFieldIndexFromHeaderIndex(i2);
                        while (Objects.equals(beanFieldFindField, beanFieldFindField3) && Objects.equals(comparableChooseMultivaluedFieldIndexFromHeaderIndex, comparableChooseMultivaluedFieldIndexFromHeaderIndex3) && i2 < iFindMaxIndex) {
                            arrayList.add("");
                            i2++;
                            beanFieldFindField3 = findField(i2);
                            comparableChooseMultivaluedFieldIndexFromHeaderIndex3 = chooseMultivaluedFieldIndexFromHeaderIndex(i2);
                        }
                    }
                    i = i2;
                }
            }
            return (String[]) arrayList.toArray(new String[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            CsvBeanIntrospectionException csvBeanIntrospectionException = new CsvBeanIntrospectionException(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("error.introspecting.beans"));
            csvBeanIntrospectionException.initCause(e);
            throw csvBeanIntrospectionException;
        }
    }

    protected CsvConverter determineConverter(Field field, Class<?> cls, String str, String str2, Class<? extends AbstractCsvConverter> cls2) throws CsvBadConverterException {
        if (cls2 != null && !cls2.equals(AbstractCsvConverter.class)) {
            try {
                AbstractCsvConverter abstractCsvConverterNewInstance = cls2.newInstance();
                abstractCsvConverterNewInstance.setType(cls);
                abstractCsvConverterNewInstance.setLocale(str);
                abstractCsvConverterNewInstance.setWriteLocale(str2);
                abstractCsvConverterNewInstance.setErrorLocale(this.errorLocale);
                return abstractCsvConverterNewInstance;
            } catch (IllegalAccessException | InstantiationException e) {
                CsvBadConverterException csvBadConverterException = new CsvBadConverterException(cls2, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("custom.converter.invalid"), cls2.getCanonicalName()));
                csvBadConverterException.initCause(e);
                throw csvBadConverterException;
            }
        }
        if (field.isAnnotationPresent(CsvDate.class)) {
            CsvDate csvDate = (CsvDate) field.getAnnotation(CsvDate.class);
            String strValue = csvDate.value();
            String strWriteFormat = csvDate.writeFormatEqualsReadFormat() ? strValue : csvDate.writeFormat();
            String strChronology = csvDate.chronology();
            return new ConverterDate(cls, str, str2, this.errorLocale, strValue, strWriteFormat, strChronology, csvDate.writeChronologyEqualsReadChronology() ? strChronology : csvDate.writeChronology());
        }
        if (field.isAnnotationPresent(CsvNumber.class)) {
            CsvNumber csvNumber = (CsvNumber) field.getAnnotation(CsvNumber.class);
            String strValue2 = csvNumber.value();
            return new ConverterNumber(cls, str, str2, this.errorLocale, strValue2, csvNumber.writeFormatEqualsReadFormat() ? strValue2 : csvNumber.writeFormat());
        }
        return new ConverterPrimitiveTypes(cls, str, str2, this.errorLocale);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RecursiveType {
        private final Map<FieldAccess<Object>, RecursiveType> recursiveMembers = new HashMap();
        private final Class<?> type;

        RecursiveType(Class<?> cls) {
            this.type = cls;
        }

        public Class<?> getType() {
            return this.type;
        }

        public RecursiveType addRecursiveMember(FieldAccess<Object> fieldAccess, RecursiveType recursiveType) {
            return this.recursiveMembers.put(fieldAccess, recursiveType);
        }

        public Map<FieldAccess<Object>, RecursiveType> getRecursiveMembers() {
            return this.recursiveMembers;
        }
    }
}
