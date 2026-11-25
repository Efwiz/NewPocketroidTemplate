package org.jetbrains.kotlin.analyzer.common;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleContent;
import org.jetbrains.kotlin.analyzer.PlatformAnalysisParameters;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPartProvider;

/* JADX INFO: compiled from: CommonResolverForModuleFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B)\u0012\u0016\u0010\u0002\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR!\u0010\u0002\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonAnalysisParameters;", "Lorg/jetbrains/kotlin/analyzer/PlatformAnalysisParameters;", "metadataPartProviderFactory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;", "klibMetadataPackageFragmentProviderFactory", "Lorg/jetbrains/kotlin/analyzer/common/KlibMetadataPackageFragmentProviderFactory;", "(Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/analyzer/common/KlibMetadataPackageFragmentProviderFactory;)V", "getKlibMetadataPackageFragmentProviderFactory", "()Lorg/jetbrains/kotlin/analyzer/common/KlibMetadataPackageFragmentProviderFactory;", "getMetadataPartProviderFactory", "()Lkotlin/jvm/functions/Function1;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonAnalysisParameters implements PlatformAnalysisParameters {
    private final KlibMetadataPackageFragmentProviderFactory klibMetadataPackageFragmentProviderFactory;
    private final Function1<ModuleContent<?>, MetadataPartProvider> metadataPartProviderFactory;

    /* JADX WARN: Multi-variable type inference failed */
    public CommonAnalysisParameters(Function1<? super ModuleContent<?>, ? extends MetadataPartProvider> metadataPartProviderFactory, KlibMetadataPackageFragmentProviderFactory klibMetadataPackageFragmentProviderFactory) {
        Intrinsics.checkNotNullParameter(metadataPartProviderFactory, "metadataPartProviderFactory");
        this.metadataPartProviderFactory = metadataPartProviderFactory;
        this.klibMetadataPackageFragmentProviderFactory = klibMetadataPackageFragmentProviderFactory;
    }

    public /* synthetic */ CommonAnalysisParameters(Function1 function1, KlibMetadataPackageFragmentProviderFactory klibMetadataPackageFragmentProviderFactory, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, (i & 2) != 0 ? null : klibMetadataPackageFragmentProviderFactory);
    }

    public final Function1<ModuleContent<?>, MetadataPartProvider> getMetadataPartProviderFactory() {
        return this.metadataPartProviderFactory;
    }

    public final KlibMetadataPackageFragmentProviderFactory getKlibMetadataPackageFragmentProviderFactory() {
        return this.klibMetadataPackageFragmentProviderFactory;
    }
}
