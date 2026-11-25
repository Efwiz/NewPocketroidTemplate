package org.apache.commons.collections;

import com.huawei.hms.framework.common.ContainerUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes12.dex */
public class ExtendedProperties extends Hashtable {
    protected static final String END_TOKEN = "}";
    protected static final String START_TOKEN = "${";
    protected static String include = "include";
    protected String basePath;
    private ExtendedProperties defaults;
    protected String file;
    protected String fileSeparator;
    protected boolean isInitialized;
    protected ArrayList keysAsListed;

    protected String interpolate(String str) {
        return interpolateHelper(str, null);
    }

    protected String interpolateHelper(String str, List list) {
        int iIndexOf;
        if (str == null) {
            return null;
        }
        if (list == null) {
            list = new ArrayList();
            list.add(str);
        }
        int length = 0 - END_TOKEN.length();
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int iIndexOf2 = str.indexOf(START_TOKEN, END_TOKEN.length() + length);
            if (iIndexOf2 <= -1 || (iIndexOf = str.indexOf(END_TOKEN, iIndexOf2)) <= -1) {
                break;
            }
            stringBuffer.append(str.substring(length + END_TOKEN.length(), iIndexOf2));
            String strSubstring = str.substring(iIndexOf2 + START_TOKEN.length(), iIndexOf);
            if (list.contains(strSubstring)) {
                String string = list.remove(0).toString();
                list.add(strSubstring);
                StringBuffer stringBuffer2 = new StringBuffer();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    stringBuffer2.append(it.next());
                    if (it.hasNext()) {
                        stringBuffer2.append("->");
                    }
                }
                throw new IllegalStateException(new StringBuffer("infinite loop in property interpolation of ").append(string).append(": ").append(stringBuffer2.toString()).toString());
            }
            list.add(strSubstring);
            Object property = getProperty(strSubstring);
            if (property != null) {
                stringBuffer.append(interpolateHelper(property.toString(), list));
                list.remove(list.size() - 1);
            } else {
                ExtendedProperties extendedProperties = this.defaults;
                if (extendedProperties == null || extendedProperties.getString(strSubstring, null) == null) {
                    stringBuffer.append(START_TOKEN).append(strSubstring).append(END_TOKEN);
                } else {
                    stringBuffer.append(this.defaults.getString(strSubstring));
                }
            }
            length = iIndexOf;
        }
        stringBuffer.append(str.substring(length + END_TOKEN.length(), str.length()));
        return stringBuffer.toString();
    }

    private static String escape(String str) {
        StringBuffer stringBuffer = new StringBuffer(str);
        int i = 0;
        while (i < stringBuffer.length()) {
            char cCharAt = stringBuffer.charAt(i);
            if (cCharAt == ',' || cCharAt == '\\') {
                stringBuffer.insert(i, '\\');
                i++;
            }
            i++;
        }
        return stringBuffer.toString();
    }

    private static String unescape(String str) {
        StringBuffer stringBuffer = new StringBuffer(str);
        int i = 0;
        while (i < stringBuffer.length() - 1) {
            char cCharAt = stringBuffer.charAt(i);
            int i2 = i + 1;
            char cCharAt2 = stringBuffer.charAt(i2);
            if (cCharAt == '\\' && cCharAt2 == '\\') {
                stringBuffer.deleteCharAt(i);
            }
            i = i2;
        }
        return stringBuffer.toString();
    }

    private static int countPreceding(String str, int i, char c) {
        int i2 = i - 1;
        int i3 = i2;
        while (i3 >= 0 && str.charAt(i3) == c) {
            i3--;
        }
        return i2 - i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean endsWithSlash(String str) {
        return str.endsWith("\\") && countPreceding(str, str.length() - 1, '\\') % 2 == 0;
    }

    static class PropertiesReader extends LineNumberReader {
        public PropertiesReader(Reader reader) {
            super(reader);
        }

        public String readProperty() throws IOException {
            StringBuffer stringBuffer = new StringBuffer();
            String line = readLine();
            while (line != null) {
                String strTrim = line.trim();
                if (strTrim.length() != 0 && strTrim.charAt(0) != '#') {
                    if (ExtendedProperties.endsWithSlash(strTrim)) {
                        stringBuffer.append(strTrim.substring(0, strTrim.length() - 1));
                    } else {
                        stringBuffer.append(strTrim);
                        return stringBuffer.toString();
                    }
                }
                line = readLine();
            }
            return null;
        }
    }

    static class PropertiesTokenizer extends StringTokenizer {
        static final String DELIMITER = ",";

        public PropertiesTokenizer(String str) {
            super(str, ",");
        }

        @Override // java.util.StringTokenizer
        public boolean hasMoreTokens() {
            return super.hasMoreTokens();
        }

        @Override // java.util.StringTokenizer
        public String nextToken() {
            StringBuffer stringBuffer = new StringBuffer();
            while (hasMoreTokens()) {
                String strNextToken = super.nextToken();
                if (ExtendedProperties.endsWithSlash(strNextToken)) {
                    stringBuffer.append(strNextToken.substring(0, strNextToken.length() - 1));
                    stringBuffer.append(",");
                } else {
                    stringBuffer.append(strNextToken);
                    break;
                }
            }
            return stringBuffer.toString().trim();
        }
    }

    public ExtendedProperties() {
        try {
            this.fileSeparator = (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.commons.collections.ExtendedProperties.1
                @Override // java.security.PrivilegedAction
                public Object run() {
                    return System.getProperty(SystemProperties.FILE_SEPARATOR);
                }
            });
        } catch (SecurityException unused) {
            this.fileSeparator = File.separator;
        }
        this.isInitialized = false;
        this.keysAsListed = new ArrayList();
    }

    public ExtendedProperties(String str) throws IOException {
        this(str, null);
    }

    public ExtendedProperties(String str, String str2) throws Throwable {
        try {
            this.fileSeparator = (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.apache.commons.collections.ExtendedProperties.1
                @Override // java.security.PrivilegedAction
                public Object run() {
                    return System.getProperty(SystemProperties.FILE_SEPARATOR);
                }
            });
        } catch (SecurityException unused) {
            this.fileSeparator = File.separator;
        }
        this.isInitialized = false;
        this.keysAsListed = new ArrayList();
        this.file = str;
        String absolutePath = new File(str).getAbsolutePath();
        this.basePath = absolutePath;
        this.basePath = absolutePath.substring(0, absolutePath.lastIndexOf(this.fileSeparator) + 1);
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                load(fileInputStream2);
                try {
                    fileInputStream2.close();
                } catch (IOException unused2) {
                }
                if (str2 != null) {
                    this.defaults = new ExtendedProperties(str2);
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String str) {
        include = str;
    }

    public void load(InputStream inputStream) throws IOException {
        load(inputStream, null);
    }

    public synchronized void load(InputStream inputStream, String str) throws IOException {
        PropertiesReader propertiesReader;
        File file;
        if (str != null) {
            try {
                try {
                    propertiesReader = new PropertiesReader(new InputStreamReader(inputStream, str));
                } catch (UnsupportedEncodingException unused) {
                    propertiesReader = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        } else {
            propertiesReader = null;
        }
        if (propertiesReader == null) {
            try {
                propertiesReader = new PropertiesReader(new InputStreamReader(inputStream, "8859_1"));
            } catch (UnsupportedEncodingException unused2) {
                propertiesReader = new PropertiesReader(new InputStreamReader(inputStream));
            }
        }
        while (true) {
            try {
                String property = propertiesReader.readProperty();
                if (property != null) {
                    int iIndexOf = property.indexOf(61);
                    if (iIndexOf > 0) {
                        String strTrim = property.substring(0, iIndexOf).trim();
                        String strTrim2 = property.substring(iIndexOf + 1).trim();
                        if (!"".equals(strTrim2)) {
                            if (getInclude() != null && strTrim.equalsIgnoreCase(getInclude())) {
                                if (strTrim2.startsWith(this.fileSeparator)) {
                                    file = new File(strTrim2);
                                } else {
                                    if (strTrim2.startsWith(new StringBuffer().append(".").append(this.fileSeparator).toString())) {
                                        strTrim2 = strTrim2.substring(2);
                                    }
                                    file = new File(new StringBuffer().append(this.basePath).append(strTrim2).toString());
                                }
                                if (file.exists() && file.canRead()) {
                                    load(new FileInputStream(file));
                                }
                            } else {
                                addProperty(strTrim, strTrim2);
                            }
                        }
                    }
                } else {
                    this.isInitialized = true;
                }
            } catch (Throwable th2) {
                this.isInitialized = true;
                throw th2;
            }
        }
    }

    public Object getProperty(String str) {
        ExtendedProperties extendedProperties;
        Object obj = get(str);
        return (obj != null || (extendedProperties = this.defaults) == null) ? obj : extendedProperties.get(str);
    }

    public void addProperty(String str, Object obj) {
        if (obj instanceof String) {
            String str2 = (String) obj;
            if (str2.indexOf(",") > 0) {
                PropertiesTokenizer propertiesTokenizer = new PropertiesTokenizer(str2);
                while (propertiesTokenizer.hasMoreTokens()) {
                    addPropertyInternal(str, unescape(propertiesTokenizer.nextToken()));
                }
            } else {
                addPropertyInternal(str, unescape(str2));
            }
        } else {
            addPropertyInternal(str, obj);
        }
        this.isInitialized = true;
    }

    private void addPropertyDirect(String str, Object obj) {
        if (!containsKey(str)) {
            this.keysAsListed.add(str);
        }
        put(str, obj);
    }

    private void addPropertyInternal(String str, Object obj) {
        Object obj2 = get(str);
        if (obj2 instanceof String) {
            Vector vector = new Vector(2);
            vector.add(obj2);
            vector.add(obj);
            put(str, vector);
            return;
        }
        if (obj2 instanceof List) {
            ((List) obj2).add(obj);
            return;
        }
        if (!containsKey(str)) {
            this.keysAsListed.add(str);
        }
        put(str, obj);
    }

    public void setProperty(String str, Object obj) {
        clearProperty(str);
        addProperty(str, obj);
    }

    public synchronized void save(OutputStream outputStream, String str) throws IOException {
        if (outputStream == null) {
            return;
        }
        PrintWriter printWriter = new PrintWriter(outputStream);
        if (str != null) {
            printWriter.println(str);
        }
        Enumeration enumerationKeys = keys();
        while (enumerationKeys.hasMoreElements()) {
            String str2 = (String) enumerationKeys.nextElement();
            Object obj = get(str2);
            if (obj != null) {
                if (obj instanceof String) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(str2);
                    stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
                    stringBuffer.append(escape((String) obj));
                    printWriter.println(stringBuffer.toString());
                } else if (obj instanceof List) {
                    for (String str3 : (List) obj) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append(str2);
                        stringBuffer2.append(ContainerUtils.KEY_VALUE_DELIMITER);
                        stringBuffer2.append(escape(str3));
                        printWriter.println(stringBuffer2.toString());
                    }
                }
            }
            printWriter.println();
            printWriter.flush();
        }
    }

    public void combine(ExtendedProperties extendedProperties) {
        Iterator keys = extendedProperties.getKeys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            setProperty(str, extendedProperties.get(str));
        }
    }

    public void clearProperty(String str) {
        if (containsKey(str)) {
            for (int i = 0; i < this.keysAsListed.size(); i++) {
                if (this.keysAsListed.get(i).equals(str)) {
                    this.keysAsListed.remove(i);
                    break;
                }
            }
            remove(str);
        }
    }

    public Iterator getKeys() {
        return this.keysAsListed.iterator();
    }

    public Iterator getKeys(String str) {
        Iterator keys = getKeys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasNext()) {
            Object next = keys.next();
            if ((next instanceof String) && ((String) next).startsWith(str)) {
                arrayList.add(next);
            }
        }
        return arrayList.iterator();
    }

    public ExtendedProperties subset(String str) {
        ExtendedProperties extendedProperties = new ExtendedProperties();
        Iterator keys = getKeys();
        boolean z = false;
        while (keys.hasNext()) {
            Object next = keys.next();
            if (next instanceof String) {
                String str2 = (String) next;
                if (str2.startsWith(str)) {
                    if (!z) {
                        z = true;
                    }
                    extendedProperties.addPropertyDirect(str2.length() == str.length() ? str : str2.substring(str.length() + 1), get(next));
                }
            }
        }
        if (z) {
            return extendedProperties;
        }
        return null;
    }

    public void display() {
        Iterator keys = getKeys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            System.out.println(new StringBuffer().append(str).append(" => ").append(get(str)).toString());
        }
    }

    public String getString(String str) {
        return getString(str, null);
    }

    public String getString(String str, String str2) {
        Object obj = get(str);
        if (obj instanceof String) {
            return interpolate((String) obj);
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            if (extendedProperties != null) {
                return interpolate(extendedProperties.getString(str, str2));
            }
            return interpolate(str2);
        }
        if (obj instanceof List) {
            return interpolate((String) ((List) obj).get(0));
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a String object").toString());
    }

    public Properties getProperties(String str) {
        return getProperties(str, new Properties());
    }

    public Properties getProperties(String str, Properties properties) {
        String[] stringArray = getStringArray(str);
        Properties properties2 = new Properties(properties);
        for (String str2 : stringArray) {
            int iIndexOf = str2.indexOf(61);
            if (iIndexOf > 0) {
                properties2.put(str2.substring(0, iIndexOf).trim(), str2.substring(iIndexOf + 1).trim());
            } else {
                throw new IllegalArgumentException(new StringBuffer(FormatHelper.QUOTE).append(str2).append("' does not contain an equals sign").toString());
            }
        }
        return properties2;
    }

    public String[] getStringArray(String str) {
        List vector;
        Object obj = get(str);
        if (obj instanceof String) {
            vector = new Vector(1);
            vector.add(obj);
        } else {
            if (!(obj instanceof List)) {
                if (obj == null) {
                    ExtendedProperties extendedProperties = this.defaults;
                    if (extendedProperties != null) {
                        return extendedProperties.getStringArray(str);
                    }
                    return new String[0];
                }
                throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a String/List object").toString());
            }
            vector = (List) obj;
        }
        int size = vector.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = (String) vector.get(i);
        }
        return strArr;
    }

    public Vector getVector(String str) {
        return getVector(str, null);
    }

    public Vector getVector(String str, Vector vector) {
        Object obj = get(str);
        if (obj instanceof List) {
            return new Vector((List) obj);
        }
        if (obj instanceof String) {
            Vector vector2 = new Vector(1);
            vector2.add(obj);
            put(str, vector2);
            return vector2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            if (extendedProperties != null) {
                return extendedProperties.getVector(str, vector);
            }
            return vector == null ? new Vector() : vector;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Vector object").toString());
    }

    public List getList(String str) {
        return getList(str, null);
    }

    public List getList(String str, List list) {
        Object obj = get(str);
        if (obj instanceof List) {
            return new ArrayList((List) obj);
        }
        if (obj instanceof String) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(obj);
            put(str, arrayList);
            return arrayList;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            if (extendedProperties != null) {
                return extendedProperties.getList(str, list);
            }
            return list == null ? new ArrayList() : list;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a List object").toString());
    }

    public boolean getBoolean(String str) {
        Boolean bool = getBoolean(str, (Boolean) null);
        if (bool != null) {
            return bool.booleanValue();
        }
        throw new NoSuchElementException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to an existing object").toString());
    }

    public boolean getBoolean(String str, boolean z) {
        return getBoolean(str, new Boolean(z)).booleanValue();
    }

    public Boolean getBoolean(String str, Boolean bool) {
        Object obj = get(str);
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            Boolean bool2 = new Boolean(testBoolean((String) obj));
            put(str, bool2);
            return bool2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            return extendedProperties != null ? extendedProperties.getBoolean(str, bool) : bool;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Boolean object").toString());
    }

    public String testBoolean(String str) {
        String lowerCase = str.toLowerCase();
        String str2 = "true";
        if (!lowerCase.equals("true") && !lowerCase.equals("on") && !lowerCase.equals("yes")) {
            str2 = "false";
            if (!lowerCase.equals("false") && !lowerCase.equals("off") && !lowerCase.equals("no")) {
                return null;
            }
        }
        return str2;
    }

    public byte getByte(String str) {
        Byte b = getByte(str, (Byte) null);
        if (b != null) {
            return b.byteValue();
        }
        throw new NoSuchElementException(new StringBuffer(FormatHelper.QUOTE).append(str).append(" doesn't map to an existing object").toString());
    }

    public byte getByte(String str, byte b) {
        return getByte(str, new Byte(b)).byteValue();
    }

    public Byte getByte(String str, Byte b) {
        Object obj = get(str);
        if (obj instanceof Byte) {
            return (Byte) obj;
        }
        if (obj instanceof String) {
            Byte b2 = new Byte((String) obj);
            put(str, b2);
            return b2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            return extendedProperties != null ? extendedProperties.getByte(str, b) : b;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Byte object").toString());
    }

    public short getShort(String str) {
        Short sh = getShort(str, (Short) null);
        if (sh != null) {
            return sh.shortValue();
        }
        throw new NoSuchElementException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to an existing object").toString());
    }

    public short getShort(String str, short s) {
        return getShort(str, new Short(s)).shortValue();
    }

    public Short getShort(String str, Short sh) {
        Object obj = get(str);
        if (obj instanceof Short) {
            return (Short) obj;
        }
        if (obj instanceof String) {
            Short sh2 = new Short((String) obj);
            put(str, sh2);
            return sh2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            return extendedProperties != null ? extendedProperties.getShort(str, sh) : sh;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Short object").toString());
    }

    public int getInt(String str) {
        return getInteger(str);
    }

    public int getInt(String str, int i) {
        return getInteger(str, i);
    }

    public int getInteger(String str) {
        Integer integer = getInteger(str, (Integer) null);
        if (integer != null) {
            return integer.intValue();
        }
        throw new NoSuchElementException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to an existing object").toString());
    }

    public int getInteger(String str, int i) {
        Integer integer = getInteger(str, (Integer) null);
        return integer == null ? i : integer.intValue();
    }

    public Integer getInteger(String str, Integer num) {
        Object obj = get(str);
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof String) {
            Integer num2 = new Integer((String) obj);
            put(str, num2);
            return num2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            return extendedProperties != null ? extendedProperties.getInteger(str, num) : num;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Integer object").toString());
    }

    public long getLong(String str) {
        Long l = getLong(str, (Long) null);
        if (l != null) {
            return l.longValue();
        }
        throw new NoSuchElementException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to an existing object").toString());
    }

    public long getLong(String str, long j) {
        return getLong(str, new Long(j)).longValue();
    }

    public Long getLong(String str, Long l) {
        Object obj = get(str);
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof String) {
            Long l2 = new Long((String) obj);
            put(str, l2);
            return l2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            return extendedProperties != null ? extendedProperties.getLong(str, l) : l;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Long object").toString());
    }

    public float getFloat(String str) {
        Float f = getFloat(str, (Float) null);
        if (f != null) {
            return f.floatValue();
        }
        throw new NoSuchElementException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to an existing object").toString());
    }

    public float getFloat(String str, float f) {
        return getFloat(str, new Float(f)).floatValue();
    }

    public Float getFloat(String str, Float f) {
        Object obj = get(str);
        if (obj instanceof Float) {
            return (Float) obj;
        }
        if (obj instanceof String) {
            Float f2 = new Float((String) obj);
            put(str, f2);
            return f2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            return extendedProperties != null ? extendedProperties.getFloat(str, f) : f;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Float object").toString());
    }

    public double getDouble(String str) {
        Double d = getDouble(str, (Double) null);
        if (d != null) {
            return d.doubleValue();
        }
        throw new NoSuchElementException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to an existing object").toString());
    }

    public double getDouble(String str, double d) {
        return getDouble(str, new Double(d)).doubleValue();
    }

    public Double getDouble(String str, Double d) {
        Object obj = get(str);
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof String) {
            Double d2 = new Double((String) obj);
            put(str, d2);
            return d2;
        }
        if (obj == null) {
            ExtendedProperties extendedProperties = this.defaults;
            return extendedProperties != null ? extendedProperties.getDouble(str, d) : d;
        }
        throw new ClassCastException(new StringBuffer(FormatHelper.QUOTE).append(str).append("' doesn't map to a Double object").toString());
    }

    public static ExtendedProperties convertProperties(Properties properties) {
        ExtendedProperties extendedProperties = new ExtendedProperties();
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        while (enumerationPropertyNames.hasMoreElements()) {
            String str = (String) enumerationPropertyNames.nextElement();
            extendedProperties.setProperty(str, properties.getProperty(str));
        }
        return extendedProperties;
    }
}
