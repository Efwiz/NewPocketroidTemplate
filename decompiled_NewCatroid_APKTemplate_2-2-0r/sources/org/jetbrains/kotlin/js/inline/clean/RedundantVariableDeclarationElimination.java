package org.jetbrains.kotlin.js.inline.clean;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;

/* JADX INFO: compiled from: RedundantVariableDeclarationElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0006\u0010\f\u001a\u00020\u0006J\b\u0010\r\u001a\u00020\u000bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/RedundantVariableDeclarationElimination;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "hasChanges", "", "usages", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "analyze", "", "apply", "perform", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantVariableDeclarationElimination {
    private boolean hasChanges;
    private final JsStatement root;
    private final Set<JsName> usages;

    public RedundantVariableDeclarationElimination(JsStatement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
        this.usages = new LinkedHashSet();
    }

    public final boolean apply() {
        analyze();
        perform();
        return this.hasChanges;
    }

    private final void analyze() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.RedundantVariableDeclarationElimination.analyze.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsBreak x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsContinue x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsNameRef x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsName name2 = x.getName();
                if (name2 != null && x.getQualifier() == null) {
                    RedundantVariableDeclarationElimination.this.usages.add(name2);
                }
                return super.visit(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                CollectionsKt.addAll(RedundantVariableDeclarationElimination.this.usages, CollectUtilsKt.collectFreeVariables(x));
                return false;
            }
        }.accept(this.root);
    }

    private final void perform() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.RedundantVariableDeclarationElimination.perform.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsVars x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (MetadataProperties.getSynthetic(x)) {
                    List<JsVars.JsVar> vars = x.getVars();
                    Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
                    final RedundantVariableDeclarationElimination redundantVariableDeclarationElimination = RedundantVariableDeclarationElimination.this;
                    if (CollectionsKt.removeAll((List) vars, (Function1) new Function1<JsVars.JsVar, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RedundantVariableDeclarationElimination$perform$1$endVisit$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(JsVars.JsVar jsVar) {
                            return Boolean.valueOf(jsVar.getInitExpression() == null && !redundantVariableDeclarationElimination.usages.contains(jsVar.getName()));
                        }
                    })) {
                        RedundantVariableDeclarationElimination.this.hasChanges = true;
                    }
                    if (x.getVars().isEmpty()) {
                        ctx.removeMe();
                    }
                }
                super.endVisit(x, ctx);
            }
        }.accept(this.root);
    }
}
