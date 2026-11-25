package org.jetbrains.kotlin.load.java.structure;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0014\u0010\u000e\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "arguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getArguments", "()Ljava/util/Collection;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "isFreshlySupportedTypeUseAnnotation", "", "()Z", "isIdeExternalAnnotation", "isResolvedTo", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "resolve", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaAnnotation extends JavaElement {
    Collection<JavaAnnotationArgument> getArguments();

    ClassId getClassId();

    default boolean isFreshlySupportedTypeUseAnnotation() {
        return false;
    }

    default boolean isIdeExternalAnnotation() {
        return false;
    }

    JavaClass resolve();

    default boolean isResolvedTo(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ClassId classId = getClassId();
        return Intrinsics.areEqual(classId != null ? classId.asSingleFqName() : null, fqName);
    }
}
