package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: ImplicitlyExportedDeclarationsMarkingLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\tH\u0016J\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f*\u00020\rH\u0002J\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f*\u00020\u000eH\u0002J\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f*\u00020\u000fH\u0002J\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f*\u00020\u0010H\u0002J\f\u0010\u0011\u001a\u00020\u0012*\u00020\tH\u0002J\f\u0010\u0013\u001a\u00020\u0006*\u00020\tH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ImplicitlyExportedDeclarationsMarkingLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "strictImplicitExport", "", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "collectImplicitlyExportedDeclarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "markWithJsImplicitExport", "", "shouldBeMarkedWithImplicitExport", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImplicitlyExportedDeclarationsMarkingLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;
    private final boolean strictImplicitExport;

    public ImplicitlyExportedDeclarationsMarkingLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.strictImplicitExport = context.getConfiguration().getBoolean(JSConfigurationKeys.GENERATE_STRICT_IMPLICIT_EXPORT);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Set<IrDeclaration> setCollectImplicitlyExportedDeclarations;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.strictImplicitExport && ExportModelGeneratorKt.isExported(declaration, this.context)) {
            if (declaration instanceof IrFunction) {
                setCollectImplicitlyExportedDeclarations = collectImplicitlyExportedDeclarations((IrFunction) declaration);
            } else if (declaration instanceof IrClass) {
                setCollectImplicitlyExportedDeclarations = collectImplicitlyExportedDeclarations((IrClass) declaration);
            } else {
                setCollectImplicitlyExportedDeclarations = declaration instanceof IrProperty ? collectImplicitlyExportedDeclarations((IrProperty) declaration) : SetsKt.emptySet();
            }
            Iterator<T> it = setCollectImplicitlyExportedDeclarations.iterator();
            while (it.hasNext()) {
                markWithJsImplicitExport((IrDeclaration) it.next());
            }
        }
        return null;
    }

    private final Set<IrDeclaration> collectImplicitlyExportedDeclarations(IrClass irClass) {
        return SequencesKt.toSet(SequencesKt.flatMapIterable(SequencesKt.distinct(SequencesKt.flatMapIterable(CollectionsKt.asSequence(irClass.getTypeParameters()), new Function1<IrTypeParameter, List<? extends IrType>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ImplicitlyExportedDeclarationsMarkingLowering.collectImplicitlyExportedDeclarations.1
            @Override // kotlin.jvm.functions.Function1
            public final List<IrType> invoke(IrTypeParameter it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getSuperTypes();
            }
        })), new Function1<IrType, Set<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ImplicitlyExportedDeclarationsMarkingLowering.collectImplicitlyExportedDeclarations.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Set<IrDeclaration> invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ImplicitlyExportedDeclarationsMarkingLowering.this.collectImplicitlyExportedDeclarations(it);
            }
        }));
    }

    private final Set<IrDeclaration> collectImplicitlyExportedDeclarations(IrFunction irFunction) {
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        setCreateSetBuilder.add(irFunction.getReturnType());
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrValueParameter) it.next()).getType());
        }
        setCreateSetBuilder.addAll(arrayList);
        List<IrTypeParameter> typeParameters = irFunction.getTypeParameters();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = typeParameters.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, ((IrTypeParameter) it2.next()).getSuperTypes());
        }
        setCreateSetBuilder.addAll(arrayList2);
        Set setBuild = SetsKt.build(setCreateSetBuilder);
        ArrayList arrayList3 = new ArrayList();
        Iterator it3 = setBuild.iterator();
        while (it3.hasNext()) {
            CollectionsKt.addAll(arrayList3, collectImplicitlyExportedDeclarations((IrType) it3.next()));
        }
        return CollectionsKt.toSet(arrayList3);
    }

    private final Set<IrDeclaration> collectImplicitlyExportedDeclarations(IrProperty irProperty) {
        Set<IrDeclaration> setEmptySet;
        Set<IrDeclaration> setEmptySet2;
        Set<IrDeclaration> setEmptySet3;
        IrType type;
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter == null || (setEmptySet = collectImplicitlyExportedDeclarations(getter)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        IrSimpleFunction setter = irProperty.getSetter();
        if (setter == null || (setEmptySet2 = collectImplicitlyExportedDeclarations(setter)) == null) {
            setEmptySet2 = SetsKt.emptySet();
        }
        IrField backingField = irProperty.getBackingField();
        if (backingField == null || (type = backingField.getType()) == null || (setEmptySet3 = collectImplicitlyExportedDeclarations(type)) == null) {
            setEmptySet3 = SetsKt.emptySet();
        }
        return SetsKt.plus(SetsKt.plus((Set) setEmptySet, (Iterable) setEmptySet2), (Iterable) setEmptySet3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<IrDeclaration> collectImplicitlyExportedDeclarations(IrType irType) {
        if ((irType instanceof IrDynamicType) || !(irType instanceof IrSimpleType)) {
            return SetsKt.emptySet();
        }
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(irType);
        Intrinsics.checkNotNull(irTypeMakeNotNull, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleType irSimpleType = (IrSimpleType) irTypeMakeNotNull;
        IrClassifierSymbol classifier = irSimpleType.getClassifier();
        IrSimpleType irSimpleType2 = irSimpleType;
        if (IrTypePredicatesKt.isPrimitiveType$default(irSimpleType2, false, 1, null) || IrTypeUtilsKt.isPrimitiveArray(irSimpleType2) || IrTypePredicatesKt.isAny(irSimpleType2) || IrTypePredicatesKt.isUnit(irSimpleType2)) {
            return SetsKt.emptySet();
        }
        if (!(classifier instanceof IrTypeParameterSymbol)) {
            if (!(classifier instanceof IrClassSymbol)) {
                return SetsKt.emptySet();
            }
            IrClass owner = ((IrClassSymbol) classifier).getOwner();
            return SetsKt.setOfNotNull(shouldBeMarkedWithImplicitExport(owner) ? owner : null);
        }
        List<IrType> superTypes = ((IrTypeParameterSymbol) classifier).getOwner().getSuperTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, collectImplicitlyExportedDeclarations((IrType) it.next()));
        }
        return CollectionsKt.toSet(arrayList);
    }

    private final boolean shouldBeMarkedWithImplicitExport(IrDeclaration irDeclaration) {
        return (!(irDeclaration instanceof IrClass) || ((IrClass) irDeclaration).getIsExternal() || ExportModelGeneratorKt.isExported(irDeclaration, this.context) || AnnotationUtilsKt.isJsImplicitExport(irDeclaration)) ? false : true;
    }

    private final void markWithJsImplicitExport(IrDeclaration irDeclaration) {
        irDeclaration.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irDeclaration.getAnnotations(), JsIrBuilder.buildConstructorCall$default(JsIrBuilder.INSTANCE, (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(this.context.getIntrinsics().getJsImplicitExportAnnotationSymbol())), null, null, null, 14, null)));
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclaration);
        if (parentClassOrNull != null) {
            if (!shouldBeMarkedWithImplicitExport(parentClassOrNull)) {
                parentClassOrNull = null;
            }
            if (parentClassOrNull != null) {
                markWithJsImplicitExport(parentClassOrNull);
            }
        }
    }
}
