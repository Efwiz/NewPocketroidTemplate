package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDeclarationScope;
import org.jetbrains.kotlin.js.backend.ast.JsDynamicScope;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: resolveTemporaryNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\u0002\u001a\u0018\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005*\u00020\u0002H\u0002\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\u0002¨\u0006\t"}, d2 = {"computeScopes", "Lorg/jetbrains/kotlin/js/inline/clean/Scope;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "liftUsedNames", "resolveNames", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "resolveTemporaryNames", "", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolveTemporaryNamesKt {
    public static final void resolveTemporaryNames(JsNode jsNode) {
        Intrinsics.checkNotNullParameter(jsNode, "<this>");
        final Map<JsName, JsName> mapResolveNames = resolveNames(jsNode);
        jsNode.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.ResolveTemporaryNamesKt.resolveTemporaryNames.1
            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                HasName hasName;
                JsName name2;
                JsName jsName;
                Intrinsics.checkNotNullParameter(node, "node");
                super.visitElement(node);
                if (!(node instanceof HasName) || (name2 = (hasName = (HasName) node).getName()) == null || (jsName = mapResolveNames.get(name2)) == null) {
                    return;
                }
                hasName.setName(jsName);
            }
        });
    }

    private static final Map<JsName, JsName> resolveNames(JsNode jsNode) {
        Scope scopeLiftUsedNames = liftUsedNames(computeScopes(jsNode));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        resolveNames$traverse(linkedHashMap, scopeLiftUsedNames);
        return linkedHashMap;
    }

    private static final void resolveNames$traverse(final Map<JsName, JsName> map, Scope scope) {
        Set mutableSet = SequencesKt.toMutableSet(SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence(scope.getDeclaredNames()), new Function1<JsName, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.ResolveTemporaryNamesKt$resolveNames$traverse$occupiedNames$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JsName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!it.isTemporary());
            }
        }), new Function1<JsName, String>() { // from class: org.jetbrains.kotlin.js.inline.clean.ResolveTemporaryNamesKt$resolveNames$traverse$occupiedNames$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(JsName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getIdent();
            }
        }));
        Set set = mutableSet;
        CollectionsKt.addAll(set, SequencesKt.mapNotNull(CollectionsKt.asSequence(scope.getUsedNames()), new Function1<JsName, String>() { // from class: org.jetbrains.kotlin.js.inline.clean.ResolveTemporaryNamesKt$resolveNames$traverse$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(JsName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.isTemporary() && (it = map.get(it)) == null) {
                    return null;
                }
                return it.getIdent();
            }
        }));
        for (JsName jsName : SequencesKt.filter(CollectionsKt.asSequence(scope.getDeclaredNames()), new Function1<JsName, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.ResolveTemporaryNamesKt$resolveNames$traverse$2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JsName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.isTemporary());
            }
        })) {
            String ident = jsName.getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            int i = 0;
            while (true) {
                if (JsDeclarationScope.INSTANCE.getRESERVED_WORDS().contains(ident) || !mutableSet.add(ident)) {
                    ident = jsName.getIdent() + '_' + i;
                    i++;
                }
            }
            JsName jsNameDeclareName = JsDynamicScope.INSTANCE.declareName(ident);
            jsNameDeclareName.copyMetadataFrom(jsName);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "apply(...)");
            map.put(jsName, jsNameDeclareName);
            set.add(ident);
        }
        Iterator<T> it = scope.getChildren().iterator();
        while (it.hasNext()) {
            resolveNames$traverse(map, (Scope) it.next());
        }
    }

    private static final void liftUsedNames$traverse$2(Scope scope) {
        for (Scope scope2 : scope.getChildren()) {
            CollectionsKt.addAll(scope.getUsedNames(), scope2.getDeclaredNames());
            liftUsedNames$traverse$2(scope2);
            CollectionsKt.addAll(scope.getUsedNames(), scope2.getUsedNames());
        }
    }

    private static final Scope liftUsedNames(Scope scope) {
        liftUsedNames$traverse$2(scope);
        return scope;
    }

    private static final Scope computeScopes(JsNode jsNode) {
        Scope scope = new Scope();
        jsNode.accept(new RecursiveJsVisitor(scope) { // from class: org.jetbrains.kotlin.js.inline.clean.ResolveTemporaryNamesKt.computeScopes.1
            private Scope currentScope;

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            {
                this.currentScope = scope;
            }

            public final Scope getCurrentScope() {
                return this.currentScope;
            }

            public final void setCurrentScope(Scope scope2) {
                Intrinsics.checkNotNullParameter(scope2, "<set-?>");
                this.currentScope = scope2;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsName name2 = x.getName();
                if (name2 != null) {
                    this.currentScope.getDeclaredNames().add(name2);
                }
                Scope scope2 = this.currentScope;
                Scope scope3 = new Scope();
                scope3.setParent(this.currentScope);
                this.currentScope.getChildren().add(scope3);
                this.currentScope = scope3;
                Set<JsName> declaredNames = scope3.getDeclaredNames();
                List<JsParameter> parameters = x.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                List<JsParameter> list = parameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    JsName name3 = ((JsParameter) it.next()).getName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    arrayList.add(name3);
                }
                CollectionsKt.addAll(declaredNames, arrayList);
                super.visitFunction(x);
                this.currentScope = scope2;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitCatch(JsCatch x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.currentScope.getDeclaredNames().add(x.getParameter().getName());
                super.visitCatch(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.currentScope.getDeclaredNames().add(x.getName());
                super.visit(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                if (nameRef.getQualifier() == null) {
                    JsName name2 = nameRef.getName();
                    Set<JsName> usedNames = this.currentScope.getUsedNames();
                    if (name2 == null) {
                        name2 = JsDynamicScope.INSTANCE.declareName(nameRef.getIdent());
                        Intrinsics.checkNotNullExpressionValue(name2, "declareName(...)");
                    }
                    usedNames.add(name2);
                }
                super.visitNameRef(nameRef);
            }
        });
        return scope;
    }
}
