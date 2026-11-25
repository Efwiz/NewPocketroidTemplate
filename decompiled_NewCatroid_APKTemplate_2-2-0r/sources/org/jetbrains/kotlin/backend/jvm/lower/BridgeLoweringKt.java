package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: BridgeLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u000e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\n*\u00020\bH\u0002\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"bridgePhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getBridgePhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "overriddenFromClass", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "resolvesToClass", "", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BridgeLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> bridgePhase = PhaseBuildersKt.makeIrFilePhase(BridgeLoweringKt$bridgePhase$1.INSTANCE, "Bridge", "Generate bridges", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{JvmInlineClassLoweringKt.getJvmInlineClassPhase(), InheritedDefaultMethodsOnClassesLoweringKt.getInheritedDefaultMethodsOnClassesPhase()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getBridgePhase() {
        return bridgePhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean resolvesToClass(IrSimpleFunction irSimpleFunction) {
        IrSimpleFunction irSimpleFunctionOverriddenFromClass = overriddenFromClass(irSimpleFunction);
        return (irSimpleFunctionOverriddenFromClass == null || irSimpleFunctionOverriddenFromClass.getModality() == Modality.ABSTRACT) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction overriddenFromClass(IrSimpleFunction irSimpleFunction) {
        Iterator<T> it = irSimpleFunction.getOverriddenSymbols().iterator();
        boolean z = false;
        Object obj = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next = it.next();
            if (!JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(((IrSimpleFunctionSymbol) next).getOwner()))) {
                if (!z) {
                    z = true;
                    obj = next;
                }
            }
            obj = null;
            break;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) obj;
        if (irSimpleFunctionSymbol != null) {
            return irSimpleFunctionSymbol.getOwner();
        }
        return null;
    }
}
