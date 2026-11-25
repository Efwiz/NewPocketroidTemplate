package org.jetbrains.kotlin.load.java;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: JavaModuleAnnotationsProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaModuleAnnotationsProvider;", "", "getAnnotationsForModuleOwnerOfClass", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaModuleAnnotationsProvider {
    List<JavaAnnotation> getAnnotationsForModuleOwnerOfClass(ClassId classId);
}
