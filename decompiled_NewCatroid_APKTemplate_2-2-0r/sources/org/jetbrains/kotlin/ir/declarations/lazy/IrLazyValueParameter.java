package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLazyValueParameter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u007f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u0004\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0013\u0012\u0006\u0010\u0016\u001a\u00020\u0013\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u0012\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bR7\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b$\u0010%\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u001a\u0010\u000e\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010/\"\u0004\b1\u00102R\u0014\u0010\u0016\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u00103R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u00103\"\u0004\b4\u00105R\u001a\u0010\u0015\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u00103\"\u0004\b6\u00105R\u001a\u0010\u0014\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u00103\"\u0004\b7\u00105R\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010/R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bI\u0010JR+\u0010L\u001a\u00020K2\u0006\u0010\u001c\u001a\u00020K8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bQ\u0010%\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u0014\u0010\u0019\u001a\u00020\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR/\u0010T\u001a\u0004\u0018\u00010K2\b\u0010\u001c\u001a\u0004\u0018\u00010K8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bW\u0010%\u001a\u0004\bU\u0010N\"\u0004\bV\u0010P¨\u0006X"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "varargElementKotlinType", "isCrossinline", "", "isNoinline", "isHidden", "isAssignable", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;Lorg/jetbrains/kotlin/name/Name;ILorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;ZZZZLorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "defaultValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "getDefaultValue", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "setDefaultValue", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getEndOffset", "()I", "getIndex", "setIndex", "(I)V", "()Z", "setCrossinline", "(Z)V", "setHidden", "setNoinline", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getStartOffset", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "type$delegate", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "varargElementType", "getVarargElementType", "setVarargElementType", "varargElementType$delegate", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLazyValueParameter extends IrValueParameter implements IrLazyDeclarationBase {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyValueParameter.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyValueParameter.class), ModuleXmlParser.TYPE, "getType()Lorg/jetbrains/kotlin/ir/types/IrType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyValueParameter.class), "varargElementType", "getVarargElementType()Lorg/jetbrains/kotlin/ir/types/IrType;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;
    private IrExpressionBody defaultValue;
    private final ValueParameterDescriptor descriptor;
    private final int endOffset;
    private int index;
    private final boolean isAssignable;
    private boolean isCrossinline;
    private boolean isHidden;
    private boolean isNoinline;
    private Name name;
    private IrDeclarationOrigin origin;
    public IrDeclarationParent parent;
    private final int startOffset;
    private final DeclarationStubGenerator stubGenerator;
    private final IrValueParameterSymbol symbol;

    /* JADX INFO: renamed from: type$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty type;
    private final TypeTranslator typeTranslator;

    /* JADX INFO: renamed from: varargElementType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty varargElementType;

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations() {
        return IrLazyDeclarationBase.DefaultImpls.createLazyAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public ReadWriteProperty<Object, IrDeclarationParent> createLazyParent() {
        return IrLazyDeclarationBase.DefaultImpls.createLazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return IrLazyDeclarationBase.DefaultImpls.generateReceiverParameterStub(this, receiverParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return IrLazyDeclarationBase.DefaultImpls.getFactory(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrDeclarationParent lazyParent() {
        return IrLazyDeclarationBase.DefaultImpls.lazyParent(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrType toIrType(KotlinType kotlinType) {
        return IrLazyDeclarationBase.DefaultImpls.toIrType(this, kotlinType);
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ValueParameterDescriptor getDescriptor() {
        return this.descriptor;
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

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    public IrLazyValueParameter(int i, int i2, IrDeclarationOrigin origin, IrValueParameterSymbol symbol, ValueParameterDescriptor descriptor, Name name2, int i3, final KotlinType kotlinType, final KotlinType kotlinType2, boolean z, boolean z2, boolean z3, boolean z4, DeclarationStubGenerator stubGenerator, TypeTranslator typeTranslator) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.descriptor = descriptor;
        this.name = name2;
        this.index = i3;
        this.isCrossinline = z;
        this.isNoinline = z2;
        this.isHidden = z3;
        this.isAssignable = z4;
        this.stubGenerator = stubGenerator;
        this.typeTranslator = typeTranslator;
        this.annotations = createLazyAnnotations();
        this.type = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyValueParameter$type$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return this.this$0.toIrType(kotlinType);
            }
        });
        this.varargElementType = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyValueParameter$varargElementType$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                KotlinType kotlinType3 = kotlinType2;
                if (kotlinType3 != null) {
                    return this.toIrType(kotlinType3);
                }
                return null;
            }
        });
        getSymbol().bind(this);
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

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public IrExpressionBody getDefaultValue() {
        return this.defaultValue;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setDefaultValue(IrExpressionBody irExpressionBody) {
        this.defaultValue = irExpressionBody;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return (List) this.annotations.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations.setValue(this, $$delegatedProperties[0], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
    public IrType getType() {
        return (IrType) this.type.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type.setValue(this, $$delegatedProperties[1], irType);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public IrType getVarargElementType() {
        return (IrType) this.varargElementType.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrValueParameter
    public void setVarargElementType(IrType irType) {
        this.varargElementType.setValue(this, $$delegatedProperties[2], irType);
    }
}
