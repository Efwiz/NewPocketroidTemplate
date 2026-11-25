package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public class JsCatchScope extends JsDeclarationScope {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final JsName f884name;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "identifier";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsCatchScope";
        } else if (i != 3) {
            objArr[0] = "ident";
        } else {
            objArr[0] = "name";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsCatchScope";
        } else {
            objArr[1] = "declareName";
        }
        if (i == 1) {
            objArr[2] = "declareName";
        } else if (i != 2) {
            if (i == 3) {
                objArr[2] = "hasOwnName";
            } else if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "findOwnName";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsCatchScope(JsScope jsScope, String str) {
        super(jsScope, "Catch scope", true);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.f884name = new JsName(str, false);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    public JsName declareName(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        JsName jsNameDeclareName = getParent().declareName(str);
        if (jsNameDeclareName == null) {
            $$$reportNull$$$0(2);
        }
        return jsNameDeclareName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    public boolean hasOwnName(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return findOwnName(str) != null;
    }

    public JsCatchScope copy() {
        return new JsCatchScope(getParent(), this.f884name.getIdent());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    protected JsName findOwnName(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (this.f884name.getIdent().equals(str)) {
            return this.f884name;
        }
        return null;
    }
}
