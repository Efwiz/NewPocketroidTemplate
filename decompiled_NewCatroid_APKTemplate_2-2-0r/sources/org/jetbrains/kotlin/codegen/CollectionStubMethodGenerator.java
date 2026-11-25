package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.MutableClassDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.NonReportingOverrideStrategy;
import org.jetbrains.kotlin.resolve.OverridingStrategy;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.DescriptorSubstitutor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: CollectionStubMethodGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 /2\u00020\u0001:\u0003./0B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0002J\u001a\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\nH\u0002J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ \u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0018\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020\u000fH\u0002J\u001e\u0010'\u001a\u00020(2\u0006\u0010\"\u001a\u00020\u00052\f\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\fH\u0002J\u001a\u0010+\u001a\u00020(*\b\u0012\u0004\u0012\u00020(0\u00142\u0006\u0010,\u001a\u00020\u0012H\u0002J\u0016\u0010-\u001a\u0004\u0018\u00010\u000f*\u00020\u000f2\u0006\u0010,\u001a\u00020\u0012H\u0002J\f\u0010\u001d\u001a\u00020\u001e*\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CollectionStubMethodGenerator;", "", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "computeTasksToGenerate", "Lorg/jetbrains/kotlin/codegen/CollectionStubMethodGenerator$TasksToGenerate;", "createSyntheticSubclass", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/impl/MutableClassDescriptor;", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "findFakeOverridesForMethodsFromMutableCollection", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "klass", "mutableCollectionTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "findRelevantSuperCollectionClasses", "", "Lorg/jetbrains/kotlin/codegen/CollectionStubMethodGenerator$CollectionClassPair;", CompilerOptions.GENERATE, "", "functionCodegen", "Lorg/jetbrains/kotlin/codegen/FunctionCodegen;", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "generateMethodStub", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodGenericSignature;", "synthetic", "", "generateOverridesInAClass", "classDescriptor", "strategy", "Lorg/jetbrains/kotlin/resolve/OverridingStrategy;", "isDefaultInJdk", "method", "newType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeArguments", "Lorg/jetbrains/kotlin/types/TypeProjection;", "findMostSpecificTypeForClass", "typeConstructor", "findOverriddenFromDirectSuperClass", "CollectionClassPair", "Companion", "TasksToGenerate", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectionStubMethodGenerator {
    private static final TasksToGenerate NO_TASKS = new TasksToGenerate(SetsKt.emptySet(), SetsKt.emptySet(), SetsKt.emptySet());
    private final ClassDescriptor descriptor;
    private final KotlinTypeMapper typeMapper;

    public CollectionStubMethodGenerator(KotlinTypeMapper typeMapper, ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.typeMapper = typeMapper;
        this.descriptor = descriptor;
    }

    /* JADX INFO: compiled from: CollectionStubMethodGenerator.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\u0002\u0010\bJ\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003HÆ\u0003J9\u0010\u0010\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CollectionStubMethodGenerator$TasksToGenerate;", "", "methodStubsToGenerate", "", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodGenericSignature;", "syntheticStubsToGenerate", "bridgesToGenerate", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)V", "getBridgesToGenerate", "()Ljava/util/Set;", "getMethodStubsToGenerate", "getSyntheticStubsToGenerate", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class TasksToGenerate {
        private final Set<FunctionDescriptor> bridgesToGenerate;
        private final Set<JvmMethodGenericSignature> methodStubsToGenerate;
        private final Set<JvmMethodGenericSignature> syntheticStubsToGenerate;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ TasksToGenerate copy$default(TasksToGenerate tasksToGenerate, Set set, Set set2, Set set3, int i, Object obj) {
            if ((i & 1) != 0) {
                set = tasksToGenerate.methodStubsToGenerate;
            }
            if ((i & 2) != 0) {
                set2 = tasksToGenerate.syntheticStubsToGenerate;
            }
            if ((i & 4) != 0) {
                set3 = tasksToGenerate.bridgesToGenerate;
            }
            return tasksToGenerate.copy(set, set2, set3);
        }

        public final Set<JvmMethodGenericSignature> component1() {
            return this.methodStubsToGenerate;
        }

        public final Set<JvmMethodGenericSignature> component2() {
            return this.syntheticStubsToGenerate;
        }

        public final Set<FunctionDescriptor> component3() {
            return this.bridgesToGenerate;
        }

        public final TasksToGenerate copy(Set<? extends JvmMethodGenericSignature> methodStubsToGenerate, Set<? extends JvmMethodGenericSignature> syntheticStubsToGenerate, Set<? extends FunctionDescriptor> bridgesToGenerate) {
            Intrinsics.checkNotNullParameter(methodStubsToGenerate, "methodStubsToGenerate");
            Intrinsics.checkNotNullParameter(syntheticStubsToGenerate, "syntheticStubsToGenerate");
            Intrinsics.checkNotNullParameter(bridgesToGenerate, "bridgesToGenerate");
            return new TasksToGenerate(methodStubsToGenerate, syntheticStubsToGenerate, bridgesToGenerate);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TasksToGenerate)) {
                return false;
            }
            TasksToGenerate tasksToGenerate = (TasksToGenerate) other;
            return Intrinsics.areEqual(this.methodStubsToGenerate, tasksToGenerate.methodStubsToGenerate) && Intrinsics.areEqual(this.syntheticStubsToGenerate, tasksToGenerate.syntheticStubsToGenerate) && Intrinsics.areEqual(this.bridgesToGenerate, tasksToGenerate.bridgesToGenerate);
        }

        public int hashCode() {
            return (((this.methodStubsToGenerate.hashCode() * 31) + this.syntheticStubsToGenerate.hashCode()) * 31) + this.bridgesToGenerate.hashCode();
        }

        public String toString() {
            return "TasksToGenerate(methodStubsToGenerate=" + this.methodStubsToGenerate + ", syntheticStubsToGenerate=" + this.syntheticStubsToGenerate + ", bridgesToGenerate=" + this.bridgesToGenerate + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TasksToGenerate(Set<? extends JvmMethodGenericSignature> methodStubsToGenerate, Set<? extends JvmMethodGenericSignature> syntheticStubsToGenerate, Set<? extends FunctionDescriptor> bridgesToGenerate) {
            Intrinsics.checkNotNullParameter(methodStubsToGenerate, "methodStubsToGenerate");
            Intrinsics.checkNotNullParameter(syntheticStubsToGenerate, "syntheticStubsToGenerate");
            Intrinsics.checkNotNullParameter(bridgesToGenerate, "bridgesToGenerate");
            this.methodStubsToGenerate = methodStubsToGenerate;
            this.syntheticStubsToGenerate = syntheticStubsToGenerate;
            this.bridgesToGenerate = bridgesToGenerate;
        }

        public final Set<JvmMethodGenericSignature> getMethodStubsToGenerate() {
            return this.methodStubsToGenerate;
        }

        public final Set<JvmMethodGenericSignature> getSyntheticStubsToGenerate() {
            return this.syntheticStubsToGenerate;
        }

        public final Set<FunctionDescriptor> getBridgesToGenerate() {
            return this.bridgesToGenerate;
        }
    }

    private final TasksToGenerate computeTasksToGenerate() {
        JvmMethodGenericSignature jvmMethodGenericSignatureSignature;
        String genericsSignature;
        Pair pair;
        if (this.descriptor.getClassKind() == ClassKind.INTERFACE || (this.descriptor instanceof JavaClassDescriptor)) {
            return NO_TASKS;
        }
        Collection<CollectionClassPair> collectionFindRelevantSuperCollectionClasses = findRelevantSuperCollectionClasses();
        if (collectionFindRelevantSuperCollectionClasses.isEmpty()) {
            return NO_TASKS;
        }
        SmartList<ClassDescriptor> allSuperclassesWithoutAny = DescriptorUtilsKt.getAllSuperclassesWithoutAny(this.descriptor);
        ArrayList arrayList = new ArrayList();
        for (ClassDescriptor classDescriptor : allSuperclassesWithoutAny) {
            KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
            Intrinsics.checkNotNull(classDescriptor);
            TasksToGenerate tasksToGenerateComputeTasksToGenerate = new CollectionStubMethodGenerator(kotlinTypeMapper, classDescriptor).computeTasksToGenerate();
            Set setPlus = SetsKt.plus((Set) tasksToGenerateComputeTasksToGenerate.getMethodStubsToGenerate(), (Iterable) tasksToGenerateComputeTasksToGenerate.getSyntheticStubsToGenerate());
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setPlus, 10));
            Iterator it = setPlus.iterator();
            while (it.hasNext()) {
                arrayList2.add(((JvmMethodGenericSignature) it.next()).getAsmMethod());
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        ArrayList arrayList3 = arrayList;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        for (CollectionClassPair collectionClassPair : collectionFindRelevantSuperCollectionClasses) {
            TypeConstructor readOnlyClass = collectionClassPair.getReadOnlyClass();
            TypeConstructor mutableClass = collectionClassPair.getMutableClass();
            Pair<MutableClassDescriptor, List<TypeParameterDescriptor>> pairCreateSyntheticSubclass = createSyntheticSubclass();
            MutableClassDescriptor mutableClassDescriptorComponent1 = pairCreateSyntheticSubclass.component1();
            List<TypeParameterDescriptor> listComponent2 = pairCreateSyntheticSubclass.component2();
            ClassDescriptor classDescriptor2 = this.descriptor;
            List<TypeParameterDescriptor> list = listComponent2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                arrayList4.add(new TypeProjectionImpl(((TypeParameterDescriptor) it2.next()).getDefaultType()));
            }
            KotlinType kotlinTypeNewType = newType(classDescriptor2, arrayList4);
            Set<KotlinType> allSupertypes = TypeUtils.getAllSupertypes(kotlinTypeNewType);
            Intrinsics.checkNotNullExpressionValue(allSupertypes, "getAllSupertypes(...)");
            KotlinType kotlinTypeFindMostSpecificTypeForClass = findMostSpecificTypeForClass(allSupertypes, readOnlyClass);
            ClassifierDescriptor this$0 = mutableClass.getThis$0();
            Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            KotlinType kotlinTypeNewType2 = newType((ClassDescriptor) this$0, kotlinTypeFindMostSpecificTypeForClass.getArguments());
            mutableClassDescriptorComponent1.addSupertype(kotlinTypeNewType);
            mutableClassDescriptorComponent1.addSupertype(kotlinTypeNewType2);
            mutableClassDescriptorComponent1.createTypeConstructor();
            for (FunctionDescriptor functionDescriptor : findFakeOverridesForMethodsFromMutableCollection(mutableClassDescriptorComponent1, mutableClass)) {
                if (functionDescriptor.getModality() == Modality.ABSTRACT || isDefaultInJdk(functionDescriptor)) {
                    TypeConstructor typeConstructor = this.descriptor.getTypeConstructor();
                    Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
                    FunctionDescriptor functionDescriptorFindOverriddenFromDirectSuperClass = findOverriddenFromDirectSuperClass(functionDescriptor, typeConstructor);
                    if ((functionDescriptorFindOverriddenFromDirectSuperClass != null ? functionDescriptorFindOverriddenFromDirectSuperClass.getKind() : null) != CallableMemberDescriptor.Kind.DECLARATION) {
                        FunctionDescriptor functionDescriptorFindOverriddenFromDirectSuperClass2 = findOverriddenFromDirectSuperClass(functionDescriptor, mutableClass);
                        Intrinsics.checkNotNull(functionDescriptorFindOverriddenFromDirectSuperClass2);
                        FunctionDescriptor original = functionDescriptorFindOverriddenFromDirectSuperClass2.getOriginal();
                        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                        JvmMethodGenericSignature jvmMethodGenericSignatureSignature2 = signature(original);
                        FunctionDescriptor functionDescriptor2 = functionDescriptorFindOverriddenFromDirectSuperClass2;
                        if (BuiltinMethodsWithSpecialGenericSignature.INSTANCE.isBuiltinWithSpecialDescriptorInJvm(functionDescriptor2)) {
                            JvmMethodGenericSignature jvmMethodGenericSignatureSignature3 = signature(functionDescriptorFindOverriddenFromDirectSuperClass2);
                            SpecialGenericSignatures.SpecialSignatureInfo specialSignatureInfo = BuiltinMethodsWithSpecialGenericSignature.getSpecialSignatureInfo(functionDescriptor2);
                            if (specialSignatureInfo == null || (genericsSignature = CodegenUtilKt.replaceValueParametersIn(specialSignatureInfo, jvmMethodGenericSignatureSignature3.getGenericsSignature())) == null) {
                                genericsSignature = jvmMethodGenericSignatureSignature3.getGenericsSignature();
                            }
                            if (specialSignatureInfo != null && specialSignatureInfo.getIsObjectReplacedWithTypeParameter()) {
                                pair = new Pair(jvmMethodGenericSignatureSignature2.getAsmMethod(), jvmMethodGenericSignatureSignature2.getValueParameters());
                            } else {
                                pair = new Pair(jvmMethodGenericSignatureSignature3.getAsmMethod(), jvmMethodGenericSignatureSignature3.getValueParameters());
                            }
                            jvmMethodGenericSignatureSignature = new JvmMethodGenericSignature((Method) pair.component1(), (List) pair.component2(), genericsSignature);
                        } else {
                            jvmMethodGenericSignatureSignature = signature(functionDescriptor);
                        }
                        if (!arrayList3.contains(jvmMethodGenericSignatureSignature.getAsmMethod()) && !arrayList3.contains(jvmMethodGenericSignatureSignature2.getAsmMethod())) {
                            linkedHashSet.add(jvmMethodGenericSignatureSignature);
                            if (!Intrinsics.areEqual(jvmMethodGenericSignatureSignature2.getAsmMethod(), jvmMethodGenericSignatureSignature.getAsmMethod())) {
                                linkedHashSet2.add(jvmMethodGenericSignatureSignature2);
                            }
                        }
                    }
                } else {
                    linkedHashSet3.add(functionDescriptor);
                }
            }
        }
        return new TasksToGenerate(linkedHashSet, linkedHashSet2, linkedHashSet3);
    }

    public final void generate(FunctionCodegen functionCodegen, ClassBuilder v) {
        Intrinsics.checkNotNullParameter(functionCodegen, "functionCodegen");
        Intrinsics.checkNotNullParameter(v, "v");
        TasksToGenerate tasksToGenerateComputeTasksToGenerate = computeTasksToGenerate();
        Set<JvmMethodGenericSignature> setComponent1 = tasksToGenerateComputeTasksToGenerate.component1();
        Set<JvmMethodGenericSignature> setComponent2 = tasksToGenerateComputeTasksToGenerate.component2();
        Set<FunctionDescriptor> setComponent3 = tasksToGenerateComputeTasksToGenerate.component3();
        Iterator<JvmMethodGenericSignature> it = setComponent1.iterator();
        while (it.hasNext()) {
            generateMethodStub(v, it.next(), false);
        }
        Iterator<JvmMethodGenericSignature> it2 = setComponent2.iterator();
        while (it2.hasNext()) {
            generateMethodStub(v, it2.next(), true);
        }
        Iterator<FunctionDescriptor> it3 = setComponent3.iterator();
        while (it3.hasNext()) {
            functionCodegen.generateBridges(it3.next());
        }
    }

    private final boolean isDefaultInJdk(FunctionDescriptor method) {
        if (method.getModality() != Modality.ABSTRACT) {
            for (FunctionDescriptor functionDescriptor : DescriptorUtilsKt.overriddenTreeUniqueAsSequence(method.getOriginal(), true)) {
                if (functionDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE || KotlinBuiltIns.isBuiltIn(functionDescriptor)) {
                }
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: compiled from: CollectionStubMethodGenerator.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CollectionStubMethodGenerator$CollectionClassPair;", "", "readOnlyClass", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "mutableClass", "(Lorg/jetbrains/kotlin/types/TypeConstructor;Lorg/jetbrains/kotlin/types/TypeConstructor;)V", "getMutableClass", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "getReadOnlyClass", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class CollectionClassPair {
        private final TypeConstructor mutableClass;
        private final TypeConstructor readOnlyClass;

        public static /* synthetic */ CollectionClassPair copy$default(CollectionClassPair collectionClassPair, TypeConstructor typeConstructor, TypeConstructor typeConstructor2, int i, Object obj) {
            if ((i & 1) != 0) {
                typeConstructor = collectionClassPair.readOnlyClass;
            }
            if ((i & 2) != 0) {
                typeConstructor2 = collectionClassPair.mutableClass;
            }
            return collectionClassPair.copy(typeConstructor, typeConstructor2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final TypeConstructor getReadOnlyClass() {
            return this.readOnlyClass;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final TypeConstructor getMutableClass() {
            return this.mutableClass;
        }

        public final CollectionClassPair copy(TypeConstructor readOnlyClass, TypeConstructor mutableClass) {
            Intrinsics.checkNotNullParameter(readOnlyClass, "readOnlyClass");
            Intrinsics.checkNotNullParameter(mutableClass, "mutableClass");
            return new CollectionClassPair(readOnlyClass, mutableClass);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CollectionClassPair)) {
                return false;
            }
            CollectionClassPair collectionClassPair = (CollectionClassPair) other;
            return Intrinsics.areEqual(this.readOnlyClass, collectionClassPair.readOnlyClass) && Intrinsics.areEqual(this.mutableClass, collectionClassPair.mutableClass);
        }

        public int hashCode() {
            return (this.readOnlyClass.hashCode() * 31) + this.mutableClass.hashCode();
        }

        public String toString() {
            return "CollectionClassPair(readOnlyClass=" + this.readOnlyClass + ", mutableClass=" + this.mutableClass + ')';
        }

        public CollectionClassPair(TypeConstructor readOnlyClass, TypeConstructor mutableClass) {
            Intrinsics.checkNotNullParameter(readOnlyClass, "readOnlyClass");
            Intrinsics.checkNotNullParameter(mutableClass, "mutableClass");
            this.readOnlyClass = readOnlyClass;
            this.mutableClass = mutableClass;
        }

        public final TypeConstructor getReadOnlyClass() {
            return this.readOnlyClass;
        }

        public final TypeConstructor getMutableClass() {
            return this.mutableClass;
        }
    }

    private static final CollectionClassPair findRelevantSuperCollectionClasses$pair(TypeConstructor typeConstructor, TypeConstructor typeConstructor2) {
        return new CollectionClassPair(typeConstructor, typeConstructor2);
    }

    private final Collection<CollectionClassPair> findRelevantSuperCollectionClasses() {
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(this.descriptor);
        List<Pair> listListOf = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(builtIns.getCollection(), builtIns.getMutableCollection()), TuplesKt.to(builtIns.getSet(), builtIns.getMutableSet()), TuplesKt.to(builtIns.getList(), builtIns.getMutableList()), TuplesKt.to(builtIns.getMap(), builtIns.getMutableMap()), TuplesKt.to(builtIns.getMapEntry(), builtIns.getMutableMapEntry()), TuplesKt.to(builtIns.getIterable(), builtIns.getMutableIterable()), TuplesKt.to(builtIns.getIterator(), builtIns.getMutableIterator()), TuplesKt.to(builtIns.getListIterator(), builtIns.getMutableListIterator())});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        for (Pair pair : listListOf) {
            ClassDescriptor classDescriptor = (ClassDescriptor) pair.component1();
            ClassDescriptor classDescriptor2 = (ClassDescriptor) pair.component2();
            TypeConstructor typeConstructor = classDescriptor.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
            TypeConstructor typeConstructor2 = classDescriptor2.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "getTypeConstructor(...)");
            arrayList.add(findRelevantSuperCollectionClasses$pair(typeConstructor, typeConstructor2));
        }
        ArrayList arrayList2 = arrayList;
        Set<KotlinType> allSupertypes = TypeUtils.getAllSupertypes(this.descriptor.getDefaultType());
        Intrinsics.checkNotNullExpressionValue(allSupertypes, "getAllSupertypes(...)");
        HashSet hashSet = new HashSet();
        Iterator<T> it = allSupertypes.iterator();
        while (it.hasNext()) {
            hashSet.add(((KotlinType) it.next()).getConstructor());
        }
        HashSet hashSet2 = hashSet;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            CollectionClassPair collectionClassPair = (CollectionClassPair) obj;
            TypeConstructor readOnlyClass = collectionClassPair.getReadOnlyClass();
            TypeConstructor mutableClass = collectionClassPair.getMutableClass();
            if (hashSet2.contains(readOnlyClass) && !hashSet2.contains(mutableClass)) {
                arrayList3.add(obj);
            }
        }
        ArrayList arrayList4 = arrayList3;
        if (arrayList4.isEmpty()) {
            return SetsKt.emptySet();
        }
        ArrayList arrayList5 = arrayList4;
        HashSet hashSet3 = new HashSet();
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            Collection<KotlinType> collectionMo7438getSupertypes = ((CollectionClassPair) it2.next()).getReadOnlyClass().mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            Collection<KotlinType> collection = collectionMo7438getSupertypes;
            ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it3 = collection.iterator();
            while (it3.hasNext()) {
                arrayList6.add(((KotlinType) it3.next()).getConstructor());
            }
            CollectionsKt.addAll(hashSet3, arrayList6);
        }
        HashSet hashSet4 = hashSet3;
        ArrayList arrayList7 = new ArrayList();
        for (Object obj2 : arrayList5) {
            if (!hashSet4.contains(((CollectionClassPair) obj2).getReadOnlyClass())) {
                arrayList7.add(obj2);
            }
        }
        return arrayList7;
    }

    private final List<FunctionDescriptor> findFakeOverridesForMethodsFromMutableCollection(ClassDescriptor klass, final TypeConstructor mutableCollectionTypeConstructor) {
        final ArrayList arrayList = new ArrayList();
        generateOverridesInAClass(klass, new NonReportingOverrideStrategy() { // from class: org.jetbrains.kotlin.codegen.CollectionStubMethodGenerator.findFakeOverridesForMethodsFromMutableCollection.1
            @Override // org.jetbrains.kotlin.resolve.NonReportingOverrideStrategy
            protected void conflict(CallableMemberDescriptor fromSuper, CallableMemberDescriptor fromCurrent) {
                Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
                Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
            }

            @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
            public void addFakeOverride(CallableMemberDescriptor fakeOverride) {
                FunctionDescriptor functionDescriptorCopy;
                Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
                if (fakeOverride instanceof FunctionDescriptor) {
                    FunctionDescriptor functionDescriptor = (FunctionDescriptor) fakeOverride;
                    FunctionDescriptor functionDescriptorFindOverriddenFromDirectSuperClass = CollectionStubMethodGenerator.this.findOverriddenFromDirectSuperClass(functionDescriptor, mutableCollectionTypeConstructor);
                    if (functionDescriptorFindOverriddenFromDirectSuperClass != null && functionDescriptorFindOverriddenFromDirectSuperClass.getKind() == CallableMemberDescriptor.Kind.DECLARATION) {
                        KotlinTypeChecker kotlinTypeChecker = CollectionStubMethodGeneratorKt.READ_ONLY_ARE_EQUAL_TO_MUTABLE_TYPE_CHECKER;
                        KotlinType returnType = functionDescriptor.getReturnType();
                        Intrinsics.checkNotNull(returnType);
                        KotlinType returnType2 = functionDescriptorFindOverriddenFromDirectSuperClass.getReturnType();
                        Intrinsics.checkNotNull(returnType2);
                        if (kotlinTypeChecker.isSubtypeOf(returnType, returnType2)) {
                            functionDescriptorCopy = functionDescriptor;
                        } else {
                            functionDescriptorCopy = functionDescriptorFindOverriddenFromDirectSuperClass.copy(functionDescriptor.getContainingModule(), functionDescriptorFindOverriddenFromDirectSuperClass.getModality(), functionDescriptorFindOverriddenFromDirectSuperClass.getVisibility(), functionDescriptor.getKind(), false);
                            Intrinsics.checkNotNullExpressionValue(functionDescriptorCopy, "copy(...)");
                        }
                        Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
                        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj : overriddenDescriptors) {
                            KotlinTypeChecker kotlinTypeChecker2 = CollectionStubMethodGeneratorKt.READ_ONLY_ARE_EQUAL_TO_MUTABLE_TYPE_CHECKER;
                            KotlinType returnType3 = functionDescriptorCopy.getReturnType();
                            Intrinsics.checkNotNull(returnType3);
                            KotlinType returnType4 = ((FunctionDescriptor) obj).getReturnType();
                            Intrinsics.checkNotNull(returnType4);
                            if (kotlinTypeChecker2.isSubtypeOf(returnType3, returnType4)) {
                                arrayList2.add(obj);
                            }
                        }
                        functionDescriptorCopy.mo7205setOverriddenDescriptors(arrayList2);
                        arrayList.add(functionDescriptorCopy);
                    }
                }
            }
        });
        return arrayList;
    }

    private final KotlinType findMostSpecificTypeForClass(Collection<? extends KotlinType> collection, TypeConstructor typeConstructor) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (Intrinsics.areEqual(((KotlinType) obj).getConstructor(), typeConstructor)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            throw new IllegalStateException(("No supertype of " + typeConstructor + " in " + collection).toString());
        }
        if (arrayList2.size() == 1) {
            return (KotlinType) CollectionsKt.first((List) arrayList2);
        }
        ArrayList<KotlinType> arrayList3 = arrayList2;
        for (KotlinType kotlinType : arrayList3) {
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType, (KotlinType) it.next())) {
                    }
                }
            }
            return kotlinType;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private final void generateOverridesInAClass(ClassDescriptor classDescriptor, OverridingStrategy strategy) {
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = collectionMo7438getSupertypes.iterator();
        while (it.hasNext()) {
            Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(((KotlinType) it.next()).getMemberScope());
            Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : allDescriptors) {
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
                if ((declarationDescriptor instanceof PropertyDescriptor) || (declarationDescriptor instanceof SimpleFunctionDescriptor)) {
                    arrayList.add(obj);
                }
            }
            CollectionsKt.addAll(linkedHashSet, arrayList);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : linkedHashSet) {
            Name name2 = ((CallableMemberDescriptor) obj2).getShortName();
            Object obj3 = linkedHashMap.get(name2);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(name2, obj3);
            }
            ((List) obj3).add(obj2);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            OverridingUtil.DEFAULT.generateOverridesInFunctionGroup((Name) entry.getKey(), (List) entry.getValue(), CollectionsKt.emptyList(), classDescriptor, strategy);
        }
    }

    private final Pair<MutableClassDescriptor, List<TypeParameterDescriptor>> createSyntheticSubclass() {
        MutableClassDescriptor mutableClassDescriptor = new MutableClassDescriptor(this.descriptor.getContainingModule(), ClassKind.CLASS, false, false, Name.special("<synthetic inheritor of " + this.descriptor.getShortName() + '>'), this.descriptor.getSourceElement(), LockBasedStorageManager.NO_LOCKS);
        mutableClassDescriptor.setModality(Modality.FINAL);
        mutableClassDescriptor.setVisibility(DescriptorVisibilities.PUBLIC);
        List<TypeParameterDescriptor> parameters = this.descriptor.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        ArrayList arrayList = new ArrayList(parameters.size());
        DescriptorSubstitutor.substituteTypeParameters(parameters, TypeSubstitution.EMPTY, mutableClassDescriptor, arrayList);
        mutableClassDescriptor.setTypeParameterDescriptors(parameters);
        return new Pair<>(mutableClassDescriptor, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FunctionDescriptor findOverriddenFromDirectSuperClass(FunctionDescriptor functionDescriptor, TypeConstructor typeConstructor) {
        Object obj;
        Object next;
        ClassDescriptor classDescriptor;
        Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Iterator<T> it = overriddenDescriptors.iterator();
        do {
            obj = null;
            if (it.hasNext()) {
                next = it.next();
                DeclarationDescriptor containingModule = ((FunctionDescriptor) next).getContainingModule();
                classDescriptor = containingModule instanceof ClassDescriptor ? (ClassDescriptor) containingModule : null;
            }
            return (FunctionDescriptor) obj;
        } while (!Intrinsics.areEqual(classDescriptor != null ? classDescriptor.getTypeConstructor() : null, typeConstructor));
        obj = next;
        return (FunctionDescriptor) obj;
    }

    private final KotlinType newType(ClassDescriptor classDescriptor, List<? extends TypeProjection> typeArguments) {
        return KotlinTypeFactory.simpleNotNullType(TypeAttributes.INSTANCE.getEmpty(), classDescriptor, typeArguments);
    }

    private final JvmMethodGenericSignature signature(FunctionDescriptor functionDescriptor) {
        return this.typeMapper.mapSignatureWithGeneric(functionDescriptor, OwnerKind.IMPLEMENTATION);
    }

    private final void generateMethodStub(ClassBuilder v, JvmMethodGenericSignature signature, boolean synthetic) {
        this.descriptor.getClassKind();
        ClassKind classKind = ClassKind.INTERFACE;
        int i = (synthetic ? 4096 : 0) | 1;
        Method asmMethod = signature.getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        MethodVisitor methodVisitorNewMethod = v.newMethod(JvmDeclarationOriginKt.getCollectionStub(), i, asmMethod.getName(), asmMethod.getDescriptor(), synthetic ? null : signature.getGenericsSignature(), null);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        methodVisitorNewMethod.visitCode();
        AsmUtil.genThrow(new InstructionAdapter(methodVisitorNewMethod), "java/lang/UnsupportedOperationException", "Operation is not supported for read-only collection");
        FunctionCodegen.endVisit(methodVisitorNewMethod, "built-in stub for " + signature);
    }
}
