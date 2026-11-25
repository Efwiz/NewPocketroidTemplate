package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a6\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00030\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\f\u0010\f\u001a\u00020\r*\u00020\u0005H\u0002\u001a\u000e\u0010\u000e\u001a\u00020\r*\u0004\u0018\u00010\tH\u0002\u001a\f\u0010\u000f\u001a\u00020\r*\u00020\tH\u0002\u001a\u0014\u0010\u0010\u001a\u00020\u0011*\u00020\u00122\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a$\u0010\u0013\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"DYNAMIC_EXTENSION_ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "findClosestMatchingReceivers", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverDescription;", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "receiverGroups", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "isJavaApplicableCandidate", "", "isSuperCall", "isSuperReferenceExpression", "markCandidateForCompatibilityResolve", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "prepareReceivers", "argumentExtensionReceiver", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolutionStagesKt {
    private static final ClassId DYNAMIC_EXTENSION_ANNOTATION_CLASS_ID;

    /* JADX INFO: Access modifiers changed from: private */
    public static final ReceiverDescription prepareReceivers(Candidate candidate, FirExpression firExpression, ConeKotlinType coneKotlinType, ResolutionContext resolutionContext) {
        return new ReceiverDescription(firExpression, ArgumentsKt.prepareCapturedType(candidate, ArgumentsKt.captureFromTypeParameterUpperBoundIfNeeded(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()), coneKotlinType, resolutionContext.getSession()), resolutionContext));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<ReceiverDescription> findClosestMatchingReceivers(Candidate candidate, ConeKotlinType coneKotlinType, List<? extends List<? extends FirExpression>> list, ResolutionContext resolutionContext) {
        for (List<? extends FirExpression> list2 : list) {
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(prepareReceivers(candidate, (FirExpression) it.next(), coneKotlinType, resolutionContext));
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (ConstraintSystemBuilderKt.isSubtypeConstraintCompatible(candidate.getSystem(), ((ReceiverDescription) obj).getType(), coneKotlinType, SimpleConstraintSystemConstraintPosition.INSTANCE)) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = arrayList2;
            if (!arrayList3.isEmpty()) {
                return arrayList3;
            }
        }
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isSuperCall(FirExpression firExpression) {
        if (firExpression instanceof FirQualifiedAccessExpression) {
            return ((FirQualifiedAccessExpression) firExpression).getCalleeReference() instanceof FirSuperReference;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isSuperReferenceExpression(FirExpression firExpression) {
        if (firExpression instanceof FirQualifiedAccessExpression) {
            return ((FirQualifiedAccessExpression) firExpression).getCalleeReference() instanceof FirSuperReference;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isJavaApplicableCandidate(Candidate candidate) {
        ImportedFromObjectOrStaticData importedFromObjectOrStaticData;
        FirCallableDeclaration original;
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        FirFunctionSymbol firFunctionSymbol = symbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) symbol : null;
        if (firFunctionSymbol == null) {
            return false;
        }
        FirFunctionSymbol firFunctionSymbol2 = firFunctionSymbol;
        FirDeclarationOrigin origin = firFunctionSymbol2.getOrigin();
        if (!(origin instanceof FirDeclarationOrigin.Java) && !Intrinsics.areEqual(origin, FirDeclarationOrigin.Enhancement.INSTANCE)) {
            D fir = firFunctionSymbol2.getFir();
            FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
            if (firCallableDeclaration == null || (importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firCallableDeclaration)) == null || (original = importedFromObjectOrStaticData.getOriginal()) == null || !DeclarationUtilsKt.isJavaOrEnhancement(original)) {
                if (!(candidate.getOriginScope() instanceof FirTypeScope) || !(firFunctionSymbol instanceof FirNamedFunctionSymbol)) {
                    return false;
                }
                final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                FirTypeScopeKt.processOverriddenFunctions((FirTypeScope) candidate.getOriginScope(), (FirNamedFunctionSymbol) firFunctionSymbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.ResolutionStagesKt.isJavaApplicableCandidate.1
                    {
                        super(1);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                        ImportedFromObjectOrStaticData importedFromObjectOrStaticData2;
                        FirCallableDeclaration original2;
                        Intrinsics.checkNotNullParameter(it, "it");
                        FirNamedFunctionSymbol firNamedFunctionSymbol = it;
                        FirDeclarationOrigin origin2 = firNamedFunctionSymbol.getOrigin();
                        if (!(origin2 instanceof FirDeclarationOrigin.Java) && !Intrinsics.areEqual(origin2, FirDeclarationOrigin.Enhancement.INSTANCE)) {
                            D fir2 = firNamedFunctionSymbol.getFir();
                            FirCallableDeclaration firCallableDeclaration2 = fir2 instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir2 : null;
                            if (firCallableDeclaration2 == null || (importedFromObjectOrStaticData2 = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firCallableDeclaration2)) == null || (original2 = importedFromObjectOrStaticData2.getOriginal()) == null || !DeclarationUtilsKt.isJavaOrEnhancement(original2)) {
                                return ProcessorAction.NEXT;
                            }
                        }
                        booleanRef.element = true;
                        return ProcessorAction.STOP;
                    }
                });
                return booleanRef.element;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void markCandidateForCompatibilityResolve(CheckerSink checkerSink, ResolutionContext resolutionContext) {
        if (FirLanguageSettingsComponentKt.getLanguageVersionSettings(resolutionContext.getSession()).supportsFeature(LanguageFeature.DisableCompatibilityModeForNewInference)) {
            return;
        }
        checkerSink.reportDiagnostic(LowerPriorityToPreserveCompatibilityDiagnostic.INSTANCE);
    }

    static {
        ClassId classId = ClassId.topLevel(AnnotationsForResolveKt.getDYNAMIC_EXTENSION_FQ_NAME());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        DYNAMIC_EXTENSION_ANNOTATION_CLASS_ID = classId;
    }
}
