package org.jetbrains.kotlin.library.abi.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.AbiFunction;
import org.jetbrains.kotlin.library.abi.AbiModality;
import org.jetbrains.kotlin.library.abi.AbiQualifiedName;
import org.jetbrains.kotlin.library.abi.AbiSignatures;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiTypeParameter;
import org.jetbrains.kotlin.library.abi.AbiValueParameter;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\b\u0001\u0018\u0000 +2\u00020\u0001:\u0001+Bq\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u000b\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\u0002\u0010\u0017J\u0010\u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001cR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u001cR\u0014\u0010\f\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u001cR\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010'¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiFunctionImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "qualifiedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "signatures", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "annotations", "", "modality", "Lorg/jetbrains/kotlin/library/abi/AbiModality;", "isInline", "", "isSuspend", "typeParameters", "", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParameter;", "hasExtensionReceiverParameter", "contextReceiverParametersCount", "", "valueParameters", "Lorg/jetbrains/kotlin/library/abi/AbiValueParameter;", "returnType", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/AbiSignatures;Ljava/util/Set;Lorg/jetbrains/kotlin/library/abi/AbiModality;ZZLjava/util/List;ZILjava/util/List;Lorg/jetbrains/kotlin/library/abi/AbiType;)V", "getContextReceiverParametersCount", "()I", OverlayablePolicy.NAME_flags, "getHasExtensionReceiverParameter", "()Z", "isConstructor", "getModality", "()Lorg/jetbrains/kotlin/library/abi/AbiModality;", "getQualifiedName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "getReturnType", "()Lorg/jetbrains/kotlin/library/abi/AbiType;", "getSignatures", "()Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "getTypeParameters", "()Ljava/util/List;", "getValueParameters", "hasAnnotation", "annotationClassName", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiFunctionImpl implements AbiFunction {
    public static final int BITS_ENOUGH_FOR_STORING_PARAMETERS_COUNT = 8;
    private static final FlagFieldEx<Integer> CONTEXT_RECEIVERS_COUNT;
    private static final Flags.BooleanFlagField HAS_EXTENSION_RECEIVER;
    private static final Flags.BooleanFlagField IS_INLINE;
    private static final Flags.BooleanFlagField IS_SUSPEND;
    private static final FlagFieldEx<AbiModality> MODALITY;
    private final Set<AbiQualifiedName> annotations;
    private final int flags;
    private final AbiQualifiedName qualifiedName;
    private final AbiType returnType;
    private final AbiSignatures signatures;
    private final List<AbiTypeParameter> typeParameters;
    private final List<AbiValueParameter> valueParameters;

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean isConstructor() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AbiFunctionImpl(AbiQualifiedName qualifiedName, AbiSignatures signatures, Set<AbiQualifiedName> annotations, AbiModality modality, boolean z, boolean z2, List<? extends AbiTypeParameter> typeParameters, boolean z3, int i, List<? extends AbiValueParameter> valueParameters, AbiType abiType) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        this.qualifiedName = qualifiedName;
        this.signatures = signatures;
        this.annotations = annotations;
        this.typeParameters = typeParameters;
        this.valueParameters = valueParameters;
        this.returnType = abiType;
        this.flags = IS_INLINE.toFlags(Boolean.valueOf(z)) | IS_SUSPEND.toFlags(Boolean.valueOf(z2)) | HAS_EXTENSION_RECEIVER.toFlags(Boolean.valueOf(z3)) | CONTEXT_RECEIVERS_COUNT.toFlags(Integer.valueOf(i)) | MODALITY.toFlags(modality);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiQualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiSignatures getSignatures() {
        return this.signatures;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeParametersContainer
    public List<AbiTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public List<AbiValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public AbiType getReturnType() {
        return this.returnType;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclarationWithModality
    public AbiModality getModality() {
        return MODALITY.get(this.flags);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean isInline() {
        Boolean bool = IS_INLINE.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean isSuspend() {
        Boolean bool = IS_SUSPEND.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean getHasExtensionReceiverParameter() {
        Boolean bool = HAS_EXTENSION_RECEIVER.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public int getContextReceiverParametersCount() {
        return CONTEXT_RECEIVERS_COUNT.get(this.flags).intValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public boolean hasAnnotation(AbiQualifiedName annotationClassName) {
        Intrinsics.checkNotNullParameter(annotationClassName, "annotationClassName");
        return this.annotations.contains(annotationClassName);
    }

    static {
        Flags.BooleanFlagField booleanFlagFieldBooleanFirst = Flags.FlagField.booleanFirst();
        IS_INLINE = booleanFlagFieldBooleanFirst;
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanFirst);
        IS_SUSPEND = booleanFlagFieldBooleanAfter;
        Flags.BooleanFlagField HAS_EXTENSION_RECEIVER2 = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter);
        HAS_EXTENSION_RECEIVER = HAS_EXTENSION_RECEIVER2;
        FlagFieldEx.Companion companion = FlagFieldEx.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(HAS_EXTENSION_RECEIVER2, "HAS_EXTENSION_RECEIVER");
        FlagFieldEx<Integer> flagFieldExIntAfter = companion.intAfter(HAS_EXTENSION_RECEIVER2, 8);
        CONTEXT_RECEIVERS_COUNT = flagFieldExIntAfter;
        MODALITY = new FlagFieldEx.EnumFlagFieldEx(FlagFieldEx.INSTANCE.getNextOffset(flagFieldExIntAfter), AbiModality.values());
    }
}
