package org.jetbrains.kotlin.ir.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorImpl;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.ByteValue;
import org.jetbrains.kotlin.resolve.constants.CharValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.DoubleValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.FloatValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.ShortValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0003H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\f\u0010\u0018\u001a\u00020\u0019*\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001c*\u00020\u001dH\u0002J\f\u0010\u001e\u001a\u00020\u001f*\u00020 H\u0004R\u001b\u0010\u0006\u001a\u00020\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\r¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotations$delegate", "Lkotlin/Lazy;", "getOwner", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getContainingDeclaration", "hashCode", "", "toString", "", "toAnnotationDescriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "toConstantValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Lorg/jetbrains/kotlin/ir/IrElement;", "toIrBasedKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBasedDeclarationDescriptor<T extends IrDeclaration> implements DeclarationDescriptor {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final T owner;

    public IrBasedDeclarationDescriptor(T owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.owner = owner;
        this.annotations = LazyKt.lazy(new Function0<Annotations>(this) { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor$annotations$2
            final /* synthetic */ IrBasedDeclarationDescriptor<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Annotations invoke() {
                List<IrConstructorCall> annotations;
                IrDeclaration owner2 = this.this$0.getOwner();
                IrDeclaration irDeclaration = owner2 instanceof IrAnnotationContainer ? owner2 : null;
                if (irDeclaration == null || (annotations = irDeclaration.getAnnotations()) == null) {
                    return Annotations.INSTANCE.getEMPTY();
                }
                Annotations.Companion companion = Annotations.INSTANCE;
                List<IrConstructorCall> list = annotations;
                IrBasedDeclarationDescriptor<T> irBasedDeclarationDescriptor = this.this$0;
                ArrayList arrayList = new ArrayList(list.size());
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(irBasedDeclarationDescriptor.toAnnotationDescriptor((IrConstructorCall) it.next()));
                }
                return companion.create(CollectionsKt.compactIfPossible(arrayList));
            }
        });
    }

    public final T getOwner() {
        return this.owner;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return (Annotations) this.annotations.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AnnotationDescriptor toAnnotationDescriptor(IrConstructorCall irConstructorCall) {
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructorCall.getSymbol().getOwner());
        if (Intrinsics.areEqual(parentAsClass.getSymbol().getDescriptor(), ErrorUtils.INSTANCE.getErrorClass())) {
            return new AnnotationDescriptorImpl(IrTypesKt.toKotlinType(irConstructorCall.getType()), MapsKt.emptyMap(), irConstructorCall.getSource());
        }
        IrUtilsKt.isAnnotationClass(parentAsClass);
        KotlinType irBasedKotlinType = toIrBasedKotlinType(IrUtilsKt.getDefaultType(parentAsClass));
        List<IrValueParameter> valueParameters = irConstructorCall.getSymbol().getOwner().getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        for (IrValueParameter irValueParameter : valueParameters) {
            arrayList.add(TuplesKt.to(irValueParameter.getName(), irConstructorCall.getValueArgument(irValueParameter.getIndex())));
        }
        List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : listCompactIfPossible) {
            if (((Pair) obj).getSecond() != null) {
                arrayList2.add(obj);
            }
        }
        ArrayList<Pair> arrayList3 = arrayList2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kotlin.collections.CollectionsKt.collectionSizeOrDefault(arrayList3, 10)), 16));
        for (Pair pair : arrayList3) {
            Object first = pair.getFirst();
            Object second = pair.getSecond();
            Intrinsics.checkNotNull(second);
            Pair pair2 = TuplesKt.to(first, toConstantValue((IrElement) second));
            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
        }
        return new AnnotationDescriptorImpl(irBasedKotlinType, linkedHashMap, irConstructorCall.getSource());
    }

    private final ConstantValue<?> toConstantValue(IrElement irElement) {
        if (irElement instanceof IrConst) {
            IrConst irConst = (IrConst) irElement;
            IrConstKind kind = irConst.getKind();
            if (Intrinsics.areEqual(kind, IrConstKind.Null.INSTANCE)) {
                return new NullValue();
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Boolean.INSTANCE)) {
                Object value = irConst.getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
                return new BooleanValue(((Boolean) value).booleanValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Char.INSTANCE)) {
                Object value2 = irConst.getValue();
                Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Char");
                return new CharValue(((Character) value2).charValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
                Object value3 = irConst.getValue();
                Intrinsics.checkNotNull(value3, "null cannot be cast to non-null type kotlin.Byte");
                return new ByteValue(((Byte) value3).byteValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
                Object value4 = irConst.getValue();
                Intrinsics.checkNotNull(value4, "null cannot be cast to non-null type kotlin.Short");
                return new ShortValue(((Short) value4).shortValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
                Object value5 = irConst.getValue();
                Intrinsics.checkNotNull(value5, "null cannot be cast to non-null type kotlin.Int");
                return new IntValue(((Integer) value5).intValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
                Object value6 = irConst.getValue();
                Intrinsics.checkNotNull(value6, "null cannot be cast to non-null type kotlin.Long");
                return new LongValue(((Long) value6).longValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE)) {
                Object value7 = irConst.getValue();
                Intrinsics.checkNotNull(value7, "null cannot be cast to non-null type kotlin.String");
                return new StringValue((String) value7);
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Float.INSTANCE)) {
                Object value8 = irConst.getValue();
                Intrinsics.checkNotNull(value8, "null cannot be cast to non-null type kotlin.Float");
                return new FloatValue(((Float) value8).floatValue());
            }
            if (!Intrinsics.areEqual(kind, IrConstKind.Double.INSTANCE)) {
                throw new NoWhenBranchMatchedException();
            }
            Object value9 = irConst.getValue();
            Intrinsics.checkNotNull(value9, "null cannot be cast to non-null type kotlin.Double");
            return new DoubleValue(((Double) value9).doubleValue());
        }
        if (irElement instanceof IrVararg) {
            List<IrVarargElement> elements = ((IrVararg) irElement).getElements();
            ArrayList arrayList = new ArrayList(elements.size());
            for (IrVarargElement irVarargElement : elements) {
                if (irVarargElement instanceof IrSpreadElement) {
                    throw new IllegalStateException((irVarargElement + " is not expected").toString());
                }
                arrayList.add(toConstantValue(irVarargElement));
            }
            return new ArrayValue(CollectionsKt.compactIfPossible(arrayList), new Function1<ModuleDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor.toConstantValue.1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(ModuleDescriptor moduleDescriptor) {
                    Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
                    SimpleType defaultType = moduleDescriptor.getBuiltIns().getArray().getDefaultType();
                    Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                    return defaultType;
                }
            });
        }
        if (irElement instanceof IrGetEnumValue) {
            IrGetEnumValue irGetEnumValue = (IrGetEnumValue) irElement;
            ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) IrBasedDescriptorsKt.toIrBasedDescriptor(IrUtilsKt.getParentAsClass(irGetEnumValue.getSymbol().getOwner())));
            Intrinsics.checkNotNull(classId);
            return new EnumValue(classId, irGetEnumValue.getSymbol().getOwner().getName());
        }
        if (!(irElement instanceof IrClassReference)) {
            if (irElement instanceof IrConstructorCall) {
                return new AnnotationValue(toAnnotationDescriptor((IrConstructorCall) irElement));
            }
            throw new IllegalStateException((irElement + " is not expected: " + DumpIrTreeKt.dump$default(irElement, null, 1, null)).toString());
        }
        IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(((IrClassReference) irElement).getClassType()).getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        ClassId classId2 = DescriptorUtilsKt.getClassId((ClassifierDescriptor) IrBasedDescriptorsKt.toIrBasedDescriptor((IrClass) owner));
        Intrinsics.checkNotNull(classId2);
        return new KClassValue(classId2, 0);
    }

    protected final KotlinType toIrBasedKotlinType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrBasedDescriptorsKt.toIrBasedKotlinType(irType, AdditionalIrUtilsKt.getModule(this.owner).getBuiltIns());
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public DeclarationDescriptor getDeclarationDescriptor() {
        return IrBasedDescriptorsKt.getContainingDeclaration(this.owner);
    }

    public boolean equals(Object other) {
        return (other instanceof IrBasedDeclarationDescriptor) && Intrinsics.areEqual(this.owner, ((IrBasedDeclarationDescriptor) other).owner);
    }

    public int hashCode() {
        return this.owner.hashCode();
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + RenderIrElementKt.render$default(this.owner, (DumpIrTreeOptions) null, 1, (Object) null);
    }
}
