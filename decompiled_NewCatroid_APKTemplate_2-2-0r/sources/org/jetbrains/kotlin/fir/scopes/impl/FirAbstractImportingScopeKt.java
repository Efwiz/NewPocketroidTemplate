package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirFieldBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirAbstractImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u000b\u001a\u00020\f*\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0000\u001a\u0014\u0010\u000b\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010\r\u001a\u00020\u000eH\u0000\"I\u0010\u0002\u001a\n\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0004*\u0002H\u00032\u000e\u0010\u0000\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"<set-?>", "Lorg/jetbrains/kotlin/fir/scopes/impl/ImportedFromObjectOrStaticData;", "importedFromObjectOrStaticData", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "getImportedFromObjectOrStaticData", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/scopes/impl/ImportedFromObjectOrStaticData;", "setImportedFromObjectOrStaticData", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/scopes/impl/ImportedFromObjectOrStaticData;)V", "importedFromObjectOrStaticData$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "buildImportedVersion", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "importedClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirAbstractImportingScopeKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirAbstractImportingScopeKt.class, "providers"), "importedFromObjectOrStaticData", "getImportedFromObjectOrStaticData(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Lorg/jetbrains/kotlin/fir/scopes/impl/ImportedFromObjectOrStaticData;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor importedFromObjectOrStaticData$delegate = FirDeclarationDataRegistry.INSTANCE.data(ImportedFromObjectOrStaticClassIdKey.INSTANCE);

    public static final FirVariable buildImportedVersion(FirVariable firVariable, ClassId importedClassId) {
        Intrinsics.checkNotNullParameter(firVariable, "<this>");
        Intrinsics.checkNotNullParameter(importedClassId, "importedClassId");
        if (firVariable instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) firVariable;
            FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
            firPropertyBuilder.setSource(firProperty.getSource());
            firPropertyBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firProperty));
            firPropertyBuilder.setModuleData(firProperty.getModuleData());
            firPropertyBuilder.setOrigin(firProperty.getOrigin());
            firPropertyBuilder.setAttributes(firProperty.getAttributes().copy());
            firPropertyBuilder.setStatus(firProperty.getStatus());
            firPropertyBuilder.setReturnTypeRef(firProperty.getReturnTypeRef());
            firPropertyBuilder.setReceiverParameter(firProperty.getReceiverParameter());
            firPropertyBuilder.setDeprecationsProvider(firProperty.getDeprecationsProvider());
            firPropertyBuilder.setContainerSource(firProperty.getContainerSource());
            firPropertyBuilder.setDispatchReceiverType(firProperty.getDispatchReceiverType());
            firPropertyBuilder.setName(firProperty.getName());
            firPropertyBuilder.setInitializer(firProperty.getInitializer());
            firPropertyBuilder.setDelegate(firProperty.getDelegate());
            firPropertyBuilder.setVar(firProperty.getIsVar());
            firPropertyBuilder.setGetter(firProperty.getGetter());
            firPropertyBuilder.setSetter(firProperty.getSetter());
            firPropertyBuilder.setBackingField(firProperty.getBackingField());
            firPropertyBuilder.getAnnotations().addAll(firProperty.getAnnotations());
            firPropertyBuilder.getContextReceivers().addAll(firProperty.getContextReceivers());
            firPropertyBuilder.setSymbol(firProperty.getSymbol());
            firPropertyBuilder.setDelegateFieldSymbol(firProperty.getDelegateFieldSymbol());
            firPropertyBuilder.setLocal(firProperty.getIsLocal());
            firPropertyBuilder.setBodyResolveState(firProperty.getBodyResolveState());
            firPropertyBuilder.getTypeParameters().addAll(firProperty.getTypeParameters());
            firPropertyBuilder.setOrigin(FirDeclarationOrigin.ImportedFromObjectOrStatic.INSTANCE);
            firPropertyBuilder.setSymbol(new FirPropertySymbol(new CallableId(importedClassId, firPropertyBuilder.getName())));
            firPropertyBuilder.setDelegateFieldSymbol(null);
            FirProperty firPropertyMo6810build = firPropertyBuilder.mo6809build();
            setImportedFromObjectOrStaticData(firPropertyMo6810build, new ImportedFromObjectOrStaticData(importedClassId, firVariable));
            return firPropertyMo6810build;
        }
        if (firVariable instanceof FirField) {
            FirField firField = (FirField) firVariable;
            FirFieldBuilder firFieldBuilder = new FirFieldBuilder();
            firFieldBuilder.setSource(firField.getSource());
            firFieldBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firField));
            firFieldBuilder.setModuleData(firField.getModuleData());
            firFieldBuilder.setOrigin(firField.getOrigin());
            firFieldBuilder.setAttributes(firField.getAttributes().copy());
            firFieldBuilder.getTypeParameters().addAll(firField.getTypeParameters());
            firFieldBuilder.setStatus(firField.getStatus());
            firFieldBuilder.setReturnTypeRef(firField.getReturnTypeRef());
            firFieldBuilder.setDeprecationsProvider(firField.getDeprecationsProvider());
            firFieldBuilder.setContainerSource(firField.getContainerSource());
            firFieldBuilder.setDispatchReceiverType(firField.getDispatchReceiverType());
            firFieldBuilder.getContextReceivers().addAll(firField.getContextReceivers());
            firFieldBuilder.setName(firField.getName());
            firFieldBuilder.setInitializer(firField.getInitializer());
            firFieldBuilder.setVar(firField.getIsVar());
            firFieldBuilder.setBackingField(firField.getBackingField());
            firFieldBuilder.getAnnotations().addAll(firField.getAnnotations());
            firFieldBuilder.setSymbol(firField.getSymbol());
            firFieldBuilder.setOrigin(FirDeclarationOrigin.ImportedFromObjectOrStatic.INSTANCE);
            firFieldBuilder.setSymbol(new FirFieldSymbol(new CallableId(importedClassId, firFieldBuilder.getName())));
            FirField firFieldMo6810build = firFieldBuilder.mo6809build();
            setImportedFromObjectOrStaticData(firFieldMo6810build, new ImportedFromObjectOrStaticData(importedClassId, firVariable));
            return firFieldMo6810build;
        }
        if (firVariable instanceof FirEnumEntry) {
            return firVariable;
        }
        throw new IllegalStateException("Unexpected variable in buildImportedCopy: " + UtilsKt.render(firVariable) + " of type " + firVariable.getClass());
    }

    public static final <D extends FirCallableDeclaration> ImportedFromObjectOrStaticData<D> getImportedFromObjectOrStaticData(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        return (ImportedFromObjectOrStaticData) importedFromObjectOrStaticData$delegate.getValue(d, $$delegatedProperties[0]);
    }

    public static final <D extends FirCallableDeclaration> void setImportedFromObjectOrStaticData(D d, ImportedFromObjectOrStaticData<D> importedFromObjectOrStaticData) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        importedFromObjectOrStaticData$delegate.setValue(d, $$delegatedProperties[0], importedFromObjectOrStaticData);
    }

    public static final FirSimpleFunction buildImportedVersion(FirSimpleFunction firSimpleFunction, ClassId importedClassId) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(importedClassId, "importedClassId");
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setSource(firSimpleFunction.getSource());
        firSimpleFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firSimpleFunction));
        firSimpleFunctionBuilder.setModuleData(firSimpleFunction.getModuleData());
        firSimpleFunctionBuilder.setOrigin(firSimpleFunction.getOrigin());
        firSimpleFunctionBuilder.setAttributes(firSimpleFunction.getAttributes().copy());
        firSimpleFunctionBuilder.setStatus(firSimpleFunction.getStatus());
        firSimpleFunctionBuilder.setReturnTypeRef(firSimpleFunction.getReturnTypeRef());
        firSimpleFunctionBuilder.setReceiverParameter(firSimpleFunction.getReceiverParameter());
        firSimpleFunctionBuilder.setDeprecationsProvider(firSimpleFunction.getDeprecationsProvider());
        firSimpleFunctionBuilder.setContainerSource(firSimpleFunction.getContainerSource());
        firSimpleFunctionBuilder.setDispatchReceiverType(firSimpleFunction.getDispatchReceiverType());
        firSimpleFunctionBuilder.getContextReceivers().addAll(firSimpleFunction.getContextReceivers());
        firSimpleFunctionBuilder.getValueParameters().addAll(firSimpleFunction.getValueParameters());
        firSimpleFunctionBuilder.setBody(firSimpleFunction.getBody());
        firSimpleFunctionBuilder.setContractDescription(firSimpleFunction.getContractDescription());
        firSimpleFunctionBuilder.setName(firSimpleFunction.getName());
        firSimpleFunctionBuilder.setSymbol(firSimpleFunction.getSymbol());
        firSimpleFunctionBuilder.getAnnotations().addAll(firSimpleFunction.getAnnotations());
        firSimpleFunctionBuilder.getTypeParameters().addAll(firSimpleFunction.getTypeParameters());
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.ImportedFromObjectOrStatic.INSTANCE);
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(importedClassId, firSimpleFunctionBuilder.getName())));
        FirSimpleFunction firSimpleFunctionMo6810build = firSimpleFunctionBuilder.mo6809build();
        setImportedFromObjectOrStaticData(firSimpleFunctionMo6810build, new ImportedFromObjectOrStaticData(importedClassId, firSimpleFunction));
        return firSimpleFunctionMo6810build;
    }
}
