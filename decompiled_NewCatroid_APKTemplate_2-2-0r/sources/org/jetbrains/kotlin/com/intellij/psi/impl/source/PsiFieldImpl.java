package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.lang.ref.Reference;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiSyntheticClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiConstantEvaluationHelperImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stub.JavaStubImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class PsiFieldImpl extends JavaStubPsiElement<PsiFieldStub> implements Queryable, PsiField, PsiVariableEx {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile Reference<PsiType> myCachedType;

    /* JADX WARN: Code duplicated, block: B:13:0x0019  */
    /* JADX WARN: Code duplicated, block: B:25:0x0047  */
    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 4 && i != 16) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 4 && i != 16) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 4) {
            objArr[0] = "name";
        } else if (i != 16) {
            switch (i) {
                case 9:
                    objArr[0] = "name";
                    break;
                case 10:
                    objArr[0] = "variable";
                    break;
                case 11:
                    objArr[0] = "visitor";
                    break;
                case 12:
                    objArr[0] = "processor";
                    break;
                case 13:
                    objArr[0] = "state";
                    break;
                case 14:
                    objArr[0] = "place";
                    break;
                default:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiFieldImpl";
                    break;
            }
        } else {
            objArr[0] = CompilerOptions.INFO;
        }
        switch (i) {
            case 1:
                objArr[1] = "getNameIdentifier";
                break;
            case 2:
            case 3:
                objArr[1] = "getName";
                break;
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiFieldImpl";
                break;
            case 5:
            case 6:
                objArr[1] = "getType";
                break;
            case 7:
            case 8:
                objArr[1] = "getModifierList";
                break;
            case 15:
                objArr[1] = "getUseScope";
                break;
            default:
                objArr[1] = "getNode";
                break;
        }
        if (i == 4) {
            objArr[2] = "setName";
        } else if (i != 16) {
            switch (i) {
                case 9:
                    objArr[2] = "hasModifierProperty";
                    break;
                case 10:
                    objArr[2] = "getDetachedInitializer";
                    break;
                case 11:
                    objArr[2] = "accept";
                    break;
                case 12:
                case 13:
                case 14:
                    objArr[2] = "processDeclarations";
                    break;
            }
        } else {
            objArr[2] = "putInfo";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 16) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalStateException(str2);
            }
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public PsiFieldImpl(PsiFieldStub psiFieldStub) {
        this(psiFieldStub, JavaStubElementTypes.FIELD);
    }

    protected PsiFieldImpl(PsiFieldStub psiFieldStub, IStubElementType iStubElementType) {
        super(psiFieldStub, iStubElementType);
    }

    public PsiFieldImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        dropCached();
    }

    private void dropCached() {
        this.myCachedType = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        PsiFieldImpl psiFieldImpl = (PsiFieldImpl) super.clone();
        psiFieldImpl.dropCached();
        return psiFieldImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        PsiElement parent = getMyParent();
        if (!(parent instanceof PsiClass)) {
            parent = PsiTreeUtil.getParentOfType(this, (Class<PsiElement>) PsiSyntheticClass.class);
        }
        return (PsiClass) parent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        PsiClass psiClassMo6092getContainingClass = mo6092getContainingClass();
        return psiClassMo6092getContainingClass != null ? psiClassMo6092getContainingClass : super.getContext();
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public CompositeElement getNode() {
        CompositeElement compositeElement = (CompositeElement) super.getNode();
        if (compositeElement == null) {
            $$$reportNull$$$0(0);
        }
        return compositeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifier = (PsiIdentifier) getNode().findChildByRoleAsPsiElement(9);
        if (psiIdentifier == null) {
            $$$reportNull$$$0(1);
        }
        return psiIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        PsiFieldStub psiFieldStub = (PsiFieldStub) getGreenStub();
        if (psiFieldStub != null) {
            String name2 = psiFieldStub.getName();
            if (name2 == null) {
                $$$reportNull$$$0(2);
            }
            return name2;
        }
        String text = mo7392getNameIdentifier().getText();
        if (text == null) {
            $$$reportNull$$$0(3);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        PsiImplUtil.setName(mo7392getNameIdentifier(), str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        PsiFieldStub psiFieldStub = (PsiFieldStub) getStub();
        if (psiFieldStub != null) {
            PsiType psiTypeCreateTypeFromStub = (PsiType) SoftReference.dereference(this.myCachedType);
            if (psiTypeCreateTypeFromStub == null) {
                psiTypeCreateTypeFromStub = JavaSharedImplUtil.createTypeFromStub(this, psiFieldStub.getType());
                this.myCachedType = new SoftReference(psiTypeCreateTypeFromStub);
            }
            if (psiTypeCreateTypeFromStub == null) {
                $$$reportNull$$$0(5);
            }
            return psiTypeCreateTypeFromStub;
        }
        this.myCachedType = null;
        PsiType type = JavaSharedImplUtil.getType(getTypeElement(), mo7392getNameIdentifier());
        if (type == null) {
            $$$reportNull$$$0(6);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiField psiFieldFindFirstFieldInDeclaration = findFirstFieldInDeclaration();
        if (psiFieldFindFirstFieldInDeclaration != this) {
            return psiFieldFindFirstFieldInDeclaration.getTypeElement();
        }
        return (PsiTypeElement) getNode().findChildByRoleAsPsiElement(10);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        PsiModifierList selfModifierList = getSelfModifierList();
        if (selfModifierList != null) {
            if (selfModifierList == null) {
                $$$reportNull$$$0(7);
            }
            return selfModifierList;
        }
        PsiField psiFieldFindFirstFieldInDeclaration = findFirstFieldInDeclaration();
        if (psiFieldFindFirstFieldInDeclaration == this) {
            if (!isValid()) {
                throw new PsiInvalidElementAccessException(this);
            }
            psiFieldFindFirstFieldInDeclaration = findFirstFieldByTree();
            if (psiFieldFindFirstFieldInDeclaration == this) {
                throw new IllegalStateException("Missing modifier list for sequence of fields: '" + getText() + FormatHelper.QUOTE);
            }
        }
        PsiModifierList modifierList = psiFieldFindFirstFieldInDeclaration.mo6095getModifierList();
        if (modifierList == null) {
            $$$reportNull$$$0(8);
        }
        return modifierList;
    }

    private PsiModifierList getSelfModifierList() {
        return (PsiModifierList) getStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return mo6095getModifierList().hasModifierProperty(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private PsiField findFirstFieldInDeclaration() {
        if (getSelfModifierList() != null) {
            return this;
        }
        PsiFieldStub psiFieldStub = (PsiFieldStub) getGreenStub();
        if (psiFieldStub != null) {
            List<StubElement> childrenStubs = psiFieldStub.getParentStub().getChildrenStubs();
            for (int iIndexOf = childrenStubs.indexOf(psiFieldStub) - 1; iIndexOf >= 0 && (childrenStubs.get(iIndexOf) instanceof PsiFieldStub); iIndexOf--) {
                PsiFieldImpl psiFieldImpl = (PsiFieldImpl) ((PsiFieldStub) childrenStubs.get(iIndexOf)).getPsi();
                if (psiFieldImpl.getSelfModifierList() != null) {
                    return psiFieldImpl;
                }
            }
        }
        return findFirstFieldByTree();
    }

    private PsiField findFirstFieldByTree() {
        CompositeElement node = getNode();
        if (node.findChildByRole(8) != null) {
            return this;
        }
        ASTNode treePrev = node.getTreePrev();
        while (treePrev != null && treePrev.getElementType() != JavaElementType.FIELD) {
            treePrev = treePrev.getTreePrev();
        }
        return treePrev == null ? this : ((PsiFieldImpl) SourceTreeToPsiMap.treeElementToPsi(treePrev)).findFirstFieldInDeclaration();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return (PsiExpression) getNode().findChildByRoleAsPsiElement(21);
    }

    public static PsiExpression getDetachedInitializer(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(10);
        }
        return psiVariable instanceof PsiFieldImpl ? ((PsiFieldImpl) psiVariable).getDetachedInitializer() : psiVariable.getInitializer();
    }

    private PsiExpression getDetachedInitializer() {
        PsiFieldStub psiFieldStub = (PsiFieldStub) getGreenStub();
        if (psiFieldStub == null) {
            return getInitializer();
        }
        String initializerText = psiFieldStub.getInitializerText();
        if (StringUtil.isEmpty(initializerText)) {
            return null;
        }
        if (PsiFieldStub.INITIALIZER_NOT_STORED.equals(initializerText) || PsiFieldStub.INITIALIZER_TOO_LONG.equals(initializerText)) {
            return getInitializer();
        }
        PsiExpression psiExpressionCreateExpressionFromText = JavaPsiFacade.getInstance(getProject()).getParserFacade().createExpressionFromText(initializerText, this);
        ((LightVirtualFile) psiExpressionCreateExpressionFromText.getContainingFile().getViewProvider().getVirtualFile()).setWritable(false);
        return psiExpressionCreateExpressionFromText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        PsiFieldStub psiFieldStub = (PsiFieldStub) getGreenStub();
        if (psiFieldStub != null) {
            return psiFieldStub.getInitializerText() != null;
        }
        return getInitializer() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, PlatformIcons.FIELD_ICON, ElementPresentationUtil.getFlags(this, false)));
    }

    private static class OurConstValueComputer implements JavaResolveCache.ConstValueComputer {
        private static final OurConstValueComputer INSTANCE = new OurConstValueComputer();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "variable", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiFieldImpl$OurConstValueComputer", "execute"));
        }

        private OurConstValueComputer() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache.ConstValueComputer
        public Object execute(PsiVariable psiVariable, Set<PsiVariable> set) {
            if (psiVariable == null) {
                $$$reportNull$$$0(0);
            }
            return ((PsiFieldImpl) psiVariable)._computeConstantValue(set);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object _computeConstantValue(Set<PsiVariable> set) {
        PsiExpression detachedInitializer;
        PsiType type = getType();
        if (((type instanceof PsiPrimitiveType) || type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) && (detachedInitializer = getDetachedInitializer()) != null) {
            return PsiConstantEvaluationHelperImpl.computeCastTo(detachedInitializer, type, set);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return computeConstantValue(new HashSet(2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx
    public Object computeConstantValue(Set<PsiVariable> set) {
        if (hasModifierProperty("final")) {
            return JavaResolveCache.getInstance(getProject()).computeConstantValueWithCaching(this, OurConstValueComputer.INSTANCE, set);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return JavaStubImplUtil.isMemberDeprecated(this, (PsiMemberStub) getGreenStub());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        PsiFieldStub psiFieldStub = (PsiFieldStub) getGreenStub();
        if (psiFieldStub != null && !psiFieldStub.hasDocComment()) {
            return null;
        }
        CompositeElement node = getNode();
        if (getTypeElement() != null) {
            PsiElement psiElementFindChildByRoleAsPsiElement = node.findChildByRoleAsPsiElement(7);
            if (psiElementFindChildByRoleAsPsiElement instanceof PsiDocComment) {
                return (PsiDocComment) psiElementFindChildByRoleAsPsiElement;
            }
            return null;
        }
        ASTNode treePrev = node.getTreePrev();
        while (treePrev != null && treePrev.getElementType() != JavaElementType.FIELD) {
            treePrev = treePrev.getTreePrev();
        }
        if (treePrev == null) {
            return null;
        }
        return ((PsiField) SourceTreeToPsiMap.treeElementToPsi(treePrev)).mo6091getDocComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
        ASTNode aSTNodeSkipWhitespaceAndComments;
        CheckUtil.checkWritable(this);
        PsiTypeElement typeElement = getTypeElement();
        PsiModifierList psiModifierListMo6095getModifierList = mo6095getModifierList();
        ASTNode aSTNodePsiElementToTree = SourceTreeToPsiMap.psiElementToTree(typeElement.getMyParent());
        while (true) {
            ASTNode aSTNodeSkipWhitespaceAndComments2 = PsiImplUtil.skipWhitespaceAndComments(aSTNodePsiElementToTree.getTreeNext());
            if (aSTNodeSkipWhitespaceAndComments2 == null || aSTNodeSkipWhitespaceAndComments2.getElementType() != JavaTokenType.COMMA || (aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(aSTNodeSkipWhitespaceAndComments2.getTreeNext())) == null || aSTNodeSkipWhitespaceAndComments.getElementType() != JavaElementType.FIELD) {
                break;
            }
            CodeEditUtil.addChild(aSTNodePsiElementToTree, Factory.createSingleLeafElement(JavaTokenType.SEMICOLON, ";", 0, 1, null, getManager()), null);
            CodeEditUtil.removeChild(aSTNodeSkipWhitespaceAndComments2.getTreeParent(), aSTNodeSkipWhitespaceAndComments2);
            CodeEditUtil.addChild(aSTNodeSkipWhitespaceAndComments, SourceTreeToPsiMap.psiElementToTree(typeElement.copy()), aSTNodeSkipWhitespaceAndComments.getFirstChildNode());
            CodeEditUtil.addChild(aSTNodeSkipWhitespaceAndComments, SourceTreeToPsiMap.psiElementToTree(psiModifierListMo6095getModifierList.copy()), aSTNodeSkipWhitespaceAndComments.getFirstChildNode());
            aSTNodePsiElementToTree = aSTNodeSkipWhitespaceAndComments;
        }
        JavaSharedImplUtil.normalizeBrackets(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(11);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitField(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(12);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(14);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        return true;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiField:" + getMyName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getOriginalElement() {
        PsiField psiFieldFindFieldByName;
        PsiClass psiClassMo6092getContainingClass = mo6092getContainingClass();
        return (psiClassMo6092getContainingClass == null || (psiFieldFindFieldByName = ((PsiClass) psiClassMo6092getContainingClass.getOriginalElement()).findFieldByName(getMyName(), false)) == null) ? this : psiFieldFindFieldByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        JavaSharedImplUtil.setInitializer(this, psiExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isFieldEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope memberUseScope = PsiImplUtil.getMemberUseScope(this);
        if (memberUseScope == null) {
            $$$reportNull$$$0(15);
        }
        return memberUseScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(16);
        }
        map.put("fieldName", getMyName());
    }
}
