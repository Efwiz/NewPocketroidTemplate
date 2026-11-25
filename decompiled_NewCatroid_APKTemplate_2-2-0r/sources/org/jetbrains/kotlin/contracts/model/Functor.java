package org.jetbrains.kotlin.contracts.model;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.contracts.model.visitors.Reducer;

/* JADX INFO: compiled from: Functor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/Functor;", "", "invokeWithArguments", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "arguments", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "typeSubstitution", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "reducer", "Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Functor {
    List<ESEffect> invokeWithArguments(List<? extends Computation> arguments, ESTypeSubstitution typeSubstitution, Reducer reducer);
}
