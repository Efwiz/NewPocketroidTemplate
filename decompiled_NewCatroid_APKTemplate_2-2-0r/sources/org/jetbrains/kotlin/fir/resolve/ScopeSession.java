package org.jetbrains.kotlin.fir.resolve;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: ScopeSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JS\u0010\u0007\u001a\u0002H\b\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\u0001\"\n\b\u0001\u0010\b\u0018\u0001*\u00020\u00012\u0006\u0010\n\u001a\u0002H\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\b0\u00052\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\b0\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u000eJ|\u0010\u0003\u001av\u0012\u0004\u0012\u00020\u0001\u00120\u0012.\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00010\u0004j\u0016\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0001`\u00060\u0004j:\u0012\u0004\u0012\u00020\u0001\u00120\u0012.\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00010\u0004j\u0016\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0001`\u0006`\u0006H\u0007R\u0082\u0001\u0010\u0003\u001av\u0012\u0004\u0012\u00020\u0001\u00120\u0012.\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00010\u0004j\u0016\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0001`\u00060\u0004j:\u0012\u0004\u0012\u00020\u0001\u00120\u0012.\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00010\u0004j\u0016\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0001`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "", "()V", "scopes", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "Lkotlin/collections/HashMap;", "getOrBuild", "FS", SchemaSymbols.ATTVAL_ID, "id", ASTExpr.DEFAULT_MAP_KEY_NAME, JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lkotlin/Function0;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScopeSession {
    private final HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> scopes = new HashMap<>();

    @PrivateForInline
    public final HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> scopes() {
        return this.scopes;
    }

    public final /* synthetic */ <ID, FS> FS getOrBuild(ID id2, ScopeSessionKey<ID, FS> key, Function0<? extends FS> build) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(build, "build");
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(id2);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(id2, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        FS fsInvoke = (FS) map2.get(key);
        if (fsInvoke == null) {
            fsInvoke = build.invoke();
            map2.put(key, fsInvoke);
        }
        Intrinsics.reifiedOperationMarker(1, "FS");
        FS fs = fsInvoke;
        return fsInvoke;
    }
}
