package org.jetbrains.kotlin.javac.wrappers.symbols;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: symbolBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 !*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u00032\u00020\u0004:\u0001!B\u0017\b\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014\u0082\u0001\u0004\"#$%¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", ExifInterface.GPS_DIRECTION_TRUE, "Ljavax/lang/model/type/TypeMirror;", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "typeMirror", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/type/TypeMirror;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "isDeprecatedInJavaDoc", "", "()Z", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getTypeMirror", "()Ljavax/lang/model/type/TypeMirror;", "Ljavax/lang/model/type/TypeMirror;", Namer.EQUALS_METHOD_NAME, "other", "", "findAnnotation", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotation;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "hashCode", "", "toString", "", "Companion", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedArrayType;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifierType;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedPrimitiveType;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedWildcardType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SymbolBasedType<T extends TypeMirror> implements JavaAnnotationOwner, JavaType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JavacWrapper javac;
    private final T typeMirror;

    public /* synthetic */ SymbolBasedType(TypeMirror typeMirror, JavacWrapper javacWrapper, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeMirror, javacWrapper);
    }

    private SymbolBasedType(T t, JavacWrapper javacWrapper) {
        this.typeMirror = t;
        this.javac = javacWrapper;
    }

    public final T getTypeMirror() {
        return this.typeMirror;
    }

    public final JavacWrapper getJavac() {
        return this.javac;
    }

    /* JADX INFO: compiled from: symbolBasedTypes.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\"\b\b\u0001\u0010\u0006*\u00020\u00052\u0006\u0010\u0007\u001a\u0002H\u00062\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", "Ljavax/lang/model/type/TypeMirror;", ExifInterface.GPS_DIRECTION_TRUE, "t", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/type/TypeMirror;Lorg/jetbrains/kotlin/javac/JavacWrapper;)Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <T extends TypeMirror> SymbolBasedType<TypeMirror> create(T t, JavacWrapper javac) {
            Intrinsics.checkNotNullParameter(t, "t");
            Intrinsics.checkNotNullParameter(javac, "javac");
            if (t.getKind().isPrimitive() || t.getKind() == TypeKind.VOID) {
                return new SymbolBasedPrimitiveType(t, javac);
            }
            if (t.getKind() == TypeKind.DECLARED || t.getKind() == TypeKind.TYPEVAR) {
                return new SymbolBasedClassifierType(t, javac);
            }
            if (t.getKind() == TypeKind.WILDCARD) {
                return new SymbolBasedWildcardType((WildcardType) t, javac);
            }
            if (t.getKind() == TypeKind.ARRAY) {
                return new SymbolBasedArrayType((ArrayType) t, javac);
            }
            throw new UnsupportedOperationException("Unsupported type: " + t);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        List annotationMirrors = this.typeMirror.getAnnotationMirrors();
        Intrinsics.checkNotNullExpressionValue(annotationMirrors, "getAnnotationMirrors(...)");
        List<AnnotationMirror> list = annotationMirrors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (AnnotationMirror annotationMirror : list) {
            Intrinsics.checkNotNull(annotationMirror);
            arrayList.add(new SymbolBasedAnnotation(annotationMirror, this.javac));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return this.javac.isDeprecated(this.typeMirror);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public SymbolBasedAnnotation mo7251findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return UtilsKt.findAnnotation(this.typeMirror, fqName, this.javac);
    }

    public boolean equals(Object other) {
        SymbolBasedType symbolBasedType = other instanceof SymbolBasedType ? (SymbolBasedType) other : null;
        return Intrinsics.areEqual(symbolBasedType != null ? symbolBasedType.typeMirror : null, this.typeMirror);
    }

    public int hashCode() {
        return this.typeMirror.hashCode();
    }

    public String toString() {
        return this.typeMirror.toString();
    }
}
