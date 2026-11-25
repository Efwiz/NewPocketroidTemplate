package com.reandroid.json;

import com.reandroid.common.FileChannelInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes11.dex */
public class JSONArray extends JSONItem implements Iterable<Object> {
    private final ArrayList<Object> myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList<>();
    }

    public JSONArray(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '[') {
            throw jSONTokener.syntaxError("A JSONArray text must start with '['");
        }
        char cNextClean = jSONTokener.nextClean();
        if (cNextClean == 0) {
            throw jSONTokener.syntaxError("Expected a ',' or ']'");
        }
        if (cNextClean == ']') {
            return;
        }
        jSONTokener.back();
        while (true) {
            if (jSONTokener.nextClean() == ',') {
                jSONTokener.back();
                this.myArrayList.add(JSONObject.NULL);
            } else {
                jSONTokener.back();
                this.myArrayList.add(jSONTokener.nextValue());
            }
            char cNextClean2 = jSONTokener.nextClean();
            if (cNextClean2 == 0) {
                throw jSONTokener.syntaxError("Expected a ',' or ']'");
            }
            if (cNextClean2 != ',') {
                if (cNextClean2 != ']') {
                    throw jSONTokener.syntaxError("Expected a ',' or ']'");
                }
                return;
            }
            char cNextClean3 = jSONTokener.nextClean();
            if (cNextClean3 == 0) {
                throw jSONTokener.syntaxError("Expected a ',' or ']'");
            }
            if (cNextClean3 == ']') {
                return;
            } else {
                jSONTokener.back();
            }
        }
    }

    public JSONArray(String str) throws JSONException {
        this(new JSONTokener(str));
    }

    public JSONArray(Collection<?> collection) {
        if (collection == null) {
            this.myArrayList = new ArrayList<>();
            return;
        }
        ArrayList<Object> arrayList = new ArrayList<>(collection.size());
        this.myArrayList = arrayList;
        addAll(collection, true);
        arrayList.trimToSize();
    }

    public JSONArray(Iterable<?> iterable) {
        this();
        if (iterable == null) {
            return;
        }
        addAll(iterable, true);
    }

    public JSONArray(JSONArray jSONArray) {
        if (jSONArray == null) {
            this.myArrayList = new ArrayList<>();
        } else {
            this.myArrayList = new ArrayList<>(jSONArray.myArrayList);
        }
    }

    JSONArray(Object obj) throws JSONException {
        this();
        if (!obj.getClass().isArray()) {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
        addAll(obj, true);
    }

    public JSONArray(int i) throws JSONException {
        if (i < 0) {
            throw new JSONException("JSONArray initial capacity cannot be negative.");
        }
        this.myArrayList = new ArrayList<>(i);
    }

    public JSONArray(File file) throws IOException {
        this((InputStream) new FileChannelInputStream(file));
    }

    public JSONArray(Reader reader) {
        this(new JSONTokener(reader));
    }

    public JSONArray(InputStream inputStream) throws JSONException {
        this(new JSONTokener(inputStream));
        try {
            inputStream.close();
        } catch (IOException unused) {
        }
    }

    public void trimToSize() {
        this.myArrayList.trimToSize();
    }

    public ArrayList<Object> getArrayList() {
        return this.myArrayList;
    }

    @Override // java.lang.Iterable
    public Iterator<Object> iterator() {
        return this.myArrayList.iterator();
    }

    public Object get(int i) throws JSONException {
        Object objOpt = opt(i);
        if (objOpt != null) {
            return objOpt;
        }
        throw new JSONException("JSONArray[" + i + "] not found.");
    }

    public boolean getBoolean(int i) throws JSONException {
        Object obj = get(i);
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
        throw wrongValueFormatException(i, "boolean", null);
    }

    public double getDouble(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(i, "double", e);
        }
    }

    public float getFloat(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof Number) {
            return ((Float) obj).floatValue();
        }
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(i, "float", e);
        }
    }

    public Number getNumber(int i) throws JSONException {
        Object obj = get(i);
        try {
            if (obj instanceof Number) {
                return (Number) obj;
            }
            return JSONObject.stringToNumber(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(i, "number", e);
        }
    }

    public <E extends Enum<E>> E getEnum(Class<E> cls, int i) throws JSONException {
        E e = (E) optEnum(cls, i);
        if (e != null) {
            return e;
        }
        throw wrongValueFormatException(i, "enum of type " + JSONObject.quote(cls.getSimpleName()), null);
    }

    public BigDecimal getBigDecimal(int i) throws JSONException {
        Object obj = get(i);
        BigDecimal bigDecimalObjectToBigDecimal = JSONObject.objectToBigDecimal(obj, null);
        if (bigDecimalObjectToBigDecimal != null) {
            return bigDecimalObjectToBigDecimal;
        }
        throw wrongValueFormatException(i, "BigDecimal", obj, null);
    }

    public BigInteger getBigInteger(int i) throws JSONException {
        Object obj = get(i);
        BigInteger bigIntegerObjectToBigInteger = JSONObject.objectToBigInteger(obj, null);
        if (bigIntegerObjectToBigInteger != null) {
            return bigIntegerObjectToBigInteger;
        }
        throw wrongValueFormatException(i, "BigInteger", obj, null);
    }

    public int getInt(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(i, "int", e);
        }
    }

    public JSONArray getJSONArray(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw wrongValueFormatException(i, "JSONArray", null);
    }

    public JSONObject getJSONObject(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw wrongValueFormatException(i, "JSONObject", null);
    }

    public long getLong(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(i, "long", e);
        }
    }

    public String getString(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        throw wrongValueFormatException(i, CommonClassNames.JAVA_LANG_STRING_SHORT, null);
    }

    public boolean isNull(int i) {
        return JSONObject.NULL.equals(opt(i));
    }

    public String join(String str) throws JSONException {
        int length = length();
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(JSONObject.valueToString(this.myArrayList.get(0)));
        for (int i = 1; i < length; i++) {
            sb.append(str).append(JSONObject.valueToString(this.myArrayList.get(i)));
        }
        return sb.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return this.myArrayList.get(i);
    }

    public boolean optBoolean(int i) {
        return optBoolean(i, false);
    }

    public boolean optBoolean(int i, boolean z) {
        try {
            return getBoolean(i);
        } catch (Exception unused) {
            return z;
        }
    }

    public double optDouble(int i) {
        return optDouble(i, Double.NaN);
    }

    public double optDouble(int i, double d) {
        Number numberOptNumber = optNumber(i, null);
        return numberOptNumber == null ? d : numberOptNumber.doubleValue();
    }

    public float optFloat(int i) {
        return optFloat(i, Float.NaN);
    }

    public float optFloat(int i, float f) {
        Number numberOptNumber = optNumber(i, null);
        return numberOptNumber == null ? f : numberOptNumber.floatValue();
    }

    public int optInt(int i) {
        return optInt(i, 0);
    }

    public int optInt(int i, int i2) {
        Number numberOptNumber = optNumber(i, null);
        return numberOptNumber == null ? i2 : numberOptNumber.intValue();
    }

    public <E extends Enum<E>> E optEnum(Class<E> cls, int i) {
        return (E) optEnum(cls, i, null);
    }

    public <E extends Enum<E>> E optEnum(Class<E> cls, int i, E e) {
        try {
            Object objOpt = opt(i);
            if (JSONObject.NULL.equals(objOpt)) {
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

    public BigInteger optBigInteger(int i, BigInteger bigInteger) {
        return JSONObject.objectToBigInteger(opt(i), bigInteger);
    }

    public BigDecimal optBigDecimal(int i, BigDecimal bigDecimal) {
        return JSONObject.objectToBigDecimal(opt(i), bigDecimal);
    }

    public JSONArray optJSONArray(int i) {
        Object objOpt = opt(i);
        if (objOpt instanceof JSONArray) {
            return (JSONArray) objOpt;
        }
        return null;
    }

    public JSONObject optJSONObject(int i) {
        Object objOpt = opt(i);
        if (objOpt instanceof JSONObject) {
            return (JSONObject) objOpt;
        }
        return null;
    }

    public long optLong(int i) {
        return optLong(i, 0L);
    }

    public long optLong(int i, long j) {
        Number numberOptNumber = optNumber(i, null);
        return numberOptNumber == null ? j : numberOptNumber.longValue();
    }

    public Number optNumber(int i) {
        return optNumber(i, null);
    }

    public Number optNumber(int i, Number number) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
            return number;
        }
        if (objOpt instanceof Number) {
            return (Number) objOpt;
        }
        if (objOpt instanceof String) {
            try {
                return JSONObject.stringToNumber((String) objOpt);
            } catch (Exception unused) {
            }
        }
        return number;
    }

    public String optString(int i) {
        return optString(i, "");
    }

    public String optString(int i, String str) {
        Object objOpt = opt(i);
        return JSONObject.NULL.equals(objOpt) ? str : objOpt.toString();
    }

    public JSONArray put(boolean z) {
        return put(z ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(Collection<?> collection) {
        return put(new JSONArray(collection));
    }

    public JSONArray put(double d) throws JSONException {
        return put(Double.valueOf(d));
    }

    public JSONArray put(float f) throws JSONException {
        return put(Float.valueOf(f));
    }

    public JSONArray put(int i) {
        return put(Integer.valueOf(i));
    }

    public JSONArray put(long j) {
        return put(Long.valueOf(j));
    }

    public JSONArray put(Map<?, ?> map) {
        return put(new JSONObject(map));
    }

    public JSONArray put(Object obj) {
        JSONObject.testValidity(obj);
        this.myArrayList.add(obj);
        return this;
    }

    public JSONArray put(int i, boolean z) throws JSONException {
        return put(i, z ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(int i, Collection<?> collection) throws JSONException {
        return put(i, new JSONArray(collection));
    }

    public JSONArray put(int i, double d) throws JSONException {
        return put(i, Double.valueOf(d));
    }

    public JSONArray put(int i, float f) throws JSONException {
        return put(i, Float.valueOf(f));
    }

    public JSONArray put(int i, int i2) throws JSONException {
        return put(i, Integer.valueOf(i2));
    }

    public JSONArray put(int i, long j) throws JSONException {
        return put(i, Long.valueOf(j));
    }

    public JSONArray put(int i, Map<?, ?> map) throws JSONException {
        put(i, new JSONObject(map));
        return this;
    }

    public JSONArray put(int i, Object obj) throws JSONException {
        if (i < 0) {
            throw new JSONException("JSONArray[" + i + "] not found.");
        }
        if (i < length()) {
            JSONObject.testValidity(obj);
            this.myArrayList.set(i, obj);
            return this;
        }
        if (i == length()) {
            return put(obj);
        }
        this.myArrayList.ensureCapacity(i + 1);
        while (i != length()) {
            this.myArrayList.add(JSONObject.NULL);
        }
        return put(obj);
    }

    public JSONArray putAll(Collection<?> collection) {
        addAll(collection, false);
        return this;
    }

    public JSONArray putAll(Iterable<?> iterable) {
        addAll(iterable, false);
        return this;
    }

    public JSONArray putAll(JSONArray jSONArray) {
        this.myArrayList.addAll(jSONArray.myArrayList);
        return this;
    }

    public JSONArray putAll(Object obj) throws JSONException {
        addAll(obj, false);
        return this;
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

    public Object remove(int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return this.myArrayList.remove(i);
    }

    public boolean similar(Object obj) {
        if (!(obj instanceof JSONArray)) {
            return false;
        }
        int length = length();
        JSONArray jSONArray = (JSONArray) obj;
        if (length != jSONArray.length()) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            Object obj2 = this.myArrayList.get(i);
            Object obj3 = jSONArray.myArrayList.get(i);
            if (obj2 != obj3) {
                if (obj2 == null) {
                    return false;
                }
                if (obj2 instanceof JSONObject) {
                    if (!((JSONObject) obj2).similar(obj3)) {
                        return false;
                    }
                } else if (obj2 instanceof JSONArray) {
                    if (!((JSONArray) obj2).similar(obj3)) {
                        return false;
                    }
                } else if (!obj2.equals(obj3)) {
                    return false;
                }
            }
        }
        return true;
    }

    public JSONObject toJSONObject(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.isEmpty() || isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONObject.put(jSONArray.getString(i), opt(i));
        }
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONItem
    public Writer write(Writer writer, int i, int i2) throws JSONException {
        try {
            writer.write(91);
            int i3 = i2 + i;
            ArrayList<Object> arrayList = this.myArrayList;
            int size = arrayList.size();
            int i4 = 0;
            boolean z = false;
            while (i4 < size) {
                if (z) {
                    writer.write(44);
                }
                if (i > 0) {
                    writer.write(10);
                }
                indent(writer, i3);
                try {
                    writeValue(writer, arrayList.get(i4), i, i3);
                    i4++;
                    z = true;
                } catch (Exception e) {
                    throw new JSONException("Unable to write JSONArray value at index: " + i4, e);
                }
            }
            if (z) {
                if (i > 0) {
                    writer.write(10);
                }
                indent(writer, i2);
            }
            writer.write(93);
            return writer;
        } catch (IOException e2) {
            throw new JSONException(e2);
        }
    }

    public List<Object> toList() {
        ArrayList arrayList = new ArrayList(this.myArrayList.size());
        for (Object obj : this.myArrayList) {
            if (obj == null || JSONObject.NULL.equals(obj)) {
                arrayList.add(null);
            } else if (obj instanceof JSONArray) {
                arrayList.add(((JSONArray) obj).toList());
            } else if (obj instanceof JSONObject) {
                arrayList.add(((JSONObject) obj).toMap());
            } else {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public boolean isEmpty() {
        return this.myArrayList.isEmpty();
    }

    public void sort(Comparator comparator) {
        this.myArrayList.sort(comparator);
    }

    private void addAll(Collection<?> collection, boolean z) {
        ArrayList<Object> arrayList = this.myArrayList;
        arrayList.ensureCapacity(arrayList.size() + collection.size());
        if (z) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                put(JSONObject.wrap(it.next()));
            }
        } else {
            Iterator<?> it2 = collection.iterator();
            while (it2.hasNext()) {
                put(it2.next());
            }
        }
    }

    private void addAll(Iterable<?> iterable, boolean z) {
        if (z) {
            Iterator<?> it = iterable.iterator();
            while (it.hasNext()) {
                put(JSONObject.wrap(it.next()));
            }
        } else {
            Iterator<?> it2 = iterable.iterator();
            while (it2.hasNext()) {
                put(it2.next());
            }
        }
    }

    private void addAll(Object obj, boolean z) throws JSONException {
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            ArrayList<Object> arrayList = this.myArrayList;
            arrayList.ensureCapacity(arrayList.size() + length);
            int i = 0;
            if (z) {
                while (i < length) {
                    put(JSONObject.wrap(Array.get(obj, i)));
                    i++;
                }
                return;
            } else {
                while (i < length) {
                    put(Array.get(obj, i));
                    i++;
                }
                return;
            }
        }
        if (obj instanceof JSONArray) {
            this.myArrayList.addAll(((JSONArray) obj).myArrayList);
        } else if (obj instanceof Collection) {
            addAll((Collection<?>) obj, z);
        } else {
            if (obj instanceof Iterable) {
                addAll((Iterable<?>) obj, z);
                return;
            }
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
    }

    private static JSONException wrongValueFormatException(int i, String str, Throwable th) {
        return new JSONException("JSONArray[" + i + "] is not a " + str + ".", th);
    }

    private static JSONException wrongValueFormatException(int i, String str, Object obj, Throwable th) {
        return new JSONException("JSONArray[" + i + "] is not a " + str + " (" + obj + ").", th);
    }
}
