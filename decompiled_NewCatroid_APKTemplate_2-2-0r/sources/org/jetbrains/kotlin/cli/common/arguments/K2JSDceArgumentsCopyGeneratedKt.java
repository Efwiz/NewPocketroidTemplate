package org.jetbrains.kotlin.cli.common.arguments;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: K2JSDceArgumentsCopyGenerated.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"copyK2JSDceArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSDceArguments;", TypedValues.TransitionType.S_FROM, "to", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2JSDceArgumentsCopyGeneratedKt {
    public static final K2JSDceArguments copyK2JSDceArguments(K2JSDceArguments from, K2JSDceArguments to) {
        String[] strArr;
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        CommonToolArgumentsCopyGeneratedKt.copyCommonToolArguments(from, to);
        String[] declarationsToKeep = from.getDeclarationsToKeep();
        if (declarationsToKeep != null) {
            Object[] objArrCopyOf = Arrays.copyOf(declarationsToKeep, declarationsToKeep.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
            strArr = (String[]) objArrCopyOf;
        } else {
            strArr = null;
        }
        to.setDeclarationsToKeep(strArr);
        to.setDevMode(from.getDevMode());
        to.setDevModeOverwritingStrategy(from.getDevModeOverwritingStrategy());
        to.setOutputDirectory(from.getOutputDirectory());
        to.setPrintReachabilityInfo(from.getPrintReachabilityInfo());
        return to;
    }
}
