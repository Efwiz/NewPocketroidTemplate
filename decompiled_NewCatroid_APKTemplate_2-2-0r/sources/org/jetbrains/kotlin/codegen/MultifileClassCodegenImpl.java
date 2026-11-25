package org.jetbrains.kotlin.codegen;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.context.MultifileClassPartContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackageFragmentProvider;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.MemberComparator;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.lazy.descriptors.PackageDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: MultifileClassCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 A2\u00020\u0001:\u0002ABB#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001e\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00112\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J \u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u0016H\u0002J\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060)2\n\u0010*\u001a\u0006\u0012\u0002\b\u00030+H\u0002J\b\u0010,\u001a\u00020\u0013H\u0002J\b\u0010-\u001a\u00020\u0013H\u0016J\u001e\u0010.\u001a\u00020\u00132\u0006\u0010/\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00180+H\u0016J\b\u00102\u001a\u00020\u0013H\u0002J \u00103\u001a\u00020\u00132\u0006\u00104\u001a\u0002052\u0006\u0010\f\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u0016H\u0002J\b\u00106\u001a\u00020\u0013H\u0002J\b\u00107\u001a\u00020\u0013H\u0002J\u0010\u00108\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u0006H\u0002J\u001b\u00109\u001a\r\u0012\t\u0012\u00070\u001d¢\u0006\u0002\b\u001e0\u001a2\u0006\u0010\f\u001a\u00020\u0018H\u0002J\u0018\u0010:\u001a\n ;*\u0004\u0018\u00010\u001b0\u001b2\u0006\u0010<\u001a\u00020\u001bH\u0002J\u0010\u0010=\u001a\u00020 2\u0006\u0010>\u001a\u00020\u0011H\u0002J\b\u0010?\u001a\u00020\u0013H\u0002J\f\u0010@\u001a\u00020 *\u00020\u0006H\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0018\u00010\rR\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u000f\u001a*\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0010j\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0012`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u001c\u001a\r\u0012\t\u0012\u00070\u001d¢\u0006\u0002\b\u001e0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010!\u001a\u001e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0\u0010j\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/codegen/MultifileClassCodegenImpl;", "Lorg/jetbrains/kotlin/codegen/MultifileClassCodegen;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Ljava/util/Collection;Lorg/jetbrains/kotlin/name/FqName;)V", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilderOnDemand;", "compiledPackageFragment", "Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider$IncrementalMultifileClassPackageFragment;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider;", "delegateGenerationTasks", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "Lkotlin/Function0;", "", "Lkotlin/collections/HashMap;", "facadeClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "partInternalNamesSorted", "", "", "previouslyCompiledCallables", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedCallableMemberDescriptor;", "Lkotlin/internal/NoInfer;", "shouldGeneratePartHierarchy", "", "superClassForInheritedPart", "addDelegateGenerationTaskIfNeeded", "callable", "task", "addDelegateGenerationTasksForDeclarationsInFile", "file", "partType", "createCodegenForDelegatesInMultifileFacade", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "facadeContext", "Lorg/jetbrains/kotlin/codegen/context/FieldOwnerContext;", ES6Iterator.DONE_PROPERTY, CompilerOptions.GENERATE, "generateClassOrObject", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "packagePartContext", "generateCodeForSourceFiles", "generateDelegateToCompiledMember", "member", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "generateDelegatesToPreviouslyCompiledParts", "generateMultifileFacadeClass", "generatePart", "getDeserializedCallables", "getSuperClassForPart", JvmProtoBufUtil.PLATFORM_TYPE_ID, "partInternalName", "shouldGenerateInFacade", "descriptor", "writeKotlinMultifileFacadeAnnotationIfNeeded", "isJvmSynthetic", "Companion", "DelegateToCompiledMemberGenerationStrategy", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MultifileClassCodegenImpl implements MultifileClassCodegen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String J_L_OBJECT = AsmTypes.OBJECT_TYPE.getInternalName();
    private final ClassBuilderOnDemand classBuilder;
    private final IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment compiledPackageFragment;
    private final HashMap<MemberDescriptor, Function0<Unit>> delegateGenerationTasks;
    private final Type facadeClassType;
    private final FqName facadeFqName;
    private final Collection<KtFile> files;
    private final PackageFragmentDescriptor packageFragment;
    private final List<String> partInternalNamesSorted;
    private final List<DeserializedCallableMemberDescriptor> previouslyCompiledCallables;
    private final boolean shouldGeneratePartHierarchy;
    private final GenerationState state;
    private final HashMap<String, String> superClassForInheritedPart;

    /* JADX WARN: Multi-variable type inference failed */
    public MultifileClassCodegenImpl(GenerationState state, Collection<? extends KtFile> files, FqName facadeFqName) {
        List<DeserializedCallableMemberDescriptor> deserializedCallables;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        this.state = state;
        this.files = files;
        this.facadeFqName = facadeFqName;
        Type typeAsmTypeByFqNameWithoutInnerClasses = AsmUtil.asmTypeByFqNameWithoutInnerClasses(facadeFqName);
        Intrinsics.checkNotNullExpressionValue(typeAsmTypeByFqNameWithoutInnerClasses, "asmTypeByFqNameWithoutInnerClasses(...)");
        this.facadeClassType = typeAsmTypeByFqNameWithoutInnerClasses;
        Companion companion = INSTANCE;
        this.packageFragment = companion.getOnlyPackageFragment(files, state.getModule());
        IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment compiledPackageFragment = companion.getCompiledPackageFragment(facadeFqName, state);
        this.compiledPackageFragment = compiledPackageFragment;
        if (compiledPackageFragment == null) {
            deserializedCallables = CollectionsKt.emptyList();
        } else {
            deserializedCallables = getDeserializedCallables(compiledPackageFragment);
        }
        this.previouslyCompiledCallables = deserializedCallables;
        this.shouldGeneratePartHierarchy = ((Boolean) state.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getInheritMultifileParts())).booleanValue();
        HashSet hashSet = new HashSet();
        for (KtFile ktFile : this.files) {
            if (INSTANCE.hasDeclarationsForPartClass(ktFile)) {
                hashSet.add(JvmFileClassUtil.getFileClassInternalName(ktFile));
            }
        }
        IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment incrementalMultifileClassPackageFragment = this.compiledPackageFragment;
        if (incrementalMultifileClassPackageFragment != null) {
            hashSet.addAll(incrementalMultifileClassPackageFragment.getPartsInternalNames());
        }
        this.partInternalNamesSorted = CollectionsKt.sorted(hashSet);
        HashMap<String, String> map = new HashMap<>();
        int size = this.partInternalNamesSorted.size();
        for (int i = 1; i < size; i++) {
            map.put(this.partInternalNamesSorted.get(i), this.partInternalNamesSorted.get(i - 1));
        }
        this.superClassForInheritedPart = map;
        this.delegateGenerationTasks = new HashMap<>();
        this.classBuilder = new ClassBuilderOnDemand(new Function0<ClassBuilder>() { // from class: org.jetbrains.kotlin.codegen.MultifileClassCodegenImpl$classBuilder$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassBuilder invoke() {
                KtFile ktFile2;
                int i2;
                IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment incrementalMultifileClassPackageFragment2 = this.this$0.packageFragment;
                if (incrementalMultifileClassPackageFragment2 == null) {
                    IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment incrementalMultifileClassPackageFragment3 = this.this$0.compiledPackageFragment;
                    if (incrementalMultifileClassPackageFragment3 != null) {
                        incrementalMultifileClassPackageFragment2 = incrementalMultifileClassPackageFragment3;
                    } else {
                        throw new AssertionError("No package fragment for multifile facade " + this.this$0.facadeFqName + "; files: " + this.this$0.files);
                    }
                }
                if (this.this$0.previouslyCompiledCallables.isEmpty()) {
                    Iterator it = this.this$0.files.iterator();
                    boolean z = false;
                    Object obj = null;
                    while (true) {
                        if (!it.hasNext()) {
                            if (!z) {
                                break;
                            }
                            break;
                        }
                        Object next = it.next();
                        if (MultifileClassCodegenImpl.INSTANCE.hasDeclarationsForPartClass((KtFile) next)) {
                            if (!z) {
                                z = true;
                                obj = next;
                            }
                        }
                        obj = null;
                        break;
                    }
                    ktFile2 = (KtFile) obj;
                } else {
                    ktFile2 = null;
                }
                String str = this.this$0.shouldGeneratePartHierarchy ? (String) CollectionsKt.last(this.this$0.partInternalNamesSorted) : MultifileClassCodegenImpl.J_L_OBJECT;
                ClassBuilder classBuilderNewVisitor = this.this$0.state.getFactory().newVisitor(JvmDeclarationOriginKt.MultifileClass((KtFile) CollectionsKt.firstOrNull(this.this$0.files), incrementalMultifileClassPackageFragment2), this.this$0.facadeClassType, this.this$0.files);
                Intrinsics.checkNotNullExpressionValue(classBuilderNewVisitor, "newVisitor(...)");
                MultifileClassCodegenImpl multifileClassCodegenImpl = this.this$0;
                Collection collection = multifileClassCodegenImpl.files;
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : collection) {
                    if (!multifileClassCodegenImpl.isJvmSynthetic((KtFile) obj2)) {
                        arrayList.add(obj2);
                    }
                }
                ArrayList<KtFile> arrayList2 = arrayList;
                if (arrayList2.isEmpty()) {
                    i2 = 4145;
                } else {
                    if (arrayList2.size() < multifileClassCodegenImpl.files.size()) {
                        for (KtFile ktFile3 : arrayList2) {
                            DiagnosticSink diagnostics = multifileClassCodegenImpl.state.getDiagnostics();
                            DiagnosticFactory0<PsiElement> diagnosticFactory0 = ErrorsJvm.NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC;
                            KtPackageDirective packageDirective = ktFile3.getPackageDirective();
                            if (packageDirective != null) {
                                ktFile3 = packageDirective;
                            }
                            diagnostics.report(diagnosticFactory0.on(ktFile3));
                        }
                    }
                    i2 = 49;
                }
                classBuilderNewVisitor.defineClass(ktFile2, multifileClassCodegenImpl.state.getConfig().getClassFileVersion(), i2, multifileClassCodegenImpl.facadeClassType.getInternalName(), null, str, new String[0]);
                if (ktFile2 != null) {
                    classBuilderNewVisitor.visitSource(ktFile2.get$name(), null);
                }
                if (multifileClassCodegenImpl.shouldGeneratePartHierarchy) {
                    MethodVisitor methodVisitorNewMethod = classBuilderNewVisitor.newMethod(JvmDeclarationOriginKt.OtherOrigin(incrementalMultifileClassPackageFragment2), 2, "<init>", "()V", null, null);
                    methodVisitorNewMethod.visitCode();
                    methodVisitorNewMethod.visitVarInsn(25, 0);
                    methodVisitorNewMethod.visitMethodInsn(183, str, "<init>", "()V", false);
                    methodVisitorNewMethod.visitInsn(177);
                    methodVisitorNewMethod.visitMaxs(1, 1);
                    methodVisitorNewMethod.visitEnd();
                }
                return classBuilderNewVisitor;
            }
        });
    }

    private final List<DeserializedCallableMemberDescriptor> getDeserializedCallables(PackageFragmentDescriptor compiledPackageFragment) {
        Collection<DeclarationDescriptor> contributedDescriptors = compiledPackageFragment.getScope().getContributedDescriptors(DescriptorKindFilter.CALLABLES, MemberScope.INSTANCE.getALL_NAME_FILTER());
        ArrayList arrayList = new ArrayList();
        for (Object obj : contributedDescriptors) {
            if (obj instanceof DeserializedCallableMemberDescriptor) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final String getSuperClassForPart(String partInternalName) {
        if (this.shouldGeneratePartHierarchy) {
            String str = this.superClassForInheritedPart.get(partInternalName);
            return str == null ? J_L_OBJECT : str;
        }
        return J_L_OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isJvmSynthetic(KtFile ktFile) {
        ClassDescriptor annotationClass;
        List<KtAnnotationEntry> annotationEntries = ktFile.getAnnotationEntries();
        if ((annotationEntries instanceof Collection) && annotationEntries.isEmpty()) {
            return false;
        }
        Iterator<T> it = annotationEntries.iterator();
        while (it.hasNext()) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) this.state.getBindingContext().get(BindingContext.ANNOTATION, (KtAnnotationEntry) it.next());
            if (Intrinsics.areEqual((annotationDescriptor == null || (annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) == null) ? null : DescriptorUtils.getFqNameSafe(annotationClass), JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.codegen.MultifileClassCodegen
    public void generate() {
        this.delegateGenerationTasks.isEmpty();
        generateCodeForSourceFiles();
        generateDelegatesToPreviouslyCompiledParts();
        if (!this.partInternalNamesSorted.isEmpty()) {
            generateMultifileFacadeClass();
        }
        done();
    }

    private final void generateCodeForSourceFiles() {
        for (KtFile ktFile : this.files) {
            ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            try {
                generatePart(ktFile);
                this.state.afterIndependentPart();
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Throwable th) {
                VirtualFile virtualFile = ktFile.getVirtualFile();
                CodegenUtil.reportBackendException$default(th, "multi-file class part code generation", virtualFile != null ? virtualFile.getUrl() : null, null, null, 24, null);
                throw null;
            }
        }
    }

    private final void generateMultifileFacadeClass() {
        Set<MemberDescriptor> setKeySet = this.delegateGenerationTasks.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        MemberComparator INSTANCE2 = MemberComparator.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
        Iterator it = CollectionsKt.sortedWith(setKeySet, INSTANCE2).iterator();
        while (it.hasNext()) {
            Function0<Unit> function0 = this.delegateGenerationTasks.get((MemberDescriptor) it.next());
            Intrinsics.checkNotNull(function0);
            function0.invoke();
        }
        writeKotlinMultifileFacadeAnnotationIfNeeded();
    }

    @Override // org.jetbrains.kotlin.codegen.MultifileClassCodegen
    public void generateClassOrObject(KtClassOrObject classOrObject, FieldOwnerContext<PackageFragmentDescriptor> packagePartContext) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(packagePartContext, "packagePartContext");
        MemberCodegen.genClassOrObject(packagePartContext, classOrObject, this.state, null);
    }

    private final void generatePart(KtFile file) {
        PackageFragmentDescriptor packageFragmentDescriptor = this.packageFragment;
        if (packageFragmentDescriptor == null) {
            throw new AssertionError("File part " + file + " of " + this.facadeFqName + ": no package fragment");
        }
        Type objectType = Type.getObjectType(JvmFileClassUtil.getFileClassInternalName(file));
        MultifileClassPartContext multifileClassPartContextIntoMultifileClassPart = this.state.getRootContext().intoMultifileClassPart(packageFragmentDescriptor, this.facadeClassType, objectType, file);
        Intrinsics.checkNotNullExpressionValue(multifileClassPartContextIntoMultifileClassPart, "intoMultifileClassPart(...)");
        PackageCodegenImpl.generateClassesAndObjectsInFile(file, multifileClassPartContextIntoMultifileClassPart, this.state);
        if (this.state.getGenerateDeclaredClassFilter().shouldGeneratePackagePart(file) && INSTANCE.hasDeclarationsForPartClass(file)) {
            PackagePartRegistry packagePartRegistry = this.state.getFactory().getPackagePartRegistry();
            FqName fqName = packageFragmentDescriptor.getFqName();
            String internalName = objectType.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            packagePartRegistry.addPart(fqName, internalName, this.facadeClassType.getInternalName());
            ClassBuilder classBuilderNewVisitor = this.state.getFactory().newVisitor(JvmDeclarationOriginKt.MultifileClassPart(file, packageFragmentDescriptor), objectType, file);
            Intrinsics.checkNotNullExpressionValue(classBuilderNewVisitor, "newVisitor(...)");
            String internalName2 = objectType.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
            String superClassForPart = getSuperClassForPart(internalName2);
            Intrinsics.checkNotNullExpressionValue(superClassForPart, "getSuperClassForPart(...)");
            new MultifileClassPartCodegen(classBuilderNewVisitor, file, packageFragmentDescriptor, superClassForPart, this.shouldGeneratePartHierarchy, multifileClassPartContextIntoMultifileClassPart, this.state).generate();
            Intrinsics.checkNotNull(objectType);
            addDelegateGenerationTasksForDeclarationsInFile(file, packageFragmentDescriptor, objectType);
        }
    }

    private final void addDelegateGenerationTasksForDeclarationsInFile(KtFile file, PackageFragmentDescriptor packageFragment, Type partType) {
        FieldOwnerContext<PackageFragmentDescriptor> fieldOwnerContextIntoMultifileClass = this.state.getRootContext().intoMultifileClass(packageFragment, this.facadeClassType, partType);
        Intrinsics.checkNotNullExpressionValue(fieldOwnerContextIntoMultifileClass, "intoMultifileClass(...)");
        final MemberCodegen<KtFile> memberCodegenCreateCodegenForDelegatesInMultifileFacade = createCodegenForDelegatesInMultifileFacade(fieldOwnerContextIntoMultifileClass);
        for (final KtDeclaration ktDeclaration : CodegenUtil.getMemberDeclarationsToGenerate(file)) {
            if (!(ktDeclaration instanceof KtTypeAlias) || this.state.getClassBuilderMode().generateMetadata) {
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.state.getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktDeclaration);
                if (!(declarationDescriptor instanceof MemberDescriptor)) {
                    throw new AssertionError("Expected callable member, was " + declarationDescriptor + " for " + ktDeclaration.getText());
                }
                addDelegateGenerationTaskIfNeeded((MemberDescriptor) declarationDescriptor, new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.MultifileClassCodegenImpl.addDelegateGenerationTasksForDeclarationsInFile.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        memberCodegenCreateCodegenForDelegatesInMultifileFacade.genSimpleMember(ktDeclaration);
                    }
                });
            }
        }
    }

    private final boolean shouldGenerateInFacade(MemberDescriptor descriptor) {
        if (DescriptorVisibilities.isPrivate(descriptor.getVisibility()) || DescriptorAsmUtil.getVisibilityAccessFlag(descriptor) == 2) {
            return false;
        }
        return (this.state.getClassBuilderMode().generateBodies && this.shouldGeneratePartHierarchy && (!(descriptor instanceof PropertyDescriptor) || !((PropertyDescriptor) descriptor).isConst())) ? false : true;
    }

    private final void addDelegateGenerationTaskIfNeeded(MemberDescriptor callable, Function0<Unit> task) {
        if (shouldGenerateInFacade(callable)) {
            this.delegateGenerationTasks.put(callable, task);
        }
    }

    private final void generateDelegatesToPreviouslyCompiledParts() {
        if (this.compiledPackageFragment == null) {
            return;
        }
        for (final DeserializedCallableMemberDescriptor deserializedCallableMemberDescriptor : this.previouslyCompiledCallables) {
            final Type typeAsmTypeByFqNameWithoutInnerClasses = AsmUtil.asmTypeByFqNameWithoutInnerClasses(JvmFileClassUtil.INSTANCE.getPartFqNameForDeserialized(deserializedCallableMemberDescriptor));
            Intrinsics.checkNotNullExpressionValue(typeAsmTypeByFqNameWithoutInnerClasses, "asmTypeByFqNameWithoutInnerClasses(...)");
            addDelegateGenerationTaskIfNeeded(deserializedCallableMemberDescriptor, new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.MultifileClassCodegenImpl.generateDelegatesToPreviouslyCompiledParts.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    MultifileClassCodegenImpl multifileClassCodegenImpl = MultifileClassCodegenImpl.this;
                    multifileClassCodegenImpl.generateDelegateToCompiledMember(deserializedCallableMemberDescriptor, multifileClassCodegenImpl.compiledPackageFragment, typeAsmTypeByFqNameWithoutInnerClasses);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void generateDelegateToCompiledMember(CallableMemberDescriptor member, PackageFragmentDescriptor compiledPackageFragment, Type partType) {
        FieldOwnerContext<PackageFragmentDescriptor> fieldOwnerContextIntoMultifileClass = this.state.getRootContext().intoMultifileClass(compiledPackageFragment, this.facadeClassType, partType);
        Intrinsics.checkNotNullExpressionValue(fieldOwnerContextIntoMultifileClass, "intoMultifileClass(...)");
        MemberCodegen<KtFile> memberCodegenCreateCodegenForDelegatesInMultifileFacade = createCodegenForDelegatesInMultifileFacade(fieldOwnerContextIntoMultifileClass);
        if (member instanceof DeserializedSimpleFunctionDescriptor) {
            FunctionCodegen functionCodegen = memberCodegenCreateCodegenForDelegatesInMultifileFacade.functionCodegen;
            JvmDeclarationOrigin jvmDeclarationOriginOtherOrigin = JvmDeclarationOriginKt.OtherOrigin(member);
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) member;
            functionCodegen.generateMethod(jvmDeclarationOriginOtherOrigin, functionDescriptor, DelegateToCompiledMemberGenerationStrategy.INSTANCE);
            memberCodegenCreateCodegenForDelegatesInMultifileFacade.functionCodegen.generateDefaultIfNeeded(fieldOwnerContextIntoMultifileClass.intoFunction(functionDescriptor), functionDescriptor, OwnerKind.PACKAGE, DefaultParameterValueLoader.DEFAULT, null);
            memberCodegenCreateCodegenForDelegatesInMultifileFacade.functionCodegen.generateOverloadsWithDefaultValues(null, functionDescriptor, functionDescriptor);
            return;
        }
        if (member instanceof DeserializedPropertyDescriptor) {
            memberCodegenCreateCodegenForDelegatesInMultifileFacade.propertyCodegen.generateInPackageFacade((DeserializedPropertyDescriptor) member);
            return;
        }
        throw new IllegalStateException("Unexpected member: " + member);
    }

    /* JADX INFO: compiled from: MultifileClassCodegen.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/MultifileClassCodegenImpl$DelegateToCompiledMemberGenerationStrategy;", "Lorg/jetbrains/kotlin/codegen/FunctionGenerationStrategy;", "()V", "generateBody", "", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "context", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "parentCodegen", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "skipNotNullAssertionsForParameters", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DelegateToCompiledMemberGenerationStrategy extends FunctionGenerationStrategy {
        public static final DelegateToCompiledMemberGenerationStrategy INSTANCE = new DelegateToCompiledMemberGenerationStrategy();

        private DelegateToCompiledMemberGenerationStrategy() {
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
        public boolean skipNotNullAssertionsForParameters() {
            throw new IllegalStateException("shouldn't be called");
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
        public void generateBody(MethodVisitor mv, FrameMap frameMap, JvmMethodSignature signature, MethodContext context, MemberCodegen<?> parentCodegen) {
            Intrinsics.checkNotNullParameter(mv, "mv");
            Intrinsics.checkNotNullParameter(frameMap, "frameMap");
            Intrinsics.checkNotNullParameter(signature, "signature");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(parentCodegen, "parentCodegen");
            throw new IllegalStateException("shouldn't be called");
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final void writeKotlinMultifileFacadeAnnotationIfNeeded() {
        FqName fqName;
        if (!this.state.getClassBuilderMode().generateMetadata) {
            this.classBuilder.ensureGenerated();
            return;
        }
        Collection<KtFile> collection = this.files;
        if (!(collection instanceof Collection) || !collection.isEmpty()) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                if (((KtFile) it.next()).isScript()) {
                    return;
                }
            }
        }
        boolean z = this.shouldGeneratePartHierarchy;
        PackageFragmentDescriptor packageFragmentDescriptor = this.packageFragment;
        if (packageFragmentDescriptor == null || (fqName = packageFragmentDescriptor.getFqName()) == null) {
            IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment incrementalMultifileClassPackageFragment = this.compiledPackageFragment;
            if (incrementalMultifileClassPackageFragment == null) {
                throw new IllegalStateException(("Either source package or compiled package should not be null: " + this.facadeClassType + " (" + this.files + ')').toString());
            }
            fqName = incrementalMultifileClassPackageFragment.getFqName();
        }
        FqName fqName2 = fqName;
        Collection<KtFile> collection2 = this.files;
        if (!(collection2 instanceof Collection) || !collection2.isEmpty()) {
            Iterator<T> it2 = collection2.iterator();
            while (it2.hasNext()) {
                if (!Intrinsics.areEqual(((KtFile) it2.next()).getPackageFqName(), fqName2)) {
                    throw new UnsupportedOperationException("Multi-file parts of a facade with JvmPackageName should all lie in the same Kotlin package:\n  " + CollectionsKt.joinToString$default(this.files, "\n  ", null, null, 0, null, new Function1<KtFile, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.MultifileClassCodegenImpl.writeKotlinMultifileFacadeAnnotationIfNeeded.3
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(KtFile file) {
                            Intrinsics.checkNotNullParameter(file, "file");
                            return file + ": package " + file.getPackageFqName();
                        }
                    }, 30, null));
                }
            }
        }
        INSTANCE.writeMetadata(this.classBuilder, this.state, z ? 1 : 0, this.partInternalNamesSorted, this.facadeClassType, fqName2);
    }

    private final MemberCodegen<KtFile> createCodegenForDelegatesInMultifileFacade(FieldOwnerContext<?> facadeContext) {
        return new MemberCodegen<KtFile>(facadeContext, this.state, this.classBuilder) { // from class: org.jetbrains.kotlin.codegen.MultifileClassCodegenImpl.createCodegenForDelegatesInMultifileFacade.1
            {
                ClassBuilderOnDemand classBuilderOnDemand = classBuilderOnDemand;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.codegen.MemberCodegen
            /* JADX INFO: renamed from: generateDeclaration, reason: merged with bridge method [inline-methods] */
            public Void mo6420generateDeclaration() {
                throw new UnsupportedOperationException();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.codegen.MemberCodegen
            /* JADX INFO: renamed from: generateBody, reason: merged with bridge method [inline-methods] */
            public Void mo6419generateBody() {
                throw new UnsupportedOperationException();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.codegen.MemberCodegen
            /* JADX INFO: renamed from: generateKotlinMetadataAnnotation, reason: merged with bridge method [inline-methods] */
            public Void mo6421generateKotlinMetadataAnnotation() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private final void done() {
        this.classBuilder.done(this.state.getConfig().getGenerateSmapCopyToAnnotation());
        if (this.classBuilder.isComputed()) {
            this.state.afterIndependentPart();
        }
    }

    /* JADX INFO: compiled from: MultifileClassCodegen.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\b\u0018\u00010\tR\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J<\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u00042\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\fJ\f\u0010 \u001a\u00020!*\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/codegen/MultifileClassCodegenImpl$Companion;", "", "()V", "FACADE_CLASS_ATTRIBUTES", "", "J_L_OBJECT", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getCompiledPackageFragment", "Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider$IncrementalMultifileClassPackageFragment;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/IncrementalPackageFragmentProvider;", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getOnlyPackageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "writeMetadata", "", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", OverlayablePolicy.NAME_flags, "partInternalNames", "", "facadeClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "kotlinPackageFqName", "hasDeclarationsForPartClass", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final PackageFragmentDescriptor getOnlyPackageFragment(Collection<? extends KtFile> files, ModuleDescriptor moduleDescriptor) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (KtFile ktFile : files) {
                PackageFragmentDescriptor packageFragmentDescriptorFindPackageFragmentForFile = PackageDescriptorUtilKt.findPackageFragmentForFile(moduleDescriptor, ktFile);
                if (packageFragmentDescriptorFindPackageFragmentForFile == null) {
                    throw new AssertionError("package fragment is null for " + ktFile + '\n' + ktFile.getText());
                }
                linkedHashSet.add(packageFragmentDescriptorFindPackageFragmentForFile);
            }
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            if (linkedHashSet2.size() > 1) {
                throw new IllegalStateException("More than one package fragment, files: " + files + " | fragments: " + linkedHashSet2);
            }
            return (PackageFragmentDescriptor) CollectionsKt.firstOrNull(linkedHashSet2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasDeclarationsForPartClass(KtFile ktFile) {
            return !CodegenUtil.getMemberDeclarationsToGenerate(ktFile).isEmpty();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IncrementalPackageFragmentProvider.IncrementalMultifileClassPackageFragment getCompiledPackageFragment(FqName facadeFqName, GenerationState state) {
            Object next;
            if (!state.getIsIncrementalCompilation()) {
                return null;
            }
            FqName fqNameParent = facadeFqName.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            Iterator<T> it = state.getModule().getPackage(fqNameParent).getFragments().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) next;
                if ((packageFragmentDescriptor instanceof IncrementalPackageFragmentProvider.IncrementalPackageFragment) && Intrinsics.areEqual(((IncrementalPackageFragmentProvider.IncrementalPackageFragment) packageFragmentDescriptor).getTarget(), state.getTargetId())) {
                    break;
                }
            }
            IncrementalPackageFragmentProvider.IncrementalPackageFragment incrementalPackageFragment = (IncrementalPackageFragmentProvider.IncrementalPackageFragment) next;
            if (incrementalPackageFragment != null) {
                return incrementalPackageFragment.getPackageFragmentForMultifileClass(facadeFqName);
            }
            return null;
        }

        public final void writeMetadata(ClassBuilder classBuilder, GenerationState state, int flags, final List<String> partInternalNames, final Type facadeClassType, final FqName kotlinPackageFqName) {
            Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(partInternalNames, "partInternalNames");
            Intrinsics.checkNotNullParameter(facadeClassType, "facadeClassType");
            Intrinsics.checkNotNullParameter(kotlinPackageFqName, "kotlinPackageFqName");
            WriteAnnotationUtilKt.writeKotlinMetadata(classBuilder, state, KotlinClassHeader.Kind.MULTIFILE_CLASS, false, flags, new Function1<AnnotationVisitor, Unit>() { // from class: org.jetbrains.kotlin.codegen.MultifileClassCodegenImpl$Companion$writeMetadata$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(AnnotationVisitor annotationVisitor) {
                    invoke2(annotationVisitor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(AnnotationVisitor av) {
                    Intrinsics.checkNotNullParameter(av, "av");
                    AnnotationVisitor annotationVisitorVisitArray = av.visitArray(JvmAnnotationNames.METADATA_DATA_FIELD_NAME);
                    Iterator<String> it = partInternalNames.iterator();
                    while (it.hasNext()) {
                        annotationVisitorVisitArray.visit(null, it.next());
                    }
                    annotationVisitorVisitArray.visitEnd();
                    if (Intrinsics.areEqual(kotlinPackageFqName, JvmClassName.byInternalName(facadeClassType.getInternalName()).getPackageFqName())) {
                        return;
                    }
                    av.visit(JvmAnnotationNames.METADATA_PACKAGE_NAME_FIELD_NAME, kotlinPackageFqName.asString());
                }
            });
        }
    }
}
