package org.xmlunit.diff;

import java.util.Iterator;
import javax.xml.transform.Source;

/* JADX INFO: loaded from: classes3.dex */
public class Diff {
    private static final ComparisonFormatter DEFAULT_FORMATTER = new DefaultComparisonFormatter();
    private final Source controlSource;
    private final Iterable<Difference> differences;
    private final ComparisonFormatter formatter;
    private final Source testSource;

    public Diff(Source source, Source source2, Iterable<Difference> iterable) {
        this(source, source2, DEFAULT_FORMATTER, iterable);
    }

    public Diff(Source source, Source source2, ComparisonFormatter comparisonFormatter, Iterable<Difference> iterable) {
        this.controlSource = source;
        this.testSource = source2;
        this.formatter = comparisonFormatter;
        this.differences = iterable;
        Iterator<Difference> it = iterable.iterator();
        while (it.hasNext()) {
            it.next().setComparisonFormatter(comparisonFormatter);
        }
    }

    public String fullDescription() {
        return fullDescription(this.formatter);
    }

    public String fullDescription(ComparisonFormatter comparisonFormatter) {
        if (!hasDifferences()) {
            return "[identical]";
        }
        Iterator<Difference> it = getDifferences().iterator();
        StringBuilder sbAppend = new StringBuilder().append(it.next().getComparison().toString(comparisonFormatter));
        String strLineSeparator = System.lineSeparator();
        while (it.hasNext()) {
            sbAppend.append(strLineSeparator).append(it.next().getComparison().toString(comparisonFormatter));
        }
        return sbAppend.toString();
    }

    public boolean hasDifferences() {
        return this.differences.iterator().hasNext();
    }

    public Iterable<Difference> getDifferences() {
        return this.differences;
    }

    public Source getControlSource() {
        return this.controlSource;
    }

    public Source getTestSource() {
        return this.testSource;
    }

    public String toString() {
        return toString(this.formatter);
    }

    public String toString(ComparisonFormatter comparisonFormatter) {
        if (!hasDifferences()) {
            return "[identical]";
        }
        return getDifferences().iterator().next().getComparison().toString(comparisonFormatter);
    }
}
