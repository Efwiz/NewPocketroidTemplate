package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: SubroutineInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/cfg/SubroutineInfo;", "Lorg/jetbrains/kotlin/cfg/BreakableBlockInfo;", "subroutine", "Lorg/jetbrains/kotlin/psi/KtElement;", "entryPoint", "Lorg/jetbrains/kotlin/cfg/Label;", "exitPoint", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/cfg/Label;Lorg/jetbrains/kotlin/cfg/Label;)V", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubroutineInfo extends BreakableBlockInfo {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubroutineInfo(KtElement subroutine, Label entryPoint, Label exitPoint) {
        super(subroutine, entryPoint, exitPoint);
        Intrinsics.checkNotNullParameter(subroutine, "subroutine");
        Intrinsics.checkNotNullParameter(entryPoint, "entryPoint");
        Intrinsics.checkNotNullParameter(exitPoint, "exitPoint");
    }
}
