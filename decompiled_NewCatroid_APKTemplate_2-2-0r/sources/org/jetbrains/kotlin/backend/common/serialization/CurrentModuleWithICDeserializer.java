package org.jetbrains.kotlin.backend.common.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeserializedDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbolKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.jetbrains.kotlin.library.SerializedIrFile;

/* JADX INFO: compiled from: IncrementalCompilationSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b¢\u0006\u0002\u0010\rJ\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0010H\u0016J\u0011\u0010(\u001a\u00020)2\u0006\u0010'\u001a\u00020\u0010H\u0096\u0002J\b\u0010*\u001a\u00020&H\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010'\u001a\u00020\u0010H\u0016J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u001eH\u0016J\u0010\u0010/\u001a\u00020&2\u0006\u0010\u0002\u001a\u00020\u0001H\u0016J\b\u00100\u001a\u000201H\u0016J\u0018\u00102\u001a\u00020\u00112\u0006\u0010'\u001a\u00020\u00102\u0006\u00103\u001a\u000204H\u0016J\f\u00105\u001a\u00020)*\u000206H\u0002R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fj\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/CurrentModuleWithICDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "icData", "", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "icReaderFactory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/library/IrLibrary;", "(Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "dirtyDeclarations", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lkotlin/collections/HashMap;", "icDeserializer", "icKlib", "Lorg/jetbrains/kotlin/backend/common/serialization/ICKotlinLibrary;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "klib", "getKlib", "()Lorg/jetbrains/kotlin/library/IrLibrary;", "moduleDependencies", "", "getModuleDependencies", "()Ljava/util/Collection;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "addModuleReachableTopLevel", "", "idSig", "contains", "", "deserializeReachableDeclarations", "deserializedSymbolNotFound", "", "fileDeserializers", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", ConfigConstants.CONFIG_INIT_SECTION, "toString", "", "tryDeserializeIrSymbol", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "isDirtyDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CurrentModuleWithICDeserializer extends IrModuleDeserializer {
    private final IrModuleDeserializer delegate;
    private final HashMap<IdSignature, IrSymbol> dirtyDeclarations;
    private final IrModuleDeserializer icDeserializer;
    private final ICKotlinLibrary icKlib;
    private final IrBuiltIns irBuiltIns;
    private final SymbolTable symbolTable;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CurrentModuleWithICDeserializer(IrModuleDeserializer delegate, SymbolTable symbolTable, IrBuiltIns irBuiltIns, List<SerializedIrFile> icData, Function1<? super IrLibrary, ? extends IrModuleDeserializer> icReaderFactory) {
        super(delegate.getModuleDescriptor(), KotlinAbiVersion.INSTANCE.getCURRENT());
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(icData, "icData");
        Intrinsics.checkNotNullParameter(icReaderFactory, "icReaderFactory");
        this.delegate = delegate;
        this.symbolTable = symbolTable;
        this.irBuiltIns = irBuiltIns;
        this.dirtyDeclarations = new HashMap<>();
        ICKotlinLibrary iCKotlinLibrary = new ICKotlinLibrary(icData);
        this.icKlib = iCKotlinLibrary;
        this.icDeserializer = icReaderFactory.invoke(iCKotlinLibrary);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public boolean contains(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        return this.dirtyDeclarations.containsKey(idSig) || this.icDeserializer.contains(idSig.topLevelSignature()) || this.delegate.contains(idSig);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    /* JADX INFO: renamed from: tryDeserializeIrSymbol */
    public IrSymbol mo6122tryDeserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        IrSymbol irSymbol = this.dirtyDeclarations.get(idSig);
        if (irSymbol != null) {
            return irSymbol;
        }
        return this.icDeserializer.contains(idSig.topLevelSignature()) ? IrModuleDeserializerKt.deserializeIrSymbolOrFail(this.icDeserializer, idSig, symbolKind) : IrModuleDeserializerKt.deserializeIrSymbolOrFail(this.delegate, idSig, symbolKind);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Void deserializedSymbolNotFound(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        this.delegate.deserializedSymbolNotFound(idSig);
        throw null;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void addModuleReachableTopLevel(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        this.icDeserializer.contains(idSig);
        this.icDeserializer.addModuleReachableTopLevel(idSig);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void deserializeReachableDeclarations() {
        this.icDeserializer.deserializeReachableDeclarations();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isDirtyDescriptor(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof PropertyAccessorDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            return isDirtyDescriptor(correspondingProperty);
        }
        if (declarationDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
            if (callableMemberDescriptor.mo6753getDispatchReceiverParameter() != null) {
                DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                return isDirtyDescriptor(containingDeclaration);
            }
        }
        return !(declarationDescriptor instanceof DeserializedDescriptor);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void init(IrModuleDeserializer delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        List<IrDeclaration> knownBuiltins = DeclarationTableKt.getKnownBuiltins(this.irBuiltIns);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(knownBuiltins, 10));
        for (IrDeclaration irDeclaration : knownBuiltins) {
            Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
            arrayList.add(irDeclaration.getSymbol());
        }
        final Set set = CollectionsKt.toSet(arrayList);
        this.symbolTable.getDescriptorExtension().forEachDeclarationSymbol(new Function1<IrSymbol, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.CurrentModuleWithICDeserializer.init.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrSymbol irSymbol) {
                invoke2(irSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrSymbolKt.isPublicApi(it);
                if (!CurrentModuleWithICDeserializer.this.isDirtyDescriptor(it.getDescriptor()) || set.contains(it)) {
                    return;
                }
                HashMap map = CurrentModuleWithICDeserializer.this.dirtyDeclarations;
                IdSignature signature = it.getSignature();
                Intrinsics.checkNotNull(signature);
                map.put(signature, it);
            }
        });
        this.icDeserializer.init(delegate);
    }

    public String toString() {
        return "Incremental Cache Klib";
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrLibrary getKlib() {
        return this.icDeserializer.getKlib();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleFragment getModuleFragment() {
        return this.delegate.getModuleFragment();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Collection<IrModuleDeserializer> getModuleDependencies() {
        return this.delegate.getModuleDependencies();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleDeserializerKind getKind() {
        return this.delegate.getKind();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Collection<IrFileDeserializer> fileDeserializers() {
        return this.delegate.fileDeserializers();
    }
}
