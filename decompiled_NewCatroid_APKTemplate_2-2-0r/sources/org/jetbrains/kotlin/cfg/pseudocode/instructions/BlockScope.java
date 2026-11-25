package org.jetbrains.kotlin.cfg.pseudocode.instructions;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: BlockScope.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u0004\u0018\u00010\u00008FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "", "parentScope", "block", "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;Lorg/jetbrains/kotlin/psi/KtElement;)V", "getBlock", "()Lorg/jetbrains/kotlin/psi/KtElement;", "blockScopeForContainingDeclaration", "getBlockScopeForContainingDeclaration", "()Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "blockScopeForContainingDeclaration$delegate", "Lkotlin/Lazy;", ConfigConstants.CONFIG_KEY_DEPTH, "", "getDepth", "()I", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BlockScope {
    private final KtElement block;

    /* JADX INFO: renamed from: blockScopeForContainingDeclaration$delegate, reason: from kotlin metadata */
    private final Lazy blockScopeForContainingDeclaration;
    private final int depth;
    private final BlockScope parentScope;

    public BlockScope(BlockScope blockScope, KtElement block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.parentScope = blockScope;
        this.block = block;
        this.depth = (blockScope != null ? blockScope.depth : 0) + 1;
        this.blockScopeForContainingDeclaration = LazyKt.lazy(new Function0<BlockScope>() { // from class: org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope$blockScopeForContainingDeclaration$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final BlockScope invoke() {
                BlockScope blockScope2 = this.this$0;
                while (blockScope2 != null && !(blockScope2.getBlock() instanceof KtDeclaration)) {
                    blockScope2 = blockScope2.parentScope;
                }
                return blockScope2;
            }
        });
    }

    public final KtElement getBlock() {
        return this.block;
    }

    public final int getDepth() {
        return this.depth;
    }

    public final BlockScope getBlockScopeForContainingDeclaration() {
        return (BlockScope) this.blockScopeForContainingDeclaration.getValue();
    }
}
