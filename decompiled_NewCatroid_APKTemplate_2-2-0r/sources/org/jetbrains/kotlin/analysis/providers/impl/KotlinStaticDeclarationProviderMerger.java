package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProviderKt;
import org.jetbrains.kotlin.analysis.providers.impl.util.SublistMerger;
import org.jetbrains.kotlin.analysis.providers.impl.util.SublistMergerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: compiled from: KotlinStaticDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationProviderMerger;", "Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinDeclarationProviderMergerBase;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "mergeToList", "", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "declarationProviders", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticDeclarationProviderMerger extends KotlinDeclarationProviderMergerBase {
    private final Project project;

    public KotlinStaticDeclarationProviderMerger(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.impl.KotlinDeclarationProviderMergerBase
    protected List<KotlinDeclarationProvider> mergeToList(List<? extends KotlinDeclarationProvider> declarationProviders) {
        Intrinsics.checkNotNullParameter(declarationProviders, "declarationProviders");
        return SublistMergerKt.mergeWith(declarationProviders, new Function1<SublistMerger<KotlinDeclarationProvider>, Unit>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderMerger$mergeToList$$inlined$mergeOnly$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SublistMerger<KotlinDeclarationProvider> sublistMerger) {
                invoke2(sublistMerger);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SublistMerger<KotlinDeclarationProvider> mergeWith) {
                Intrinsics.checkNotNullParameter(mergeWith, "$this$mergeWith");
                List<KotlinDeclarationProvider> remainingElements = mergeWith.getRemainingElements();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : remainingElements) {
                    if (obj instanceof KotlinStaticDeclarationProvider) {
                        arrayList.add(obj);
                    } else {
                        arrayList2.add(obj);
                    }
                }
                Pair pair = new Pair(arrayList, arrayList2);
                List list = (List) pair.component1();
                List<? extends KotlinDeclarationProvider> list2 = (List) pair.component2();
                List<KotlinDeclarationProvider> destination = mergeWith.getDestination();
                List list3 = list;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator it = list3.iterator();
                while (it.hasNext()) {
                    arrayList3.add(((KotlinStaticDeclarationProvider) it.next()).getScope());
                }
                GlobalSearchScope globalSearchScopeUnion = GlobalSearchScope.union(arrayList3);
                Intrinsics.checkNotNullExpressionValue(globalSearchScopeUnion, "union(...)");
                KotlinDeclarationProvider kotlinDeclarationProviderCreateDeclarationProvider = KotlinDeclarationProviderKt.createDeclarationProvider(this.this$0.project, globalSearchScopeUnion, null);
                if (kotlinDeclarationProviderCreateDeclarationProvider instanceof KotlinStaticDeclarationProvider) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(destination, kotlinDeclarationProviderCreateDeclarationProvider);
                    mergeWith.setRemainingElements(list2);
                    return;
                }
                throw new IllegalStateException("`KotlinStaticDeclarationProvider` can only be merged into a combined declaration provider of the same type.".toString());
            }
        });
    }
}
