package org.jetbrains.kotlin.fir.analysis.checkers.type;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeChecker<T extends FirTypeRef> {
    public abstract void check(T typeRef, CheckerContext context, DiagnosticReporter reporter);
}
