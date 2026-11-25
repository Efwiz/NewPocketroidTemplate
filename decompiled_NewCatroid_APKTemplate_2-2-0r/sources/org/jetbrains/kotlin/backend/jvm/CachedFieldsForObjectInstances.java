package org.jetbrains.kotlin.backend.jvm;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.builtins.CompanionObjectMapping;
import org.jetbrains.kotlin.builtins.CompanionObjectMappingUtilsKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmCachedDeclarations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\f\u0010\u0010\u001a\u00020\u0011*\u00020\u000eH\u0002R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "interfaceCompanionFieldDeclarations", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "singletonFieldDeclarations", "getFieldForObjectInstance", "singleton", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getPrivateFieldForObjectInstance", "isMappedIntrinsicCompanionObject", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CachedFieldsForObjectInstances {
    private final ConcurrentHashMap<IrSymbolOwner, IrField> interfaceCompanionFieldDeclarations;
    private final IrFactory irFactory;
    private final LanguageVersionSettings languageVersionSettings;
    private final ConcurrentHashMap<IrSymbolOwner, IrField> singletonFieldDeclarations;

    public CachedFieldsForObjectInstances(IrFactory irFactory, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.irFactory = irFactory;
        this.languageVersionSettings = languageVersionSettings;
        this.singletonFieldDeclarations = new ConcurrentHashMap<>();
        this.interfaceCompanionFieldDeclarations = new ConcurrentHashMap<>();
    }

    public final IrField getFieldForObjectInstance(IrClass singleton) {
        Name nameIdentifier;
        Intrinsics.checkNotNullParameter(singleton, "singleton");
        ConcurrentHashMap<IrSymbolOwner, IrField> concurrentHashMap = this.singletonFieldDeclarations;
        IrField irFieldBuildField = concurrentHashMap.get(singleton);
        if (irFieldBuildField == null) {
            DescriptorVisibility PROTECTED_STATIC_VISIBILITY = singleton.getVisibility();
            boolean z = false;
            boolean z2 = singleton.isCompanion() && !isMappedIntrinsicCompanionObject(singleton);
            if (this.languageVersionSettings.supportsFeature(LanguageFeature.ProperVisibilityForCompanionObjectInstanceField) && singleton.isCompanion() && !IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(singleton))) {
                z = true;
            }
            IrFactory irFactory = this.irFactory;
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            if (z2) {
                nameIdentifier = singleton.getName();
            } else {
                nameIdentifier = Name.identifier(JvmAbi.INSTANCE_FIELD);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            }
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(IrUtilsKt.getDefaultType(singleton));
            irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE);
            irFieldBuilder.setFinal(true);
            irFieldBuilder.setStatic(true);
            if (!z) {
                PROTECTED_STATIC_VISIBILITY = DescriptorVisibilities.PUBLIC;
                Intrinsics.checkNotNullExpressionValue(PROTECTED_STATIC_VISIBILITY, "PUBLIC");
            } else if (Intrinsics.areEqual(PROTECTED_STATIC_VISIBILITY, DescriptorVisibilities.PROTECTED)) {
                PROTECTED_STATIC_VISIBILITY = JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY;
                Intrinsics.checkNotNullExpressionValue(PROTECTED_STATIC_VISIBILITY, "PROTECTED_STATIC_VISIBILITY");
            }
            irFieldBuilder.setVisibility(PROTECTED_STATIC_VISIBILITY);
            irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
            irFieldBuildField.setParent(z2 ? singleton.getParent() : singleton);
            IrField irFieldPutIfAbsent = concurrentHashMap.putIfAbsent(singleton, irFieldBuildField);
            if (irFieldPutIfAbsent != null) {
                irFieldBuildField = irFieldPutIfAbsent;
            }
        }
        Intrinsics.checkNotNullExpressionValue(irFieldBuildField, "getOrPut(...)");
        return irFieldBuildField;
    }

    private final boolean isMappedIntrinsicCompanionObject(IrClass irClass) {
        ClassId classId;
        return irClass.isCompanion() && (classId = AdditionalIrUtilsKt.getClassId(irClass)) != null && CompanionObjectMappingUtilsKt.isMappedIntrinsicCompanionObjectClassId(CompanionObjectMapping.INSTANCE, classId);
    }

    public final IrField getPrivateFieldForObjectInstance(IrClass singleton) {
        Intrinsics.checkNotNullParameter(singleton, "singleton");
        if (singleton.isCompanion() && JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(singleton))) {
            ConcurrentHashMap<IrSymbolOwner, IrField> concurrentHashMap = this.interfaceCompanionFieldDeclarations;
            IrField irFieldBuildField = concurrentHashMap.get(singleton);
            if (irFieldBuildField == null) {
                IrFactory irFactory = this.irFactory;
                IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                Name nameIdentifier = Name.identifier(JvmAbi.HIDDEN_INSTANCE_FIELD);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFieldBuilder.setName(nameIdentifier);
                irFieldBuilder.setType(IrUtilsKt.getDefaultType(singleton));
                irFieldBuilder.setOrigin(JvmLoweredDeclarationOrigin.INTERFACE_COMPANION_PRIVATE_INSTANCE.INSTANCE);
                irFieldBuilder.setFinal(true);
                irFieldBuilder.setStatic(true);
                DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
                Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
                irFieldBuilder.setVisibility(PACKAGE_VISIBILITY);
                irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
                irFieldBuildField.setParent(singleton);
                IrField irFieldPutIfAbsent = concurrentHashMap.putIfAbsent(singleton, irFieldBuildField);
                if (irFieldPutIfAbsent != null) {
                    irFieldBuildField = irFieldPutIfAbsent;
                }
            }
            Intrinsics.checkNotNullExpressionValue(irFieldBuildField, "getOrPut(...)");
            return irFieldBuildField;
        }
        return getFieldForObjectInstance(singleton);
    }
}
