package org.jetbrains.kotlin.resolve.calls.results;

import gnu.trove.THashSet;
import gnu.trove.TObjectHashingStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.util.CancellationChecker;

/* JADX INFO: compiled from: OverloadingConflictResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0007\n\u0002\b\b\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010#\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0002\u001d$\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B±\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00140\u000e\u0012\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000e\u0012\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00170\u000e\u0012\u0014\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u000e\u0012\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bJ*\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0017J4\u00102\u001a\u00020\u00172\f\u00103\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\f\u00104\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u00101\u001a\u00020\u00172\u0006\u00105\u001a\u00020\u0017H\u0002J\u001a\u00106\u001a\b\u0012\u0004\u0012\u00028\u00000,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.J-\u00107\u001a\u0004\u0018\u00018\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000,2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0017H\u0002¢\u0006\u0002\u00108J/\u00109\u001a\u0004\u0018\u00018\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000,2\u0006\u00101\u001a\u00020\u00172\b\b\u0002\u00105\u001a\u00020\u0017H\u0002¢\u0006\u0002\u0010:J\u001e\u0010;\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.H\u0002JD\u0010<\u001a\u00020\u0017\"\u0004\b\u0001\u0010\u00012\u0006\u0010=\u001a\u0002H\u00012\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H\u00010.2\u0018\u0010>\u001a\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00170?H\u0082\b¢\u0006\u0002\u0010@JD\u0010A\u001a\u00020\u0017\"\u0004\b\u0001\u0010\u00012\u0006\u0010=\u001a\u0002H\u00012\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H\u00010.2\u0018\u0010>\u001a\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00170?H\u0082\b¢\u0006\u0002\u0010@J4\u0010B\u001a\u00020\u00172\f\u00103\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\f\u00104\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\u0006\u00101\u001a\u00020\u00172\u0006\u00105\u001a\u00020\u0017H\u0002J\u0018\u0010C\u001a\u00020\u00172\u0006\u0010D\u001a\u00020\u000f2\u0006\u0010E\u001a\u00020\u000fH\u0002J\u0018\u0010F\u001a\u00020\u00172\u0006\u0010D\u001a\u00020\u000f2\u0006\u0010E\u001a\u00020\u000fH\u0002J$\u0010G\u001a\u00020\u00172\f\u00103\u001a\b\u0012\u0004\u0012\u00028\u00000\u00142\f\u00104\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J\u0016\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000I2\u0006\u0010J\u001a\u00020KH\u0002J\u001f\u0010L\u001a\u0004\u0018\u00010\u00172\u0006\u0010M\u001a\u00020\u000f2\u0006\u0010N\u001a\u00020\u000fH\u0002¢\u0006\u0002\u0010OJ\u001c\u0010P\u001a\b\u0012\u0004\u0012\u00028\u00000,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.H\u0002J\u0012\u0010Q\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J\u0012\u0010R\u001a\u00020S*\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0002J>\u0010T\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0001\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010.2\u0018\u0010U\u001a\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00170?H\u0082\b¢\u0006\u0002\u0010VJ\u001a\u0010W\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010,*\b\u0012\u0004\u0012\u00028\u00000.H\u0002R\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00140\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00170\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001f\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$X\u0082\u0004¢\u0006\u0004\n\u0002\u0010%R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010&\u001a\u00020\u000f*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0018\u0010)\u001a\u00020\u000f*\u00028\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010*¨\u0006X"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/OverloadingConflictResolver;", "C", "", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "platformOverloadsSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/PlatformOverloadsSpecificityComparator;", "cancellationChecker", "Lorg/jetbrains/kotlin/util/CancellationChecker;", "getResultingDescriptor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "createEmptyConstraintSystem", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "createFlatSignature", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "getVariableCandidates", "isFromSources", "", "hasSAMConversion", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/resolve/calls/results/PlatformOverloadsSpecificityComparator;Lorg/jetbrains/kotlin/util/CancellationChecker;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "SpecificityComparisonWithNumerics", "org/jetbrains/kotlin/resolve/calls/results/OverloadingConflictResolver$SpecificityComparisonWithNumerics$1", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadingConflictResolver$SpecificityComparisonWithNumerics$1;", "isTypeRefinementEnabled", "()Z", "isTypeRefinementEnabled$delegate", "Lkotlin/Lazy;", "resolvedCallHashingStrategy", "org/jetbrains/kotlin/resolve/calls/results/OverloadingConflictResolver$resolvedCallHashingStrategy$1", "Lorg/jetbrains/kotlin/resolve/calls/results/OverloadingConflictResolver$resolvedCallHashingStrategy$1;", "originalIfTypeRefinementEnabled", "getOriginalIfTypeRefinementEnabled", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resultingDescriptor", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "chooseMaximallySpecificCandidates", "", "candidates", "", "checkArgumentsMode", "Lorg/jetbrains/kotlin/resolve/calls/context/CheckArgumentTypesMode;", "discriminateGenerics", "compareCallsByUsedArguments", "call1", "call2", "useOriginalSamTypes", "filterOutEquivalentCalls", "findMaximallySpecific", "(Ljava/util/Set;Lorg/jetbrains/kotlin/resolve/calls/context/CheckArgumentTypesMode;Z)Ljava/lang/Object;", "findMaximallySpecificCall", "(Ljava/util/Set;ZZ)Ljava/lang/Object;", "findMaximallySpecificVariableAsFunctionCalls", "isDefinitelyMostSpecific", "candidate", "isNotLessSpecific", "Lkotlin/Function2;", "(Ljava/lang/Object;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Z", "isMostSpecific", "isNotLessSpecificCallWithArgumentMapping", "isNotLessSpecificCallableReference", "f", "g", "isNotLessSpecificCallableReferenceDescriptor", "isOfNotLessSpecificShape", "newResolvedCallSet", "", "expectedSize", "", "tryCompareDescriptorsFromScripts", JvmAnnotationNames.METADATA_DATA_FIELD_NAME, JvmAnnotationNames.METADATA_STRINGS_FIELD_NAME, "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Ljava/lang/Boolean;", "uniquifyCandidatesSet", "candidateDescriptor", "descriptorVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "exactMaxWith", "isNotWorse", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "setIfOneOrEmpty", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class OverloadingConflictResolver<C> {
    private final OverloadingConflictResolver$SpecificityComparisonWithNumerics$1<C> SpecificityComparisonWithNumerics;
    private final KotlinBuiltIns builtIns;
    private final CancellationChecker cancellationChecker;
    private final Function0<SimpleConstraintSystem> createEmptyConstraintSystem;
    private final Function1<C, FlatSignature<C>> createFlatSignature;
    private final Function1<C, CallableDescriptor> getResultingDescriptor;
    private final Function1<C, C> getVariableCandidates;
    private final Function1<C, Boolean> hasSAMConversion;
    private final Function1<CallableDescriptor, Boolean> isFromSources;

    /* JADX INFO: renamed from: isTypeRefinementEnabled$delegate, reason: from kotlin metadata */
    private final Lazy isTypeRefinementEnabled;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final ModuleDescriptor module;
    private final PlatformOverloadsSpecificityComparator platformOverloadsSpecificityComparator;
    private final OverloadingConflictResolver$resolvedCallHashingStrategy$1<C> resolvedCallHashingStrategy;
    private final TypeSpecificityComparator specificityComparator;

    /* JADX INFO: compiled from: OverloadingConflictResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CheckArgumentTypesMode.values().length];
            try {
                iArr[CheckArgumentTypesMode.CHECK_CALLABLE_TYPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public OverloadingConflictResolver(KotlinBuiltIns builtIns, ModuleDescriptor module, TypeSpecificityComparator specificityComparator, PlatformOverloadsSpecificityComparator platformOverloadsSpecificityComparator, CancellationChecker cancellationChecker, Function1<? super C, ? extends CallableDescriptor> getResultingDescriptor, Function0<? extends SimpleConstraintSystem> createEmptyConstraintSystem, Function1<? super C, ? extends FlatSignature<? extends C>> createFlatSignature, Function1<? super C, ? extends C> getVariableCandidates, Function1<? super CallableDescriptor, Boolean> isFromSources, Function1<? super C, Boolean> function1, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        Intrinsics.checkNotNullParameter(platformOverloadsSpecificityComparator, "platformOverloadsSpecificityComparator");
        Intrinsics.checkNotNullParameter(cancellationChecker, "cancellationChecker");
        Intrinsics.checkNotNullParameter(getResultingDescriptor, "getResultingDescriptor");
        Intrinsics.checkNotNullParameter(createEmptyConstraintSystem, "createEmptyConstraintSystem");
        Intrinsics.checkNotNullParameter(createFlatSignature, "createFlatSignature");
        Intrinsics.checkNotNullParameter(getVariableCandidates, "getVariableCandidates");
        Intrinsics.checkNotNullParameter(isFromSources, "isFromSources");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.builtIns = builtIns;
        this.module = module;
        this.specificityComparator = specificityComparator;
        this.platformOverloadsSpecificityComparator = platformOverloadsSpecificityComparator;
        this.cancellationChecker = cancellationChecker;
        this.getResultingDescriptor = getResultingDescriptor;
        this.createEmptyConstraintSystem = createEmptyConstraintSystem;
        this.createFlatSignature = createFlatSignature;
        this.getVariableCandidates = getVariableCandidates;
        this.isFromSources = isFromSources;
        this.hasSAMConversion = function1;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.isTypeRefinementEnabled = LazyKt.lazy(new Function0<Boolean>(this) { // from class: org.jetbrains.kotlin.resolve.calls.results.OverloadingConflictResolver.isTypeRefinementEnabled.2
            final /* synthetic */ OverloadingConflictResolver<C> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(DescriptorUtilsKt.isTypeRefinementEnabled(((OverloadingConflictResolver) this.this$0).module));
            }
        });
        this.resolvedCallHashingStrategy = new TObjectHashingStrategy<C>(this) { // from class: org.jetbrains.kotlin.resolve.calls.results.OverloadingConflictResolver$resolvedCallHashingStrategy$1
            final /* synthetic */ OverloadingConflictResolver<C> this$0;

            {
                this.this$0 = this;
            }

            @Override // gnu.trove.TObjectHashingStrategy, gnu.trove.Equality
            public boolean equals(C call1, C call2) {
                if (call1 != null && call2 != null) {
                    return Intrinsics.areEqual(this.this$0.getResultingDescriptor(call1), this.this$0.getResultingDescriptor(call2));
                }
                return Intrinsics.areEqual(call1, call2);
            }

            @Override // gnu.trove.TObjectHashingStrategy
            public int computeHashCode(C call) {
                CallableDescriptor resultingDescriptor;
                if (call == null || (resultingDescriptor = this.this$0.getResultingDescriptor(call)) == null) {
                    return 0;
                }
                return resultingDescriptor.hashCode();
            }
        };
        this.SpecificityComparisonWithNumerics = (OverloadingConflictResolver$SpecificityComparisonWithNumerics$1<C>) new SpecificityComparisonCallbacks(this) { // from class: org.jetbrains.kotlin.resolve.calls.results.OverloadingConflictResolver$SpecificityComparisonWithNumerics$1
            final /* synthetic */ OverloadingConflictResolver<C> this$0;

            {
                this.this$0 = this;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.results.SpecificityComparisonCallbacks
            public boolean isNonSubtypeNotLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general) {
                SimpleType defaultType;
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies;
                SimpleType defaultType2;
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies2;
                SimpleType defaultType3;
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies3;
                SimpleType defaultType4;
                Intrinsics.checkNotNullParameter(specific, "specific");
                Intrinsics.checkNotNullParameter(general, "general");
                TypeSystemContextKt.requireOrDescribe(specific instanceof KotlinType, specific);
                TypeSystemContextKt.requireOrDescribe(general instanceof KotlinType, general);
                SimpleType doubleType = ((OverloadingConflictResolver) this.this$0).builtIns.getDoubleType();
                Intrinsics.checkNotNullExpressionValue(doubleType, "getDoubleType(...)");
                SimpleType floatType = ((OverloadingConflictResolver) this.this$0).builtIns.getFloatType();
                Intrinsics.checkNotNullExpressionValue(floatType, "getFloatType(...)");
                KotlinType kotlinType = (KotlinType) specific;
                boolean zIsUnsignedType = UnsignedTypes.isUnsignedType(kotlinType);
                KotlinType kotlinType2 = (KotlinType) general;
                boolean zIsUnsignedType2 = UnsignedTypes.isUnsignedType(kotlinType2);
                if (zIsUnsignedType && zIsUnsignedType2) {
                    ClassDescriptor classDescriptorFindClassAcrossModuleDependencies4 = FindClassInModuleKt.findClassAcrossModuleDependencies(((OverloadingConflictResolver) this.this$0).module, StandardNames.FqNames.uLong);
                    if (classDescriptorFindClassAcrossModuleDependencies4 == null || (defaultType = classDescriptorFindClassAcrossModuleDependencies4.getDefaultType()) == null || (classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(((OverloadingConflictResolver) this.this$0).module, StandardNames.FqNames.uInt)) == null || (defaultType2 = classDescriptorFindClassAcrossModuleDependencies.getDefaultType()) == null || (classDescriptorFindClassAcrossModuleDependencies2 = FindClassInModuleKt.findClassAcrossModuleDependencies(((OverloadingConflictResolver) this.this$0).module, StandardNames.FqNames.uByte)) == null || (defaultType3 = classDescriptorFindClassAcrossModuleDependencies2.getDefaultType()) == null || (classDescriptorFindClassAcrossModuleDependencies3 = FindClassInModuleKt.findClassAcrossModuleDependencies(((OverloadingConflictResolver) this.this$0).module, StandardNames.FqNames.uShort)) == null || (defaultType4 = classDescriptorFindClassAcrossModuleDependencies3.getDefaultType()) == null) {
                        return false;
                    }
                    return isNonSubtypeNotLessSpecific(kotlinType, kotlinType2, doubleType, floatType, defaultType, defaultType2, defaultType3, defaultType4);
                }
                if (!zIsUnsignedType && zIsUnsignedType2) {
                    return true;
                }
                SimpleType longType = ((OverloadingConflictResolver) this.this$0).builtIns.getLongType();
                Intrinsics.checkNotNullExpressionValue(longType, "getLongType(...)");
                SimpleType intType = ((OverloadingConflictResolver) this.this$0).builtIns.getIntType();
                Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
                SimpleType byteType = ((OverloadingConflictResolver) this.this$0).builtIns.getByteType();
                Intrinsics.checkNotNullExpressionValue(byteType, "getByteType(...)");
                SimpleType shortType = ((OverloadingConflictResolver) this.this$0).builtIns.getShortType();
                Intrinsics.checkNotNullExpressionValue(shortType, "getShortType(...)");
                return isNonSubtypeNotLessSpecific(kotlinType, kotlinType2, doubleType, floatType, longType, intType, byteType, shortType);
            }

            private final boolean isNonSubtypeNotLessSpecific(KotlinType specific, KotlinType general, KotlinType _double, KotlinType _float, KotlinType _long, KotlinType _int, KotlinType _byte, KotlinType _short) {
                if (TypeUtils.equalTypes(specific, _double) && TypeUtils.equalTypes(general, _float)) {
                    return true;
                }
                if (TypeUtils.equalTypes(specific, _int)) {
                    return TypeUtils.equalTypes(general, _long) || TypeUtils.equalTypes(general, _byte) || TypeUtils.equalTypes(general, _short);
                }
                return TypeUtils.equalTypes(specific, _short) && TypeUtils.equalTypes(general, _byte);
            }
        };
    }

    private final boolean isTypeRefinementEnabled() {
        return ((Boolean) this.isTypeRefinementEnabled.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallableDescriptor getResultingDescriptor(C c) {
        return this.getResultingDescriptor.invoke(c);
    }

    public final Set<C> chooseMaximallySpecificCandidates(Collection<? extends C> candidates, CheckArgumentTypesMode checkArgumentsMode, boolean discriminateGenerics) {
        C cFindMaximallySpecific;
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(checkArgumentsMode, "checkArgumentsMode");
        Set<C> ifOneOrEmpty = setIfOneOrEmpty(candidates);
        if (ifOneOrEmpty != null) {
            return ifOneOrEmpty;
        }
        if (this.getVariableCandidates.invoke((C) CollectionsKt.first(candidates)) != null) {
            Collection<? extends C> collectionFindMaximallySpecificVariableAsFunctionCalls = findMaximallySpecificVariableAsFunctionCalls(candidates);
            if (collectionFindMaximallySpecificVariableAsFunctionCalls == null) {
                return new LinkedHashSet(candidates);
            }
            candidates = collectionFindMaximallySpecificVariableAsFunctionCalls;
        }
        Set<C> setFilterOverrides = OverridingUtil.filterOverrides(filterOutEquivalentCalls(candidates), isTypeRefinementEnabled(), new OverloadingConflictResolver$chooseMaximallySpecificCandidates$noOverrides$1(this.cancellationChecker), new Function2<C, C, Pair<? extends CallableDescriptor, ? extends CallableDescriptor>>(this) { // from class: org.jetbrains.kotlin.resolve.calls.results.OverloadingConflictResolver$chooseMaximallySpecificCandidates$noOverrides$2
            final /* synthetic */ OverloadingConflictResolver<C> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Pair<? extends CallableDescriptor, ? extends CallableDescriptor> invoke(C c, C c2) {
                OverloadingConflictResolver<C> overloadingConflictResolver = this.this$0;
                Intrinsics.checkNotNull(c);
                CallableDescriptor resultingDescriptor = overloadingConflictResolver.getResultingDescriptor(c);
                OverloadingConflictResolver<C> overloadingConflictResolver2 = this.this$0;
                Intrinsics.checkNotNull(c2);
                CallableDescriptor resultingDescriptor2 = overloadingConflictResolver2.getResultingDescriptor(c2);
                if ((resultingDescriptor instanceof SyntheticMemberDescriptor) && (resultingDescriptor2 instanceof SyntheticMemberDescriptor)) {
                    DeclarationDescriptor samInterface = ((SyntheticMemberDescriptor) resultingDescriptor).getSamInterface();
                    DeclarationDescriptor samInterface2 = ((SyntheticMemberDescriptor) resultingDescriptor2).getSamInterface();
                    if ((samInterface instanceof CallableMemberDescriptor) && (samInterface2 instanceof CallableMemberDescriptor)) {
                        return new Pair<>(samInterface, samInterface2);
                    }
                }
                return new Pair<>(resultingDescriptor, resultingDescriptor2);
            }
        });
        Intrinsics.checkNotNullExpressionValue(setFilterOverrides, "filterOverrides(...)");
        if (setFilterOverrides.size() == 1) {
            return setFilterOverrides;
        }
        C cFindMaximallySpecific2 = findMaximallySpecific(setFilterOverrides, checkArgumentsMode, false);
        if (cFindMaximallySpecific2 != null) {
            return SetsKt.setOf(cFindMaximallySpecific2);
        }
        return (!discriminateGenerics || (cFindMaximallySpecific = findMaximallySpecific(setFilterOverrides, checkArgumentsMode, true)) == null) ? setFilterOverrides : SetsKt.setOf(cFindMaximallySpecific);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Set<C> filterOutEquivalentCalls(Collection<? extends C> candidates) {
        CallableDescriptor originalIfTypeRefinementEnabled;
        CallableDescriptor originalIfTypeRefinementEnabled2;
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Set<C> ifOneOrEmpty = setIfOneOrEmpty(candidates);
        if (ifOneOrEmpty != null) {
            return ifOneOrEmpty;
        }
        List listSortedWith = CollectionsKt.sortedWith(candidates, new Comparator() { // from class: org.jetbrains.kotlin.resolve.calls.results.OverloadingConflictResolver$filterOutEquivalentCalls$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(!((Boolean) this.this$0.isFromSources.invoke(this.this$0.getResultingDescriptor(t))).booleanValue() ? 1 : 0), Integer.valueOf(!((Boolean) this.this$0.isFromSources.invoke(this.this$0.getResultingDescriptor(t2))).booleanValue() ? 1 : 0));
            }
        });
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : listSortedWith) {
            this.cancellationChecker.check();
            Iterator it = linkedHashSet.iterator();
            do {
                if (it.hasNext()) {
                    Object next = it.next();
                    originalIfTypeRefinementEnabled = getOriginalIfTypeRefinementEnabled(getResultingDescriptor(obj));
                    Intrinsics.checkNotNull(next);
                    originalIfTypeRefinementEnabled2 = getOriginalIfTypeRefinementEnabled(getResultingDescriptor(next));
                } else {
                    linkedHashSet.add(obj);
                    break;
                }
            } while (!DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides.INSTANCE, originalIfTypeRefinementEnabled, originalIfTypeRefinementEnabled2, isTypeRefinementEnabled(), false, this.isFromSources.invoke(originalIfTypeRefinementEnabled).booleanValue() != this.isFromSources.invoke(originalIfTypeRefinementEnabled2).booleanValue(), this.kotlinTypeRefiner, 8, null));
        }
        return linkedHashSet;
    }

    private final CallableDescriptor getOriginalIfTypeRefinementEnabled(CallableDescriptor callableDescriptor) {
        if (isTypeRefinementEnabled()) {
            callableDescriptor = callableDescriptor.getOriginal();
        }
        Intrinsics.checkNotNull(callableDescriptor);
        return callableDescriptor;
    }

    private final Set<C> setIfOneOrEmpty(Collection<? extends C> collection) {
        int size = collection.size();
        if (size == 0) {
            return SetsKt.emptySet();
        }
        if (size != 1) {
            return null;
        }
        return SetsKt.setOf(CollectionsKt.single(collection));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final C findMaximallySpecific(Set<? extends C> candidates, CheckArgumentTypesMode checkArgumentsMode, boolean discriminateGenerics) {
        if (candidates.size() <= 1) {
            return (C) CollectionsKt.firstOrNull(candidates);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[checkArgumentsMode.ordinal()];
        if (i != 1) {
            if (i == 2) {
                Object objFindMaximallySpecificCall$default = findMaximallySpecificCall$default(this, candidates, discriminateGenerics, false, 4, null);
                if (objFindMaximallySpecificCall$default == null) {
                    Function1<C, Boolean> function1 = this.hasSAMConversion;
                    if (function1 != null) {
                        LinkedHashSet linkedHashSet = new LinkedHashSet();
                        for (Object obj : candidates) {
                            if (!function1.invoke((C) obj).booleanValue()) {
                                linkedHashSet.add(obj);
                            }
                        }
                        objFindMaximallySpecificCall$default = findMaximallySpecificCall$default(this, linkedHashSet, discriminateGenerics, false, 4, null);
                    } else {
                        objFindMaximallySpecificCall$default = null;
                    }
                    if (objFindMaximallySpecificCall$default == null) {
                        Set<? extends C> set = candidates;
                        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                        for (Object obj2 : set) {
                            if (!this.createFlatSignature.invoke((C) obj2).getIsSyntheticMember()) {
                                linkedHashSet2.add(obj2);
                            }
                        }
                        objFindMaximallySpecificCall$default = findMaximallySpecificCall$default(this, linkedHashSet2, discriminateGenerics, false, 4, null);
                        if (objFindMaximallySpecificCall$default == null) {
                            Function1<C, Boolean> function2 = this.hasSAMConversion;
                            if (function2 != null) {
                                LinkedHashSet linkedHashSet3 = new LinkedHashSet();
                                for (Object obj3 : set) {
                                    if (function2.invoke((C) obj3).booleanValue()) {
                                        linkedHashSet3.add(obj3);
                                    }
                                }
                                return (C) findMaximallySpecificCall(linkedHashSet3, discriminateGenerics, true);
                            }
                        }
                    }
                }
                return (C) objFindMaximallySpecificCall$default;
            }
            throw new NoWhenBranchMatchedException();
        }
        Set<? extends C> set2 = candidates;
        boolean z = false;
        Object obj4 = null;
        for (Object obj5 : uniquifyCandidatesSet(set2)) {
            Set<? extends C> set3 = set2;
            if (!(set3 instanceof Collection) || !set3.isEmpty()) {
                Iterator<T> it = set3.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object next = it.next();
                        if (obj5 == next || (isNotLessSpecificCallableReference(getResultingDescriptor(obj5), getResultingDescriptor(next)) && !isNotLessSpecificCallableReference(getResultingDescriptor(next), getResultingDescriptor(obj5)))) {
                        }
                    }
                }
            }
            if (!z) {
                z = true;
                obj4 = obj5;
            }
        }
        if (z) {
            return (C) obj4;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Set<C> findMaximallySpecificVariableAsFunctionCalls(Collection<? extends C> candidates) {
        Collection<? extends C> collection = candidates;
        Set setNewResolvedCallSet = newResolvedCallSet(candidates.size());
        for (Object obj : collection) {
            C cInvoke = this.getVariableCandidates.invoke((C) obj);
            if (cInvoke == null) {
                throw new AssertionError("Regular call among variable-as-function calls: " + obj);
            }
            setNewResolvedCallSet.add(cInvoke);
        }
        Object objSingleOrNull = CollectionsKt.singleOrNull(chooseMaximallySpecificCandidates(setNewResolvedCallSet, CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS, false));
        if (objSingleOrNull == null) {
            return null;
        }
        Set setNewResolvedCallSet2 = newResolvedCallSet(2);
        for (Object obj2 : collection) {
            C cInvoke2 = this.getVariableCandidates.invoke((C) obj2);
            Intrinsics.checkNotNull(cInvoke2);
            if (Intrinsics.areEqual(getResultingDescriptor(cInvoke2), getResultingDescriptor(objSingleOrNull))) {
                setNewResolvedCallSet2.add(obj2);
            }
        }
        return setNewResolvedCallSet2;
    }

    static /* synthetic */ Object findMaximallySpecificCall$default(OverloadingConflictResolver overloadingConflictResolver, Set set, boolean z, boolean z2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findMaximallySpecificCall");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return overloadingConflictResolver.findMaximallySpecificCall(set, z, z2);
    }

    private final C findMaximallySpecificCall(Set<? extends C> candidates, boolean discriminateGenerics, boolean useOriginalSamTypes) {
        Set<C> setUniquifyCandidatesSet = uniquifyCandidatesSet(candidates);
        if (setUniquifyCandidatesSet.size() <= 1) {
            return (C) CollectionsKt.singleOrNull(setUniquifyCandidatesSet);
        }
        Set<C> set = setUniquifyCandidatesSet;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(this.createFlatSignature.invoke((C) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            FlatSignature<? extends C> flatSignature = (FlatSignature) obj;
            this.cancellationChecker.check();
            ArrayList arrayList4 = arrayList2;
            if (!(arrayList4 instanceof Collection) || !arrayList4.isEmpty()) {
                Iterator it2 = arrayList4.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        Object next = it2.next();
                        if (flatSignature != next && !isNotLessSpecificCallWithArgumentMapping(flatSignature, (FlatSignature) next, discriminateGenerics, useOriginalSamTypes)) {
                            break;
                        }
                    }
                }
            }
            arrayList3.add(obj);
        }
        ArrayList arrayList5 = arrayList3;
        Object obj2 = null;
        for (Object obj3 : arrayList5) {
            if (obj2 != null) {
                if (isOfNotLessSpecificShape((FlatSignature) obj3, (FlatSignature) obj2)) {
                }
            }
            obj2 = obj3;
        }
        if (obj2 == null) {
            obj2 = null;
            break;
        }
        ArrayList arrayList6 = arrayList5;
        if (!(arrayList6 instanceof Collection) || !arrayList6.isEmpty()) {
            for (Object obj4 : arrayList6) {
                if (!Intrinsics.areEqual(obj4, obj2)) {
                    if (isOfNotLessSpecificShape((FlatSignature) obj4, (FlatSignature) obj2)) {
                        obj2 = null;
                        break;
                    }
                }
            }
        }
        FlatSignature flatSignature2 = (FlatSignature) obj2;
        if (flatSignature2 != null) {
            return (C) flatSignature2.getOrigin();
        }
        return null;
    }

    private final boolean isNotLessSpecificCallWithArgumentMapping(FlatSignature<? extends C> call1, FlatSignature<? extends C> call2, boolean discriminateGenerics, boolean useOriginalSamTypes) {
        Boolean boolTryCompareDescriptorsFromScripts = tryCompareDescriptorsFromScripts(candidateDescriptor(call1), candidateDescriptor(call2));
        return boolTryCompareDescriptorsFromScripts != null ? boolTryCompareDescriptorsFromScripts.booleanValue() : compareCallsByUsedArguments(call1, call2, discriminateGenerics, useOriginalSamTypes);
    }

    private final boolean compareCallsByUsedArguments(FlatSignature<? extends C> call1, FlatSignature<? extends C> call2, boolean discriminateGenerics, boolean useOriginalSamTypes) {
        if (discriminateGenerics) {
            boolean isGeneric = call1.getIsGeneric();
            boolean isGeneric2 = call2.getIsGeneric();
            if (isGeneric && !isGeneric2) {
                return false;
            }
            if (!isGeneric && isGeneric2) {
                return true;
            }
            if (isGeneric && isGeneric2) {
                return false;
            }
        }
        if (!call1.getIsExpect() && call2.getIsExpect()) {
            return true;
        }
        if (call1.getIsExpect() && !call2.getIsExpect()) {
            return false;
        }
        if (call1.getContextReceiverCount() > call2.getContextReceiverCount()) {
            return true;
        }
        if (call1.getContextReceiverCount() < call2.getContextReceiverCount()) {
            return false;
        }
        return FlatSignatureKt.isSignatureNotLessSpecific(this.createEmptyConstraintSystem.invoke(), call1, call2, this.SpecificityComparisonWithNumerics, this.specificityComparator, useOriginalSamTypes);
    }

    private final boolean isOfNotLessSpecificShape(FlatSignature<? extends C> call1, FlatSignature<? extends C> call2) {
        boolean hasVarargs = call1.getHasVarargs();
        boolean hasVarargs2 = call2.getHasVarargs();
        if (hasVarargs && !hasVarargs2) {
            return false;
        }
        if (hasVarargs || !hasVarargs2) {
            return call1.getNumDefaults() <= call2.getNumDefaults() && !this.platformOverloadsSpecificityComparator.isMoreSpecificShape(candidateDescriptor(call2), candidateDescriptor(call1));
        }
        return true;
    }

    private final Boolean tryCompareDescriptorsFromScripts(CallableDescriptor d1, CallableDescriptor d2) {
        DeclarationDescriptor containingDeclaration = d1.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        DeclarationDescriptor containingDeclaration2 = d2.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
        if (!(containingDeclaration instanceof ScriptDescriptor) || !(containingDeclaration2 instanceof ScriptDescriptor)) {
            return null;
        }
        ScriptDescriptor scriptDescriptor = (ScriptDescriptor) containingDeclaration;
        ScriptDescriptor scriptDescriptor2 = (ScriptDescriptor) containingDeclaration2;
        if (scriptDescriptor.getPriority() > scriptDescriptor2.getPriority()) {
            return true;
        }
        return scriptDescriptor.getPriority() < scriptDescriptor2.getPriority() ? false : null;
    }

    private final boolean isNotLessSpecificCallableReferenceDescriptor(CallableDescriptor f, CallableDescriptor g) {
        if (f.getValueParameters().size() != g.getValueParameters().size() || DescriptorUtilsKt.varargParameterPosition(f) != DescriptorUtilsKt.varargParameterPosition(g)) {
            return false;
        }
        if (!FlatSignatureKt.isSignatureNotLessSpecific$default(this.createEmptyConstraintSystem.invoke(), FlatSignatureUtilsKt.createFromCallableDescriptor(FlatSignature.INSTANCE, f), FlatSignatureUtilsKt.createFromCallableDescriptor(FlatSignature.INSTANCE, g), this.SpecificityComparisonWithNumerics, this.specificityComparator, false, 16, null)) {
            return false;
        }
        if ((f instanceof CallableMemberDescriptor) && (g instanceof CallableMemberDescriptor)) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) f;
            if (!callableMemberDescriptor.get$isExpect() && ((CallableMemberDescriptor) g).get$isExpect()) {
                return true;
            }
            if (callableMemberDescriptor.get$isExpect() && !((CallableMemberDescriptor) g).get$isExpect()) {
                return false;
            }
        }
        return !this.platformOverloadsSpecificityComparator.isMoreSpecificShape(g, f);
    }

    private final boolean isNotLessSpecificCallableReference(CallableDescriptor f, CallableDescriptor g) {
        Boolean boolTryCompareDescriptorsFromScripts = tryCompareDescriptorsFromScripts(f, g);
        return boolTryCompareDescriptorsFromScripts != null ? boolTryCompareDescriptorsFromScripts.booleanValue() : isNotLessSpecificCallableReferenceDescriptor(f, g);
    }

    private final Set<C> uniquifyCandidatesSet(Collection<? extends C> candidates) {
        THashSet tHashSet = new THashSet(candidates.size(), this.resolvedCallHashingStrategy);
        tHashSet.addAll(candidates);
        return tHashSet;
    }

    private final Set<C> newResolvedCallSet(int expectedSize) {
        return new THashSet(expectedSize, this.resolvedCallHashingStrategy);
    }

    private final CallableDescriptor candidateDescriptor(FlatSignature<? extends C> flatSignature) {
        CallableDescriptor original = getResultingDescriptor(flatSignature.getOrigin()).getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return original;
    }
}
