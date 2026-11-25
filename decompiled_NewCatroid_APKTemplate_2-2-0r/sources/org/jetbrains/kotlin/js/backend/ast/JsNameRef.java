package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.common.Symbol;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsNameRef extends JsExpression implements HasName {
    private String ident;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private JsName f893name;
    private JsExpression qualifier;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "ident";
                break;
            case 4:
                objArr[0] = "qualifier";
                break;
            case 5:
            default:
                objArr[0] = "name";
                break;
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsNameRef";
                break;
        }
        if (i == 6) {
            objArr[1] = "getIdent";
        } else if (i == 7 || i == 8) {
            objArr[1] = "deepCopy";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsNameRef";
        }
        if (i != 6 && i != 7 && i != 8) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JsNameRef(JsName jsName) {
        if (jsName == null) {
            $$$reportNull$$$0(0);
        }
        this.f893name = jsName;
    }

    public JsNameRef(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.ident = str;
    }

    public JsNameRef(String str, JsExpression jsExpression) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        this.ident = str;
        this.qualifier = jsExpression;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsNameRef(String str, String str2) {
        this(str, new JsNameRef(str2));
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (str2 == null) {
            $$$reportNull$$$0(4);
        }
    }

    public JsNameRef(JsName jsName, JsExpression jsExpression) {
        if (jsName == null) {
            $$$reportNull$$$0(5);
        }
        this.f893name = jsName;
        this.qualifier = jsExpression;
    }

    public String getIdent() {
        JsName jsName = this.f893name;
        String ident = jsName == null ? this.ident : jsName.getIdent();
        if (ident == null) {
            $$$reportNull$$$0(6);
        }
        return ident;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public JsName getName() {
        return this.f893name;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public void setName(JsName jsName) {
        this.f893name = jsName;
    }

    @Override // org.jetbrains.kotlin.js.common.HasSymbol
    public Symbol getSymbol() {
        return this.f893name;
    }

    public JsExpression getQualifier() {
        return this.qualifier;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression
    public boolean isLeaf() {
        return this.qualifier == null;
    }

    public void resolve(JsName jsName) {
        this.f893name = jsName;
        this.ident = null;
    }

    public void setQualifier(JsExpression jsExpression) {
        this.qualifier = jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitNameRef(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        JsExpression jsExpression = this.qualifier;
        if (jsExpression != null) {
            jsVisitor.accept(jsExpression);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        JsExpression jsExpression;
        if (jsVisitorWithContext.visit(this, jsContext) && (jsExpression = this.qualifier) != null) {
            this.qualifier = (JsExpression) jsVisitorWithContext.accept(jsExpression);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsNameRef deepCopy() {
        JsExpression jsExpression = (JsExpression) AstUtil.deepCopy(this.qualifier);
        JsName jsName = this.f893name;
        if (jsName != null) {
            JsNameRef jsNameRef = (JsNameRef) new JsNameRef(jsName, jsExpression).withMetadataFrom(this);
            if (jsNameRef == null) {
                $$$reportNull$$$0(7);
            }
            return jsNameRef;
        }
        JsNameRef jsNameRef2 = (JsNameRef) new JsNameRef(this.ident, jsExpression).withMetadataFrom(this);
        if (jsNameRef2 == null) {
            $$$reportNull$$$0(8);
        }
        return jsNameRef2;
    }
}
