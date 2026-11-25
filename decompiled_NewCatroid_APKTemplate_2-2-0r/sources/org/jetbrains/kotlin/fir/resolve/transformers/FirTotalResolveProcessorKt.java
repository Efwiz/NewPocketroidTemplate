package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirBodyResolveProcessor;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirImplicitTypeBodyResolveProcessor;
import org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirContractResolveProcessor;
import org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatcherProcessor;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirAnnotationArgumentsMappingProcessor;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirAnnotationArgumentsResolveProcessor;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirCompanionGenerationProcessor;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirCompilerRequiredAnnotationsResolveProcessor;

/* JADX INFO: compiled from: FirTotalResolveProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001aD\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\b\b\u0000\u0010\b*\u00020\u00022\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u001d\u0010\t\u001a\u0019\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\b0\n¢\u0006\u0002\b\fH\u0082\b\u001a\u001a\u0010\r\u001a\u00020\u0002*\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u000e"}, d2 = {"createAllCompilerResolveProcessors", "", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirResolveProcessor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "createAllResolveProcessors", ExifInterface.GPS_DIRECTION_TRUE, "creator", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "Lkotlin/ExtensionFunctionType;", "createCompilerProcessorByPhase", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTotalResolveProcessorKt {

    /* JADX INFO: compiled from: FirTotalResolveProcessor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirResolvePhase.values().length];
            try {
                iArr[FirResolvePhase.RAW_FIR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirResolvePhase.COMPANION_GENERATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirResolvePhase.IMPORTS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FirResolvePhase.SUPER_TYPES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[FirResolvePhase.SEALED_CLASS_INHERITORS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[FirResolvePhase.TYPES.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[FirResolvePhase.STATUS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[FirResolvePhase.ARGUMENTS_OF_ANNOTATIONS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[FirResolvePhase.CONTRACTS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[FirResolvePhase.IMPLICIT_TYPES_BODY_RESOLVE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[FirResolvePhase.ANNOTATIONS_ARGUMENTS_MAPPING.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[FirResolvePhase.BODY_RESOLVE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[FirResolvePhase.EXPECT_ACTUAL_MATCHING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ List createAllCompilerResolveProcessors$default(FirSession firSession, ScopeSession scopeSession, int i, Object obj) {
        if ((i & 2) != 0) {
            scopeSession = null;
        }
        return createAllCompilerResolveProcessors(firSession, scopeSession);
    }

    public static final FirResolveProcessor createCompilerProcessorByPhase(FirResolvePhase firResolvePhase, FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        switch (WhenMappings.$EnumSwitchMapping$0[firResolvePhase.ordinal()]) {
            case 1:
                throw new IllegalArgumentException("Raw FIR building phase does not have a transformer");
            case 2:
                return new FirCompilerRequiredAnnotationsResolveProcessor(session, scopeSession);
            case 3:
                return new FirCompanionGenerationProcessor(session, scopeSession);
            case 4:
                return new FirImportResolveProcessor(session, scopeSession);
            case 5:
                return new FirSupertypeResolverProcessor(session, scopeSession);
            case 6:
                return new FirSealedClassInheritorsProcessor(session, scopeSession);
            case 7:
                return new FirTypeResolveProcessor(session, scopeSession);
            case 8:
                return new FirStatusResolveProcessor(session, scopeSession);
            case 9:
                return new FirAnnotationArgumentsResolveProcessor(session, scopeSession);
            case 10:
                return new FirContractResolveProcessor(session, scopeSession);
            case 11:
                return new FirImplicitTypeBodyResolveProcessor(session, scopeSession);
            case 12:
                return new FirAnnotationArgumentsMappingProcessor(session, scopeSession);
            case 13:
                return new FirBodyResolveProcessor(session, scopeSession);
            case 14:
                return new FirExpectActualMatcherProcessor(session, scopeSession);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final List<FirResolveProcessor> createAllCompilerResolveProcessors(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        if (scopeSession == null) {
            scopeSession = new ScopeSession();
        }
        FirResolvePhase[] firResolvePhaseArrValues = FirResolvePhase.values();
        ArrayList arrayList = new ArrayList();
        for (FirResolvePhase firResolvePhase : firResolvePhaseArrValues) {
            if (!firResolvePhase.getNoProcessor()) {
                arrayList.add(firResolvePhase);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(createCompilerProcessorByPhase((FirResolvePhase) it.next(), session, scopeSession));
        }
        return arrayList3;
    }
}
