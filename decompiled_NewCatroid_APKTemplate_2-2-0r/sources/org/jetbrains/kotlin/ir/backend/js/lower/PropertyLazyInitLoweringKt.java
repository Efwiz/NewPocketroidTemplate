package org.jetbrains.kotlin.ir.backend.js.lower;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;

/* JADX INFO: compiled from: PropertyLazyInitLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002\u001a*\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000fH\u0002\u001a\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\fH\u0002\u001a\u0014\u0010\u0019\u001a\u00020\t*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\f\u0010\u001a\u001a\u00020\t*\u00020\u0004H\u0002\u001a)\u0010\u001b\u001a\u0004\u0018\u00010\u0004*\u00020\u00052\u0019\u0010\u001c\u001a\u0015\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u001d¢\u0006\u0002\b\u001eH\u0002\u001a4\u0010\u001f\u001a\u0004\u0018\u0001H \"\u0004\b\u0000\u0010 *\u00020\u00052\u0019\u0010\u001c\u001a\u0015\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 0\u001d¢\u0006\u0002\b\u001eH\u0002¢\u0006\u0002\u0010!\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\""}, d2 = {"compatibleOrigins", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "correspondingProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getCorrespondingProperty", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "allFieldsInFilePure", "", "fieldToInitializer", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "calculateFieldToExpression", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "declarations", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "createIrGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "field", "createIrSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "expression", "isCompatibleDeclaration", "isForLazyInit", "propertyWithPersistentSafe", "transform", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "withPersistentSafe", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertyLazyInitLoweringKt {
    private static final List<IrDeclarationOriginImpl> compatibleOrigins = CollectionsKt.listOf((Object[]) new IrDeclarationOriginImpl[]{IrDeclarationOrigin.DEFINED.INSTANCE, IrDeclarationOrigin.DELEGATED_PROPERTY_ACCESSOR.INSTANCE, IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE, IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE, IrDeclarationOrigin.PROPERTY_BACKING_FIELD.INSTANCE});

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrGetField createIrGetField(IrField irField) {
        return JsIrBuilder.INSTANCE.buildGetField(irField.getSymbol(), (124 & 2) != 0 ? null : null, (124 & 4) != 0 ? null : null, (124 & 8) == 0 ? null : null, (124 & 16) != 0 ? -1 : 0, (124 & 32) == 0 ? 0 : -1, (124 & 64) != 0 ? JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSetField createIrSetField(IrField irField, IrExpression irExpression) {
        return JsIrBuilder.buildSetField$default(JsIrBuilder.INSTANCE, irField.getSymbol(), null, irExpression, irExpression.getType(), null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean allFieldsInFilePure(Collection<? extends IrExpression> collection) {
        Collection<? extends IrExpression> collection2 = collection;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (!IrUtilsKt.isPure$default((IrExpression) it.next(), true, false, null, 6, null)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<IrField, IrExpression> calculateFieldToExpression(Collection<? extends IrDeclaration> collection, final JsCommonBackendContext jsCommonBackendContext) {
        return MapsKt.toMap(SequencesKt.map(SequencesKt.filter(SequencesKt.mapNotNull(SequencesKt.distinct(SequencesKt.filter(SequencesKt.filterNotNull(SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence(collection), new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt.calculateFieldToExpression.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(PropertyLazyInitLoweringKt.isCompatibleDeclaration(it, jsCommonBackendContext));
            }
        }), new Function1<IrDeclaration, IrProperty>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt.calculateFieldToExpression.2
            @Override // kotlin.jvm.functions.Function1
            public final IrProperty invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return PropertyLazyInitLoweringKt.getCorrespondingProperty(it);
            }
        })), new Function1<IrProperty, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt.calculateFieldToExpression.3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrProperty it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(PropertyLazyInitLoweringKt.isForLazyInit(it));
            }
        })), new Function1<IrProperty, IrField>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt.calculateFieldToExpression.4
            @Override // kotlin.jvm.functions.Function1
            public final IrField invoke(IrProperty it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getBackingField();
            }
        }), new Function1<IrField, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt.calculateFieldToExpression.5
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getInitializer() != null);
            }
        }), new Function1<IrField, Pair<? extends IrField, ? extends IrExpression>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt.calculateFieldToExpression.6
            @Override // kotlin.jvm.functions.Function1
            public final Pair<IrField, IrExpression> invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrExpressionBody initializer = it.getInitializer();
                Intrinsics.checkNotNull(initializer);
                return TuplesKt.to(it, initializer.getExpression());
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isForLazyInit(IrProperty irProperty) {
        return org.jetbrains.kotlin.ir.util.IrUtilsKt.isTopLevel(irProperty) && !irProperty.getIsConst();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrProperty getCorrespondingProperty(final IrDeclaration irDeclaration) {
        boolean z = irDeclaration instanceof IrSimpleFunction;
        if (!z && !(irDeclaration instanceof IrField) && !(irDeclaration instanceof IrProperty)) {
            return null;
        }
        if (irDeclaration instanceof IrProperty) {
            return (IrProperty) irDeclaration;
        }
        if (z) {
            return propertyWithPersistentSafe(irDeclaration, new Function1<IrDeclaration, IrProperty>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt$correspondingProperty$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrProperty invoke(IrDeclaration propertyWithPersistentSafe) {
                    Intrinsics.checkNotNullParameter(propertyWithPersistentSafe, "$this$propertyWithPersistentSafe");
                    IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irDeclaration).getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol != null) {
                        return correspondingPropertySymbol.getOwner();
                    }
                    return null;
                }
            });
        }
        if (irDeclaration instanceof IrField) {
            return propertyWithPersistentSafe(irDeclaration, new Function1<IrDeclaration, IrProperty>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt$correspondingProperty$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrProperty invoke(IrDeclaration propertyWithPersistentSafe) {
                    Intrinsics.checkNotNullParameter(propertyWithPersistentSafe, "$this$propertyWithPersistentSafe");
                    IrPropertySymbol correspondingPropertySymbol = ((IrField) irDeclaration).getCorrespondingPropertySymbol();
                    if (correspondingPropertySymbol != null) {
                        return correspondingPropertySymbol.getOwner();
                    }
                    return null;
                }
            });
        }
        CompilationExceptionKt.compilationException("Can be only IrProperty, IrSimpleFunction or IrField", irDeclaration);
        throw null;
    }

    private static final IrProperty propertyWithPersistentSafe(IrDeclaration irDeclaration, Function1<? super IrDeclaration, ? extends IrProperty> function1) {
        return (IrProperty) withPersistentSafe(irDeclaration, function1);
    }

    private static final <T> T withPersistentSafe(IrDeclaration irDeclaration, Function1<? super IrDeclaration, ? extends T> function1) {
        return function1.invoke(irDeclaration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isCompatibleDeclaration(IrDeclaration irDeclaration, JsCommonBackendContext jsCommonBackendContext) {
        IrProperty correspondingProperty = getCorrespondingProperty(irDeclaration);
        return (correspondingProperty == null || !(correspondingProperty.getIsExternal() || !isForLazyInit(correspondingProperty) || org.jetbrains.kotlin.ir.util.IrUtilsKt.hasAnnotation(correspondingProperty, jsCommonBackendContext.getPropertyLazyInitialization().getEagerInitialization()))) && Intrinsics.areEqual(withPersistentSafe(irDeclaration, new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLoweringKt.isCompatibleDeclaration.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrDeclaration withPersistentSafe) {
                Intrinsics.checkNotNullParameter(withPersistentSafe, "$this$withPersistentSafe");
                return Boolean.valueOf(CollectionsKt.contains(PropertyLazyInitLoweringKt.compatibleOrigins, withPersistentSafe.getOrigin()));
            }
        }), (Object) true);
    }
}
