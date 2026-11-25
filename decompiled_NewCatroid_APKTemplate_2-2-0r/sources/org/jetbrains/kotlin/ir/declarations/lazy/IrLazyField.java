package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLazyField.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002Be\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0011\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018R7\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R/\u0010$\u001a\u0004\u0018\u00010#2\b\u0010\u0019\u001a\u0004\u0018\u00010#8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b)\u0010\"\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R/\u0010/\u001a\u0004\u0018\u00010.2\b\u0010\u0019\u001a\u0004\u0018\u00010.8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b4\u0010\"\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u0010\u0012\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u00105\"\u0004\b6\u00107R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u00105\"\u0004\b8\u00107R\u001a\u0010\u0013\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u00105\"\u0004\b9\u00107R(\u0010<\u001a\u0004\u0018\u00010;2\b\u0010:\u001a\u0004\u0018\u00010;8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR+\u0010J\u001a\u00020I2\u0006\u0010\u0019\u001a\u00020I8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bO\u0010\"\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bP\u0010-R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bQ\u0010RR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u0010TR+\u0010V\u001a\u00020U2\u0006\u0010\u0019\u001a\u00020U8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b[\u0010\"\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010]R\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010_\"\u0004\b`\u0010a¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyField;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isFinal", "", "isExternal", "isStatic", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "(IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;ZZZLorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "correspondingPropertySymbol", "getCorrespondingPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setCorrespondingPropertySymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "correspondingPropertySymbol$delegate", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getEndOffset", "()I", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "initializer", "getInitializer", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "setInitializer", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;)V", "initializer$delegate", "()Z", "setExternal", "(Z)V", "setFinal", "setStatic", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "metadata", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "parent", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "parent$delegate", "getStartOffset", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "type$delegate", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLazyField extends IrField implements IrLazyDeclarationBase {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyField.class), "parent", "getParent()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyField.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyField.class), ModuleXmlParser.TYPE, "getType()Lorg/jetbrains/kotlin/ir/types/IrType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyField.class), "initializer", "getInitializer()Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(IrLazyField.class), "correspondingPropertySymbol", "getCorrespondingPropertySymbol()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;

    /* JADX INFO: renamed from: correspondingPropertySymbol$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty correspondingPropertySymbol;
    private final PropertyDescriptor descriptor;
    private final int endOffset;

    /* JADX INFO: renamed from: initializer$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty initializer;
    private boolean isExternal;
    private boolean isFinal;
    private boolean isStatic;
    private Name name;
    private IrDeclarationOrigin origin;

    /* JADX INFO: renamed from: parent$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty parent;
    private final int startOffset;
    private final DeclarationStubGenerator stubGenerator;
    private final IrFieldSymbol symbol;

    /* JADX INFO: renamed from: type$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty type;
    private final TypeTranslator typeTranslator;
    private DescriptorVisibility visibility;

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return null;
    }

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
    public IrFieldSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public PropertyDescriptor getDescriptor() {
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

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public TypeTranslator getTypeTranslator() {
        return this.typeTranslator;
    }

    public IrLazyField(int i, int i2, IrDeclarationOrigin origin, IrFieldSymbol symbol, PropertyDescriptor descriptor, Name name2, DescriptorVisibility visibility, boolean z, boolean z2, boolean z3, DeclarationStubGenerator stubGenerator, TypeTranslator typeTranslator) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = origin;
        this.symbol = symbol;
        this.descriptor = descriptor;
        this.name = name2;
        this.visibility = visibility;
        this.isFinal = z;
        this.isExternal = z2;
        this.isStatic = z3;
        this.stubGenerator = stubGenerator;
        this.typeTranslator = typeTranslator;
        getSymbol().bind(this);
        this.parent = createLazyParent();
        this.annotations = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<List<? extends IrConstructorCall>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyField$annotations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrConstructorCall> invoke() {
                Annotations annotations;
                FieldDescriptor backingField = this.this$0.getDescriptor().getBackingField();
                if (backingField != null && (annotations = backingField.getAnnotations()) != null) {
                    ArrayList arrayList = new ArrayList();
                    ConstantValueGenerator constantValueGenerator = this.this$0.getTypeTranslator().getConstantValueGenerator();
                    Iterator<AnnotationDescriptor> it = annotations.iterator();
                    while (it.hasNext()) {
                        IrConstructorCall irConstructorCallGenerateAnnotationConstructorCall$default = ConstantValueGenerator.generateAnnotationConstructorCall$default(constantValueGenerator, it.next(), null, 2, null);
                        if (irConstructorCallGenerateAnnotationConstructorCall$default != null) {
                            arrayList.add(irConstructorCallGenerateAnnotationConstructorCall$default);
                        }
                    }
                    return arrayList;
                }
                return new ArrayList();
            }
        });
        this.type = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyField$type$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                IrLazyField irLazyField = this.this$0;
                KotlinType type = irLazyField.getDescriptor().getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                return irLazyField.toIrType(type);
            }
        });
        this.initializer = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrExpressionBody>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyField$initializer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrExpressionBody invoke() {
                ConstantValue<?> compileTimeInitializer = this.this$0.getDescriptor().mo6754getCompileTimeInitializer();
                if (compileTimeInitializer == null) {
                    return null;
                }
                IrLazyField irLazyField = this.this$0;
                return IrFactoryHelpersKt.createExpressionBody(irLazyField.getFactory(), irLazyField.getTypeTranslator().getConstantValueGenerator().generateConstantValueAsExpression(-1, -1, compileTimeInitializer));
            }
        });
        this.correspondingPropertySymbol = LazyUtilKt.lazyVar(getStubGenerator().getLock(), new Function0<IrPropertySymbol>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyField$correspondingPropertySymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrPropertySymbol invoke() {
                return this.this$0.getStubGenerator().generatePropertyStub(this.this$0.getDescriptor()).getSymbol();
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        return (IrDeclarationParent) this.parent.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
        this.parent.setValue(this, $$delegatedProperties[0], irDeclarationParent);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return (List) this.annotations.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations.setValue(this, $$delegatedProperties[1], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public IrType getType() {
        return (IrType) this.type.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type.setValue(this, $$delegatedProperties[2], irType);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public IrExpressionBody getInitializer() {
        return (IrExpressionBody) this.initializer.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setInitializer(IrExpressionBody irExpressionBody) {
        this.initializer.setValue(this, $$delegatedProperties[3], irExpressionBody);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public IrPropertySymbol getCorrespondingPropertySymbol() {
        return (IrPropertySymbol) this.correspondingPropertySymbol.getValue(this, $$delegatedProperties[4]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrField
    public void setCorrespondingPropertySymbol(IrPropertySymbol irPropertySymbol) {
        this.correspondingPropertySymbol.setValue(this, $$delegatedProperties[4], irPropertySymbol);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        throw new IllegalStateException("We should never need to store metadata of external declarations.".toString());
    }
}
