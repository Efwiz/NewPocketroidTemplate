package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import io.netty.util.internal.StringUtil;
import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordHeader;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordComponentStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public final class ClsRecordComponentImpl extends ClsRepositoryPsiElement<PsiRecordComponentStub> implements PsiRecordComponent {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final NotNullLazyValue<PsiTypeElement> myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4 || i == 5 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4 || i == 5 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsRecordComponentImpl";
                break;
            case 2:
            case 6:
                objArr[0] = "name";
                break;
            case 7:
                objArr[0] = "buffer";
                break;
            case 8:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 9:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i == 3) {
            objArr[1] = "getTypeElement";
        } else if (i == 4) {
            objArr[1] = "getType";
        } else if (i == 5) {
            objArr[1] = "getModifierList";
        } else if (i == 10 || i == 11) {
            objArr[1] = "getNavigationElement";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsRecordComponentImpl";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 10:
            case 11:
                break;
            case 2:
                objArr[2] = "setName";
                break;
            case 6:
                objArr[2] = "hasModifierProperty";
                break;
            case 7:
                objArr[2] = "appendMirrorText";
                break;
            case 8:
                objArr[2] = "setMirror";
                break;
            case 9:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4 && i != 5 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsRecordComponentImpl(PsiRecordComponentStub psiRecordComponentStub) {
        super(psiRecordComponentStub);
        if (psiRecordComponentStub == null) {
            $$$reportNull$$$0(0);
        }
        this.myType = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRecordComponentImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6597x763e424b();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsRecordComponentImpl, reason: not valid java name */
    /* synthetic */ PsiTypeElement m6597x763e424b() {
        return new ClsTypeElementImpl(this, getStub().getType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = getStub().getName();
        if (name2 == null) {
            $$$reportNull$$$0(1);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement value = this.myType.getValue();
        if (value == null) {
            $$$reportNull$$$0(3);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiType getType() {
        PsiType type = getTypeElement().getType();
        if (type == null) {
            $$$reportNull$$$0(4);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        PsiModifierList psiModifierList = (PsiModifierList) getStub().findChildStubByType(JavaStubElementTypes.MODIFIER_LIST).getPsi();
        if (psiModifierList == null) {
            $$$reportNull$$$0(5);
        }
        return psiModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return mo6095getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(7);
        }
        for (PsiAnnotation psiAnnotation : mo6095getModifierList().getAnnotations()) {
            appendText(psiAnnotation, i, sb);
            sb.append(StringUtil.SPACE);
        }
        appendText(getTypeElement(), i, sb, " ");
        sb.append(getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(8);
        }
        setMirrorCheckingType(treeElement, null);
        PsiParameter psiParameter = (PsiParameter) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirror(mo6095getModifierList(), psiParameter.mo6095getModifierList());
        setMirror(getTypeElement(), psiParameter.getTypeElement());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitRecordComponent(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent
    public boolean isVarArgs() {
        return getStub().isVararg();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return new LocalSearchScope(getMyParent());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiClass psiClass;
        PsiRecordComponent[] recordComponents;
        int iIndexOf;
        PsiClass containingClass = getContainingClass();
        if (containingClass != null && (psiClass = (PsiClass) ObjectUtils.tryCast(containingClass.getNavigationElement(), PsiClass.class)) != null && psiClass != containingClass && (iIndexOf = ArrayUtil.indexOf((recordComponents = containingClass.getRecordComponents()), this)) >= 0) {
            PsiRecordComponent[] recordComponents2 = psiClass.getRecordComponents();
            if (recordComponents2.length == recordComponents.length) {
                PsiRecordComponent psiRecordComponent = recordComponents2[iIndexOf];
                if (psiRecordComponent == null) {
                    $$$reportNull$$$0(10);
                }
                return psiRecordComponent;
            }
        }
        return this;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiRecordComponent:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    public PsiClass getContainingClass() {
        PsiElement parent = getMyParent();
        if (parent instanceof PsiRecordHeader) {
            return ((PsiRecordHeader) parent).getContainingClass();
        }
        return null;
    }
}
