package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: CallKind.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¨\u0006\u0007"}, d2 = {"buildCallKindWithCustomResolutionSequence", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionSequenceBuilder;", "", "Lkotlin/ExtensionFunctionType;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallKindKt {
    public static final CallKind buildCallKindWithCustomResolutionSequence(Function1<? super ResolutionSequenceBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        ResolutionSequenceBuilder resolutionSequenceBuilder = new ResolutionSequenceBuilder(false, false, false, false, false, false, false, false, false, false, false, false, UnixStat.PERM_MASK, null);
        init.invoke(resolutionSequenceBuilder);
        return resolutionSequenceBuilder.build();
    }
}
