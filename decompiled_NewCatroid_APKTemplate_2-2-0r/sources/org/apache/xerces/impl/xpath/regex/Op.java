package org.apache.xerces.impl.xpath.regex;

import java.util.Vector;

/* JADX INFO: loaded from: classes13.dex */
class Op {
    static final int ANCHOR = 5;
    static final int BACKREFERENCE = 16;
    static final int CAPTURE = 15;
    static final int CHAR = 1;
    static final int CLOSURE = 7;
    static final int CONDITION = 26;
    static final boolean COUNT = false;
    static final int DOT = 0;
    static final int INDEPENDENT = 24;
    static final int LOOKAHEAD = 20;
    static final int LOOKBEHIND = 22;
    static final int MODIFIER = 25;
    static final int NEGATIVELOOKAHEAD = 21;
    static final int NEGATIVELOOKBEHIND = 23;
    static final int NONGREEDYCLOSURE = 8;
    static final int NONGREEDYQUESTION = 10;
    static final int NRANGE = 4;
    static final int QUESTION = 9;
    static final int RANGE = 3;
    static final int STRING = 6;
    static final int UNION = 11;
    static int nofinstances;
    Op next = null;
    final int type;

    static class CharOp extends Op {
        final int charData;

        CharOp(int i, int i2) {
            super(i);
            this.charData = i2;
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        int getData() {
            return this.charData;
        }
    }

    static class ChildOp extends Op {
        Op child;

        ChildOp(int i) {
            super(i);
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        Op getChild() {
            return this.child;
        }

        void setChild(Op op) {
            this.child = op;
        }
    }

    static class ConditionOp extends Op {
        final Op condition;
        final Op no;
        final int refNumber;
        final Op yes;

        ConditionOp(int i, int i2, Op op, Op op2, Op op3) {
            super(i);
            this.refNumber = i2;
            this.condition = op;
            this.yes = op2;
            this.no = op3;
        }
    }

    static class ModifierOp extends ChildOp {
        final int v1;
        final int v2;

        ModifierOp(int i, int i2, int i3) {
            super(i);
            this.v1 = i2;
            this.v2 = i3;
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        int getData() {
            return this.v1;
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        int getData2() {
            return this.v2;
        }
    }

    static class RangeOp extends Op {
        final Token tok;

        RangeOp(int i, Token token) {
            super(i);
            this.tok = token;
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        RangeToken getToken() {
            return (RangeToken) this.tok;
        }
    }

    static class StringOp extends Op {
        final String string;

        StringOp(int i, String str) {
            super(i);
            this.string = str;
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        String getString() {
            return this.string;
        }
    }

    static class UnionOp extends Op {
        final Vector branches;

        UnionOp(int i, int i2) {
            super(i);
            this.branches = new Vector(i2);
        }

        void addElement(Op op) {
            this.branches.addElement(op);
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        Op elementAt(int i) {
            return (Op) this.branches.elementAt(i);
        }

        @Override // org.apache.xerces.impl.xpath.regex.Op
        int size() {
            return this.branches.size();
        }
    }

    protected Op(int i) {
        this.type = i;
    }

    static CharOp createAnchor(int i) {
        return new CharOp(5, i);
    }

    static CharOp createBackReference(int i) {
        return new CharOp(16, i);
    }

    static CharOp createCapture(int i, Op op) {
        CharOp charOp = new CharOp(15, i);
        charOp.next = op;
        return charOp;
    }

    static CharOp createChar(int i) {
        return new CharOp(1, i);
    }

    static ChildOp createClosure(int i) {
        return new ModifierOp(7, i, -1);
    }

    static ConditionOp createCondition(Op op, int i, Op op2, Op op3, Op op4) {
        ConditionOp conditionOp = new ConditionOp(26, i, op2, op3, op4);
        conditionOp.next = op;
        return conditionOp;
    }

    static Op createDot() {
        return new Op(0);
    }

    static ChildOp createIndependent(Op op, Op op2) {
        ChildOp childOp = new ChildOp(24);
        childOp.setChild(op2);
        childOp.next = op;
        return childOp;
    }

    static ChildOp createLook(int i, Op op, Op op2) {
        ChildOp childOp = new ChildOp(i);
        childOp.setChild(op2);
        childOp.next = op;
        return childOp;
    }

    static ModifierOp createModifier(Op op, Op op2, int i, int i2) {
        ModifierOp modifierOp = new ModifierOp(25, i, i2);
        modifierOp.setChild(op2);
        modifierOp.next = op;
        return modifierOp;
    }

    static ChildOp createNonGreedyClosure() {
        return new ChildOp(8);
    }

    static ChildOp createQuestion(boolean z) {
        return new ChildOp(z ? 10 : 9);
    }

    static RangeOp createRange(Token token) {
        return new RangeOp(3, token);
    }

    static StringOp createString(String str) {
        return new StringOp(6, str);
    }

    static UnionOp createUnion(int i) {
        return new UnionOp(11, i);
    }

    Op elementAt(int i) {
        throw new RuntimeException("Internal Error: type=" + this.type);
    }

    Op getChild() {
        throw new RuntimeException("Internal Error: type=" + this.type);
    }

    int getData() {
        throw new RuntimeException("Internal Error: type=" + this.type);
    }

    int getData2() {
        throw new RuntimeException("Internal Error: type=" + this.type);
    }

    String getString() {
        throw new RuntimeException("Internal Error: type=" + this.type);
    }

    RangeToken getToken() {
        throw new RuntimeException("Internal Error: type=" + this.type);
    }

    int size() {
        return 0;
    }
}
