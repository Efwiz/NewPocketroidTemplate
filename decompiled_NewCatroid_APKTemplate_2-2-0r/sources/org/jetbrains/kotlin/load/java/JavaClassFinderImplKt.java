package org.jetbrains.kotlin.load.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: compiled from: JavaClassFinderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"createJavaClassFinder", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaClassFinderImplKt {
    public static final JavaClassFinder createJavaClassFinder(Project project, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        JavaClassFinderImpl javaClassFinderImpl = new JavaClassFinderImpl();
        javaClassFinderImpl.setProjectInstance(project);
        javaClassFinderImpl.setScope(scope);
        return javaClassFinderImpl;
    }
}
