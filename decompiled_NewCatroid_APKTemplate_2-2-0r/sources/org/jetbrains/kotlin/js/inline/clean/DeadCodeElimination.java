package org.jetbrains.kotlin.js.inline.clean;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: DeadCodeElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001:\u0001\fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\u0006R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/DeadCodeElimination;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "hasChanges", "", "getHasChanges", "()Z", "setHasChanges", "(Z)V", "apply", "EliminationVisitor", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeadCodeElimination {
    private boolean hasChanges;
    private final JsStatement root;

    public DeadCodeElimination(JsStatement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
    }

    public final boolean getHasChanges() {
        return this.hasChanges;
    }

    public final void setHasChanges(boolean z) {
        this.hasChanges = z;
    }

    public final boolean apply() {
        new EliminationVisitor().accept(this.root);
        return this.hasChanges;
    }

    /* JADX INFO: compiled from: DeadCodeElimination.kt */
    @Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020(H\u0016J \u0010)\u001a\u00020\u00142\b\u0010*\u001a\u0004\u0018\u00010+2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000b0-H\u0002J\u0010\u0010.\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020/H\u0016J\u0016\u00100\u001a\u00020\u00142\f\u00101\u001a\b\u0012\u0004\u0012\u00020+02H\u0002J\u0010\u00103\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u000206H\u0016J\u0010\u00107\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020:H\u0016R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000f¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/DeadCodeElimination$EliminationVisitor;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "(Lorg/jetbrains/kotlin/js/inline/clean/DeadCodeElimination;)V", "breakLabels", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getBreakLabels", "()Ljava/util/Set;", "setBreakLabels", "(Ljava/util/Set;)V", "canContinue", "", "getCanContinue", "()Z", "setCanContinue", "(Z)V", "localBreakExists", "getLocalBreakExists", "setLocalBreakExists", "visit", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsSwitch;", "visitBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "visitBreak", "Lorg/jetbrains/kotlin/js/backend/ast/JsBreak;", "visitContinue", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "visitDoWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", "visitExpressionStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "visitFor", "Lorg/jetbrains/kotlin/js/backend/ast/JsFor;", "visitForIn", "Lorg/jetbrains/kotlin/js/backend/ast/JsForIn;", "visitIf", "Lorg/jetbrains/kotlin/js/backend/ast/JsIf;", "visitLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "visitLoop", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "additionalExitCondition", "Lkotlin/Function0;", "visitReturn", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "visitStatements", "statements", "", "visitThrow", "Lorg/jetbrains/kotlin/js/backend/ast/JsThrow;", "visitTry", "Lorg/jetbrains/kotlin/js/backend/ast/JsTry;", "visitVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "visitWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class EliminationVisitor extends RecursiveJsVisitor {
        private Set<JsName> breakLabels = new LinkedHashSet();
        private boolean canContinue;
        private boolean localBreakExists;

        public EliminationVisitor() {
        }

        public final Set<JsName> getBreakLabels() {
            return this.breakLabels;
        }

        public final void setBreakLabels(Set<JsName> set) {
            Intrinsics.checkNotNullParameter(set, "<set-?>");
            this.breakLabels = set;
        }

        public final boolean getLocalBreakExists() {
            return this.localBreakExists;
        }

        public final void setLocalBreakExists(boolean z) {
            this.localBreakExists = z;
        }

        public final boolean getCanContinue() {
            return this.canContinue;
        }

        public final void setCanContinue(boolean z) {
            this.canContinue = z;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitBreak(JsBreak x) {
            Intrinsics.checkNotNullParameter(x, "x");
            JsNameRef label = x.getLabel();
            JsName name2 = label != null ? label.getName() : null;
            if (name2 != null) {
                this.breakLabels.add(name2);
            } else {
                this.localBreakExists = true;
            }
            this.canContinue = false;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitContinue(JsContinue x) {
            Intrinsics.checkNotNullParameter(x, "x");
            this.canContinue = false;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitLabel(JsLabel x) {
            Intrinsics.checkNotNullParameter(x, "x");
            accept(x.getStatement());
            if (!this.canContinue && this.breakLabels.contains(x.getName())) {
                this.canContinue = true;
            }
            this.breakLabels.remove(x.getName());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitBlock(JsBlock x) {
            Intrinsics.checkNotNullParameter(x, "x");
            this.canContinue = true;
            List<JsStatement> statements = x.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            visitStatements(statements);
        }

        private final void visitStatements(List<JsStatement> statements) {
            Iterator<JsStatement> it = statements.iterator();
            int i = 0;
            while (it.hasNext()) {
                i++;
                accept(it.next());
                if (!this.canContinue) {
                    List<JsStatement> listSubList = statements.subList(i, statements.size());
                    if (listSubList.isEmpty()) {
                        return;
                    }
                    DeadCodeElimination.this.setHasChanges(true);
                    listSubList.clear();
                    return;
                }
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitWhile(final JsWhile x) {
            Intrinsics.checkNotNullParameter(x, "x");
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getCondition());
            visitLoop(x.getBody(), new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.DeadCodeElimination$EliminationVisitor$visitWhile$1
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Boolean invoke() {
                    JsExpression condition = x.getCondition();
                    return Boolean.valueOf(((condition instanceof JsBooleanLiteral) && ((JsBooleanLiteral) condition).getValue()) ? false : true);
                }
            });
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitDoWhile(JsDoWhile x) {
            Intrinsics.checkNotNullParameter(x, "x");
            visitWhile(x);
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitFor(JsFor x) {
            Intrinsics.checkNotNullParameter(x, "x");
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getCondition());
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getInitExpression());
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getInitVars());
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getIncrementExpression());
            visitLoop(x.getBody(), new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.DeadCodeElimination$EliminationVisitor$visitFor$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Boolean invoke() {
                    return true;
                }
            });
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitForIn(JsForIn x) {
            Intrinsics.checkNotNullParameter(x, "x");
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getIterExpression());
            visitLoop(x.getBody(), new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.DeadCodeElimination$EliminationVisitor$visitForIn$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Boolean invoke() {
                    return true;
                }
            });
        }

        private final void visitLoop(JsStatement body, Function0<Boolean> additionalExitCondition) {
            boolean z = this.localBreakExists;
            this.localBreakExists = false;
            if (body != null) {
                accept(body);
            }
            if (!this.canContinue) {
                this.canContinue = additionalExitCondition.invoke().booleanValue() || this.localBreakExists;
            }
            this.localBreakExists = z;
        }

        /* JADX WARN: Code duplicated, block: B:6:0x0034  */
        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitIf(JsIf x) {
            Intrinsics.checkNotNullParameter(x, "x");
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getIfExpression());
            accept(x.getThenStatement());
            boolean z = this.canContinue;
            if (x.getElseStatement() != null) {
                accept(x.getElseStatement());
                if (this.canContinue) {
                    z = true;
                }
            } else {
                z = true;
            }
            this.canContinue = z;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitTry(JsTry x) {
            Intrinsics.checkNotNullParameter(x, "x");
            accept(x.getTryBlock());
            boolean z = this.canContinue;
            Iterator<JsCatch> it = x.getCatches().iterator();
            while (it.hasNext()) {
                accept(it.next().getBody());
                if (this.canContinue) {
                    z = true;
                }
            }
            JsBlock finallyBlock = x.getFinallyBlock();
            if (finallyBlock != null) {
                accept(finallyBlock);
                if (!this.canContinue) {
                    z = false;
                }
            }
            this.canContinue = z;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitExpressionStatement(JsExpressionStatement x) {
            Intrinsics.checkNotNullParameter(x, "x");
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getExpression());
            this.canContinue = true;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visit(JsSwitch x) {
            Intrinsics.checkNotNullParameter(x, "x");
            DeadCodeElimination.this.new EliminationVisitor().accept(x.getExpression());
            boolean z = this.localBreakExists;
            boolean z2 = true;
            boolean z3 = true;
            boolean z4 = true;
            for (JsSwitchMember jsSwitchMember : x.getCases()) {
                this.canContinue = true;
                List<JsStatement> statements = jsSwitchMember.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                visitStatements(statements);
                if (!this.canContinue && this.localBreakExists) {
                    this.canContinue = true;
                }
                if (jsSwitchMember instanceof JsDefault) {
                    z4 = this.canContinue;
                } else if (z3 && this.canContinue) {
                    z3 = false;
                }
            }
            if (z3 && !z4) {
                z2 = false;
            }
            this.canContinue = z2;
            this.localBreakExists = z;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitThrow(JsThrow x) {
            Intrinsics.checkNotNullParameter(x, "x");
            this.canContinue = false;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitReturn(JsReturn x) {
            Intrinsics.checkNotNullParameter(x, "x");
            this.canContinue = false;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitVars(JsVars x) {
            Intrinsics.checkNotNullParameter(x, "x");
            List<JsVars.JsVar> vars = x.getVars();
            Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
            DeadCodeElimination deadCodeElimination = DeadCodeElimination.this;
            Iterator<T> it = vars.iterator();
            while (it.hasNext()) {
                deadCodeElimination.new EliminationVisitor().accept((JsVars.JsVar) it.next());
            }
            this.canContinue = true;
        }
    }
}
