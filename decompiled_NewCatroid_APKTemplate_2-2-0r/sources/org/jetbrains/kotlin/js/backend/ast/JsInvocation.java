package org.jetbrains.kotlin.js.backend.ast;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsInvocation extends JsExpression.JsExpressionHasArguments {
    private JsExpression qualifier;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 6) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "arguments";
        } else if (i == 6 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsInvocation";
        } else {
            objArr[0] = "qualifier";
        }
        if (i == 3) {
            objArr[1] = "getArguments";
        } else if (i == 4) {
            objArr[1] = "getQualifier";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsInvocation";
        } else {
            objArr[1] = "deepCopy";
        }
        if (i != 3 && i != 4) {
            if (i == 5) {
                objArr[2] = "setQualifier";
            } else if (i != 6) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsInvocation(JsExpression jsExpression, List<? extends JsExpression> list) {
        super(new SmartList((Collection) list));
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        this.qualifier = jsExpression;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsInvocation(JsExpression jsExpression, JsExpression... jsExpressionArr) {
        this(jsExpression, new SmartList((Object[]) jsExpressionArr));
        if (jsExpression == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression.JsExpressionHasArguments, org.jetbrains.kotlin.js.backend.ast.HasArguments
    public List<JsExpression> getArguments() {
        List<JsExpression> list = this.arguments;
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        return list;
    }

    public JsExpression getQualifier() {
        JsExpression jsExpression = this.qualifier;
        if (jsExpression == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpression;
    }

    public void setQualifier(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(5);
        }
        this.qualifier = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitInvocation(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.qualifier);
        jsVisitor.acceptList(this.arguments);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.qualifier = (JsExpression) jsVisitorWithContext.accept(this.qualifier);
            jsVisitorWithContext.acceptList(this.arguments);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression.JsExpressionHasArguments, org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsInvocation deepCopy() {
        JsInvocation jsInvocation = (JsInvocation) new JsInvocation((JsExpression) AstUtil.deepCopy(this.qualifier), (List<? extends JsExpression>) AstUtil.deepCopy(this.arguments)).withMetadataFrom(this);
        if (jsInvocation == null) {
            $$$reportNull$$$0(6);
        }
        return jsInvocation;
    }
}
