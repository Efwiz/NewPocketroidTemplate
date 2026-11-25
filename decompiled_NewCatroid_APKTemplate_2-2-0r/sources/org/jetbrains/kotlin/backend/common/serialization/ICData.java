package org.jetbrains.kotlin.backend.common.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.SerializedIrFile;

/* JADX INFO: compiled from: IncrementalCompilationSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/ICData;", "", "icData", "", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "containsErrorCode", "", "(Ljava/util/List;Z)V", "getContainsErrorCode", "()Z", "getIcData", "()Ljava/util/List;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ICData {
    private final boolean containsErrorCode;
    private final List<SerializedIrFile> icData;

    public ICData(List<SerializedIrFile> icData, boolean z) {
        Intrinsics.checkNotNullParameter(icData, "icData");
        this.icData = icData;
        this.containsErrorCode = z;
    }

    public final boolean getContainsErrorCode() {
        return this.containsErrorCode;
    }

    public final List<SerializedIrFile> getIcData() {
        return this.icData;
    }
}
