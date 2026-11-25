package org.jetbrains.kotlin.js.backend.ast;

import org.jetbrains.kotlin.js.backend.JsReservedIdentifiers;

/* JADX INFO: loaded from: classes5.dex */
public final class JsRootScope extends JsScope {
    private final JsProgram program;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "ident", "org/jetbrains/kotlin/js/backend/ast/JsRootScope", "findOwnName"));
    }

    public JsRootScope(JsProgram jsProgram) {
        super("Root");
        this.program = jsProgram;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    public JsProgram getProgram() {
        return this.program;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsScope
    protected JsName findOwnName(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        JsName jsNameFindOwnName = super.findOwnName(str);
        return (jsNameFindOwnName == null && JsReservedIdentifiers.reservedGlobalSymbols.contains(str)) ? doCreateName(str) : jsNameFindOwnName;
    }
}
