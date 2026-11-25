package org.jetbrains.kotlin.fir.session;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirImportTrackerComponent;
import org.jetbrains.kotlin.incremental.components.ImportTracker;

/* JADX INFO: compiled from: IncrementalPassThroughImportTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/IncrementalPassThroughImportTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirImportTrackerComponent;", "importTracker", "Lorg/jetbrains/kotlin/incremental/components/ImportTracker;", "(Lorg/jetbrains/kotlin/incremental/components/ImportTracker;)V", "report", "", "filePath", "", "importedFqName", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalPassThroughImportTrackerComponent extends FirImportTrackerComponent {
    private final ImportTracker importTracker;

    public IncrementalPassThroughImportTrackerComponent(ImportTracker importTracker) {
        Intrinsics.checkNotNullParameter(importTracker, "importTracker");
        this.importTracker = importTracker;
    }

    @Override // org.jetbrains.kotlin.fir.FirImportTrackerComponent
    public void report(String filePath, String importedFqName) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(importedFqName, "importedFqName");
        this.importTracker.report(filePath, importedFqName);
    }
}
