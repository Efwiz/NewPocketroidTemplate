package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.ListBasedJavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b \u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/JavaPlainType;", "Lorg/jetbrains/kotlin/load/java/structure/ListBasedJavaAnnotationOwner;", "Lorg/jetbrains/kotlin/load/java/structure/MutableJavaAnnotationOwner;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "isDeprecatedInJavaDoc", "", "()Z", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JavaPlainType implements ListBasedJavaAnnotationOwner, MutableJavaAnnotationOwner {
    private final Collection<JavaAnnotation> annotations = new SmartList();
    private final boolean isDeprecatedInJavaDoc;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc, reason: from getter */
    public boolean getIsDeprecatedInJavaDoc() {
        return this.isDeprecatedInJavaDoc;
    }
}
