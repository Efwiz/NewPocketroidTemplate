package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: KtExpressionImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u00142\u00020\u00012\u00020\u0002:\u0001\u0014B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J=\u0010\u0006\u001a\n \b*\u0004\u0018\u0001H\u0007H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\t0\u000b2\u0006\u0010\f\u001a\u0002H\tH\u0016¢\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtExpressionImpl;", "Lorg/jetbrains/kotlin/psi/KtElementImpl;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "findExpressionUnder", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", SchemaSymbols.ATTVAL_REPLACE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "newElement", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtExpressionImpl extends KtElementImpl implements KtExpression {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtExpressionImpl(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitExpression(this, data);
    }

    protected final KtExpression findExpressionUnder(IElementType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        KtContainerNode ktContainerNode = (KtContainerNode) findChildByType(type);
        if (ktContainerNode == null) {
            return null;
        }
        return (KtExpression) ktContainerNode.findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement newElement) {
        Intrinsics.checkNotNullParameter(newElement, "newElement");
        return Companion.replaceExpression$default(INSTANCE, this, newElement, false, new Function1<PsiElement, PsiElement>() { // from class: org.jetbrains.kotlin.psi.KtExpressionImpl.replace.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                PsiElement psiElementReplace = KtExpressionImpl.super.replace(it);
                Intrinsics.checkNotNullExpressionValue(psiElementReplace, "access$replace$s-395209229(...)");
                return psiElementReplace;
            }
        }, 4, null);
    }

    /* JADX INFO: compiled from: KtExpressionImpl.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtExpressionImpl$Companion;", "", "()V", "replaceExpression", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "newElement", "reformat", "", "rawReplaceHandler", "Lkotlin/Function1;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ PsiElement replaceExpression$default(Companion companion, KtExpression ktExpression, PsiElement psiElement, boolean z, Function1 function1, int i, Object obj) {
            if ((i & 4) != 0) {
                z = true;
            }
            return companion.replaceExpression(ktExpression, psiElement, z, function1);
        }

        public final PsiElement replaceExpression(KtExpression expression, PsiElement newElement, boolean reformat, Function1<? super PsiElement, ? extends PsiElement> rawReplaceHandler) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(newElement, "newElement");
            Intrinsics.checkNotNullParameter(rawReplaceHandler, "rawReplaceHandler");
            PsiElement parent = expression.getLightParent();
            if (newElement instanceof KtExpression) {
                DefaultConstructorMarker defaultConstructorMarker = null;
                int i = 2;
                boolean z = false;
                if (parent instanceof KtExpression ? true : parent instanceof KtValueArgument) {
                    Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
                    if (KtPsiUtil.areParenthesesNecessary((KtExpression) newElement, expression, (KtElement) parent)) {
                        Project project = expression.getProject();
                        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                        return rawReplaceHandler.invoke(CreateByPatternKt.createExpressionByPattern(new KtPsiFactory(project, z, i, defaultConstructorMarker), "($0)", new Object[]{newElement}, reformat));
                    }
                } else if ((parent instanceof KtSimpleNameStringTemplateEntry) && !(newElement instanceof KtSimpleNameExpression) && !KtExpressionImplKt.isThisWithoutLabel(newElement)) {
                    Project project2 = expression.getProject();
                    Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
                    PsiElement psiElementReplace = parent.replace(new KtPsiFactory(project2, z, i, defaultConstructorMarker).createBlockStringTemplateEntry((KtExpression) newElement));
                    Intrinsics.checkNotNull(psiElementReplace, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtBlockStringTemplateEntry");
                    KtExpression expression2 = ((KtBlockStringTemplateEntry) psiElementReplace).getExpression();
                    Intrinsics.checkNotNull(expression2);
                    return expression2;
                }
            }
            return rawReplaceHandler.invoke(newElement);
        }
    }
}
