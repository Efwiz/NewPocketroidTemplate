package org.jetbrains.kotlin.library.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.KotlinLibraryUtilsKt;
import org.jetbrains.kotlin.library.MetadataKotlinLibraryLayout;
import org.jetbrains.kotlin.library.MetadataLibrary;

/* JADX INFO: compiled from: KotlinLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0018\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00132\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\b\u001a\u00020\t8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/MetadataLibraryImpl;", "Lorg/jetbrains/kotlin/library/MetadataLibrary;", "access", "Lorg/jetbrains/kotlin/library/impl/MetadataLibraryAccess;", "Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/library/impl/MetadataLibraryAccess;)V", "getAccess", "()Lorg/jetbrains/kotlin/library/impl/MetadataLibraryAccess;", "moduleHeaderData", "", "getModuleHeaderData", "()[B", "moduleHeaderData$delegate", "Lkotlin/Lazy;", "packageMetadata", "fqName", "", "partName", "packageMetadataParts", "", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MetadataLibraryImpl implements MetadataLibrary {
    private final MetadataLibraryAccess<MetadataKotlinLibraryLayout> access;

    /* JADX INFO: renamed from: moduleHeaderData$delegate, reason: from kotlin metadata */
    private final Lazy moduleHeaderData;

    public MetadataLibraryImpl(MetadataLibraryAccess<MetadataKotlinLibraryLayout> access) {
        Intrinsics.checkNotNullParameter(access, "access");
        this.access = access;
        this.moduleHeaderData = LazyKt.lazy(new Function0<byte[]>() { // from class: org.jetbrains.kotlin.library.impl.MetadataLibraryImpl$moduleHeaderData$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final byte[] invoke() {
                return (byte[]) this.this$0.getAccess().inPlace(new Function1<MetadataKotlinLibraryLayout, byte[]>() { // from class: org.jetbrains.kotlin.library.impl.MetadataLibraryImpl$moduleHeaderData$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final byte[] invoke(MetadataKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getModuleHeaderFile().readBytes();
                    }
                });
            }
        });
    }

    public final MetadataLibraryAccess<MetadataKotlinLibraryLayout> getAccess() {
        return this.access;
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] getModuleHeaderData() {
        Object value = this.moduleHeaderData.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-moduleHeaderData>(...)");
        return (byte[]) value;
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] packageMetadata(final String fqName, final String partName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(partName, "partName");
        Object objInPlace = this.access.inPlace(new Function1<MetadataKotlinLibraryLayout, byte[]>() { // from class: org.jetbrains.kotlin.library.impl.MetadataLibraryImpl.packageMetadata.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final byte[] invoke(MetadataKotlinLibraryLayout it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.packageFragmentFile(fqName, partName).readBytes();
            }
        });
        Intrinsics.checkNotNullExpressionValue(objInPlace, "fqName: String, partName…me).readBytes()\n        }");
        return (byte[]) objInPlace;
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public Set<String> packageMetadataParts(final String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return (Set) this.access.inPlace(new Function1<MetadataKotlinLibraryLayout, SortedSet<String>>() { // from class: org.jetbrains.kotlin.library.impl.MetadataLibraryImpl.packageMetadataParts.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SortedSet<String> invoke(MetadataKotlinLibraryLayout inPlaceaccess) throws Exception {
                Intrinsics.checkNotNullParameter(inPlaceaccess, "inPlaceaccess");
                List<File> listFiles = inPlaceaccess.packageFragmentsDir(fqName).getListFiles();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = listFiles.iterator();
                while (it.hasNext()) {
                    String strSubstringBeforeLast = StringsKt.substringBeforeLast(((File) it.next()).getName(), KotlinLibraryUtilsKt.KLIB_METADATA_FILE_EXTENSION_WITH_DOT, "");
                    if (strSubstringBeforeLast.length() <= 0) {
                        strSubstringBeforeLast = null;
                    }
                    if (strSubstringBeforeLast != null) {
                        arrayList.add(strSubstringBeforeLast);
                    }
                }
                ArrayList arrayList2 = arrayList;
                final ArrayList arrayList3 = arrayList2;
                SortedSet<String> sortedSet = CollectionsKt.toSortedSet(arrayList3);
                if (sortedSet.size() == arrayList2.size()) {
                    return sortedSet;
                }
                StringBuilder sb = new StringBuilder("Duplicated names: ");
                Map mapEachCount = GroupingKt.eachCount(new Grouping<String, String>() { // from class: org.jetbrains.kotlin.library.impl.MetadataLibraryImpl$packageMetadataParts$1$invoke$lambda$5$lambda$4$$inlined$groupingBy$1
                    @Override // kotlin.collections.Grouping
                    public Iterator<String> sourceIterator() {
                        return arrayList3.iterator();
                    }

                    @Override // kotlin.collections.Grouping
                    public String keyOf(String element) {
                        return element;
                    }
                });
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : mapEachCount.entrySet()) {
                    if (((Number) entry.getValue()).intValue() > 1) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                throw new IllegalArgumentException(sb.append(linkedHashMap).toString().toString());
            }
        });
    }
}
