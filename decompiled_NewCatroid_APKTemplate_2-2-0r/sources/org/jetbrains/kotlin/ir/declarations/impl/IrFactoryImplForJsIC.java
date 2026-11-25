package org.jetbrains.kotlin.ir.declarations.impl;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.WeakHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.declarations.IdSignatureRetriever;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.StageController;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFactoryImplForJsIC.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0088\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J0\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0088\u0001\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u00162\u0006\u0010$\u001a\u00020\u00162\u0006\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020*H\u0016Jj\u0010+\u001a\u00020,2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020\u00162\u0006\u0010'\u001a\u00020\u00162\u0006\u0010.\u001a\u00020/2\u0006\u0010\u0013\u001a\u0002002\u0006\u00101\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00162\b\u00102\u001a\u0004\u0018\u000103H\u0016J0\u00104\u001a\u0002052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u000206H\u0016J\"\u00107\u001a\u0002082\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\b\u00109\u001a\u0004\u0018\u00010:H\u0016JX\u0010;\u001a\u00020<2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020=2\u0006\u0010>\u001a\u00020/2\u0006\u0010?\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u0016H\u0016J\u0080\u0001\u0010@\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020\u00162\u0006\u0010'\u001a\u00020\u00162\u0006\u0010.\u001a\u00020/2\u0006\u0010 \u001a\u00020!2\u0006\u0010B\u001a\u00020\u00162\u0006\u0010C\u001a\u00020\u00162\u0006\u0010D\u001a\u00020\u00162\u0006\u0010E\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010F\u001a\u00020\u0016H\u0016J@\u0010G\u001a\u00020H2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020I2\u0006\u0010>\u001a\u00020/2\u0006\u0010J\u001a\u00020\u0016H\u0016J\u0082\u0001\u0010K\u001a\u00020L2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0013\u001a\u00020M2\u0006\u0010J\u001a\u00020\u00162\u0006\u0010N\u001a\u00020\u00162\u0006\u0010O\u001a\u00020\u00162\u0006\u0010P\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00162\b\u00102\u001a\u0004\u0018\u0001032\u0006\u0010'\u001a\u00020\u00162\u0006\u0010F\u001a\u00020\u0016H\u0016Jp\u0010Q\u001a\u00020R2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!2\u0006\u0010J\u001a\u00020\u00162\u0006\u0010N\u001a\u00020\u00162\u0006\u0010O\u001a\u00020\u00162\u0006\u0010P\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010'\u001a\u00020\u00162\u0006\u0010F\u001a\u00020\u0016H\u0016J\u0092\u0001\u0010S\u001a\u00020T2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020\u00162\u0006\u0010'\u001a\u00020\u00162\u0006\u0010.\u001a\u00020/2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0013\u001a\u00020U2\u0006\u0010B\u001a\u00020\u00162\u0006\u0010C\u001a\u00020\u00162\u0006\u0010D\u001a\u00020\u00162\u0006\u0010E\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00162\b\u00102\u001a\u0004\u0018\u0001032\u0006\u0010F\u001a\u00020\u0016H\u0016JH\u0010V\u001a\u00020W2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020X2\u0006\u0010Y\u001a\u00020\u00162\u0006\u0010Z\u001a\u00020/H\u0016JH\u0010[\u001a\u00020\\2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020]2\u0006\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020\u000f2\u0006\u0010a\u001a\u00020\u0016H\u0016Jj\u0010b\u001a\u00020c2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010>\u001a\u00020/2\u0006\u0010d\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020e2\u0006\u0010`\u001a\u00020\u000f2\b\u0010f\u001a\u0004\u0018\u00010/2\u0006\u0010g\u001a\u00020\u00162\u0006\u0010h\u001a\u00020\u00162\u0006\u0010i\u001a\u00020\u0016H\u0016J\u0012\u0010j\u001a\u0004\u0018\u00010\t2\u0006\u0010k\u001a\u00020\bH\u0016J\u001b\u0010l\u001a\u0002Hm\"\b\b\u0000\u0010m*\u00020\b*\u0002HmH\u0002¢\u0006\u0002\u0010nR\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006o"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrFactoryImplForJsIC;", "Lorg/jetbrains/kotlin/ir/declarations/impl/AbstractIrFactoryImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IdSignatureRetriever;", "stageController", "Lorg/jetbrains/kotlin/ir/declarations/StageController;", "(Lorg/jetbrains/kotlin/ir/declarations/StageController;)V", "declarationToSignature", "Ljava/util/WeakHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getStageController", "()Lorg/jetbrains/kotlin/ir/declarations/StageController;", "createAnonymousInitializer", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrAnonymousInitializerSymbol;", "isStatic", "", "createClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isExternal", "isCompanion", "isInner", "isData", "isValue", "isExpect", "isFun", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "createConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "isInline", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "isPrimary", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "createEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "createField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", ModuleXmlParser.TYPE, "isFinal", "createFunctionWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "isTailrec", "isSuspend", "isOperator", "isInfix", "isFakeOverride", "createLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "isVar", "createProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "isConst", "isLateinit", "isDelegated", "createPropertyWithLateBinding", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "createSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "createTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "isActual", "expandedType", "createTypeParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "index", "isReified", "createValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "isAssignable", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "varargElementType", "isCrossinline", "isNoinline", "isHidden", "declarationSignature", "declaration", "register", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFactoryImplForJsIC extends AbstractIrFactoryImpl implements IdSignatureRetriever {
    private final WeakHashMap<IrDeclaration, IdSignature> declarationToSignature;
    private final StageController stageController;

    public IrFactoryImplForJsIC(StageController stageController) {
        Intrinsics.checkNotNullParameter(stageController, "stageController");
        this.stageController = stageController;
        this.declarationToSignature = new WeakHashMap<>();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFactory
    public StageController getStageController() {
        return this.stageController;
    }

    private final <T extends IrDeclaration> T register(T t) {
        IdSignature idSignatureDeclarationSignature;
        IdSignature idSignatureCreateSignature;
        IrDeclaration currentDeclaration = getStageController().getCurrentDeclaration();
        if (currentDeclaration != null && (idSignatureDeclarationSignature = declarationSignature(currentDeclaration)) != null && (idSignatureCreateSignature = getStageController().createSignature(idSignatureDeclarationSignature)) != null) {
            this.declarationToSignature.put(t, idSignatureCreateSignature);
        }
        return t;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IdSignatureRetriever
    public IdSignature declarationSignature(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IdSignature idSignature = this.declarationToSignature.get(declaration);
        if (idSignature != null) {
            return idSignature;
        }
        IdSignature signature = declaration.getSymbol().getSignature();
        return signature == null ? declaration.getSymbol().getPrivateSignature() : signature;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrAnonymousInitializer createAnonymousInitializer(int startOffset, int endOffset, IrDeclarationOrigin origin, IrAnonymousInitializerSymbol symbol, boolean isStatic) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrAnonymousInitializer) register(super.createAnonymousInitializer(startOffset, endOffset, origin, symbol, isStatic));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrClass createClass(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrClassSymbol symbol, ClassKind kind, Modality modality, boolean isExternal, boolean isCompanion, boolean isInner, boolean isData, boolean isValue, boolean isExpect, boolean isFun, SourceElement source) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(source, "source");
        return (IrClass) register(super.createClass(startOffset, endOffset, origin, name2, visibility, symbol, kind, modality, isExternal, isCompanion, isInner, isData, isValue, isExpect, isFun, source));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrConstructor createConstructor(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, IrConstructorSymbol symbol, boolean isPrimary, boolean isExternal, DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrConstructor) register(super.createConstructor(startOffset, endOffset, origin, name2, visibility, isInline, isExpect, returnType, symbol, isPrimary, isExternal, containerSource));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrEnumEntry createEnumEntry(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrEnumEntry) register(super.createEnumEntry(startOffset, endOffset, origin, name2, symbol));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrErrorDeclaration createErrorDeclaration(int startOffset, int endOffset, DeclarationDescriptor descriptor) {
        return super.createErrorDeclaration(startOffset, endOffset, descriptor);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrField createField(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrFieldSymbol symbol, IrType type, boolean isFinal, boolean isStatic, boolean isExternal) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        return (IrField) register(super.createField(startOffset, endOffset, origin, name2, visibility, symbol, type, isFinal, isStatic, isExternal));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrSimpleFunction createSimpleFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, IrSimpleFunctionSymbol symbol, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, DeserializedContainerSource containerSource, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrSimpleFunction) register(super.createSimpleFunction(startOffset, endOffset, origin, name2, visibility, isInline, isExpect, returnType, modality, symbol, isTailrec, isSuspend, isOperator, isInfix, isExternal, containerSource, isFakeOverride));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrFunctionWithLateBinding createFunctionWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, boolean isInline, boolean isExpect, IrType returnType, Modality modality, boolean isTailrec, boolean isSuspend, boolean isOperator, boolean isInfix, boolean isExternal, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return (IrFunctionWithLateBinding) register(super.createFunctionWithLateBinding(startOffset, endOffset, origin, name2, visibility, isInline, isExpect, returnType, modality, isTailrec, isSuspend, isOperator, isInfix, isExternal, isFakeOverride));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrLocalDelegatedProperty createLocalDelegatedProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrLocalDelegatedPropertySymbol symbol, IrType type, boolean isVar) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        return (IrLocalDelegatedProperty) register(super.createLocalDelegatedProperty(startOffset, endOffset, origin, name2, symbol, type, isVar));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrProperty createProperty(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, IrPropertySymbol symbol, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, DeserializedContainerSource containerSource, boolean isExpect, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrProperty) register(super.createProperty(startOffset, endOffset, origin, name2, visibility, modality, symbol, isVar, isConst, isLateinit, isDelegated, isExternal, containerSource, isExpect, isFakeOverride));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrPropertyWithLateBinding createPropertyWithLateBinding(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, Modality modality, boolean isVar, boolean isConst, boolean isLateinit, boolean isDelegated, boolean isExternal, boolean isExpect, boolean isFakeOverride) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        return (IrPropertyWithLateBinding) register(super.createPropertyWithLateBinding(startOffset, endOffset, origin, name2, visibility, modality, isVar, isConst, isLateinit, isDelegated, isExternal, isExpect, isFakeOverride));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrTypeAlias createTypeAlias(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, DescriptorVisibility visibility, IrTypeAliasSymbol symbol, boolean isActual, IrType expandedType) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(expandedType, "expandedType");
        return (IrTypeAlias) register(super.createTypeAlias(startOffset, endOffset, origin, name2, visibility, symbol, isActual, expandedType));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrTypeParameter createTypeParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrTypeParameterSymbol symbol, Variance variance, int index, boolean isReified) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return (IrTypeParameter) register(super.createTypeParameter(startOffset, endOffset, origin, name2, symbol, variance, index, isReified));
    }

    @Override // org.jetbrains.kotlin.ir.declarations.impl.AbstractIrFactoryImpl, org.jetbrains.kotlin.ir.declarations.IrFactory
    public IrValueParameter createValueParameter(int startOffset, int endOffset, IrDeclarationOrigin origin, Name name2, IrType type, boolean isAssignable, IrValueParameterSymbol symbol, int index, IrType varargElementType, boolean isCrossinline, boolean isNoinline, boolean isHidden) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return (IrValueParameter) register(super.createValueParameter(startOffset, endOffset, origin, name2, type, isAssignable, symbol, index, varargElementType, isCrossinline, isNoinline, isHidden));
    }
}
