package org.jetbrains.kotlin.js.coroutine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDebugger;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: CoroutineBodyTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ô\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0002deB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0&2\u0006\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020 H\u0002J\b\u0010)\u001a\u00020*H\u0002J\u0012\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010-\u001a\u00020,H\u0002J\u0018\u0010.\u001a\u00020*2\u0006\u0010/\u001a\u00020,2\u0006\u00100\u001a\u00020,H\u0002J\b\u00101\u001a\u00020\u001aH\u0002J\b\u00102\u001a\u00020\u0007H\u0002J \u00103\u001a\u00020*2\u0006\u00104\u001a\u00020\u00142\u0006\u00105\u001a\u00020\r2\u0006\u0010(\u001a\u00020 H\u0002J\f\u00106\u001a\b\u0012\u0004\u0012\u00020\r0&J\u000e\u00107\u001a\u00020*2\u0006\u00108\u001a\u00020 J\u0016\u00109\u001a\b\u0012\u0004\u0012\u00020\r0&2\u0006\u00104\u001a\u00020\u0014H\u0002J\u001f\u0010:\u001a\u00020*2\u0006\u0010;\u001a\u00020\b2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020*0=H\u0082\bJ\u001e\u0010>\u001a\b\u0012\u0004\u0012\u00020\b0&2\u0006\u0010'\u001a\u00020\r2\u0006\u0010?\u001a\u00020 H\u0002J\u001e\u0010@\u001a\b\u0012\u0004\u0012\u00020\b0&2\u0006\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020 H\u0002J\u001c\u0010A\u001a\b\u0012\u0004\u0012\u00020\b0&2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\r0&H\u0002J\u0010\u0010C\u001a\u00020*2\u0006\u0010D\u001a\u00020EH\u0016J\u0010\u0010F\u001a\u00020*2\u0006\u0010D\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020*2\u0006\u0010D\u001a\u00020IH\u0016J\u0010\u0010J\u001a\u00020*2\u0006\u0010D\u001a\u00020\u0007H\u0016J\u0010\u0010K\u001a\u00020*2\u0006\u0010D\u001a\u00020LH\u0016J\u0010\u0010M\u001a\u00020*2\u0006\u0010D\u001a\u00020NH\u0016J\u0010\u0010O\u001a\u00020*2\u0006\u0010D\u001a\u00020PH\u0016J\u0010\u0010Q\u001a\u00020*2\u0006\u0010D\u001a\u00020RH\u0016J\u0010\u0010S\u001a\u00020*2\u0006\u0010D\u001a\u00020TH\u0016J\u0010\u0010U\u001a\u00020*2\u0006\u0010D\u001a\u00020VH\u0016J\u0010\u0010W\u001a\u00020*2\u0006\u0010D\u001a\u00020XH\u0016J\u0010\u0010Y\u001a\u00020*2\u0006\u0010D\u001a\u00020ZH\u0016J\u0010\u0010[\u001a\u00020*2\u0006\u0010D\u001a\u00020\\H\u0016J\u0010\u0010]\u001a\u00020*2\u0006\u0010D\u001a\u00020^H\u0016J\u0010\u0010_\u001a\u00020*2\u0006\u0010D\u001a\u00020`H\u0016J2\u0010a\u001a\u00020*2\u0006\u0010;\u001a\u00020\b2\u0006\u0010b\u001a\u00020\r2\n\b\u0002\u0010c\u001a\u0004\u0018\u00010\r2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020*0=H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001a8F@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006f"}, d2 = {"Lorg/jetbrains/kotlin/js/coroutine/CoroutineBodyTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "context", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineTransformationContext;", "(Lorg/jetbrains/kotlin/js/coroutine/CoroutineTransformationContext;)V", "breakContinueTargetStatements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "breakTargets", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBodyTransformer$JumpTarget;", "continueTargets", "currentBlock", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "currentCatchBlock", "currentStatements", "", "getCurrentStatements", "()Ljava/util/List;", "currentTryDepth", "", "getCurrentTryDepth", "()I", "entryBlock", "globalCatchBlock", "<set-?>", "", "hasFinallyBlocks", "getHasFinallyBlocks", "()Z", "nodesToSplit", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "referencedBlocks", "", "tryStack", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBodyTransformer$TryBlock;", "exceptionState", "", "target", "fromNode", "generateFinallyExit", "", "handleExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "expression", "handleSuspend", "invocation", "sourceNode", "hasEnclosingFinallyBlock", "jump", "jumpWithFinally", "targetTryDepth", "successor", "postProcess", "preProcess", "node", "relativeFinallyPath", "splitIfNecessary", "statement", "action", "Lkotlin/Function0;", "state", "fromExpression", "stateAndJump", "updateFinallyPath", "path", "visit", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsSwitch;", "visitBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "visitBreak", "Lorg/jetbrains/kotlin/js/backend/ast/JsBreak;", "visitContinue", "visitDebugger", "Lorg/jetbrains/kotlin/js/backend/ast/JsDebugger;", "visitDoWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", "visitExpressionStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "visitFor", "Lorg/jetbrains/kotlin/js/backend/ast/JsFor;", "visitIf", "Lorg/jetbrains/kotlin/js/backend/ast/JsIf;", "visitLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "visitReturn", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "visitThrow", "Lorg/jetbrains/kotlin/js/backend/ast/JsThrow;", "visitTry", "Lorg/jetbrains/kotlin/js/backend/ast/JsTry;", "visitVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "visitWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;", "withBreakAndContinue", "breakBlock", "continueBlock", "JumpTarget", "TryBlock", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineBodyTransformer extends RecursiveJsVisitor {
    private final Map<JsContinue, JsStatement> breakContinueTargetStatements;
    private final Map<JsStatement, JumpTarget> breakTargets;
    private final CoroutineTransformationContext context;
    private final Map<JsStatement, JumpTarget> continueTargets;
    private CoroutineBlock currentBlock;
    private CoroutineBlock currentCatchBlock;
    private final CoroutineBlock entryBlock;
    private final CoroutineBlock globalCatchBlock;
    private boolean hasFinallyBlocks;
    private Set<? extends JsNode> nodesToSplit;
    private final Set<CoroutineBlock> referencedBlocks;
    private final List<TryBlock> tryStack;

    public CoroutineBodyTransformer(CoroutineTransformationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        CoroutineBlock entryBlock = context.getEntryBlock();
        this.entryBlock = entryBlock;
        CoroutineBlock globalCatchBlock = context.getGlobalCatchBlock();
        this.globalCatchBlock = globalCatchBlock;
        this.currentBlock = entryBlock;
        this.breakContinueTargetStatements = new LinkedHashMap();
        this.breakTargets = new LinkedHashMap();
        this.continueTargets = new LinkedHashMap();
        this.referencedBlocks = new LinkedHashSet();
        this.currentCatchBlock = globalCatchBlock;
        this.tryStack = CollectionsKt.mutableListOf(new TryBlock(globalCatchBlock, null));
    }

    private final List<JsStatement> getCurrentStatements() {
        return this.currentBlock.getStatements();
    }

    public final boolean getHasFinallyBlocks() {
        return this.hasFinallyBlocks;
    }

    private final int getCurrentTryDepth() {
        return CollectionsKt.getLastIndex(this.tryStack);
    }

    public final void preProcess(JsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        this.breakContinueTargetStatements.putAll(CollectUtilsKt.collectBreakContinueTargets(node));
        this.nodesToSplit = CoroutinePassesKt.collectNodesToSplit(node, this.breakContinueTargetStatements);
    }

    public final List<CoroutineBlock> postProcess() {
        this.currentBlock.getStatements().add(new JsReturn());
        final Map<CoroutineBlock, Set<CoroutineBlock>> mapBuildGraph = CoroutinePassesKt.buildGraph(this.entryBlock, this.globalCatchBlock);
        List<CoroutineBlock> list = DFS.topologicalOrder(CollectionsKt.listOf(this.entryBlock), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineBodyTransformer$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return CoroutineBodyTransformer.postProcess$lambda$0(mapBuildGraph, (CoroutineBlock) obj);
            }
        });
        Intrinsics.checkNotNull(list);
        CoroutinePassesKt.replaceCoroutineFlowStatements(list, this.context);
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable postProcess$lambda$0(Map graph, CoroutineBlock coroutineBlock) {
        Intrinsics.checkNotNullParameter(graph, "$graph");
        Set setEmptySet = (Set) graph.get(coroutineBlock);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        return setEmptySet;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitLabel(JsLabel x) {
        Intrinsics.checkNotNullParameter(x, "x");
        final JsStatement statement = x.getStatement();
        Set<? extends JsNode> set = null;
        if (statement instanceof JsWhile ? true : statement instanceof JsDoWhile ? true : statement instanceof JsFor) {
            Set<? extends JsNode> set2 = this.nodesToSplit;
            if (set2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            } else {
                set = set2;
            }
            if (set.contains(x)) {
                statement.accept(this);
                return;
            } else {
                getCurrentStatements().add(x);
                return;
            }
        }
        Set<? extends JsNode> set3 = this.nodesToSplit;
        if (set3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set3 = null;
        }
        JsLabel jsLabel = x;
        if (set3.contains(jsLabel)) {
            CoroutineBlock coroutineBlock = new CoroutineBlock();
            JsStatement statement2 = x.getStatement();
            Intrinsics.checkNotNullExpressionValue(statement2, "getStatement(...)");
            withBreakAndContinue(statement2, coroutineBlock, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineBodyTransformer$visitLabel$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.accept(statement);
                }
            });
            if (this.referencedBlocks.contains(coroutineBlock)) {
                CollectionsKt.addAll(this.currentBlock.getStatements(), stateAndJump(coroutineBlock, x));
                this.currentBlock = coroutineBlock;
                return;
            }
            return;
        }
        getCurrentStatements().add(jsLabel);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBreak(JsBreak x) {
        Intrinsics.checkNotNullParameter(x, "x");
        JsStatement jsStatement = this.breakContinueTargetStatements.get(x);
        Intrinsics.checkNotNull(jsStatement);
        JumpTarget jumpTarget = this.breakTargets.get(jsStatement);
        Intrinsics.checkNotNull(jumpTarget);
        JumpTarget jumpTarget2 = jumpTarget;
        CoroutineBlock block = jumpTarget2.getBlock();
        int tryDepth = jumpTarget2.getTryDepth();
        this.referencedBlocks.add(block);
        jumpWithFinally(tryDepth + 1, block, x);
        getCurrentStatements().add(jump());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitContinue(JsContinue x) {
        Intrinsics.checkNotNullParameter(x, "x");
        JsStatement jsStatement = this.breakContinueTargetStatements.get(x);
        Intrinsics.checkNotNull(jsStatement);
        JumpTarget jumpTarget = this.continueTargets.get(jsStatement);
        Intrinsics.checkNotNull(jumpTarget);
        JumpTarget jumpTarget2 = jumpTarget;
        CoroutineBlock block = jumpTarget2.getBlock();
        int tryDepth = jumpTarget2.getTryDepth();
        this.referencedBlocks.add(block);
        jumpWithFinally(tryDepth + 1, block, x);
        getCurrentStatements().add(jump());
    }

    private final void jumpWithFinally(int targetTryDepth, CoroutineBlock successor, JsNode fromNode) {
        if (targetTryDepth < this.tryStack.size()) {
            CollectionsKt.addAll(getCurrentStatements(), exceptionState(this.tryStack.get(targetTryDepth).getCatchBlock(), fromNode));
        }
        List listPlus = CollectionsKt.plus((Collection<? extends CoroutineBlock>) relativeFinallyPath(targetTryDepth), successor);
        if (listPlus.size() > 1) {
            CollectionsKt.addAll(getCurrentStatements(), updateFinallyPath(CollectionsKt.drop(listPlus, 1)));
        }
        CollectionsKt.addAll(getCurrentStatements(), state((CoroutineBlock) listPlus.get(0), fromNode));
    }

    private final void generateFinallyExit() {
        JsNameRef jsNameRef = new JsNameRef(this.context.getMetadata().getFinallyPathName(), JsAstUtils.stateMachineReceiver());
        getCurrentStatements().add(JsAstUtils.assignment(new JsNameRef(this.context.getMetadata().getStateName(), JsAstUtils.stateMachineReceiver()), new JsInvocation(new JsNameRef("shift", jsNameRef), new JsExpression[0])).makeStmt());
        getCurrentStatements().add(jump());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitExpressionStatement(JsExpressionStatement x) {
        Intrinsics.checkNotNullParameter(x, "x");
        JsExpression expression = x.getExpression();
        Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
        JsExpression jsExpressionHandleExpression = handleExpression(expression);
        if (Intrinsics.areEqual(jsExpressionHandleExpression, expression)) {
            getCurrentStatements().add(x);
        } else if (jsExpressionHandleExpression != null) {
            List<JsStatement> currentStatements = getCurrentStatements();
            JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(jsExpressionHandleExpression);
            MetadataProperties.setSynthetic(jsExpressionStatement, true);
            currentStatements.add(jsExpressionStatement);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitVars(JsVars x) {
        Intrinsics.checkNotNullParameter(x, "x");
        getCurrentStatements().add(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitReturn(JsReturn x) {
        JsNameRef jsNameRef;
        Intrinsics.checkNotNullParameter(x, "x");
        if (hasEnclosingFinallyBlock()) {
            CoroutineBlock coroutineBlock = new CoroutineBlock();
            jumpWithFinally(0, coroutineBlock, x);
            if (x.getExpression() != null) {
                jsNameRef = new JsNameRef(this.context.getReturnValueFieldName(), JsAstUtils.stateMachineReceiver());
                getCurrentStatements().add(JsAstUtils.assignment(jsNameRef, x.getExpression()).makeStmt());
            } else {
                jsNameRef = null;
            }
            getCurrentStatements().add(jump());
            this.currentBlock = coroutineBlock;
            getCurrentStatements().add(new JsReturn(jsNameRef != null ? jsNameRef.deepCopy() : null));
            return;
        }
        getCurrentStatements().add(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitThrow(JsThrow x) {
        Intrinsics.checkNotNullParameter(x, "x");
        getCurrentStatements().add(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDebugger(JsDebugger x) {
        Intrinsics.checkNotNullParameter(x, "x");
        getCurrentStatements().add(x);
    }

    private final JsExpression handleExpression(JsExpression expression) {
        Pair<JsExpression, JsExpression> pairDecomposeAssignment = JsAstUtils.decomposeAssignment(expression);
        if (pairDecomposeAssignment != null) {
            JsExpression second = pairDecomposeAssignment.getSecond();
            Intrinsics.checkNotNull(second);
            if (MetadataProperties.isSuspend(second)) {
                handleSuspend(expression, second);
                return null;
            }
        } else if (MetadataProperties.isSuspend(expression)) {
            handleSuspend(expression, expression);
            return null;
        }
        return expression;
    }

    private final void handleSuspend(JsExpression invocation, JsExpression sourceNode) {
        Object source = sourceNode.getSource();
        if (source == null) {
            source = invocation.getSource();
        }
        CoroutineBlock coroutineBlock = new CoroutineBlock();
        CollectionsKt.addAll(getCurrentStatements(), state(coroutineBlock, invocation));
        JsNameRef jsNameRef = new JsNameRef(this.context.getMetadata().getResultName(), JsAstUtils.stateMachineReceiver());
        MetadataProperties.setSideEffects(jsNameRef, SideEffectKind.DEPENDS_ON_STATE);
        JsStatement jsStatementMakeStmt = JsAstUtils.assignment(jsNameRef, invocation).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        JsIf jsIf = new JsIf(JsAstUtils.equality(jsNameRef.deepCopy(), this.context.getMetadata().getSuspendObjectRef().deepCopy()).source(source), new JsReturn(this.context.getMetadata().getSuspendObjectRef().deepCopy().source(source)));
        List<JsStatement> currentStatements = getCurrentStatements();
        JsContinue jsContinue = new JsContinue();
        jsContinue.setSource(source);
        Unit unit = Unit.INSTANCE;
        CollectionsKt.addAll(currentStatements, CollectionsKt.listOf((Object[]) new JsStatement[]{jsStatementMakeStmt, jsIf, jsContinue}));
        this.currentBlock = coroutineBlock;
    }

    private final List<JsStatement> state(CoroutineBlock target, JsNode fromExpression) {
        JsDebugger jsDebugger = new JsDebugger();
        CoroutineMetadataPropertiesKt.setTargetBlock(jsDebugger, target);
        jsDebugger.setSource(fromExpression.getSource());
        return CollectionsKt.listOf(jsDebugger);
    }

    private final JsContinue jump() {
        return new JsContinue();
    }

    private final List<JsStatement> stateAndJump(CoroutineBlock target, JsNode fromNode) {
        return CollectionsKt.plus((Collection<? extends JsContinue>) state(target, fromNode), jump());
    }

    private final List<JsStatement> exceptionState(CoroutineBlock target, JsNode fromNode) {
        JsDebugger jsDebugger = new JsDebugger();
        CoroutineMetadataPropertiesKt.setTargetExceptionBlock(jsDebugger, target);
        jsDebugger.setSource(fromNode);
        return CollectionsKt.listOf(jsDebugger);
    }

    private final List<JsStatement> updateFinallyPath(List<CoroutineBlock> path) {
        JsDebugger jsDebugger = new JsDebugger();
        CoroutineMetadataPropertiesKt.setFinallyPath(jsDebugger, path);
        return CollectionsKt.listOf(jsDebugger);
    }

    private final void withBreakAndContinue(JsStatement statement, CoroutineBlock breakBlock, CoroutineBlock continueBlock, Function0<Unit> action) {
        this.breakTargets.put(statement, new JumpTarget(breakBlock, getCurrentTryDepth()));
        if (continueBlock != null) {
            this.continueTargets.put(statement, new JumpTarget(continueBlock, getCurrentTryDepth()));
        }
        action.invoke();
        this.breakTargets.keySet().remove(statement);
        this.continueTargets.keySet().remove(statement);
    }

    private final List<CoroutineBlock> relativeFinallyPath(int targetTryDepth) {
        List<TryBlock> list = this.tryStack;
        List<TryBlock> listSubList = list.subList(targetTryDepth, list.size());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listSubList.iterator();
        while (it.hasNext()) {
            CoroutineBlock finallyBlock = ((TryBlock) it.next()).getFinallyBlock();
            if (finallyBlock != null) {
                arrayList.add(finallyBlock);
            }
        }
        return CollectionsKt.reversed(arrayList);
    }

    private final boolean hasEnclosingFinallyBlock() {
        List<TryBlock> list = this.tryStack;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((TryBlock) it.next()).getFinallyBlock() != null) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: compiled from: CoroutineBodyTransformer.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/js/coroutine/CoroutineBodyTransformer$JumpTarget;", "", "block", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "tryDepth", "", "(Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;I)V", "getBlock", "()Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "getTryDepth", "()I", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class JumpTarget {
        private final CoroutineBlock block;
        private final int tryDepth;

        public static /* synthetic */ JumpTarget copy$default(JumpTarget jumpTarget, CoroutineBlock coroutineBlock, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                coroutineBlock = jumpTarget.block;
            }
            if ((i2 & 2) != 0) {
                i = jumpTarget.tryDepth;
            }
            return jumpTarget.copy(coroutineBlock, i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final CoroutineBlock getBlock() {
            return this.block;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getTryDepth() {
            return this.tryDepth;
        }

        public final JumpTarget copy(CoroutineBlock block, int tryDepth) {
            Intrinsics.checkNotNullParameter(block, "block");
            return new JumpTarget(block, tryDepth);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof JumpTarget)) {
                return false;
            }
            JumpTarget jumpTarget = (JumpTarget) other;
            return Intrinsics.areEqual(this.block, jumpTarget.block) && this.tryDepth == jumpTarget.tryDepth;
        }

        public int hashCode() {
            return (this.block.hashCode() * 31) + Integer.hashCode(this.tryDepth);
        }

        public String toString() {
            return "JumpTarget(block=" + this.block + ", tryDepth=" + this.tryDepth + ')';
        }

        public JumpTarget(CoroutineBlock block, int i) {
            Intrinsics.checkNotNullParameter(block, "block");
            this.block = block;
            this.tryDepth = i;
        }

        public final CoroutineBlock getBlock() {
            return this.block;
        }

        public final int getTryDepth() {
            return this.tryDepth;
        }
    }

    /* JADX INFO: compiled from: CoroutineBodyTransformer.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/coroutine/CoroutineBodyTransformer$TryBlock;", "", "catchBlock", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "finallyBlock", "(Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;)V", "getCatchBlock", "()Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "getFinallyBlock", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TryBlock {
        private final CoroutineBlock catchBlock;
        private final CoroutineBlock finallyBlock;

        public TryBlock(CoroutineBlock catchBlock, CoroutineBlock coroutineBlock) {
            Intrinsics.checkNotNullParameter(catchBlock, "catchBlock");
            this.catchBlock = catchBlock;
            this.finallyBlock = coroutineBlock;
        }

        public final CoroutineBlock getCatchBlock() {
            return this.catchBlock;
        }

        public final CoroutineBlock getFinallyBlock() {
            return this.finallyBlock;
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBlock(JsBlock x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Set<? extends JsNode> set = this.nodesToSplit;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set = null;
        }
        JsBlock jsBlock = x;
        if (set.contains(jsBlock)) {
            Iterator<JsStatement> it = x.getStatements().iterator();
            while (it.hasNext()) {
                it.next().accept(this);
            }
            return;
        }
        getCurrentStatements().add(jsBlock);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitIf(JsIf x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Set<? extends JsNode> set = this.nodesToSplit;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set = null;
        }
        JsIf jsIf = x;
        if (set.contains(jsIf)) {
            CoroutineBlock coroutineBlock = this.currentBlock;
            CoroutineBlock coroutineBlock2 = new CoroutineBlock();
            this.currentBlock = coroutineBlock2;
            CoroutineBodyTransformer coroutineBodyTransformer = this;
            x.getThenStatement().accept(coroutineBodyTransformer);
            CoroutineBlock coroutineBlock3 = this.currentBlock;
            CoroutineBlock coroutineBlock4 = new CoroutineBlock();
            this.currentBlock = coroutineBlock4;
            JsStatement elseStatement = x.getElseStatement();
            if (elseStatement != null) {
                elseStatement.accept(coroutineBodyTransformer);
            }
            CoroutineBlock coroutineBlock5 = this.currentBlock;
            x.setThenStatement(new JsBlock(coroutineBlock2.getStatements()));
            x.setElseStatement(new JsBlock(coroutineBlock4.getStatements()));
            coroutineBlock.getStatements().add(x);
            CoroutineBlock coroutineBlock6 = new CoroutineBlock();
            JsIf jsIf2 = x;
            CollectionsKt.addAll(coroutineBlock3.getStatements(), stateAndJump(coroutineBlock6, jsIf2));
            CollectionsKt.addAll(coroutineBlock5.getStatements(), stateAndJump(coroutineBlock6, jsIf2));
            this.currentBlock = coroutineBlock6;
            return;
        }
        getCurrentStatements().add(jsIf);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visit(final JsSwitch x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Set<? extends JsNode> set = this.nodesToSplit;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set = null;
        }
        JsSwitch jsSwitch = x;
        if (set.contains(jsSwitch)) {
            CoroutineBlock coroutineBlock = this.currentBlock;
            CoroutineBlock coroutineBlock2 = new CoroutineBlock();
            withBreakAndContinue(jsSwitch, coroutineBlock2, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineBodyTransformer$visit$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    for (JsSwitchMember jsSwitchMember : x.getCases()) {
                        CoroutineBlock coroutineBlock3 = new CoroutineBlock();
                        this.currentBlock = coroutineBlock3;
                        List<JsStatement> statements = jsSwitchMember.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                        CoroutineBodyTransformer coroutineBodyTransformer = this;
                        Iterator<T> it = statements.iterator();
                        while (it.hasNext()) {
                            coroutineBodyTransformer.accept((JsStatement) it.next());
                        }
                        jsSwitchMember.getStatements().clear();
                        List<JsStatement> statements2 = jsSwitchMember.getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                        CollectionsKt.addAll(statements2, coroutineBlock3.getStatements());
                    }
                }
            });
            coroutineBlock.getStatements().add(x);
            this.currentBlock = coroutineBlock2;
            return;
        }
        getCurrentStatements().add(jsSwitch);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitWhile(final JsWhile x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Set<? extends JsNode> set = this.nodesToSplit;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set = null;
        }
        JsWhile jsWhile = x;
        if (set.contains(jsWhile)) {
            CoroutineBlock coroutineBlock = new CoroutineBlock();
            CoroutineBlock coroutineBlock2 = new CoroutineBlock();
            JsWhile jsWhile2 = x;
            CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock2, jsWhile2));
            this.currentBlock = coroutineBlock2;
            if (!JsBooleanLiteral.isTrue(x.getCondition())) {
                List<JsStatement> currentStatements = getCurrentStatements();
                JsIf jsIf = new JsIf(JsAstUtils.notOptimized(x.getCondition()), new JsBlock(stateAndJump(coroutineBlock, jsWhile2)));
                jsIf.setSource(x.getSource());
                currentStatements.add(jsIf);
            }
            withBreakAndContinue(jsWhile, coroutineBlock, coroutineBlock2, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineBodyTransformer$visitWhile$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    x.getBody().accept(this);
                }
            });
            CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock2, jsWhile2));
            this.currentBlock = coroutineBlock;
            return;
        }
        getCurrentStatements().add(jsWhile);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitDoWhile(final JsDoWhile x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Set<? extends JsNode> set = this.nodesToSplit;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set = null;
        }
        JsDoWhile jsDoWhile = x;
        if (set.contains(jsDoWhile)) {
            CoroutineBlock coroutineBlock = new CoroutineBlock();
            CoroutineBlock coroutineBlock2 = new CoroutineBlock();
            JsDoWhile jsDoWhile2 = x;
            CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock2, jsDoWhile2));
            this.currentBlock = coroutineBlock2;
            withBreakAndContinue(jsDoWhile, coroutineBlock, coroutineBlock2, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineBodyTransformer$visitDoWhile$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    x.getBody().accept(this);
                }
            });
            if (!JsBooleanLiteral.isTrue(x.getCondition())) {
                JsIf jsIf = new JsIf(JsAstUtils.notOptimized(x.getCondition()), new JsBlock(stateAndJump(coroutineBlock, jsDoWhile2)));
                jsIf.setSource(x.getSource());
                getCurrentStatements().add(jsIf);
            }
            CollectionsKt.addAll(this.currentBlock.getStatements(), stateAndJump(coroutineBlock2, jsDoWhile2));
            this.currentBlock = coroutineBlock;
            return;
        }
        getCurrentStatements().add(jsDoWhile);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFor(final JsFor x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Set<? extends JsNode> set = this.nodesToSplit;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set = null;
        }
        JsFor jsFor = x;
        if (set.contains(jsFor)) {
            JsExpression initExpression = x.getInitExpression();
            if (initExpression != null) {
                Intrinsics.checkNotNull(initExpression);
                new JsExpressionStatement(initExpression).accept(this);
            }
            JsVars initVars = x.getInitVars();
            if (initVars != null) {
                Intrinsics.checkNotNull(initVars);
                List<JsVars.JsVar> vars = initVars.getVars();
                Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
                if (!vars.isEmpty()) {
                    initVars.accept(this);
                }
            }
            CoroutineBlock coroutineBlock = new CoroutineBlock();
            CoroutineBlock coroutineBlock2 = new CoroutineBlock();
            CoroutineBlock coroutineBlock3 = new CoroutineBlock();
            JsFor jsFor2 = x;
            CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock3, jsFor2));
            this.currentBlock = coroutineBlock3;
            if (x.getCondition() != null && !JsBooleanLiteral.isTrue(x.getCondition())) {
                List<JsStatement> currentStatements = getCurrentStatements();
                JsIf jsIf = new JsIf(JsAstUtils.notOptimized(x.getCondition()), new JsBlock(stateAndJump(coroutineBlock2, jsFor2)));
                jsIf.setSource(x.getSource());
                currentStatements.add(jsIf);
            }
            withBreakAndContinue(jsFor, coroutineBlock2, coroutineBlock, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineBodyTransformer$visitFor$1$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    x.getBody().accept(this);
                }
            });
            CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock, jsFor2));
            this.currentBlock = coroutineBlock;
            JsExpression incrementExpression = x.getIncrementExpression();
            if (incrementExpression != null) {
                Intrinsics.checkNotNull(incrementExpression);
                new JsExpressionStatement(incrementExpression).accept(this);
            }
            CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock3, jsFor2));
            this.currentBlock = coroutineBlock2;
            return;
        }
        getCurrentStatements().add(jsFor);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitTry(JsTry x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Set<? extends JsNode> set = this.nodesToSplit;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nodesToSplit");
            set = null;
        }
        JsTry jsTry = x;
        if (set.contains(jsTry)) {
            List<JsCatch> catches = x.getCatches();
            Intrinsics.checkNotNullExpressionValue(catches, "getCatches(...)");
            JsCatch jsCatch = (JsCatch) CollectionsKt.firstOrNull((List) catches);
            JsBlock finallyBlock = x.getFinallyBlock();
            CoroutineBlock coroutineBlock = new CoroutineBlock();
            CoroutineBlock coroutineBlock2 = new CoroutineBlock();
            CoroutineBlock coroutineBlock3 = new CoroutineBlock();
            this.tryStack.add(new TryBlock(coroutineBlock2, finallyBlock != null ? coroutineBlock3 : null));
            CoroutineBlock coroutineBlock4 = this.currentCatchBlock;
            this.currentCatchBlock = coroutineBlock2;
            JsTry jsTry2 = x;
            CollectionsKt.addAll(getCurrentStatements(), exceptionState(coroutineBlock2, jsTry2));
            List<JsStatement> statements = x.getTryBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            Iterator<T> it = statements.iterator();
            while (it.hasNext()) {
                ((JsStatement) it.next()).accept(this);
            }
            CollectionsKt.addAll(getCurrentStatements(), exceptionState(coroutineBlock4, jsTry2));
            this.currentCatchBlock = coroutineBlock4;
            if (finallyBlock != null) {
                CollectionsKt.addAll(getCurrentStatements(), updateFinallyPath(CollectionsKt.listOf(coroutineBlock)));
                CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock3, jsTry2));
            } else {
                CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock, jsTry2));
            }
            this.currentBlock = coroutineBlock2;
            if (finallyBlock != null) {
                CollectionsKt.addAll(getCurrentStatements(), updateFinallyPath(CollectionsKt.listOf(coroutineBlock4)));
                CollectionsKt.addAll(getCurrentStatements(), jsCatch != null ? exceptionState(coroutineBlock3, jsTry2) : stateAndJump(coroutineBlock3, jsTry2));
            } else {
                CollectionsKt.addAll(getCurrentStatements(), jsCatch != null ? exceptionState(coroutineBlock4, jsTry2) : stateAndJump(coroutineBlock4, jsTry2));
            }
            if (jsCatch != null) {
                getCurrentStatements().add(JsAstUtils.newVar(jsCatch.getParameter().getName(), new JsNameRef(this.context.getMetadata().getExceptionName(), JsAstUtils.stateMachineReceiver())));
                List<JsStatement> statements2 = jsCatch.getBody().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                Iterator<T> it2 = statements2.iterator();
                while (it2.hasNext()) {
                    ((JsStatement) it2.next()).accept(this);
                }
                if (finallyBlock == null) {
                    CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock, jsTry2));
                } else {
                    CollectionsKt.addAll(getCurrentStatements(), updateFinallyPath(CollectionsKt.listOf(coroutineBlock)));
                    CollectionsKt.addAll(getCurrentStatements(), stateAndJump(coroutineBlock3, jsTry2));
                }
            }
            List<TryBlock> list = this.tryStack;
            list.remove(CollectionsKt.getLastIndex(list));
            if (finallyBlock != null) {
                this.currentBlock = coroutineBlock3;
                CollectionsKt.addAll(getCurrentStatements(), exceptionState(coroutineBlock4, jsTry2));
                List<JsStatement> statements3 = finallyBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                Iterator<T> it3 = statements3.iterator();
                while (it3.hasNext()) {
                    ((JsStatement) it3.next()).accept(this);
                }
                generateFinallyExit();
                this.hasFinallyBlocks = true;
            }
            this.currentBlock = coroutineBlock;
            return;
        }
        getCurrentStatements().add(jsTry);
    }
}
