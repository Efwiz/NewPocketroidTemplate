package org.xmlunit.diff;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class ComparisonListenerSupport {
    private final List<ComparisonListener> compListeners = new CopyOnWriteArrayList();
    private final List<ComparisonListener> matchListeners = new CopyOnWriteArrayList();
    private final List<ComparisonListener> diffListeners = new CopyOnWriteArrayList();

    public void addComparisonListener(ComparisonListener comparisonListener) {
        this.compListeners.add(comparisonListener);
    }

    public void addMatchListener(ComparisonListener comparisonListener) {
        this.matchListeners.add(comparisonListener);
    }

    public void addDifferenceListener(ComparisonListener comparisonListener) {
        this.diffListeners.add(comparisonListener);
    }

    public void fireComparisonPerformed(Comparison comparison, ComparisonResult comparisonResult) {
        fire(comparison, comparisonResult, this.compListeners);
        if (comparisonResult == ComparisonResult.EQUAL) {
            fire(comparison, comparisonResult, this.matchListeners);
        } else {
            fire(comparison, comparisonResult, this.diffListeners);
        }
    }

    private static void fire(Comparison comparison, ComparisonResult comparisonResult, List<ComparisonListener> list) {
        if (list.isEmpty()) {
            return;
        }
        Iterator<ComparisonListener> it = list.iterator();
        while (it.hasNext()) {
            it.next().comparisonPerformed(comparison, comparisonResult);
        }
    }
}
