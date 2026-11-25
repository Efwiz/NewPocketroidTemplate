package com.reandroid.json;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.common.FileChannelInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class JSONObject extends JSONItem {
    private final LinkedHashMap<String, Object> map;

    public JSONObject() {
        this.map = new LinkedHashMap<>();
    }

    public JSONObject(JSONObject jSONObject, String... strArr) {
        this(strArr.length);
        for (String str : strArr) {
            try {
                putOnce(str, jSONObject.opt(str));
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '{') {
            throw jSONTokener.syntaxError("A JSONObject text must begin with '{'");
        }
        while (true) {
            char cNextClean = jSONTokener.nextClean();
            if (cNextClean == 0) {
                throw jSONTokener.syntaxError("A JSONObject text must end with '}'");
            }
            if (cNextClean == '}') {
                return;
            }
            jSONTokener.back();
            String string = jSONTokener.nextValue().toString();
            if (jSONTokener.nextClean() != ':') {
                throw jSONTokener.syntaxError("Expected a ':' after a key");
            }
            if (string != null) {
                if (opt(string) != null) {
                    throw jSONTokener.syntaxError("Duplicate key \"" + string + "\"");
                }
                Object objNextValue = jSONTokener.nextValue();
                if (objNextValue != null) {
                    put(string, objNextValue);
                }
            }
            char cNextClean2 = jSONTokener.nextClean();
            if (cNextClean2 != ',' && cNextClean2 != ';') {
                if (cNextClean2 != '}') {
                    throw jSONTokener.syntaxError("Expected a ',' or '}'");
                }
                return;
            } else if (jSONTokener.nextClean() == '}') {
                return;
            } else {
                jSONTokener.back();
            }
        }
    }

    public JSONObject(Map<?, ?> map) {
        if (map == null) {
            this.map = new LinkedHashMap<>();
            return;
        }
        this.map = new LinkedHashMap<>(map.size());
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getKey() == null) {
                throw new NullPointerException("Null key.");
            }
            Object value = entry.getValue();
            if (value != null) {
                this.map.put(String.valueOf(entry.getKey()), wrap(value));
            }
        }
    }

    JSONObject(Object obj) {
        this();
        populateMap(obj);
    }

    public JSONObject(String str) throws JSONException {
        this(new JSONTokener(str));
    }

    public JSONObject(String str, Locale locale) throws JSONException {
        this();
        ResourceBundle bundle = ResourceBundle.getBundle(str, locale, Thread.currentThread().getContextClassLoader());
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String strNextElement = keys.nextElement();
            if (strNextElement != null) {
                String str2 = strNextElement;
                String[] strArrSplit = str2.split("\\.");
                int length = strArrSplit.length - 1;
                JSONObject jSONObject = this;
                for (int i = 0; i < length; i++) {
                    String str3 = strArrSplit[i];
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(str3);
                    if (jSONObjectOptJSONObject == null) {
                        jSONObjectOptJSONObject = new JSONObject();
                        jSONObject.put(str3, jSONObjectOptJSONObject);
                    }
                    jSONObject = jSONObjectOptJSONObject;
                }
                jSONObject.put(strArrSplit[length], bundle.getString(str2));
            }
        }
    }

    protected JSONObject(int i) {
        this.map = new LinkedHashMap<>(i);
    }

    public JSONObject(File file) throws IOException {
        this((InputStream) new FileChannelInputStream(file));
    }

    public JSONObject(Reader reader) {
        this(new JSONTokener(reader));
    }

    public JSONObject(InputStream inputStream) throws JSONException {
        this(new JSONTokener(inputStream));
        try {
            inputStream.close();
        } catch (IOException unused) {
        }
    }

    public void sort(Comparator<String> comparator) {
        sort(comparator, false);
    }

    public void sort(Comparator<String> comparator, boolean z) {
        LinkedHashMap<String, Object> linkedHashMap = this.map;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap.clear();
        ArrayList<String> arrayList = new ArrayList(linkedHashMap2.keySet());
        arrayList.sort(comparator);
        for (String str : arrayList) {
            linkedHashMap.put(str, linkedHashMap2.get(str));
        }
        linkedHashMap2.clear();
        if (z) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object obj = linkedHashMap.get((String) it.next());
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).sort(comparator, true);
                }
            }
        }
    }

    public JSONObject accumulate(String str, Object obj) throws JSONException {
        testValidity(obj);
        Object objOpt = opt(str);
        if (objOpt == null) {
            if (obj instanceof JSONArray) {
                obj = new JSONArray().put(obj);
            }
            put(str, obj);
        } else if (objOpt instanceof JSONArray) {
            ((JSONArray) objOpt).put(obj);
        } else {
            put(str, new JSONArray().put(objOpt).put(obj));
        }
        return this;
    }

    public JSONObject append(String str, Object obj) throws JSONException {
        testValidity(obj);
        Object objOpt = opt(str);
        if (objOpt == null) {
            put(str, new JSONArray().put(obj));
        } else if (objOpt instanceof JSONArray) {
            put(str, ((JSONArray) objOpt).put(obj));
        } else {
            throw wrongValueFormatException(str, "JSONArray", null, null);
        }
        return this;
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return "null";
        }
        String string = Double.toString(d);
        if (string.indexOf(46) <= 0 || string.indexOf(101) >= 0 || string.indexOf(69) >= 0) {
            return string;
        }
        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }
        return string.endsWith(".") ? string.substring(0, string.length() - 1) : string;
    }

    public Object get(String str) throws JSONException {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        Object objOpt = opt(str);
        if (objOpt != null) {
            return objOpt;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] not found.");
    }

    public <E extends Enum<E>> E getEnum(Class<E> cls, String str) throws JSONException {
        E e = (E) optEnum(cls, str);
        if (e != null) {
            return e;
        }
        throw wrongValueFormatException(str, "enum of type " + quote(cls.getSimpleName()), null);
    }

    public boolean getBoolean(String str) throws JSONException {
        Object obj = get(str);
        if (obj.equals(Boolean.FALSE)) {
            return false;
        }
        boolean z = obj instanceof String;
        if (z && ((String) obj).equalsIgnoreCase("false")) {
            return false;
        }
        if (obj.equals(Boolean.TRUE)) {
            return true;
        }
        if (z && ((String) obj).equalsIgnoreCase("true")) {
            return true;
        }
        throw wrongValueFormatException(str, "Boolean", null);
    }

    public BigInteger getBigInteger(String str) throws JSONException {
        Object obj = get(str);
        BigInteger bigIntegerObjectToBigInteger = objectToBigInteger(obj, null);
        if (bigIntegerObjectToBigInteger != null) {
            return bigIntegerObjectToBigInteger;
        }
        throw wrongValueFormatException(str, "BigInteger", obj, null);
    }

    public BigDecimal getBigDecimal(String str) throws JSONException {
        Object obj = get(str);
        BigDecimal bigDecimalObjectToBigDecimal = objectToBigDecimal(obj, null);
        if (bigDecimalObjectToBigDecimal != null) {
            return bigDecimalObjectToBigDecimal;
        }
        throw wrongValueFormatException(str, "BigDecimal", obj, null);
    }

    public double getDouble(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(str, "double", e);
        }
    }

    public float getFloat(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(str, "float", e);
        }
    }

    public Number getNumber(String str) throws JSONException {
        Object obj = get(str);
        try {
            if (obj instanceof Number) {
                return (Number) obj;
            }
            return stringToNumber(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(str, "number", e);
        }
    }

    public int getInt(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(str, "int", e);
        }
    }

    public JSONArray getJSONArray(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw wrongValueFormatException(str, "JSONArray", null);
    }

    public JSONObject getJSONObject(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw wrongValueFormatException(str, "JSONObject", null);
    }

    public long getLong(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(str, "long", e);
        }
    }

    public static String[] getNames(JSONObject jSONObject) {
        if (jSONObject.isEmpty()) {
            return null;
        }
        return (String[]) jSONObject.keySet().toArray(new String[jSONObject.length()]);
    }

    public static String[] getNames(Object obj) {
        Field[] fields;
        int length;
        if (obj == null || (length = (fields = obj.getClass().getFields()).length) == 0) {
            return null;
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = fields[i].getName();
        }
        return strArr;
    }

    public String getString(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof String) {
            return (String) obj;
        }
        throw wrongValueFormatException(str, "string", null);
    }

    public boolean has(String str) {
        return this.map.containsKey(str);
    }

    public JSONObject increment(String str) throws JSONException {
        Object objOpt = opt(str);
        if (objOpt == null) {
            put(str, 1);
        } else if (objOpt instanceof Integer) {
            put(str, ((Integer) objOpt).intValue() + 1);
        } else if (objOpt instanceof Long) {
            put(str, ((Long) objOpt).longValue() + 1);
        } else if (objOpt instanceof BigInteger) {
            put(str, ((BigInteger) objOpt).add(BigInteger.ONE));
        } else if (objOpt instanceof Float) {
            put(str, ((Float) objOpt).floatValue() + 1.0f);
        } else if (objOpt instanceof Double) {
            put(str, ((Double) objOpt).doubleValue() + 1.0d);
        } else if (objOpt instanceof BigDecimal) {
            put(str, ((BigDecimal) objOpt).add(BigDecimal.ONE));
        } else {
            throw new JSONException("Unable to increment [" + quote(str) + "].");
        }
        return this;
    }

    public boolean isNull(String str) {
        return NULL.equals(opt(str));
    }

    public Iterator<String> keys() {
        return keySet().iterator();
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    protected Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public int length() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public JSONArray names() {
        if (this.map.isEmpty()) {
            return null;
        }
        return new JSONArray((Collection<?>) this.map.keySet());
    }

    public Object opt(String str) {
        if (str == null) {
            return null;
        }
        return this.map.get(str);
    }

    public <E extends Enum<E>> E optEnum(Class<E> cls, String str) {
        return (E) optEnum(cls, str, null);
    }

    public <E extends Enum<E>> E optEnum(Class<E> cls, String str, E e) {
        try {
            Object objOpt = opt(str);
            if (NULL.equals(objOpt)) {
                return e;
            }
            if (cls.isAssignableFrom(objOpt.getClass())) {
                return (E) objOpt;
            }
            return (E) Enum.valueOf(cls, objOpt.toString());
        } catch (IllegalArgumentException | NullPointerException unused) {
            return e;
        }
    }

    public boolean optBoolean(String str) {
        return optBoolean(str, false);
    }

    public boolean optBoolean(String str, boolean z) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return z;
        }
        if (objOpt instanceof Boolean) {
            return ((Boolean) objOpt).booleanValue();
        }
        try {
            return getBoolean(str);
        } catch (Exception unused) {
            return z;
        }
    }

    public BigDecimal optBigDecimal(String str, BigDecimal bigDecimal) {
        return objectToBigDecimal(opt(str), bigDecimal);
    }

    static BigDecimal objectToBigDecimal(Object obj, BigDecimal bigDecimal) {
        if (NULL.equals(obj)) {
            return bigDecimal;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof BigInteger) {
            return new BigDecimal((BigInteger) obj);
        }
        if ((obj instanceof Double) || (obj instanceof Float)) {
            Number number = (Number) obj;
            return Double.isNaN(number.doubleValue()) ? bigDecimal : new BigDecimal(number.doubleValue());
        }
        if ((obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) {
            return new BigDecimal(((Number) obj).longValue());
        }
        try {
            return new BigDecimal(obj.toString());
        } catch (Exception unused) {
            return bigDecimal;
        }
    }

    public BigInteger optBigInteger(String str, BigInteger bigInteger) {
        return objectToBigInteger(opt(str), bigInteger);
    }

    static BigInteger objectToBigInteger(Object obj, BigInteger bigInteger) {
        if (NULL.equals(obj)) {
            return bigInteger;
        }
        if (obj instanceof BigInteger) {
            return (BigInteger) obj;
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).toBigInteger();
        }
        if ((obj instanceof Double) || (obj instanceof Float)) {
            double dDoubleValue = ((Number) obj).doubleValue();
            return Double.isNaN(dDoubleValue) ? bigInteger : new BigDecimal(dDoubleValue).toBigInteger();
        }
        if ((obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) {
            return BigInteger.valueOf(((Number) obj).longValue());
        }
        try {
            String string = obj.toString();
            if (isDecimalNotation(string)) {
                return new BigDecimal(string).toBigInteger();
            }
            return new BigInteger(string);
        } catch (Exception unused) {
            return bigInteger;
        }
    }

    public double optDouble(String str) {
        return optDouble(str, Double.NaN);
    }

    public double optDouble(String str, double d) {
        Number numberOptNumber = optNumber(str);
        return numberOptNumber == null ? d : numberOptNumber.doubleValue();
    }

    public float optFloat(String str) {
        return optFloat(str, Float.NaN);
    }

    public float optFloat(String str, float f) {
        Number numberOptNumber = optNumber(str);
        return numberOptNumber == null ? f : numberOptNumber.floatValue();
    }

    public int optInt(String str) {
        return optInt(str, 0);
    }

    public int optInt(String str, int i) {
        Number numberOptNumber = optNumber(str, null);
        return numberOptNumber == null ? i : numberOptNumber.intValue();
    }

    public JSONArray optJSONArray(String str) {
        Object objOpt = opt(str);
        if (objOpt instanceof JSONArray) {
            return (JSONArray) objOpt;
        }
        return null;
    }

    public JSONObject optJSONObject(String str) {
        Object objOpt = opt(str);
        if (objOpt instanceof JSONObject) {
            return (JSONObject) objOpt;
        }
        return null;
    }

    public long optLong(String str) {
        return optLong(str, 0L);
    }

    public long optLong(String str, long j) {
        Number numberOptNumber = optNumber(str, null);
        return numberOptNumber == null ? j : numberOptNumber.longValue();
    }

    public Number optNumber(String str) {
        return optNumber(str, null);
    }

    public Number optNumber(String str, Number number) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return number;
        }
        if (objOpt instanceof Number) {
            return (Number) objOpt;
        }
        try {
            return stringToNumber(objOpt.toString());
        } catch (Exception unused) {
            return number;
        }
    }

    public String optString(String str) {
        return optString(str, "");
    }

    public String optString(String str, String str2) {
        Object objOpt = opt(str);
        return NULL.equals(objOpt) ? str2 : objOpt.toString();
    }

    private void populateMap(Object obj) {
        String keyNameFromMethod;
        Class<?> cls = obj.getClass();
        for (Method method : cls.getClassLoader() != null ? cls.getMethods() : cls.getDeclaredMethods()) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && method.getParameterTypes().length == 0 && !method.isBridge() && method.getReturnType() != Void.TYPE && isValidMethodName(method.getName()) && (keyNameFromMethod = getKeyNameFromMethod(method)) != null && !keyNameFromMethod.isEmpty()) {
                try {
                    Object objInvoke = method.invoke(obj, new Object[0]);
                    if (objInvoke != null) {
                        this.map.put(keyNameFromMethod, wrap(objInvoke));
                        if (objInvoke instanceof Closeable) {
                            ((Closeable) objInvoke).close();
                        }
                    }
                } catch (IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                }
            }
        }
    }

    private static boolean isValidMethodName(String str) {
        return ("getClass".equals(str) || "getDeclaringClass".equals(str)) ? false : true;
    }

    private static String getKeyNameFromMethod(Method method) {
        String strSubstring;
        int annotationDepth;
        int annotationDepth2 = getAnnotationDepth(method, JSONPropertyIgnore.class);
        if (annotationDepth2 > 0 && ((annotationDepth = getAnnotationDepth(method, JSONPropertyName.class)) < 0 || annotationDepth2 <= annotationDepth)) {
            return null;
        }
        JSONPropertyName jSONPropertyName = (JSONPropertyName) getAnnotation(method, JSONPropertyName.class);
        if (jSONPropertyName != null && jSONPropertyName.value() != null && !jSONPropertyName.value().isEmpty()) {
            return jSONPropertyName.value();
        }
        String name2 = method.getName();
        if (name2.startsWith("get") && name2.length() > 3) {
            strSubstring = name2.substring(3);
        } else {
            if (!name2.startsWith(LanguageCodeUtil.IS) || name2.length() <= 2) {
                return null;
            }
            strSubstring = name2.substring(2);
        }
        if (Character.isLowerCase(strSubstring.charAt(0))) {
            return null;
        }
        if (strSubstring.length() == 1) {
            return strSubstring.toLowerCase(Locale.ROOT);
        }
        return !Character.isUpperCase(strSubstring.charAt(1)) ? strSubstring.substring(0, 1).toLowerCase(Locale.ROOT) + strSubstring.substring(1) : strSubstring;
    }

    private static <A extends Annotation> A getAnnotation(Method method, Class<A> cls) {
        if (method != null && cls != null) {
            if (method.isAnnotationPresent(cls)) {
                return (A) method.getAnnotation(cls);
            }
            Class<?> declaringClass = method.getDeclaringClass();
            if (declaringClass.getSuperclass() == null) {
                return null;
            }
            for (Class<?> cls2 : declaringClass.getInterfaces()) {
                try {
                    return (A) getAnnotation(cls2.getMethod(method.getName(), method.getParameterTypes()), cls);
                } catch (NoSuchMethodException | SecurityException unused) {
                }
            }
            try {
                return (A) getAnnotation(declaringClass.getSuperclass().getMethod(method.getName(), method.getParameterTypes()), cls);
            } catch (NoSuchMethodException | SecurityException unused2) {
            }
        }
        return null;
    }

    private static int getAnnotationDepth(Method method, Class<? extends Annotation> cls) {
        if (method != null && cls != null) {
            if (method.isAnnotationPresent(cls)) {
                return 1;
            }
            Class<?> declaringClass = method.getDeclaringClass();
            if (declaringClass.getSuperclass() == null) {
                return -1;
            }
            for (Class<?> cls2 : declaringClass.getInterfaces()) {
                try {
                    int annotationDepth = getAnnotationDepth(cls2.getMethod(method.getName(), method.getParameterTypes()), cls);
                    if (annotationDepth > 0) {
                        return annotationDepth + 1;
                    }
                } catch (NoSuchMethodException | SecurityException unused) {
                }
            }
            try {
                int annotationDepth2 = getAnnotationDepth(declaringClass.getSuperclass().getMethod(method.getName(), method.getParameterTypes()), cls);
                if (annotationDepth2 > 0) {
                    return annotationDepth2 + 1;
                }
            } catch (NoSuchMethodException | SecurityException unused2) {
            }
        }
        return -1;
    }

    public JSONObject put(String str, boolean z) throws JSONException {
        return put(str, z ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONObject put(String str, Collection<?> collection) throws JSONException {
        return put(str, new JSONArray(collection));
    }

    public JSONObject put(String str, double d) throws JSONException {
        return put(str, Double.valueOf(d));
    }

    public JSONObject put(String str, float f) throws JSONException {
        return put(str, Float.valueOf(f));
    }

    public JSONObject put(String str, int i) throws JSONException {
        return put(str, Integer.valueOf(i));
    }

    public JSONObject put(String str, long j) throws JSONException {
        return put(str, Long.valueOf(j));
    }

    public JSONObject put(String str, Map<?, ?> map) throws JSONException {
        return put(str, new JSONObject(map));
    }

    public JSONObject put(String str, Object obj) throws JSONException {
        if (str == null) {
            throw new NullPointerException("Null key.");
        }
        if (obj != null) {
            testValidity(obj);
            this.map.put(str, obj);
        } else {
            remove(str);
        }
        return this;
    }

    public JSONObject putOnce(String str, Object obj) throws JSONException {
        if (str == null || obj == null) {
            return this;
        }
        if (opt(str) != null) {
            throw new JSONException("Duplicate key \"" + str + "\"");
        }
        return put(str, obj);
    }

    public JSONObject putOpt(String str, Object obj) throws JSONException {
        return (str == null || obj == null) ? this : put(str, obj);
    }

    public Object query(String str) {
        return query(new JSONPointer(str));
    }

    public Object query(JSONPointer jSONPointer) {
        return jSONPointer.queryFrom(this);
    }

    public Object optQuery(String str) {
        return optQuery(new JSONPointer(str));
    }

    public Object optQuery(JSONPointer jSONPointer) {
        try {
            return jSONPointer.queryFrom(this);
        } catch (JSONPointerException unused) {
            return null;
        }
    }

    public Object remove(String str) {
        return this.map.remove(str);
    }

    public boolean similar(Object obj) {
        try {
            if (!(obj instanceof JSONObject) || !keySet().equals(((JSONObject) obj).keySet())) {
                return false;
            }
            for (Map.Entry<String, Object> entry : entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Object obj2 = ((JSONObject) obj).get(key);
                if (value != obj2) {
                    if (value == null) {
                        return false;
                    }
                    if (value instanceof JSONObject) {
                        if (!((JSONObject) value).similar(obj2)) {
                            return false;
                        }
                    } else if (value instanceof JSONArray) {
                        if (!((JSONArray) value).similar(obj2)) {
                            return false;
                        }
                    } else if (!value.equals(obj2)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    protected static boolean isDecimalNotation(String str) {
        return str.indexOf(46) > -1 || str.indexOf(101) > -1 || str.indexOf(69) > -1 || "-0".equals(str);
    }

    protected static Number stringToNumber(String str) throws NumberFormatException {
        char cCharAt = str.charAt(0);
        if ((cCharAt >= '0' && cCharAt <= '9') || cCharAt == '-') {
            if (isDecimalNotation(str)) {
                try {
                    try {
                        BigDecimal bigDecimal = new BigDecimal(str);
                        return (cCharAt == '-' && BigDecimal.ZERO.compareTo(bigDecimal) == 0) ? Double.valueOf(-0.0d) : bigDecimal;
                    } catch (NumberFormatException unused) {
                        Double dValueOf = Double.valueOf(str);
                        if (dValueOf.isNaN() || dValueOf.isInfinite()) {
                            throw new NumberFormatException("val [" + str + "] is not a valid number.");
                        }
                        return dValueOf;
                    }
                } catch (NumberFormatException unused2) {
                    throw new NumberFormatException("val [" + str + "] is not a valid number.");
                }
                throw new NumberFormatException("val [" + str + "] is not a valid number.");
            }
            if (cCharAt == '0' && str.length() > 1) {
                char cCharAt2 = str.charAt(1);
                if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                    throw new NumberFormatException("val [" + str + "] is not a valid number.");
                }
            } else if (cCharAt == '-' && str.length() > 2) {
                char cCharAt3 = str.charAt(1);
                char cCharAt4 = str.charAt(2);
                if (cCharAt3 == '0' && cCharAt4 >= '0' && cCharAt4 <= '9') {
                    throw new NumberFormatException("val [" + str + "] is not a valid number.");
                }
            }
            BigInteger bigInteger = new BigInteger(str);
            if (bigInteger.bitLength() <= 31) {
                return Integer.valueOf(bigInteger.intValue());
            }
            return bigInteger.bitLength() <= 63 ? Long.valueOf(bigInteger.longValue()) : bigInteger;
        }
        throw new NumberFormatException("val [" + str + "] is not a valid number.");
    }

    public static Object stringToValue(String str) {
        if ("".equals(str)) {
            return str;
        }
        if ("true".equalsIgnoreCase(str)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(str)) {
            return Boolean.FALSE;
        }
        if ("null".equalsIgnoreCase(str)) {
            return NULL;
        }
        char cCharAt = str.charAt(0);
        if ((cCharAt < '0' || cCharAt > '9') && cCharAt != '-') {
            return str;
        }
        try {
            return stringToNumber(str);
        } catch (Exception unused) {
            return str;
        }
    }

    public JSONArray toJSONArray(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.isEmpty()) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONArray2.put(opt(jSONArray.getString(i)));
        }
        return jSONArray2;
    }

    @Override // com.reandroid.json.JSONItem
    public Writer write(Writer writer, int i, int i2) throws JSONException {
        try {
            writer.write(123);
            int i3 = i2 + i;
            boolean z = false;
            for (Map.Entry<String, Object> entry : entrySet()) {
                if (z) {
                    writer.write(44);
                }
                if (i > 0) {
                    writer.write(10);
                }
                indent(writer, i3);
                String key = entry.getKey();
                writer.write(quote(key));
                writer.write(58);
                if (i > 0) {
                    writer.write(32);
                }
                try {
                    writeValue(writer, entry.getValue(), i, i3);
                    z = true;
                } catch (Exception e) {
                    throw new JSONException("Unable to write JSONObject value for key: " + key, e);
                }
            }
            if (z) {
                if (i > 0) {
                    writer.write(10);
                }
                indent(writer, i2);
            }
            writer.write(125);
            return writer;
        } catch (IOException e2) {
            throw new JSONException(e2);
        }
    }

    public LinkedHashMap<String, Object> toMap() {
        Object value;
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : entrySet()) {
            if (entry.getValue() == null || NULL.equals(entry.getValue())) {
                value = null;
            } else if (entry.getValue() instanceof JSONObject) {
                value = ((JSONObject) entry.getValue()).toMap();
            } else if (entry.getValue() instanceof JSONArray) {
                value = ((JSONArray) entry.getValue()).toList();
            } else {
                value = entry.getValue();
            }
            linkedHashMap.put(entry.getKey(), value);
        }
        return linkedHashMap;
    }

    private static JSONException wrongValueFormatException(String str, String str2, Throwable th) {
        return new JSONException("JSONObject[" + quote(str) + "] is not a " + str2 + ".", th);
    }

    private static JSONException wrongValueFormatException(String str, String str2, Object obj, Throwable th) {
        return new JSONException("JSONObject[" + quote(str) + "] is not a " + str2 + " (" + obj + ").", th);
    }
}
