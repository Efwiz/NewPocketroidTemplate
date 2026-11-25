package org.jetbrains.kotlin.konan.library.impl;

import com.reandroid.apk.ApkUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.TargetedKotlinLibraryLayout;
import org.jetbrains.kotlin.konan.library.TargetedLibrary;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.konan.util.SubstitutionKt;
import org.jetbrains.kotlin.library.BaseKotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;

/* JADX INFO: compiled from: KonanLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u0012\u0010\u0013\u001a\u00020\u000eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010R\u0012\u0010\u0014\u001a\u00020\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\nX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001f\u0010\u001b\u001a\u00060\u001cj\u0002`\u001d8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010\"\u001a\u0004\u0018\u00010#8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\fR\u0012\u0010(\u001a\u00020)X\u0096\u0005¢\u0006\u0006\u001a\u0004\b*\u0010+¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryImpl;", "Lorg/jetbrains/kotlin/konan/library/TargetedLibrary;", "Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;", "access", "Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryAccess;", "Lorg/jetbrains/kotlin/konan/library/TargetedKotlinLibraryLayout;", ApkUtil.DEF_MODULE_NAME, "(Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryAccess;Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;)V", "componentList", "", "", "getComponentList", "()Ljava/util/List;", "has_pre_1_4_manifest", "", "getHas_pre_1_4_manifest", "()Z", "includedPaths", "getIncludedPaths", "isDefault", "libraryFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getLibraryFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "libraryName", "getLibraryName", "()Ljava/lang/String;", "manifestProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "getManifestProperties", "()Ljava/util/Properties;", "manifestProperties$delegate", "Lkotlin/Lazy;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "getTarget", "()Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "targetList", "getTargetList", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "getVersions", "()Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class TargetedLibraryImpl implements TargetedLibrary, BaseKotlinLibrary {
    private final TargetedLibraryAccess<TargetedKotlinLibraryLayout> access;
    private final BaseKotlinLibrary base;

    /* JADX INFO: renamed from: manifestProperties$delegate, reason: from kotlin metadata */
    private final Lazy manifestProperties;

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public List<String> getComponentList() {
        return this.base.getComponentList();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public boolean getHas_pre_1_4_manifest() {
        return this.base.getHas_pre_1_4_manifest();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public File getLibraryFile() {
        return this.base.getLibraryFile();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public String getLibraryName() {
        return this.base.getLibraryName();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public KotlinLibraryVersioning getVersions() {
        return this.base.getVersions();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    /* JADX INFO: renamed from: isDefault */
    public boolean getIsDefault() {
        return this.base.getIsDefault();
    }

    public TargetedLibraryImpl(TargetedLibraryAccess<TargetedKotlinLibraryLayout> access, BaseKotlinLibrary base) {
        Intrinsics.checkNotNullParameter(access, "access");
        Intrinsics.checkNotNullParameter(base, "base");
        this.access = access;
        this.base = base;
        this.manifestProperties = LazyKt.lazy(new Function0<Properties>() { // from class: org.jetbrains.kotlin.konan.library.impl.TargetedLibraryImpl$manifestProperties$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Properties invoke() {
                Properties manifestProperties = this.this$0.base.getManifestProperties();
                KonanTarget target = this.this$0.getTarget();
                if (target != null) {
                    SubstitutionKt.substitute(manifestProperties, SubstitutionKt.defaultTargetSubstitutions(target));
                }
                return manifestProperties;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KonanTarget getTarget() {
        return this.access.getTarget();
    }

    @Override // org.jetbrains.kotlin.konan.library.TargetedLibrary
    public List<String> getTargetList() {
        TargetedLibraryImpl targetedLibraryImpl = this;
        List<String> commonizerNativeTargets = KotlinLibraryKt.getCommonizerNativeTargets(targetedLibraryImpl);
        if (commonizerNativeTargets != null) {
            if (commonizerNativeTargets.isEmpty()) {
                commonizerNativeTargets = null;
            }
            if (commonizerNativeTargets != null) {
                return commonizerNativeTargets;
            }
        }
        List<String> nativeTargets = KotlinLibraryKt.getNativeTargets(targetedLibraryImpl);
        List<String> list = !nativeTargets.isEmpty() ? nativeTargets : null;
        if (list != null) {
            return list;
        }
        KonanTarget target = this.access.getTarget();
        return CollectionsKt.listOfNotNull(target != null ? target.getVisibleName() : null);
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public Properties getManifestProperties() {
        return (Properties) this.manifestProperties.getValue();
    }

    @Override // org.jetbrains.kotlin.konan.library.TargetedLibrary
    public List<String> getIncludedPaths() {
        return (List) this.access.realFiles(new Function1<TargetedKotlinLibraryLayout, List<? extends String>>() { // from class: org.jetbrains.kotlin.konan.library.impl.TargetedLibraryImpl$includedPaths$1
            @Override // kotlin.jvm.functions.Function1
            public final List<String> invoke(TargetedKotlinLibraryLayout it) {
                Intrinsics.checkNotNullParameter(it, "it");
                List<File> listFilesOrEmpty = it.getIncludedDir().getListFilesOrEmpty();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFilesOrEmpty, 10));
                Iterator<T> it2 = listFilesOrEmpty.iterator();
                while (it2.hasNext()) {
                    arrayList.add(((File) it2.next()).getAbsolutePath());
                }
                return arrayList;
            }
        });
    }
}
