package org.jetbrains.kotlin.codegen.inline;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.SourceInfo;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J*\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015J \u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u0006\u0010\u0018\u001a\u00020\u0019J&\u0010\u001a\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\b\u0010\u001e\u001a\u0004\u0018\u00010\u0015H\u0002R!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/FileMapping;", "", "name", "", "path", "(Ljava/lang/String;Ljava/lang/String;)V", "lineMappings", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/codegen/inline/RangeMapping;", "Lkotlin/collections/ArrayList;", "getLineMappings", "()Ljava/util/ArrayList;", "getName", "()Ljava/lang/String;", "getPath", "mapNewInterval", FirebaseAnalytics.Param.SOURCE, "", "dest", "range", "callSite", "Lorg/jetbrains/kotlin/codegen/inline/SourcePosition;", "mapNewLineNumber", "currentIndex", "toSourceInfo", "Lorg/jetbrains/kotlin/codegen/SourceInfo;", "canReuseFor", "", "newSource", "globalMaxDest", "newCallSite", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileMapping {
    private final ArrayList<RangeMapping> lineMappings;
    private final String name;
    private final String path;

    public FileMapping(String name2, String path) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(path, "path");
        this.name = name2;
        this.path = path;
        this.lineMappings = new ArrayList<>();
    }

    public final String getName() {
        return this.name;
    }

    public final String getPath() {
        return this.path;
    }

    public final ArrayList<RangeMapping> getLineMappings() {
        return this.lineMappings;
    }

    public final SourceInfo toSourceInfo() {
        String str = this.name;
        String str2 = this.path;
        int iMax = 0;
        for (RangeMapping rangeMapping : this.lineMappings) {
            iMax = Math.max(iMax, (rangeMapping.getSource() + rangeMapping.getRange()) - 1);
        }
        return new SourceInfo(str, str2, iMax);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002d  */
    /* JADX WARN: Code duplicated, block: B:16:0x0033  */
    /* JADX WARN: Code duplicated, block: B:9:0x0018  */
    public final int mapNewLineNumber(int source, int currentIndex, SourcePosition callSite) {
        RangeMapping rangeMapping;
        RangeMapping rangeMappingMapNewInterval = (RangeMapping) CollectionsKt.lastOrNull((List) this.lineMappings);
        RangeMapping rangeMapping2 = null;
        if (rangeMappingMapNewInterval == null) {
            rangeMapping = (RangeMapping) CollectionsKt.firstOrNull((List) this.lineMappings);
            if (rangeMapping != null && canReuseFor(rangeMapping, source, currentIndex, callSite)) {
                rangeMapping2 = rangeMapping;
            }
            if (rangeMapping2 == null) {
                rangeMappingMapNewInterval = mapNewInterval(source, currentIndex + 1, 1, callSite);
            } else {
                rangeMappingMapNewInterval = rangeMapping2;
            }
        } else {
            if (!canReuseFor(rangeMappingMapNewInterval, source, currentIndex, callSite)) {
                rangeMappingMapNewInterval = null;
            }
            if (rangeMappingMapNewInterval == null) {
                rangeMapping = (RangeMapping) CollectionsKt.firstOrNull((List) this.lineMappings);
                if (rangeMapping != null) {
                    rangeMapping2 = rangeMapping;
                }
                if (rangeMapping2 == null) {
                    rangeMappingMapNewInterval = mapNewInterval(source, currentIndex + 1, 1, callSite);
                } else {
                    rangeMappingMapNewInterval = rangeMapping2;
                }
            }
        }
        rangeMappingMapNewInterval.setRange(Math.max(rangeMappingMapNewInterval.getRange(), (source - rangeMappingMapNewInterval.getSource()) + 1));
        return rangeMappingMapNewInterval.mapSourceToDest(source);
    }

    private final boolean canReuseFor(RangeMapping rangeMapping, int i, int i2, SourcePosition sourcePosition) {
        if (!Intrinsics.areEqual(rangeMapping.getCallSite(), sourcePosition)) {
            return false;
        }
        int range = rangeMapping.getRange() + (rangeMapping.contains(i2) ? 10 : 0);
        int source = i - rangeMapping.getSource();
        return source >= 0 && source < range;
    }

    public static /* synthetic */ RangeMapping mapNewInterval$default(FileMapping fileMapping, int i, int i2, int i3, SourcePosition sourcePosition, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            sourcePosition = null;
        }
        return fileMapping.mapNewInterval(i, i2, i3, sourcePosition);
    }

    public final RangeMapping mapNewInterval(int source, int dest, int range, SourcePosition callSite) {
        RangeMapping rangeMapping = new RangeMapping(source, dest, range, callSite, this);
        this.lineMappings.add(rangeMapping);
        return rangeMapping;
    }
}
