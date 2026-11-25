package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\nH\u0002J\u0018\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR3\u0010\u0011\u001a\u0004\u0018\u00010\n*\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\n8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0016\u0010\u0010\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumEntryInstancesLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "correspondingField", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "correspondingField$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "fieldToEnumEntry", "getFieldToEnumEntry", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "setFieldToEnumEntry", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)V", "fieldToEnumEntry$delegate", "createEnumEntryInstanceVariable", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "enumEntry", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumEntryInstancesLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumEntryInstancesLowering.class), "correspondingField", "getCorrespondingField(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;")), Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumEntryInstancesLowering.class), "fieldToEnumEntry", "getFieldToEnumEntry(Lorg/jetbrains/kotlin/ir/declarations/IrField;)Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: correspondingField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingField;

    /* JADX INFO: renamed from: fieldToEnumEntry$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate fieldToEnumEntry;

    public EnumEntryInstancesLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.correspondingField = context.getMapping().getEnumEntryToCorrespondingField();
        this.fieldToEnumEntry = context.getMapping().getFieldToEnumEntry();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final void setCorrespondingField(IrEnumEntry irEnumEntry, IrField irField) {
        this.correspondingField.setValue(irEnumEntry, $$delegatedProperties[0], irField);
    }

    private final void setFieldToEnumEntry(IrField irField, IrEnumEntry irEnumEntry) {
        this.fieldToEnumEntry.setValue(irField, $$delegatedProperties[1], irEnumEntry);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrEnumEntry)) {
            return null;
        }
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(declaration);
        if (IrJsUtilsKt.isInstantiableEnum(parentAsClass)) {
            return CollectionsKt.listOf((Object[]) new IrDeclaration[]{declaration, createEnumEntryInstanceVariable(parentAsClass, (IrEnumEntry) declaration)});
        }
        return null;
    }

    private final IrField createEnumEntryInstanceVariable(IrClass irClass, IrEnumEntry enumEntry) {
        String identifier = irClass.getName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        IrFactory irFactory = this.context.getIrFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        Name nameIdentifier = Name.identifier(identifier + '_' + enumEntry.getName().getIdentifier() + Namer.OBJECT_INSTANCE_VAR_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier);
        irFieldBuilder.setType(IrTypesKt.makeNullable(EnumClassLoweringKt.getType(enumEntry, irClass)));
        irFieldBuilder.setOrigin(IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRY.INSTANCE);
        irFieldBuilder.setStatic(true);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        irFieldBuildField.setInitializer(null);
        setCorrespondingField(enumEntry, irFieldBuildField);
        setFieldToEnumEntry(irFieldBuildField, enumEntry);
        return irFieldBuildField;
    }
}
