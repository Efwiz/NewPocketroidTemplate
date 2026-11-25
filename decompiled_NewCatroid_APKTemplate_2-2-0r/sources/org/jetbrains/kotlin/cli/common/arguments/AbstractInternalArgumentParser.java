package org.jetbrains.kotlin.cli.common.arguments;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.cli.common.arguments.InternalArgument;

/* JADX INFO: compiled from: InternalCompilerArgument.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u0016J\u001f\u0010\u000b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u000eJ'\u0010\u000f\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH&¢\u0006\u0002\u0010\u0012R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/AbstractInternalArgumentParser;", "A", "Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgumentParser;", "familyName", "", "(Ljava/lang/String;)V", "wholePrefix", "canParse", "", ParameterDescription.NAME_PREFIX, "parseInternalArgument", "errors", "Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;)Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "parseTail", "tail", "wholeArgument", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;)Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractInternalArgumentParser<A extends InternalArgument> implements InternalArgumentParser<A> {
    private final String wholePrefix;

    public abstract A parseTail(String tail, String wholeArgument, ArgumentParseErrors errors);

    public AbstractInternalArgumentParser(String familyName) {
        Intrinsics.checkNotNullParameter(familyName, "familyName");
        this.wholePrefix = "-XX" + familyName;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.InternalArgumentParser
    public boolean canParse(String arg) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        return StringsKt.startsWith$default(arg, this.wholePrefix, false, 2, (Object) null);
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.InternalArgumentParser
    public A parseInternalArgument(String arg, ArgumentParseErrors errors) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        Intrinsics.checkNotNullParameter(errors, "errors");
        if (StringsKt.startsWith$default(arg, this.wholePrefix, false, 2, (Object) null)) {
            return (A) parseTail(StringsKt.removePrefix(arg, (CharSequence) this.wholePrefix), arg, errors);
        }
        return null;
    }
}
