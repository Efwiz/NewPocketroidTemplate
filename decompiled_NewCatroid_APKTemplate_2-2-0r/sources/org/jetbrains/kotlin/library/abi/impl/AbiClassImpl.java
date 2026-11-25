package org.jetbrains.kotlin.library.abi.impl;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.abi.AbiClass;
import org.jetbrains.kotlin.library.abi.AbiClassKind;
import org.jetbrains.kotlin.library.abi.AbiDeclaration;
import org.jetbrains.kotlin.library.abi.AbiModality;
import org.jetbrains.kotlin.library.abi.AbiQualifiedName;
import org.jetbrains.kotlin.library.abi.AbiSignatures;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiTypeParameter;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000f\b\u0001\u0018\u0000 )2\u00020\u0001:\u0001)Bu\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0011¢\u0006\u0002\u0010\u0017J\u0010\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u001cR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u001cR\u0014\u0010\u000e\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u001cR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0019¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiClassImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiClass;", "qualifiedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "signatures", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "annotations", "", "modality", "Lorg/jetbrains/kotlin/library/abi/AbiModality;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/library/abi/AbiClassKind;", "isInner", "", "isValue", "isFunction", "superTypes", "", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "declarations", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "typeParameters", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParameter;", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/AbiSignatures;Ljava/util/Set;Lorg/jetbrains/kotlin/library/abi/AbiModality;Lorg/jetbrains/kotlin/library/abi/AbiClassKind;ZZZLjava/util/List;Ljava/util/List;Ljava/util/List;)V", "getDeclarations", "()Ljava/util/List;", OverlayablePolicy.NAME_flags, "", "()Z", "getKind", "()Lorg/jetbrains/kotlin/library/abi/AbiClassKind;", "getModality", "()Lorg/jetbrains/kotlin/library/abi/AbiModality;", "getQualifiedName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "getSignatures", "()Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "getSuperTypes", "getTypeParameters", "hasAnnotation", "annotationClassName", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiClassImpl implements AbiClass {
    private static final FlagFieldEx<AbiClassKind> CLASS_KIND;
    private static final Flags.BooleanFlagField IS_FUNCTION;
    private static final Flags.BooleanFlagField IS_INNER;
    private static final Flags.BooleanFlagField IS_VALUE;
    private static final FlagFieldEx<AbiModality> MODALITY;
    private final Set<AbiQualifiedName> annotations;
    private final List<AbiDeclaration> declarations;
    private final int flags;
    private final AbiQualifiedName qualifiedName;
    private final AbiSignatures signatures;
    private final List<AbiType> superTypes;
    private final List<AbiTypeParameter> typeParameters;

    /* JADX WARN: Multi-variable type inference failed */
    public AbiClassImpl(AbiQualifiedName qualifiedName, AbiSignatures signatures, Set<AbiQualifiedName> annotations, AbiModality modality, AbiClassKind kind, boolean z, boolean z2, boolean z3, List<? extends AbiType> superTypes, List<? extends AbiDeclaration> declarations, List<? extends AbiTypeParameter> typeParameters) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(superTypes, "superTypes");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        this.qualifiedName = qualifiedName;
        this.signatures = signatures;
        this.annotations = annotations;
        this.superTypes = superTypes;
        this.declarations = declarations;
        this.typeParameters = typeParameters;
        this.flags = IS_INNER.toFlags(Boolean.valueOf(z)) | IS_VALUE.toFlags(Boolean.valueOf(z2)) | IS_FUNCTION.toFlags(Boolean.valueOf(z3)) | MODALITY.toFlags(modality) | CLASS_KIND.toFlags(kind);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiQualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiSignatures getSignatures() {
        return this.signatures;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiClass
    public List<AbiType> getSuperTypes() {
        return this.superTypes;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclarationContainer
    public List<AbiDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeParametersContainer
    public List<AbiTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclarationWithModality
    public AbiModality getModality() {
        return MODALITY.get(this.flags);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiClass
    public AbiClassKind getKind() {
        return CLASS_KIND.get(this.flags);
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiClass
    public boolean isInner() {
        Boolean bool = IS_INNER.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiClass
    public boolean isValue() {
        Boolean bool = IS_VALUE.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiClass
    public boolean isFunction() {
        Boolean bool = IS_FUNCTION.get(this.flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public boolean hasAnnotation(AbiQualifiedName annotationClassName) {
        Intrinsics.checkNotNullParameter(annotationClassName, "annotationClassName");
        return this.annotations.contains(annotationClassName);
    }

    static {
        Flags.BooleanFlagField booleanFlagFieldBooleanFirst = Flags.FlagField.booleanFirst();
        IS_INNER = booleanFlagFieldBooleanFirst;
        Flags.BooleanFlagField booleanFlagFieldBooleanAfter = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanFirst);
        IS_VALUE = booleanFlagFieldBooleanAfter;
        Flags.BooleanFlagField IS_FUNCTION2 = Flags.FlagField.booleanAfter(booleanFlagFieldBooleanAfter);
        IS_FUNCTION = IS_FUNCTION2;
        FlagFieldEx.Companion companion = FlagFieldEx.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(IS_FUNCTION2, "IS_FUNCTION");
        FlagFieldEx.EnumFlagFieldEx enumFlagFieldEx = new FlagFieldEx.EnumFlagFieldEx(companion.getNextOffset(IS_FUNCTION2), AbiModality.values());
        MODALITY = enumFlagFieldEx;
        CLASS_KIND = new FlagFieldEx.EnumFlagFieldEx(FlagFieldEx.INSTANCE.getNextOffset(enumFlagFieldEx), AbiClassKind.values());
    }
}
