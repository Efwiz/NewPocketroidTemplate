package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.tools.JavaFileObject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedPackage.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0013\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0096\u0002J\"\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020'0.H\u0016J\b\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u00020\u0004H\u0016R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR)\u0010\u000f\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\u0004\u0012\u00020\f0\u00108VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001d\u0010!\u001a\u0004\u0018\u00010\"8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u0015\u001a\u0004\b#\u0010$¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedPackage;", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "Lorg/jetbrains/kotlin/load/java/structure/MapBasedJavaAnnotationOwner;", "name", "", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "unit", "Lcom/sun/tools/javac/tree/JCTree$JCCompilationUnit;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lcom/sun/tools/javac/tree/JCTree$JCCompilationUnit;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "fqName", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getName", "()Ljava/lang/String;", "subPackages", "getSubPackages", "getUnit", "()Lcom/sun/tools/javac/tree/JCTree$JCCompilationUnit;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "virtualFile$delegate", Namer.EQUALS_METHOD_NAME, "", "other", "", "getClasses", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "hashCode", "", "toString", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedPackage implements JavaPackage, MapBasedJavaAnnotationOwner {

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;
    private final JavacWrapper javac;
    private final String name;
    private final JCTree.JCCompilationUnit unit;

    /* JADX INFO: renamed from: virtualFile$delegate, reason: from kotlin metadata */
    private final Lazy virtualFile;

    public TreeBasedPackage(String name2, JavacWrapper javac, JCTree.JCCompilationUnit unit) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(unit, "unit");
        this.name = name2;
        this.javac = javac;
        this.unit = unit;
        this.virtualFile = LazyKt.lazy(new Function0<VirtualFile>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedPackage$virtualFile$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFile invoke() {
                JavacWrapper javac2 = this.this$0.getJavac();
                JavaFileObject sourceFile = this.this$0.getUnit().getSourceFile();
                Intrinsics.checkNotNullExpressionValue(sourceFile, "getSourceFile(...)");
                return javac2.toVirtualFile(sourceFile);
            }
        });
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public /* bridge */ /* synthetic */ Collection getClasses(Function1 function1) {
        return getClasses((Function1<? super Name, Boolean>) function1);
    }

    public final JavacWrapper getJavac() {
        return this.javac;
    }

    public final String getName() {
        return this.name;
    }

    public final JCTree.JCCompilationUnit getUnit() {
        return this.unit;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public FqName getFqName() {
        return new FqName(this.name);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public Collection<JavaPackage> getSubPackages() {
        return this.javac.findSubPackages(getFqName());
    }

    public final VirtualFile getVirtualFile() {
        return (VirtualFile) this.virtualFile.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public List<JavaClass> getClasses(Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        List<JavaClass> listFindClassesFromPackage = this.javac.findClassesFromPackage(getFqName());
        ArrayList arrayList = new ArrayList();
        for (Object obj : listFindClassesFromPackage) {
            FqName fqName = ((JavaClass) obj).getFqName();
            Intrinsics.checkNotNull(fqName);
            Name nameShortName = fqName.shortName();
            Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
            if (nameFilter.invoke(nameShortName).booleanValue()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        List<JCTree.JCAnnotation> packageAnnotationsFromSources = this.javac.getPackageAnnotationsFromSources(getFqName());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(packageAnnotationsFromSources, 10));
        Iterator<T> it = packageAnnotationsFromSources.iterator();
        while (it.hasNext()) {
            arrayList.add(new TreeBasedAnnotation((JCTree.JCAnnotation) it.next(), this.unit, this.javac, this));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return (Map) this.annotationsByFqName.getValue();
    }

    public boolean equals(Object other) {
        TreeBasedPackage treeBasedPackage = other instanceof TreeBasedPackage ? (TreeBasedPackage) other : null;
        return Intrinsics.areEqual(treeBasedPackage != null ? treeBasedPackage.name : null, this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }
}
