package io.github.rosemoe.sora.lang.diagnostic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class DiagnosticsContainer {
    private final List<DiagnosticRegion> regions;
    private final boolean shiftEnabled;

    public DiagnosticsContainer() {
        this(true);
    }

    public DiagnosticsContainer(boolean z) {
        this.regions = new ArrayList();
        this.shiftEnabled = z;
    }

    public synchronized void addDiagnostics(Collection<DiagnosticRegion> collection) {
        this.regions.addAll(collection);
    }

    public synchronized void addDiagnostic(DiagnosticRegion diagnosticRegion) {
        this.regions.add(diagnosticRegion);
    }

    public synchronized void queryInRegion(List<DiagnosticRegion> list, int i, int i2) {
        for (DiagnosticRegion diagnosticRegion : this.regions) {
            if (diagnosticRegion.endIndex > i && diagnosticRegion.startIndex <= i2) {
                list.add(diagnosticRegion);
            }
        }
    }

    public synchronized void shiftOnInsert(int i, int i2) {
        if (this.shiftEnabled) {
            int i3 = i2 - i;
            for (DiagnosticRegion diagnosticRegion : this.regions) {
                if (diagnosticRegion.startIndex <= i && diagnosticRegion.endIndex >= i) {
                    diagnosticRegion.endIndex += i3;
                }
                if (diagnosticRegion.startIndex > i) {
                    diagnosticRegion.startIndex += i3;
                    diagnosticRegion.endIndex += i3;
                }
            }
        }
    }

    public synchronized void shiftOnDelete(int i, int i2) {
        if (this.shiftEnabled) {
            int i3 = i2 - i;
            ArrayList arrayList = new ArrayList();
            for (DiagnosticRegion diagnosticRegion : this.regions) {
                int iMax = Math.max(i, diagnosticRegion.startIndex);
                int iMin = Math.min(i2, diagnosticRegion.endIndex);
                if (iMin <= iMax) {
                    if (diagnosticRegion.startIndex >= i2) {
                        diagnosticRegion.startIndex -= i3;
                        diagnosticRegion.endIndex -= i3;
                    }
                } else {
                    diagnosticRegion.endIndex -= iMin - iMax;
                    if (diagnosticRegion.startIndex > i) {
                        int i4 = diagnosticRegion.startIndex - i;
                        diagnosticRegion.startIndex -= i4;
                        diagnosticRegion.endIndex -= i4;
                    }
                    if (diagnosticRegion.startIndex == diagnosticRegion.endIndex) {
                        arrayList.add(diagnosticRegion);
                    }
                }
            }
            this.regions.removeAll(arrayList);
        }
    }

    public synchronized void reset() {
        this.regions.clear();
    }
}
