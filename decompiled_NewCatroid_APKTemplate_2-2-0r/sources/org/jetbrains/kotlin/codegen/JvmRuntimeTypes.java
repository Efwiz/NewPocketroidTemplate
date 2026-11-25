package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.MutableClassDescriptor;
import org.jetbrains.kotlin.descriptors.impl.MutablePackageFragmentDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: JvmRuntimeTypes.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010>\u001a\b\u0012\u0004\u0012\u00020\n0?2\u0006\u0010@\u001a\u00020AH\u0002J\"\u0010B\u001a\u00020\n2\u0006\u0010C\u001a\u00020D2\u0006\u0010@\u001a\u00020A2\b\b\u0002\u0010E\u001a\u00020FH\u0002J\u0010\u0010G\u001a\u00020\n2\u0006\u0010H\u001a\u00020AH\u0002J\u001e\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\u00072\u0006\u0010N\u001a\u00020\u0007J\u0014\u0010O\u001a\b\u0012\u0004\u0012\u00020J0P2\u0006\u0010K\u001a\u00020QJ4\u0010R\u001a\b\u0012\u0004\u0012\u00020J0P2\u0006\u0010S\u001a\u00020Q2\u0006\u0010T\u001a\u00020U2\u0006\u0010N\u001a\u00020\u00072\u0006\u0010V\u001a\u00020\u00072\u0006\u0010W\u001a\u00020\u0007J \u0010X\u001a\b\u0012\u0004\u0012\u00020\n0?2\u0006\u0010H\u001a\u00020A2\b\b\u0002\u0010C\u001a\u00020DH\u0002J$\u0010Y\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0%0?2\u0006\u0010Z\u001a\u00020A2\u0006\u0010[\u001a\u00020AH\u0002R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0010\u0010\fR\u001b\u0010\u0012\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000e\u001a\u0004\b\u0013\u0010\fR\u001b\u0010\u0015\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000e\u001a\u0004\b\u0016\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001b\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001c\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001e\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u000e\u001a\u0004\b\u001f\u0010\fR\u001b\u0010!\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u000e\u001a\u0004\b\"\u0010\fR!\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\u000e\u001a\u0004\b&\u0010'R!\u0010)\u001a\b\u0012\u0004\u0012\u00020\n0%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u000e\u001a\u0004\b*\u0010'R!\u0010,\u001a\b\u0012\u0004\u0012\u00020\n0%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\u000e\u001a\u0004\b-\u0010'R!\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0%8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\u000e\u001a\u0004\b0\u0010'R\u001b\u00102\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u000e\u001a\u0004\b3\u0010\fR\u001b\u00105\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b7\u0010\u000e\u001a\u0004\b6\u0010\fR\u001d\u00108\u001a\u0004\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b:\u0010\u000e\u001a\u0004\b9\u0010\fR\u001b\u0010;\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b=\u0010\u000e\u001a\u0004\b<\u0010\f¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmRuntimeTypes;", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "generateOptimizedCallableReferenceSuperClasses", "", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Z)V", "adaptedFunctionReference", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getAdaptedFunctionReference", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "adaptedFunctionReference$delegate", "Lkotlin/Lazy;", "continuationImpl", "getContinuationImpl", "continuationImpl$delegate", "functionReference", "getFunctionReference", "functionReference$delegate", "functionReferenceImpl", "getFunctionReferenceImpl", "functionReferenceImpl$delegate", "kotlinCoroutinesJvmInternalPackage", "Lorg/jetbrains/kotlin/descriptors/impl/MutablePackageFragmentDescriptor;", "kotlinJvmInternalPackage", "lambda", "getLambda", "lambda$delegate", "localVariableReference", "getLocalVariableReference", "localVariableReference$delegate", "mutableLocalVariableReference", "getMutableLocalVariableReference", "mutableLocalVariableReference$delegate", "mutablePropertyReferenceImpls", "", "getMutablePropertyReferenceImpls", "()Ljava/util/List;", "mutablePropertyReferenceImpls$delegate", "mutablePropertyReferences", "getMutablePropertyReferences", "mutablePropertyReferences$delegate", "propertyReferenceImpls", "getPropertyReferenceImpls", "propertyReferenceImpls$delegate", "propertyReferences", "getPropertyReferences", "propertyReferences$delegate", "restrictedContinuationImpl", "getRestrictedContinuationImpl", "restrictedContinuationImpl$delegate", "restrictedSuspendLambda", "getRestrictedSuspendLambda", "restrictedSuspendLambda$delegate", "suspendFunctionInterface", "getSuspendFunctionInterface", "suspendFunctionInterface$delegate", "suspendLambda", "getSuspendLambda", "suspendLambda$delegate", "coroutinesInternal", "Lkotlin/Lazy;", "name", "", "createClass", "packageFragment", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "createCoroutineSuperClass", "className", "getSupertypeForPropertyReference", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "isMutable", "isBound", "getSupertypesForClosure", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getSupertypesForFunctionReference", "referencedFunction", "anonymousFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/AnonymousFunctionDescriptor;", "isAdaptedCallableReference", "isSuspendConversion", "internal", "propertyClasses", "prefix", "suffix", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmRuntimeTypes {

    /* JADX INFO: renamed from: adaptedFunctionReference$delegate, reason: from kotlin metadata */
    private final Lazy adaptedFunctionReference;

    /* JADX INFO: renamed from: continuationImpl$delegate, reason: from kotlin metadata */
    private final Lazy continuationImpl;

    /* JADX INFO: renamed from: functionReference$delegate, reason: from kotlin metadata */
    private final Lazy functionReference;

    /* JADX INFO: renamed from: functionReferenceImpl$delegate, reason: from kotlin metadata */
    private final Lazy functionReferenceImpl;
    private final boolean generateOptimizedCallableReferenceSuperClasses;
    private final MutablePackageFragmentDescriptor kotlinCoroutinesJvmInternalPackage;
    private final MutablePackageFragmentDescriptor kotlinJvmInternalPackage;

    /* JADX INFO: renamed from: lambda$delegate, reason: from kotlin metadata */
    private final Lazy lambda;
    private final LanguageVersionSettings languageVersionSettings;

    /* JADX INFO: renamed from: localVariableReference$delegate, reason: from kotlin metadata */
    private final Lazy localVariableReference;

    /* JADX INFO: renamed from: mutableLocalVariableReference$delegate, reason: from kotlin metadata */
    private final Lazy mutableLocalVariableReference;

    /* JADX INFO: renamed from: mutablePropertyReferenceImpls$delegate, reason: from kotlin metadata */
    private final Lazy mutablePropertyReferenceImpls;

    /* JADX INFO: renamed from: mutablePropertyReferences$delegate, reason: from kotlin metadata */
    private final Lazy mutablePropertyReferences;

    /* JADX INFO: renamed from: propertyReferenceImpls$delegate, reason: from kotlin metadata */
    private final Lazy propertyReferenceImpls;

    /* JADX INFO: renamed from: propertyReferences$delegate, reason: from kotlin metadata */
    private final Lazy propertyReferences;

    /* JADX INFO: renamed from: restrictedContinuationImpl$delegate, reason: from kotlin metadata */
    private final Lazy restrictedContinuationImpl;

    /* JADX INFO: renamed from: restrictedSuspendLambda$delegate, reason: from kotlin metadata */
    private final Lazy restrictedSuspendLambda;

    /* JADX INFO: renamed from: suspendFunctionInterface$delegate, reason: from kotlin metadata */
    private final Lazy suspendFunctionInterface;

    /* JADX INFO: renamed from: suspendLambda$delegate, reason: from kotlin metadata */
    private final Lazy suspendLambda;

    public JvmRuntimeTypes(ModuleDescriptor module, LanguageVersionSettings languageVersionSettings, boolean z) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
        this.generateOptimizedCallableReferenceSuperClasses = z;
        this.kotlinJvmInternalPackage = new MutablePackageFragmentDescriptor(module, new FqName("kotlin.jvm.internal"));
        this.kotlinCoroutinesJvmInternalPackage = new MutablePackageFragmentDescriptor(module, StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME);
        this.lambda = internal$default(this, "Lambda", null, 2, null);
        this.functionReference = internal$default(this, "FunctionReference", null, 2, null);
        this.functionReferenceImpl = internal$default(this, "FunctionReferenceImpl", null, 2, null);
        this.adaptedFunctionReference = internal$default(this, "AdaptedFunctionReference", null, 2, null);
        this.localVariableReference = internal$default(this, "LocalVariableReference", null, 2, null);
        this.mutableLocalVariableReference = internal$default(this, "MutableLocalVariableReference", null, 2, null);
        this.continuationImpl = coroutinesInternal("ContinuationImpl");
        this.restrictedContinuationImpl = coroutinesInternal("RestrictedContinuationImpl");
        this.suspendLambda = coroutinesInternal("SuspendLambda");
        this.restrictedSuspendLambda = coroutinesInternal("RestrictedSuspendLambda");
        this.suspendFunctionInterface = LazyKt.lazy(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.codegen.JvmRuntimeTypes$suspendFunctionInterface$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                JvmRuntimeTypes jvmRuntimeTypes = this.this$0;
                return jvmRuntimeTypes.createClass(jvmRuntimeTypes.kotlinCoroutinesJvmInternalPackage, "SuspendFunction", ClassKind.INTERFACE);
            }
        });
        this.propertyReferences = propertyClasses("PropertyReference", "");
        this.mutablePropertyReferences = propertyClasses("MutablePropertyReference", "");
        this.propertyReferenceImpls = propertyClasses("PropertyReference", "Impl");
        this.mutablePropertyReferenceImpls = propertyClasses("MutablePropertyReference", "Impl");
    }

    static /* synthetic */ Lazy internal$default(JvmRuntimeTypes jvmRuntimeTypes, String str, PackageFragmentDescriptor packageFragmentDescriptor, int i, Object obj) {
        if ((i & 2) != 0) {
            packageFragmentDescriptor = jvmRuntimeTypes.kotlinJvmInternalPackage;
        }
        return jvmRuntimeTypes.internal(str, packageFragmentDescriptor);
    }

    private final Lazy<ClassDescriptor> internal(final String className, final PackageFragmentDescriptor packageFragment) {
        return LazyKt.lazy(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.codegen.JvmRuntimeTypes.internal.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                return JvmRuntimeTypes.createClass$default(JvmRuntimeTypes.this, packageFragment, className, null, 4, null);
            }
        });
    }

    private final Lazy<ClassDescriptor> coroutinesInternal(final String name2) {
        return LazyKt.lazy(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.codegen.JvmRuntimeTypes.coroutinesInternal.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                return JvmRuntimeTypes.this.createCoroutineSuperClass(name2);
            }
        });
    }

    private final Lazy<List<ClassDescriptor>> propertyClasses(final String prefix, final String suffix) {
        return LazyKt.lazy(new Function0<List<? extends ClassDescriptor>>() { // from class: org.jetbrains.kotlin.codegen.JvmRuntimeTypes.propertyClasses.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ClassDescriptor> invoke() {
                IntRange intRange = new IntRange(0, 2);
                JvmRuntimeTypes jvmRuntimeTypes = JvmRuntimeTypes.this;
                String str = prefix;
                String str2 = suffix;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
                Iterator<Integer> it = intRange.iterator();
                while (it.hasNext()) {
                    arrayList.add(JvmRuntimeTypes.createClass$default(jvmRuntimeTypes, jvmRuntimeTypes.kotlinJvmInternalPackage, str + ((IntIterator) it).nextInt() + str2, null, 4, null));
                }
                return arrayList;
            }
        });
    }

    private final ClassDescriptor getLambda() {
        return (ClassDescriptor) this.lambda.getValue();
    }

    public final ClassDescriptor getFunctionReference() {
        return (ClassDescriptor) this.functionReference.getValue();
    }

    public final ClassDescriptor getFunctionReferenceImpl() {
        return (ClassDescriptor) this.functionReferenceImpl.getValue();
    }

    public final ClassDescriptor getAdaptedFunctionReference() {
        return (ClassDescriptor) this.adaptedFunctionReference.getValue();
    }

    private final ClassDescriptor getLocalVariableReference() {
        return (ClassDescriptor) this.localVariableReference.getValue();
    }

    private final ClassDescriptor getMutableLocalVariableReference() {
        return (ClassDescriptor) this.mutableLocalVariableReference.getValue();
    }

    private final ClassDescriptor getContinuationImpl() {
        return (ClassDescriptor) this.continuationImpl.getValue();
    }

    private final ClassDescriptor getRestrictedContinuationImpl() {
        return (ClassDescriptor) this.restrictedContinuationImpl.getValue();
    }

    private final ClassDescriptor getSuspendLambda() {
        return (ClassDescriptor) this.suspendLambda.getValue();
    }

    private final ClassDescriptor getRestrictedSuspendLambda() {
        return (ClassDescriptor) this.restrictedSuspendLambda.getValue();
    }

    private final ClassDescriptor getSuspendFunctionInterface() {
        return (ClassDescriptor) this.suspendFunctionInterface.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor createCoroutineSuperClass(String className) {
        return createClass$default(this, this.kotlinCoroutinesJvmInternalPackage, className, null, 4, null);
    }

    private final List<ClassDescriptor> getPropertyReferences() {
        return (List) this.propertyReferences.getValue();
    }

    private final List<ClassDescriptor> getMutablePropertyReferences() {
        return (List) this.mutablePropertyReferences.getValue();
    }

    private final List<ClassDescriptor> getPropertyReferenceImpls() {
        return (List) this.propertyReferenceImpls.getValue();
    }

    private final List<ClassDescriptor> getMutablePropertyReferenceImpls() {
        return (List) this.mutablePropertyReferenceImpls.getValue();
    }

    static /* synthetic */ ClassDescriptor createClass$default(JvmRuntimeTypes jvmRuntimeTypes, PackageFragmentDescriptor packageFragmentDescriptor, String str, ClassKind classKind, int i, Object obj) {
        if ((i & 4) != 0) {
            classKind = ClassKind.CLASS;
        }
        return jvmRuntimeTypes.createClass(packageFragmentDescriptor, str, classKind);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor createClass(PackageFragmentDescriptor packageFragment, String name2, ClassKind classKind) {
        MutableClassDescriptor mutableClassDescriptor = new MutableClassDescriptor(packageFragment, classKind, false, false, Name.identifier(name2), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        mutableClassDescriptor.setModality(Modality.FINAL);
        mutableClassDescriptor.setVisibility(DescriptorVisibilities.PUBLIC);
        mutableClassDescriptor.setTypeParameterDescriptors(CollectionsKt.emptyList());
        mutableClassDescriptor.createTypeConstructor();
        return mutableClassDescriptor;
    }

    public final Collection<KotlinType> getSupertypesForClosure(FunctionDescriptor descriptor) {
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        FunctionDescriptor orCreateJvmSuspendFunctionView$default = descriptor.isSuspend() ? CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView$default(descriptor, null, 2, null) : descriptor;
        if (orCreateJvmSuspendFunctionView$default.getReturnType() == null) {
            throw new KotlinExceptionWithAttachments("Return type for function description is null. Super type cannot be calculated.initDesc=" + descriptor + ", actDesc=" + orCreateJvmSuspendFunctionView$default + ", isReleaseCoroutines=" + this.languageVersionSettings.supportsFeature(LanguageFeature.ReleaseCoroutines));
        }
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(descriptor);
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        ReceiverParameterDescriptor extensionReceiverParameter = orCreateJvmSuspendFunctionView$default.getExtensionReceiverParameter();
        KotlinType type2 = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        List<ReceiverParameterDescriptor> contextReceiverParameters = orCreateJvmSuspendFunctionView$default.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType type3 = ((ReceiverParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            arrayList.add(type3);
        }
        ArrayList arrayList2 = arrayList;
        List<ValueParameterDescriptor> valueParameters = orCreateJvmSuspendFunctionView$default.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list2 = valueParameters;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            KotlinType type4 = ((ValueParameterDescriptor) it2.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
            arrayList3.add(type4);
        }
        KotlinType returnType = orCreateJvmSuspendFunctionView$default.getReturnType();
        Intrinsics.checkNotNull(returnType);
        SimpleType simpleTypeCreateFunctionType$default = FunctionTypesKt.createFunctionType$default(builtIns, empty, type2, arrayList2, arrayList3, null, returnType, false, 128, null);
        if (!descriptor.isSuspend()) {
            return CollectionsKt.listOf((Object[]) new SimpleType[]{getLambda().getDefaultType(), simpleTypeCreateFunctionType$default});
        }
        ArrayList arrayList4 = new ArrayList();
        ReceiverParameterDescriptor extensionReceiverParameter2 = orCreateJvmSuspendFunctionView$default.getExtensionReceiverParameter();
        if (extensionReceiverParameter2 == null || (type = extensionReceiverParameter2.getType()) == null || !CoroutineCallCheckerKt.isRestrictsSuspensionReceiver(type)) {
            if (CoroutineCodegenUtilKt.isSuspendLambdaOrLocalFunction(descriptor)) {
                SimpleType defaultType = getSuspendLambda().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                arrayList4.add(defaultType);
            } else {
                SimpleType defaultType2 = getContinuationImpl().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType2, "getDefaultType(...)");
                arrayList4.add(defaultType2);
            }
        } else if (CoroutineCodegenUtilKt.isSuspendLambdaOrLocalFunction(descriptor)) {
            SimpleType defaultType3 = getRestrictedSuspendLambda().getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType3, "getDefaultType(...)");
            arrayList4.add(defaultType3);
        } else {
            SimpleType defaultType4 = getRestrictedContinuationImpl().getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType4, "getDefaultType(...)");
            arrayList4.add(defaultType4);
        }
        if (CoroutineCodegenUtilKt.isSuspendLambdaOrLocalFunction(descriptor)) {
            arrayList4.add(simpleTypeCreateFunctionType$default);
        }
        return arrayList4;
    }

    /* JADX WARN: Code duplicated, block: B:4:0x001e  */
    public final Collection<KotlinType> getSupertypesForFunctionReference(FunctionDescriptor referencedFunction, AnonymousFunctionDescriptor anonymousFunctionDescriptor, boolean isBound, boolean isAdaptedCallableReference, boolean isSuspendConversion) {
        KotlinType type;
        KotlinType kotlinType;
        ClassDescriptor suspendFunctionInterface;
        ClassDescriptor functionReference;
        Intrinsics.checkNotNullParameter(referencedFunction, "referencedFunction");
        Intrinsics.checkNotNullParameter(anonymousFunctionDescriptor, "anonymousFunctionDescriptor");
        int iComputeExpectedNumberOfReceivers = CallableReferenceUtilKt.computeExpectedNumberOfReceivers(referencedFunction, isBound);
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(referencedFunction);
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        SimpleType defaultType = null;
        if (isBound) {
            kotlinType = null;
        } else {
            ReceiverParameterDescriptor extensionReceiverParameter = referencedFunction.getExtensionReceiverParameter();
            if (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null) {
                ReceiverParameterDescriptor dispatchReceiverParameter = referencedFunction.mo6753getDispatchReceiverParameter();
                if (dispatchReceiverParameter != null) {
                    type = dispatchReceiverParameter.getType();
                } else {
                    kotlinType = null;
                }
            }
            kotlinType = type;
        }
        List<ReceiverParameterDescriptor> contextReceiverParameters = referencedFunction.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType type2 = ((ReceiverParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            arrayList.add(type2);
        }
        ArrayList arrayList2 = arrayList;
        List<ValueParameterDescriptor> valueParameters = anonymousFunctionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List listDrop = CollectionsKt.drop(valueParameters, iComputeExpectedNumberOfReceivers);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDrop, 10));
        Iterator it2 = listDrop.iterator();
        while (it2.hasNext()) {
            KotlinType type3 = ((ValueParameterDescriptor) it2.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            arrayList3.add(type3);
        }
        ArrayList arrayList4 = arrayList3;
        KotlinType returnType = anonymousFunctionDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        SimpleType simpleTypeCreateFunctionType = FunctionTypesKt.createFunctionType(builtIns, empty, kotlinType, arrayList2, arrayList4, null, returnType, referencedFunction.isSuspend() || isSuspendConversion);
        if ((referencedFunction.isSuspend() || isSuspendConversion) && (suspendFunctionInterface = getSuspendFunctionInterface()) != null) {
            defaultType = suspendFunctionInterface.getDefaultType();
        }
        if (!this.generateOptimizedCallableReferenceSuperClasses) {
            functionReference = getFunctionReference();
        } else if (isAdaptedCallableReference || isSuspendConversion) {
            functionReference = getAdaptedFunctionReference();
        } else {
            functionReference = getFunctionReferenceImpl();
        }
        return CollectionsKt.listOfNotNull((Object[]) new SimpleType[]{functionReference.getDefaultType(), simpleTypeCreateFunctionType, defaultType});
    }

    public final KotlinType getSupertypeForPropertyReference(VariableDescriptorWithAccessors descriptor, boolean isMutable, boolean isBound) {
        List<ClassDescriptor> mutablePropertyReferences;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof LocalVariableDescriptor) {
            SimpleType defaultType = (isMutable ? getMutableLocalVariableReference() : getLocalVariableReference()).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            return defaultType;
        }
        int i = ((descriptor.getExtensionReceiverParameter() != null ? 1 : 0) + (descriptor.mo6753getDispatchReceiverParameter() == null ? 0 : 1)) - (isBound ? 1 : 0);
        if (this.generateOptimizedCallableReferenceSuperClasses) {
            mutablePropertyReferences = isMutable ? getMutablePropertyReferenceImpls() : getPropertyReferenceImpls();
        } else {
            mutablePropertyReferences = isMutable ? getMutablePropertyReferences() : getPropertyReferences();
        }
        if (i >= 0) {
            SimpleType defaultType2 = mutablePropertyReferences.get(i).getDefaultType();
            Intrinsics.checkNotNull(defaultType2);
            return defaultType2;
        }
        SimpleType defaultType3 = mutablePropertyReferences.get(0).getDefaultType();
        Intrinsics.checkNotNull(defaultType3);
        return defaultType3;
    }
}
