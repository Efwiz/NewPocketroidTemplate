package org.jetbrains.kotlin.analysis.project.structure;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoCacheForModuleException.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00060\u0001j\u0002`\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/NoCacheForModuleException;", "Ljava/util/NoSuchElementException;", "Lkotlin/NoSuchElementException;", "missingModule", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "existingModules", "", "(Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;Ljava/util/Set;)V", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoCacheForModuleException extends NoSuchElementException {
    private final Set<KtModule> existingModules;
    private final KtModule missingModule;

    /* JADX WARN: Multi-variable type inference failed */
    public NoCacheForModuleException(KtModule missingModule, Set<? extends KtModule> existingModules) {
        Intrinsics.checkNotNullParameter(missingModule, "missingModule");
        Intrinsics.checkNotNullParameter(existingModules, "existingModules");
        this.missingModule = missingModule;
        this.existingModules = existingModules;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        StringBuilder sbAppend = new StringBuilder("No cache was found for module '").append(this.missingModule.getModuleDescription()).append("'! Caches exist for the following modules: ");
        Set<KtModule> set = this.existingModules;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtModule) it.next()).getModuleDescription());
        }
        return sbAppend.append(arrayList).toString();
    }
}
