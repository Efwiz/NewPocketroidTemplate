package org.jetbrains.kotlin.library.impl;

import com.reandroid.apk.ApkUtil;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.BaseKotlinLibrary;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.MetadataLibrary;

/* JADX INFO: compiled from: KotlinLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\"\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0011\u00102\u001a\u00020\u00142\u0006\u00103\u001a\u000204H\u0096\u0001J\u0019\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u0002042\u0006\u00103\u001a\u000204H\u0096\u0001J\u001b\u00107\u001a\u0004\u0018\u00010\u00142\u0006\u00106\u001a\u0002042\u0006\u00103\u001a\u000204H\u0096\u0001J\u0011\u00108\u001a\u00020\u00142\u0006\u00103\u001a\u000204H\u0096\u0001J\u0011\u00109\u001a\u00020\u00142\u0006\u00106\u001a\u000204H\u0096\u0001J\t\u0010:\u001a\u000204H\u0096\u0001J\u0019\u0010;\u001a\u00020\u00142\u0006\u00106\u001a\u0002042\u0006\u00103\u001a\u000204H\u0096\u0001J\u0019\u0010<\u001a\u00020\u00142\u0006\u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u0010H\u0096\u0001J\u0017\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00100@2\u0006\u0010=\u001a\u00020\u0010H\u0096\u0001J\u0019\u0010A\u001a\u00020\u00142\u0006\u00106\u001a\u0002042\u0006\u00103\u001a\u000204H\u0096\u0001J\u0011\u0010B\u001a\u00020\u00142\u0006\u00103\u001a\u000204H\u0096\u0001J\u0019\u0010C\u001a\u00020\u00142\u0006\u00106\u001a\u0002042\u0006\u00103\u001a\u000204H\u0096\u0001J\u0011\u0010D\u001a\u00020\u00142\u0006\u00103\u001a\u000204H\u0096\u0001J\b\u0010E\u001a\u00020\u0010H\u0016J\u0019\u0010F\u001a\u00020\u00142\u0006\u00106\u001a\u0002042\u0006\u00103\u001a\u000204H\u0096\u0001J\u0011\u0010G\u001a\u00020\u00142\u0006\u00103\u001a\u000204H\u0096\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001aR\u0012\u0010\u001e\u001a\u00020\u001fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020\u0010X\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0016\u0010%\u001a\u00060&j\u0002`'X\u0096\u0005¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0012\u0010,\u001a\u00020\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010\u0016R\u0012\u0010.\u001a\u00020/X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u00101¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/KotlinLibraryImpl;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;", "Lorg/jetbrains/kotlin/library/MetadataLibrary;", "Lorg/jetbrains/kotlin/library/IrLibrary;", ApkUtil.DEF_MODULE_NAME, "Lorg/jetbrains/kotlin/library/impl/BaseKotlinLibraryImpl;", "metadata", "Lorg/jetbrains/kotlin/library/impl/MetadataLibraryImpl;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;", "(Lorg/jetbrains/kotlin/library/impl/BaseKotlinLibraryImpl;Lorg/jetbrains/kotlin/library/impl/MetadataLibraryImpl;Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;)V", "getBase", "()Lorg/jetbrains/kotlin/library/impl/BaseKotlinLibraryImpl;", "componentList", "", "", "getComponentList", "()Ljava/util/List;", "dataFlowGraph", "", "getDataFlowGraph", "()[B", "has_pre_1_4_manifest", "", "getHas_pre_1_4_manifest", "()Z", "getIr", "()Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;", "isDefault", "libraryFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getLibraryFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "libraryName", "getLibraryName", "()Ljava/lang/String;", "manifestProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "getManifestProperties", "()Ljava/util/Properties;", "getMetadata", "()Lorg/jetbrains/kotlin/library/impl/MetadataLibraryImpl;", "moduleHeaderData", "getModuleHeaderData", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "getVersions", "()Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "bodies", "fileIndex", "", "body", "index", "debugInfo", "declarations", "file", "fileCount", "irDeclaration", "packageMetadata", "fqName", "partName", "packageMetadataParts", "", "signature", "signatures", "string", "strings", "toString", ModuleXmlParser.TYPE, SpecTypePair.NAME_types, "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryImpl implements BaseKotlinLibrary, IrLibrary, KotlinLibrary, MetadataLibrary {
    private final BaseKotlinLibraryImpl base;
    private final IrLibraryImpl ir;
    private final MetadataLibraryImpl metadata;

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] bodies(int fileIndex) {
        return this.ir.bodies(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] body(int index, int fileIndex) {
        return this.ir.body(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] debugInfo(int index, int fileIndex) {
        return this.ir.debugInfo(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] declarations(int fileIndex) {
        return this.ir.declarations(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] file(int index) {
        return this.ir.file(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public int fileCount() {
        return this.ir.fileCount();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public List<String> getComponentList() {
        return this.base.getComponentList();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] getDataFlowGraph() {
        return this.ir.getDataFlowGraph();
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
    public Properties getManifestProperties() {
        return this.base.getManifestProperties();
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] getModuleHeaderData() {
        return this.metadata.getModuleHeaderData();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public KotlinLibraryVersioning getVersions() {
        return this.base.getVersions();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] irDeclaration(int index, int fileIndex) {
        return this.ir.irDeclaration(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    /* JADX INFO: renamed from: isDefault */
    public boolean getIsDefault() {
        return this.base.getIsDefault();
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] packageMetadata(String fqName, String partName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(partName, "partName");
        return this.metadata.packageMetadata(fqName, partName);
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public Set<String> packageMetadataParts(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.metadata.packageMetadataParts(fqName);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signature(int index, int fileIndex) {
        return this.ir.signature(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signatures(int fileIndex) {
        return this.ir.signatures(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] string(int index, int fileIndex) {
        return this.ir.string(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] strings(int fileIndex) {
        return this.ir.strings(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] type(int index, int fileIndex) {
        return this.ir.type(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] types(int fileIndex) {
        return this.ir.types(fileIndex);
    }

    public KotlinLibraryImpl(BaseKotlinLibraryImpl base, MetadataLibraryImpl metadata, IrLibraryImpl ir) {
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.base = base;
        this.metadata = metadata;
        this.ir = ir;
    }

    public final BaseKotlinLibraryImpl getBase() {
        return this.base;
    }

    public final MetadataLibraryImpl getMetadata() {
        return this.metadata;
    }

    public final IrLibraryImpl getIr() {
        return this.ir;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name ");
        sb.append(this.base.getLibraryName());
        sb.append(", file: ");
        sb.append(this.base.getLibraryFile().getPath());
        sb.append(", version: ");
        sb.append(this.base.getVersions());
        if (KotlinLibraryKt.isInterop(this)) {
            sb.append(", interop: true, native targets: ");
            CollectionsKt.joinTo(KotlinLibraryKt.getNativeTargets(this), sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : "{", (112 & 8) != 0 ? "" : "}", (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
        }
        sb.append(')');
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
