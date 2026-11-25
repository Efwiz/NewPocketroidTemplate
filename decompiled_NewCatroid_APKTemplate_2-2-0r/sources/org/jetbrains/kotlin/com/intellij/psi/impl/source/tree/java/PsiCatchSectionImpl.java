package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.NullableFunction;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiCatchSectionImpl extends CompositePsiElement implements PsiCatchSection, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiCatchSectionImpl.class);
    private CachedValue<List<PsiType>> myTypesCache;
    private final Object myTypesCacheLock;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 3:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 3;
                break;
            case 3:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = "statement";
                break;
            case 3:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCatchSectionImpl";
                break;
            case 4:
                objArr[0] = "visitor";
                break;
            case 5:
                objArr[0] = "child";
                break;
            case 6:
                objArr[0] = "processor";
                break;
            case 7:
                objArr[0] = "state";
                break;
            case 8:
                objArr[0] = "place";
                break;
        }
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCatchSectionImpl";
                break;
            case 3:
                objArr[1] = "getTryStatement";
                break;
            default:
                objArr[1] = "getPreciseCatchTypes";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "getThrownTypes";
                break;
            case 4:
                objArr[2] = "accept";
                break;
            case 5:
                objArr[2] = "getChildRole";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "processDeclarations";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalArgumentException(str2);
            case 3:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public PsiCatchSectionImpl() {
        super(CATCH_SECTION);
        this.myTypesCacheLock = new Object();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection
    public PsiParameter getParameter() {
        return (PsiParameter) findChildByRoleAsPsiElement(15);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection
    public PsiCodeBlock getCatchBlock() {
        return (PsiCodeBlock) findChildByRoleAsPsiElement(49);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection
    public PsiType getCatchType() {
        PsiParameter parameter = getParameter();
        if (parameter == null) {
            return null;
        }
        return parameter.getType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection
    public List<PsiType> getPreciseCatchTypes() {
        if (getParameter() == null) {
            List<PsiType> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(0);
            }
            return listEmptyList;
        }
        List<PsiType> value = getTypesCache().getValue();
        if (value == null) {
            $$$reportNull$$$0(1);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        synchronized (this.myTypesCacheLock) {
            this.myTypesCache = null;
        }
    }

    private CachedValue<List<PsiType>> getTypesCache() {
        CachedValue<List<PsiType>> cachedValue;
        synchronized (this.myTypesCacheLock) {
            if (this.myTypesCache == null) {
                this.myTypesCache = CachedValuesManager.getManager(getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCatchSectionImpl$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                    public final CachedValueProvider.Result compute() {
                        return this.f$0.m6679xa228cd14();
                    }
                }, false);
            }
            cachedValue = this.myTypesCache;
        }
        return cachedValue;
    }

    /* JADX INFO: renamed from: lambda$getTypesCache$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiCatchSectionImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6679xa228cd14() {
        return CachedValueProvider.Result.create(computePreciseCatchTypes(getParameter()), PsiModificationTracker.MODIFICATION_COUNT);
    }

    private List<PsiType> computePreciseCatchTypes(PsiParameter psiParameter) {
        if (psiParameter == null) {
            return ContainerUtil.emptyList();
        }
        PsiType type = psiParameter.getType();
        if (PsiUtil.getLanguageLevel(psiParameter).isAtLeast(LanguageLevel.JDK_1_7) && isCatchParameterEffectivelyFinal(psiParameter, getCatchBlock())) {
            PsiTryStatement tryStatement = getTryStatement();
            Collection<PsiClassType> thrownTypes = getThrownTypes(tryStatement);
            if (thrownTypes.isEmpty()) {
                return Collections.emptyList();
            }
            final PsiParameter[] catchBlockParameters = tryStatement.getCatchBlockParameters();
            final int iFind = ArrayUtil.find(catchBlockParameters, psiParameter);
            final List listMapNotNull = ContainerUtil.mapNotNull(thrownTypes, new NullableFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCatchSectionImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return PsiCatchSectionImpl.lambda$computePreciseCatchTypes$1(iFind, catchBlockParameters, (PsiClassType) obj);
                }
            });
            if (listMapNotNull.isEmpty()) {
                return Collections.emptyList();
            }
            if (type instanceof PsiDisjunctionType) {
                return Collections.unmodifiableList((List) ((PsiDisjunctionType) type).getDisjunctions().stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCatchSectionImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return PsiCatchSectionImpl.computePreciseCatchTypes((PsiType) obj, listMapNotNull).stream();
                    }
                }).collect(Collectors.toCollection(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCatchSectionImpl$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return PsiCatchSectionImpl.lambda$computePreciseCatchTypes$3();
                    }
                })));
            }
            return computePreciseCatchTypes(type, listMapNotNull);
        }
        return Collections.singletonList(type);
    }

    static /* synthetic */ PsiType lambda$computePreciseCatchTypes$1(int i, PsiParameter[] psiParameterArr, PsiClassType psiClassType) {
        for (int i2 = 0; i2 < i; i2++) {
            if (psiParameterArr[i2].getType().isAssignableFrom(psiClassType)) {
                return null;
            }
        }
        return psiClassType;
    }

    static /* synthetic */ ArrayList lambda$computePreciseCatchTypes$3() {
        return new ArrayList();
    }

    private static List<PsiType> computePreciseCatchTypes(PsiType psiType, List<PsiType> list) {
        SmartList smartList = new SmartList();
        for (PsiType psiType2 : list) {
            if (psiType2.isAssignableFrom(psiType)) {
                smartList.add(psiType);
            } else if (psiType.isAssignableFrom(psiType2) || (ExceptionUtil.isGeneralExceptionType(psiType) && (psiType2 instanceof PsiClassType) && ExceptionUtil.isUncheckedException((PsiClassType) psiType2))) {
                smartList.add(psiType2);
            }
        }
        return smartList;
    }

    private static Collection<PsiClassType> getThrownTypes(PsiTryStatement psiTryStatement) {
        if (psiTryStatement == null) {
            $$$reportNull$$$0(2);
        }
        ArrayList arrayList = new ArrayList();
        PsiCodeBlock tryBlock = psiTryStatement.getTryBlock();
        if (tryBlock != null) {
            arrayList.addAll(ExceptionUtil.getThrownExceptions(tryBlock));
        }
        PsiResourceList resourceList = psiTryStatement.getResourceList();
        if (resourceList != null) {
            arrayList.addAll(ExceptionUtil.getThrownExceptions(resourceList));
        }
        return arrayList;
    }

    private static boolean isCatchParameterEffectivelyFinal(final PsiParameter psiParameter, PsiCodeBlock psiCodeBlock) {
        final boolean[] zArr = {true};
        if (psiCodeBlock != null) {
            psiCodeBlock.accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCatchSectionImpl.1
                @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
                    super.visitReferenceExpression(psiReferenceExpression);
                    if (psiReferenceExpression.resolve() == psiParameter && PsiUtil.isAccessedForWriting(psiReferenceExpression)) {
                        zArr[0] = false;
                        stopWalking();
                    }
                }
            });
        }
        return zArr[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection
    public PsiTryStatement getTryStatement() {
        PsiTryStatement psiTryStatement = (PsiTryStatement) getLightParent();
        if (psiTryStatement == null) {
            $$$reportNull$$$0(3);
        }
        return psiTryStatement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection
    public PsiJavaToken getLParenth() {
        return (PsiJavaToken) findChildByRole(50);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection
    public PsiJavaToken getRParenth() {
        return (PsiJavaToken) findChildByRole(51);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitCatchSection(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiCatchSection";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        if (i == 15) {
            return findChildByType(PARAMETER);
        }
        if (i == 46) {
            return findChildByType(CATCH_KEYWORD);
        }
        switch (i) {
            case 49:
                return findChildByType(CODE_BLOCK);
            case 50:
                return findChildByType(LPARENTH);
            case 51:
                return findChildByType(RPARENTH);
            default:
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == PARAMETER) {
            return 15;
        }
        if (elementType == CODE_BLOCK) {
            return 49;
        }
        if (elementType == CATCH_KEYWORD) {
            return 46;
        }
        if (elementType == LPARENTH) {
            return 50;
        }
        return elementType == RPARENTH ? 51 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(6);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(8);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        if (psiElement == null || psiElement.getLightParent() != this) {
            return true;
        }
        PsiParameter parameter = getParameter();
        if (parameter != null) {
            return psiScopeProcessor.execute(parameter, resolveState);
        }
        return PsiScopesUtil.walkChildrenScopes(this, psiScopeProcessor, resolveState, psiElement, psiElement2);
    }
}
