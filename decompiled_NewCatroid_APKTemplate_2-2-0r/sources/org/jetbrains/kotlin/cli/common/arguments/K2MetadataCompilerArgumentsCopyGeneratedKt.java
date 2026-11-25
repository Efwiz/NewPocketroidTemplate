package org.jetbrains.kotlin.cli.common.arguments;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: K2MetadataCompilerArgumentsCopyGenerated.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"copyK2MetadataCompilerArguments", "Lorg/jetbrains/kotlin/cli/common/arguments/K2MetadataCompilerArguments;", TypedValues.TransitionType.S_FROM, "to", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2MetadataCompilerArgumentsCopyGeneratedKt {
    public static final K2MetadataCompilerArguments copyK2MetadataCompilerArguments(K2MetadataCompilerArguments from, K2MetadataCompilerArguments to) {
        String[] strArr;
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        CommonCompilerArgumentsCopyGeneratedKt.copyCommonCompilerArguments(from, to);
        to.setClasspath(from.getClasspath());
        to.setDestination(from.getDestination());
        to.setEnabledInJps(from.getEnabledInJps());
        String[] friendPaths = from.getFriendPaths();
        String[] strArr2 = null;
        if (friendPaths != null) {
            Object[] objArrCopyOf = Arrays.copyOf(friendPaths, friendPaths.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
            strArr = (String[]) objArrCopyOf;
        } else {
            strArr = null;
        }
        to.setFriendPaths(strArr);
        to.setModuleName(from.getModuleName());
        String[] refinesPaths = from.getRefinesPaths();
        if (refinesPaths != null) {
            Object[] objArrCopyOf2 = Arrays.copyOf(refinesPaths, refinesPaths.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf2, "copyOf(...)");
            strArr2 = (String[]) objArrCopyOf2;
        }
        to.setRefinesPaths(strArr2);
        return to;
    }
}
