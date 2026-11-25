package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiConstantEvaluationHelperImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class ClsFieldImpl extends ClsMemberImpl<PsiFieldStub> implements PsiField, PsiVariableEx, ClsModifierListOwner {
    private final NullableLazyValue<PsiExpression> myInitializer;
    private final NotNullLazyValue<PsiTypeElement> myTypeElement;

    /* JADX WARN: Code duplicated, block: B:7:0x000c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 2) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 2) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsFieldImpl";
                break;
            case 3:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "buffer";
                break;
            case 5:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 6:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 1) {
            objArr[1] = "getChildren";
        } else if (i != 2) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                    objArr[1] = "getNavigationElement";
                    break;
                case 10:
                    objArr[1] = "getUseScope";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsFieldImpl";
                    break;
            }
        } else {
            objArr[1] = "getType";
        }
        switch (i) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 9:
            case 10:
                break;
            case 3:
                objArr[2] = "hasModifierProperty";
                break;
            case 4:
                objArr[2] = "appendMirrorText";
                break;
            case 5:
                objArr[2] = "setMirror";
                break;
            case 6:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ Object setName(String str) {
        return super.setName(str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsFieldImpl(PsiFieldStub psiFieldStub) {
        super(psiFieldStub);
        if (psiFieldStub == null) {
            $$$reportNull$$$0(0);
        }
        this.myTypeElement = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6586x4400b59();
            }
        });
        this.myInitializer = new VolatileNullableLazyValue<PsiExpression>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFieldImpl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.VolatileNullableLazyValue, org.jetbrains.kotlin.com.intellij.openapi.util.NullableLazyValue
            public PsiExpression compute() {
                String initializerText = ((PsiFieldStub) ClsFieldImpl.this.getStub()).getInitializerText();
                if (initializerText == null || Objects.equals(PsiFieldStub.INITIALIZER_TOO_LONG, initializerText)) {
                    return null;
                }
                return ClsParsingUtil.createExpressionFromText(initializerText, ClsFieldImpl.this.getManager(), ClsFieldImpl.this);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsFieldImpl, reason: not valid java name */
    /* synthetic */ PsiTypeElement m6586x4400b59() {
        return new ClsTypeElementImpl(this, ((PsiFieldStub) getStub()).getType());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRepositoryPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] children = getChildren(mo6091getDocComment(), mo6095getModifierList(), getTypeElement(), mo7392getNameIdentifier());
        if (children == null) {
            $$$reportNull$$$0(1);
        }
        return children;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return (PsiClass) getMyParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        PsiType type = ((PsiTypeElement) Objects.requireNonNull(getTypeElement())).getType();
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        return type;
    }

    public PsiTypeElement getTypeElement() {
        return this.myTypeElement.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return ((PsiModifierListStub) Objects.requireNonNull((PsiModifierListStub) ((PsiFieldStub) getStub()).findChildStubByType(JavaStubElementTypes.MODIFIER_LIST))).getPsi();
    }

    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return ((PsiModifierList) Objects.requireNonNull(mo6095getModifierList())).hasModifierProperty(str);
    }

    public PsiExpression getInitializer() {
        return this.myInitializer.getValue();
    }

    public boolean hasInitializer() {
        return getInitializer() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return computeConstantValue(new HashSet());
    }

    public Object computeConstantValue(Set<PsiVariable> set) {
        PsiExpression initializer;
        if (!hasModifierProperty("final") || (initializer = getInitializer()) == null) {
            return null;
        }
        PsiClass psiClassMo6092getContainingClass = mo6092getContainingClass();
        if (psiClassMo6092getContainingClass != null) {
            String qualifiedName = psiClassMo6092getContainingClass.getQualifiedName();
            if (CommonClassNames.JAVA_LANG_FLOAT.equals(qualifiedName)) {
                String name2 = getMyName();
                if ("POSITIVE_INFINITY".equals(name2)) {
                    return Float.valueOf(Float.POSITIVE_INFINITY);
                }
                if ("NEGATIVE_INFINITY".equals(name2)) {
                    return Float.valueOf(Float.NEGATIVE_INFINITY);
                }
                if ("NaN".equals(name2)) {
                    return Float.valueOf(Float.NaN);
                }
            } else if (CommonClassNames.JAVA_LANG_DOUBLE.equals(qualifiedName)) {
                String name3 = getMyName();
                if ("POSITIVE_INFINITY".equals(name3)) {
                    return Double.valueOf(Double.POSITIVE_INFINITY);
                }
                if ("NEGATIVE_INFINITY".equals(name3)) {
                    return Double.valueOf(Double.NEGATIVE_INFINITY);
                }
                if ("NaN".equals(name3)) {
                    return Double.valueOf(Double.NaN);
                }
            }
        }
        return PsiConstantEvaluationHelperImpl.computeCastTo(initializer, getType(), set);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return ((PsiFieldStub) getStub()).isDeprecated() || PsiImplUtil.isDeprecatedByAnnotation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(4);
        }
        appendText(mo6091getDocComment(), i, sb, "go_to_next_line_and_indent");
        appendText(mo6095getModifierList(), i, sb, "");
        appendText(getTypeElement(), i, sb, " ");
        appendText(mo7392getNameIdentifier(), i, sb);
        PsiExpression initializer = getInitializer();
        if (initializer != null) {
            sb.append(" = ");
            sb.append(initializer.getText());
        }
        sb.append(';');
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(5);
        }
        setMirrorCheckingType(treeElement, null);
        PsiField psiField = (PsiField) SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        setMirrorIfPresent(mo6091getDocComment(), psiField.mo6091getDocComment());
        setMirror(mo6095getModifierList(), psiField.mo6095getModifierList());
        setMirror(getTypeElement(), psiField.getTypeElement());
        setMirror(mo7392getNameIdentifier(), psiField.mo7392getNameIdentifier());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitField(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiField psiFieldFindFieldByName;
        Iterator<ClsCustomNavigationPolicy> it = ClsCustomNavigationPolicy.EP_NAME.getExtensionList().iterator();
        while (it.hasNext()) {
            try {
                PsiElement navigationElement = it.next().getNavigationElement(this);
                if (navigationElement != null) {
                    if (navigationElement == null) {
                        $$$reportNull$$$0(7);
                    }
                    return navigationElement;
                }
            } catch (IndexNotReadyException unused) {
            }
        }
        try {
            PsiClass sourceMirrorClass = ((ClsClassImpl) getMyParent()).getSourceMirrorClass();
            if (sourceMirrorClass != null && (psiFieldFindFieldByName = sourceMirrorClass.findFieldByName(getMyName(), false)) != null) {
                PsiElement navigationElement2 = psiFieldFindFieldByName.getNavigationElement();
                if (navigationElement2 == null) {
                    $$$reportNull$$$0(8);
                }
                return navigationElement2;
            }
        } catch (IndexNotReadyException unused2) {
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, PlatformIcons.FIELD_ICON, ElementPresentationUtil.getFlags(this, false)));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isFieldEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope memberUseScope = PsiImplUtil.getMemberUseScope(this);
        if (memberUseScope == null) {
            $$$reportNull$$$0(10);
        }
        return memberUseScope;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiField:" + getMyName();
    }
}
