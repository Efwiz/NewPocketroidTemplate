package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: KtLabeledExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J=\u0010\n\u001a\n \f*\u0004\u0018\u0001H\u000bH\u000b\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0011J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0013H\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u00078G¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtLabeledExpression;", "Lorg/jetbrains/kotlin/psi/KtExpressionWithLabel;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameIdentifierOwner;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "baseExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getBaseExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/LocalSearchScope;", "setName", "name", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLabeledExpression extends KtExpressionWithLabel implements PsiNameIdentifierOwner {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLabeledExpression(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @IfNotParsed
    public final KtExpression getBaseExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionWithLabel, org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitLabeledExpression(this, data);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        return getLabelName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        KtSimpleNameExpression targetLabel = getTargetLabel();
        if (targetLabel != null) {
            Project project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            KtSimpleNameExpression targetLabel2 = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createLabeledExpression(name2).getTargetLabel();
            Intrinsics.checkNotNull(targetLabel2);
            targetLabel.replace(targetLabel2);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiElement mo7392getNameIdentifier() {
        KtSimpleNameExpression targetLabel = getTargetLabel();
        if (targetLabel != null) {
            return targetLabel.getIdentifier();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public LocalSearchScope getUseScope() {
        return new LocalSearchScope(this);
    }
}
