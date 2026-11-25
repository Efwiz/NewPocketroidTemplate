package org.jetbrains.kotlin.js.translate.context;

import com.reandroid.apk.PathMap;
import java.util.Collections;
import java.util.Map;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: loaded from: classes5.dex */
public class AliasingContext {
    private final Map<DeclarationDescriptor, JsExpression> aliasesForDescriptors;
    private final Map<KtExpression, JsExpression> aliasesForExpressions;
    private final AliasingContext parent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = PathMap.NAME_alias;
        } else if (i == 2) {
            objArr[0] = "aliasesForExpressions";
        } else if (i == 3) {
            objArr[0] = "aliases";
        } else if (i != 5) {
            objArr[0] = "descriptor";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/context/AliasingContext";
        if (i == 2) {
            objArr[2] = "withExpressionsAliased";
        } else if (i == 3) {
            objArr[2] = "withDescriptorsAliased";
        } else if (i == 4) {
            objArr[2] = "getAliasForDescriptor";
        } else if (i != 5) {
            objArr[2] = "inner";
        } else {
            objArr[2] = "getAliasForExpression";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static AliasingContext getCleanContext() {
        return new AliasingContext(null, null, null);
    }

    private AliasingContext(AliasingContext aliasingContext, Map<DeclarationDescriptor, JsExpression> map, Map<KtExpression, JsExpression> map2) {
        this.parent = aliasingContext;
        this.aliasesForDescriptors = map;
        this.aliasesForExpressions = map2;
    }

    public AliasingContext inner() {
        return new AliasingContext(this, null, null);
    }

    public AliasingContext inner(DeclarationDescriptor declarationDescriptor, JsExpression jsExpression) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(1);
        }
        return new AliasingContext(this, Collections.singletonMap(declarationDescriptor, jsExpression), null);
    }

    public AliasingContext withExpressionsAliased(Map<KtExpression, JsExpression> map) {
        if (map == null) {
            $$$reportNull$$$0(2);
        }
        return new AliasingContext(this, null, map);
    }

    public AliasingContext withDescriptorsAliased(Map<DeclarationDescriptor, JsExpression> map) {
        if (map == null) {
            $$$reportNull$$$0(3);
        }
        return new AliasingContext(this, map, null);
    }

    public JsExpression getAliasForDescriptor(DeclarationDescriptor declarationDescriptor) {
        AliasingContext aliasingContext;
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        Map<DeclarationDescriptor, JsExpression> map = this.aliasesForDescriptors;
        JsExpression aliasForDescriptor = map != null ? map.get(declarationDescriptor.getOriginal()) : null;
        if (aliasForDescriptor == null && (aliasingContext = this.parent) != null) {
            aliasForDescriptor = aliasingContext.getAliasForDescriptor(declarationDescriptor);
        }
        if (aliasForDescriptor != null) {
            return aliasForDescriptor.deepCopy();
        }
        return null;
    }

    public JsExpression getAliasForExpression(KtExpression ktExpression) {
        AliasingContext aliasingContext;
        if (ktExpression == null) {
            $$$reportNull$$$0(5);
        }
        Map<KtExpression, JsExpression> map = this.aliasesForExpressions;
        JsExpression aliasForExpression = map != null ? map.get(ktExpression) : null;
        if (aliasForExpression == null && (aliasingContext = this.parent) != null) {
            aliasForExpression = aliasingContext.getAliasForExpression(ktExpression);
        }
        if (aliasForExpression != null) {
            return aliasForExpression.deepCopy();
        }
        return null;
    }
}
