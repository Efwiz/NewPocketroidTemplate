package org.apache.commons.beanutils.converters;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/* JADX INFO: loaded from: classes12.dex */
public class ArrayConverter extends AbstractConverter {
    private char[] allowedChars;
    private int defaultSize;
    private final Class<?> defaultType;
    private char delimiter;
    private final Converter elementConverter;
    private boolean onlyFirstToString;

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Object convertArray(Object obj) {
        return obj;
    }

    public ArrayConverter(Class<?> cls, Converter converter) {
        this.delimiter = ',';
        this.allowedChars = new char[]{'.', '-'};
        this.onlyFirstToString = true;
        if (cls == null) {
            throw new IllegalArgumentException("Default type is missing");
        }
        if (!cls.isArray()) {
            throw new IllegalArgumentException("Default type must be an array.");
        }
        if (converter == null) {
            throw new IllegalArgumentException("Component Converter is missing.");
        }
        this.defaultType = cls;
        this.elementConverter = converter;
    }

    public ArrayConverter(Class<?> cls, Converter converter, int i) {
        this(cls, converter);
        this.defaultSize = i;
        setDefaultValue(i >= 0 ? Array.newInstance(cls.getComponentType(), i) : null);
    }

    public void setDelimiter(char c) {
        this.delimiter = c;
    }

    public void setAllowedChars(char[] cArr) {
        this.allowedChars = cArr;
    }

    public void setOnlyFirstToString(boolean z) {
        this.onlyFirstToString = z;
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Class<?> getDefaultType() {
        return this.defaultType;
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected String convertToString(Object obj) throws Throwable {
        Iterator<?> it;
        int length;
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            length = Array.getLength(obj);
            it = null;
        } else {
            Collection<?> collectionConvertToCollection = convertToCollection(cls, obj);
            int size = collectionConvertToCollection.size();
            it = collectionConvertToCollection.iterator();
            length = size;
        }
        if (length == 0) {
            return (String) getDefault(String.class);
        }
        if (this.onlyFirstToString) {
            length = 1;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(this.delimiter);
            }
            Object objConvert = this.elementConverter.convert(String.class, it == null ? Array.get(obj, i) : it.next());
            if (objConvert != null) {
                sb.append(objConvert);
            }
        }
        return sb.toString();
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected <T> T convertToType(Class<T> cls, Object obj) throws Throwable {
        Iterator<?> it;
        int length;
        if (!cls.isArray()) {
            throw new ConversionException(toString(getClass()) + " cannot handle conversion to '" + toString(cls) + "' (not an array).");
        }
        if (obj.getClass().isArray()) {
            length = Array.getLength(obj);
            it = null;
        } else {
            Collection<?> collectionConvertToCollection = convertToCollection(cls, obj);
            int size = collectionConvertToCollection.size();
            it = collectionConvertToCollection.iterator();
            length = size;
        }
        Class<?> componentType = cls.getComponentType();
        T t = (T) Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Array.set(t, i, this.elementConverter.convert(componentType, it == null ? Array.get(obj, i) : it.next()));
        }
        return t;
    }

    protected Collection<?> convertToCollection(Class<?> cls, Object obj) {
        if (obj instanceof Collection) {
            return (Collection) obj;
        }
        if ((obj instanceof Number) || (obj instanceof Boolean) || (obj instanceof Date)) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(obj);
            return arrayList;
        }
        return parseElements(cls, obj.toString());
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    protected Object getDefault(Class<?> cls) {
        Object obj;
        if (cls.equals(String.class) || (obj = super.getDefault(cls)) == null) {
            return null;
        }
        return obj.getClass().equals(cls) ? obj : Array.newInstance(cls.getComponentType(), this.defaultSize);
    }

    @Override // org.apache.commons.beanutils.converters.AbstractConverter
    public String toString() {
        return toString(getClass()) + "[UseDefault=" + isUseDefault() + ", " + this.elementConverter.toString() + ']';
    }

    private List<String> parseElements(Class<?> cls, String str) {
        int iNextToken;
        if (log().isDebugEnabled()) {
            log().debug("Parsing elements, delimiter=[" + this.delimiter + "], value=[" + str + "]");
        }
        String strTrim = str.trim();
        if (strTrim.startsWith("{") && strTrim.endsWith("}")) {
            strTrim = strTrim.substring(1, strTrim.length() - 1);
        }
        try {
            StreamTokenizer streamTokenizer = new StreamTokenizer(new StringReader(strTrim));
            char c = this.delimiter;
            streamTokenizer.whitespaceChars(c, c);
            streamTokenizer.ordinaryChars(48, 57);
            streamTokenizer.wordChars(48, 57);
            for (char c2 : this.allowedChars) {
                streamTokenizer.ordinaryChars(c2, c2);
                streamTokenizer.wordChars(c2, c2);
            }
            List<String> arrayList = null;
            while (true) {
                iNextToken = streamTokenizer.nextToken();
                if (iNextToken != -3 && iNextToken <= 0) {
                    break;
                }
                if (streamTokenizer.sval != null) {
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(streamTokenizer.sval);
                }
            }
            if (iNextToken != -1) {
                throw new ConversionException("Encountered token of type " + iNextToken + " parsing elements to '" + toString(cls) + ".");
            }
            if (arrayList == null) {
                arrayList = Collections.emptyList();
            }
            if (log().isDebugEnabled()) {
                log().debug(arrayList.size() + " elements parsed");
            }
            return arrayList;
        } catch (IOException e) {
            throw new ConversionException("Error converting from String to '" + toString(cls) + "': " + e.getMessage(), e);
        }
    }
}
