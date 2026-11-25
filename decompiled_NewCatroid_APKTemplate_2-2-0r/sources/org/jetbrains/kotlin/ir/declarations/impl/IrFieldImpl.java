package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFieldImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020$8VX\u0097\u0004¢\u0006\f\u0012\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001c\u0010-\u001a\u0004\u0018\u00010.X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001a\u0010\u0011\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u00103\"\u0004\b4\u00105R\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u00103\"\u0004\b6\u00105R\u001a\u0010\u0012\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u00103\"\u0004\b7\u00105R\u001c\u00108\u001a\u0004\u0018\u000109X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020GX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010*R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010T\"\u0004\bU\u0010V¨\u0006W"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrFieldImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isFinal", "", "isExternal", "isStatic", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;ZZZLorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "correspondingPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCorrespondingPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setCorrespondingPropertySymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getEndOffset", "()I", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "initializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "getInitializer", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "setInitializer", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)V", "()Z", "setExternal", "(Z)V", "setFinal", "setStatic", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFieldImpl extends IrField {
    private List<? extends IrConstructorCall> annotations;
    private IrPropertySymbol correspondingPropertySymbol;
    private final int endOffset;
    private final IrFactory factory;
    private IrExpressionBody initializer;
    private boolean isExternal;
    private boolean isFinal;
    private boolean isStatic;
    private MetadataSource metadata;
    private Name name;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;
    private final int startOffset;
    private final IrFieldSymbol symbol;
    private IrType type;
    private DescriptorVisibility visibility;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrFieldSymbol getSymbol() {
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    /* JADX INFO: renamed from: isFinal, reason: from getter */
    public boolean getIsFinal() {
        return this.isFinal;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setFinal(boolean z) {
        this.isFinal = z;
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    /* JADX INFO: renamed from: isStatic, reason: from getter */
    public boolean getIsStatic() {
        return this.isStatic;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setStatic(boolean z) {
        this.isStatic = z;
    }

    public /* synthetic */ IrFieldImpl(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrFieldSymbol irFieldSymbol, Name name2, IrType irType, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, boolean z3, IrFactory irFactory, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irDeclarationOrigin, irFieldSymbol, name2, irType, descriptorVisibility, z, z2, z3, (i3 & 1024) != 0 ? IrFactoryImpl.INSTANCE : irFactory);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrFieldImpl(int i, int i2, IrDeclarationOrigin origin, IrFieldSymbol symbol, Name name2, IrType type, DescriptorVisibility visibility, boolean z, boolean z2, boolean z3, IrFactory factory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.name = name2;
        this.type = type;
        this.visibility = visibility;
        this.isFinal = z;
        this.isExternal = z2;
        this.isStatic = z3;
        this.factory = factory;
        getSymbol().bind(this);
        this.annotations = CollectionsKt.emptyList();
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
    public PropertyDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public IrExpressionBody getInitializer() {
        return this.initializer;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setInitializer(IrExpressionBody irExpressionBody) {
        this.initializer = irExpressionBody;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public IrPropertySymbol getCorrespondingPropertySymbol() {
        return this.correspondingPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setCorrespondingPropertySymbol(IrPropertySymbol irPropertySymbol) {
        this.correspondingPropertySymbol = irPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return this.metadata;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        this.metadata = metadataSource;
    }
}
