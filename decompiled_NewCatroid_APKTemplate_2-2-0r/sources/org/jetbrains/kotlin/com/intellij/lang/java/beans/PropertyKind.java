package org.jetbrains.kotlin.com.intellij.lang.java.beans;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: loaded from: classes3.dex */
public enum PropertyKind {
    GETTER("get"),
    BOOLEAN_GETTER(LanguageCodeUtil.IS),
    SETTER(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);

    public final String prefix;

    PropertyKind(String str) {
        this.prefix = str;
    }
}
