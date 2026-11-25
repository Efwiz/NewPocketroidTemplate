package org.jetbrains.kotlin.fir.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.fir.backend.FirMangler;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;

/* JADX INFO: compiled from: FirJvmKotlinMangler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmKotlinMangler;", "Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "()V", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "compatibleMode", "", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmKotlinMangler extends FirMangler {
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinMangleComputer<FirDeclaration> getMangleComputer(MangleMode mode, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return new FirJvmMangleComputer(new StringBuilder(256), mode);
    }
}
