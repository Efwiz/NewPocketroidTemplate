package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachementBuilderUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: KtDotQualifiedExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u0000 \u00192\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00000\u00020\u00012\u00020\u0003:\u0001\u0019B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002¢\u0006\u0002\u0010\bJ5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00110\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015J#\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u00172\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H\u0002¢\u0006\u0002\u0010\u0018R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtDotQualifiedExpression;", "Lorg/jetbrains/kotlin/psi/KtExpressionImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;)V", "receiverExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getReceiverExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "selectorExpression", "getSelectorExpression", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getChildExpressionsByStub", "", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;)[Lorg/jetbrains/kotlin/psi/KtExpression;", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDotQualifiedExpression extends KtExpressionImplStub<KotlinPlaceHolderStub<KtDotQualifiedExpression>> implements KtQualifiedExpression {
    private static final Logger LOG;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDotQualifiedExpression(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDotQualifiedExpression(KotlinPlaceHolderStub<KtDotQualifiedExpression> stub) {
        super(stub, KtStubElementTypes.DOT_QUALIFIED_EXPRESSION);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImplStub, org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitDotQualifiedExpression(this, data);
    }

    @Override // org.jetbrains.kotlin.psi.KtQualifiedExpression
    public KtExpression getReceiverExpression() {
        KtExpression[] childExpressionsByStub;
        KotlinPlaceHolderStub<KtDotQualifiedExpression> kotlinPlaceHolderStub = (KotlinPlaceHolderStub) getStub();
        if (kotlinPlaceHolderStub != null && (childExpressionsByStub = getChildExpressionsByStub(kotlinPlaceHolderStub)) != null) {
            return childExpressionsByStub[0];
        }
        return super.getReceiverExpression();
    }

    @Override // org.jetbrains.kotlin.psi.KtQualifiedExpression
    public KtExpression getSelectorExpression() {
        KtExpression[] childExpressionsByStub;
        KotlinPlaceHolderStub<KtDotQualifiedExpression> kotlinPlaceHolderStub = (KotlinPlaceHolderStub) getStub();
        if (kotlinPlaceHolderStub != null && (childExpressionsByStub = getChildExpressionsByStub(kotlinPlaceHolderStub)) != null && childExpressionsByStub.length == 2) {
            return childExpressionsByStub[1];
        }
        return super.getSelectorExpression();
    }

    private final KtExpression[] getChildExpressionsByStub(KotlinPlaceHolderStub<KtDotQualifiedExpression> stub) {
        if (stub.getParentStubOfType(KtImportDirective.class) == null && stub.getParentStubOfType(KtPackageDirective.class) == null && stub.getParentStubOfType(KtValueArgument.class) == null) {
            LOG.error("KtDotQualifiedExpression should only have stubs inside import, argument or package directives.\nStubs were created for:\n" + getText() + "\nFile text:\n" + getContainingFile().getText());
            return null;
        }
        PsiElement[] childrenByType = stub.getChildrenByType(KtTokenSets.INSIDE_DIRECTIVE_EXPRESSIONS, KtExpression.ARRAY_FACTORY);
        Intrinsics.checkNotNullExpressionValue(childrenByType, "getChildrenByType(...)");
        KtExpression[] ktExpressionArr = (KtExpression[]) childrenByType;
        int length = ktExpressionArr.length;
        if (1 <= length && length < 3) {
            return ktExpressionArr;
        }
        Logger logger = LOG;
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Invalid stub structure. DOT_QUALIFIED_EXPRESSION must have one or two children. Was: " + ktExpressionArr.length, null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        ExceptionAttachementBuilderUtilsKt.withPsiEntry(exceptionAttachmentBuilder, "file", getContainingFile());
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        logger.error((Throwable) kotlinIllegalArgumentExceptionWithAttachments);
        return null;
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) KtDotQualifiedExpression.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
