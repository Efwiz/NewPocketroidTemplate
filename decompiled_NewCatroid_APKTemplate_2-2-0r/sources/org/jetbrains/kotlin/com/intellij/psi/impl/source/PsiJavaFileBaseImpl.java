package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.eclipse.jgit.transport.RefSpec;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyKey;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ClassResolverProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.SymbolCollectingProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodsProcessor;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.FileContentUtilCore;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.com.intellij.util.containers.MostlySingularMultiMap;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.com.intellij.util.execution.ParametersListUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiJavaFileBaseImpl extends PsiFileImpl implements PsiJavaFile {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile String myPackageName;
    private final CachedValue<MostlySingularMultiMap<String, SymbolCollectingProcessor.ResultWithContext>> myResolveCache;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiJavaFileBaseImpl.class);
    private static final String[] IMPLICIT_IMPORTS = {CommonClassNames.DEFAULT_PACKAGE};
    private static final NotNullLazyKey<LanguageLevel, PsiJavaFileBaseImpl> LANGUAGE_LEVEL_KEY = NotNullLazyKey.create("LANGUAGE_LEVEL", new NotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.NotNullFunction, org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ((PsiJavaFileBaseImpl) obj).getLanguageLevelInner();
        }
    });
    private static final Key<String> SHEBANG_SOURCE_LEVEL = Key.create("SHEBANG_SOURCE_LEVEL");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 21:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 16:
            case 18:
            case 20:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 21:
                i2 = 3;
                break;
            case 16:
            case 18:
            case 20:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 10:
                objArr[0] = "processor";
                break;
            case 11:
            case 13:
            case 14:
                objArr[0] = "state";
                break;
            case 12:
                objArr[0] = "place";
                break;
            case 15:
                objArr[0] = "aPackage";
                break;
            case 16:
            case 18:
            case 20:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaFileBaseImpl";
                break;
            case 17:
                objArr[0] = "visitor";
                break;
            case 19:
                objArr[0] = "aClass";
                break;
            case 21:
                objArr[0] = "originalFile";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[1] = "getPackageName";
                break;
            case 4:
            case 5:
                objArr[1] = "getOnDemandImports";
                break;
            case 6:
            case 7:
                objArr[1] = "getSingleClassImports";
                break;
            case 8:
                objArr[1] = "getImplicitlyImportedPackages";
                break;
            case 9:
                objArr[1] = "getImplicitlyImportedPackageReferences";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 21:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaFileBaseImpl";
                break;
            case 16:
                objArr[1] = "createRawSubstitutor";
                break;
            case 18:
                objArr[1] = "getLanguage";
                break;
            case 20:
                objArr[1] = "getLanguageLevel";
                break;
            default:
                objArr[1] = "getClasses";
                break;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
                objArr[2] = "processDeclarations";
                break;
            case 13:
                objArr[2] = "processOnDemandPackages";
                break;
            case 14:
            case 15:
                objArr[2] = "processPackageDeclarations";
                break;
            case 17:
                objArr[2] = "accept";
                break;
            case 19:
                objArr[2] = "importClass";
                break;
            case 21:
                objArr[2] = "setOriginalFile";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 21:
                throw new IllegalArgumentException(str2);
            case 16:
            case 18:
            case 20:
            default:
                throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiJavaModule getModuleDeclaration() {
        return null;
    }

    protected PsiJavaFileBaseImpl(IElementType iElementType, IElementType iElementType2, FileViewProvider fileViewProvider) {
        super(iElementType, iElementType2, fileViewProvider);
        this.myResolveCache = CachedValuesManager.getManager(this.myManager.getProject()).createCachedValue(new MyCacheBuilder(this), false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public void subtreeChanged() {
        super.subtreeChanged();
        this.myPackageName = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner
    public PsiClass[] getClasses() {
        StubElement<?> greenStub = getGreenStub();
        if (greenStub != null) {
            PsiClass[] psiClassArr = (PsiClass[]) greenStub.getChildrenByType(JavaStubElementTypes.CLASS, PsiClass.ARRAY_FACTORY);
            if (psiClassArr == null) {
                $$$reportNull$$$0(0);
            }
            return psiClassArr;
        }
        PsiClass[] psiClassArr2 = (PsiClass[]) calcTreeElement().getChildrenAsPsiElements(Constants.CLASS_BIT_SET, PsiClass.ARRAY_FACTORY);
        if (psiClassArr2 == null) {
            $$$reportNull$$$0(1);
        }
        return psiClassArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiPackageStatement getPackageStatement() {
        ASTNode aSTNodeFindChildByType = calcTreeElement().findChildByType(JavaElementType.PACKAGE_STATEMENT);
        if (aSTNodeFindChildByType != null) {
            return (PsiPackageStatement) aSTNodeFindChildByType.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public String getPackageName() {
        PsiJavaFileStub psiJavaFileStub = (PsiJavaFileStub) getGreenStub();
        if (psiJavaFileStub != null) {
            String packageName = psiJavaFileStub.getPackageName();
            if (packageName == null) {
                $$$reportNull$$$0(2);
            }
            return packageName;
        }
        String packageName2 = this.myPackageName;
        if (packageName2 == null) {
            PsiPackageStatement packageStatement = getPackageStatement();
            packageName2 = packageStatement == null ? "" : packageStatement.getPackageName();
            this.myPackageName = packageName2;
        }
        if (packageName2 == null) {
            $$$reportNull$$$0(3);
        }
        return packageName2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner
    public void setPackageName(String str) throws IncorrectOperationException {
        if (PsiUtil.isModuleFile(this)) {
            throw new IncorrectOperationException("Cannot set package name for module declarations");
        }
        PsiPackageStatement packageStatement = getPackageStatement();
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
        if (packageStatement != null) {
            if (!str.isEmpty()) {
                packageStatement.getPackageReference().replace(elementFactory.createPackageStatement(str).getPackageReference());
                return;
            } else {
                packageStatement.delete();
                return;
            }
        }
        if (str.isEmpty()) {
            return;
        }
        cleanupBrokenPackageKeyword();
        PsiElement firstChild = getFirstChild();
        if (PsiPackage.PACKAGE_INFO_FILE.equals(getMyName())) {
            firstChild = getImportList();
            PsiElement prevSibling = firstChild.getPrevSibling();
            if (prevSibling instanceof PsiComment) {
                String strTrim = prevSibling.getText().trim();
                if (strTrim.startsWith(RefSpec.WILDCARD_SUFFIX) && !strTrim.endsWith("*/")) {
                    prevSibling.replace(elementFactory.createCommentFromText(strTrim + (StringUtil.containsLineBreak(strTrim) ? "\n*/" : " */"), prevSibling));
                }
            }
        }
        addBefore(elementFactory.createPackageStatement(str), firstChild);
    }

    private void cleanupBrokenPackageKeyword() {
        PsiElement firstChild = getFirstChild();
        while (true) {
            if (!(firstChild instanceof PsiWhiteSpace) && !(firstChild instanceof PsiComment) && !(firstChild instanceof PsiErrorElement)) {
                return;
            }
            if ((firstChild instanceof PsiErrorElement) && firstChild.getFirstChild() != null && firstChild.getFirstChild().textMatches("package")) {
                firstChild.delete();
                return;
            }
            firstChild = firstChild.getNextSibling();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiImportList getImportList() {
        StubElement<?> greenStub = getGreenStub();
        if (greenStub != null) {
            PsiImportList[] psiImportListArr = (PsiImportList[]) greenStub.getChildrenByType(JavaStubElementTypes.IMPORT_LIST, PsiImportList.ARRAY_FACTORY);
            if (psiImportListArr.length == 1) {
                return psiImportListArr[0];
            }
            return null;
        }
        return (PsiImportList) SourceTreeToPsiMap.treeElementToPsi(calcTreeElement().findChildByType(JavaElementType.IMPORT_LIST));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiElement[] getOnDemandImports(boolean z, boolean z2) {
        PsiElement psiElementResolve;
        PsiImportList importList = getImportList();
        if (importList == null) {
            PsiFile[] psiFileArr = EMPTY_ARRAY;
            if (psiFileArr == null) {
                $$$reportNull$$$0(4);
            }
            return psiFileArr;
        }
        ArrayList arrayList = new ArrayList();
        for (PsiImportStatement psiImportStatement : importList.getImportStatements()) {
            if (psiImportStatement.isOnDemand() && (psiElementResolve = psiImportStatement.resolve()) != null) {
                arrayList.add(psiElementResolve);
            }
        }
        if (z) {
            for (PsiJavaCodeReferenceElement psiJavaCodeReferenceElement : getImplicitlyImportedPackageReferences()) {
                PsiElement psiElementResolve2 = psiJavaCodeReferenceElement.resolve();
                if (psiElementResolve2 != null) {
                    arrayList.add(psiElementResolve2);
                }
            }
        }
        PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(5);
        }
        return psiElementArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiClass[] getSingleClassImports(boolean z) {
        PsiImportList importList = getImportList();
        if (importList == null) {
            PsiClass[] psiClassArr = PsiClass.EMPTY_ARRAY;
            if (psiClassArr == null) {
                $$$reportNull$$$0(6);
            }
            return psiClassArr;
        }
        ArrayList arrayList = new ArrayList();
        for (PsiImportStatement psiImportStatement : importList.getImportStatements()) {
            if (!psiImportStatement.isOnDemand()) {
                PsiElement psiElementResolve = psiImportStatement.resolve();
                if (psiElementResolve instanceof PsiClass) {
                    arrayList.add((PsiClass) psiElementResolve);
                }
            }
        }
        PsiClass[] psiClassArr2 = (PsiClass[]) arrayList.toArray(PsiClass.EMPTY_ARRAY);
        if (psiClassArr2 == null) {
            $$$reportNull$$$0(7);
        }
        return psiClassArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiJavaCodeReferenceElement findImportReferenceTo(PsiClass psiClass) {
        PsiElement psiElementResolve;
        PsiImportList importList = getImportList();
        if (importList == null) {
            return null;
        }
        for (PsiImportStatement psiImportStatement : importList.getImportStatements()) {
            if (!psiImportStatement.isOnDemand() && (psiElementResolve = psiImportStatement.resolve()) != null && getManager().areElementsEquivalent(psiElementResolve, psiClass)) {
                return psiImportStatement.getImportReference();
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public String[] getImplicitlyImportedPackages() {
        String[] strArr = IMPLICIT_IMPORTS;
        if (strArr == null) {
            $$$reportNull$$$0(8);
        }
        return strArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiJavaCodeReferenceElement[] getImplicitlyImportedPackageReferences() {
        PsiJavaCodeReferenceElement[] psiJavaCodeReferenceElementArrNamesToPackageReferences = PsiImplUtil.namesToPackageReferences(this.myManager, IMPLICIT_IMPORTS);
        if (psiJavaCodeReferenceElementArrNamesToPackageReferences == null) {
            $$$reportNull$$$0(9);
        }
        return psiJavaCodeReferenceElementArrNamesToPackageReferences;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class StaticImportFilteringProcessor extends DelegatingScopeProcessor {
        private final Collection<PsiElement> myCollectedElements;
        private final Map<String, Iterable<SymbolCollectingProcessor.ResultWithContext>> myExplicitlyEnumerated;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "state";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaFileBaseImpl$StaticImportFilteringProcessor";
            objArr[2] = "execute";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        StaticImportFilteringProcessor(PsiScopeProcessor psiScopeProcessor, Map<String, Iterable<SymbolCollectingProcessor.ResultWithContext>> map) {
            super(psiScopeProcessor);
            this.myCollectedElements = new HashSet();
            this.myExplicitlyEnumerated = map;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
        public boolean execute(final PsiElement psiElement, ResolveState resolveState) {
            PsiClass containingClass;
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            if (resolveState == null) {
                $$$reportNull$$$0(1);
            }
            if ((psiElement instanceof PsiModifierListOwner) && ((PsiModifierListOwner) psiElement).hasModifierProperty("static")) {
                PsiScopeProcessor delegate = getDelegate();
                if (psiElement instanceof PsiNamedElement) {
                    Iterable<SymbolCollectingProcessor.ResultWithContext> iterable = this.myExplicitlyEnumerated.get(((PsiNamedElement) psiElement).getMyName());
                    if (iterable != null && ContainerUtil.exists(iterable, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$StaticImportFilteringProcessor$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                        public final boolean value(Object obj) {
                            return PsiJavaFileBaseImpl.StaticImportFilteringProcessor.hasSameDeclarationKind(psiElement, ((SymbolCollectingProcessor.ResultWithContext) obj).getElement());
                        }
                    })) {
                        return true;
                    }
                    if ((delegate instanceof MethodsProcessor) && (psiElement instanceof PsiMethod) && (containingClass = ((PsiMethod) psiElement).mo6092getContainingClass()) != null && containingClass.isInterface()) {
                        PsiElement currentFileContext = ((MethodsProcessor) delegate).getCurrentFileContext();
                        if (currentFileContext instanceof PsiImportStaticStatement) {
                            PsiImportStaticStatement psiImportStaticStatement = (PsiImportStaticStatement) currentFileContext;
                            if (psiImportStaticStatement.isOnDemand() && !containingClass.isEquivalentTo(psiImportStaticStatement.resolveTargetClass())) {
                                return true;
                            }
                        }
                    }
                }
                if (this.myCollectedElements.add(psiElement)) {
                    return delegate.execute(psiElement, resolveState);
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean hasSameDeclarationKind(PsiElement psiElement, PsiElement psiElement2) {
            if (psiElement instanceof PsiClass) {
                return psiElement2 instanceof PsiClass;
            }
            return psiElement instanceof PsiMethod ? psiElement2 instanceof PsiMethod : psiElement2 instanceof PsiField;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        Iterable<SymbolCollectingProcessor.ResultWithContext> iterableConcat;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(10);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(11);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(12);
        }
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        String name2 = nameHint != null ? nameHint.getName(resolveState) : null;
        Map<String, Iterable<SymbolCollectingProcessor.ResultWithContext>> explicitlyEnumeratedDeclarations = getExplicitlyEnumeratedDeclarations();
        if (name2 != null) {
            iterableConcat = explicitlyEnumeratedDeclarations.get(name2);
        } else {
            iterableConcat = ContainerUtil.concat((Iterable[]) explicitlyEnumeratedDeclarations.values().toArray(new Iterable[0]));
        }
        if (iterableConcat != null && !ContainerUtil.process(iterableConcat, new MyResolveCacheProcessor(psiScopeProcessor, resolveState))) {
            return false;
        }
        if ((psiScopeProcessor instanceof ClassResolverProcessor) && ((getUserData(PsiFileEx.BATCH_REFERENCE_PROCESSING) == Boolean.TRUE || this.myResolveCache.hasUpToDateValue()) && !PsiUtil.isInsideJavadocComment(psiElement2))) {
            MostlySingularMultiMap<String, SymbolCollectingProcessor.ResultWithContext> value = this.myResolveCache.getValue();
            MyResolveCacheProcessor myResolveCacheProcessor = new MyResolveCacheProcessor(psiScopeProcessor, resolveState);
            return name2 != null ? value.processForKey(name2, myResolveCacheProcessor) : value.processAllValues(myResolveCacheProcessor);
        }
        return processOnDemandPackages(psiScopeProcessor, resolveState, psiElement2);
    }

    private Map<String, Iterable<SymbolCollectingProcessor.ResultWithContext>> getExplicitlyEnumeratedDeclarations() {
        return (Map) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6653xf19555a8();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getExplicitlyEnumeratedDeclarations$2$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiJavaFileBaseImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6653xf19555a8() {
        String qualifiedName;
        final MultiMap multiMapCreate = MultiMap.create();
        final MultiMap multiMapCreate2 = MultiMap.create();
        final MultiMap multiMapCreate3 = MultiMap.create();
        for (PsiClass psiClass : getClasses()) {
            String name2 = psiClass.getMyName();
            if (name2 != null) {
                multiMapCreate.putValue(name2, psiClass);
            }
        }
        for (PsiImportStatement psiImportStatement : getImportStatements()) {
            if (!psiImportStatement.isOnDemand() && (qualifiedName = psiImportStatement.getQualifiedName()) != null) {
                multiMapCreate2.putValue(StringUtil.getShortName(qualifiedName), psiImportStatement);
            }
        }
        for (PsiImportStaticStatement psiImportStaticStatement : getImportStaticStatements()) {
            String referenceName = psiImportStaticStatement.getReferenceName();
            if (referenceName != null) {
                multiMapCreate3.putValue(referenceName, psiImportStaticStatement);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (final String str : ContainerUtil.newLinkedHashSet(ContainerUtil.concat(multiMapCreate.keySet(), multiMapCreate2.keySet(), multiMapCreate3.keySet()))) {
            final NotNullLazyValue notNullLazyValueVolatileLazy = NotNullLazyValue.volatileLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return PsiJavaFileBaseImpl.findExplicitDeclarations(str, multiMapCreate, multiMapCreate2, multiMapCreate3);
                }
            });
            linkedHashMap.put(str, new Iterable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda5
                @Override // java.lang.Iterable
                public final Iterator iterator() {
                    return ((Iterable) notNullLazyValueVolatileLazy.getValue()).iterator();
                }
            });
        }
        return CachedValueProvider.Result.create(linkedHashMap, PsiModificationTracker.MODIFICATION_COUNT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Iterable<SymbolCollectingProcessor.ResultWithContext> findExplicitDeclarations(String str, MultiMap<String, PsiClass> multiMap, MultiMap<String, PsiImportStatement> multiMap2, MultiMap<String, PsiImportStaticStatement> multiMap3) {
        ArrayList arrayList = new ArrayList();
        Iterator<PsiClass> it = multiMap.get(str).iterator();
        while (it.hasNext()) {
            arrayList.add(new SymbolCollectingProcessor.ResultWithContext(it.next(), null));
        }
        for (PsiImportStatement psiImportStatement : multiMap2.get(str)) {
            PsiElement psiElementResolve = psiImportStatement.resolve();
            if (psiElementResolve instanceof PsiClass) {
                arrayList.add(new SymbolCollectingProcessor.ResultWithContext((PsiNamedElement) psiElementResolve, psiImportStatement));
            }
        }
        for (PsiImportStaticStatement psiImportStaticStatement : multiMap3.get(str)) {
            PsiJavaCodeReferenceElement importReference = psiImportStaticStatement.getImportReference();
            if (importReference != null) {
                for (JavaResolveResult javaResolveResult : importReference.multiResolve(false)) {
                    PsiElement element = javaResolveResult.getElement();
                    if (element instanceof PsiNamedElement) {
                        arrayList.add(new SymbolCollectingProcessor.ResultWithContext((PsiNamedElement) element, psiImportStaticStatement));
                    }
                }
            }
        }
        return JBIterable.from(arrayList).unique(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((SymbolCollectingProcessor.ResultWithContext) obj).getElement();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processOnDemandPackages(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement) {
        if (resolveState == null) {
            $$$reportNull$$$0(13);
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        boolean z = elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS);
        if ((z && !processCurrentPackage(psiScopeProcessor, resolveState, psiElement)) || !processOnDemandStaticImports(resolveState, new StaticImportFilteringProcessor(psiScopeProcessor, getExplicitlyEnumeratedDeclarations()))) {
            return false;
        }
        if (!z || processOnDemandTypeImports(psiScopeProcessor, resolveState, psiElement)) {
            return !z || processImplicitImports(psiScopeProcessor, resolveState, psiElement);
        }
        return false;
    }

    private PsiImportStaticStatement[] getImportStaticStatements() {
        return getImportList() != null ? getImportList().getImportStaticStatements() : PsiImportStaticStatement.EMPTY_ARRAY;
    }

    private PsiImportStatement[] getImportStatements() {
        return getImportList() != null ? getImportList().getImportStatements() : PsiImportStatement.EMPTY_ARRAY;
    }

    private boolean processCurrentPackage(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement) {
        psiScopeProcessor.handleEvent(JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT, null);
        PsiPackage psiPackageFindPackage = JavaPsiFacade.getInstance(this.myManager.getProject()).findPackage(getPackageName());
        return psiPackageFindPackage == null || processPackageDeclarations(psiScopeProcessor, resolveState, psiElement, psiPackageFindPackage);
    }

    private boolean processOnDemandTypeImports(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement) {
        PsiElement psiElementResolve;
        for (PsiImportStatement psiImportStatement : getImportStatements()) {
            if (psiImportStatement.isOnDemand() && (psiElementResolve = psiImportStatement.resolve()) != null) {
                psiScopeProcessor.handleEvent(JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT, psiImportStatement);
                if (!processOnDemandTarget(psiElementResolve, psiScopeProcessor, resolveState, psiElement)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean processOnDemandStaticImports(ResolveState resolveState, StaticImportFilteringProcessor staticImportFilteringProcessor) {
        PsiClass psiClassResolveTargetClass;
        for (PsiImportStaticStatement psiImportStaticStatement : getImportStaticStatements()) {
            if (psiImportStaticStatement.isOnDemand() && (psiClassResolveTargetClass = psiImportStaticStatement.resolveTargetClass()) != null) {
                staticImportFilteringProcessor.handleEvent(JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT, psiImportStaticStatement);
                if (!PsiClassImplUtil.processAllMembersWithoutSubstitutors(psiClassResolveTargetClass, staticImportFilteringProcessor, resolveState)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean processImplicitImports(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement) {
        psiScopeProcessor.handleEvent(JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT, null);
        for (PsiJavaCodeReferenceElement psiJavaCodeReferenceElement : getImplicitlyImportedPackageReferences()) {
            PsiElement psiElementResolve = psiJavaCodeReferenceElement.resolve();
            if (psiElementResolve != null && !processOnDemandTarget(psiElementResolve, psiScopeProcessor, resolveState, psiElement)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$1, reason: invalid class name */
    class AnonymousClass1 extends DelegatingScopeProcessor {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "hintKey", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaFileBaseImpl$1", "getHint"));
        }

        AnonymousClass1(PsiScopeProcessor psiScopeProcessor) {
            super(psiScopeProcessor);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.scope.DelegatingScopeProcessor, org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
        public <T> T getHint(Key<T> key) {
            if (key == null) {
                $$$reportNull$$$0(0);
            }
            if (key == ElementClassHint.KEY) {
                return (T) new ElementClassHint() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$1$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint
                    public final boolean shouldProcess(ElementClassHint.DeclarationKind declarationKind) {
                        return PsiJavaFileBaseImpl.AnonymousClass1.lambda$getHint$0(declarationKind);
                    }
                };
            }
            return (T) super.getHint(key);
        }

        static /* synthetic */ boolean lambda$getHint$0(ElementClassHint.DeclarationKind declarationKind) {
            return declarationKind == ElementClassHint.DeclarationKind.CLASS;
        }
    }

    private static boolean processPackageDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiPackage psiPackage) {
        if (resolveState == null) {
            $$$reportNull$$$0(14);
        }
        if (psiPackage == null) {
            $$$reportNull$$$0(15);
        }
        if (!psiPackage.getQualifiedName().isEmpty()) {
            psiScopeProcessor = new AnonymousClass1(psiScopeProcessor);
        }
        return psiPackage.processDeclarations(psiScopeProcessor, resolveState, null, psiElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiSubstitutor createRawSubstitutor(PsiClass psiClass) {
        PsiSubstitutor psiSubstitutorCreateRawSubstitutor = JavaPsiFacade.getElementFactory(psiClass.getProject()).createRawSubstitutor(psiClass);
        if (psiSubstitutorCreateRawSubstitutor == null) {
            $$$reportNull$$$0(16);
        }
        return psiSubstitutorCreateRawSubstitutor;
    }

    private static boolean processOnDemandTarget(PsiElement psiElement, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement2) {
        if (psiElement instanceof PsiPackage) {
            return processPackageDeclarations(psiScopeProcessor, resolveState, psiElement2, (PsiPackage) psiElement);
        }
        if (psiElement instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) psiElement;
            PsiClass[] innerClasses = psiClass.getInnerClasses();
            if (psiClass.hasTypeParameters()) {
                resolveState = resolveState.put(PsiSubstitutor.KEY, createRawSubstitutor(psiClass));
            }
            for (PsiClass psiClass2 : innerClasses) {
                if (!psiScopeProcessor.execute(psiClass2, resolveState)) {
                    return false;
                }
            }
            return true;
        }
        LOG.error(psiElement);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        JavaLanguage javaLanguage = JavaLanguage.INSTANCE;
        if (javaLanguage == null) {
            $$$reportNull$$$0(18);
        }
        return javaLanguage;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportHolder
    public boolean importClass(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(19);
        }
        return JavaCodeStyleManager.getInstance(getProject()).addImport(this, psiClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public LanguageLevel getLanguageLevel() {
        LanguageLevel value = LANGUAGE_LEVEL_KEY.getValue(this);
        if (value == null) {
            $$$reportNull$$$0(20);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public void clearCaches() {
        super.clearCaches();
        putUserData(LANGUAGE_LEVEL_KEY, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl
    public void setOriginalFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(21);
        }
        super.setOriginalFile(psiFile);
        clearCaches();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LanguageLevel getLanguageLevelInner() {
        Key<String> key;
        Application application;
        Runnable runnable;
        String str;
        Throwable th;
        List<String> list;
        int iIndexOf;
        int i;
        if (this.myOriginalFile instanceof PsiJavaFile) {
            return ((PsiJavaFile) this.myOriginalFile).getLanguageLevel();
        }
        LanguageLevel languageLevel = (LanguageLevel) getUserData(PsiUtil.FILE_LANGUAGE_LEVEL_KEY);
        if (languageLevel != null) {
            return languageLevel;
        }
        final VirtualFile virtualFile = getVirtualFile();
        if (virtualFile == null) {
            virtualFile = getViewProvider().getVirtualFile();
        }
        String str2 = null;
        try {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(virtualFile.getInputStream(), StandardCharsets.UTF_8));
                try {
                    String line = bufferedReader.readLine();
                    if (line != null && line.startsWith("#!") && (iIndexOf = (list = ParametersListUtil.parse(line)).indexOf("--source")) > 0 && (i = iIndexOf + 1) < list.size()) {
                        str = list.get(i);
                        try {
                            LanguageLevel languageLevel2 = LanguageLevel.parse(str);
                            if (languageLevel2 != null) {
                                bufferedReader.close();
                                Key<String> key2 = SHEBANG_SOURCE_LEVEL;
                                if (!Objects.equals(str, virtualFile.getUserData(key2)) && virtualFile.isInLocalFileSystem()) {
                                    virtualFile.putUserData(key2, str);
                                    ApplicationManager.getApplication().invokeLater(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            FileContentUtilCore.reparseFiles(virtualFile);
                                        }
                                    }, ModalityState.NON_MODAL, ApplicationManager.getApplication().getDisposed());
                                }
                                return languageLevel2;
                            }
                            str2 = str;
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                bufferedReader.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                            throw th;
                        }
                    }
                    bufferedReader.close();
                    Key<String> key3 = SHEBANG_SOURCE_LEVEL;
                    if (!Objects.equals(str2, virtualFile.getUserData(key3)) && virtualFile.isInLocalFileSystem()) {
                        virtualFile.putUserData(key3, str2);
                        application = ApplicationManager.getApplication();
                        runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                FileContentUtilCore.reparseFiles(virtualFile);
                            }
                        };
                        application.invokeLater(runnable, ModalityState.NON_MODAL, ApplicationManager.getApplication().getDisposed());
                    }
                    return JavaPsiImplementationHelper.getInstance(getProject()).getEffectiveLanguageLevel(virtualFile);
                } catch (Throwable th4) {
                    str = null;
                    th = th4;
                }
            } catch (Throwable unused) {
                str2 = str;
                key = SHEBANG_SOURCE_LEVEL;
                if (!Objects.equals(str2, virtualFile.getUserData(key)) && virtualFile.isInLocalFileSystem()) {
                    virtualFile.putUserData(key, str2);
                    application = ApplicationManager.getApplication();
                    runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FileContentUtilCore.reparseFiles(virtualFile);
                        }
                    };
                    application.invokeLater(runnable, ModalityState.NON_MODAL, ApplicationManager.getApplication().getDisposed());
                }
                return JavaPsiImplementationHelper.getInstance(getProject()).getEffectiveLanguageLevel(virtualFile);
            }
        } catch (Throwable unused2) {
            key = SHEBANG_SOURCE_LEVEL;
            if (!Objects.equals(str2, virtualFile.getUserData(key))) {
                virtualFile.putUserData(key, str2);
                application = ApplicationManager.getApplication();
                runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        FileContentUtilCore.reparseFiles(virtualFile);
                    }
                };
            }
            return JavaPsiImplementationHelper.getInstance(getProject()).getEffectiveLanguageLevel(virtualFile);
        }
    }

    private static class MyCacheBuilder implements CachedValueProvider<MostlySingularMultiMap<String, SymbolCollectingProcessor.ResultWithContext>> {
        private final PsiJavaFileBaseImpl myFile;

        MyCacheBuilder(PsiJavaFileBaseImpl psiJavaFileBaseImpl) {
            this.myFile = psiJavaFileBaseImpl;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
        public CachedValueProvider.Result<MostlySingularMultiMap<String, SymbolCollectingProcessor.ResultWithContext>> compute() {
            SymbolCollectingProcessor symbolCollectingProcessor = new SymbolCollectingProcessor();
            this.myFile.processOnDemandPackages(symbolCollectingProcessor, ResolveState.initial(), this.myFile);
            return CachedValueProvider.Result.create(symbolCollectingProcessor.getResults(), PsiModificationTracker.MODIFICATION_COUNT, this.myFile);
        }
    }

    private static class MyResolveCacheProcessor implements Processor<SymbolCollectingProcessor.ResultWithContext> {
        private final PsiScopeProcessor myProcessor;
        private final ResolveState myState;

        MyResolveCacheProcessor(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState) {
            this.myProcessor = psiScopeProcessor;
            this.myState = resolveState;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.Processor
        public boolean process(SymbolCollectingProcessor.ResultWithContext resultWithContext) {
            PsiClass psiClassMo6092getContainingClass;
            PsiElement fileContext = resultWithContext.getFileContext();
            this.myProcessor.handleEvent(JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT, fileContext);
            PsiNamedElement element = resultWithContext.getElement();
            if ((element instanceof PsiClass) && (fileContext instanceof PsiImportStatement) && (psiClassMo6092getContainingClass = ((PsiClass) element).mo6092getContainingClass()) != null && psiClassMo6092getContainingClass.hasTypeParameters()) {
                return this.myProcessor.execute(element, this.myState.put(PsiSubstitutor.KEY, PsiJavaFileBaseImpl.createRawSubstitutor(psiClassMo6092getContainingClass)));
            }
            return this.myProcessor.execute(element, this.myState);
        }
    }
}
