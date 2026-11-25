package org.jetbrains.kotlin.js.inline.clean;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: WhileConditionFolding.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\t\u001a\u00020\bJ\u001a\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/WhileConditionFolding;", "", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;)V", "getBody", "()Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "changed", "", "apply", "hasContinue", "statement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WhileConditionFolding {
    private final JsBlock body;
    private boolean changed;

    public WhileConditionFolding(JsBlock body) {
        Intrinsics.checkNotNullParameter(body, "body");
        this.body = body;
    }

    public final JsBlock getBody() {
        return this.body;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding$apply$1, reason: invalid class name */
    /* JADX INFO: compiled from: WhileConditionFolding.kt */
    @Metadata(d1 = {"\u0000S\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0010\u0010\t\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0007H\u0002J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0007H\u0002J\\\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u00072\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0010\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\fH\u0016J\u0010\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000eH\u0016¨\u0006\u001e"}, d2 = {"org/jetbrains/kotlin/js/inline/clean/WhileConditionFolding$apply$1", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "extractCondition", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "statement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "first", "last", "process", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;", "find", "Lkotlin/Function1;", ProductAction.ACTION_REMOVE, "combine", "Lkotlin/Function2;", "removeFirst", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "removeLast", "visitDoWhile", "x", "visitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "visitWhile", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 extends RecursiveJsVisitor {
        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitFunction(JsFunction x) {
            Intrinsics.checkNotNullParameter(x, "x");
        }

        AnonymousClass1() {
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitLabel(JsLabel x) {
            Intrinsics.checkNotNullParameter(x, "x");
            JsStatement statement = x.getStatement();
            if (statement instanceof JsWhile) {
                Intrinsics.checkNotNull(statement);
                process((JsWhile) statement, x.getName());
            } else if (!(statement instanceof JsDoWhile)) {
                super.visitLabel(x);
            } else {
                Intrinsics.checkNotNull(statement);
                process((JsDoWhile) statement, x.getName());
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitWhile(JsWhile x) {
            Intrinsics.checkNotNullParameter(x, "x");
            process(x, (JsName) null);
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitDoWhile(JsDoWhile x) {
            Intrinsics.checkNotNullParameter(x, "x");
            process(x, (JsName) null);
        }

        private final void process(JsWhile statement, JsName name2) {
            process(statement, name2, new Function1<JsStatement, JsStatement>() { // from class: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding$apply$1$process$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(JsStatement it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.first(it);
                }
            }, new Function1<JsStatement, JsStatement>() { // from class: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding$apply$1$process$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(JsStatement it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.removeFirst(it);
                }
            }, new Function2<JsExpression, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding$apply$1$process$3
                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(JsExpression a, JsExpression b) {
                    Intrinsics.checkNotNullParameter(a, "a");
                    Intrinsics.checkNotNullParameter(b, "b");
                    JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and(a, b);
                    Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAnd, "and(...)");
                    return jsBinaryOperationAnd;
                }
            });
        }

        private final void process(JsDoWhile statement, JsName name2) {
            WhileConditionFolding whileConditionFolding = WhileConditionFolding.this;
            JsStatement body = statement.getBody();
            Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
            if (whileConditionFolding.hasContinue(body, name2)) {
                return;
            }
            process(statement, name2, new Function1<JsStatement, JsStatement>() { // from class: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding$apply$1$process$4
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(JsStatement it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.last(it);
                }
            }, new Function1<JsStatement, JsStatement>() { // from class: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding$apply$1$process$5
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(JsStatement it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.removeLast(it);
                }
            }, new Function2<JsExpression, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding$apply$1$process$6
                @Override // kotlin.jvm.functions.Function2
                public final JsExpression invoke(JsExpression a, JsExpression b) {
                    Intrinsics.checkNotNullParameter(a, "a");
                    Intrinsics.checkNotNullParameter(b, "b");
                    JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and(b, a);
                    Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAnd, "and(...)");
                    return jsBinaryOperationAnd;
                }
            });
        }

        private final void process(JsWhile statement, JsName name2, Function1<? super JsStatement, ? extends JsStatement> find, Function1<? super JsStatement, ? extends JsStatement> remove, Function2<? super JsExpression, ? super JsExpression, ? extends JsExpression> combine) {
            statement.getBody().accept(this);
            while (true) {
                JsStatement body = statement.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                JsExpression jsExpressionExtractCondition = extractCondition(find.invoke(body), name2);
                if (jsExpressionExtractCondition == null) {
                    return;
                }
                JsStatement body2 = statement.getBody();
                Intrinsics.checkNotNullExpressionValue(body2, "getBody(...)");
                statement.setBody(remove.invoke(body2));
                JsExpression condition = statement.getCondition();
                if (!JsBooleanLiteral.isTrue(condition)) {
                    Intrinsics.checkNotNull(condition);
                    jsExpressionExtractCondition = combine.invoke(condition, jsExpressionExtractCondition);
                }
                statement.setCondition(jsExpressionExtractCondition);
                WhileConditionFolding.this.changed = true;
            }
        }

        private final JsExpression extractCondition(JsStatement statement, JsName label) {
            JsExpression jsExpressionExtractCondition;
            if (statement instanceof JsBreak) {
                JsNameRef label2 = ((JsBreak) statement).getLabel();
                return Intrinsics.areEqual(label, label2 != null ? label2.getName() : null) ? new JsBooleanLiteral(false) : null;
            }
            if (statement instanceof JsIf) {
                JsIf jsIf = (JsIf) statement;
                JsStatement thenStatement = jsIf.getThenStatement();
                Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
                if (jsIf.getElseStatement() != null || (jsExpressionExtractCondition = extractCondition(thenStatement, label)) == null) {
                    return null;
                }
                return JsBooleanLiteral.isFalse(jsExpressionExtractCondition) ? JsAstUtils.notOptimized(jsIf.getIfExpression()) : JsAstUtils.or(JsAstUtils.notOptimized(jsIf.getIfExpression()), jsExpressionExtractCondition);
            }
            if (!(statement instanceof JsBlock)) {
                return null;
            }
            JsBlock jsBlock = (JsBlock) statement;
            if (jsBlock.getStatements().size() != 1) {
                return null;
            }
            JsStatement jsStatement = jsBlock.getStatements().get(0);
            Intrinsics.checkNotNullExpressionValue(jsStatement, "get(...)");
            return extractCondition(jsStatement, label);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final JsStatement first(JsStatement statement) {
            if (!(statement instanceof JsBlock)) {
                return statement;
            }
            List<JsStatement> statements = ((JsBlock) statement).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsStatement jsStatement = (JsStatement) CollectionsKt.firstOrNull((List) statements);
            return jsStatement == null ? statement : jsStatement;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final JsBlock removeFirst(JsStatement statement) {
            if (statement instanceof JsBlock) {
                JsBlock jsBlock = (JsBlock) statement;
                List<JsStatement> statements = jsBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                if (statements.isEmpty()) {
                    return jsBlock;
                }
                statements.remove(0);
                return jsBlock;
            }
            return new JsBlock();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final JsStatement last(JsStatement statement) {
            if (!(statement instanceof JsBlock)) {
                return statement;
            }
            List<JsStatement> statements = ((JsBlock) statement).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsStatement jsStatement = (JsStatement) CollectionsKt.lastOrNull((List) statements);
            return jsStatement == null ? statement : jsStatement;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final JsBlock removeLast(JsStatement statement) {
            if (statement instanceof JsBlock) {
                JsBlock jsBlock = (JsBlock) statement;
                List<JsStatement> statements = jsBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                if (statements.isEmpty()) {
                    return jsBlock;
                }
                statements.remove(CollectionsKt.getLastIndex(statements));
                return jsBlock;
            }
            return new JsBlock();
        }
    }

    public final boolean apply() {
        this.body.accept(new AnonymousClass1());
        return this.changed;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasContinue(JsStatement statement, final JsName label) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        statement.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.WhileConditionFolding.hasContinue.1
            private int level;

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsNameRef label2 = x.getLabel();
                JsName name2 = label2 != null ? label2.getName() : null;
                if (name2 == null) {
                    if (this.level == 0) {
                        booleanRef.element = true;
                    }
                } else if (Intrinsics.areEqual(name2, label)) {
                    booleanRef.element = true;
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFor(JsFor x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.level++;
                super.visitFor(x);
                this.level--;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitWhile(JsWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.level++;
                super.visitWhile(x);
                this.level--;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDoWhile(JsDoWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.level++;
                super.visitDoWhile(x);
                this.level--;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitForIn(JsForIn x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.level++;
                super.visitForIn(x);
                this.level--;
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
}
