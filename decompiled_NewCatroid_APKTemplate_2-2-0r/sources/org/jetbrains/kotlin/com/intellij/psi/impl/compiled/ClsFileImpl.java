package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.lang.ref.Reference;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.ide.plugins.PluginManagerCore;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.DefaultProjectFactory;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaSdkVersion;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.ClassFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwnerEx;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubTree;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiClassHolderFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTree;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.ClassReader;

/* JADX INFO: loaded from: classes3.dex */
public class ClsFileImpl extends PsiBinaryFileImpl implements Queryable, PsiClassOwnerEx, PsiCompiledFile, PsiJavaFile, PsiFileEx, PsiFileWithStubSupport {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean myIsForDecompiling;
    private volatile Reference<TreeElement> myMirrorFileElement;
    private final Object myMirrorLock;
    private volatile ClsPackageStatementImpl myPackageStatement;
    private volatile SoftReference<StubTree> myStub;
    private final Object myStubLock;
    private static final Logger LOG = Logger.getInstance((Class<?>) ClsFileImpl.class);
    private static final Key<Document> CLS_DOCUMENT_LINK_KEY = Key.create("cls.document.link");
    private static final InnerClassSourceStrategy<FileContentPair> STRATEGY = new InnerClassSourceStrategy<FileContentPair>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl.1
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.InnerClassSourceStrategy
        public FileContentPair findInnerClass(String str, FileContentPair fileContentPair) {
            VirtualFile virtualFileFindChild = ((VirtualFile) fileContentPair.first).getParent().findChild(((VirtualFile) fileContentPair.first).getNameWithoutExtension() + '$' + str + ".class");
            if (virtualFileFindChild == null) {
                return null;
            }
            try {
                return new FileContentPair(virtualFileFindChild, virtualFileFindChild.contentsToByteArray(false));
            } catch (IOException unused) {
                return null;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.InnerClassSourceStrategy
        public void accept(FileContentPair fileContentPair, StubBuildingVisitor<FileContentPair> stubBuildingVisitor) {
            try {
                new ClassReader((byte[]) fileContentPair.second).accept(stubBuildingVisitor, ClsFileImpl.EMPTY_ATTRIBUTES, 4);
            } catch (Exception e) {
                VirtualFile virtualFile = (VirtualFile) fileContentPair.first;
                if (ClsFileImpl.LOG.isDebugEnabled()) {
                    ClsFileImpl.LOG.debug(String.valueOf(virtualFile), e);
                } else {
                    ClsFileImpl.LOG.info(virtualFile + ": " + e.getMessage());
                }
            }
        }
    };
    public static final Attribute[] EMPTY_ATTRIBUTES = new Attribute[0];

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 27:
            case 28:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
                i2 = 2;
                break;
            case 6:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 27:
            case 28:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsFileImpl";
                break;
            case 6:
                objArr[0] = "aClass";
                break;
            case 13:
                objArr[0] = "name";
                break;
            case 17:
                objArr[0] = "visitor";
                break;
            case 20:
                objArr[0] = "processor";
                break;
            case 21:
                objArr[0] = "state";
                break;
            case 22:
                objArr[0] = "place";
                break;
            case 27:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 28:
            case 30:
                objArr[0] = "file";
                break;
            case 31:
                objArr[0] = "bytes";
                break;
            default:
                objArr[0] = "viewProvider";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getLanguage";
                break;
            case 3:
                objArr[1] = "getChildren";
                break;
            case 4:
                objArr[1] = "getClasses";
                break;
            case 5:
                objArr[1] = "getPackageName";
                break;
            case 6:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 27:
            case 28:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsFileImpl";
                break;
            case 7:
                objArr[1] = "getOnDemandImports";
                break;
            case 8:
                objArr[1] = "getSingleClassImports";
                break;
            case 9:
                objArr[1] = "getImplicitlyImportedPackages";
                break;
            case 10:
                objArr[1] = "getImplicitlyImportedPackageReferences";
                break;
            case 11:
            case 12:
                objArr[1] = "getLanguageLevel";
                break;
            case 14:
            case 15:
                objArr[1] = "getNavigationElement";
                break;
            case 16:
                objArr[1] = "getMirror";
                break;
            case 18:
                objArr[1] = "textToCharArray";
                break;
            case 19:
                objArr[1] = "getStub";
                break;
            case 23:
            case 24:
            case 25:
                objArr[1] = "getStubTree";
                break;
            case 26:
                objArr[1] = "getStubbedSpine";
                break;
            case 29:
                objArr[1] = "decompile";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
                break;
            case 6:
                objArr[2] = "importClass";
                break;
            case 13:
                objArr[2] = "setName";
                break;
            case 17:
                objArr[2] = "accept";
                break;
            case 20:
            case 21:
            case 22:
                objArr[2] = "processDeclarations";
                break;
            case 27:
                objArr[2] = "putInfo";
                break;
            case 28:
                objArr[2] = "decompile";
                break;
            case 30:
            case 31:
                objArr[2] = "buildFileStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 29:
                throw new IllegalStateException(str2);
            case 6:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 27:
            case 28:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiJavaCodeReferenceElement findImportReferenceTo(PsiClass psiClass) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiImportList getImportList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final int getStartOffsetInParent() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final int getTextOffset() {
        return 0;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClsFileImpl(FileViewProvider fileViewProvider) {
        this(fileViewProvider, false);
        if (fileViewProvider == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private ClsFileImpl(FileViewProvider fileViewProvider, boolean z) {
        super((PsiManagerImpl) fileViewProvider.getManager(), fileViewProvider);
        if (fileViewProvider == null) {
            $$$reportNull$$$0(1);
        }
        this.myMirrorLock = new Object();
        this.myStubLock = new Object();
        this.myIsForDecompiling = z;
        JavaElementType.CLASS.getIndex();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        if (isValid()) {
            return this;
        }
        throw new PsiInvalidElementAccessException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return super.isValid() && (this.myIsForDecompiling || getVirtualFile().isValid());
    }

    boolean isForDecompiling() {
        return this.myIsForDecompiling;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        JavaLanguage javaLanguage = JavaLanguage.INSTANCE;
        if (javaLanguage == null) {
            $$$reportNull$$$0(2);
        }
        return javaLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiJavaModule moduleDeclaration = getModuleDeclaration();
        PsiElement[] classes = moduleDeclaration != null ? new PsiElement[]{moduleDeclaration} : getClasses();
        if (classes == null) {
            $$$reportNull$$$0(3);
        }
        return classes;
    }

    public PsiClass[] getClasses() {
        PsiClass[] classes = getStub().getClasses();
        if (classes == null) {
            $$$reportNull$$$0(4);
        }
        return classes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiPackageStatement getPackageStatement() {
        String strFindPackageName;
        ClsPackageStatementImpl clsPackageStatementImpl = this.myPackageStatement;
        if (clsPackageStatementImpl == null) {
            clsPackageStatementImpl = ClsPackageStatementImpl.NULL_PACKAGE;
            PsiClassHolderFileStub<?> stub = getStub();
            if ((!(stub instanceof PsiJavaFileStub) || stub.findChildStubByType(JavaStubElementTypes.MODULE) == null) && (strFindPackageName = findPackageName(stub)) != null) {
                clsPackageStatementImpl = new ClsPackageStatementImpl(this, strFindPackageName);
            }
            this.myPackageStatement = clsPackageStatementImpl;
        }
        if (clsPackageStatementImpl != ClsPackageStatementImpl.NULL_PACKAGE) {
            return clsPackageStatementImpl;
        }
        return null;
    }

    private static String findPackageName(PsiClassHolderFileStub<?> psiClassHolderFileStub) {
        String strSubstring;
        String qualifiedName;
        int iLastIndexOf;
        if (psiClassHolderFileStub instanceof PsiJavaFileStub) {
            strSubstring = ((PsiJavaFileStub) psiClassHolderFileStub).getPackageName();
        } else {
            PsiClass[] classes = psiClassHolderFileStub.getClasses();
            strSubstring = (classes.length <= 0 || (qualifiedName = classes[0].getQualifiedName()) == null || (iLastIndexOf = qualifiedName.lastIndexOf(46)) < 0) ? null : qualifiedName.substring(0, iLastIndexOf);
        }
        if (StringUtil.isEmpty(strSubstring)) {
            return null;
        }
        return strSubstring;
    }

    public String getPackageName() {
        PsiPackageStatement packageStatement = getPackageStatement();
        String packageName = packageStatement == null ? "" : packageStatement.getPackageName();
        if (packageName == null) {
            $$$reportNull$$$0(5);
        }
        return packageName;
    }

    public void setPackageName(String str) throws IncorrectOperationException {
        throw new IncorrectOperationException("Cannot set package name for compiled files");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportHolder
    public boolean importClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(6);
        }
        throw new UnsupportedOperationException("Cannot add imports to compiled classes");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiElement[] getOnDemandImports(boolean z, boolean z2) {
        PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr = PsiJavaCodeReferenceElement.EMPTY_ARRAY;
        if (psiJavaCodeReferenceElementArr == null) {
            $$$reportNull$$$0(7);
        }
        return psiJavaCodeReferenceElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiClass[] getSingleClassImports(boolean z) {
        PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
        if (psiClassArr == null) {
            $$$reportNull$$$0(8);
        }
        return psiClassArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public String[] getImplicitlyImportedPackages() {
        String[] strArr = ArrayUtil.EMPTY_STRING_ARRAY;
        if (strArr == null) {
            $$$reportNull$$$0(9);
        }
        return strArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassOwnerEx
    public Set<String> getClassNames() {
        return Collections.singleton(getVirtualFile().getNameWithoutExtension());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiJavaCodeReferenceElement[] getImplicitlyImportedPackageReferences() {
        PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArr = PsiJavaCodeReferenceElement.EMPTY_ARRAY;
        if (psiJavaCodeReferenceElementArr == null) {
            $$$reportNull$$$0(10);
        }
        return psiJavaCodeReferenceElementArr;
    }

    public LanguageLevel getLanguageLevel() {
        LanguageLevel languageLevel;
        PsiClassHolderFileStub<?> stub = getStub();
        if ((stub instanceof PsiJavaFileStub) && (languageLevel = ((PsiJavaFileStub) stub).getLanguageLevel()) != null) {
            if (languageLevel == null) {
                $$$reportNull$$$0(11);
            }
            return languageLevel;
        }
        LanguageLevel languageLevel2 = LanguageLevel.HIGHEST;
        if (languageLevel2 == null) {
            $$$reportNull$$$0(12);
        }
        return languageLevel2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiJavaModule getModuleDeclaration() {
        PsiClassHolderFileStub<?> stub = getStub();
        if (stub instanceof PsiJavaFileStub) {
            return ((PsiJavaFileStub) stub).getModule();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        throw ClsElementImpl.cannotModifyException(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiCheckedRenameElement
    public void checkSetName(String str) throws IncorrectOperationException {
        throw ClsElementImpl.cannotModifyException(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendMirrorText(StringBuilder sb) {
        sb.append("\n  // IntelliJ API Decompiler stub source generated from a class file\n  // Implementation of methods is not available\n\n");
        PsiJavaModule moduleDeclaration = getModuleDeclaration();
        if (moduleDeclaration != null) {
            ClsElementImpl.appendText(moduleDeclaration, 0, sb);
            return;
        }
        PsiClass[] classes = getClasses();
        if (classes.length > 0) {
            PsiClass psiClass = classes[0];
            if ("package-info".equals(psiClass.getName())) {
                for (PsiAnnotation psiAnnotation : psiClass.getAnnotations()) {
                    ClsElementImpl.appendText(psiAnnotation, 0, sb, "go_to_next_line_and_indent");
                }
                ClsElementImpl.appendText(getPackageStatement(), 0, sb, "\n");
                return;
            }
            ClsElementImpl.appendText(getPackageStatement(), 0, sb, "\n\n");
            ClsElementImpl.appendText(psiClass, 0, sb);
        }
    }

    private void setFileMirror(TreeElement treeElement) {
        PsiElement psiElementTreeToPsiNotNull = SourceTreeToPsiMap.treeToPsiNotNull(treeElement);
        if (!(psiElementTreeToPsiNotNull instanceof PsiJavaFile)) {
            throw new ClsElementImpl.InvalidMirrorException("Unexpected mirror file: " + psiElementTreeToPsiNotNull);
        }
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiElementTreeToPsiNotNull;
        PsiJavaModule moduleDeclaration = getModuleDeclaration();
        if (moduleDeclaration != null) {
            ClsElementImpl.setMirror(moduleDeclaration, psiJavaFile.getModuleDeclaration());
            return;
        }
        PsiClass[] classes = getClasses();
        PsiClass[] classes2 = psiJavaFile.getClasses();
        if (classes.length == 1 && classes2.length == 0 && "package-info".equals(classes[0].getName())) {
            PsiPackageStatement packageStatement = getPackageStatement();
            PsiPackageStatement packageStatement2 = psiJavaFile.getPackageStatement();
            ClsElementImpl.setMirror(packageStatement, packageStatement2);
            ClsElementImpl.setMirrorIfPresent(classes[0].mo6095getModifierList(), packageStatement2.getAnnotationList());
            return;
        }
        ClsElementImpl.setMirrorIfPresent(getPackageStatement(), psiJavaFile.getPackageStatement());
        ClsElementImpl.setMirrors(classes, classes2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        for (ClsCustomNavigationPolicy clsCustomNavigationPolicy : ClsCustomNavigationPolicy.EP_NAME.getExtensionList()) {
            try {
                PsiElement fileNavigationElement = clsCustomNavigationPolicy instanceof ClsCustomNavigationPolicyEx ? ((ClsCustomNavigationPolicyEx) clsCustomNavigationPolicy).getFileNavigationElement(this) : clsCustomNavigationPolicy.getNavigationElement(this);
                if (fileNavigationElement != null) {
                    if (fileNavigationElement == null) {
                        $$$reportNull$$$0(14);
                    }
                    return fileNavigationElement;
                }
            } catch (IndexNotReadyException unused) {
            }
        }
        PsiElement psiElement = (PsiElement) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6588xecc274c9();
            }
        });
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        return psiElement;
    }

    /* JADX INFO: renamed from: lambda$getNavigationElement$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsFileImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6588xecc274c9() {
        PsiElement clsFileNavigationElement = JavaPsiImplementationHelper.getInstance(getProject()).getClsFileNavigationElement(this);
        return CachedValueProvider.Result.create(clsFileNavigationElement, this, clsFileNavigationElement.getContainingFile(), FileIndexFacade.getInstance(getProject()).getRootModificationTracker());
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public PsiElement getMirror() {
        TreeElement treeElement = (TreeElement) SoftReference.dereference(this.myMirrorFileElement);
        if (treeElement == null) {
            synchronized (this.myMirrorLock) {
                treeElement = (TreeElement) SoftReference.dereference(this.myMirrorFileElement);
                if (treeElement == null) {
                    VirtualFile virtualFile = getVirtualFile();
                    AstLoadingFilter.assertTreeLoadingAllowed(virtualFile);
                    PsiClass[] classes = getClasses();
                    String str = (classes.length > 0 ? classes[0].getName() : virtualFile.getNameWithoutExtension()) + SuffixConstants.SUFFIX_STRING_java;
                    final Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
                    CharSequence immutableCharSequence = document.getImmutableCharSequence();
                    boolean zStartsWith = StringUtil.startsWith(immutableCharSequence, "\n  // IntelliJ API Decompiler stub source generated from a class file\n  // Implementation of methods is not available\n\n");
                    PsiFile psiFileCreateFileFromText = PsiFileFactory.getInstance(getManager().getProject()).createFileFromText(str, (Language) JavaLanguage.INSTANCE, immutableCharSequence, false, false, true);
                    psiFileCreateFileFromText.putUserData(PsiUtil.FILE_LANGUAGE_LEVEL_KEY, getLanguageLevel());
                    final TreeElement treeElementPsiToTreeNotNull = SourceTreeToPsiMap.psiToTreeNotNull(psiFileCreateFileFromText);
                    try {
                        ProgressManager.getInstance().executeNonCancelableSection(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m6587x2b1aba53(treeElementPsiToTreeNotNull, document);
                            }
                        });
                    } catch (ClsElementImpl.InvalidMirrorException e) {
                        LOG.error(virtualFile.getUrl(), zStartsWith ? e : wrapException(e, virtualFile));
                    }
                    ((PsiFileImpl) psiFileCreateFileFromText).setOriginalFile(this);
                    this.myMirrorFileElement = new SoftReference(treeElementPsiToTreeNotNull);
                    treeElement = treeElementPsiToTreeNotNull;
                }
            }
        }
        PsiElement psi = treeElement.getPsi();
        if (psi == null) {
            $$$reportNull$$$0(16);
        }
        return psi;
    }

    /* JADX INFO: renamed from: lambda$getMirror$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsFileImpl, reason: not valid java name */
    /* synthetic */ void m6587x2b1aba53(TreeElement treeElement, Document document) {
        setFileMirror(treeElement);
        putUserData(CLS_DOCUMENT_LINK_KEY, document);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return FileDocumentManager.getInstance().getDocument(getVirtualFile()).getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        return FileDocumentManager.getInstance().getDocument(getVirtualFile()).getTextLength();
    }

    private static Exception wrapException(ClsElementImpl.InvalidMirrorException invalidMirrorException, VirtualFile virtualFile) {
        PluginId pluginByClassName;
        ClassFileDecompilers.Decompiler decompilerFind = ClassFileDecompilers.getInstance().find(virtualFile, ClassFileDecompilers.Light.class);
        return (decompilerFind == null || (pluginByClassName = PluginManagerCore.getPluginByClassName(decompilerFind.getClass().getName())) == null) ? invalidMirrorException : new PluginException(invalidMirrorException, pluginByClassName);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCompiledFile
    public PsiFile getDecompiledPsiFile() {
        return (PsiFile) getMirror();
    }

    public PsiFile getCachedMirror() {
        TreeElement treeElement = (TreeElement) SoftReference.dereference(this.myMirrorFileElement);
        if (treeElement == null) {
            return null;
        }
        return (PsiFile) treeElement.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(17);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitJavaFile(this);
        } else {
            psiElementVisitor.visitFile(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiFile:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final TextRange getTextRange() {
        return TextRange.create(0, getTextLength());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final PsiElement findElementAt(int i) {
        return getMirror().findElementAt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference findReferenceAt(int i) {
        return getMirror().findReferenceAt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        char[] cArrTextToCharArray = getMirror().textToCharArray();
        if (cArrTextToCharArray == null) {
            $$$reportNull$$$0(18);
        }
        return cArrTextToCharArray;
    }

    public PsiClassHolderFileStub<?> getStub() {
        PsiClassHolderFileStub<?> psiClassHolderFileStub = (PsiClassHolderFileStub) getStubTree().getRoot();
        if (psiClassHolderFileStub == null) {
            $$$reportNull$$$0(19);
        }
        return psiClassHolderFileStub;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(20);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(21);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(22);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) {
            return true;
        }
        for (PsiClass psiClass : getClasses()) {
            if (!psiScopeProcessor.execute(psiClass, resolveState)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport
    public StubTree getStubTree() {
        ObjectStubTree<?> orBuild;
        ApplicationManager.getApplication().assertReadAccessAllowed();
        StubTree stubTree = (StubTree) SoftReference.dereference(this.myStub);
        if (stubTree != null) {
            if (stubTree == null) {
                $$$reportNull$$$0(23);
            }
            return stubTree;
        }
        StubTreeLoader stubTreeLoader = StubTreeLoader.getInstance();
        Project project = getProject();
        VirtualFile virtualFile = getVirtualFile();
        if (project.isDefault()) {
            orBuild = stubTreeLoader.build(null, virtualFile, this);
        } else {
            orBuild = stubTreeLoader.readOrBuild(project, virtualFile, this);
        }
        StubTree stubTree2 = (StubTree) orBuild;
        if (stubTree2 == null) {
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("No stub for class file " + virtualFile.getPresentableUrl());
            }
            stubTree2 = new StubTree(new PsiJavaFileStubImpl("corrupted_class_files", true));
        } else if (!(stubTree2.getRoot() instanceof PsiClassHolderFileStub)) {
            Logger logger2 = LOG;
            if (logger2.isDebugEnabled()) {
                logger2.debug("Invalid stub for class file " + virtualFile.getPresentableUrl() + ": " + stubTree2.getRoot());
            }
            stubTree2 = new StubTree(new PsiJavaFileStubImpl("corrupted_class_files", true));
        }
        synchronized (this.myStubLock) {
            StubTree stubTree3 = (StubTree) SoftReference.dereference(this.myStub);
            if (stubTree3 != null) {
                if (stubTree3 == null) {
                    $$$reportNull$$$0(24);
                }
                return stubTree3;
            }
            ((PsiFileStubImpl) stubTree2.getRoot()).setPsi(this);
            this.myStub = new SoftReference<>(stubTree2);
            if (stubTree2 == null) {
                $$$reportNull$$$0(25);
            }
            return stubTree2;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport
    public StubbedSpine getStubbedSpine() {
        StubbedSpine spine = getStubTree().getSpine();
        if (spine == null) {
            $$$reportNull$$$0(26);
        }
        return spine;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx
    public boolean isContentsLoaded() {
        return getCachedMirror() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx
    public void onContentReload() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        synchronized (this.myStubLock) {
            StubTree stubTree = (StubTree) SoftReference.dereference(this.myStub);
            this.myStub = null;
            if (stubTree != null) {
                ((PsiFileStubImpl) stubTree.getRoot()).clearPsi("cls onContentReload");
            }
        }
        synchronized (this.myMirrorLock) {
            putUserData(CLS_DOCUMENT_LINK_KEY, null);
            this.myMirrorFileElement = null;
            this.myPackageStatement = null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.openapi.ui.Queryable
    public void putInfo(Map<? super String, ? super String> map) {
        if (map == null) {
            $$$reportNull$$$0(27);
        }
        PsiFileImpl.putInfo(this, map);
    }

    public static CharSequence decompile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(28);
        }
        final ClsFileImpl clsFileImpl = new ClsFileImpl(new ClassFileViewProvider(PsiManager.getInstance(DefaultProjectFactory.getInstance().getDefaultProject()), virtualFile), true);
        final StringBuilder sb = new StringBuilder();
        ApplicationManager.getApplication().runReadAction(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.appendMirrorText(sb);
            }
        });
        return sb;
    }

    public static PsiJavaFileStub buildFileStub(VirtualFile virtualFile, byte[] bArr) throws ClsFormatException {
        if (virtualFile == null) {
            $$$reportNull$$$0(30);
        }
        if (bArr == null) {
            $$$reportNull$$$0(31);
        }
        try {
            if (ClassFileViewProvider.isInnerClass(virtualFile, bArr)) {
                return null;
            }
            ClassReader classReader = new ClassReader(bArr);
            String nameWithoutExtension = virtualFile.getNameWithoutExtension();
            String className = classReader.getClassName();
            boolean z = className.equals(PsiJavaModule.MODULE_INFO_CLASS) && BitUtil.isSet(classReader.getAccess(), 32768);
            JavaSdkVersion jdkVersionByBytecode = ClsParsingUtil.getJdkVersionByBytecode(classReader.readUnsignedShort(6));
            LanguageLevel maxLanguageLevel = jdkVersionByBytecode != null ? jdkVersionByBytecode.getMaxLanguageLevel() : null;
            if (maxLanguageLevel != null && maxLanguageLevel.isAtLeast(LanguageLevel.JDK_11) && ClsParsingUtil.isPreviewLevel(classReader.readUnsignedShort(4))) {
                maxLanguageLevel = (LanguageLevel) ObjectUtils.notNull(maxLanguageLevel.getPreviewLevel(), LanguageLevel.HIGHEST);
            }
            if (z) {
                PsiJavaFileStubImpl psiJavaFileStubImpl = new PsiJavaFileStubImpl(null, "", maxLanguageLevel, true);
                ModuleStubBuildingVisitor moduleStubBuildingVisitor = new ModuleStubBuildingVisitor(psiJavaFileStubImpl);
                classReader.accept(moduleStubBuildingVisitor, EMPTY_ATTRIBUTES, 4);
                if (moduleStubBuildingVisitor.getResult() != null) {
                    return psiJavaFileStubImpl;
                }
            } else {
                PsiJavaFileStubImpl psiJavaFileStubImpl2 = new PsiJavaFileStubImpl(null, getPackageName(className), maxLanguageLevel, true);
                try {
                    StubBuildingVisitor stubBuildingVisitor = new StubBuildingVisitor(new FileContentPair(virtualFile, bArr), STRATEGY, psiJavaFileStubImpl2, 0, nameWithoutExtension);
                    classReader.accept(stubBuildingVisitor, EMPTY_ATTRIBUTES, 4);
                    if (stubBuildingVisitor.getResult() != null) {
                        return psiJavaFileStubImpl2;
                    }
                } catch (OutOfOrderInnerClassException unused) {
                    Logger logger = LOG;
                    if (logger.isTraceEnabled()) {
                        logger.trace(virtualFile.getPath());
                    }
                }
            }
            return null;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            throw new ClsFormatException(virtualFile.getPath() + ": " + th.getMessage(), th);
        }
    }

    private static String getPackageName(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        return iLastIndexOf > 0 ? str.substring(0, iLastIndexOf).replace('/', '.') : "";
    }

    static class FileContentPair extends Pair<VirtualFile, byte[]> {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
            } else if (i != 2) {
                objArr[0] = "file";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsFileImpl$FileContentPair";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsFileImpl$FileContentPair";
            } else {
                objArr[1] = "getContent";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        FileContentPair(VirtualFile virtualFile, byte[] bArr) {
            super(virtualFile, bArr);
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            if (bArr == null) {
                $$$reportNull$$$0(1);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public byte[] getContent() {
            byte[] bArr = (byte[]) this.second;
            if (bArr == null) {
                $$$reportNull$$$0(2);
            }
            return bArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Pair
        public String toString() {
            return ((VirtualFile) this.first).toString();
        }
    }
}
