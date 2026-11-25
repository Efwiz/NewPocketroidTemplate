package org.xmlunit.diff;

/* JADX INFO: loaded from: classes3.dex */
public interface ComparisonFormatter {
    String getDescription(Comparison comparison);

    String getDetails(Comparison.Detail detail, ComparisonType comparisonType, boolean z);
}
