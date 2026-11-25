package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.common.Symbol;

/* JADX INFO: loaded from: classes5.dex */
public final class JsParameter extends SourceInfoAwareJsNode implements HasName {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private JsName f894name;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsParameter";
        } else {
            objArr[0] = "name";
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i == 3) {
            objArr[1] = "getSymbol";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsParameter";
        } else {
            objArr[1] = "deepCopy";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "setName";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JsParameter(JsName jsName) {
        if (jsName == null) {
            $$$reportNull$$$0(0);
        }
        this.f894name = jsName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public JsName getName() {
        JsName jsName = this.f894name;
        if (jsName == null) {
            $$$reportNull$$$0(1);
        }
        return jsName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public void setName(JsName jsName) {
        if (jsName == null) {
            $$$reportNull$$$0(2);
        }
        this.f894name = jsName;
    }

    @Override // org.jetbrains.kotlin.js.common.HasSymbol
    public Symbol getSymbol() {
        JsName jsName = this.f894name;
        if (jsName == null) {
            $$$reportNull$$$0(3);
        }
        return jsName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitParameter(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        jsVisitorWithContext.visit(this, jsContext);
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsParameter deepCopy() {
        JsParameter jsParameter = (JsParameter) new JsParameter(this.f894name).withMetadataFrom(this);
        if (jsParameter == null) {
            $$$reportNull$$$0(4);
        }
        return jsParameter;
    }
}
