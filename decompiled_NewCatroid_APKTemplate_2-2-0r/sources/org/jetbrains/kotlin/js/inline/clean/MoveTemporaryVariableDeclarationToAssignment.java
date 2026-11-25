package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: MoveTemporaryVariableDeclarationToAssignment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0006\u0010\u0014\u001a\u00020\tJ\u0016\u0010\u0015\u001a\u00020\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0013H\u0002J\u001e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00032\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\u001cH\u0002R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0006j\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000RJ\u0010\u000e\u001a>\u0012\u0004\u0012\u00020\f\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\r0\u0006j\u001e\u0012\u0004\u0012\u00020\f\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\r`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000RJ\u0010\u0010\u001a>\u0012\u0004\u0012\u00020\f\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\r0\u0006j\u001e\u0012\u0004\u0012\u00020\f\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\r`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/MoveTemporaryVariableDeclarationToAssignment;", "", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;)V", "blockParents", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "hasChanges", "", "removedVarDeclarations", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lkotlin/collections/HashSet;", "varAssignedInBlocks", "varBeforeAssignmentUsages", "varUsedInBlocks", "varWithoutInitDeclarations", "analyze", "", "apply", "calculateLCA", "blocks", "", "perform", "removeNonParentBlocks", "block", "parents", "Ljava/util/LinkedHashSet;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MoveTemporaryVariableDeclarationToAssignment {
    private final HashMap<JsBlock, JsBlock> blockParents;
    private final JsBlock body;
    private boolean hasChanges;
    private final HashSet<JsName> removedVarDeclarations;
    private final HashMap<JsName, HashSet<JsBlock>> varAssignedInBlocks;
    private final HashSet<JsName> varBeforeAssignmentUsages;
    private final HashMap<JsName, HashSet<JsBlock>> varUsedInBlocks;
    private final HashSet<JsName> varWithoutInitDeclarations;

    public MoveTemporaryVariableDeclarationToAssignment(JsBlock body) {
        Intrinsics.checkNotNullParameter(body, "body");
        this.body = body;
        this.varUsedInBlocks = new HashMap<>();
        this.varAssignedInBlocks = new HashMap<>();
        this.blockParents = new HashMap<>();
        this.varWithoutInitDeclarations = new HashSet<>();
        this.varBeforeAssignmentUsages = new HashSet<>();
        this.removedVarDeclarations = new HashSet<>();
    }

    public final boolean apply() {
        analyze();
        perform();
        if (!this.removedVarDeclarations.isEmpty()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return this.hasChanges;
    }

    private final void analyze() {
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.MoveTemporaryVariableDeclarationToAssignment$analyze$visitor$1
            private final List<JsBlock> blockStack = new ArrayList();

            private final JsBlock getCurrentBlock() {
                return (JsBlock) CollectionsKt.last((List) this.blockStack);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBlock(JsBlock x) {
                Intrinsics.checkNotNullParameter(x, "x");
                if (!this.blockStack.isEmpty()) {
                    this.this$0.blockParents.put(x, getCurrentBlock());
                }
                this.blockStack.add(x);
                super.visitBlock(x);
                CollectionsKt.removeLast(this.blockStack);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                Intrinsics.checkNotNullParameter(x, "x");
                if (x.getInitExpression() == null && MetadataProperties.getSynthetic(x)) {
                    this.this$0.varWithoutInitDeclarations.add(x.getName());
                }
                super.visit(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitVars(JsVars x) {
                Intrinsics.checkNotNullParameter(x, "x");
                if (MetadataProperties.getSynthetic(x)) {
                    for (JsVars.JsVar jsVar : x) {
                        if (jsVar.getInitExpression() == null) {
                            this.this$0.varWithoutInitDeclarations.add(jsVar.getName());
                        }
                    }
                }
                super.visitVars(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                JsName name2 = nameRef.getName();
                if (name2 != null && this.this$0.varWithoutInitDeclarations.contains(name2)) {
                    HashMap map = this.this$0.varUsedInBlocks;
                    Object hashSet = map.get(name2);
                    if (hashSet == null) {
                        hashSet = new HashSet();
                        map.put(name2, hashSet);
                    }
                    ((Collection) hashSet).add(getCurrentBlock());
                    HashSet hashSet2 = (HashSet) this.this$0.varAssignedInBlocks.get(name2);
                    HashSet hashSetEmptySet = hashSet2 == null ? SetsKt.emptySet() : hashSet2;
                    List<JsBlock> list = this.blockStack;
                    if ((list instanceof Collection) && list.isEmpty()) {
                        this.this$0.varBeforeAssignmentUsages.add(name2);
                    } else {
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            if (hashSetEmptySet.contains((JsBlock) it.next())) {
                            }
                        }
                        this.this$0.varBeforeAssignmentUsages.add(name2);
                    }
                }
                super.visitNameRef(nameRef);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitExpressionStatement(JsExpressionStatement x) {
                Intrinsics.checkNotNullParameter(x, "x");
                Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(x.getExpression());
                if (pairDecomposeAssignmentToVariable != null) {
                    JsName jsNameComponent1 = pairDecomposeAssignmentToVariable.component1();
                    if (this.this$0.varWithoutInitDeclarations.contains(jsNameComponent1)) {
                        HashMap map = this.this$0.varAssignedInBlocks;
                        Intrinsics.checkNotNull(jsNameComponent1);
                        Object hashSet = map.get(jsNameComponent1);
                        if (hashSet == null) {
                            hashSet = new HashSet();
                            map.put(jsNameComponent1, hashSet);
                        }
                        ((Collection) hashSet).add(getCurrentBlock());
                    }
                }
                super.visitExpressionStatement(x);
            }
        }.accept(this.body);
    }

    private final void removeNonParentBlocks(JsBlock block, LinkedHashSet<JsBlock> parents) {
        while (block != null) {
            if (!parents.contains(block)) {
                block = this.blockParents.get(block);
            } else {
                Iterator<JsBlock> it = parents.iterator();
                Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(it.next(), block)) {
                        return;
                    } else {
                        it.remove();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsBlock calculateLCA(Set<? extends JsBlock> blocks) {
        if (blocks.isEmpty()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        LinkedHashSet<JsBlock> linkedHashSet = new LinkedHashSet<>();
        Set<? extends JsBlock> set = blocks;
        JsBlock jsBlock = (JsBlock) CollectionsKt.first(set);
        while (jsBlock != null) {
            linkedHashSet.add(jsBlock);
            jsBlock = this.blockParents.get(jsBlock);
        }
        Iterator it = SequencesKt.drop(CollectionsKt.asSequence(set), 1).iterator();
        while (it.hasNext()) {
            removeNonParentBlocks((JsBlock) it.next(), linkedHashSet);
        }
        if (linkedHashSet.isEmpty()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return (JsBlock) CollectionsKt.first(linkedHashSet);
    }

    private final void perform() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.MoveTemporaryVariableDeclarationToAssignment$perform$visitor$1
            private final boolean canRemoveDeclarationWithoutInit(JsName name2) {
                HashSet hashSet;
                if (!this.this$0.varWithoutInitDeclarations.contains(name2) || this.this$0.varBeforeAssignmentUsages.contains(name2) || (hashSet = (HashSet) this.this$0.varAssignedInBlocks.get(name2)) == null) {
                    return false;
                }
                HashSet hashSet2 = (HashSet) this.this$0.varUsedInBlocks.get(name2);
                return hashSet.contains(this.this$0.calculateLCA(SetsKt.plus(hashSet2 == null ? SetsKt.emptySet() : hashSet2, (Iterable) hashSet)));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsVars.JsVar x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsName name2 = x.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (canRemoveDeclarationWithoutInit(name2)) {
                    this.this$0.removedVarDeclarations.add(x.getName());
                    ctx.removeMe();
                    this.this$0.hasChanges = true;
                    return;
                }
                super.endVisit(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsVars x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (x.isEmpty()) {
                    ctx.removeMe();
                    this.this$0.hasChanges = true;
                } else {
                    super.endVisit(x, ctx);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsExpressionStatement x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(x.getExpression());
                if (pairDecomposeAssignmentToVariable != null) {
                    JsName jsNameComponent1 = pairDecomposeAssignmentToVariable.component1();
                    JsExpression jsExpressionComponent2 = pairDecomposeAssignmentToVariable.component2();
                    if (this.this$0.removedVarDeclarations.remove(jsNameComponent1)) {
                        JsVars.JsVar jsVar = new JsVars.JsVar(jsNameComponent1, jsExpressionComponent2);
                        MetadataProperties.setSynthetic(jsVar, true);
                        JsVars jsVars = new JsVars(jsVar);
                        MetadataProperties.setSynthetic(jsVars, true);
                        ctx.replaceMe(jsVars);
                    }
                    accept(jsExpressionComponent2);
                    return;
                }
                super.endVisit(x, ctx);
            }
        }.accept(this.body);
    }
}
