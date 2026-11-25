package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.UtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteral;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: KtLightPsiLiteral.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u0006H\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u0015\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000eH\u0016¢\u0006\u0002\u0010\u000fJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightPsiLiteral;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiLiteralExpression;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtExpression;", "lightParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lcom/intellij/psi/PsiElement;)V", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getParent", "getReference", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "getReferences", "", "()[Lcom/intellij/psi/PsiReference;", "getType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "getValue", "", "isPhysical", "", SchemaSymbols.ATTVAL_REPLACE, "newElement", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtLightPsiLiteral extends KtLightElementBase implements PsiLiteralExpression {
    private final KtExpression kotlinOrigin;
    private final PsiElement lightParent;

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtExpression getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightPsiLiteral(KtExpression kotlinOrigin, PsiElement lightParent) {
        super(lightParent);
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        Intrinsics.checkNotNullParameter(lightParent, "lightParent");
        this.kotlinOrigin = kotlinOrigin;
        this.lightParent = lightParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLiteralValue
    public Object getValue() {
        return UtilsKt.computeExpression(this);
    }

    public PsiType getType() {
        ClassifierDescriptor this$0;
        FqName fqNameSafe;
        String strAsString;
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KotlinType kotlinType = (KotlinType) companion.getInstance(project).analyze(getKotlinOrigin()).get(BindingContext.EXPECTED_EXPRESSION_TYPE, getKotlinOrigin());
        if (kotlinType == null || (this$0 = kotlinType.getConstructor().getThis$0()) == null || (fqNameSafe = DescriptorUtilsKt.getFqNameSafe(this$0)) == null || (strAsString = fqNameSafe.asString()) == null) {
            return null;
        }
        return KtLightAnnotationsValuesKt.psiType$default(strAsString, getKotlinOrigin(), false, 4, null);
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent, reason: from getter */
    public PsiElement getMyParent() {
        return this.lightParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement newElement) {
        Intrinsics.checkNotNullParameter(newElement, "newElement");
        PsiLiteral psiLiteral = newElement instanceof PsiLiteral ? (PsiLiteral) newElement : null;
        Object value = psiLiteral != null ? psiLiteral.getValue() : null;
        String str = value instanceof String ? (String) value : null;
        if (str == null) {
            return this;
        }
        KtExpression kotlinOrigin = getKotlinOrigin();
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        kotlinOrigin.replace(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createExpression("\"" + StringUtil.escapeStringCharacters(str) + '\"'));
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        return (PsiReference) ArraysKt.singleOrNull(getReferences());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] references = getKotlinOrigin().getReferences();
        Intrinsics.checkNotNullExpressionValue(references, "getReferences(...)");
        return references;
    }
}
