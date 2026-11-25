package org.jetbrains.kotlin.library.metadata.resolver.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializationUtilsKt;
import org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;

/* JADX INFO: compiled from: KotlinResolvedLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0001H\u0000¢\u0006\u0002\b\u001bJ\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u001fH\u0016R#\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u001e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/resolver/impl/KotlinResolvedLibraryImpl;", "Lorg/jetbrains/kotlin/library/metadata/resolver/KotlinResolvedLibrary;", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;)V", "_emptyPackages", "Lorg/jetbrains/kotlin/protobuf/ProtocolStringList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "get_emptyPackages", "()Lorg/jetbrains/kotlin/protobuf/ProtocolStringList;", "_emptyPackages$delegate", "Lkotlin/Lazy;", "_resolvedDependencies", "", "isDefault", "", "()Z", "<set-?>", "isNeededForLink", "getLibrary", "()Lorg/jetbrains/kotlin/library/KotlinLibrary;", "resolvedDependencies", "", "getResolvedDependencies", "()Ljava/util/List;", "addDependency", "resolvedLibrary", "addDependency$kotlin_util_klib_metadata", "markNeededForLink", "", "fqName", "", "toString", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinResolvedLibraryImpl implements KotlinResolvedLibrary {

    /* JADX INFO: renamed from: _emptyPackages$delegate, reason: from kotlin metadata */
    private final Lazy _emptyPackages;
    private final List<KotlinResolvedLibrary> _resolvedDependencies;
    private boolean isNeededForLink;
    private final KotlinLibrary library;

    public KotlinResolvedLibraryImpl(KotlinLibrary library) {
        Intrinsics.checkNotNullParameter(library, "library");
        this.library = library;
        this._resolvedDependencies = new ArrayList();
        this._emptyPackages = LazyKt.lazy(new Function0<ProtocolStringList>() { // from class: org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinResolvedLibraryImpl$_emptyPackages$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ProtocolStringList invoke() {
                return KlibMetadataDeserializationUtilsKt.parseModuleHeader(this.this$0.getLibrary().getModuleHeaderData()).getEmptyPackageList();
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary
    public KotlinLibrary getLibrary() {
        return this.library;
    }

    private final ProtocolStringList get_emptyPackages() {
        return (ProtocolStringList) this._emptyPackages.getValue();
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary
    public List<KotlinResolvedLibrary> getResolvedDependencies() {
        return this._resolvedDependencies;
    }

    public final boolean addDependency$kotlin_util_klib_metadata(KotlinResolvedLibrary resolvedLibrary) {
        Intrinsics.checkNotNullParameter(resolvedLibrary, "resolvedLibrary");
        return this._resolvedDependencies.add(resolvedLibrary);
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary
    /* JADX INFO: renamed from: isNeededForLink, reason: from getter */
    public boolean getIsNeededForLink() {
        return this.isNeededForLink;
    }

    @Override // org.jetbrains.kotlin.library.metadata.resolver.KotlinResolvedLibrary
    public boolean isDefault() {
        return getLibrary().getIsDefault();
    }

    @Override // org.jetbrains.kotlin.library.metadata.PackageAccessHandler
    public void markNeededForLink(KotlinLibrary library, String fqName) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (getIsNeededForLink() || get_emptyPackages().contains(fqName)) {
            return;
        }
        this.isNeededForLink = true;
    }

    public String toString() {
        return "library=" + getLibrary() + ", dependsOn=" + CollectionsKt.joinToString$default(this._resolvedDependencies, null, null, null, 0, null, new Function1<KotlinResolvedLibrary, CharSequence>() { // from class: org.jetbrains.kotlin.library.metadata.resolver.impl.KotlinResolvedLibraryImpl.toString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KotlinResolvedLibrary it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getLibrary().toString();
            }
        }, 31, null);
    }
}
