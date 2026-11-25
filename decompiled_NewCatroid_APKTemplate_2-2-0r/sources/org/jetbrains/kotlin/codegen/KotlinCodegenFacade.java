package org.jetbrains.kotlin.codegen;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: loaded from: classes3.dex */
public class KotlinCodegenFacade {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 3) {
            objArr[0] = "packageFqName";
        } else if (i != 4) {
            objArr[0] = "state";
        } else {
            objArr[0] = "files";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/KotlinCodegenFacade";
        if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "generatePackage";
        } else {
            objArr[2] = "compileCorrectFiles";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static void compileCorrectFiles(Collection<KtFile> collection, GenerationState generationState, CodegenFactory codegenFactory) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        generationState.beforeCompile();
        generationState.oldBEInitTrace(collection);
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        CodegenFactory.BackendInput backendInputConvertToIr = codegenFactory.convertToIr(CodegenFactory.IrConversionInput.INSTANCE.fromGenerationStateAndFiles(generationState, collection));
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        codegenFactory.generateModule(generationState, backendInputConvertToIr);
        CodegenFactory.INSTANCE.doCheckCancelled(generationState);
        generationState.getFactory().done();
    }

    public static void compileCorrectFiles(GenerationState generationState) {
        if (generationState == null) {
            $$$reportNull$$$0(1);
        }
        CodegenFactory codegenFactory = generationState.getCodegenFactory();
        List<KtFile> files = generationState.getFiles();
        if (codegenFactory == null) {
            codegenFactory = DefaultCodegenFactory.INSTANCE;
        }
        compileCorrectFiles(files, generationState, codegenFactory);
    }

    public static void generatePackage(GenerationState generationState, FqName fqName, Collection<KtFile> collection) {
        if (generationState == null) {
            $$$reportNull$$$0(2);
        }
        if (fqName == null) {
            $$$reportNull$$$0(3);
        }
        if (collection == null) {
            $$$reportNull$$$0(4);
        }
        DefaultCodegenFactory.INSTANCE.generatePackage(generationState, fqName, collection);
    }

    private KotlinCodegenFacade() {
    }
}
