package org.jetbrains.kotlin.analysis.providers.impl;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;

/* JADX INFO: compiled from: implUtils.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00030\u0007H\u0086\bø\u0001\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"KOTLIN_JAVA_ELEMENT_FINDER_CLASS_NAME", "", "forEachNonKotlinPsiElementFinder", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "action", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementFinder;", "analysis-api-providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImplUtilsKt {
    public static final String KOTLIN_JAVA_ELEMENT_FINDER_CLASS_NAME = "org.jetbrains.kotlin.asJava.finder.JavaElementFinder";

    public static final void forEachNonKotlinPsiElementFinder(Project project, Function1<? super PsiElementFinder, Unit> action) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(action, "action");
        for (PsiElementFinder psiElementFinder : PsiElementFinder.EP.getPoint(project).getExtensionList()) {
            if (!Intrinsics.areEqual(psiElementFinder.getClass().getName(), KOTLIN_JAVA_ELEMENT_FINDER_CLASS_NAME)) {
                Intrinsics.checkNotNull(psiElementFinder);
                action.invoke(psiElementFinder);
            }
        }
    }
}
