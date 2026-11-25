package org.jetbrains.kotlin.incremental;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.MessageLite;

/* JADX INFO: compiled from: AbiSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006R \u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/AbiSnapshotImpl;", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "protos", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "(Ljava/util/Map;)V", "getProtos", "()Ljava/util/Map;", "Companion", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbiSnapshotImpl implements AbiSnapshot {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<FqName, ProtoData> protos;

    public AbiSnapshotImpl(Map<FqName, ProtoData> protos) {
        Intrinsics.checkNotNullParameter(protos, "protos");
        this.protos = protos;
    }

    @Override // org.jetbrains.kotlin.incremental.AbiSnapshot
    public Map<FqName, ProtoData> getProtos() {
        return this.protos;
    }

    /* JADX INFO: compiled from: AbiSnapshot.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\n\u0010\f\u001a\u00020\r*\u00020\u000eJ\u0015\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010*\u00020\u000e¢\u0006\u0002\u0010\u0012J\u0012\u0010\u0013\u001a\u00020\b*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004J\u001c\u0010\u0016\u001a\u00020\b*\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001c\u0010\u001b\u001a\u00020\b*\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001cH\u0002J\u001d\u0010\u001d\u001a\u00020\b*\u00020\u00142\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\u0002\u0010\u001f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/incremental/AbiSnapshotImpl$Companion;", "", "()V", "read", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "file", "Ljava/io/File;", "write", "", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "buildInfo", "readAbiSnapshot", "Lorg/jetbrains/kotlin/incremental/AbiSnapshotImpl;", "Ljava/io/ObjectInputStream;", "readStringArray", "", "", "(Ljava/io/ObjectInputStream;)[Ljava/lang/String;", "writeAbiSnapshot", "Ljava/io/ObjectOutputStream;", "abiSnapshot", "writeMessageWithJvmNameResolver", CrashHianalyticsData.MESSAGE, "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;", "writeMessageWithNameResolverImpl", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "writeStringArray", "stringArray", "(Ljava/io/ObjectOutputStream;[Ljava/lang/String;)V", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String[] readStringArray(ObjectInputStream objectInputStream) throws IOException {
            Intrinsics.checkNotNullParameter(objectInputStream, "<this>");
            int i = objectInputStream.readInt();
            String[] strArr = new String[i];
            for (int i2 = 0; i2 < i; i2++) {
                strArr[i2] = objectInputStream.readUTF();
            }
            return (String[]) ArraysKt.requireNoNulls(strArr);
        }

        public final AbiSnapshotImpl readAbiSnapshot(ObjectInputStream objectInputStream) throws IOException {
            Intrinsics.checkNotNullParameter(objectInputStream, "<this>");
            int i = objectInputStream.readInt();
            HashMap map = new HashMap();
            for (int i2 = 0; i2 < i; i2++) {
                String utf = objectInputStream.readUTF();
                if (objectInputStream.readBoolean()) {
                    FqName fqName = new FqName(utf);
                    Pair<JvmNameResolver, ProtoBuf.Class> classDataFrom = JvmProtoBufUtil.readClassDataFrom(AbiSnapshotImpl.INSTANCE.readStringArray(objectInputStream), AbiSnapshotImpl.INSTANCE.readStringArray(objectInputStream));
                    map.put(fqName, new ClassProtoData(classDataFrom.component2(), classDataFrom.component1()));
                } else {
                    FqName fqName2 = new FqName(utf);
                    FqName fqName3 = new FqName(objectInputStream.readUTF());
                    Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(AbiSnapshotImpl.INSTANCE.readStringArray(objectInputStream), AbiSnapshotImpl.INSTANCE.readStringArray(objectInputStream));
                    map.put(fqName2, new PackagePartProtoData(packageDataFrom.component2(), packageDataFrom.component1(), fqName3));
                }
            }
            return new AbiSnapshotImpl(map);
        }

        public final void writeStringArray(ObjectOutputStream objectOutputStream, String[] stringArray) throws IOException {
            Intrinsics.checkNotNullParameter(objectOutputStream, "<this>");
            Intrinsics.checkNotNullParameter(stringArray, "stringArray");
            objectOutputStream.writeInt(stringArray.length);
            for (String str : stringArray) {
                objectOutputStream.writeUTF(str);
            }
        }

        public final void writeAbiSnapshot(ObjectOutputStream objectOutputStream, AbiSnapshot abiSnapshot) throws IOException {
            Intrinsics.checkNotNullParameter(objectOutputStream, "<this>");
            Intrinsics.checkNotNullParameter(abiSnapshot, "abiSnapshot");
            objectOutputStream.writeInt(abiSnapshot.getProtos().size());
            for (Map.Entry<FqName, ProtoData> entry : abiSnapshot.getProtos().entrySet()) {
                objectOutputStream.writeUTF(entry.getKey().asString());
                ProtoData value = entry.getValue();
                if (value instanceof ClassProtoData) {
                    objectOutputStream.writeBoolean(true);
                    ClassProtoData classProtoData = (ClassProtoData) value;
                    NameResolver nameResolver = classProtoData.getNameResolver();
                    if (nameResolver instanceof NameResolverImpl) {
                        writeMessageWithNameResolverImpl(objectOutputStream, classProtoData.getProto(), (NameResolverImpl) nameResolver);
                    } else if (nameResolver instanceof JvmNameResolver) {
                        writeMessageWithJvmNameResolver(objectOutputStream, classProtoData.getProto(), (JvmNameResolver) nameResolver);
                    } else {
                        throw new IllegalStateException("Can't store name resolver for class proto: " + nameResolver.getClass());
                    }
                } else if (value instanceof PackagePartProtoData) {
                    objectOutputStream.writeBoolean(false);
                    PackagePartProtoData packagePartProtoData = (PackagePartProtoData) value;
                    objectOutputStream.writeUTF(packagePartProtoData.getPackageFqName().asString());
                    NameResolver nameResolver2 = packagePartProtoData.getNameResolver();
                    if (nameResolver2 instanceof JvmNameResolver) {
                        writeMessageWithJvmNameResolver(objectOutputStream, packagePartProtoData.getProto(), (JvmNameResolver) nameResolver2);
                    } else if (nameResolver2 instanceof NameResolverImpl) {
                        writeMessageWithNameResolverImpl(objectOutputStream, packagePartProtoData.getProto(), (NameResolverImpl) nameResolver2);
                    } else {
                        throw new IllegalStateException("Can't store name resolver for package proto: " + nameResolver2.getClass());
                    }
                } else {
                    continue;
                }
            }
        }

        private final void writeMessageWithNameResolverImpl(ObjectOutputStream objectOutputStream, MessageLite messageLite, NameResolverImpl nameResolverImpl) throws IOException {
            JvmStringTable jvmStringTable = new JvmStringTable(null, 1, null);
            int stringCount = nameResolverImpl.getStrings().getStringCount();
            for (int i = 0; i < stringCount; i++) {
                jvmStringTable.getStringIndex(nameResolverImpl.getString(i));
            }
            int qualifiedNameCount = nameResolverImpl.getQualifiedNames().getQualifiedNameCount();
            for (int i2 = 0; i2 < qualifiedNameCount; i2++) {
                jvmStringTable.getQualifiedClassNameIndex(nameResolverImpl.getQualifiedClassName(i2), nameResolverImpl.isLocalClassName(i2));
            }
            writeStringArray(objectOutputStream, JvmProtoBufUtil.writeData(messageLite, jvmStringTable));
            int stringCount2 = nameResolverImpl.getStrings().getStringCount();
            objectOutputStream.writeInt(stringCount2);
            for (int i3 = 0; i3 < stringCount2; i3++) {
                objectOutputStream.writeUTF(nameResolverImpl.getString(i3));
            }
        }

        private final void writeMessageWithJvmNameResolver(ObjectOutputStream objectOutputStream, MessageLite messageLite, JvmNameResolver jvmNameResolver) throws IOException {
            writeStringArray(objectOutputStream, JvmProtoBufUtil.writeData(messageLite, new JvmStringTable(jvmNameResolver)));
            writeStringArray(objectOutputStream, jvmNameResolver.getStrings());
        }

        public final void write(IncrementalCompilationContext icContext, final AbiSnapshot buildInfo, final File file) {
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            Intrinsics.checkNotNullParameter(buildInfo, "buildInfo");
            Intrinsics.checkNotNullParameter(file, "file");
            CompilationTransaction transaction = icContext.getTransaction();
            Path path = file.toPath();
            Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
            CompilationTransactionKt.write(transaction, path, new Function0<Unit>() { // from class: org.jetbrains.kotlin.incremental.AbiSnapshotImpl$Companion$write$1
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
                    AbiSnapshot abiSnapshot = buildInfo;
                    try {
                        AbiSnapshotImpl.INSTANCE.writeAbiSnapshot(objectOutputStream, abiSnapshot);
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

        public final AbiSnapshot read(File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            try {
                AbiSnapshotImpl abiSnapshot = AbiSnapshotImpl.INSTANCE.readAbiSnapshot(objectInputStream);
                CloseableKt.closeFinally(objectInputStream, null);
                return abiSnapshot;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(objectInputStream, th);
                    throw th2;
                }
            }
        }
    }
}
