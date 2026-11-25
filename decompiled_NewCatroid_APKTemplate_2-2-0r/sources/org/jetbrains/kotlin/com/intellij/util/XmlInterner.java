package org.jetbrains.kotlin.com.intellij.util;

import kotlin.Metadata;

/* JADX INFO: compiled from: xmlDom.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/XmlInterner;", "", "name", "", "value", "intellij.platform.util.xmlDom"}, k = 1, mv = {1, 5, 1})
public interface XmlInterner {
    String name(String value);

    String value(String name2, String value);
}
