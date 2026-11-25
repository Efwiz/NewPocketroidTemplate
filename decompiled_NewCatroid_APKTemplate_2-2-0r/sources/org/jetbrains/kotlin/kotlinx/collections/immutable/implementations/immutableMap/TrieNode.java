package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.MutabilityOwnership;

/* JADX INFO: compiled from: TrieNode.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b(\n\u0002\u0018\u0002\n\u0002\b\"\b\u0000\u0018\u0000 p*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0002pqB'\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\b¢\u0006\u0002\u0010\tB/\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0091\u0001\u0010\u0011\u001a\u00020\u00122\u0081\u0001\u0010\u0013\u001a}\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00120\u0014H\u0000¢\u0006\u0002\b\u001aJ\u009c\u0001\u0010\u0011\u001a\u00020\u00122\u0081\u0001\u0010\u0013\u001a}\u0012\u001f\u0012\u001d\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00120\u00142\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0005H\u0002J\u0014\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001cH\u0002J\u0014\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001cH\u0002JO\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\b2\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u00052\b\u0010$\u001a\u0004\u0018\u00010\u000bH\u0002¢\u0006\u0002\u0010%J\u0015\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010)J\u0017\u0010*\u001a\u0004\u0018\u00018\u00012\u0006\u0010(\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010+J+\u0010,\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001c2\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010.J!\u0010/\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010(\u001a\u00028\u0000H\u0002¢\u0006\u0002\u00100J)\u0010/\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u0001H\u0002¢\u0006\u0002\u00101J\u001c\u00102\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u00103\u001a\u00020\u0005H\u0002J#\u00104\u001a\u00020'2\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0005¢\u0006\u0002\u00106J\r\u00107\u001a\u00020\u0005H\u0000¢\u0006\u0002\b8J\u0015\u00109\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005H\u0000¢\u0006\u0002\b:J%\u0010;\u001a\u0004\u0018\u00018\u00012\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0005¢\u0006\u0002\u0010<J\u0015\u0010=\u001a\u00020'2\u0006\u0010 \u001a\u00020\u0005H\u0000¢\u0006\u0002\b>J\u0010\u0010?\u001a\u00020'2\u0006\u0010 \u001a\u00020\u0005H\u0002J1\u0010@\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010 \u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010AJ\u0015\u0010B\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010CJ[\u0010D\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010E\u001a\u00020\u00052\u0006\u0010F\u001a\u00028\u00002\u0006\u0010G\u001a\u00028\u00012\u0006\u0010H\u001a\u00020\u00052\u0006\u0010I\u001a\u00028\u00002\u0006\u0010J\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u00052\b\u0010$\u001a\u0004\u0018\u00010\u000bH\u0002¢\u0006\u0002\u0010KJI\u0010L\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010MJ=\u0010N\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u00012\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010PH\u0002¢\u0006\u0002\u0010QJ=\u0010R\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u00012\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010PH\u0002¢\u0006\u0002\u0010QJ5\u0010R\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010(\u001a\u00028\u00002\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010PH\u0002¢\u0006\u0002\u0010SJ0\u0010T\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u00103\u001a\u00020\u00052\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010PH\u0002J9\u0010U\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010 \u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u00012\u0006\u0010$\u001a\u00020\u000bH\u0002¢\u0006\u0002\u0010VJQ\u0010W\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00028\u00002\u0006\u0010#\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u000bH\u0002¢\u0006\u0002\u0010XJK\u0010Y\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u00052\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010P¢\u0006\u0002\u0010ZJK\u0010[\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u00052\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010P¢\u0006\u0002\u0010ZJC\u0010[\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00052\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010P¢\u0006\u0002\u0010\\J8\u0010]\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u00052\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010PH\u0002J@\u0010^\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010_\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u00052\u0012\u0010`\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010$\u001a\u00020\u000bH\u0002J=\u0010a\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010-\u001a\u00028\u00012\u0012\u0010O\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010PH\u0002¢\u0006\u0002\u0010bJ!\u0010c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010_\u001a\u00020\u0005H\u0000¢\u0006\u0002\bdJ\u0015\u0010_\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005H\u0000¢\u0006\u0002\beJ9\u0010f\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001c2\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u0005¢\u0006\u0002\u0010gJ7\u0010h\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u00012\u0006\u0010\u0018\u001a\u00020\u0005¢\u0006\u0002\u0010iJ/\u0010h\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u00105\u001a\u00020\u00052\u0006\u0010(\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0005¢\u0006\u0002\u0010jJ$\u0010k\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005H\u0002J8\u0010l\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010_\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u00052\u0012\u0010`\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000H\u0002J)\u0010m\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010-\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010nJ\u0015\u0010o\u001a\u00028\u00012\u0006\u0010\u001f\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010CR0\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\b2\u000e\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\b@BX\u0080\u000e¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006r"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "dataMap", "", "nodeMap", "buffer", "", "(II[Ljava/lang/Object;)V", "ownedBy", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/internal/MutabilityOwnership;", "(II[Ljava/lang/Object;Lkotlinx/collections/immutable/internal/MutabilityOwnership;)V", "<set-?>", "getBuffer$kotlinx_collections_immutable", "()[Ljava/lang/Object;", "[Ljava/lang/Object;", "accept", "", "visitor", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "node", "shift", "hash", "accept$kotlinx_collections_immutable", "asInsertResult", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode$ModificationResult;", "asUpdateResult", "bufferMoveEntryToNode", "keyIndex", "positionMask", "newKeyHash", "newKey", "newValue", "owner", "(IIILjava/lang/Object;Ljava/lang/Object;ILkotlinx/collections/immutable/internal/MutabilityOwnership;)[Ljava/lang/Object;", "collisionContainsKey", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Z", "collisionGet", "(Ljava/lang/Object;)Ljava/lang/Object;", "collisionPut", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode$ModificationResult;", "collisionRemove", "(Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "collisionRemoveEntryAtIndex", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "containsKey", "keyHash", "(ILjava/lang/Object;I)Z", "entryCount", "entryCount$kotlinx_collections_immutable", "entryKeyIndex", "entryKeyIndex$kotlinx_collections_immutable", "get", "(ILjava/lang/Object;I)Ljava/lang/Object;", "hasEntryAt", "hasEntryAt$kotlinx_collections_immutable", "hasNodeAt", "insertEntryAt", "(ILjava/lang/Object;Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "keyAtIndex", "(I)Ljava/lang/Object;", "makeNode", "keyHash1", "key1", "value1", "keyHash2", "key2", "value2", "(ILjava/lang/Object;Ljava/lang/Object;ILjava/lang/Object;Ljava/lang/Object;ILkotlinx/collections/immutable/internal/MutabilityOwnership;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "moveEntryToNode", "(IIILjava/lang/Object;Ljava/lang/Object;I)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "mutableCollisionPut", "mutator", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "mutableCollisionRemove", "(Ljava/lang/Object;Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "mutableCollisionRemoveEntryAtIndex", "mutableInsertEntryAt", "(ILjava/lang/Object;Ljava/lang/Object;Lkotlinx/collections/immutable/internal/MutabilityOwnership;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "mutableMoveEntryToNode", "(IIILjava/lang/Object;Ljava/lang/Object;ILkotlinx/collections/immutable/internal/MutabilityOwnership;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "mutablePut", "(ILjava/lang/Object;Ljava/lang/Object;ILkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "mutableRemove", "(ILjava/lang/Object;ILkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "mutableRemoveEntryAtIndex", "mutableUpdateNodeAtIndex", "nodeIndex", "newNode", "mutableUpdateValueAtIndex", "(ILjava/lang/Object;Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "nodeAtIndex", "nodeAtIndex$kotlinx_collections_immutable", "nodeIndex$kotlinx_collections_immutable", "put", "(ILjava/lang/Object;Ljava/lang/Object;I)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode$ModificationResult;", ProductAction.ACTION_REMOVE, "(ILjava/lang/Object;Ljava/lang/Object;I)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "(ILjava/lang/Object;I)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "removeEntryAtIndex", "updateNodeAtIndex", "updateValueAtIndex", "(ILjava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "valueAtKeyIndex", "Companion", "ModificationResult", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class TrieNode<K, V> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final TrieNode EMPTY = new TrieNode(0, 0, new Object[0]);
    private Object[] buffer;
    private int dataMap;
    private int nodeMap;
    private final MutabilityOwnership ownedBy;

    public TrieNode(int i, int i2, Object[] buffer, MutabilityOwnership mutabilityOwnership) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        this.dataMap = i;
        this.nodeMap = i2;
        this.ownedBy = mutabilityOwnership;
        this.buffer = buffer;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TrieNode(int i, int i2, Object[] buffer) {
        this(i, i2, buffer, null);
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
    }

    /* JADX INFO: compiled from: TrieNode.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u00020\u0003B!\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJA\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00002*\u0010\u0010\u001a&\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00050\u0011H\u0086\bR&\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode$ModificationResult;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "node", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "sizeDelta", "", "(Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;I)V", "getNode", "()Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "setNode", "(Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;)V", "getSizeDelta", "()I", "replaceNode", "operation", "Lkotlin/Function1;", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
    public static final class ModificationResult<K, V> {
        private TrieNode<K, V> node;
        private final int sizeDelta;

        public ModificationResult(TrieNode<K, V> node, int i) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            this.node = node;
            this.sizeDelta = i;
        }

        public final TrieNode<K, V> getNode() {
            return this.node;
        }

        public final int getSizeDelta() {
            return this.sizeDelta;
        }

        public final void setNode(TrieNode<K, V> trieNode) {
            Intrinsics.checkParameterIsNotNull(trieNode, "<set-?>");
            this.node = trieNode;
        }
    }

    private final ModificationResult<K, V> asInsertResult() {
        return new ModificationResult<>(this, 1);
    }

    private final ModificationResult<K, V> asUpdateResult() {
        return new ModificationResult<>(this, 0);
    }

    /* JADX INFO: renamed from: getBuffer$kotlinx_collections_immutable, reason: from getter */
    public final Object[] getBuffer() {
        return this.buffer;
    }

    public final int entryCount$kotlinx_collections_immutable() {
        return Integer.bitCount(this.dataMap);
    }

    public final boolean hasEntryAt$kotlinx_collections_immutable(int positionMask) {
        return (positionMask & this.dataMap) != 0;
    }

    private final boolean hasNodeAt(int positionMask) {
        return (positionMask & this.nodeMap) != 0;
    }

    public final int entryKeyIndex$kotlinx_collections_immutable(int positionMask) {
        return Integer.bitCount((positionMask - 1) & this.dataMap) * 2;
    }

    public final int nodeIndex$kotlinx_collections_immutable(int positionMask) {
        return (this.buffer.length - 1) - Integer.bitCount((positionMask - 1) & this.nodeMap);
    }

    private final K keyAtIndex(int keyIndex) {
        return (K) this.buffer[keyIndex];
    }

    private final V valueAtKeyIndex(int keyIndex) {
        return (V) this.buffer[keyIndex + 1];
    }

    public final TrieNode<K, V> nodeAtIndex$kotlinx_collections_immutable(int nodeIndex) {
        Object obj = this.buffer[nodeIndex];
        if (obj != null) {
            return (TrieNode) obj;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.collections.immutable.implementations.immutableMap.TrieNode<K, V>");
    }

    private final TrieNode<K, V> insertEntryAt(int i, K k, V v) {
        return new TrieNode<>(i | this.dataMap, this.nodeMap, TrieNodeKt.insertEntryAtIndex(this.buffer, entryKeyIndex$kotlinx_collections_immutable(i), k, v));
    }

    private final TrieNode<K, V> mutableInsertEntryAt(int i, K k, V v, MutabilityOwnership mutabilityOwnership) {
        int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(i);
        if (this.ownedBy == mutabilityOwnership) {
            this.buffer = TrieNodeKt.insertEntryAtIndex(this.buffer, iEntryKeyIndex$kotlinx_collections_immutable, k, v);
            this.dataMap = i | this.dataMap;
            return this;
        }
        return new TrieNode<>(i | this.dataMap, this.nodeMap, TrieNodeKt.insertEntryAtIndex(this.buffer, iEntryKeyIndex$kotlinx_collections_immutable, k, v), mutabilityOwnership);
    }

    private final TrieNode<K, V> updateValueAtIndex(int i, V v) {
        Object[] objArr = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        objArrCopyOf[i + 1] = v;
        return new TrieNode<>(this.dataMap, this.nodeMap, objArrCopyOf);
    }

    private final TrieNode<K, V> mutableUpdateValueAtIndex(int i, V v, PersistentHashMapBuilder<K, V> persistentHashMapBuilder) {
        if (this.ownedBy == persistentHashMapBuilder.getOwnership()) {
            this.buffer[i + 1] = v;
            return this;
        }
        persistentHashMapBuilder.setModCount$kotlinx_collections_immutable(persistentHashMapBuilder.getModCount() + 1);
        Object[] objArr = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        objArrCopyOf[i + 1] = v;
        return new TrieNode<>(this.dataMap, this.nodeMap, objArrCopyOf, persistentHashMapBuilder.getOwnership());
    }

    private final TrieNode<K, V> updateNodeAtIndex(int nodeIndex, int positionMask, TrieNode<K, V> newNode) {
        Object[] objArr = newNode.buffer;
        if (objArr.length == 2 && newNode.nodeMap == 0) {
            if (this.buffer.length == 1) {
                newNode.dataMap = this.nodeMap;
                return newNode;
            }
            return new TrieNode<>(this.dataMap ^ positionMask, positionMask ^ this.nodeMap, TrieNodeKt.replaceNodeWithEntry(this.buffer, nodeIndex, entryKeyIndex$kotlinx_collections_immutable(positionMask), objArr[0], objArr[1]));
        }
        Object[] objArr2 = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr2, objArr2.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        objArrCopyOf[nodeIndex] = newNode;
        return new TrieNode<>(this.dataMap, this.nodeMap, objArrCopyOf);
    }

    private final TrieNode<K, V> mutableUpdateNodeAtIndex(int nodeIndex, int positionMask, TrieNode<K, V> newNode, MutabilityOwnership owner) {
        Object[] objArr = newNode.buffer;
        if (objArr.length == 2 && newNode.nodeMap == 0) {
            if (this.buffer.length == 1) {
                newNode.dataMap = this.nodeMap;
                return newNode;
            }
            Object[] objArrReplaceNodeWithEntry = TrieNodeKt.replaceNodeWithEntry(this.buffer, nodeIndex, entryKeyIndex$kotlinx_collections_immutable(positionMask), objArr[0], objArr[1]);
            if (this.ownedBy == owner) {
                this.buffer = objArrReplaceNodeWithEntry;
                this.dataMap ^= positionMask;
                this.nodeMap ^= positionMask;
                return this;
            }
            return new TrieNode<>(this.dataMap ^ positionMask, positionMask ^ this.nodeMap, objArrReplaceNodeWithEntry);
        }
        if (this.ownedBy == owner) {
            this.buffer[nodeIndex] = newNode;
            return this;
        }
        Object[] objArr2 = this.buffer;
        Object[] objArrCopyOf = Arrays.copyOf(objArr2, objArr2.length);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        objArrCopyOf[nodeIndex] = newNode;
        return new TrieNode<>(this.dataMap, this.nodeMap, objArrCopyOf, owner);
    }

    private final Object[] bufferMoveEntryToNode(int i, int i2, int i3, K k, V v, int i4, MutabilityOwnership mutabilityOwnership) {
        K kKeyAtIndex = keyAtIndex(i);
        return TrieNodeKt.replaceEntryWithNode(this.buffer, i, nodeIndex$kotlinx_collections_immutable(i2) + 1, makeNode(kKeyAtIndex != null ? kKeyAtIndex.hashCode() : 0, kKeyAtIndex, valueAtKeyIndex(i), i3, k, v, i4 + 5, mutabilityOwnership));
    }

    private final TrieNode<K, V> moveEntryToNode(int i, int i2, int i3, K k, V v, int i4) {
        return new TrieNode<>(this.dataMap ^ i2, i2 | this.nodeMap, bufferMoveEntryToNode(i, i2, i3, k, v, i4, null));
    }

    private final TrieNode<K, V> mutableMoveEntryToNode(int i, int i2, int i3, K k, V v, int i4, MutabilityOwnership mutabilityOwnership) {
        if (this.ownedBy == mutabilityOwnership) {
            this.buffer = bufferMoveEntryToNode(i, i2, i3, k, v, i4, mutabilityOwnership);
            this.dataMap ^= i2;
            this.nodeMap |= i2;
            return this;
        }
        return new TrieNode<>(this.dataMap ^ i2, i2 | this.nodeMap, bufferMoveEntryToNode(i, i2, i3, k, v, i4, mutabilityOwnership), mutabilityOwnership);
    }

    private final TrieNode<K, V> makeNode(int i, K k, V v, int i2, K k2, V v2, int i3, MutabilityOwnership mutabilityOwnership) {
        Object[] objArr;
        if (i3 > 30) {
            return new TrieNode<>(0, 0, new Object[]{k, v, k2, v2}, mutabilityOwnership);
        }
        int iIndexSegment = TrieNodeKt.indexSegment(i, i3);
        int iIndexSegment2 = TrieNodeKt.indexSegment(i2, i3);
        if (iIndexSegment != iIndexSegment2) {
            if (iIndexSegment < iIndexSegment2) {
                objArr = new Object[]{k, v, k2, v2};
            } else {
                objArr = new Object[]{k2, v2, k, v};
            }
            return new TrieNode<>((1 << iIndexSegment) | (1 << iIndexSegment2), 0, objArr, mutabilityOwnership);
        }
        return new TrieNode<>(0, 1 << iIndexSegment, new Object[]{makeNode(i, k, v, i2, k2, v2, i3 + 5, mutabilityOwnership)}, mutabilityOwnership);
    }

    private final TrieNode<K, V> removeEntryAtIndex(int keyIndex, int positionMask) {
        return new TrieNode<>(positionMask ^ this.dataMap, this.nodeMap, TrieNodeKt.removeEntryAtIndex(this.buffer, keyIndex));
    }

    private final TrieNode<K, V> mutableRemoveEntryAtIndex(int keyIndex, int positionMask, PersistentHashMapBuilder<K, V> mutator) {
        mutator.setSize(mutator.size() - 1);
        mutator.setOperationResult$kotlinx_collections_immutable(valueAtKeyIndex(keyIndex));
        if (this.ownedBy == mutator.getOwnership()) {
            this.buffer = TrieNodeKt.removeEntryAtIndex(this.buffer, keyIndex);
            this.dataMap ^= positionMask;
            return this;
        }
        return new TrieNode<>(positionMask ^ this.dataMap, this.nodeMap, TrieNodeKt.removeEntryAtIndex(this.buffer, keyIndex), mutator.getOwnership());
    }

    private final TrieNode<K, V> collisionRemoveEntryAtIndex(int i) {
        return new TrieNode<>(0, 0, TrieNodeKt.removeEntryAtIndex(this.buffer, i));
    }

    private final TrieNode<K, V> mutableCollisionRemoveEntryAtIndex(int i, PersistentHashMapBuilder<K, V> mutator) {
        mutator.setSize(mutator.size() - 1);
        mutator.setOperationResult$kotlinx_collections_immutable(valueAtKeyIndex(i));
        if (this.ownedBy == mutator.getOwnership()) {
            this.buffer = TrieNodeKt.removeEntryAtIndex(this.buffer, i);
            return this;
        }
        return new TrieNode<>(0, 0, TrieNodeKt.removeEntryAtIndex(this.buffer, i), mutator.getOwnership());
    }

    private final boolean collisionContainsKey(K key) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (!Intrinsics.areEqual(key, this.buffer[first])) {
                if (first != last) {
                    first += step;
                }
            }
            return true;
        }
        return false;
    }

    private final V collisionGet(K key) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step >= 0) {
            if (first > last) {
                return null;
            }
        } else if (first < last) {
            return null;
        }
        while (!Intrinsics.areEqual(key, keyAtIndex(first))) {
            if (first == last) {
                return null;
            }
            first += step;
        }
        return valueAtKeyIndex(first);
    }

    private final ModificationResult<K, V> collisionPut(K k, V v) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (!Intrinsics.areEqual(k, keyAtIndex(first))) {
                if (first != last) {
                    first += step;
                }
            }
            if (v == valueAtKeyIndex(first)) {
                return null;
            }
            Object[] objArr = this.buffer;
            Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
            Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
            objArrCopyOf[first + 1] = v;
            return new TrieNode(0, 0, objArrCopyOf).asUpdateResult();
        }
        return new TrieNode(0, 0, TrieNodeKt.insertEntryAtIndex(this.buffer, 0, k, v)).asInsertResult();
    }

    private final TrieNode<K, V> mutableCollisionPut(K k, V v, PersistentHashMapBuilder<K, V> persistentHashMapBuilder) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (!Intrinsics.areEqual(k, keyAtIndex(first))) {
                if (first != last) {
                    first += step;
                }
            }
            persistentHashMapBuilder.setOperationResult$kotlinx_collections_immutable(valueAtKeyIndex(first));
            if (this.ownedBy == persistentHashMapBuilder.getOwnership()) {
                this.buffer[first + 1] = v;
                return this;
            }
            persistentHashMapBuilder.setModCount$kotlinx_collections_immutable(persistentHashMapBuilder.getModCount() + 1);
            Object[] objArr = this.buffer;
            Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
            Intrinsics.checkExpressionValueIsNotNull(objArrCopyOf, "java.util.Arrays.copyOf(this, size)");
            objArrCopyOf[first + 1] = v;
            return new TrieNode<>(0, 0, objArrCopyOf, persistentHashMapBuilder.getOwnership());
        }
        persistentHashMapBuilder.setSize(persistentHashMapBuilder.size() + 1);
        return new TrieNode<>(0, 0, TrieNodeKt.insertEntryAtIndex(this.buffer, 0, k, v), persistentHashMapBuilder.getOwnership());
    }

    private final TrieNode<K, V> collisionRemove(K k) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (!Intrinsics.areEqual(k, keyAtIndex(first))) {
                if (first != last) {
                    first += step;
                }
            }
            return collisionRemoveEntryAtIndex(first);
        }
        return this;
    }

    private final TrieNode<K, V> mutableCollisionRemove(K k, PersistentHashMapBuilder<K, V> persistentHashMapBuilder) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (!Intrinsics.areEqual(k, keyAtIndex(first))) {
                if (first != last) {
                    first += step;
                }
            }
            return mutableCollisionRemoveEntryAtIndex(first, persistentHashMapBuilder);
        }
        return this;
    }

    private final TrieNode<K, V> collisionRemove(K k, V v) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (true) {
                if (Intrinsics.areEqual(k, keyAtIndex(first)) && Intrinsics.areEqual(v, valueAtKeyIndex(first))) {
                    return collisionRemoveEntryAtIndex(first);
                }
                if (first != last) {
                    first += step;
                }
            }
        }
        return this;
    }

    private final TrieNode<K, V> mutableCollisionRemove(K k, V v, PersistentHashMapBuilder<K, V> persistentHashMapBuilder) {
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, this.buffer.length), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if (step < 0 ? first >= last : first <= last) {
            while (true) {
                if (Intrinsics.areEqual(k, keyAtIndex(first)) && Intrinsics.areEqual(v, valueAtKeyIndex(first))) {
                    return mutableCollisionRemoveEntryAtIndex(first, persistentHashMapBuilder);
                }
                if (first != last) {
                    first += step;
                }
            }
        }
        return this;
    }

    public final boolean containsKey(int keyHash, K key, int shift) {
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(keyHash, shift);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            return Intrinsics.areEqual(key, keyAtIndex(entryKeyIndex$kotlinx_collections_immutable(iIndexSegment)));
        }
        if (!hasNodeAt(iIndexSegment)) {
            return false;
        }
        TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(nodeIndex$kotlinx_collections_immutable(iIndexSegment));
        if (shift == 30) {
            return trieNodeNodeAtIndex$kotlinx_collections_immutable.collisionContainsKey(key);
        }
        return trieNodeNodeAtIndex$kotlinx_collections_immutable.containsKey(keyHash, key, shift + 5);
    }

    public final V get(int keyHash, K key, int shift) {
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(keyHash, shift);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(iIndexSegment);
            if (Intrinsics.areEqual(key, keyAtIndex(iEntryKeyIndex$kotlinx_collections_immutable))) {
                return valueAtKeyIndex(iEntryKeyIndex$kotlinx_collections_immutable);
            }
            return null;
        }
        if (!hasNodeAt(iIndexSegment)) {
            return null;
        }
        TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(nodeIndex$kotlinx_collections_immutable(iIndexSegment));
        if (shift == 30) {
            return trieNodeNodeAtIndex$kotlinx_collections_immutable.collisionGet(key);
        }
        return trieNodeNodeAtIndex$kotlinx_collections_immutable.get(keyHash, key, shift + 5);
    }

    public final ModificationResult<K, V> put(int i, K k, V v, int i2) {
        ModificationResult<K, V> modificationResultPut;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(iIndexSegment);
            if (Intrinsics.areEqual(k, keyAtIndex(iEntryKeyIndex$kotlinx_collections_immutable))) {
                if (valueAtKeyIndex(iEntryKeyIndex$kotlinx_collections_immutable) == v) {
                    return null;
                }
                return updateValueAtIndex(iEntryKeyIndex$kotlinx_collections_immutable, v).asUpdateResult();
            }
            return moveEntryToNode(iEntryKeyIndex$kotlinx_collections_immutable, iIndexSegment, i, k, v, i2).asInsertResult();
        }
        if (hasNodeAt(iIndexSegment)) {
            int iNodeIndex$kotlinx_collections_immutable = nodeIndex$kotlinx_collections_immutable(iIndexSegment);
            TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(iNodeIndex$kotlinx_collections_immutable);
            if (i2 == 30) {
                modificationResultPut = trieNodeNodeAtIndex$kotlinx_collections_immutable.collisionPut(k, v);
                if (modificationResultPut == null) {
                    return null;
                }
            } else {
                modificationResultPut = trieNodeNodeAtIndex$kotlinx_collections_immutable.put(i, k, v, i2 + 5);
                if (modificationResultPut == null) {
                    return null;
                }
            }
            modificationResultPut.setNode(updateNodeAtIndex(iNodeIndex$kotlinx_collections_immutable, iIndexSegment, modificationResultPut.getNode()));
            return modificationResultPut;
        }
        return insertEntryAt(iIndexSegment, k, v).asInsertResult();
    }

    public final TrieNode<K, V> mutablePut(int i, K k, V v, int i2, PersistentHashMapBuilder<K, V> mutator) {
        TrieNode<K, V> trieNodeMutablePut;
        Intrinsics.checkParameterIsNotNull(mutator, "mutator");
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(iIndexSegment);
            if (Intrinsics.areEqual(k, keyAtIndex(iEntryKeyIndex$kotlinx_collections_immutable))) {
                mutator.setOperationResult$kotlinx_collections_immutable(valueAtKeyIndex(iEntryKeyIndex$kotlinx_collections_immutable));
                return valueAtKeyIndex(iEntryKeyIndex$kotlinx_collections_immutable) == v ? this : mutableUpdateValueAtIndex(iEntryKeyIndex$kotlinx_collections_immutable, v, mutator);
            }
            mutator.setSize(mutator.size() + 1);
            return mutableMoveEntryToNode(iEntryKeyIndex$kotlinx_collections_immutable, iIndexSegment, i, k, v, i2, mutator.getOwnership());
        }
        if (hasNodeAt(iIndexSegment)) {
            int iNodeIndex$kotlinx_collections_immutable = nodeIndex$kotlinx_collections_immutable(iIndexSegment);
            TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(iNodeIndex$kotlinx_collections_immutable);
            if (i2 == 30) {
                trieNodeMutablePut = trieNodeNodeAtIndex$kotlinx_collections_immutable.mutableCollisionPut(k, v, mutator);
            } else {
                trieNodeMutablePut = trieNodeNodeAtIndex$kotlinx_collections_immutable.mutablePut(i, k, v, i2 + 5, mutator);
            }
            return trieNodeNodeAtIndex$kotlinx_collections_immutable == trieNodeMutablePut ? this : mutableUpdateNodeAtIndex(iNodeIndex$kotlinx_collections_immutable, iIndexSegment, trieNodeMutablePut, mutator.getOwnership());
        }
        mutator.setSize(mutator.size() + 1);
        return mutableInsertEntryAt(iIndexSegment, k, v, mutator.getOwnership());
    }

    public final TrieNode<K, V> remove(int i, K k, int i2) {
        TrieNode<K, V> trieNodeRemove;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(iIndexSegment);
            return Intrinsics.areEqual(k, keyAtIndex(iEntryKeyIndex$kotlinx_collections_immutable)) ? removeEntryAtIndex(iEntryKeyIndex$kotlinx_collections_immutable, iIndexSegment) : this;
        }
        if (!hasNodeAt(iIndexSegment)) {
            return this;
        }
        int iNodeIndex$kotlinx_collections_immutable = nodeIndex$kotlinx_collections_immutable(iIndexSegment);
        TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(iNodeIndex$kotlinx_collections_immutable);
        if (i2 == 30) {
            trieNodeRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.collisionRemove(k);
        } else {
            trieNodeRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.remove(i, k, i2 + 5);
        }
        return trieNodeNodeAtIndex$kotlinx_collections_immutable == trieNodeRemove ? this : updateNodeAtIndex(iNodeIndex$kotlinx_collections_immutable, iIndexSegment, trieNodeRemove);
    }

    public final TrieNode<K, V> mutableRemove(int i, K k, int i2, PersistentHashMapBuilder<K, V> mutator) {
        TrieNode<K, V> trieNodeMutableRemove;
        Intrinsics.checkParameterIsNotNull(mutator, "mutator");
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(iIndexSegment);
            return Intrinsics.areEqual(k, keyAtIndex(iEntryKeyIndex$kotlinx_collections_immutable)) ? mutableRemoveEntryAtIndex(iEntryKeyIndex$kotlinx_collections_immutable, iIndexSegment, mutator) : this;
        }
        if (!hasNodeAt(iIndexSegment)) {
            return this;
        }
        int iNodeIndex$kotlinx_collections_immutable = nodeIndex$kotlinx_collections_immutable(iIndexSegment);
        TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(iNodeIndex$kotlinx_collections_immutable);
        if (i2 == 30) {
            trieNodeMutableRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.mutableCollisionRemove(k, mutator);
        } else {
            trieNodeMutableRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.mutableRemove(i, k, i2 + 5, mutator);
        }
        return (this.ownedBy == mutator.getOwnership() || trieNodeNodeAtIndex$kotlinx_collections_immutable != trieNodeMutableRemove) ? mutableUpdateNodeAtIndex(iNodeIndex$kotlinx_collections_immutable, iIndexSegment, trieNodeMutableRemove, mutator.getOwnership()) : this;
    }

    public final TrieNode<K, V> remove(int i, K k, V v, int i2) {
        TrieNode<K, V> trieNodeRemove;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(iIndexSegment);
            return (Intrinsics.areEqual(k, keyAtIndex(iEntryKeyIndex$kotlinx_collections_immutable)) && Intrinsics.areEqual(v, valueAtKeyIndex(iEntryKeyIndex$kotlinx_collections_immutable))) ? removeEntryAtIndex(iEntryKeyIndex$kotlinx_collections_immutable, iIndexSegment) : this;
        }
        if (!hasNodeAt(iIndexSegment)) {
            return this;
        }
        int iNodeIndex$kotlinx_collections_immutable = nodeIndex$kotlinx_collections_immutable(iIndexSegment);
        TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(iNodeIndex$kotlinx_collections_immutable);
        if (i2 == 30) {
            trieNodeRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.collisionRemove(k, v);
        } else {
            trieNodeRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.remove(i, k, v, i2 + 5);
        }
        return trieNodeNodeAtIndex$kotlinx_collections_immutable == trieNodeRemove ? this : updateNodeAtIndex(iNodeIndex$kotlinx_collections_immutable, iIndexSegment, trieNodeRemove);
    }

    public final TrieNode<K, V> mutableRemove(int i, K k, V v, int i2, PersistentHashMapBuilder<K, V> mutator) {
        TrieNode<K, V> trieNodeMutableRemove;
        Intrinsics.checkParameterIsNotNull(mutator, "mutator");
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$kotlinx_collections_immutable(iIndexSegment)) {
            int iEntryKeyIndex$kotlinx_collections_immutable = entryKeyIndex$kotlinx_collections_immutable(iIndexSegment);
            return (Intrinsics.areEqual(k, keyAtIndex(iEntryKeyIndex$kotlinx_collections_immutable)) && Intrinsics.areEqual(v, valueAtKeyIndex(iEntryKeyIndex$kotlinx_collections_immutable))) ? mutableRemoveEntryAtIndex(iEntryKeyIndex$kotlinx_collections_immutable, iIndexSegment, mutator) : this;
        }
        if (!hasNodeAt(iIndexSegment)) {
            return this;
        }
        int iNodeIndex$kotlinx_collections_immutable = nodeIndex$kotlinx_collections_immutable(iIndexSegment);
        TrieNode<K, V> trieNodeNodeAtIndex$kotlinx_collections_immutable = nodeAtIndex$kotlinx_collections_immutable(iNodeIndex$kotlinx_collections_immutable);
        if (i2 == 30) {
            trieNodeMutableRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.mutableCollisionRemove(k, v, mutator);
        } else {
            trieNodeMutableRemove = trieNodeNodeAtIndex$kotlinx_collections_immutable.mutableRemove(i, k, v, i2 + 5, mutator);
        }
        return (this.ownedBy == mutator.getOwnership() || trieNodeNodeAtIndex$kotlinx_collections_immutable != trieNodeMutableRemove) ? mutableUpdateNodeAtIndex(iNodeIndex$kotlinx_collections_immutable, iIndexSegment, trieNodeMutableRemove, mutator.getOwnership()) : this;
    }

    /* JADX INFO: compiled from: TrieNode.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "", "getEMPTY$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final TrieNode getEMPTY$kotlinx_collections_immutable() {
            return TrieNode.EMPTY;
        }
    }
}
