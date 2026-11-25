package org.jetbrains.kotlin.ir.backend.js.lower;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: JsCodeOutliningLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\u0010\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0012H\u0016R\u001c\u0010\u0003\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsScopesCollector;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "()V", "functionalScopes", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/JsScopesCollector$Scope;", "functionsStack", "", "varWithNameExistsInScopeOf", "", "function", "variableName", "", "visitFunction", "", "x", "visitVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "Scope", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsScopesCollector extends RecursiveJsVisitor {
    private final Map<JsFunction, Scope> functionalScopes;
    private final List<Scope> functionsStack;

    public JsScopesCollector() {
        List<Scope> listMutableListOf = CollectionsKt.mutableListOf(new Scope(null));
        this.functionsStack = listMutableListOf;
        this.functionalScopes = MapsKt.mutableMapOf(TuplesKt.to(null, CollectionsKt.first((List) listMutableListOf)));
    }

    /* JADX INFO: compiled from: JsCodeOutliningLowering.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsScopesCollector$Scope;", "", "parent", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/JsScopesCollector$Scope;)V", "getParent", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/JsScopesCollector$Scope;", "variables", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", ProductAction.ACTION_ADD, "", "variableName", "variableWithNameExists", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Scope {
        private final Scope parent;
        private final HashSet<String> variables = new HashSet<>();

        public Scope(Scope scope) {
            this.parent = scope;
        }

        public final Scope getParent() {
            return this.parent;
        }

        public final void add(String variableName) {
            Intrinsics.checkNotNullParameter(variableName, "variableName");
            this.variables.add(variableName);
        }

        public final boolean variableWithNameExists(String variableName) {
            Intrinsics.checkNotNullParameter(variableName, "variableName");
            if (this.variables.contains(variableName)) {
                return true;
            }
            Scope scope = this.parent;
            return scope != null && scope.variableWithNameExists(variableName);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitVars(JsVars x) {
        Intrinsics.checkNotNullParameter(x, "x");
        super.visitVars(x);
        Scope scope = (Scope) CollectionsKt.last((List) this.functionsStack);
        List<JsVars.JsVar> vars = x.getVars();
        Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
        Iterator<T> it = vars.iterator();
        while (it.hasNext()) {
            String ident = ((JsVars.JsVar) it.next()).getName().getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            scope.add(ident);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(JsFunction x) {
        Intrinsics.checkNotNullParameter(x, "x");
        Scope scope = new Scope((Scope) CollectionsKt.last((List) this.functionsStack));
        JsName name2 = x.getName();
        String ident = name2 != null ? name2.getIdent() : null;
        if (ident != null) {
            scope.add(ident);
        }
        List<JsParameter> parameters = x.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        Iterator<T> it = parameters.iterator();
        while (it.hasNext()) {
            String ident2 = ((JsParameter) it.next()).getName().getIdent();
            Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
            scope.add(ident2);
        }
        UtilsKt.push(this.functionsStack, scope);
        this.functionalScopes.put(x, scope);
        super.visitFunction(x);
        UtilsKt.pop(this.functionsStack);
    }

    public final boolean varWithNameExistsInScopeOf(JsFunction function, String variableName) {
        Intrinsics.checkNotNullParameter(variableName, "variableName");
        Scope scope = this.functionalScopes.get(function);
        Intrinsics.checkNotNull(scope);
        return scope.variableWithNameExists(variableName);
    }
}
