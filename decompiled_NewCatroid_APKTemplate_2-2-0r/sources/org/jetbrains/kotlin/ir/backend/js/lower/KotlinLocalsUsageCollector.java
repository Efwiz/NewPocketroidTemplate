package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: JsCodeOutliningLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005¢\u0006\u0002\u0010\bJ\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000bH\u0016J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\f\u0010\u001b\u001a\u00020\u001c*\u00020\u000eH\u0002R\u001c\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00070\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00070\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/KotlinLocalsUsageCollector;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "scopeInfo", "Lorg/jetbrains/kotlin/ir/backend/js/lower/JsScopesCollector;", "findValueDeclarationWithName", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/JsScopesCollector;Lkotlin/jvm/functions/Function1;)V", "functionStack", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "kotlinLocalsUsedInJs", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "processedNames", "", "usedLocals", "", "getUsedLocals", "()Ljava/util/Map;", "visitFunction", "", "x", "visitNameRef", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "isDeclaredInsideJsCode", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KotlinLocalsUsageCollector extends RecursiveJsVisitor {
    private final Function1<String, IrValueDeclaration> findValueDeclarationWithName;
    private final List<JsFunction> functionStack;
    private final Map<JsName, IrValueDeclaration> kotlinLocalsUsedInJs;
    private final Set<String> processedNames;
    private final JsScopesCollector scopeInfo;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinLocalsUsageCollector(JsScopesCollector scopeInfo, Function1<? super String, ? extends IrValueDeclaration> findValueDeclarationWithName) {
        Intrinsics.checkNotNullParameter(scopeInfo, "scopeInfo");
        Intrinsics.checkNotNullParameter(findValueDeclarationWithName, "findValueDeclarationWithName");
        this.scopeInfo = scopeInfo;
        this.findValueDeclarationWithName = findValueDeclarationWithName;
        this.functionStack = CollectionsKt.mutableListOf(null);
        this.processedNames = new LinkedHashSet();
        this.kotlinLocalsUsedInJs = new LinkedHashMap();
    }

    public final Map<JsName, IrValueDeclaration> getUsedLocals() {
        return this.kotlinLocalsUsedInJs;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(JsFunction x) {
        Intrinsics.checkNotNullParameter(x, "x");
        UtilsKt.push(this.functionStack, x);
        super.visitFunction(x);
        UtilsKt.pop(this.functionStack);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitNameRef(JsNameRef nameRef) {
        Intrinsics.checkNotNullParameter(nameRef, "nameRef");
        super.visitNameRef(nameRef);
        JsName name2 = nameRef.getName();
        if (nameRef.getQualifier() != null) {
            name2 = null;
        }
        if (name2 == null) {
            return;
        }
        Set<String> set = this.processedNames;
        String ident = name2.getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        if (!set.add(ident) || isDeclaredInsideJsCode(name2)) {
            return;
        }
        Function1<String, IrValueDeclaration> function1 = this.findValueDeclarationWithName;
        String ident2 = name2.getIdent();
        Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
        IrValueDeclaration irValueDeclarationInvoke = function1.invoke(ident2);
        if (irValueDeclarationInvoke != null) {
            this.kotlinLocalsUsedInJs.put(name2, irValueDeclarationInvoke);
        }
    }

    private final boolean isDeclaredInsideJsCode(JsName jsName) {
        JsScopesCollector jsScopesCollector = this.scopeInfo;
        JsFunction jsFunction = (JsFunction) UtilsKt.peek(this.functionStack);
        String ident = jsName.getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        return jsScopesCollector.varWithNameExistsInScopeOf(jsFunction, ident);
    }
}
