package org.jetbrains.kotlin.com.intellij.ide.plugins;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.util.XmlInterner;

/* JADX INFO: compiled from: XmlReader.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ReadModuleContext;", "", "interner", "Lorg/jetbrains/kotlin/com/intellij/util/XmlInterner;", "getInterner", "()Lcom/intellij/util/XmlInterner;", "isMissingIncludeIgnored", "", "()Z", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public interface ReadModuleContext {
    XmlInterner getInterner();

    boolean isMissingIncludeIgnored();
}
