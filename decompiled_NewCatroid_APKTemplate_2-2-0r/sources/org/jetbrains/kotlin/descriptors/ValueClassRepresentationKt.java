package org.jetbrains.kotlin.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: ValueClassRepresentation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a8\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002H\u00020\b0\u0007\u001a.\u0010\n\u001a\u00020\u000b\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002H\u00020\b0\u0007¨\u0006\f"}, d2 = {"createValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Type", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "context", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "fields", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/Name;", "valueClassLoweringKind", "Lorg/jetbrains/kotlin/descriptors/ValueClassKind;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ValueClassRepresentationKt {

    /* JADX INFO: compiled from: ValueClassRepresentation.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ValueClassKind.values().length];
            try {
                iArr[ValueClassKind.Inline.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ValueClassKind.MultiField.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final <Type extends SimpleTypeMarker> ValueClassKind valueClassLoweringKind(TypeSystemCommonBackendContext typeSystemCommonBackendContext, List<? extends Pair<Name, ? extends Type>> fields) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(fields, "fields");
        if (fields.size() > 1) {
            return ValueClassKind.MultiField;
        }
        if (fields.isEmpty()) {
            throw new IllegalStateException("Value classes cannot have 0 fields".toString());
        }
        SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) ((Pair) CollectionsKt.single((List) fields)).getSecond();
        if (!typeSystemCommonBackendContext.isNullableType(simpleTypeMarker) && typeSystemCommonBackendContext.isMultiFieldValueClass(typeSystemCommonBackendContext.typeConstructor(simpleTypeMarker))) {
            return ValueClassKind.MultiField;
        }
        return ValueClassKind.Inline;
    }

    public static final <Type extends SimpleTypeMarker> ValueClassRepresentation<Type> createValueClassRepresentation(TypeSystemCommonBackendContext context, List<? extends Pair<Name, ? extends Type>> fields) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fields, "fields");
        int i = WhenMappings.$EnumSwitchMapping$0[valueClassLoweringKind(context, fields).ordinal()];
        if (i == 1) {
            return new InlineClassRepresentation(fields.get(0).getFirst(), fields.get(0).getSecond());
        }
        if (i == 2) {
            return new MultiFieldValueClassRepresentation(fields);
        }
        throw new NoWhenBranchMatchedException();
    }
}
