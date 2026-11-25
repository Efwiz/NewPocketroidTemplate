package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirEnumWhenTrackerComponent;
import org.jetbrains.kotlin.fir.FirEnumWhenTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatus;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirElseIfTrueCondition;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00182\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0002J)\u0010\u0010\u001a\u0002H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u0002H\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u0015J\u001a\u0010\u0016\u001a\u00020\u00172\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirWhenExhaustivenessTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "bodyResolveComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "(Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;)V", "computeStatusForNonIntersectionType", "Lorg/jetbrains/kotlin/fir/expressions/ExhaustivenessStatus;", "unwrappedSubjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "processExhaustivenessCheck", "", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformWhenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirWhenExhaustivenessTransformer extends FirTransformer<Object> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<WhenExhaustivenessChecker> exhaustivenessCheckers = CollectionsKt.listOf((Object[]) new WhenExhaustivenessChecker[]{WhenOnBooleanExhaustivenessChecker.INSTANCE, WhenOnEnumExhaustivenessChecker.INSTANCE, WhenOnSealedClassExhaustivenessChecker.INSTANCE, WhenOnNothingExhaustivenessChecker.INSTANCE});
    private final BodyResolveComponents bodyResolveComponents;

    /* JADX INFO: compiled from: FirWhenExhaustivenessTransformer.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J8\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00070\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0012\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u000eH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirWhenExhaustivenessTransformer$Companion;", "", "()V", "exhaustivenessCheckers", "", "Lorg/jetbrains/kotlin/fir/resolve/transformers/WhenExhaustivenessChecker;", "computeAllMissingCases", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "getCheckers", "subjectType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getSubjectType", "collectMissingCases", "", "", "checkers", "unwrapIntersectionType", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<WhenMissingCase> computeAllMissingCases(FirSession session, FirWhenExpression whenExpression) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
            ConeKotlinType subjectType = getSubjectType(session, whenExpression);
            if (subjectType == null) {
                return CollectionsKt.emptyList();
            }
            List<WhenMissingCase> listCreateListBuilder = CollectionsKt.createListBuilder();
            for (ConeKotlinType coneKotlinType : FirWhenExhaustivenessTransformer.INSTANCE.unwrapIntersectionType(subjectType)) {
                FirWhenExhaustivenessTransformer.INSTANCE.collectMissingCases(listCreateListBuilder, FirWhenExhaustivenessTransformer.INSTANCE.getCheckers(coneKotlinType, session), whenExpression, coneKotlinType, session);
            }
            return CollectionsKt.build(listCreateListBuilder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ConeKotlinType getSubjectType(FirSession session, FirWhenExpression whenExpression) {
            FirTypeRef typeRef;
            ConeKotlinType coneType;
            FirTypeRef returnTypeRef;
            FirVariable subjectVariable = whenExpression.getSubjectVariable();
            if (subjectVariable == null || (returnTypeRef = subjectVariable.getReturnTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(returnTypeRef)) == null) {
                FirExpression subject = whenExpression.getSubject();
                if (subject == null || (typeRef = subject.getTypeRef()) == null) {
                    return null;
                }
                coneType = FirTypeUtilsKt.getConeType(typeRef);
            }
            return ConeTypeUtilsKt.lowerBoundIfFlexible(TypeExpansionUtilsKt.fullyExpandedType(coneType, session));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Collection<ConeKotlinType> unwrapIntersectionType(ConeKotlinType coneKotlinType) {
            Collection<ConeKotlinType> intersectedTypes;
            ConeIntersectionType coneIntersectionType = coneKotlinType instanceof ConeIntersectionType ? (ConeIntersectionType) coneKotlinType : null;
            return (coneIntersectionType == null || (intersectedTypes = coneIntersectionType.getIntersectedTypes()) == null) ? CollectionsKt.listOf(coneKotlinType) : intersectedTypes;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<WhenExhaustivenessChecker> getCheckers(ConeKotlinType subjectType, FirSession session) {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            for (Object obj : FirWhenExhaustivenessTransformer.exhaustivenessCheckers) {
                if (((WhenExhaustivenessChecker) obj).isApplicable(subjectType, session)) {
                    listCreateListBuilder.add(obj);
                }
            }
            if (!listCreateListBuilder.isEmpty() && ConeTypeUtilsKt.isMarkedNullable(subjectType)) {
                listCreateListBuilder.add(WhenOnNullableExhaustivenessChecker.INSTANCE);
            }
            return CollectionsKt.build(listCreateListBuilder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void collectMissingCases(List<WhenMissingCase> list, List<? extends WhenExhaustivenessChecker> list2, FirWhenExpression firWhenExpression, ConeKotlinType coneKotlinType, FirSession firSession) {
            Iterator<? extends WhenExhaustivenessChecker> it = list2.iterator();
            while (it.hasNext()) {
                it.next().computeMissingCases(firWhenExpression, coneKotlinType, firSession, list);
            }
            if (list.isEmpty() && firWhenExpression.getBranches().isEmpty()) {
                list.add(WhenMissingCase.Unknown.INSTANCE);
            }
        }
    }

    public FirWhenExhaustivenessTransformer(BodyResolveComponents bodyResolveComponents) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "bodyResolveComponents");
        this.bodyResolveComponents = bodyResolveComponents;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new IllegalArgumentException("Should not be there");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhenExpression(FirWhenExpression whenExpression, Object data) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        processExhaustivenessCheck(whenExpression);
        FirEnumWhenTrackerComponent enumWhenTracker = FirEnumWhenTrackerComponentKt.getEnumWhenTracker(this.bodyResolveComponents.getSession());
        if (enumWhenTracker != null) {
            KtSourceFile sourceFile = this.bodyResolveComponents.getFile().getSourceFile();
            FirEnumWhenTrackerComponentKt.reportEnumUsageInWhen(enumWhenTracker, sourceFile != null ? sourceFile.getPath() : null, INSTANCE.getSubjectType(this.bodyResolveComponents.getSession(), whenExpression));
        }
        return whenExpression;
    }

    private final void processExhaustivenessCheck(FirWhenExpression whenExpression) {
        List<FirWhenBranch> branches = whenExpression.getBranches();
        if (!(branches instanceof Collection) || !branches.isEmpty()) {
            Iterator<T> it = branches.iterator();
            while (it.hasNext()) {
                if (((FirWhenBranch) it.next()).getCondition() instanceof FirElseIfTrueCondition) {
                    whenExpression.replaceExhaustivenessStatus(ExhaustivenessStatus.ProperlyExhaustive.INSTANCE);
                    return;
                }
            }
        }
        FirSession session = this.bodyResolveComponents.getSession();
        Companion companion = INSTANCE;
        ConeKotlinType subjectType = companion.getSubjectType(session, whenExpression);
        if (subjectType == null) {
            whenExpression.replaceExhaustivenessStatus(ExhaustivenessStatus.NotExhaustive.INSTANCE.getNO_ELSE_BRANCH());
            return;
        }
        if (whenExpression.getBranches().isEmpty() && ConeBuiltinTypeUtilsKt.isNothing(subjectType)) {
            whenExpression.replaceExhaustivenessStatus(ExhaustivenessStatus.ExhaustiveAsNothing.INSTANCE);
            return;
        }
        ExhaustivenessStatus.NotExhaustive no_else_branch = ExhaustivenessStatus.NotExhaustive.INSTANCE.getNO_ELSE_BRANCH();
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(subjectType, session);
        if (regularClassSymbol == null || !regularClassSymbol.getRawStatus().isExpect()) {
            Iterator it2 = companion.unwrapIntersectionType(subjectType).iterator();
            while (it2.hasNext()) {
                ExhaustivenessStatus exhaustivenessStatusComputeStatusForNonIntersectionType = computeStatusForNonIntersectionType((ConeKotlinType) it2.next(), session, whenExpression);
                if (exhaustivenessStatusComputeStatusForNonIntersectionType == ExhaustivenessStatus.ProperlyExhaustive.INSTANCE) {
                    no_else_branch = exhaustivenessStatusComputeStatusForNonIntersectionType;
                    break;
                } else if (exhaustivenessStatusComputeStatusForNonIntersectionType != ExhaustivenessStatus.NotExhaustive.INSTANCE.getNO_ELSE_BRANCH() && no_else_branch == ExhaustivenessStatus.NotExhaustive.INSTANCE.getNO_ELSE_BRANCH()) {
                    no_else_branch = exhaustivenessStatusComputeStatusForNonIntersectionType;
                }
            }
        }
        whenExpression.replaceExhaustivenessStatus(no_else_branch);
    }

    private final ExhaustivenessStatus computeStatusForNonIntersectionType(ConeKotlinType unwrappedSubjectType, FirSession session, FirWhenExpression whenExpression) {
        Companion companion = INSTANCE;
        List checkers = companion.getCheckers(unwrappedSubjectType, session);
        if (checkers.isEmpty()) {
            return ExhaustivenessStatus.NotExhaustive.INSTANCE.getNO_ELSE_BRANCH();
        }
        ArrayList arrayList = new ArrayList();
        companion.collectMissingCases(arrayList, checkers, whenExpression, unwrappedSubjectType, session);
        if (arrayList.isEmpty()) {
            return ExhaustivenessStatus.ProperlyExhaustive.INSTANCE;
        }
        return new ExhaustivenessStatus.NotExhaustive(arrayList);
    }
}
