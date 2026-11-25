package org.luaj.vm2.ast;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class Block extends Stat {
    public NameScope scope;
    public List stats = new ArrayList();

    @Override // org.luaj.vm2.ast.Stat
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void add(Stat stat) {
        if (stat == null) {
            return;
        }
        this.stats.add(stat);
    }
}
