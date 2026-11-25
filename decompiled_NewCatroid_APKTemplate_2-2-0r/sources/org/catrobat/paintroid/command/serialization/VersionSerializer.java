package org.catrobat.paintroid.command.serialization;

import androidx.exifinterface.media.ExifInterface;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: VersionSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 \u0016*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\u0016B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J;\u0010\b\u001a\u00028\u00002\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u000fH\u0004¢\u0006\u0002\u0010\u0010J-\u0010\u0011\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u000fH&¢\u0006\u0002\u0010\u0012J;\u0010\u0013\u001a\u00028\u00002\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u000fH\u0014¢\u0006\u0002\u0010\u0010J;\u0010\u0014\u001a\u00028\u00002\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u000fH\u0014¢\u0006\u0002\u0010\u0010J;\u0010\u0015\u001a\u00028\u00002\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u000fH\u0014¢\u0006\u0002\u0010\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/esotericsoftware/kryo/Serializer;", "version", "", "(I)V", "getVersion", "()I", "handleVersions", "serializer", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "(Lorg/catrobat/paintroid/command/serialization/VersionSerializer;Lcom/esotericsoftware/kryo/Kryo;Lcom/esotericsoftware/kryo/io/Input;Ljava/lang/Class;)Ljava/lang/Object;", "readCurrentVersion", "(Lcom/esotericsoftware/kryo/Kryo;Lcom/esotericsoftware/kryo/io/Input;Ljava/lang/Class;)Ljava/lang/Object;", "readV1", "readV2", "readV3", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class VersionSerializer<T> extends Serializer<T> {
    private static final int V1 = 1;
    private static final int V2 = 2;
    private static final int V3 = 3;
    private final int version;

    public abstract T readCurrentVersion(Kryo kryo, Input input, Class<? extends T> type);

    public VersionSerializer(int i) {
        this.version = i;
    }

    public final int getVersion() {
        return this.version;
    }

    protected final T handleVersions(VersionSerializer<T> serializer, Kryo kryo, Input input, Class<? extends T> type) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = this.version;
        if (i == 1) {
            return serializer.readV1(serializer, kryo, input, type);
        }
        if (i == 2) {
            return serializer.readV2(serializer, kryo, input, type);
        }
        if (i == 3) {
            return serializer.readV3(serializer, kryo, input, type);
        }
        throw new KryoException();
    }

    protected T readV1(VersionSerializer<T> serializer, Kryo kryo, Input input, Class<? extends T> type) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return serializer.readV2(serializer, kryo, input, type);
    }

    protected T readV2(VersionSerializer<T> serializer, Kryo kryo, Input input, Class<? extends T> type) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return serializer.readV3(serializer, kryo, input, type);
    }

    protected T readV3(VersionSerializer<T> serializer, Kryo kryo, Input input, Class<? extends T> type) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return serializer.readCurrentVersion(kryo, input, type);
    }
}
