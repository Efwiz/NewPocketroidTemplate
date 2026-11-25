package io.github.rosemoe.sora.lang.diagnostic;

/* JADX INFO: loaded from: classes11.dex */
public final class DiagnosticRegion implements Comparable<DiagnosticRegion> {
    public static final short SEVERITY_ERROR = 3;
    public static final short SEVERITY_NONE = 0;
    public static final short SEVERITY_TYPO = 1;
    public static final short SEVERITY_WARNING = 2;
    public DiagnosticDetail detail;
    public int endIndex;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public long f272id;
    public short severity;
    public int startIndex;

    public DiagnosticRegion(int i, int i2, short s) {
        this(i, i2, s, 0L, null);
    }

    public DiagnosticRegion(int i, int i2, short s, long j) {
        this(i, i2, s, j, null);
    }

    public DiagnosticRegion(int i, int i2, short s, long j, DiagnosticDetail diagnosticDetail) {
        this.startIndex = i;
        this.endIndex = i2;
        this.severity = s;
        this.detail = diagnosticDetail;
        this.f272id = j;
    }

    @Override // java.lang.Comparable
    public int compareTo(DiagnosticRegion diagnosticRegion) {
        int iCompare = Integer.compare(this.startIndex, diagnosticRegion.startIndex);
        if (iCompare == 0) {
            iCompare = Integer.compare(this.endIndex, diagnosticRegion.endIndex);
        }
        if (iCompare == 0) {
            iCompare = Short.compare(this.severity, diagnosticRegion.severity);
        }
        return iCompare == 0 ? Long.compare(this.f272id, diagnosticRegion.f272id) : iCompare;
    }
}
