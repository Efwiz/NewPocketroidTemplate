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
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SymbolBasedMember.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020$H\u0016R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014R\u0014\u0010\u0017\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014R\u0014\u0010\u0018\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0014R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedMember;", ExifInterface.GPS_DIRECTION_TRUE, "Ljavax/lang/model/element/Element;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/Element;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "getContainingClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "isAbstract", "", "()Z", "isDeprecatedInJavaDoc", "isFinal", "isFromSource", "isStatic", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "findAnnotation", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotation;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SymbolBasedMember<T extends Element> extends SymbolBasedElement<T> implements JavaMember {
    private final JavaClass containingClass;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    public JavaClass getContainingClass() {
        return this.containingClass;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedMember(T element, JavaClass containingClass, JavacWrapper javac) {
        super(element, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.containingClass = containingClass;
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

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        return UtilsKt.getVisibility(getElement());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(getElement().getSimpleName().toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getJavac().isDeprecated(getElement());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        return UtilsKt.isAbstract(getElement());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        return UtilsKt.isStatic(getElement());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        return UtilsKt.isFinal(getElement());
    }
}
