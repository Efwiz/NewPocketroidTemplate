package org.jetbrains.kotlin.analyzer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/PackageOracleFactory;", "", "createOracle", "Lorg/jetbrains/kotlin/analyzer/PackageOracle;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "OptimisticFactory", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageOracleFactory {
    PackageOracle createOracle(ModuleInfo moduleInfo);

    /* JADX INFO: compiled from: AnalyzerFacade.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/PackageOracleFactory$OptimisticFactory;", "Lorg/jetbrains/kotlin/analyzer/PackageOracleFactory;", "()V", "createOracle", "Lorg/jetbrains/kotlin/analyzer/PackageOracle$Optimistic;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class OptimisticFactory implements PackageOracleFactory {
        public static final OptimisticFactory INSTANCE = new OptimisticFactory();

        private OptimisticFactory() {
        }

        @Override // org.jetbrains.kotlin.analyzer.PackageOracleFactory
        public PackageOracle.Optimistic createOracle(ModuleInfo moduleInfo) {
            Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
            return PackageOracle.Optimistic.INSTANCE;
        }
    }
}
