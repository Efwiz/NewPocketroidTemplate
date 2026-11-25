package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.core.Caching;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/OuterClassMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class OuterClassMapper extends MapperWrapper implements Caching {
    private static final String[] EMPTY_NAMES = new String[0];
    static /* synthetic */ Class class$java$lang$Object;
    private final String alias;
    private final Map innerFields;

    public OuterClassMapper(Mapper mapper) {
        this(mapper, "outer-class");
    }

    public OuterClassMapper(Mapper mapper, String str) throws Throwable {
        super(mapper);
        this.alias = str;
        Map mapSynchronizedMap = Collections.synchronizedMap(new HashMap());
        this.innerFields = mapSynchronizedMap;
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$;
        }
        mapSynchronizedMap.put(clsClass$.getName(), EMPTY_NAMES);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String serializedMember(Class cls, String str) {
        if (str.startsWith("this$")) {
            String[] innerFieldNames = getInnerFieldNames(cls);
            int i = 0;
            while (i < innerFieldNames.length) {
                if (innerFieldNames[i].equals(str)) {
                    return i == 0 ? this.alias : new StringBuffer().append(this.alias).append('-').append(i).toString();
                }
                i++;
            }
        }
        return super.serializedMember(cls, str);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String realMember(Class cls, String str) {
        int iIntValue;
        if (str.startsWith(this.alias)) {
            int length = this.alias.length();
            if (length == str.length()) {
                iIntValue = 0;
            } else {
                int i = length + 1;
                iIntValue = (str.length() <= i || str.charAt(length) != '-') ? -1 : Integer.valueOf(str.substring(i)).intValue();
            }
            if (iIntValue >= 0) {
                String[] innerFieldNames = getInnerFieldNames(cls);
                if (iIntValue < innerFieldNames.length) {
                    return innerFieldNames[iIntValue];
                }
            }
        }
        return super.realMember(cls, str);
    }

    private String[] getInnerFieldNames(Class cls) {
        String[] innerFieldNames = (String[]) this.innerFields.get(cls.getName());
        if (innerFieldNames == null) {
            innerFieldNames = getInnerFieldNames(cls.getSuperclass());
            for (Field field : cls.getDeclaredFields()) {
                if (field.getName().startsWith("this$")) {
                    int length = innerFieldNames.length;
                    String[] strArr = new String[length + 1];
                    System.arraycopy(innerFieldNames, 0, strArr, 0, innerFieldNames.length);
                    strArr[length] = field.getName();
                    innerFieldNames = strArr;
                }
            }
            this.innerFields.put(cls.getName(), innerFieldNames);
        }
        return innerFieldNames;
    }

    @Override // com.thoughtworks.xstream.core.Caching
    public void flushCache() throws Throwable {
        Set setKeySet = this.innerFields.keySet();
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$;
        }
        setKeySet.retainAll(Collections.singletonList(clsClass$.getName()));
    }
}
