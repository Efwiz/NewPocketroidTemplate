package org.jetbrains.kotlin.library.abi.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.abi.AbiFunction;
import org.jetbrains.kotlin.library.abi.AbiModality;
import org.jetbrains.kotlin.library.abi.AbiProperty;
import org.jetbrains.kotlin.library.abi.AbiPropertyKind;
import org.jetbrains.kotlin.library.abi.AbiQualifiedName;
import org.jetbrains.kotlin.library.abi.AbiSignatures;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0001\u0018\u0000  2\u00020\u0001:\u0001 BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiPropertyImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiProperty;", "qualifiedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "signatures", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "annotations", "", "modality", "Lorg/jetbrains/kotlin/library/abi/AbiModality;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/library/abi/AbiPropertyKind;", "getter", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "setter", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/AbiSignatures;Ljava/util/Set;Lorg/jetbrains/kotlin/library/abi/AbiModality;Lorg/jetbrains/kotlin/library/abi/AbiPropertyKind;Lorg/jetbrains/kotlin/library/abi/AbiFunction;Lorg/jetbrains/kotlin/library/abi/AbiFunction;)V", OverlayablePolicy.NAME_flags, "", "getGetter", "()Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "getKind", "()Lorg/jetbrains/kotlin/library/abi/AbiPropertyKind;", "getModality", "()Lorg/jetbrains/kotlin/library/abi/AbiModality;", "getQualifiedName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "getSetter", "getSignatures", "()Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "hasAnnotation", "", "annotationClassName", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiPropertyImpl implements AbiProperty {
    private static final FlagFieldEx<AbiModality> MODALITY;
    private static final FlagFieldEx<AbiPropertyKind> PROPERTY_KIND;
    private final Set<AbiQualifiedName> annotations;
    private final int flags;
    private final AbiFunction getter;
    private final AbiQualifiedName qualifiedName;
    private final AbiFunction setter;
    private final AbiSignatures signatures;

    public AbiPropertyImpl(AbiQualifiedName qualifiedName, AbiSignatures signatures, Set<AbiQualifiedName> annotations, AbiModality modality, AbiPropertyKind kind, AbiFunction abiFunction, AbiFunction abiFunction2) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.qualifiedName = qualifiedName;
        this.signatures = signatures;
        this.annotations = annotations;
        this.getter = abiFunction;
        this.setter = abiFunction2;
        this.flags = MODALITY.toFlags(modality) | PROPERTY_KIND.toFlags(kind);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiQualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiSignatures getSignatures() {
        return this.signatures;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiProperty
    public AbiFunction getGetter() {
        return this.getter;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiProperty
    public AbiFunction getSetter() {
        return this.setter;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclarationWithModality
    public AbiModality getModality() {
        return MODALITY.get(this.flags);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiProperty
    public AbiPropertyKind getKind() {
        return PROPERTY_KIND.get(this.flags);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public boolean hasAnnotation(AbiQualifiedName annotationClassName) {
        Intrinsics.checkNotNullParameter(annotationClassName, "annotationClassName");
        return this.annotations.contains(annotationClassName);
    }

    static {
        FlagFieldEx.Companion companion = FlagFieldEx.INSTANCE;
        FlagFieldEx.EnumFlagFieldEx enumFlagFieldEx = new FlagFieldEx.EnumFlagFieldEx(0, AbiModality.values());
        MODALITY = enumFlagFieldEx;
        PROPERTY_KIND = new FlagFieldEx.EnumFlagFieldEx(FlagFieldEx.INSTANCE.getNextOffset(enumFlagFieldEx), AbiPropertyKind.values());
    }
}
