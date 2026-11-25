package org.jetbrains.kotlin.javac.components;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.AbstractJavaClassFinder;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;

/* JADX INFO: compiled from: JavacBasedClassFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J(\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/javac/components/JavacBasedClassFinder;", "Lorg/jetbrains/kotlin/load/java/AbstractJavaClassFinder;", "()V", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "canComputeKnownClassNamesInPackage", "", "findClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "request", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder$Request;", "findClasses", "", "findPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "mayHaveAnnotations", "initialize", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "codeAnalyzer", "Lorg/jetbrains/kotlin/resolve/lazy/KotlinCodeAnalyzer;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "knownClassNamesInPackage", "", "", "packageFqName", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavacBasedClassFinder extends AbstractJavaClassFinder {
    private JavacWrapper javac;

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public boolean canComputeKnownClassNamesInPackage() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.AbstractJavaClassFinder
    public void initialize(BindingTrace trace, KotlinCodeAnalyzer codeAnalyzer, LanguageVersionSettings languageVersionSettings, JvmTarget jvmTarget) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(codeAnalyzer, "codeAnalyzer");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        this.javac = JavacWrapper.INSTANCE.getInstance(getProject());
        super.initialize(trace, codeAnalyzer, languageVersionSettings, jvmTarget);
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public JavaClass findClass(JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        JavacWrapper javacWrapper = this.javac;
        if (javacWrapper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javac");
            javacWrapper = null;
        }
        return javacWrapper.findClass(request.getClassId(), getJavaSearchScope());
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public List<JavaClass> findClasses(JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        return CollectionsKt.listOfNotNull(findClass(request));
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public JavaPackage findPackage(FqName fqName, boolean mayHaveAnnotations) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        JavacWrapper javacWrapper = this.javac;
        if (javacWrapper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javac");
            javacWrapper = null;
        }
        return javacWrapper.findPackage(fqName, getJavaSearchScope());
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassFinder
    public Set<String> knownClassNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        JavacWrapper javacWrapper = this.javac;
        if (javacWrapper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javac");
            javacWrapper = null;
        }
        return javacWrapper.knownClassNamesInPackage(packageFqName);
    }
}
