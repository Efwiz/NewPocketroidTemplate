package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: Lower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Empty", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FileLoweringPass {
    void lower(IrFile irFile);

    /* JADX INFO: compiled from: Lower.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/FileLoweringPass$Empty;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "()V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements FileLoweringPass {
        public static final Empty INSTANCE = new Empty();

        @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
        public void lower(IrFile irFile) {
            Intrinsics.checkNotNullParameter(irFile, "irFile");
        }

        private Empty() {
        }
    }
}
