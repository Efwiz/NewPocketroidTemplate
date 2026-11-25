package org.jetbrains.kotlin.load.java.structure.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.load.java.structure.JavaClass;

/* JADX INFO: compiled from: VirtualFileBoundJavaClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/VirtualFileBoundJavaClass;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "isFromSourceCodeInScope", "", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface VirtualFileBoundJavaClass extends JavaClass {
    VirtualFile getVirtualFile();

    boolean isFromSourceCodeInScope(SearchScope scope);
}
