package org.jetbrains.kotlin.js.inline.clean;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;

/* JADX INFO: compiled from: removeUnusedLocalFunctionDeclarations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001c\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\u001c\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000e2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/UnusedInstanceCollector;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "()V", "removableDeclarations", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getRemovableDeclarations", "()Ljava/util/List;", "tracker", "Lorg/jetbrains/kotlin/js/inline/clean/ReferenceTracker;", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "isLocalFunctionDeclaration", "", "jsVar", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "visit", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UnusedInstanceCollector extends JsVisitorWithContextImpl {
    private final ReferenceTracker<JsName, JsStatement> tracker = new ReferenceTracker<>();

    public final List<JsStatement> getRemovableDeclarations() {
        return this.tracker.getRemovable();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsVars.JsVar x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (!isLocalFunctionDeclaration(x)) {
            return super.visit(x, ctx);
        }
        JsName name2 = x.getName();
        Intrinsics.checkNotNull(name2);
        JsContext<JsStatement> lastStatementLevelContext = getLastStatementLevelContext();
        Intrinsics.checkNotNullExpressionValue(lastStatementLevelContext, "getLastStatementLevelContext(...)");
        JsStatement jsStatement = (JsStatement) lastStatementLevelContext.getCurrentNode();
        ReferenceTracker<JsName, JsStatement> referenceTracker = this.tracker;
        Intrinsics.checkNotNull(jsStatement);
        referenceTracker.addCandidateForRemoval(name2, jsStatement);
        Iterator<T> it = CollectUtilsKt.collectUsedNames(x).iterator();
        while (it.hasNext()) {
            this.tracker.addRemovableReference(name2, (JsName) it.next());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsNameRef x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        while (x != null) {
            JsName name2 = x.getName();
            if (name2 != null) {
                this.tracker.markReachable(name2);
            }
            JsExpression qualifier = x.getQualifier();
            x = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
        }
        return false;
    }

    private final boolean isLocalFunctionDeclaration(JsVars.JsVar jsVar) {
        JsName name2 = jsVar.getName();
        JsExpression initExpression = jsVar.getInitExpression();
        if (initExpression instanceof JsNameRef) {
            JsName name3 = ((JsNameRef) initExpression).getName();
            if (name3 != null ? this.tracker.isReferenceToRemovableCandidate(name3) : false) {
                return true;
            }
        }
        JsNode staticRef = name2 != null ? MetadataProperties.getStaticRef(name2) : null;
        if (staticRef != null && Intrinsics.areEqual(staticRef, initExpression)) {
            Intrinsics.checkNotNull(initExpression);
            if (RemoveUnusedLocalFunctionDeclarationsKt.isFunctionReference(initExpression)) {
                return true;
            }
        }
        return false;
    }
}
