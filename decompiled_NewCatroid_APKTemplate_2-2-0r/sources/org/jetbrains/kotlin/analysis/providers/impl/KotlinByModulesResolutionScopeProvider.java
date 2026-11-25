package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.project.structure.KtModule;
import org.jetbrains.kotlin.analysis.project.structure.KtModuleUtilsKt;
import org.jetbrains.kotlin.analysis.providers.KotlinResolutionScopeProvider;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: compiled from: KotlinByModulesResolutionScopeProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinByModulesResolutionScopeProvider;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinResolutionScopeProvider;", "()V", "getResolutionScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "module", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinByModulesResolutionScopeProvider extends KotlinResolutionScopeProvider {
    @Override // org.jetbrains.kotlin.analysis.providers.KotlinResolutionScopeProvider
    public GlobalSearchScope getResolutionScope(KtModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(module);
        CollectionsKt.addAll(listCreateListBuilder, KtModuleUtilsKt.allDirectDependencies(module));
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listBuild, 10));
        Iterator it = listBuild.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtModule) it.next()).getContentScope());
        }
        GlobalSearchScope globalSearchScopeUnion = GlobalSearchScope.union(arrayList);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeUnion, "union(...)");
        return globalSearchScopeUnion;
    }
}
