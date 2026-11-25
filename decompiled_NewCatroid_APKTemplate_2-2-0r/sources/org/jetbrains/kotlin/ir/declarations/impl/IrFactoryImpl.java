package org.jetbrains.kotlin.ir.declarations.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.StageController;

/* JADX INFO: compiled from: IrFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrFactoryImpl;", "Lorg/jetbrains/kotlin/ir/declarations/impl/AbstractIrFactoryImpl;", "()V", "stageController", "Lorg/jetbrains/kotlin/ir/declarations/StageController;", "getStageController", "()Lorg/jetbrains/kotlin/ir/declarations/StageController;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFactoryImpl extends AbstractIrFactoryImpl {
    public static final IrFactoryImpl INSTANCE = new IrFactoryImpl();
    private static final StageController stageController = new StageController(0, 1, null);

    private IrFactoryImpl() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public StageController getStageController() {
        return stageController;
    }
}
