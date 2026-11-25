package org.jetbrains.kotlin.library.abi;

import com.google.android.gms.cast.MediaTrack;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.library.abi.impl.AbiSignatureVersions;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bg\u0018\u0000 \r2\u00020\u0001:\u0001\rR\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "", MediaTrack.ROLE_DESCRIPTION, "", "getDescription", "()Ljava/lang/String;", "isSupportedByAbiReader", "", "()Z", "versionNumber", "", "getVersionNumber", "()I", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiSignatureVersion {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    String getDescription();

    int getVersionNumber();

    boolean isSupportedByAbiReader();

    /* JADX INFO: compiled from: LibraryAbi.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion$Companion;", "", "()V", "allSupportedByAbiReader", "", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "getAllSupportedByAbiReader", "()Ljava/util/List;", "resolveByVersionNumber", "versionNumber", "", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final List<AbiSignatureVersion> getAllSupportedByAbiReader() {
            return AbiSignatureVersions.Supported.getEntries();
        }

        public final AbiSignatureVersion resolveByVersionNumber(int versionNumber) {
            return AbiSignatureVersions.INSTANCE.resolveByVersionNumber(versionNumber);
        }
    }
}
