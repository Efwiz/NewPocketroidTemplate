package org.xmlunit.diff;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes3.dex */
public class Difference {
    private final Comparison comparison;
    private ComparisonFormatter formatter = new DefaultComparisonFormatter();
    private final ComparisonResult result;

    public Difference(Comparison comparison, ComparisonResult comparisonResult) {
        this.result = comparisonResult;
        this.comparison = comparison;
    }

    public ComparisonResult getResult() {
        return this.result;
    }

    public Comparison getComparison() {
        return this.comparison;
    }

    public void setComparisonFormatter(ComparisonFormatter comparisonFormatter) {
        this.formatter = comparisonFormatter;
    }

    public String toString(ComparisonFormatter comparisonFormatter) {
        return this.comparison.toString(comparisonFormatter) + " (" + this.result.name() + Constant.AFTER_QUTO;
    }

    public String toString() {
        return toString(this.formatter);
    }
}
