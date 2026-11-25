package org.jetbrains.kotlin.backend.common.ir;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;

/* JADX INFO: compiled from: Ir.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/Ir;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "", "context", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "localScopeWithCounterMap", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalScopeWithCounterMap;", "getLocalScopeWithCounterMap$ir_backend_common", "()Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalScopeWithCounterMap;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "shouldGenerateHandlerParameterForDefaultBodyFun", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Ir<T extends CommonBackendContext> {
    private final T context;
    private final LocalDeclarationsLowering.LocalScopeWithCounterMap localScopeWithCounterMap;

    public abstract Symbols getSymbols();

    public boolean shouldGenerateHandlerParameterForDefaultBodyFun() {
        return false;
    }

    public Ir(T context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.localScopeWithCounterMap = new LocalDeclarationsLowering.LocalScopeWithCounterMap();
    }

    public final T getContext() {
        return this.context;
    }

    /* JADX INFO: renamed from: getLocalScopeWithCounterMap$ir_backend_common, reason: from getter */
    public final LocalDeclarationsLowering.LocalScopeWithCounterMap getLocalScopeWithCounterMap() {
        return this.localScopeWithCounterMap;
    }
}
