package org.xmlunit.diff;

/* JADX INFO: loaded from: classes3.dex */
public final class ComparisonControllers {
    public static final ComparisonController Default = new ComparisonController() { // from class: org.xmlunit.diff.ComparisonControllers.1
        @Override // org.xmlunit.diff.ComparisonController
        public boolean stopDiffing(Difference difference) {
            return false;
        }
    };
    public static final ComparisonController StopWhenDifferent = new StopComparisonController(ComparisonResult.DIFFERENT);
    public static final ComparisonController StopWhenSimilar = new StopComparisonController(ComparisonResult.SIMILAR);

    private ComparisonControllers() {
    }

    private static final class StopComparisonController implements ComparisonController {
        final ComparisonResult minimumComparisonResult;

        public StopComparisonController(ComparisonResult comparisonResult) {
            this.minimumComparisonResult = comparisonResult;
        }

        @Override // org.xmlunit.diff.ComparisonController
        public boolean stopDiffing(Difference difference) {
            return difference.getResult().ordinal() >= this.minimumComparisonResult.ordinal();
        }
    }
}
