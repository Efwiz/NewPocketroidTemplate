package org.jetbrains.kotlin.psi2ir.generators;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.SourceRangeInfo;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.ExternalDependenciesGenerator;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi2ir.transformations.InsertImplicitCastsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.lazy.descriptors.PackageDescriptorUtilKt;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: ModuleGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\b\u0002\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0013\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015H\u0016J\u001e\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0014\u0010\u0019\u001a\u00020\u00122\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ModuleGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/Generator;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;Ljava/util/Map;)V", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "createEmptyIrFile", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrFileImpl;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "createStubIrForErrorClass", "", "generateModuleFragment", "ktFiles", "", "generateSingleFile", "irDeclarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "generateUnboundSymbolsAsDependencies", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ModuleGenerator implements Generator {
    private final GeneratorContext context;
    private final Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol;

    public ModuleGenerator(GeneratorContext context, Map<DeclarationDescriptor, IrSymbol> map) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.expectDescriptorToSymbol = map;
    }

    public /* synthetic */ ModuleGenerator(GeneratorContext generatorContext, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(generatorContext, (i & 2) != 0 ? null : map);
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.context;
    }

    public IrModuleFragment generateModuleFragment(Collection<? extends KtFile> ktFiles) {
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        IrModuleFragmentImpl irModuleFragmentImpl = new IrModuleFragmentImpl(getContext().getModuleDescriptor(), getContext().getIrBuiltIns(), null, 4, null);
        Set<KtFile> set = CollectionsKt.toSet(ktFiles);
        List<IrFile> files = irModuleFragmentImpl.getFiles();
        for (KtFile ktFile : set) {
            files.add(generateSingleFile(new DeclarationGenerator(getContext().createFileScopeContext(ktFile)), ktFile, irModuleFragmentImpl));
        }
        return irModuleFragmentImpl;
    }

    public final void generateUnboundSymbolsAsDependencies(List<? extends IrProvider> irProviders) {
        Intrinsics.checkNotNullParameter(irProviders, "irProviders");
        new ExternalDependenciesGenerator(getContext().getSymbolTable(), irProviders).generateUnboundSymbolsAsDependencies();
    }

    public final IrFileImpl generateSingleFile(DeclarationGenerator irDeclarationGenerator, KtFile ktFile, IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(irDeclarationGenerator, "irDeclarationGenerator");
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        Intrinsics.checkNotNullParameter(module, "module");
        IrFileImpl irFileImplCreateEmptyIrFile = createEmptyIrFile(ktFile, module);
        ConstantValueGenerator constantValueGenerator = irDeclarationGenerator.getContext().getConstantValueGenerator();
        for (KtAnnotationEntry ktAnnotationEntry : ktFile.getAnnotationEntries()) {
            WritableSlice<KtAnnotationEntry, AnnotationDescriptor> ANNOTATION = BindingContext.ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(ANNOTATION, "ANNOTATION");
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) GeneratorKt.getOrFail(this, ANNOTATION, ktAnnotationEntry);
            Intrinsics.checkNotNull(annotationDescriptor);
            IrConstructorCall irConstructorCallGenerateAnnotationConstructorCall$default = ConstantValueGenerator.generateAnnotationConstructorCall$default(constantValueGenerator, annotationDescriptor, null, 2, null);
            if (irConstructorCallGenerateAnnotationConstructorCall$default != null) {
                irFileImplCreateEmptyIrFile.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) irFileImplCreateEmptyIrFile.getAnnotations(), irConstructorCallGenerateAnnotationConstructorCall$default));
            }
        }
        Iterator<KtDeclaration> it = ktFile.getDeclarations().iterator();
        while (it.hasNext()) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(irFileImplCreateEmptyIrFile.getDeclarations(), irDeclarationGenerator.generateMemberDeclaration(it.next()));
        }
        IrFileImpl irFileImpl = irFileImplCreateEmptyIrFile;
        PatchDeclarationParentsKt.patchDeclarationParents$default(irFileImpl, null, 1, null);
        Map<DeclarationDescriptor, IrSymbol> map = this.expectDescriptorToSymbol;
        if (map != null) {
            ExpectDependencyGeneratorKt.referenceExpectsForUsedActuals(map, getContext().getSymbolTable(), irFileImpl);
        }
        IrFileImpl irFileImpl2 = irFileImplCreateEmptyIrFile;
        new IrSyntheticDeclarationGenerator(getContext()).generateSyntheticDeclarations(irFileImpl2);
        if (getContext().getConfiguration().getSkipBodies()) {
            createStubIrForErrorClass();
        }
        InsertImplicitCastsKt.insertImplicitCasts(irFileImpl2, getContext());
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().clear();
        IrVisitorsKt.acceptVoid(irFileImpl, new AnnotationGenerator(getContext()));
        PatchDeclarationParentsKt.patchDeclarationParents$default(irFileImpl, null, 1, null);
        return irFileImplCreateEmptyIrFile;
    }

    private final IrFileImpl createEmptyIrFile(KtFile ktFile, IrModuleFragment module) {
        PsiIrFileEntry psiIrFileEntry = new PsiIrFileEntry(ktFile);
        PackageFragmentDescriptor packageFragmentDescriptorFindPackageFragmentForFile = PackageDescriptorUtilKt.findPackageFragmentForFile(getContext().getModuleDescriptor(), ktFile);
        Intrinsics.checkNotNull(packageFragmentDescriptorFindPackageFragmentForFile);
        IrFileImpl irFileImpl = new IrFileImpl(psiIrFileEntry, packageFragmentDescriptorFindPackageFragmentForFile, module);
        irFileImpl.setMetadata(new DescriptorMetadataSource.File(CodegenUtil.getMemberDescriptorsToGenerate(ktFile, getContext().getBindingContext())));
        return irFileImpl;
    }

    private final void createStubIrForErrorClass() {
        IrFileEntry irFileEntry = new IrFileEntry() { // from class: org.jetbrains.kotlin.psi2ir.generators.ModuleGenerator$createStubIrForErrorClass$fakeFileEntry$1
            private final String name = "<error-class>";
            private final int maxOffset = -1;

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public boolean getSupportsDebugInfo() {
                return IrFileEntry.DefaultImpls.getSupportsDebugInfo(this);
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public String getName() {
                return this.name;
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public int getMaxOffset() {
                return this.maxOffset;
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public SourceRangeInfo getSourceRangeInfo(int beginOffset, int endOffset) {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public int getLineNumber(int offset) {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }

            @Override // org.jetbrains.kotlin.ir.IrFileEntry
            public int getColumnNumber(int offset) {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }
        };
        IrFileImpl irFileImpl = new IrFileImpl(irFileEntry, new EmptyPackageFragmentDescriptor(getContext().getModuleDescriptor(), new FqName(irFileEntry.getName())));
        SyntheticDeclarationsGenerator syntheticDeclarationsGenerator = new SyntheticDeclarationsGenerator(getContext());
        syntheticDeclarationsGenerator.visitClassDescriptor2((ClassDescriptor) ErrorUtils.INSTANCE.getErrorClass(), (IrDeclarationContainer) irFileImpl);
        ClassConstructorDescriptor unsubstitutedPrimaryConstructor = ErrorUtils.INSTANCE.getErrorClass().mo6347getUnsubstitutedPrimaryConstructor();
        Intrinsics.checkNotNull(unsubstitutedPrimaryConstructor);
        syntheticDeclarationsGenerator.visitConstructorDescriptor2((ConstructorDescriptor) unsubstitutedPrimaryConstructor, (IrDeclarationContainer) getContext().getSymbolTable().getDescriptorExtension().referenceClass((ClassDescriptor) ErrorUtils.INSTANCE.getErrorClass()).getOwner());
    }
}
