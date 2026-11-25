package com.reandroid.xml.base;

import com.reandroid.common.Namespace;
import com.reandroid.utils.StringsUtil;

/* JADX INFO: loaded from: classes11.dex */
public interface NamedNode {
    String getName();

    Namespace getNamespace();

    void setName(String str);

    void setNamespace(Namespace namespace);

    default String getName(boolean z) {
        String name2 = getName();
        if (!z) {
            return name2;
        }
        String prefix = getPrefix();
        return !StringsUtil.isEmpty(prefix) ? prefix + ":" + name2 : name2;
    }

    default String getPrefix() {
        Namespace namespace = getNamespace();
        if (namespace != null) {
            return namespace.getPrefix();
        }
        return null;
    }

    default String getUri() {
        Namespace namespace = getNamespace();
        if (namespace != null) {
            return namespace.getUri();
        }
        return null;
    }
}
