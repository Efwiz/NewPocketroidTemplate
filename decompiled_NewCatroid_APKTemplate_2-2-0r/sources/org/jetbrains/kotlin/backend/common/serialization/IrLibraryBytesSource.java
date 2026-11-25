package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryBytesSource;", "", "()V", "body", "", "index", "", "debugInfo", "irDeclaration", "signature", "string", ModuleXmlParser.TYPE, "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrLibraryBytesSource {
    public abstract byte[] body(int index);

    public abstract byte[] debugInfo(int index);

    public abstract byte[] irDeclaration(int index);

    public abstract byte[] signature(int index);

    public abstract byte[] string(int index);

    public abstract byte[] type(int index);
}
