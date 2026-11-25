package org.jetbrains.kotlin.fir.analysis.js.checkers.expression;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirJsDynamicCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0004"}, d2 = {"nameToOperator", "", "Lorg/jetbrains/kotlin/name/Name;", "", "checkers.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJsDynamicCallCheckerKt {
    private static final Map<Name, String> nameToOperator = MapsKt.mapOf(TuplesKt.to(OperatorNameConventions.CONTAINS, "in"), TuplesKt.to(OperatorNameConventions.RANGE_TO, ".."), TuplesKt.to(OperatorNameConventions.RANGE_UNTIL, "..<"));
}
