package org.jetbrains.kotlin.js.dce;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;

/* JADX INFO: compiled from: ReachabilityTracker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 >2\u00020\u0001:\u0001>B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u001a\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\u0007¢\u0006\u0002\u0010\u000bJ\u0016\u0010\u0017\u001a\u00020\n2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019H\u0002J\u0012\u0010\u001a\u001a\u00020\n2\n\u0010\u001b\u001a\u00060\u0012R\u00020\u0003J\u0014\u0010\u001c\u001a\u00020\n2\n\u0010\u001b\u001a\u00060\u0012R\u00020\u0003H\u0002J\u0014\u0010\u001d\u001a\u00020\n2\n\u0010\u001b\u001a\u00060\u0012R\u00020\u0003H\u0002J(\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\t2\b\u0010 \u001a\u0004\u0018\u00010\r2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019H\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\rH\u0002J\u0010\u0010$\u001a\u00020\n2\u0006\u0010#\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\n2\u0006\u0010#\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\n2\u0006\u0010#\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\n2\u0006\u0010#\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\rH\u0014J\u0010\u0010-\u001a\u00020\n2\u0006\u0010#\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020\n2\u0006\u0010#\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020\n2\u0006\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020\"2\u0006\u0010#\u001a\u000205H\u0002J\u0010\u00106\u001a\u00020\n2\u0006\u00107\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020\n2\u0006\u0010#\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\n2\u0006\u0010#\u001a\u00020<H\u0016J\u0016\u0010=\u001a\u00020\n2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\f\u0012\b\u0012\u00060\u0012R\u00020\u00030\u00118F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\f\u0012\b\u0012\u00060\u0012R\u00020\u00030\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/ReachabilityTracker;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "context", "Lorg/jetbrains/kotlin/js/dce/Context;", "analysisResult", "Lorg/jetbrains/kotlin/js/dce/AnalysisResult;", "logConsumer", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/dce/DCELogLevel;", "", "", "(Lorg/jetbrains/kotlin/js/dce/Context;Lorg/jetbrains/kotlin/js/dce/AnalysisResult;Lkotlin/jvm/functions/Function2;)V", "currentNodeWithLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", ConfigConstants.CONFIG_KEY_DEPTH, "", "reachableNodes", "", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "getReachableNodes", "()Ljava/lang/Iterable;", "reachableNodesImpl", "", "nested", "action", "Lkotlin/Function0;", "reach", "node", "reachDeclaration", "reachDependencies", "reportAndNest", CrashHianalyticsData.MESSAGE, "dueTo", "shouldTraverse", "", "x", "visit", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "visitArrayAccess", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;", "visitBreak", "Lorg/jetbrains/kotlin/js/backend/ast/JsBreak;", "visitContinue", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "visitElement", "visitExpressionStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "visitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitInvocation", "invocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "visitNameLikeNode", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "visitNameRef", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "visitPrefixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPrefixOperation;", "visitReturn", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "withErasedThis", "Companion", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReachabilityTracker extends RecursiveJsVisitor {
    private final AnalysisResult analysisResult;
    private final Context context;
    private JsNode currentNodeWithLocation;
    private int depth;
    private final Function2<DCELogLevel, String, Unit> logConsumer;
    private final List<Context.Node> reachableNodesImpl;
    private static final Set<String> CALL_FUNCTIONS = SetsKt.setOf((Object[]) new String[]{"call", "apply"});

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBreak(JsBreak x) {
        Intrinsics.checkNotNullParameter(x, "x");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitContinue(JsContinue x) {
        Intrinsics.checkNotNullParameter(x, "x");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ReachabilityTracker(Context context, AnalysisResult analysisResult, Function2<? super DCELogLevel, ? super String, Unit> function2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(analysisResult, "analysisResult");
        this.context = context;
        this.analysisResult = analysisResult;
        this.logConsumer = function2;
        context.clearVisited();
        this.reachableNodesImpl = new ArrayList();
    }

    public final Iterable<Context.Node> getReachableNodes() {
        return this.reachableNodesImpl;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visit(JsVars.JsVar x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (shouldTraverse(x)) {
            super.visit(x);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitExpressionStatement(JsExpressionStatement x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (shouldTraverse(x)) {
            super.visitExpressionStatement(x);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitReturn(JsReturn x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (shouldTraverse(x)) {
            super.visitReturn(x);
        }
    }

    private final boolean shouldTraverse(JsNode x) {
        return this.analysisResult.getNodeMap().get(x) == null && !this.analysisResult.getAstNodesToEliminate().contains(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNameRef(JsNameRef nameRef) {
        Intrinsics.checkNotNullParameter(nameRef, "nameRef");
        if (visitNameLikeNode(nameRef)) {
            super.visitNameRef(nameRef);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitArrayAccess(JsArrayAccess x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (visitNameLikeNode(x)) {
            super.visitArrayAccess(x);
        }
    }

    private final boolean visitNameLikeNode(JsExpression x) {
        if (this.analysisResult.getAstNodesToSkip().contains(x)) {
            return false;
        }
        final Context.Node nodeExtractNode = this.context.extractNode(x);
        if (nodeExtractNode == null) {
            return true;
        }
        if (!nodeExtractNode.getReachable()) {
            reportAndNest("reach: referenced name " + nodeExtractNode, this.currentNodeWithLocation, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.visitNameLikeNode.1
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
                    ReachabilityTracker.this.reach(nodeExtractNode);
                    JsNode jsNode = ReachabilityTracker.this.currentNodeWithLocation;
                    if (jsNode != null) {
                        nodeExtractNode.addUsedByAstNode(jsNode);
                    }
                }
            });
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitInvocation(JsInvocation invocation) {
        Intrinsics.checkNotNullParameter(invocation, "invocation");
        JsExpression qualifier = invocation.getQualifier();
        Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
        if ((qualifier instanceof JsFunction) && this.analysisResult.getFunctionsToEnter().contains(qualifier)) {
            accept(((JsFunction) qualifier).getBody());
            List<JsExpression> arguments = invocation.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : arguments) {
                JsExpression jsExpression = (JsExpression) obj;
                if ((jsExpression instanceof JsFunction) && this.analysisResult.getFunctionsToEnter().contains(jsExpression)) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                accept((JsExpression) it.next());
            }
            return;
        }
        if (this.analysisResult.getInvocationsToSkip().contains(invocation)) {
            return;
        }
        Context context = this.context;
        JsExpression qualifier2 = invocation.getQualifier();
        Intrinsics.checkNotNullExpressionValue(qualifier2, "getQualifier(...)");
        Context.Node nodeExtractNode = context.extractNode(qualifier2);
        if (nodeExtractNode != null && CollectionsKt.contains(CALL_FUNCTIONS, nodeExtractNode.getMemberName())) {
            Context.Node parent = nodeExtractNode.getParent();
            Intrinsics.checkNotNull(parent);
            reach(parent);
            JsNode jsNode = this.currentNodeWithLocation;
            if (jsNode != null) {
                parent.addUsedByAstNode(jsNode);
            }
        }
        super.visitInvocation(invocation);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(final JsFunction x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (!this.analysisResult.getFunctionsToEnter().contains(x)) {
            Set setCollectLocalVariables$default = CollectUtilsKt.collectLocalVariables$default(x, false, 1, null);
            this.context.addNodesForLocalVars(setCollectLocalVariables$default);
            CollectionsKt.addAll(this.context.getNamesOfLocalVars(), setCollectLocalVariables$default);
            withErasedThis(new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.visitFunction.2
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
                    ReachabilityTracker.super.visitFunction(x);
                }
            });
            return;
        }
        super.visitFunction(x);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void withErasedThis(Function0<Unit> action) {
        Context.Node thisNode = this.context.getThisNode();
        this.context.setThisNode(null);
        action.invoke();
        this.context.setThisNode(thisNode);
    }

    public final void reach(Context.Node node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (node.getReachable()) {
            return;
        }
        node.setReachable(true);
        if (this.context.visit(node)) {
            this.reachableNodesImpl.add(node);
        }
        reachDeclaration(node);
        reachDependencies(node);
        for (Pair pair : MapsKt.toList(node.getMembers())) {
            String str = (String) pair.component1();
            final Context.Node node2 = (Context.Node) pair.component2();
            if (!node2.getReachable()) {
                reportAndNest("reach: member " + str, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker$reach$1$1
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
                        this.this$0.reach(node2);
                    }
                });
            }
        }
        if (!this.analysisResult.getFunctionsToSkip().contains(node)) {
            for (final JsFunction jsFunction : node.getFunctions()) {
                reportAndNest("traverse: function", jsFunction, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.reach.2
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
                        Set setCollectLocalVariables$default = CollectUtilsKt.collectLocalVariables$default(jsFunction, false, 1, null);
                        ReachabilityTracker reachabilityTracker = this;
                        reachabilityTracker.context.addNodesForLocalVars(setCollectLocalVariables$default);
                        CollectionsKt.addAll(reachabilityTracker.context.getNamesOfLocalVars(), setCollectLocalVariables$default);
                        ReachabilityTracker reachabilityTracker2 = this;
                        final JsFunction jsFunction2 = jsFunction;
                        final ReachabilityTracker reachabilityTracker3 = this;
                        reachabilityTracker2.withErasedThis(new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.reach.2.2
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
                                jsFunction2.getBody().accept(reachabilityTracker3);
                            }
                        });
                    }
                });
            }
        }
        for (final JsExpression jsExpression : node.getExpressions()) {
            reportAndNest("traverse: value", jsExpression, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.reach.3
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
                    jsExpression.accept(this);
                }
            });
        }
    }

    private final void reachDependencies(Context.Node node) {
        ArrayList arrayList = new ArrayList();
        while (true) {
            Iterator<Context.Node> it = node.getDependencies().iterator();
            while (it.hasNext()) {
                final Context.Node next = it.next();
                if (!SequencesKt.contains(SequencesKt.generateSequence(next, new Function1<Context.Node, Context.Node>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.reachDependencies.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Context.Node invoke(Context.Node it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return it2.getParent();
                    }
                }), node)) {
                    Iterator it2 = CollectionsKt.asReversedMutable(arrayList).iterator();
                    while (it2.hasNext()) {
                        next = next.member((String) it2.next());
                    }
                    if (!next.getReachable()) {
                        reportAndNest("reach: dependency " + next, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.reachDependencies.2
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
                                ReachabilityTracker.this.reach(next);
                            }
                        });
                    }
                }
            }
            ArrayList arrayList2 = arrayList;
            String memberName = node.getMemberName();
            if (memberName == null) {
                return;
            }
            arrayList2.add(memberName);
            node = node.getParent();
            Intrinsics.checkNotNull(node);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reachDeclaration(final Context.Node node) {
        if (node.getHasSideEffects() && !node.getReachable()) {
            reportAndNest("reach: because of side effect", null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.reachDeclaration.1
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
                    ReachabilityTracker.this.reach(node);
                }
            });
            return;
        }
        if (node.getDeclarationReachable()) {
            return;
        }
        node.setDeclarationReachable(true);
        if (this.context.visit(node)) {
            this.reachableNodesImpl.add(node);
        }
        final Context.Node parent = node.getOriginal().getParent();
        if (parent != null) {
            reportAndNest("reach-decl: parent " + parent, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker$reachDeclaration$2$1
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
                    this.this$0.reachDeclaration(parent);
                }
            });
        }
        for (final JsExpression jsExpression : node.getExpressions()) {
            reportAndNest("traverse: value", jsExpression, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.dce.ReachabilityTracker.reachDeclaration.3
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
                    jsExpression.accept(this);
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitPrefixOperation(JsPrefixOperation x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (x.getOperator() == JsUnaryOperator.TYPEOF) {
            JsExpression arg = x.getArg();
            Context context = this.context;
            Intrinsics.checkNotNull(arg);
            Context.Node nodeExtractNode = context.extractNode(arg);
            if (nodeExtractNode != null) {
                reachDeclaration(nodeExtractNode);
                return;
            }
        }
        super.visitPrefixOperation(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
    protected void visitElement(JsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (this.analysisResult.getAstNodesToSkip().contains(node)) {
            return;
        }
        JsLocation jsLocationExtractLocation = UtilKt.extractLocation(node);
        JsNode jsNode = this.currentNodeWithLocation;
        if (jsLocationExtractLocation != null) {
            this.currentNodeWithLocation = node;
        }
        super.visitElement(node);
        this.currentNodeWithLocation = jsNode;
    }

    private final void reportAndNest(String message, JsNode dueTo, Function0<Unit> action) {
        String str;
        if (this.logConsumer != null) {
            String strRepeat = StringsKt.repeat("  ", this.depth);
            JsLocation jsLocationExtractLocation = dueTo != null ? UtilKt.extractLocation(dueTo) : null;
            if (jsLocationExtractLocation == null) {
                str = strRepeat + message;
            } else {
                str = strRepeat + message + " (due to " + UtilKt.asString(jsLocationExtractLocation) + ')';
            }
            this.logConsumer.invoke(DCELogLevel.INFO, str);
        }
        nested(action);
    }

    private final void nested(Function0<Unit> action) {
        this.depth++;
        action.invoke();
        this.depth--;
    }
}
