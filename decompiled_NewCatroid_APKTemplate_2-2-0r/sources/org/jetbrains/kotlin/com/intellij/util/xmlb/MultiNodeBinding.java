package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;

/* JADX INFO: loaded from: classes3.dex */
interface MultiNodeBinding extends Binding {
    Object deserializeList2(Object obj, List<XmlElement> list);

    boolean isMulti();
}
