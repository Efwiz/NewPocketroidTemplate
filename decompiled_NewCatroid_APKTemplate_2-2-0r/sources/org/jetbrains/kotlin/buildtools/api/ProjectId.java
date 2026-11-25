package org.jetbrains.kotlin.buildtools.api;

import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ProjectId.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0001\u0002\u0082\u0001\u0001\u0003ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/ProjectId;", "", "ProjectUUID", "Lorg/jetbrains/kotlin/buildtools/api/ProjectId$ProjectUUID;", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ProjectId {

    /* JADX INFO: compiled from: ProjectId.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/ProjectId$ProjectUUID;", "Lorg/jetbrains/kotlin/buildtools/api/ProjectId;", "uuid", "Ljava/util/UUID;", "(Ljava/util/UUID;)V", "getUuid", "()Ljava/util/UUID;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final /* data */ class ProjectUUID implements ProjectId {
        private final UUID uuid;

        public static /* synthetic */ ProjectUUID copy$default(ProjectUUID projectUUID, UUID uuid, int i, Object obj) {
            if ((i & 1) != 0) {
                uuid = projectUUID.uuid;
            }
            return projectUUID.copy(uuid);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final UUID getUuid() {
            return this.uuid;
        }

        public final ProjectUUID copy(UUID uuid) {
            Intrinsics.checkNotNullParameter(uuid, "uuid");
            return new ProjectUUID(uuid);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof ProjectUUID) && Intrinsics.areEqual(this.uuid, ((ProjectUUID) other).uuid);
        }

        public int hashCode() {
            return this.uuid.hashCode();
        }

        public String toString() {
            return "ProjectUUID(uuid=" + this.uuid + ')';
        }

        public ProjectUUID(UUID uuid) {
            Intrinsics.checkNotNullParameter(uuid, "uuid");
            this.uuid = uuid;
        }

        public final UUID getUuid() {
            return this.uuid;
        }
    }
}
