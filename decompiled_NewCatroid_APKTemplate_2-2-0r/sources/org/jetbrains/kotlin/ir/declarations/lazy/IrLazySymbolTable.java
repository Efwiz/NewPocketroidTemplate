package org.jetbrains.kotlin.ir.declarations.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLazySymbolTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u00017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0096\u0001J\u0011\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#H\u0096\u0001J\u0011\u0010$\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0096\u0001J\u0011\u0010$\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#H\u0096\u0001J\u0011\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0096\u0001J\u0011\u0010)\u001a\u00020*2\u0006\u0010'\u001a\u00020(H\u0096\u0001J\u0011\u0010+\u001a\u00020,2\u0006\u0010'\u001a\u00020(H\u0096\u0001J\u0011\u0010-\u001a\u00020.2\u0006\u0010'\u001a\u00020(H\u0096\u0001J\u0011\u0010/\u001a\u0002002\u0006\u0010'\u001a\u00020(H\u0096\u0001J\u0011\u00101\u001a\u0002022\u0006\u0010'\u001a\u00020(H\u0096\u0001J\u0011\u00103\u001a\u0002042\u0006\u0010'\u001a\u00020(H\u0096\u0001J\u0011\u00105\u001a\u0002062\u0006\u0010'\u001a\u00020(H\u0096\u0001RP\u0010\u0005\u001a6\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\u0006j\u0002`\u000f8\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazySymbolTable;", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "originalTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "descriptorExtension", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTableExtension;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/util/DescriptorBasedReferenceSymbolTableExtension;", "getDescriptorExtension$annotations", "()V", "getDescriptorExtension", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTableExtension;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "setStubGenerator", "(Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;)V", "enterScope", "", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "leaveScope", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "referenceConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "referenceEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "referenceField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "referenceProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceSimpleFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "referenceTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "referenceTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "ExtensionWrapper", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLazySymbolTable implements ReferenceSymbolTable {
    private final ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension;
    private final SymbolTable originalTable;
    private DeclarationStubGenerator stubGenerator;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptorExtension$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void enterScope(IrDeclaration owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.originalTable.enterScope(owner);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void enterScope(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.originalTable.enterScope(symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void leaveScope(IrDeclaration owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.originalTable.leaveScope(owner);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public void leaveScope(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.originalTable.leaveScope(symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrClassSymbol referenceClass(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceClass(signature);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrConstructorSymbol referenceConstructor(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceConstructor(signature);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrEnumEntrySymbol referenceEnumEntry(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceEnumEntry(signature);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrFieldSymbol referenceField(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceField(signature);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrPropertySymbol referenceProperty(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceProperty(signature);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrSimpleFunctionSymbol referenceSimpleFunction(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceSimpleFunction(signature);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrTypeAliasSymbol referenceTypeAlias(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceTypeAlias(signature);
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public IrTypeParameterSymbol referenceTypeParameter(IdSignature signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return this.originalTable.referenceTypeParameter(signature);
    }

    public IrLazySymbolTable(SymbolTable originalTable) {
        Intrinsics.checkNotNullParameter(originalTable, "originalTable");
        this.originalTable = originalTable;
        this.descriptorExtension = new ExtensionWrapper();
    }

    public final IrLock getLock() {
        return this.originalTable.getLock();
    }

    public final DeclarationStubGenerator getStubGenerator() {
        return this.stubGenerator;
    }

    public final void setStubGenerator(DeclarationStubGenerator declarationStubGenerator) {
        this.stubGenerator = declarationStubGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.util.ReferenceSymbolTable
    public ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> getDescriptorExtension() {
        return this.descriptorExtension;
    }

    /* JADX INFO: compiled from: IrLazySymbolTable.kt */
    @Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\u0014H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u000fH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\b\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\u0019H\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020'2\u0006\u0010\b\u001a\u00020(H\u0016R\u0014\u0010\u0003\u001a\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazySymbolTable$ExtensionWrapper;", "Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "(Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazySymbolTable;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "referenceClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "declaration", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "referenceConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "referenceDeclaredFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "referenceEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "referenceField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "referenceProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "referenceScopedTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "referenceScript", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "referenceSimpleFunction", "referenceTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "referenceTypeParameter", "referenceValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "value", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "referenceValueParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ExtensionWrapper extends DescriptorSymbolTableExtension {
        public ExtensionWrapper() {
            super(IrLazySymbolTable.this.originalTable);
        }

        private final DescriptorSymbolTableExtension getDelegate() {
            return IrLazySymbolTable.this.originalTable.getDescriptorExtension();
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension, org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrClassSymbol referenceClass(ClassDescriptor declaration) {
            IrClassSymbol irClassSymbolReferenceClass;
            DeclarationStubGenerator stubGenerator;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrLock lock = getLock();
            IrLazySymbolTable irLazySymbolTable = IrLazySymbolTable.this;
            synchronized (lock) {
                irClassSymbolReferenceClass = getDelegate().referenceClass(declaration);
                if (!irClassSymbolReferenceClass.isBound() && (stubGenerator = irLazySymbolTable.getStubGenerator()) != null) {
                    stubGenerator.generateClassStub(declaration);
                }
            }
            return irClassSymbolReferenceClass;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrTypeAliasSymbol referenceTypeAlias(TypeAliasDescriptor declaration) {
            IrTypeAliasSymbol irTypeAliasSymbolReferenceTypeAlias;
            DeclarationStubGenerator stubGenerator;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrLock lock = getLock();
            IrLazySymbolTable irLazySymbolTable = IrLazySymbolTable.this;
            synchronized (lock) {
                irTypeAliasSymbolReferenceTypeAlias = getDelegate().referenceTypeAlias(declaration);
                if (!irTypeAliasSymbolReferenceTypeAlias.isBound() && (stubGenerator = irLazySymbolTable.getStubGenerator()) != null) {
                    stubGenerator.generateTypeAliasStub(declaration);
                }
            }
            return irTypeAliasSymbolReferenceTypeAlias;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrConstructorSymbol referenceConstructor(ClassConstructorDescriptor declaration) {
            IrConstructorSymbol irConstructorSymbolReferenceConstructor;
            DeclarationStubGenerator stubGenerator;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrLock lock = getLock();
            IrLazySymbolTable irLazySymbolTable = IrLazySymbolTable.this;
            synchronized (lock) {
                irConstructorSymbolReferenceConstructor = getDelegate().referenceConstructor(declaration);
                if (!irConstructorSymbolReferenceConstructor.isBound() && (stubGenerator = irLazySymbolTable.getStubGenerator()) != null) {
                    stubGenerator.generateConstructorStub(declaration);
                }
            }
            return irConstructorSymbolReferenceConstructor;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrEnumEntrySymbol referenceEnumEntry(ClassDescriptor declaration) {
            IrEnumEntrySymbol irEnumEntrySymbolReferenceEnumEntry;
            DeclarationStubGenerator stubGenerator;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrLock lock = getLock();
            IrLazySymbolTable irLazySymbolTable = IrLazySymbolTable.this;
            synchronized (lock) {
                irEnumEntrySymbolReferenceEnumEntry = getDelegate().referenceEnumEntry(declaration);
                if (!irEnumEntrySymbolReferenceEnumEntry.isBound() && (stubGenerator = irLazySymbolTable.getStubGenerator()) != null) {
                    stubGenerator.generateEnumEntryStub(declaration);
                }
            }
            return irEnumEntrySymbolReferenceEnumEntry;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrSimpleFunctionSymbol referenceSimpleFunction(FunctionDescriptor declaration) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction;
            DeclarationStubGenerator stubGenerator;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrLock lock = getLock();
            IrLazySymbolTable irLazySymbolTable = IrLazySymbolTable.this;
            synchronized (lock) {
                irSimpleFunctionSymbolReferenceSimpleFunction = getDelegate().referenceSimpleFunction(declaration);
                if (!irSimpleFunctionSymbolReferenceSimpleFunction.isBound() && (stubGenerator = irLazySymbolTable.getStubGenerator()) != null) {
                    DeclarationStubGenerator.generateFunctionStub$default(stubGenerator, declaration, false, 2, null);
                }
            }
            return irSimpleFunctionSymbolReferenceSimpleFunction;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrPropertySymbol referenceProperty(PropertyDescriptor declaration) {
            IrPropertySymbol irPropertySymbolReferenceProperty;
            DeclarationStubGenerator stubGenerator;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrLock lock = getLock();
            IrLazySymbolTable irLazySymbolTable = IrLazySymbolTable.this;
            synchronized (lock) {
                irPropertySymbolReferenceProperty = getDelegate().referenceProperty(declaration);
                if (!irPropertySymbolReferenceProperty.isBound() && (stubGenerator = irLazySymbolTable.getStubGenerator()) != null) {
                    stubGenerator.generatePropertyStub(declaration);
                }
            }
            return irPropertySymbolReferenceProperty;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrTypeParameterSymbol referenceTypeParameter(TypeParameterDescriptor declaration) {
            IrTypeParameterSymbol irTypeParameterSymbolReferenceTypeParameter;
            DeclarationStubGenerator stubGenerator;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrLock lock = getLock();
            IrLazySymbolTable irLazySymbolTable = IrLazySymbolTable.this;
            synchronized (lock) {
                irTypeParameterSymbolReferenceTypeParameter = getDelegate().referenceTypeParameter(declaration);
                if (!irTypeParameterSymbolReferenceTypeParameter.isBound() && (stubGenerator = irLazySymbolTable.getStubGenerator()) != null) {
                    stubGenerator.generateOrGetTypeParameterStub$ir_tree(declaration);
                }
            }
            return irTypeParameterSymbolReferenceTypeParameter;
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension, org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrValueParameterSymbol referenceValueParameter(ParameterDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return getDelegate().referenceValueParameter(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension
        public IrValueSymbol referenceValue(ValueDescriptor value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return getDelegate().referenceValue(value);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrScriptSymbol referenceScript(ScriptDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return getDelegate().referenceScript(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrFieldSymbol referenceField(PropertyDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return getDelegate().referenceField(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrSimpleFunctionSymbol referenceDeclaredFunction(FunctionDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return getDelegate().referenceDeclaredFunction(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrTypeParameterSymbol referenceScopedTypeParameter(TypeParameterDescriptor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return getDelegate().referenceScopedTypeParameter(declaration);
        }
    }
}
