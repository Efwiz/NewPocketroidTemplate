package org.jetbrains.kotlin.javac.wrappers.trees;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: TreeBasedClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\"B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/EnumSupertype;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "classifier", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "classifierQualifiedName", "", "getClassifierQualifiedName", "()Ljava/lang/String;", "isDeprecatedInJavaDoc", "", "()Z", "isRaw", "presentableText", "getPresentableText", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeArguments", "()Ljava/util/List;", "findAnnotation", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "TypeArgument", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class EnumSupertype implements JavaClassifierType {
    private final JavaClass javaClass;
    private final JavacWrapper javac;

    public Void findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return null;
    }

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

    public EnumSupertype(JavaClass javaClass, JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.javaClass = javaClass;
        this.javac = javac;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.ListBasedJavaAnnotationOwner, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public /* bridge */ /* synthetic */ JavaAnnotation mo7251findAnnotation(FqName fqName) {
        return (JavaAnnotation) findAnnotation(fqName);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaClass getClassifier() {
        return this.javac.getJAVA_LANG_ENUM();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments */
    public List<JavaType> mo7270getTypeArguments() {
        return CollectionsKt.listOf(new TypeArgument());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getClassifierQualifiedName() {
        FqName fqName;
        JavaClass classifier = getClassifier();
        String strAsString = (classifier == null || (fqName = classifier.getFqName()) == null) ? null : fqName.asString();
        return strAsString == null ? "" : strAsString;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getPresentableText() {
        return getClassifierQualifiedName();
    }

    /* JADX INFO: compiled from: TreeBasedClass.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/EnumSupertype$TypeArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "(Lorg/jetbrains/kotlin/javac/wrappers/trees/EnumSupertype;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "classifierQualifiedName", "", "getClassifierQualifiedName", "()Ljava/lang/String;", "isDeprecatedInJavaDoc", "", "()Z", "isRaw", "presentableText", "getPresentableText", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeArguments", "()Ljava/util/List;", "findAnnotation", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class TypeArgument implements JavaClassifierType {
        public Void findAnnotation(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return null;
        }

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

        public TypeArgument() {
        }

        @Override // org.jetbrains.kotlin.load.java.structure.ListBasedJavaAnnotationOwner, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
        /* JADX INFO: renamed from: findAnnotation */
        public /* bridge */ /* synthetic */ JavaAnnotation mo7251findAnnotation(FqName fqName) {
            return (JavaAnnotation) findAnnotation(fqName);
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
        public JavaClassifier getClassifier() {
            return EnumSupertype.this.javaClass;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
        /* JADX INFO: renamed from: getTypeArguments */
        public List<JavaType> mo7270getTypeArguments() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
        /* JADX INFO: renamed from: getAnnotations */
        public Collection<JavaAnnotation> mo7252getAnnotations() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
        public String getClassifierQualifiedName() {
            FqName fqName = EnumSupertype.this.javaClass.getFqName();
            Intrinsics.checkNotNull(fqName);
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }

        @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
        public String getPresentableText() {
            return getClassifierQualifiedName();
        }
    }
}
