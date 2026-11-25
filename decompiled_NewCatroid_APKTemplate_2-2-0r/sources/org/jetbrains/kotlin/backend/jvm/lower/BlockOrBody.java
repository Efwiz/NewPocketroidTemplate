package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/BlockOrBody;", "", "()V", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "getElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "Block", "Body", "Lorg/jetbrains/kotlin/backend/jvm/lower/BlockOrBody$Block;", "Lorg/jetbrains/kotlin/backend/jvm/lower/BlockOrBody$Body;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
abstract class BlockOrBody {
    public /* synthetic */ BlockOrBody(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract IrElement getElement();

    private BlockOrBody() {
    }

    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/BlockOrBody$Body;", "Lorg/jetbrains/kotlin/backend/jvm/lower/BlockOrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)V", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getElement", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Body extends BlockOrBody {
        private final IrBody body;

        public static /* synthetic */ Body copy$default(Body body, IrBody irBody, int i, Object obj) {
            if ((i & 1) != 0) {
                irBody = body.body;
            }
            return body.copy(irBody);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrBody getBody() {
            return this.body;
        }

        public final Body copy(IrBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            return new Body(body);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Body) && Intrinsics.areEqual(this.body, ((Body) other).body);
        }

        public int hashCode() {
            return this.body.hashCode();
        }

        public String toString() {
            return "Body(body=" + this.body + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Body(IrBody body) {
            super(null);
            Intrinsics.checkNotNullParameter(body, "body");
            this.body = body;
        }

        public final IrBody getBody() {
            return this.body;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.lower.BlockOrBody
        public IrBody getElement() {
            return this.body;
        }
    }

    /* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/BlockOrBody$Block;", "Lorg/jetbrains/kotlin/backend/jvm/lower/BlockOrBody;", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlock;)V", "getBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getElement", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Block extends BlockOrBody {
        private final IrBlock block;

        public static /* synthetic */ Block copy$default(Block block, IrBlock irBlock, int i, Object obj) {
            if ((i & 1) != 0) {
                irBlock = block.block;
            }
            return block.copy(irBlock);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrBlock getBlock() {
            return this.block;
        }

        public final Block copy(IrBlock block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return new Block(block);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Block) && Intrinsics.areEqual(this.block, ((Block) other).block);
        }

        public int hashCode() {
            return this.block.hashCode();
        }

        public String toString() {
            return "Block(block=" + this.block + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Block(IrBlock block) {
            super(null);
            Intrinsics.checkNotNullParameter(block, "block");
            this.block = block;
        }

        public final IrBlock getBlock() {
            return this.block;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.lower.BlockOrBody
        public IrBlock getElement() {
            return this.block;
        }
    }
}
