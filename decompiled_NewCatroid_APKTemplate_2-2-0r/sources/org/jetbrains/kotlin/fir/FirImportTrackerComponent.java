package org.jetbrains.kotlin.fir;

import kotlin.Metadata;

/* JADX INFO: compiled from: FirImportTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirImportTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "report", "", "filePath", "", "importedFqName", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirImportTrackerComponent implements FirSessionComponent {
    public abstract void report(String filePath, String importedFqName);
}
