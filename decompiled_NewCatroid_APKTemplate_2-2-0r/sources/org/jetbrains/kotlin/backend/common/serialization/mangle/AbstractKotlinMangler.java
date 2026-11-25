package org.jetbrains.kotlin.backend.common.serialization.mangle;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.CityHashKt;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: AbstractKotlinMangler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\rH&R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/AbstractKotlinMangler;", "D", "", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler;", "()V", "hashMangle", "", "", "getHashMangle", "(Ljava/lang/String;)J", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractKotlinMangler<D> implements KotlinMangler<D> {
    public abstract KotlinExportChecker<D> getExportChecker(boolean compatibleMode);

    public abstract KotlinMangleComputer<D> getMangleComputer(MangleMode mode, boolean compatibleMode);

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public long hashedMangle(D d, boolean z) {
        return KotlinMangler.DefaultImpls.hashedMangle(this, d, z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isPlatformSpecificExport(D d) {
        return KotlinMangler.DefaultImpls.isPlatformSpecificExport(this, d);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public long signatureMangle(D d, boolean z) {
        return KotlinMangler.DefaultImpls.signatureMangle(this, d, z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public long getHashMangle(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return CityHashKt.cityHash64(str);
    }
}
