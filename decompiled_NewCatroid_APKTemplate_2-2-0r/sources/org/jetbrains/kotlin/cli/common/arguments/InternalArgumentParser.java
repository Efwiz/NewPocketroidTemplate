package org.jetbrains.kotlin.cli.common.arguments;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.cli.common.arguments.InternalArgument;

/* JADX INFO: compiled from: InternalCompilerArgument.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \f*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\fJ\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001f\u0010\b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH&¢\u0006\u0002\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgumentParser;", "A", "Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "", "canParse", "", ParameterDescription.NAME_PREFIX, "", "parseInternalArgument", "errors", "Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;)Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InternalArgumentParser<A extends InternalArgument> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final String INTERNAL_ARGUMENT_PREFIX = "-XX";

    boolean canParse(String arg);

    A parseInternalArgument(String arg, ArgumentParseErrors errors);

    /* JADX INFO: compiled from: InternalCompilerArgument.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgumentParser$Companion;", "", "()V", "INTERNAL_ARGUMENT_PREFIX", "", "PARSERS", "", "Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgumentParser;", "getPARSERS$cli_common", "()Ljava/util/List;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public static final String INTERNAL_ARGUMENT_PREFIX = "-XX";
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final List<InternalArgumentParser<?>> PARSERS = CollectionsKt.listOf(new LanguageSettingsParser());

        private Companion() {
        }

        public final List<InternalArgumentParser<?>> getPARSERS$cli_common() {
            return PARSERS;
        }
    }
}
