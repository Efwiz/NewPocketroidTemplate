package org.jetbrains.kotlin.js.translate.context;

import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;

/* JADX INFO: loaded from: classes5.dex */
public final class DynamicContext {
    private final JsBlock currentBlock;
    private final JsScope currentScope;
    private JsVars vars;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 9 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 9 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "globalBlock";
                break;
            case 2:
            case 4:
                objArr[0] = "scope";
                break;
            case 3:
            case 5:
            case 6:
                objArr[0] = "block";
                break;
            case 7:
            case 9:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/js/translate/context/DynamicContext";
                break;
            case 8:
                objArr[0] = "dynamicContext";
                break;
            default:
                objArr[0] = "rootScope";
                break;
        }
        if (i == 7) {
            objArr[1] = "declareTemporary";
        } else if (i == 9) {
            objArr[1] = "getScope";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/context/DynamicContext";
        } else {
            objArr[1] = "jsBlock";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "newContext";
                break;
            case 4:
            case 5:
                objArr[2] = "<init>";
                break;
            case 6:
                objArr[2] = "innerBlock";
                break;
            case 7:
            case 9:
            case 10:
                break;
            case 8:
                objArr[2] = "moveVarsFrom";
                break;
            default:
                objArr[2] = "rootContext";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 9 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static DynamicContext rootContext(JsScope jsScope, JsBlock jsBlock) {
        if (jsScope == null) {
            $$$reportNull$$$0(0);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(1);
        }
        return new DynamicContext(jsScope, jsBlock);
    }

    public static DynamicContext newContext(JsScope jsScope, JsBlock jsBlock) {
        if (jsScope == null) {
            $$$reportNull$$$0(2);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(3);
        }
        return new DynamicContext(jsScope, jsBlock);
    }

    private DynamicContext(JsScope jsScope, JsBlock jsBlock) {
        if (jsScope == null) {
            $$$reportNull$$$0(4);
        }
        if (jsBlock == null) {
            $$$reportNull$$$0(5);
        }
        this.currentScope = jsScope;
        this.currentBlock = jsBlock;
    }

    public DynamicContext innerBlock(JsBlock jsBlock) {
        if (jsBlock == null) {
            $$$reportNull$$$0(6);
        }
        return new DynamicContext(this.currentScope, jsBlock);
    }

    public TemporaryVariable declareTemporary(JsExpression jsExpression, Object obj) {
        if (this.vars == null) {
            JsVars jsVars = new JsVars();
            this.vars = jsVars;
            MetadataProperties.setSynthetic(jsVars, true);
            this.currentBlock.getStatements().add(this.vars);
            this.vars.setSource(obj);
        }
        JsName jsNameDeclareTemporary = JsScope.declareTemporary();
        JsVars.JsVar jsVar = new JsVars.JsVar(jsNameDeclareTemporary, null);
        jsVar.setSource(obj);
        MetadataProperties.setSynthetic(jsVar, true);
        this.vars.add(jsVar);
        if (jsExpression != null) {
            jsVar.source(jsExpression.getSource());
        }
        TemporaryVariable temporaryVariableCreate = TemporaryVariable.create(jsNameDeclareTemporary, jsExpression);
        if (temporaryVariableCreate == null) {
            $$$reportNull$$$0(7);
        }
        return temporaryVariableCreate;
    }

    void moveVarsFrom(DynamicContext dynamicContext) {
        if (dynamicContext == null) {
            $$$reportNull$$$0(8);
        }
        JsVars jsVars = dynamicContext.vars;
        if (jsVars != null) {
            JsVars jsVars2 = this.vars;
            if (jsVars2 == null) {
                this.vars = jsVars;
                this.currentBlock.getStatements().add(this.vars);
            } else {
                jsVars2.addAll(jsVars);
            }
            dynamicContext.currentBlock.getStatements().remove(dynamicContext.vars);
            dynamicContext.vars = null;
        }
    }

    public JsScope getScope() {
        JsScope jsScope = this.currentScope;
        if (jsScope == null) {
            $$$reportNull$$$0(9);
        }
        return jsScope;
    }

    public JsBlock jsBlock() {
        JsBlock jsBlock = this.currentBlock;
        if (jsBlock == null) {
            $$$reportNull$$$0(10);
        }
        return jsBlock;
    }
}
