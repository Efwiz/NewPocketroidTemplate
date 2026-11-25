package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeElement;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: KtLightPsiClassObjectAccessExpression.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightPsiClassObjectAccessExpression;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightPsiLiteral;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassObjectAccessExpression;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "lightParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;Lcom/intellij/psi/PsiElement;)V", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtClassLiteralExpression;", "getOperand", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeElement;", "getType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightPsiClassObjectAccessExpression extends KtLightPsiLiteral implements PsiClassObjectAccessExpression {
    private final KtClassLiteralExpression kotlinOrigin;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightPsiLiteral, org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtClassLiteralExpression getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightPsiClassObjectAccessExpression(KtClassLiteralExpression kotlinOrigin, PsiElement lightParent) {
        super(kotlinOrigin, lightParent);
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        Intrinsics.checkNotNullParameter(lightParent, "lightParent");
        this.kotlinOrigin = kotlinOrigin;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightPsiLiteral, org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        KClassValue.Value value;
        ClassLiteralValue value2;
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        CompileTimeConstant compileTimeConstant = (CompileTimeConstant) companion.getInstance(project).analyze(getKotlinOrigin()).get(BindingContext.COMPILE_TIME_VALUE, getKotlinOrigin());
        if (compileTimeConstant != null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            ConstantValue constantValue = compileTimeConstant.toConstantValue(NO_EXPECTED_TYPE);
            if (constantValue != null) {
                if (!(constantValue instanceof KClassValue)) {
                    constantValue = null;
                }
                KClassValue kClassValue = (KClassValue) constantValue;
                if (kClassValue != null && (value = kClassValue.getValue()) != null) {
                    KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) (value instanceof KClassValue.Value.NormalClass ? value : null);
                    if (normalClass != null && (value2 = normalClass.getValue()) != null) {
                        ClassId classId = value2.getClassId();
                        int arrayNestedness = value2.getArrayNestedness();
                        String strAsString = classId.asSingleFqName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        PsiType psiType = KtLightAnnotationsValuesKt.psiType(strAsString, getKotlinOrigin(), arrayNestedness > 0);
                        for (int i = 0; i < arrayNestedness; i++) {
                            psiType = psiType.createArrayType();
                            Intrinsics.checkNotNullExpressionValue(psiType, "createArrayType(...)");
                        }
                        return psiType;
                    }
                }
            }
        }
        PsiPrimitiveType VOID = PsiType.VOID;
        Intrinsics.checkNotNullExpressionValue(VOID, "VOID");
        return VOID;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiTypeElement getOperand() {
        return new LightTypeElement(getKotlinOrigin().getManager(), getType());
    }
}
