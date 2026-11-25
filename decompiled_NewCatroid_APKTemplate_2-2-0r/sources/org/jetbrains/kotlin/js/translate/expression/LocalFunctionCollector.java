package org.jetbrains.kotlin.js.translate.expression;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: LocalFunctionCollector.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/LocalFunctionCollector;", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "functions", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getFunctions", "()Ljava/util/Set;", "visitClassOrObject", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "visitExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalFunctionCollector extends KtVisitorVoid {
    private final BindingContext bindingContext;
    private final Set<FunctionDescriptor> functions;

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitClassOrObject(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
    }

    public LocalFunctionCollector(BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.bindingContext = bindingContext;
        this.functions = new LinkedHashSet();
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final Set<FunctionDescriptor> getFunctions() {
        return this.functions;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitExpression(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression instanceof KtDeclarationWithBody) {
            this.functions.add(BindingUtils.getFunctionDescriptor(this.bindingContext, (KtDeclarationWithBody) expression));
        } else {
            expression.acceptChildren(this, null);
        }
    }
}
