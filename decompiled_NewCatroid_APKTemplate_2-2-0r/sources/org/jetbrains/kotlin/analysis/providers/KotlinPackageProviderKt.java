package org.jetbrains.kotlin.analysis.providers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: compiled from: KotlinPackageProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"createPackageProvider", "Lorg/jetbrains/kotlin/analysis/providers/KotlinPackageProvider;", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "analysis-api-providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinPackageProviderKt {
    public static final KotlinPackageProvider createPackageProvider(Project project, GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        return ((KotlinPackageProviderFactory) project.getService(KotlinPackageProviderFactory.class)).createPackageProvider(searchScope);
    }
}
