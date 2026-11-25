package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.lang.LanguageMatcher;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LocalSearchScope extends SearchScope {
    private final String myDisplayName;
    private final boolean myIgnoreInjectedPsi;
    private final PsiElement[] myScope;
    private final VirtualFile[] myVirtualFiles;
    private static final Logger LOG = Logger.getInstance((Class<?>) LocalSearchScope.class);
    public static final LocalSearchScope EMPTY = new LocalSearchScope(PsiElement.EMPTY_ARRAY);

    /* JADX WARN: Code duplicated, block: B:16:0x0031  */
    /* JADX WARN: Code duplicated, block: B:61:0x00b7  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 5 && i != 6 && i != 7 && i != 9 && i != 24 && i != 20 && i != 21 && i != 32 && i != 33 && i != 36 && i != 37) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 5 && i != 6 && i != 7 && i != 9 && i != 24 && i != 20 && i != 21 && i != 32 && i != 33 && i != 36 && i != 37) {
            switch (i) {
                case 13:
                case 14:
                case 15:
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
            case 5:
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
            case 15:
            case 20:
            case 21:
            case 24:
            case 32:
            case 33:
            case 36:
            case 37:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/LocalSearchScope";
                break;
            case 8:
            case 11:
            case 12:
            case 23:
                objArr[0] = "scope2";
                break;
            case 10:
                objArr[0] = "scope1";
                break;
            case 16:
            case 19:
            case 30:
            case 34:
            default:
                objArr[0] = "scope";
                break;
            case 17:
            case 25:
                objArr[0] = "element1";
                break;
            case 18:
            case 26:
                objArr[0] = "element2";
                break;
            case 22:
            case 27:
            case 28:
                objArr[0] = "file";
                break;
            case 29:
                objArr[0] = "range";
                break;
            case 31:
                objArr[0] = "fileTypes";
                break;
            case 35:
                objArr[0] = "matcher";
                break;
        }
        if (i == 5) {
            objArr[1] = "getDisplayName";
        } else if (i == 6) {
            objArr[1] = "getScope";
        } else if (i == 7) {
            objArr[1] = "getVirtualFiles";
        } else if (i != 9) {
            if (i != 24 && i != 20 && i != 21) {
                if (i != 32 && i != 33) {
                    if (i != 36 && i != 37) {
                        switch (i) {
                            case 13:
                            case 14:
                            case 15:
                                objArr[1] = "intersectWith";
                                break;
                            default:
                                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/LocalSearchScope";
                                break;
                        }
                    } else {
                        objArr[1] = "getScopeRestrictedByFileLanguage";
                    }
                } else {
                    objArr[1] = "getScopeRestrictedByFileTypes";
                }
            } else {
                objArr[1] = SchemaSymbols.ATTVAL_UNION;
            }
        } else {
            objArr[1] = "intersectWith";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
            case 15:
            case 20:
            case 21:
            case 24:
            case 32:
            case 33:
            case 36:
            case 37:
                break;
            case 8:
            case 12:
                objArr[2] = "intersectWith";
                break;
            case 10:
            case 11:
                objArr[2] = "intersection";
                break;
            case 16:
                objArr[2] = "tryIntersectNonPhysicalWith";
                break;
            case 17:
            case 18:
                objArr[2] = "intersectScopeElements";
                break;
            case 19:
            case 23:
                objArr[2] = SchemaSymbols.ATTVAL_UNION;
                break;
            case 22:
                objArr[2] = "contains";
                break;
            case 25:
            case 26:
                objArr[2] = "scopeElementsUnion";
                break;
            case 27:
                objArr[2] = "isInScope";
                break;
            case 28:
            case 29:
                objArr[2] = "containsRange";
                break;
            case 30:
            case 31:
                objArr[2] = "getScopeRestrictedByFileTypes";
                break;
            case 34:
            case 35:
                objArr[2] = "getScopeRestrictedByFileLanguage";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 7 && i != 9 && i != 24 && i != 20 && i != 21 && i != 32 && i != 33 && i != 36 && i != 37) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LocalSearchScope(PsiElement psiElement) {
        this(psiElement, (String) null);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LocalSearchScope(PsiElement psiElement, String str) {
        this(new PsiElement[]{psiElement}, str);
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LocalSearchScope(PsiElement[] psiElementArr) {
        this(psiElementArr, (String) null);
        if (psiElementArr == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LocalSearchScope(PsiElement[] psiElementArr, String str) {
        this(psiElementArr, str, false);
        if (psiElementArr == null) {
            $$$reportNull$$$0(3);
        }
    }

    public LocalSearchScope(PsiElement[] psiElementArr, String str, boolean z) {
        if (psiElementArr == null) {
            $$$reportNull$$$0(4);
        }
        this.myIgnoreInjectedPsi = z;
        this.myDisplayName = str;
        LinkedHashSet linkedHashSet = new LinkedHashSet(psiElementArr.length);
        HashSet hashSet = new HashSet(psiElementArr.length);
        int length = psiElementArr.length;
        for (int i = 0; i < length; i++) {
            PsiElement psiElement = psiElementArr[i];
            Logger logger = LOG;
            logger.assertTrue(psiElement != null, "null element");
            PsiFile containingFile = psiElement.getContainingFile();
            logger.assertTrue(containingFile != null, psiElement.getClass().getName());
            if (psiElement instanceof PsiFile) {
                for (PsiFile psiFile : ((PsiFile) psiElement).getViewProvider().getAllFiles()) {
                    if (psiFile == null) {
                        throw new IllegalArgumentException("file " + psiElement + " returned null in its getAllFiles()");
                    }
                    linkedHashSet.add(psiFile);
                }
            } else if ((psiElement instanceof StubBasedPsiElement) || psiElement.getTextRange() != null) {
                linkedHashSet.add(psiElement);
            }
            VirtualFile virtualFile = PsiUtilCore.getVirtualFile(containingFile);
            if (virtualFile != null) {
                hashSet.add(virtualFile);
            }
        }
        this.myScope = PsiUtilCore.toPsiElementArray(linkedHashSet);
        this.myVirtualFiles = VfsUtilCore.toVirtualFileArray(hashSet);
    }

    public boolean isIgnoreInjectedPsi() {
        return this.myIgnoreInjectedPsi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public String getDisplayName() {
        String displayName = this.myDisplayName;
        if (displayName == null) {
            displayName = super.getDisplayName();
        }
        if (displayName == null) {
            $$$reportNull$$$0(5);
        }
        return displayName;
    }

    public PsiElement[] getScope() {
        PsiElement[] psiElementArr = this.myScope;
        if (psiElementArr == null) {
            $$$reportNull$$$0(6);
        }
        return psiElementArr;
    }

    public VirtualFile[] getVirtualFiles() {
        VirtualFile[] virtualFileArr = this.myVirtualFiles;
        if (virtualFileArr == null) {
            $$$reportNull$$$0(7);
        }
        return virtualFileArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocalSearchScope)) {
            return false;
        }
        LocalSearchScope localSearchScope = (LocalSearchScope) obj;
        if (localSearchScope.myIgnoreInjectedPsi != this.myIgnoreInjectedPsi || localSearchScope.myScope.length != this.myScope.length || !Comparing.strEqual(this.myDisplayName, localSearchScope.myDisplayName)) {
            return false;
        }
        for (PsiElement psiElement : this.myScope) {
            for (PsiElement psiElement2 : localSearchScope.myScope) {
                if (!Comparing.equal(psiElement, psiElement2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    protected int calcHashCode() {
        boolean z = this.myIgnoreInjectedPsi;
        PsiElement[] psiElementArr = this.myScope;
        int length = psiElementArr.length;
        int i = 0;
        int i2 = z;
        while (i < length) {
            int iHashCode = i2 + psiElementArr[i].hashCode();
            i++;
            i2 = iHashCode;
        }
        return i2;
    }

    public LocalSearchScope intersectWith(LocalSearchScope localSearchScope) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(8);
        }
        return equals(localSearchScope) ? this : intersection(this, localSearchScope);
    }

    private static LocalSearchScope intersection(LocalSearchScope localSearchScope, LocalSearchScope localSearchScope2) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(10);
        }
        if (localSearchScope2 == null) {
            $$$reportNull$$$0(11);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElement : localSearchScope.myScope) {
            for (PsiElement psiElement2 : localSearchScope2.myScope) {
                PsiElement psiElementIntersectScopeElements = intersectScopeElements(psiElement, psiElement2);
                if (psiElementIntersectScopeElements != null) {
                    arrayList.add(psiElementIntersectScopeElements);
                }
            }
        }
        return new LocalSearchScope(PsiUtilCore.toPsiElementArray(arrayList), null, localSearchScope.myIgnoreInjectedPsi || localSearchScope2.myIgnoreInjectedPsi);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public SearchScope intersectWith(SearchScope searchScope) {
        if (searchScope == null) {
            $$$reportNull$$$0(12);
        }
        if (searchScope instanceof LocalSearchScope) {
            LocalSearchScope localSearchScopeIntersectWith = intersectWith((LocalSearchScope) searchScope);
            if (localSearchScopeIntersectWith == null) {
                $$$reportNull$$$0(13);
            }
            return localSearchScopeIntersectWith;
        }
        GlobalSearchScope globalSearchScope = (GlobalSearchScope) searchScope;
        LocalSearchScope localSearchScopeTryIntersectNonPhysicalWith = tryIntersectNonPhysicalWith(globalSearchScope);
        if (localSearchScopeTryIntersectNonPhysicalWith != null) {
            if (localSearchScopeTryIntersectNonPhysicalWith == null) {
                $$$reportNull$$$0(14);
            }
            return localSearchScopeTryIntersectNonPhysicalWith;
        }
        LocalSearchScope localSearchScopeIntersectWith2 = globalSearchScope.intersectWith(this);
        if (localSearchScopeIntersectWith2 == null) {
            $$$reportNull$$$0(15);
        }
        return localSearchScopeIntersectWith2;
    }

    private LocalSearchScope tryIntersectNonPhysicalWith(GlobalSearchScope globalSearchScope) {
        if (globalSearchScope == null) {
            $$$reportNull$$$0(16);
        }
        Project project = globalSearchScope.getProject();
        for (PsiElement psiElement : this.myScope) {
            PsiFile containingFile = psiElement.getContainingFile();
            if (containingFile != null) {
                if (containingFile.getViewProvider().isPhysical()) {
                    return null;
                }
                if (project != null && project != containingFile.getProject()) {
                    return EMPTY;
                }
            }
        }
        return this;
    }

    private static PsiElement intersectScopeElements(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(18);
        }
        if (PsiTreeUtil.isContextAncestor(psiElement, psiElement2, false)) {
            return psiElement2;
        }
        if (PsiTreeUtil.isContextAncestor(psiElement2, psiElement, false)) {
            return psiElement;
        }
        if (PsiTreeUtil.isAncestor(psiElement, psiElement2, false)) {
            return psiElement2;
        }
        if (PsiTreeUtil.isAncestor(psiElement2, psiElement, false)) {
            return psiElement;
        }
        return null;
    }

    public String toString() {
        return (String) Arrays.stream(this.myScope).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return String.valueOf((PsiElement) obj);
            }
        }).collect(Collectors.joining(",", "LocalSearchScope:", ""));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public SearchScope union(SearchScope searchScope) {
        if (searchScope == null) {
            $$$reportNull$$$0(19);
        }
        if (searchScope instanceof LocalSearchScope) {
            SearchScope searchScopeUnion = union((LocalSearchScope) searchScope);
            if (searchScopeUnion == null) {
                $$$reportNull$$$0(20);
            }
            return searchScopeUnion;
        }
        GlobalSearchScope globalSearchScopeUnion = ((GlobalSearchScope) searchScope).union(this);
        if (globalSearchScopeUnion == null) {
            $$$reportNull$$$0(21);
        }
        return globalSearchScopeUnion;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
    public boolean contains(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(22);
        }
        return isInScope(virtualFile);
    }

    public SearchScope union(LocalSearchScope localSearchScope) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(23);
        }
        if (equals(localSearchScope)) {
            return this;
        }
        PsiElement[] scope = getScope();
        PsiElement[] scope2 = localSearchScope.getScope();
        int length = scope2.length;
        boolean[] zArr = new boolean[length];
        ArrayList arrayList = new ArrayList();
        loop0: for (PsiElement psiElement : scope) {
            for (int i = 0; i < scope2.length; i++) {
                PsiElement psiElementScopeElementsUnion = scopeElementsUnion(psiElement, scope2[i]);
                if (psiElementScopeElementsUnion != null && psiElementScopeElementsUnion.getContainingFile() != null) {
                    arrayList.add(psiElementScopeElementsUnion);
                    zArr[i] = true;
                    break loop0;
                }
            }
            arrayList.add(psiElement);
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (!zArr[i2]) {
                arrayList.add(scope2[i2]);
            }
        }
        return new LocalSearchScope(PsiUtilCore.toPsiElementArray(arrayList));
    }

    private static PsiElement scopeElementsUnion(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(25);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(26);
        }
        if (PsiTreeUtil.isAncestor(psiElement, psiElement2, false)) {
            return psiElement;
        }
        return PsiTreeUtil.isAncestor(psiElement2, psiElement, false) ? psiElement2 : PsiTreeUtil.findCommonParent(psiElement, psiElement2);
    }

    public boolean isInScope(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(27);
        }
        return ArrayUtil.indexOf(this.myVirtualFiles, virtualFile) != -1;
    }

    public boolean containsRange(PsiFile psiFile, TextRange textRange) {
        if (psiFile == null) {
            $$$reportNull$$$0(28);
        }
        if (textRange == null) {
            $$$reportNull$$$0(29);
        }
        for (PsiElement psiElement : getScope()) {
            if (psiFile == psiElement.getContainingFile() && psiElement.getTextRange().contains(textRange)) {
                return true;
            }
        }
        return false;
    }

    public static LocalSearchScope getScopeRestrictedByFileTypes(final LocalSearchScope localSearchScope, final FileType... fileTypeArr) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(30);
        }
        if (fileTypeArr == null) {
            $$$reportNull$$$0(31);
        }
        if (fileTypeArr.length == 0) {
            throw new IllegalArgumentException("empty fileTypes");
        }
        LocalSearchScope localSearchScope2 = EMPTY;
        if (localSearchScope == localSearchScope2) {
            if (localSearchScope2 == null) {
                $$$reportNull$$$0(32);
            }
            return localSearchScope2;
        }
        LocalSearchScope localSearchScope3 = (LocalSearchScope) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return LocalSearchScope.lambda$getScopeRestrictedByFileTypes$0(this.f$0, fileTypeArr);
            }
        });
        if (localSearchScope3 == null) {
            $$$reportNull$$$0(33);
        }
        return localSearchScope3;
    }

    static /* synthetic */ LocalSearchScope lambda$getScopeRestrictedByFileTypes$0(LocalSearchScope localSearchScope, FileType[] fileTypeArr) throws RuntimeException {
        PsiElement[] scope = localSearchScope.getScope();
        ArrayList arrayList = new ArrayList(scope.length);
        for (PsiElement psiElement : scope) {
            if (ArrayUtil.contains(psiElement.getContainingFile().getFileType(), fileTypeArr)) {
                arrayList.add(psiElement);
            }
        }
        if (arrayList.isEmpty()) {
            return EMPTY;
        }
        return new LocalSearchScope(PsiUtilCore.toPsiElementArray(arrayList), localSearchScope.getDisplayName(), localSearchScope.isIgnoreInjectedPsi());
    }

    static LocalSearchScope getScopeRestrictedByFileLanguage(final LocalSearchScope localSearchScope, final LanguageMatcher languageMatcher) {
        if (localSearchScope == null) {
            $$$reportNull$$$0(34);
        }
        if (languageMatcher == null) {
            $$$reportNull$$$0(35);
        }
        LocalSearchScope localSearchScope2 = EMPTY;
        if (localSearchScope == localSearchScope2) {
            if (localSearchScope2 == null) {
                $$$reportNull$$$0(36);
            }
            return localSearchScope2;
        }
        LocalSearchScope localSearchScope3 = (LocalSearchScope) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return LocalSearchScope.lambda$getScopeRestrictedByFileLanguage$2(this.f$0, languageMatcher);
            }
        });
        if (localSearchScope3 == null) {
            $$$reportNull$$$0(37);
        }
        return localSearchScope3;
    }

    static /* synthetic */ LocalSearchScope lambda$getScopeRestrictedByFileLanguage$2(LocalSearchScope localSearchScope, final LanguageMatcher languageMatcher) throws RuntimeException {
        List listFilter = ContainerUtil.filter(localSearchScope.getScope(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return languageMatcher.matchesLanguage(((PsiElement) obj).getContainingFile().getLanguage());
            }
        });
        if (listFilter.isEmpty()) {
            return EMPTY;
        }
        return new LocalSearchScope(PsiUtilCore.toPsiElementArray(listFilter), localSearchScope.getDisplayName(), localSearchScope.isIgnoreInjectedPsi());
    }
}
