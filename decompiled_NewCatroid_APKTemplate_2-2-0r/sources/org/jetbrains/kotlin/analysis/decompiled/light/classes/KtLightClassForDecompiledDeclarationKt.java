package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinModificationTrackerFactoryKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;

/* JADX INFO: compiled from: KtLightClassForDecompiledDeclaration.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a5\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0001*\u0002H\u00022\u000e\b\u0004\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0005H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"cachedValueWithLibraryTracker", ExifInterface.GPS_DIRECTION_TRUE, "R", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "computer", "Lkotlin/Function0;", "(Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "light-classes-for-decompiled"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtLightClassForDecompiledDeclarationKt {
    public static final <R extends PsiElement, T> T cachedValueWithLibraryTracker(final R r, final Function0<? extends T> computer) {
        Intrinsics.checkNotNullParameter(r, "<this>");
        Intrinsics.checkNotNullParameter(computer, "computer");
        return (T) CachedValuesManager.getCachedValue((PsiElement) r, new CachedValueProvider() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclarationKt.cachedValueWithLibraryTracker.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result<T> compute() {
                T tInvoke = computer.invoke();
                Project project = r.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return CachedValueProvider.Result.createSingleDependency(tInvoke, KotlinModificationTrackerFactoryKt.createAllLibrariesModificationTracker(project));
            }
        });
    }
}
