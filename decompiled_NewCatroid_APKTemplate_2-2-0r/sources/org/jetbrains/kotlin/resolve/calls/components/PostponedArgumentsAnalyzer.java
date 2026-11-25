package org.jetbrains.kotlin.resolve.calls.components;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferencePosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.LambdaArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaReturnType;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceArgumentAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCollectionLiteralAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: PostponedArgumentsAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001)B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J.\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J.\u0010\u0013\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0012JD\u0010\u0018\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00122\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001bJ\u0016\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001f*\u0004\u0018\u00010\u001dH\u0002J\n\u0010 \u001a\u00020!*\u00020\nJ7\u0010\"\u001a\u0004\u0018\u0001H#\"\u0004\b\u0000\u0010#*\u0004\u0018\u00010\u001d2\u0019\u0010$\u001a\u0015\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u0001H#0\u001b¢\u0006\u0002\b%H\u0082\b¢\u0006\u0002\u0010&J\u0010\u0010'\u001a\u0004\u0018\u00010\u001d*\u0004\u0018\u00010\u001dH\u0002J\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001f*\u0004\u0018\u00010\u001dH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "", "callableReferenceArgumentResolver", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "analyze", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "analyzeLambda", "Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsAnalysisResult;", "lambda", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "diagnosticHolder", "applyResultsOfAnalyzedLambdaToCandidateSystem", "returnArgumentsAnalysisResult", "substitute", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "contextReceivers", "", "createSubstituteFunctorForLambdaAnalysis", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer$SubstitutorAndStubsForLambdaAnalysis;", "forFunctionalType", ExifInterface.GPS_DIRECTION_TRUE, "f", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/types/UnwrappedType;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "receiver", "valueParameters", "SubstitutorAndStubsForLambdaAnalysis", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PostponedArgumentsAnalyzer {
    private final CallableReferenceArgumentResolver callableReferenceArgumentResolver;
    private final LanguageVersionSettings languageVersionSettings;

    public PostponedArgumentsAnalyzer(CallableReferenceArgumentResolver callableReferenceArgumentResolver, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(callableReferenceArgumentResolver, "callableReferenceArgumentResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.callableReferenceArgumentResolver = callableReferenceArgumentResolver;
        this.languageVersionSettings = languageVersionSettings;
    }

    public final void analyze(PostponedArgumentsAnalyzerContext c, KotlinResolutionCallbacks resolutionCallbacks, ResolvedAtom argument, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticsHolder) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        if (argument instanceof ResolvedLambdaAtom) {
            analyzeLambda(c, resolutionCallbacks, (ResolvedLambdaAtom) argument, completionMode, diagnosticsHolder);
        } else if (argument instanceof LambdaWithTypeVariableAsExpectedTypeAtom) {
            analyzeLambda(c, resolutionCallbacks, PostponeArgumentsChecksKt.transformToResolvedLambda$default((LambdaWithTypeVariableAsExpectedTypeAtom) argument, c.getBuilder(), diagnosticsHolder, (UnwrappedType) null, (TypeVariableForLambdaReturnType) null, 12, (Object) null), completionMode, diagnosticsHolder);
        } else if (argument instanceof ResolvedCallableReferenceArgumentAtom) {
            this.callableReferenceArgumentResolver.processCallableReferenceArgument(c.getBuilder(), (ResolvedCallableReferenceArgumentAtom) argument, diagnosticsHolder, resolutionCallbacks);
        } else {
            if (!(argument instanceof ResolvedCollectionLiteralAtom)) {
                throw new IllegalStateException(("Unexpected resolved primitive: " + argument.getClass().getCanonicalName()).toString());
            }
            throw new NotImplementedError("An operation is not implemented: Not supported");
        }
    }

    /* JADX INFO: compiled from: PostponedArgumentsAnalyzer.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u0015\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0015\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007HÆ\u0003J5\u0010\u0011\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer$SubstitutorAndStubsForLambdaAnalysis;", "", "stubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "substitute", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)V", "getStubsForPostponedVariables", "()Ljava/util/Map;", "getSubstitute", "()Lkotlin/jvm/functions/Function1;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class SubstitutorAndStubsForLambdaAnalysis {
        private final Map<TypeVariableMarker, StubTypeMarker> stubsForPostponedVariables;
        private final Function1<KotlinType, UnwrappedType> substitute;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ SubstitutorAndStubsForLambdaAnalysis copy$default(SubstitutorAndStubsForLambdaAnalysis substitutorAndStubsForLambdaAnalysis, Map map, Function1 function1, int i, Object obj) {
            if ((i & 1) != 0) {
                map = substitutorAndStubsForLambdaAnalysis.stubsForPostponedVariables;
            }
            if ((i & 2) != 0) {
                function1 = substitutorAndStubsForLambdaAnalysis.substitute;
            }
            return substitutorAndStubsForLambdaAnalysis.copy(map, function1);
        }

        public final Map<TypeVariableMarker, StubTypeMarker> component1() {
            return this.stubsForPostponedVariables;
        }

        public final Function1<KotlinType, UnwrappedType> component2() {
            return this.substitute;
        }

        public final SubstitutorAndStubsForLambdaAnalysis copy(Map<TypeVariableMarker, ? extends StubTypeMarker> stubsForPostponedVariables, Function1<? super KotlinType, ? extends UnwrappedType> substitute) {
            Intrinsics.checkNotNullParameter(stubsForPostponedVariables, "stubsForPostponedVariables");
            Intrinsics.checkNotNullParameter(substitute, "substitute");
            return new SubstitutorAndStubsForLambdaAnalysis(stubsForPostponedVariables, substitute);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SubstitutorAndStubsForLambdaAnalysis)) {
                return false;
            }
            SubstitutorAndStubsForLambdaAnalysis substitutorAndStubsForLambdaAnalysis = (SubstitutorAndStubsForLambdaAnalysis) other;
            return Intrinsics.areEqual(this.stubsForPostponedVariables, substitutorAndStubsForLambdaAnalysis.stubsForPostponedVariables) && Intrinsics.areEqual(this.substitute, substitutorAndStubsForLambdaAnalysis.substitute);
        }

        public int hashCode() {
            return (this.stubsForPostponedVariables.hashCode() * 31) + this.substitute.hashCode();
        }

        public String toString() {
            return "SubstitutorAndStubsForLambdaAnalysis(stubsForPostponedVariables=" + this.stubsForPostponedVariables + ", substitute=" + this.substitute + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SubstitutorAndStubsForLambdaAnalysis(Map<TypeVariableMarker, ? extends StubTypeMarker> stubsForPostponedVariables, Function1<? super KotlinType, ? extends UnwrappedType> substitute) {
            Intrinsics.checkNotNullParameter(stubsForPostponedVariables, "stubsForPostponedVariables");
            Intrinsics.checkNotNullParameter(substitute, "substitute");
            this.stubsForPostponedVariables = stubsForPostponedVariables;
            this.substitute = substitute;
        }

        public final Map<TypeVariableMarker, StubTypeMarker> getStubsForPostponedVariables() {
            return this.stubsForPostponedVariables;
        }

        public final Function1<KotlinType, UnwrappedType> getSubstitute() {
            return this.substitute;
        }
    }

    public final SubstitutorAndStubsForLambdaAnalysis createSubstituteFunctorForLambdaAnalysis(final PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext) {
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzerContext, "<this>");
        Map<TypeVariableMarker, StubTypeMarker> mapBindingStubsForPostponedVariables = postponedArgumentsAnalyzerContext.bindingStubsForPostponedVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapBindingStubsForPostponedVariables.size()));
        Iterator<T> it = mapBindingStubsForPostponedVariables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(MarkerExtensionsKt.freshTypeConstructor((TypeVariableMarker) entry.getKey(), postponedArgumentsAnalyzerContext), entry.getValue());
        }
        final TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = postponedArgumentsAnalyzerContext.buildCurrentSubstitutor(linkedHashMap);
        return new SubstitutorAndStubsForLambdaAnalysis(mapBindingStubsForPostponedVariables, new Function1<KotlinType, UnwrappedType>() { // from class: org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer.createSubstituteFunctorForLambdaAnalysis.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final UnwrappedType invoke(KotlinType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarkerBuildCurrentSubstitutor, postponedArgumentsAnalyzerContext, it2);
                Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
                return (UnwrappedType) kotlinTypeMarkerSafeSubstitute;
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:75:0x019e  */
    /* JADX WARN: Code duplicated, block: B:78:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:79:0x01b2  */
    /* JADX WARN: Multi-variable type inference failed */
    public final ReturnArgumentsAnalysisResult analyzeLambda(PostponedArgumentsAnalyzerContext c, KotlinResolutionCallbacks resolutionCallbacks, ResolvedLambdaAtom lambda, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticHolder) {
        UnwrappedType unwrappedTypeAnalyzeLambda$expectedOrActualType;
        ArrayList arrayList;
        UnwrappedType unwrappedType;
        SimpleType unitType;
        UnwrappedType expectedType;
        Annotations annotations;
        Annotations empty;
        Annotations annotations2;
        UnwrappedType unwrappedType2;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        SubstitutorAndStubsForLambdaAnalysis substitutorAndStubsForLambdaAnalysisCreateSubstituteFunctorForLambdaAnalysis = createSubstituteFunctorForLambdaAnalysis(c);
        Function1<? super KotlinType, ? extends UnwrappedType> substitute = substitutorAndStubsForLambdaAnalysisCreateSubstituteFunctorForLambdaAnalysis.getSubstitute();
        KotlinBuiltIns builtIns = BuiltInsProviderKt.getBuiltIns(c.getBuilder());
        List<UnwrappedType> listValueParameters = valueParameters(lambda.getExpectedType());
        UnwrappedType unwrappedTypeReceiver = receiver(lambda.getExpectedType());
        List<UnwrappedType> listContextReceivers = contextReceivers(lambda.getExpectedType());
        if (lambda.getReceiver() != null) {
            if (unwrappedTypeReceiver == null) {
                unwrappedType2 = listValueParameters != null ? (UnwrappedType) CollectionsKt.getOrNull(listValueParameters, 0) : null;
            } else {
                unwrappedType2 = unwrappedTypeReceiver;
            }
            unwrappedTypeAnalyzeLambda$expectedOrActualType = analyzeLambda$expectedOrActualType(substitute, c, unwrappedType2, lambda.getReceiver());
        } else {
            unwrappedTypeAnalyzeLambda$expectedOrActualType = null;
        }
        List<UnwrappedType> contextReceivers = lambda.getContextReceivers();
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        for (Object obj : contextReceivers) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            UnwrappedType unwrappedTypeAnalyzeLambda$expectedOrActualType2 = analyzeLambda$expectedOrActualType(substitute, c, listContextReceivers != null ? (UnwrappedType) CollectionsKt.getOrNull(listContextReceivers, i) : null, (UnwrappedType) obj);
            if (unwrappedTypeAnalyzeLambda$expectedOrActualType2 != null) {
                arrayList2.add(unwrappedTypeAnalyzeLambda$expectedOrActualType2);
            }
            i = i2;
        }
        ArrayList arrayList3 = arrayList2;
        if (unwrappedTypeAnalyzeLambda$expectedOrActualType == null && unwrappedTypeReceiver != null && listValueParameters != null) {
            listValueParameters = CollectionsKt.plus((Collection) CollectionsKt.listOf(unwrappedTypeReceiver), (Iterable) listValueParameters);
        } else if (unwrappedTypeAnalyzeLambda$expectedOrActualType == null && unwrappedTypeReceiver != null) {
            listValueParameters = CollectionsKt.listOf(unwrappedTypeReceiver);
        } else if (unwrappedTypeAnalyzeLambda$expectedOrActualType != null && unwrappedTypeReceiver == null) {
            listValueParameters = listValueParameters != null ? CollectionsKt.drop(listValueParameters, 1) : null;
        }
        if (listValueParameters != null) {
            List<UnwrappedType> list = listValueParameters;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            int i3 = 0;
            for (Object obj2 : list) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                KotlinType kotlinTypeAnalyzeLambda$expectedOrActualType = analyzeLambda$expectedOrActualType(substitute, c, (UnwrappedType) obj2, (UnwrappedType) CollectionsKt.getOrNull(lambda.getParameters(), i3));
                if (kotlinTypeAnalyzeLambda$expectedOrActualType == null) {
                    KotlinType nothingType = builtIns.getNothingType();
                    Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
                    kotlinTypeAnalyzeLambda$expectedOrActualType = (UnwrappedType) nothingType;
                }
                arrayList4.add(kotlinTypeAnalyzeLambda$expectedOrActualType);
                i3 = i4;
            }
            arrayList = arrayList4;
        } else {
            List<UnwrappedType> parameters = lambda.getParameters();
            ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(parameters, 10));
            Iterator<T> it = parameters.iterator();
            while (it.hasNext()) {
                arrayList5.add(substitute.invoke(it.next()));
            }
            arrayList = arrayList5;
        }
        UnwrappedType returnType = lambda.getReturnType();
        UnwrappedType unwrappedType3 = returnType;
        if (!c.canBeProper(unwrappedType3)) {
            if (returnType.getIsMarkedNullable() || !c.hasUpperOrEqualUnitConstraint(unwrappedType3)) {
                unwrappedType = null;
            } else {
                unitType = builtIns.getUnitType();
            }
            expectedType = lambda.getExpectedType();
            if (expectedType != null || (annotations2 = expectedType.getAnnotations()) == null) {
                annotations = null;
            } else {
                if (unwrappedTypeAnalyzeLambda$expectedOrActualType == null && unwrappedTypeReceiver != null) {
                    annotations2 = new FilteredAnnotations(annotations2, true, new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer$analyzeLambda$convertedAnnotations$1$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(FqName it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return Boolean.valueOf(!Intrinsics.areEqual(it2, StandardNames.FqNames.extensionFunctionType));
                        }
                    });
                }
                annotations = annotations2;
            }
            LambdaKotlinCallArgument atom = lambda.getAtom();
            boolean isSuspend = lambda.getIsSuspend();
            if (annotations == null) {
                empty = Annotations.INSTANCE.getEMPTY();
            } else {
                empty = annotations;
            }
            Map<TypeVariableMarker, StubTypeMarker> stubsForPostponedVariables = substitutorAndStubsForLambdaAnalysisCreateSubstituteFunctorForLambdaAnalysis.getStubsForPostponedVariables();
            Intrinsics.checkNotNull(stubsForPostponedVariables, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable, org.jetbrains.kotlin.types.StubTypeForBuilderInference>");
            ReturnArgumentsAnalysisResult returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments = resolutionCallbacks.analyzeAndGetLambdaReturnArguments(atom, isSuspend, unwrappedTypeAnalyzeLambda$expectedOrActualType, arrayList3, arrayList, unwrappedType, empty, stubsForPostponedVariables);
            applyResultsOfAnalyzedLambdaToCandidateSystem(c, lambda, returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments, completionMode, diagnosticHolder, substitute);
            return returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments;
        }
        unitType = substitute.invoke(returnType);
        unwrappedType = unitType;
        expectedType = lambda.getExpectedType();
        if (expectedType != null) {
            annotations = null;
        } else {
            annotations = null;
        }
        LambdaKotlinCallArgument atom2 = lambda.getAtom();
        boolean isSuspend2 = lambda.getIsSuspend();
        if (annotations == null) {
            empty = Annotations.INSTANCE.getEMPTY();
        } else {
            empty = annotations;
        }
        Map<TypeVariableMarker, StubTypeMarker> stubsForPostponedVariables2 = substitutorAndStubsForLambdaAnalysisCreateSubstituteFunctorForLambdaAnalysis.getStubsForPostponedVariables();
        Intrinsics.checkNotNull(stubsForPostponedVariables2, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable, org.jetbrains.kotlin.types.StubTypeForBuilderInference>");
        ReturnArgumentsAnalysisResult returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments2 = resolutionCallbacks.analyzeAndGetLambdaReturnArguments(atom2, isSuspend2, unwrappedTypeAnalyzeLambda$expectedOrActualType, arrayList3, arrayList, unwrappedType, empty, stubsForPostponedVariables2);
        applyResultsOfAnalyzedLambdaToCandidateSystem(c, lambda, returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments2, completionMode, diagnosticHolder, substitute);
        return returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments2;
    }

    private static final UnwrappedType analyzeLambda$expectedOrActualType(Function1<? super KotlinType, ? extends UnwrappedType> function1, PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext, UnwrappedType unwrappedType, UnwrappedType unwrappedType2) {
        UnwrappedType unwrappedTypeInvoke = unwrappedType != null ? function1.invoke(unwrappedType) : null;
        if (unwrappedTypeInvoke != null && postponedArgumentsAnalyzerContext.canBeProper(unwrappedTypeInvoke)) {
            return unwrappedTypeInvoke;
        }
        if (unwrappedType2 != null) {
            return function1.invoke(unwrappedType2);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void applyResultsOfAnalyzedLambdaToCandidateSystem$default(PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext, ResolvedLambdaAtom resolvedLambdaAtom, ReturnArgumentsAnalysisResult returnArgumentsAnalysisResult, ConstraintSystemCompletionMode constraintSystemCompletionMode, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, Function1 function1, int i, Object obj) {
        if ((i & 32) != 0) {
            function1 = postponedArgumentsAnalyzer.createSubstituteFunctorForLambdaAnalysis(postponedArgumentsAnalyzerContext).getSubstitute();
        }
        postponedArgumentsAnalyzer.applyResultsOfAnalyzedLambdaToCandidateSystem(postponedArgumentsAnalyzerContext, resolvedLambdaAtom, returnArgumentsAnalysisResult, constraintSystemCompletionMode, kotlinDiagnosticsHolder, function1);
    }

    public final void applyResultsOfAnalyzedLambdaToCandidateSystem(PostponedArgumentsAnalyzerContext c, ResolvedLambdaAtom lambda, ReturnArgumentsAnalysisResult returnArgumentsAnalysisResult, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticHolder, Function1<? super KotlinType, ? extends UnwrappedType> substitute) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(returnArgumentsAnalysisResult, "returnArgumentsAnalysisResult");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(substitute, "substitute");
        ReturnArgumentsInfo returnArgumentsInfo = returnArgumentsAnalysisResult.getReturnArgumentsInfo();
        InferenceSession inferenceSession = returnArgumentsAnalysisResult.getInferenceSession();
        if (returnArgumentsAnalysisResult.getHasInapplicableCallForBuilderInference()) {
            if (inferenceSession != null) {
                inferenceSession.initializeLambda(lambda);
            }
            c.getBuilder().markCouldBeResolvedWithUnrestrictedBuilderInference();
            c.getBuilder().removePostponedVariables();
            return;
        }
        List<KotlinCallArgument> nonErrorArguments = returnArgumentsInfo.getNonErrorArguments();
        Iterator<T> it = nonErrorArguments.iterator();
        while (it.hasNext()) {
            DescriptorRelatedInferenceUtilsKt.addSubsystemFromArgument(c, (KotlinCallArgument) it.next());
        }
        KotlinCallArgument lastExpression = returnArgumentsInfo.getLastExpression();
        if (lastExpression != null && returnArgumentsInfo.getLastExpressionCoercedToUnit() && DescriptorRelatedInferenceUtilsKt.addSubsystemFromArgument(c, lastExpression)) {
            nonErrorArguments = CollectionsKt.plus((Collection<? extends KotlinCallArgument>) nonErrorArguments, lastExpression);
        }
        List<KotlinCallArgument> list = nonErrorArguments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            ArrayList arrayList2 = arrayList;
            arrayList2.add(PostponeArgumentsChecksKt.resolveKtPrimitive(c.getBuilder(), (KotlinCallArgument) it2.next(), substitute.invoke(lambda.getReturnType()), diagnosticHolder, ReceiverInfo.INSTANCE.getNotReceiver(), null, inferenceSession, (128 & 128) != 0 ? null : null));
            inferenceSession = inferenceSession;
            arrayList = arrayList2;
        }
        InferenceSession inferenceSession2 = inferenceSession;
        ArrayList arrayList3 = arrayList;
        if (!returnArgumentsInfo.getReturnArgumentsExist()) {
            SimpleType unitType = TypeUtilsKt.getBuiltIns(lambda.getReturnType()).getUnitType();
            Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
            c.getBuilder().addSubtypeConstraint(unitType, substitute.invoke(lambda.getReturnType()), new LambdaArgumentConstraintPositionImpl(lambda));
        }
        lambda.setAnalyzedResults(returnArgumentsInfo, arrayList3);
        boolean z = lambda.getAtom().getHasBuilderInferenceAnnotation() || this.languageVersionSettings.supportsFeature(LanguageFeature.UseBuilderInferenceWithoutAnnotation);
        if (inferenceSession2 == null || !z) {
            return;
        }
        ConstraintSystemBuilder builder = c.getBuilder();
        Map<TypeConstructor, UnwrappedType> mapInferPostponedVariables = inferenceSession2.inferPostponedVariables(lambda, builder, completionMode, diagnosticHolder);
        if (mapInferPostponedVariables == null) {
            c.getBuilder().removePostponedVariables();
            return;
        }
        for (Map.Entry<TypeConstructor, UnwrappedType> entry : mapInferPostponedVariables.entrySet()) {
            TypeConstructor key = entry.getKey();
            UnwrappedType value = entry.getValue();
            VariableWithConstraints variableWithConstraints = builder.currentStorage().getNotFixedTypeVariables().get(key);
            if (variableWithConstraints != null) {
                TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
                c.getBuilder().unmarkPostponedVariable(typeVariable);
                c.getBuilder().addSubtypeConstraint(value, MarkerExtensionsKt.defaultType(typeVariable, c), BuilderInferencePosition.INSTANCE);
            }
        }
        c.removePostponedTypeVariablesFromConstraints(mapInferPostponedVariables.keySet());
    }

    private final UnwrappedType receiver(UnwrappedType unwrappedType) {
        KotlinType receiverTypeFromFunctionType;
        if (unwrappedType == null || !FunctionTypesKt.isBuiltinFunctionalType(unwrappedType) || (receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(unwrappedType)) == null) {
            return null;
        }
        return receiverTypeFromFunctionType.unwrap();
    }

    private final List<UnwrappedType> contextReceivers(UnwrappedType unwrappedType) {
        if (unwrappedType == null || !FunctionTypesKt.isBuiltinFunctionalType(unwrappedType)) {
            return null;
        }
        List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(unwrappedType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiverTypesFromFunctionType, 10));
        Iterator<T> it = contextReceiverTypesFromFunctionType.iterator();
        while (it.hasNext()) {
            arrayList.add(((KotlinType) it.next()).unwrap());
        }
        return arrayList;
    }

    private final List<UnwrappedType> valueParameters(UnwrappedType unwrappedType) {
        if (unwrappedType == null || !FunctionTypesKt.isBuiltinFunctionalType(unwrappedType)) {
            return null;
        }
        List<TypeProjection> valueParameterTypesFromFunctionType = FunctionTypesKt.getValueParameterTypesFromFunctionType(unwrappedType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypesFromFunctionType, 10));
        Iterator<T> it = valueParameterTypesFromFunctionType.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getNullableAnyType().unwrap());
        }
        return arrayList;
    }
}
