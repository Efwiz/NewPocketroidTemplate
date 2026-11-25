package org.jetbrains.kotlin.resolve.calls.inference.components;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintSystemCompletionContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H&J:\u0010\u0018\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u00112\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u00150 JB\u0010!\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u00112\u0006\u0010\"\u001a\u00020#2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u00150 J2\u0010$\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u00112\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u00150 J\u0010\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u000bH&J\u0010\u0010'\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u000bH&J\u0010\u0010(\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u000bH&J\b\u0010)\u001a\u00020\u0019H&J'\u0010*\u001a\u0004\u0018\u0001H+\"\b\b\u0000\u0010+*\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H+0\u0011H\u0002¢\u0006\u0002\u0010,J'\u0010-\u001a\u0004\u0018\u0001H+\"\b\b\u0000\u0010+*\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H+0\u0011H\u0002¢\u0006\u0002\u0010,J'\u0010.\u001a\u0004\u0018\u0001H\u001a\"\b\b\u0000\u0010\u001a*\u00020\u001b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u0011H\u0002¢\u0006\u0002\u0010,J$\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u00020\u000b2\n\u00102\u001a\u0006\u0012\u0002\b\u000303H&J\b\u00104\u001a\u000205H&J&\u00106\u001a\u00020\u0019\"\b\b\u0000\u0010\u001a*\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001a0\u0011J\b\u00107\u001a\u00020\u0015H&J\u0016\u00108\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011*\b\u0012\u0004\u0012\u0002090\u0011R\u001e\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\tR\u001e\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000e0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\tR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$Context;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver$Context;", "()V", "allTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getAllTypeVariables", "()Ljava/util/Map;", "fixedTypeVariables", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getFixedTypeVariables", "notFixedTypeVariables", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "getNotFixedTypeVariables", "postponedTypeVariables", "", "getPostponedTypeVariables", "()Ljava/util/List;", "addError", "", "error", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "analyzeArgumentWithFixedParameterTypes", "", "A", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "postponedArguments", "analyze", "Lkotlin/Function1;", "analyzeNextReadyPostponedArgument", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "analyzeRemainingNotAnalyzedPostponedArgument", "canBeProper", ModuleXmlParser.TYPE, "containsOnlyFixedOrPostponedVariables", "containsOnlyFixedVariables", "couldBeResolvedWithUnrestrictedBuilderInference", "findPostponedArgumentWithFixedInputTypes", ExifInterface.GPS_DIRECTION_TRUE, "(Ljava/util/List;)Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "findPostponedArgumentWithFixedOrPostponedInputTypes", "findPostponedArgumentWithRevisableExpectedType", "fixVariable", "variable", "resultType", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/FixVariableConstraintPosition;", "getBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "hasLambdaToAnalyze", "resolveForkPointsConstraints", "extractUpperTypesToCheckIntersectionEmptiness", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstraintSystemCompletionContext implements ResultTypeResolver.Context, VariableFixationFinder.Context {
    public abstract void addError(ConstraintSystemError error);

    public abstract boolean canBeProper(KotlinTypeMarker type);

    public abstract boolean containsOnlyFixedOrPostponedVariables(KotlinTypeMarker type);

    public abstract boolean containsOnlyFixedVariables(KotlinTypeMarker type);

    /* JADX INFO: renamed from: couldBeResolvedWithUnrestrictedBuilderInference */
    public abstract boolean getCouldBeResolvedWithUnrestrictedBuilderInference();

    public abstract void fixVariable(TypeVariableMarker variable, KotlinTypeMarker resultType, FixVariableConstraintPosition<?> position);

    public abstract Map<TypeConstructorMarker, TypeVariableMarker> getAllTypeVariables();

    public abstract ConstraintSystemBuilder getBuilder();

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context
    public abstract Map<TypeConstructorMarker, KotlinTypeMarker> getFixedTypeVariables();

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context
    public abstract Map<TypeConstructorMarker, VariableWithConstraints> getNotFixedTypeVariables();

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder.Context
    public abstract List<TypeVariableMarker> getPostponedTypeVariables();

    public abstract void resolveForkPointsConstraints();

    public final <A extends PostponedResolvedAtomMarker> boolean analyzeArgumentWithFixedParameterTypes(LanguageVersionSettings languageVersionSettings, List<? extends A> postponedArguments, Function1<? super A, Unit> analyze) {
        PostponedResolvedAtomMarker postponedResolvedAtomMarkerFindPostponedArgumentWithFixedOrPostponedInputTypes;
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(postponedArguments, "postponedArguments");
        Intrinsics.checkNotNullParameter(analyze, "analyze");
        if (languageVersionSettings.supportsFeature(LanguageFeature.UseBuilderInferenceOnlyIfNeeded)) {
            postponedResolvedAtomMarkerFindPostponedArgumentWithFixedOrPostponedInputTypes = findPostponedArgumentWithFixedInputTypes(postponedArguments);
        } else {
            postponedResolvedAtomMarkerFindPostponedArgumentWithFixedOrPostponedInputTypes = findPostponedArgumentWithFixedOrPostponedInputTypes(postponedArguments);
        }
        if (postponedResolvedAtomMarkerFindPostponedArgumentWithFixedOrPostponedInputTypes == null) {
            return false;
        }
        analyze.invoke(postponedResolvedAtomMarkerFindPostponedArgumentWithFixedOrPostponedInputTypes);
        return true;
    }

    public final <A extends PostponedResolvedAtomMarker> boolean analyzeNextReadyPostponedArgument(LanguageVersionSettings languageVersionSettings, List<? extends A> postponedArguments, ConstraintSystemCompletionMode completionMode, Function1<? super A, Unit> analyze) {
        PostponedResolvedAtomMarker postponedResolvedAtomMarkerFindPostponedArgumentWithRevisableExpectedType;
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(postponedArguments, "postponedArguments");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(analyze, "analyze");
        if (completionMode == ConstraintSystemCompletionMode.FULL && (postponedResolvedAtomMarkerFindPostponedArgumentWithRevisableExpectedType = findPostponedArgumentWithRevisableExpectedType(postponedArguments)) != null) {
            analyze.invoke(postponedResolvedAtomMarkerFindPostponedArgumentWithRevisableExpectedType);
            return true;
        }
        return analyzeArgumentWithFixedParameterTypes(languageVersionSettings, postponedArguments, analyze);
    }

    public final <A extends PostponedResolvedAtomMarker> boolean analyzeRemainingNotAnalyzedPostponedArgument(List<? extends A> postponedArguments, Function1<? super A, Unit> analyze) {
        Object next;
        Intrinsics.checkNotNullParameter(postponedArguments, "postponedArguments");
        Intrinsics.checkNotNullParameter(analyze, "analyze");
        Iterator<T> it = postponedArguments.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((PostponedResolvedAtomMarker) next).getAnalyzed());
        PostponedResolvedAtomMarker postponedResolvedAtomMarker = (PostponedResolvedAtomMarker) next;
        if (postponedResolvedAtomMarker == null) {
            return false;
        }
        analyze.invoke(postponedResolvedAtomMarker);
        return true;
    }

    public final <A extends PostponedResolvedAtomMarker> boolean hasLambdaToAnalyze(LanguageVersionSettings languageVersionSettings, List<? extends A> postponedArguments) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(postponedArguments, "postponedArguments");
        return analyzeArgumentWithFixedParameterTypes(languageVersionSettings, postponedArguments, new Function1<A, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext.hasLambdaToAnalyze.1
            /* JADX WARN: Incorrect types in method signature: (TA;)V */
            public final void invoke(PostponedResolvedAtomMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke((PostponedResolvedAtomMarker) obj);
                return Unit.INSTANCE;
            }
        });
    }

    private final <A extends PostponedResolvedAtomMarker> A findPostponedArgumentWithRevisableExpectedType(List<? extends A> postponedArguments) {
        Object next;
        Iterator<T> it = postponedArguments.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((PostponedResolvedAtomMarker) next) instanceof PostponedAtomWithRevisableExpectedType) {
                return (A) next;
            }
        }
        next = null;
        return (A) next;
    }

    private final <T extends PostponedResolvedAtomMarker> T findPostponedArgumentWithFixedOrPostponedInputTypes(List<? extends T> postponedArguments) {
        Object next;
        Iterator<T> it = postponedArguments.iterator();
        while (it.hasNext()) {
            next = it.next();
            Collection<KotlinTypeMarker> inputTypes = ((PostponedResolvedAtomMarker) next).getInputTypes();
            if (!(inputTypes instanceof Collection) || !inputTypes.isEmpty()) {
                Iterator<T> it2 = inputTypes.iterator();
                do {
                    if (it2.hasNext()) {
                    }
                } while (containsOnlyFixedOrPostponedVariables((KotlinTypeMarker) it2.next()));
            }
            return (T) next;
        }
        next = null;
        return (T) next;
    }

    private final <T extends PostponedResolvedAtomMarker> T findPostponedArgumentWithFixedInputTypes(List<? extends T> postponedArguments) {
        Object next;
        Iterator<T> it = postponedArguments.iterator();
        while (it.hasNext()) {
            next = it.next();
            Collection<KotlinTypeMarker> inputTypes = ((PostponedResolvedAtomMarker) next).getInputTypes();
            if (!(inputTypes instanceof Collection) || !inputTypes.isEmpty()) {
                Iterator<T> it2 = inputTypes.iterator();
                do {
                    if (it2.hasNext()) {
                    }
                } while (containsOnlyFixedVariables((KotlinTypeMarker) it2.next()));
            }
            return (T) next;
        }
        next = null;
        return (T) next;
    }

    public final List<KotlinTypeMarker> extractUpperTypesToCheckIntersectionEmptiness(List<Constraint> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            Constraint constraint = (Constraint) obj;
            if (constraint.getKind() == ConstraintKind.UPPER && !contains(constraint.getType(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext$extractUpperTypesToCheckIntersectionEmptiness$1$1
                {
                    super(1);
                }

                /* JADX WARN: Code duplicated, block: B:7:0x001f  */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker it) {
                    boolean z;
                    Intrinsics.checkNotNullParameter(it, "it");
                    ConstraintSystemCompletionContext constraintSystemCompletionContext = this.this$0;
                    if (constraintSystemCompletionContext.isClassTypeConstructor(constraintSystemCompletionContext.typeConstructor(it))) {
                        z = false;
                    } else {
                        ConstraintSystemCompletionContext constraintSystemCompletionContext2 = this.this$0;
                        if (constraintSystemCompletionContext2.isTypeParameterTypeConstructor(constraintSystemCompletionContext2.typeConstructor(it))) {
                            z = false;
                        } else {
                            z = true;
                        }
                    }
                    return Boolean.valueOf(z);
                }
            })) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((Constraint) it.next()).getType());
        }
        return arrayList3;
    }
}
