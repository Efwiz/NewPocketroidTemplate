package org.jetbrains.kotlin.synthetic;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptorImpl;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassConstructorDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import org.jetbrains.kotlin.load.java.sam.JavaSingleAbstractMethodUtils;
import org.jetbrains.kotlin.load.java.sam.SamAdapterDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConstructorUtilsKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScope;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.CompositionTypeSubstitution;
import org.jetbrains.kotlin.types.DescriptorSubstitutor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.UtilsKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: SamAdapterFunctionsScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001KB=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r¢\u0006\u0002\u0010\u000fJ\u0012\u0010&\u001a\u0004\u0018\u00010\u001c2\u0006\u0010'\u001a\u00020\u001bH\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010\u00122\u0006\u0010)\u001a\u00020\u0012H\u0002J\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00120+2\u0006\u0010,\u001a\u00020-H\u0002J\u0016\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00120/2\u0006\u0010,\u001a\u00020-H\u0002J\u0012\u00100\u001a\u0004\u0018\u00010\u00192\u0006\u0010,\u001a\u00020-H\u0002J,\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00160+2\f\u00102\u001a\b\u0012\u0004\u0012\u000204032\b\u00105\u001a\u0004\u0018\u000106H\u0002J\u0012\u00107\u001a\u0004\u0018\u0001082\u0006\u0010'\u001a\u000208H\u0016J\u001c\u00109\u001a\b\u0012\u0004\u0012\u00020\u0012032\f\u0010:\u001a\b\u0012\u0004\u0012\u00020403H\u0016J\u001e\u00109\u001a\b\u0012\u0004\u0012\u00020\u0012032\u0006\u0010;\u001a\u00020-2\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010<\u001a\b\u0012\u0004\u0012\u00020\u0012032\f\u0010=\u001a\b\u0012\u0004\u0012\u00020>03H\u0016J,\u0010<\u001a\b\u0012\u0004\u0012\u00020\u0012032\f\u0010=\u001a\b\u0012\u0004\u0012\u00020>032\u0006\u0010?\u001a\u00020@2\u0006\u00105\u001a\u000206H\u0016J\u001c\u0010A\u001a\b\u0012\u0004\u0012\u00020\u0012032\f\u0010B\u001a\b\u0012\u0004\u0012\u00020403H\u0016J$\u0010A\u001a\b\u0012\u0004\u0012\u00020\u0012032\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u0012032\u0006\u00105\u001a\u000206H\u0016J\u0012\u0010D\u001a\u0004\u0018\u00010\u00192\u0006\u0010,\u001a\u00020\u001fH\u0002J\u0018\u0010E\u001a\u00020F2\u0006\u0010)\u001a\u00020\u00122\u0006\u00105\u001a\u000206H\u0002J\u0018\u0010G\u001a\u00020F2\u0006\u0010,\u001a\u00020-2\u0006\u00105\u001a\u000206H\u0002J\f\u0010H\u001a\u00020\r*\u000206H\u0002J\u0016\u0010I\u001a\u0004\u0018\u00010\u0012*\u00020\u00122\u0006\u0010J\u001a\u00020>H\u0002R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001c0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u001d\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001f0\u001e\u0012\u0004\u0012\u00020 0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010!\u001a\u00020\r*\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\u00020\r*\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010#¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/SamAdapterFunctionsScope;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope$Default;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "samResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "samViaSyntheticScopeDisabled", "", "allowNonSpreadArraysForVarargAfterSam", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;ZZ)V", "extensionForFunction", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "samAdapterForStaticFunction", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaMethodDescriptor;", "Lorg/jetbrains/kotlin/load/java/sam/SamAdapterDescriptor;", "samConstructorForClassifier", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "samConstructorForJavaConstructor", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "samConstructorForTypeAliasConstructor", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", "hasNothingTypeInParameters", "getHasNothingTypeInParameters", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)Z", "shouldGenerateCandidateForVarargAfterSamAndHasVararg", "getShouldGenerateCandidateForVarargAfterSamAndHasVararg", "createJavaSamAdapterConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "extensionForFunctionNotCached", "function", "getAllSamConstructors", "", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getSamAdaptersFromConstructors", "", "getSamConstructor", "getSamFunctions", "functions", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getSyntheticConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "getSyntheticConstructors", "classifierDescriptors", "contributedClassifier", "getSyntheticMemberFunctions", "receiverTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getSyntheticStaticFunctions", "functionDescriptors", "contributedFunctions", "getTypeAliasSamConstructor", "recordSamLookupsForParameters", "", "recordSamLookupsToClassifier", "isCallableReference", "substituteForReceiverType", "receiverType", "SamAdapterExtensionFunctionDescriptorImpl", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamAdapterFunctionsScope extends SyntheticScope.Default {
    private final boolean allowNonSpreadArraysForVarargAfterSam;
    private final DeprecationResolver deprecationResolver;
    private final MemoizedFunctionToNullable<FunctionDescriptor, FunctionDescriptor> extensionForFunction;
    private final LookupTracker lookupTracker;
    private final MemoizedFunctionToNotNull<JavaMethodDescriptor, SamAdapterDescriptor<JavaMethodDescriptor>> samAdapterForStaticFunction;
    private final MemoizedFunctionToNotNull<JavaClassDescriptor, SamConstructorDescriptor> samConstructorForClassifier;
    private final MemoizedFunctionToNotNull<JavaClassConstructorDescriptor, ClassConstructorDescriptor> samConstructorForJavaConstructor;
    private final MemoizedFunctionToNullable<Pair<? extends ClassConstructorDescriptor, ? extends TypeAliasDescriptor>, TypeAliasConstructorDescriptor> samConstructorForTypeAliasConstructor;
    private final SamConversionOracle samConversionOracle;
    private final SamConversionResolver samResolver;
    private final boolean samViaSyntheticScopeDisabled;

    public SamAdapterFunctionsScope(final StorageManager storageManager, SamConversionResolver samResolver, SamConversionOracle samConversionOracle, DeprecationResolver deprecationResolver, LookupTracker lookupTracker, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(samResolver, "samResolver");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        this.samResolver = samResolver;
        this.samConversionOracle = samConversionOracle;
        this.deprecationResolver = deprecationResolver;
        this.lookupTracker = lookupTracker;
        this.samViaSyntheticScopeDisabled = z;
        this.allowNonSpreadArraysForVarargAfterSam = z2;
        this.extensionForFunction = storageManager.createMemoizedFunctionWithNullableValues(new Function1<FunctionDescriptor, FunctionDescriptor>() { // from class: org.jetbrains.kotlin.synthetic.SamAdapterFunctionsScope$extensionForFunction$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FunctionDescriptor invoke(FunctionDescriptor function) {
                Intrinsics.checkNotNullParameter(function, "function");
                return this.this$0.extensionForFunctionNotCached(function);
            }
        });
        this.samAdapterForStaticFunction = storageManager.createMemoizedFunction(new Function1<JavaMethodDescriptor, SamAdapterDescriptor<JavaMethodDescriptor>>() { // from class: org.jetbrains.kotlin.synthetic.SamAdapterFunctionsScope$samAdapterForStaticFunction$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SamAdapterDescriptor<JavaMethodDescriptor> invoke(JavaMethodDescriptor function) {
                Intrinsics.checkNotNullParameter(function, "function");
                SamAdapterDescriptor<JavaMethodDescriptor> samAdapterDescriptorCreateSamAdapterFunction = JavaSingleAbstractMethodUtils.createSamAdapterFunction(function, this.this$0.samResolver, this.this$0.samConversionOracle, this.this$0.allowNonSpreadArraysForVarargAfterSam);
                Intrinsics.checkNotNullExpressionValue(samAdapterDescriptorCreateSamAdapterFunction, "createSamAdapterFunction(...)");
                return samAdapterDescriptorCreateSamAdapterFunction;
            }
        });
        this.samConstructorForClassifier = storageManager.createMemoizedFunction(new Function1<JavaClassDescriptor, SamConstructorDescriptor>() { // from class: org.jetbrains.kotlin.synthetic.SamAdapterFunctionsScope$samConstructorForClassifier$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SamConstructorDescriptor invoke(JavaClassDescriptor classifier) {
                Intrinsics.checkNotNullParameter(classifier, "classifier");
                DeclarationDescriptor containingDeclaration = classifier.getDeclarationDescriptor();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                return SamConstructorUtilsKt.createSamConstructorFunction(containingDeclaration, classifier, this.this$0.samResolver, this.this$0.samConversionOracle);
            }
        });
        this.samConstructorForJavaConstructor = storageManager.createMemoizedFunction(new Function1<JavaClassConstructorDescriptor, ClassConstructorDescriptor>() { // from class: org.jetbrains.kotlin.synthetic.SamAdapterFunctionsScope$samConstructorForJavaConstructor$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassConstructorDescriptor invoke(JavaClassConstructorDescriptor constructor) {
                Intrinsics.checkNotNullParameter(constructor, "constructor");
                SamAdapterDescriptor<JavaClassConstructorDescriptor> samAdapterDescriptorCreateSamAdapterConstructor = JavaSingleAbstractMethodUtils.createSamAdapterConstructor(constructor, this.this$0.samResolver, this.this$0.samConversionOracle, this.this$0.allowNonSpreadArraysForVarargAfterSam);
                Intrinsics.checkNotNull(samAdapterDescriptorCreateSamAdapterConstructor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
                return (ClassConstructorDescriptor) samAdapterDescriptorCreateSamAdapterConstructor;
            }
        });
        this.samConstructorForTypeAliasConstructor = storageManager.createMemoizedFunctionWithNullableValues(new Function1<Pair<? extends ClassConstructorDescriptor, ? extends TypeAliasDescriptor>, TypeAliasConstructorDescriptor>() { // from class: org.jetbrains.kotlin.synthetic.SamAdapterFunctionsScope$samConstructorForTypeAliasConstructor$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final TypeAliasConstructorDescriptor invoke(Pair<? extends ClassConstructorDescriptor, ? extends TypeAliasDescriptor> pair) {
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                ClassConstructorDescriptor classConstructorDescriptorComponent1 = pair.component1();
                return TypeAliasConstructorDescriptorImpl.INSTANCE.createIfAvailable(storageManager, pair.component2(), classConstructorDescriptorComponent1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FunctionDescriptor extensionForFunctionNotCached(FunctionDescriptor function) {
        DescriptorVisibility visibility = function.getVisibilityImpl();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        if (SyntheticExtensionsUtilsKt.isVisibleOutside(visibility) && SyntheticExtensionsUtilsKt.hasJavaOriginInHierarchy(function) && JavaSingleAbstractMethodUtils.isSamAdapterNecessary(function) && function.getReturnType() != null && !DeprecationResolver.isHiddenInResolution$default(this.deprecationResolver, function, null, null, false, false, 30, null)) {
            return SamAdapterExtensionFunctionDescriptorImpl.INSTANCE.create(function, this.samResolver, this.samConversionOracle, this.allowNonSpreadArraysForVarargAfterSam);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        SmartList smartList = null;
        for (KotlinType kotlinType : receiverTypes) {
            for (SimpleFunctionDescriptor simpleFunctionDescriptor : kotlinType.getMemberScope().getContributedFunctions(name2, location)) {
                if (this.samViaSyntheticScopeDisabled) {
                    SimpleFunctionDescriptor simpleFunctionDescriptor2 = simpleFunctionDescriptor;
                    if (getHasNothingTypeInParameters(simpleFunctionDescriptor2) || getShouldGenerateCandidateForVarargAfterSamAndHasVararg(simpleFunctionDescriptor2) || isCallableReference(location)) {
                    }
                }
                MemoizedFunctionToNullable<FunctionDescriptor, FunctionDescriptor> memoizedFunctionToNullable = this.extensionForFunction;
                SimpleFunctionDescriptor original = simpleFunctionDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                FunctionDescriptor functionDescriptorInvoke = memoizedFunctionToNullable.invoke(original);
                FunctionDescriptor functionDescriptorSubstituteForReceiverType = functionDescriptorInvoke != null ? substituteForReceiverType(functionDescriptorInvoke, kotlinType) : null;
                if (functionDescriptorSubstituteForReceiverType != null) {
                    recordSamLookupsForParameters(simpleFunctionDescriptor, location);
                    if (smartList == null) {
                        smartList = new SmartList();
                    }
                    smartList.add(functionDescriptorSubstituteForReceiverType);
                }
            }
        }
        if (smartList == null) {
            return CollectionsKt.emptyList();
        }
        return smartList.size() > 1 ? CollectionsKt.toSet(smartList) : smartList;
    }

    private final boolean isCallableReference(LookupLocation lookupLocation) {
        if (lookupLocation instanceof KotlinLookupLocation) {
            return CallUtilKt.isCallableReference(((KotlinLookupLocation) lookupLocation).getElement());
        }
        return false;
    }

    private final void recordSamLookupsForParameters(FunctionDescriptor function, LookupLocation location) {
        Iterator<ValueParameterDescriptor> it = function.getValueParameters().iterator();
        while (it.hasNext()) {
            ClassifierDescriptor this$0 = it.next().getType().getConstructor().getThis$0();
            if (this$0 != null) {
                recordSamLookupsToClassifier(this$0, location);
            }
        }
    }

    private final boolean getShouldGenerateCandidateForVarargAfterSamAndHasVararg(FunctionDescriptor functionDescriptor) {
        if (this.allowNonSpreadArraysForVarargAfterSam) {
            List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.lastOrNull((List) valueParameters);
            if (valueParameterDescriptor != null && ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
                return true;
            }
        }
        return false;
    }

    private final boolean getHasNothingTypeInParameters(FunctionDescriptor functionDescriptor) {
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            KotlinType type = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (TypeUtilsKt.isNothing(type)) {
                KotlinType type2 = valueParameterDescriptor.getOriginal().getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (!TypeUtilsKt.isNothing(type2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final FunctionDescriptor substituteForReceiverType(FunctionDescriptor functionDescriptor, KotlinType kotlinType) {
        DeclarationDescriptor declarationDescriptor = functionDescriptor.getDeclarationDescriptor();
        ClassDescriptor classDescriptor = declarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor : null;
        if (classDescriptor == null) {
            return null;
        }
        SimpleType defaultType = classDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        KotlinType kotlinTypeFindCorrespondingSupertype$default = UtilsKt.findCorrespondingSupertype$default(kotlinType, defaultType, null, 4, null);
        if (kotlinTypeFindCorrespondingSupertype$default == null) {
            return null;
        }
        return functionDescriptor.substitute(CapturedTypeConstructorKt.wrapWithCapturingSubstitution(TypeConstructorSubstitution.INSTANCE.create(kotlinTypeFindCorrespondingSupertype$default), true).buildSubstitutor());
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes) {
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (KotlinType kotlinType : receiverTypes) {
            Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(kotlinType.getMemberScope(), DescriptorKindFilter.FUNCTIONS, null, 2, null);
            ArrayList arrayList = new ArrayList();
            for (Object obj : contributedDescriptors$default) {
                if (obj instanceof FunctionDescriptor) {
                    arrayList.add(obj);
                }
            }
            ArrayList<FunctionDescriptor> arrayList2 = arrayList;
            if (this.samViaSyntheticScopeDisabled) {
                ArrayList arrayList3 = new ArrayList();
                for (Object obj2 : arrayList2) {
                    if (getShouldGenerateCandidateForVarargAfterSamAndHasVararg((FunctionDescriptor) obj2)) {
                        arrayList3.add(obj2);
                    }
                }
                arrayList2 = arrayList3;
            }
            ArrayList arrayList4 = new ArrayList();
            for (FunctionDescriptor functionDescriptor : arrayList2) {
                MemoizedFunctionToNullable<FunctionDescriptor, FunctionDescriptor> memoizedFunctionToNullable = this.extensionForFunction;
                FunctionDescriptor original = functionDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                FunctionDescriptor functionDescriptorInvoke = memoizedFunctionToNullable.invoke(original);
                FunctionDescriptor functionDescriptorSubstituteForReceiverType = functionDescriptorInvoke != null ? substituteForReceiverType(functionDescriptorInvoke, kotlinType) : null;
                if (functionDescriptorSubstituteForReceiverType != null) {
                    arrayList4.add(functionDescriptorSubstituteForReceiverType);
                }
            }
            CollectionsKt.addAll(linkedHashSet, arrayList4);
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticStaticFunctions(Collection<? extends FunctionDescriptor> contributedFunctions, LookupLocation location) {
        Intrinsics.checkNotNullParameter(contributedFunctions, "contributedFunctions");
        Intrinsics.checkNotNullParameter(location, "location");
        return getSamFunctions(contributedFunctions, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticConstructors(ClassifierDescriptor contributedClassifier, LookupLocation location) {
        Intrinsics.checkNotNullParameter(contributedClassifier, "contributedClassifier");
        Intrinsics.checkNotNullParameter(location, "location");
        recordSamLookupsToClassifier(contributedClassifier, location);
        return getAllSamConstructors(contributedClassifier);
    }

    private final void recordSamLookupsToClassifier(ClassifierDescriptor classifier, LookupLocation location) {
        if (classifier instanceof JavaClassDescriptor) {
            JavaClassDescriptor javaClassDescriptor = (JavaClassDescriptor) classifier;
            if (javaClassDescriptor.getKind() == ClassKind.INTERFACE && JavaSingleAbstractMethodUtils.isSamType(javaClassDescriptor.getDefaultType())) {
                org.jetbrains.kotlin.incremental.UtilsKt.record(this.lookupTracker, location, (ClassDescriptor) classifier, SamConversionResolverKt.getSAM_LOOKUP_NAME());
            }
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticStaticFunctions(Collection<? extends DeclarationDescriptor> functionDescriptors) {
        Intrinsics.checkNotNullParameter(functionDescriptors, "functionDescriptors");
        return getSamFunctions(functionDescriptors, null);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticConstructors(Collection<? extends DeclarationDescriptor> classifierDescriptors) {
        Intrinsics.checkNotNullParameter(classifierDescriptors, "classifierDescriptors");
        ArrayList arrayList = new ArrayList();
        for (Object obj : classifierDescriptors) {
            if (obj instanceof ClassifierDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList2, getAllSamConstructors((ClassifierDescriptor) it.next()));
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public ConstructorDescriptor getSyntheticConstructor(ConstructorDescriptor constructor) {
        ClassConstructorDescriptor classConstructorDescriptorCreateJavaSamAdapterConstructor;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        if (this.samViaSyntheticScopeDisabled && !getShouldGenerateCandidateForVarargAfterSamAndHasVararg(constructor)) {
            return null;
        }
        if (constructor instanceof JavaClassConstructorDescriptor) {
            return createJavaSamAdapterConstructor((JavaClassConstructorDescriptor) constructor);
        }
        if (!(constructor instanceof TypeAliasConstructorDescriptor)) {
            return null;
        }
        TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = (TypeAliasConstructorDescriptor) constructor;
        ClassConstructorDescriptor underlyingConstructorDescriptor = typeAliasConstructorDescriptor.getUnderlyingConstructorDescriptor();
        if ((underlyingConstructorDescriptor instanceof JavaClassConstructorDescriptor) && (classConstructorDescriptorCreateJavaSamAdapterConstructor = createJavaSamAdapterConstructor((JavaClassConstructorDescriptor) underlyingConstructorDescriptor)) != null) {
            return this.samConstructorForTypeAliasConstructor.invoke(new Pair(classConstructorDescriptorCreateJavaSamAdapterConstructor, typeAliasConstructorDescriptor.getTypeAliasDescriptor()));
        }
        return null;
    }

    private final ClassConstructorDescriptor createJavaSamAdapterConstructor(JavaClassConstructorDescriptor constructor) {
        if (JavaSingleAbstractMethodUtils.isSamAdapterNecessary(constructor)) {
            return this.samConstructorForJavaConstructor.invoke(constructor);
        }
        return null;
    }

    private final List<SamAdapterDescriptor<JavaMethodDescriptor>> getSamFunctions(Collection<? extends DeclarationDescriptor> functions, LookupLocation location) {
        ArrayList arrayList = new ArrayList();
        for (DeclarationDescriptor declarationDescriptor : functions) {
            SamAdapterDescriptor<JavaMethodDescriptor> samAdapterDescriptorInvoke = null;
            if ((declarationDescriptor instanceof JavaMethodDescriptor) && ((JavaMethodDescriptor) declarationDescriptor).mo6753getDispatchReceiverParameter() == null) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
                if (JavaSingleAbstractMethodUtils.isSamAdapterNecessary(functionDescriptor) && (!this.samViaSyntheticScopeDisabled || getShouldGenerateCandidateForVarargAfterSamAndHasVararg(functionDescriptor))) {
                    if (location != null) {
                        recordSamLookupsForParameters(functionDescriptor, location);
                    }
                    samAdapterDescriptorInvoke = this.samAdapterForStaticFunction.invoke(declarationDescriptor);
                }
            }
            if (samAdapterDescriptorInvoke != null) {
                arrayList.add(samAdapterDescriptorInvoke);
            }
        }
        return arrayList;
    }

    private final List<FunctionDescriptor> getAllSamConstructors(ClassifierDescriptor classifier) {
        List<FunctionDescriptor> samAdaptersFromConstructors = getSamAdaptersFromConstructors(classifier);
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(samAdaptersFromConstructors, getSamConstructor(classifier));
        return samAdaptersFromConstructors;
    }

    private final List<FunctionDescriptor> getSamAdaptersFromConstructors(ClassifierDescriptor classifier) {
        if (!(classifier instanceof JavaClassDescriptor)) {
            return new SmartList();
        }
        SmartList smartList = new SmartList();
        for (ClassConstructorDescriptor classConstructorDescriptor : ((JavaClassDescriptor) classifier).getConstructors()) {
            Intrinsics.checkNotNull(classConstructorDescriptor);
            ConstructorDescriptor syntheticConstructor = getSyntheticConstructor(classConstructorDescriptor);
            if (syntheticConstructor != null) {
                smartList.add(syntheticConstructor);
            }
        }
        return smartList;
    }

    private final SamConstructorDescriptor getSamConstructor(ClassifierDescriptor classifier) {
        if (classifier instanceof TypeAliasDescriptor) {
            return getTypeAliasSamConstructor((TypeAliasDescriptor) classifier);
        }
        if (!(classifier instanceof LazyJavaClassDescriptor) || ((LazyJavaClassDescriptor) classifier).getDefaultFunctionTypeForSamInterface() == null) {
            return null;
        }
        return this.samConstructorForClassifier.invoke(classifier);
    }

    private final SamConstructorDescriptor getTypeAliasSamConstructor(TypeAliasDescriptor classifier) {
        ClassDescriptor classDescriptor = classifier.getClassDescriptor();
        if (classDescriptor == null || !(classDescriptor instanceof LazyJavaClassDescriptor) || ((LazyJavaClassDescriptor) classDescriptor).getDefaultFunctionTypeForSamInterface() == null) {
            return null;
        }
        return SamConstructorUtilsKt.createTypeAliasSamConstructorFunction(classifier, this.samConstructorForClassifier.invoke(classDescriptor), this.samResolver, this.samConversionOracle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: SamAdapterFunctionsScope.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 -2\u00020\u00012\u00020\u0002:\u0001-B7\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ<\u0010 \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\"\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u0016\u0010#\u001a\u0004\u0018\u00010\u00112\n\u0010$\u001a\u00060%R\u00020&H\u0014J\b\u0010'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020(H\u0016J\u0014\u0010*\u001a\u00060%R\u00020&2\u0006\u0010+\u001a\u00020,H\u0014R+\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118V@RX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R'\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001d¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/synthetic/SamAdapterFunctionsScope$SamAdapterExtensionFunctionDescriptorImpl;", "Lorg/jetbrains/kotlin/synthetic/SamAdapterExtensionFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "original", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "<set-?>", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "baseDescriptorForSynthetic", "getBaseDescriptorForSynthetic", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "setBaseDescriptorForSynthetic", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "baseDescriptorForSynthetic$delegate", "Lkotlin/properties/ReadWriteProperty;", "fromSourceFunctionTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getFromSourceFunctionTypeParameters", "()Ljava/util/Map;", "fromSourceFunctionTypeParameters$delegate", "Lkotlin/Lazy;", "createSubstitutedCopy", "newOwner", "newName", "doSubstitute", "configuration", "Lorg/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration;", "Lorg/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl;", "hasStableParameterNames", "", "hasSynthesizedParameterNames", "newCopyBuilder", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class SamAdapterExtensionFunctionDescriptorImpl extends SimpleFunctionDescriptorImpl implements SamAdapterExtensionFunctionDescriptor {
        static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(SamAdapterExtensionFunctionDescriptorImpl.class), "baseDescriptorForSynthetic", "getBaseDescriptorForSynthetic()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;"))};

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        /* JADX INFO: renamed from: baseDescriptorForSynthetic$delegate, reason: from kotlin metadata */
        private final ReadWriteProperty baseDescriptorForSynthetic;

        /* JADX INFO: renamed from: fromSourceFunctionTypeParameters$delegate, reason: from kotlin metadata */
        private final Lazy fromSourceFunctionTypeParameters;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SamAdapterExtensionFunctionDescriptorImpl(DeclarationDescriptor containingDeclaration, SimpleFunctionDescriptor simpleFunctionDescriptor, Annotations annotations, Name name2, CallableMemberDescriptor.Kind kind, SourceElement source) {
            super(containingDeclaration, simpleFunctionDescriptor, annotations, name2, kind, source);
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(source, "source");
            this.baseDescriptorForSynthetic = Delegates.INSTANCE.notNull();
            this.fromSourceFunctionTypeParameters = LazyKt.lazy(new Function0<Map<TypeParameterDescriptor, ? extends TypeParameterDescriptor>>() { // from class: org.jetbrains.kotlin.synthetic.SamAdapterFunctionsScope$SamAdapterExtensionFunctionDescriptorImpl$fromSourceFunctionTypeParameters$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Map<TypeParameterDescriptor, ? extends TypeParameterDescriptor> invoke() {
                    List<TypeParameterDescriptor> typeParameters = this.this$0.getSamInterface().getTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                    List<TypeParameterDescriptor> typeParameters2 = this.this$0.getTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
                    return MapsKt.toMap(CollectionsKt.zip(typeParameters, typeParameters2));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBaseDescriptorForSynthetic(FunctionDescriptor functionDescriptor) {
            this.baseDescriptorForSynthetic.setValue(this, $$delegatedProperties[0], functionDescriptor);
        }

        @Override // org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor
        /* JADX INFO: renamed from: getBaseDescriptorForSynthetic */
        public FunctionDescriptor getSamInterface() {
            return (FunctionDescriptor) this.baseDescriptorForSynthetic.getValue(this, $$delegatedProperties[0]);
        }

        private final Map<TypeParameterDescriptor, TypeParameterDescriptor> getFromSourceFunctionTypeParameters() {
            return (Map) this.fromSourceFunctionTypeParameters.getValue();
        }

        /* JADX INFO: compiled from: SamAdapterFunctionsScope.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/SamAdapterFunctionsScope$SamAdapterExtensionFunctionDescriptorImpl$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/synthetic/SamAdapterFunctionsScope$SamAdapterExtensionFunctionDescriptorImpl;", "sourceFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "samResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "allowNonSpreadArraysForVarargAfterSam", "", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final SamAdapterExtensionFunctionDescriptorImpl create(FunctionDescriptor sourceFunction, SamConversionResolver samResolver, SamConversionOracle samConversionOracle, boolean allowNonSpreadArraysForVarargAfterSam) {
                Intrinsics.checkNotNullParameter(sourceFunction, "sourceFunction");
                Intrinsics.checkNotNullParameter(samResolver, "samResolver");
                Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
                DeclarationDescriptor declarationDescriptor = sourceFunction.getDeclarationDescriptor();
                Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "getContainingDeclaration(...)");
                Annotations annotations = sourceFunction.getAnnotations();
                Name name2 = sourceFunction.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                CallableMemberDescriptor.Kind kind = CallableMemberDescriptor.Kind.SYNTHESIZED;
                SourceElement source = sourceFunction.getOriginal().getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                SamAdapterExtensionFunctionDescriptorImpl samAdapterExtensionFunctionDescriptorImpl = new SamAdapterExtensionFunctionDescriptorImpl(declarationDescriptor, null, annotations, name2, kind, source);
                samAdapterExtensionFunctionDescriptorImpl.setBaseDescriptorForSynthetic(sourceFunction);
                List<TypeParameterDescriptor> typeParameters = sourceFunction.getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                List mutableList = CollectionsKt.toMutableList((Collection) typeParameters);
                DeclarationDescriptor declarationDescriptor2 = sourceFunction.getDeclarationDescriptor();
                Intrinsics.checkNotNull(declarationDescriptor2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor2;
                ArrayList arrayList = new ArrayList(mutableList.size());
                TypeSubstitutor typeSubstitutorSubstituteTypeParameters = DescriptorSubstitutor.substituteTypeParameters(mutableList, TypeSubstitution.EMPTY, samAdapterExtensionFunctionDescriptorImpl, arrayList);
                Intrinsics.checkNotNullExpressionValue(typeSubstitutorSubstituteTypeParameters, "substituteTypeParameters(...)");
                KotlinType returnType = sourceFunction.getReturnType();
                Intrinsics.checkNotNull(returnType);
                KotlinType kotlinTypeSafeSubstitute = typeSubstitutorSubstituteTypeParameters.safeSubstitute(returnType, Variance.INVARIANT);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
                samAdapterExtensionFunctionDescriptorImpl.initialize((ReceiverParameterDescriptor) null, classDescriptor.getThisAsReceiverParameter(), CollectionsKt.emptyList(), (List) arrayList, (List) JavaSingleAbstractMethodUtils.createValueParametersForSamAdapter(sourceFunction, samAdapterExtensionFunctionDescriptorImpl, typeSubstitutorSubstituteTypeParameters, samResolver, samConversionOracle, allowNonSpreadArraysForVarargAfterSam), kotlinTypeSafeSubstitute, Modality.FINAL, SyntheticExtensionsUtilsKt.syntheticVisibility(sourceFunction, false));
                samAdapterExtensionFunctionDescriptorImpl.setOperator(sourceFunction.isOperator());
                samAdapterExtensionFunctionDescriptorImpl.setInfix(sourceFunction.isInfix());
                return samAdapterExtensionFunctionDescriptorImpl;
            }
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableDescriptor
        public boolean hasStableParameterNames() {
            return getSamInterface().hasStableParameterNames();
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableDescriptor
        public boolean hasSynthesizedParameterNames() {
            return getSamInterface().hasSynthesizedParameterNames();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
        public SamAdapterExtensionFunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor newOwner, FunctionDescriptor original, CallableMemberDescriptor.Kind kind, Name newName, Annotations annotations, SourceElement source) {
            Intrinsics.checkNotNullParameter(newOwner, "newOwner");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            Intrinsics.checkNotNullParameter(source, "source");
            DeclarationDescriptor containingDeclaration = getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) original;
            if (newName == null) {
                newName = getShortName();
                Intrinsics.checkNotNullExpressionValue(newName, "getName(...)");
            }
            SamAdapterExtensionFunctionDescriptorImpl samAdapterExtensionFunctionDescriptorImpl = new SamAdapterExtensionFunctionDescriptorImpl(containingDeclaration, simpleFunctionDescriptor, annotations, newName, kind, source);
            samAdapterExtensionFunctionDescriptorImpl.setBaseDescriptorForSynthetic(getSamInterface());
            return samAdapterExtensionFunctionDescriptorImpl;
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
        protected FunctionDescriptorImpl.CopyConfiguration newCopyBuilder(TypeSubstitutor substitutor) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            FunctionDescriptorImpl.CopyConfiguration original = super.newCopyBuilder(substitutor).setOriginal((CallableMemberDescriptor) getOriginal());
            Intrinsics.checkNotNullExpressionValue(original, "setOriginal(...)");
            return original;
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
        protected FunctionDescriptor doSubstitute(FunctionDescriptorImpl.CopyConfiguration configuration) {
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            SamAdapterExtensionFunctionDescriptorImpl samAdapterExtensionFunctionDescriptorImpl = (SamAdapterExtensionFunctionDescriptorImpl) super.doSubstitute(configuration);
            if (samAdapterExtensionFunctionDescriptorImpl == null) {
                return null;
            }
            FunctionDescriptor original = configuration.getOriginal();
            if (original == null) {
                throw new UnsupportedOperationException("doSubstitute with no original should not be called for synthetic extension " + this);
            }
            SamAdapterExtensionFunctionDescriptorImpl samAdapterExtensionFunctionDescriptorImpl2 = (SamAdapterExtensionFunctionDescriptorImpl) original;
            Intrinsics.areEqual(samAdapterExtensionFunctionDescriptorImpl2.getOriginal(), original);
            TypeSubstitution substitution = configuration.getSubstitution();
            Intrinsics.checkNotNullExpressionValue(substitution, "getSubstitution(...)");
            FunctionDescriptor functionDescriptorSubstitute = samAdapterExtensionFunctionDescriptorImpl2.getSamInterface().substitute(new CompositionTypeSubstitution(substitution, getFromSourceFunctionTypeParameters()).buildSubstitutor());
            if (functionDescriptorSubstitute == null) {
                return null;
            }
            samAdapterExtensionFunctionDescriptorImpl.setBaseDescriptorForSynthetic(functionDescriptorSubstitute);
            return samAdapterExtensionFunctionDescriptorImpl;
        }
    }
}
