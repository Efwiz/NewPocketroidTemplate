package org.jetbrains.kotlin.modules;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TargetId.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"TargetId", "Lorg/jetbrains/kotlin/modules/TargetId;", "module", "Lorg/jetbrains/kotlin/modules/Module;", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TargetIdKt {
    public static final TargetId TargetId(Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return new TargetId(module.getModuleName(), module.getModuleType());
    }
}
