package org.jetbrains.kotlin.javac.wrappers.symbols;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: SymbolBasedClassifier.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u00042\u00020\u0005B\u0015\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifier;", ExifInterface.GPS_DIRECTION_TRUE, "Ljavax/lang/model/element/Element;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/Element;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "isDeprecatedInJavaDoc", "", "()Z", "isFromSource", "findAnnotation", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotation;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SymbolBasedClassifier<T extends Element> extends SymbolBasedElement<T> implements JavaAnnotationOwner, JavaClassifier {
    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedClassifier(T element, JavacWrapper javac) {
        super(element, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        List annotationMirrors = getElement().getAnnotationMirrors();
        Intrinsics.checkNotNullExpressionValue(annotationMirrors, "getAnnotationMirrors(...)");
        List<AnnotationMirror> list = annotationMirrors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (AnnotationMirror annotationMirror : list) {
            Intrinsics.checkNotNull(annotationMirror);
            arrayList.add(new SymbolBasedAnnotation(annotationMirror, getJavac()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public SymbolBasedAnnotation mo7251findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return UtilsKt.findAnnotation(getElement(), fqName, getJavac());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getJavac().isDeprecated(getElement());
    }
}
