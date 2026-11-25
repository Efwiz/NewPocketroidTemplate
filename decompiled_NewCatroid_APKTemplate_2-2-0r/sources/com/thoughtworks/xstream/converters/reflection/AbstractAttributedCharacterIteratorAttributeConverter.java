package com.thoughtworks.xstream.converters.reflection;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.core.util.Fields;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/reflection/AbstractAttributedCharacterIteratorAttributeConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class AbstractAttributedCharacterIteratorAttributeConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$java$text$AttributedCharacterIterator$Attribute;
    static /* synthetic */ Class class$java$util$Map;
    private static final Method getName;
    private static final Map instanceMaps = new HashMap();
    private transient Map attributeMap;
    private final Class type;

    static {
        Method declaredMethod = null;
        try {
            Class clsClass$ = class$java$text$AttributedCharacterIterator$Attribute;
            if (clsClass$ == null) {
                clsClass$ = class$("java.text.AttributedCharacterIterator$Attribute");
                class$java$text$AttributedCharacterIterator$Attribute = clsClass$;
            }
            declaredMethod = clsClass$.getDeclaredMethod("getName", null);
            if (!declaredMethod.isAccessible()) {
                declaredMethod.setAccessible(true);
            }
        } catch (NoSuchMethodException | SecurityException unused) {
        }
        getName = declaredMethod;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public AbstractAttributedCharacterIteratorAttributeConverter(Class cls) throws Throwable {
        Class clsClass$ = class$java$text$AttributedCharacterIterator$Attribute;
        if (clsClass$ == null) {
            clsClass$ = class$("java.text.AttributedCharacterIterator$Attribute");
            class$java$text$AttributedCharacterIterator$Attribute = clsClass$;
        }
        if (!clsClass$.isAssignableFrom(cls)) {
            StringBuffer stringBufferAppend = new StringBuffer().append(cls.getName()).append(" is not a ");
            Class clsClass$2 = class$java$text$AttributedCharacterIterator$Attribute;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.text.AttributedCharacterIterator$Attribute");
                class$java$text$AttributedCharacterIterator$Attribute = clsClass$2;
            }
            throw new IllegalArgumentException(stringBufferAppend.append(clsClass$2.getName()).toString());
        }
        this.type = cls;
        readResolve();
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls == this.type && !this.attributeMap.isEmpty();
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        return getName((AttributedCharacterIterator.Attribute) obj);
    }

    private String getName(AttributedCharacterIterator.Attribute attribute) {
        Method method = getName;
        Throwable th = null;
        if (method != null) {
            try {
                return (String) method.invoke(attribute, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                th = e;
            }
        }
        String string = attribute.toString();
        String name2 = attribute.getClass().getName();
        if (string.startsWith(name2)) {
            return string.substring(name2.length() + 1, string.length() - 1);
        }
        ConversionException conversionException = new ConversionException("Cannot find name of attribute", th);
        conversionException.add("attribute-type", name2);
        throw conversionException;
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        if (this.attributeMap.containsKey(str)) {
            return this.attributeMap.get(str);
        }
        ConversionException conversionException = new ConversionException("Cannot find attribute");
        conversionException.add("attribute-type", this.type.getName());
        conversionException.add("attribute-name", str);
        throw conversionException;
    }

    private Object readResolve() throws Throwable {
        boolean z;
        Map map = (Map) instanceMaps.get(this.type.getName());
        this.attributeMap = map;
        if (map == null) {
            this.attributeMap = new HashMap();
            Class cls = this.type;
            Class clsClass$ = class$java$util$Map;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_UTIL_MAP);
                class$java$util$Map = clsClass$;
            }
            Field fieldLocate = Fields.locate(cls, clsClass$, true);
            if (fieldLocate != null) {
                try {
                    Map map2 = (Map) Fields.read(fieldLocate, null);
                    if (map2 != null) {
                        Iterator it = map2.entrySet().iterator();
                        loop1: while (true) {
                            z = true;
                            while (true) {
                                if (!z || !it.hasNext()) {
                                    break loop1;
                                }
                                Map.Entry entry = (Map.Entry) it.next();
                                Class<?> cls2 = entry.getKey().getClass();
                                Class<?> clsClass$2 = class$java$lang$String;
                                if (clsClass$2 == null) {
                                    clsClass$2 = class$(CommonClassNames.JAVA_LANG_STRING);
                                    class$java$lang$String = clsClass$2;
                                }
                                if (cls2 != clsClass$2 || entry.getValue().getClass() != this.type) {
                                    z = false;
                                }
                            }
                        }
                        if (z) {
                            this.attributeMap.putAll(map2);
                        }
                    }
                } catch (ObjectAccessException unused) {
                }
            }
            if (this.attributeMap.isEmpty()) {
                try {
                    Field[] declaredFields = this.type.getDeclaredFields();
                    for (int i = 0; i < declaredFields.length; i++) {
                        if ((declaredFields[i].getType() == this.type) == Modifier.isStatic(declaredFields[i].getModifiers())) {
                            AttributedCharacterIterator.Attribute attribute = (AttributedCharacterIterator.Attribute) Fields.read(declaredFields[i], null);
                            this.attributeMap.put(toString(attribute), attribute);
                        }
                    }
                } catch (ObjectAccessException unused2) {
                    this.attributeMap.clear();
                } catch (NoClassDefFoundError unused3) {
                    this.attributeMap.clear();
                } catch (SecurityException unused4) {
                    this.attributeMap.clear();
                }
            }
            instanceMaps.put(this.type.getName(), this.attributeMap);
        }
        return this;
    }
}
