package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010&\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\n\b\u0016\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00010\u00020\u0002B%\u0012\u001e\u0010\u0005\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u00020\u0002¢\u0006\u0002\u0010\u0006J,\u0010\u0016\u001a\u00020\u00172\u001e\u0010\u0018\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00170\u0019H\u0086\bø\u0001\u0000J\u001b\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0003H\u0096\u0001ø\u0001\u0001¢\u0006\u0004\b\u001c\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\u00172\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0002H\u0096\u0001J,\u0010 \u001a\u00020!2\u001e\u0010\"\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020!0\u0019H\u0086\bø\u0001\u0000J)\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00022\u0006\u0010\u001b\u001a\u00020\u0003H\u0096\u0003ø\u0001\u0001¢\u0006\u0004\b$\u0010%J%\u0010#\u001a\u0004\u0018\u00018\u00002\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u0004H\u0086\u0002ø\u0001\u0001¢\u0006\u0004\b(\u0010)J\t\u0010*\u001a\u00020\u0017H\u0096\u0001R0\u0010\u0007\u001a \u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u00020\t0\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0012\u0010\u000e\u001a\u00020\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R$\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u0082\u0002\u000e\n\u0005\b\u009920\u0001\n\u0005\b¡\u001e0\u0001¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "files", "(Ljava/util/Map;)V", TypeBlock.NAME_entries, "", "", "getEntries", "()Ljava/util/Set;", UserMetadata.KEYDATA_FILENAME, "getKeys", "size", "", "getSize", "()I", CompoundEntry.NAME_values, "", "getValues", "()Ljava/util/Collection;", "allFiles", "", "p", "Lkotlin/Function3;", "containsKey", ASTExpr.DEFAULT_MAP_KEY_NAME, "containsKey-5h9WozY", "(Ljava/lang/String;)Z", "containsValue", "value", "forEachFile", "", "f", "get", "get-5h9WozY", "(Ljava/lang/String;)Ljava/util/Map;", "libFile", "sourceFile", "get-IlDO4Ro", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", "isEmpty", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinSourceFileMap<T> implements Map<KotlinLibraryFile, Map<KotlinSourceFile, ? extends T>>, KMappedMarker {
    private final /* synthetic */ Map<KotlinLibraryFile, Map<KotlinSourceFile, T>> $$delegate_0;

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object compute(KotlinLibraryFile kotlinLibraryFile, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: compute-BOFFYcU, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7173computeBOFFYcU(String str, BiFunction<? super KotlinLibraryFile, ? super Map<KotlinSourceFile, ? extends T>, ? extends Map<KotlinSourceFile, ? extends T>> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object computeIfAbsent(KotlinLibraryFile kotlinLibraryFile, Function function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: computeIfAbsent-BOFFYcU, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7174computeIfAbsentBOFFYcU(String str, Function<? super KotlinLibraryFile, ? extends Map<KotlinSourceFile, ? extends T>> function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object computeIfPresent(KotlinLibraryFile kotlinLibraryFile, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: computeIfPresent-BOFFYcU, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7175computeIfPresentBOFFYcU(String str, BiFunction<? super KotlinLibraryFile, ? super Map<KotlinSourceFile, ? extends T>, ? extends Map<KotlinSourceFile, ? extends T>> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: containsKey-5h9WozY, reason: not valid java name */
    public boolean m7176containsKey5h9WozY(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.containsKey(KotlinLibraryFile.m7149boximpl(key));
    }

    public boolean containsValue(Map<KotlinSourceFile, ? extends T> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return this.$$delegate_0.containsValue(value);
    }

    /* JADX INFO: renamed from: get-5h9WozY, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7177get5h9WozY(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$$delegate_0.get(KotlinLibraryFile.m7149boximpl(key));
    }

    public Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, T>>> getEntries() {
        return this.$$delegate_0.entrySet();
    }

    public Set<KotlinLibraryFile> getKeys() {
        return this.$$delegate_0.keySet();
    }

    public int getSize() {
        return this.$$delegate_0.size();
    }

    public Collection<Map<KotlinSourceFile, T>> getValues() {
        return this.$$delegate_0.values();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.$$delegate_0.isEmpty();
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object merge(KotlinLibraryFile kotlinLibraryFile, Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: merge-wkabOcM, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7179mergewkabOcM(String str, Map<KotlinSourceFile, ? extends T> map, BiFunction<? super Map<KotlinSourceFile, ? extends T>, ? super Map<KotlinSourceFile, ? extends T>, ? extends Map<KotlinSourceFile, ? extends T>> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object put(KotlinLibraryFile kotlinLibraryFile, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: put-BOFFYcU, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7180putBOFFYcU(String str, Map<KotlinSourceFile, ? extends T> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void putAll(Map<? extends KotlinLibraryFile, ? extends Map<KotlinSourceFile, ? extends T>> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object putIfAbsent(KotlinLibraryFile kotlinLibraryFile, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: putIfAbsent-BOFFYcU, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7181putIfAbsentBOFFYcU(String str, Map<KotlinSourceFile, ? extends T> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public Map<KotlinSourceFile, T> remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object replace(KotlinLibraryFile kotlinLibraryFile, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ boolean replace(KotlinLibraryFile kotlinLibraryFile, Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: replace-BOFFYcU, reason: not valid java name */
    public Map<KotlinSourceFile, T> m7182replaceBOFFYcU(String str, Map<KotlinSourceFile, ? extends T> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX INFO: renamed from: replace-wkabOcM, reason: not valid java name */
    public boolean m7183replacewkabOcM(String str, Map<KotlinSourceFile, ? extends T> map, Map<KotlinSourceFile, ? extends T> map2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void replaceAll(BiFunction<? super KotlinLibraryFile, ? super Map<KotlinSourceFile, ? extends T>, ? extends Map<KotlinSourceFile, ? extends T>> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinSourceFileMap(Map<KotlinLibraryFile, ? extends Map<KotlinSourceFile, ? extends T>> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        this.$$delegate_0 = files;
    }

    @Override // java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof KotlinLibraryFile) {
            return m7176containsKey5h9WozY(((KotlinLibraryFile) obj).m7157unboximpl());
        }
        return false;
    }

    @Override // java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof Map) {
            return containsValue((Map) obj);
        }
        return false;
    }

    @Override // java.util.Map
    public final /* bridge */ Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, T>>> entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object get(Object obj) {
        if (obj instanceof KotlinLibraryFile) {
            return m7177get5h9WozY(((KotlinLibraryFile) obj).m7157unboximpl());
        }
        return null;
    }

    @Override // java.util.Map
    public final /* bridge */ Map<KotlinSourceFile, T> get(Object obj) {
        if (obj instanceof KotlinLibraryFile) {
            return m7177get5h9WozY(((KotlinLibraryFile) obj).m7157unboximpl());
        }
        return null;
    }

    @Override // java.util.Map
    public final /* bridge */ Set<KotlinLibraryFile> keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Map
    public final /* bridge */ Collection<Map<KotlinSourceFile, T>> values() {
        return getValues();
    }

    public final void forEachFile(Function3<? super KotlinLibraryFile, ? super KotlinSourceFile, ? super T, Unit> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        for (Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, ? extends T>> entry : entrySet()) {
            String strM7157unboximpl = entry.getKey().m7157unboximpl();
            for (Map.Entry<KotlinSourceFile, ? extends T> entry2 : entry.getValue().entrySet()) {
                String strM7171unboximpl = entry2.getKey().m7171unboximpl();
                f.invoke(KotlinLibraryFile.m7149boximpl(strM7157unboximpl), KotlinSourceFile.m7163boximpl(strM7171unboximpl), entry2.getValue());
            }
        }
    }

    public final boolean allFiles(Function3<? super KotlinLibraryFile, ? super KotlinSourceFile, ? super T, Boolean> p) {
        Intrinsics.checkNotNullParameter(p, "p");
        Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, T>>> setEntrySet = entrySet();
        if ((setEntrySet instanceof Collection) && setEntrySet.isEmpty()) {
            return true;
        }
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String strM7157unboximpl = ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
            Set<Map.Entry> setEntrySet2 = ((Map) entry.getValue()).entrySet();
            if (!(setEntrySet2 instanceof Collection) || !setEntrySet2.isEmpty()) {
                for (Map.Entry entry2 : setEntrySet2) {
                    String strM7171unboximpl = ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                    if (!p.invoke(KotlinLibraryFile.m7149boximpl(strM7157unboximpl), KotlinSourceFile.m7163boximpl(strM7171unboximpl), (Object) entry2.getValue()).booleanValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: get-IlDO4Ro, reason: not valid java name */
    public final T m7178getIlDO4Ro(String libFile, String sourceFile) {
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Map<KotlinSourceFile, T> mapM7177get5h9WozY = m7177get5h9WozY(libFile);
        if (mapM7177get5h9WozY != null) {
            return mapM7177get5h9WozY.get(KotlinSourceFile.m7163boximpl(sourceFile));
        }
        return null;
    }
}
