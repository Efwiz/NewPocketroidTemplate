package org.jetbrains.kotlin.library.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;
import org.jetbrains.kotlin.library.BaseKotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryLayout;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.KotlinLibraryVersioningKt;

/* JADX INFO: compiled from: KotlinLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010+\u001a\u00020\fH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR-\u0010\u000f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0004\u0012\u00020\u00060\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001c\u001a\u00020\f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0014\u001a\u0004\b\u001d\u0010\u001eR\u001f\u0010 \u001a\u00060!j\u0002`\"8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u0014\u001a\u0004\b#\u0010$R\u001b\u0010&\u001a\u00020'8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u0014\u001a\u0004\b(\u0010)¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/BaseKotlinLibraryImpl;", "Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;", "access", "Lorg/jetbrains/kotlin/library/impl/BaseLibraryAccess;", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "isDefault", "", "(Lorg/jetbrains/kotlin/library/impl/BaseLibraryAccess;Z)V", "getAccess", "()Lorg/jetbrains/kotlin/library/impl/BaseLibraryAccess;", "componentList", "", "", "getComponentList", "()Ljava/util/List;", "componentListAndHasPre14Manifest", "Lkotlin/Pair;", "getComponentListAndHasPre14Manifest", "()Lkotlin/Pair;", "componentListAndHasPre14Manifest$delegate", "Lkotlin/Lazy;", "has_pre_1_4_manifest", "getHas_pre_1_4_manifest", "()Z", "libraryFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getLibraryFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "libraryName", "getLibraryName", "()Ljava/lang/String;", "libraryName$delegate", "manifestProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "getManifestProperties", "()Ljava/util/Properties;", "manifestProperties$delegate", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "getVersions", "()Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "versions$delegate", "toString", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BaseKotlinLibraryImpl implements BaseKotlinLibrary {
    private final BaseLibraryAccess<KotlinLibraryLayout> access;

    /* JADX INFO: renamed from: componentListAndHasPre14Manifest$delegate, reason: from kotlin metadata */
    private final Lazy componentListAndHasPre14Manifest;
    private final boolean isDefault;

    /* JADX INFO: renamed from: libraryName$delegate, reason: from kotlin metadata */
    private final Lazy libraryName;

    /* JADX INFO: renamed from: manifestProperties$delegate, reason: from kotlin metadata */
    private final Lazy manifestProperties;

    /* JADX INFO: renamed from: versions$delegate, reason: from kotlin metadata */
    private final Lazy versions;

    public BaseKotlinLibraryImpl(BaseLibraryAccess<KotlinLibraryLayout> access, boolean z) {
        Intrinsics.checkNotNullParameter(access, "access");
        this.access = access;
        this.isDefault = z;
        this.libraryName = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl$libraryName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return (String) this.this$0.getAccess().inPlace(new Function1<KotlinLibraryLayout, String>() { // from class: org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl$libraryName$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final String invoke(KotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getLibraryName();
                    }
                });
            }
        });
        this.componentListAndHasPre14Manifest = LazyKt.lazy(new Function0<Pair<? extends List<? extends String>, ? extends Boolean>>() { // from class: org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl$componentListAndHasPre14Manifest$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Pair<? extends List<? extends String>, ? extends Boolean> invoke() {
                return (Pair) this.this$0.getAccess().inPlace(new Function1<KotlinLibraryLayout, Pair<? extends List<? extends String>, ? extends Boolean>>() { // from class: org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl$componentListAndHasPre14Manifest$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Pair<List<String>, Boolean> invoke(KotlinLibraryLayout layout) throws Exception {
                        Intrinsics.checkNotNullParameter(layout, "layout");
                        List<File> listFiles = layout.getLibFile().getListFiles();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : listFiles) {
                            if (((File) obj).isDirectory()) {
                                arrayList.add(obj);
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj2 : arrayList) {
                            List<File> listFiles2 = ((File) obj2).getListFiles();
                            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFiles2, 10));
                            Iterator<T> it = listFiles2.iterator();
                            while (it.hasNext()) {
                                arrayList3.add(((File) it.next()).getName());
                            }
                            if (arrayList3.contains(KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME)) {
                                arrayList2.add(obj2);
                            }
                        }
                        ArrayList arrayList4 = arrayList2;
                        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
                        Iterator it2 = arrayList4.iterator();
                        while (it2.hasNext()) {
                            arrayList5.add(((File) it2.next()).getName());
                        }
                        ArrayList arrayList6 = arrayList5;
                        boolean z2 = false;
                        if (!(listFiles instanceof Collection) || !listFiles.isEmpty()) {
                            Iterator<T> it3 = listFiles.iterator();
                            while (it3.hasNext()) {
                                if (Intrinsics.areEqual(((File) it3.next()).getAbsolutePath(), layout.getPre_1_4_manifest().getAbsolutePath())) {
                                    z2 = true;
                                    break;
                                }
                            }
                        }
                        return TuplesKt.to(arrayList6, Boolean.valueOf(z2));
                    }
                });
            }
        });
        this.manifestProperties = LazyKt.lazy(new Function0<Properties>() { // from class: org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl$manifestProperties$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Properties invoke() {
                return (Properties) this.this$0.getAccess().inPlace(new Function1<KotlinLibraryLayout, Properties>() { // from class: org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl$manifestProperties$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Properties invoke(KotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return PropertiesKt.loadProperties(it.getManifestFile());
                    }
                });
            }
        });
        this.versions = LazyKt.lazy(new Function0<KotlinLibraryVersioning>() { // from class: org.jetbrains.kotlin.library.impl.BaseKotlinLibraryImpl$versions$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinLibraryVersioning invoke() {
                return KotlinLibraryVersioningKt.readKonanLibraryVersioning(this.this$0.getManifestProperties());
            }
        });
    }

    public final BaseLibraryAccess<KotlinLibraryLayout> getAccess() {
        return this.access;
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    /* JADX INFO: renamed from: isDefault, reason: from getter */
    public boolean getIsDefault() {
        return this.isDefault;
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public File getLibraryFile() {
        return this.access.getKlib();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public String getLibraryName() {
        return (String) this.libraryName.getValue();
    }

    private final Pair<List<String>, Boolean> getComponentListAndHasPre14Manifest() {
        return (Pair) this.componentListAndHasPre14Manifest.getValue();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public List<String> getComponentList() {
        return getComponentListAndHasPre14Manifest().getFirst();
    }

    public String toString() {
        return getLibraryName() + "[default=" + getIsDefault() + ']';
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public boolean getHas_pre_1_4_manifest() {
        return getComponentListAndHasPre14Manifest().getSecond().booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public Properties getManifestProperties() {
        return (Properties) this.manifestProperties.getValue();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public KotlinLibraryVersioning getVersions() {
        return (KotlinLibraryVersioning) this.versions.getValue();
    }
}
