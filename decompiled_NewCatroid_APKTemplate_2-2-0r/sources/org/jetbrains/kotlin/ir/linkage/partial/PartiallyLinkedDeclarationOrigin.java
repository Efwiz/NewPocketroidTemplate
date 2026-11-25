package org.jetbrains.kotlin.ir.linkage.partial;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;

/* JADX INFO: compiled from: PartiallyLinkedDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartiallyLinkedDeclarationOrigin;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Ljava/lang/String;I)V", "MISSING_DECLARATION", "UNIMPLEMENTED_ABSTRACT_CALLABLE_MEMBER", "AMBIGUOUS_NON_OVERRIDDEN_CALLABLE_MEMBER", "AUXILIARY_GENERATED_DECLARATION", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum PartiallyLinkedDeclarationOrigin implements IrDeclarationOrigin {
    MISSING_DECLARATION,
    UNIMPLEMENTED_ABSTRACT_CALLABLE_MEMBER,
    AMBIGUOUS_NON_OVERRIDDEN_CALLABLE_MEMBER,
    AUXILIARY_GENERATED_DECLARATION;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<PartiallyLinkedDeclarationOrigin> getEntries() {
        return $ENTRIES;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
    public /* bridge */ /* synthetic */ String getName() {
        return name();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
    /* JADX INFO: renamed from: isSynthetic */
    public boolean getIsSynthetic() {
        return IrDeclarationOrigin.DefaultImpls.isSynthetic(this);
    }
}
