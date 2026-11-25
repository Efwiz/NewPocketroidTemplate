package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: KotlinLibraryLayout.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0016J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0016J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0016J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0016J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0016J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0016J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0016R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0014\u0010\b\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0014\u0010\n\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0014\u0010\f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005R\u0014\u0010\u000e\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0014\u0010\u0010\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0014\u0010\u0012\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0014\u0010\u0014\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005¨\u0006\u0018À\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "dataFlowGraphFile", "Lorg/jetbrains/kotlin/konan/file/File;", "getDataFlowGraphFile", "()Lorg/jetbrains/kotlin/konan/file/File;", "irBodies", "getIrBodies", "irDebugInfo", "getIrDebugInfo", "irDeclarations", "getIrDeclarations", "irDir", "getIrDir", "irFiles", "getIrFiles", "irSignatures", "getIrSignatures", "irStrings", "getIrStrings", "irTypes", "getIrTypes", "file", "irFile", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface IrKotlinLibraryLayout extends KotlinLibraryLayout {

    /* JADX INFO: compiled from: KotlinLibraryLayout.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static File getComponentDir(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getComponentDir();
        }

        @Deprecated
        public static String getLibraryName(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getLibraryName();
        }

        @Deprecated
        public static File getManifestFile(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getManifestFile();
        }

        @Deprecated
        public static File getPre_1_4_manifest(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getPre_1_4_manifest();
        }

        @Deprecated
        public static File getResourcesDir(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getResourcesDir();
        }

        @Deprecated
        public static File getIrDir(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrDir();
        }

        @Deprecated
        public static File getIrDeclarations(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrDeclarations();
        }

        @Deprecated
        public static File getIrTypes(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrTypes();
        }

        @Deprecated
        public static File getIrSignatures(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrSignatures();
        }

        @Deprecated
        public static File getIrStrings(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrStrings();
        }

        @Deprecated
        public static File getIrBodies(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrBodies();
        }

        @Deprecated
        public static File getIrFiles(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrFiles();
        }

        @Deprecated
        public static File getDataFlowGraphFile(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getDataFlowGraphFile();
        }

        @Deprecated
        public static File getIrDebugInfo(IrKotlinLibraryLayout irKotlinLibraryLayout) {
            return IrKotlinLibraryLayout.super.getIrDebugInfo();
        }

        @Deprecated
        public static File irDeclarations(IrKotlinLibraryLayout irKotlinLibraryLayout, File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return IrKotlinLibraryLayout.super.irDeclarations(file);
        }

        @Deprecated
        public static File irTypes(IrKotlinLibraryLayout irKotlinLibraryLayout, File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return IrKotlinLibraryLayout.super.irTypes(file);
        }

        @Deprecated
        public static File irSignatures(IrKotlinLibraryLayout irKotlinLibraryLayout, File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return IrKotlinLibraryLayout.super.irSignatures(file);
        }

        @Deprecated
        public static File irStrings(IrKotlinLibraryLayout irKotlinLibraryLayout, File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return IrKotlinLibraryLayout.super.irStrings(file);
        }

        @Deprecated
        public static File irBodies(IrKotlinLibraryLayout irKotlinLibraryLayout, File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return IrKotlinLibraryLayout.super.irBodies(file);
        }

        @Deprecated
        public static File irFile(IrKotlinLibraryLayout irKotlinLibraryLayout, File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return IrKotlinLibraryLayout.super.irFile(file);
        }

        @Deprecated
        public static File irDebugInfo(IrKotlinLibraryLayout irKotlinLibraryLayout, File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return IrKotlinLibraryLayout.super.irDebugInfo(file);
        }
    }

    default File getIrDir() {
        return new File(getComponentDir(), KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME);
    }

    default File getIrDeclarations() {
        return new File(getIrDir(), "irDeclarations.knd");
    }

    default File getIrTypes() {
        return new File(getIrDir(), "types.knt");
    }

    default File getIrSignatures() {
        return new File(getIrDir(), "signatures.knt");
    }

    default File getIrStrings() {
        return new File(getIrDir(), "strings.knt");
    }

    default File getIrBodies() {
        return new File(getIrDir(), "bodies.knb");
    }

    default File getIrFiles() {
        return new File(getIrDir(), "files.knf");
    }

    default File getDataFlowGraphFile() {
        return new File(getIrDir(), "module_data_flow_graph");
    }

    default File getIrDebugInfo() {
        return new File(getIrDir(), "debugInfo.knd");
    }

    default File irDeclarations(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new File(file, "irDeclarations.knd");
    }

    default File irTypes(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new File(file, "types.knt");
    }

    default File irSignatures(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new File(file, "signatures.knt");
    }

    default File irStrings(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new File(file, "strings.knt");
    }

    default File irBodies(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new File(file, "body.knb");
    }

    default File irFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new File(file, "file.knf");
    }

    default File irDebugInfo(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new File(file, "debugInfo.knd");
    }
}
