package org.jetbrains.kotlin.resolve.jvm;

import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: KotlinCliJavaFileManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/file/impl/JavaFileManager;", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "request", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder$Request;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "knownClassNamesInPackage", "", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinCliJavaFileManager extends JavaFileManager {
    JavaClass findClass(JavaClassFinder.Request request, GlobalSearchScope searchScope);

    Set<String> knownClassNamesInPackage(FqName packageFqName);
}
