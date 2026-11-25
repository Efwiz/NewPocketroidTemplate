package org.jetbrains.kotlin.js.inline.util.rewriters;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsLoop;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;

/* JADX INFO: compiled from: ContinueReplacingVisitor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00182\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00192\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/util/rewriters/ContinueReplacingVisitor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "loopLabelName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "guardLabelName", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "getGuardLabelName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getLoopLabelName", "loopNestingLevel", "", "getLoopNestingLevel", "()I", "setLoopNestingLevel", "(I)V", "endVisit", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsLoop;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "visit", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContinueReplacingVisitor extends JsVisitorWithContextImpl {
    private final JsName guardLabelName;
    private final JsName loopLabelName;
    private int loopNestingLevel;

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsFunction x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return false;
    }

    public ContinueReplacingVisitor(JsName jsName, JsName guardLabelName) {
        Intrinsics.checkNotNullParameter(guardLabelName, "guardLabelName");
        this.loopLabelName = jsName;
        this.guardLabelName = guardLabelName;
    }

    public final JsName getGuardLabelName() {
        return this.guardLabelName;
    }

    public final JsName getLoopLabelName() {
        return this.loopLabelName;
    }

    public final int getLoopNestingLevel() {
        return this.loopNestingLevel;
    }

    public final void setLoopNestingLevel(int i) {
        this.loopNestingLevel = i;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsContinue x, JsContext<JsNode> ctx) {
        boolean zAreEqual;
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsNameRef label = x.getLabel();
        JsName name2 = label != null ? label.getName() : null;
        if (name2 == null) {
            zAreEqual = this.loopNestingLevel == 0;
        } else {
            zAreEqual = Intrinsics.areEqual(name2, this.loopLabelName);
        }
        if (zAreEqual) {
            ctx.replaceMe(new JsBreak(this.guardLabelName.makeRef()));
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsLoop x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (this.loopLabelName == null) {
            return false;
        }
        this.loopNestingLevel++;
        return super.visit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsLoop x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        super.endVisit(x, ctx);
        if (this.loopLabelName == null) {
            return;
        }
        this.loopNestingLevel--;
    }
}
