package org.jetbrains.kotlin.fir.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticNamesProvider;
import org.jetbrains.kotlin.load.java.PropertiesConventionUtilKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJavaSyntheticNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirJavaSyntheticNamesProvider;", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticNamesProvider;", "()V", "possibleGetterNamesByPropertyName", "", "Lorg/jetbrains/kotlin/name/Name;", "name", "possiblePropertyNamesByAccessorName", "setterNameByGetterName", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaSyntheticNamesProvider extends FirSyntheticNamesProvider {
    public static final FirJavaSyntheticNamesProvider INSTANCE = new FirJavaSyntheticNamesProvider();

    private FirJavaSyntheticNamesProvider() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticNamesProvider
    public List<Name> possibleGetterNamesByPropertyName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return PropertiesConventionUtilKt.possibleGetMethodNames(name2);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticNamesProvider
    public Name setterNameByGetterName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return PropertiesConventionUtilKt.setMethodName(name2);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticNamesProvider
    public List<Name> possiblePropertyNamesByAccessorName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return PropertiesConventionUtilKt.getPropertyNamesCandidatesByAccessorName(name2);
    }
}
