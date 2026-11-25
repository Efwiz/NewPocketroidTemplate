package org.jetbrains.kotlin.js.inline.clean;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.RewriteUtilsKt;

/* JADX INFO: compiled from: removeDuplicateImports.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0002\u001a>\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\n0\u0006H\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"removeDuplicateImports", "", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "existingImports", "", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "replacements", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "node", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RemoveDuplicateImportsKt {
    public static final void removeDuplicateImports(JsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        node.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveDuplicateImportsKt.removeDuplicateImports.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBlock(JsBlock x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitBlock(x);
                List<JsStatement> statements = x.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                RemoveDuplicateImportsKt.removeDuplicateImports(statements);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeDuplicateImports(List<JsStatement> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        removeDuplicateImports(list, linkedHashMap, linkedHashMap2);
        Iterator<JsStatement> it = list.iterator();
        while (it.hasNext()) {
            RewriteUtilsKt.replaceNames(it.next(), linkedHashMap2);
        }
    }

    private static final void removeDuplicateImports(List<JsStatement> list, Map<String, JsName> map, Map<JsName, JsExpression> map2) {
        int i = 0;
        while (i < list.size()) {
            JsStatement jsStatement = list.get(i);
            if (jsStatement instanceof JsVars) {
                JsVars jsVars = (JsVars) jsStatement;
                String importTag = CollectUtilsKt.getImportTag(jsVars);
                if (importTag != null) {
                    JsName name2 = jsVars.getVars().get(0).getName();
                    JsName jsName = map.get(importTag);
                    if (jsName != null) {
                        Intrinsics.checkNotNull(name2);
                        JsNameRef jsNameRefMakeRef = jsName.makeRef();
                        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                        map2.put(name2, jsNameRefMakeRef);
                        list.remove(i);
                    } else {
                        Intrinsics.checkNotNull(name2);
                        map.put(importTag, name2);
                    }
                }
            } else if (jsStatement instanceof JsBlock) {
                List<JsStatement> statements = ((JsBlock) jsStatement).getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                removeDuplicateImports(statements, map, map2);
            }
            i++;
        }
    }
}
