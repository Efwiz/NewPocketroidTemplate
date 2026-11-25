package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;

/* JADX INFO: compiled from: FirKeywordUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002:\u0002\u0010\u0011B\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0001\u0002\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier;", "Node", "", "node", "token", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;)V", "getNode", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getToken", "()Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "FirLightModifier", "FirPsiModifier", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier$FirLightModifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier$FirPsiModifier;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirModifier<Node> {
    private final Node node;
    private final KtModifierKeywordToken token;

    public /* synthetic */ FirModifier(Object obj, KtModifierKeywordToken ktModifierKeywordToken, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj, ktModifierKeywordToken);
    }

    public abstract KtSourceElement getSource();

    private FirModifier(Node node, KtModifierKeywordToken ktModifierKeywordToken) {
        this.node = node;
        this.token = ktModifierKeywordToken;
    }

    public final Node getNode() {
        return this.node;
    }

    public final KtModifierKeywordToken getToken() {
        return this.token;
    }

    /* JADX INFO: compiled from: FirKeywordUtils.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier$FirPsiModifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier;", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "node", "token", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "(Lcom/intellij/lang/ASTNode;Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FirPsiModifier extends FirModifier<ASTNode> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FirPsiModifier(ASTNode node, KtModifierKeywordToken token) {
            super(node, token, null);
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(token, "token");
        }

        @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirModifier
        public KtSourceElement getSource() {
            KtFakeSourceElement ktFakeSourceElement;
            PsiElement psi = getNode().getPsi();
            Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
            Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
            if (obj instanceof KtRealSourceElementKind) {
                ktFakeSourceElement = new KtRealPsiSourceElement(psi);
            } else {
                if (!(obj instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement = new KtFakeSourceElement(psi, (KtFakeSourceElementKind) obj);
            }
            return ktFakeSourceElement;
        }
    }

    /* JADX INFO: compiled from: FirKeywordUtils.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B+\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier$FirLightModifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier;", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "node", "token", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "offsetDelta", "", "(Lcom/intellij/lang/LighterASTNode;Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;Lcom/intellij/util/diff/FlyweightCapableTreeStructure;I)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getTree", "()Lcom/intellij/util/diff/FlyweightCapableTreeStructure;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FirLightModifier extends FirModifier<LighterASTNode> {
        private final int offsetDelta;
        private final FlyweightCapableTreeStructure<LighterASTNode> tree;

        public final FlyweightCapableTreeStructure<LighterASTNode> getTree() {
            return this.tree;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FirLightModifier(LighterASTNode node, KtModifierKeywordToken token, FlyweightCapableTreeStructure<LighterASTNode> tree, int i) {
            super(node, token, null);
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(tree, "tree");
            this.tree = tree;
            this.offsetDelta = i;
        }

        @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirModifier
        public KtSourceElement getSource() {
            return new KtLightSourceElement(getNode(), getNode().getStartOffset() + this.offsetDelta, getNode().getEndOffset() + this.offsetDelta, this.tree, KtRealSourceElementKind.INSTANCE);
        }
    }
}
