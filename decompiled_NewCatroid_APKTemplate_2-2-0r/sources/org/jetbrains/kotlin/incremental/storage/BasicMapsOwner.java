package org.jetbrains.kotlin.incremental.storage;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: BasicMapsOwner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u0000 !2\u00020\u0001:\u0001!B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0006\u0010\u0012\u001a\u00020\fJ\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J0\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\f2\u001e\u0010\u0018\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\t\u0012\u0004\u0012\u00020\u00100\u0019H\u0002JC\u0010\u001a\u001a\u0002H\u001b\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d\"\u0004\b\u0002\u0010\u001e\"\u001a\b\u0003\u0010\u001b*\u0014\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\u001e0\t2\u0006\u0010\u001f\u001a\u0002H\u001bH\u0004¢\u0006\u0002\u0010 R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R6\u0010\u0007\u001a*\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\t0\bj\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\u00020\u0003*\u00020\f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;", "", "cachesDir", "Ljava/io/File;", "(Ljava/io/File;)V", "getCachesDir", "()Ljava/io/File;", "maps", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/incremental/storage/BasicMap;", "Lkotlin/collections/ArrayList;", "storageFile", "", "getStorageFile", "(Ljava/lang/String;)Ljava/io/File;", Constants.ATTR_FILTER_TYPE_CLEAN, "", "close", ArchiveStreamFactory.DUMP, "flush", "memoryCachesOnly", "", "forEachMapSafe", "actionName", "action", "Lkotlin/Function1;", "registerMap", "M", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LATITUDE_SOUTH, "map", "(Lorg/jetbrains/kotlin/incremental/storage/BasicMap;)Lorg/jetbrains/kotlin/incremental/storage/BasicMap;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BasicMapsOwner {
    private final File cachesDir;
    private final ArrayList<BasicMap<?, ?, ?>> maps;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String CACHE_EXTENSION = "tab";

    public BasicMapsOwner(File cachesDir) {
        Intrinsics.checkNotNullParameter(cachesDir, "cachesDir");
        this.cachesDir = cachesDir;
        this.maps = new ArrayList<>();
    }

    public final File getCachesDir() {
        return this.cachesDir;
    }

    /* JADX INFO: compiled from: BasicMapsOwner.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner$Companion;", "", "()V", "CACHE_EXTENSION", "", "getCACHE_EXTENSION", "()Ljava/lang/String;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getCACHE_EXTENSION() {
            return BasicMapsOwner.CACHE_EXTENSION;
        }
    }

    protected final File getStorageFile(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return new File(this.cachesDir, str + '.' + CACHE_EXTENSION);
    }

    protected final synchronized <K, V, S, M extends BasicMap<K, V, S>> M registerMap(M map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.maps.add(map);
        return map;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.incremental.storage.BasicMapsOwner$clean$1, reason: invalid class name */
    /* JADX INFO: compiled from: BasicMapsOwner.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<BasicMap<?, ?, ?>, Unit> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "clean()V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return Constants.ATTR_FILTER_TYPE_CLEAN;
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(BasicMap.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(BasicMap<?, ?, ?> basicMap) {
            invoke2(basicMap);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(BasicMap<?, ?, ?> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            p0.clean();
        }
    }

    public void clean() {
        forEachMapSafe(Constants.ATTR_FILTER_TYPE_CLEAN, AnonymousClass1.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.incremental.storage.BasicMapsOwner$close$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BasicMapsOwner.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C46661 extends FunctionReference implements Function1<BasicMap<?, ?, ?>, Unit> {
        public static final C46661 INSTANCE = new C46661();

        C46661() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "close()V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "close";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(BasicMap.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(BasicMap<?, ?, ?> basicMap) {
            invoke2(basicMap);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(BasicMap<?, ?, ?> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            p0.close();
        }
    }

    public void close() {
        forEachMapSafe("close", C46661.INSTANCE);
    }

    public void flush(final boolean memoryCachesOnly) {
        forEachMapSafe("flush", new Function1<BasicMap<?, ?, ?>, Unit>() { // from class: org.jetbrains.kotlin.incremental.storage.BasicMapsOwner.flush.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BasicMap<?, ?, ?> basicMap) {
                invoke2(basicMap);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BasicMap<?, ?, ?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                it.flush(memoryCachesOnly);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00c1 A[Catch: all -> 0x00d9, LOOP:1: B:24:0x00bb->B:26:0x00c1, LOOP_END, TryCatch #1 {, blocks: (B:3:0x0001, B:4:0x000e, B:6:0x0014, B:7:0x001a, B:10:0x001f, B:11:0x0033, B:13:0x003c, B:22:0x00aa, B:23:0x00b1, B:24:0x00bb, B:26:0x00c1, B:27:0x00d6, B:16:0x008c, B:17:0x0094, B:19:0x009a, B:21:0x00a4), top: B:35:0x0001, inners: #0 }] */
    private final synchronized void forEachMapSafe(String actionName, Function1<? super BasicMap<?, ?, ?>, Unit> action) {
        IOException exc;
        Iterator it;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<T> it2 = this.maps.iterator();
        while (it2.hasNext()) {
            BasicMap basicMap = (BasicMap) it2.next();
            try {
                action.invoke(basicMap);
            } catch (Exception e) {
                String name2 = basicMap.getStorageFile().getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                linkedHashMap.put(name2, e);
            }
        }
        if (!linkedHashMap.isEmpty()) {
            StringBuilder sbAppend = new StringBuilder().append("Could not ").append(actionName).append(" incremental caches in ").append(this.cachesDir).append(": ");
            Set setKeySet = linkedHashMap.keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
            String string = sbAppend.append(CollectionsKt.joinToString$default(setKeySet, ", ", null, null, 0, null, null, 62, null)).toString();
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            if (!linkedHashMap2.isEmpty()) {
                Iterator it3 = linkedHashMap2.entrySet().iterator();
                while (true) {
                    if (it3.hasNext()) {
                        if (!(((Map.Entry) it3.next()) instanceof IOException)) {
                            exc = new Exception(string);
                            break;
                        }
                    }
                }
                it = linkedHashMap.entrySet().iterator();
                while (it.hasNext()) {
                    ExceptionsKt.addSuppressed(exc, (Exception) ((Map.Entry) it.next()).getValue());
                }
                throw exc;
            }
            exc = new IOException(string);
            it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                ExceptionsKt.addSuppressed(exc, (Exception) ((Map.Entry) it.next()).getValue());
            }
            throw exc;
        }
    }

    public final synchronized String dump() {
        return CollectionsKt.joinToString$default(this.maps, "\n\n", null, null, 0, null, new Function1<BasicMap<?, ?, ?>, CharSequence>() { // from class: org.jetbrains.kotlin.incremental.storage.BasicMapsOwner.dump.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(BasicMap<?, ?, ?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.dump();
            }
        }, 30, null);
    }
}
