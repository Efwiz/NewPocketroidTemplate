package org.luaj.vm2.ast;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public abstract class Stat extends SyntaxElement {

    public static class Assign extends Stat {
        public final List exps;
        public final List vars;

        public Assign(List list, List list2) {
            this.vars = list;
            this.exps = list2;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Break extends Stat {
        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class FuncCallStat extends Stat {
        public final Exp.FuncCall funccall;

        public FuncCallStat(Exp.FuncCall funcCall) {
            this.funccall = funcCall;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class FuncDef extends Stat {
        public final FuncBody body;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final FuncName f1049name;

        public FuncDef(FuncName funcName, FuncBody funcBody) {
            this.f1049name = funcName;
            this.body = funcBody;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class GenericFor extends Stat {
        public Block block;
        public List exps;
        public List names;
        public NameScope scope;

        public GenericFor(List list, List list2, Block block) {
            this.names = list;
            this.exps = list2;
            this.block = block;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Goto extends Stat {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final String f1050name;

        public Goto(String str) {
            this.f1050name = str;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class IfThenElse extends Stat {
        public final Block elseblock;
        public final List elseifblocks;
        public final List elseifexps;
        public final Block ifblock;
        public final Exp ifexp;

        public IfThenElse(Exp exp, Block block, List list, List list2, Block block2) {
            this.ifexp = exp;
            this.ifblock = block;
            this.elseifexps = list;
            this.elseifblocks = list2;
            this.elseblock = block2;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Label extends Stat {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final String f1051name;

        public Label(String str) {
            this.f1051name = str;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class LocalAssign extends Stat {
        public final List names;
        public final List values;

        public LocalAssign(List list, List list2) {
            this.names = list;
            this.values = list2;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class LocalFuncDef extends Stat {
        public final FuncBody body;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final Name f1052name;

        public LocalFuncDef(String str, FuncBody funcBody) {
            this.f1052name = new Name(str);
            this.body = funcBody;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class NumericFor extends Stat {
        public final Block block;
        public final Exp initial;
        public final Exp limit;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final Name f1053name;
        public NameScope scope;
        public final Exp step;

        public NumericFor(String str, Exp exp, Exp exp2, Exp exp3, Block block) {
            this.f1053name = new Name(str);
            this.initial = exp;
            this.limit = exp2;
            this.step = exp3;
            this.block = block;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class RepeatUntil extends Stat {
        public final Block block;
        public final Exp exp;

        public RepeatUntil(Block block, Exp exp) {
            this.block = block;
            this.exp = exp;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Return extends Stat {
        public final List values;

        public Return(List list) {
            this.values = list;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public int nreturns() {
            List list = this.values;
            int size = list != null ? list.size() : 0;
            if (size <= 0 || !((Exp) this.values.get(size - 1)).isvarargexp()) {
                return size;
            }
            return -1;
        }
    }

    public static class WhileDo extends Stat {
        public final Block block;
        public final Exp exp;

        public WhileDo(Exp exp, Block block) {
            this.exp = exp;
            this.block = block;
        }

        @Override // org.luaj.vm2.ast.Stat
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static Stat assignment(List list, List list2) {
        return new Assign(list, list2);
    }

    public static Stat block(Block block) {
        return block;
    }

    public static Stat breakstat() {
        return new Break();
    }

    public static Stat forgeneric(List list, List list2, Block block) {
        return new GenericFor(list, list2, block);
    }

    public static Stat fornumeric(String str, Exp exp, Exp exp2, Exp exp3, Block block) {
        return new NumericFor(str, exp, exp2, exp3, block);
    }

    public static Stat functioncall(Exp.FuncCall funcCall) {
        return new FuncCallStat(funcCall);
    }

    public static Stat functiondef(FuncName funcName, FuncBody funcBody) {
        return new FuncDef(funcName, funcBody);
    }

    public static Stat gotostat(String str) {
        return new Goto(str);
    }

    public static Stat ifthenelse(Exp exp, Block block, List list, List list2, Block block2) {
        return new IfThenElse(exp, block, list, list2, block2);
    }

    public static Stat labelstat(String str) {
        return new Label(str);
    }

    public static Stat localassignment(List list, List list2) {
        return new LocalAssign(list, list2);
    }

    public static Stat localfunctiondef(String str, FuncBody funcBody) {
        return new LocalFuncDef(str, funcBody);
    }

    public static Stat repeatuntil(Block block, Exp exp) {
        return new RepeatUntil(block, exp);
    }

    public static Stat returnstat(List list) {
        return new Return(list);
    }

    public static Stat whiledo(Exp exp, Block block) {
        return new WhileDo(exp, block);
    }

    public abstract void accept(Visitor visitor);
}
