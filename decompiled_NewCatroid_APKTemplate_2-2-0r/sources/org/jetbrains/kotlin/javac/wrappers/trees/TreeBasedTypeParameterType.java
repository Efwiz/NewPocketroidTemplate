package org.jetbrains.kotlin.javac.wrappers.trees;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: treeBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedTypeParameterType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "classifierQualifiedName", "", "getClassifierQualifiedName", "()Ljava/lang/String;", "isDeprecatedInJavaDoc", "", "()Z", "isRaw", "presentableText", "getPresentableText", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeArguments", "()Ljava/util/List;", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedTypeParameterType implements JavaClassifierType {
    private final JavaTypeParameter classifier;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: isRaw */
    public boolean mo7271isRaw() {
        return false;
    }

    public TreeBasedTypeParameterType(JavaTypeParameter classifier) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        this.classifier = classifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaTypeParameter getClassifier() {
        return this.classifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments */
    public List<JavaType> mo7270getTypeArguments() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return UtilsKt.filterTypeAnnotations(getClassifier().mo7252getAnnotations());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getClassifierQualifiedName() {
        String strAsString = getClassifier().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getPresentableText() {
        return getClassifierQualifiedName();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.ListBasedJavaAnnotationOwner, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public JavaAnnotation mo7251findAnnotation(FqName fqName) {
        Object obj;
        Object next;
        ClassId classId;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = mo7252getAnnotations().iterator();
        do {
            obj = null;
            if (it.hasNext()) {
                next = it.next();
                classId = ((JavaAnnotation) next).getClassId();
            }
            return (JavaAnnotation) obj;
        } while (!Intrinsics.areEqual(classId != null ? classId.asSingleFqName() : null, fqName));
        obj = next;
        return (JavaAnnotation) obj;
    }
}
