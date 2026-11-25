package org.jetbrains.kotlin.js.backend.ast;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.common.Symbol;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsVars extends SourceInfoAwareJsNode implements Iterable<JsVar>, JsStatement {
    private boolean multiline;
    private final List<JsVar> vars;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsVars";
        if (i != 1) {
            objArr[1] = "iterator";
        } else {
            objArr[1] = "deepCopy";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    public JsVars() {
        this(new SmartList(), false);
    }

    public JsVars(boolean z) {
        this(new SmartList(), z);
    }

    public JsVars(List<JsVar> list, boolean z) {
        this.vars = list;
        this.multiline = z;
    }

    public JsVars(JsVar jsVar) {
        this(new SmartList(jsVar), false);
    }

    public JsVars(JsVar... jsVarArr) {
        this(new SmartList((Object[]) jsVarArr), false);
    }

    public boolean isMultiline() {
        return this.multiline;
    }

    public void setMultiline(boolean z) {
        this.multiline = z;
    }

    public static class JsVar extends SourceInfoAwareJsNode implements HasName {
        private JsExpression initExpression;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private JsName f895name;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsVars$JsVar", "deepCopy"));
        }

        public JsVar(JsName jsName) {
            this.f895name = jsName;
        }

        public JsVar(JsName jsName, JsExpression jsExpression) {
            this.f895name = jsName;
            this.initExpression = jsExpression;
        }

        public JsExpression getInitExpression() {
            return this.initExpression;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.HasName
        public JsName getName() {
            return this.f895name;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.HasName
        public void setName(JsName jsName) {
            this.f895name = jsName;
        }

        @Override // org.jetbrains.kotlin.js.common.HasSymbol
        public Symbol getSymbol() {
            return this.f895name;
        }

        public void setInitExpression(JsExpression jsExpression) {
            this.initExpression = jsExpression;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
        public void accept(JsVisitor jsVisitor) {
            jsVisitor.visit(this);
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
        public void acceptChildren(JsVisitor jsVisitor) {
            JsExpression jsExpression = this.initExpression;
            if (jsExpression != null) {
                jsVisitor.accept(jsExpression);
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
        public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
            JsExpression jsExpression;
            if (jsVisitorWithContext.visit(this, jsContext) && (jsExpression = this.initExpression) != null) {
                this.initExpression = (JsExpression) jsVisitorWithContext.accept(jsExpression);
            }
            jsVisitorWithContext.endVisit(this, jsContext);
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
        public JsVar deepCopy() {
            JsExpression jsExpression = this.initExpression;
            if (jsExpression == null) {
                return new JsVar(this.f895name);
            }
            JsVar jsVar = (JsVar) new JsVar(this.f895name, jsExpression.deepCopy()).withMetadataFrom(this);
            if (jsVar == null) {
                $$$reportNull$$$0(0);
            }
            return jsVar;
        }
    }

    public void add(JsVar jsVar) {
        this.vars.add(jsVar);
    }

    public void addAll(Collection<? extends JsVar> collection) {
        this.vars.addAll(collection);
    }

    public void addAll(JsVars jsVars) {
        this.vars.addAll(jsVars.vars);
    }

    public void addIfHasInitializer(JsVar jsVar) {
        if (jsVar.getInitExpression() != null) {
            add(jsVar);
        }
    }

    public boolean isEmpty() {
        return this.vars.isEmpty();
    }

    @Override // java.lang.Iterable
    public Iterator<JsVar> iterator() {
        Iterator<JsVar> it = this.vars.iterator();
        if (it == null) {
            $$$reportNull$$$0(0);
        }
        return it;
    }

    public List<JsVar> getVars() {
        return this.vars;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitVars(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.acceptWithInsertRemove(this.vars);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            jsVisitorWithContext.acceptList(this.vars);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsVars deepCopy() {
        JsVars jsVars = (JsVars) new JsVars(AstUtil.deepCopy(this.vars), this.multiline).withMetadataFrom(this);
        if (jsVars == null) {
            $$$reportNull$$$0(1);
        }
        return jsVars;
    }
}
