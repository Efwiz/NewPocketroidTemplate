package org.jetbrains.kotlin.codegen.inline;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: defaultMethodUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ExtractedDefaultLambda;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "capturedArgs", "", TypedValues.CycleType.S_WAVE_OFFSET, "", "needReification", "", "(Lorg/jetbrains/org/objectweb/asm/Type;[Lorg/jetbrains/org/objectweb/asm/Type;IZ)V", "getCapturedArgs", "()[Lorg/jetbrains/org/objectweb/asm/Type;", "[Lorg/jetbrains/org/objectweb/asm/Type;", "getNeedReification", "()Z", "getOffset", "()I", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExtractedDefaultLambda {
    private final Type[] capturedArgs;
    private final boolean needReification;
    private final int offset;
    private final Type type;

    public ExtractedDefaultLambda(Type type, Type[] capturedArgs, int i, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(capturedArgs, "capturedArgs");
        this.type = type;
        this.capturedArgs = capturedArgs;
        this.offset = i;
        this.needReification = z;
    }

    public final Type[] getCapturedArgs() {
        return this.capturedArgs;
    }

    public final boolean getNeedReification() {
        return this.needReification;
    }

    public final int getOffset() {
        return this.offset;
    }

    public final Type getType() {
        return this.type;
    }
}
