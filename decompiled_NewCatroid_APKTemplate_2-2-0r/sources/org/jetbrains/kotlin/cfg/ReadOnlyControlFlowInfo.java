package org.jetbrains.kotlin.cfg;

import javaslang.collection.Map;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: ControlFlowInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u00020\u0002J$\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001`\u0006H&J\u0017\u0010\u0007\u001a\u0004\u0018\u00018\u00012\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ReadOnlyControlFlowInfo;", "K", "", "D", "asMap", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "getOrNull", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Ljava/lang/Object;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReadOnlyControlFlowInfo<K, D> {
    Map<K, D> asMap();

    D getOrNull(K key);
}
