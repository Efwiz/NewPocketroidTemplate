package org.jetbrains.kotlin.analysis.providers.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.KotlinBuiltInDecompiler;
import org.jetbrains.kotlin.analysis.decompiler.psi.KotlinBuiltInFileType;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.ClsKotlinBinaryClassCache;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.KotlinClsStubBuilder;
import org.jetbrains.kotlin.analysis.project.structure.KtModule;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProviderFactory;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar.CoreJarFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContentImpl;
import org.jetbrains.kotlin.com.intellij.util.io.URLUtil;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinClassStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFileStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFunctionStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinObjectStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPlaceHolderStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinPropertyStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeAliasStubImpl;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: KotlinStaticDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u000234B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0006H\u0002J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020'H\u0002J\u001a\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\"\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u00100\u001a\u000201H\u0002J\u000e\u00102\u001a\b\u0012\u0004\u0012\u00020/0\u0005H\u0002R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0013\u001a\u00070\u0014¢\u0006\u0002\b\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationProviderFactory;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProviderFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "jarFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarFileSystem;", "additionalRoots", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "skipBuiltins", "", "(Lcom/intellij/openapi/project/Project;Ljava/util/Collection;Lcom/intellij/openapi/vfs/impl/jar/CoreJarFileSystem;Ljava/util/List;Z)V", "builtInDecompiler", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinBuiltInDecompiler;", "index", "Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationIndex;", "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "Lorg/jetbrains/annotations/NotNull;", "addToClassMap", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "addToFacadeFileMap", "file", "addToFunctionMap", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "addToPropertyMap", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "addToScriptMap", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "addToTypeAliasMap", "typeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "createDeclarationProvider", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "contextualModule", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "createKtFileStub", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFileStubImpl;", "fileContent", "Lorg/jetbrains/kotlin/com/intellij/util/indexing/FileContent;", "loadBuiltIns", "KtClassFileViewProvider", "KtDeclarationRecorder", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticDeclarationProviderFactory extends KotlinDeclarationProviderFactory {
    private final KotlinBuiltInDecompiler builtInDecompiler;
    private final KotlinStaticDeclarationIndex index;
    private final CoreJarFileSystem jarFileSystem;
    private final Project project;
    private final PsiManager psiManager;

    public /* synthetic */ KotlinStaticDeclarationProviderFactory(Project project, Collection collection, CoreJarFileSystem coreJarFileSystem, List list, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(project, collection, (i & 4) != 0 ? new CoreJarFileSystem() : coreJarFileSystem, (i & 8) != 0 ? CollectionsKt.emptyList() : list, (i & 16) != 0 ? false : z);
    }

    public KotlinStaticDeclarationProviderFactory(Project project, Collection<? extends KtFile> files, CoreJarFileSystem jarFileSystem, List<? extends VirtualFile> additionalRoots, boolean z) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(jarFileSystem, "jarFileSystem");
        Intrinsics.checkNotNullParameter(additionalRoots, "additionalRoots");
        this.project = project;
        this.jarFileSystem = jarFileSystem;
        this.index = new KotlinStaticDeclarationIndex();
        PsiManager psiManager = PsiManager.getInstance(project);
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        this.psiManager = psiManager;
        this.builtInDecompiler = new KotlinBuiltInDecompiler();
        KtDeclarationRecorder ktDeclarationRecorder = new KtDeclarationRecorder();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (!z) {
            for (KotlinFileStubImpl kotlinFileStubImpl : loadBuiltIns()) {
                _init_$processStub(this, kotlinFileStubImpl);
                String name2 = ((KtFile) kotlinFileStubImpl.getPsi()).getVirtualFile().getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                linkedHashSet.add(name2);
            }
        }
        final ClsKotlinBinaryClassCache companion = ClsKotlinBinaryClassCache.INSTANCE.getInstance();
        Iterator<? extends VirtualFile> it = additionalRoots.iterator();
        while (it.hasNext()) {
            Map<VirtualFile, KotlinFileStubImpl> mapProcessAdditionalRoot = KotlinFakeClsStubsCache.INSTANCE.processAdditionalRoot(it.next(), new Function1<VirtualFile, Map<VirtualFile, ? extends KotlinFileStubImpl>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Map<VirtualFile, KotlinFileStubImpl> invoke(VirtualFile additionalRoot) throws Throwable {
                    Intrinsics.checkNotNullParameter(additionalRoot, "additionalRoot");
                    final HashMap map = new HashMap();
                    final ClsKotlinBinaryClassCache clsKotlinBinaryClassCache = companion;
                    final KotlinStaticDeclarationProviderFactory kotlinStaticDeclarationProviderFactory = this;
                    VfsUtilCore.visitChildrenRecursively(additionalRoot, new VirtualFileVisitor<Void>(new VirtualFileVisitor.Option[0]) { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory.2.1
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor
                        public boolean visitFile(VirtualFile file) throws IOException {
                            KotlinFileStubImpl kotlinFileStubImpl2;
                            Intrinsics.checkNotNullParameter(file, "file");
                            if (file.isDirectory()) {
                                return true;
                            }
                            FileContent fileContentCreateByFile = FileContentImpl.createByFile(file);
                            Intrinsics.checkNotNullExpressionValue(fileContentCreateByFile, "createByFile(...)");
                            if (clsKotlinBinaryClassCache.isKotlinJvmCompiledFile(file, fileContentCreateByFile.getContent()) && Intrinsics.areEqual(file.getFileType(), JavaClassFileType.INSTANCE)) {
                                PsiFileStub<?> psiFileStubBuildFileStub = new KotlinClsStubBuilder().buildFileStub(fileContentCreateByFile);
                                kotlinFileStubImpl2 = psiFileStubBuildFileStub instanceof KotlinFileStubImpl ? (KotlinFileStubImpl) psiFileStubBuildFileStub : null;
                                if (kotlinFileStubImpl2 == null) {
                                    return true;
                                }
                                map.put(file, kotlinFileStubImpl2);
                                return true;
                            }
                            if (!Intrinsics.areEqual(file.getFileType(), KotlinBuiltInFileType.INSTANCE) || Intrinsics.areEqual(file.getExtension(), BuiltInSerializerProtocol.BUILTINS_FILE_EXTENSION)) {
                                return true;
                            }
                            PsiFileStub<?> psiFileStubBuildFileStub2 = kotlinStaticDeclarationProviderFactory.builtInDecompiler.getStubBuilder().buildFileStub(fileContentCreateByFile);
                            kotlinFileStubImpl2 = psiFileStubBuildFileStub2 instanceof KotlinFileStubImpl ? (KotlinFileStubImpl) psiFileStubBuildFileStub2 : null;
                            if (kotlinFileStubImpl2 == null) {
                                return true;
                            }
                            map.put(file, kotlinFileStubImpl2);
                            return true;
                        }
                    });
                    return map;
                }
            });
            if (mapProcessAdditionalRoot != null) {
                for (Map.Entry<VirtualFile, KotlinFileStubImpl> entry : mapProcessAdditionalRoot.entrySet()) {
                    final KotlinFileStubImpl value = entry.getValue();
                    final KtClassFileViewProvider ktClassFileViewProvider = new KtClassFileViewProvider(this.psiManager, entry.getKey());
                    value.setPsi(new KtFile(ktClassFileViewProvider) { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$3$fakeFile$1
                        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
                        /* JADX INFO: renamed from: isPhysical */
                        public boolean getIsPhysical() {
                            return false;
                        }

                        {
                            super(ktClassFileViewProvider, true);
                        }

                        @Override // org.jetbrains.kotlin.psi.KtFile, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl
                        /* JADX INFO: renamed from: getStub, reason: from getter */
                        public KotlinFileStubImpl get$ktFileStub() {
                            return this.$stub;
                        }
                    });
                    _init_$processStub(this, value);
                }
            }
        }
        Iterator<T> it2 = files.iterator();
        while (it2.hasNext()) {
            ((KtFile) it2.next()).accept(ktDeclarationRecorder);
        }
    }

    private final Collection<KotlinFileStubImpl> loadBuiltIns() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<T> it = StandardClassIds.INSTANCE.getBuiltInsPackages().iterator();
        while (it.hasNext()) {
            URL resource = classLoader.getResource(BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath((FqName) it.next()));
            if (resource != null) {
                Intrinsics.checkNotNull(resource);
                Pair<String, String> pairSplitJarUrl = URLUtil.splitJarUrl(resource.getPath());
                if (pairSplitJarUrl != null) {
                    VirtualFile virtualFileFindFileByPath = this.jarFileSystem.findFileByPath(pairSplitJarUrl.first + "!/" + pairSplitJarUrl.second);
                    if (virtualFileFindFileByPath != null) {
                        FileContent fileContentCreateByFile = FileContentImpl.createByFile(virtualFileFindFileByPath, this.project);
                        Intrinsics.checkNotNullExpressionValue(fileContentCreateByFile, "createByFile(...)");
                        KotlinFileStubImpl kotlinFileStubImplCreateKtFileStub = createKtFileStub(this.psiManager, this.builtInDecompiler, fileContentCreateByFile);
                        if (kotlinFileStubImplCreateKtFileStub != null) {
                            listCreateListBuilder.add(kotlinFileStubImplCreateKtFileStub);
                        }
                    }
                }
            }
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    private final KotlinFileStubImpl createKtFileStub(PsiManager psiManager, KotlinBuiltInDecompiler builtInDecompiler, FileContent fileContent) {
        PsiFileStub<?> psiFileStubBuildFileStub = builtInDecompiler.getStubBuilder().buildFileStub(fileContent);
        final KotlinFileStubImpl kotlinFileStubImpl = psiFileStubBuildFileStub instanceof KotlinFileStubImpl ? (KotlinFileStubImpl) psiFileStubBuildFileStub : null;
        if (kotlinFileStubImpl == null) {
            return null;
        }
        VirtualFile file = fileContent.getFile();
        Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
        final KtClassFileViewProvider ktClassFileViewProvider = new KtClassFileViewProvider(psiManager, file);
        kotlinFileStubImpl.setPsi(new KtFile(ktClassFileViewProvider) { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$createKtFileStub$fakeFile$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            /* JADX INFO: renamed from: isPhysical */
            public boolean getIsPhysical() {
                return false;
            }

            {
                super(ktClassFileViewProvider, true);
            }

            @Override // org.jetbrains.kotlin.psi.KtFile, org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl
            /* JADX INFO: renamed from: getStub, reason: from getter */
            public KotlinFileStubImpl get$ktFileStub() {
                return this.$ktFileStub;
            }
        });
        return kotlinFileStubImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: KotlinStaticDeclarationProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationProviderFactory$KtClassFileViewProvider;", "Lorg/jetbrains/kotlin/com/intellij/psi/SingleRootFileViewProvider;", "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "(Lcom/intellij/psi/PsiManager;Lcom/intellij/openapi/vfs/VirtualFile;)V", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class KtClassFileViewProvider extends SingleRootFileViewProvider {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KtClassFileViewProvider(PsiManager psiManager, VirtualFile virtualFile) {
            super(psiManager, virtualFile, true, (Language) KotlinLanguage.INSTANCE);
            Intrinsics.checkNotNullParameter(psiManager, "psiManager");
            Intrinsics.checkNotNullParameter(virtualFile, "virtualFile");
        }
    }

    /* JADX INFO: compiled from: KotlinStaticDeclarationProvider.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationProviderFactory$KtDeclarationRecorder;", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", "(Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationProviderFactory;)V", "visitClassOrObject", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "visitKtFile", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "visitNamedFunction", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "visitProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class KtDeclarationRecorder extends KtVisitorVoid {
        public KtDeclarationRecorder() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
        public void visitElement(PsiElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitKtFile(KtFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            KotlinStaticDeclarationProviderFactory.this.addToFacadeFileMap(file);
            KtScript script = file.getScript();
            if (script != null) {
                KotlinStaticDeclarationProviderFactory.this.addToScriptMap(script);
            }
            super.visitKtFile(file);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitClassOrObject(KtClassOrObject classOrObject) {
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            KotlinStaticDeclarationProviderFactory.this.addToClassMap(classOrObject);
            super.visitClassOrObject(classOrObject);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitTypeAlias(KtTypeAlias typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            KotlinStaticDeclarationProviderFactory.this.addToTypeAliasMap(typeAlias);
            super.visitTypeAlias(typeAlias);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitNamedFunction(KtNamedFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            KotlinStaticDeclarationProviderFactory.this.addToFunctionMap(function);
            super.visitNamedFunction(function);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitProperty(KtProperty property) {
            Intrinsics.checkNotNullParameter(property, "property");
            KotlinStaticDeclarationProviderFactory.this.addToPropertyMap(property);
            super.visitProperty(property);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addToFacadeFileMap(KtFile file) {
        if (file.hasTopLevelCallables()) {
            Map<FqName, Set<KtFile>> facadeFileMap$analysis_api_providers = this.index.getFacadeFileMap$analysis_api_providers();
            FqName packageFqName = file.getPackageFqName();
            final AnonymousClass1 anonymousClass1 = new Function1<FqName, Set<KtFile>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory.addToFacadeFileMap.1
                @Override // kotlin.jvm.functions.Function1
                public final Set<KtFile> invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new LinkedHashSet();
                }
            };
            facadeFileMap$analysis_api_providers.computeIfAbsent(packageFqName, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KotlinStaticDeclarationProviderFactory.addToFacadeFileMap$lambda$6(anonymousClass1, obj);
                }
            }).add(file);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set addToFacadeFileMap$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addToScriptMap(KtScript script) {
        Map<FqName, Set<KtScript>> scriptMap$analysis_api_providers = this.index.getScriptMap$analysis_api_providers();
        FqName fqNameMo7391getFqName = script.mo7391getFqName();
        final C42431 c42431 = new Function1<FqName, Set<KtScript>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory.addToScriptMap.1
            @Override // kotlin.jvm.functions.Function1
            public final Set<KtScript> invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new LinkedHashSet();
            }
        };
        scriptMap$analysis_api_providers.computeIfAbsent(fqNameMo7391getFqName, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return KotlinStaticDeclarationProviderFactory.addToScriptMap$lambda$7(c42431, obj);
            }
        }).add(script);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set addToScriptMap$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addToClassMap(KtClassOrObject classOrObject) {
        ClassId classId = classOrObject.getClassId();
        if (classId != null) {
            Map<FqName, Set<KtClassOrObject>> classMap$analysis_api_providers = this.index.getClassMap$analysis_api_providers();
            FqName packageFqName = classId.getPackageFqName();
            final KotlinStaticDeclarationProviderFactory$addToClassMap$1$1 kotlinStaticDeclarationProviderFactory$addToClassMap$1$1 = new Function1<FqName, Set<KtClassOrObject>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$addToClassMap$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Set<KtClassOrObject> invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new LinkedHashSet();
                }
            };
            classMap$analysis_api_providers.computeIfAbsent(packageFqName, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KotlinStaticDeclarationProviderFactory.addToClassMap$lambda$9$lambda$8(kotlinStaticDeclarationProviderFactory$addToClassMap$1$1, obj);
                }
            }).add(classOrObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set addToClassMap$lambda$9$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addToTypeAliasMap(KtTypeAlias typeAlias) {
        ClassId classId = typeAlias.getClassId();
        if (classId != null) {
            Map<FqName, Set<KtTypeAlias>> typeAliasMap$analysis_api_providers = this.index.getTypeAliasMap$analysis_api_providers();
            FqName packageFqName = classId.getPackageFqName();
            final KotlinStaticDeclarationProviderFactory$addToTypeAliasMap$1$1 kotlinStaticDeclarationProviderFactory$addToTypeAliasMap$1$1 = new Function1<FqName, Set<KtTypeAlias>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$addToTypeAliasMap$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Set<KtTypeAlias> invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new LinkedHashSet();
                }
            };
            typeAliasMap$analysis_api_providers.computeIfAbsent(packageFqName, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KotlinStaticDeclarationProviderFactory.addToTypeAliasMap$lambda$11$lambda$10(kotlinStaticDeclarationProviderFactory$addToTypeAliasMap$1$1, obj);
                }
            }).add(typeAlias);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set addToTypeAliasMap$lambda$11$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addToFunctionMap(KtNamedFunction function) {
        if (function.isTopLevel()) {
            PsiElement parent = function.getLightParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
            FqName packageFqName = ((KtFile) parent).getPackageFqName();
            Map<FqName, Set<KtNamedFunction>> topLevelFunctionMap$analysis_api_providers = this.index.getTopLevelFunctionMap$analysis_api_providers();
            final C42411 c42411 = new Function1<FqName, Set<KtNamedFunction>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory.addToFunctionMap.1
                @Override // kotlin.jvm.functions.Function1
                public final Set<KtNamedFunction> invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new LinkedHashSet();
                }
            };
            topLevelFunctionMap$analysis_api_providers.computeIfAbsent(packageFqName, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KotlinStaticDeclarationProviderFactory.addToFunctionMap$lambda$12(c42411, obj);
                }
            }).add(function);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set addToFunctionMap$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addToPropertyMap(KtProperty property) {
        if (property.isTopLevel()) {
            PsiElement parent = property.getLightParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
            FqName packageFqName = ((KtFile) parent).getPackageFqName();
            Map<FqName, Set<KtProperty>> topLevelPropertyMap$analysis_api_providers = this.index.getTopLevelPropertyMap$analysis_api_providers();
            final C42421 c42421 = new Function1<FqName, Set<KtProperty>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory.addToPropertyMap.1
                @Override // kotlin.jvm.functions.Function1
                public final Set<KtProperty> invoke(FqName it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new LinkedHashSet();
                }
            };
            topLevelPropertyMap$analysis_api_providers.computeIfAbsent(packageFqName, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KotlinStaticDeclarationProviderFactory.addToPropertyMap$lambda$13(c42421, obj);
                }
            }).add(property);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set addToPropertyMap$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void _init_$indexStub(KotlinStaticDeclarationProviderFactory kotlinStaticDeclarationProviderFactory, StubElement<?> stubElement) {
        if (stubElement instanceof KotlinClassStubImpl) {
            KotlinClassStubImpl kotlinClassStubImpl = (KotlinClassStubImpl) stubElement;
            T psi = kotlinClassStubImpl.getPsi();
            Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
            kotlinStaticDeclarationProviderFactory.addToClassMap((KtClassOrObject) psi);
            List<StubElement> childrenStubs = kotlinClassStubImpl.getChildrenStubs();
            Intrinsics.checkNotNullExpressionValue(childrenStubs, "getChildrenStubs(...)");
            Iterator<T> it = childrenStubs.iterator();
            while (it.hasNext()) {
                _init_$indexStub(kotlinStaticDeclarationProviderFactory, (StubElement) it.next());
            }
            return;
        }
        if (stubElement instanceof KotlinObjectStubImpl) {
            KotlinObjectStubImpl kotlinObjectStubImpl = (KotlinObjectStubImpl) stubElement;
            T psi2 = kotlinObjectStubImpl.getPsi();
            Intrinsics.checkNotNullExpressionValue(psi2, "getPsi(...)");
            kotlinStaticDeclarationProviderFactory.addToClassMap((KtClassOrObject) psi2);
            List<StubElement> childrenStubs2 = kotlinObjectStubImpl.getChildrenStubs();
            Intrinsics.checkNotNullExpressionValue(childrenStubs2, "getChildrenStubs(...)");
            Iterator<T> it2 = childrenStubs2.iterator();
            while (it2.hasNext()) {
                _init_$indexStub(kotlinStaticDeclarationProviderFactory, (StubElement) it2.next());
            }
            return;
        }
        if (stubElement instanceof KotlinTypeAliasStubImpl) {
            T psi3 = ((KotlinTypeAliasStubImpl) stubElement).getPsi();
            Intrinsics.checkNotNullExpressionValue(psi3, "getPsi(...)");
            kotlinStaticDeclarationProviderFactory.addToTypeAliasMap((KtTypeAlias) psi3);
            return;
        }
        if (stubElement instanceof KotlinFunctionStubImpl) {
            T psi4 = ((KotlinFunctionStubImpl) stubElement).getPsi();
            Intrinsics.checkNotNullExpressionValue(psi4, "getPsi(...)");
            kotlinStaticDeclarationProviderFactory.addToFunctionMap((KtNamedFunction) psi4);
            return;
        }
        if (stubElement instanceof KotlinPropertyStubImpl) {
            T psi5 = ((KotlinPropertyStubImpl) stubElement).getPsi();
            Intrinsics.checkNotNullExpressionValue(psi5, "getPsi(...)");
            kotlinStaticDeclarationProviderFactory.addToPropertyMap((KtProperty) psi5);
        } else if ((stubElement instanceof KotlinPlaceHolderStubImpl) && Intrinsics.areEqual(((KotlinPlaceHolderStubImpl) stubElement).getStubType(), KtStubElementTypes.CLASS_BODY)) {
            List<StubElement> childrenStubs3 = stubElement.getChildrenStubs();
            Intrinsics.checkNotNullExpressionValue(childrenStubs3, "getChildrenStubs(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : childrenStubs3) {
                if (obj instanceof KotlinClassOrObjectStub) {
                    arrayList.add(obj);
                }
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                _init_$indexStub(kotlinStaticDeclarationProviderFactory, (StubElement) it3.next());
            }
        }
    }

    private static final void _init_$processStub(KotlinStaticDeclarationProviderFactory kotlinStaticDeclarationProviderFactory, KotlinFileStubImpl kotlinFileStubImpl) {
        PsiFile psi = kotlinFileStubImpl.getPsi();
        Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
        KtFile ktFile = (KtFile) psi;
        kotlinStaticDeclarationProviderFactory.addToFacadeFileMap(ktFile);
        List<String> facadePartSimpleNames = kotlinFileStubImpl.getFacadePartSimpleNames();
        if (facadePartSimpleNames != null) {
            FqName packageFqName = kotlinFileStubImpl.getPackageFqName();
            Iterator<String> it = facadePartSimpleNames.iterator();
            while (it.hasNext()) {
                FqName fqNameChild = packageFqName.child(Name.identifier(it.next()));
                Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
                Map<FqName, Set<KtFile>> multiFileClassPartMap$analysis_api_providers = kotlinStaticDeclarationProviderFactory.index.getMultiFileClassPartMap$analysis_api_providers();
                final KotlinStaticDeclarationProviderFactory$processStub$1 kotlinStaticDeclarationProviderFactory$processStub$1 = new Function1<FqName, Set<KtFile>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$processStub$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Set<KtFile> invoke(FqName it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return new LinkedHashSet();
                    }
                };
                multiFileClassPartMap$analysis_api_providers.computeIfAbsent(fqNameChild, new Function() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticDeclarationProviderFactory$$ExternalSyntheticLambda5
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return KotlinStaticDeclarationProviderFactory.processStub$lambda$16(kotlinStaticDeclarationProviderFactory$processStub$1, obj);
                    }
                }).add(ktFile);
            }
        }
        List<StubElement> childrenStubs = kotlinFileStubImpl.getChildrenStubs();
        Intrinsics.checkNotNullExpressionValue(childrenStubs, "getChildrenStubs(...)");
        Iterator<T> it2 = childrenStubs.iterator();
        while (it2.hasNext()) {
            _init_$indexStub(kotlinStaticDeclarationProviderFactory, (StubElement) it2.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set processStub$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProviderFactory
    public KotlinDeclarationProvider createDeclarationProvider(GlobalSearchScope scope, KtModule contextualModule) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return new KotlinStaticDeclarationProvider(this.index, scope);
    }
}
