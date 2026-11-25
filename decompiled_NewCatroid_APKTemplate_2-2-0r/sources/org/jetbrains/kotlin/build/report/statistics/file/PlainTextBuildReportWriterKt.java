package org.jetbrains.kotlin.build.report.statistics.file;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PlainTextBuildReportWriter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007H\u0000¨\u0006\t"}, d2 = {"printMap", "", "p", "Lorg/jetbrains/kotlin/build/report/statistics/file/Printer;", "name", "", "mapping", "", "", "kotlin-build-statistics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PlainTextBuildReportWriterKt {
    public static final void printMap(Printer p, String name2, Map<String, Integer> mapping) {
        Intrinsics.checkNotNullParameter(p, "p");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        if (mapping.isEmpty()) {
            return;
        }
        if (mapping.size() == 1) {
            p.println(name2 + ": " + ((String) CollectionsKt.single(mapping.keySet())));
            return;
        }
        String str = name2 + ':';
        if (str != null) {
            p.println(str);
        }
        p.pushIndent();
        try {
            for (Map.Entry entry : MapsKt.toSortedMap(mapping).entrySet()) {
                p.println(((String) entry.getKey()) + '(' + ((Integer) entry.getValue()) + ')');
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            p.popIndent();
        }
    }
}
