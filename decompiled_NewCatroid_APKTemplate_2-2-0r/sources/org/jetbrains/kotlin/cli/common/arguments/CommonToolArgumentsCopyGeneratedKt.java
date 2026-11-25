package org.jetbrains.kotlin.cli.common.arguments;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonToolArgumentsCopyGenerated.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"copyCommonToolArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", TypedValues.TransitionType.S_FROM, "to", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonToolArgumentsCopyGeneratedKt {
    public static final CommonToolArguments copyCommonToolArguments(CommonToolArguments from, CommonToolArguments to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        to.setAllWarningsAsErrors(from.getAllWarningsAsErrors());
        to.setExtraHelp(from.getExtraHelp());
        to.setFreeArgs(from.getFreeArgs());
        to.setHelp(from.getHelp());
        to.setInternalArguments(from.getInternalArguments());
        to.setSuppressWarnings(from.getSuppressWarnings());
        to.setVerbose(from.getVerbose());
        to.setVersion(from.getVersion());
        return to;
    }
}
