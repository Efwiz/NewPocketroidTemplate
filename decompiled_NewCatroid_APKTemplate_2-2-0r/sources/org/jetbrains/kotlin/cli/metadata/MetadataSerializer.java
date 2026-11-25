package org.jetbrains.kotlin.cli.metadata;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.load.kotlin.PackagePartClassUtils;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.JvmModuleProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMappingKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.PackageParts;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragment;

/* JADX INFO: compiled from: MetadataSerializer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001%B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\n\u0010\u0015\u001a\u0004\u0018\u00010\u0002H\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0014J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0019H\u0002J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00022\u0006\u0010#\u001a\u00020$H\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/MetadataSerializer;", "Lorg/jetbrains/kotlin/cli/metadata/AbstractMetadataSerializer;", "Lorg/jetbrains/kotlin/cli/metadata/CommonAnalysisResult;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "dependOnOldBuiltIns", "", "definedMetadataVersion", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;ZLorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;)V", "totalFiles", "", "getTotalFiles", "()I", "setTotalFiles", "(I)V", "totalSize", "getTotalSize", "setTotalSize", "analyze", "createSerializerExtension", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "getClassFilePath", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackageFilePath", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", PsiTreeChangeEvent.PROP_FILE_NAME, "serialize", "", "analysisResult", "destDir", "Ljava/io/File;", "PackageSerializer", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class MetadataSerializer extends AbstractMetadataSerializer<CommonAnalysisResult> {
    private final boolean dependOnOldBuiltIns;
    private int totalFiles;
    private int totalSize;

    public /* synthetic */ MetadataSerializer(CompilerConfiguration compilerConfiguration, KotlinCoreEnvironment kotlinCoreEnvironment, boolean z, BuiltInsBinaryVersion builtInsBinaryVersion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerConfiguration, kotlinCoreEnvironment, z, (i & 8) != 0 ? null : builtInsBinaryVersion);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataSerializer(CompilerConfiguration configuration, KotlinCoreEnvironment environment, boolean z, BuiltInsBinaryVersion builtInsBinaryVersion) {
        super(configuration, environment, builtInsBinaryVersion);
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(environment, "environment");
        this.dependOnOldBuiltIns = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getTotalSize() {
        return this.totalSize;
    }

    protected final void setTotalSize(int i) {
        this.totalSize = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getTotalFiles() {
        return this.totalFiles;
    }

    protected final void setTotalFiles(int i) {
        this.totalFiles = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.metadata.AbstractMetadataSerializer
    public CommonAnalysisResult analyze() {
        return CommonAnalysisKt.runCommonAnalysisForSerialization(getEnvironment(), this.dependOnOldBuiltIns, new Function0<CommonDependenciesContainer>() { // from class: org.jetbrains.kotlin.cli.metadata.MetadataSerializer.analyze.1
            @Override // kotlin.jvm.functions.Function0
            public final CommonDependenciesContainer invoke() {
                return null;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.metadata.AbstractMetadataSerializer
    public void serialize(CommonAnalysisResult analysisResult, final File destDir) throws IOException {
        Intrinsics.checkNotNullParameter(analysisResult, "analysisResult");
        Intrinsics.checkNotNullParameter(destDir, "destDir");
        final LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(getEnvironment().getConfiguration());
        List<KtFile> sourceFiles = getEnvironment().getSourceFiles();
        final Project project = getEnvironment().getProject();
        ModuleDescriptor moduleDescriptor = analysisResult.getModuleDescriptor();
        final BindingContext bindingContext = analysisResult.getBindingContext();
        HashMap map = new HashMap();
        for (KtFile ktFile : sourceFiles) {
            final FqName packageFqName = ktFile.getPackageFqName();
            final ArrayList arrayList = new ArrayList();
            Iterator<KtDeclaration> it = ktFile.getDeclarations().iterator();
            while (it.hasNext()) {
                it.next().accept(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.cli.metadata.MetadataSerializer.serialize.1
                    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                    public void visitNamedFunction(KtNamedFunction function) {
                        Intrinsics.checkNotNullParameter(function, "function");
                        ArrayList<DeclarationDescriptor> arrayList2 = arrayList;
                        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) bindingContext.get(BindingContext.FUNCTION, function);
                        if (simpleFunctionDescriptor != null) {
                            arrayList2.add(simpleFunctionDescriptor);
                            return;
                        }
                        throw new IllegalStateException(("No descriptor found for function " + function.mo7391getFqName()).toString());
                    }

                    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                    public void visitProperty(KtProperty property) {
                        Intrinsics.checkNotNullParameter(property, "property");
                        ArrayList<DeclarationDescriptor> arrayList2 = arrayList;
                        VariableDescriptor variableDescriptor = (VariableDescriptor) bindingContext.get(BindingContext.VARIABLE, property);
                        if (variableDescriptor != null) {
                            arrayList2.add(variableDescriptor);
                            return;
                        }
                        throw new IllegalStateException(("No descriptor found for property " + property.mo7391getFqName()).toString());
                    }

                    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                    public void visitTypeAlias(KtTypeAlias typeAlias) {
                        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
                        ArrayList<DeclarationDescriptor> arrayList2 = arrayList;
                        TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) bindingContext.get(BindingContext.TYPE_ALIAS, typeAlias);
                        if (typeAliasDescriptor != null) {
                            arrayList2.add(typeAliasDescriptor);
                            return;
                        }
                        throw new IllegalStateException(("No descriptor found for type alias " + typeAlias.mo7391getFqName()).toString());
                    }

                    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                    public void visitClassOrObject(KtClassOrObject classOrObject) throws IOException {
                        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
                        ClassDescriptor classDescriptor = (ClassDescriptor) bindingContext.get(BindingContext.CLASS, classOrObject);
                        if (classDescriptor == null) {
                            throw new IllegalStateException(("No descriptor found for class " + classOrObject.mo7391getFqName()).toString());
                        }
                        new PackageSerializer(this, CollectionsKt.listOf(classDescriptor), CollectionsKt.emptyList(), packageFqName, new File(destDir, this.getClassFilePath(new ClassId(packageFqName, classDescriptor.getShortName()))), languageVersionSettings, project).run();
                    }
                });
            }
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                String name2 = ktFile.get$name();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                File file = new File(destDir, getPackageFilePath(packageFqName, name2));
                new PackageSerializer(this, CollectionsKt.emptyList(), arrayList2, packageFqName, file, languageVersionSettings, project).run();
                HashMap map2 = map;
                Object packageParts = map2.get(packageFqName);
                if (packageParts == null) {
                    String strAsString = packageFqName.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    packageParts = new PackageParts(strAsString);
                    map2.put(packageFqName, packageParts);
                }
                ((PackageParts) packageParts).addMetadataPart(FilesKt.getNameWithoutExtension(file));
            }
        }
        File file2 = new File(destDir, JvmCodegenUtil.getMappingFileName(JvmCodegenUtil.getModuleName(moduleDescriptor)));
        JvmModuleProtoBuf.Module.Builder builderNewBuilder = JvmModuleProtoBuf.Module.newBuilder();
        for (PackageParts packageParts2 : map.values()) {
            Intrinsics.checkNotNull(builderNewBuilder);
            packageParts2.addTo(builderNewBuilder);
        }
        JvmModuleProtoBuf.Module moduleBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(moduleBuild, "build(...)");
        byte[] bArrSerializeToByteArray = ModuleMappingKt.serializeToByteArray(moduleBuild, JvmMetadataVersion.INSTANCE, 0);
        file2.getParentFile().mkdirs();
        FilesKt.writeBytes(file2, bArrSerializeToByteArray);
    }

    protected KotlinSerializerExtensionBase createSerializerExtension() {
        return new MetadataSerializerExtension(getMetadataVersion());
    }

    private final String getPackageFilePath(FqName packageFqName, String fileName) {
        StringBuilder sb = new StringBuilder();
        String strAsString = packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return sb.append(StringsKt.replace$default(strAsString, '.', '/', false, 4, (Object) null)).append('/').append(PackagePartClassUtils.getFilePartShortName(fileName)).append(MetadataPackageFragment.DOT_METADATA_FILE_EXTENSION).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getClassFilePath(ClassId classId) {
        StringBuilder sb = new StringBuilder();
        String strAsString = classId.asSingleFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return sb.append(StringsKt.replace$default(strAsString, '.', '/', false, 4, (Object) null)).append(MetadataPackageFragment.DOT_METADATA_FILE_EXTENSION).toString();
    }

    /* JADX INFO: compiled from: MetadataSerializer.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0084\u0004\u0018\u00002\u00020\u0001BE\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u00020\u0015H\u0002J(\u0010\u0017\u001a\u00020\u00152\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u001e\u0010\u001a\u001a\u00020\u00152\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u001b\u001a\u00020\u0019H\u0002J\b\u0010\u001c\u001a\u00020\u0015H\u0002J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/MetadataSerializer$PackageSerializer;", "", "classes", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "members", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "destFile", "Ljava/io/File;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lorg/jetbrains/kotlin/cli/metadata/MetadataSerializer;Ljava/util/Collection;Ljava/util/Collection;Lorg/jetbrains/kotlin/name/FqName;Ljava/io/File;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/openapi/project/Project;)V", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment$Builder;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "run", "", "serializeBuiltInsFile", "serializeClasses", "parentSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializeMembers", "serializer", "serializeStringTable", "write", "stream", "Ljava/io/ByteArrayOutputStream;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected final class PackageSerializer {
        private final Collection<DeclarationDescriptor> classes;
        private final File destFile;
        private final KotlinSerializerExtensionBase extension;
        private final LanguageVersionSettings languageVersionSettings;
        private final Collection<DeclarationDescriptor> members;
        private final FqName packageFqName;
        private final Project project;
        private final ProtoBuf.PackageFragment.Builder proto;
        final /* synthetic */ MetadataSerializer this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public PackageSerializer(MetadataSerializer metadataSerializer, Collection<? extends DeclarationDescriptor> classes, Collection<? extends DeclarationDescriptor> members, FqName packageFqName, File destFile, LanguageVersionSettings languageVersionSettings, Project project) {
            Intrinsics.checkNotNullParameter(classes, "classes");
            Intrinsics.checkNotNullParameter(members, "members");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(destFile, "destFile");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            this.this$0 = metadataSerializer;
            this.classes = classes;
            this.members = members;
            this.packageFqName = packageFqName;
            this.destFile = destFile;
            this.languageVersionSettings = languageVersionSettings;
            this.project = project;
            this.proto = ProtoBuf.PackageFragment.newBuilder();
            this.extension = metadataSerializer.createSerializerExtension();
        }

        public /* synthetic */ PackageSerializer(MetadataSerializer metadataSerializer, Collection collection, Collection collection2, FqName fqName, File file, LanguageVersionSettings languageVersionSettings, Project project, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(metadataSerializer, collection, collection2, fqName, file, languageVersionSettings, (i & 32) != 0 ? null : project);
        }

        public final void run() throws IOException {
            DescriptorSerializer descriptorSerializerCreateTopLevel = DescriptorSerializer.INSTANCE.createTopLevel(this.extension, this.languageVersionSettings, this.project);
            serializeClasses(this.classes, descriptorSerializerCreateTopLevel, this.project);
            serializeMembers(this.members, descriptorSerializerCreateTopLevel);
            serializeStringTable();
            serializeBuiltInsFile();
        }

        private final void serializeClasses(Collection<? extends DeclarationDescriptor> classes, DescriptorSerializer parentSerializer, Project project) {
            for (DeclarationDescriptor declarationDescriptor : DescriptorSerializer.INSTANCE.sort(classes)) {
                if (declarationDescriptor instanceof ClassDescriptor) {
                    ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
                    if (classDescriptor.getClassKind() != ClassKind.ENUM_ENTRY) {
                        DescriptorSerializer descriptorSerializerCreate = DescriptorSerializer.INSTANCE.create(classDescriptor, this.extension, parentSerializer, this.languageVersionSettings, project);
                        MemberScope unsubstitutedInnerClassesScope = classDescriptor.getUnsubstitutedInnerClassesScope();
                        Intrinsics.checkNotNullExpressionValue(unsubstitutedInnerClassesScope, "getUnsubstitutedInnerClassesScope(...)");
                        serializeClasses(ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedInnerClassesScope, DescriptorKindFilter.CLASSIFIERS, null, 2, null), descriptorSerializerCreate, project);
                        this.proto.addClass_(descriptorSerializerCreate.classProto(classDescriptor).build());
                    }
                }
            }
        }

        private final void serializeMembers(Collection<? extends DeclarationDescriptor> members, DescriptorSerializer serializer) {
            this.proto.setPackage(serializer.packagePartProto(this.packageFqName, members).build());
        }

        private final void serializeStringTable() {
            Pair<ProtoBuf.StringTable, ProtoBuf.QualifiedNameTable> pairBuildProto = this.extension.getStringTable().buildProto();
            ProtoBuf.StringTable stringTableComponent1 = pairBuildProto.component1();
            ProtoBuf.QualifiedNameTable qualifiedNameTableComponent2 = pairBuildProto.component2();
            this.proto.setStrings(stringTableComponent1);
            this.proto.setQualifiedNames(qualifiedNameTableComponent2);
        }

        private final void serializeBuiltInsFile() throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream2);
            int[] numbers = this.extension.getMetadataVersion().getNumbers();
            dataOutputStream.writeInt(numbers.length);
            for (int i : numbers) {
                dataOutputStream.writeInt(i);
            }
            this.proto.build().writeTo(byteArrayOutputStream2);
            write(byteArrayOutputStream);
        }

        private final void write(ByteArrayOutputStream stream) {
            MetadataSerializer metadataSerializer = this.this$0;
            metadataSerializer.setTotalSize(metadataSerializer.getTotalSize() + stream.size());
            MetadataSerializer metadataSerializer2 = this.this$0;
            metadataSerializer2.setTotalFiles(metadataSerializer2.getTotalFiles() + 1);
            this.destFile.isDirectory();
            this.destFile.getParentFile().mkdirs();
            File file = this.destFile;
            byte[] byteArray = stream.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            FilesKt.writeBytes(file, byteArray);
        }
    }
}
