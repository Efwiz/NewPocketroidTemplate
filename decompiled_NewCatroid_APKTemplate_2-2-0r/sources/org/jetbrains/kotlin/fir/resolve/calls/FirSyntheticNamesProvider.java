package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSyntheticNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticNamesProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "possibleGetterNamesByPropertyName", "", "Lorg/jetbrains/kotlin/name/Name;", "name", "possiblePropertyNamesByAccessorName", "setterNameByGetterName", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSyntheticNamesProvider implements FirSessionComponent {
    public abstract List<Name> possibleGetterNamesByPropertyName(Name name2);

    public abstract List<Name> possiblePropertyNamesByAccessorName(Name name2);

    public abstract Name setterNameByGetterName(Name name2);
}
