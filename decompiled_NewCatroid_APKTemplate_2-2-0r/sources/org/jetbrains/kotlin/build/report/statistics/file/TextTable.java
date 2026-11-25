package org.jetbrains.kotlin.build.report.statistics.file;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PlainTextBuildReportWriter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001f\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/file/TextTable;", "", "columnNames", "", "", "([Ljava/lang/String;)V", "columnsCount", "", "maxLengths", "", "rows", "Ljava/util/ArrayList;", "", "addRow", "", "row", "printTo", "p", "Lorg/jetbrains/kotlin/build/report/statistics/file/Printer;", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextTable {
    private final int columnsCount;
    private final int[] maxLengths;
    private final ArrayList<List<String>> rows;

    public TextTable(String... columnNames) {
        Intrinsics.checkNotNullParameter(columnNames, "columnNames");
        this.rows = new ArrayList<>();
        int length = columnNames.length;
        this.columnsCount = length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = columnNames[i].length();
        }
        this.maxLengths = iArr;
        this.rows.add(ArraysKt.toList(columnNames));
    }

    public final void addRow(String... row) {
        Intrinsics.checkNotNullParameter(row, "row");
        if (row.length != this.columnsCount) {
            throw new IllegalStateException(("Row size " + row.length + " differs from columns count " + this.columnsCount).toString());
        }
        this.rows.add(ArraysKt.toList(row));
        int length = row.length;
        for (int i = 0; i < length; i++) {
            String str = row[i];
            int[] iArr = this.maxLengths;
            iArr[i] = Math.max(iArr[i], str.length());
        }
    }

    public final void printTo(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        for (List<String> list : this.rows) {
            Intrinsics.checkNotNull(list);
            p.println(CollectionsKt.joinToString$default(CollectionsKt.withIndex(list), "|", null, null, 0, null, new Function1<IndexedValue<? extends String>, CharSequence>() { // from class: org.jetbrains.kotlin.build.report.statistics.file.TextTable$printTo$rowStr$1
                {
                    super(1);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final CharSequence invoke2(IndexedValue<String> indexedValue) {
                    Intrinsics.checkNotNullParameter(indexedValue, "<name for destructuring parameter 0>");
                    return StringsKt.padEnd(indexedValue.component2(), this.this$0.maxLengths[indexedValue.getIndex()], StringUtil.SPACE);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(IndexedValue<? extends String> indexedValue) {
                    return invoke2((IndexedValue<String>) indexedValue);
                }
            }, 30, null));
        }
    }
}
