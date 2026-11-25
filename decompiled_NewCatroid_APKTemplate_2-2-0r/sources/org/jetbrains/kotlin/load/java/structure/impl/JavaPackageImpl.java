package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaPackageImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B%\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\"\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\r2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\n0 H\u0016R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R)\u0010\u0011\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0012\u0004\u0012\u00020\u000e0\u00128VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0010¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaPackageImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaElementImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPackage;", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "Lorg/jetbrains/kotlin/load/java/structure/MapBasedJavaAnnotationOwner;", "psiPackageSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "mayHaveAnnotations", "", "(Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;Lcom/intellij/psi/search/GlobalSearchScope;Z)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "fqName", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "subPackages", "getSubPackages", "getClasses", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaPackageImpl extends JavaElementImpl<PsiPackage> implements JavaPackage, MapBasedJavaAnnotationOwner {

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;
    private final boolean mayHaveAnnotations;
    private final GlobalSearchScope scope;

    public /* synthetic */ JavaPackageImpl(JavaElementPsiSource javaElementPsiSource, GlobalSearchScope globalSearchScope, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(javaElementPsiSource, globalSearchScope, (i & 4) != 0 ? true : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaPackageImpl(JavaElementPsiSource<PsiPackage> psiPackageSource, GlobalSearchScope scope, boolean z) {
        super(psiPackageSource);
        Intrinsics.checkNotNullParameter(psiPackageSource, "psiPackageSource");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.scope = scope;
        this.mayHaveAnnotations = z;
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public Collection<JavaClass> getClasses(Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        PsiClass[] classes = getPsi().getClasses(this.scope);
        Intrinsics.checkNotNullExpressionValue(classes, "getClasses(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiClass psiClass : classes) {
            String name2 = psiClass.getName();
            if (name2 != null) {
                Name nameIdentifier = Name.identifier(name2);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                if (nameFilter.invoke(nameIdentifier).booleanValue()) {
                    arrayList.add(psiClass);
                }
            }
        }
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        Intrinsics.checkNotNullExpressionValue(sourceFactory, "<get-sourceFactory>(...)");
        return JavaElementCollectionFromPsiArrayUtil.classes(arrayList, sourceFactory);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public Collection<JavaPackage> getSubPackages() {
        PsiPackage[] subPackages = getPsi().getSubPackages(this.scope);
        Intrinsics.checkNotNullExpressionValue(subPackages, "getSubPackages(...)");
        GlobalSearchScope globalSearchScope = this.scope;
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        Intrinsics.checkNotNullExpressionValue(sourceFactory, "<get-sourceFactory>(...)");
        return JavaElementCollectionFromPsiArrayUtil.packages(subPackages, globalSearchScope, sourceFactory);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPackage
    public FqName getFqName() {
        return new FqName(getPsi().getQualifiedName());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        if (this.mayHaveAnnotations) {
            PsiModifierList annotationList = getPsi().getAnnotationList();
            PsiAnnotation[] annotations = annotationList != null ? annotationList.getAnnotations() : null;
            if (annotations == null) {
                annotations = new PsiAnnotation[0];
            }
            JavaElementSourceFactory sourceFactory = getSourceFactory();
            Intrinsics.checkNotNullExpressionValue(sourceFactory, "<get-sourceFactory>(...)");
            return JavaElementCollectionFromPsiArrayUtil.annotations(annotations, sourceFactory);
        }
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return (Map) this.annotationsByFqName.getValue();
    }
}
