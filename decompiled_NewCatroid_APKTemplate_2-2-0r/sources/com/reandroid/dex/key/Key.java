package com.reandroid.dex.key;

import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.CollectionUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface Key extends Comparable<Object>, SmaliFormat {
    public static final String DALVIK_accessFlags = ObjectsUtil.of("accessFlags");
    public static final String DALVIK_name = ObjectsUtil.of("name");
    public static final String DALVIK_value = ObjectsUtil.of("value");

    default Key replaceKey(Key key, Key key2) {
        return this;
    }

    default boolean uses(Key key) {
        return equals(key) || CollectionUtil.contains(mentionedKeys(), key);
    }

    default TypeKey getDeclaring() {
        return TypeKey.NULL;
    }

    default Iterator<? extends Key> mentionedKeys() {
        throw new RuntimeException("Method 'mentionedKeys()' Not implemented for: " + getClass());
    }

    default boolean isPlatform() {
        return DexUtils.isPlatform(getDeclaring());
    }

    default void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) toString());
    }

    static Key parseBasic(String str) {
        if (StringsUtil.isEmpty(str)) {
            return null;
        }
        if (str.indexOf(34) == 0) {
            return StringKey.parseQuotedString(str);
        }
        if (str.indexOf(40) > 0) {
            return MethodKey.parse(str);
        }
        if (str.indexOf(58) > 0) {
            return FieldKey.parse(str);
        }
        return TypeKey.parse(str);
    }
}
