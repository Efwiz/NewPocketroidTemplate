package org.jetbrains.kotlin.backend.wasm.lower;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.android.dx.rop.code.RegisterSpec;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: WasmSharedVariablesManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010&\u001a\u00020\u000eH\u0002J\b\u0010'\u001a\u00020\u0014H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0014H\u0002J\b\u0010+\u001a\u00020\u0019H\u0002J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-H\u0016J\u0018\u0010/\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\u0006\u00100\u001a\u00020-H\u0016J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0016J\u0018\u00107\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00108\u001a\u000209H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0012\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0012\u001a\u0004\b\u001e\u0010\u001bR\u000e\u0010 \u001a\u00020!X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmSharedVariablesManager;", "Lorg/jetbrains/kotlin/backend/common/ir/SharedVariablesManager;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "implicitDeclarationsFile", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;)V", "boxTypeName", "", "getBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "closureBoxClassDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getClosureBoxClassDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "closureBoxClassDeclaration$delegate", "Lkotlin/Lazy;", "closureBoxConstructorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getClosureBoxConstructorDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "closureBoxConstructorDeclaration$delegate", "closureBoxFieldDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getClosureBoxFieldDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "closureBoxFieldDeclaration$delegate", "closureBoxPropertyDeclaration", "getClosureBoxPropertyDeclaration", "closureBoxPropertyDeclaration$delegate", "closureBoxType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "getImplicitDeclarationsFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "createClosureBoxClassDeclaration", "createClosureBoxConstructorDeclaration", "createClosureBoxConstructorParameterDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "irConstructor", "createClosureBoxPropertyDeclaration", "declareSharedVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "originalDeclaration", "defineSharedValue", "sharedVariableDeclaration", "getSharedValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "sharedVariableSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "originalGet", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "setSharedValue", "originalSet", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmSharedVariablesManager implements SharedVariablesManager {
    private final String boxTypeName;
    private final IrBuiltIns builtIns;

    /* JADX INFO: renamed from: closureBoxClassDeclaration$delegate, reason: from kotlin metadata */
    private final Lazy closureBoxClassDeclaration;

    /* JADX INFO: renamed from: closureBoxConstructorDeclaration$delegate, reason: from kotlin metadata */
    private final Lazy closureBoxConstructorDeclaration;

    /* JADX INFO: renamed from: closureBoxFieldDeclaration$delegate, reason: from kotlin metadata */
    private final Lazy closureBoxFieldDeclaration;

    /* JADX INFO: renamed from: closureBoxPropertyDeclaration$delegate, reason: from kotlin metadata */
    private final Lazy closureBoxPropertyDeclaration;
    private IrType closureBoxType;
    private final JsCommonBackendContext context;
    private final IrPackageFragment implicitDeclarationsFile;

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrVariable defineSharedValue(IrVariable originalDeclaration, IrVariable sharedVariableDeclaration) {
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        Intrinsics.checkNotNullParameter(sharedVariableDeclaration, "sharedVariableDeclaration");
        return sharedVariableDeclaration;
    }

    public WasmSharedVariablesManager(JsCommonBackendContext context, IrBuiltIns builtIns, IrPackageFragment implicitDeclarationsFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(implicitDeclarationsFile, "implicitDeclarationsFile");
        this.context = context;
        this.builtIns = builtIns;
        this.implicitDeclarationsFile = implicitDeclarationsFile;
        this.boxTypeName = "$closureBox$";
        this.closureBoxClassDeclaration = LazyKt.lazy(new Function0<IrClass>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmSharedVariablesManager$closureBoxClassDeclaration$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClass invoke() {
                return this.this$0.createClosureBoxClassDeclaration();
            }
        });
        this.closureBoxConstructorDeclaration = LazyKt.lazy(new Function0<IrConstructor>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmSharedVariablesManager$closureBoxConstructorDeclaration$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructor invoke() {
                return this.this$0.createClosureBoxConstructorDeclaration();
            }
        });
        this.closureBoxFieldDeclaration = LazyKt.lazy(new Function0<IrField>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmSharedVariablesManager$closureBoxFieldDeclaration$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                return this.this$0.getClosureBoxPropertyDeclaration();
            }
        });
        this.closureBoxPropertyDeclaration = LazyKt.lazy(new Function0<IrField>() { // from class: org.jetbrains.kotlin.backend.wasm.lower.WasmSharedVariablesManager$closureBoxPropertyDeclaration$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                return this.this$0.createClosureBoxPropertyDeclaration();
            }
        });
    }

    public final IrBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    public final IrPackageFragment getImplicitDeclarationsFile() {
        return this.implicitDeclarationsFile;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrVariable declareSharedVariable(IrVariable originalDeclaration) {
        IrType irType;
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        IrConstImpl initializer = originalDeclaration.getInitializer();
        if (initializer == null) {
            initializer = IrConstImpl.INSTANCE.constNull(originalDeclaration.getStartOffset(), originalDeclaration.getEndOffset(), this.builtIns.getNothingNType());
        }
        IrConstructorSymbol symbol = getClosureBoxConstructorDeclaration().getSymbol();
        int startOffset = initializer.getStartOffset();
        int endOffset = initializer.getEndOffset();
        IrType irType2 = this.closureBoxType;
        if (irType2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closureBoxType");
            irType = null;
        } else {
            irType = irType2;
        }
        IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(startOffset, endOffset, irType, symbol, IrUtilsKt.getParentAsClass(getClosureBoxConstructorDeclaration()).getTypeParameters().size(), getClosureBoxConstructorDeclaration().getTypeParameters().size(), getClosureBoxConstructorDeclaration().getValueParameters().size(), null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
        irConstructorCallImpl.putValueArgument(0, initializer);
        IrVariableImpl irVariableImpl = new IrVariableImpl(originalDeclaration.getStartOffset(), originalDeclaration.getEndOffset(), originalDeclaration.getOrigin(), new IrVariableSymbolImpl(null, 1, null), originalDeclaration.getName(), irConstructorCallImpl.getType(), false, false, false);
        irVariableImpl.setParent(originalDeclaration.getParent());
        irVariableImpl.setInitializer(irConstructorCallImpl);
        return irVariableImpl;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrExpression getSharedValue(IrValueSymbol sharedVariableSymbol, IrGetValue originalGet) {
        Intrinsics.checkNotNullParameter(sharedVariableSymbol, "sharedVariableSymbol");
        Intrinsics.checkNotNullParameter(originalGet, "originalGet");
        int startOffset = originalGet.getStartOffset();
        int endOffset = originalGet.getEndOffset();
        IrFieldSymbol symbol = getClosureBoxFieldDeclaration().getSymbol();
        IrType type = getClosureBoxFieldDeclaration().getType();
        int startOffset2 = originalGet.getStartOffset();
        int endOffset2 = originalGet.getEndOffset();
        IrType irType = this.closureBoxType;
        if (irType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closureBoxType");
            irType = null;
        }
        return new IrTypeOperatorCallImpl(originalGet.getStartOffset(), originalGet.getEndOffset(), originalGet.getType(), IrTypeOperator.IMPLICIT_CAST, originalGet.getType(), new IrGetFieldImpl(startOffset, endOffset, symbol, type, new IrGetValueImpl(startOffset2, endOffset2, irType, sharedVariableSymbol, originalGet.getOrigin()), originalGet.getOrigin(), null, 64, null));
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrExpression setSharedValue(IrValueSymbol sharedVariableSymbol, IrSetValue originalSet) {
        Intrinsics.checkNotNullParameter(sharedVariableSymbol, "sharedVariableSymbol");
        Intrinsics.checkNotNullParameter(originalSet, "originalSet");
        int startOffset = originalSet.getStartOffset();
        int endOffset = originalSet.getEndOffset();
        IrFieldSymbol symbol = getClosureBoxFieldDeclaration().getSymbol();
        int startOffset2 = originalSet.getStartOffset();
        int endOffset2 = originalSet.getEndOffset();
        IrType irType = this.closureBoxType;
        if (irType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closureBoxType");
            irType = null;
        }
        return new IrSetFieldImpl(startOffset, endOffset, symbol, new IrGetValueImpl(startOffset2, endOffset2, irType, sharedVariableSymbol, originalSet.getOrigin()), originalSet.getValue(), originalSet.getType(), originalSet.getOrigin(), null, 128, null);
    }

    private final IrClass getClosureBoxClassDeclaration() {
        return (IrClass) this.closureBoxClassDeclaration.getValue();
    }

    private final IrConstructor getClosureBoxConstructorDeclaration() {
        return (IrConstructor) this.closureBoxConstructorDeclaration.getValue();
    }

    private final IrField getClosureBoxFieldDeclaration() {
        return (IrField) this.closureBoxFieldDeclaration.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrField getClosureBoxPropertyDeclaration() {
        return (IrField) this.closureBoxPropertyDeclaration.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass createClosureBoxClassDeclaration() {
        IrFactory irFactory = this.context.getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        irClassBuilder.setOrigin(JsLoweredDeclarationOrigin.JS_CLOSURE_BOX_CLASS_DECLARATION.INSTANCE);
        Name nameIdentifier = Name.identifier(this.boxTypeName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irClassBuilder.setName(nameIdentifier);
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irClassBuilder.setVisibility(PUBLIC);
        irClassBuilder.setModality(Modality.FINAL);
        irClassBuilder.setCompanion(false);
        irClassBuilder.setInner(false);
        irClassBuilder.setData(false);
        irClassBuilder.setExternal(false);
        irClassBuilder.setValue(false);
        irClassBuilder.setExpect(false);
        irClassBuilder.setFun(false);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        irClassBuildClass.setParent(this.implicitDeclarationsFile);
        this.closureBoxType = new IrSimpleTypeImpl((IrClassifierSymbol) irClassBuildClass.getSymbol(), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier2 = Name.identifier("_this_");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier2);
        irValueParameterBuilder.setIndex(-1);
        IrType irType = this.closureBoxType;
        if (irType == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closureBoxType");
            irType = null;
        }
        irValueParameterBuilder.setType(irType);
        IrClass irClass = irClassBuildClass;
        irClassBuildClass.setThisReceiver(DeclarationBuildersKt.buildValueParameter(irClass.getFactory(), irValueParameterBuilder, irClass));
        this.implicitDeclarationsFile.getDeclarations().add(irClassBuildClass);
        return irClassBuildClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrField createClosureBoxPropertyDeclaration() {
        IrFieldSymbolImpl irFieldSymbolImpl = new IrFieldSymbolImpl(null, 1, null);
        Name nameIdentifier = Name.identifier(RegisterSpec.PREFIX);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrFactory irFactory = this.context.getIrFactory();
        IrDeclarationOrigin.FIELD_FOR_OUTER_THIS field_for_outer_this = IrDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE;
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        IrField irFieldCreateField = irFactory.createField(-1, -1, (IrDeclarationOrigin) field_for_outer_this, nameIdentifier, PUBLIC, (IrFieldSymbol) irFieldSymbolImpl, this.builtIns.getAnyNType(), false, false, false);
        irFieldCreateField.setParent(getClosureBoxClassDeclaration());
        getClosureBoxClassDeclaration().getDeclarations().add(irFieldCreateField);
        return irFieldCreateField;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstructor createClosureBoxConstructorDeclaration() {
        IrConstructorSymbolImpl irConstructorSymbolImpl = new IrConstructorSymbolImpl(null, 1, null);
        IrFactory irFactory = this.context.getIrFactory();
        JsLoweredDeclarationOrigin.JS_CLOSURE_BOX_CLASS_DECLARATION js_closure_box_class_declaration = JsLoweredDeclarationOrigin.JS_CLOSURE_BOX_CLASS_DECLARATION.INSTANCE;
        Name name2 = SpecialNames.INIT;
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        IrConstructor irConstructorCreateConstructor$default = IrFactory.DefaultImpls.createConstructor$default(irFactory, -1, -1, (IrDeclarationOrigin) js_closure_box_class_declaration, name2, PUBLIC, false, false, (IrType) IrUtilsKt.getDefaultType(getClosureBoxClassDeclaration()), (IrConstructorSymbol) irConstructorSymbolImpl, true, false, (DeserializedContainerSource) null, 2048, (Object) null);
        irConstructorCreateConstructor$default.setParent(getClosureBoxClassDeclaration());
        IrValueParameter irValueParameterCreateClosureBoxConstructorParameterDeclaration = createClosureBoxConstructorParameterDeclaration(irConstructorCreateConstructor$default);
        irConstructorCreateConstructor$default.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irConstructorCreateConstructor$default.getValueParameters(), irValueParameterCreateClosureBoxConstructorParameterDeclaration));
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrValueParameter thisReceiver = getClosureBoxClassDeclaration().getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        irConstructorCreateConstructor$default.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, (List<? extends IrStatement>) CollectionsKt.listOf(JsIrBuilder.buildSetField$default(JsIrBuilder.INSTANCE, getClosureBoxFieldDeclaration().getSymbol(), JsIrBuilder.buildGetValue$default(jsIrBuilder, thisReceiver.getSymbol(), null, 2, null), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irValueParameterCreateClosureBoxConstructorParameterDeclaration.getSymbol(), null, 2, null), this.builtIns.getUnitType(), null, 16, null))));
        getClosureBoxClassDeclaration().getDeclarations().add(irConstructorCreateConstructor$default);
        return irConstructorCreateConstructor$default;
    }

    private final IrValueParameter createClosureBoxConstructorParameterDeclaration(IrConstructor irConstructor) {
        return JsIrBuilder.buildValueParameter$default(JsIrBuilder.INSTANCE, irConstructor, "p", 0, getClosureBoxPropertyDeclaration().getType(), false, null, 48, null);
    }
}
