package org.jetbrains.kotlin.ir.declarations.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrValueParameterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001Bq\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016R \u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%8VX\u0097\u0004¢\u0006\f\u0012\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u001a\u0010\u000b\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010+\"\u0004\b/\u00100R\u0014\u0010\u0013\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u00101R\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u00101\"\u0004\b2\u00103R\u001a\u0010\u0012\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u00101\"\u0004\b4\u00103R\u001a\u0010\u0011\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u00101\"\u0004\b5\u00103R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010>\u001a\u00020?X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010+R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010H\"\u0004\bL\u0010J¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrValueParameterImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "varargElementType", "isCrossinline", "", "isNoinline", "isHidden", "isAssignable", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;Lorg/jetbrains/kotlin/name/Name;ILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/types/IrType;ZZZZLorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "defaultValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "getDefaultValue", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "setDefaultValue", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "getEndOffset", "()I", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIndex", "setIndex", "(I)V", "()Z", "setCrossinline", "(Z)V", "setHidden", "setNoinline", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getVarargElementType", "setVarargElementType", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrValueParameterImpl extends IrValueParameter {
    private List<? extends IrConstructorCall> annotations;
    private IrExpressionBody defaultValue;
    private final int endOffset;
    private final IrFactory factory;
    private int index;
    private final boolean isAssignable;
    private boolean isCrossinline;
    private boolean isHidden;
    private boolean isNoinline;
    private Name name;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;
    private final int startOffset;
    private final IrValueParameterSymbol symbol;
    private IrType type;
    private IrType varargElementType;

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
    public IrValueParameterSymbol getSymbol() {
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public int getIndex() {
        return this.index;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setIndex(int i) {
        this.index = i;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public IrType getVarargElementType() {
        return this.varargElementType;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setVarargElementType(IrType irType) {
        this.varargElementType = irType;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    /* JADX INFO: renamed from: isCrossinline, reason: from getter */
    public boolean getIsCrossinline() {
        return this.isCrossinline;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setCrossinline(boolean z) {
        this.isCrossinline = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    /* JADX INFO: renamed from: isNoinline, reason: from getter */
    public boolean getIsNoinline() {
        return this.isNoinline;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setNoinline(boolean z) {
        this.isNoinline = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    /* JADX INFO: renamed from: isHidden, reason: from getter */
    public boolean getIsHidden() {
        return this.isHidden;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setHidden(boolean z) {
        this.isHidden = z;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
    /* JADX INFO: renamed from: isAssignable, reason: from getter */
    public boolean getIsAssignable() {
        return this.isAssignable;
    }

    public /* synthetic */ IrValueParameterImpl(int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrValueParameterSymbol irValueParameterSymbol, Name name2, int i3, IrType irType, IrType irType2, boolean z, boolean z2, boolean z3, boolean z4, IrFactory irFactory, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irDeclarationOrigin, irValueParameterSymbol, name2, i3, irType, irType2, z, z2, z3, z4, (i4 & 4096) != 0 ? IrFactoryImpl.INSTANCE : irFactory);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    public IrValueParameterImpl(int i, int i2, IrDeclarationOrigin origin, IrValueParameterSymbol symbol, Name name2, int i3, IrType type, IrType irType, boolean z, boolean z2, boolean z3, boolean z4, IrFactory factory) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.name = name2;
        this.index = i3;
        this.type = type;
        this.varargElementType = irType;
        this.isCrossinline = z;
        this.isNoinline = z2;
        this.isHidden = z3;
        this.isAssignable = z4;
        this.factory = factory;
        getSymbol().bind(this);
        this.annotations = CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ParameterDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public IrExpressionBody getDefaultValue() {
        return this.defaultValue;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setDefaultValue(IrExpressionBody irExpressionBody) {
        this.defaultValue = irExpressionBody;
    }
}
