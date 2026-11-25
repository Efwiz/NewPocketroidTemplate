package org.jetbrains.kotlin.codegen.range;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BoundedValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/BoundedValue;", "", "lowBound", "Lorg/jetbrains/kotlin/codegen/StackValue;", "isLowInclusive", "", "highBound", "isHighInclusive", "(Lorg/jetbrains/kotlin/codegen/StackValue;ZLorg/jetbrains/kotlin/codegen/StackValue;Z)V", "getHighBound", "()Lorg/jetbrains/kotlin/codegen/StackValue;", "()Z", "getLowBound", "putHighLow", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BoundedValue {
    private final StackValue highBound;
    private final boolean isHighInclusive;
    private final boolean isLowInclusive;
    private final StackValue lowBound;

    public BoundedValue(StackValue lowBound, boolean z, StackValue highBound, boolean z2) {
        Intrinsics.checkNotNullParameter(lowBound, "lowBound");
        Intrinsics.checkNotNullParameter(highBound, "highBound");
        this.lowBound = lowBound;
        this.isLowInclusive = z;
        this.highBound = highBound;
        this.isHighInclusive = z2;
    }

    public /* synthetic */ BoundedValue(StackValue stackValue, boolean z, StackValue stackValue2, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(stackValue, (i & 2) != 0 ? true : z, stackValue2, (i & 8) != 0 ? true : z2);
    }

    public final StackValue getLowBound() {
        return this.lowBound;
    }

    /* JADX INFO: renamed from: isLowInclusive, reason: from getter */
    public final boolean getIsLowInclusive() {
        return this.isLowInclusive;
    }

    public final StackValue getHighBound() {
        return this.highBound;
    }

    /* JADX INFO: renamed from: isHighInclusive, reason: from getter */
    public final boolean getIsHighInclusive() {
        return this.isHighInclusive;
    }

    public final void putHighLow(InstructionAdapter v, Type type) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(type, "type");
        if (!this.lowBound.canHaveSideEffects() || !this.highBound.canHaveSideEffects()) {
            this.highBound.put(type, v);
            this.lowBound.put(type, v);
        } else {
            this.lowBound.put(type, v);
            this.highBound.put(type, v);
            AsmUtil.swap(v, type, type);
        }
    }
}
