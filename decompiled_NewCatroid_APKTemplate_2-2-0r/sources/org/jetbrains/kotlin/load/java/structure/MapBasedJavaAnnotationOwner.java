package org.jetbrains.kotlin.load.java.structure;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\u0004H\u0016R \u0010\u0002\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/MapBasedJavaAnnotationOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotationsByFqName", "()Ljava/util/Map;", "isDeprecatedInJavaDoc", "", "()Z", "findAnnotation", "fqName", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MapBasedJavaAnnotationOwner extends JavaAnnotationOwner {
    Map<FqName, JavaAnnotation> getAnnotationsByFqName();

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    default boolean getIsDeprecatedInJavaDoc() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    default JavaAnnotation mo7251findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return getAnnotationsByFqName().get(fqName);
    }
}
