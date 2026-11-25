package org.jetbrains.kotlin.js.parser.sourcemaps;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithEmbeddedSource;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode;

/* JADX INFO: compiled from: SourceMapLocationRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u000fB#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapLocationRemapper;", "", "sourceMap", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;", "sourceMapPathMapper", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMap;Lkotlin/jvm/functions/Function1;)V", "applySourceMap", "", "nodes", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "remap", "node", "JsNodeFlatListCollector", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMapLocationRemapper {
    private final SourceMap sourceMap;
    private final Function1<String, String> sourceMapPathMapper;

    /* JADX WARN: Multi-variable type inference failed */
    public SourceMapLocationRemapper(SourceMap sourceMap, Function1<? super String, String> sourceMapPathMapper) {
        Intrinsics.checkNotNullParameter(sourceMap, "sourceMap");
        Intrinsics.checkNotNullParameter(sourceMapPathMapper, "sourceMapPathMapper");
        this.sourceMap = sourceMap;
        this.sourceMapPathMapper = sourceMapPathMapper;
    }

    public /* synthetic */ SourceMapLocationRemapper(SourceMap sourceMap, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sourceMap, (i & 2) != 0 ? new Function1<String, String>() { // from class: org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapLocationRemapper.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        } : anonymousClass1);
    }

    public final void remap(JsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        JsNodeFlatListCollector jsNodeFlatListCollector = new JsNodeFlatListCollector();
        node.accept(jsNodeFlatListCollector);
        applySourceMap(jsNodeFlatListCollector.getNodeList());
    }

    private final void applySourceMap(List<? extends JsNode> nodes) {
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Ref.IntRef intRef = new Ref.IntRef();
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        Ref.IntRef intRef2 = new Ref.IntRef();
        Sequence<SourceInfoAwareJsNode> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(nodes), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapLocationRemapper$applySourceMap$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof SourceInfoAwareJsNode);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        for (SourceInfoAwareJsNode sourceInfoAwareJsNode : sequenceFilter) {
            final SourceMapSegment sourceMapSegmentApplySourceMap$findCorrespondingSegment = applySourceMap$findCorrespondingSegment(this, objectRef, intRef2, intRef, objectRef2, sourceInfoAwareJsNode);
            JsLocationWithEmbeddedSource jsLocationWithEmbeddedSource = null;
            String sourceFileName = sourceMapSegmentApplySourceMap$findCorrespondingSegment != null ? sourceMapSegmentApplySourceMap$findCorrespondingSegment.getSourceFileName() : null;
            if (sourceFileName != null) {
                jsLocationWithEmbeddedSource = new JsLocationWithEmbeddedSource(new JsLocation(this.sourceMapPathMapper.invoke(sourceFileName), sourceMapSegmentApplySourceMap$findCorrespondingSegment.getSourceLineNumber(), sourceMapSegmentApplySourceMap$findCorrespondingSegment.getSourceColumnNumber(), sourceMapSegmentApplySourceMap$findCorrespondingSegment.getName()), null, new Function0<Reader>() { // from class: org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapLocationRemapper.applySourceMap.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Reader invoke() {
                        return SourceMapLocationRemapper.this.sourceMap.getSourceContentResolver().invoke(sourceMapSegmentApplySourceMap$findCorrespondingSegment.getSourceFileName());
                    }
                });
            }
            sourceInfoAwareJsNode.setSource(jsLocationWithEmbeddedSource);
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [T, java.lang.Object, org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapGroup] */
    /* JADX WARN: Type inference failed for: r5v12, types: [T, org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapSegment] */
    /* JADX WARN: Type inference failed for: r5v6, types: [T, org.jetbrains.kotlin.js.parser.sourcemaps.SourceMapSegment] */
    private static final SourceMapSegment applySourceMap$findCorrespondingSegment(SourceMapLocationRemapper sourceMapLocationRemapper, Ref.ObjectRef<SourceMapGroup> objectRef, Ref.IntRef intRef, Ref.IntRef intRef2, Ref.ObjectRef<SourceMapSegment> objectRef2, SourceInfoAwareJsNode sourceInfoAwareJsNode) {
        Object source = sourceInfoAwareJsNode.getSource();
        JsLocation jsLocation = source instanceof JsLocation ? (JsLocation) source : null;
        if (jsLocation == null) {
            return null;
        }
        List<SourceMapGroup> groups = sourceMapLocationRemapper.sourceMap.getGroups();
        int startLine = jsLocation.getStartLine();
        if (startLine < 0 || startLine > CollectionsKt.getLastIndex(groups)) {
            return null;
        }
        SourceMapGroup sourceMapGroup = groups.get(startLine);
        if (!Intrinsics.areEqual(objectRef.element, sourceMapGroup)) {
            if (objectRef.element != null) {
                SourceMapGroup sourceMapGroup2 = objectRef.element;
                Intrinsics.checkNotNull(sourceMapGroup2);
                List mutableList = CollectionsKt.toMutableList((Collection) CollectionsKt.drop(sourceMapGroup2.getSegments(), intRef.element));
                if (intRef2.element + 1 < jsLocation.getStartLine()) {
                    List list = mutableList;
                    List<SourceMapGroup> listSubList = sourceMapLocationRemapper.sourceMap.getGroups().subList(intRef2.element + 1, jsLocation.getStartLine());
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it = listSubList.iterator();
                    while (it.hasNext()) {
                        CollectionsKt.addAll(arrayList, ((SourceMapGroup) it.next()).getSegments());
                    }
                    CollectionsKt.addAll(list, arrayList);
                }
                ?? r5 = (SourceMapSegment) CollectionsKt.lastOrNull(mutableList);
                if (r5 != 0) {
                    objectRef2.element = r5;
                }
            }
            objectRef.element = sourceMapGroup;
            intRef2.element = jsLocation.getStartLine();
            intRef.element = 0;
        }
        while (intRef.element < sourceMapGroup.getSegments().size()) {
            SourceMapSegment sourceMapSegment = sourceMapGroup.getSegments().get(intRef.element);
            if (sourceMapSegment.getGeneratedColumnNumber() > jsLocation.getStartChar()) {
                break;
            }
            objectRef2.element = sourceMapSegment;
            intRef.element++;
        }
        return objectRef2.element;
    }

    /* JADX INFO: compiled from: SourceMapLocationRemapper.kt */
    @Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J-\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0016\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\f\"\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0014J\u0010\u0010\u001a\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020%H\u0016J\u0014\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapLocationRemapper$JsNodeFlatListCollector;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "()V", "nodeList", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "getNodeList", "()Ljava/util/List;", "handleNode", "", "node", "children", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;[Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)V", "visitArray", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayLiteral;", "visitArrayAccess", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;", "visitBinaryExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "visitConditional", "Lorg/jetbrains/kotlin/js/backend/ast/JsConditional;", "visitDoWhile", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", "visitElement", "visitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitInvocation", "invocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "visitNameRef", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "visitPostfixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPostfixOperation;", "visitPrefixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPrefixOperation;", "isNotBefore", "", "other", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JsNodeFlatListCollector extends RecursiveJsVisitor {
        private final List<JsNode> nodeList = new ArrayList();

        public final List<JsNode> getNodeList() {
            return this.nodeList;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitDoWhile(JsDoWhile x) {
            Intrinsics.checkNotNullParameter(x, "x");
            this.nodeList.add(x);
            accept(x.getBody());
            accept(x.getCondition());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitBinaryExpression(JsBinaryOperation x) {
            Intrinsics.checkNotNullParameter(x, "x");
            handleNode(x, x.getArg1(), x.getArg2());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitConditional(JsConditional x) {
            Intrinsics.checkNotNullParameter(x, "x");
            handleNode(x, x.getTestExpression(), x.getThenExpression(), x.getElseExpression());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitArrayAccess(JsArrayAccess x) {
            Intrinsics.checkNotNullParameter(x, "x");
            handleNode(x, x.getArrayExpression(), x.getIndexExpression());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitArray(JsArrayLiteral x) {
            Intrinsics.checkNotNullParameter(x, "x");
            JsArrayLiteral jsArrayLiteral = x;
            List<JsExpression> expressions = x.getExpressions();
            Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
            JsExpression[] jsExpressionArr = (JsExpression[]) expressions.toArray(new JsExpression[0]);
            handleNode(jsArrayLiteral, (JsNode[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitPrefixOperation(JsPrefixOperation x) {
            Intrinsics.checkNotNullParameter(x, "x");
            handleNode(x, x.getArg());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitPostfixOperation(JsPostfixOperation x) {
            Intrinsics.checkNotNullParameter(x, "x");
            handleNode(x, x.getArg());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitNameRef(JsNameRef nameRef) {
            Intrinsics.checkNotNullParameter(nameRef, "nameRef");
            handleNode(nameRef, nameRef.getQualifier());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitInvocation(JsInvocation invocation) {
            Intrinsics.checkNotNullParameter(invocation, "invocation");
            JsInvocation jsInvocation = invocation;
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(invocation.getQualifier());
            List<JsExpression> arguments = invocation.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            spreadBuilder.addSpread(arguments.toArray(new JsExpression[0]));
            handleNode(jsInvocation, (JsNode[]) spreadBuilder.toArray(new JsNode[spreadBuilder.size()]));
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitFunction(JsFunction x) {
            Intrinsics.checkNotNullParameter(x, "x");
            this.nodeList.add(x);
            List<JsParameter> parameters = x.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Iterator<T> it = parameters.iterator();
            while (it.hasNext()) {
                accept((JsParameter) it.next());
            }
            List<JsStatement> statements = x.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            Iterator<T> it2 = statements.iterator();
            while (it2.hasNext()) {
                accept((JsStatement) it2.next());
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
        protected void visitElement(JsNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            this.nodeList.add(node);
            node.acceptChildren(this);
        }

        private final boolean isNotBefore(JsNode jsNode, JsNode jsNode2) {
            Object source = jsNode.getSource();
            JsLocation jsLocation = source instanceof JsLocation ? (JsLocation) source : null;
            if (jsLocation == null) {
                return false;
            }
            Object source2 = jsNode2.getSource();
            JsLocation jsLocation2 = source2 instanceof JsLocation ? (JsLocation) source2 : null;
            if (jsLocation2 != null && Intrinsics.areEqual(jsLocation.getFile(), jsLocation2.getFile())) {
                return jsLocation.getStartLine() > jsLocation2.getStartLine() || (jsLocation.getStartLine() == jsLocation2.getStartLine() && jsLocation.getStartChar() >= jsLocation2.getStartChar());
            }
            return false;
        }

        private final void handleNode(JsNode node, JsNode... children) {
            ArrayList arrayList = new ArrayList();
            for (JsNode jsNode : children) {
                if (jsNode != null) {
                    arrayList.add(jsNode);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                this.nodeList.add(node);
                return;
            }
            JsNode jsNode2 = (JsNode) CollectionsKt.first((List) arrayList2);
            if (isNotBefore(node, jsNode2)) {
                accept(jsNode2);
                this.nodeList.add(node);
                Iterator it = CollectionsKt.drop(arrayList2, 1).iterator();
                while (it.hasNext()) {
                    accept((JsNode) it.next());
                }
                return;
            }
            this.nodeList.add(node);
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                accept((JsNode) it2.next());
            }
        }
    }
}
