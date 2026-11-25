package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import io.netty.util.internal.StringUtil;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiParameterStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class ClsParameterImpl extends ClsRepositoryPsiElement<PsiParameterStub> implements PsiParameter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile String myMirrorName;
    private final NotNullLazyValue<PsiTypeElement> myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 4:
            case 8:
            case 9:
            case 12:
            case 13:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
                i2 = 2;
                break;
            case 4:
            case 8:
            case 9:
            case 12:
            case 13:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsParameterImpl";
                break;
            case 4:
            case 8:
                objArr[0] = "name";
                break;
            case 9:
                objArr[0] = "buffer";
                break;
            case 12:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 13:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getName";
                break;
            case 2:
            case 3:
                objArr[1] = "calcName";
                break;
            case 4:
            case 8:
            case 9:
            case 12:
            case 13:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsParameterImpl";
                break;
            case 5:
                objArr[1] = "getTypeElement";
                break;
            case 6:
                objArr[1] = "getType";
                break;
            case 7:
                objArr[1] = "getModifierList";
                break;
            case 10:
                objArr[1] = "getMirrorName";
                break;
            case 11:
                objArr[1] = "calcNiceParameterName";
                break;
            case 14:
                objArr[1] = "getDeclarationScope";
                break;
            case 15:
            case 16:
                objArr[1] = "getNavigationElement";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
                break;
            case 4:
                objArr[2] = "setName";
                break;
            case 8:
                objArr[2] = "hasModifierProperty";
                break;
            case 9:
                objArr[2] = "appendMirrorText";
                break;
            case 12:
                objArr[2] = "setMirror";
                break;
            case 13:
                objArr[2] = "accept";
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
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 14:
            case 15:
            case 16:
                throw new IllegalStateException(str2);
            case 4:
            case 8:
            case 9:
            case 12:
            case 13:
            default:
                throw new IllegalArgumentException(str2);
        }
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsParameterImpl(PsiParameterStub psiParameterStub) {
        super(psiParameterStub);
        if (psiParameterStub == null) {
            $$$reportNull$$$0(0);
        }
        this.myType = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsParameterImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6596x9b358708();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsParameterImpl, reason: not valid java name */
    /* synthetic */ PsiTypeElement m6596x9b358708() {
        return new ClsTypeElementImpl(this, getStub().getType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String str = (String) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsParameterImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6595x6c4c50a6();
            }
        });
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    /* JADX INFO: renamed from: lambda$getName$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsParameterImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6595x6c4c50a6() {
        return CachedValueProvider.Result.create(calcName(), PsiModificationTracker.MODIFICATION_COUNT);
    }

    private String calcName() {
        PsiParameterStubImpl psiParameterStubImpl = (PsiParameterStubImpl) getStub();
        if (!psiParameterStubImpl.isAutoGeneratedName()) {
            String name2 = psiParameterStubImpl.getName();
            if (name2 == null) {
                $$$reportNull$$$0(2);
            }
            return name2;
        }
        PsiMethod sourceMirrorMethod = ((ClsMethodImpl) getMethod()).getSourceMirrorMethod();
        if (sourceMirrorMethod != null) {
            String name3 = sourceMirrorMethod.getParameterList().getParameters()[getIndex()].getName();
            if (name3 == null) {
                $$$reportNull$$$0(3);
            }
            return name3;
        }
        return getMirrorName();
    }

    public boolean isAutoGeneratedName() {
        return ((PsiParameterStubImpl) getStub()).isAutoGeneratedName() && ((ClsMethodImpl) getMethod()).getSourceMirrorMethod() == null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        throw cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement value = this.myType.getValue();
        if (value == null) {
            $$$reportNull$$$0(5);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        PsiType type = getTypeElement().getType();
        if (type == null) {
            $$$reportNull$$$0(6);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        PsiModifierList psiModifierList = (PsiModifierList) getStub().findChildStubByType(JavaStubElementTypes.MODIFIER_LIST).getPsi();
        if (psiModifierList == null) {
            $$$reportNull$$$0(7);
        }
        return psiModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return mo6095getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(9);
        }
        for (PsiAnnotation psiAnnotation : mo6095getModifierList().getAnnotations()) {
            appendText(psiAnnotation, i, sb);
            sb.append(StringUtil.SPACE);
        }
        appendText(getTypeElement(), i, sb, " ");
        sb.append(getMirrorName());
    }

    private String getMirrorName() {
        String strCalcNiceParameterName = this.myMirrorName;
        if (strCalcNiceParameterName == null) {
            synchronized (getLightParent()) {
                strCalcNiceParameterName = this.myMirrorName;
                if (strCalcNiceParameterName == null) {
                    strCalcNiceParameterName = calcNiceParameterName();
                    this.myMirrorName = strCalcNiceParameterName;
                }
            }
        }
        if (strCalcNiceParameterName == null) {
            $$$reportNull$$$0(10);
        }
        return strCalcNiceParameterName;
    }

    private String calcNiceParameterName() {
        PsiParameterStubImpl psiParameterStubImpl = (PsiParameterStubImpl) getStub();
        String name2 = !psiParameterStubImpl.isAutoGeneratedName() ? psiParameterStubImpl.getName() : null;
        if (name2 == null) {
            String strNotNullize = org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.notNullize(JavaCodeStyleManager.getInstance(getProject()).suggestCompiledParameterName(getType()), "p");
            name2 = strNotNullize;
            int i = 0;
            loop0: while (true) {
                for (PsiParameter psiParameter : ((PsiParameterList) getLightParent()).getParameters()) {
                    if (psiParameter == this) {
                        break loop0;
                    }
                    if (name2.equals(((ClsParameterImpl) psiParameter).getMirrorName())) {
                        i++;
                        name2 = strNotNullize + i;
                        break;
                    }
                }
            }
        }
        if (name2 == null) {
            $$$reportNull$$$0(11);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(12);
        }
        setMirrorCheckingType(treeElement, null);
        PsiParameter psiParameter = (PsiParameter) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirror(mo6095getModifierList(), psiParameter.mo6095getModifierList());
        setMirror(getTypeElement(), psiParameter.getTypeElement());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitParameter(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: getDeclarationScope */
    public PsiElement getMethod() {
        PsiElement lightParent = getLightParent().getLightParent();
        if (lightParent == null) {
            $$$reportNull$$$0(14);
        }
        return lightParent;
    }

    private int getIndex() {
        PsiParameterStub stub = getStub();
        return stub.getParentStub().getChildrenStubs().indexOf(stub);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        PsiParameterList psiParameterList = (PsiParameterList) getLightParent();
        return ((PsiMethod) psiParameterList.getLightParent()).isVarArgs() && getIndex() == psiParameterList.getParametersCount() - 1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, PlatformIcons.PARAMETER_ICON, 0));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        return new LocalSearchScope(getMethod());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiMethod psiMethod;
        int iIndexOf;
        PsiMethod psiMethod2;
        PsiParameterList psiParameterList = (PsiParameterList) ObjectUtils.tryCast(getLightParent(), PsiParameterList.class);
        if (psiParameterList != null && (psiMethod = (PsiMethod) ObjectUtils.tryCast(psiParameterList.getLightParent(), PsiMethod.class)) != null && (iIndexOf = ArrayUtil.indexOf(psiParameterList.getParameters(), this)) >= 0 && (psiMethod2 = (PsiMethod) ObjectUtils.tryCast(psiMethod.getNavigationElement(), PsiMethod.class)) != null && psiMethod2 != psiMethod) {
            PsiParameter[] parameters = psiMethod2.getParameterList().getParameters();
            if (parameters.length > iIndexOf) {
                PsiParameter psiParameter = parameters[iIndexOf];
                if (psiParameter == null) {
                    $$$reportNull$$$0(15);
                }
                return psiParameter;
            }
        }
        return this;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiParameter";
    }
}
