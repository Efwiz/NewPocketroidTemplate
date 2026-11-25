package org.jetbrains.kotlin.javac;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: JavacWrapperKotlinResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/javac/JavacWrapperKotlinResolver;", "", "findField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "name", "", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "resolveSupertypes", "", "Lorg/jetbrains/kotlin/name/ClassId;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavacWrapperKotlinResolver {
    JavaField findField(KtClassOrObject classOrObject, String name2);

    JavaField findField(KtFile ktFile, String name2);

    List<ClassId> resolveSupertypes(KtClassOrObject classOrObject);
}
