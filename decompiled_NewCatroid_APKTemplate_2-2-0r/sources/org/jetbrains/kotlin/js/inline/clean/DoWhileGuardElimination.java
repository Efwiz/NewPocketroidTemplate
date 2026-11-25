package org.jetbrains.kotlin.js.inline.clean;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLoop;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: DoWhileGuardElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0006\u0010\u0011\u001a\u00020\u000bJ\u0018\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0007H\u0002J\b\u0010\u0015\u001a\u00020\u0010H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/DoWhileGuardElimination;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "guardLabels", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "guardToLoopLabel", "", "hasChanges", "", "loopGuardMap", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "analyze", "", "apply", "findBreakInNestedLoop", "statement", "name", "perform", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DoWhileGuardElimination {
    private final Set<JsName> guardLabels;
    private final Map<JsName, JsName> guardToLoopLabel;
    private boolean hasChanges;
    private final Map<JsDoWhile, JsLabel> loopGuardMap;
    private final JsStatement root;

    public DoWhileGuardElimination(JsStatement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
        this.guardLabels = new LinkedHashSet();
        this.loopGuardMap = new LinkedHashMap();
        this.guardToLoopLabel = new LinkedHashMap();
    }

    public final boolean apply() {
        analyze();
        perform();
        return this.hasChanges;
    }

    private final void analyze() {
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.DoWhileGuardElimination.analyze.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsStatement statement = x.getStatement();
                if (statement instanceof JsDoWhile) {
                    processDoWhile((JsDoWhile) statement, x.getName());
                } else {
                    super.visitLabel(x);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDoWhile(JsDoWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                processDoWhile(x, null);
            }

            /* JADX WARN: Code duplicated, block: B:19:0x0055  */
            private final void processDoWhile(JsDoWhile x, JsName label) {
                JsStatement body = x.getBody();
                JsLabel jsLabel = null;
                if (body instanceof JsBlock) {
                    JsBlock jsBlock = (JsBlock) body;
                    List<JsStatement> statements = jsBlock.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                    JsStatement jsStatement = (JsStatement) CollectionsKt.firstOrNull((List) statements);
                    if ((jsStatement instanceof JsLabel) && jsBlock.getStatements().size() == 1) {
                        jsLabel = (JsLabel) jsStatement;
                    }
                } else if (body instanceof JsLabel) {
                    jsLabel = (JsLabel) body;
                }
                if (jsLabel != null && !(jsLabel.getStatement() instanceof JsLoop)) {
                    if (label != null) {
                        DoWhileGuardElimination.this.guardLabels.add(jsLabel.getName());
                        DoWhileGuardElimination.this.loopGuardMap.put(x, jsLabel);
                        Map map = DoWhileGuardElimination.this.guardToLoopLabel;
                        JsName name2 = jsLabel.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        map.put(name2, label);
                    } else {
                        JsName name3 = jsLabel.getName();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        if (!DoWhileGuardElimination.this.findBreakInNestedLoop(jsLabel, name3)) {
                            DoWhileGuardElimination.this.guardLabels.add(jsLabel.getName());
                            DoWhileGuardElimination.this.loopGuardMap.put(x, jsLabel);
                            Map map2 = DoWhileGuardElimination.this.guardToLoopLabel;
                            JsName name4 = jsLabel.getName();
                            Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                            map2.put(name4, label);
                        }
                    }
                }
                body.accept(this);
            }
        }.accept(this.root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean findBreakInNestedLoop(JsStatement statement, final JsName name2) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        statement.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.DoWhileGuardElimination.findBreakInNestedLoop.1
            private int loopLevel;

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                JsName name3;
                Intrinsics.checkNotNullParameter(x, "x");
                JsNameRef label = x.getLabel();
                if (label == null || (name3 = label.getName()) == null || !Intrinsics.areEqual(name3, name2) || !isInLoop()) {
                    return;
                }
                booleanRef.element = true;
            }

            private final boolean isInLoop() {
                return this.loopLevel > 0;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLoop(JsLoop x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.loopLevel++;
                super.visitLoop(x);
                this.loopLevel--;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (booleanRef.element) {
                    return;
                }
                super.visitElement(node);
            }
        });
        return booleanRef.element;
    }

    private final void perform() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.DoWhileGuardElimination.perform.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsDoWhile x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsLabel jsLabel = (JsLabel) DoWhileGuardElimination.this.loopGuardMap.get(x);
                if (jsLabel != null) {
                    DoWhileGuardElimination doWhileGuardElimination = DoWhileGuardElimination.this;
                    if (doWhileGuardElimination.guardLabels.contains(jsLabel.getName())) {
                        x.setBody((JsStatement) accept(jsLabel.getStatement()));
                        doWhileGuardElimination.hasChanges = true;
                        return false;
                    }
                }
                return super.visit(x, (JsContext) ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsBreak x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsNameRef label = x.getLabel();
                JsName name2 = label != null ? label.getName() : null;
                if (!CollectionsKt.contains(DoWhileGuardElimination.this.guardLabels, name2)) {
                    return false;
                }
                JsName jsName = (JsName) DoWhileGuardElimination.this.guardToLoopLabel.get(name2);
                ctx.replaceMe(new JsContinue(jsName != null ? jsName.makeRef() : null));
                DoWhileGuardElimination.this.hasChanges = true;
                return false;
            }
        }.accept(this.root);
    }
}
