package org.jetbrains.kotlin.js.inline.clean;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsDynamicScope;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: renameLabels.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"renameLabels", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RenameLabelsKt {
    public static final void renameLabels(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        root.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.RenameLabelsKt.renameLabels.1
            private final Map<JsName, JsName> replacements = new LinkedHashMap();
            private Set<String> labels = new LinkedHashSet();

            public final Map<JsName, JsName> getReplacements() {
                return this.replacements;
            }

            public final Set<String> getLabels() {
                return this.labels;
            }

            public final void setLabels(Set<String> set) {
                Intrinsics.checkNotNullParameter(set, "<set-?>");
                this.labels = set;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                HasName hasName;
                JsName name2;
                JsName jsName;
                Intrinsics.checkNotNullParameter(node, "node");
                super.visitElement(node);
                if (!(node instanceof HasName) || (name2 = (hasName = (HasName) node).getName()) == null || (jsName = this.replacements.get(name2)) == null) {
                    return;
                }
                hasName.setName(jsName);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) {
                String ident;
                Intrinsics.checkNotNullParameter(x, "x");
                if (x.getName().isTemporary()) {
                    ident = x.getName().getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                    int i = 0;
                    while (!this.labels.add(ident)) {
                        ident = x.getName().getIdent() + '_' + i;
                        i++;
                    }
                    Map<JsName, JsName> map = this.replacements;
                    JsName name2 = x.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    JsName jsNameDeclareName = JsDynamicScope.INSTANCE.declareName(ident);
                    Intrinsics.checkNotNullExpressionValue(jsNameDeclareName, "declareName(...)");
                    map.put(name2, jsNameDeclareName);
                } else {
                    ident = null;
                }
                super.visitLabel(x);
                if (ident != null) {
                    this.labels.remove(ident);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                Set<String> set = this.labels;
                this.labels = new LinkedHashSet();
                super.visitFunction(x);
                this.labels = set;
            }
        });
    }
}
