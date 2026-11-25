package org.jetbrains.kotlin.resolve.calls.results;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tower.TowerUtilsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.util.CancellationChecker;

/* JADX INFO: loaded from: classes6.dex */
public class ResolutionResultsHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final OverloadingConflictResolver<ResolvedCall<?>> overloadingConflictResolver;

    /* JADX WARN: Code duplicated, block: B:11:0x001e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 10 && i != 11 && i != 29 && i != 30 && i != 35 && i != 36) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 10 && i != 11 && i != 29 && i != 30 && i != 35 && i != 36) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "module";
                break;
            case 2:
                objArr[0] = "specificityComparator";
                break;
            case 3:
                objArr[0] = "platformOverloadsSpecificityComparator";
                break;
            case 4:
                objArr[0] = "cancellationChecker";
                break;
            case 5:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 6:
            case 12:
                objArr[0] = "context";
                break;
            case 7:
            case 13:
            case 21:
            case 26:
                objArr[0] = "tracing";
                break;
            case 8:
            case 28:
            case 32:
                objArr[0] = "candidates";
                break;
            case 9:
            case 17:
            case 25:
            case 34:
                objArr[0] = "languageVersionSettings";
                break;
            case 10:
            case 11:
            case 18:
            case 19:
            case 20:
            case 29:
            case 30:
            case 35:
            case 36:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/results/ResolutionResultsHandler";
                break;
            case 14:
                objArr[0] = "successfulCandidates";
                break;
            case 15:
                objArr[0] = "incompleteCandidates";
                break;
            case 16:
            case 24:
            case 33:
                objArr[0] = "checkArgumentsMode";
                break;
            case 22:
            case 27:
                objArr[0] = "trace";
                break;
            case 23:
                objArr[0] = "failedCandidates";
                break;
            case 31:
                objArr[0] = "results";
                break;
            default:
                objArr[0] = "builtIns";
                break;
        }
        if (i != 10 && i != 11) {
            if (i != 29 && i != 30) {
                if (i != 35 && i != 36) {
                    switch (i) {
                        case 18:
                        case 19:
                        case 20:
                            objArr[1] = "computeSuccessfulResult";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/resolve/calls/results/ResolutionResultsHandler";
                            break;
                    }
                } else {
                    objArr[1] = "chooseAndReportMaximallySpecific";
                }
            } else {
                objArr[1] = "recordFailedInfo";
            }
        } else {
            objArr[1] = "computeResultAndReportErrors";
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "computeResultAndReportErrors";
                break;
            case 10:
            case 11:
            case 18:
            case 19:
            case 20:
            case 29:
            case 30:
            case 35:
            case 36:
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[2] = "computeSuccessfulResult";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[2] = "computeFailedResult";
                break;
            case 26:
            case 27:
            case 28:
                objArr[2] = "recordFailedInfo";
                break;
            case 31:
                objArr[2] = "allIncomplete";
                break;
            case 32:
            case 33:
            case 34:
                objArr[2] = "chooseAndReportMaximallySpecific";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 11 && i != 29 && i != 30 && i != 35 && i != 36) {
            switch (i) {
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public ResolutionResultsHandler(KotlinBuiltIns kotlinBuiltIns, ModuleDescriptor moduleDescriptor, TypeSpecificityComparator typeSpecificityComparator, PlatformOverloadsSpecificityComparator platformOverloadsSpecificityComparator, CancellationChecker cancellationChecker, KotlinTypeRefiner kotlinTypeRefiner) {
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(0);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (typeSpecificityComparator == null) {
            $$$reportNull$$$0(2);
        }
        if (platformOverloadsSpecificityComparator == null) {
            $$$reportNull$$$0(3);
        }
        if (cancellationChecker == null) {
            $$$reportNull$$$0(4);
        }
        if (kotlinTypeRefiner == null) {
            $$$reportNull$$$0(5);
        }
        this.overloadingConflictResolver = FlatSignatureForResolvedCallKt.createOverloadingConflictResolver(kotlinBuiltIns, moduleDescriptor, typeSpecificityComparator, platformOverloadsSpecificityComparator, cancellationChecker, kotlinTypeRefiner);
    }

    public <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> computeResultAndReportErrors(CallResolutionContext callResolutionContext, TracingStrategy tracingStrategy, Collection<MutableResolvedCall<D>> collection, LanguageVersionSettings languageVersionSettings) {
        if (callResolutionContext == null) {
            $$$reportNull$$$0(6);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(7);
        }
        if (collection == null) {
            $$$reportNull$$$0(8);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(9);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        LinkedHashSet linkedHashSet4 = new LinkedHashSet();
        for (MutableResolvedCall<D> mutableResolvedCall : collection) {
            ResolutionStatus status = mutableResolvedCall.getStatus();
            if (status.isSuccess()) {
                linkedHashSet.add(mutableResolvedCall);
            } else if (status == ResolutionStatus.INCOMPLETE_TYPE_INFERENCE) {
                linkedHashSet3.add(mutableResolvedCall);
            } else if (mutableResolvedCall.getStatus() == ResolutionStatus.RECEIVER_TYPE_ERROR) {
                linkedHashSet4.add(mutableResolvedCall);
            } else if (mutableResolvedCall.getStatus() != ResolutionStatus.RECEIVER_PRESENCE_ERROR) {
                linkedHashSet2.add(mutableResolvedCall);
            }
        }
        if (!linkedHashSet.isEmpty() || !linkedHashSet3.isEmpty()) {
            return computeSuccessfulResult(callResolutionContext, tracingStrategy, linkedHashSet, linkedHashSet3, callResolutionContext.checkArguments, languageVersionSettings);
        }
        if (!linkedHashSet2.isEmpty()) {
            return computeFailedResult(tracingStrategy, callResolutionContext.trace, linkedHashSet2, callResolutionContext.checkArguments, languageVersionSettings);
        }
        if (!linkedHashSet4.isEmpty()) {
            tracingStrategy.unresolvedReferenceWrongReceiver(callResolutionContext.trace, linkedHashSet4);
            OverloadResolutionResultsImpl<D> overloadResolutionResultsImplCandidatesWithWrongReceiver = OverloadResolutionResultsImpl.candidatesWithWrongReceiver(linkedHashSet4);
            if (overloadResolutionResultsImplCandidatesWithWrongReceiver == null) {
                $$$reportNull$$$0(10);
            }
            return overloadResolutionResultsImplCandidatesWithWrongReceiver;
        }
        tracingStrategy.unresolvedReference(callResolutionContext.trace);
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplNameNotFound = OverloadResolutionResultsImpl.nameNotFound();
        if (overloadResolutionResultsImplNameNotFound == null) {
            $$$reportNull$$$0(11);
        }
        return overloadResolutionResultsImplNameNotFound;
    }

    private <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> computeSuccessfulResult(CallResolutionContext<?> callResolutionContext, TracingStrategy tracingStrategy, Set<MutableResolvedCall<D>> set, Set<MutableResolvedCall<D>> set2, CheckArgumentTypesMode checkArgumentTypesMode, LanguageVersionSettings languageVersionSettings) {
        if (callResolutionContext == null) {
            $$$reportNull$$$0(12);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(13);
        }
        if (set == null) {
            $$$reportNull$$$0(14);
        }
        if (set2 == null) {
            $$$reportNull$$$0(15);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(16);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(17);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(set);
        linkedHashSet.addAll(set2);
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplChooseAndReportMaximallySpecific = chooseAndReportMaximallySpecific(linkedHashSet, true, checkArgumentTypesMode, languageVersionSettings);
        if (overloadResolutionResultsImplChooseAndReportMaximallySpecific.isSingleResult()) {
            MutableResolvedCall<D> mutableResolvedCallMo7436getResultingCall = overloadResolutionResultsImplChooseAndReportMaximallySpecific.mo7436getResultingCall();
            mutableResolvedCallMo7436getResultingCall.getTrace().moveAllMyDataTo(callResolutionContext.trace);
            if (mutableResolvedCallMo7436getResultingCall.getStatus() == ResolutionStatus.INCOMPLETE_TYPE_INFERENCE) {
                OverloadResolutionResultsImpl<D> overloadResolutionResultsImplIncompleteTypeInference = OverloadResolutionResultsImpl.incompleteTypeInference(mutableResolvedCallMo7436getResultingCall);
                if (overloadResolutionResultsImplIncompleteTypeInference == null) {
                    $$$reportNull$$$0(18);
                }
                return overloadResolutionResultsImplIncompleteTypeInference;
            }
        }
        if (overloadResolutionResultsImplChooseAndReportMaximallySpecific.isAmbiguity()) {
            tracingStrategy.recordAmbiguity(callResolutionContext.trace, overloadResolutionResultsImplChooseAndReportMaximallySpecific.getResultingCalls());
            boolean zAllIncomplete = allIncomplete(overloadResolutionResultsImplChooseAndReportMaximallySpecific.getResultingCalls());
            if (callResolutionContext.checkArguments != CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS || !CallUtilKt.hasUnresolvedArguments(callResolutionContext.call, callResolutionContext)) {
                if (zAllIncomplete) {
                    tracingStrategy.cannotCompleteResolve(callResolutionContext.trace, overloadResolutionResultsImplChooseAndReportMaximallySpecific.getResultingCalls());
                } else {
                    tracingStrategy.ambiguity(callResolutionContext.trace, overloadResolutionResultsImplChooseAndReportMaximallySpecific.getResultingCalls());
                }
            }
            if (zAllIncomplete) {
                OverloadResolutionResultsImpl<D> overloadResolutionResultsImplIncompleteTypeInference2 = OverloadResolutionResultsImpl.incompleteTypeInference(overloadResolutionResultsImplChooseAndReportMaximallySpecific.getResultingCalls());
                if (overloadResolutionResultsImplIncompleteTypeInference2 == null) {
                    $$$reportNull$$$0(19);
                }
                return overloadResolutionResultsImplIncompleteTypeInference2;
            }
        }
        if (overloadResolutionResultsImplChooseAndReportMaximallySpecific == null) {
            $$$reportNull$$$0(20);
        }
        return overloadResolutionResultsImplChooseAndReportMaximallySpecific;
    }

    private <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> computeFailedResult(TracingStrategy tracingStrategy, BindingTrace bindingTrace, Set<MutableResolvedCall<D>> set, CheckArgumentTypesMode checkArgumentTypesMode, LanguageVersionSettings languageVersionSettings) {
        if (tracingStrategy == null) {
            $$$reportNull$$$0(21);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(22);
        }
        if (set == null) {
            $$$reportNull$$$0(23);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(24);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(25);
        }
        if (set.size() == 1) {
            return recordFailedInfo(tracingStrategy, bindingTrace, set);
        }
        for (EnumSet<ResolutionStatus> enumSet : ResolutionStatus.SEVERITY_LEVELS) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (MutableResolvedCall<D> mutableResolvedCall : set) {
                if (enumSet.contains(mutableResolvedCall.getStatus())) {
                    linkedHashSet.add(mutableResolvedCall);
                }
            }
            if (!linkedHashSet.isEmpty()) {
                if (enumSet.contains(ResolutionStatus.ARGUMENTS_MAPPING_ERROR)) {
                    return recordFailedInfo(tracingStrategy, bindingTrace, this.overloadingConflictResolver.filterOutEquivalentCalls(new LinkedHashSet(linkedHashSet)));
                }
                return recordFailedInfo(tracingStrategy, bindingTrace, chooseAndReportMaximallySpecific(linkedHashSet, false, checkArgumentTypesMode, languageVersionSettings).getResultingCalls());
            }
        }
        throw new AssertionError("Should not be reachable, cause every status must belong to some level: " + set);
    }

    private static <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> recordFailedInfo(TracingStrategy tracingStrategy, BindingTrace bindingTrace, Collection<MutableResolvedCall<D>> collection) {
        if (tracingStrategy == null) {
            $$$reportNull$$$0(26);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(27);
        }
        if (collection == null) {
            $$$reportNull$$$0(28);
        }
        if (collection.size() == 1) {
            MutableResolvedCall<D> next = collection.iterator().next();
            next.getTrace().moveAllMyDataTo(bindingTrace);
            OverloadResolutionResultsImpl<D> overloadResolutionResultsImplSingleFailedCandidate = OverloadResolutionResultsImpl.singleFailedCandidate(next);
            if (overloadResolutionResultsImplSingleFailedCandidate == null) {
                $$$reportNull$$$0(29);
            }
            return overloadResolutionResultsImplSingleFailedCandidate;
        }
        tracingStrategy.noneApplicable(bindingTrace, collection);
        tracingStrategy.recordAmbiguity(bindingTrace, collection);
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplManyFailedCandidates = OverloadResolutionResultsImpl.manyFailedCandidates(collection);
        if (overloadResolutionResultsImplManyFailedCandidates == null) {
            $$$reportNull$$$0(30);
        }
        return overloadResolutionResultsImplManyFailedCandidates;
    }

    private static <D extends CallableDescriptor> boolean allIncomplete(Collection<MutableResolvedCall<D>> collection) {
        if (collection == null) {
            $$$reportNull$$$0(31);
        }
        Iterator<MutableResolvedCall<D>> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next().getStatus() != ResolutionStatus.INCOMPLETE_TYPE_INFERENCE) {
                return false;
            }
        }
        return true;
    }

    private <D extends CallableDescriptor> OverloadResolutionResultsImpl<D> chooseAndReportMaximallySpecific(Set<MutableResolvedCall<D>> set, boolean z, CheckArgumentTypesMode checkArgumentTypesMode, LanguageVersionSettings languageVersionSettings) {
        if (set == null) {
            $$$reportNull$$$0(32);
        }
        if (checkArgumentTypesMode == null) {
            $$$reportNull$$$0(33);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(34);
        }
        OverloadingConflictResolver<ResolvedCall<?>> overloadingConflictResolver = this.overloadingConflictResolver;
        if (!languageVersionSettings.supportsFeature(LanguageFeature.RefinedSamAdaptersPriority)) {
            HashSet hashSet = new HashSet();
            for (MutableResolvedCall<D> mutableResolvedCall : set) {
                if (!TowerUtilsKt.isSynthesized(mutableResolvedCall.get$suspendPropertyDescriptor())) {
                    hashSet.add(mutableResolvedCall);
                }
            }
            if (!hashSet.isEmpty()) {
                set = hashSet;
            }
        }
        Set<ResolvedCall<?>> setChooseMaximallySpecificCandidates = overloadingConflictResolver.chooseMaximallySpecificCandidates(set, checkArgumentTypesMode, z);
        if (setChooseMaximallySpecificCandidates.size() > 1) {
            setChooseMaximallySpecificCandidates = (Set) setChooseMaximallySpecificCandidates.stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.resolve.calls.results.ResolutionResultsHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ResolutionResultsHandler.lambda$chooseAndReportMaximallySpecific$0((MutableResolvedCall) obj);
                }
            }).collect(Collectors.toSet());
        }
        if (setChooseMaximallySpecificCandidates.size() == 1) {
            OverloadResolutionResultsImpl<D> overloadResolutionResultsImplSuccess = OverloadResolutionResultsImpl.success((MutableResolvedCall) setChooseMaximallySpecificCandidates.iterator().next());
            if (overloadResolutionResultsImplSuccess == null) {
                $$$reportNull$$$0(35);
            }
            return overloadResolutionResultsImplSuccess;
        }
        OverloadResolutionResultsImpl<D> overloadResolutionResultsImplAmbiguity = OverloadResolutionResultsImpl.ambiguity(setChooseMaximallySpecificCandidates);
        if (overloadResolutionResultsImplAmbiguity == null) {
            $$$reportNull$$$0(36);
        }
        return overloadResolutionResultsImplAmbiguity;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.descriptors.CallableDescriptor] */
    static /* synthetic */ boolean lambda$chooseAndReportMaximallySpecific$0(MutableResolvedCall mutableResolvedCall) {
        return !mutableResolvedCall.get$suspendPropertyDescriptor().getAnnotations().hasAnnotation(AnnotationsForResolveKt.getOVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION_FQ_NAME());
    }
}
