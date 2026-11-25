package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.incremental.util.ExceptionLocation;
import org.jetbrains.kotlin.incremental.util.MessageCollectorUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BuildInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005HÆ\u0003J)\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/incremental/BuildInfo;", "Ljava/io/Serializable;", "startTS", "", "dependencyToAbiSnapshot", "", "", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "(JLjava/util/Map;)V", "getDependencyToAbiSnapshot", "()Ljava/util/Map;", "getStartTS", "()J", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class BuildInfo implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<String, AbiSnapshot> dependencyToAbiSnapshot;
    private final long startTS;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BuildInfo copy$default(BuildInfo buildInfo, long j, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            j = buildInfo.startTS;
        }
        if ((i & 2) != 0) {
            map = buildInfo.dependencyToAbiSnapshot;
        }
        return buildInfo.copy(j, map);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getStartTS() {
        return this.startTS;
    }

    public final Map<String, AbiSnapshot> component2() {
        return this.dependencyToAbiSnapshot;
    }

    public final BuildInfo copy(long startTS, Map<String, ? extends AbiSnapshot> dependencyToAbiSnapshot) {
        Intrinsics.checkNotNullParameter(dependencyToAbiSnapshot, "dependencyToAbiSnapshot");
        return new BuildInfo(startTS, dependencyToAbiSnapshot);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BuildInfo)) {
            return false;
        }
        BuildInfo buildInfo = (BuildInfo) other;
        return this.startTS == buildInfo.startTS && Intrinsics.areEqual(this.dependencyToAbiSnapshot, buildInfo.dependencyToAbiSnapshot);
    }

    public int hashCode() {
        return (Long.hashCode(this.startTS) * 31) + this.dependencyToAbiSnapshot.hashCode();
    }

    public String toString() {
        return "BuildInfo(startTS=" + this.startTS + ", dependencyToAbiSnapshot=" + this.dependencyToAbiSnapshot + ')';
    }

    /* JADX INFO: compiled from: BuildInfo.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u000e\u001a\u00020\u0004*\u00020\u000fH\u0002J\u0014\u0010\u0010\u001a\u00020\n*\u00020\u00112\u0006\u0010\r\u001a\u00020\u0004H\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/incremental/BuildInfo$Companion;", "", "()V", "read", "Lorg/jetbrains/kotlin/incremental/BuildInfo;", "file", "Ljava/io/File;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "write", "", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "buildInfo", "readBuildInfo", "Ljava/io/ObjectInputStream;", "writeBuildInfo", "Ljava/io/ObjectOutputStream;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final BuildInfo readBuildInfo(ObjectInputStream objectInputStream) throws IOException {
            long j = objectInputStream.readLong();
            int i = objectInputStream.readInt();
            HashMap map = new HashMap(i);
            for (int i2 = 0; i2 < i; i2++) {
                map.put(objectInputStream.readUTF(), AbiSnapshotImpl.INSTANCE.readAbiSnapshot(objectInputStream));
            }
            return new BuildInfo(j, map);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void writeBuildInfo(ObjectOutputStream objectOutputStream, BuildInfo buildInfo) throws IOException {
            objectOutputStream.writeLong(buildInfo.getStartTS());
            objectOutputStream.writeInt(buildInfo.getDependencyToAbiSnapshot().size());
            for (Map.Entry<String, AbiSnapshot> entry : buildInfo.getDependencyToAbiSnapshot().entrySet()) {
                String key = entry.getKey();
                AbiSnapshot value = entry.getValue();
                objectOutputStream.writeUTF(key);
                AbiSnapshotImpl.INSTANCE.writeAbiSnapshot(objectOutputStream, value);
            }
        }

        public final BuildInfo read(File file, MessageCollector messageCollector) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                try {
                    BuildInfo buildInfo = BuildInfo.INSTANCE.readBuildInfo(objectInputStream);
                    CloseableKt.closeFinally(objectInputStream, null);
                    return buildInfo;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(objectInputStream, th);
                        throw th2;
                    }
                }
            } catch (Exception e) {
                MessageCollectorUtilKt.reportException(messageCollector, e, ExceptionLocation.INCREMENTAL_COMPILATION);
                return null;
            }
        }

        public final void write(IncrementalCompilationContext icContext, final BuildInfo buildInfo, final File file) {
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            Intrinsics.checkNotNullParameter(buildInfo, "buildInfo");
            Intrinsics.checkNotNullParameter(file, "file");
            CompilationTransaction transaction = icContext.getTransaction();
            Path path = file.toPath();
            Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
            CompilationTransactionKt.write(transaction, path, new Function0<Unit>() { // from class: org.jetbrains.kotlin.incremental.BuildInfo$Companion$write$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    BuildInfo buildInfo2 = buildInfo;
                    try {
                        BuildInfo.INSTANCE.writeBuildInfo(objectOutputStream, buildInfo2);
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(objectOutputStream, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(objectOutputStream, th);
                            throw th2;
                        }
                    }
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BuildInfo(long j, Map<String, ? extends AbiSnapshot> dependencyToAbiSnapshot) {
        Intrinsics.checkNotNullParameter(dependencyToAbiSnapshot, "dependencyToAbiSnapshot");
        this.startTS = j;
        this.dependencyToAbiSnapshot = dependencyToAbiSnapshot;
    }

    public /* synthetic */ BuildInfo(long j, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i & 2) != 0 ? MapsKt.emptyMap() : map);
    }

    public final Map<String, AbiSnapshot> getDependencyToAbiSnapshot() {
        return this.dependencyToAbiSnapshot;
    }

    public final long getStartTS() {
        return this.startTS;
    }
}
