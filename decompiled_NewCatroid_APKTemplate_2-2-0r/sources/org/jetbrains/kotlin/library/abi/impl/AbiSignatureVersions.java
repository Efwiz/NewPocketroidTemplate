package org.jetbrains.kotlin.library.abi.impl;

import com.google.android.gms.cast.MediaTrack;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.abi.AbiSignatureVersion;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bÁ\u0002\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiSignatureVersions;", "", "()V", "resolveByVersionNumber", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "versionNumber", "", "Supported", "Unsupported", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiSignatureVersions {
    public static final AbiSignatureVersions INSTANCE = new AbiSignatureVersions();

    private AbiSignatureVersions() {
    }

    /* JADX INFO: compiled from: LibraryAbiImpl.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0017\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiSignatureVersions$Supported;", "", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "versionNumber", "", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;IILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "isSupportedByAbiReader", "", "()Z", "getVersionNumber", "()I", "V1", "V2", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Supported implements AbiSignatureVersion {
        V1(1, "The signatures with hashes"),
        V2(2, "The self-descriptive signatures (with mangled names)");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String description;
        private final int versionNumber;

        public static EnumEntries<Supported> getEntries() {
            return $ENTRIES;
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiSignatureVersion
        public boolean isSupportedByAbiReader() {
            return true;
        }

        Supported(int i, String str) {
            this.versionNumber = i;
            this.description = str;
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiSignatureVersion
        public String getDescription() {
            return this.description;
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiSignatureVersion
        public int getVersionNumber() {
            return this.versionNumber;
        }
    }

    /* JADX INFO: compiled from: LibraryAbiImpl.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0082\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiSignatureVersions$Unsupported;", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "versionNumber", "", "(I)V", MediaTrack.ROLE_DESCRIPTION, "", "getDescription", "()Ljava/lang/String;", "isSupportedByAbiReader", "", "()Z", "getVersionNumber", "()I", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "toString", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Unsupported implements AbiSignatureVersion {
        private final int versionNumber;

        public static /* synthetic */ Unsupported copy$default(Unsupported unsupported, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = unsupported.versionNumber;
            }
            return unsupported.copy(i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getVersionNumber() {
            return this.versionNumber;
        }

        public final Unsupported copy(int versionNumber) {
            return new Unsupported(versionNumber);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Unsupported) && this.versionNumber == ((Unsupported) other).versionNumber;
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiSignatureVersion
        public String getDescription() {
            return null;
        }

        public int hashCode() {
            return Integer.hashCode(this.versionNumber);
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiSignatureVersion
        public boolean isSupportedByAbiReader() {
            return false;
        }

        public String toString() {
            return "Unsupported(versionNumber=" + this.versionNumber + ')';
        }

        public Unsupported(int i) {
            this.versionNumber = i;
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiSignatureVersion
        public int getVersionNumber() {
            return this.versionNumber;
        }
    }

    public final AbiSignatureVersion resolveByVersionNumber(int versionNumber) {
        Supported next;
        Iterator<Supported> it = Supported.getEntries().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (next.getVersionNumber() != versionNumber);
        Supported supported = next;
        return supported != null ? supported : new Unsupported(versionNumber);
    }
}
