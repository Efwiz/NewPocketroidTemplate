package org.jetbrains.kotlin.library.abi.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0001\u0018\u0000 +2\u00020\u0001:\u0001+BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0002\u0010\u000fJ\u0010\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010'¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiConstructorImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "qualifiedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "signatures", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "annotations", "", "isInline", "", "contextReceiverParametersCount", "", "valueParameters", "", "Lorg/jetbrains/kotlin/library/abi/AbiValueParameter;", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/AbiSignatures;Ljava/util/Set;ZILjava/util/List;)V", "getContextReceiverParametersCount", "()I", OverlayablePolicy.NAME_flags, "hasExtensionReceiverParameter", "getHasExtensionReceiverParameter", "()Z", "isConstructor", "isSuspend", "modality", "Lorg/jetbrains/kotlin/library/abi/AbiModality;", "getModality", "()Lorg/jetbrains/kotlin/library/abi/AbiModality;", "getQualifiedName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "returnType", "", "getReturnType", "()Ljava/lang/Void;", "getSignatures", "()Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "typeParameters", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "getValueParameters", "hasAnnotation", "annotationClassName", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiConstructorImpl implements AbiFunction {
    private static final FlagFieldEx<Integer> CONTEXT_RECEIVERS_COUNT;
    private static final Flags.BooleanFlagField IS_INLINE;
    private final Set<AbiQualifiedName> annotations;
    private final int flags;
    private final AbiQualifiedName qualifiedName;
    private final AbiSignatures signatures;
    private final List<AbiValueParameter> valueParameters;

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean getHasExtensionReceiverParameter() {
        return false;
    }

    public Void getReturnType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean isConstructor() {
        return true;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean isSuspend() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AbiConstructorImpl(AbiQualifiedName qualifiedName, AbiSignatures signatures, Set<AbiQualifiedName> annotations, boolean z, int i, List<? extends AbiValueParameter> valueParameters) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        this.qualifiedName = qualifiedName;
        this.signatures = signatures;
        this.annotations = annotations;
        this.valueParameters = valueParameters;
        this.flags = IS_INLINE.toFlags(Boolean.valueOf(z)) | CONTEXT_RECEIVERS_COUNT.toFlags(Integer.valueOf(i));
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public /* bridge */ /* synthetic */ AbiType getReturnType() {
        return (AbiType) getReturnType();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiQualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiSignatures getSignatures() {
        return this.signatures;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public List<AbiValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclarationWithModality
    public AbiModality getModality() {
        return AbiModality.FINAL;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public boolean isInline() {
        Boolean bool = IS_INLINE.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiFunction
    public int getContextReceiverParametersCount() {
        return CONTEXT_RECEIVERS_COUNT.get(this.flags).intValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeParametersContainer
    public List<AbiTypeParameter> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public boolean hasAnnotation(AbiQualifiedName annotationClassName) {
        Intrinsics.checkNotNullParameter(annotationClassName, "annotationClassName");
        return this.annotations.contains(annotationClassName);
    }

    static {
        Flags.BooleanFlagField IS_INLINE2 = Flags.FlagField.booleanFirst();
        IS_INLINE = IS_INLINE2;
        FlagFieldEx.Companion companion = FlagFieldEx.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(IS_INLINE2, "IS_INLINE");
        CONTEXT_RECEIVERS_COUNT = companion.intAfter(IS_INLINE2, 8);
    }
}
