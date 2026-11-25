package org.jetbrains.kotlin.fir.declarations;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;

/* JADX INFO: compiled from: deprecationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\fJ\u0018\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fR\"\u0010\u0003\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfoPerUseSiteStorageBuilder;", "", "()V", "storage", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", ProductAction.ACTION_ADD, "", "useSite", AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS, "", CompilerOptions.INFO, "other", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfoPerUseSiteStorage;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationAnnotationInfoPerUseSiteStorageBuilder {
    private final Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> storage = new LinkedHashMap();

    public final void add(AnnotationUseSiteTarget useSite, DeprecationAnnotationInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> map = this.storage;
        ArrayList arrayList = map.get(useSite);
        if (arrayList == null) {
            arrayList = new ArrayList();
            map.put(useSite, arrayList);
        }
        arrayList.add(info);
    }

    public final void add(AnnotationUseSiteTarget useSite, Iterable<? extends DeprecationAnnotationInfo> infos) {
        Intrinsics.checkNotNullParameter(infos, "infos");
        Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> map = this.storage;
        ArrayList arrayList = map.get(useSite);
        if (arrayList == null) {
            arrayList = new ArrayList();
            map.put(useSite, arrayList);
        }
        CollectionsKt.addAll(arrayList, infos);
    }

    public final void add(DeprecationAnnotationInfoPerUseSiteStorage other) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (Map.Entry<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> entry : other.getStorage().entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    public final DeprecationAnnotationInfoPerUseSiteStorage build() {
        return new DeprecationAnnotationInfoPerUseSiteStorage(this.storage);
    }
}
