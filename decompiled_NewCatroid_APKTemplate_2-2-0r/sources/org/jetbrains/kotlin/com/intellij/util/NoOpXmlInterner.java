package org.jetbrains.kotlin.com.intellij.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: xmlDom.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/NoOpXmlInterner;", "Lorg/jetbrains/kotlin/com/intellij/util/XmlInterner;", "()V", "name", "", "value", "intellij.platform.util.xmlDom"}, k = 1, mv = {1, 5, 1})
public final class NoOpXmlInterner implements XmlInterner {
    public static final NoOpXmlInterner INSTANCE = new NoOpXmlInterner();

    @Override // org.jetbrains.kotlin.com.intellij.util.XmlInterner
    public String name(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.XmlInterner
    public String value(String name2, String value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        return value;
    }

    private NoOpXmlInterner() {
    }
}
