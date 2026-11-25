package org.jetbrains.kotlin.library.impl;

import com.reandroid.apk.ApkUtil;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.BaseWriter;
import org.jetbrains.kotlin.library.IrWriter;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.KotlinLibraryWriter;
import org.jetbrains.kotlin.library.MetadataWriter;
import org.jetbrains.kotlin.library.SerializedIrModule;
import org.jetbrains.kotlin.library.SerializedMetadata;

/* JADX INFO: compiled from: KotlinLibraryWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004Bg\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0001\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003¢\u0006\u0002\u0010\u0015J\u0011\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0096\u0001J\u0011\u0010 \u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020!H\u0096\u0001J\u0017\u0010\"\u001a\u00020\u001d2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\fH\u0096\u0001J\u0015\u0010%\u001a\u00020\u001d2\n\u0010&\u001a\u00060'j\u0002`(H\u0096\u0001J\u0011\u0010)\u001a\u00020\u001d2\u0006\u0010\u0013\u001a\u00020*H\u0096\u0001J\t\u0010+\u001a\u00020\u001dH\u0096\u0001R\u0011\u0010\u0012\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u0007\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/KotlinLibraryWriterImpl;", "Lorg/jetbrains/kotlin/library/BaseWriter;", "Lorg/jetbrains/kotlin/library/MetadataWriter;", "Lorg/jetbrains/kotlin/library/IrWriter;", "Lorg/jetbrains/kotlin/library/KotlinLibraryWriter;", "moduleName", "", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "builtInsPlatform", "Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;", "nativeTargets", "", "nopack", "", "shortName", "layout", "Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;", ApkUtil.DEF_MODULE_NAME, "metadata", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "(Ljava/lang/String;Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;Ljava/util/List;ZLjava/lang/String;Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;Lorg/jetbrains/kotlin/library/BaseWriter;Lorg/jetbrains/kotlin/library/MetadataWriter;Lorg/jetbrains/kotlin/library/IrWriter;)V", "getBase", "()Lorg/jetbrains/kotlin/library/BaseWriter;", "getLayout", "()Lorg/jetbrains/kotlin/library/impl/KotlinLibraryLayoutForWriter;", "getVersions", "()Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "addDataFlowGraph", "", "dataFlowGraph", "", "addIr", "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "addLinkDependencies", "libraries", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "addManifestAddend", "properties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "addMetadata", "Lorg/jetbrains/kotlin/library/SerializedMetadata;", "commit", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryWriterImpl implements BaseWriter, IrWriter, KotlinLibraryWriter, MetadataWriter {
    private final /* synthetic */ MetadataWriter $$delegate_1;
    private final /* synthetic */ IrWriter $$delegate_2;
    private final BaseWriter base;
    private final KotlinLibraryLayoutForWriter layout;

    @Override // org.jetbrains.kotlin.library.IrWriter
    public void addDataFlowGraph(byte[] dataFlowGraph) {
        Intrinsics.checkNotNullParameter(dataFlowGraph, "dataFlowGraph");
        this.$$delegate_2.addDataFlowGraph(dataFlowGraph);
    }

    @Override // org.jetbrains.kotlin.library.IrWriter
    public void addIr(SerializedIrModule ir) {
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.$$delegate_2.addIr(ir);
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void addLinkDependencies(List<? extends KotlinLibrary> libraries) {
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        this.base.addLinkDependencies(libraries);
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void addManifestAddend(Properties properties) {
        Intrinsics.checkNotNullParameter(properties, "properties");
        this.base.addManifestAddend(properties);
    }

    @Override // org.jetbrains.kotlin.library.MetadataWriter
    public void addMetadata(SerializedMetadata metadata) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.$$delegate_1.addMetadata(metadata);
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public void commit() {
        this.base.commit();
    }

    @Override // org.jetbrains.kotlin.library.BaseWriter
    public KotlinLibraryVersioning getVersions() {
        return this.base.getVersions();
    }

    public KotlinLibraryWriterImpl(String moduleName, KotlinLibraryVersioning versions, BuiltInsPlatform builtInsPlatform, List<String> nativeTargets, boolean z, String str, KotlinLibraryLayoutForWriter layout, BaseWriter base, MetadataWriter metadata, IrWriter ir) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(versions, "versions");
        Intrinsics.checkNotNullParameter(builtInsPlatform, "builtInsPlatform");
        Intrinsics.checkNotNullParameter(nativeTargets, "nativeTargets");
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.layout = layout;
        this.base = base;
        this.$$delegate_1 = metadata;
        this.$$delegate_2 = ir;
    }

    public final KotlinLibraryLayoutForWriter getLayout() {
        return this.layout;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ KotlinLibraryWriterImpl(String str, KotlinLibraryVersioning kotlinLibraryVersioning, BuiltInsPlatform builtInsPlatform, List list, boolean z, String str2, KotlinLibraryLayoutForWriter kotlinLibraryLayoutForWriter, BaseWriter baseWriter, MetadataWriter metadataWriter, IrWriter irWriter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        boolean z2 = (i & 16) != 0 ? false : z;
        String str3 = (i & 32) != 0 ? null : str2;
        this(str, kotlinLibraryVersioning, builtInsPlatform, list, z2, str3, kotlinLibraryLayoutForWriter, (i & 128) != 0 ? new BaseWriterImpl(kotlinLibraryLayoutForWriter, str, kotlinLibraryVersioning, builtInsPlatform, list, z2, str3) : baseWriter, (i & 256) != 0 ? new MetadataWriterImpl(kotlinLibraryLayoutForWriter) : metadataWriter, (i & 512) != 0 ? new IrMonoliticWriterImpl(kotlinLibraryLayoutForWriter) : irWriter);
    }

    public final BaseWriter getBase() {
        return this.base;
    }
}
