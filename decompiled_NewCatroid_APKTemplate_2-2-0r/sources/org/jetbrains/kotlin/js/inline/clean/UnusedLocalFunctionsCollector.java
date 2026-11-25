package org.jetbrains.kotlin.js.inline.clean;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.CollectionUtilsKt;

/* JADX INFO: compiled from: removeUnusedFunctionDefinitions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00142\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0006\u0010\u0019\u001a\u00020\u0010J\u0018\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J\u0010\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J\u001c\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00052\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\u001c\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u001f2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\u0012\u0010 \u001a\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0002R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/UnusedLocalFunctionsCollector;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "functions", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Ljava/util/Map;)V", "processed", "", "removableFunctions", "", "getRemovableFunctions", "()Ljava/util/List;", "tracker", "Lorg/jetbrains/kotlin/js/inline/clean/ReferenceTracker;", "endVisit", "", "x", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "isFunctionReference", "", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/HasName;", "process", "processLocalFunction", "name", "function", "processNonLocalFunction", "visit", "Lorg/jetbrains/kotlin/js/backend/ast/JsPropertyInitializer;", "wasProcessed", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UnusedLocalFunctionsCollector extends JsVisitorWithContextImpl {
    private final Map<JsName, JsFunction> functions;
    private final Set<JsFunction> processed;
    private final ReferenceTracker<JsName, JsFunction> tracker;

    public UnusedLocalFunctionsCollector(Map<JsName, JsFunction> functions) {
        Intrinsics.checkNotNullParameter(functions, "functions");
        this.functions = functions;
        this.tracker = new ReferenceTracker<>();
        this.processed = CollectionUtilsKt.IdentitySet();
    }

    public final List<JsFunction> getRemovableFunctions() {
        return this.tracker.getRemovable();
    }

    public final void process() {
        Map<JsName, JsFunction> map = this.functions;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<JsName, JsFunction> entry : map.entrySet()) {
            if (MetadataProperties.isLocal(entry.getValue())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            this.tracker.addCandidateForRemoval((JsName) entry2.getKey(), (JsNode) entry2.getValue());
        }
        for (Map.Entry<JsName, JsFunction> entry3 : this.functions.entrySet()) {
            JsName key = entry3.getKey();
            JsFunction value = entry3.getValue();
            if (MetadataProperties.isLocal(value)) {
                processLocalFunction(key, value);
            } else {
                processNonLocalFunction(value);
            }
            this.processed.add(value);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsPropertyInitializer x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsExpression valueExpr = x.getValueExpr();
        Intrinsics.checkNotNullExpressionValue(valueExpr, "getValueExpr(...)");
        if (valueExpr instanceof JsFunction) {
            return !wasProcessed((JsFunction) valueExpr);
        }
        return super.visit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsFunction x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return !wasProcessed(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsFunction x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.processed.add(x);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsNameRef x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsName name2 = x.getName();
        if (!isFunctionReference(x) || name2 == null) {
            return;
        }
        this.tracker.markReachable(name2);
    }

    private final void processLocalFunction(JsName name2, JsFunction function) {
        Iterator<JsName> it = CollectUtilsKt.collectReferencedNames(function).iterator();
        while (it.hasNext()) {
            this.tracker.addRemovableReference(name2, it.next());
        }
    }

    private final void processNonLocalFunction(JsFunction function) {
        Iterator<JsName> it = CollectUtilsKt.collectReferencedNames(function).iterator();
        while (it.hasNext()) {
            this.tracker.markReachable(it.next());
        }
    }

    private final boolean isFunctionReference(HasName nameRef) {
        JsName name2;
        return ((nameRef == null || (name2 = nameRef.getName()) == null) ? null : MetadataProperties.getStaticRef(name2)) instanceof JsFunction;
    }

    private final boolean wasProcessed(JsFunction function) {
        return function != null && this.processed.contains(function);
    }
}
