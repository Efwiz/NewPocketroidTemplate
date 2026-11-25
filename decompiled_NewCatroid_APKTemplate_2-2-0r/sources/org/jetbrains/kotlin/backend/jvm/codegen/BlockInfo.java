package org.jetbrains.kotlin.backend.jvm.codegen;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0003J2\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0017\u0010\u001a\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00170\u001b¢\u0006\u0002\b\u001cH\u0080\bø\u0001\u0000¢\u0006\u0002\b\u001dJ/\u0010\u001e\u001a\u0004\u0018\u0001H\u001f\"\u0004\b\u0000\u0010\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u0002H\u001f0\u001bH\u0080\bø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u0006\u0010#\u001a\u00020$J'\u0010%\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00170'H\u0080\bø\u0001\u0000¢\u0006\u0002\b(J?\u0010)\u001a\u0002H\u001f\"\b\b\u0000\u0010**\u00020\f\"\u0004\b\u0001\u0010\u001f2\u0006\u0010+\u001a\u0002H*2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u0002H\u001f0\u001bH\u0080\bø\u0001\u0000¢\u0006\u0004\b,\u0010-R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "", "parent", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;)V", "activeLocalGaps", "", "getActiveLocalGaps", "()I", "setActiveLocalGaps", "(I)V", AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS, "Ljava/util/Stack;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionInfo;", "getInfos", "()Ljava/util/Stack;", "getParent", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "variables", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/VariableInfo;", "getVariables", "()Ljava/util/List;", "forEachBlockUntil", "", "tryWithFinallyInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/TryWithFinallyInfo;", "onBlock", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "forEachBlockUntil$backend_jvm_codegen", "handleBlock", "R", "f", "handleBlock$backend_jvm_codegen", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "hasFinallyBlocks", "", "localGapScope", "block", "Lkotlin/Function0;", "localGapScope$backend_jvm_codegen", "withBlock", ExifInterface.GPS_DIRECTION_TRUE, CompilerOptions.INFO, "withBlock$backend_jvm_codegen", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionInfo;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BlockInfo {
    private int activeLocalGaps;
    private final Stack<ExpressionInfo> infos;
    private final BlockInfo parent;
    private final List<VariableInfo> variables;

    /* JADX WARN: Multi-variable type inference failed */
    public BlockInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public BlockInfo(BlockInfo blockInfo) {
        Stack<ExpressionInfo> stack;
        this.parent = blockInfo;
        this.variables = new ArrayList();
        this.infos = (blockInfo == null || (stack = blockInfo.infos) == null) ? new Stack<>() : stack;
    }

    public /* synthetic */ BlockInfo(BlockInfo blockInfo, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : blockInfo);
    }

    public final BlockInfo getParent() {
        return this.parent;
    }

    public final List<VariableInfo> getVariables() {
        return this.variables;
    }

    public final Stack<ExpressionInfo> getInfos() {
        return this.infos;
    }

    public final int getActiveLocalGaps() {
        return this.activeLocalGaps;
    }

    public final void setActiveLocalGaps(int i) {
        this.activeLocalGaps = i;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001a A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:11:0x001c A[ORIG_RETURN, RETURN] */
    public final boolean hasFinallyBlocks() {
        for (Object obj : this.infos) {
            if (obj instanceof TryWithFinallyInfo) {
                if (obj != null) {
                    return true;
                }
                return false;
            }
        }
        obj = null;
        if (obj != null) {
            return true;
        }
        return false;
    }

    public final void forEachBlockUntil$backend_jvm_codegen(TryWithFinallyInfo tryWithFinallyInfo, Function1<? super BlockInfo, Unit> onBlock) {
        Intrinsics.checkNotNullParameter(tryWithFinallyInfo, "tryWithFinallyInfo");
        Intrinsics.checkNotNullParameter(onBlock, "onBlock");
        for (BlockInfo parent = this; parent != null && !Intrinsics.areEqual(parent, tryWithFinallyInfo.getBlockInfo()); parent = parent.getParent()) {
            onBlock.invoke(parent);
        }
    }

    public final <T extends ExpressionInfo, R> R withBlock$backend_jvm_codegen(T info, Function1<? super T, ? extends R> f) {
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(f, "f");
        info.setBlockInfo(this);
        getInfos().add(info);
        try {
            return f.invoke(info);
        } finally {
            InlineMarker.finallyStart(1);
            getInfos().pop().setBlockInfo(null);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <R> R handleBlock$backend_jvm_codegen(Function1<? super ExpressionInfo, ? extends R> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        if (getInfos().isEmpty()) {
            return null;
        }
        ExpressionInfo expressionInfoPop = getInfos().pop();
        try {
            Intrinsics.checkNotNull(expressionInfoPop);
            return f.invoke(expressionInfoPop);
        } finally {
            InlineMarker.finallyStart(1);
            getInfos().add(expressionInfoPop);
            InlineMarker.finallyEnd(1);
        }
    }

    public final void localGapScope$backend_jvm_codegen(TryWithFinallyInfo tryWithFinallyInfo, Function0<Unit> block) {
        BlockInfo parent;
        boolean zAreEqual;
        Intrinsics.checkNotNullParameter(tryWithFinallyInfo, "tryWithFinallyInfo");
        Intrinsics.checkNotNullParameter(block, "block");
        BlockInfo parent2 = this;
        while (true) {
            if (parent2 == null || Intrinsics.areEqual(parent2, tryWithFinallyInfo.getBlockInfo())) {
                break;
            }
            parent2.setActiveLocalGaps(parent2.getActiveLocalGaps() + 1);
            parent2.getActiveLocalGaps();
            parent2 = parent2.getParent();
        }
        try {
            block.invoke();
            InlineMarker.finallyStart(1);
            BlockInfo blockInfo = this;
            while (parent != null) {
                if (!zAreEqual) {
                }
            }
        } finally {
            InlineMarker.finallyStart(1);
            for (parent = this; parent != null && !Intrinsics.areEqual(parent, tryWithFinallyInfo.getBlockInfo()); parent = parent.getParent()) {
                parent.setActiveLocalGaps(parent.getActiveLocalGaps() - 1);
                parent.getActiveLocalGaps();
            }
            InlineMarker.finallyEnd(1);
        }
    }
}
