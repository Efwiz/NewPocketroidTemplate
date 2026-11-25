package org.jetbrains.kotlin.fir.backend;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirMetadataSource.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\u000b\f\r\u000e\u000f\u0010\u0011B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0007\u0012\u0013\u0014\u0015\u0016\u0017\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "()V", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Class", "CodeFragment", "Field", "File", "Function", "Property", "Script", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Class;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$CodeFragment;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Field;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$File;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Function;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Property;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Script;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirMetadataSource implements MetadataSource {
    public /* synthetic */ FirMetadataSource(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract FirDeclaration getFir();

    private FirMetadataSource() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource
    public Name getName() {
        FirDeclaration fir = getFir();
        if (fir instanceof FirConstructor) {
            return SpecialNames.INIT;
        }
        if (fir instanceof FirSimpleFunction) {
            return ((FirSimpleFunction) fir).getName();
        }
        if (fir instanceof FirRegularClass) {
            return ((FirRegularClass) fir).getName();
        }
        if (fir instanceof FirProperty) {
            return ((FirProperty) fir).getName();
        }
        return null;
    }

    /* JADX INFO: compiled from: FirMetadataSource.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$File;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$File;", "files", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "(Ljava/util/List;)V", "getFiles", "()Ljava/util/List;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "serializedIr", "", "getSerializedIr", "()[B", "setSerializedIr", "([B)V", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class File extends FirMetadataSource implements MetadataSource.File {
        private final List<FirFile> files;
        private byte[] serializedIr;

        @Override // org.jetbrains.kotlin.fir.backend.FirMetadataSource
        public FirDeclaration getFir() {
            return null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public File(List<? extends FirFile> files) {
            super(null);
            Intrinsics.checkNotNullParameter(files, "files");
            this.files = files;
        }

        public final List<FirFile> getFiles() {
            return this.files;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.File
        public byte[] getSerializedIr() {
            return this.serializedIr;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.File
        public void setSerializedIr(byte[] bArr) {
            this.serializedIr = bArr;
        }
    }

    /* JADX INFO: compiled from: FirMetadataSource.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Class;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Class;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "serializedIr", "", "getSerializedIr", "()[B", "setSerializedIr", "([B)V", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Class extends FirMetadataSource implements MetadataSource.Class {
        private final FirClass fir;
        private byte[] serializedIr;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Class(FirClass fir) {
            super(null);
            Intrinsics.checkNotNullParameter(fir, "fir");
            this.fir = fir;
        }

        @Override // org.jetbrains.kotlin.fir.backend.FirMetadataSource
        public FirClass getFir() {
            return this.fir;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Class
        public byte[] getSerializedIr() {
            return this.serializedIr;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Class
        public void setSerializedIr(byte[] bArr) {
            this.serializedIr = bArr;
        }
    }

    /* JADX INFO: compiled from: FirMetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Function;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Function;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Function extends FirMetadataSource implements MetadataSource.Function {
        private final FirFunction fir;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Function(FirFunction fir) {
            super(null);
            Intrinsics.checkNotNullParameter(fir, "fir");
            this.fir = fir;
        }

        @Override // org.jetbrains.kotlin.fir.backend.FirMetadataSource
        public FirFunction getFir() {
            return this.fir;
        }
    }

    /* JADX INFO: compiled from: FirMetadataSource.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Property;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "isConst", "", "()Z", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Property extends FirMetadataSource implements MetadataSource.Property {
        private final FirProperty fir;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Property(FirProperty fir) {
            super(null);
            Intrinsics.checkNotNullParameter(fir, "fir");
            this.fir = fir;
        }

        @Override // org.jetbrains.kotlin.fir.backend.FirMetadataSource
        public FirProperty getFir() {
            return this.fir;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Property
        public boolean isConst() {
            return getFir().getStatus().isConst();
        }
    }

    /* JADX INFO: compiled from: FirMetadataSource.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Field;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirField;)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirField;", "isConst", "", "()Z", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Field extends FirMetadataSource implements MetadataSource.Property {
        private final FirField fir;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(FirField fir) {
            super(null);
            Intrinsics.checkNotNullParameter(fir, "fir");
            this.fir = fir;
        }

        @Override // org.jetbrains.kotlin.fir.backend.FirMetadataSource
        public FirField getFir() {
            return this.fir;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Property
        public boolean isConst() {
            return getFir().getStatus().isConst();
        }
    }

    /* JADX INFO: compiled from: FirMetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$Script;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Script;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "(Lorg/jetbrains/kotlin/fir/declarations/FirScript;)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Script extends FirMetadataSource implements MetadataSource.Script {
        private final FirScript fir;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Script(FirScript fir) {
            super(null);
            Intrinsics.checkNotNullParameter(fir, "fir");
            this.fir = fir;
        }

        @Override // org.jetbrains.kotlin.fir.backend.FirMetadataSource
        public FirScript getFir() {
            return this.fir;
        }
    }

    /* JADX INFO: compiled from: FirMetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource$CodeFragment;", "Lorg/jetbrains/kotlin/fir/backend/FirMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$CodeFragment;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;)V", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CodeFragment extends FirMetadataSource implements MetadataSource.CodeFragment {
        private final FirCodeFragment fir;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CodeFragment(FirCodeFragment fir) {
            super(null);
            Intrinsics.checkNotNullParameter(fir, "fir");
            this.fir = fir;
        }

        @Override // org.jetbrains.kotlin.fir.backend.FirMetadataSource
        public FirCodeFragment getFir() {
            return this.fir;
        }
    }
}
