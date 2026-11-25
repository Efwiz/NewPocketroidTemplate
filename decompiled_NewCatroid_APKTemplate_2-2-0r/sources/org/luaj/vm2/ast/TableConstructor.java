package org.luaj.vm2.ast;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class TableConstructor extends Exp {
    public List fields;

    @Override // org.luaj.vm2.ast.Exp
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
