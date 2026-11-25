package org.jetbrains.kotlin.com.fasterxml.aalto.impl;

import java.util.HashMap;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CommonConfig {
    static final HashMap<String, Integer> sStdProperties;
    protected int _flagMods;
    protected int _flags;

    static {
        HashMap<String, Integer> map = new HashMap<>(16);
        sStdProperties = map;
        map.put("org.jetbrains.kotlin.org.codehaus.stax2.implName", 1);
        map.put("org.jetbrains.kotlin.org.codehaus.stax2.implVersion", 2);
        map.put("org.jetbrains.kotlin.org.codehaus.stax2.supportsXml11", 3);
        map.put("org.jetbrains.kotlin.org.codehaus.stax2.supportXmlId", 4);
        map.put("http://java.sun.com/xml/stream/properties/implementation-name", 1);
    }

    protected CommonConfig(int i, int i2) {
        this._flags = i;
        this._flagMods = i2;
    }

    public Object getProperty(String str, boolean z) {
        Integer num = sStdProperties.get(str);
        if (num != null) {
            int iIntValue = num.intValue();
            if (iIntValue == 1) {
                return "aalto";
            }
            if (iIntValue == 2) {
                return "0.9";
            }
            if (iIntValue == 3) {
                return Boolean.FALSE;
            }
            if (iIntValue == 4) {
                return Boolean.FALSE;
            }
        }
        if (z) {
            throw new IllegalArgumentException("Unrecognized property '" + str + FormatHelper.QUOTE);
        }
        return null;
    }

    public boolean setProperty(String str, Object obj) {
        if (sStdProperties.get(str) != null) {
            return false;
        }
        throw new IllegalArgumentException("Unrecognized property '" + str + FormatHelper.QUOTE);
    }

    protected final boolean hasFlag(int i) {
        return (i & this._flags) != 0;
    }

    protected final void setFlag(int i, boolean z) {
        if (z) {
            this._flags |= i;
        } else {
            this._flags &= ~i;
        }
        this._flagMods = i | this._flagMods;
    }
}
