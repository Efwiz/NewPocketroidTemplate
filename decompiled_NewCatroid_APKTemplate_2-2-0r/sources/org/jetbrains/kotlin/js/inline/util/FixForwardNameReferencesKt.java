package org.jetbrains.kotlin.js.inline.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: fixForwardNameReferences.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"fixForwardNameReferences", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FixForwardNameReferencesKt {
    public static final void fixForwardNameReferences(JsNode jsNode) {
        Intrinsics.checkNotNullParameter(jsNode, "<this>");
        jsNode.accept(new RecursiveJsVisitor(jsNode) { // from class: org.jetbrains.kotlin.js.inline.util.FixForwardNameReferencesKt.fixForwardNameReferences.1
            private final Map<String, JsName> currentScope;

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
            }

            {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                this.currentScope = linkedHashMap;
                Set<JsName> setCollectDefinedNames = CollectUtilsKt.collectDefinedNames(jsNode, true);
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setCollectDefinedNames, 10)), 16));
                for (Object obj : setCollectDefinedNames) {
                    linkedHashMap2.put(((JsName) obj).getIdent(), obj);
                }
                linkedHashMap.putAll(linkedHashMap2);
            }

            public final Map<String, JsName> getCurrentScope() {
                return this.currentScope;
            }

            private final void restore(String ident, JsName oldName) {
                if (oldName == null) {
                    this.currentScope.remove(ident);
                } else {
                    this.currentScope.put(ident, oldName);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                List list = CollectionsKt.toList(CollectUtilsKt.collectLocalVariables(x, true));
                JsName[] jsNameArr = new JsName[list.size()];
                int i = 0;
                for (Object obj : list) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    JsName jsName = (JsName) obj;
                    jsNameArr[i] = this.currentScope.get(jsName.getIdent());
                    Map<String, JsName> map = this.currentScope;
                    String ident = jsName.getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                    map.put(ident, jsName);
                    i = i2;
                }
                super.visitFunction(x);
                int size = list.size() - 1;
                if (size < 0) {
                    return;
                }
                while (true) {
                    int i3 = size - 1;
                    String ident2 = ((JsName) list.get(size)).getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
                    restore(ident2, jsNameArr[size]);
                    if (i3 < 0) {
                        return;
                    } else {
                        size = i3;
                    }
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitCatch(JsCatch x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsName name2 = x.getParameter().getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                JsName jsName = this.currentScope.get(name2.getIdent());
                Map<String, JsName> map = this.currentScope;
                String ident = name2.getIdent();
                Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                map.put(ident, name2);
                super.visitCatch(x);
                String ident2 = name2.getIdent();
                Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
                restore(ident2, jsName);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                super.visitNameRef(nameRef);
                if (nameRef.getQualifier() == null) {
                    String ident = nameRef.getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                    JsName jsName = this.currentScope.get(ident);
                    if (jsName != null) {
                        nameRef.setName(jsName);
                    }
                }
            }
        });
    }
}
