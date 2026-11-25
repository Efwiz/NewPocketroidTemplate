package org.jetbrains.kotlin.backend.common.actualizer;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExpectActualCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0017B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J$\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u0010H\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ExpectActualCollector;", "", "mainFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "dependentFragments", "", "typeSystemContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/util/List;Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;)V", "collect", "Lorg/jetbrains/kotlin/backend/common/actualizer/ExpectActualCollector$Result;", "collectActualDeclarations", "Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "matchAllExpectDeclarations", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "classActualizationInfo", "Result", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualCollector {
    private final List<IrModuleFragment> dependentFragments;
    private final KtDiagnosticReporterWithImplicitIrBasedContext diagnosticsReporter;
    private final ExpectActualTracker expectActualTracker;
    private final IrModuleFragment mainFragment;
    private final IrTypeSystemContext typeSystemContext;

    /* JADX WARN: Multi-variable type inference failed */
    public ExpectActualCollector(IrModuleFragment mainFragment, List<? extends IrModuleFragment> dependentFragments, IrTypeSystemContext typeSystemContext, KtDiagnosticReporterWithImplicitIrBasedContext diagnosticsReporter, ExpectActualTracker expectActualTracker) {
        Intrinsics.checkNotNullParameter(mainFragment, "mainFragment");
        Intrinsics.checkNotNullParameter(dependentFragments, "dependentFragments");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        this.mainFragment = mainFragment;
        this.dependentFragments = dependentFragments;
        this.typeSystemContext = typeSystemContext;
        this.diagnosticsReporter = diagnosticsReporter;
        this.expectActualTracker = expectActualTracker;
    }

    /* JADX INFO: compiled from: ExpectActualCollector.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ExpectActualCollector$Result;", "", "expectToActualMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "classActualizationInfo", "Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;)V", "getClassActualizationInfo", "()Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "getExpectToActualMap", "()Ljava/util/Map;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Result {
        private final ClassActualizationInfo classActualizationInfo;
        private final Map<IrSymbol, IrSymbol> expectToActualMap;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Result copy$default(Result result, Map map, ClassActualizationInfo classActualizationInfo, int i, Object obj) {
            if ((i & 1) != 0) {
                map = result.expectToActualMap;
            }
            if ((i & 2) != 0) {
                classActualizationInfo = result.classActualizationInfo;
            }
            return result.copy(map, classActualizationInfo);
        }

        public final Map<IrSymbol, IrSymbol> component1() {
            return this.expectToActualMap;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ClassActualizationInfo getClassActualizationInfo() {
            return this.classActualizationInfo;
        }

        public final Result copy(Map<IrSymbol, IrSymbol> expectToActualMap, ClassActualizationInfo classActualizationInfo) {
            Intrinsics.checkNotNullParameter(expectToActualMap, "expectToActualMap");
            Intrinsics.checkNotNullParameter(classActualizationInfo, "classActualizationInfo");
            return new Result(expectToActualMap, classActualizationInfo);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Result)) {
                return false;
            }
            Result result = (Result) other;
            return Intrinsics.areEqual(this.expectToActualMap, result.expectToActualMap) && Intrinsics.areEqual(this.classActualizationInfo, result.classActualizationInfo);
        }

        public int hashCode() {
            return (this.expectToActualMap.hashCode() * 31) + this.classActualizationInfo.hashCode();
        }

        public String toString() {
            return "Result(expectToActualMap=" + this.expectToActualMap + ", classActualizationInfo=" + this.classActualizationInfo + ')';
        }

        public Result(Map<IrSymbol, IrSymbol> expectToActualMap, ClassActualizationInfo classActualizationInfo) {
            Intrinsics.checkNotNullParameter(expectToActualMap, "expectToActualMap");
            Intrinsics.checkNotNullParameter(classActualizationInfo, "classActualizationInfo");
            this.expectToActualMap = expectToActualMap;
            this.classActualizationInfo = classActualizationInfo;
        }

        public final ClassActualizationInfo getClassActualizationInfo() {
            return this.classActualizationInfo;
        }

        public final Map<IrSymbol, IrSymbol> getExpectToActualMap() {
            return this.expectToActualMap;
        }
    }

    public final Result collect() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ClassActualizationInfo classActualizationInfoCollectActualDeclarations = collectActualDeclarations();
        matchAllExpectDeclarations(linkedHashMap, classActualizationInfoCollectActualDeclarations);
        return new Result(linkedHashMap, classActualizationInfoCollectActualDeclarations);
    }

    private final ClassActualizationInfo collectActualDeclarations() {
        return ActualDeclarationsCollector.INSTANCE.collectActualsFromFragments(CollectionsKt.plus((Collection<? extends IrModuleFragment>) CollectionsKt.drop(this.dependentFragments, 1), this.mainFragment));
    }

    private final void matchAllExpectDeclarations(Map<IrSymbol, IrSymbol> destination, ClassActualizationInfo classActualizationInfo) {
        ExpectActualLinkCollector expectActualLinkCollector = new ExpectActualLinkCollector();
        ExpectActualLinkCollector.MatchingContext matchingContext = new ExpectActualLinkCollector.MatchingContext(this.typeSystemContext, destination, this.diagnosticsReporter, this.expectActualTracker, classActualizationInfo, null);
        Iterator<T> it = this.dependentFragments.iterator();
        while (it.hasNext()) {
            expectActualLinkCollector.visitModuleFragment2((IrModuleFragment) it.next(), matchingContext);
        }
        expectActualLinkCollector.visitModuleFragment2(this.mainFragment, matchingContext);
    }
}
