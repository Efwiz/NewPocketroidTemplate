package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: EnumClassLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/EnumEntryInstancesBodyLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "correspondingField", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "setCorrespondingField", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "correspondingField$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumEntryInstancesBodyLowering implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(EnumEntryInstancesBodyLowering.class), "correspondingField", "getCorrespondingField(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/ir/declarations/IrField;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: correspondingField$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingField;

    public EnumEntryInstancesBodyLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.correspondingField = context.getMapping().getEnumEntryToCorrespondingField();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final IrField getCorrespondingField(IrEnumEntry irEnumEntry) {
        return (IrField) this.correspondingField.getValue(irEnumEntry, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Object next;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (container instanceof IrConstructor) {
            IrConstructor irConstructor = (IrConstructor) container;
            if (AdditionalIrUtilsKt.getConstructedClass(irConstructor).getKind() == ClassKind.ENUM_ENTRY) {
                IrClass constructedClass = AdditionalIrUtilsKt.getConstructedClass(irConstructor);
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(constructedClass);
                if (IrJsUtilsKt.isInstantiableEnum(parentAsClass)) {
                    Iterator<T> it = parentAsClass.getDeclarations().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if ((next instanceof IrEnumEntry) && ((IrEnumEntry) next).getCorrespondingClass() == constructedClass) {
                            break;
                        }
                    }
                    Intrinsics.checkNotNull(next);
                    IrEnumEntry irEnumEntry = (IrEnumEntry) next;
                    IrBlockBody irBlockBody = (IrBlockBody) irBody;
                    int i = 0;
                    for (IrStatement irStatement : irBlockBody.getStatements()) {
                        if ((irStatement instanceof IrDelegatingConstructorCall) || ((irStatement instanceof IrTypeOperatorCall) && (((IrTypeOperatorCall) irStatement).getArgument() instanceof IrDelegatingConstructorCall))) {
                            List<IrStatement> statements = irBlockBody.getStatements();
                            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irConstructor.getSymbol(), 0, 0, 6, (Object) null);
                            IrField correspondingField = getCorrespondingField(irEnumEntry);
                            Intrinsics.checkNotNull(correspondingField);
                            IrValueParameter thisReceiver = constructedClass.getThisReceiver();
                            Intrinsics.checkNotNull(thisReceiver);
                            statements.add(i + 1, ExpressionHelpersKt.irSetField$default(declarationIrBuilderCreateIrBuilder$default, null, correspondingField, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, thisReceiver), null, 8, null));
                        }
                        i++;
                    }
                    i = -1;
                    List<IrStatement> statements2 = irBlockBody.getStatements();
                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(this.context, irConstructor.getSymbol(), 0, 0, 6, (Object) null);
                    IrField correspondingField2 = getCorrespondingField(irEnumEntry);
                    Intrinsics.checkNotNull(correspondingField2);
                    IrValueParameter thisReceiver2 = constructedClass.getThisReceiver();
                    Intrinsics.checkNotNull(thisReceiver2);
                    statements2.add(i + 1, ExpressionHelpersKt.irSetField$default(declarationIrBuilderCreateIrBuilder$default2, null, correspondingField2, ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default2, thisReceiver2), null, 8, null));
                }
            }
        }
    }
}
