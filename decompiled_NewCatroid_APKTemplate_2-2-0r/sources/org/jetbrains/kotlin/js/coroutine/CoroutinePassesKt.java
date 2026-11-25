package org.jetbrains.kotlin.js.coroutine;

import java.util.ArrayList;
import java.util.Arrays;
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
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDebugger;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.RewriteUtilsKt;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;

/* JADX INFO: compiled from: CoroutinePasses.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\u0001*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u001a\u0018\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00060\u0006*\u00020\u0002H\u0002\u001a$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003*\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\u0001\u001a\u0012\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003*\u00020\u0002H\u0002\u001a2\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003*\b\u0012\u0004\u0012\u00020\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u001a\u000e\u0010\u0011\u001a\u00020\u0012*\u0004\u0018\u00010\u0013H\u0000\u001a\u0018\u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0017\u001a \u0010\u0018\u001a\u00020\u0015*\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u001a\u0012\u0010\u001a\u001a\u00020\u0015*\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u001a\u0010\u001b\u001a\u00020\u0015*\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000e¨\u0006\u001f"}, d2 = {"buildGraph", "", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "", "globalCatchBlock", "collectFinallyPaths", "", "collectNodesToSplit", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "breakContinueTargets", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "collectTargetBlocks", "collectVariablesSurvivingBetweenBlocks", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "localVariables", "parameters", "isStateMachineResult", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "replaceCoroutineFlowStatements", "", "context", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineTransformationContext;", "replaceLocalVariables", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "replaceSpecialReferences", "replaceSpecialReferencesInSimpleFunction", "continuationParam", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "resultVar", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutinePassesKt {
    public static final Set<JsNode> collectNodesToSplit(final JsNode jsNode, final Map<JsContinue, ? extends JsStatement> breakContinueTargets) {
        int size;
        Intrinsics.checkNotNullParameter(jsNode, "<this>");
        Intrinsics.checkNotNullParameter(breakContinueTargets, "breakContinueTargets");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        RecursiveJsVisitor recursiveJsVisitor = new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt$collectNodesToSplit$visitor$1
            private boolean childrenInSet;
            private int finallyLevel;

            public final boolean getChildrenInSet() {
                return this.childrenInSet;
            }

            public final void setChildrenInSet(boolean z) {
                this.childrenInSet = z;
            }

            public final int getFinallyLevel() {
                return this.finallyLevel;
            }

            public final void setFinallyLevel(int i) {
                this.finallyLevel = i;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitExpressionStatement(JsExpressionStatement x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitExpressionStatement(x);
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                if (MetadataProperties.isSuspend(expression)) {
                    linkedHashSet.add(x.getExpression());
                    this.childrenInSet = true;
                    return;
                }
                Pair<JsExpression, JsExpression> pairDecomposeAssignment = JsAstUtils.decomposeAssignment(x.getExpression());
                if (pairDecomposeAssignment != null) {
                    JsExpression second = pairDecomposeAssignment.getSecond();
                    Intrinsics.checkNotNullExpressionValue(second, "<get-second>(...)");
                    if (MetadataProperties.isSuspend(second)) {
                        linkedHashSet.add(pairDecomposeAssignment.getSecond());
                        this.childrenInSet = true;
                    }
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitReturn(JsReturn x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitReturn(x);
                if (linkedHashSet.contains(jsNode) || this.finallyLevel > 0) {
                    linkedHashSet.add(x);
                    this.childrenInSet = true;
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitBreak(x);
                JsStatement jsStatement = breakContinueTargets.get(x);
                Intrinsics.checkNotNull(jsStatement);
                if (linkedHashSet.contains(jsStatement)) {
                    linkedHashSet.add(x);
                    this.childrenInSet = true;
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitContinue(x);
                JsStatement jsStatement = breakContinueTargets.get(x);
                Intrinsics.checkNotNull(jsStatement);
                if (linkedHashSet.contains(jsStatement)) {
                    linkedHashSet.add(x);
                    this.childrenInSet = true;
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitTry(JsTry x) {
                Intrinsics.checkNotNullParameter(x, "x");
                if (x.getFinallyBlock() != null) {
                    this.finallyLevel++;
                }
                super.visitTry(x);
                if (x.getFinallyBlock() != null) {
                    this.finallyLevel--;
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                boolean z = this.childrenInSet;
                this.childrenInSet = false;
                node.acceptChildren(this);
                if (this.childrenInSet) {
                    linkedHashSet.add(node);
                } else {
                    this.childrenInSet = z;
                }
            }
        };
        do {
            size = linkedHashSet.size();
            recursiveJsVisitor.accept(jsNode);
        } while (linkedHashSet.size() != size);
        return linkedHashSet;
    }

    public static final void replaceCoroutineFlowStatements(List<CoroutineBlock> list, final CoroutineTransformationContext context) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        List<CoroutineBlock> list2 = list;
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(list2);
        final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            Pair pair = new Pair((CoroutineBlock) indexedValue.component2(), Integer.valueOf(indexedValue.getIndex()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        JsVisitorWithContextImpl jsVisitorWithContextImpl = new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt$replaceCoroutineFlowStatements$blockReplacementVisitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsDebugger x, JsContext<? super JsStatement> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                CoroutineBlock targetBlock = CoroutineMetadataPropertiesKt.getTargetBlock(x);
                if (targetBlock != null) {
                    JsNameRef jsNameRef = new JsNameRef(context.getMetadata().getStateName(), JsAstUtils.stateMachineReceiver());
                    Integer num = linkedHashMap.get(targetBlock);
                    Intrinsics.checkNotNull(num);
                    JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(JsAstUtils.assignment(jsNameRef, new JsIntLiteral(num.intValue())).source(x.getSource()));
                    CoroutineMetadataPropertiesKt.setTargetBlock(jsExpressionStatement, true);
                    ctx.replaceMe(jsExpressionStatement);
                }
                CoroutineBlock targetExceptionBlock = CoroutineMetadataPropertiesKt.getTargetExceptionBlock(x);
                if (targetExceptionBlock != null) {
                    JsNameRef jsNameRef2 = new JsNameRef(context.getMetadata().getExceptionStateName(), JsAstUtils.stateMachineReceiver());
                    Integer num2 = linkedHashMap.get(targetExceptionBlock);
                    Intrinsics.checkNotNull(num2);
                    JsExpressionStatement jsExpressionStatement2 = new JsExpressionStatement(JsAstUtils.assignment(jsNameRef2, new JsIntLiteral(num2.intValue())).source(x.getSource()));
                    CoroutineMetadataPropertiesKt.setTargetExceptionBlock(jsExpressionStatement2, true);
                    ctx.replaceMe(jsExpressionStatement2);
                }
                List<CoroutineBlock> finallyPath = CoroutineMetadataPropertiesKt.getFinallyPath(x);
                if (finallyPath != null) {
                    if (!finallyPath.isEmpty()) {
                        JsNameRef jsNameRef3 = new JsNameRef(context.getMetadata().getFinallyPathName(), JsAstUtils.stateMachineReceiver());
                        List<CoroutineBlock> list3 = finallyPath;
                        Map<CoroutineBlock, Integer> map = linkedHashMap;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                        Iterator<T> it = list3.iterator();
                        while (it.hasNext()) {
                            Integer num3 = map.get((CoroutineBlock) it.next());
                            Intrinsics.checkNotNull(num3);
                            arrayList.add(new JsIntLiteral(num3.intValue()));
                        }
                        JsExpressionStatement jsExpressionStatement3 = new JsExpressionStatement(JsAstUtils.assignment(jsNameRef3, new JsArrayLiteral(arrayList)).source(x.getSource()));
                        CoroutineMetadataPropertiesKt.setFinallyPath(jsExpressionStatement3, true);
                        ctx.replaceMe(jsExpressionStatement3);
                        return;
                    }
                    ctx.removeMe();
                }
            }
        };
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            jsVisitorWithContextImpl.accept(((CoroutineBlock) it.next()).getJsBlock());
        }
    }

    public static final Map<CoroutineBlock, Set<CoroutineBlock>> buildGraph(CoroutineBlock coroutineBlock, CoroutineBlock coroutineBlock2) {
        Intrinsics.checkNotNullParameter(coroutineBlock, "<this>");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        buildGraph$visitBlock(linkedHashSet, linkedHashMap, coroutineBlock, coroutineBlock2, coroutineBlock);
        return linkedHashMap;
    }

    private static final void buildGraph$visitBlock(Set<CoroutineBlock> set, Map<CoroutineBlock, Set<CoroutineBlock>> map, CoroutineBlock coroutineBlock, CoroutineBlock coroutineBlock2, CoroutineBlock coroutineBlock3) {
        if (set.contains(coroutineBlock3)) {
            return;
        }
        for (List<CoroutineBlock> list : collectFinallyPaths(coroutineBlock3)) {
            for (Pair pair : CollectionsKt.zip(CollectionsKt.plus((Collection) CollectionsKt.listOf(coroutineBlock3), (Iterable) list), list)) {
                CoroutineBlock coroutineBlock4 = (CoroutineBlock) pair.component1();
                CoroutineBlock coroutineBlock5 = (CoroutineBlock) pair.component2();
                LinkedHashSet linkedHashSet = map.get(coroutineBlock4);
                if (linkedHashSet == null) {
                    linkedHashSet = new LinkedHashSet();
                    map.put(coroutineBlock4, linkedHashSet);
                }
                if (linkedHashSet.add(coroutineBlock5)) {
                    set.remove(coroutineBlock4);
                }
            }
        }
        set.add(coroutineBlock3);
        LinkedHashSet linkedHashSet2 = map.get(coroutineBlock3);
        if (linkedHashSet2 == null) {
            linkedHashSet2 = new LinkedHashSet();
            map.put(coroutineBlock3, linkedHashSet2);
        }
        Set<CoroutineBlock> set2 = linkedHashSet2;
        Set<CoroutineBlock> set3 = set2;
        CollectionsKt.addAll(set3, collectTargetBlocks(coroutineBlock3));
        if (Intrinsics.areEqual(coroutineBlock3, coroutineBlock) && coroutineBlock2 != null) {
            set3.add(coroutineBlock2);
        }
        Iterator<T> it = set2.iterator();
        while (it.hasNext()) {
            buildGraph$visitBlock(set, map, coroutineBlock, coroutineBlock2, (CoroutineBlock) it.next());
        }
    }

    private static final Set<CoroutineBlock> collectTargetBlocks(CoroutineBlock coroutineBlock) {
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        coroutineBlock.getJsBlock().accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt.collectTargetBlocks.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDebugger(JsDebugger x) {
                Intrinsics.checkNotNullParameter(x, "x");
                CollectionsKt.addAll(linkedHashSet, CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(CoroutineMetadataPropertiesKt.getTargetExceptionBlock(x)), (Iterable) CollectionsKt.listOfNotNull(CoroutineMetadataPropertiesKt.getTargetBlock(x))));
            }
        });
        return linkedHashSet;
    }

    private static final List<List<CoroutineBlock>> collectFinallyPaths(CoroutineBlock coroutineBlock) {
        final ArrayList arrayList = new ArrayList();
        coroutineBlock.getJsBlock().accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt.collectFinallyPaths.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDebugger(JsDebugger x) {
                Intrinsics.checkNotNullParameter(x, "x");
                List<CoroutineBlock> finallyPath = CoroutineMetadataPropertiesKt.getFinallyPath(x);
                if (finallyPath != null) {
                    arrayList.add(finallyPath);
                }
            }
        });
        return arrayList;
    }

    public static final void replaceSpecialReferences(JsBlock jsBlock, final CoroutineTransformationContext context) {
        Intrinsics.checkNotNullParameter(jsBlock, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt$replaceSpecialReferences$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsThisRef x, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                ctx.replaceMe(new JsNameRef(context.getReceiverFieldName(), new JsThisRef()));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNameRef x, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (MetadataProperties.getCoroutineReceiver(x)) {
                    ctx.replaceMe(new JsThisRef());
                    return;
                }
                if (MetadataProperties.getCoroutineController(x)) {
                    JsNameRef jsNameRef = new JsNameRef(context.getControllerFieldName(), x.getQualifier());
                    jsNameRef.setSource(x.getSource());
                    MetadataProperties.setSideEffects(jsNameRef, SideEffectKind.PURE);
                    ctx.replaceMe(jsNameRef);
                    return;
                }
                if (MetadataProperties.getCoroutineResult(x)) {
                    JsNameRef jsNameRef2 = new JsNameRef(context.getMetadata().getResultName(), x.getQualifier());
                    jsNameRef2.setSource(x.getSource());
                    MetadataProperties.setSideEffects(jsNameRef2, SideEffectKind.DEPENDS_ON_STATE);
                    ctx.replaceMe(jsNameRef2);
                }
            }
        }.accept(jsBlock);
    }

    public static final void replaceSpecialReferencesInSimpleFunction(JsBlock jsBlock, final JsParameter continuationParam, final JsName resultVar) {
        Intrinsics.checkNotNullParameter(jsBlock, "<this>");
        Intrinsics.checkNotNullParameter(continuationParam, "continuationParam");
        Intrinsics.checkNotNullParameter(resultVar, "resultVar");
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt$replaceSpecialReferencesInSimpleFunction$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNameRef x, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (MetadataProperties.getCoroutineReceiver(x)) {
                    ctx.replaceMe(JsAstUtils.pureFqn(continuationParam.getName(), (JsExpression) null).source(x.getSource()));
                    return;
                }
                if (MetadataProperties.getCoroutineController(x)) {
                    JsThisRef jsThisRef = new JsThisRef();
                    jsThisRef.setSource(x.getSource());
                    ctx.replaceMe(jsThisRef);
                } else if (MetadataProperties.getCoroutineResult(x)) {
                    JsExpression qualifier = x.getQualifier();
                    JsParameter jsParameter = continuationParam;
                    if ((qualifier instanceof JsNameRef) && Intrinsics.areEqual(((JsNameRef) qualifier).getName(), jsParameter.getName())) {
                        ctx.replaceMe(JsAstUtils.pureFqn(resultVar, (JsExpression) null).source(x.getSource()));
                    }
                }
            }
        }.accept(jsBlock);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0145  */
    public static final Set<JsName> collectVariablesSurvivingBetweenBlocks(List<CoroutineBlock> list, Set<? extends JsName> localVariables, Set<? extends JsName> parameters) {
        boolean zCollectVariablesSurvivingBetweenBlocks$isLocalInBlock;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(localVariables, "localVariables");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Set<? extends JsName> set = localVariables;
        final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            Pair pair = TuplesKt.to((JsName) it.next(), new LinkedHashSet());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        Iterator<T> it2 = set.iterator();
        while (it2.hasNext()) {
            Pair pair2 = TuplesKt.to((JsName) it2.next(), new LinkedHashSet());
            linkedHashMap2.put(pair2.getFirst(), pair2.getSecond());
        }
        final LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        Iterator<T> it3 = set.iterator();
        while (it3.hasNext()) {
            Pair pair3 = TuplesKt.to((JsName) it3.next(), new LinkedHashSet());
            linkedHashMap3.put(pair3.getFirst(), pair3.getSecond());
        }
        Iterator<CoroutineBlock> it4 = list.iterator();
        final int i = 0;
        while (it4.hasNext()) {
            int i2 = i + 1;
            Iterator<JsStatement> it5 = it4.next().getStatements().iterator();
            while (it5.hasNext()) {
                it5.next().accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt.collectVariablesSurvivingBetweenBlocks.1
                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visitBreak(JsBreak x) {
                        Intrinsics.checkNotNullParameter(x, "x");
                    }

                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visitContinue(JsContinue x) {
                        Intrinsics.checkNotNullParameter(x, "x");
                    }

                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visitNameRef(JsNameRef nameRef) {
                        Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                        super.visitNameRef(nameRef);
                        Set<Integer> set2 = linkedHashMap3.get(nameRef.getName());
                        if (set2 != null) {
                            set2.add(Integer.valueOf(i));
                        }
                    }

                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visit(JsVars.JsVar x) {
                        Set<Integer> set2;
                        Intrinsics.checkNotNullParameter(x, "x");
                        Set<Integer> set3 = linkedHashMap2.get(x.getName());
                        if (set3 != null) {
                            set3.add(Integer.valueOf(i));
                        }
                        if (x.getInitExpression() != null && (set2 = linkedHashMap.get(x.getName())) != null) {
                            set2.add(Integer.valueOf(i));
                        }
                        super.visit(x);
                    }

                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visitParameter(JsParameter x) {
                        Intrinsics.checkNotNullParameter(x, "x");
                        Set<Integer> set2 = linkedHashMap2.get(x.getName());
                        if (set2 != null) {
                            set2.add(Integer.valueOf(i));
                        }
                        Set<Integer> set3 = linkedHashMap.get(x.getName());
                        if (set3 != null) {
                            set3.add(Integer.valueOf(i));
                        }
                        super.visitParameter(x);
                    }

                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visitBinaryExpression(JsBinaryOperation x) {
                        Set<Integer> set2;
                        Intrinsics.checkNotNullParameter(x, "x");
                        JsExpression arg1 = x.getArg1();
                        if (x.getOperator().isAssignment() && (arg1 instanceof JsNameRef) && (set2 = linkedHashMap.get(((JsNameRef) arg1).getName())) != null) {
                            set2.add(Integer.valueOf(i));
                            accept(x.getArg2());
                        } else {
                            super.visitBinaryExpression(x);
                        }
                    }

                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visitFunction(JsFunction x) {
                        Intrinsics.checkNotNullParameter(x, "x");
                        JsName name2 = x.getName();
                        if (name2 != null) {
                            Map<JsName, Set<Integer>> map = linkedHashMap;
                            int i3 = i;
                            Set<Integer> set2 = map.get(name2);
                            if (set2 != null) {
                                set2.add(Integer.valueOf(i3));
                            }
                        }
                    }

                    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                    public void visitLabel(JsLabel x) {
                        Intrinsics.checkNotNullParameter(x, "x");
                        accept(x.getStatement());
                    }
                });
            }
            i = i2;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            JsName jsName = (JsName) obj;
            if (parameters.contains(jsName)) {
                Object obj2 = linkedHashMap3.get(jsName);
                Intrinsics.checkNotNull(obj2);
                if (((Set) obj2).isEmpty()) {
                    Object obj3 = linkedHashMap.get(jsName);
                    Intrinsics.checkNotNull(obj3);
                    if (((Set) obj3).isEmpty()) {
                        Object obj4 = linkedHashMap2.get(jsName);
                        Intrinsics.checkNotNull(obj4);
                        if (((Set) obj4).isEmpty()) {
                            zCollectVariablesSurvivingBetweenBlocks$isLocalInBlock = true;
                        } else {
                            zCollectVariablesSurvivingBetweenBlocks$isLocalInBlock = false;
                        }
                    } else {
                        zCollectVariablesSurvivingBetweenBlocks$isLocalInBlock = false;
                    }
                } else {
                    zCollectVariablesSurvivingBetweenBlocks$isLocalInBlock = false;
                }
            } else {
                zCollectVariablesSurvivingBetweenBlocks$isLocalInBlock = collectVariablesSurvivingBetweenBlocks$isLocalInBlock(jsName, linkedHashMap, linkedHashMap3, linkedHashMap2);
            }
            if (!zCollectVariablesSurvivingBetweenBlocks$isLocalInBlock) {
                arrayList.add(obj);
            }
        }
        return CollectionsKt.toSet(arrayList);
    }

    private static final boolean collectVariablesSurvivingBetweenBlocks$isLocalInBlock(JsName jsName, Map<JsName, ? extends Set<Integer>> map, Map<JsName, ? extends Set<Integer>> map2, Map<JsName, ? extends Set<Integer>> map3) {
        Set<Integer> set = map.get(jsName);
        Intrinsics.checkNotNull(set);
        Set<Integer> set2 = set;
        Set<Integer> set3 = map2.get(jsName);
        Intrinsics.checkNotNull(set3);
        Set<Integer> set4 = set3;
        Set<Integer> set5 = map3.get(jsName);
        Intrinsics.checkNotNull(set5);
        Set<Integer> set6 = set5;
        if (set2.size() == 1 && set4.size() == 1) {
            return ((Number) CollectionsKt.single(set2)).intValue() == ((Number) CollectionsKt.single(set4)).intValue() && !set6.isEmpty();
        }
        return set4.isEmpty();
    }

    public static final void replaceLocalVariables(JsBlock jsBlock, final CoroutineTransformationContext context, final Set<? extends JsName> localVariables) {
        Intrinsics.checkNotNullParameter(jsBlock, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(localVariables, "localVariables");
        replaceSpecialReferences(jsBlock, context);
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt$replaceLocalVariables$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsFunction x, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                Set<JsName> setIntersect = CollectionsKt.intersect(CollectUtilsKt.collectFreeVariables(x), localVariables);
                if (setIntersect.isEmpty()) {
                    return;
                }
                JsFunction jsFunction = new JsFunction(x.getScope().getParent(), new JsBlock(), "");
                JsInvocation jsInvocation = new JsInvocation(jsFunction, new JsExpression[0]);
                List<JsStatement> statements = jsFunction.getBody().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                statements.add(new JsReturn(x));
                Set<JsName> set = setIntersect;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
                for (JsName jsName : set) {
                    Pair pair = TuplesKt.to(jsName, JsScope.declareTemporaryName(jsName.getIdent()));
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                for (JsName jsName2 : setIntersect) {
                    List<JsParameter> parameters = jsFunction.getParameters();
                    Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                    Object obj = linkedHashMap.get(jsName2);
                    Intrinsics.checkNotNull(obj);
                    parameters.add(new JsParameter((JsName) obj));
                    List<JsExpression> arguments = jsInvocation.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                    arguments.add(new JsNameRef(context.getFieldName(jsName2), new JsThisRef()));
                }
                JsBlock body = x.getBody();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap.size()));
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    linkedHashMap2.put(entry.getKey(), ((JsName) entry.getValue()).makeRef());
                }
                x.setBody((JsBlock) RewriteUtilsKt.replaceNames(body, linkedHashMap2));
                ctx.replaceMe(jsInvocation);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNameRef x, JsContext<? super JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (x.getQualifier() == null && CollectionsKt.contains(localVariables, x.getName())) {
                    CoroutineTransformationContext coroutineTransformationContext = context;
                    JsName name2 = x.getName();
                    Intrinsics.checkNotNull(name2);
                    ctx.replaceMe(new JsNameRef(coroutineTransformationContext.getFieldName(name2), new JsThisRef()).source(x.getSource()));
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsVars x, JsContext<? super JsStatement> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                List<JsVars.JsVar> vars = x.getVars();
                Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
                List<JsVars.JsVar> list = vars;
                Set<JsName> set = localVariables;
                if ((list instanceof Collection) && list.isEmpty()) {
                    return;
                }
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (set.contains(((JsVars.JsVar) it.next()).getName())) {
                        ArrayList arrayList = new ArrayList();
                        List<JsVars.JsVar> vars2 = x.getVars();
                        Intrinsics.checkNotNullExpressionValue(vars2, "getVars(...)");
                        final Set<JsName> set2 = localVariables;
                        for (Pair pair : UtilsKt.splitToRanges(vars2, new Function1<JsVars.JsVar, Boolean>() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutinePassesKt$replaceLocalVariables$visitor$1$endVisit$3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(JsVars.JsVar jsVar) {
                                return Boolean.valueOf(set2.contains(jsVar.getName()));
                            }
                        })) {
                            List list2 = (List) pair.component1();
                            if (((Boolean) pair.component2()).booleanValue()) {
                                List<JsVars.JsVar> vars3 = x.getVars();
                                Intrinsics.checkNotNullExpressionValue(vars3, "getVars(...)");
                                CoroutineTransformationContext coroutineTransformationContext = context;
                                ArrayList arrayList2 = new ArrayList();
                                for (JsVars.JsVar jsVar : vars3) {
                                    JsName name2 = jsVar.getName();
                                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                                    JsBinaryOperation jsBinaryOperationAssignment = jsVar.getInitExpression() != null ? JsAstUtils.assignment(new JsNameRef(coroutineTransformationContext.getFieldName(name2), new JsThisRef()), jsVar.getInitExpression()) : null;
                                    if (jsBinaryOperationAssignment != null) {
                                        arrayList2.add(jsBinaryOperationAssignment);
                                    }
                                }
                                ArrayList arrayList3 = arrayList2;
                                if (!arrayList3.isEmpty()) {
                                    arrayList.add(new JsExpressionStatement(JsAstUtils.newSequence(arrayList3)));
                                }
                            } else {
                                JsVars.JsVar[] jsVarArr = (JsVars.JsVar[]) list2.toArray(new JsVars.JsVar[0]);
                                arrayList.add(new JsVars((JsVars.JsVar[]) Arrays.copyOf(jsVarArr, jsVarArr.length)));
                            }
                        }
                        if (arrayList.size() == 1) {
                            ctx.replaceMe((JsNode) arrayList.get(0));
                            return;
                        } else {
                            ctx.removeMe();
                            ctx.addPrevious(arrayList);
                            return;
                        }
                    }
                }
            }
        }.accept(jsBlock);
    }

    public static final boolean isStateMachineResult(JsExpression jsExpression) {
        if (jsExpression instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) jsExpression;
            if (MetadataProperties.getCoroutineResult(jsNameRef)) {
                JsExpression qualifier = jsNameRef.getQualifier();
                if (qualifier instanceof JsNameRef) {
                    JsNameRef jsNameRef2 = (JsNameRef) qualifier;
                    if (MetadataProperties.getCoroutineReceiver(jsNameRef2) && jsNameRef2.getQualifier() == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
