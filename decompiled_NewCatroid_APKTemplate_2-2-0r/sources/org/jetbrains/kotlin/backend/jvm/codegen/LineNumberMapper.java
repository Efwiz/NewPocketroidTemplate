package org.jetbrains.kotlin.backend.jvm.codegen;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.arsc.chunk.SpecBlock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.codegen.SourceInfo;
import org.jetbrains.kotlin.codegen.inline.SMAP;
import org.jetbrains.kotlin.codegen.inline.SourceMapCopier;
import org.jetbrains.kotlin.codegen.inline.SourceMapper;
import org.jetbrains.kotlin.codegen.inline.SourcePosition;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: LineNumberMapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001=B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u001e\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020\u0017J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020$H\u0002J\u0006\u0010%\u001a\u00020\u000eJ\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\u000eH\u0002J\u0016\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020\u0010J\u000e\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u0010J\b\u0010-\u001a\u00020.H\u0002J\u0014\u0010\u000f\u001a\u00020\u00172\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001700J4\u00101\u001a\u00020\u00172\u0006\u00102\u001a\u00020\u00102\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001700H\u0080\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\b3J\u0006\u00104\u001a\u00020\u0017J\u0010\u00105\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u00106\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001c\u00107\u001a\u00020\u00172\u0006\u00108\u001a\u0002092\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001700J\f\u0010:\u001a\u00020;*\u00020\u0019H\u0002J\f\u0010<\u001a\u00020\u0010*\u00020\u0019H\u0002R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/LineNumberMapper;", "", "expressionCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;)V", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "lastLineNumber", "", "noLineNumberScope", "", "smap", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "smapStack", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/LineNumberMapper$DataForIrInlinedFunction;", "afterIrInline", "", "inlinedBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "beforeIrInline", "buildSmapFor", "classSMAP", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "dropCurrentSmap", "getDeclarationWhereGivenElementWasInlined", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "inlinedElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "getLineNumber", "getLineNumberForOffset", TypedValues.CycleType.S_WAVE_OFFSET, "markLineNumber", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "startOffset", "markLineNumberAfterInlineIfNeeded", "registerLineNumberAfterwards", "markNewLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "block", "Lkotlin/Function0;", "noLineNumberScopeWithCondition", SpecBlock.NAME_flag, "noLineNumberScopeWithCondition$backend_jvm_codegen", "resetLineNumber", "setUpAdditionalLineNumbersAfterLambdaInlining", "setUpAdditionalLineNumbersBeforeLambdaInlining", "stashSmapForGivenTry", "tryInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/TryWithFinallyInfo;", "getClassThatContainsDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isInvokeOnDefaultArg", "DataForIrInlinedFunction", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LineNumberMapper {
    private final ExpressionCodegen expressionCodegen;
    private final IrFileEntry fileEntry;
    private final IrFunction irFunction;
    private int lastLineNumber;
    private boolean noLineNumberScope;
    private final SourceMapper smap;
    private final List<DataForIrInlinedFunction> smapStack;

    public LineNumberMapper(ExpressionCodegen expressionCodegen) {
        Intrinsics.checkNotNullParameter(expressionCodegen, "expressionCodegen");
        this.expressionCodegen = expressionCodegen;
        this.smap = expressionCodegen.getSmap();
        IrFunction irFunction = expressionCodegen.getIrFunction();
        this.irFunction = irFunction;
        this.fileEntry = JvmIrInlineUtilsKt.getFileParentBeforeInline(irFunction).getFileEntry();
        this.lastLineNumber = -1;
        this.smapStack = new ArrayList();
    }

    private final JvmBackendContext getContext() {
        return this.expressionCodegen.getContext();
    }

    /* JADX INFO: compiled from: LineNumberMapper.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\tHÆ\u0003J3\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/LineNumberMapper$DataForIrInlinedFunction;", "", "smap", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapCopier;", "inlinedBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "parentSmap", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "tryInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/TryWithFinallyInfo;", "(Lorg/jetbrains/kotlin/codegen/inline/SourceMapCopier;Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;Lorg/jetbrains/kotlin/backend/jvm/codegen/TryWithFinallyInfo;)V", "getInlinedBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "getParentSmap", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getSmap", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapCopier;", "getTryInfo", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/TryWithFinallyInfo;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class DataForIrInlinedFunction {
        private final IrInlinedFunctionBlock inlinedBlock;
        private final SourceMapper parentSmap;
        private final SourceMapCopier smap;
        private final TryWithFinallyInfo tryInfo;

        public static /* synthetic */ DataForIrInlinedFunction copy$default(DataForIrInlinedFunction dataForIrInlinedFunction, SourceMapCopier sourceMapCopier, IrInlinedFunctionBlock irInlinedFunctionBlock, SourceMapper sourceMapper, TryWithFinallyInfo tryWithFinallyInfo, int i, Object obj) {
            if ((i & 1) != 0) {
                sourceMapCopier = dataForIrInlinedFunction.smap;
            }
            if ((i & 2) != 0) {
                irInlinedFunctionBlock = dataForIrInlinedFunction.inlinedBlock;
            }
            if ((i & 4) != 0) {
                sourceMapper = dataForIrInlinedFunction.parentSmap;
            }
            if ((i & 8) != 0) {
                tryWithFinallyInfo = dataForIrInlinedFunction.tryInfo;
            }
            return dataForIrInlinedFunction.copy(sourceMapCopier, irInlinedFunctionBlock, sourceMapper, tryWithFinallyInfo);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final SourceMapCopier getSmap() {
            return this.smap;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final IrInlinedFunctionBlock getInlinedBlock() {
            return this.inlinedBlock;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final SourceMapper getParentSmap() {
            return this.parentSmap;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final TryWithFinallyInfo getTryInfo() {
            return this.tryInfo;
        }

        public final DataForIrInlinedFunction copy(SourceMapCopier smap, IrInlinedFunctionBlock inlinedBlock, SourceMapper parentSmap, TryWithFinallyInfo tryInfo) {
            Intrinsics.checkNotNullParameter(smap, "smap");
            Intrinsics.checkNotNullParameter(inlinedBlock, "inlinedBlock");
            Intrinsics.checkNotNullParameter(parentSmap, "parentSmap");
            return new DataForIrInlinedFunction(smap, inlinedBlock, parentSmap, tryInfo);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DataForIrInlinedFunction)) {
                return false;
            }
            DataForIrInlinedFunction dataForIrInlinedFunction = (DataForIrInlinedFunction) other;
            return Intrinsics.areEqual(this.smap, dataForIrInlinedFunction.smap) && Intrinsics.areEqual(this.inlinedBlock, dataForIrInlinedFunction.inlinedBlock) && Intrinsics.areEqual(this.parentSmap, dataForIrInlinedFunction.parentSmap) && Intrinsics.areEqual(this.tryInfo, dataForIrInlinedFunction.tryInfo);
        }

        public int hashCode() {
            int iHashCode = ((((this.smap.hashCode() * 31) + this.inlinedBlock.hashCode()) * 31) + this.parentSmap.hashCode()) * 31;
            TryWithFinallyInfo tryWithFinallyInfo = this.tryInfo;
            return iHashCode + (tryWithFinallyInfo == null ? 0 : tryWithFinallyInfo.hashCode());
        }

        public String toString() {
            return "DataForIrInlinedFunction(smap=" + this.smap + ", inlinedBlock=" + this.inlinedBlock + ", parentSmap=" + this.parentSmap + ", tryInfo=" + this.tryInfo + ')';
        }

        public DataForIrInlinedFunction(SourceMapCopier smap, IrInlinedFunctionBlock inlinedBlock, SourceMapper parentSmap, TryWithFinallyInfo tryWithFinallyInfo) {
            Intrinsics.checkNotNullParameter(smap, "smap");
            Intrinsics.checkNotNullParameter(inlinedBlock, "inlinedBlock");
            Intrinsics.checkNotNullParameter(parentSmap, "parentSmap");
            this.smap = smap;
            this.inlinedBlock = inlinedBlock;
            this.parentSmap = parentSmap;
            this.tryInfo = tryWithFinallyInfo;
        }

        public final SourceMapCopier getSmap() {
            return this.smap;
        }

        public final IrInlinedFunctionBlock getInlinedBlock() {
            return this.inlinedBlock;
        }

        public final SourceMapper getParentSmap() {
            return this.parentSmap;
        }

        public final TryWithFinallyInfo getTryInfo() {
            return this.tryInfo;
        }
    }

    private final Label markNewLabel() {
        Label label = new Label();
        this.expressionCodegen.getMv().visitLabel(label);
        return label;
    }

    public final void markLineNumber(IrElement element, boolean startOffset) {
        int lineNumberForOffset;
        Intrinsics.checkNotNullParameter(element, "element");
        if (this.noLineNumberScope) {
            return;
        }
        int startOffset2 = startOffset ? element.getStartOffset() : element.getEndOffset();
        if (startOffset2 >= 0 && this.lastLineNumber != (lineNumberForOffset = getLineNumberForOffset(startOffset2))) {
            this.lastLineNumber = lineNumberForOffset;
            this.expressionCodegen.getMv().visitLineNumber(lineNumberForOffset, markNewLabel());
        }
    }

    public final void noLineNumberScopeWithCondition$backend_jvm_codegen(boolean flag, Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean z = this.noLineNumberScope;
        this.noLineNumberScope = this.noLineNumberScope || flag;
        block.invoke();
        this.noLineNumberScope = z;
    }

    public final void noLineNumberScope(Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean z = this.noLineNumberScope;
        this.noLineNumberScope = true;
        block.invoke();
        this.noLineNumberScope = z;
    }

    public final void markLineNumberAfterInlineIfNeeded(boolean registerLineNumberAfterwards) {
        if (this.noLineNumberScope || registerLineNumberAfterwards) {
            if (this.lastLineNumber > -1) {
                Label label = new Label();
                this.expressionCodegen.getMv().visitLabel(label);
                this.expressionCodegen.getMv().visitLineNumber(this.lastLineNumber, label);
                return;
            }
            return;
        }
        this.lastLineNumber = -1;
    }

    /* JADX INFO: renamed from: getLineNumber, reason: from getter */
    public final int getLastLineNumber() {
        return this.lastLineNumber;
    }

    public final void resetLineNumber() {
        this.lastLineNumber = -1;
    }

    public final void beforeIrInline(IrInlinedFunctionBlock inlinedBlock) {
        Intrinsics.checkNotNullParameter(inlinedBlock, "inlinedBlock");
        if (IrInlineUtilsKt.isLambdaInlining(inlinedBlock)) {
            setUpAdditionalLineNumbersBeforeLambdaInlining(inlinedBlock);
        }
    }

    public final void afterIrInline(IrInlinedFunctionBlock inlinedBlock) {
        Intrinsics.checkNotNullParameter(inlinedBlock, "inlinedBlock");
        if (IrInlineUtilsKt.isFunctionInlining(inlinedBlock)) {
            Integer numValueOf = Integer.valueOf(getLineNumberForOffset(inlinedBlock.getInlineCall().getStartOffset()));
            numValueOf.intValue();
            if (this.noLineNumberScope) {
                numValueOf = null;
            }
            this.lastLineNumber = numValueOf != null ? numValueOf.intValue() : -1;
            markLineNumberAfterInlineIfNeeded(this.expressionCodegen.getIsInsideCondition());
            return;
        }
        setUpAdditionalLineNumbersAfterLambdaInlining(inlinedBlock);
    }

    public final void dropCurrentSmap() {
        CollectionsKt.removeFirst(this.smapStack);
    }

    private final int getLineNumberForOffset(int offset) {
        if (this.smapStack.isEmpty()) {
            return this.fileEntry.getLineNumber(offset) + 1;
        }
        Iterator<DataForIrInlinedFunction> it = this.smapStack.iterator();
        DataForIrInlinedFunction next = null;
        int iMapLineNumber = -1;
        while (it.hasNext()) {
            if (next != null && IrInlineUtilsKt.isLambdaInlining(next.getInlinedBlock())) {
                IrDeclaration declarationWhereGivenElementWasInlined = getDeclarationWhereGivenElementWasInlined(next.getInlinedBlock().getInlinedElement());
                while (it.hasNext() && !Intrinsics.areEqual(IrInlineUtilsKt.getInlineDeclaration(it.next().getInlinedBlock()), declarationWhereGivenElementWasInlined)) {
                }
                if (!it.hasNext()) {
                    break;
                }
            }
            next = it.next();
            IrFileEntry fileEntry = JvmIrInlineUtilsKt.getFileParentBeforeInline(getClassThatContainsDeclaration(next.getInlinedBlock())).getFileEntry();
            if (iMapLineNumber == -1) {
                iMapLineNumber = fileEntry.getLineNumber(offset) + 1;
            }
            iMapLineNumber = next.getSmap().mapLineNumber(iMapLineNumber);
        }
        return iMapLineNumber;
    }

    public final void buildSmapFor(IrInlinedFunctionBlock inlinedBlock, SMAP classSMAP, BlockInfo data) {
        IrFileEntry fileEntry;
        DataForIrInlinedFunction dataForIrInlinedFunction;
        IrInlinedFunctionBlock inlinedBlock2;
        IrDeclaration inlineDeclaration;
        IrFile fileParentBeforeInline;
        SourcePosition callSite;
        SourceMapper parent;
        SourceMapCopier smap;
        Intrinsics.checkNotNullParameter(inlinedBlock, "inlinedBlock");
        Intrinsics.checkNotNullParameter(classSMAP, "classSMAP");
        Intrinsics.checkNotNullParameter(data, "data");
        IrFunctionAccessExpression inlineCall = inlinedBlock.getInlineCall();
        if (IrInlineUtilsKt.isLambdaInlining(inlinedBlock)) {
            DataForIrInlinedFunction dataForIrInlinedFunction2 = (DataForIrInlinedFunction) CollectionsKt.firstOrNull((List) this.smapStack);
            Object obj = null;
            if (dataForIrInlinedFunction2 == null || (smap = dataForIrInlinedFunction2.getSmap()) == null || (callSite = smap.getCallSite()) == null || !isInvokeOnDefaultArg(inlinedBlock)) {
                callSite = null;
            }
            if (this.smapStack.isEmpty()) {
                parent = this.smap;
            } else {
                IrDeclaration declarationWhereGivenElementWasInlined = getDeclarationWhereGivenElementWasInlined(inlinedBlock.getInlinedElement());
                for (Object obj2 : this.smapStack) {
                    if (Intrinsics.areEqual(IrInlineUtilsKt.getInlineDeclaration(((DataForIrInlinedFunction) obj2).getInlinedBlock()), declarationWhereGivenElementWasInlined)) {
                        obj = obj2;
                        break;
                    }
                }
                DataForIrInlinedFunction dataForIrInlinedFunction3 = (DataForIrInlinedFunction) obj;
                if (dataForIrInlinedFunction3 == null) {
                    dataForIrInlinedFunction3 = (DataForIrInlinedFunction) CollectionsKt.first((List) this.smapStack);
                }
                parent = dataForIrInlinedFunction3.getSmap().getParent();
            }
            SourceMapCopier sourceMapCopier = new SourceMapCopier(parent, classSMAP, callSite);
            SourceMapper sourceMapper = IrCodegenUtilsKt.getSourceMapper(getContext(), getClassThatContainsDeclaration(inlinedBlock));
            Stack<ExpressionInfo> infos = data.getInfos();
            ArrayList arrayList = new ArrayList();
            for (Object obj3 : infos) {
                if (obj3 instanceof TryWithFinallyInfo) {
                    arrayList.add(obj3);
                }
            }
            dataForIrInlinedFunction = new DataForIrInlinedFunction(sourceMapCopier, inlinedBlock, sourceMapper, (TryWithFinallyInfo) CollectionsKt.lastOrNull((List) arrayList));
        } else {
            SourceMapper parentSmap = this.smapStack.isEmpty() ? this.smap : ((DataForIrInlinedFunction) CollectionsKt.first((List) this.smapStack)).getParentSmap();
            SourceInfo sourceInfo = parentSmap.getSourceInfo();
            Intrinsics.checkNotNull(sourceInfo);
            DataForIrInlinedFunction dataForIrInlinedFunction4 = (DataForIrInlinedFunction) CollectionsKt.firstOrNull((List) this.smapStack);
            if (dataForIrInlinedFunction4 == null || (inlinedBlock2 = dataForIrInlinedFunction4.getInlinedBlock()) == null || (inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(inlinedBlock2)) == null || (fileParentBeforeInline = JvmIrInlineUtilsKt.getFileParentBeforeInline(inlineDeclaration)) == null || (fileEntry = fileParentBeforeInline.getFileEntry()) == null) {
                fileEntry = this.fileEntry;
            }
            int lineNumber = inlineCall.getStartOffset() < 0 ? this.lastLineNumber : fileEntry.getLineNumber(inlineCall.getStartOffset()) + 1;
            String sourceFileName = sourceInfo.getSourceFileName();
            Intrinsics.checkNotNull(sourceFileName);
            SourceMapCopier sourceMapCopier2 = new SourceMapCopier(parentSmap, classSMAP, new SourcePosition(lineNumber, sourceFileName, sourceInfo.getPathOrCleanFQN()));
            SourceMapper sourceMapper2 = IrCodegenUtilsKt.getSourceMapper(getContext(), getClassThatContainsDeclaration(inlinedBlock));
            Stack<ExpressionInfo> infos2 = data.getInfos();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj4 : infos2) {
                if (obj4 instanceof TryWithFinallyInfo) {
                    arrayList2.add(obj4);
                }
            }
            dataForIrInlinedFunction = new DataForIrInlinedFunction(sourceMapCopier2, inlinedBlock, sourceMapper2, (TryWithFinallyInfo) CollectionsKt.lastOrNull((List) arrayList2));
        }
        this.smapStack.add(0, dataForIrInlinedFunction);
    }

    public final void stashSmapForGivenTry(TryWithFinallyInfo tryInfo, Function0<Unit> block) {
        int iNextIndex;
        Intrinsics.checkNotNullParameter(tryInfo, "tryInfo");
        Intrinsics.checkNotNullParameter(block, "block");
        int i = this.lastLineNumber;
        List<DataForIrInlinedFunction> list = this.smapStack;
        ListIterator<DataForIrInlinedFunction> listIterator = list.listIterator(list.size());
        while (true) {
            if (listIterator.hasPrevious()) {
                if (Intrinsics.areEqual(listIterator.previous().getTryInfo(), tryInfo)) {
                    iNextIndex = listIterator.nextIndex();
                    break;
                }
            } else {
                iNextIndex = -1;
                break;
            }
        }
        int i2 = iNextIndex + 1;
        if (i2 == 0) {
            block.invoke();
            return;
        }
        List listTake = CollectionsKt.take(this.smapStack, i2);
        List<DataForIrInlinedFunction> list2 = listTake;
        for (DataForIrInlinedFunction dataForIrInlinedFunction : list2) {
            dropCurrentSmap();
        }
        block.invoke();
        Iterator it = CollectionsKt.reversed(list2).iterator();
        while (it.hasNext()) {
            this.smapStack.add(0, (DataForIrInlinedFunction) it.next());
        }
        if (listTake.isEmpty()) {
            return;
        }
        this.lastLineNumber = i;
    }

    private final void setUpAdditionalLineNumbersBeforeLambdaInlining(IrInlinedFunctionBlock inlinedBlock) {
        SourceMapper parent;
        SourceMapCopier smap;
        IrBody body;
        List<IrStatement> statements;
        IrStatement irStatement;
        IrDeclaration inlineDeclaration;
        int lineNumberForOffset = getLineNumberForOffset(inlinedBlock.getInlineCall().getStartOffset());
        IrDeclaration inlineDeclaration2 = IrInlineUtilsKt.getInlineDeclaration(inlinedBlock);
        Object obj = null;
        IrFunction irFunction = inlineDeclaration2 instanceof IrFunction ? (IrFunction) inlineDeclaration2 : null;
        List<DataForIrInlinedFunction> list = this.smapStack;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((DataForIrInlinedFunction) it.next()).getInlinedBlock());
        }
        for (Object obj2 : arrayList) {
            if (!IrInlineUtilsKt.isLambdaInlining((IrInlinedFunctionBlock) obj2)) {
                obj = obj2;
                break;
            }
        }
        IrInlinedFunctionBlock irInlinedFunctionBlock = (IrInlinedFunctionBlock) obj;
        boolean z = false;
        if (irInlinedFunctionBlock != null && (inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock)) != null && JvmIrInlineUtilsKt.isInlineOnly(inlineDeclaration)) {
            z = true;
        }
        if (z) {
            SourcePosition callSite = ((DataForIrInlinedFunction) CollectionsKt.first((List) this.smapStack)).getSmap().getCallSite();
            Intrinsics.checkNotNull(callSite);
            lineNumberForOffset = callSite.getLine();
        }
        int lineNumber = (irFunction == null || (body = irFunction.getBody()) == null || (statements = IrUtilsKt.getStatements(body)) == null || (irStatement = (IrStatement) CollectionsKt.firstOrNull((List) statements)) == null) ? -1 : AdditionalIrUtilsKt.getFileEntry(IrInlineUtilsKt.getInlineDeclaration(inlinedBlock)).getLineNumber(irStatement.getStartOffset()) + 1;
        if (isInvokeOnDefaultArg(inlinedBlock) == z || lineNumberForOffset < 0 || lineNumber != lineNumberForOffset) {
            return;
        }
        Label label = new Label();
        DataForIrInlinedFunction dataForIrInlinedFunction = (DataForIrInlinedFunction) CollectionsKt.firstOrNull((List) this.smapStack);
        if (dataForIrInlinedFunction == null || (smap = dataForIrInlinedFunction.getSmap()) == null || (parent = smap.getParent()) == null) {
            parent = this.smap;
        }
        int iMapSyntheticLineNumber = parent.mapSyntheticLineNumber(1);
        this.expressionCodegen.getMv().visitLabel(label);
        this.expressionCodegen.getMv().visitLineNumber(iMapSyntheticLineNumber, label);
    }

    private final void setUpAdditionalLineNumbersAfterLambdaInlining(IrInlinedFunctionBlock inlinedBlock) {
        Object next;
        IrDeclaration inlineDeclaration;
        int lineNumberForOffset = getLineNumberForOffset(inlinedBlock.getInlineCall().getStartOffset());
        List<DataForIrInlinedFunction> list = this.smapStack;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((DataForIrInlinedFunction) it.next()).getInlinedBlock());
        }
        Iterator it2 = arrayList.iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (IrInlineUtilsKt.isLambdaInlining((IrInlinedFunctionBlock) next));
        IrInlinedFunctionBlock irInlinedFunctionBlock = (IrInlinedFunctionBlock) next;
        boolean z = false;
        if (irInlinedFunctionBlock != null && (inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock)) != null && JvmIrInlineUtilsKt.isInlineOnly(inlineDeclaration)) {
            z = true;
        }
        if (z) {
            SourcePosition callSite = ((DataForIrInlinedFunction) CollectionsKt.first((List) this.smapStack)).getSmap().getCallSite();
            Intrinsics.checkNotNull(callSite);
            lineNumberForOffset = callSite.getLine();
        }
        if (lineNumberForOffset != -1) {
            if (z) {
                this.expressionCodegen.getMv().visitLineNumber(lineNumberForOffset, markNewLabel());
            } else {
                markLineNumber(inlinedBlock.getInlineCall(), true);
            }
            this.expressionCodegen.getMv().nop();
        }
    }

    private final IrClass getClassThatContainsDeclaration(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        IrDeclaration inlineDeclaration;
        IrClass parentClassOrNull;
        Object next;
        if (irInlinedFunctionBlock.getInlinedElement() instanceof IrCallableReference) {
            List<DataForIrInlinedFunction> list = this.smapStack;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((DataForIrInlinedFunction) it.next()).getInlinedBlock());
            }
            Iterator it2 = arrayList.iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!IrInlineUtilsKt.isFunctionInlining((IrInlinedFunctionBlock) next));
            irInlinedFunctionBlock = (IrInlinedFunctionBlock) next;
        }
        return (irInlinedFunctionBlock == null || (inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock)) == null || (parentClassOrNull = IrUtilsKt.getParentClassOrNull(inlineDeclaration)) == null) ? IrUtilsKt.getParentAsClass(this.irFunction) : parentClassOrNull;
    }

    private final IrDeclaration getDeclarationWhereGivenElementWasInlined(IrElement inlinedElement) {
        List<DataForIrInlinedFunction> list = this.smapStack;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((DataForIrInlinedFunction) it.next()).getInlinedBlock());
        }
        return org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt.extractDeclarationWhereGivenElementWasInlined(arrayList, inlinedElement);
    }

    private final boolean isInvokeOnDefaultArg(IrInlinedFunctionBlock irInlinedFunctionBlock) {
        IrExpressionBody defaultValue;
        IrValueSymbol symbol;
        IrFunctionAccessExpression inlineCall = irInlinedFunctionBlock.getInlineCall();
        IrDeclaration inlineDeclaration = IrInlineUtilsKt.getInlineDeclaration(irInlinedFunctionBlock);
        if (!Intrinsics.areEqual(inlineCall.getSymbol().getOwner().getName(), OperatorNameConventions.INVOKE)) {
            return false;
        }
        IrExpression dispatchReceiver = inlineCall.getDispatchReceiver();
        IrGetValue irGetValue = dispatchReceiver instanceof IrGetValue ? (IrGetValue) dispatchReceiver : null;
        IrValueDeclaration owner = (irGetValue == null || (symbol = irGetValue.getSymbol()) == null) ? null : symbol.getOwner();
        IrValueParameter irValueParameter = owner instanceof IrValueParameter ? (IrValueParameter) owner : null;
        IrExpression expression = (irValueParameter == null || (defaultValue = irValueParameter.getDefaultValue()) == null) ? null : defaultValue.getExpression();
        IrFunctionExpression irFunctionExpression = expression instanceof IrFunctionExpression ? (IrFunctionExpression) expression : null;
        return Intrinsics.areEqual(irFunctionExpression != null ? irFunctionExpression.getFunction() : null, inlineDeclaration);
    }
}
