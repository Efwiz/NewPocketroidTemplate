package org.jetbrains.kotlin.com.intellij.psi.util;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.Icon;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaData;
import org.jetbrains.kotlin.com.intellij.psi.meta.PsiMetaOwner;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.TimeoutUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiUtilCore {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiUtilCore.class);
    public static final PsiElement NULL_PSI_ELEMENT = new NullPsiElement();
    public static final PsiFile NULL_PSI_FILE = new NullPsiFile();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 4 || i == 10 || i == 16 || i == 23 || i == 25 || i == 6 || i == 7 || i == 20 || i == 21) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 4 || i == 10 || i == 16 || i == 23 || i == 25 || i == 6 || i == 7 || i == 20 || i == 21) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 6:
            case 7:
            case 10:
            case 16:
            case 20:
            case 21:
            case 23:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiUtilCore";
                break;
            case 2:
            case 11:
            case 13:
            case 24:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 3:
            case 15:
            case 22:
                objArr[0] = "file";
                break;
            case 5:
            default:
                objArr[0] = "collection";
                break;
            case 8:
                objArr[0] = "psiManager";
                break;
            case 9:
                objArr[0] = "virtualFiles";
                break;
            case 12:
                objArr[0] = "candidate";
                break;
            case 14:
                objArr[0] = "project";
                break;
            case 17:
                objArr[0] = "psiElement";
                break;
            case 18:
                objArr[0] = "elementClass";
                break;
            case 19:
                objArr[0] = "elt";
                break;
        }
        if (i == 1) {
            objArr[1] = "toPsiElementArray";
        } else if (i == 4) {
            objArr[1] = "getElementAtOffset";
        } else if (i == 10) {
            objArr[1] = "toPsiFiles";
        } else if (i == 16) {
            objArr[1] = "getPsiFile";
        } else if (i == 23) {
            objArr[1] = "getLanguageAtOffset";
        } else if (i == 25) {
            objArr[1] = "getProjectInReadAction";
        } else if (i == 6 || i == 7) {
            objArr[1] = "toPsiFileArray";
        } else if (i == 20 || i == 21) {
            objArr[1] = "findLanguageFromElement";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiUtilCore";
        }
        switch (i) {
            case 1:
            case 4:
            case 6:
            case 7:
            case 10:
            case 16:
            case 20:
            case 21:
            case 23:
            case 25:
                break;
            case 2:
                objArr[2] = "hasErrorElementChild";
                break;
            case 3:
                objArr[2] = "getElementAtOffset";
                break;
            case 5:
                objArr[2] = "toPsiFileArray";
                break;
            case 8:
            case 9:
                objArr[2] = "toPsiFiles";
                break;
            case 11:
                objArr[2] = "getDialect";
                break;
            case 12:
                objArr[2] = "narrowLanguage";
                break;
            case 13:
                objArr[2] = "ensureValid";
                break;
            case 14:
            case 15:
                objArr[2] = "getPsiFile";
                break;
            case 17:
            case 18:
                objArr[2] = "getOriginalElement";
                break;
            case 19:
                objArr[2] = "findLanguageFromElement";
                break;
            case 22:
                objArr[2] = "getLanguageAtOffset";
                break;
            case 24:
                objArr[2] = "getProjectInReadAction";
                break;
            default:
                objArr[2] = "toPsiElementArray";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4 && i != 10 && i != 16 && i != 23 && i != 25 && i != 6 && i != 7 && i != 20 && i != 21) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected static class NullPsiElement implements PsiElement {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 4:
                case 5:
                case 6:
                case 7:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
                case 2:
                case 3:
                    objArr[0] = "visitor";
                    break;
                case 8:
                    objArr[0] = "first";
                    break;
                case 9:
                    objArr[0] = "last";
                    break;
                case 10:
                    objArr[0] = "newElement";
                    break;
                case 11:
                    objArr[0] = "processor";
                    break;
                case 12:
                    objArr[0] = "state";
                    break;
                case 13:
                    objArr[0] = "place";
                    break;
                case 14:
                case 15:
                    objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                    break;
                default:
                    objArr[0] = "text";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiUtilCore$NullPsiElement";
            switch (i) {
                case 2:
                    objArr[2] = "accept";
                    break;
                case 3:
                    objArr[2] = "acceptChildren";
                    break;
                case 4:
                    objArr[2] = ProductAction.ACTION_ADD;
                    break;
                case 5:
                    objArr[2] = "addBefore";
                    break;
                case 6:
                    objArr[2] = "addAfter";
                    break;
                case 7:
                    objArr[2] = "checkAdd";
                    break;
                case 8:
                case 9:
                    objArr[2] = "addRangeBefore";
                    break;
                case 10:
                    objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                    break;
                case 11:
                case 12:
                case 13:
                    objArr[2] = "processDeclarations";
                    break;
                case 14:
                    objArr[2] = "getUserData";
                    break;
                case 15:
                    objArr[2] = "putUserData";
                    break;
                default:
                    objArr[2] = "textMatches";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean isEquivalentTo(PsiElement psiElement) {
            return this == psiElement;
        }

        protected NullPsiElement() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public Project getProject() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public Language getLanguage() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiManager getManager() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement[] getChildren() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent */
        public PsiElement getLightParent() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getFirstChild() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getLastChild() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getNextSibling() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getPrevSibling() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiFile getContainingFile() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public TextRange getTextRange() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getStartOffsetInParent() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextLength() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement findElementAt(int i) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference findReferenceAt(int i) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public int getTextOffset() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String getText() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public char[] textToCharArray() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getNavigationElement() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getOriginalElement() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textMatches(CharSequence charSequence) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textMatches(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean textContains(char c) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void accept(PsiElementVisitor psiElementVisitor) {
            if (psiElementVisitor == null) {
                $$$reportNull$$$0(2);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void acceptChildren(PsiElementVisitor psiElementVisitor) {
            if (psiElementVisitor == null) {
                $$$reportNull$$$0(3);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement copy() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement add(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(4);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) {
            if (psiElement == null) {
                $$$reportNull$$$0(5);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) {
            if (psiElement == null) {
                $$$reportNull$$$0(6);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void checkAdd(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(7);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRange(PsiElement psiElement, PsiElement psiElement2) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRangeBefore(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) {
            if (psiElement == null) {
                $$$reportNull$$$0(8);
            }
            if (psiElement2 == null) {
                $$$reportNull$$$0(9);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void delete() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void checkDelete() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public void deleteChildRange(PsiElement psiElement, PsiElement psiElement2) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement replace(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(10);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
        public boolean isValid() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean isWritable() {
            throw createException();
        }

        PsiInvalidElementAccessException createException() {
            return new PsiInvalidElementAccessException(this, toString(), null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference getReference() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiReference[] getReferences() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public <T> T getCopyableUserData(Key<T> key) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public <T> void putCopyableUserData(Key<T> key, T t) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
            if (psiScopeProcessor == null) {
                $$$reportNull$$$0(11);
            }
            if (resolveState == null) {
                $$$reportNull$$$0(12);
            }
            if (psiElement2 == null) {
                $$$reportNull$$$0(13);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public PsiElement getContext() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public boolean isPhysical() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public GlobalSearchScope getResolveScope() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public SearchScope getUseScope() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public ASTNode getNode() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
        public <T> T getUserData(Key<T> key) {
            if (key == null) {
                $$$reportNull$$$0(14);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
        public <T> void putUserData(Key<T> key, T t) {
            if (key == null) {
                $$$reportNull$$$0(15);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
        public Icon getIcon(int i) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String toString() {
            return "NULL_PSI_ELEMENT";
        }
    }

    public static PsiElement[] toPsiElementArray(Collection<? extends PsiElement> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        PsiElement[] psiElementArr = collection.isEmpty() ? PsiElement.EMPTY_ARRAY : (PsiElement[]) collection.toArray(PsiElement.EMPTY_ARRAY);
        if (psiElementArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiElementArr;
    }

    public static Language getNotAnyLanguage(ASTNode aSTNode) {
        if (aSTNode == null) {
            return Language.ANY;
        }
        Language language = aSTNode.getElementType().getLanguage();
        return language == Language.ANY ? getNotAnyLanguage(aSTNode.getTreeParent()) : language;
    }

    public static VirtualFile getVirtualFile(PsiElement psiElement) {
        PsiFile originalFile;
        if (psiElement == null) {
            return null;
        }
        if (psiElement instanceof PsiFileSystemItem) {
            if (psiElement.isValid()) {
                return ((PsiFileSystemItem) psiElement).getVirtualFile();
            }
            return null;
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null || !containingFile.isValid()) {
            return null;
        }
        VirtualFile virtualFile = containingFile.getVirtualFile();
        return (virtualFile == null && (originalFile = containingFile.getOriginalFile()) != containingFile && originalFile.isValid()) ? originalFile.getVirtualFile() : virtualFile;
    }

    public static int compareElementsByPosition(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null || psiElement2 == null || psiElement.equals(psiElement2)) {
            return 0;
        }
        PsiFile containingFile = psiElement.getContainingFile();
        PsiFile containingFile2 = psiElement2.getContainingFile();
        if (Comparing.equal(containingFile, containingFile2)) {
            TextRange textRange = psiElement.getTextRange();
            TextRange textRange2 = psiElement2.getTextRange();
            if (textRange != null && textRange2 != null) {
                return textRange.getStartOffset() - textRange2.getStartOffset();
            }
        } else if (containingFile != null && containingFile2 != null) {
            return containingFile.getName().compareToIgnoreCase(containingFile2.getName());
        }
        return 0;
    }

    public static boolean hasErrorElementChild(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof PsiErrorElement) {
                return true;
            }
        }
        return false;
    }

    public static PsiElement getElementAtOffset(PsiFile psiFile, int i) {
        if (psiFile == null) {
            $$$reportNull$$$0(3);
        }
        PsiElement psiElementFindElementAt = psiFile.findElementAt(i);
        if (psiElementFindElementAt == null && i > 0) {
            psiElementFindElementAt = psiFile.findElementAt(i - 1);
        }
        if (psiElementFindElementAt != null) {
            psiFile = psiElementFindElementAt;
        }
        if (psiFile == null) {
            $$$reportNull$$$0(4);
        }
        return psiFile;
    }

    public static PsiFile getTemplateLanguageFile(PsiElement psiElement) {
        PsiFile containingFile;
        if (psiElement == null || (containingFile = psiElement.getContainingFile()) == null) {
            return null;
        }
        FileViewProvider viewProvider = containingFile.getViewProvider();
        return viewProvider.getPsi(viewProvider.getBaseLanguage());
    }

    public static PsiFile[] toPsiFileArray(Collection<? extends PsiFile> collection) {
        if (collection == null) {
            $$$reportNull$$$0(5);
        }
        if (collection.isEmpty()) {
            PsiFile[] psiFileArr = PsiFile.EMPTY_ARRAY;
            if (psiFileArr == null) {
                $$$reportNull$$$0(6);
            }
            return psiFileArr;
        }
        PsiFile[] psiFileArr2 = (PsiFile[]) collection.toArray(PsiFile.EMPTY_ARRAY);
        if (psiFileArr2 == null) {
            $$$reportNull$$$0(7);
        }
        return psiFileArr2;
    }

    public static <VF extends VirtualFile> List<PsiFile> toPsiFiles(final PsiManager psiManager, Collection<VF> collection) {
        if (psiManager == null) {
            $$$reportNull$$$0(8);
        }
        if (collection == null) {
            $$$reportNull$$$0(9);
        }
        Stream<VF> stream = collection.stream();
        Objects.requireNonNull(psiManager);
        List<PsiFile> list = (List) stream.map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return psiManager.findFile((VirtualFile) obj);
            }
        }).filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((PsiFile) obj);
            }
        }).collect(Collectors.toList());
        if (list == null) {
            $$$reportNull$$$0(10);
        }
        return list;
    }

    public static String getName(PsiElement psiElement) {
        PsiMetaData metaData;
        String name2 = (!(psiElement instanceof PsiMetaOwner) || (metaData = ((PsiMetaOwner) psiElement).getMetaData()) == null) ? null : metaData.getName(psiElement);
        return (name2 == null && (psiElement instanceof PsiNamedElement)) ? ((PsiNamedElement) psiElement).getName() : name2;
    }

    public static String getQualifiedNameAfterRename(String str, String str2) {
        int iLastIndexOf;
        return (str != null && (iLastIndexOf = str.lastIndexOf(46)) >= 0) ? str.substring(0, iLastIndexOf + 1) + str2 : str2;
    }

    public static Language getDialect(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        return narrowLanguage(psiElement.getLanguage(), psiElement.getContainingFile().getLanguage());
    }

    protected static Language narrowLanguage(Language language, Language language2) {
        if (language2 == null) {
            $$$reportNull$$$0(12);
        }
        return language2.isKindOf(language) ? language2 : language;
    }

    public static void ensureValid(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement.isValid()) {
            return;
        }
        TimeoutUtil.sleep(1L);
        if (psiElement.isValid()) {
            LOG.error("PSI resurrected: " + psiElement + " of " + psiElement.getClass());
            return;
        }
        throw PluginException.createByClass(new PsiInvalidElementAccessException(psiElement), psiElement.getClass());
    }

    public static PsiFileSystemItem findFileSystemItem(Project project, VirtualFile virtualFile) {
        if (project == null || virtualFile == null || project.isDisposed() || !virtualFile.isValid()) {
            return null;
        }
        PsiManager psiManager = PsiManager.getInstance(project);
        return virtualFile.isDirectory() ? psiManager.findDirectory(virtualFile) : psiManager.findFile(virtualFile);
    }

    public static PsiFile getPsiFile(Project project, VirtualFile virtualFile) {
        if (project == null) {
            $$$reportNull$$$0(14);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(15);
        }
        PsiManager psiManager = PsiManager.getInstance(project);
        PsiFile psiFileFindFile = psiManager.findFile(virtualFile);
        if (psiFileFindFile != null) {
            if (psiFileFindFile == null) {
                $$$reportNull$$$0(16);
            }
            return psiFileFindFile;
        }
        FileType fileType = virtualFile.getFileType();
        FileViewProvider fileViewProviderFindViewProvider = psiManager.findViewProvider(virtualFile);
        Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
        boolean z = !(virtualFile instanceof LightVirtualFile) && FileTypeRegistry.getInstance().isFileIgnored(virtualFile);
        VirtualFile parent = virtualFile.getParent();
        PsiDirectory psiDirectoryFindDirectory = parent == null ? null : PsiManager.getInstance(project).findDirectory(parent);
        FileIndexFacade fileIndexFacade = FileIndexFacade.getInstance(project);
        StringBuilder sb = new StringBuilder("valid=");
        sb.append(virtualFile.isValid()).append(" isDirectory=").append(virtualFile.isDirectory()).append(" hasDocument=").append(document != null).append(" length=").append(virtualFile.getLength());
        sb.append("\nproject=").append(project.getName()).append(" default=").append(project.isDefault()).append(" open=").append(project.isOpen());
        sb.append("\nfileType=").append(fileType.getName()).append("/").append(fileType.getClass().getName());
        sb.append("\nisIgnored=").append(z);
        sb.append(" underIgnored=").append(fileIndexFacade.isUnderIgnored(virtualFile));
        sb.append(" inLibrary=").append(fileIndexFacade.isInLibrarySource(virtualFile) || fileIndexFacade.isInLibraryClasses(virtualFile));
        sb.append(" parentDir=").append(parent == null ? "no-vfs" : parent.isDirectory() ? "has-vfs-dir" : "has-vfs-file").append("/").append(psiDirectoryFindDirectory == null ? "no-psi" : "has-psi");
        sb.append("\nviewProvider=").append(fileViewProviderFindViewProvider == null ? "null" : fileViewProviderFindViewProvider.getClass().getName());
        if (fileViewProviderFindViewProvider != null) {
            List<PsiFile> allFiles = fileViewProviderFindViewProvider.getAllFiles();
            sb.append(" language=").append(fileViewProviderFindViewProvider.getBaseLanguage().getID());
            sb.append(" physical=").append(fileViewProviderFindViewProvider.isPhysical());
            sb.append(" rootCount=").append(allFiles.size());
            for (PsiFile psiFile : allFiles) {
                sb.append("\n  root=").append(psiFile.getLanguage().getID()).append("/").append(psiFile.getClass().getName());
            }
        }
        LOG.error("no PSI for file '" + virtualFile.getName() + FormatHelper.QUOTE, new Attachment(virtualFile.getPresentableUrl(), sb.toString()));
        throw new AssertionError();
    }

    @Deprecated
    public static <T extends PsiElement> T getOriginalElement(T t, Class<? extends T> cls) {
        if (t == null) {
            $$$reportNull$$$0(17);
        }
        if (cls == null) {
            $$$reportNull$$$0(18);
        }
        PsiFile containingFile = t.getContainingFile();
        PsiFile originalFile = containingFile.getOriginalFile();
        if (originalFile == containingFile) {
            return t;
        }
        TextRange textRange = t.getTextRange();
        PsiElement psiElementFindElementAt = originalFile.findElementAt(textRange.getStartOffset());
        int length = textRange.getLength();
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElementFindElementAt, cls, false);
        T t2 = (T) parentOfType;
        while (parentOfType != null && parentOfType.getTextLength() <= length) {
            t2 = (T) parentOfType;
            parentOfType = PsiTreeUtil.getParentOfType(parentOfType, cls, true);
        }
        return t2;
    }

    public static Language findLanguageFromElement(PsiElement psiElement) {
        PsiElement parent;
        if (psiElement == null) {
            $$$reportNull$$$0(19);
        }
        if (!(psiElement instanceof PsiFile) && psiElement.getFirstChild() == null && (parent = psiElement.getLightParent()) != null) {
            Language language = parent.getLanguage();
            if (language == null) {
                $$$reportNull$$$0(20);
            }
            return language;
        }
        Language language2 = psiElement.getLanguage();
        if (language2 == null) {
            $$$reportNull$$$0(21);
        }
        return language2;
    }

    public static Language getLanguageAtOffset(PsiFile psiFile, int i) {
        if (psiFile == null) {
            $$$reportNull$$$0(22);
        }
        PsiElement psiElementFindElementAt = psiFile.findElementAt(i);
        if (psiElementFindElementAt == null) {
            Language language = psiFile.getLanguage();
            if (language == null) {
                $$$reportNull$$$0(23);
            }
            return language;
        }
        if (psiElementFindElementAt instanceof PsiWhiteSpace) {
            TextRange textRange = psiElementFindElementAt.getTextRange();
            if (!textRange.contains(i)) {
                LOG.error("PSI corrupted: in file " + psiFile + " (" + psiFile.getViewProvider().getVirtualFile() + ") offset=" + i + " returned element " + psiElementFindElementAt + " with text range " + textRange);
            }
            int startOffset = textRange.getStartOffset() - 1;
            if (startOffset >= 0) {
                return getLanguageAtOffset(psiFile, startOffset);
            }
        }
        return findLanguageFromElement(psiElementFindElementAt);
    }

    public static Project getProjectInReadAction(final PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(24);
        }
        Project project = (Project) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return psiElement.getProject();
            }
        });
        if (project == null) {
            $$$reportNull$$$0(25);
        }
        return project;
    }

    public static IElementType getElementType(ASTNode aSTNode) {
        if (aSTNode == null) {
            return null;
        }
        return aSTNode.getElementType();
    }

    public static IElementType getElementType(PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        }
        if (psiElement instanceof StubBasedPsiElement) {
            return ((StubBasedPsiElement) psiElement).getElementType();
        }
        return psiElement instanceof PsiFile ? ((PsiFile) psiElement).getFileElementType() : getElementType(psiElement.getNode());
    }

    private static class NullPsiFile extends NullPsiElement implements PsiFile {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "processor";
            } else {
                objArr[0] = "name";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiUtilCore$NullPsiFile";
            if (i != 1) {
                objArr[2] = "processChildren";
            } else {
                objArr[2] = "setName";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private NullPsiFile() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore.NullPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public FileASTNode getNode() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore.NullPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        /* JADX INFO: renamed from: getParent */
        public PsiDirectory getLightParent() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile, org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
        public VirtualFile getVirtualFile() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
        public PsiDirectory getContainingDirectory() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
        public long getModificationStamp() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
        public PsiFile getOriginalFile() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
        public FileType getFileType() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
        public PsiFile[] getPsiRoots() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
        public FileViewProvider getViewProvider() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
        public void subtreeChanged() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
        public boolean isDirectory() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
        public String getName() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
        public boolean processChildren(PsiElementProcessor<? super PsiFileSystemItem> psiElementProcessor) {
            if (psiElementProcessor == null) {
                $$$reportNull$$$0(0);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
        public ItemPresentation getPresentation() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
        public void navigate(boolean z) {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
        public boolean canNavigate() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
        public boolean canNavigateToSource() {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCheckedRenameElement
        public void checkSetName(String str) throws IncorrectOperationException {
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
        public PsiElement setName(String str) throws IncorrectOperationException {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            throw createException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore.NullPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
        public String toString() {
            return "NULL_PSI_FILE";
        }
    }
}
