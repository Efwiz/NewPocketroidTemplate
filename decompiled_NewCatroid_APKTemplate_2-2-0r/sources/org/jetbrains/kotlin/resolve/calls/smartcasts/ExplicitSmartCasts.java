package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ExplicitSmartCasts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0011\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bH¦\u0002J\u0014\u0010\t\u001a\u0004\u0018\u00010\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ExplicitSmartCasts;", "", "defaultType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDefaultType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "plus", "smartCast", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SingleSmartCast;", ModuleXmlParser.TYPE, "call", "Lorg/jetbrains/kotlin/psi/Call;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ExplicitSmartCasts {
    KotlinType getDefaultType();

    ExplicitSmartCasts plus(SingleSmartCast smartCast);

    KotlinType type(Call call);
}
