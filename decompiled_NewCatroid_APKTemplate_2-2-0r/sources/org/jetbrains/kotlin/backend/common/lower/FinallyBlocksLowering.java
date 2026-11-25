package org.jetbrains.kotlin.backend.common.lower;

import androidx.exifinterface.media.ExifInterface;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.DeepCopyIrTreeWithDeclarationsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrContinueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnableBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrReturnableBlockSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FinallyBlocksLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002:\bMNOPQRSTB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J>\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J>\u0010\u0011\u001a\u00020\u00122\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J:\u0010 \u001a\u0002H!\"\b\b\u0000\u0010\"*\u00020\f\"\u0004\b\u0001\u0010!2\u0006\u0010#\u001a\u0002H\"2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\"\u0012\u0004\u0012\u0002H!0\u0014H\u0082\b¢\u0006\u0002\u0010%J\u0010\u0010&\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020\u00122\u0006\u0010)\u001a\u000205H\u0016J\u0010\u00106\u001a\u00020\u00122\u0006\u00107\u001a\u000208H\u0016J&\u00109\u001a\u0002H:\"\n\b\u0000\u0010:\u0018\u0001*\u00020;*\u00020<2\u0006\u0010=\u001a\u0002H:H\u0082\b¢\u0006\u0002\u0010>J,\u0010?\u001a\u00020\u0012*\u00020<2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010C\u001a\u00020\u0012H\u0002J\u001a\u0010D\u001a\u00020E*\u00020<2\u0006\u0010F\u001a\u00020G2\u0006\u0010\u001b\u001a\u00020\u0012J6\u0010H\u001a\u00020I*\u00020<2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u00062\u0017\u0010J\u001a\u0013\u0012\u0004\u0012\u00020K\u0012\u0004\u0012\u00020\u000e0\u0014¢\u0006\u0002\bLH\u0082\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006U"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "throwableType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "otherScopeStack", "", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Scope;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "performHighLevelJump", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "targetScopePredicate", "Lkotlin/Function1;", "", "jump", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$HighLevelJump;", "startOffset", "", "endOffset", "value", "tryScopes", "", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$TryScope;", "index", "using", "R", ExifInterface.LATITUDE_SOUTH, "scope", "block", "(Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Scope;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "visitBreak", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitContainerExpression", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitFunctionNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;Lorg/jetbrains/kotlin/ir/IrElement;)Lorg/jetbrains/kotlin/ir/IrElement;", "irInlineFinally", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", ModuleXmlParser.TYPE, "finallyExpression", "irReturn", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrReturnImpl;", "target", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "irReturnableBlock", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrReturnableBlockImpl;", "body", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "Lkotlin/ExtensionFunctionType;", "Break", "Continue", "HighLevelJump", "LoopScope", "Return", "ReturnableScope", "Scope", "TryScope", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FinallyBlocksLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final CommonBackendContext context;
    private final List<Scope> otherScopeStack;
    private final IrType throwableType;

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bb\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$HighLevelJump;", "", "toIr", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "startOffset", "", "endOffset", "value", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface HighLevelJump {
        IrExpression toIr(CommonBackendContext context, int startOffset, int endOffset, IrExpression value);
    }

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\"\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Scope;", "", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class Scope {
    }

    public FinallyBlocksLowering(CommonBackendContext context, IrType throwableType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(throwableType, "throwableType");
        this.context = context;
        this.throwableType = throwableType;
        this.otherScopeStack = new ArrayList();
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J(\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Return;", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$HighLevelJump;", "target", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;)V", "getTarget", "()Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toIr", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrReturnImpl;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "startOffset", "endOffset", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Return implements HighLevelJump {
        private final IrReturnTargetSymbol target;

        public static /* synthetic */ Return copy$default(Return r0, IrReturnTargetSymbol irReturnTargetSymbol, int i, Object obj) {
            if ((i & 1) != 0) {
                irReturnTargetSymbol = r0.target;
            }
            return r0.copy(irReturnTargetSymbol);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrReturnTargetSymbol getTarget() {
            return this.target;
        }

        public final Return copy(IrReturnTargetSymbol target) {
            Intrinsics.checkNotNullParameter(target, "target");
            return new Return(target);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Return) && Intrinsics.areEqual(this.target, ((Return) other).target);
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public String toString() {
            return "Return(target=" + this.target + ')';
        }

        public Return(IrReturnTargetSymbol target) {
            Intrinsics.checkNotNullParameter(target, "target");
            this.target = target;
        }

        public final IrReturnTargetSymbol getTarget() {
            return this.target;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.FinallyBlocksLowering.HighLevelJump
        public IrReturnImpl toIr(CommonBackendContext context, int startOffset, int endOffset, IrExpression value) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(value, "value");
            return new IrReturnImpl(startOffset, endOffset, context.getIrBuiltIns().getNothingType(), this.target, value);
        }
    }

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J(\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Break;", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$HighLevelJump;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;)V", "getLoop", "()Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toIr", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCompositeImpl;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "startOffset", "endOffset", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Break implements HighLevelJump {
        private final IrLoop loop;

        public static /* synthetic */ Break copy$default(Break r0, IrLoop irLoop, int i, Object obj) {
            if ((i & 1) != 0) {
                irLoop = r0.loop;
            }
            return r0.copy(irLoop);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrLoop getLoop() {
            return this.loop;
        }

        public final Break copy(IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return new Break(loop);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Break) && Intrinsics.areEqual(this.loop, ((Break) other).loop);
        }

        public int hashCode() {
            return this.loop.hashCode();
        }

        public String toString() {
            return "Break(loop=" + this.loop + ')';
        }

        public Break(IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            this.loop = loop;
        }

        public final IrLoop getLoop() {
            return this.loop;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.FinallyBlocksLowering.HighLevelJump
        public IrCompositeImpl toIr(CommonBackendContext context, int startOffset, int endOffset, IrExpression value) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(value, "value");
            return new IrCompositeImpl(startOffset, endOffset, context.getIrBuiltIns().getUnitType(), null, CollectionsKt.listOf((Object[]) new IrExpression[]{value, new IrBreakImpl(startOffset, endOffset, context.getIrBuiltIns().getNothingType(), this.loop)}));
        }
    }

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J(\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Continue;", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$HighLevelJump;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;)V", "getLoop", "()Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toIr", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCompositeImpl;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "startOffset", "endOffset", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Continue implements HighLevelJump {
        private final IrLoop loop;

        public static /* synthetic */ Continue copy$default(Continue r0, IrLoop irLoop, int i, Object obj) {
            if ((i & 1) != 0) {
                irLoop = r0.loop;
            }
            return r0.copy(irLoop);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrLoop getLoop() {
            return this.loop;
        }

        public final Continue copy(IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return new Continue(loop);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Continue) && Intrinsics.areEqual(this.loop, ((Continue) other).loop);
        }

        public int hashCode() {
            return this.loop.hashCode();
        }

        public String toString() {
            return "Continue(loop=" + this.loop + ')';
        }

        public Continue(IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            this.loop = loop;
        }

        public final IrLoop getLoop() {
            return this.loop;
        }

        @Override // org.jetbrains.kotlin.backend.common.lower.FinallyBlocksLowering.HighLevelJump
        public IrCompositeImpl toIr(CommonBackendContext context, int startOffset, int endOffset, IrExpression value) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(value, "value");
            return new IrCompositeImpl(startOffset, endOffset, context.getIrBuiltIns().getUnitType(), null, CollectionsKt.listOf((Object[]) new IrExpression[]{value, new IrContinueImpl(startOffset, endOffset, context.getIrBuiltIns().getNothingType(), this.loop)}));
        }
    }

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$ReturnableScope;", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Scope;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;)V", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ReturnableScope extends Scope {
        private final IrReturnTargetSymbol symbol;

        public ReturnableScope(IrReturnTargetSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            this.symbol = symbol;
        }

        public final IrReturnTargetSymbol getSymbol() {
            return this.symbol;
        }
    }

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$LoopScope;", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Scope;", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;)V", "getLoop", "()Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class LoopScope extends Scope {
        private final IrLoop loop;

        public LoopScope(IrLoop loop) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            this.loop = loop;
        }

        public final IrLoop getLoop() {
            return this.loop;
        }
    }

    /* JADX INFO: compiled from: FinallyBlocksLowering.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$TryScope;", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$Scope;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "finallyExpression", "irBuilder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getFinallyExpression", "getIrBuilder", "()Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "jumps", "", "Lorg/jetbrains/kotlin/backend/common/lower/FinallyBlocksLowering$HighLevelJump;", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "getJumps", "()Ljava/util/Map;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TryScope extends Scope {
        private IrExpression expression;
        private final IrExpression finallyExpression;
        private final IrBuilderWithScope irBuilder;
        private final Map<HighLevelJump, IrReturnTargetSymbol> jumps;

        public final IrExpression getExpression() {
            return this.expression;
        }

        public final void setExpression(IrExpression irExpression) {
            Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
            this.expression = irExpression;
        }

        public final IrExpression getFinallyExpression() {
            return this.finallyExpression;
        }

        public final IrBuilderWithScope getIrBuilder() {
            return this.irBuilder;
        }

        public TryScope(IrExpression expression, IrExpression finallyExpression, IrBuilderWithScope irBuilder) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(finallyExpression, "finallyExpression");
            Intrinsics.checkNotNullParameter(irBuilder, "irBuilder");
            this.expression = expression;
            this.finallyExpression = finallyExpression;
            this.irBuilder = irBuilder;
            this.jumps = new LinkedHashMap();
        }

        public final Map<HighLevelJump, IrReturnTargetSymbol> getJumps() {
            return this.jumps;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitFunctionNew(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        ReturnableScope returnableScope = new ReturnableScope(declaration.getSymbol());
        UtilsKt.push(this.otherScopeStack, returnableScope);
        try {
            return super.visitFunctionNew(declaration);
        } finally {
            UtilsKt.pop(this.otherScopeStack);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!(expression instanceof IrReturnableBlockImpl)) {
            return super.visitContainerExpression(expression);
        }
        ReturnableScope returnableScope = new ReturnableScope(((IrReturnableBlockImpl) expression).getSymbol());
        UtilsKt.push(this.otherScopeStack, returnableScope);
        try {
            return super.visitContainerExpression(expression);
        } finally {
            UtilsKt.pop(this.otherScopeStack);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitLoop(IrLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        LoopScope loopScope = new LoopScope(loop);
        UtilsKt.push(this.otherScopeStack, loopScope);
        try {
            return super.visitLoop(loop);
        } finally {
            UtilsKt.pop(this.otherScopeStack);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitBreak(final IrBreak jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        int startOffset = jump.getStartOffset();
        int endOffset = jump.getEndOffset();
        CommonBackendContext commonBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        IrExpression irExpressionPerformHighLevelJump = performHighLevelJump(new Function1<Scope, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.FinallyBlocksLowering.visitBreak.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Scope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof LoopScope) && Intrinsics.areEqual(((LoopScope) it).getLoop(), jump.getLoop()));
            }
        }, new Break(jump.getLoop()), startOffset, endOffset, IrBuilderKt.irGetObject(LowerUtilsKt.createIrBuilder(commonBackendContext, currentScope.getScope().getScopeOwnerSymbol(), startOffset, endOffset), this.context.getIrBuiltIns().getUnitClass()));
        return irExpressionPerformHighLevelJump == null ? jump : irExpressionPerformHighLevelJump;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContinue(final IrContinue jump) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        int startOffset = jump.getStartOffset();
        int endOffset = jump.getEndOffset();
        CommonBackendContext commonBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        IrExpression irExpressionPerformHighLevelJump = performHighLevelJump(new Function1<Scope, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.FinallyBlocksLowering.visitContinue.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Scope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof LoopScope) && Intrinsics.areEqual(((LoopScope) it).getLoop(), jump.getLoop()));
            }
        }, new Continue(jump.getLoop()), startOffset, endOffset, IrBuilderKt.irGetObject(LowerUtilsKt.createIrBuilder(commonBackendContext, currentScope.getScope().getScopeOwnerSymbol(), startOffset, endOffset), this.context.getIrBuiltIns().getUnitClass()));
        return irExpressionPerformHighLevelJump == null ? jump : irExpressionPerformHighLevelJump;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(final IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        IrExpression irExpressionPerformHighLevelJump = performHighLevelJump(new Function1<Scope, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.FinallyBlocksLowering.visitReturn.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Scope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof ReturnableScope) && Intrinsics.areEqual(((ReturnableScope) it).getSymbol(), expression.getReturnTargetSymbol()));
            }
        }, new Return(expression.getReturnTargetSymbol()), expression.getStartOffset(), expression.getEndOffset(), expression.getValue());
        return irExpressionPerformHighLevelJump == null ? expression : irExpressionPerformHighLevelJump;
    }

    private final IrExpression performHighLevelJump(Function1<? super Scope, Boolean> targetScopePredicate, HighLevelJump jump, int startOffset, int endOffset, IrExpression value) {
        List listReversed = CollectionsKt.reversed(this.otherScopeStack);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listReversed) {
            if (targetScopePredicate.invoke((Scope) obj).booleanValue()) {
                break;
            }
            arrayList.add(obj);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (obj2 instanceof TryScope) {
                arrayList2.add(obj2);
            }
        }
        List<TryScope> list = CollectionsKt.toList(arrayList2);
        if (list.isEmpty()) {
            return null;
        }
        return performHighLevelJump(list, 0, jump, startOffset, endOffset, value);
    }

    private final IrExpression performHighLevelJump(List<TryScope> tryScopes, int index, HighLevelJump jump, int startOffset, int endOffset, IrExpression value) {
        IrType type;
        IrReturnTargetSymbol target;
        IrReturnTarget owner;
        if (index == tryScopes.size()) {
            return jump.toIr(this.context, startOffset, endOffset, value);
        }
        TryScope tryScope = tryScopes.get(index);
        Map<HighLevelJump, IrReturnTargetSymbol> jumps = tryScope.getJumps();
        IrReturnableBlockSymbolImpl irReturnableBlockSymbolImpl = jumps.get(jump);
        if (irReturnableBlockSymbolImpl == null) {
            Return r0 = jump instanceof Return ? (Return) jump : null;
            if (r0 == null || (target = r0.getTarget()) == null || (owner = target.getOwner()) == null || (type = IrUtilsKt.returnType(owner, this.context)) == null) {
                type = value.getType();
            }
            jump.toString();
            IrReturnableBlockSymbolImpl irReturnableBlockSymbolImpl2 = new IrReturnableBlockSymbolImpl(null, 1, null);
            tryScope.setExpression(performHighLevelJump(tryScopes, index + 1, jump, startOffset, endOffset, irInlineFinally(tryScope.getIrBuilder(), irReturnableBlockSymbolImpl2, type, tryScope.getExpression(), tryScope.getFinallyExpression())));
            irReturnableBlockSymbolImpl = irReturnableBlockSymbolImpl2;
            jumps.put(jump, irReturnableBlockSymbolImpl);
        }
        return new IrReturnImpl(startOffset, endOffset, this.context.getIrBuiltIns().getNothingType(), irReturnableBlockSymbolImpl, value);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTry(IrTry aTry) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        IrExpression finallyExpression = aTry.getFinallyExpression();
        if (finallyExpression == null) {
            return super.visitTry(aTry);
        }
        int startOffset = aTry.getStartOffset();
        int endOffset = aTry.getEndOffset();
        CommonBackendContext commonBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(commonBackendContext, currentScope.getScope().getScopeOwnerSymbol(), startOffset, endOffset);
        IrExpression irExpressionTransform = finallyExpression.transform((IrElementTransformer<? super Object>) this, (Object) null);
        IrDeclarationParent localDeclarationParent = declarationIrBuilderCreateIrBuilder.getScope().getLocalDeclarationParent();
        IrDeclarationOrigin.CATCH_PARAMETER catch_parameter = IrDeclarationOrigin.CATCH_PARAMETER.INSTANCE;
        Name nameIdentifier = Name.identifier("t");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrVariable irVariableBuildVariable = DeclarationBuildersKt.buildVariable(localDeclarationParent, startOffset, endOffset, catch_parameter, nameIdentifier, this.throwableType, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
        IrTryImpl irTryImpl = new IrTryImpl(startOffset, endOffset, declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getNothingType());
        List<IrCatch> catches = irTryImpl.getCatches();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset(), null, null, true);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        irBlockBuilder.unaryPlus((IrStatement) org.jetbrains.kotlin.ir.util.IrUtilsKt.setDeclarationsParent(DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables(finallyExpression), ExpressionHelpersKt.getParent(irBlockBuilder2)));
        irBlockBuilder.unaryPlus(LowerUtilsKt.irThrow(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableBuildVariable)));
        Unit unit = Unit.INSTANCE;
        catches.add(LowerUtilsKt.irCatch(declarationIrBuilder, irVariableBuildVariable, irBlockBuilder.getIrBlockBody()));
        irTryImpl.setFinallyExpression(null);
        TryScope tryScope = new TryScope(irTryImpl, irExpressionTransform, declarationIrBuilder);
        UtilsKt.push(this.otherScopeStack, tryScope);
        try {
            TryScope tryScope2 = tryScope;
            IrType type = aTry.getType();
            IrReturnableBlockSymbolImpl irReturnableBlockSymbolImpl = new IrReturnableBlockSymbolImpl(null, 1, null);
            IrReturnImpl irReturnImplIrReturn = irReturn(declarationIrBuilderCreateIrBuilder, irReturnableBlockSymbolImpl, aTry.getTryResult().transform((IrElementTransformer<? super Object>) this, (Object) null));
            if (!aTry.getCatches().isEmpty()) {
                IrTryImpl irTryImpl2 = new IrTryImpl(startOffset, endOffset, declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getNothingType());
                irTryImpl2.setTryResult(irReturnImplIrReturn);
                Iterator<IrCatch> it = aTry.getCatches().iterator();
                while (it.hasNext()) {
                    IrCatch irCatchTransform = it.next().transform((IrElementTransformer<? super Object>) this, (Object) null);
                    irCatchTransform.setResult(irReturn(declarationIrBuilderCreateIrBuilder, irReturnableBlockSymbolImpl, irCatchTransform.getResult()));
                    irTryImpl2.getCatches().add(irCatchTransform);
                }
                irTryImpl.setTryResult(irTryImpl2);
            } else {
                irTryImpl.setTryResult(irReturnImplIrReturn);
            }
            return irInlineFinally(declarationIrBuilderCreateIrBuilder, irReturnableBlockSymbolImpl, type, tryScope2.getExpression(), tryScope2.getFinallyExpression());
        } finally {
            UtilsKt.pop(this.otherScopeStack);
        }
    }

    private final IrExpression irInlineFinally(IrBuilderWithScope irBuilderWithScope, IrReturnableBlockSymbol irReturnableBlockSymbol, IrType irType, IrExpression irExpression, IrExpression irExpression2) {
        if (IrTypePredicatesKt.isUnit(irType) || IrTypePredicatesKt.isNothing(irType)) {
            IrBuilderWithScope irBuilderWithScope2 = irBuilderWithScope;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope2.getContext(), irBuilderWithScope2.getScope(), irExpression.getStartOffset(), irExpression.getEndOffset(), null, irType, false, 64, null);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            int startOffset = irBlockBuilder2.getStartOffset();
            int endOffset = irBlockBuilder2.getEndOffset();
            IrBlockBuilder irBlockBuilder3 = new IrBlockBuilder(irBlockBuilder2.getContext(), irBlockBuilder2.getScope(), irBlockBuilder2.getStartOffset(), irBlockBuilder2.getEndOffset(), null, irType, true);
            irBlockBuilder3.unaryPlus(irExpression);
            Unit unit = Unit.INSTANCE;
            irBlockBuilder.unaryPlus(new IrReturnableBlockImpl(startOffset, endOffset, irType, irReturnableBlockSymbol, null, irBlockBuilder3.getIrBlockBody().getStatements()));
            IrType unitType = irBlockBuilder.getContext().getIrBuiltIns().getUnitType();
            FINALLY_EXPRESSION finally_expression = FINALLY_EXPRESSION.INSTANCE;
            IrBlockBuilder irBlockBuilder4 = new IrBlockBuilder(irBlockBuilder2.getContext(), irBlockBuilder2.getScope(), irBlockBuilder2.getStartOffset(), irBlockBuilder2.getEndOffset(), finally_expression, unitType, true);
            irBlockBuilder4.unaryPlus((IrStatement) org.jetbrains.kotlin.ir.util.IrUtilsKt.setDeclarationsParent(DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables(irExpression2), ExpressionHelpersKt.getParent(irBlockBuilder4)));
            irBlockBuilder.unaryPlus(irBlockBuilder4.getIrBlockBody());
            return irBlockBuilder.getIrBlockBody();
        }
        IrBuilderWithScope irBuilderWithScope3 = irBuilderWithScope;
        IrBlockBuilder irBlockBuilder5 = new IrBlockBuilder(irBuilderWithScope3.getContext(), irBuilderWithScope3.getScope(), irExpression.getStartOffset(), irExpression.getEndOffset(), null, irType, false, 64, null);
        IrBlockBuilder irBlockBuilder6 = irBlockBuilder5;
        int startOffset2 = irBlockBuilder6.getStartOffset();
        int endOffset2 = irBlockBuilder6.getEndOffset();
        IrBlockBuilder irBlockBuilder7 = new IrBlockBuilder(irBlockBuilder6.getContext(), irBlockBuilder6.getScope(), irBlockBuilder6.getStartOffset(), irBlockBuilder6.getEndOffset(), null, irType, true);
        irBlockBuilder7.unaryPlus(irReturn(irBlockBuilder7, irReturnableBlockSymbol, irExpression));
        Unit unit2 = Unit.INSTANCE;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder5, (IrExpression) new IrReturnableBlockImpl(startOffset2, endOffset2, irType, irReturnableBlockSymbol, null, irBlockBuilder7.getIrBlockBody().getStatements()), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
        IrType unitType2 = irBlockBuilder5.getContext().getIrBuiltIns().getUnitType();
        FINALLY_EXPRESSION finally_expression2 = FINALLY_EXPRESSION.INSTANCE;
        IrBlockBuilder irBlockBuilder8 = new IrBlockBuilder(irBlockBuilder6.getContext(), irBlockBuilder6.getScope(), irBlockBuilder6.getStartOffset(), irBlockBuilder6.getEndOffset(), finally_expression2, unitType2, true);
        irBlockBuilder8.unaryPlus((IrStatement) org.jetbrains.kotlin.ir.util.IrUtilsKt.setDeclarationsParent(DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables(irExpression2), ExpressionHelpersKt.getParent(irBlockBuilder8)));
        irBlockBuilder5.unaryPlus(irBlockBuilder8.getIrBlockBody());
        irBlockBuilder5.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder6, irVariableCreateTmpVariable$default));
        return irBlockBuilder5.getIrBlockBody();
    }

    public final IrReturnImpl irReturn(IrBuilderWithScope irBuilderWithScope, IrReturnTargetSymbol target, IrExpression value) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(value, "value");
        return new IrReturnImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), irBuilderWithScope.getContext().getIrBuiltIns().getNothingType(), target, value);
    }
}
