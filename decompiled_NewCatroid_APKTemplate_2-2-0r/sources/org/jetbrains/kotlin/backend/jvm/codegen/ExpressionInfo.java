package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionInfo;", "", "()V", "blockInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "getBlockInfo", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "setBlockInfo", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;)V", "Lorg/jetbrains/kotlin/backend/jvm/codegen/LoopInfo;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/TryInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpressionInfo {
    private BlockInfo blockInfo;

    public /* synthetic */ ExpressionInfo(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ExpressionInfo() {
    }

    public final BlockInfo getBlockInfo() {
        return this.blockInfo;
    }

    public final void setBlockInfo(BlockInfo blockInfo) {
        this.blockInfo = blockInfo;
    }
}
