package org.jetbrains.kotlin.load.java;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.impl.JavaPackageImpl;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade;

/* JADX INFO: compiled from: JavaClassFinderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u0018\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u00172\u0006\u0010\u0019\u001a\u00020\u0015H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaClassFinderImpl;", "Lorg/jetbrains/kotlin/load/java/AbstractJavaClassFinder;", "()V", "javaFacade", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinJavaPsiFacade;", "canComputeKnownClassNamesInPackage", "", "createJavaPackage", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaPackageImpl;", "psiPackage", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPackage;", "mayHaveAnnotations", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "request", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder$Request;", "findClasses", "", "findPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "knownClassNamesInPackage", "", "", "packageFqName", "setProjectInstance", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassFinderImpl extends AbstractJavaClassFinder {
    private KotlinJavaPsiFacade javaFacade;

    @Override // org.jetbrains.kotlin.load.java.AbstractJavaClassFinder
    @Inject
    public void setProjectInstance(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        super.setProjectInstance(project);
        KotlinJavaPsiFacade kotlinJavaPsiFacade = KotlinJavaPsiFacade.getInstance(project);
        Intrinsics.checkNotNullExpressionValue(kotlinJavaPsiFacade, "getInstance(...)");
        this.javaFacade = kotlinJavaPsiFacade;
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public JavaClass findClass(JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        KotlinJavaPsiFacade kotlinJavaPsiFacade = this.javaFacade;
        if (kotlinJavaPsiFacade == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javaFacade");
            kotlinJavaPsiFacade = null;
        }
        return kotlinJavaPsiFacade.findClass(request, getJavaSearchScope());
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public List<JavaClass> findClasses(JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        KotlinJavaPsiFacade kotlinJavaPsiFacade = this.javaFacade;
        if (kotlinJavaPsiFacade == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javaFacade");
            kotlinJavaPsiFacade = null;
        }
        List<JavaClass> listFindClasses = kotlinJavaPsiFacade.findClasses(request, getJavaSearchScope());
        Intrinsics.checkNotNullExpressionValue(listFindClasses, "findClasses(...)");
        return listFindClasses;
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public JavaPackage findPackage(FqName fqName, boolean mayHaveAnnotations) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        KotlinJavaPsiFacade kotlinJavaPsiFacade = this.javaFacade;
        if (kotlinJavaPsiFacade == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javaFacade");
            kotlinJavaPsiFacade = null;
        }
        PsiPackage psiPackageFindPackage = kotlinJavaPsiFacade.findPackage(fqName.asString(), getJavaSearchScope());
        return psiPackageFindPackage != null ? createJavaPackage(psiPackageFindPackage, mayHaveAnnotations) : null;
    }

    private final JavaPackageImpl createJavaPackage(PsiPackage psiPackage, boolean mayHaveAnnotations) {
        KotlinJavaPsiFacade kotlinJavaPsiFacade = this.javaFacade;
        if (kotlinJavaPsiFacade == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javaFacade");
            kotlinJavaPsiFacade = null;
        }
        Project project = kotlinJavaPsiFacade.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return new JavaPackageImpl(JavaElementSourceFactory.INSTANCE.getInstance(project).createPsiSource(psiPackage), getJavaSearchScope(), mayHaveAnnotations);
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public Set<String> knownClassNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        KotlinJavaPsiFacade kotlinJavaPsiFacade = this.javaFacade;
        if (kotlinJavaPsiFacade == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javaFacade");
            kotlinJavaPsiFacade = null;
        }
        return kotlinJavaPsiFacade.knownClassNamesInPackage(packageFqName, getJavaSearchScope());
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public boolean canComputeKnownClassNamesInPackage() {
        KotlinJavaPsiFacade kotlinJavaPsiFacade = this.javaFacade;
        if (kotlinJavaPsiFacade == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javaFacade");
            kotlinJavaPsiFacade = null;
        }
        Boolean boolCanComputeKnownClassNamesInPackage = kotlinJavaPsiFacade.canComputeKnownClassNamesInPackage();
        Intrinsics.checkNotNullExpressionValue(boolCanComputeKnownClassNamesInPackage, "canComputeKnownClassNamesInPackage(...)");
        return boolCanComputeKnownClassNamesInPackage.booleanValue();
    }
}
