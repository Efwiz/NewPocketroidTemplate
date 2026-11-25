package org.jetbrains.kotlin.ir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrClassImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u009f\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u001a\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001c¢\u0006\u0002\u0010\u001dR \u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\"R\u001a\u0010/\u001a\u0002008VX\u0097\u0004¢\u0006\f\u0012\u0004\b1\u00102\u001a\u0004\b3\u00104R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u00109\"\u0004\b:\u0010;R\u001a\u0010\u0014\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u00109\"\u0004\b<\u0010;R\u001a\u0010\u0017\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u00109\"\u0004\b=\u0010;R\u001a\u0010\u0015\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u00109\"\u0004\b>\u0010;R\u001a\u0010\u0018\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u00109\"\u0004\b?\u0010;R\u001a\u0010\u0013\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u00109\"\u0004\b@\u0010;R\u001a\u0010\u0016\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u00109\"\u0004\bA\u0010;R\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001c\u0010F\u001a\u0004\u0018\u00010GX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u001c\u0010X\u001a\u0004\u0018\u00010&X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010(\"\u0004\bZ\u0010*R\u001a\u0010[\u001a\u00020\\X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R \u0010a\u001a\b\u0012\u0004\u0012\u00020\b0\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010\"\"\u0004\bc\u0010$R\u0014\u0010\u0019\u001a\u00020\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bf\u00106R \u0010g\u001a\b\u0012\u0004\u0012\u00020h0\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010\"\"\u0004\bj\u0010$R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bk\u0010lR\u001c\u0010m\u001a\u0004\u0018\u00010nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR \u0010s\u001a\b\u0012\u0004\u0012\u00020t0\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010\"\"\u0004\bv\u0010$R\"\u0010w\u001a\n\u0012\u0004\u0012\u00020y\u0018\u00010xX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010{\"\u0004\b|\u0010}R\u001c\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u0010\n\u0000\u001a\u0004\b~\u0010\u007f\"\u0006\b\u0080\u0001\u0010\u0081\u0001¨\u0006\u0082\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrClassImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isCompanion", "", "isInner", "isData", "isExternal", "isValue", "isExpect", "isFun", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/ClassKind;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;Lorg/jetbrains/kotlin/descriptors/Modality;ZZZZZZZLorg/jetbrains/kotlin/descriptors/SourceElement;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "attributeOwnerId", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getAttributeOwnerId", "()Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "setAttributeOwnerId", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)V", "declarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getDeclarations", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getEndOffset", "()I", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "()Z", "setCompanion", "(Z)V", "setData", "setExpect", "setExternal", "setFun", "setInner", "setValue", "getKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "setKind", "(Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "originalBeforeInline", "getOriginalBeforeInline", "setOriginalBeforeInline", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "sealedSubclasses", "getSealedSubclasses", "setSealedSubclasses", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStartOffset", "superTypes", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getSuperTypes", "setSuperTypes", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "thisReceiver", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getThisReceiver", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setThisReceiver", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "typeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameters", "setTypeParameters", "valueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getValueClassRepresentation", "()Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "setValueClassRepresentation", "(Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;)V", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrClassImpl extends IrClass {
    private List<? extends IrConstructorCall> annotations;
    private IrAttributeContainer attributeOwnerId;
    private final List<IrDeclaration> declarations;
    private final int endOffset;
    private final IrFactory factory;
    private boolean isCompanion;
    private boolean isData;
    private boolean isExpect;
    private boolean isExternal;
    private boolean isFun;
    private boolean isInner;
    private boolean isValue;
    private ClassKind kind;
    private MetadataSource metadata;
    private Modality modality;
    private Name name;
    private IrDeclarationOrigin origin;
    private IrAttributeContainer originalBeforeInline;
    public IrDeclarationParent parent;
    private List<? extends IrClassSymbol> sealedSubclasses;
    private final SourceElement source;
    private final int startOffset;
    private List<? extends IrType> superTypes;
    private final IrClassSymbol symbol;
    private IrValueParameter thisReceiver;
    private List<? extends IrTypeParameter> typeParameters;
    private ValueClassRepresentation<IrSimpleType> valueClassRepresentation;
    private DescriptorVisibility visibility;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public /* bridge */ /* synthetic */ IrSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setOrigin(IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irDeclarationOrigin, "<set-?>");
        this.origin = irDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass, org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public final IrClassSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public ClassKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setKind(ClassKind classKind) {
        Intrinsics.checkNotNullParameter(classKind, "<set-?>");
        this.kind = classKind;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public DescriptorVisibility getVisibility() {
        return this.visibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility
    public void setVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<set-?>");
        this.visibility = descriptorVisibility;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public Modality getModality() {
        return this.modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<set-?>");
        this.modality = modality;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isCompanion, reason: from getter */
    public boolean getIsCompanion() {
        return this.isCompanion;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setCompanion(boolean z) {
        this.isCompanion = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isInner, reason: from getter */
    public boolean getIsInner() {
        return this.isInner;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setInner(boolean z) {
        this.isInner = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isData, reason: from getter */
    public boolean getIsData() {
        return this.isData;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setData(boolean z) {
        this.isData = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public boolean getIsExternal() {
        return this.isExternal;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration
    public void setExternal(boolean z) {
        this.isExternal = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isValue, reason: from getter */
    public boolean getIsValue() {
        return this.isValue;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setValue(boolean z) {
        this.isValue = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public boolean getIsExpect() {
        return this.isExpect;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setExpect(boolean z) {
        this.isExpect = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    /* JADX INFO: renamed from: isFun, reason: from getter */
    public boolean getIsFun() {
        return this.isFun;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setFun(boolean z) {
        this.isFun = z;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ IrClassImpl(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrClassSymbol irClassSymbol, Name name2, ClassKind classKind, DescriptorVisibility descriptorVisibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, SourceElement sourceElement, IrFactory irFactory, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        SourceElement sourceElement2;
        boolean z8 = (i3 & 256) != 0 ? false : z;
        boolean z9 = (i3 & 512) != 0 ? false : z2;
        boolean z10 = (i3 & 1024) != 0 ? false : z3;
        boolean z11 = (i3 & 2048) != 0 ? false : z4;
        boolean z12 = (i3 & 4096) != 0 ? false : z5;
        boolean z13 = (i3 & 8192) != 0 ? false : z6;
        boolean z14 = (i3 & 16384) != 0 ? false : z7;
        if ((32768 & i3) != 0) {
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            sourceElement2 = NO_SOURCE;
        } else {
            sourceElement2 = sourceElement;
        }
        this(i, i2, irDeclarationOrigin, irClassSymbol, name2, classKind, descriptorVisibility, modality, z8, z9, z10, z11, z12, z13, z14, sourceElement2, (i3 & 65536) != 0 ? IrFactoryImpl.INSTANCE : irFactory);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public SourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrClassImpl(int i, int i2, IrDeclarationOrigin origin, IrClassSymbol symbol, Name name2, ClassKind kind, DescriptorVisibility visibility, Modality modality, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, SourceElement source, IrFactory factory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.name = name2;
        this.kind = kind;
        this.visibility = visibility;
        this.modality = modality;
        this.isCompanion = z;
        this.isInner = z2;
        this.isData = z3;
        this.isExternal = z4;
        this.isValue = z5;
        this.isExpect = z6;
        this.isFun = z7;
        this.source = source;
        this.factory = factory;
        symbol.bind(this);
        this.annotations = CollectionsKt.emptyList();
        this.declarations = new ArrayList();
        this.typeParameters = CollectionsKt.emptyList();
        this.superTypes = CollectionsKt.emptyList();
        this.attributeOwnerId = this;
        this.sealedSubclasses = CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        IrDeclarationParent irDeclarationParent = this.parent;
        if (irDeclarationParent != null) {
            return irDeclarationParent;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parent");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.parent = irDeclarationParent;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ClassDescriptor getDescriptor() {
        return this.symbol.getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public IrValueParameter getThisReceiver() {
        return this.thisReceiver;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setThisReceiver(IrValueParameter irValueParameter) {
        this.thisReceiver = irValueParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer
    public List<IrDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public List<IrTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer
    public void setTypeParameters(List<? extends IrTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public List<IrType> getSuperTypes() {
        return this.superTypes;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setSuperTypes(List<? extends IrType> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.superTypes = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public ValueClassRepresentation<IrSimpleType> getValueClassRepresentation() {
        return this.valueClassRepresentation;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setValueClassRepresentation(ValueClassRepresentation<IrSimpleType> valueClassRepresentation) {
        this.valueClassRepresentation = valueClassRepresentation;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return this.metadata;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        this.metadata = metadataSource;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getAttributeOwnerId() {
        return this.attributeOwnerId;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setAttributeOwnerId(IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(irAttributeContainer, "<set-?>");
        this.attributeOwnerId = irAttributeContainer;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public IrAttributeContainer getOriginalBeforeInline() {
        return this.originalBeforeInline;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAttributeContainer
    public void setOriginalBeforeInline(IrAttributeContainer irAttributeContainer) {
        this.originalBeforeInline = irAttributeContainer;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public List<IrClassSymbol> getSealedSubclasses() {
        return this.sealedSubclasses;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrClass
    public void setSealedSubclasses(List<? extends IrClassSymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.sealedSubclasses = list;
    }
}
