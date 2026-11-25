package org.jetbrains.kotlin.js.translate.context;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes5.dex */
public class TemporaryVariable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final JsExpression assignmentExpression;
    private final KotlinType type;
    private final JsName variableName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/context/TemporaryVariable";
        } else {
            objArr[0] = "temporaryName";
        }
        if (i == 2) {
            objArr[1] = "reference";
        } else if (i == 3) {
            objArr[1] = "name";
        } else if (i == 4) {
            objArr[1] = "assignmentExpression";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/context/TemporaryVariable";
        } else {
            objArr[1] = "assignmentStatement";
        }
        if (i == 1) {
            objArr[2] = "<init>";
        } else if (i != 2 && i != 3 && i != 4 && i != 5) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static TemporaryVariable create(JsName jsName, JsExpression jsExpression) {
        JsBinaryOperation jsBinaryOperationAssignment;
        KotlinType type;
        if (jsName == null) {
            $$$reportNull$$$0(0);
        }
        if (jsExpression != null) {
            jsBinaryOperationAssignment = JsAstUtils.assignment(jsName.makeRef(), jsExpression);
            jsBinaryOperationAssignment.source(jsExpression.getSource());
            MetadataProperties.setSynthetic(jsBinaryOperationAssignment, true);
            type = MetadataProperties.getType(jsExpression);
        } else {
            jsBinaryOperationAssignment = null;
            type = null;
        }
        return new TemporaryVariable(jsName, jsBinaryOperationAssignment, type);
    }

    protected TemporaryVariable(JsName jsName, JsExpression jsExpression, KotlinType kotlinType) {
        if (jsName == null) {
            $$$reportNull$$$0(1);
        }
        this.variableName = jsName;
        this.assignmentExpression = jsExpression;
        this.type = kotlinType;
    }

    public JsNameRef reference() {
        JsNameRef jsNameRefMakeRef = this.variableName.makeRef();
        MetadataProperties.setSynthetic(jsNameRefMakeRef, true);
        MetadataProperties.setType(jsNameRefMakeRef, this.type);
        if (jsNameRefMakeRef == null) {
            $$$reportNull$$$0(2);
        }
        return jsNameRefMakeRef;
    }

    public JsName name() {
        JsName jsName = this.variableName;
        if (jsName == null) {
            $$$reportNull$$$0(3);
        }
        return jsName;
    }

    public JsExpression assignmentExpression() {
        JsExpression jsExpression = this.assignmentExpression;
        if (jsExpression == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpression;
    }

    public JsStatement assignmentStatement() {
        JsStatement jsStatementAsSyntheticStatement = JsAstUtils.asSyntheticStatement(assignmentExpression());
        if (jsStatementAsSyntheticStatement == null) {
            $$$reportNull$$$0(5);
        }
        return jsStatementAsSyntheticStatement;
    }
}
