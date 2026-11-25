package com.opencsv.bean;

import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.SortedBag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bag.TreeBag;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes10.dex */
public class BeanFieldSplit<T, I> extends AbstractBeanField<T, I> {
    private final Pattern capture;
    private final Class<? extends Collection> collectionType;
    private final Pattern splitOn;
    private final String writeDelimiter;
    private final String writeFormat;

    public BeanFieldSplit(Class<?> cls, Field field, boolean z, Locale locale, CsvConverter csvConverter, String str, String str2, Class<? extends Collection> cls2, String str3, String str4) {
        super(cls, field, z, locale, csvConverter);
        this.writeDelimiter = str2;
        this.writeFormat = str4;
        if (!Collection.class.isAssignableFrom(field.getType())) {
            throw new CsvBadConverterException(BeanFieldSplit.class, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("invalid.collection.type"), field.getType().toString()));
        }
        this.splitOn = OpencsvUtils.compilePattern(str, 0, BeanFieldSplit.class, this.errorLocale);
        this.capture = OpencsvUtils.compilePatternAtLeastOneGroup(str3, 0, BeanFieldSplit.class, this.errorLocale);
        OpencsvUtils.verifyFormatString(str4, BeanFieldSplit.class, this.errorLocale);
        Class<?> type = field.getType();
        if (!type.isInterface()) {
            this.collectionType = field.getType();
        } else if (!cls2.isInterface()) {
            this.collectionType = cls2;
        } else if (Collection.class.equals(type) || List.class.equals(type)) {
            this.collectionType = ArrayList.class;
        } else if (Set.class.equals(type)) {
            this.collectionType = HashSet.class;
        } else if (SortedSet.class.equals(type) || NavigableSet.class.equals(type)) {
            this.collectionType = TreeSet.class;
        } else if (Queue.class.equals(type) || Deque.class.equals(type)) {
            this.collectionType = ArrayDeque.class;
        } else if (Bag.class.equals(type)) {
            this.collectionType = HashBag.class;
        } else if (SortedBag.class.equals(type)) {
            this.collectionType = TreeBag.class;
        } else {
            this.collectionType = null;
            throw new CsvBadConverterException(BeanFieldSplit.class, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("invalid.collection.type"), cls2.toString()));
        }
        if (!field.getType().isAssignableFrom(this.collectionType)) {
            throw new CsvBadConverterException(BeanFieldSplit.class, String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("unassignable.collection.type"), cls2.getName(), field.getType().getName()));
        }
    }

    @Override // com.opencsv.bean.AbstractBeanField
    protected Object convert(String str) throws CsvConstraintViolationException, CsvDataTypeMismatchException {
        try {
            Collection collectionNewInstance = this.collectionType.newInstance();
            for (String strGroup : this.splitOn.split(str)) {
                Pattern pattern = this.capture;
                if (pattern != null) {
                    Matcher matcher = pattern.matcher(strGroup);
                    if (matcher.matches()) {
                        strGroup = matcher.group(1);
                    }
                }
                collectionNewInstance.add(this.converter.convertToRead(strGroup));
            }
            return collectionNewInstance;
        } catch (IllegalAccessException | InstantiationException e) {
            CsvBeanIntrospectionException csvBeanIntrospectionException = new CsvBeanIntrospectionException(String.format(ResourceBundle.getBundle(ICSVParser.DEFAULT_BUNDLE_NAME, this.errorLocale).getString("collection.cannot.be.instantiated"), this.collectionType.getCanonicalName()));
            csvBeanIntrospectionException.initCause(e);
            throw csvBeanIntrospectionException;
        }
    }

    @Override // com.opencsv.bean.AbstractBeanField
    protected String convertToWrite(Object obj) throws CsvDataTypeMismatchException {
        if (obj == null) {
            return "";
        }
        Collection collection = (Collection) obj;
        String[] strArr = new String[collection.size()];
        Iterator it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            strArr[i] = this.converter.convertToWrite(it.next());
            if (StringUtils.isNotEmpty(this.writeFormat) && StringUtils.isNotEmpty(strArr[i])) {
                strArr[i] = String.format(this.writeFormat, strArr[i]);
            }
            i++;
        }
        return StringUtils.join(strArr, this.writeDelimiter);
    }

    @Override // com.opencsv.bean.AbstractBeanField
    protected boolean isFieldEmptyForWrite(Object obj) {
        return super.isFieldEmptyForWrite(obj) || ((Collection) obj).isEmpty();
    }
}
