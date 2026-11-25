package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: FixStackValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B'\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "_size", "", "loadOpcode", "storeOpcode", "popOpcode", "(Ljava/lang/String;IIIII)V", "getLoadOpcode", "()I", "getPopOpcode", "getStoreOpcode", "getSize", "INT", "LONG", "FLOAT", "DOUBLE", Namer.CLASS_KIND_OBJECT, "UNINITIALIZED", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum FixStackValue implements Value {
    INT(1, 21, 54, 87),
    LONG(2, 22, 55, 88),
    FLOAT(1, 23, 56, 87),
    DOUBLE(2, 24, 57, 88),
    OBJECT(1, 25, 58, 87),
    UNINITIALIZED(1, -1, -1, -1);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final int _size;
    private final int loadOpcode;
    private final int popOpcode;
    private final int storeOpcode;

    public static EnumEntries<FixStackValue> getEntries() {
        return $ENTRIES;
    }

    FixStackValue(int i, int i2, int i3, int i4) {
        this._size = i;
        this.loadOpcode = i2;
        this.storeOpcode = i3;
        this.popOpcode = i4;
    }

    public final int getLoadOpcode() {
        return this.loadOpcode;
    }

    public final int getStoreOpcode() {
        return this.storeOpcode;
    }

    public final int getPopOpcode() {
        return this.popOpcode;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Value
    /* JADX INFO: renamed from: getSize, reason: from getter */
    public int get_size() {
        return this._size;
    }
}
