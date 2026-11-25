package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: JvmLower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "it", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class JvmLowerKt$makePatchParentsPhase$1 extends Lambda implements Function1<CommonBackendContext, FileLoweringPass> {
    public static final JvmLowerKt$makePatchParentsPhase$1 INSTANCE = new JvmLowerKt$makePatchParentsPhase$1();

    JvmLowerKt$makePatchParentsPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final FileLoweringPass invoke(CommonBackendContext it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return new PatchDeclarationParents();
    }
}
