package org.jetbrains.kotlin.js.inline.clean;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: EmptyStatementElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/EmptyStatementElimination;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "hasChanges", "", "apply", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyStatementElimination {
    private boolean hasChanges;
    private final JsStatement root;

    public EmptyStatementElimination(JsStatement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
    }

    public final boolean apply() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.EmptyStatementElimination.apply.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsLabel x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (MetadataProperties.getSynthetic(x)) {
                    JsStatement statement = x.getStatement();
                    Intrinsics.checkNotNullExpressionValue(statement, "getStatement(...)");
                    if (isEmpty(statement)) {
                        ctx.replaceMe(x.getStatement());
                        EmptyStatementElimination.this.hasChanges = true;
                    }
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsBlock x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                List<JsStatement> statements = x.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                processStatements(statements);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsIf x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsStatement thenStatement = x.getThenStatement();
                Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
                boolean zIsEmpty = isEmpty(thenStatement);
                JsStatement elseStatement = x.getElseStatement();
                boolean zIsEmpty2 = elseStatement != null ? isEmpty(elseStatement) : true;
                if (zIsEmpty && zIsEmpty2) {
                    EmptyStatementElimination.this.hasChanges = true;
                    ctx.replaceMe(JsAstUtils.asSyntheticStatement(x.getIfExpression()));
                    return;
                }
                if (zIsEmpty2) {
                    if (x.getElseStatement() != null) {
                        EmptyStatementElimination.this.hasChanges = true;
                        x.setElseStatement(null);
                        return;
                    }
                    return;
                }
                if (zIsEmpty) {
                    EmptyStatementElimination.this.hasChanges = true;
                    JsStatement elseStatement2 = x.getElseStatement();
                    Intrinsics.checkNotNull(elseStatement2);
                    x.setThenStatement(elseStatement2);
                    x.setElseStatement(null);
                    x.setIfExpression(JsAstUtils.notOptimized(x.getIfExpression()));
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsTry x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsBlock finallyBlock = x.getFinallyBlock();
                if (x.getTryBlock().isEmpty()) {
                    EmptyStatementElimination.this.hasChanges = true;
                    ctx.replaceMe(finallyBlock == null ? JsEmpty.INSTANCE : finallyBlock);
                }
            }

            /* JADX WARN: Code duplicated, block: B:23:0x0089  */
            /* JADX WARN: Code duplicated, block: B:26:0x0093  */
            /* JADX WARN: Code duplicated, block: B:39:0x00df A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:40:? A[LOOP:2: B:24:0x008d->B:40:?, LOOP_END, SYNTHETIC] */
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsSwitch x, JsContext<JsNode> ctx) {
                List<JsSwitchMember> list;
                Iterator<T> it;
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                Iterator<JsSwitchMember> it2 = x.getCases().iterator();
                while (it2.hasNext()) {
                    List<JsStatement> statements = it2.next().getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                    processStatements(statements);
                }
                List<JsSwitchMember> cases = x.getCases();
                Intrinsics.checkNotNullExpressionValue(cases, "getCases(...)");
                if (!(CollectionsKt.lastOrNull((List) cases) instanceof JsDefault)) {
                    List<JsSwitchMember> cases2 = x.getCases();
                    Intrinsics.checkNotNullExpressionValue(cases2, "getCases(...)");
                    list = cases2;
                    if (list instanceof Collection) {
                        it = list.iterator();
                        while (it.hasNext()) {
                            if (!((JsSwitchMember) it.next()).getStatements().isEmpty()) {
                                return;
                            }
                        }
                    } else {
                        it = list.iterator();
                        while (it.hasNext()) {
                            if (!((JsSwitchMember) it.next()).getStatements().isEmpty()) {
                                return;
                            }
                        }
                    }
                } else {
                    List<JsSwitchMember> cases3 = x.getCases();
                    Intrinsics.checkNotNullExpressionValue(cases3, "getCases(...)");
                    List listDropLast = CollectionsKt.dropLast(cases3, 1);
                    if (!(listDropLast instanceof Collection) || !listDropLast.isEmpty()) {
                        Iterator it3 = listDropLast.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                if (!((JsSwitchMember) it3.next()).getStatements().isEmpty()) {
                                    List<JsSwitchMember> cases4 = x.getCases();
                                    Intrinsics.checkNotNullExpressionValue(cases4, "getCases(...)");
                                    list = cases4;
                                    if ((list instanceof Collection) || !list.isEmpty()) {
                                        it = list.iterator();
                                        while (it.hasNext()) {
                                            if (!((JsSwitchMember) it.next()).getStatements().isEmpty()) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                EmptyStatementElimination.this.hasChanges = true;
                List listMutableListOf = CollectionsKt.mutableListOf(JsAstUtils.asSyntheticStatement(x.getExpression()));
                List<JsSwitchMember> cases5 = x.getCases();
                Intrinsics.checkNotNullExpressionValue(cases5, "getCases(...)");
                JsSwitchMember jsSwitchMember = (JsSwitchMember) CollectionsKt.lastOrNull((List) cases5);
                if (jsSwitchMember != null) {
                    List<JsStatement> statements2 = jsSwitchMember.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                    listMutableListOf.addAll(statements2);
                }
                ctx.replaceMe(new JsBlock((List<JsStatement>) listMutableListOf));
            }

            private final void processStatements(List<JsStatement> statements) {
                for (IndexedValue indexedValue : CollectionsKt.reversed(CollectionsKt.withIndex(statements))) {
                    int index = indexedValue.getIndex();
                    JsStatement jsStatement = (JsStatement) indexedValue.component2();
                    if (jsStatement instanceof JsEmpty) {
                        statements.remove(index);
                        EmptyStatementElimination.this.hasChanges = true;
                    } else if (jsStatement instanceof JsBlock) {
                        statements.remove(index);
                        List<JsStatement> statements2 = ((JsBlock) jsStatement).getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                        statements.addAll(index, statements2);
                    }
                }
            }

            private final boolean isEmpty(JsStatement statement) {
                return ((statement instanceof JsBlock) && ((JsBlock) statement).isEmpty()) || (statement instanceof JsEmpty);
            }
        }.accept(this.root);
        return this.hasChanges;
    }
}
