package org.jetbrains.kotlin.kotlinx.collections.immutable;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.archive.PathTree;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableList.UtilsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableSet.PersistentHashSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedMap.PersistentOrderedMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSetBuilder;

/* JADX INFO: compiled from: extensions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u0002\n\u0002\u0010%\n\u0002\u0010#\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0004\u001aQ\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0006H\u0007¢\u0006\u0002\u0010\u0007\u001a-\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005\"\u0002H\nH\u0007¢\u0006\u0002\u0010\f\u001a\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\nH\u0007\u001a-\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005\"\u0002H\nH\u0007¢\u0006\u0002\u0010\u000f\u001aQ\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0006H\u0007¢\u0006\u0002\u0010\u0007\u001a\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\nH\u0007\u001a-\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005\"\u0002H\nH\u0007¢\u0006\u0002\u0010\f\u001aO\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0006¢\u0006\u0002\u0010\u0007\u001a+\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005\"\u0002H\n¢\u0006\u0002\u0010\f\u001a\u0012\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005\"\u0002H\n¢\u0006\u0002\u0010\u000f\u001aO\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0006¢\u0006\u0002\u0010\u0007\u001a\u0012\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n\u001a+\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005\"\u0002H\n¢\u0006\u0002\u0010\f\u001a,\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\u0006\u0010\u0019\u001a\u0002H\nH\u0086\n¢\u0006\u0002\u0010\u001a\u001a4\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005H\u0086\u0002¢\u0006\u0002\u0010\u001b\u001a-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001cH\u0086\u0002\u001a-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001dH\u0086\u0002\u001a,\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\u0006\u0010\u0019\u001a\u0002H\nH\u0086\n¢\u0006\u0002\u0010\u001e\u001a4\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005H\u0086\u0002¢\u0006\u0002\u0010\u001f\u001a-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001cH\u0086\u0002\u001a-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001dH\u0086\u0002\u001a@\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010 \u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010!\u001aH\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u000e\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005H\u0086\u0002¢\u0006\u0002\u0010#\u001aA\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001cH\u0086\u0002\u001aA\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0086\u0002\u001a,\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\u0006\u0010\u0019\u001a\u0002H\nH\u0086\n¢\u0006\u0002\u0010$\u001a4\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005H\u0086\u0002¢\u0006\u0002\u0010%\u001a-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001cH\u0086\u0002\u001a-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001dH\u0086\u0002\u001a9\u0010&\u001a\b\u0012\u0004\u0012\u0002H'0\u000e\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0\u000e2\u0018\u0010(\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0*\u0012\u0004\u0012\u00020+0)H\u0086\b\u001aS\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010(\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030,\u0012\u0004\u0012\u00020+0)H\u0086\b\u001a9\u0010&\u001a\b\u0012\u0004\u0012\u0002H'0\t\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0\t2\u0018\u0010(\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0004\u0012\u00020+0)H\u0086\b\u001a,\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\u0006\u0010\u0019\u001a\u0002H\nH\u0086\n¢\u0006\u0002\u0010\u001a\u001a4\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005H\u0086\u0002¢\u0006\u0002\u0010\u001b\u001a-\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001cH\u0086\u0002\u001a-\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u0018\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u00182\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001dH\u0086\u0002\u001a,\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\u0006\u0010\u0019\u001a\u0002H\nH\u0086\n¢\u0006\u0002\u0010\u001e\u001a4\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005H\u0086\u0002¢\u0006\u0002\u0010\u001f\u001a-\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001cH\u0086\u0002\u001a-\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\u000e\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000e2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001dH\u0086\u0002\u001aT\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001a\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005H\u0086\n¢\u0006\u0002\u0010/\u001aG\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0006H\u0086\n\u001aM\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u001cH\u0086\n\u001aI\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0014\u00101\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000302H\u0086\n\u001aM\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u001dH\u0086\n\u001a,\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\u0006\u0010\u0019\u001a\u0002H\nH\u0086\n¢\u0006\u0002\u0010$\u001a4\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\n0\u0005H\u0086\u0002¢\u0006\u0002\u0010%\u001a-\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001cH\u0086\u0002\u001a-\u0010.\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\u001dH\u0086\u0002\u001aQ\u00103\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001a\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005¢\u0006\u0002\u0010/\u001aJ\u00103\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u001c\u001aF\u00103\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0014\u00101\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000302\u001aJ\u00103\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u001d\u001a\u0010\u00104\u001a\b\u0012\u0004\u0012\u00020605*\u000207\u001a\u001c\u00104\u001a\b\u0012\u0004\u0012\u0002H'05\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0\u001c\u001a.\u00108\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000309\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000302\u001a\u001c\u0010:\u001a\b\u0012\u0004\u0012\u0002H'0;\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0\u001c\u001a.\u0010<\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000302\u001a\u001c\u0010=\u001a\b\u0012\u0004\u0012\u0002H'0\t\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0>\u001a\u0010\u0010?\u001a\b\u0012\u0004\u0012\u0002060\u000e*\u000207\u001a\u001c\u0010?\u001a\b\u0012\u0004\u0012\u0002H'0\u000e\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0\u001c\u001a.\u0010@\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000302\u001a\u0010\u0010A\u001a\b\u0012\u0004\u0012\u0002060\t*\u000207\u001a\u001c\u0010A\u001a\b\u0012\u0004\u0012\u0002H'0\t\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0\u001c¨\u0006B"}, d2 = {"immutableHashMapOf", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlinx/collections/immutable/PersistentMap;", "immutableHashSetOf", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", ExifInterface.LONGITUDE_EAST, PathTree.NAME_elements, "([Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentSet;", "immutableListOf", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "([Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentList;", "immutableMapOf", "immutableSetOf", "persistentHashMapOf", "persistentHashSetOf", "persistentListOf", "persistentMapOf", "persistentSetOf", "minus", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentCollection;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lkotlinx/collections/immutable/PersistentCollection;Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentCollection;", "(Lkotlinx/collections/immutable/PersistentCollection;[Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentCollection;", "", "Lkotlin/sequences/Sequence;", "(Lkotlinx/collections/immutable/PersistentList;Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentList;", "(Lkotlinx/collections/immutable/PersistentList;[Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentList;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lkotlinx/collections/immutable/PersistentMap;Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentMap;", UserMetadata.KEYDATA_FILENAME, "(Lkotlinx/collections/immutable/PersistentMap;[Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentMap;", "(Lkotlinx/collections/immutable/PersistentSet;Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentSet;", "(Lkotlinx/collections/immutable/PersistentSet;[Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentSet;", "mutate", ExifInterface.GPS_DIRECTION_TRUE, "mutator", "Lkotlin/Function1;", "", "", "", "", "plus", "(Lkotlinx/collections/immutable/PersistentMap;[Lkotlin/Pair;)Lkotlinx/collections/immutable/PersistentMap;", "pair", "map", "", "putAll", "toImmutableList", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableList;", "", "", "toImmutableMap", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableMap;", "toImmutableSet", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableSet;", "toPersistentHashMap", "toPersistentHashSet", "", "toPersistentList", "toPersistentMap", "toPersistentSet", "kotlinx-collections-immutable"}, k = 2, mv = {1, 1, 16})
public final class ExtensionsKt {
    public static final <E> PersistentList<E> plus(PersistentList<? extends E> plus, Iterable<? extends E> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        if (elements instanceof Collection) {
            return plus.addAll((Collection<? extends Object>) elements);
        }
        PersistentList.Builder<? extends E> builder = plus.builder();
        CollectionsKt.addAll(builder, elements);
        return builder.build();
    }

    public static final <E> PersistentSet<E> plus(PersistentSet<? extends E> plus, Iterable<? extends E> elements) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        if (elements instanceof Collection) {
            return plus.addAll((Collection<? extends Object>) elements);
        }
        PersistentSet.Builder<? extends E> builder = plus.builder();
        CollectionsKt.addAll(builder, elements);
        return builder.build();
    }

    public static final <E> PersistentList<E> persistentListOf(E... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return UtilsKt.persistentVectorOf().addAll((Collection) ArraysKt.asList(elements));
    }

    public static final <E> PersistentList<E> persistentListOf() {
        return UtilsKt.persistentVectorOf();
    }

    public static final <E> PersistentSet<E> persistentSetOf(E... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return PersistentOrderedSet.INSTANCE.emptyOf$kotlinx_collections_immutable().addAll((Collection) ArraysKt.asList(elements));
    }

    public static final <E> PersistentSet<E> persistentSetOf() {
        return PersistentOrderedSet.INSTANCE.emptyOf$kotlinx_collections_immutable();
    }

    public static final <E> PersistentSet<E> persistentHashSetOf(E... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return PersistentHashSet.INSTANCE.emptyOf$kotlinx_collections_immutable().addAll((Collection) ArraysKt.asList(elements));
    }

    public static final <K, V> PersistentMap<K, V> persistentMapOf(Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        PersistentOrderedMap<K, V> persistentOrderedMapEmptyOf$kotlinx_collections_immutable = PersistentOrderedMap.INSTANCE.emptyOf$kotlinx_collections_immutable();
        if (persistentOrderedMapEmptyOf$kotlinx_collections_immutable == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.collections.immutable.PersistentMap<K, V>");
        }
        PersistentMap.Builder<K, V> builder = persistentOrderedMapEmptyOf$kotlinx_collections_immutable.builder();
        MapsKt.putAll(builder, pairs);
        return builder.build();
    }

    public static final <K, V> PersistentMap<K, V> persistentHashMapOf(Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        PersistentHashMap<K, V> persistentHashMapEmptyOf$kotlinx_collections_immutable = PersistentHashMap.INSTANCE.emptyOf$kotlinx_collections_immutable();
        if (persistentHashMapEmptyOf$kotlinx_collections_immutable == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.collections.immutable.PersistentMap<K, V>");
        }
        PersistentMap.Builder<K, V> builder = persistentHashMapEmptyOf$kotlinx_collections_immutable.builder();
        MapsKt.putAll(builder, pairs);
        return builder.build();
    }

    public static final <T> ImmutableList<T> toImmutableList(Iterable<? extends T> toImmutableList) {
        Intrinsics.checkParameterIsNotNull(toImmutableList, "$this$toImmutableList");
        ImmutableList<T> immutableList = (ImmutableList) (!(toImmutableList instanceof ImmutableList) ? null : toImmutableList);
        return immutableList != null ? immutableList : toPersistentList(toImmutableList);
    }

    public static final <T> PersistentList<T> toPersistentList(Iterable<? extends T> toPersistentList) {
        Intrinsics.checkParameterIsNotNull(toPersistentList, "$this$toPersistentList");
        PersistentList<T> persistentListBuild = null;
        PersistentList<T> persistentList = (PersistentList) (!(toPersistentList instanceof PersistentList) ? null : toPersistentList);
        if (persistentList != null) {
            persistentListBuild = persistentList;
        } else {
            PersistentList.Builder builder = (PersistentList.Builder) (!(toPersistentList instanceof PersistentList.Builder) ? null : toPersistentList);
            if (builder != null) {
                persistentListBuild = builder.build();
            }
        }
        return persistentListBuild != null ? persistentListBuild : plus(persistentListOf(), toPersistentList);
    }

    public static final <T> PersistentSet<T> toPersistentSet(Iterable<? extends T> toPersistentSet) {
        Intrinsics.checkParameterIsNotNull(toPersistentSet, "$this$toPersistentSet");
        PersistentOrderedSet persistentOrderedSetBuild = null;
        PersistentOrderedSet persistentOrderedSet = (PersistentOrderedSet) (!(toPersistentSet instanceof PersistentOrderedSet) ? null : toPersistentSet);
        if (persistentOrderedSet != null) {
            persistentOrderedSetBuild = persistentOrderedSet;
        } else {
            PersistentOrderedSetBuilder persistentOrderedSetBuilder = (PersistentOrderedSetBuilder) (!(toPersistentSet instanceof PersistentOrderedSetBuilder) ? null : toPersistentSet);
            if (persistentOrderedSetBuilder != null) {
                persistentOrderedSetBuild = persistentOrderedSetBuilder.build();
            }
        }
        return persistentOrderedSetBuild != null ? persistentOrderedSetBuild : plus(PersistentOrderedSet.INSTANCE.emptyOf$kotlinx_collections_immutable(), toPersistentSet);
    }

    public static final <K, V> PersistentMap<K, V> toPersistentHashMap(Map<K, ? extends V> toPersistentHashMap) {
        PersistentHashMap<K, V> persistentHashMapBuild;
        Intrinsics.checkParameterIsNotNull(toPersistentHashMap, "$this$toPersistentHashMap");
        PersistentHashMap<K, V> persistentHashMap = (PersistentHashMap) (!(toPersistentHashMap instanceof PersistentHashMap) ? null : toPersistentHashMap);
        if (persistentHashMap != null) {
            persistentHashMapBuild = persistentHashMap;
        } else {
            PersistentHashMapBuilder persistentHashMapBuilder = (PersistentHashMapBuilder) (!(toPersistentHashMap instanceof PersistentHashMapBuilder) ? null : toPersistentHashMap);
            persistentHashMapBuild = persistentHashMapBuilder != null ? persistentHashMapBuilder.build() : null;
        }
        return persistentHashMapBuild != null ? persistentHashMapBuild : PersistentHashMap.INSTANCE.emptyOf$kotlinx_collections_immutable().putAll((Map) toPersistentHashMap);
    }
}
