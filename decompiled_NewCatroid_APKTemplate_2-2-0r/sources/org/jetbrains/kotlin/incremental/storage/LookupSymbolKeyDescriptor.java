package org.jetbrains.kotlin.incremental.storage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002H\u0016J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\u0010\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\u0002H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKeyDescriptor;", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "storeFullFqNames", "", "(Z)V", "getHashCode", "", "value", "isEqual", "val1", "val2", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookupSymbolKeyDescriptor implements KeyDescriptor<LookupSymbolKey> {
    private final boolean storeFullFqNames;

    public LookupSymbolKeyDescriptor() {
        this(false, 1, null);
    }

    public LookupSymbolKeyDescriptor(boolean z) {
        this.storeFullFqNames = z;
    }

    public /* synthetic */ LookupSymbolKeyDescriptor(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public LookupSymbolKey read(DataInput input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        byte b = input.readByte();
        if (b != 0) {
            if (b != 1) {
                throw new IllegalStateException(("Unexpected byte value for storeFullFqNames: " + ((int) b)).toString());
            }
            return new LookupSymbolKey(input.readInt(), input.readInt(), "", "");
        }
        String utf = input.readUTF();
        String utf2 = input.readUTF();
        int iHashCode = utf.hashCode();
        int iHashCode2 = utf2.hashCode();
        Intrinsics.checkNotNull(utf);
        Intrinsics.checkNotNull(utf2);
        return new LookupSymbolKey(iHashCode, iHashCode2, utf, utf2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor, org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, LookupSymbolKey value) throws IOException {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(value, "value");
        output.writeByte(!this.storeFullFqNames ? 1 : 0);
        if (this.storeFullFqNames) {
            output.writeUTF(value.getName());
            output.writeUTF(value.getScope());
        } else {
            output.writeInt(value.getNameHash());
            output.writeInt(value.getScopeHash());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.KeyDescriptor, org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
    public int getHashCode(LookupSymbolKey value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
    public boolean isEqual(LookupSymbolKey val1, LookupSymbolKey val2) {
        Intrinsics.checkNotNullParameter(val1, "val1");
        Intrinsics.checkNotNullParameter(val2, "val2");
        return Intrinsics.areEqual(val1, val2);
    }
}
