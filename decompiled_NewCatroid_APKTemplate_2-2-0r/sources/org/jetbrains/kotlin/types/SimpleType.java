package org.jetbrains.kotlin.types;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;

/* JADX INFO: compiled from: KotlinType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH'J\u0010\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/types/SimpleType;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "()V", "makeNullableAsSpecified", "newNullability", "", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceAttributes", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SimpleType extends UnwrappedType implements SimpleTypeMarker, TypeArgumentListMarker {
    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public abstract SimpleType makeNullableAsSpecified(boolean newNullability);

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public abstract SimpleType refine(KotlinTypeRefiner kotlinTypeRefiner);

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public abstract SimpleType replaceAttributes(TypeAttributes newAttributes);

    public SimpleType() {
        super(null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<AnnotationDescriptor> it = getAnnotations().iterator();
        while (it.hasNext()) {
            StringsKt.append(sb, "[", DescriptorRenderer.renderAnnotation$default(DescriptorRenderer.DEBUG_TEXT, it.next(), null, 2, null), "] ");
        }
        sb.append(getConstructor());
        if (!getArguments().isEmpty()) {
            CollectionsKt.joinTo(getArguments(), sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : "<", (112 & 8) != 0 ? "" : ">", (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
        }
        if (getIsMarkedNullable()) {
            sb.append(TypeDescription.Generic.OfWildcardType.SYMBOL);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
