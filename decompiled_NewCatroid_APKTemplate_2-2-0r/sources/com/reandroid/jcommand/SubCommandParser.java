package com.reandroid.jcommand;

import com.reandroid.jcommand.annotations.ChoiceArg;
import com.reandroid.jcommand.annotations.LastArgs;
import com.reandroid.jcommand.annotations.OptionArg;
import com.reandroid.jcommand.exceptions.DuplicateOptionException;
import com.reandroid.jcommand.exceptions.MissingValueException;
import com.reandroid.jcommand.exceptions.UnknownOptionException;
import com.reandroid.jcommand.utils.CommandUtil;
import com.reandroid.jcommand.utils.ReflectionUtil;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes11.dex */
public class SubCommandParser {
    private final String[] mArgs;
    private int mIndex;
    private Field mLastArgs;
    private Object mObj;
    private final Map<String, Field> optionFieldsMap = new HashMap();
    private final Map<String, Field> choiceOptionFieldsMap = new HashMap();
    private final Set<Field> mParsedFields = new HashSet();

    public SubCommandParser(String[] strArr) {
        this.mArgs = strArr;
    }

    public <T> T parse(Class<T> cls) {
        T t = (T) ReflectionUtil.createNew(cls);
        parse(t);
        return t;
    }

    public void parse(Object obj) {
        this.mObj = obj;
        mapFields(obj.getClass());
        parseArgs();
    }

    private void parseArgs() {
        String[] strArr = this.mArgs;
        int length = strArr.length;
        int i = 0;
        while (true) {
            this.mIndex = i;
            int i2 = this.mIndex;
            if (i2 >= length) {
                return;
            }
            String str = strArr[i2];
            Field field = this.optionFieldsMap.get(str);
            if (field == null && (field = this.choiceOptionFieldsMap.get(str)) != null) {
                parseChoiceOptionField(field);
            } else if (field == null) {
                if (this.mLastArgs == null) {
                    throw new UnknownOptionException(str);
                }
                parseLastArgs();
            } else {
                parseOptionField(field);
            }
            i = this.mIndex + 1;
        }
    }

    private void addParsed(Field field, String str) {
        if (this.mParsedFields.contains(field)) {
            if (!ReflectionUtil.isInstanceClass(field.getType(), Collection.class)) {
                throw new DuplicateOptionException(str);
            }
        } else {
            this.mParsedFields.add(field);
        }
    }

    private void parseOptionField(Field field) {
        OptionArg optionArg = (OptionArg) field.getAnnotation(OptionArg.class);
        addParsed(field, optionArg.name());
        field.setAccessible(true);
        if (optionArg.flag()) {
            ReflectionUtil.setBoolean(this.mObj, field, true);
            return;
        }
        int i = this.mIndex + 1;
        this.mIndex = i;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            throw new MissingValueException(this.mArgs[this.mIndex - 1]);
        }
        String str = strArr[i];
        Class<?> type = field.getType();
        Object obj = this.mObj;
        if (ReflectionUtil.isInstanceClass(type, Collection.class)) {
            ReflectionUtil.setCollection(obj, field, str);
            return;
        }
        if (type == String.class) {
            ReflectionUtil.setObject(obj, field, str);
            return;
        }
        if (type == Integer.TYPE) {
            ReflectionUtil.setInt(obj, field, str);
            return;
        }
        if (type == Integer.class) {
            ReflectionUtil.setIntObject(obj, field, str);
            return;
        }
        if (type == Long.TYPE) {
            ReflectionUtil.setLong(obj, field, str);
            return;
        }
        if (type == Long.class) {
            ReflectionUtil.setLongObject(obj, field, str);
            return;
        }
        if (type == Double.TYPE) {
            ReflectionUtil.setDouble(obj, field, str);
            return;
        }
        if (type == Double.class) {
            ReflectionUtil.setDoubleObject(obj, field, str);
            return;
        }
        if (type == Boolean.TYPE) {
            ReflectionUtil.setBoolean(obj, field, str);
        } else if (type == Boolean.class) {
            ReflectionUtil.setBooleanObject(obj, field, str);
        } else {
            if (type == File.class) {
                ReflectionUtil.setFile(obj, field, str);
                return;
            }
            throw new RuntimeException("Unsupported field type: " + field);
        }
    }

    private void parseChoiceOptionField(Field field) {
        ChoiceArg choiceArg = (ChoiceArg) field.getAnnotation(ChoiceArg.class);
        addParsed(field, choiceArg.name());
        field.setAccessible(true);
        int i = this.mIndex + 1;
        this.mIndex = i;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            throw new MissingValueException(this.mArgs[this.mIndex - 1]);
        }
        String str = strArr[i];
        if (!CommandUtil.containsIgnoreCase(choiceArg.values(), str)) {
            throw new UnknownOptionException(str);
        }
        Class<?> type = field.getType();
        if (String.class.equals(type)) {
            ReflectionUtil.setObject(this.mObj, field, str);
        } else {
            if (type.isEnum()) {
                ReflectionUtil.setEnum(this.mObj, field, str);
                return;
            }
            throw new RuntimeException("Unsupported choice field type: " + field);
        }
    }

    private void parseLastArgs() {
        Field field = this.mLastArgs;
        field.setAccessible(true);
        try {
            Collection hashSet = (Collection) field.get(this.mObj);
            if (hashSet == null) {
                if (ReflectionUtil.isInstanceClass(field.getType(), List.class)) {
                    hashSet = new ArrayList();
                } else {
                    hashSet = new HashSet();
                }
                field.set(this.mObj, hashSet);
            }
            this.mIndex++;
            while (true) {
                int i = this.mIndex;
                String[] strArr = this.mArgs;
                if (i >= strArr.length) {
                    return;
                }
                hashSet.add(strArr[i]);
                this.mIndex++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void mapFields(Class<?> cls) {
        List<Field> listListInstanceFields = ReflectionUtil.listInstanceFields(cls);
        Map<String, Field> map = this.optionFieldsMap;
        Map<String, Field> map2 = this.choiceOptionFieldsMap;
        for (Field field : listListInstanceFields) {
            List<String> listListNames = ReflectionUtil.listNames(field);
            for (String str : listListNames) {
                Field field2 = getField(str);
                if (field2 != null) {
                    throw new RuntimeException("Duplicate fields: '" + field2 + "', '" + field + FormatHelper.QUOTE);
                }
                if (field.getAnnotation(OptionArg.class) != null) {
                    map.put(str, field);
                }
                if (field.getAnnotation(ChoiceArg.class) != null) {
                    map2.put(str, field);
                }
            }
            if (listListNames.isEmpty() && ((LastArgs) field.getAnnotation(LastArgs.class)) != null) {
                if (this.mLastArgs != null) {
                    throw new RuntimeException("Duplicate LastArgs: '" + this.mLastArgs + "', '" + field + FormatHelper.QUOTE);
                }
                this.mLastArgs = field;
            }
        }
    }

    private Field getField(String str) {
        Field field = this.optionFieldsMap.get(str);
        return field == null ? this.choiceOptionFieldsMap.get(str) : field;
    }

    public static <T> T parse(Class<T> cls, String[] strArr) {
        return (T) new SubCommandParser(strArr).parse((Class) cls);
    }

    public static void parse(Object obj, String[] strArr) {
        new SubCommandParser(strArr).parse(obj);
    }
}
