package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.TreeBackedLighterAST;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.roots.impl.PushedFilePropertiesRetriever;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Trinity;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.StubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;
import org.jetbrains.kotlin.com.intellij.util.indexing.IndexedFile;
import org.jetbrains.kotlin.com.intellij.util.indexing.IndexingDataKeys;
import org.jetbrains.kotlin.com.intellij.util.indexing.PsiDependentFileContent;

/* JADX INFO: loaded from: classes3.dex */
public final class StubTreeBuilder {
    private static final Logger LOG = Logger.getInstance((Class<?>) StubTreeBuilder.class);
    private static final Key<Stub> stubElementKey = Key.create("stub.tree.for.file.content");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 9 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 9 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "file";
                break;
            case 2:
            case 3:
            case 6:
                objArr[0] = "inputData";
                break;
            case 4:
            case 7:
                objArr[0] = "stubBuilderType";
                break;
            case 5:
                objArr[0] = "builder";
                break;
            case 8:
                objArr[0] = "viewProvider";
                break;
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTreeBuilder";
                break;
            default:
                objArr[0] = "fileType";
                break;
        }
        if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTreeBuilder";
        } else {
            objArr[1] = "getStubbedRoots";
        }
        switch (i) {
            case 1:
                objArr[2] = "getStubBuilderType";
                break;
            case 2:
            case 6:
            case 7:
                objArr[2] = "buildStubTree";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "handleStubBuilderException";
                break;
            case 8:
                objArr[2] = "getStubbedRoots";
                break;
            case 9:
                break;
            default:
                objArr[2] = "requiresContentToFindBuilder";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 9) {
            throw new IllegalStateException(str2);
        }
    }

    private StubTreeBuilder() {
    }

    static StubBuilderType getStubBuilderType(IndexedFile indexedFile, boolean z) {
        List<String> listEmptyList;
        if (indexedFile == null) {
            $$$reportNull$$$0(1);
        }
        FileType fileType = indexedFile.getFileType();
        BinaryFileStubBuilder binaryFileStubBuilderForFileType = BinaryFileStubBuilders.INSTANCE.forFileType(fileType);
        if (binaryFileStubBuilderForFileType != null) {
            if (binaryFileStubBuilderForFileType instanceof BinaryFileStubBuilder.CompositeBinaryFileStubBuilder) {
                BinaryFileStubBuilder.CompositeBinaryFileStubBuilder compositeBinaryFileStubBuilder = (BinaryFileStubBuilder.CompositeBinaryFileStubBuilder) binaryFileStubBuilderForFileType;
                return new StubBuilderType(compositeBinaryFileStubBuilder, compositeBinaryFileStubBuilder.getSubBuilder((FileContent) indexedFile));
            }
            return new StubBuilderType(binaryFileStubBuilderForFileType);
        }
        if (!(fileType instanceof LanguageFileType)) {
            return null;
        }
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(((LanguageFileType) fileType).getLanguage());
        if (parserDefinitionForLanguage == null) {
            return null;
        }
        IFileElementType fileNodeType = parserDefinitionForLanguage.getFileNodeType();
        if (!(fileNodeType instanceof IStubFileElementType)) {
            return null;
        }
        VirtualFile file = indexedFile.getFile();
        IStubFileElementType iStubFileElementType = (IStubFileElementType) fileNodeType;
        boolean zShouldBuildStubFor = iStubFileElementType.shouldBuildStubFor(file);
        if (z && !zShouldBuildStubFor) {
            return null;
        }
        PushedFilePropertiesRetriever pushedFilePropertiesRetriever = PushedFilePropertiesRetriever.getInstance();
        if (pushedFilePropertiesRetriever != null) {
            listEmptyList = pushedFilePropertiesRetriever.dumpSortedPushedProperties(file);
        } else {
            listEmptyList = Collections.emptyList();
        }
        return new StubBuilderType(iStubFileElementType, listEmptyList);
    }

    public static Stub buildStubTree(FileContent fileContent) {
        if (fileContent == null) {
            $$$reportNull$$$0(2);
        }
        StubBuilderType stubBuilderType = getStubBuilderType(fileContent, false);
        if (stubBuilderType == null) {
            return null;
        }
        return buildStubTree(fileContent, stubBuilderType);
    }

    private static <T> T handleStubBuilderException(FileContent fileContent, StubBuilderType stubBuilderType, ThrowableComputable<T, Exception> throwableComputable) {
        if (fileContent == null) {
            $$$reportNull$$$0(3);
        }
        if (stubBuilderType == null) {
            $$$reportNull$$$0(4);
        }
        if (throwableComputable == null) {
            $$$reportNull$$$0(5);
        }
        try {
            return throwableComputable.compute();
        } catch (Exception e) {
            if (e instanceof ControlFlowException) {
                ExceptionUtil.rethrowUnchecked(e);
            }
            LOG.error((Throwable) PluginException.createByClass("Failed to build stub tree for " + fileContent.getFileName(), e, stubBuilderType.getClassToBlameInCaseOfException()));
            return null;
        }
    }

    public static Stub buildStubTree(final FileContent fileContent, StubBuilderType stubBuilderType) {
        Stub stub;
        if (fileContent == null) {
            $$$reportNull$$$0(6);
        }
        if (stubBuilderType == null) {
            $$$reportNull$$$0(7);
        }
        Key<Stub> key = stubElementKey;
        Stub stub2 = (Stub) fileContent.getUserData(key);
        if (stub2 != null) {
            return stub2;
        }
        synchronized (fileContent) {
            try {
                Stub stub3 = (Stub) fileContent.getUserData(key);
                if (stub3 != null) {
                    return stub3;
                }
                final BinaryFileStubBuilder binaryFileStubBuilder = stubBuilderType.getBinaryFileStubBuilder();
                if (binaryFileStubBuilder != null) {
                    stub = (Stub) handleStubBuilderException(fileContent, stubBuilderType, new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeBuilder$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                        public final Object compute() {
                            return binaryFileStubBuilder.buildStubTree(fileContent);
                        }
                    });
                    if ((stub instanceof PsiFileStubImpl) && !((PsiFileStubImpl) stub).rootsAreSet()) {
                        ((PsiFileStubImpl) stub).setStubRoots(new PsiFileStub[]{(PsiFileStubImpl) stub});
                    }
                } else {
                    CharSequence contentAsText = fileContent.getContentAsText();
                    PsiDependentFileContent psiDependentFileContent = (PsiDependentFileContent) fileContent;
                    FileViewProvider viewProvider = psiDependentFileContent.getPsiFile().getViewProvider();
                    final PsiFile stubBindingRoot = viewProvider.getStubBindingRoot();
                    stubBindingRoot.putUserData(IndexingDataKeys.FILE_TEXT_CONTENT_KEY, contentAsText);
                    stubBindingRoot.getManager().startBatchFilesProcessingMode();
                    try {
                        IStubFileElementType<?> elementTypeForStubBuilder = ((PsiFileImpl) stubBindingRoot).getElementTypeForStubBuilder();
                        if (elementTypeForStubBuilder != null) {
                            final StubBuilder builder = elementTypeForStubBuilder.getBuilder();
                            if (builder instanceof LightStubBuilder) {
                                LightStubBuilder.FORCED_AST.set(psiDependentFileContent.getLighterAST());
                            }
                            stub3 = (Stub) handleStubBuilderException(fileContent, stubBuilderType, new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeBuilder$$ExternalSyntheticLambda1
                                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                                public final Object compute() {
                                    return builder.buildStubTree(stubBindingRoot);
                                }
                            });
                            List<Pair<IStubFileElementType, PsiFile>> stubbedRoots = getStubbedRoots(viewProvider);
                            ArrayList arrayList = new ArrayList(stubbedRoots.size());
                            arrayList.add((PsiFileStub) stub3);
                            for (Pair<IStubFileElementType, PsiFile> pair : stubbedRoots) {
                                final PsiFile psiFile = pair.second;
                                if (stubBindingRoot != psiFile) {
                                    final StubBuilder builder2 = pair.first.getBuilder();
                                    if (builder2 instanceof LightStubBuilder) {
                                        LightStubBuilder.FORCED_AST.set(new TreeBackedLighterAST(psiFile.getNode()));
                                    }
                                    StubElement stubElement = (StubElement) handleStubBuilderException(fileContent, stubBuilderType, new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeBuilder$$ExternalSyntheticLambda2
                                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                                        public final Object compute() {
                                            return builder2.buildStubTree(psiFile);
                                        }
                                    });
                                    if (stubElement instanceof PsiFileStub) {
                                        arrayList.add((PsiFileStub) stubElement);
                                    }
                                    ensureNormalizedOrder(stubElement);
                                }
                            }
                            PsiFileStub[] psiFileStubArr = (PsiFileStub[]) arrayList.toArray(PsiFileStub.EMPTY_ARRAY);
                            for (PsiFileStub psiFileStub : psiFileStubArr) {
                                if (psiFileStub instanceof PsiFileStubImpl) {
                                    ((PsiFileStubImpl) psiFileStub).setStubRoots(psiFileStubArr);
                                }
                            }
                        }
                        stubBindingRoot.putUserData(IndexingDataKeys.FILE_TEXT_CONTENT_KEY, null);
                        stubBindingRoot.getManager().finishBatchFilesProcessingMode();
                        stub = stub3;
                    } catch (Throwable th) {
                        stubBindingRoot.putUserData(IndexingDataKeys.FILE_TEXT_CONTENT_KEY, null);
                        stubBindingRoot.getManager().finishBatchFilesProcessingMode();
                        throw th;
                    }
                }
                ensureNormalizedOrder(stub);
                fileContent.putUserData(stubElementKey, stub);
                return stub;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    private static void ensureNormalizedOrder(Stub stub) {
        if (stub instanceof StubBase) {
            ((StubBase) stub).myStubList.finalizeLoadingStage();
        }
    }

    public static List<Pair<IStubFileElementType, PsiFile>> getStubbedRoots(FileViewProvider fileViewProvider) {
        IStubFileElementType<?> elementTypeForStubBuilder;
        if (fileViewProvider == null) {
            $$$reportNull$$$0(8);
        }
        SmartList smartList = new SmartList();
        final PsiFile stubBindingRoot = fileViewProvider.getStubBindingRoot();
        for (Language language : fileViewProvider.getLanguages()) {
            PsiFile psi = fileViewProvider.getPsi(language);
            if ((psi instanceof PsiFileImpl) && (elementTypeForStubBuilder = ((PsiFileImpl) psi).getElementTypeForStubBuilder()) != null) {
                smartList.add(Trinity.create(language, elementTypeForStubBuilder, psi));
            }
        }
        ContainerUtil.sort(smartList, new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeBuilder$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return StubTreeBuilder.lambda$getStubbedRoots$3(stubBindingRoot, (Trinity) obj, (Trinity) obj2);
            }
        });
        List<Pair<IStubFileElementType, PsiFile>> map = ContainerUtil.map((Collection) smartList, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeBuilder$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                Trinity trinity = (Trinity) obj;
                return Pair.create((IStubFileElementType) trinity.second, (PsiFile) trinity.third);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(9);
        }
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ int lambda$getStubbedRoots$3(PsiFile psiFile, Trinity trinity, Trinity trinity2) {
        if (trinity.third == psiFile) {
            return trinity2.third == psiFile ? 0 : -1;
        }
        if (trinity2.third == psiFile) {
            return 1;
        }
        return StringUtil.compare(((Language) trinity.first).getID(), ((Language) trinity2.first).getID(), false);
    }
}
