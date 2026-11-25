package org.jetbrains.kotlin.com.intellij.psi.util;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.IntArrayList;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTreeUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiTreeUtil.class);
    private static final Key<Object> MARKER = Key.create("PsiTreeUtil.copyElements.MARKER");
    private static final Class<? extends PsiElement>[] WS = {PsiWhiteSpace.class};
    private static final Class<? extends PsiElement>[] WS_COMMENTS = {PsiWhiteSpace.class, PsiComment.class};

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 19:
            case 20:
            case 27:
            case 32:
            case 34:
            case 37:
            case 40:
            case 41:
            case 61:
            case 78:
            case 97:
            case 99:
            case 103:
            case 105:
            case 114:
            case 122:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 19:
            case 20:
            case 27:
            case 32:
            case 34:
            case 37:
            case 40:
            case 41:
            case 61:
            case 78:
            case 97:
            case 99:
            case 103:
            case 105:
            case 114:
            case 122:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 3:
            case 7:
            case 113:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 4:
            case 8:
                objArr[0] = "element1";
                break;
            case 5:
            case 9:
                objArr[0] = "element2";
                break;
            case 6:
            case 10:
            case 25:
            case 28:
            case 58:
            case 62:
            case 64:
            case 66:
            case 68:
            case 84:
            case 86:
            case 96:
            case 98:
            case 102:
            case 104:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
            case 12:
            case 13:
            case 16:
            case 21:
            case 26:
            case 30:
            case 33:
            case 38:
            case 39:
            case 44:
            case 45:
            case 46:
            case 47:
            case 49:
            case 51:
            case 54:
            case 55:
            case 56:
                objArr[0] = "aClass";
                break;
            case 14:
            case 15:
            case 17:
            case 18:
            case 31:
            case 42:
            case 43:
            case 52:
            case 53:
            case 75:
            case 76:
            case 79:
                objArr[0] = "classes";
                break;
            case 19:
            case 20:
            case 27:
            case 32:
            case 34:
            case 37:
            case 40:
            case 41:
            case 61:
            case 78:
            case 97:
            case 99:
            case 103:
            case 105:
            case 114:
            case 122:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTreeUtil";
                break;
            case 22:
            case 23:
            case 24:
            case 74:
                objArr[0] = "condition";
                break;
            case 29:
            case 90:
            case 92:
            case 95:
                objArr[0] = "clazz";
                break;
            case 35:
                objArr[0] = "start";
                break;
            case 36:
                objArr[0] = "end";
                break;
            case 48:
                objArr[0] = "parentClass";
                break;
            case 50:
            case 57:
            case 93:
                objArr[0] = "stopAt";
                break;
            case 59:
                objArr[0] = "parent";
                break;
            case 60:
                objArr[0] = "stopCondition";
                break;
            case 63:
            case 65:
            case 67:
            case 69:
                objArr[0] = "elementType";
                break;
            case 70:
            case 71:
                objArr[0] = "elementClasses";
                break;
            case 72:
                objArr[0] = "parentClasses";
                break;
            case 73:
                objArr[0] = ES6Iterator.NEXT_METHOD;
                break;
            case 77:
                objArr[0] = "filter";
                break;
            case 80:
                objArr[0] = "elementClass";
                break;
            case 81:
            case 82:
            case 83:
            case 115:
                objArr[0] = "processor";
                break;
            case 85:
            case 88:
                objArr[0] = "marker";
                break;
            case 87:
                objArr[0] = "root";
                break;
            case 89:
            case 91:
            case 94:
                objArr[0] = "file";
                break;
            case 100:
            case 101:
                objArr[0] = "current";
                break;
            case 116:
            case 118:
                objArr[0] = "entrance";
                break;
            case 117:
                objArr[0] = "state";
                break;
            case 119:
                objArr[0] = "eachScopeAndLastParent";
                break;
            case 120:
                objArr[0] = "ancestor";
                break;
            case 121:
                objArr[0] = "descendant";
                break;
            case 123:
                objArr[0] = "outerLanguageElement";
                break;
            case 124:
            case 125:
                objArr[0] = "psiElement";
                break;
            case 126:
                objArr[0] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
        }
        switch (i) {
            case 19:
            case 20:
                objArr[1] = "findChildrenOfAnyType";
                break;
            case 27:
                objArr[1] = "getRequiredChildOfType";
                break;
            case 32:
                objArr[1] = "getChildrenOfAnyType";
                break;
            case 34:
                objArr[1] = "getChildrenOfTypeAsList";
                break;
            case 37:
                objArr[1] = "getElementsOfRange";
                break;
            case 40:
            case 41:
                objArr[1] = "getStubChildrenOfTypeAsList";
                break;
            case 61:
                objArr[1] = "collectParents";
                break;
            case 78:
                objArr[1] = "collectElements";
                break;
            case 97:
                objArr[1] = "getDeepestFirst";
                break;
            case 99:
                objArr[1] = "getDeepestLast";
                break;
            case 103:
                objArr[1] = "lastChild";
                break;
            case 105:
                objArr[1] = "firstChild";
                break;
            case 114:
                objArr[1] = "filterAncestors";
                break;
            case 122:
                objArr[1] = "findPrevParent";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiTreeUtil";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "isContextAncestor";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[2] = "findCommonParent";
                break;
            case 6:
                objArr[2] = "getDepth";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "findCommonContext";
                break;
            case 10:
                objArr[2] = "getContextDepth";
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "findChildOfType";
                break;
            case 14:
            case 15:
                objArr[2] = "findChildOfAnyType";
                break;
            case 16:
                objArr[2] = "findChildrenOfType";
                break;
            case 17:
            case 18:
                objArr[2] = "findChildrenOfAnyType";
                break;
            case 19:
            case 20:
            case 27:
            case 32:
            case 34:
            case 37:
            case 40:
            case 41:
            case 61:
            case 78:
            case 97:
            case 99:
            case 103:
            case 105:
            case 114:
            case 122:
                break;
            case 21:
                objArr[2] = "getChildOfType";
                break;
            case 22:
            case 23:
                objArr[2] = "findFirstParent";
                break;
            case 24:
                objArr[2] = "findFirstContext";
                break;
            case 25:
            case 26:
                objArr[2] = "getRequiredChildOfType";
                break;
            case 28:
            case 29:
                objArr[2] = "countChildrenOfType";
                break;
            case 30:
                objArr[2] = "getChildrenOfType";
                break;
            case 31:
                objArr[2] = "getChildrenOfAnyType";
                break;
            case 33:
                objArr[2] = "getChildrenOfTypeAsList";
                break;
            case 35:
            case 36:
                objArr[2] = "getElementsOfRange";
                break;
            case 38:
                objArr[2] = "getStubChildOfType";
                break;
            case 39:
                objArr[2] = "getStubChildrenOfTypeAsList";
                break;
            case 42:
                objArr[2] = "instanceOf";
                break;
            case 43:
                objArr[2] = "getChildOfAnyType";
                break;
            case 44:
                objArr[2] = "getNextSiblingOfType";
                break;
            case 45:
                objArr[2] = "getPrevSiblingOfType";
                break;
            case 46:
                objArr[2] = "getTopmostParentOfType";
                break;
            case 47:
            case 54:
            case 55:
            case 56:
            case 57:
            case 75:
                objArr[2] = "getParentOfType";
                break;
            case 48:
                objArr[2] = "getStubOrPsiParentOfType";
                break;
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
                objArr[2] = "getContextOfType";
                break;
            case 58:
            case 59:
            case 60:
                objArr[2] = "collectParents";
                break;
            case 62:
            case 63:
            case 64:
            case 65:
                objArr[2] = "findSiblingForward";
                break;
            case 66:
            case 67:
            case 68:
            case 69:
                objArr[2] = "findSiblingBackward";
                break;
            case 70:
                objArr[2] = "skipSiblingsForward";
                break;
            case 71:
                objArr[2] = "skipSiblingsBackward";
                break;
            case 72:
                objArr[2] = "skipParentsOfType";
                break;
            case 73:
            case 74:
                objArr[2] = "skipMatching";
                break;
            case 76:
                objArr[2] = "getNonStrictParentOfType";
                break;
            case 77:
                objArr[2] = "collectElements";
                break;
            case 79:
                objArr[2] = "collectElementsOfType";
                break;
            case 80:
            case 81:
            case 82:
            case 83:
                objArr[2] = "processElements";
                break;
            case 84:
            case 85:
            case 86:
                objArr[2] = "mark";
                break;
            case 87:
            case 88:
                objArr[2] = "releaseMark";
                break;
            case 89:
            case 90:
                objArr[2] = "findElementOfClassAtOffset";
                break;
            case 91:
            case 92:
            case 93:
                objArr[2] = "findElementOfClassAtOffsetWithStopSet";
                break;
            case 94:
            case 95:
                objArr[2] = "findElementOfClassAtRange";
                break;
            case 96:
                objArr[2] = "getDeepestFirst";
                break;
            case 98:
                objArr[2] = "getDeepestLast";
                break;
            case 100:
            case 106:
                objArr[2] = "prevLeaf";
                break;
            case 101:
            case 111:
                objArr[2] = "nextLeaf";
                break;
            case 102:
                objArr[2] = "lastChild";
                break;
            case 104:
                objArr[2] = "firstChild";
                break;
            case 107:
                objArr[2] = "prevVisibleLeaf";
                break;
            case 108:
                objArr[2] = "nextVisibleLeaf";
                break;
            case 109:
                objArr[2] = "prevCodeLeaf";
                break;
            case 110:
                objArr[2] = "nextCodeLeaf";
                break;
            case 112:
                objArr[2] = "hasErrorElements";
                break;
            case 113:
                objArr[2] = "filterAncestors";
                break;
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
                objArr[2] = "treeWalkUp";
                break;
            case 120:
            case 121:
                objArr[2] = "findPrevParent";
                break;
            case 123:
                objArr[2] = "getInjectedElements";
                break;
            case 124:
                objArr[2] = "getDeepestVisibleFirst";
                break;
            case 125:
                objArr[2] = "getDeepestVisibleLast";
                break;
            case 126:
                objArr[2] = "findSameElementInCopy";
                break;
            default:
                objArr[2] = "isAncestor";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 19:
            case 20:
            case 27:
            case 32:
            case 34:
            case 37:
            case 40:
            case 41:
            case 61:
            case 78:
            case 97:
            case 99:
            case 103:
            case 105:
            case 114:
            case 122:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public static boolean isAncestor(PsiElement psiElement, PsiElement psiElement2, boolean z) {
        if (psiElement2 == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            return false;
        }
        if ((((psiElement instanceof StubBasedPsiElement) && ((StubBasedPsiElement) psiElement).getStub() != null) || ((psiElement2 instanceof StubBasedPsiElement) && ((StubBasedPsiElement) psiElement2).getStub() != null)) && psiElement.getContainingFile() != psiElement2.getContainingFile()) {
            return false;
        }
        boolean z2 = ((psiElement instanceof PsiFile) || (psiElement instanceof PsiDirectory)) ? false : true;
        if (z) {
            psiElement2 = psiElement2.getLightParent();
        }
        while (psiElement2 != null) {
            if (psiElement2.equals(psiElement)) {
                return true;
            }
            if (z2 && (psiElement2 instanceof PsiFile)) {
                return false;
            }
            psiElement2 = psiElement2.getLightParent();
        }
        return false;
    }

    public static boolean isContextAncestor(PsiElement psiElement, PsiElement psiElement2, boolean z) {
        if (psiElement2 == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            return false;
        }
        boolean z2 = ((psiElement instanceof PsiFile) || (psiElement instanceof PsiDirectory)) ? false : true;
        if (z) {
            psiElement2 = psiElement2.getContext();
        }
        while (psiElement2 != null) {
            if (psiElement2.equals(psiElement)) {
                return true;
            }
            if (z2 && (psiElement2 instanceof PsiFile) && psiElement2.getContext() == null) {
                return false;
            }
            psiElement2 = psiElement2.getContext();
        }
        return false;
    }

    public static PsiElement findCommonParent(List<? extends PsiElement> list) {
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        if (list.isEmpty()) {
            return null;
        }
        PsiElement psiElementFindCommonParent = null;
        for (PsiElement psiElement : list) {
            if (psiElement != null) {
                psiElementFindCommonParent = psiElementFindCommonParent == null ? psiElement : findCommonParent(psiElementFindCommonParent, psiElement);
                if (psiElementFindCommonParent == null) {
                    return null;
                }
            }
        }
        return psiElementFindCommonParent;
    }

    public static PsiElement findCommonParent(PsiElement... psiElementArr) {
        if (psiElementArr == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementArr.length == 0) {
            return null;
        }
        return findCommonParent((List<? extends PsiElement>) Arrays.asList(psiElementArr));
    }

    public static PsiElement findCommonParent(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement == psiElement2) {
            return psiElement;
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile != psiElement2.getContainingFile()) {
            containingFile = null;
        }
        int depth = getDepth(psiElement, containingFile);
        int depth2 = getDepth(psiElement2, containingFile);
        while (depth > depth2) {
            psiElement = psiElement.getLightParent();
            depth--;
        }
        while (depth2 > depth) {
            psiElement2 = psiElement2.getLightParent();
            depth2--;
        }
        while (psiElement != null && psiElement2 != null && !psiElement.equals(psiElement2)) {
            psiElement = psiElement.getLightParent();
            psiElement2 = psiElement2.getLightParent();
        }
        return psiElement;
    }

    public static int getDepth(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        int i = 0;
        while (psiElement != psiElement2 && psiElement != null) {
            i++;
            psiElement = psiElement.getLightParent();
        }
        return i;
    }

    public static PsiElement findCommonContext(Collection<? extends PsiElement> collection) {
        if (collection == null) {
            $$$reportNull$$$0(7);
        }
        if (collection.isEmpty()) {
            return null;
        }
        PsiElement psiElementFindCommonContext = null;
        for (PsiElement psiElement : collection) {
            if (psiElement != null) {
                psiElementFindCommonContext = psiElementFindCommonContext == null ? psiElement : findCommonContext(psiElementFindCommonContext, psiElement);
                if (psiElementFindCommonContext == null) {
                    return null;
                }
            }
        }
        return psiElementFindCommonContext;
    }

    public static PsiElement findCommonContext(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement == psiElement2) {
            return psiElement;
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile != psiElement2.getContainingFile()) {
            containingFile = null;
        }
        int contextDepth = getContextDepth(psiElement, containingFile);
        int contextDepth2 = getContextDepth(psiElement2, containingFile);
        while (contextDepth > contextDepth2 && psiElement != null) {
            psiElement = psiElement.getContext();
            contextDepth--;
        }
        while (contextDepth2 > contextDepth && psiElement2 != null) {
            psiElement2 = psiElement2.getContext();
            contextDepth2--;
        }
        while (psiElement != null && psiElement2 != null && !psiElement.equals(psiElement2)) {
            psiElement = psiElement.getContext();
            psiElement2 = psiElement2.getContext();
        }
        return psiElement;
    }

    private static int getContextDepth(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        int i = 0;
        while (psiElement != psiElement2 && psiElement != null) {
            i++;
            psiElement = psiElement.getContext();
        }
        return i;
    }

    public static <T extends PsiElement> T findChildOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(11);
        }
        return (T) findChildOfType(psiElement, cls, true, null);
    }

    public static <T extends PsiElement> T findChildOfType(PsiElement psiElement, Class<T> cls, boolean z) {
        if (cls == null) {
            $$$reportNull$$$0(12);
        }
        return (T) findChildOfType(psiElement, cls, z, null);
    }

    public static <T extends PsiElement> T findChildOfType(final PsiElement psiElement, final Class<T> cls, final boolean z, final Class<? extends PsiElement> cls2) {
        if (cls == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement == null) {
            return null;
        }
        PsiElementProcessor.FindElement<PsiElement> findElement = new PsiElementProcessor.FindElement<PsiElement>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "each", "org/jetbrains/kotlin/com/intellij/psi/util/PsiTreeUtil$1", "execute"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor.FindElement, org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
            public boolean execute(PsiElement psiElement2) {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (z && psiElement2 == psiElement) {
                    return true;
                }
                if (cls.isInstance(psiElement2)) {
                    return setFound(psiElement2);
                }
                Class cls3 = cls2;
                return cls3 == null || !cls3.isInstance(psiElement2);
            }
        };
        processElements(psiElement, findElement);
        return cls.cast(findElement.getFoundElement());
    }

    @SafeVarargs
    public static <T extends PsiElement> T findChildOfAnyType(PsiElement psiElement, Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(14);
        }
        return (T) findChildOfAnyType(psiElement, true, clsArr);
    }

    @SafeVarargs
    public static <T extends PsiElement> T findChildOfAnyType(final PsiElement psiElement, final boolean z, final Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(15);
        }
        if (psiElement == null) {
            return null;
        }
        PsiElementProcessor.FindElement<PsiElement> findElement = new PsiElementProcessor.FindElement<PsiElement>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "each", "org/jetbrains/kotlin/com/intellij/psi/util/PsiTreeUtil$2", "execute"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor.FindElement, org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
            public boolean execute(PsiElement psiElement2) {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (!(z && psiElement2 == psiElement) && PsiTreeUtil.instanceOf(psiElement2, clsArr)) {
                    return setFound(psiElement2);
                }
                return true;
            }
        };
        processElements(psiElement, findElement);
        return (T) findElement.getFoundElement();
    }

    public static <T extends PsiElement> Collection<T> findChildrenOfType(PsiElement psiElement, Class<? extends T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(16);
        }
        return findChildrenOfAnyType(psiElement, true, cls);
    }

    @SafeVarargs
    public static <T extends PsiElement> Collection<T> findChildrenOfAnyType(PsiElement psiElement, Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(17);
        }
        return findChildrenOfAnyType(psiElement, true, clsArr);
    }

    @SafeVarargs
    public static <T extends PsiElement> Collection<T> findChildrenOfAnyType(final PsiElement psiElement, final boolean z, final Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(18);
        }
        if (psiElement == null) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(19);
            }
            return listEmptyList;
        }
        PsiElementProcessor.CollectElements<PsiElement> collectElements = new PsiElementProcessor.CollectElements<PsiElement>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "each", "org/jetbrains/kotlin/com/intellij/psi/util/PsiTreeUtil$3", "execute"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor.CollectElements, org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
            public boolean execute(PsiElement psiElement2) {
                if (psiElement2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (!(z && psiElement2 == psiElement) && PsiTreeUtil.instanceOf(psiElement2, clsArr)) {
                    return super.execute(psiElement2);
                }
                return true;
            }
        };
        processElements(psiElement, collectElements);
        Collection<T> collection = collectElements.getCollection();
        if (collection == null) {
            $$$reportNull$$$0(20);
        }
        return collection;
    }

    public static <T extends PsiElement> T getChildOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(21);
        }
        if (psiElement == null) {
            return null;
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (cls.isInstance(firstChild)) {
                return cls.cast(firstChild);
            }
        }
        return null;
    }

    public static PsiElement findFirstParent(PsiElement psiElement, Condition<? super PsiElement> condition) {
        if (condition == null) {
            $$$reportNull$$$0(22);
        }
        return findFirstParent(psiElement, false, condition);
    }

    public static PsiElement findFirstParent(PsiElement psiElement, boolean z, Condition<? super PsiElement> condition) {
        if (condition == null) {
            $$$reportNull$$$0(23);
        }
        if (z && psiElement != null) {
            psiElement = psiElement.getLightParent();
        }
        while (psiElement != null) {
            if (condition.value(psiElement)) {
                return psiElement;
            }
            psiElement = psiElement.getLightParent();
        }
        return null;
    }

    public static PsiElement findFirstContext(PsiElement psiElement, boolean z, Condition<? super PsiElement> condition) {
        if (condition == null) {
            $$$reportNull$$$0(24);
        }
        if (z && psiElement != null) {
            psiElement = psiElement.getContext();
        }
        while (psiElement != null) {
            if (condition.value(psiElement)) {
                return psiElement;
            }
            psiElement = psiElement.getContext();
        }
        return null;
    }

    public static <T extends PsiElement> T getRequiredChildOfType(PsiElement psiElement, Class<T> cls) {
        if (psiElement == null) {
            $$$reportNull$$$0(25);
        }
        if (cls == null) {
            $$$reportNull$$$0(26);
        }
        T t = (T) getChildOfType(psiElement, cls);
        if (t == null) {
            $$$reportNull$$$0(27);
        }
        return t;
    }

    public static int countChildrenOfType(PsiElement psiElement, Class<? extends PsiElement> cls) {
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        if (cls == null) {
            $$$reportNull$$$0(29);
        }
        int i = 0;
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (cls.isInstance(firstChild)) {
                i++;
            }
        }
        return i;
    }

    public static <T extends PsiElement> T[] getChildrenOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(30);
        }
        if (psiElement == null) {
            return null;
        }
        List childrenOfTypeAsList = getChildrenOfTypeAsList(psiElement, cls);
        if (childrenOfTypeAsList.isEmpty()) {
            return null;
        }
        return (T[]) ((PsiElement[]) ArrayUtil.toObjectArray(childrenOfTypeAsList, cls));
    }

    @SafeVarargs
    public static <T extends PsiElement> List<T> getChildrenOfAnyType(PsiElement psiElement, Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(31);
        }
        SmartList smartList = null;
        if (psiElement != null) {
            for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                if (instanceOf(firstChild, clsArr)) {
                    if (smartList == null) {
                        smartList = new SmartList();
                    }
                    smartList.add(firstChild);
                }
            }
        }
        if (smartList == null) {
            smartList = (List<T>) ContainerUtil.emptyList();
        }
        if (smartList == null) {
            $$$reportNull$$$0(32);
        }
        return (List<T>) smartList;
    }

    public static <T extends PsiElement> List<T> getChildrenOfTypeAsList(PsiElement psiElement, Class<? extends T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(33);
        }
        List<T> listEmptyList = null;
        if (psiElement != null) {
            for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                if (cls.isInstance(firstChild)) {
                    if (listEmptyList == null) {
                        listEmptyList = new SmartList();
                    }
                    listEmptyList.add(cls.cast(firstChild));
                }
            }
        }
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(34);
        }
        return listEmptyList;
    }

    public static List<PsiElement> getElementsOfRange(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(35);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(36);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiElement nextSibling = psiElement; nextSibling != psiElement2; nextSibling = nextSibling.getNextSibling()) {
            if (nextSibling == null) {
                throw new IllegalArgumentException("Invalid range: " + psiElement + ".." + psiElement2);
            }
            arrayList.add(nextSibling);
        }
        arrayList.add(psiElement2);
        return arrayList;
    }

    public static <T extends PsiElement> T getStubChildOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(38);
        }
        if (psiElement == null) {
            return null;
        }
        StubElement stub = psiElement instanceof StubBasedPsiElement ? ((StubBasedPsiElement) psiElement).getStub() : null;
        if (stub == null) {
            return (T) getChildOfType(psiElement, cls);
        }
        Iterator<StubElement> it = stub.getChildrenStubs().iterator();
        while (it.hasNext()) {
            PsiElement psi = it.next().getPsi();
            if (cls.isInstance(psi)) {
                return cls.cast(psi);
            }
        }
        return null;
    }

    public static <T extends PsiElement> List<T> getStubChildrenOfTypeAsList(PsiElement psiElement, Class<? extends T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(39);
        }
        if (psiElement == null) {
            List<T> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(40);
            }
            return listEmptyList;
        }
        StubElement stub = psiElement instanceof StubBasedPsiElement ? ((StubBasedPsiElement) psiElement).getStub() : null;
        if (stub == null) {
            return getChildrenOfTypeAsList(psiElement, cls);
        }
        SmartList smartList = new SmartList();
        Iterator<StubElement> it = stub.getChildrenStubs().iterator();
        while (it.hasNext()) {
            PsiElement psi = it.next().getPsi();
            if (cls.isInstance(psi)) {
                smartList.add(cls.cast(psi));
            }
        }
        return smartList;
    }

    public static boolean instanceOf(Object obj, Class<?>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(42);
        }
        if (obj != null) {
            for (Class<?> cls : clsArr) {
                if (cls.isInstance(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SafeVarargs
    public static <T extends PsiElement> T getChildOfAnyType(PsiElement psiElement, Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(43);
        }
        if (psiElement == null) {
            return null;
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            for (Class<? extends T> cls : clsArr) {
                if (cls.isInstance(firstChild)) {
                    return cls.cast(firstChild);
                }
            }
        }
        return null;
    }

    public static <T extends PsiElement> T getNextSiblingOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(44);
        }
        if (psiElement == null) {
            return null;
        }
        do {
            psiElement = psiElement.getNextSibling();
            if (psiElement == null) {
                return null;
            }
        } while (!cls.isInstance(psiElement));
        return cls.cast(psiElement);
    }

    public static <T extends PsiElement> T getPrevSiblingOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(45);
        }
        if (psiElement == null) {
            return null;
        }
        do {
            psiElement = psiElement.getPrevSibling();
            if (psiElement == null) {
                return null;
            }
        } while (!cls.isInstance(psiElement));
        return cls.cast(psiElement);
    }

    public static <T extends PsiElement> T getTopmostParentOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(46);
        }
        T t = (T) getParentOfType(psiElement, cls);
        while (true) {
            PsiElement parentOfType = getParentOfType(t, cls);
            if (parentOfType == null) {
                return t;
            }
            t = (T) parentOfType;
        }
    }

    public static <T extends PsiElement> T getParentOfType(PsiElement psiElement, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(47);
        }
        return (T) getParentOfType(psiElement, cls, true);
    }

    public static PsiElement getStubOrPsiParent(PsiElement psiElement) {
        StubElement stub;
        if (!(psiElement instanceof StubBasedPsiElement) || (stub = ((StubBasedPsiElement) psiElement).getStub()) == null) {
            if (psiElement != null) {
                return psiElement.getLightParent();
            }
            return null;
        }
        StubElement parentStub = stub.getParentStub();
        if (parentStub != null) {
            return parentStub.getPsi();
        }
        return null;
    }

    public static <E extends PsiElement> E getStubOrPsiParentOfType(PsiElement psiElement, Class<E> cls) {
        StubElement stub;
        if (cls == null) {
            $$$reportNull$$$0(48);
        }
        if ((psiElement instanceof StubBasedPsiElement) && (stub = ((StubBasedPsiElement) psiElement).getStub()) != null) {
            return (E) stub.getParentStubOfType(cls);
        }
        return (E) getParentOfType(psiElement, cls);
    }

    @SafeVarargs
    public static <T extends PsiElement> T getContextOfType(PsiElement psiElement, Class<T> cls, boolean z, Class<? extends PsiElement>... clsArr) {
        if (cls == null) {
            $$$reportNull$$$0(49);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(50);
        }
        if (psiElement == null) {
            return null;
        }
        if (z) {
            psiElement = psiElement.getContext();
        }
        while (psiElement != null && !cls.isInstance(psiElement)) {
            if (instanceOf(psiElement, clsArr)) {
                return null;
            }
            psiElement = psiElement.getContext();
        }
        return cls.cast(psiElement);
    }

    public static <T extends PsiElement> T getContextOfType(PsiElement psiElement, Class<? extends T> cls, boolean z) {
        if (cls == null) {
            $$$reportNull$$$0(51);
        }
        return (T) getContextOfType(psiElement, z, cls);
    }

    @SafeVarargs
    public static <T extends PsiElement> T getContextOfType(PsiElement psiElement, Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(52);
        }
        return (T) getContextOfType(psiElement, true, (Class[]) clsArr);
    }

    @SafeVarargs
    public static <T extends PsiElement> T getContextOfType(PsiElement psiElement, boolean z, Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(53);
        }
        if (psiElement == null) {
            return null;
        }
        if (z) {
            psiElement = (T) psiElement.getContext();
        }
        while (psiElement != null && !instanceOf(psiElement, clsArr)) {
            psiElement = (T) psiElement.getContext();
        }
        return (T) psiElement;
    }

    public static <T extends PsiElement> T getParentOfType(PsiElement psiElement, Class<T> cls, boolean z) {
        if (cls == null) {
            $$$reportNull$$$0(54);
        }
        return (T) getParentOfType(psiElement, cls, z, -1);
    }

    public static <T extends PsiElement> T getParentOfType(PsiElement psiElement, Class<T> cls, boolean z, int i) {
        if (cls == null) {
            $$$reportNull$$$0(55);
        }
        if (psiElement == null) {
            return null;
        }
        if (z) {
            if (psiElement instanceof PsiFile) {
                return null;
            }
            psiElement = psiElement.getLightParent();
        }
        while (psiElement != null && (i == -1 || psiElement.getNode().getStartOffset() >= i)) {
            if (cls.isInstance(psiElement)) {
                return cls.cast(psiElement);
            }
            if (psiElement instanceof PsiFile) {
                return null;
            }
            psiElement = psiElement.getLightParent();
        }
        return null;
    }

    @SafeVarargs
    public static <T extends PsiElement> T getParentOfType(PsiElement psiElement, Class<T> cls, boolean z, Class<? extends PsiElement>... clsArr) {
        if (cls == null) {
            $$$reportNull$$$0(56);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(57);
        }
        if (psiElement == null) {
            return null;
        }
        if (z) {
            if (psiElement instanceof PsiFile) {
                return null;
            }
            psiElement = psiElement.getLightParent();
        }
        while (psiElement != null && !cls.isInstance(psiElement)) {
            if (instanceOf(psiElement, clsArr) || (psiElement instanceof PsiFile)) {
                return null;
            }
            psiElement = psiElement.getLightParent();
        }
        return cls.cast(psiElement);
    }

    public static <T extends PsiElement> List<T> collectParents(PsiElement psiElement, Class<? extends T> cls, boolean z, Predicate<? super PsiElement> predicate) {
        if (psiElement == null) {
            $$$reportNull$$$0(58);
        }
        if (cls == null) {
            $$$reportNull$$$0(59);
        }
        if (predicate == null) {
            $$$reportNull$$$0(60);
        }
        if (!z) {
            psiElement = psiElement.getLightParent();
        }
        SmartList smartList = new SmartList();
        while (psiElement != null && !predicate.test(psiElement)) {
            if (cls.isInstance(psiElement)) {
                smartList.add(cls.cast(psiElement));
            }
            psiElement = psiElement.getLightParent();
        }
        return smartList;
    }

    public static PsiElement findSiblingForward(PsiElement psiElement, IElementType iElementType, Consumer<? super PsiElement> consumer) {
        if (psiElement == null) {
            $$$reportNull$$$0(62);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(63);
        }
        return findSiblingForward(psiElement, iElementType, true, consumer);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0022 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:13:0x0024  */
    /* JADX WARN: Code duplicated, block: B:15:0x002c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:9:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x0027 -> B:8:0x0011). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public static org.jetbrains.kotlin.com.intellij.psi.PsiElement findSiblingForward(org.jetbrains.kotlin.com.intellij.psi.PsiElement r1, org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r2, boolean r3, org.jetbrains.kotlin.com.intellij.util.Consumer<? super org.jetbrains.kotlin.com.intellij.psi.PsiElement> r4) {
        /*
            if (r1 != 0) goto L7
            r0 = 64
            $$$reportNull$$$0(r0)
        L7:
            if (r2 != 0) goto Le
            r0 = 65
            $$$reportNull$$$0(r0)
        Le:
            if (r3 == 0) goto L11
            goto L27
        L11:
            if (r1 == 0) goto L2c
            org.jetbrains.kotlin.com.intellij.lang.ASTNode r3 = r1.getNode()
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r3 = r3.getElementType()
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L22
            return r1
        L22:
            if (r4 == 0) goto L27
            r4.consume(r1)
        L27:
            org.jetbrains.kotlin.com.intellij.psi.PsiElement r1 = r1.getNextSibling()
            goto L11
        L2c:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.findSiblingForward(org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.psi.tree.IElementType, boolean, org.jetbrains.kotlin.com.intellij.util.Consumer):org.jetbrains.kotlin.com.intellij.psi.PsiElement");
    }

    public static PsiElement findSiblingBackward(PsiElement psiElement, IElementType iElementType, Consumer<? super PsiElement> consumer) {
        if (psiElement == null) {
            $$$reportNull$$$0(66);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(67);
        }
        return findSiblingBackward(psiElement, iElementType, true, consumer);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0022 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:13:0x0024  */
    /* JADX WARN: Code duplicated, block: B:15:0x002c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:9:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x0027 -> B:8:0x0011). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public static org.jetbrains.kotlin.com.intellij.psi.PsiElement findSiblingBackward(org.jetbrains.kotlin.com.intellij.psi.PsiElement r1, org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r2, boolean r3, org.jetbrains.kotlin.com.intellij.util.Consumer<? super org.jetbrains.kotlin.com.intellij.psi.PsiElement> r4) {
        /*
            if (r1 != 0) goto L7
            r0 = 68
            $$$reportNull$$$0(r0)
        L7:
            if (r2 != 0) goto Le
            r0 = 69
            $$$reportNull$$$0(r0)
        Le:
            if (r3 == 0) goto L11
            goto L27
        L11:
            if (r1 == 0) goto L2c
            org.jetbrains.kotlin.com.intellij.lang.ASTNode r3 = r1.getNode()
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r3 = r3.getElementType()
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L22
            return r1
        L22:
            if (r4 == 0) goto L27
            r4.consume(r1)
        L27:
            org.jetbrains.kotlin.com.intellij.psi.PsiElement r1 = r1.getPrevSibling()
            goto L11
        L2c:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.findSiblingBackward(org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.psi.tree.IElementType, boolean, org.jetbrains.kotlin.com.intellij.util.Consumer):org.jetbrains.kotlin.com.intellij.psi.PsiElement");
    }

    @SafeVarargs
    public static PsiElement skipSiblingsForward(PsiElement psiElement, Class<? extends PsiElement>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(70);
        }
        if (psiElement == null) {
            return null;
        }
        do {
            psiElement = psiElement.getNextSibling();
            if (psiElement == null) {
                return null;
            }
        } while (instanceOf(psiElement, clsArr));
        return psiElement;
    }

    public static PsiElement skipWhitespacesForward(PsiElement psiElement) {
        return skipSiblingsForward(psiElement, WS);
    }

    public static PsiElement skipWhitespacesAndCommentsForward(PsiElement psiElement) {
        return skipSiblingsForward(psiElement, WS_COMMENTS);
    }

    @SafeVarargs
    public static PsiElement skipSiblingsBackward(PsiElement psiElement, Class<? extends PsiElement>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(71);
        }
        if (psiElement == null) {
            return null;
        }
        do {
            psiElement = psiElement.getPrevSibling();
            if (psiElement == null) {
                return null;
            }
        } while (instanceOf(psiElement, clsArr));
        return psiElement;
    }

    public static PsiElement skipWhitespacesBackward(PsiElement psiElement) {
        return skipSiblingsBackward(psiElement, WS);
    }

    public static PsiElement skipWhitespacesAndCommentsBackward(PsiElement psiElement) {
        return skipSiblingsBackward(psiElement, WS_COMMENTS);
    }

    @SafeVarargs
    public static PsiElement skipParentsOfType(PsiElement psiElement, Class<? extends PsiElement>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(72);
        }
        if (psiElement == null) {
            return null;
        }
        do {
            psiElement = psiElement.getLightParent();
            if (psiElement == null) {
                return null;
            }
        } while (instanceOf(psiElement, clsArr));
        return psiElement;
    }

    public static PsiElement skipMatching(PsiElement psiElement, Function<? super PsiElement, ? extends PsiElement> function, Predicate<? super PsiElement> predicate) {
        if (function == null) {
            $$$reportNull$$$0(73);
        }
        if (predicate == null) {
            $$$reportNull$$$0(74);
        }
        if (psiElement == null) {
            return null;
        }
        do {
            psiElement = function.apply(psiElement);
            if (psiElement == null) {
                return null;
            }
        } while (predicate.test(psiElement));
        return psiElement;
    }

    @SafeVarargs
    public static <T extends PsiElement> T getParentOfType(PsiElement psiElement, Class<? extends T>... clsArr) {
        PsiElement parent;
        if (clsArr == null) {
            $$$reportNull$$$0(75);
        }
        if (psiElement == null || (psiElement instanceof PsiFile) || (parent = psiElement.getLightParent()) == null) {
            return null;
        }
        return (T) getNonStrictParentOfType(parent, clsArr);
    }

    @SafeVarargs
    public static <T extends PsiElement> T getNonStrictParentOfType(PsiElement psiElement, Class<? extends T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(76);
        }
        while (psiElement != null) {
            if (instanceOf(psiElement, clsArr)) {
                return (T) psiElement;
            }
            if (psiElement instanceof PsiFile) {
                return null;
            }
            psiElement = (T) psiElement.getLightParent();
        }
        return null;
    }

    public static PsiElement[] collectElements(PsiElement psiElement, final PsiElementFilter psiElementFilter) {
        if (psiElementFilter == null) {
            $$$reportNull$$$0(77);
        }
        final ArrayList arrayList = new ArrayList();
        processElements(psiElement, (PsiElementProcessor<? super PsiElement>) new PsiElementProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
            public final boolean execute(PsiElement psiElement2) {
                return PsiTreeUtil.lambda$collectElements$0(psiElementFilter, arrayList, psiElement2);
            }
        });
        PsiElement[] psiElementArr = (PsiElement[]) arrayList.toArray(PsiElement.EMPTY_ARRAY);
        if (psiElementArr == null) {
            $$$reportNull$$$0(78);
        }
        return psiElementArr;
    }

    static /* synthetic */ boolean lambda$collectElements$0(PsiElementFilter psiElementFilter, List list, PsiElement psiElement) {
        if (!psiElementFilter.isAccepted(psiElement)) {
            return true;
        }
        list.add(psiElement);
        return true;
    }

    @SafeVarargs
    public static <T extends PsiElement> Collection<T> collectElementsOfType(PsiElement psiElement, Class<T>... clsArr) {
        if (clsArr == null) {
            $$$reportNull$$$0(79);
        }
        return findChildrenOfAnyType(psiElement, false, clsArr);
    }

    public static <T extends PsiElement> boolean processElements(PsiElement psiElement, final Class<T> cls, final PsiElementProcessor<? super T> psiElementProcessor) {
        if (cls == null) {
            $$$reportNull$$$0(80);
        }
        if (psiElementProcessor == null) {
            $$$reportNull$$$0(81);
        }
        return psiElement == null || processElements(psiElement, (PsiElementProcessor<? super PsiElement>) new PsiElementProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
            public final boolean execute(PsiElement psiElement2) {
                return PsiTreeUtil.lambda$processElements$1(cls, psiElementProcessor, psiElement2);
            }
        });
    }

    static /* synthetic */ boolean lambda$processElements$1(Class cls, PsiElementProcessor psiElementProcessor, PsiElement psiElement) {
        PsiElement psiElement2 = (PsiElement) ObjectUtils.tryCast(psiElement, cls);
        return psiElement2 == null || psiElementProcessor.execute(psiElement2);
    }

    public static boolean processElements(PsiElement psiElement, final PsiElementProcessor<? super PsiElement> psiElementProcessor) {
        if (psiElementProcessor == null) {
            $$$reportNull$$$0(82);
        }
        if (psiElement == null) {
            return true;
        }
        if ((psiElement instanceof PsiCompiledElement) || !psiElement.isPhysical()) {
            if (!psiElementProcessor.execute(psiElement)) {
                return false;
            }
            for (PsiElement psiElement2 : psiElement.getChildren()) {
                if (!processElements(psiElement2, psiElementProcessor)) {
                    return false;
                }
            }
            return true;
        }
        final boolean[] zArr = {true};
        psiElement.accept(new PsiRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/util/PsiTreeUtil$4", "visitElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement psiElement3) {
                if (psiElement3 == null) {
                    $$$reportNull$$$0(0);
                }
                if (psiElementProcessor.execute(psiElement3)) {
                    super.visitElement(psiElement3);
                } else {
                    stopWalking();
                    zArr[0] = false;
                }
            }
        });
        return zArr[0];
    }

    public static boolean processElements(PsiElementProcessor<? super PsiElement> psiElementProcessor, PsiElement... psiElementArr) {
        if (psiElementProcessor == null) {
            $$$reportNull$$$0(83);
        }
        if (psiElementArr == null || psiElementArr.length == 0) {
            return true;
        }
        for (PsiElement psiElement : psiElementArr) {
            if (!processElements(psiElement, psiElementProcessor)) {
                return false;
            }
        }
        return true;
    }

    public static void mark(PsiElement psiElement, Object obj) {
        if (psiElement == null) {
            $$$reportNull$$$0(84);
        }
        if (obj == null) {
            $$$reportNull$$$0(85);
        }
        psiElement.getNode().putCopyableUserData(MARKER, obj);
    }

    public static Object mark(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(86);
        }
        Object obj = new Object();
        mark(psiElement, obj);
        return obj;
    }

    public static PsiElement releaseMark(PsiElement psiElement, Object obj) {
        if (psiElement == null) {
            $$$reportNull$$$0(87);
        }
        if (obj == null) {
            $$$reportNull$$$0(88);
        }
        ASTNode node = psiElement.getNode();
        Key<Object> key = MARKER;
        if (obj.equals(node.getCopyableUserData(key))) {
            node.putCopyableUserData(key, null);
            return psiElement;
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            PsiElement psiElementReleaseMark = releaseMark(firstChild, obj);
            if (psiElementReleaseMark != null) {
                return psiElementReleaseMark;
            }
        }
        return null;
    }

    public static <T extends PsiElement> T findElementOfClassAtOffset(PsiFile psiFile, int i, Class<T> cls, boolean z) {
        PsiElement parentOfType;
        if (psiFile == null) {
            $$$reportNull$$$0(89);
        }
        if (cls == null) {
            $$$reportNull$$$0(90);
        }
        Iterator<PsiFile> it = psiFile.getViewProvider().getAllFiles().iterator();
        T t = null;
        while (it.hasNext()) {
            PsiElement psiElementFindElementAt = it.next().findElementAt(i);
            if (psiElementFindElementAt != null && (parentOfType = getParentOfType(psiElementFindElementAt, cls, z)) != null) {
                TextRange textRange = parentOfType.getTextRange();
                if (!z || textRange.getStartOffset() == i) {
                    if (t == null || t.getTextRange().getEndOffset() > textRange.getEndOffset()) {
                        t = (T) parentOfType;
                    }
                }
            }
        }
        return t;
    }

    @SafeVarargs
    public static <T extends PsiElement> T findElementOfClassAtOffsetWithStopSet(PsiFile psiFile, int i, Class<T> cls, boolean z, Class<? extends PsiElement>... clsArr) {
        PsiElement parentOfType;
        if (psiFile == null) {
            $$$reportNull$$$0(91);
        }
        if (cls == null) {
            $$$reportNull$$$0(92);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(93);
        }
        Iterator<PsiFile> it = psiFile.getViewProvider().getAllFiles().iterator();
        T t = null;
        while (it.hasNext()) {
            PsiElement psiElementFindElementAt = it.next().findElementAt(i);
            if (psiElementFindElementAt != null && (parentOfType = getParentOfType(psiElementFindElementAt, cls, z, clsArr)) != null) {
                TextRange textRange = parentOfType.getTextRange();
                if (!z || textRange.getStartOffset() == i) {
                    if (t == null || t.getTextRange().getEndOffset() > textRange.getEndOffset()) {
                        t = (T) parentOfType;
                    }
                }
            }
        }
        return t;
    }

    public static <T extends PsiElement> T findElementOfClassAtRange(PsiFile psiFile, int i, int i2, Class<T> cls) {
        if (psiFile == null) {
            $$$reportNull$$$0(94);
        }
        if (cls == null) {
            $$$reportNull$$$0(95);
        }
        FileViewProvider viewProvider = psiFile.getViewProvider();
        Iterator<Language> it = viewProvider.getLanguages().iterator();
        T t = null;
        while (it.hasNext()) {
            PsiElement parentOfType = getParentOfType(viewProvider.findElementAt(i, it.next()), cls, false);
            PsiElement psiElement = parentOfType;
            while (parentOfType != null && parentOfType.getTextRange().getStartOffset() == i && parentOfType.getTextRange().getEndOffset() <= i2) {
                psiElement = parentOfType;
                parentOfType = getParentOfType(parentOfType, cls);
            }
            if (psiElement != null) {
                int startOffset = psiElement.getTextRange().getStartOffset();
                int endOffset = psiElement.getTextRange().getEndOffset();
                if (startOffset == i && endOffset <= i2 && (t == null || t.getTextRange().getEndOffset() < endOffset)) {
                    t = (T) psiElement;
                }
            }
        }
        return t;
    }

    public static PsiElement getDeepestFirst(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(96);
        }
        while (true) {
            PsiElement firstChild = psiElement.getFirstChild();
            if (firstChild == null) {
                break;
            }
            psiElement = firstChild;
        }
        if (psiElement == null) {
            $$$reportNull$$$0(97);
        }
        return psiElement;
    }

    public static PsiElement getDeepestLast(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(98);
        }
        while (true) {
            PsiElement lastChild = psiElement.getLastChild();
            if (lastChild == null) {
                break;
            }
            psiElement = lastChild;
        }
        if (psiElement == null) {
            $$$reportNull$$$0(99);
        }
        return psiElement;
    }

    public static PsiElement prevLeaf(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(100);
        }
        if (psiElement instanceof PsiFileSystemItem) {
            return null;
        }
        PsiElement prevSibling = psiElement.getPrevSibling();
        if (prevSibling != null) {
            return lastChild(prevSibling);
        }
        PsiElement parent = psiElement.getLightParent();
        if (parent == null || (parent instanceof PsiFile)) {
            return null;
        }
        return prevLeaf(parent);
    }

    public static PsiElement nextLeaf(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(101);
        }
        if (psiElement instanceof PsiFileSystemItem) {
            return null;
        }
        PsiElement nextSibling = psiElement.getNextSibling();
        if (nextSibling != null) {
            return firstChild(nextSibling);
        }
        PsiElement parent = psiElement.getLightParent();
        if (parent == null || (parent instanceof PsiFile)) {
            return null;
        }
        return nextLeaf(parent);
    }

    public static PsiElement lastChild(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(102);
        }
        PsiElement lastChild = psiElement.getLastChild();
        if (lastChild != null) {
            return lastChild(lastChild);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(103);
        }
        return psiElement;
    }

    public static PsiElement firstChild(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(104);
        }
        PsiElement firstChild = psiElement.getFirstChild();
        if (firstChild != null) {
            return firstChild(firstChild);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(105);
        }
        return psiElement;
    }

    public static PsiElement prevLeaf(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(106);
        }
        PsiElement psiElementPrevLeaf = prevLeaf(psiElement);
        while (z && psiElementPrevLeaf != null && psiElementPrevLeaf.getTextLength() == 0) {
            psiElementPrevLeaf = prevLeaf(psiElementPrevLeaf);
        }
        return psiElementPrevLeaf;
    }

    public static PsiElement prevVisibleLeaf(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(107);
        }
        PsiElement psiElementPrevLeaf = prevLeaf(psiElement, true);
        while (psiElementPrevLeaf != null && StringUtil.isEmptyOrSpaces(psiElementPrevLeaf.getText())) {
            psiElementPrevLeaf = prevLeaf(psiElementPrevLeaf, true);
        }
        return psiElementPrevLeaf;
    }

    public static PsiElement nextVisibleLeaf(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(108);
        }
        PsiElement psiElementNextLeaf = nextLeaf(psiElement, true);
        while (psiElementNextLeaf != null && StringUtil.isEmptyOrSpaces(psiElementNextLeaf.getText())) {
            psiElementNextLeaf = nextLeaf(psiElementNextLeaf, true);
        }
        return psiElementNextLeaf;
    }

    public static PsiElement prevCodeLeaf(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(109);
        }
        PsiElement psiElementPrevLeaf = prevLeaf(psiElement, true);
        while (psiElementPrevLeaf != null && isNonCodeLeaf(psiElementPrevLeaf)) {
            psiElementPrevLeaf = prevLeaf(psiElementPrevLeaf, true);
        }
        return psiElementPrevLeaf;
    }

    public static PsiElement nextCodeLeaf(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(110);
        }
        PsiElement psiElementNextLeaf = nextLeaf(psiElement, true);
        while (psiElementNextLeaf != null && isNonCodeLeaf(psiElementNextLeaf)) {
            psiElementNextLeaf = nextLeaf(psiElementNextLeaf, true);
        }
        return psiElementNextLeaf;
    }

    private static boolean isNonCodeLeaf(PsiElement psiElement) {
        return StringUtil.isEmptyOrSpaces(psiElement.getText()) || getNonStrictParentOfType(psiElement, PsiComment.class) != null;
    }

    public static PsiElement nextLeaf(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(111);
        }
        PsiElement psiElementNextLeaf = nextLeaf(psiElement);
        while (z && psiElementNextLeaf != null && psiElementNextLeaf.getTextLength() == 0) {
            psiElementNextLeaf = nextLeaf(psiElementNextLeaf);
        }
        return psiElementNextLeaf;
    }

    public static boolean hasErrorElements(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(112);
        }
        return !SyntaxTraverser.psiTraverser(psiElement).traverse().filter(PsiErrorElement.class).isEmpty();
    }

    public static PsiElement[] filterAncestors(PsiElement[] psiElementArr) {
        int size;
        if (psiElementArr == null) {
            $$$reportNull$$$0(113);
        }
        if (LOG.isDebugEnabled()) {
            for (PsiElement psiElement : psiElementArr) {
                LOG.debug("element = " + psiElement);
            }
        }
        ArrayList<PsiElement> arrayList = new ArrayList();
        ContainerUtil.addAll(arrayList, psiElementArr);
        do {
            size = arrayList.size();
            for (PsiElement psiElement2 : arrayList) {
                for (PsiElement psiElement3 : arrayList) {
                    if (psiElement2 != psiElement3 && isAncestor(psiElement2, psiElement3, false)) {
                        Logger logger = LOG;
                        if (logger.isDebugEnabled()) {
                            logger.debug("removing " + psiElement3);
                        }
                        arrayList.remove(psiElement3);
                        break;
                    }
                }
            }
        } while (arrayList.size() != size);
        if (LOG.isDebugEnabled()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                LOG.debug("filtered element = " + ((PsiElement) it.next()));
            }
        }
        PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(114);
        }
        return psiElementArray;
    }

    public static boolean treeWalkUp(PsiScopeProcessor psiScopeProcessor, PsiElement psiElement, PsiElement psiElement2, ResolveState resolveState) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(115);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(116);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(117);
        }
        PsiElement context = psiElement;
        PsiElement psiElement3 = context;
        while (context != null) {
            if (!context.processDeclarations(psiScopeProcessor, resolveState, psiElement3, psiElement)) {
                return false;
            }
            if (context == psiElement2) {
                return true;
            }
            psiElement3 = context;
            context = context.getContext();
        }
        return true;
    }

    public static boolean treeWalkUp(PsiElement psiElement, PsiElement psiElement2, PairProcessor<? super PsiElement, ? super PsiElement> pairProcessor) {
        if (psiElement == null) {
            $$$reportNull$$$0(118);
        }
        if (pairProcessor == null) {
            $$$reportNull$$$0(119);
        }
        PsiElement psiElement3 = null;
        while (psiElement != null) {
            if (!pairProcessor.process(psiElement, psiElement3)) {
                return false;
            }
            if (psiElement == psiElement2) {
                return true;
            }
            psiElement3 = psiElement;
            psiElement = psiElement.getContext();
        }
        return true;
    }

    public static PsiElement findPrevParent(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(120);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(121);
        }
        PsiElement psiElement3 = psiElement2;
        while (psiElement3 != null) {
            PsiElement parent = psiElement3.getLightParent();
            if (parent == psiElement) {
                if (psiElement3 == null) {
                    $$$reportNull$$$0(122);
                }
                return psiElement3;
            }
            psiElement3 = parent;
        }
        throw new AssertionError(psiElement2 + " is not a descendant of " + psiElement);
    }

    public static List<PsiElement> getInjectedElements(OuterLanguageElement outerLanguageElement) {
        if (outerLanguageElement == null) {
            $$$reportNull$$$0(123);
        }
        PsiFile psi = outerLanguageElement.getContainingFile().getViewProvider().getPsi(outerLanguageElement.getLanguage());
        TextRange textRange = outerLanguageElement.getTextRange();
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElementFindElementAt = psi.findElementAt(textRange.getStartOffset()); psiElementFindElementAt != null && textRange.intersectsStrict(psiElementFindElementAt.getTextRange()); psiElementFindElementAt = psiElementFindElementAt.getNextSibling()) {
            arrayList.add(psiElementFindElementAt);
        }
        return arrayList;
    }

    public static PsiElement getDeepestVisibleFirst(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(124);
        }
        PsiElement deepestFirst = getDeepestFirst(psiElement);
        return StringUtil.isEmptyOrSpaces(deepestFirst.getText()) ? nextVisibleLeaf(deepestFirst) : deepestFirst;
    }

    public static PsiElement getDeepestVisibleLast(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(125);
        }
        PsiElement deepestLast = getDeepestLast(psiElement);
        return StringUtil.isEmptyOrSpaces(deepestLast.getText()) ? prevVisibleLeaf(deepestLast) : deepestLast;
    }

    public static <T extends PsiElement> T findSameElementInCopy(T t, PsiFile psiFile) throws IllegalStateException {
        PsiElement psiElement;
        if (psiFile == null) {
            $$$reportNull$$$0(126);
        }
        if (t == null) {
            return null;
        }
        IntArrayList intArrayList = new IntArrayList();
        PsiElement parent = t;
        do {
            int i = 0;
            if (!parent.getClass().equals(psiFile.getClass())) {
                for (PsiElement prevSibling = parent.getPrevSibling(); prevSibling != null; prevSibling = prevSibling.getPrevSibling()) {
                    i++;
                }
                intArrayList.add(i);
                parent = parent.getLightParent();
            } else {
                int size = intArrayList.size() - 1;
                PsiElement psiElement2 = psiFile;
                while (size >= 0) {
                    int i2 = intArrayList.get(size);
                    PsiElement firstChild = psiElement2.getFirstChild();
                    if (firstChild == null) {
                        throw new IllegalStateException("File structure differs: no child");
                    }
                    int i3 = 0;
                    while (i3 < i2) {
                        PsiElement psiElement3 = (T) psiElement.getNextSibling();
                        if (psiElement3 == null) {
                            psiElement = firstChild;
                            throw new IllegalStateException("File structure differs: number of siblings is less than " + i2);
                        }
                        psiElement = firstChild;
                        i3++;
                        psiElement = psiElement3;
                    }
                    psiElement = firstChild;
                    size--;
                    psiElement2 = psiElement;
                }
                if (psiElement2.getClass().equals(t.getClass())) {
                    return (T) psiElement2;
                }
                throw new IllegalStateException("File structure differs: " + psiElement2.getClass() + " != " + t.getClass());
            }
        } while (parent != null);
        throw new IllegalStateException("Cannot find parent file; element class: " + t.getClass());
    }
}
