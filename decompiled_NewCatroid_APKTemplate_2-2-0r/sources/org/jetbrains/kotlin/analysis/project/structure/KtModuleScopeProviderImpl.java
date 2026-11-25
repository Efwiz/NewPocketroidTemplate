package org.jetbrains.kotlin.analysis.project.structure;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: compiled from: KtModuleScopeProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtModuleScopeProviderImpl;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModuleScopeProvider;", "()V", "getModuleLibrariesScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "sourceModule", "Lorg/jetbrains/kotlin/analysis/project/structure/KtSourceModule;", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtModuleScopeProviderImpl extends KtModuleScopeProvider {
    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModuleScopeProvider
    public GlobalSearchScope getModuleLibrariesScope(KtSourceModule sourceModule) {
        Intrinsics.checkNotNullParameter(sourceModule, "sourceModule");
        Sequence sequenceFilter = SequencesKt.filter(KtModuleUtilsKt.allDirectDependencies(sourceModule), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleScopeProviderImpl$getModuleLibrariesScope$$inlined$allDirectDependenciesOfType$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof KtBinaryModule);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        List list = SequencesKt.toList(SequencesKt.map(sequenceFilter, new Function1<KtBinaryModule, GlobalSearchScope>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleScopeProviderImpl$getModuleLibrariesScope$scopes$1
            @Override // kotlin.jvm.functions.Function1
            public final GlobalSearchScope invoke(KtBinaryModule it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getContentScope();
            }
        }));
        if (list.isEmpty()) {
            GlobalSearchScope EMPTY_SCOPE = GlobalSearchScope.EMPTY_SCOPE;
            Intrinsics.checkNotNullExpressionValue(EMPTY_SCOPE, "EMPTY_SCOPE");
            return EMPTY_SCOPE;
        }
        GlobalSearchScope globalSearchScopeUnion = GlobalSearchScope.union(list);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeUnion, "union(...)");
        return globalSearchScopeUnion;
    }
}
