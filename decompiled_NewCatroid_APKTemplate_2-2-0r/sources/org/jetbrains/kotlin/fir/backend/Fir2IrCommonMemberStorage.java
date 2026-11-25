package org.jetbrains.kotlin.fir.backend;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.util.IdSignatureComposer;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: Fir2IrCommonMemberStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\fR)\u0010\u0017\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\b0\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\fR\u0011\u0010\u001b\u001a\u00020\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001d\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0012R\u001d\u0010#\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0\b¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\fR\u001d\u0010'\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\fR\u001d\u0010*\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020,0\u000e¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0012R\u0011\u0010.\u001a\u00020/¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u001d\u00102\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u0002040\b¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\f¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrCommonMemberStorage;", "", "signatureComposer", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "firMangler", "Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "(Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;Lorg/jetbrains/kotlin/fir/backend/FirMangler;)V", "classCache", "", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getClassCache", "()Ljava/util/Map;", "constructorCache", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructorCache", "()Ljava/util/concurrent/ConcurrentHashMap;", "enumEntryCache", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getEnumEntryCache", "fakeOverridesInClass", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideKey;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getFakeOverridesInClass", "firSignatureComposer", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "getFirSignatureComposer", "()Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "functionCache", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFunctionCache", "irFakeOverridesForFirFakeOverrideMap", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage$FakeOverrideIdentifier;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getIrFakeOverridesForFirFakeOverrideMap", "localClassCache", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getLocalClassCache", "propertyCache", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getPropertyCache", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeParameterCache", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getTypeParameterCache", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrCommonMemberStorage {
    private final Map<FirRegularClass, IrClass> classCache;
    private final ConcurrentHashMap<FirConstructor, IrConstructor> constructorCache;
    private final Map<FirEnumEntry, IrEnumEntry> enumEntryCache;
    private final Map<IrClass, Map<Fir2IrDeclarationStorage.FakeOverrideKey, FirCallableDeclaration>> fakeOverridesInClass;
    private final FirBasedSignatureComposer firSignatureComposer;
    private final ConcurrentHashMap<FirFunction, IrSimpleFunction> functionCache;
    private final Map<Fir2IrDeclarationStorage.FakeOverrideIdentifier, IrDeclaration> irFakeOverridesForFirFakeOverrideMap;
    private final Map<FirClass, IrClass> localClassCache;
    private final ConcurrentHashMap<FirProperty, IrProperty> propertyCache;
    private final SymbolTable symbolTable;
    private final Map<FirTypeParameter, IrTypeParameter> typeParameterCache;

    public Fir2IrCommonMemberStorage(IdSignatureComposer signatureComposer, FirMangler firMangler) {
        Intrinsics.checkNotNullParameter(signatureComposer, "signatureComposer");
        Intrinsics.checkNotNullParameter(firMangler, "firMangler");
        this.firSignatureComposer = new FirBasedSignatureComposer(firMangler);
        this.symbolTable = new SymbolTable(signatureComposer, IrFactoryImpl.INSTANCE, null, 4, null);
        this.classCache = new LinkedHashMap();
        this.typeParameterCache = new LinkedHashMap();
        this.enumEntryCache = new LinkedHashMap();
        this.localClassCache = new LinkedHashMap();
        this.functionCache = new ConcurrentHashMap<>();
        this.constructorCache = new ConcurrentHashMap<>();
        this.propertyCache = new ConcurrentHashMap<>();
        this.fakeOverridesInClass = new LinkedHashMap();
        this.irFakeOverridesForFirFakeOverrideMap = new LinkedHashMap();
    }

    public final FirBasedSignatureComposer getFirSignatureComposer() {
        return this.firSignatureComposer;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final Map<FirRegularClass, IrClass> getClassCache() {
        return this.classCache;
    }

    public final Map<FirTypeParameter, IrTypeParameter> getTypeParameterCache() {
        return this.typeParameterCache;
    }

    public final Map<FirEnumEntry, IrEnumEntry> getEnumEntryCache() {
        return this.enumEntryCache;
    }

    public final Map<FirClass, IrClass> getLocalClassCache() {
        return this.localClassCache;
    }

    public final ConcurrentHashMap<FirFunction, IrSimpleFunction> getFunctionCache() {
        return this.functionCache;
    }

    public final ConcurrentHashMap<FirConstructor, IrConstructor> getConstructorCache() {
        return this.constructorCache;
    }

    public final ConcurrentHashMap<FirProperty, IrProperty> getPropertyCache() {
        return this.propertyCache;
    }

    public final Map<IrClass, Map<Fir2IrDeclarationStorage.FakeOverrideKey, FirCallableDeclaration>> getFakeOverridesInClass() {
        return this.fakeOverridesInClass;
    }

    public final Map<Fir2IrDeclarationStorage.FakeOverrideIdentifier, IrDeclaration> getIrFakeOverridesForFirFakeOverrideMap() {
        return this.irFakeOverridesForFirFakeOverrideMap;
    }
}
