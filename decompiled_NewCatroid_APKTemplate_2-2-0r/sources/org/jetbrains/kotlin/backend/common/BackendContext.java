package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.ir.Ir;
import org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: BackendContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u00020\u001cX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/BackendContext;", "", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "internalPackageFqn", "Lorg/jetbrains/kotlin/name/FqName;", "getInternalPackageFqn", "()Lorg/jetbrains/kotlin/name/FqName;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getIr", "()Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "sharedVariablesManager", "Lorg/jetbrains/kotlin/backend/common/ir/SharedVariablesManager;", "getSharedVariablesManager", "()Lorg/jetbrains/kotlin/backend/common/ir/SharedVariablesManager;", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BackendContext {
    KotlinBuiltIns getBuiltIns();

    FqName getInternalPackageFqn();

    Ir<CommonBackendContext> getIr();

    IrBuiltIns getIrBuiltIns();

    IrFactory getIrFactory();

    SharedVariablesManager getSharedVariablesManager();

    IrTypeSystemContext getTypeSystem();
}
