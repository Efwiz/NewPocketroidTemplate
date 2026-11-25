package org.jetbrains.kotlin.util.collectionUtils;

import android.Manifest;
import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: scopeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\u001aQ\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u00022\b\u0010\u0004\u001a\u0004\u0018\u0001H\u00032\b\u0010\u0005\u001a\u0004\u0018\u0001H\u00032\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\b\u001a?\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u00032\b\u0010\u0004\u001a\u0004\u0018\u0001H\u00032\b\u0010\u0005\u001a\u0004\u0018\u0001H\u00032\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\n0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\f\u001aG\u0010\r\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u0003\"\b\b\u0001\u0010\u000e*\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00112\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u0002H\u0003\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aS\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u000e2\u0006\u0010\u0015\u001a\u0002H\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00112\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00010\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001aK\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00112\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00010\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a)\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0016\u0010\u0010\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u001b0\u0011\"\u0004\u0018\u00010\u001b¢\u0006\u0002\u0010\u001c\u001a\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u000e\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u001d\u001a.\u0010\u001e\u001a\n\u0012\u0004\u0012\u0002H\u000e\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u000e*\n\u0012\u0004\u0012\u0002H\u000e\u0018\u00010\u00012\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0001\u001a6\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00020!\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030\u001d2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020#0\u0007H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006$"}, d2 = {"flatMapScopes", "", "R", "Scope", "scope1", "scope2", "transform", "Lkotlin/Function1;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "forEachScope", "", "action", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "getFirstClassifierDiscriminateHeaders", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "scopes", "", "callback", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getFromAllScopes", "firstScope", "restScopes", "(Ljava/lang/Object;[Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "listOfNonEmptyScopes", "Lorg/jetbrains/kotlin/utils/SmartList;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "([Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;)Lorg/jetbrains/kotlin/utils/SmartList;", "", "concat", "collection", "filterIsInstanceAnd", "", "predicate", "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScopeUtilsKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Collection<T> concat(Collection<? extends T> collection, Collection<? extends T> collection2) {
        Intrinsics.checkNotNullParameter(collection2, "collection");
        if (collection2.isEmpty()) {
            return collection;
        }
        if (collection == 0) {
            return collection2;
        }
        if (collection instanceof LinkedHashSet) {
            ((LinkedHashSet) collection).addAll(collection2);
            return collection;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(collection);
        linkedHashSet.addAll(collection2);
        return linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <Scope, T> Collection<T> getFromAllScopes(Scope[] scopes, Function1<? super Scope, ? extends Collection<? extends T>> callback) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Intrinsics.checkNotNullParameter(callback, "callback");
        int length = scopes.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        if (length == 1) {
            return callback.invoke(scopes[0]);
        }
        Collection<T> collectionConcat = null;
        for (Manifest manifest : scopes) {
            collectionConcat = concat(collectionConcat, callback.invoke(manifest));
        }
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <Scope, T> Collection<T> getFromAllScopes(Scope scope, Scope[] restScopes, Function1<? super Scope, ? extends Collection<? extends T>> callback) {
        Intrinsics.checkNotNullParameter(restScopes, "restScopes");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Collection<? extends T> collectionInvoke = callback.invoke(scope);
        for (Manifest manifest : restScopes) {
            collectionInvoke = concat(collectionInvoke, callback.invoke(manifest));
        }
        return collectionInvoke == null ? SetsKt.emptySet() : collectionInvoke;
    }

    public static final <Scope, R> Collection<R> flatMapScopes(Scope scope, Scope scope2, Function1<? super Scope, ? extends Collection<? extends R>> transform) {
        Intrinsics.checkNotNullParameter(transform, "transform");
        Collection<R> collectionInvoke = scope != null ? transform.invoke(scope) : CollectionsKt.emptyList();
        if (scope2 == null) {
            return collectionInvoke;
        }
        Collection<? extends R> collectionInvoke2 = transform.invoke(scope2);
        if (collectionInvoke.isEmpty()) {
            return collectionInvoke2;
        }
        List mutableList = CollectionsKt.toMutableList((Collection) collectionInvoke);
        mutableList.addAll(collectionInvoke2);
        return mutableList;
    }

    public static final <Scope> void forEachScope(Scope scope, Scope scope2, Function1<? super Scope, Unit> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        if (scope != null) {
            action.invoke(scope);
        }
        if (scope2 != null) {
            action.invoke(scope2);
        }
    }

    public static final SmartList<MemberScope> listOfNonEmptyScopes(MemberScope... scopes) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        SmartList<MemberScope> smartList = new SmartList<>();
        for (MemberScope memberScope : scopes) {
            if (memberScope != null && memberScope != MemberScope.Empty.INSTANCE) {
                smartList.add(memberScope);
            }
        }
        return smartList;
    }

    public static final SmartList<MemberScope> listOfNonEmptyScopes(Iterable<? extends MemberScope> scopes) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        SmartList<MemberScope> smartList = new SmartList<>();
        for (MemberScope memberScope : scopes) {
            MemberScope memberScope2 = memberScope;
            if (memberScope2 != null && memberScope2 != MemberScope.Empty.INSTANCE) {
                smartList.add(memberScope);
            }
        }
        return smartList;
    }

    public static final <Scope, T extends ClassifierDescriptor> T getFirstClassifierDiscriminateHeaders(Scope[] scopes, Function1<? super Scope, ? extends T> callback) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Intrinsics.checkNotNullParameter(callback, "callback");
        T t = null;
        for (Scope scope : scopes) {
            T tInvoke = callback.invoke(scope);
            if (tInvoke != null) {
                if (!(tInvoke instanceof ClassifierDescriptorWithTypeParameters) || !((ClassifierDescriptorWithTypeParameters) tInvoke).isExpect()) {
                    return tInvoke;
                }
                if (t == null) {
                    t = tInvoke;
                }
            }
        }
        return t;
    }

    public static final /* synthetic */ <R> List<R> filterIsInstanceAnd(Iterable<?> iterable, Function1<? super R, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        SmartList smartList = new SmartList();
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            Manifest manifest = (Object) it.next();
            Intrinsics.reifiedOperationMarker(3, "R");
            if ((manifest instanceof Object) && predicate.invoke(manifest).booleanValue()) {
                smartList.add(manifest);
            }
        }
        return smartList;
    }
}
