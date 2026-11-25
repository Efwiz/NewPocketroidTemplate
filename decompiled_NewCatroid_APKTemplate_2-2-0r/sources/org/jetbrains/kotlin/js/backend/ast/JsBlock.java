package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsBlock extends SourceInfoAwareJsNode implements JsStatement {
    private Object closingBraceSource;
    private final List<JsStatement> statements;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsBlock";
        } else {
            objArr[0] = "statements";
        }
        if (i == 1) {
            objArr[1] = "getStatements";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsBlock";
        } else {
            objArr[1] = "deepCopy";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public boolean isTransparent() {
        return false;
    }

    public JsBlock() {
        this(new SmartList());
    }

    public JsBlock(JsStatement jsStatement) {
        this(new SmartList(jsStatement));
    }

    public JsBlock(JsStatement... jsStatementArr) {
        this(new SmartList((Object[]) jsStatementArr));
    }

    public JsBlock(List<JsStatement> list) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        this.statements = list;
    }

    public List<JsStatement> getStatements() {
        List<JsStatement> list = this.statements;
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        return list;
    }

    public Object getClosingBraceSource() {
        return this.closingBraceSource;
    }

    public void setClosingBraceSource(Object obj) {
        this.closingBraceSource = obj;
    }

    public boolean isEmpty() {
        return this.statements.isEmpty();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitBlock(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.acceptWithInsertRemove(this.statements);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            jsVisitorWithContext.acceptStatementList(this.statements);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsBlock deepCopy() {
        JsBlock jsBlock = (JsBlock) new JsBlock((List<JsStatement>) AstUtil.deepCopy(this.statements)).withMetadataFrom(this);
        jsBlock.setClosingBraceSource(getClosingBraceSource());
        if (jsBlock == null) {
            $$$reportNull$$$0(2);
        }
        return jsBlock;
    }
}
