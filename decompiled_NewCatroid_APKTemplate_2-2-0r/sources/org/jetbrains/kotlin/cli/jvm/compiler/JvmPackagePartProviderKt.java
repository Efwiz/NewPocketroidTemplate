package org.jetbrains.kotlin.cli.jvm.compiler;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.PrintStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.load.kotlin.ModuleMappingUtilKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;

/* JADX INFO: compiled from: JvmPackagePartProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a6\u0010\u0000\u001a\u0004\u0018\u00010\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"tryLoadModuleMapping", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/ModuleMapping;", "getModuleBytes", "Lkotlin/Function0;", "", "debugName", "", "modulePath", "deserializationConfiguration", "Lorg/jetbrains/kotlin/resolve/CompilerDeserializationConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmPackagePartProviderKt {
    public static final ModuleMapping tryLoadModuleMapping(Function0<byte[]> getModuleBytes, String debugName, final String modulePath, CompilerDeserializationConfiguration deserializationConfiguration, final MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(getModuleBytes, "getModuleBytes");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(modulePath, "modulePath");
        Intrinsics.checkNotNullParameter(deserializationConfiguration, "deserializationConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        try {
            return ModuleMappingUtilKt.loadModuleMapping(ModuleMapping.INSTANCE, getModuleBytes.invoke(), debugName, deserializationConfiguration, new Function1<JvmMetadataVersion, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProviderKt.tryLoadModuleMapping.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(JvmMetadataVersion jvmMetadataVersion) {
                    invoke2(jvmMetadataVersion);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(JvmMetadataVersion incompatibleVersion) {
                    Intrinsics.checkNotNullParameter(incompatibleVersion, "incompatibleVersion");
                    messageCollector.report(CompilerMessageSeverity.ERROR, "Module was compiled with an incompatible version of Kotlin. The binary version of its metadata is " + incompatibleVersion + ", expected version is " + JvmMetadataVersion.INSTANCE + '.', CompilerMessageLocation.INSTANCE.create(modulePath));
                }
            });
        } catch (EOFException e) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Error occurred when reading the module: " + e.getMessage(), CompilerMessageLocation.INSTANCE.create(modulePath));
            CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.LOGGING;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(byteArrayOutputStream));
            Unit unit = Unit.INSTANCE;
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            messageCollector.report(compilerMessageSeverity, new String(byteArray, Charsets.UTF_8), CompilerMessageLocation.INSTANCE.create(modulePath));
            return null;
        }
    }
}
