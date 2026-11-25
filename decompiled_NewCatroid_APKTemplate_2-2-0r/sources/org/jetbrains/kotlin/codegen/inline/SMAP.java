package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "", "fileMappings", "", "Lorg/jetbrains/kotlin/codegen/inline/FileMapping;", "(Ljava/util/List;)V", "getFileMappings", "()Ljava/util/List;", "intervals", "Lorg/jetbrains/kotlin/codegen/inline/RangeMapping;", "findRange", "lineNumber", "", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SMAP {
    public static final String END = "*E";
    public static final String FILE_SECTION = "*F";
    public static final String LINE_SECTION = "*L";
    public static final String STRATA_SECTION = "*S";
    private final List<FileMapping> fileMappings;
    private final List<RangeMapping> intervals;

    public SMAP(List<FileMapping> fileMappings) {
        Intrinsics.checkNotNullParameter(fileMappings, "fileMappings");
        this.fileMappings = fileMappings;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = fileMappings.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((FileMapping) it.next()).getLineMappings());
        }
        this.intervals = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.codegen.inline.SMAP$special$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((RangeMapping) t).getDest()), Integer.valueOf(((RangeMapping) t2).getDest()));
            }
        });
    }

    public final List<FileMapping> getFileMappings() {
        return this.fileMappings;
    }

    public final RangeMapping findRange(final int lineNumber) {
        int iBinarySearch$default = CollectionsKt.binarySearch$default(this.intervals, 0, 0, new Function1<RangeMapping, Integer>() { // from class: org.jetbrains.kotlin.codegen.inline.SMAP$findRange$index$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(RangeMapping it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Integer.valueOf(it.contains(lineNumber) ? 0 : it.getDest() - lineNumber);
            }
        }, 3, (Object) null);
        if (iBinarySearch$default < 0) {
            return null;
        }
        return this.intervals.get(iBinarySearch$default);
    }
}
