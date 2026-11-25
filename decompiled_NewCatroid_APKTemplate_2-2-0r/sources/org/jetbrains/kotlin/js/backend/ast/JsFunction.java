package org.jetbrains.kotlin.js.backend.ast;

import com.google.android.gms.cast.MediaTrack;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.common.Symbol;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public final class JsFunction extends JsLiteral implements HasName {
    private JsBlock body;
    private Set<Modifier> modifiers;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private JsName f888name;
    private List<JsParameter> params;
    private final JsFunctionScope scope;

    public enum Modifier {
        STATIC,
        GET,
        SET
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 11:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
                i2 = 2;
                break;
            case 11:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 6:
                objArr[0] = MediaTrack.ROLE_DESCRIPTION;
                break;
            case 2:
            case 5:
            default:
                objArr[0] = "parentScope";
                break;
            case 3:
            case 11:
                objArr[0] = "body";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsFunction";
                break;
        }
        switch (i) {
            case 7:
                objArr[1] = "getBody";
                break;
            case 8:
                objArr[1] = "getParameters";
                break;
            case 9:
                objArr[1] = "getScope";
                break;
            case 10:
                objArr[1] = "getModifiers";
                break;
            case 11:
            default:
                objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsFunction";
                break;
            case 12:
                objArr[1] = "deepCopy";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
                break;
            case 11:
                objArr[2] = "setBody";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
                throw new IllegalStateException(str2);
            case 11:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsFunction(JsScope jsScope, String str) {
        this(jsScope, str, (JsName) null);
        if (jsScope == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsFunction(JsScope jsScope, JsBlock jsBlock, String str) {
        this(jsScope, str, (JsName) null);
        if (jsScope == null) {
            $$$reportNull$$$0(2);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        this.body = jsBlock;
    }

    private JsFunction(JsScope jsScope, String str, JsName jsName) {
        if (jsScope == null) {
            $$$reportNull$$$0(5);
        }
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        this.f888name = jsName;
        this.scope = new JsFunctionScope(jsScope, jsName != null ? jsName.getIdent() : str);
    }

    public JsBlock getBody() {
        JsBlock jsBlock = this.body;
        if (jsBlock == null) {
            $$$reportNull$$$0(7);
        }
        return jsBlock;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public JsName getName() {
        return this.f888name;
    }

    @Override // org.jetbrains.kotlin.js.common.HasSymbol
    public Symbol getSymbol() {
        return this.f888name;
    }

    public List<JsParameter> getParameters() {
        if (this.params == null) {
            this.params = new SmartList();
        }
        List<JsParameter> list = this.params;
        if (list == null) {
            $$$reportNull$$$0(8);
        }
        return list;
    }

    public JsFunctionScope getScope() {
        JsFunctionScope jsFunctionScope = this.scope;
        if (jsFunctionScope == null) {
            $$$reportNull$$$0(9);
        }
        return jsFunctionScope;
    }

    public boolean isStatic() {
        Set<Modifier> set = this.modifiers;
        return set != null && set.contains(Modifier.STATIC);
    }

    public boolean isGetter() {
        Set<Modifier> set = this.modifiers;
        return set != null && set.contains(Modifier.GET);
    }

    public boolean isSetter() {
        Set<Modifier> set = this.modifiers;
        return set != null && set.contains(Modifier.SET);
    }

    public Set<Modifier> getModifiers() {
        if (this.modifiers == null) {
            this.modifiers = EnumSet.noneOf(Modifier.class);
        }
        Set<Modifier> set = this.modifiers;
        if (set == null) {
            $$$reportNull$$$0(10);
        }
        return set;
    }

    public void setBody(JsBlock jsBlock) {
        if (jsBlock == null) {
            $$$reportNull$$$0(11);
        }
        this.body = jsBlock;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public void setName(JsName jsName) {
        this.f888name = jsName;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitFunction(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.acceptWithInsertRemove(getParameters());
        jsVisitor.accept(this.body);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            jsVisitorWithContext.acceptList(getParameters());
            this.body = (JsBlock) jsVisitorWithContext.acceptStatement(this.body);
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsFunction deepCopy() {
        JsFunction jsFunction = new JsFunction(this.scope.getParent(), this.scope.getDescription(), this.f888name);
        jsFunction.getScope().copyOwnNames(this.scope);
        jsFunction.setBody(this.body.deepCopy());
        jsFunction.params = AstUtil.deepCopy(this.params);
        Set<Modifier> set = this.modifiers;
        jsFunction.modifiers = set == null ? null : EnumSet.copyOf((Collection) set);
        JsFunction jsFunction2 = (JsFunction) jsFunction.withMetadataFrom(this);
        if (jsFunction2 == null) {
            $$$reportNull$$$0(12);
        }
        return jsFunction2;
    }
}
