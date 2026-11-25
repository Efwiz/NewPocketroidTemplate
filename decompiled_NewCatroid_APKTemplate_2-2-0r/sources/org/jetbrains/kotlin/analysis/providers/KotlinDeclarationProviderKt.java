package org.jetbrains.kotlin.analysis.providers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.project.structure.KtModule;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: compiled from: KotlinDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t¨\u0006\n"}, d2 = {"createDeclarationProvider", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "module", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "mergeDeclarationProviders", "declarationProviders", "", "analysis-api-providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinDeclarationProviderKt {
    public static final KotlinDeclarationProvider createDeclarationProvider(Project project, GlobalSearchScope scope, KtModule ktModule) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return KotlinDeclarationProviderFactory.INSTANCE.getInstance(project).createDeclarationProvider(scope, ktModule);
    }

    public static final KotlinDeclarationProvider mergeDeclarationProviders(Project project, List<? extends KotlinDeclarationProvider> declarationProviders) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        Intrinsics.checkNotNullParameter(declarationProviders, "declarationProviders");
        return KotlinDeclarationProviderMerger.INSTANCE.getInstance(project).mergeDeclarationProviders(declarationProviders);
    }
}
