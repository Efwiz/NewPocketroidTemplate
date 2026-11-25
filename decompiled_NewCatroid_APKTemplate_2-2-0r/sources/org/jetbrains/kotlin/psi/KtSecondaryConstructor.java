package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtSecondaryConstructorElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtSecondaryConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\b\u0016\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00000\u0006¢\u0006\u0002\u0010\u0007J=\u0010\b\u001a\n \n*\u0004\u0018\u0001H\tH\t\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000fJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\r\u0010\u0012\u001a\u00070\u0013¢\u0006\u0002\b\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018J\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "Lorg/jetbrains/kotlin/psi/KtConstructor;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;)V", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getBodyExpression", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "getConstructorKeyword", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/annotations/NotNull;", "getContainingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getDelegationCall", "Lorg/jetbrains/kotlin/psi/KtConstructorDelegationCall;", "getDelegationCallOrNull", "hasImplicitDelegationCall", "", "replaceImplicitDelegationCallWithExplicit", "isThis", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtSecondaryConstructor extends KtConstructor<KtSecondaryConstructor> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSecondaryConstructor(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtSecondaryConstructor(KotlinConstructorStub<KtSecondaryConstructor> stub) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        KtSecondaryConstructorElementType SECONDARY_CONSTRUCTOR = KtStubElementTypes.SECONDARY_CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(SECONDARY_CONSTRUCTOR, "SECONDARY_CONSTRUCTOR");
        super(stub, SECONDARY_CONSTRUCTOR);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSecondaryConstructor(this, data);
    }

    @Override // org.jetbrains.kotlin.psi.KtConstructor
    public KtClassOrObject getContainingClassOrObject() {
        PsiElement parent = getLightParent().getLightParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        return (KtClassOrObject) parent;
    }

    @Override // org.jetbrains.kotlin.psi.KtConstructor, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public KtBlockExpression getBodyExpression() {
        KotlinConstructorStub kotlinConstructorStub = (KotlinConstructorStub) getStub();
        if (kotlinConstructorStub == null || (kotlinConstructorStub.getHasBody() && !getContainingKtFile().getIsCompiled())) {
            return (KtBlockExpression) findChildByClass(KtBlockExpression.class);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtConstructor
    public PsiElement getConstructorKeyword() {
        Object objNotNullChild = notNullChild(super.getConstructorKeyword());
        Intrinsics.checkNotNullExpressionValue(objNotNullChild, "notNullChild(...)");
        return (PsiElement) objNotNullChild;
    }

    public final KtConstructorDelegationCall getDelegationCall() {
        Object objFindNotNullChildByClass = findNotNullChildByClass(KtConstructorDelegationCall.class);
        Intrinsics.checkNotNullExpressionValue(objFindNotNullChildByClass, "findNotNullChildByClass(...)");
        return (KtConstructorDelegationCall) objFindNotNullChildByClass;
    }

    public final KtConstructorDelegationCall getDelegationCallOrNull() {
        return (KtConstructorDelegationCall) findChildByClass(KtConstructorDelegationCall.class);
    }

    public final boolean hasImplicitDelegationCall() {
        return getDelegationCall().isImplicit();
    }

    public final KtConstructorDelegationCall replaceImplicitDelegationCallWithExplicit(boolean isThis) {
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null);
        KtConstructorDelegationCall delegationCall = getDelegationCall();
        delegationCall.isImplicit();
        delegationCall.delete();
        PsiElement psiElementAddAfter = addAfter(ktPsiFactory.creareDelegatedSuperTypeEntry((isThis ? "this" : PsiKeyword.SUPER).concat("()")), addAfter(ktPsiFactory.createColon(), getValueParameterList()));
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtConstructorDelegationCall");
        return (KtConstructorDelegationCall) psiElementAddAfter;
    }
}
