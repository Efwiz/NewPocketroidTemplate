package org.jetbrains.kotlin.konan.library.impl;

import com.reandroid.apk.ApkUtil;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.library.BitcodeWriter;
import org.jetbrains.kotlin.konan.library.KonanLibraryWriter;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.library.BaseWriter;
import org.jetbrains.kotlin.library.IrWriter;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.MetadataWriter;
import org.jetbrains.kotlin.library.SerializedIrModule;
import org.jetbrains.kotlin.library.SerializedMetadata;
import org.jetbrains.kotlin.library.impl.BaseWriterImpl;
import org.jetbrains.kotlin.library.impl.BuiltInsPlatform;
import org.jetbrains.kotlin.library.impl.IrMonoliticWriterImpl;
import org.jetbrains.kotlin.library.impl.MetadataWriterImpl;

/* JADX INFO: compiled from: KonanLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005Bk\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0001\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0004¢\u0006\u0002\u0010\u0017J\u0011\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0096\u0001J\u0011\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u0007H\u0096\u0001J\u0011\u0010\"\u001a\u00020\u001d2\u0006\u0010\u0016\u001a\u00020#H\u0096\u0001J\u0017\u0010$\u001a\u00020\u001d2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&H\u0096\u0001J\u0015\u0010(\u001a\u00020\u001d2\n\u0010)\u001a\u00060*j\u0002`+H\u0096\u0001J\u0011\u0010,\u001a\u00020\u001d2\u0006\u0010\u0015\u001a\u00020-H\u0096\u0001J\u0011\u0010.\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u0007H\u0096\u0001J\t\u0010/\u001a\u00020\u001dH\u0096\u0001R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/KonanLibraryWriterImpl;", "Lorg/jetbrains/kotlin/library/BaseWriter;", "Lorg/jetbrains/kotlin/konan/library/BitcodeWriter;", "Lorg/jetbrains/kotlin/library/MetadataWriter;", "Lorg/jetbrains/kotlin/library/IrWriter;", "Lorg/jetbrains/kotlin/konan/library/KonanLibraryWriter;", "moduleName", "", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "builtInsPlatform", "Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;", "nopack", "", "shortName", "layout", "Lorg/jetbrains/kotlin/konan/library/impl/KonanLibraryLayoutForWriter;", ApkUtil.DEF_MODULE_NAME, "bitcode", "metadata", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "(Ljava/lang/String;Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;Lorg/jetbrains/kotlin/konan/target/KonanTarget;Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;ZLjava/lang/String;Lorg/jetbrains/kotlin/konan/library/impl/KonanLibraryLayoutForWriter;Lorg/jetbrains/kotlin/library/BaseWriter;Lorg/jetbrains/kotlin/konan/library/BitcodeWriter;Lorg/jetbrains/kotlin/library/MetadataWriter;Lorg/jetbrains/kotlin/library/IrWriter;)V", "getLayout", "()Lorg/jetbrains/kotlin/konan/library/impl/KonanLibraryLayoutForWriter;", "getVersions", "()Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "addDataFlowGraph", "", "dataFlowGraph", "", "addIncludedBinary", "library", "addIr", "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "addLinkDependencies", "libraries", "", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "addManifestAddend", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "addMetadata", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", "addNativeBitcode", "commit", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KonanLibraryWriterImpl implements BitcodeWriter, KonanLibraryWriter, BaseWriter, IrWriter, MetadataWriter {
    private final /* synthetic */ BaseWriter $$delegate_0;
    private final /* synthetic */ BitcodeWriter $$delegate_1;
    private final /* synthetic */ MetadataWriter $$delegate_2;
    private final /* synthetic */ IrWriter $$delegate_3;
    private final KonanLibraryLayoutForWriter layout;

    @Override // org.jetbrains.kotlin.library.IrWriter
    public void addDataFlowGraph(byte[] dataFlowGraph) {
        Intrinsics.checkNotNullParameter(dataFlowGraph, "dataFlowGraph");
        this.$$delegate_3.addDataFlowGraph(dataFlowGraph);
    }

    @Override // org.jetbrains.kotlin.konan.library.TargetedWriter
    public void addIncludedBinary(String library) {
        Intrinsics.checkNotNullParameter(library, "library");
        this.$$delegate_1.addIncludedBinary(library);
    }

    @Override // org.jetbrains.kotlin.library.IrWriter
    public void addIr(SerializedIrModule ir) {
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.$$delegate_3.addIr(ir);
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void addLinkDependencies(List<? extends KotlinLibrary> libraries) {
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        this.$$delegate_0.addLinkDependencies(libraries);
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void addManifestAddend(Properties properties) {
        Intrinsics.checkNotNullParameter(properties, "properties");
        this.$$delegate_0.addManifestAddend(properties);
    }

    @Override // org.jetbrains.kotlin.library.MetadataWriter
    public void addMetadata(SerializedMetadata metadata) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.$$delegate_2.addMetadata(metadata);
    }

    @Override // org.jetbrains.kotlin.konan.library.BitcodeWriter
    public void addNativeBitcode(String library) {
        Intrinsics.checkNotNullParameter(library, "library");
        this.$$delegate_1.addNativeBitcode(library);
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void commit() {
        this.$$delegate_0.commit();
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public KotlinLibraryVersioning getVersions() {
        return this.$$delegate_0.getVersions();
    }

    public KonanLibraryWriterImpl(String moduleName, KotlinLibraryVersioning versions, KonanTarget target, BuiltInsPlatform builtInsPlatform, boolean z, String str, KonanLibraryLayoutForWriter layout, BaseWriter base, BitcodeWriter bitcode, MetadataWriter metadata, IrWriter ir) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(versions, "versions");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(builtInsPlatform, "builtInsPlatform");
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(bitcode, "bitcode");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.layout = layout;
        this.$$delegate_0 = base;
        this.$$delegate_1 = bitcode;
        this.$$delegate_2 = metadata;
        this.$$delegate_3 = ir;
    }

    public final KonanLibraryLayoutForWriter getLayout() {
        return this.layout;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ KonanLibraryWriterImpl(String str, KotlinLibraryVersioning kotlinLibraryVersioning, KonanTarget konanTarget, BuiltInsPlatform builtInsPlatform, boolean z, String str2, KonanLibraryLayoutForWriter konanLibraryLayoutForWriter, BaseWriter baseWriter, BitcodeWriter bitcodeWriter, MetadataWriter metadataWriter, IrWriter irWriter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        boolean z2 = (i & 16) != 0 ? false : z;
        String str3 = (i & 32) != 0 ? null : str2;
        this(str, kotlinLibraryVersioning, konanTarget, builtInsPlatform, z2, str3, konanLibraryLayoutForWriter, (i & 128) != 0 ? new BaseWriterImpl(konanLibraryLayoutForWriter, str, kotlinLibraryVersioning, builtInsPlatform, CollectionsKt.listOf(konanTarget.getVisibleName()), z2, str3) : baseWriter, (i & 256) != 0 ? new BitcodeWriterImpl(konanLibraryLayoutForWriter) : bitcodeWriter, (i & 512) != 0 ? new MetadataWriterImpl(konanLibraryLayoutForWriter) : metadataWriter, (i & 1024) != 0 ? new IrMonoliticWriterImpl(konanLibraryLayoutForWriter) : irWriter);
    }
}
