package com.reandroid.dex.common;

import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.StringsUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes7.dex */
public abstract class Modifier implements SmaliFormat {
    private final int hash;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f234name;
    private final int value;

    public boolean equals(Object obj) {
        return obj == this;
    }

    public abstract boolean isSet(int i);

    public Modifier(int i, String str) {
        this.value = i;
        this.f234name = str;
        this.hash = str.hashCode() + (i * 31);
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.f234name;
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(StringUtil.SPACE);
    }

    public int hashCode() {
        return this.hash;
    }

    public String toString() {
        return getName();
    }

    public static void append(SmaliWriter smaliWriter, Modifier[] modifierArr) throws IOException {
        if (modifierArr == null) {
            return;
        }
        for (Modifier modifier : modifierArr) {
            if (modifier != null) {
                smaliWriter.append((CharSequence) modifier.getName());
                smaliWriter.append(StringUtil.SPACE);
            }
        }
    }

    public static boolean contains(Modifier[] modifierArr, Modifier modifier) {
        if (modifierArr != null && modifier != null) {
            for (Modifier modifier2 : modifierArr) {
                if (modifier == modifier2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String toString(Iterator<? extends Modifier> it) {
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next().getName());
            sb.append(StringUtil.SPACE);
        }
        return sb.toString();
    }

    public static String toString(Modifier[] modifierArr) {
        if (modifierArr == null) {
            return StringsUtil.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (Modifier modifier : modifierArr) {
            if (modifier != null) {
                sb.append(modifier.getName());
                sb.append(StringUtil.SPACE);
            }
        }
        return sb.toString();
    }

    public static int combineValues(Modifier[] modifierArr) {
        if (modifierArr == null) {
            return 0;
        }
        int value = 0;
        for (Modifier modifier : modifierArr) {
            if (modifier != null) {
                value |= modifier.getValue();
            }
        }
        return value;
    }
}
