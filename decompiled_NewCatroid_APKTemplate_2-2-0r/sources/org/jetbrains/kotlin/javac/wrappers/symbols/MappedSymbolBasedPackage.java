package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.PackageElement;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MappedSymbolBasedPackage.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\"\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00072\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"0 H\u0016J\b\u0010#\u001a\u00020$H\u0016R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R)\u0010\u0011\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u000e0\u00128VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/MappedSymbolBasedPackage;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedElement;", "Ljavax/lang/model/element/PackageElement;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedPackage;", "originalFqName", "Lorg/jetbrains/kotlin/name/FqName;", "childrenPackages", "", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SimpleSymbolBasedPackage;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/List;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "fqName", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "subPackages", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "getSubPackages", "getClasses", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "toString", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MappedSymbolBasedPackage extends SymbolBasedElement<PackageElement> implements SymbolBasedPackage {

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;
    private final List<SimpleSymbolBasedPackage> childrenPackages;
    private final FqName originalFqName;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public /* bridge */ /* synthetic */ Collection getClasses(Function1 function1) {
        return getClasses((Function1<? super Name, Boolean>) function1);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MappedSymbolBasedPackage(FqName originalFqName, List<SimpleSymbolBasedPackage> childrenPackages, JavacWrapper javac) {
        super(((SimpleSymbolBasedPackage) CollectionsKt.first((List) childrenPackages)).getElement(), javac);
        Intrinsics.checkNotNullParameter(originalFqName, "originalFqName");
        Intrinsics.checkNotNullParameter(childrenPackages, "childrenPackages");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.originalFqName = originalFqName;
        this.childrenPackages = childrenPackages;
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public FqName getFqName() {
        return this.originalFqName;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public Collection<JavaPackage> getSubPackages() {
        List<SimpleSymbolBasedPackage> list = this.childrenPackages;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, getJavac().findSubPackages(((SimpleSymbolBasedPackage) it.next()).getFqName()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        List<SimpleSymbolBasedPackage> list = this.childrenPackages;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            List annotationMirrors = ((SimpleSymbolBasedPackage) it.next()).getElement().getAnnotationMirrors();
            Intrinsics.checkNotNullExpressionValue(annotationMirrors, "getAnnotationMirrors(...)");
            List<AnnotationMirror> list2 = annotationMirrors;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (AnnotationMirror annotationMirror : list2) {
                Intrinsics.checkNotNull(annotationMirror);
                arrayList2.add(new SymbolBasedAnnotation(annotationMirror, getJavac()));
            }
            arrayList.add(arrayList2);
        }
        return CollectionsKt.flatten(arrayList);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return (Map) this.annotationsByFqName.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public List<JavaClass> getClasses(Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedElement
    public String toString() {
        String string = this.originalFqName.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
