package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;

/* JADX INFO: compiled from: LateinitLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0005H\u0002\u001a&\u0010\b\u001a\u00020\t*\u00020\t2\u0014\b\u0004\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u000bH\u0086\bø\u0001\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\f"}, d2 = {"isRealLateinit", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Z", "buildOrGetNullableField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "originalField", "replaceTailExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "transform", "Lkotlin/Function1;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LateinitLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrField buildOrGetNullableField(final CommonBackendContext commonBackendContext, final IrField irField) {
        return IrTypePredicatesKt.isMarkedNullable(irField.getType()) ? irField : (IrField) MappingsKt.getOrPut(commonBackendContext.getMapping().getLateInitFieldToNullableField(), irField, new Function0<IrField>() { // from class: org.jetbrains.kotlin.backend.common.lower.LateinitLoweringKt.buildOrGetNullableField.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrField invoke() {
                IrFactory irFactory = commonBackendContext.getIrFactory();
                IrField irField2 = irField;
                IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                irFieldBuilder.updateFrom(irField2);
                irFieldBuilder.setType(IrTypesKt.makeNullable(irField2.getType()));
                irFieldBuilder.setName(irField2.getName());
                IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
                IrField irField3 = irField;
                irFieldBuildField.setParent(irField3.getParent());
                irFieldBuildField.setCorrespondingPropertySymbol(irField3.getCorrespondingPropertySymbol());
                irFieldBuildField.setAnnotations(irField3.getAnnotations());
                return irFieldBuildField;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isRealLateinit(IrProperty irProperty) {
        return irProperty.getIsLateinit() && !irProperty.getIsFakeOverride();
    }

    public static final IrExpression replaceTailExpression(IrExpression irExpression, Function1<? super IrExpression, ? extends IrExpression> transform) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(transform, "transform");
        IrContainerExpression irContainerExpression = null;
        IrExpression irExpression2 = irExpression;
        while (irExpression2 instanceof IrContainerExpression) {
            irContainerExpression = (IrContainerExpression) irExpression2;
            Object objLast = CollectionsKt.last((List<? extends Object>) irContainerExpression.getStatements());
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
            irExpression2 = (IrExpression) objLast;
        }
        IrExpression irExpressionInvoke = transform.invoke(irExpression2);
        if (irContainerExpression == null) {
            return irExpressionInvoke;
        }
        irContainerExpression.getStatements().set(irContainerExpression.getStatements().size() - 1, irExpressionInvoke);
        return irExpression;
    }
}
