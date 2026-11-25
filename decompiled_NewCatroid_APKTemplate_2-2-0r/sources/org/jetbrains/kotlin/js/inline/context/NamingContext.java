package org.jetbrains.kotlin.js.inline.context;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.RewriteUtilsKt;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: NamingContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ$\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\f2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001J\u0016\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/context/NamingContext;", "", "previousStatements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Ljava/util/List;)V", "addedDeclarations", "", "declarations", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "renamings", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "applyRenameTo", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "target", "newVar", "", "name", "value", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", FirebaseAnalytics.Param.SOURCE, "replaceName", "replacement", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NamingContext {
    private boolean addedDeclarations;
    private final List<JsVars> declarations;
    private final List<JsStatement> previousStatements;
    private final Map<JsName, JsNameRef> renamings;

    public NamingContext(List<JsStatement> previousStatements) {
        Intrinsics.checkNotNullParameter(previousStatements, "previousStatements");
        this.previousStatements = previousStatements;
        this.renamings = new LinkedHashMap();
        this.declarations = new ArrayList();
    }

    public final JsNode applyRenameTo(JsNode target) {
        Intrinsics.checkNotNullParameter(target, "target");
        if (!this.addedDeclarations) {
            this.previousStatements.addAll(this.declarations);
            this.addedDeclarations = true;
        }
        return RewriteUtilsKt.replaceNames(target, this.renamings);
    }

    public final void replaceName(JsName name2, JsNameRef replacement) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        this.renamings.containsKey(name2);
        this.renamings.put(name2, replacement);
    }

    public static /* synthetic */ void newVar$default(NamingContext namingContext, JsName jsName, JsExpression jsExpression, Object obj, int i, Object obj2) {
        if ((i & 2) != 0) {
            jsExpression = null;
        }
        namingContext.newVar(jsName, jsExpression, obj);
    }

    public final void newVar(JsName name2, JsExpression value, Object source) {
        Intrinsics.checkNotNullParameter(name2, "name");
        JsVars jsVarsNewVar = JsAstUtils.newVar(name2, value);
        Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "newVar(...)");
        MetadataProperties.setSynthetic(jsVarsNewVar, true);
        jsVarsNewVar.setSource(source);
        this.declarations.add(jsVarsNewVar);
    }
}
