package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SimpleSymbolBasedPackage.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\"\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001eH\u0016J\b\u0010!\u001a\u00020\"H\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR)\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0004\u0012\u00020\n0\u000e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\f¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SimpleSymbolBasedPackage;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedElement;", "Ljavax/lang/model/element/PackageElement;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedPackage;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/PackageElement;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "fqName", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "subPackages", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "getSubPackages", "getClasses", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "toString", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleSymbolBasedPackage extends SymbolBasedElement<PackageElement> implements SymbolBasedPackage {

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public /* bridge */ /* synthetic */ Collection getClasses(Function1 function1) {
        return getClasses((Function1<? super Name, Boolean>) function1);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleSymbolBasedPackage(PackageElement element, JavacWrapper javac) {
        super((Element) element, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public FqName getFqName() {
        return new FqName(getElement().getQualifiedName().toString());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public Collection<JavaPackage> getSubPackages() {
        return getJavac().findSubPackages(getFqName());
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

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return (Map) this.annotationsByFqName.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public List<JavaClass> getClasses(Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        List<JavaClass> listFindClassesFromPackage = getJavac().findClassesFromPackage(getFqName());
        ArrayList arrayList = new ArrayList();
        for (Object obj : listFindClassesFromPackage) {
            if (nameFilter.invoke(((JavaClass) obj).getName()).booleanValue()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedElement
    public String toString() {
        return getElement().getQualifiedName().toString();
    }
}
