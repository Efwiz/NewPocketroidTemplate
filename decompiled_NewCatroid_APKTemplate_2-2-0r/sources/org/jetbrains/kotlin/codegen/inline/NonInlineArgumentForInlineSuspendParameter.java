package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: LambdaInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/NonInlineArgumentForInlineSuspendParameter;", "", "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "(Ljava/lang/String;I)V", "INLINE_LAMBDA_AS_VARIABLE", "OTHER", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum NonInlineArgumentForInlineSuspendParameter implements FunctionalArgument {
    INLINE_LAMBDA_AS_VARIABLE,
    OTHER;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<NonInlineArgumentForInlineSuspendParameter> getEntries() {
        return $ENTRIES;
    }
}
