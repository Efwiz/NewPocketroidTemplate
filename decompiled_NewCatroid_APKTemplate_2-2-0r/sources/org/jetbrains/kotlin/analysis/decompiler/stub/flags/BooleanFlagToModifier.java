package org.jetbrains.kotlin.analysis.decompiler.stub.flags;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: flags.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/flags/BooleanFlagToModifier;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/flags/FlagsToModifiers;", "flagField", "Lorg/jetbrains/kotlin/metadata/deserialization/Flags$BooleanFlagField;", "ktModifierKeywordToken", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "(Lorg/jetbrains/kotlin/metadata/deserialization/Flags$BooleanFlagField;Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;)V", "getModifiers", OverlayablePolicy.NAME_flags, "", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class BooleanFlagToModifier extends FlagsToModifiers {
    private final Flags.BooleanFlagField flagField;
    private final KtModifierKeywordToken ktModifierKeywordToken;

    public BooleanFlagToModifier(Flags.BooleanFlagField flagField, KtModifierKeywordToken ktModifierKeywordToken) {
        Intrinsics.checkNotNullParameter(flagField, "flagField");
        Intrinsics.checkNotNullParameter(ktModifierKeywordToken, "ktModifierKeywordToken");
        this.flagField = flagField;
        this.ktModifierKeywordToken = ktModifierKeywordToken;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.flags.FlagsToModifiers
    public KtModifierKeywordToken getModifiers(int flags) {
        Boolean bool = this.flagField.get(flags);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        if (bool.booleanValue()) {
            return this.ktModifierKeywordToken;
        }
        return null;
    }
}
