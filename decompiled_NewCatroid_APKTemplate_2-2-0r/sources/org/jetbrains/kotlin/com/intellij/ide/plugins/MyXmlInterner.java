package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.XmlInterner;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

/* JADX INFO: compiled from: DescriptorListLoadingContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0002R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/MyXmlInterner;", "Lorg/jetbrains/kotlin/com/intellij/util/XmlInterner;", "()V", "strings", "Lorg/jetbrains/kotlin/it/unimi/dsi/fastutil/objects/ObjectOpenHashSet;", "", "getStrings$annotations", "visitedFiles", "Ljava/util/ArrayList;", "name", "value", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
final class MyXmlInterner implements XmlInterner {
    private final ObjectOpenHashSet<String> strings;
    public final ArrayList<String> visitedFiles;

    public MyXmlInterner() {
        ObjectOpenHashSet<String> objectOpenHashSet = new ObjectOpenHashSet<>(256);
        this.strings = objectOpenHashSet;
        this.visitedFiles = new ArrayList<>(3);
        objectOpenHashSet.addAll(DescriptorListLoadingContextKt.CLASS_NAMES);
        objectOpenHashSet.addAll(DescriptorListLoadingContextKt.EXTRA_STRINGS);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.XmlInterner
    public String name(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        String strAddOrGet = this.strings.addOrGet(value);
        Intrinsics.checkNotNullExpressionValue(strAddOrGet, "strings.addOrGet(value)");
        return strAddOrGet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.XmlInterner
    public String value(String name2, String value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        if (value.length() > 64 || DescriptorListLoadingContextKt.CLASS_NAMES.contains(name2)) {
            return value;
        }
        String strAddOrGet = this.strings.addOrGet(value);
        Intrinsics.checkNotNullExpressionValue(strAddOrGet, "strings.addOrGet(value)");
        return strAddOrGet;
    }
}
