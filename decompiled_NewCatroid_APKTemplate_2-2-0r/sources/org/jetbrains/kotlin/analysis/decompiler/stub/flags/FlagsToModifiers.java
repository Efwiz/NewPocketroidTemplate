package org.jetbrains.kotlin.analysis.decompiler.stub.flags;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;

/* JADX INFO: compiled from: flags.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/flags/FlagsToModifiers;", "", "()V", "getModifiers", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", OverlayablePolicy.NAME_flags, "", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FlagsToModifiers {
    public abstract KtModifierKeywordToken getModifiers(int flags);
}
