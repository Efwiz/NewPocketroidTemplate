package org.jetbrains.kotlin.js.dce;

import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNode;

/* JADX INFO: compiled from: AnalysisResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0006R\u001c\u0010\f\u001a\f\u0012\b\u0012\u00060\rR\u00020\u000e0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0006R\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0006R\"\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u00060\rR\u00020\u000e0\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/AnalysisResult;", "", "astNodesToEliminate", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "getAstNodesToEliminate", "()Ljava/util/Set;", "astNodesToSkip", "getAstNodesToSkip", "functionsToEnter", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "getFunctionsToEnter", "functionsToSkip", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "Lorg/jetbrains/kotlin/js/dce/Context;", "getFunctionsToSkip", "invocationsToSkip", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "getInvocationsToSkip", "nodeMap", "", "getNodeMap", "()Ljava/util/Map;", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AnalysisResult {
    Set<JsNode> getAstNodesToEliminate();

    Set<JsNode> getAstNodesToSkip();

    Set<JsFunction> getFunctionsToEnter();

    Set<Context.Node> getFunctionsToSkip();

    Set<JsInvocation> getInvocationsToSkip();

    Map<JsNode, Context.Node> getNodeMap();
}
