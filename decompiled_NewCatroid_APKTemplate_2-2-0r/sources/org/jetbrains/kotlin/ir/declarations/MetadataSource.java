package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MetadataSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001:\u0006\u0006\u0007\b\t\n\u000bR\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Class", "CodeFragment", "File", "Function", "Property", "Script", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MetadataSource {

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Class;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "serializedIr", "", "getSerializedIr", "()[B", "setSerializedIr", "([B)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Class extends MetadataSource {
        byte[] getSerializedIr();

        void setSerializedIr(byte[] bArr);
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$CodeFragment;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface CodeFragment extends MetadataSource {
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$File;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "serializedIr", "", "getSerializedIr", "()[B", "setSerializedIr", "([B)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface File extends MetadataSource {
        byte[] getSerializedIr();

        void setSerializedIr(byte[] bArr);
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Function;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Function extends MetadataSource {
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "isConst", "", "()Z", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Property extends MetadataSource {
        boolean isConst();
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Script;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Script extends MetadataSource {
    }

    Name getName();
}
