package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirCacheWithPostCompute.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\n\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\u0006\b\u0001\u0010\u0003 \u0001*\u0006\b\u0002\u0010\u0004 \u00002\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0005J\u001d\u0010\u0006\u001a\u00028\u00012\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00028\u0002H&¢\u0006\u0002\u0010\tJ\u0017\u0010\n\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/FirCache;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "CONTEXT", "()V", "getValue", ASTExpr.DEFAULT_MAP_KEY_NAME, "context", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getValueIfComputed", "(Ljava/lang/Object;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirCache<K, V, CONTEXT> {
    public abstract V getValue(K key, CONTEXT context);

    public abstract V getValueIfComputed(K key);
}
