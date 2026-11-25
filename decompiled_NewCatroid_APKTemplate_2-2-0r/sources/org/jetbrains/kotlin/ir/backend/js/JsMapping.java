package org.jetbrains.kotlin.ir.backend.js;

import java.util.Collection;
import java.util.WeakHashMap;
import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.DefaultDelegateFactory;
import org.jetbrains.kotlin.backend.common.DefaultMapping;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.utils.MutableReference;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;

/* JADX INFO: compiled from: JsMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u001e\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00110\r¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u001d\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u001d\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\r¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000fR\u001d\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000fR\u001d\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u001d0\r¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000fR\u001d\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u000fR\u001d\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u001d0\r¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u000fR#\u0010#\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0$0\r¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u000fR\u001d\u0010'\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020%0\r¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u000fR\u001d\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00110\r¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u000fR\u001d\u0010+\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020,0\r¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u000fR\u001d\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\r¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\u000fR\u001d\u00100\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u000fR\u001d\u00102\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0\r¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\u000fR\u001d\u00104\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020%0\r¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u000fR\u001d\u00106\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\r¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\u000fR\u001d\u00108\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0\r¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u000fR\u001d\u0010:\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\u000fR\u001d\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\b=\u0010\u000fR\u001d\u0010>\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\u000fR#\u0010@\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140A0\r¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\u000fR\u001d\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\u000fR\u001d\u0010E\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\u000fR\u001d\u0010G\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\u000fR\u001d\u0010I\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u00140\r¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\u000f¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "Lorg/jetbrains/kotlin/backend/common/DefaultMapping;", "()V", "chunkToOriginalFile", "Ljava/util/WeakHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getChunkToOriginalFile", "()Ljava/util/WeakHashMap;", "classToItsDefaultConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getClassToItsDefaultConstructor", "classToSyntheticPrimaryConstructor", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "getClassToSyntheticPrimaryConstructor", "()Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "enumClassToCorrespondingEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getEnumClassToCorrespondingEnumEntry", "enumClassToInitEntryInstancesFun", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getEnumClassToInitEntryInstancesFun", "enumConstructorOldToNewValueParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getEnumConstructorOldToNewValueParameters", "enumConstructorToNewConstructor", "getEnumConstructorToNewConstructor", "enumEntryToCorrespondingField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getEnumEntryToCorrespondingField", "enumEntryToGetInstanceFun", "getEnumEntryToGetInstanceFun", "enumEntryToInstanceField", "getEnumEntryToInstanceField", "esClassToPossibilityForOptimization", "Lorg/jetbrains/kotlin/ir/backend/js/utils/MutableReference;", "", "getEsClassToPossibilityForOptimization", "esClassWhichNeedBoxParameters", "getEsClassWhichNeedBoxParameters", "fieldToEnumEntry", "getFieldToEnumEntry", "inlineFunctionsBeforeInlining", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getInlineFunctionsBeforeInlining", "innerClassConstructors", "getInnerClassConstructors", "objectToGetInstanceFunction", "getObjectToGetInstanceFunction", "objectToInstanceField", "getObjectToInstanceField", "objectsWithPureInitialization", "getObjectsWithPureInitialization", "originalInnerClassPrimaryConstructorByClass", "getOriginalInnerClassPrimaryConstructorByClass", "outerThisFieldSymbols", "getOuterThisFieldSymbols", "privateMemberToCorrespondingStatic", "getPrivateMemberToCorrespondingStatic", "secondaryConstructorToDelegate", "getSecondaryConstructorToDelegate", "secondaryConstructorToFactory", "getSecondaryConstructorToFactory", "suspendArityStore", "", "getSuspendArityStore", "wasmExternalClassToInstanceCheck", "getWasmExternalClassToInstanceCheck", "wasmExternalObjectToGetInstanceFunction", "getWasmExternalObjectToGetInstanceFunction", "wasmJsInteropFunctionToWrapper", "getWasmJsInteropFunctionToWrapper", "wasmNestedExternalToNewTopLevelFunction", "getWasmNestedExternalToNewTopLevelFunction", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsMapping extends DefaultMapping {
    private final WeakHashMap<IrFile, IrFile> chunkToOriginalFile;
    private final WeakHashMap<IrClass, IrConstructor> classToItsDefaultConstructor;
    private final Mapping.Delegate<IrClass, IrConstructor> classToSyntheticPrimaryConstructor;
    private final Mapping.Delegate<IrClass, IrEnumEntry> enumClassToCorrespondingEnumEntry;
    private final Mapping.Delegate<IrClass, IrSimpleFunction> enumClassToInitEntryInstancesFun;
    private final Mapping.Delegate<IrValueDeclaration, IrValueParameter> enumConstructorOldToNewValueParameters;
    private final Mapping.Delegate<IrConstructor, IrConstructor> enumConstructorToNewConstructor;
    private final Mapping.Delegate<IrEnumEntry, IrField> enumEntryToCorrespondingField;
    private final Mapping.Delegate<IrEnumEntry, IrSimpleFunction> enumEntryToGetInstanceFun;
    private final Mapping.Delegate<IrEnumEntry, IrField> enumEntryToInstanceField;
    private final Mapping.Delegate<IrClass, MutableReference<Boolean>> esClassToPossibilityForOptimization;
    private final Mapping.Delegate<IrClass, Boolean> esClassWhichNeedBoxParameters;
    private final Mapping.Delegate<IrField, IrEnumEntry> fieldToEnumEntry;
    private final Mapping.Delegate<IrFunction, IrFunction> inlineFunctionsBeforeInlining;
    private final Mapping.Delegate<IrConstructor, IrConstructor> innerClassConstructors;
    private final Mapping.Delegate<IrClass, IrSimpleFunction> objectToGetInstanceFunction;
    private final Mapping.Delegate<IrClass, IrField> objectToInstanceField;
    private final Mapping.Delegate<IrClass, Boolean> objectsWithPureInitialization;
    private final Mapping.Delegate<IrClass, IrConstructor> originalInnerClassPrimaryConstructorByClass;
    private final Mapping.Delegate<IrClass, IrField> outerThisFieldSymbols;
    private final Mapping.Delegate<IrFunction, IrSimpleFunction> privateMemberToCorrespondingStatic;
    private final Mapping.Delegate<IrConstructor, IrSimpleFunction> secondaryConstructorToDelegate;
    private final Mapping.Delegate<IrConstructor, IrSimpleFunction> secondaryConstructorToFactory;
    private final Mapping.Delegate<IrClass, Collection<IrSimpleFunction>> suspendArityStore;
    private final Mapping.Delegate<IrClass, IrSimpleFunction> wasmExternalClassToInstanceCheck;
    private final Mapping.Delegate<IrClass, IrSimpleFunction> wasmExternalObjectToGetInstanceFunction;
    private final Mapping.Delegate<IrSimpleFunction, IrSimpleFunction> wasmJsInteropFunctionToWrapper;
    private final Mapping.Delegate<IrFunction, IrSimpleFunction> wasmNestedExternalToNewTopLevelFunction;

    /* JADX WARN: Multi-variable type inference failed */
    public JsMapping() {
        super(null, 1, 0 == true ? 1 : 0);
        this.classToItsDefaultConstructor = new WeakHashMap<>();
        this.esClassWhichNeedBoxParameters = DefaultDelegateFactory.INSTANCE.newDeclarationToValueMapping();
        this.esClassToPossibilityForOptimization = DefaultDelegateFactory.INSTANCE.newDeclarationToValueMapping();
        this.chunkToOriginalFile = new WeakHashMap<>();
        this.outerThisFieldSymbols = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.innerClassConstructors = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.originalInnerClassPrimaryConstructorByClass = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.secondaryConstructorToDelegate = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.secondaryConstructorToFactory = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.objectToGetInstanceFunction = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.objectToInstanceField = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.classToSyntheticPrimaryConstructor = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.privateMemberToCorrespondingStatic = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.enumEntryToGetInstanceFun = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.enumEntryToInstanceField = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.enumConstructorToNewConstructor = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.enumClassToCorrespondingEnumEntry = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.enumConstructorOldToNewValueParameters = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.enumEntryToCorrespondingField = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.fieldToEnumEntry = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.enumClassToInitEntryInstancesFun = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.suspendArityStore = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationCollectionMapping();
        this.objectsWithPureInitialization = DefaultDelegateFactory.INSTANCE.newDeclarationToValueMapping();
        this.inlineFunctionsBeforeInlining = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.wasmJsInteropFunctionToWrapper = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.wasmNestedExternalToNewTopLevelFunction = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.wasmExternalObjectToGetInstanceFunction = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
        this.wasmExternalClassToInstanceCheck = DefaultDelegateFactory.INSTANCE.newDeclarationToDeclarationMapping();
    }

    public final WeakHashMap<IrClass, IrConstructor> getClassToItsDefaultConstructor() {
        return this.classToItsDefaultConstructor;
    }

    public final Mapping.Delegate<IrClass, Boolean> getEsClassWhichNeedBoxParameters() {
        return this.esClassWhichNeedBoxParameters;
    }

    public final Mapping.Delegate<IrClass, MutableReference<Boolean>> getEsClassToPossibilityForOptimization() {
        return this.esClassToPossibilityForOptimization;
    }

    public final WeakHashMap<IrFile, IrFile> getChunkToOriginalFile() {
        return this.chunkToOriginalFile;
    }

    public final Mapping.Delegate<IrClass, IrField> getOuterThisFieldSymbols() {
        return this.outerThisFieldSymbols;
    }

    public final Mapping.Delegate<IrConstructor, IrConstructor> getInnerClassConstructors() {
        return this.innerClassConstructors;
    }

    public final Mapping.Delegate<IrClass, IrConstructor> getOriginalInnerClassPrimaryConstructorByClass() {
        return this.originalInnerClassPrimaryConstructorByClass;
    }

    public final Mapping.Delegate<IrConstructor, IrSimpleFunction> getSecondaryConstructorToDelegate() {
        return this.secondaryConstructorToDelegate;
    }

    public final Mapping.Delegate<IrConstructor, IrSimpleFunction> getSecondaryConstructorToFactory() {
        return this.secondaryConstructorToFactory;
    }

    public final Mapping.Delegate<IrClass, IrSimpleFunction> getObjectToGetInstanceFunction() {
        return this.objectToGetInstanceFunction;
    }

    public final Mapping.Delegate<IrClass, IrField> getObjectToInstanceField() {
        return this.objectToInstanceField;
    }

    public final Mapping.Delegate<IrClass, IrConstructor> getClassToSyntheticPrimaryConstructor() {
        return this.classToSyntheticPrimaryConstructor;
    }

    public final Mapping.Delegate<IrFunction, IrSimpleFunction> getPrivateMemberToCorrespondingStatic() {
        return this.privateMemberToCorrespondingStatic;
    }

    public final Mapping.Delegate<IrEnumEntry, IrSimpleFunction> getEnumEntryToGetInstanceFun() {
        return this.enumEntryToGetInstanceFun;
    }

    public final Mapping.Delegate<IrEnumEntry, IrField> getEnumEntryToInstanceField() {
        return this.enumEntryToInstanceField;
    }

    public final Mapping.Delegate<IrConstructor, IrConstructor> getEnumConstructorToNewConstructor() {
        return this.enumConstructorToNewConstructor;
    }

    public final Mapping.Delegate<IrClass, IrEnumEntry> getEnumClassToCorrespondingEnumEntry() {
        return this.enumClassToCorrespondingEnumEntry;
    }

    public final Mapping.Delegate<IrValueDeclaration, IrValueParameter> getEnumConstructorOldToNewValueParameters() {
        return this.enumConstructorOldToNewValueParameters;
    }

    public final Mapping.Delegate<IrEnumEntry, IrField> getEnumEntryToCorrespondingField() {
        return this.enumEntryToCorrespondingField;
    }

    public final Mapping.Delegate<IrField, IrEnumEntry> getFieldToEnumEntry() {
        return this.fieldToEnumEntry;
    }

    public final Mapping.Delegate<IrClass, IrSimpleFunction> getEnumClassToInitEntryInstancesFun() {
        return this.enumClassToInitEntryInstancesFun;
    }

    public final Mapping.Delegate<IrClass, Collection<IrSimpleFunction>> getSuspendArityStore() {
        return this.suspendArityStore;
    }

    public final Mapping.Delegate<IrClass, Boolean> getObjectsWithPureInitialization() {
        return this.objectsWithPureInitialization;
    }

    public final Mapping.Delegate<IrFunction, IrFunction> getInlineFunctionsBeforeInlining() {
        return this.inlineFunctionsBeforeInlining;
    }

    public final Mapping.Delegate<IrSimpleFunction, IrSimpleFunction> getWasmJsInteropFunctionToWrapper() {
        return this.wasmJsInteropFunctionToWrapper;
    }

    public final Mapping.Delegate<IrFunction, IrSimpleFunction> getWasmNestedExternalToNewTopLevelFunction() {
        return this.wasmNestedExternalToNewTopLevelFunction;
    }

    public final Mapping.Delegate<IrClass, IrSimpleFunction> getWasmExternalObjectToGetInstanceFunction() {
        return this.wasmExternalObjectToGetInstanceFunction;
    }

    public final Mapping.Delegate<IrClass, IrSimpleFunction> getWasmExternalClassToInstanceCheck() {
        return this.wasmExternalClassToInstanceCheck;
    }
}
