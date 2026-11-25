package org.jetbrains.kotlin.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.PackageContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.fileClasses.JvmFileClassInfo;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.lazy.descriptors.PackageDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class PackageCodegenImpl implements PackageCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PackageCodegenImpl.class);
    private final Collection<KtFile> files;
    private final PackageFragmentDescriptor packageFragment;
    private final GenerationState state;

    /* JADX WARN: Code duplicated, block: B:18:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "files";
        } else if (i == 2) {
            objArr[0] = "packageFqName";
        } else if (i == 3) {
            objArr[0] = "file";
        } else if (i == 4) {
            objArr[0] = "context";
        } else if (i == 6) {
            objArr[0] = "declaration";
        } else if (i == 8) {
            objArr[0] = "file";
        } else if (i != 9) {
            objArr[0] = "state";
        } else {
            objArr[0] = "expectedPackageFqName";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/PackageCodegenImpl";
        switch (i) {
            case 3:
            case 4:
            case 5:
                objArr[2] = "generateClassesAndObjectsInFile";
                break;
            case 6:
            case 7:
                objArr[2] = "addDescriptorToOptionalAnnotationsIfNeeded";
                break;
            case 8:
                objArr[2] = "generateFile";
                break;
            case 9:
                objArr[2] = "getOnlyPackageFragment";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ Pair lambda$generate$0(Integer num) {
        return null;
    }

    public PackageCodegenImpl(GenerationState generationState, Collection<KtFile> collection, FqName fqName) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        if (collection == null) {
            $$$reportNull$$$0(1);
        }
        if (fqName == null) {
            $$$reportNull$$$0(2);
        }
        this.state = generationState;
        this.files = collection;
        this.packageFragment = getOnlyPackageFragment(fqName);
    }

    @Override // org.jetbrains.kotlin.codegen.PackageCodegen
    public void generate() throws Throwable {
        for (KtFile ktFile : this.files) {
            ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            try {
                generateFile(ktFile);
                this.state.afterIndependentPart();
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Throwable th) {
                VirtualFile virtualFile = ktFile.getVirtualFile();
                CodegenUtil.reportBackendException(th, "file facade code generation", virtualFile == null ? null : virtualFile.getUrl(), null, new Function1() { // from class: org.jetbrains.kotlin.codegen.PackageCodegenImpl$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return PackageCodegenImpl.lambda$generate$0((Integer) obj);
                    }
                });
            }
        }
    }

    public static void generateClassesAndObjectsInFile(KtFile ktFile, CodegenContext<?> codegenContext, GenerationState generationState) {
        if (ktFile == null) {
            $$$reportNull$$$0(3);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(4);
        }
        if (generationState == null) {
            $$$reportNull$$$0(5);
        }
        ArrayList arrayList = new ArrayList();
        for (KtDeclaration ktDeclaration : ktFile.getDeclarations()) {
            if (ktDeclaration instanceof KtClassOrObject) {
                if (PsiUtilsKt.hasExpectModifier(ktDeclaration)) {
                    addDescriptorToOptionalAnnotationsIfNeeded((KtClassOrObject) ktDeclaration, generationState);
                } else {
                    KtClassOrObject ktClassOrObject = (KtClassOrObject) ktDeclaration;
                    if (generationState.getGenerateDeclaredClassFilter().shouldGenerateClass(ktClassOrObject)) {
                        arrayList.add(ktClassOrObject);
                    }
                }
            } else if (ktDeclaration instanceof KtScript) {
                KtScript ktScript = (KtScript) ktDeclaration;
                if (generationState.getGenerateDeclaredClassFilter().shouldGenerateScript(ktScript)) {
                    ScriptCodegen.createScriptCodegen(ktScript, generationState, codegenContext).generate();
                }
            }
        }
        Iterator<KtClassOrObject> it = CodegenUtilKt.sortTopLevelClassesAndPrepareContextForSealedClasses(arrayList, codegenContext, generationState).iterator();
        while (it.hasNext()) {
            MemberCodegen.genClassOrObject(codegenContext, it.next(), generationState, null);
        }
    }

    private static void addDescriptorToOptionalAnnotationsIfNeeded(KtClassOrObject ktClassOrObject, GenerationState generationState) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(6);
        }
        if (generationState == null) {
            $$$reportNull$$$0(7);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) generationState.getBindingContext().get(BindingContext.CLASS, ktClassOrObject);
        if (classDescriptor == null || !OptionalAnnotationUtil.shouldGenerateExpectClass(classDescriptor)) {
            return;
        }
        generationState.getFactory().getPackagePartRegistry().getOptionalAnnotations().add(classDescriptor);
        KtClassBody body = ktClassOrObject.getBody();
        if (body != null) {
            for (KtDeclaration ktDeclaration : body.getDeclarations()) {
                if (ktDeclaration instanceof KtClassOrObject) {
                    addDescriptorToOptionalAnnotationsIfNeeded((KtClassOrObject) ktDeclaration, generationState);
                }
            }
        }
    }

    private void generateFile(KtFile ktFile) {
        if (ktFile == null) {
            $$$reportNull$$$0(8);
        }
        JvmFileClassInfo fileClassInfoNoResolve = JvmFileClassUtil.getFileClassInfoNoResolve(ktFile);
        if (fileClassInfoNoResolve.getWithJvmMultifileClass()) {
            return;
        }
        Type typeAsmTypeByFqNameWithoutInnerClasses = AsmUtil.asmTypeByFqNameWithoutInnerClasses(fileClassInfoNoResolve.getFileClassFqName());
        PackageContext packageContextIntoPackagePart = this.state.getRootContext().intoPackagePart(this.packageFragment, typeAsmTypeByFqNameWithoutInnerClasses, ktFile);
        if (ktFile instanceof KtCodeFragment) {
            if (this.state.getClassBuilderMode().generateBodies) {
                KtCodeFragment ktCodeFragment = (KtCodeFragment) ktFile;
                if (this.state.getGenerateDeclaredClassFilter().shouldGenerateCodeFragment(ktCodeFragment)) {
                    CodeFragmentCodegen.createCodegen(ktCodeFragment, this.state, packageContextIntoPackagePart).generate();
                }
            }
        } else {
            generateClassesAndObjectsInFile(ktFile, packageContextIntoPackagePart, this.state);
        }
        if (this.state.getGenerateDeclaredClassFilter().shouldGeneratePackagePart(ktFile) && !CodegenUtil.getMemberDeclarationsToGenerate(ktFile).isEmpty()) {
            this.state.getFactory().getPackagePartRegistry().addPart(this.packageFragment.getFqName(), typeAsmTypeByFqNameWithoutInnerClasses.getInternalName(), null);
            new PackagePartCodegen(this.state.getFactory().newVisitor(JvmDeclarationOriginKt.PackagePart(ktFile, this.packageFragment), typeAsmTypeByFqNameWithoutInnerClasses, ktFile), ktFile, typeAsmTypeByFqNameWithoutInnerClasses, packageContextIntoPackagePart, this.state).generate();
        }
    }

    private PackageFragmentDescriptor getOnlyPackageFragment(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(9);
        }
        SmartList smartList = new SmartList();
        for (KtFile ktFile : this.files) {
            PackageFragmentDescriptor packageFragmentDescriptorFindPackageFragmentForFile = PackageDescriptorUtilKt.findPackageFragmentForFile(this.state.getModule(), ktFile);
            if (packageFragmentDescriptorFindPackageFragmentForFile == null) {
                LOG.error((Throwable) new KotlinExceptionWithAttachments("package fragment is not found for module:" + this.state.getModule() + " file:" + ktFile).withPsiAttachment("file.kt", ktFile));
            } else if (!fqName.equals(packageFragmentDescriptorFindPackageFragmentForFile.getFqName())) {
                LOG.error("expected package fq name: " + fqName + ", actual: " + packageFragmentDescriptorFindPackageFragmentForFile.getFqName());
            }
            if (!smartList.contains(packageFragmentDescriptorFindPackageFragmentForFile)) {
                smartList.add(packageFragmentDescriptorFindPackageFragmentForFile);
            }
        }
        if (smartList.size() > 1) {
            throw new IllegalStateException("More than one package fragment, files: " + this.files + " | fragments: " + smartList);
        }
        if (smartList.isEmpty()) {
            return null;
        }
        return (PackageFragmentDescriptor) smartList.get(0);
    }

    @Override // org.jetbrains.kotlin.codegen.PackageCodegen
    public PackageFragmentDescriptor getPackageFragment() {
        return this.packageFragment;
    }
}
