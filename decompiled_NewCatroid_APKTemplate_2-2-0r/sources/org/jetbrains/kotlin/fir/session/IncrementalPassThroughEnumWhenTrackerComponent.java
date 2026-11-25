package org.jetbrains.kotlin.fir.session;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirEnumWhenTrackerComponent;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;

/* JADX INFO: compiled from: IncrementalPassThroughEnumWhenTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/IncrementalPassThroughEnumWhenTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirEnumWhenTrackerComponent;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "(Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;)V", "report", "", "whenExpressionFilePath", "", "enumClassFqName", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalPassThroughEnumWhenTrackerComponent extends FirEnumWhenTrackerComponent {
    private final EnumWhenTracker enumWhenTracker;

    public IncrementalPassThroughEnumWhenTrackerComponent(EnumWhenTracker enumWhenTracker) {
        Intrinsics.checkNotNullParameter(enumWhenTracker, "enumWhenTracker");
        this.enumWhenTracker = enumWhenTracker;
    }

    @Override // org.jetbrains.kotlin.fir.FirEnumWhenTrackerComponent
    public void report(String whenExpressionFilePath, String enumClassFqName) {
        Intrinsics.checkNotNullParameter(whenExpressionFilePath, "whenExpressionFilePath");
        Intrinsics.checkNotNullParameter(enumClassFqName, "enumClassFqName");
        this.enumWhenTracker.report(whenExpressionFilePath, enumClassFqName);
    }
}
