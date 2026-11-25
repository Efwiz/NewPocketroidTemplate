package org.jetbrains.kotlin.backend.common.linkage.partial;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfig;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageLogger;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;

/* JADX INFO: compiled from: PartialLinkageSupportForLoweringsImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"createPartialLinkageSupportForLowerings", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "partialLinkageConfig", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageSupportForLoweringsImplKt {
    public static final PartialLinkageSupportForLowerings createPartialLinkageSupportForLowerings(PartialLinkageConfig partialLinkageConfig, IrBuiltIns builtIns, IrMessageLogger messageLogger) {
        Intrinsics.checkNotNullParameter(partialLinkageConfig, "partialLinkageConfig");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        if (partialLinkageConfig.isEnabled()) {
            return new PartialLinkageSupportForLoweringsImpl(builtIns, new PartialLinkageLogger(messageLogger, partialLinkageConfig.getLogLevel()));
        }
        return PartialLinkageSupportForLowerings.INSTANCE.getDISABLED();
    }
}
