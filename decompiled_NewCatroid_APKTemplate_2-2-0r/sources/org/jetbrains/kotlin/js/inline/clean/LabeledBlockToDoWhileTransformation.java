package org.jetbrains.kotlin.js.inline.clean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLoop;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: LabeledBlockToDoWhileTransformation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J&\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/LabeledBlockToDoWhileTransformation;", "", "()V", "apply", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "labelLoopBreaksAndContinues", "loopOrSwitch", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "fakeLoops", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LabeledBlockToDoWhileTransformation {
    public static final LabeledBlockToDoWhileTransformation INSTANCE = new LabeledBlockToDoWhileTransformation();

    private LabeledBlockToDoWhileTransformation() {
    }

    public final void apply(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.LabeledBlockToDoWhileTransformation.apply.1
            private final Stack<JsStatement> loopOrSwitchStack = new Stack<>();
            private final HashSet<JsDoWhile> newFakeLoops = new HashSet<>();
            private final HashMap<JsStatement, JsLabel> statementsLabels = new HashMap<>();
            private final HashSet<JsStatement> loopsAndSwitchesToLabel = new HashSet<>();

            public final Stack<JsStatement> getLoopOrSwitchStack() {
                return this.loopOrSwitchStack;
            }

            public final HashSet<JsDoWhile> getNewFakeLoops() {
                return this.newFakeLoops;
            }

            public final HashMap<JsStatement, JsLabel> getStatementsLabels() {
                return this.statementsLabels;
            }

            public final HashSet<JsStatement> getLoopsAndSwitchesToLabel() {
                return this.loopsAndSwitchesToLabel;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsLabel x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (x.getStatement() instanceof JsBlock) {
                    CollectionsKt.addIfNotNull(this.loopsAndSwitchesToLabel, kotlin.collections.CollectionsKt.lastOrNull((List) this.loopOrSwitchStack));
                    JsDoWhile jsDoWhile = new JsDoWhile(new JsBooleanLiteral(false), x.getStatement());
                    this.newFakeLoops.add(jsDoWhile);
                    x.setStatement(jsDoWhile);
                }
                super.endVisit(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsLabel x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (!(x.getStatement() instanceof JsLoop)) {
                    return true;
                }
                HashMap<JsStatement, JsLabel> map = this.statementsLabels;
                JsStatement statement = x.getStatement();
                Intrinsics.checkNotNullExpressionValue(statement, "getStatement(...)");
                map.put(statement, x);
                return true;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsLoop x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                this.loopOrSwitchStack.push(x);
                return true;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsSwitch x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                this.loopOrSwitchStack.push(x);
                return true;
            }

            public final void endVisitLoopOrSwitch(JsStatement x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                this.loopOrSwitchStack.pop();
                if (this.loopsAndSwitchesToLabel.contains(x)) {
                    JsLabel jsLabel = this.statementsLabels.get(x);
                    if (jsLabel == null) {
                        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("loop_label");
                        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
                        JsLabel jsLabel2 = new JsLabel(jsNameDeclareTemporaryName, x);
                        this.statementsLabels.put(x, jsLabel2);
                        ctx.replaceMe(jsLabel2);
                        jsLabel = jsLabel2;
                    }
                    LabeledBlockToDoWhileTransformation labeledBlockToDoWhileTransformation = LabeledBlockToDoWhileTransformation.INSTANCE;
                    HashSet<JsDoWhile> hashSet = this.newFakeLoops;
                    JsNameRef jsNameRefMakeRef = jsLabel.getName().makeRef();
                    Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                    labeledBlockToDoWhileTransformation.labelLoopBreaksAndContinues(x, hashSet, jsNameRefMakeRef);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsSwitch x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                endVisitLoopOrSwitch(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsLoop x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                endVisitLoopOrSwitch(x, ctx);
            }
        }.accept(root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void labelLoopBreaksAndContinues(final JsStatement loopOrSwitch, final Set<? extends JsDoWhile> fakeLoops, final JsNameRef label) {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.LabeledBlockToDoWhileTransformation.labelLoopBreaksAndContinues.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsLoop x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return kotlin.collections.CollectionsKt.contains(fakeLoops, x) || x == loopOrSwitch;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsSwitch x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return x == loopOrSwitch;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsBreak x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (x.getLabel() == null) {
                    ctx.replaceMe(new JsBreak(label));
                }
                super.endVisit(x, (JsContext) ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsContinue x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (x.getLabel() == null) {
                    ctx.replaceMe(new JsContinue(label));
                }
                super.endVisit(x, ctx);
            }
        }.accept(loopOrSwitch);
    }
}
