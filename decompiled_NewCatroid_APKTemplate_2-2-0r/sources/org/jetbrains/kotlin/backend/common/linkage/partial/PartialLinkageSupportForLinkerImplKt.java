package org.jetbrains.kotlin.backend.common.linkage.partial;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfig;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageLogger;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;

/* JADX INFO: compiled from: PartialLinkageSupportForLinkerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"createPartialLinkageSupportForLinker", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLinker;", "partialLinkageConfig", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig;", "allowErrorTypes", "", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageSupportForLinkerImplKt {
    public static final PartialLinkageSupportForLinker createPartialLinkageSupportForLinker(PartialLinkageConfig partialLinkageConfig, boolean z, IrBuiltIns builtIns, IrMessageLogger messageLogger) {
        Intrinsics.checkNotNullParameter(partialLinkageConfig, "partialLinkageConfig");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        if (partialLinkageConfig.isEnabled()) {
            return new PartialLinkageSupportForLinkerImpl(builtIns, z, new PartialLinkageLogger(messageLogger, partialLinkageConfig.getLogLevel()));
        }
        return PartialLinkageSupportForLinker.INSTANCE.getDISABLED();
    }
}
