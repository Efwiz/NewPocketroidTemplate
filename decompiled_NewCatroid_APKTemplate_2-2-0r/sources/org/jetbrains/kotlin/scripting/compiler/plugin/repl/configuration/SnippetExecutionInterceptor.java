package org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: SnippetExecutionInterceptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J!\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H&¢\u0006\u0002\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor;", "", "execute", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "Plain", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SnippetExecutionInterceptor {

    /* JADX INFO: renamed from: Plain, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    <T> T execute(Function0<? extends T> block);

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.SnippetExecutionInterceptor$Plain, reason: from kotlin metadata */
    /* JADX INFO: compiled from: SnippetExecutionInterceptor.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0006H\u0016¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor$Plain;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/SnippetExecutionInterceptor;", "()V", "execute", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion implements SnippetExecutionInterceptor {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        @Override // org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.SnippetExecutionInterceptor
        public <T> T execute(Function0<? extends T> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            return block.invoke();
        }
    }
}
