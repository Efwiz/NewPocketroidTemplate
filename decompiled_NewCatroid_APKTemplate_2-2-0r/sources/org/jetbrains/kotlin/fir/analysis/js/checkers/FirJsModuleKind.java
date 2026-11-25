package org.jetbrains.kotlin.fir.analysis.js.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: FirJsModuleCheckUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/FirJsModuleKind;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;)V", "getModuleKind", "()Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsModuleKind implements FirSessionComponent {
    private final ModuleKind moduleKind;

    public FirJsModuleKind(ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        this.moduleKind = moduleKind;
    }

    public final ModuleKind getModuleKind() {
        return this.moduleKind;
    }
}
