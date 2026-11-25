package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDocParamRef extends CompositePsiElement implements PsiDocTagValue {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocParamRef";
        } else if (i != 2) {
            objArr[0] = "comment";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocParamRef";
        } else {
            objArr[1] = "getAllParameters";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "getAllParameters";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public PsiDocParamRef() {
        super(JavaDocElementType.DOC_PARAMETER_REF);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        final ASTNode valueToken;
        PsiDocComment psiDocComment = (PsiDocComment) PsiTreeUtil.getParentOfType(this, PsiDocComment.class);
        if (psiDocComment == null) {
            return null;
        }
        PsiJavaDocumentedElement owner = psiDocComment.getOwner();
        if ((!(owner instanceof PsiMethod) && !(owner instanceof PsiClass)) || (valueToken = getValueToken()) == null) {
            return null;
        }
        final String text = valueToken.getText();
        final boolean zIsTypeParamRef = isTypeParamRef();
        final PsiElement psiElement = (PsiElement) ContainerUtil.find(getAllParameters(psiDocComment), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocParamRef$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiDocParamRef.lambda$getReference$0(zIsTypeParamRef, text, (PsiNamedElement) obj);
            }
        });
        return new PsiReferenceBase<PsiElement>(this, TextRange.from(valueToken.getPsi().getStartOffsetInParent(), valueToken.getTextLength())) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocParamRef.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1 || i == 2) {
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                } else {
                    objArr[0] = "newElementName";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocParamRef$1";
                if (i == 1) {
                    objArr[2] = "bindToElement";
                } else if (i != 2) {
                    objArr[2] = "handleElementRename";
                } else {
                    objArr[2] = "isReferenceTo";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement resolve() {
                return psiElement;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement handleElementRename(String str) {
                if (str == null) {
                    $$$reportNull$$$0(0);
                }
                PsiDocParamRef.this.replaceChild(valueToken, Factory.createSingleLeafElement(JavaDocTokenType.DOC_TAG_VALUE_TOKEN, str, SharedImplUtil.findCharTableByTree(PsiDocParamRef.this.getNode()), PsiDocParamRef.this.getManager()));
                return PsiDocParamRef.this;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public PsiElement bindToElement(PsiElement psiElement2) throws IncorrectOperationException {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(1);
                }
                if (isReferenceTo(psiElement2)) {
                    return PsiDocParamRef.this;
                }
                if (!(psiElement2 instanceof PsiParameter)) {
                    throw new IncorrectOperationException("Unsupported operation");
                }
                return handleElementRename(((PsiParameter) psiElement2).getName());
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
            public boolean isReferenceTo(PsiElement psiElement2) {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(2);
                }
                if ((psiElement2 instanceof PsiNamedElement) && getCanonicalText().equals(((PsiNamedElement) psiElement2).getName())) {
                    return PsiDocParamRef.this.getManager().areElementsEquivalent(resolve(), psiElement2);
                }
                return false;
            }
        };
    }

    static /* synthetic */ boolean lambda$getReference$0(boolean z, String str, PsiNamedElement psiNamedElement) {
        return z == (psiNamedElement instanceof PsiTypeParameter) && str.equals(psiNamedElement.getName());
    }

    public static List<PsiNamedElement> getAllParameters(PsiDocComment psiDocComment) {
        PsiTypeParameterList psiTypeParameterListMo6093getTypeParameterList;
        if (psiDocComment == null) {
            $$$reportNull$$$0(0);
        }
        ArrayList arrayList = new ArrayList();
        PsiJavaDocumentedElement owner = psiDocComment.getOwner();
        boolean z = owner instanceof PsiMethod;
        if (z) {
            Collections.addAll(arrayList, ((PsiMethod) owner).getParameterList().getParameters());
        }
        if ((z || (owner instanceof PsiClass)) && (psiTypeParameterListMo6093getTypeParameterList = ((PsiTypeParameterListOwner) owner).mo6093getTypeParameterList()) != null) {
            Collections.addAll(arrayList, psiTypeParameterListMo6093getTypeParameterList.getTypeParameters());
        }
        if (owner instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) owner;
            if (psiClass.isRecord()) {
                Collections.addAll(arrayList, psiClass.getRecordComponents());
            }
        }
        return arrayList;
    }

    public boolean isTypeParamRef() {
        return PsiUtilCore.getElementType(getFirstChild()) == JavaDocTokenType.DOC_TAG_VALUE_LT;
    }

    public ASTNode getValueToken() {
        return findChildByType(JavaDocTokenType.DOC_TAG_VALUE_TOKEN);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocTagValue(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }
}
