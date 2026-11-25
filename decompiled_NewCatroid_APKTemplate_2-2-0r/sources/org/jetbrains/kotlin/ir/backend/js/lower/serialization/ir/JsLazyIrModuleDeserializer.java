package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.DescriptorByIdSignatureFinderImpl;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializerKind;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.impl.IrModuleFragmentImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JsLazyIrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0011\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0096\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010!\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u001a\u0010(\u001a\u0004\u0018\u00010%2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsLazyIrModuleDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "libraryAbiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/library/KotlinAbiVersion;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;)V", "dependencies", "", "descriptorFinder", "Lorg/jetbrains/kotlin/backend/common/serialization/DescriptorByIdSignatureFinderImpl;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "moduleDependencies", "", "getModuleDependencies", "()Ljava/util/Collection;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "contains", "", "idSig", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "declareFieldStub", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "declareIrSymbol", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "deserializedSymbolNotFound", "", "tryDeserializeIrSymbol", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsLazyIrModuleDeserializer extends IrModuleDeserializer {
    private final IrBuiltIns builtIns;
    private final List<IrModuleDeserializer> dependencies;
    private final DescriptorByIdSignatureFinderImpl descriptorFinder;
    private final Collection<IrModuleDeserializer> moduleDependencies;
    private final IrModuleFragment moduleFragment;
    private final DeclarationStubGenerator stubGenerator;

    /* JADX INFO: compiled from: JsLazyIrModuleDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BinarySymbolData.SymbolKind.values().length];
            try {
                iArr[BinarySymbolData.SymbolKind.CLASS_SYMBOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.PROPERTY_SYMBOL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.FUNCTION_SYMBOL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public boolean contains(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsLazyIrModuleDeserializer(ModuleDescriptor moduleDescriptor, KotlinAbiVersion libraryAbiVersion, IrBuiltIns builtIns, DeclarationStubGenerator stubGenerator) {
        super(moduleDescriptor, libraryAbiVersion);
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(libraryAbiVersion, "libraryAbiVersion");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        this.builtIns = builtIns;
        this.stubGenerator = stubGenerator;
        List<IrModuleDeserializer> listEmptyList = CollectionsKt.emptyList();
        this.dependencies = listEmptyList;
        this.descriptorFinder = new DescriptorByIdSignatureFinderImpl(moduleDescriptor, JsManglerDesc.INSTANCE, null, 4, null);
        this.moduleFragment = new IrModuleFragmentImpl(moduleDescriptor, builtIns, CollectionsKt.emptyList());
        this.moduleDependencies = listEmptyList;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    /* JADX INFO: renamed from: tryDeserializeIrSymbol */
    public IrSymbol mo6122tryDeserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        IrClass irClassGenerateClassStub;
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        DeclarationDescriptor declarationDescriptorFindDescriptorBySignature = this.descriptorFinder.findDescriptorBySignature(idSig);
        if (declarationDescriptorFindDescriptorBySignature == null) {
            return null;
        }
        DeclarationStubGenerator declarationStubGenerator = this.stubGenerator;
        switch (WhenMappings.$EnumSwitchMapping$0[symbolKind.ordinal()]) {
            case 1:
                irClassGenerateClassStub = declarationStubGenerator.generateClassStub((ClassDescriptor) declarationDescriptorFindDescriptorBySignature);
                break;
            case 2:
                irClassGenerateClassStub = declarationStubGenerator.generatePropertyStub((PropertyDescriptor) declarationDescriptorFindDescriptorBySignature);
                break;
            case 3:
                irClassGenerateClassStub = DeclarationStubGenerator.generateFunctionStub$default(declarationStubGenerator, (FunctionDescriptor) declarationDescriptorFindDescriptorBySignature, false, 2, null);
                break;
            case 4:
                irClassGenerateClassStub = declarationStubGenerator.generateConstructorStub((ClassConstructorDescriptor) declarationDescriptorFindDescriptorBySignature);
                break;
            case 5:
                irClassGenerateClassStub = declarationStubGenerator.generateEnumEntryStub((ClassDescriptor) declarationDescriptorFindDescriptorBySignature);
                break;
            case 6:
                irClassGenerateClassStub = declarationStubGenerator.generateTypeAliasStub((TypeAliasDescriptor) declarationDescriptorFindDescriptorBySignature);
                break;
            default:
                throw new IllegalStateException(("Unexpected type " + symbolKind + " for sig " + idSig).toString());
        }
        return irClassGenerateClassStub.getSymbol();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Void deserializedSymbolNotFound(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        throw new IllegalStateException(("No descriptor found for " + idSig).toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void declareIrSymbol(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol instanceof IrFieldSymbol) {
            declareFieldStub((IrFieldSymbol) symbol);
        } else {
            this.stubGenerator.generateMemberStub(symbol.getDescriptor());
        }
    }

    private final IrField declareFieldStub(IrFieldSymbol symbol) {
        DeclarationStubGenerator declarationStubGenerator = this.stubGenerator;
        boolean unboundSymbolGeneration = declarationStubGenerator.getUnboundSymbolGeneration();
        try {
            this.stubGenerator.setUnboundSymbolGeneration(true);
            return declarationStubGenerator.generateFieldStub(symbol.getDescriptor());
        } finally {
            this.stubGenerator.setUnboundSymbolGeneration(unboundSymbolGeneration);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleFragment getModuleFragment() {
        return this.moduleFragment;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Collection<IrModuleDeserializer> getModuleDependencies() {
        return this.moduleDependencies;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleDeserializerKind getKind() {
        return IrModuleDeserializerKind.SYNTHETIC;
    }
}
