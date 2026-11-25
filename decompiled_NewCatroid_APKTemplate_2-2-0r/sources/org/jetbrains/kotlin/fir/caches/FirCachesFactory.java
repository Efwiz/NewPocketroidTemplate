package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSessionComponent;

/* JADX INFO: compiled from: FirCachesFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JJ\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\u0004\"\b\b\u0000\u0010\u0005*\u00020\b\"\u0004\b\u0001\u0010\u0006\"\u0004\b\u0002\u0010\u00072\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\nH&JZ\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\u0004\"\b\b\u0000\u0010\u0005*\u00020\b\"\u0004\b\u0001\u0010\u0006\"\u0004\b\u0002\u0010\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\nH&J|\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\u0004\"\b\b\u0000\u0010\u0005*\u00020\b\"\u0004\b\u0001\u0010\u0006\"\u0004\b\u0002\u0010\u0007\"\u0004\b\u0003\u0010\u00102$\u0010\t\u001a \u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0007\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00100\u00110\n2\u001e\u0010\u0012\u001a\u001a\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00140\u0013H&J\"\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0016\"\u0004\b\u0000\u0010\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0017H&¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "createCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "CONTEXT", "", "createValue", "Lkotlin/Function2;", "initialCapacity", "", "loadFactor", "", "createCacheWithPostCompute", "DATA", "Lkotlin/Pair;", "postCompute", "Lkotlin/Function3;", "", "createLazyValue", "Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "Lkotlin/Function0;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirCachesFactory implements FirSessionComponent {
    public abstract <K, V, CONTEXT> FirCache<K, V, CONTEXT> createCache(int initialCapacity, float loadFactor, Function2<? super K, ? super CONTEXT, ? extends V> createValue);

    public abstract <K, V, CONTEXT> FirCache<K, V, CONTEXT> createCache(Function2<? super K, ? super CONTEXT, ? extends V> createValue);

    public abstract <K, V, CONTEXT, DATA> FirCache<K, V, CONTEXT> createCacheWithPostCompute(Function2<? super K, ? super CONTEXT, ? extends Pair<? extends V, ? extends DATA>> createValue, Function3<? super K, ? super V, ? super DATA, Unit> postCompute);

    public abstract <V> FirLazyValue<V> createLazyValue(Function0<? extends V> createValue);
}
