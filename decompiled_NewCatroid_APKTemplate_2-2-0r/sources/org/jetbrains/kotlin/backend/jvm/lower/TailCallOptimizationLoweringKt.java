package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TailCallOptimizationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0007\u001a\u0004\u0018\u00010\b*\b\u0012\u0004\u0012\u00020\b0\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\f\u0010\f\u001a\u00020\u000b*\u00020\rH\u0002\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"tailCallOptimizationPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getTailCallOptimizationPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "findTailCall", "Lorg/jetbrains/kotlin/ir/IrStatement;", "", "functionReturnsUnit", "", "isGetFieldOfUnit", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TailCallOptimizationLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> tailCallOptimizationPhase = PhaseBuildersKt.makeIrFilePhase(TailCallOptimizationLoweringKt$tailCallOptimizationPhase$1.INSTANCE, "TailCallOptimization", "Add or move returns to suspension points on tail-call positions", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getTailCallOptimizationPhase() {
        return tailCallOptimizationPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrStatement findTailCall(List<? extends IrStatement> list, boolean z) {
        Object next;
        Iterator<T> it = list.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(((IrStatement) next) instanceof IrReturn));
        IrReturn irReturn = next instanceof IrReturn ? (IrReturn) next : null;
        IrExpression value = irReturn != null ? irReturn.getValue() : null;
        if (!(value instanceof IrGetField)) {
            if (value == null) {
                return (IrStatement) CollectionsKt.lastOrNull((List) list);
            }
            return irReturn;
        }
        if (z && isGetFieldOfUnit((IrGetField) value)) {
            return findTailCall(list.subList(0, list.indexOf(irReturn)), z);
        }
        return irReturn;
    }

    private static final boolean isGetFieldOfUnit(IrGetField irGetField) {
        return IrTypePredicatesKt.isUnit(irGetField.getType()) && Intrinsics.areEqual(irGetField.getSymbol().getOwner().getName(), Name.identifier(JvmAbi.INSTANCE_FIELD)) && Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(irGetField.getSymbol().getOwner())), new FqName("kotlin.Unit"));
    }
}
