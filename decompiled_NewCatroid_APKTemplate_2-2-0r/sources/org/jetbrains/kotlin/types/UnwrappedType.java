package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: KotlinType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bH'J\u0010\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH&J\u0006\u0010\f\u001a\u00020\u0000\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "()V", "makeNullableAsSpecified", "newNullability", "", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceAttributes", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "unwrap", "Lorg/jetbrains/kotlin/types/FlexibleType;", "Lorg/jetbrains/kotlin/types/SimpleType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class UnwrappedType extends KotlinType {
    public /* synthetic */ UnwrappedType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract UnwrappedType makeNullableAsSpecified(boolean newNullability);

    @Override // org.jetbrains.kotlin.types.KotlinType
    public abstract UnwrappedType refine(KotlinTypeRefiner kotlinTypeRefiner);

    public abstract UnwrappedType replaceAttributes(TypeAttributes newAttributes);

    @Override // org.jetbrains.kotlin.types.KotlinType
    public final UnwrappedType unwrap() {
        return this;
    }

    private UnwrappedType() {
        super(null);
    }
}
