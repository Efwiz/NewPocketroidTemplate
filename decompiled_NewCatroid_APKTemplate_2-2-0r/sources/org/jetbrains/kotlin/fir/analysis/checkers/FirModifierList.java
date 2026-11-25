package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: FirKeywordUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\r\u000eB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0011\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0086\u0002J\u0017\u0010\f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0086\u0002R\u001c\u0010\u0003\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList;", "", "()V", "modifiers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier;", "getModifiers", "()Ljava/util/List;", "contains", "", "token", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "get", "FirLightModifierList", "FirPsiModifierList", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList$FirLightModifierList;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList$FirPsiModifierList;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirModifierList {
    public /* synthetic */ FirModifierList(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract List<FirModifier<?>> getModifiers();

    private FirModifierList() {
    }

    /* JADX INFO: compiled from: FirKeywordUtils.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList$FirPsiModifierList;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList;", "modifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "(Lorg/jetbrains/kotlin/psi/KtModifierList;)V", "getModifierList", "()Lorg/jetbrains/kotlin/psi/KtModifierList;", "modifiers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier$FirPsiModifier;", "getModifiers", "()Ljava/util/List;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FirPsiModifierList extends FirModifierList {
        private final KtModifierList modifierList;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FirPsiModifierList(KtModifierList modifierList) {
            super(null);
            Intrinsics.checkNotNullParameter(modifierList, "modifierList");
            this.modifierList = modifierList;
        }

        public final KtModifierList getModifierList() {
            return this.modifierList;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList
        public List<FirModifier.FirPsiModifier> getModifiers() {
            ASTNode[] children = this.modifierList.getNode().getChildren(FirKeywordUtilsKt.MODIFIER_KEYWORD_SET);
            Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
            ASTNode[] aSTNodeArr = children;
            ArrayList arrayList = new ArrayList(aSTNodeArr.length);
            for (ASTNode aSTNode : aSTNodeArr) {
                Intrinsics.checkNotNull(aSTNode);
                IElementType elementType = aSTNode.getElementType();
                Intrinsics.checkNotNull(elementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
                arrayList.add(new FirModifier.FirPsiModifier(aSTNode, (KtModifierKeywordToken) elementType));
            }
            return arrayList;
        }
    }

    /* JADX INFO: compiled from: FirKeywordUtils.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList$FirLightModifierList;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList;", "modifierList", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "offsetDelta", "", "(Lcom/intellij/lang/LighterASTNode;Lcom/intellij/util/diff/FlyweightCapableTreeStructure;I)V", "getModifierList", "()Lcom/intellij/lang/LighterASTNode;", "modifiers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier$FirLightModifier;", "getModifiers", "()Ljava/util/List;", "getTree", "()Lcom/intellij/util/diff/FlyweightCapableTreeStructure;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FirLightModifierList extends FirModifierList {
        private final LighterASTNode modifierList;
        private final int offsetDelta;
        private final FlyweightCapableTreeStructure<LighterASTNode> tree;

        public final LighterASTNode getModifierList() {
            return this.modifierList;
        }

        public final FlyweightCapableTreeStructure<LighterASTNode> getTree() {
            return this.tree;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FirLightModifierList(LighterASTNode modifierList, FlyweightCapableTreeStructure<LighterASTNode> tree, int i) {
            super(null);
            Intrinsics.checkNotNullParameter(modifierList, "modifierList");
            Intrinsics.checkNotNullParameter(tree, "tree");
            this.modifierList = modifierList;
            this.tree = tree;
            this.offsetDelta = i;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList
        public List<FirModifier.FirLightModifier> getModifiers() {
            List<LighterASTNode> children = LightTreeUtilsKt.getChildren(this.modifierList, this.tree);
            ArrayList arrayList = new ArrayList();
            for (Object obj : children) {
                if (((LighterASTNode) obj).getTokenType() instanceof KtModifierKeywordToken) {
                    arrayList.add(obj);
                }
            }
            ArrayList<LighterASTNode> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (LighterASTNode lighterASTNode : arrayList2) {
                IElementType tokenType = lighterASTNode.getTokenType();
                Intrinsics.checkNotNull(tokenType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
                arrayList3.add(new FirModifier.FirLightModifier(lighterASTNode, (KtModifierKeywordToken) tokenType, this.tree, this.offsetDelta));
            }
            return arrayList3;
        }
    }

    public final FirModifier<?> get(KtModifierKeywordToken token) {
        Object next;
        Intrinsics.checkNotNullParameter(token, "token");
        Iterator<T> it = getModifiers().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((FirModifier) next).getToken(), token)) {
                return (FirModifier) next;
            }
        }
        next = null;
        return (FirModifier) next;
    }

    public final boolean contains(KtModifierKeywordToken token) {
        Intrinsics.checkNotNullParameter(token, "token");
        List<FirModifier<?>> modifiers = getModifiers();
        if ((modifiers instanceof Collection) && modifiers.isEmpty()) {
            return false;
        }
        Iterator<T> it = modifiers.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((FirModifier) it.next()).getToken(), token)) {
                return true;
            }
        }
        return false;
    }
}
