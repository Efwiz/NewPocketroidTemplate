package org.jetbrains.kotlin.js.inline.clean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;

/* JADX INFO: compiled from: RedundantLabelRemoval.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0006\u0010\r\u001a\u00020\u0006J\b\u0010\u000e\u001a\u00020\fH\u0002J\u001e\u0010\u000e\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0002J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\tH\u0002J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\tH\u0002J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\tH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/RedundantLabelRemoval;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "hasChanges", "", "labelUsages", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "", "analyze", "", "apply", "perform", "statements", "", "name", "statement", "unuseLabel", "useLabel", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantLabelRemoval {
    private boolean hasChanges;
    private final Map<JsName, Integer> labelUsages;
    private final JsStatement root;

    public RedundantLabelRemoval(JsStatement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
        this.labelUsages = new LinkedHashMap();
    }

    public final boolean apply() {
        analyze();
        perform();
        return this.hasChanges;
    }

    private final void analyze() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.RedundantLabelRemoval.analyze.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsBreak x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                super.endVisit(x, (JsContext) ctx);
                JsNameRef label = x.getLabel();
                if (label != null) {
                    RedundantLabelRemoval redundantLabelRemoval = RedundantLabelRemoval.this;
                    JsName name2 = label.getName();
                    Intrinsics.checkNotNull(name2);
                    redundantLabelRemoval.useLabel(name2);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsContinue x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                super.endVisit(x, ctx);
                JsNameRef label = x.getLabel();
                if (label != null) {
                    RedundantLabelRemoval redundantLabelRemoval = RedundantLabelRemoval.this;
                    JsName name2 = label.getName();
                    Intrinsics.checkNotNull(name2);
                    redundantLabelRemoval.useLabel(name2);
                }
            }
        }.accept(this.root);
    }

    private final void perform() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.RedundantLabelRemoval.perform.1
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
                    RedundantLabelRemoval redundantLabelRemoval = RedundantLabelRemoval.this;
                    JsStatement statement = x.getStatement();
                    Intrinsics.checkNotNullExpressionValue(statement, "getStatement(...)");
                    JsName name2 = x.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    x.setStatement(redundantLabelRemoval.perform(statement, name2));
                    Integer num = (Integer) RedundantLabelRemoval.this.labelUsages.get(x.getName());
                    if ((num != null ? num.intValue() : 0) == 0) {
                        RedundantLabelRemoval.this.hasChanges = true;
                        ctx.replaceMe(x.getStatement());
                    }
                }
                super.endVisit(x, ctx);
            }
        }.accept(this.root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsStatement perform(JsStatement statement, JsName name2) {
        Object obj;
        if (statement instanceof JsBreak) {
            JsNameRef label = ((JsBreak) statement).getLabel();
            if (Intrinsics.areEqual(name2, label != null ? label.getName() : null)) {
                unuseLabel(name2);
                this.hasChanges = true;
                obj = JsEmpty.INSTANCE;
            } else {
                obj = statement;
            }
            return (JsStatement) obj;
        }
        if (statement instanceof JsLabel) {
            JsStatement statement2 = ((JsLabel) statement).getStatement();
            Intrinsics.checkNotNullExpressionValue(statement2, "getStatement(...)");
            perform(statement2, name2);
            return statement;
        }
        if (statement instanceof JsBlock) {
            List<JsStatement> statements = ((JsBlock) statement).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            perform(statements, name2);
            return statement;
        }
        if (statement instanceof JsIf) {
            JsIf jsIf = (JsIf) statement;
            JsStatement thenStatement = jsIf.getThenStatement();
            Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
            jsIf.setThenStatement(perform(thenStatement, name2));
            JsStatement elseStatement = jsIf.getElseStatement();
            jsIf.setElseStatement(elseStatement != null ? perform(elseStatement, name2) : null);
            return statement;
        }
        if (!(statement instanceof JsTry)) {
            return statement;
        }
        JsBlock tryBlock = ((JsTry) statement).getTryBlock();
        Intrinsics.checkNotNullExpressionValue(tryBlock, "getTryBlock(...)");
        perform(tryBlock, name2);
        return statement;
    }

    private final void perform(List<JsStatement> statements, JsName name2) {
        JsStatement jsStatement = (JsStatement) CollectionsKt.lastOrNull((List) statements);
        if (jsStatement != null) {
            statements.set(CollectionsKt.getLastIndex(statements), perform(jsStatement, name2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void useLabel(JsName name2) {
        Map<JsName, Integer> map = this.labelUsages;
        Integer num = map.get(name2);
        map.put(name2, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
    }

    private final void unuseLabel(JsName name2) {
        Map<JsName, Integer> map = this.labelUsages;
        Integer num = map.get(name2);
        Intrinsics.checkNotNull(num);
        map.put(name2, Integer.valueOf(num.intValue() - 1));
    }
}
