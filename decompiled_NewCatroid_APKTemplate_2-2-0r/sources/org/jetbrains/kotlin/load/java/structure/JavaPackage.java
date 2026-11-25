package org.jetbrains.kotlin.load.java.structure;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\b2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00000\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "subPackages", "", "getSubPackages", "()Ljava/util/Collection;", "getClasses", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaPackage extends JavaAnnotationOwner, JavaElement {
    Collection<JavaClass> getClasses(Function1<? super Name, Boolean> nameFilter);

    FqName getFqName();

    Collection<JavaPackage> getSubPackages();
}
