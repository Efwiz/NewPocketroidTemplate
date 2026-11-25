package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Couple;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiCodeBlockImpl extends LazyParseablePsiElement implements PsiCodeBlock {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiCodeBlockImpl.class);
    private volatile Set<String> myClassesSet;
    private volatile boolean myConflict;
    private volatile Set<String> myVariablesSet;

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
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
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
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "child";
                break;
            case 2:
                objArr[0] = "visitor";
                break;
            case 3:
            case 6:
                objArr[0] = "processor";
                break;
            case 4:
            case 7:
                objArr[0] = "state";
                break;
            case 5:
            case 8:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCodeBlockImpl";
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
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCodeBlockImpl";
                break;
            default:
                objArr[1] = "getStatements";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getChildRole";
                break;
            case 2:
                objArr[2] = "accept";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "processDeclarations";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "walkChildren";
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
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public PsiCodeBlockImpl(CharSequence charSequence) {
        super(JavaElementType.CODE_BLOCK, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myVariablesSet = null;
        this.myClassesSet = null;
        this.myConflict = false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock
    public PsiStatement[] getStatements() {
        PsiStatement[] childStatements = PsiImplUtil.getChildStatements(this);
        if (childStatements == null) {
            $$$reportNull$$$0(0);
        }
        return childStatements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock
    public int getStatementCount() {
        ApplicationManager.getApplication().assertReadAccessAllowed();
        int i = 0;
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getPsi() instanceof PsiStatement) {
                i++;
            }
        }
        return i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock
    public boolean isEmpty() {
        ApplicationManager.getApplication().assertReadAccessAllowed();
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getPsi() instanceof PsiStatement) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock
    public PsiElement getFirstBodyElement() {
        PsiElement nextSibling;
        PsiJavaToken lBrace = getLBrace();
        if (lBrace == null || (nextSibling = lBrace.getNextSibling()) == getRBrace()) {
            return null;
        }
        return nextSibling;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock
    public PsiElement getLastBodyElement() {
        PsiJavaToken rBrace = getRBrace();
        if (rBrace != null) {
            PsiElement prevSibling = rBrace.getPrevSibling();
            if (prevSibling == getLBrace()) {
                return null;
            }
            return prevSibling;
        }
        return getLastChild();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock
    public PsiJavaToken getLBrace() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(18);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock
    public PsiJavaToken getRBrace() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(19);
    }

    private Couple<Set<String>> buildMaps() {
        Set<String> set = this.myClassesSet;
        Set<String> set2 = this.myVariablesSet;
        boolean zBooleanValue = this.myConflict;
        if (set == null || set2 == null) {
            final Set<String> hashSet = new HashSet<>();
            final Set<String> hashSet2 = new HashSet<>();
            final Ref ref = new Ref(Boolean.FALSE);
            PsiScopesUtil.walkChildrenScopes(this, new PsiScopeProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCodeBlockImpl.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    } else {
                        objArr[0] = "state";
                    }
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCodeBlockImpl$1";
                    objArr[2] = "execute";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
                public boolean execute(PsiElement psiElement, ResolveState resolveState) {
                    if (psiElement == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (resolveState == null) {
                        $$$reportNull$$$0(1);
                    }
                    if ((psiElement instanceof PsiLocalVariable) || (psiElement instanceof PsiPatternVariable)) {
                        if (!hashSet.add(((PsiVariable) psiElement).get$name())) {
                            ref.set(Boolean.TRUE);
                            hashSet.clear();
                            hashSet2.clear();
                        }
                    } else if (psiElement instanceof PsiClass) {
                        if (!hashSet2.add(((PsiClass) psiElement).get$name())) {
                            ref.set(Boolean.TRUE);
                            hashSet.clear();
                            hashSet2.clear();
                        }
                    }
                    return !((Boolean) ref.get()).booleanValue();
                }
            }, PatternResolveState.WHEN_BOTH.putInto(ResolveState.initial()), this, this);
            if (hashSet2.isEmpty()) {
                hashSet2 = Collections.emptySet();
            }
            this.myClassesSet = hashSet2;
            if (hashSet.isEmpty()) {
                hashSet = Collections.emptySet();
            }
            this.myVariablesSet = hashSet;
            zBooleanValue = ((Boolean) ref.get()).booleanValue();
            this.myConflict = zBooleanValue;
            Set<String> set3 = hashSet2;
            set2 = hashSet;
            set = set3;
        }
        if (zBooleanValue) {
            return null;
        }
        return Couple.of(set, set2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        if (aSTNode2 == null) {
            if (bool == null || bool.booleanValue()) {
                aSTNode2 = findChildByRole(19);
                bool = Boolean.TRUE;
            } else {
                aSTNode2 = findChildByRole(18);
                bool = Boolean.FALSE;
            }
        }
        if (bool == Boolean.TRUE) {
            while (aSTNode2 != null && isNonJavaStatement(aSTNode2)) {
                aSTNode2 = aSTNode2.getTreePrev();
                bool = Boolean.FALSE;
            }
        } else if (bool == Boolean.FALSE) {
            while (aSTNode2 != null && isNonJavaStatement(aSTNode2)) {
                aSTNode2 = aSTNode2.getTreeNext();
                bool = Boolean.TRUE;
            }
        }
        return super.addInternal(treeElement, aSTNode, aSTNode2, bool);
    }

    private static boolean isNonJavaStatement(ASTNode aSTNode) {
        PsiElement psi = aSTNode.getPsi();
        return (psi instanceof PsiStatement) && psi.getLanguage() != JavaLanguage.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 18) {
            return findChildByType(JavaTokenType.LBRACE);
        }
        if (i != 19) {
            return null;
        }
        return TreeUtil.findChildBackward(this, JavaTokenType.RBRACE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.LBRACE) {
            return getChildRole(aSTNode, 18);
        }
        if (elementType == JavaTokenType.RBRACE) {
            return getChildRole(aSTNode, 19);
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitCodeBlock(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiCodeBlock";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        if (psiElement == null) {
            return true;
        }
        Couple<Set<String>> coupleBuildMaps = buildMaps();
        boolean z = coupleBuildMaps == null;
        Set<String> first = z ? null : coupleBuildMaps.getFirst();
        Set set = z ? null : (Set) coupleBuildMaps.getSecond();
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        if (nameHint != null && !z) {
            ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
            String name2 = nameHint.getName(resolveState);
            if ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) && first.contains(name2)) {
                return walkChildren(psiScopeProcessor, resolveState, psiElement, psiElement2);
            }
            if ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) && set.contains(name2)) {
                return walkChildren(psiScopeProcessor, resolveState, psiElement, psiElement2);
            }
            return true;
        }
        return walkChildren(psiScopeProcessor, resolveState, psiElement, psiElement2);
    }

    private boolean walkChildren(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        PsiElement prevSibling;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(6);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(8);
        }
        if (!(getLightParent() instanceof PsiSwitchBlock)) {
            return PsiScopesUtil.walkChildrenScopes(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
        }
        if (psiElement == null || psiElement.getLightParent() != this) {
            prevSibling = null;
        } else {
            prevSibling = psiElement.getPrevSibling();
            if (prevSibling == null) {
                return true;
            }
        }
        if (prevSibling == null) {
            prevSibling = getLastChild();
        }
        while (prevSibling != null) {
            if (prevSibling instanceof PsiSwitchLabelStatementBase) {
                resolveState = PatternResolveState.WHEN_NONE.putInto(resolveState);
            }
            if (!prevSibling.processDeclarations(psiScopeProcessor, resolveState, null, psiElement2)) {
                return false;
            }
            prevSibling = prevSibling.getPrevSibling();
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock
    public boolean shouldChangeModificationCount(PsiElement psiElement) {
        PsiElement parent = getLightParent();
        return ((parent instanceof PsiMethod) || (parent instanceof PsiClassInitializer)) ? false : true;
    }
}
