package org.jetbrains.kotlin.fir.expressions;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirOperation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirOperationNameConventions;", "", "()V", "ASSIGNMENTS", "", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "Lorg/jetbrains/kotlin/name/Name;", "getASSIGNMENTS", "()Ljava/util/Map;", "ASSIGNMENTS_TO_SIMPLE_OPERATOR", "getASSIGNMENTS_TO_SIMPLE_OPERATOR", "ASSIGNMENT_NAMES", "getASSIGNMENT_NAMES", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOperationNameConventions {
    private static final Map<FirOperation, Name> ASSIGNMENTS;
    private static final Map<FirOperation, Name> ASSIGNMENTS_TO_SIMPLE_OPERATOR;
    private static final Map<Name, FirOperation> ASSIGNMENT_NAMES;
    public static final FirOperationNameConventions INSTANCE = new FirOperationNameConventions();

    private FirOperationNameConventions() {
    }

    static {
        EnumMap enumMap = new EnumMap(MapsKt.mapOf(TuplesKt.to(FirOperation.PLUS_ASSIGN, OperatorNameConventions.PLUS_ASSIGN), TuplesKt.to(FirOperation.MINUS_ASSIGN, OperatorNameConventions.MINUS_ASSIGN), TuplesKt.to(FirOperation.TIMES_ASSIGN, OperatorNameConventions.TIMES_ASSIGN), TuplesKt.to(FirOperation.DIV_ASSIGN, OperatorNameConventions.DIV_ASSIGN), TuplesKt.to(FirOperation.REM_ASSIGN, OperatorNameConventions.REM_ASSIGN)));
        ASSIGNMENTS = enumMap;
        ArrayList arrayList = new ArrayList(enumMap.size());
        for (Map.Entry entry : enumMap.entrySet()) {
            arrayList.add(TuplesKt.to((Name) entry.getValue(), (FirOperation) entry.getKey()));
        }
        ASSIGNMENT_NAMES = MapsKt.toMap(arrayList);
        ASSIGNMENTS_TO_SIMPLE_OPERATOR = new EnumMap(MapsKt.mapOf(TuplesKt.to(FirOperation.PLUS_ASSIGN, OperatorNameConventions.PLUS), TuplesKt.to(FirOperation.MINUS_ASSIGN, OperatorNameConventions.MINUS), TuplesKt.to(FirOperation.TIMES_ASSIGN, OperatorNameConventions.TIMES), TuplesKt.to(FirOperation.DIV_ASSIGN, OperatorNameConventions.DIV), TuplesKt.to(FirOperation.REM_ASSIGN, OperatorNameConventions.REM)));
    }

    public final Map<FirOperation, Name> getASSIGNMENTS() {
        return ASSIGNMENTS;
    }

    public final Map<Name, FirOperation> getASSIGNMENT_NAMES() {
        return ASSIGNMENT_NAMES;
    }

    public final Map<FirOperation, Name> getASSIGNMENTS_TO_SIMPLE_OPERATOR() {
        return ASSIGNMENTS_TO_SIMPLE_OPERATOR;
    }
}
