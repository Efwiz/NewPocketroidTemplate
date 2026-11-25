package org.jetbrains.kotlin.javac.resolve;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: KotlinClassifiersCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\bR\u0014\u0010\u000e\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/MockKotlinClassifierType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;)V", "annotations", "", "getAnnotations", "()Ljava/lang/Void;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "classifierQualifiedName", "getClassifierQualifiedName", "isDeprecatedInJavaDoc", "isRaw", "presentableText", "getPresentableText", "typeArguments", "getTypeArguments", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MockKotlinClassifierType implements JavaClassifierType {
    private final JavaClassifier classifier;

    public MockKotlinClassifierType(JavaClassifier classifier) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        this.classifier = classifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.ListBasedJavaAnnotationOwner, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public /* bridge */ /* synthetic */ JavaAnnotation mo7251findAnnotation(FqName fqName) {
        return (JavaAnnotation) findAnnotation(fqName);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public /* bridge */ /* synthetic */ Collection mo7252getAnnotations() {
        return (Collection) getAnnotations();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaClassifier getClassifier() {
        return this.classifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public /* bridge */ /* synthetic */ String getClassifierQualifiedName() {
        return (String) m7268getClassifierQualifiedName();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public /* bridge */ /* synthetic */ String getPresentableText() {
        return (String) m7269getPresentableText();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ List mo7270getTypeArguments() {
        return (List) getTypeArguments();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public /* bridge */ /* synthetic */ boolean getIsDeprecatedInJavaDoc() {
        return ((Boolean) isDeprecatedInJavaDoc()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: isRaw, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7271isRaw() {
        return ((Boolean) isRaw()).booleanValue();
    }

    public Void getTypeArguments() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isRaw() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getAnnotations() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    /* JADX INFO: renamed from: getClassifierQualifiedName, reason: collision with other method in class */
    public Void m7268getClassifierQualifiedName() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    /* JADX INFO: renamed from: getPresentableText, reason: collision with other method in class */
    public Void m7269getPresentableText() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isDeprecatedInJavaDoc() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }
}
