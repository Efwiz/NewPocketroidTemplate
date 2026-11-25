package org.jetbrains.kotlin.konan.library.impl;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.library.BitcodeLibrary;
import org.jetbrains.kotlin.konan.library.KonanLibrary;
import org.jetbrains.kotlin.konan.library.KonanLibraryKt;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;
import org.jetbrains.kotlin.library.BaseKotlinLibrary;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.KotlinLibraryVersioning;
import org.jetbrains.kotlin.library.MetadataLibrary;
import org.jetbrains.kotlin.library.impl.IrLibraryImpl;
import org.jetbrains.kotlin.library.impl.MetadataLibraryImpl;

/* JADX INFO: compiled from: KonanLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\"\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005B%\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0011\u00107\u001a\u00020\u00172\u0006\u00108\u001a\u000209H\u0096\u0001J\u0019\u0010:\u001a\u00020\u00172\u0006\u0010;\u001a\u0002092\u0006\u00108\u001a\u000209H\u0096\u0001J\u001b\u0010<\u001a\u0004\u0018\u00010\u00172\u0006\u0010;\u001a\u0002092\u0006\u00108\u001a\u000209H\u0096\u0001J\u0011\u0010=\u001a\u00020\u00172\u0006\u00108\u001a\u000209H\u0096\u0001J\u0011\u0010>\u001a\u00020\u00172\u0006\u0010;\u001a\u000209H\u0096\u0001J\t\u0010?\u001a\u000209H\u0096\u0001J\u0019\u0010@\u001a\u00020\u00172\u0006\u0010;\u001a\u0002092\u0006\u00108\u001a\u000209H\u0096\u0001J\u0019\u0010A\u001a\u00020\u00172\u0006\u0010B\u001a\u00020\u00112\u0006\u0010C\u001a\u00020\u0011H\u0096\u0001J\u0017\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00110E2\u0006\u0010B\u001a\u00020\u0011H\u0096\u0001J\u0019\u0010F\u001a\u00020\u00172\u0006\u0010;\u001a\u0002092\u0006\u00108\u001a\u000209H\u0096\u0001J\u0011\u0010G\u001a\u00020\u00172\u0006\u00108\u001a\u000209H\u0096\u0001J\u0019\u0010H\u001a\u00020\u00172\u0006\u0010;\u001a\u0002092\u0006\u00108\u001a\u000209H\u0096\u0001J\u0011\u0010I\u001a\u00020\u00172\u0006\u00108\u001a\u000209H\u0096\u0001J\u0019\u0010J\u001a\u00020\u00172\u0006\u0010;\u001a\u0002092\u0006\u00108\u001a\u000209H\u0096\u0001J\u0011\u0010K\u001a\u00020\u00172\u0006\u00108\u001a\u000209H\u0096\u0001R\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0013R\u0012\u0010 \u001a\u00020\u001bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b \u0010\u001dR\u0012\u0010!\u001a\u00020\"X\u0096\u0005¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0012\u0010%\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b&\u0010'R\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u0013R\u0016\u0010*\u001a\u00060+j\u0002`,X\u0096\u0005¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0012\u0010/\u001a\u00020\u0017X\u0096\u0005¢\u0006\u0006\u001a\u0004\b0\u0010\u0019R\u0018\u00101\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u0005¢\u0006\u0006\u001a\u0004\b2\u0010\u0013R\u0012\u00103\u001a\u000204X\u0096\u0005¢\u0006\u0006\u001a\u0004\b5\u00106¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/konan/library/impl/KonanLibraryImpl;", "Lorg/jetbrains/kotlin/konan/library/KonanLibrary;", "Lorg/jetbrains/kotlin/library/BaseKotlinLibrary;", "Lorg/jetbrains/kotlin/library/MetadataLibrary;", "Lorg/jetbrains/kotlin/library/IrLibrary;", "Lorg/jetbrains/kotlin/konan/library/BitcodeLibrary;", "targeted", "Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryImpl;", "metadata", "Lorg/jetbrains/kotlin/library/impl/MetadataLibraryImpl;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;", "bitcode", "Lorg/jetbrains/kotlin/konan/library/impl/BitcodeLibraryImpl;", "(Lorg/jetbrains/kotlin/konan/library/impl/TargetedLibraryImpl;Lorg/jetbrains/kotlin/library/impl/MetadataLibraryImpl;Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;Lorg/jetbrains/kotlin/konan/library/impl/BitcodeLibraryImpl;)V", "bitcodePaths", "", "", "getBitcodePaths", "()Ljava/util/List;", "componentList", "getComponentList", "dataFlowGraph", "", "getDataFlowGraph", "()[B", "has_pre_1_4_manifest", "", "getHas_pre_1_4_manifest", "()Z", "includedPaths", "getIncludedPaths", "isDefault", "libraryFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getLibraryFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "libraryName", "getLibraryName", "()Ljava/lang/String;", KonanLibraryKt.KLIB_PROPERTY_LINKED_OPTS, "getLinkerOpts", "manifestProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "getManifestProperties", "()Ljava/util/Properties;", "moduleHeaderData", "getModuleHeaderData", "targetList", "getTargetList", "versions", "Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "getVersions", "()Lorg/jetbrains/kotlin/library/KotlinLibraryVersioning;", "bodies", "fileIndex", "", "body", "index", "debugInfo", "declarations", "file", "fileCount", "irDeclaration", "packageMetadata", "fqName", "partName", "packageMetadataParts", "", "signature", "signatures", "string", "strings", ModuleXmlParser.TYPE, SpecTypePair.NAME_types, "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KonanLibraryImpl implements BitcodeLibrary, KonanLibrary, BaseKotlinLibrary, IrLibrary, MetadataLibrary {
    private final /* synthetic */ TargetedLibraryImpl $$delegate_0;
    private final /* synthetic */ MetadataLibraryImpl $$delegate_1;
    private final /* synthetic */ IrLibraryImpl $$delegate_2;
    private final /* synthetic */ BitcodeLibraryImpl $$delegate_3;

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] bodies(int fileIndex) {
        return this.$$delegate_2.bodies(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] body(int index, int fileIndex) {
        return this.$$delegate_2.body(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] debugInfo(int index, int fileIndex) {
        return this.$$delegate_2.debugInfo(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] declarations(int fileIndex) {
        return this.$$delegate_2.declarations(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] file(int index) {
        return this.$$delegate_2.file(index);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public int fileCount() {
        return this.$$delegate_2.fileCount();
    }

    @Override // org.jetbrains.kotlin.konan.library.BitcodeLibrary
    public List<String> getBitcodePaths() {
        return this.$$delegate_3.getBitcodePaths();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public List<String> getComponentList() {
        return this.$$delegate_0.getComponentList();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] getDataFlowGraph() {
        return this.$$delegate_2.getDataFlowGraph();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public boolean getHas_pre_1_4_manifest() {
        return this.$$delegate_0.getHas_pre_1_4_manifest();
    }

    @Override // org.jetbrains.kotlin.konan.library.TargetedLibrary
    public List<String> getIncludedPaths() {
        return this.$$delegate_3.getIncludedPaths();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public File getLibraryFile() {
        return this.$$delegate_0.getLibraryFile();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public String getLibraryName() {
        return this.$$delegate_0.getLibraryName();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public Properties getManifestProperties() {
        return this.$$delegate_0.getManifestProperties();
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] getModuleHeaderData() {
        return this.$$delegate_1.getModuleHeaderData();
    }

    @Override // org.jetbrains.kotlin.konan.library.TargetedLibrary
    public List<String> getTargetList() {
        return this.$$delegate_3.getTargetList();
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    public KotlinLibraryVersioning getVersions() {
        return this.$$delegate_0.getVersions();
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] irDeclaration(int index, int fileIndex) {
        return this.$$delegate_2.irDeclaration(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.BaseKotlinLibrary
    /* JADX INFO: renamed from: isDefault */
    public boolean getIsDefault() {
        return this.$$delegate_0.getIsDefault();
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public byte[] packageMetadata(String fqName, String partName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(partName, "partName");
        return this.$$delegate_1.packageMetadata(fqName, partName);
    }

    @Override // org.jetbrains.kotlin.library.MetadataLibrary
    public Set<String> packageMetadataParts(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.$$delegate_1.packageMetadataParts(fqName);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signature(int index, int fileIndex) {
        return this.$$delegate_2.signature(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] signatures(int fileIndex) {
        return this.$$delegate_2.signatures(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] string(int index, int fileIndex) {
        return this.$$delegate_2.string(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] strings(int fileIndex) {
        return this.$$delegate_2.strings(fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] type(int index, int fileIndex) {
        return this.$$delegate_2.type(index, fileIndex);
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] types(int fileIndex) {
        return this.$$delegate_2.types(fileIndex);
    }

    public KonanLibraryImpl(TargetedLibraryImpl targeted, MetadataLibraryImpl metadata, IrLibraryImpl ir, BitcodeLibraryImpl bitcode) {
        Intrinsics.checkNotNullParameter(targeted, "targeted");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(ir, "ir");
        Intrinsics.checkNotNullParameter(bitcode, "bitcode");
        this.$$delegate_0 = targeted;
        this.$$delegate_1 = metadata;
        this.$$delegate_2 = ir;
        this.$$delegate_3 = bitcode;
    }

    @Override // org.jetbrains.kotlin.konan.library.KonanLibrary
    public List<String> getLinkerOpts() {
        return PropertiesKt.propertyList$default(getManifestProperties(), KonanLibraryKt.KLIB_PROPERTY_LINKED_OPTS, null, true, 2, null);
    }
}
