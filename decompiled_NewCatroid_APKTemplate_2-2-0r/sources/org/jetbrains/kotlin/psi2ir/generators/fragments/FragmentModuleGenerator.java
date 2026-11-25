package org.jetbrains.kotlin.psi2ir.generators.fragments;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.psi.KtBlockCodeFragment;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi2ir.generators.DeclarationGenerator;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.psi2ir.generators.ModuleGenerator;
import org.jetbrains.kotlin.resolve.lazy.descriptors.PackageDescriptorUtilKt;

/* JADX INFO: compiled from: FragmentModuleGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J/\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00100\u0012H\u0002¢\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u000e2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u0016H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentModuleGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/ModuleGenerator;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "fragmentInfo", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;)V", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "createEmptyIrFile", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrFileImpl;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "irModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "generateInContextWithoutFragmentInfo", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "generateModuleFragment", "ktFiles", "", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FragmentModuleGenerator extends ModuleGenerator {
    private final GeneratorContext context;
    private final EvaluatorFragmentInfo fragmentInfo;

    @Override // org.jetbrains.kotlin.psi2ir.generators.ModuleGenerator, org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FragmentModuleGenerator(GeneratorContext context, EvaluatorFragmentInfo fragmentInfo) {
        super(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fragmentInfo, "fragmentInfo");
        this.context = context;
        this.fragmentInfo = fragmentInfo;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.ModuleGenerator
    public IrModuleFragment generateModuleFragment(Collection<? extends KtFile> ktFiles) {
        IrFileImpl irFileImplCreateEmptyIrFile;
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        Collection<? extends KtFile> collection = ktFiles;
        Iterator<T> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (((KtFile) it.next()) instanceof KtBlockCodeFragment) {
                if (z) {
                    break;
                }
                z = true;
            }
        }
        final IrModuleFragmentImpl irModuleFragmentImpl = new IrModuleFragmentImpl(getContext().getModuleDescriptor(), getContext().getIrBuiltIns(), null, 4, null);
        FragmentDeclarationGenerator fragmentDeclarationGenerator = new FragmentDeclarationGenerator(getContext(), this.fragmentInfo);
        for (final KtFile ktFile : collection) {
            List<IrFile> files = irModuleFragmentImpl.getFiles();
            if (ktFile instanceof KtBlockCodeFragment) {
                irFileImplCreateEmptyIrFile = createEmptyIrFile(ktFile, irModuleFragmentImpl);
                irFileImplCreateEmptyIrFile.getDeclarations().add(fragmentDeclarationGenerator.generateClassForCodeFragment((KtBlockCodeFragment) ktFile));
                PatchDeclarationParentsKt.patchDeclarationParents$default(irFileImplCreateEmptyIrFile, null, 1, null);
            } else {
                irFileImplCreateEmptyIrFile = (IrFileImpl) generateInContextWithoutFragmentInfo(ktFile, new Function1<GeneratorContext, IrFileImpl>() { // from class: org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentModuleGenerator$generateModuleFragment$3$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final IrFileImpl invoke(GeneratorContext it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return this.this$0.generateSingleFile(new DeclarationGenerator(it2), ktFile, irModuleFragmentImpl);
                    }
                });
            }
            files.add(irFileImplCreateEmptyIrFile);
        }
        return irModuleFragmentImpl;
    }

    private final <T> T generateInContextWithoutFragmentInfo(KtFile ktFile, Function1<? super GeneratorContext, ? extends T> block) {
        SymbolTable symbolTable = getContext().getSymbolTable();
        Intrinsics.checkNotNull(symbolTable, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentCompilerSymbolTableDecorator");
        FragmentCompilerSymbolTableDecorator fragmentCompilerSymbolTableDecorator = (FragmentCompilerSymbolTableDecorator) symbolTable;
        EvaluatorFragmentInfo fragmentInfo = fragmentCompilerSymbolTableDecorator.getFragmentInfo();
        fragmentCompilerSymbolTableDecorator.setFragmentInfo(null);
        GeneratorContext generatorContextCreateFileScopeContext = getContext().createFileScopeContext(ktFile);
        generatorContextCreateFileScopeContext.setFragmentContext$ir_psi2ir(null);
        T tInvoke = block.invoke(generatorContextCreateFileScopeContext);
        fragmentCompilerSymbolTableDecorator.setFragmentInfo(fragmentInfo);
        getContext().getAdditionalDescriptorStorage().addAllFrom(generatorContextCreateFileScopeContext.getAdditionalDescriptorStorage());
        return tInvoke;
    }

    private final IrFileImpl createEmptyIrFile(KtFile ktFile, IrModuleFragment irModule) {
        PsiIrFileEntry psiIrFileEntry = new PsiIrFileEntry(ktFile);
        PackageFragmentDescriptor packageFragmentDescriptorFindPackageFragmentForFile = PackageDescriptorUtilKt.findPackageFragmentForFile(getContext().getModuleDescriptor(), ktFile);
        Intrinsics.checkNotNull(packageFragmentDescriptorFindPackageFragmentForFile);
        return new IrFileImpl(psiIrFileEntry, packageFragmentDescriptorFindPackageFragmentForFile, irModule);
    }
}
