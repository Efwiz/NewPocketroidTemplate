package org.jetbrains.kotlin.library.abi.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiValueParameter;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\b\u0001\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\rR\u0014\u0010\u0007\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\rR\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiValueParameterImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiValueParameter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/library/abi/AbiType;", "isVararg", "", "hasDefaultArg", "isNoinline", "isCrossinline", "(Lorg/jetbrains/kotlin/library/abi/AbiType;ZZZZ)V", OverlayablePolicy.NAME_flags, "", "getHasDefaultArg", "()Z", "getType", "()Lorg/jetbrains/kotlin/library/abi/AbiType;", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiValueParameterImpl implements AbiValueParameter {
    private static final Flags.BooleanFlagField HAS_DEFAULT_ARG;
    private static final Flags.BooleanFlagField IS_CROSSINLINE;
    private static final Flags.BooleanFlagField IS_NOINLINE;
    private static final Flags.BooleanFlagField IS_VARARG;
    private final int flags;
    private final AbiType type;

    public AbiValueParameterImpl(AbiType type, boolean z, boolean z2, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.flags = IS_VARARG.toFlags(Boolean.valueOf(z)) | HAS_DEFAULT_ARG.toFlags(Boolean.valueOf(z2)) | IS_NOINLINE.toFlags(Boolean.valueOf(z3)) | IS_CROSSINLINE.toFlags(Boolean.valueOf(z4));
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiValueParameter
    public AbiType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiValueParameter
    public boolean isVararg() {
        Boolean bool = IS_VARARG.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiValueParameter
    public boolean getHasDefaultArg() {
        Boolean bool = HAS_DEFAULT_ARG.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiValueParameter
    public boolean isNoinline() {
        Boolean bool = IS_NOINLINE.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiValueParameter
    public boolean isCrossinline() {
        Boolean bool = IS_CROSSINLINE.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    static {
        Flags.BooleanFlagField booleanFlagFieldBooleanFirst = Flags.FlagField.booleanFirst();
        IS_VARARG = booleanFlagFieldBooleanFirst;
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanFirst);
        HAS_DEFAULT_ARG = booleanFlagFieldBooleanAfter;
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter2 = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter);
        IS_NOINLINE = booleanFlagFieldBooleanAfter2;
        IS_CROSSINLINE = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter2);
    }
}
