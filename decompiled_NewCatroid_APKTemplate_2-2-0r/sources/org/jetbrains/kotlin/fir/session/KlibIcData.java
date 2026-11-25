package org.jetbrains.kotlin.fir.session;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.library.MetadataLibrary;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializationUtilsKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;

/* JADX INFO: compiled from: KlibIcData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\b\n\u0002\u0010\"\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000bH\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00182\u0006\u0010\u0015\u001a\u00020\u000bH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR3\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00060\u000f0\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/KlibIcData;", "Lorg/jetbrains/kotlin/library/MetadataLibrary;", "incrementalData", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "(Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;)V", "moduleHeaderData", "", "getModuleHeaderData", "()[B", "packageFragmentNameList", "", "", "getPackageFragmentNameList", "()Ljava/util/Collection;", "parts", "", "getParts", "()Ljava/util/Map;", "parts$delegate", "Lkotlin/Lazy;", "packageMetadata", "fqName", "partName", "packageMetadataParts", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibIcData implements MetadataLibrary {

    /* JADX INFO: renamed from: parts$delegate, reason: from kotlin metadata */
    private final Lazy parts;

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] packageMetadata(String fqName, String partName) {
        byte[] bArr;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(partName, "partName");
        Map<String, byte[]> map = getParts().get(fqName);
        if (map == null || (bArr = map.get(partName)) == null) {
            throw new IllegalStateException(("Metadata not found for package " + fqName + " part " + partName).toString());
        }
        return bArr;
    }

    public KlibIcData(final IncrementalDataProvider incrementalData) {
        Intrinsics.checkNotNullParameter(incrementalData, "incrementalData");
        this.parts = LazyKt.lazy(new Function0<Map<String, Map<String, byte[]>>>() { // from class: org.jetbrains.kotlin.fir.session.KlibIcData$parts$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, Map<String, byte[]>> invoke() {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                Iterator<T> it = incrementalData.getCompiledPackageParts().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    File file = (File) entry.getKey();
                    TranslationResultValue translationResultValue = (TranslationResultValue) entry.getValue();
                    String str = (String) KlibMetadataDeserializationUtilsKt.parsePackageFragment(translationResultValue.getMetadata()).getExtension(KlibMetadataProtoBuf.fqName);
                    Intrinsics.checkNotNull(str);
                    Object obj = linkedHashMap.get(str);
                    if (obj == null) {
                        obj = (Map) new LinkedHashMap();
                        linkedHashMap.put(str, obj);
                    }
                    String name2 = file.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    ((Map) obj).put(name2, translationResultValue.getMetadata());
                }
                return linkedHashMap;
            }
        });
    }

    private final Map<String, Map<String, byte[]>> getParts() {
        return (Map) this.parts.getValue();
    }

    public final Collection<String> getPackageFragmentNameList() {
        return getParts().keySet();
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] getModuleHeaderData() {
        throw new IllegalStateException("moduleHeaderData is not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public Set<String> packageMetadataParts(String fqName) {
        Set<String> setKeySet;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Map<String, byte[]> map = getParts().get(fqName);
        return (map == null || (setKeySet = map.keySet()) == null) ? SetsKt.emptySet() : setKeySet;
    }
}
