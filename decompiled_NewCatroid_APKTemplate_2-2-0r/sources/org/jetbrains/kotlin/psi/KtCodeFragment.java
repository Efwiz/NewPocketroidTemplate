package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: KtCodeFragment.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b&\u0018\u0000 G2\u00020\u00012\u00020\u0002:\u0001GB9\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u000e\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0006J\u0012\u0010(\u001a\u00020&2\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010)\u001a\u00020\u0000H\u0014J\u0012\u0010*\u001a\u00020&2\b\u0010+\u001a\u0004\u0018\u00010\u001fH\u0016J\n\u0010,\u001a\u0004\u0018\u00010-H&J\n\u0010.\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010/\u001a\u0004\u0018\u00010\u0001J\n\u00100\u001a\u0004\u0018\u00010\u0010H\u0016J\n\u00101\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u00102\u001a\u0004\u0018\u00010-J\r\u00103\u001a\u00070\u001f¢\u0006\u0002\b4H\u0016J\n\u00105\u001a\u0004\u0018\u00010!H\u0016J\n\u00106\u001a\u0004\u0018\u00010!H\u0016J\u0006\u00107\u001a\u00020$J\b\u00108\u001a\u000209H\u0016J\b\u0010:\u001a\u0004\u0018\u00010;J\b\u0010<\u001a\u00020\u0006H\u0016J\u001a\u0010=\u001a\u00020&2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010>\u001a\u0004\u0018\u00010\u000bH\u0004J\u0012\u0010?\u001a\u00020&2\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010@\u001a\u00020\u001dH\u0016J\u0012\u0010A\u001a\u00020&2\b\u0010B\u001a\u0004\u0018\u00010\u0010H\u0016J\u0012\u0010C\u001a\u00020&2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0012\u0010D\u001a\u00020&2\b\u0010E\u001a\u0004\u0018\u00010!H\u0016J\u0012\u0010F\u001a\u00020&2\b\u0010B\u001a\u0004\u0018\u000109H\u0016R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u0004\u0018\u00010\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/psi/KtCodeFragmentBase;", "myProject", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "name", "", "text", "", "imports", "elementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "context", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/openapi/project/Project;Ljava/lang/String;Ljava/lang/CharSequence;Ljava/lang/String;Lcom/intellij/psi/tree/IElementType;Lcom/intellij/psi/PsiElement;)V", "exceptionHandler", "Lorg/jetbrains/kotlin/com/intellij/psi/JavaCodeFragment$ExceptionHandler;", "fakeContextForJavaFile", "getFakeContextForJavaFile", "()Lcom/intellij/psi/PsiElement;", "fakeContextForJavaFile$delegate", "Lkotlin/Lazy;", "importDirectives", "", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "getImportDirectives", "()Ljava/util/List;", "Ljava/util/LinkedHashSet;", "isPhysical", "", "resolveScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "superType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "thisType", "viewProvider", "Lorg/jetbrains/kotlin/com/intellij/psi/SingleRootFileViewProvider;", "addImport", "", PsiKeyword.IMPORT, "addImportsFromString", "clone", "forceResolveScope", "scope", "getContentElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "getContext", "getContextContainingFile", "getExceptionHandler", "getForcedResolveScope", "getOriginalContext", "getResolveScope", "Lorg/jetbrains/annotations/NotNull;", "getSuperType", "getThisType", "getViewProvider", "getVisibilityChecker", "Lorg/jetbrains/kotlin/com/intellij/psi/JavaCodeFragment$VisibilityChecker;", "importsAsImportList", "Lorg/jetbrains/kotlin/psi/KtImportList;", "importsToString", ConfigConstants.CONFIG_INIT_SECTION, "contentElementType", "initImports", "isValid", "setExceptionHandler", "checker", "setSuperType", "setThisType", "psiType", "setVisibilityChecker", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtCodeFragment extends KtFile implements KtCodeFragmentBase {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Key<Function0<KtElement>> FAKE_CONTEXT_FOR_JAVA_FILE;
    public static final String IMPORT_SEPARATOR = ",";
    private static final Logger LOG;
    private final PsiElement context;
    private JavaCodeFragment.ExceptionHandler exceptionHandler;

    /* JADX INFO: renamed from: fakeContextForJavaFile$delegate, reason: from kotlin metadata */
    private final Lazy fakeContextForJavaFile;
    private LinkedHashSet<String> imports;
    private boolean isPhysical;
    private final Project myProject;
    private GlobalSearchScope resolveScope;
    private PsiType superType;
    private PsiType thisType;
    private SingleRootFileViewProvider viewProvider;

    public abstract KtElement getContentElement();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public void setVisibilityChecker(JavaCodeFragment.VisibilityChecker checker) {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtCodeFragment(Project myProject, String name2, CharSequence text, String str, IElementType elementType, PsiElement psiElement) throws Throwable {
        Intrinsics.checkNotNullParameter(myProject, "myProject");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        PsiManager psiManager = PsiManager.getInstance(myProject);
        Intrinsics.checkNotNull(psiManager, "null cannot be cast to non-null type com.intellij.psi.impl.PsiManagerEx");
        FileViewProvider fileViewProviderCreateFileViewProvider = ((PsiManagerEx) psiManager).getFileManager().createFileViewProvider(new LightVirtualFile(name2, KotlinFileType.INSTANCE, text), true);
        Intrinsics.checkNotNullExpressionValue(fileViewProviderCreateFileViewProvider, "createFileViewProvider(...)");
        super(fileViewProviderCreateFileViewProvider, false);
        this.myProject = myProject;
        this.context = psiElement;
        FileViewProvider viewProvider = super.getViewProvider();
        Intrinsics.checkNotNull(viewProvider, "null cannot be cast to non-null type com.intellij.psi.SingleRootFileViewProvider");
        this.viewProvider = (SingleRootFileViewProvider) viewProvider;
        this.imports = new LinkedHashSet<>();
        this.fakeContextForJavaFile = LazyKt.lazy(new Function0<KtElement>() { // from class: org.jetbrains.kotlin.psi.KtCodeFragment$fakeContextForJavaFile$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtElement invoke() {
                Function0 function0 = (Function0) this.this$0.getCopyableUserData(KtCodeFragment.INSTANCE.getFAKE_CONTEXT_FOR_JAVA_FILE());
                if (function0 != null) {
                    return (KtElement) function0.invoke();
                }
                return null;
            }
        });
        getViewProvider().forceCachedPsi(this);
        IElementType CODE_FRAGMENT = TokenType.CODE_FRAGMENT;
        Intrinsics.checkNotNullExpressionValue(CODE_FRAGMENT, "CODE_FRAGMENT");
        init(CODE_FRAGMENT, elementType);
        if (psiElement != null) {
            initImports(str);
        }
        this.isPhysical = true;
    }

    private final PsiElement getFakeContextForJavaFile() {
        return (PsiElement) this.fakeContextForJavaFile.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl
    protected final void init(IElementType elementType, IElementType contentElementType) {
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        super.init(elementType, contentElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeFragment
    public void forceResolveScope(GlobalSearchScope scope) {
        this.resolveScope = scope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCodeFragment
    /* JADX INFO: renamed from: getForcedResolveScope, reason: from getter */
    public GlobalSearchScope getResolveScope() {
        return this.resolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical, reason: from getter */
    public boolean getIsPhysical() {
        return this.isPhysical;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getContext() {
        String elementTextWithContext;
        VirtualFile virtualFile;
        if (getFakeContextForJavaFile() != null) {
            return getFakeContextForJavaFile();
        }
        PsiElement psiElement = this.context;
        if (psiElement == null || (psiElement instanceof KtElement)) {
            return psiElement;
        }
        PsiFile psiFile = psiElement instanceof PsiFile ? (PsiFile) psiElement : null;
        if (psiFile == null || (virtualFile = psiFile.getVirtualFile()) == null || (elementTextWithContext = virtualFile.getPath()) == null) {
            elementTextWithContext = PsiUtilsKt.getElementTextWithContext(this.context);
        }
        Intrinsics.checkNotNull(elementTextWithContext);
        LOG.warn("CodeFragment with non-kotlin context should have fakeContextForJavaFile set: \noriginalContext = " + elementTextWithContext);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public GlobalSearchScope getResolveScope() {
        GlobalSearchScope resolveScope;
        PsiElement psiElement = this.context;
        if (psiElement == null || (resolveScope = psiElement.getResolveScope()) == null) {
            resolveScope = super.getResolveScope();
        }
        Intrinsics.checkNotNull(resolveScope);
        return resolveScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public KtCodeFragment clone() throws Throwable {
        CompositeElement compositeElementClone = calcTreeElement().clone();
        Intrinsics.checkNotNull(compositeElementClone, "null cannot be cast to non-null type com.intellij.psi.impl.source.tree.FileElement");
        PsiFileImpl psiFileImplCloneImpl = cloneImpl((FileElement) compositeElementClone);
        Intrinsics.checkNotNull(psiFileImplCloneImpl, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtCodeFragment");
        KtCodeFragment ktCodeFragment = (KtCodeFragment) psiFileImplCloneImpl;
        ktCodeFragment.isPhysical = false;
        ktCodeFragment.myOriginalFile = this;
        ktCodeFragment.imports = this.imports;
        SingleRootFileViewProvider singleRootFileViewProvider = new SingleRootFileViewProvider(PsiManager.getInstance(ktCodeFragment.myProject), new LightVirtualFile(ktCodeFragment.getName(), KotlinFileType.INSTANCE, ktCodeFragment.getText()), false);
        ktCodeFragment.viewProvider = singleRootFileViewProvider;
        singleRootFileViewProvider.forceCachedPsi(ktCodeFragment);
        return ktCodeFragment;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public final SingleRootFileViewProvider getViewProvider() {
        return this.viewProvider;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public PsiType getThisType() {
        return this.thisType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public void setThisType(PsiType psiType) {
        this.thisType = psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public PsiType getSuperType() {
        return this.superType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public void setSuperType(PsiType superType) {
        this.superType = superType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public String importsToString() {
        return CollectionsKt.joinToString$default(this.imports, ",", null, null, 0, null, null, 62, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public void addImportsFromString(String imports) {
        if (imports != null) {
            String str = imports;
            if (str.length() == 0) {
                return;
            }
            boolean z = false;
            Iterator it = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null).iterator();
            while (it.hasNext()) {
                addImport((String) it.next());
            }
            Project project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            add(new KtPsiFactory(project, z, 2, (DefaultConstructorMarker) null).createColon()).delete();
        }
    }

    public final void addImport(String str) {
        Object next;
        Intrinsics.checkNotNullParameter(str, "import");
        KtFile contextContainingFile = getContextContainingFile();
        if (contextContainingFile != null) {
            Iterator<T> it = contextContainingFile.getImportDirectives().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((KtImportDirective) next).getText(), str));
            if (next == null) {
                this.imports.add(str);
                clearCaches();
            }
        }
    }

    public final KtImportList importsAsImportList() {
        if (this.imports.isEmpty() || this.context == null) {
            return null;
        }
        return KtPsiFactory.Companion.contextual$default(KtPsiFactory.INSTANCE, this.context, false, 2, null).createFile("imports_for_codeFragment.kt", CollectionsKt.joinToString$default(this.imports, "\n", null, null, 0, null, null, 62, null)).getImportList();
    }

    @Override // org.jetbrains.kotlin.psi.KtFile
    public List<KtImportDirective> getImportDirectives() {
        KtImportList ktImportListImportsAsImportList = importsAsImportList();
        List<KtImportDirective> imports = ktImportListImportsAsImportList != null ? ktImportListImportsAsImportList.getImports() : null;
        return imports == null ? CollectionsKt.emptyList() : imports;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public JavaCodeFragment.VisibilityChecker getVisibilityChecker() {
        JavaCodeFragment.VisibilityChecker EVERYTHING_VISIBLE = JavaCodeFragment.VisibilityChecker.EVERYTHING_VISIBLE;
        Intrinsics.checkNotNullExpressionValue(EVERYTHING_VISIBLE, "EVERYTHING_VISIBLE");
        return EVERYTHING_VISIBLE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public void setExceptionHandler(JavaCodeFragment.ExceptionHandler checker) {
        this.exceptionHandler = checker;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment
    public JavaCodeFragment.ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public final KtFile getContextContainingFile() {
        KtElement originalContext = getOriginalContext();
        if (originalContext == null) {
            return null;
        }
        if (!originalContext.isValid()) {
            originalContext = null;
        }
        if (originalContext != null) {
            return originalContext.getContainingKtFile();
        }
        return null;
    }

    public final KtElement getOriginalContext() {
        PsiElement context = getContext();
        KtElement ktElement = context instanceof KtElement ? (KtElement) context : null;
        PsiFile containingFile = ktElement != null ? ktElement.getContainingFile() : null;
        KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
        return ktFile instanceof KtCodeFragment ? ((KtCodeFragment) ktFile).getOriginalContext() : ktElement;
    }

    private final void initImports(String imports) {
        if (imports != null) {
            String str = imports;
            if (str.length() > 0) {
                List<String> listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                for (String str2 : listSplit$default) {
                    String str3 = StringsKt.startsWith$default(str2, "import ", false, 2, (Object) null) ? str2 : null;
                    if (str3 == null) {
                        str3 = "import " + StringsKt.trim((CharSequence) str2).toString();
                    }
                    arrayList.add(str3);
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    addImport((String) it.next());
                }
            }
        }
    }

    /* JADX INFO: compiled from: KtCodeFragment.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u0013\u0010\u000b\u001a\u00070\f¢\u0006\u0002\b\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtCodeFragment$Companion;", "", "()V", "FAKE_CONTEXT_FOR_JAVA_FILE", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/psi/KtElement;", "getFAKE_CONTEXT_FOR_JAVA_FILE", "()Lcom/intellij/openapi/util/Key;", "IMPORT_SEPARATOR", "", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Key<Function0<KtElement>> getFAKE_CONTEXT_FOR_JAVA_FILE() {
            return KtCodeFragment.FAKE_CONTEXT_FOR_JAVA_FILE;
        }
    }

    static {
        Key<Function0<KtElement>> keyCreate = Key.create("FAKE_CONTEXT_FOR_JAVA_FILE");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        FAKE_CONTEXT_FOR_JAVA_FILE = keyCreate;
        Logger logger = Logger.getInstance((Class<?>) KtCodeFragment.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
