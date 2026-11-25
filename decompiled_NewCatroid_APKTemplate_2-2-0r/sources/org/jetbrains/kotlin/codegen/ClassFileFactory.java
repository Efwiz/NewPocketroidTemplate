package org.jetbrains.kotlin.codegen;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.backend.common.output.OutputFileCollection;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.codegen.extensions.ClassFileFactoryFinalizerExtension;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.load.kotlin.ModuleMappingUtilKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.JvmModuleProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMappingKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.PackageParts;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.serialization.StringTableImpl;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class ClassFileFactory implements OutputFileCollection {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ClassBuilderFactory builderFactory;
    private final List<ClassFileFactoryFinalizerExtension> finalizers;
    private final Map<String, OutAndSourceFileList> generators;
    private boolean isDone;
    private final PackagePartRegistry packagePartRegistry;
    private final Set<File> sourceFiles;
    private final GenerationState state;

    /* JADX WARN: Code duplicated, block: B:10:0x0017 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:48:0x009e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 7 && i != 11 && i != 28) {
            switch (i) {
                default:
                    switch (i) {
                        case 19:
                        case 20:
                        case 21:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 15:
                case 16:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 7 && i != 11 && i != 28) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 19:
                        case 20:
                        case 21:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "builderFactory";
                break;
            case 2:
                objArr[0] = "finalizers";
                break;
            case 3:
            case 7:
            case 11:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 28:
                objArr[0] = "org/jetbrains/kotlin/codegen/ClassFileFactory";
                break;
            case 4:
            case 8:
            case 12:
                objArr[0] = "origin";
                break;
            case 5:
            case 9:
            case 13:
                objArr[0] = "asmType";
                break;
            case 6:
                objArr[0] = "sourceFile";
                break;
            case 10:
            case 14:
                objArr[0] = "sourceFiles";
                break;
            case 18:
                objArr[0] = "relativePath";
                break;
            case 22:
                objArr[0] = "fqName";
                break;
            case 23:
            case 25:
            case 26:
                objArr[0] = "files";
                break;
            case 24:
                objArr[0] = "facadeFqName";
                break;
            case 27:
                objArr[0] = "psiFiles";
                break;
            default:
                objArr[0] = "state";
                break;
        }
        if (i == 3) {
            objArr[1] = "getPackagePartRegistry";
        } else if (i == 7 || i == 11) {
            objArr[1] = "newVisitor";
        } else if (i != 28) {
            switch (i) {
                case 15:
                    objArr[1] = "newVisitor";
                    break;
                case 16:
                    objArr[1] = "asList";
                    break;
                case 17:
                    objArr[1] = "getCurrentOutput";
                    break;
                default:
                    switch (i) {
                        case 19:
                        case 20:
                            objArr[1] = "createText";
                            break;
                        case 21:
                            objArr[1] = "createTextForEachFile";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/codegen/ClassFileFactory";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "toIoFilesIgnoringNonPhysical";
        }
        switch (i) {
            case 3:
            case 7:
            case 11:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 28:
                break;
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
                objArr[2] = "newVisitor";
                break;
            case 18:
                objArr[2] = "get";
                break;
            case 22:
            case 23:
                objArr[2] = "forPackage";
                break;
            case 24:
            case 25:
                objArr[2] = "forMultifileClass";
                break;
            case 26:
                objArr[2] = "registerSourceFiles";
                break;
            case 27:
                objArr[2] = "toIoFilesIgnoringNonPhysical";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 7 && i != 11 && i != 28) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                    break;
                default:
                    switch (i) {
                        case 19:
                        case 20:
                        case 21:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public ClassFileFactory(GenerationState generationState, ClassBuilderFactory classBuilderFactory, List<ClassFileFactoryFinalizerExtension> list) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        if (classBuilderFactory == null) {
            $$$reportNull$$$0(1);
        }
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        this.generators = Collections.synchronizedMap(new LinkedHashMap());
        this.isDone = false;
        this.sourceFiles = new HashSet();
        this.packagePartRegistry = new PackagePartRegistry();
        this.state = generationState;
        this.builderFactory = classBuilderFactory;
        this.finalizers = list;
    }

    public GenerationState getGenerationState() {
        return this.state;
    }

    public PackagePartRegistry getPackagePartRegistry() {
        PackagePartRegistry packagePartRegistry = this.packagePartRegistry;
        if (packagePartRegistry == null) {
            $$$reportNull$$$0(3);
        }
        return packagePartRegistry;
    }

    public ClassBuilder newVisitor(JvmDeclarationOrigin jvmDeclarationOrigin, Type type, PsiFile psiFile) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(4);
        }
        if (type == null) {
            $$$reportNull$$$0(5);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(6);
        }
        ClassBuilder classBuilderNewVisitor = newVisitor(jvmDeclarationOrigin, type, (Collection<? extends PsiFile>) Collections.singletonList(psiFile));
        if (classBuilderNewVisitor == null) {
            $$$reportNull$$$0(7);
        }
        return classBuilderNewVisitor;
    }

    public ClassBuilder newVisitor(JvmDeclarationOrigin jvmDeclarationOrigin, Type type, Collection<? extends PsiFile> collection) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(8);
        }
        if (type == null) {
            $$$reportNull$$$0(9);
        }
        if (collection == null) {
            $$$reportNull$$$0(10);
        }
        ClassBuilder classBuilderNewClassBuilder = this.builderFactory.newClassBuilder(jvmDeclarationOrigin);
        this.generators.put(type.getInternalName() + ".class", new ClassBuilderAndSourceFileList(classBuilderNewClassBuilder, toIoFilesIgnoringNonPhysical(collection)));
        if (classBuilderNewClassBuilder == null) {
            $$$reportNull$$$0(11);
        }
        return classBuilderNewClassBuilder;
    }

    public ClassBuilder newVisitor(JvmDeclarationOrigin jvmDeclarationOrigin, Type type, List<File> list) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(12);
        }
        if (type == null) {
            $$$reportNull$$$0(13);
        }
        if (list == null) {
            $$$reportNull$$$0(14);
        }
        ClassBuilder classBuilderNewClassBuilder = this.builderFactory.newClassBuilder(jvmDeclarationOrigin);
        this.generators.put(type.getInternalName() + ".class", new ClassBuilderAndSourceFileList(classBuilderNewClassBuilder, list));
        if (classBuilderNewClassBuilder == null) {
            $$$reportNull$$$0(15);
        }
        return classBuilderNewClassBuilder;
    }

    public void done() {
        if (this.isDone) {
            return;
        }
        this.isDone = true;
        writeModuleMappings();
        Iterator<ClassFileFactoryFinalizerExtension> it = this.finalizers.iterator();
        while (it.hasNext()) {
            it.next().finalizeClassFactory(this);
        }
    }

    public void releaseGeneratedOutput() {
        this.generators.clear();
    }

    private void writeModuleMappings() {
        JvmModuleProtoBuf.Module.Builder builderNewBuilder = JvmModuleProtoBuf.Module.newBuilder();
        String mappingFileName = JvmCodegenUtil.getMappingFileName(this.state.getModuleName());
        StringTableImpl stringTableImpl = new StringTableImpl();
        ClassFileUtilsKt.addDataFromCompiledModule(builderNewBuilder, this.packagePartRegistry, stringTableImpl, this.state);
        Pair<ProtoBuf.StringTable, ProtoBuf.QualifiedNameTable> pairBuildProto = stringTableImpl.buildProto();
        builderNewBuilder.setStringTable(pairBuildProto.getFirst());
        builderNewBuilder.setQualifiedNameTable(pairBuildProto.getSecond());
        final JvmModuleProtoBuf.Module moduleBuild = builderNewBuilder.build();
        this.generators.put(mappingFileName, new OutAndSourceFileList(CollectionsKt.toList(this.sourceFiles)) { // from class: org.jetbrains.kotlin.codegen.ClassFileFactory.1
            @Override // org.jetbrains.kotlin.codegen.ClassFileFactory.OutAndSourceFileList
            public byte[] asBytes(ClassBuilderFactory classBuilderFactory) {
                return ModuleMappingKt.serializeToByteArray(moduleBuild, ClassFileFactory.this.state.getConfig().getMetadataVersion(), ((Boolean) ClassFileFactory.this.state.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getStrictMetadataVersionSemantics())).booleanValue() ? 1 : 0);
            }

            @Override // org.jetbrains.kotlin.codegen.ClassFileFactory.OutAndSourceFileList
            public String asText(ClassBuilderFactory classBuilderFactory) {
                return new String(asBytes(classBuilderFactory), StandardCharsets.UTF_8);
            }
        });
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFileCollection
    public List<OutputFile> asList() {
        done();
        List<OutputFile> currentOutput = getCurrentOutput();
        if (currentOutput == null) {
            $$$reportNull$$$0(16);
        }
        return currentOutput;
    }

    public List<OutputFile> getCurrentOutput() {
        List<OutputFile> map = CollectionsKt.map(this.generators.keySet(), new Function1() { // from class: org.jetbrains.kotlin.codegen.ClassFileFactory$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6377x1d4b92f5((String) obj);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(17);
        }
        return map;
    }

    /* JADX INFO: renamed from: lambda$getCurrentOutput$0$org-jetbrains-kotlin-codegen-ClassFileFactory, reason: not valid java name */
    /* synthetic */ OutputFile m6377x1d4b92f5(String str) {
        return new OutputClassFile(str);
    }

    @Override // org.jetbrains.kotlin.backend.common.output.OutputFileCollection
    public OutputFile get(String str) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        if (this.generators.containsKey(str)) {
            return new OutputClassFile(str);
        }
        return null;
    }

    public String createText() {
        String strCreateText = createText(null);
        if (strCreateText == null) {
            $$$reportNull$$$0(19);
        }
        return strCreateText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ModuleMappingException extends RuntimeException {
        public ModuleMappingException(String str) {
            super(str);
        }
    }

    public String createText(String str) {
        StringBuilder sb = new StringBuilder();
        for (OutputFile outputFile : asList()) {
            if (str == null || !outputFile.getRelativePath().startsWith(str)) {
                File file = new File(outputFile.getRelativePath());
                sb.append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT).append(file).append('\n');
                String extension = FilesKt.getExtension(file);
                extension.hashCode();
                if (extension.equals("class")) {
                    sb.append(outputFile.getContent());
                } else if (extension.equals("kotlin_module")) {
                    try {
                        for (Map.Entry<String, PackageParts> entry : ModuleMappingUtilKt.loadModuleMapping(ModuleMapping.INSTANCE, outputFile.getContent(), file.getPath(), DeserializationConfiguration.Default.INSTANCE, new Function1() { // from class: org.jetbrains.kotlin.codegen.ClassFileFactory$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return ClassFileFactory.lambda$createText$1((JvmMetadataVersion) obj);
                            }
                        }).getPackageFqName2Parts().entrySet()) {
                            sb.append("<package ").append(new FqName(entry.getKey())).append(": ").append(entry.getValue().getParts()).append(">\n");
                        }
                    } catch (ModuleMappingException e) {
                        sb.append(file).append(": ").append(e.getMessage()).append("\n");
                    }
                } else {
                    sb.append("Unknown output file: ").append(outputFile);
                }
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(20);
        }
        return string;
    }

    static /* synthetic */ Unit lambda$createText$1(JvmMetadataVersion jvmMetadataVersion) {
        throw new ModuleMappingException("Generated module has incompatible JVM metadata version: " + jvmMetadataVersion);
    }

    public Map<String, String> createTextForEachFile() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (OutputFile outputFile : asList()) {
            linkedHashMap.put(outputFile.getRelativePath(), outputFile.getContent());
        }
        return linkedHashMap;
    }

    public PackageCodegen forPackage(FqName fqName, Collection<KtFile> collection) {
        if (fqName == null) {
            $$$reportNull$$$0(22);
        }
        if (collection == null) {
            $$$reportNull$$$0(23);
        }
        this.sourceFiles.addAll(toIoFilesIgnoringNonPhysical(collection));
        return new PackageCodegenImpl(this.state, collection, fqName);
    }

    public MultifileClassCodegen forMultifileClass(FqName fqName, Collection<KtFile> collection) {
        if (fqName == null) {
            $$$reportNull$$$0(24);
        }
        if (collection == null) {
            $$$reportNull$$$0(25);
        }
        this.sourceFiles.addAll(toIoFilesIgnoringNonPhysical(collection));
        return new MultifileClassCodegenImpl(this.state, collection, fqName);
    }

    public void registerSourceFiles(Collection<File> collection) {
        if (collection == null) {
            $$$reportNull$$$0(26);
        }
        for (File file : collection) {
            if (file != null) {
                this.sourceFiles.add(file);
            }
        }
    }

    private static List<File> toIoFilesIgnoringNonPhysical(Collection<? extends PsiFile> collection) {
        VirtualFile virtualFile;
        if (collection == null) {
            $$$reportNull$$$0(27);
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (PsiFile psiFile : collection) {
            if (psiFile != null && (virtualFile = psiFile.getVirtualFile()) != null) {
                arrayList.add(new File(virtualFile.getPath()));
            }
        }
        return arrayList;
    }

    private class OutputClassFile implements OutputFile {
        private final String relativeClassFilePath;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[2];
            objArr[0] = "org/jetbrains/kotlin/codegen/ClassFileFactory$OutputClassFile";
            if (i == 1) {
                objArr[1] = "getSourceFiles";
            } else if (i == 2) {
                objArr[1] = "asByteArray";
            } else if (i == 3) {
                objArr[1] = "asText";
            } else if (i != 4) {
                objArr[1] = "getRelativePath";
            } else {
                objArr[1] = "toString";
            }
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
        }

        public OutputClassFile(String str) {
            this.relativeClassFilePath = str;
        }

        @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
        public String getRelativePath() {
            String str = this.relativeClassFilePath;
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            return str;
        }

        @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
        public List<File> getSourceFiles() {
            OutAndSourceFileList outAndSourceFileList = (OutAndSourceFileList) ClassFileFactory.this.generators.get(this.relativeClassFilePath);
            if (outAndSourceFileList == null) {
                throw new IllegalStateException("No record for binary file " + this.relativeClassFilePath);
            }
            List<File> list = outAndSourceFileList.sourceFiles;
            if (list == null) {
                $$$reportNull$$$0(1);
            }
            return list;
        }

        @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
        /* JADX INFO: renamed from: asByteArray */
        public byte[] getContent() {
            try {
                byte[] bArrAsBytes = ((OutAndSourceFileList) ClassFileFactory.this.generators.get(this.relativeClassFilePath)).asBytes(ClassFileFactory.this.builderFactory);
                if (bArrAsBytes == null) {
                    $$$reportNull$$$0(2);
                }
                return bArrAsBytes;
            } catch (RuntimeException e) {
                throw new RuntimeException("Error generating class file " + toString() + ": " + e.getMessage(), e);
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.output.OutputFile
        /* JADX INFO: renamed from: asText */
        public String getContent() {
            try {
                String strAsText = ((OutAndSourceFileList) ClassFileFactory.this.generators.get(this.relativeClassFilePath)).asText(ClassFileFactory.this.builderFactory);
                if (strAsText == null) {
                    $$$reportNull$$$0(3);
                }
                return strAsText;
            } catch (RuntimeException e) {
                throw new RuntimeException("Error generating class file " + toString() + ": " + e.getMessage(), e);
            }
        }

        public String toString() {
            String str = getRelativePath() + " (compiled from " + getSourceFiles() + Constant.AFTER_QUTO;
            if (str == null) {
                $$$reportNull$$$0(4);
            }
            return str;
        }
    }

    private static final class ClassBuilderAndSourceFileList extends OutAndSourceFileList {
        private final ClassBuilder classBuilder;

        private ClassBuilderAndSourceFileList(ClassBuilder classBuilder, List<File> list) {
            super(list);
            this.classBuilder = classBuilder;
        }

        @Override // org.jetbrains.kotlin.codegen.ClassFileFactory.OutAndSourceFileList
        public byte[] asBytes(ClassBuilderFactory classBuilderFactory) {
            byte[] bArrMo6372asBytes;
            synchronized (this) {
                bArrMo6372asBytes = classBuilderFactory.mo6372asBytes(this.classBuilder);
            }
            return bArrMo6372asBytes;
        }

        @Override // org.jetbrains.kotlin.codegen.ClassFileFactory.OutAndSourceFileList
        public String asText(ClassBuilderFactory classBuilderFactory) {
            return classBuilderFactory.asText(this.classBuilder);
        }
    }

    private static abstract class OutAndSourceFileList {
        protected final List<File> sourceFiles;

        public abstract byte[] asBytes(ClassBuilderFactory classBuilderFactory);

        public abstract String asText(ClassBuilderFactory classBuilderFactory);

        private OutAndSourceFileList(List<File> list) {
            this.sourceFiles = list;
        }
    }

    public void removeClasses(Set<String> set) {
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            this.generators.remove(it.next() + ".class");
        }
    }

    public List<KtFile> getInputFiles() {
        return this.state.getFiles();
    }
}
