package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TowerResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\u001c\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001e\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u00052\u0006\u0010\u000b\u001a\u00020\fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "C", "", "process", "", "", "data", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "recordLookups", "", "skippedData", "name", "Lorg/jetbrains/kotlin/name/Name;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ScopeTowerProcessor<C> {
    List<Collection<C>> process(TowerData data);

    void recordLookups(Collection<? extends TowerData> skippedData, Name name2);
}
