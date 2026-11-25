package org.jetbrains.kotlin.library.abi.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiTypeParameter;
import org.jetbrains.kotlin.library.abi.AbiVariance;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\b\u0001\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiTypeParameterImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParameter;", "tag", "", "variance", "Lorg/jetbrains/kotlin/library/abi/AbiVariance;", "isReified", "", "upperBounds", "", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/library/abi/AbiVariance;ZLjava/util/List;)V", OverlayablePolicy.NAME_flags, "", "()Z", "getTag", "()Ljava/lang/String;", "getUpperBounds", "()Ljava/util/List;", "getVariance", "()Lorg/jetbrains/kotlin/library/abi/AbiVariance;", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiTypeParameterImpl implements AbiTypeParameter {
    private static final Flags.BooleanFlagField IS_REIFIED;
    private static final FlagFieldEx<AbiVariance> VARIANCE;
    private final int flags;
    private final boolean isReified;
    private final String tag;
    private final List<AbiType> upperBounds;
    private final AbiVariance variance;

    /* JADX WARN: Multi-variable type inference failed */
    public AbiTypeParameterImpl(String tag, AbiVariance variance, boolean z, List<? extends AbiType> upperBounds) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(variance, "variance");
        Intrinsics.checkNotNullParameter(upperBounds, "upperBounds");
        this.tag = tag;
        this.upperBounds = upperBounds;
        Flags.BooleanFlagField booleanFlagField = IS_REIFIED;
        int flags = booleanFlagField.toFlags(Boolean.valueOf(z));
        FlagFieldEx<AbiVariance> flagFieldEx = VARIANCE;
        int flags2 = flagFieldEx.toFlags(variance) | flags;
        this.flags = flags2;
        this.variance = flagFieldEx.get(flags2);
        Boolean bool = booleanFlagField.get(flags2);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        this.isReified = bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeParameter
    public String getTag() {
        return this.tag;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeParameter
    public List<AbiType> getUpperBounds() {
        return this.upperBounds;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeParameter
    public AbiVariance getVariance() {
        return this.variance;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeParameter
    /* JADX INFO: renamed from: isReified, reason: from getter */
    public boolean getIsReified() {
        return this.isReified;
    }

    static {
        Flags.BooleanFlagField IS_REIFIED2 = Flags.FlagField.booleanFirst();
        IS_REIFIED = IS_REIFIED2;
        FlagFieldEx.Companion companion = FlagFieldEx.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(IS_REIFIED2, "IS_REIFIED");
        VARIANCE = new FlagFieldEx.EnumFlagFieldEx(companion.getNextOffset(IS_REIFIED2), AbiVariance.values());
    }
}
