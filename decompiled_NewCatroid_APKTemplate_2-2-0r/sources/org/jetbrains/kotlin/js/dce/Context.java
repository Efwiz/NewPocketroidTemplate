package org.jetbrains.kotlin.js.dce;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.apk.PathMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: Context.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00110'J\u0006\u0010(\u001a\u00020%J\u0014\u0010)\u001a\b\u0018\u00010\u0006R\u00020\u00002\u0006\u0010*\u001a\u00020\u0018J\u0016\u0010+\u001a\b\u0018\u00010\u0006R\u00020\u00002\u0006\u0010*\u001a\u00020\u0018H\u0002J\u000e\u0010,\u001a\u00020%2\u0006\u0010-\u001a\u00020\u001cJ\u0012\u0010.\u001a\u00020/2\n\u00100\u001a\u00060\u0006R\u00020\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00060\u0006R\u00020\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0015\u0010\u000b\u001a\u00060\u0006R\u00020\u0000¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0015\u0010\r\u001a\u00060\u0006R\u00020\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013Rn\u0010\u0014\u001ab\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000 \u0016*0\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0018\u00010\u00150\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R^\u0010\u0017\u001aR\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u00180\u0018 \u0016*(\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u00180\u0018\u0018\u00010\u00150\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R^\u0010\u0019\u001aR\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u001a0\u001a \u0016*(\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u001a0\u001a\u0018\u00010\u00150\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R^\u0010\u001b\u001aR\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u001c0\u001c \u0016*(\u0012\u0014\u0012\u0012 \u0016*\b\u0018\u00010\u0006R\u00020\u00000\u0006R\u00020\u0000\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u001c0\u001c\u0018\u00010\u00150\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u0011\u0012\b\u0012\u00060\u0006R\u00020\u00000\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R \u0010!\u001a\b\u0018\u00010\u0006R\u00020\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\b\"\u0004\b#\u0010\n¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/Context;", "", "()V", "currentColor", "", "currentModule", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "getCurrentModule", "()Lorg/jetbrains/kotlin/js/dce/Context$Node;", "setCurrentModule", "(Lorg/jetbrains/kotlin/js/dce/Context$Node;)V", "globalScope", "getGlobalScope", "moduleExportsNode", "getModuleExportsNode", "namesOfLocalVars", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getNamesOfLocalVars", "()Ljava/util/Set;", "nodeDependencies", "Lorg/jetbrains/kotlin/com/google/common/collect/LinkedHashMultimap;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "nodeExpressions", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "nodeFunctions", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "nodeUsedByAstNodes", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "nodes", "", "getNodes", "()Ljava/util/Map;", "thisNode", "getThisNode", "setThisNode", "addNodesForLocalVars", "", "names", "", "clearVisited", "extractNode", "expression", "extractNodeImpl", "markSpecialFunctions", "root", "visit", "", "n", "Node", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Context {
    private byte currentColor;
    private Node currentModule;
    private final Node globalScope;
    private final Node moduleExportsNode;
    private final Set<JsName> namesOfLocalVars;
    private final LinkedHashMultimap<Node, Node> nodeDependencies = LinkedHashMultimap.create();
    private final LinkedHashMultimap<Node, JsExpression> nodeExpressions = LinkedHashMultimap.create();
    private final LinkedHashMultimap<Node, JsFunction> nodeFunctions = LinkedHashMultimap.create();
    private final LinkedHashMultimap<Node, JsNode> nodeUsedByAstNodes = LinkedHashMultimap.create();
    private final Map<JsName, Node> nodes;
    private Node thisNode;

    /* JADX WARN: Multi-variable type inference failed */
    public Context() {
        Node node = new Node(this, (JsName) null, 1, (DefaultConstructorMarker) (0 == true ? 1 : 0));
        this.globalScope = node;
        this.moduleExportsNode = node.member("module").member(PsiKeyword.EXPORTS);
        this.currentModule = node;
        this.nodes = new LinkedHashMap();
        this.thisNode = node;
        this.namesOfLocalVars = new LinkedHashSet();
        this.currentColor = (byte) 1;
    }

    public final Node getGlobalScope() {
        return this.globalScope;
    }

    public final Node getModuleExportsNode() {
        return this.moduleExportsNode;
    }

    public final Node getCurrentModule() {
        return this.currentModule;
    }

    public final void setCurrentModule(Node node) {
        Intrinsics.checkNotNullParameter(node, "<set-?>");
        this.currentModule = node;
    }

    public final Map<JsName, Node> getNodes() {
        return this.nodes;
    }

    public final Node getThisNode() {
        return this.thisNode;
    }

    public final void setThisNode(Node node) {
        this.thisNode = node;
    }

    public final Set<JsName> getNamesOfLocalVars() {
        return this.namesOfLocalVars;
    }

    public final void addNodesForLocalVars(Collection<? extends JsName> names) {
        Intrinsics.checkNotNullParameter(names, "names");
        Map<JsName, Node> map = this.nodes;
        ArrayList arrayList = new ArrayList();
        for (Object obj : names) {
            if (!this.nodes.containsKey((JsName) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList<JsName> arrayList2 = arrayList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (JsName jsName : arrayList2) {
            Pair pair = TuplesKt.to(jsName, new Node(this, jsName));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        map.putAll(linkedHashMap);
    }

    public final void markSpecialFunctions(JsNode root) {
        Intrinsics.checkNotNullParameter(root, "root");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        final LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        root.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.dce.Context.markSpecialFunctions.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                SpecialFunction specialFunction;
                Intrinsics.checkNotNullParameter(x, "x");
                JsName name2 = x.getName();
                Set<JsName> set = linkedHashSet2;
                Intrinsics.checkNotNull(name2);
                if (!set.add(name2)) {
                    linkedHashSet.add(name2);
                }
                JsExpression initExpression = x.getInitExpression();
                if (initExpression != null) {
                    if (UtilKt.isDefineInlineFunction(this, initExpression)) {
                        specialFunction = SpecialFunction.DEFINE_INLINE_FUNCTION;
                    } else {
                        specialFunction = UtilKt.isWrapFunction(this, initExpression) ? SpecialFunction.WRAP_FUNCTION : null;
                    }
                    if (specialFunction != null) {
                        linkedHashMap.put(name2, specialFunction);
                    }
                }
                super.visit(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBinaryExpression(JsBinaryOperation x) {
                Intrinsics.checkNotNullParameter(x, "x");
                Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(x);
                if (pairDecomposeAssignmentToVariable != null) {
                    linkedHashSet.add(pairDecomposeAssignmentToVariable.component1());
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsName name2 = x.getName();
                if (name2 != null) {
                    linkedHashSet.add(name2);
                }
            }
        });
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            JsName jsName = (JsName) entry.getKey();
            SpecialFunction specialFunction = (SpecialFunction) entry.getValue();
            if (!linkedHashSet.contains(jsName)) {
                MetadataProperties.setSpecialFunction(jsName, specialFunction);
            }
        }
    }

    public final Node extractNode(JsExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Node nodeExtractNodeImpl = extractNodeImpl(expression);
        Node original = nodeExtractNodeImpl != null ? nodeExtractNodeImpl.getOriginal() : null;
        if (original == null || !SequencesKt.contains(SequencesKt.generateSequence(original, new Function1<Node, Node>() { // from class: org.jetbrains.kotlin.js.dce.Context.extractNode.1
            @Override // kotlin.jvm.functions.Function1
            public final Node invoke(Node it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getParent();
            }
        }), this.moduleExportsNode)) {
            return original;
        }
        List listDrop = CollectionsKt.drop(original.pathFromRoot(), 2);
        Node original2 = this.currentModule.getOriginal();
        Iterator it = listDrop.iterator();
        while (it.hasNext()) {
            original2 = original2.member((String) it.next());
        }
        return original2;
    }

    private final Node extractNodeImpl(JsExpression expression) {
        Node nodeExtractNodeImpl;
        Node original;
        if (expression instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) expression;
            JsExpression qualifier = jsNameRef.getQualifier();
            if (qualifier == null) {
                JsName name2 = jsNameRef.getName();
                if (name2 != null) {
                    if (this.namesOfLocalVars.contains(name2)) {
                        return null;
                    }
                    Node node = this.nodes.get(name2);
                    if (node != null && (original = node.getOriginal()) != null) {
                        return original;
                    }
                }
                Node node2 = this.globalScope;
                String ident = jsNameRef.getIdent();
                Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                return node2.member(ident);
            }
            Node nodeExtractNodeImpl2 = extractNodeImpl(qualifier);
            if (nodeExtractNodeImpl2 == null) {
                return null;
            }
            String ident2 = jsNameRef.getIdent();
            Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
            return nodeExtractNodeImpl2.member(ident2);
        }
        if (expression instanceof JsArrayAccess) {
            JsArrayAccess jsArrayAccess = (JsArrayAccess) expression;
            JsExpression index = AstUtilsKt.getIndex(jsArrayAccess);
            if (!(index instanceof JsStringLiteral) || (nodeExtractNodeImpl = extractNodeImpl(AstUtilsKt.getArray(jsArrayAccess))) == null) {
                return null;
            }
            String value = ((JsStringLiteral) index).getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return nodeExtractNodeImpl.member(value);
        }
        if (expression instanceof JsThisRef) {
            return this.thisNode;
        }
        if (!(expression instanceof JsInvocation)) {
            return null;
        }
        JsInvocation jsInvocation = (JsInvocation) expression;
        JsExpression qualifier2 = jsInvocation.getQualifier();
        Intrinsics.checkNotNullExpressionValue(qualifier2, "getQualifier(...)");
        if (!(qualifier2 instanceof JsNameRef)) {
            return null;
        }
        JsNameRef jsNameRef2 = (JsNameRef) qualifier2;
        if (jsNameRef2.getQualifier() != null || !Intrinsics.areEqual(jsNameRef2.getIdent(), "require") || this.nodes.containsKey(jsNameRef2.getName()) || jsInvocation.getArguments().size() != 1) {
            return null;
        }
        JsExpression jsExpression = jsInvocation.getArguments().get(0);
        if (!(jsExpression instanceof JsStringLiteral)) {
            return null;
        }
        Node node3 = this.globalScope;
        String value2 = ((JsStringLiteral) jsExpression).getValue();
        Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
        return node3.member(value2);
    }

    public final void clearVisited() {
        this.currentColor = (byte) (this.currentColor + 1);
    }

    public final boolean visit(Node n) {
        Intrinsics.checkNotNullParameter(n, "n");
        return n.visit(this.currentColor);
    }

    /* JADX INFO: compiled from: Context.kt */
    @Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010$\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004B)\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0005\u001a\b\u0018\u00010\u0000R\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0012\u0010>\u001a\u00020?2\n\u0010@\u001a\u00060\u0000R\u00020\u0006J\u000e\u0010A\u001a\u00020?2\u0006\u0010B\u001a\u00020\u001bJ\u000e\u0010C\u001a\u00020?2\u0006\u0010D\u001a\u00020\u001eJ\u000e\u0010E\u001a\u00020?2\u0006\u0010@\u001a\u00020<J\u0012\u0010F\u001a\u00020?2\n\u0010G\u001a\u00060\u0000R\u00020\u0006J\u0014\u0010H\u001a\u00020?2\n\u0010G\u001a\u00060\u0000R\u00020\u0006H\u0002J\u0014\u0010I\u001a\u00020?2\n\u0010G\u001a\u00060\u0000R\u00020\u0006H\u0002J\u0012\u0010J\u001a\u00060\u0000R\u00020\u00062\u0006\u0010K\u001a\u00020\bJ\u0014\u0010L\u001a\u00020?2\n\u0010G\u001a\u00060\u0000R\u00020\u0006H\u0002J\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\b0NJ\n\u0010O\u001a\u00060\u0000R\u00020\u0006J\b\u0010P\u001a\u00020\bH\u0016J\u000e\u0010Q\u001a\u00020\u000f2\u0006\u0010R\u001a\u00020\rR \u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\b\u0012\u00060\u0000R\u00020\u0006\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0016\u001a\f\u0012\b\u0012\u00060\u0000R\u00020\u00060\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00178F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0019R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00178F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0019R$\u0010 \u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b!\u0010\u0012\"\u0004\b\"\u0010\u0014R\u000e\u0010#\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\b0\u00178F¢\u0006\u0006\u001a\u0004\b)\u0010\u0019R!\u0010*\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\b\u0012\u00060\u0000R\u00020\u00060+8F¢\u0006\u0006\u001a\u0004\b,\u0010-R$\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\b\u0012\u00060\u0000R\u00020\u00060\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b/\u0010-R(\u00101\u001a\u00060\u0000R\u00020\u00062\n\u00100\u001a\u00060\u0000R\u00020\u00068F@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R*\u0010\u0005\u001a\b\u0018\u00010\u0000R\u00020\u00062\f\u00100\u001a\b\u0018\u00010\u0000R\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b4\u00103R\u000e\u00105\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000R$\u00107\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b8\u0010\u0012\"\u0004\b9\u0010\u0014R\u000e\u0010:\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010;\u001a\b\u0012\u0004\u0012\u00020<0\u00178F¢\u0006\u0006\u001a\u0004\b=\u0010\u0019¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/Context$Node;", "", "localName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "(Lorg/jetbrains/kotlin/js/dce/Context;Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "parent", "Lorg/jetbrains/kotlin/js/dce/Context;", "memberName", "", "(Lorg/jetbrains/kotlin/js/dce/Context;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/dce/Context$Node;Ljava/lang/String;)V", "_membersImpl", "", TypedValues.Custom.S_COLOR, "", "value", "", "declarationReachable", "getDeclarationReachable", "()Z", "setDeclarationReachable", "(Z)V", "declarationReachableImpl", "dependencies", "", "getDependencies", "()Ljava/util/Set;", "expressions", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getExpressions", "functions", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "getFunctions", "hasSideEffects", "getHasSideEffects", "setHasSideEffects", "hasSideEffectsImpl", "getLocalName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getMemberName", "()Ljava/lang/String;", "memberNames", "getMemberNames", "members", "", "getMembers", "()Ljava/util/Map;", "membersImpl", "getMembersImpl", "<set-?>", "original", "getOriginal", "()Lorg/jetbrains/kotlin/js/dce/Context$Node;", "getParent", "rank", "", "reachable", "getReachable", "setReachable", "reachableImpl", "usedByAstNodes", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "getUsedByAstNodes", "addDependency", "", "node", "addExpression", "expression", "addFunction", "function", "addUsedByAstNode", PathMap.NAME_alias, "other", "evacuateFrom", "makeDependencies", "member", "name", "merge", "pathFromRoot", "", "root", "toString", "visit", "c", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Node {
        private Map<String, Node> _membersImpl;
        private byte color;
        private boolean declarationReachableImpl;
        private boolean hasSideEffectsImpl;
        private final JsName localName;
        private final String memberName;
        private Node original;
        private Node parent;
        private int rank;
        private boolean reachableImpl;

        private Node(JsName jsName, Node node, String str) {
            this.localName = jsName;
            this.memberName = str;
            this.parent = node;
            this.original = this;
        }

        public final JsName getLocalName() {
            return this.localName;
        }

        public final String getMemberName() {
            return this.memberName;
        }

        private final Map<String, Node> getMembersImpl() {
            Map<String, Node> map = this._membersImpl;
            if (map != null) {
                return map;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            this._membersImpl = linkedHashMap;
            return linkedHashMap;
        }

        public final Set<Node> getDependencies() {
            Set<Node> set = Context.this.nodeDependencies.get((Object) getOriginal());
            Intrinsics.checkNotNullExpressionValue(set, "get(...)");
            return set;
        }

        public final Set<JsExpression> getExpressions() {
            Set<JsExpression> set = Context.this.nodeExpressions.get((Object) getOriginal());
            Intrinsics.checkNotNullExpressionValue(set, "get(...)");
            return set;
        }

        public final Set<JsFunction> getFunctions() {
            Set<JsFunction> set = Context.this.nodeFunctions.get((Object) getOriginal());
            Intrinsics.checkNotNullExpressionValue(set, "get(...)");
            return set;
        }

        public final Set<JsNode> getUsedByAstNodes() {
            Set<JsNode> set = Context.this.nodeUsedByAstNodes.get((Object) getOriginal());
            Intrinsics.checkNotNullExpressionValue(set, "get(...)");
            return set;
        }

        public final boolean getHasSideEffects() {
            return getOriginal().hasSideEffectsImpl;
        }

        public final void setHasSideEffects(boolean z) {
            getOriginal().hasSideEffectsImpl = z;
        }

        public final boolean getReachable() {
            return getOriginal().reachableImpl;
        }

        public final void setReachable(boolean z) {
            getOriginal().reachableImpl = z;
        }

        public final boolean getDeclarationReachable() {
            return getOriginal().declarationReachableImpl;
        }

        public final void setDeclarationReachable(boolean z) {
            getOriginal().declarationReachableImpl = z;
        }

        public final Node getParent() {
            return this.parent;
        }

        public final boolean visit(byte c) {
            boolean z = this.color != c;
            this.color = c;
            return z;
        }

        public final Set<String> getMemberNames() {
            Set<String> setKeySet;
            Map<String, Node> map = getOriginal()._membersImpl;
            return (map == null || (setKeySet = map.keySet()) == null) ? SetsKt.emptySet() : setKeySet;
        }

        public Node(Context context, JsName jsName) {
            this(jsName, (Node) null, (String) null);
        }

        public /* synthetic */ Node(Context context, JsName jsName, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i & 1) != 0 ? null : jsName);
        }

        public final Node getOriginal() {
            if (!Intrinsics.areEqual(this.original, this)) {
                this.original = this.original.getOriginal();
            }
            return this.original;
        }

        public final Map<String, Node> getMembers() {
            Map<String, Node> map = getOriginal()._membersImpl;
            return map == null ? MapsKt.emptyMap() : map;
        }

        public final void addDependency(Node node) {
            Intrinsics.checkNotNullParameter(node, "node");
            Context.this.nodeDependencies.put(getOriginal(), node);
        }

        public final void addFunction(JsFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            Context.this.nodeFunctions.put(getOriginal(), function);
        }

        public final void addExpression(JsExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Context.this.nodeExpressions.put(getOriginal(), expression);
        }

        public final void addUsedByAstNode(JsNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            Context.this.nodeUsedByAstNodes.put(getOriginal(), node);
        }

        public final Node member(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Map<String, Node> membersImpl = getOriginal().getMembersImpl();
            Context context = Context.this;
            Node node = membersImpl.get(name2);
            if (node == null) {
                node = context.new Node((JsName) null, this, name2);
                membersImpl.put(name2, node);
            }
            return node.getOriginal();
        }

        public final void alias(Node other) {
            Intrinsics.checkNotNullParameter(other, "other");
            Node original = getOriginal();
            Node original2 = other.getOriginal();
            if (Intrinsics.areEqual(original, original2)) {
                return;
            }
            Node node = original.parent;
            if (node == null && original2.parent == null) {
                original.merge(original2);
                return;
            }
            if (node == null) {
                if (Intrinsics.areEqual(original2.root(), original)) {
                    original.makeDependencies(original2);
                    return;
                } else {
                    original2.evacuateFrom(original);
                    return;
                }
            }
            if (original2.parent == null) {
                if (Intrinsics.areEqual(original.root(), original2)) {
                    original.makeDependencies(original2);
                    return;
                } else {
                    original.evacuateFrom(original2);
                    return;
                }
            }
            original.makeDependencies(original2);
        }

        private final void makeDependencies(Node other) {
            Context.this.nodeDependencies.put(this, other);
            Context.this.nodeDependencies.put(other, this);
        }

        private final void evacuateFrom(Node other) {
            List list = MapsKt.toList(other.getMembers());
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : list) {
                if (getMembersImpl().containsKey((String) ((Pair) obj).component1())) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List<Pair> list2 = (List) pair.component1();
            List<Pair> list3 = (List) pair.component2();
            other.original = this;
            for (Pair pair2 : list3) {
                String str = (String) pair2.component1();
                Node node = (Node) pair2.component2();
                getMembersImpl().put(str, node);
                node.getOriginal().parent = this;
            }
            for (Pair pair3 : list2) {
                String str2 = (String) pair3.component1();
                Node node2 = (Node) pair3.component2();
                Node node3 = getMembersImpl().get(str2);
                Intrinsics.checkNotNull(node3);
                node3.getOriginal().merge(node2.getOriginal());
                getMembersImpl().put(str2, node2.getOriginal());
                node2.getOriginal().parent = this;
            }
            other.getMembersImpl().clear();
            this.hasSideEffectsImpl = this.hasSideEffectsImpl || other.hasSideEffectsImpl;
            Context.this.nodeExpressions.putAll(this, Context.this.nodeExpressions.get((Object) other));
            Context.this.nodeFunctions.putAll(this, Context.this.nodeFunctions.get((Object) other));
            Context.this.nodeDependencies.putAll(this, Context.this.nodeDependencies.get((Object) other));
            Context.this.nodeUsedByAstNodes.putAll(this, Context.this.nodeUsedByAstNodes.get((Object) other));
            Context.this.nodeExpressions.removeAll((Object) other);
            Context.this.nodeFunctions.removeAll((Object) other);
            Context.this.nodeDependencies.removeAll((Object) other);
            Context.this.nodeUsedByAstNodes.removeAll((Object) other);
        }

        private final void merge(Node other) {
            if (Intrinsics.areEqual(this, other)) {
                return;
            }
            if (this.rank < other.rank) {
                other.evacuateFrom(this);
            } else {
                evacuateFrom(other);
            }
            int i = this.rank;
            if (i == other.rank) {
                this.rank = i + 1;
            }
        }

        public final Node root() {
            return (Node) SequencesKt.last(SequencesKt.generateSequence(getOriginal(), new Function1<Node, Node>() { // from class: org.jetbrains.kotlin.js.dce.Context$Node$root$1
                @Override // kotlin.jvm.functions.Function1
                public final Context.Node invoke(Context.Node it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Context.Node parent = it.getParent();
                    if (parent != null) {
                        return parent.getOriginal();
                    }
                    return null;
                }
            }));
        }

        public final List<String> pathFromRoot() {
            return CollectionsKt.asReversed(SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.generateSequence(getOriginal(), new Function1<Node, Node>() { // from class: org.jetbrains.kotlin.js.dce.Context$Node$pathFromRoot$1
                @Override // kotlin.jvm.functions.Function1
                public final Context.Node invoke(Context.Node it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Context.Node parent = it.getParent();
                    if (parent != null) {
                        return parent.getOriginal();
                    }
                    return null;
                }
            }), new Function1<Node, String>() { // from class: org.jetbrains.kotlin.js.dce.Context$Node$pathFromRoot$2
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(Context.Node it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getMemberName();
                }
            })));
        }

        public String toString() {
            String ident;
            StringBuilder sb = new StringBuilder();
            JsName jsName = root().localName;
            if (jsName == null || (ident = jsName.getIdent()) == null) {
                ident = "<unknown>";
            }
            return sb.append(ident).append(CollectionsKt.joinToString$default(pathFromRoot(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.js.dce.Context$Node$toString$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return "." + it;
                }
            }, 30, null)).toString();
        }
    }
}
