package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassLikeDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualMemberDiff;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualMemberDiffKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolver;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a*\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\f0\u000b0\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002\u001a0\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001aE\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u00010\u00182\u0006\u0010\u0013\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u0082\u0002\u0014\n\b\b\u0002\u001a\u0004\u0010\u0001(\u0000\n\b\b\u0002\u001a\u0004\u0010\u0002(\u0001\u001a\u0012\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00020\u001c*\u00020\u001dH\u0002\u001a \u0010\u001e\u001a\u00020\u0010*\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\f0\u000bH\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bò\u0001\b\n\u00020\u0014\n\u00020\u0016¨\u0006!"}, d2 = {"functionVsPropertyKind", "Lorg/jetbrains/kotlin/resolve/checkers/Kind;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getFunctionVsPropertyKind", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/resolve/checkers/Kind;", "psiIfReal", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "getPsiIfReal", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "calculateExpectActualScopeDiff", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "expect", "actual", "checkExpectActualScopeDiff", "", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "declaration", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "matchActualWithNonFinalExpect", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "extractNonPrivateCallables", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "reportIfPossible", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "diff", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierCheckerKt {

    /* JADX INFO: compiled from: ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExpectActualMemberDiff.Kind.values().length];
            try {
                iArr[ExpectActualMemberDiff.Kind.ReturnTypeChangedInOverride.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.ModalityChangedInOverride.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.VisibilityChangedInOverride.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.SetterVisibilityChangedInOverride.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.ParameterNameChangedInOverride.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.PropertyKindChangedInOverride.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.LateinitChangedInOverride.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.VarargChangedInOverride.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.TypeParameterNamesChangedInOverride.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ExpectActualMemberDiff.Kind.Unknown.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkExpectActualScopeDiff(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2, DeclarationCheckerContext declarationCheckerContext, KtClassLikeDeclaration ktClassLikeDeclaration, ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
        Set<ExpectActualMemberDiff<CallableMemberDescriptor, ClassDescriptor>> setCalculateExpectActualScopeDiff = calculateExpectActualScopeDiff(classDescriptor, classDescriptor2);
        if (!setCalculateExpectActualScopeDiff.isEmpty()) {
            declarationCheckerContext.getTrace().report(Errors.ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING.on(ktClassLikeDeclaration, classifierDescriptorWithTypeParameters, setCalculateExpectActualScopeDiff, (ClassDescriptor) ((ExpectActualMemberDiff) CollectionsKt.first(setCalculateExpectActualScopeDiff)).getExpectClass()));
        }
        if (classifierDescriptorWithTypeParameters instanceof TypeAliasDescriptor) {
            return;
        }
        Iterator<ExpectActualMemberDiff<CallableMemberDescriptor, ClassDescriptor>> it = setCalculateExpectActualScopeDiff.iterator();
        while (it.hasNext()) {
            reportIfPossible(declarationCheckerContext.getTrace(), it.next());
        }
    }

    public static final Pair<ClassDescriptor, ClassDescriptor> matchActualWithNonFinalExpect(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        ClassDescriptor classDescriptor;
        List list;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiPlatformProjects)) {
            return null;
        }
        if (!(declaration instanceof KtTypeAlias) && !(declaration instanceof KtClassOrObject)) {
            return null;
        }
        boolean z = descriptor instanceof TypeAliasDescriptor;
        if (!z && !(descriptor instanceof ClassDescriptor)) {
            return null;
        }
        if (!((ClassifierDescriptorWithTypeParameters) descriptor).isActual()) {
            return null;
        }
        if (descriptor instanceof ClassDescriptor) {
            classDescriptor = (ClassDescriptor) descriptor;
        } else if (z) {
            classDescriptor = ((TypeAliasDescriptor) descriptor).getClassDescriptor();
        } else {
            throw new IllegalStateException("ClassifierDescriptorWithTypeParameters has only two inheritors".toString());
        }
        if (classDescriptor == null || classDescriptor.getModality() == Modality.FINAL) {
            return null;
        }
        Map mapFindExpectedForActual$default = ExpectedActualResolver.findExpectedForActual$default(ExpectedActualResolver.INSTANCE, (MemberDescriptor) descriptor, null, false, 6, null);
        MemberDescriptor memberDescriptor = (mapFindExpectedForActual$default == null || (list = (List) mapFindExpectedForActual$default.get(ExpectActualCompatibility.Compatible.INSTANCE)) == null) ? null : (MemberDescriptor) CollectionsKt.singleOrNull(list);
        ClassDescriptor classDescriptor2 = memberDescriptor instanceof ClassDescriptor ? (ClassDescriptor) memberDescriptor : null;
        if (classDescriptor2 == null || classDescriptor2.getModality() == Modality.FINAL) {
            return null;
        }
        return TuplesKt.to(classDescriptor, classDescriptor2);
    }

    private static final Set<ExpectActualMemberDiff<CallableMemberDescriptor, ClassDescriptor>> calculateExpectActualScopeDiff(final ClassDescriptor classDescriptor, final ClassDescriptor classDescriptor2) {
        final ClassicExpectActualMatchingContext classicExpectActualMatchingContext = new ClassicExpectActualMatchingContext(DescriptorUtilsKt.getModule(classDescriptor2), false, 2, null);
        if (classDescriptor.getDeclaredTypeParameters().size() != classDescriptor2.getDeclaredTypeParameters().size()) {
            return SetsKt.emptySet();
        }
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        List<TypeParameterDescriptor> declaredTypeParameters2 = classDescriptor2.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters2, "getDeclaredTypeParameters(...)");
        final TypeSubstitutorMarker typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor = classicExpectActualMatchingContext.createExpectActualTypeParameterSubstitutor(declaredTypeParameters, declaredTypeParameters2, null);
        MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Sequence<CallableMemberDescriptor> sequenceExtractNonPrivateCallables = extractNonPrivateCallables(unsubstitutedMemberScope);
        MemberScope unsubstitutedMemberScope2 = classDescriptor2.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope2, "getUnsubstitutedMemberScope(...)");
        Sequence sequenceFilter = SequencesKt.filter(extractNonPrivateCallables(unsubstitutedMemberScope2), new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierCheckerKt$calculateExpectActualScopeDiff$actualClassCallables$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getKind().isReal());
            }
        });
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (CallableMemberDescriptor callableMemberDescriptor : sequenceExtractNonPrivateCallables) {
            CallableMemberDescriptor callableMemberDescriptor2 = callableMemberDescriptor;
            Pair pair = TuplesKt.to(callableMemberDescriptor2.getShortName(), getFunctionVsPropertyKind(callableMemberDescriptor2));
            Object obj = linkedHashMap.get(pair);
            if (obj == null) {
                obj = (List) new ArrayList();
                linkedHashMap.put(pair, obj);
            }
            ((List) obj).add(callableMemberDescriptor);
        }
        return SequencesKt.toSet(SequencesKt.flatMapIterable(sequenceFilter, new Function1<CallableMemberDescriptor, List<? extends ExpectActualMemberDiff<? extends CallableMemberDescriptor, ? extends ClassDescriptor>>>() { // from class: org.jetbrains.kotlin.resolve.checkers.ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierCheckerKt.calculateExpectActualScopeDiff.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<ExpectActualMemberDiff<CallableMemberDescriptor, ClassDescriptor>> invoke(CallableMemberDescriptor actualMember) {
                ArrayList arrayListEmptyList;
                ExpectActualMemberDiff.Kind memberDiffKind;
                Intrinsics.checkNotNullParameter(actualMember, "actualMember");
                List<CallableMemberDescriptor> list = linkedHashMap.get(TuplesKt.to(actualMember.getShortName(), ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierCheckerKt.getFunctionVsPropertyKind(actualMember)));
                List<CallableMemberDescriptor> list2 = list;
                if (list2 == null || list2.isEmpty()) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                } else {
                    List<CallableMemberDescriptor> list3 = list;
                    TypeSubstitutorMarker typeSubstitutorMarker = typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor;
                    ClassDescriptor classDescriptor3 = classDescriptor;
                    ClassDescriptor classDescriptor4 = classDescriptor2;
                    ClassicExpectActualMatchingContext classicExpectActualMatchingContext2 = classicExpectActualMatchingContext;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    Iterator<T> it = list3.iterator();
                    while (it.hasNext()) {
                        arrayList.add(AbstractExpectActualCompatibilityChecker.INSTANCE.getCallablesCompatibility((CallableMemberDescriptor) it.next(), actualMember, typeSubstitutorMarker, classDescriptor3, classDescriptor4, classicExpectActualMatchingContext2));
                    }
                    List<ExpectActualCompatibility> listEmptyList = arrayList;
                    ArrayList arrayList2 = listEmptyList;
                    if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                        Iterator it2 = arrayList2.iterator();
                        while (it2.hasNext()) {
                            if (Intrinsics.areEqual((ExpectActualCompatibility) it2.next(), ExpectActualCompatibility.Compatible.INSTANCE)) {
                                listEmptyList = null;
                                break;
                            }
                        }
                    }
                    if (listEmptyList == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    ArrayList arrayList3 = new ArrayList();
                    for (ExpectActualCompatibility expectActualCompatibility : listEmptyList) {
                        if (expectActualCompatibility instanceof ExpectActualCompatibility.Compatible) {
                            throw new IllegalStateException("Compatible was filtered out by takeIf".toString());
                        }
                        if (Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Incompatible.CallableKind.INSTANCE) ? true : Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Incompatible.ParameterCount.INSTANCE) ? true : Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Incompatible.ParameterShape.INSTANCE) ? true : Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Incompatible.ParameterTypes.INSTANCE) ? true : Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Incompatible.FunctionTypeParameterCount.INSTANCE) ? true : Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Incompatible.FunctionTypeParameterUpperBounds.INSTANCE)) {
                            memberDiffKind = null;
                        } else {
                            if (!(expectActualCompatibility instanceof ExpectActualCompatibility.Incompatible)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            memberDiffKind = ExpectActualMemberDiffKt.toMemberDiffKind((ExpectActualCompatibility.Incompatible) expectActualCompatibility);
                            if (memberDiffKind == null) {
                                memberDiffKind = ExpectActualMemberDiff.Kind.Unknown;
                            }
                        }
                        if (memberDiffKind != null) {
                            arrayList3.add(memberDiffKind);
                        }
                    }
                    arrayListEmptyList = arrayList3;
                }
                List list4 = arrayListEmptyList;
                ClassDescriptor classDescriptor5 = classDescriptor;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
                Iterator it3 = list4.iterator();
                while (it3.hasNext()) {
                    arrayList4.add(new ExpectActualMemberDiff((ExpectActualMemberDiff.Kind) it3.next(), actualMember, classDescriptor5));
                }
                return arrayList4;
            }
        }));
    }

    private static final Sequence<CallableMemberDescriptor> extractNonPrivateCallables(final MemberScope memberScope) {
        return SequencesKt.filter(SequencesKt.plus(SequencesKt.flatMapIterable(CollectionsKt.asSequence(memberScope.getFunctionNames()), new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.checkers.ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierCheckerKt$extractNonPrivateCallables$functions$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<? extends SimpleFunctionDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return memberScope.getContributedFunctions(it, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
            }
        }), SequencesKt.flatMapIterable(CollectionsKt.asSequence(memberScope.getVariableNames()), new Function1<Name, Collection<? extends PropertyDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.checkers.ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierCheckerKt$extractNonPrivateCallables$properties$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<? extends PropertyDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return memberScope.getContributedVariables(it, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
            }
        })), new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.ActualClassifierMustHasTheSameMembersAsNonFinalExpectClassifierCheckerKt.extractNonPrivateCallables.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!Visibilities.INSTANCE.isPrivate(it.getVisibility().getDelegate()));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Kind getFunctionVsPropertyKind(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor instanceof PropertyDescriptor) {
            return Kind.PROPERTY;
        }
        if (callableMemberDescriptor instanceof FunctionDescriptor) {
            return Kind.FUNCTION;
        }
        throw new IllegalStateException(("Unknown kind " + callableMemberDescriptor).toString());
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0024  */
    private static final KtCallableDeclaration getPsiIfReal(CallableMemberDescriptor callableMemberDescriptor) {
        KtElement psi;
        SourceElement source;
        if (!callableMemberDescriptor.getKind().isReal()) {
            callableMemberDescriptor = null;
        }
        if (callableMemberDescriptor == null || (source = callableMemberDescriptor.getSourceElement()) == null) {
            psi = null;
        } else {
            KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
            if (kotlinSourceElement != null) {
                psi = kotlinSourceElement.getPsi();
            } else {
                psi = null;
            }
        }
        if (psi instanceof KtCallableDeclaration) {
            return (KtCallableDeclaration) psi;
        }
        return null;
    }

    private static final void reportIfPossible(BindingTrace bindingTrace, ExpectActualMemberDiff<? extends CallableMemberDescriptor, ? extends ClassDescriptor> expectActualMemberDiff) {
        Diagnostic diagnosticOn;
        KtPropertyAccessor setter;
        KtCallableDeclaration psiIfReal = getPsiIfReal(expectActualMemberDiff.getActualMember());
        if (psiIfReal == null) {
            return;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[expectActualMemberDiff.getKind().ordinal()]) {
            case 1:
                diagnosticOn = Errors.RETURN_TYPE_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 2:
                diagnosticOn = Errors.MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 3:
                diagnosticOn = Errors.VISIBILITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 4:
                DiagnosticFactory1<KtPropertyAccessor, ExpectActualMemberDiff<CallableMemberDescriptor, ClassDescriptor>> diagnosticFactory1 = Errors.SETTER_VISIBILITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING;
                KtProperty ktProperty = psiIfReal instanceof KtProperty ? (KtProperty) psiIfReal : null;
                if (ktProperty == null || (setter = ktProperty.getSetter()) == null) {
                    return;
                }
                diagnosticOn = diagnosticFactory1.on(setter, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 5:
                diagnosticOn = Errors.PARAMETER_NAME_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 6:
                diagnosticOn = Errors.PROPERTY_KIND_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 7:
                diagnosticOn = Errors.LATEINIT_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 8:
                diagnosticOn = Errors.VARARG_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 9:
                diagnosticOn = Errors.TYPE_PARAMETER_NAMES_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            case 10:
                diagnosticOn = Errors.UNKNOWN_PROBLEM_DURING_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING.on(psiIfReal, expectActualMemberDiff);
                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        bindingTrace.report(diagnosticOn);
    }
}
