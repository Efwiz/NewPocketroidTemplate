package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJShellHolderMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJShellHolderMethodImpl extends ASTWrapperPsiElement implements PsiJShellHolderMethod {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiJShellHolderMethodImpl.class);
    private final String myName;
    private PsiParameterList myParameterList;
    private PsiReferenceList myThrowsList;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 9:
            case 16:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
                i2 = 2;
                break;
            case 9:
            case 16:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJShellHolderMethodImpl";
                break;
            case 9:
                objArr[0] = "substitutor";
                break;
            case 16:
            case 19:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getStatements";
                break;
            case 2:
                objArr[1] = "getName";
                break;
            case 3:
            case 4:
            case 5:
                objArr[1] = "getParameterList";
                break;
            case 6:
            case 7:
            case 8:
                objArr[1] = "getThrowsList";
                break;
            case 9:
            case 16:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJShellHolderMethodImpl";
                break;
            case 10:
                objArr[1] = "getSignature";
                break;
            case 11:
            case 12:
            case 13:
                objArr[1] = "findSuperMethods";
                break;
            case 14:
                objArr[1] = "findSuperMethodSignaturesIncludingStatic";
                break;
            case 15:
                objArr[1] = "findDeepestSuperMethods";
                break;
            case 17:
                objArr[1] = "getHierarchicalMethodSignature";
                break;
            case 18:
                objArr[1] = "getTypeParameters";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
                break;
            case 9:
                objArr[2] = "getSignature";
                break;
            case 16:
                objArr[2] = "setName";
                break;
            case 19:
                objArr[2] = "hasModifierProperty";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
                throw new IllegalStateException(str2);
            case 9:
            case 16:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod findDeepestSuperMethod() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiTypeElement getReturnTypeElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isConstructor() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public boolean isVarArgs() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiJShellHolderMethodImpl(ASTNode aSTNode, int i) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.myName = "_$$jshell_holder_method$$" + i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJShellHolderMethod
    public PsiElement[] getStatements() {
        SmartList smartList = null;
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if ((firstChild instanceof PsiStatement) || (firstChild instanceof PsiExpression)) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(firstChild);
            }
        }
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (smartList != null) {
            psiElementArr = (PsiElement[]) smartList.toArray(psiElementArr);
        }
        if (psiElementArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiType getReturnType() {
        return PsiType.VOID;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiParameterList getParameterList() {
        PsiParameterList psiParameterList = this.myParameterList;
        if (psiParameterList != null) {
            if (psiParameterList == null) {
                $$$reportNull$$$0(3);
            }
            return psiParameterList;
        }
        try {
            PsiParameterList psiParameterListCreateParameterList = JavaPsiFacade.getElementFactory(getProject()).createParameterList(ArrayUtilRt.EMPTY_STRING_ARRAY, PsiType.EMPTY_ARRAY);
            this.myParameterList = psiParameterListCreateParameterList;
            if (psiParameterListCreateParameterList == null) {
                $$$reportNull$$$0(4);
            }
            return psiParameterListCreateParameterList;
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
            $$$reportNull$$$0(5);
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiReferenceList getThrowsList() {
        PsiReferenceList psiReferenceList = this.myThrowsList;
        if (psiReferenceList != null) {
            if (psiReferenceList == null) {
                $$$reportNull$$$0(6);
            }
            return psiReferenceList;
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
        try {
            PsiReferenceList psiReferenceListCreateReferenceList = elementFactory.createReferenceList(new PsiJavaCodeReferenceElement[]{elementFactory.createFQClassNameReferenceElement(CommonClassNames.JAVA_LANG_THROWABLE, getResolveScope())});
            this.myThrowsList = psiReferenceListCreateReferenceList;
            if (psiReferenceListCreateReferenceList == null) {
                $$$reportNull$$$0(7);
            }
            return psiReferenceListCreateReferenceList;
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
            $$$reportNull$$$0(8);
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    public PsiCodeBlock getBody() {
        PsiElement firstChild = getFirstChild();
        if (firstChild instanceof PsiCodeBlock) {
            return (PsiCodeBlock) firstChild;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public MethodSignature getSignature(PsiSubstitutor psiSubstitutor) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(9);
        }
        MethodSignatureBackedByPsiMethod methodSignatureBackedByPsiMethodCreate = MethodSignatureBackedByPsiMethod.create(this, PsiSubstitutor.EMPTY);
        if (methodSignatureBackedByPsiMethodCreate == null) {
            $$$reportNull$$$0(10);
        }
        return methodSignatureBackedByPsiMethodCreate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods() {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(11);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(boolean z) {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(12);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findSuperMethods(PsiClass psiClass) {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(13);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean z) {
        List<MethodSignatureBackedByPsiMethod> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(14);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public PsiMethod[] findDeepestSuperMethods() {
        PsiMethod[] psiMethodArr = PsiMethod.EMPTY_ARRAY;
        if (psiMethodArr == null) {
            $$$reportNull$$$0(15);
        }
        return psiMethodArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return new LightModifierList(getManager());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        throw new IncorrectOperationException("Can't change name of JShell holder method");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMethod
    public HierarchicalMethodSignature getHierarchicalMethodSignature() {
        HierarchicalMethodSignature hierarchicalMethodSignature = PsiSuperMethodImplUtil.getHierarchicalMethodSignature(this);
        if (hierarchicalMethodSignature == null) {
            $$$reportNull$$$0(17);
        }
        return hierarchicalMethodSignature;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] psiTypeParameterArr = PsiTypeParameter.EMPTY_ARRAY;
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(18);
        }
        return psiTypeParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return (PsiClass) getThis$0();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        return "public".equals(str) || "static".equals(str) || "final".equals(str);
    }
}
