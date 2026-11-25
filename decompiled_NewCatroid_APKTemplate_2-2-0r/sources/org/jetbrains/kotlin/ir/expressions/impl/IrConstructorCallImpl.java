package org.jetbrains.kotlin.ir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.EmptyArraysForMemberAccessKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrConstructorCallImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 42\u00020\u0001:\u00014BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010R\u001a\u0010\n\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0012R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001e\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060+X\u0094\u0004¢\u0006\n\n\u0002\u0010.\u001a\u0004\b,\u0010-R\u001e\u0010/\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001000+X\u0094\u0004¢\u0006\n\n\u0002\u00103\u001a\u0004\b1\u00102¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstructorCallImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "typeArgumentsCount", "constructorTypeArgumentsCount", "valueArgumentsCount", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;IIILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "getConstructorTypeArgumentsCount", "()I", "setConstructorTypeArgumentsCount", "(I)V", "contextReceiversCount", "getContextReceiversCount", "setContextReceiversCount", "getEndOffset", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", "setSource", "(Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "typeArguments", "", "getTypeArguments", "()[Lorg/jetbrains/kotlin/ir/types/IrType;", "[Lorg/jetbrains/kotlin/ir/types/IrType;", "valueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getValueArguments", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstructorCallImpl extends IrConstructorCall {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int constructorTypeArgumentsCount;
    private int contextReceiversCount;
    private final int endOffset;
    private IrStatementOrigin origin;
    private SourceElement source;
    private final int startOffset;
    private IrConstructorSymbol symbol;
    private IrType type;
    private final IrType[] typeArguments;
    private final IrExpression[] valueArguments;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression, org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public IrConstructorSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstructorCall
    public void setSymbol(IrConstructorSymbol irConstructorSymbol) {
        Intrinsics.checkNotNullParameter(irConstructorSymbol, "<set-?>");
        this.symbol = irConstructorSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstructorCall
    public int getConstructorTypeArgumentsCount() {
        return this.constructorTypeArgumentsCount;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstructorCall
    public void setConstructorTypeArgumentsCount(int i) {
        this.constructorTypeArgumentsCount = i;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ IrConstructorCallImpl(int i, int i2, IrType irType, IrConstructorSymbol irConstructorSymbol, int i3, int i4, int i5, IrStatementOrigin irStatementOrigin, SourceElement sourceElement, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        SourceElement sourceElement2;
        IrStatementOrigin irStatementOrigin2 = (i6 & 128) != 0 ? null : irStatementOrigin;
        if ((i6 & 256) != 0) {
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            sourceElement2 = NO_SOURCE;
        } else {
            sourceElement2 = sourceElement;
        }
        this(i, i2, irType, irConstructorSymbol, i3, i4, i5, irStatementOrigin2, sourceElement2);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstructorCall
    public SourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstructorCall
    public void setSource(SourceElement sourceElement) {
        Intrinsics.checkNotNullParameter(sourceElement, "<set-?>");
        this.source = sourceElement;
    }

    public IrConstructorCallImpl(int i, int i2, IrType type, IrConstructorSymbol symbol, int i3, int i4, int i5, IrStatementOrigin irStatementOrigin, SourceElement source) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(source, "source");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.constructorTypeArgumentsCount = i4;
        this.origin = irStatementOrigin;
        this.source = source;
        this.typeArguments = EmptyArraysForMemberAccessKt.initializeTypeArguments(i3);
        this.valueArguments = EmptyArraysForMemberAccessKt.initializeParameterArguments(i5);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrType[] getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrExpression[] getValueArguments() {
        return this.valueArguments;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression
    public int getContextReceiversCount() {
        return this.contextReceiversCount;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression
    public void setContextReceiversCount(int i) {
        this.contextReceiversCount = i;
    }

    /* JADX INFO: compiled from: IrConstructorCallImpl.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J:\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rJ2\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rJ\"\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstructorCallImpl$Companion;", "", "()V", "fromSymbolDescriptor", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstructorCallImpl;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "constructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "fromSymbolOwner", "classTypeParametersCount", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ IrConstructorCallImpl fromSymbolDescriptor$default(Companion companion, int i, int i2, IrType irType, IrConstructorSymbol irConstructorSymbol, IrStatementOrigin irStatementOrigin, int i3, Object obj) {
            if ((i3 & 16) != 0) {
                irStatementOrigin = null;
            }
            return companion.fromSymbolDescriptor(i, i2, irType, irConstructorSymbol, irStatementOrigin);
        }

        @ObsoleteDescriptorBasedAPI
        public final IrConstructorCallImpl fromSymbolDescriptor(int startOffset, int endOffset, IrType type, IrConstructorSymbol constructorSymbol, IrStatementOrigin origin) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
            ClassConstructorDescriptor descriptor = constructorSymbol.getDescriptor();
            int size = descriptor.getConstructedClass().getOriginal().getDeclaredTypeParameters().size();
            int size2 = descriptor.getTypeParameters().size();
            return new IrConstructorCallImpl(startOffset, endOffset, type, constructorSymbol, size2, size2 - size, descriptor.getValueParameters().size() + descriptor.getContextReceiverParameters().size(), origin, null, 256, null);
        }

        public static /* synthetic */ IrConstructorCallImpl fromSymbolOwner$default(Companion companion, int i, int i2, IrType irType, IrConstructorSymbol irConstructorSymbol, int i3, IrStatementOrigin irStatementOrigin, int i4, Object obj) {
            if ((i4 & 32) != 0) {
                irStatementOrigin = null;
            }
            return companion.fromSymbolOwner(i, i2, irType, irConstructorSymbol, i3, irStatementOrigin);
        }

        public final IrConstructorCallImpl fromSymbolOwner(int startOffset, int endOffset, IrType type, IrConstructorSymbol constructorSymbol, int classTypeParametersCount, IrStatementOrigin origin) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
            IrConstructor owner = constructorSymbol.getOwner();
            int size = owner.getTypeParameters().size();
            return new IrConstructorCallImpl(startOffset, endOffset, type, constructorSymbol, classTypeParametersCount + size, size, owner.getValueParameters().size(), origin, null, 256, null);
        }

        public static /* synthetic */ IrConstructorCallImpl fromSymbolOwner$default(Companion companion, int i, int i2, IrType irType, IrConstructorSymbol irConstructorSymbol, IrStatementOrigin irStatementOrigin, int i3, Object obj) {
            if ((i3 & 16) != 0) {
                irStatementOrigin = null;
            }
            return companion.fromSymbolOwner(i, i2, irType, irConstructorSymbol, irStatementOrigin);
        }

        public final IrConstructorCallImpl fromSymbolOwner(int startOffset, int endOffset, IrType type, IrConstructorSymbol constructorSymbol, IrStatementOrigin origin) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
            return fromSymbolOwner(startOffset, endOffset, type, constructorSymbol, IrUtilsKt.getParentAsClass(constructorSymbol.getOwner()).getTypeParameters().size(), origin);
        }

        public static /* synthetic */ IrConstructorCallImpl fromSymbolOwner$default(Companion companion, IrType irType, IrConstructorSymbol irConstructorSymbol, IrStatementOrigin irStatementOrigin, int i, Object obj) {
            if ((i & 4) != 0) {
                irStatementOrigin = null;
            }
            return companion.fromSymbolOwner(irType, irConstructorSymbol, irStatementOrigin);
        }

        public final IrConstructorCallImpl fromSymbolOwner(IrType type, IrConstructorSymbol constructorSymbol, IrStatementOrigin origin) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
            return fromSymbolOwner(-1, -1, type, constructorSymbol, IrUtilsKt.getParentAsClass(constructorSymbol.getOwner()).getTypeParameters().size(), origin);
        }
    }
}
