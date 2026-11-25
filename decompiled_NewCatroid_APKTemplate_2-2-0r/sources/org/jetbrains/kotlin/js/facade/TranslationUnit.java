package org.jetbrains.kotlin.js.facade;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: TranslationUnit.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationUnit;", "", "()V", "BinaryAst", "SourceFile", "Lorg/jetbrains/kotlin/js/facade/TranslationUnit$BinaryAst;", "Lorg/jetbrains/kotlin/js/facade/TranslationUnit$SourceFile;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TranslationUnit {
    public /* synthetic */ TranslationUnit(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: TranslationUnit.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationUnit$SourceFile;", "Lorg/jetbrains/kotlin/js/facade/TranslationUnit;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/psi/KtFile;)V", "getFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SourceFile extends TranslationUnit {
        private final KtFile file;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SourceFile(KtFile file) {
            super(null);
            Intrinsics.checkNotNullParameter(file, "file");
            this.file = file;
        }

        public final KtFile getFile() {
            return this.file;
        }
    }

    private TranslationUnit() {
    }

    /* JADX INFO: compiled from: TranslationUnit.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/facade/TranslationUnit$BinaryAst;", "Lorg/jetbrains/kotlin/js/facade/TranslationUnit;", "data", "", "inlineData", "([B[B)V", "getData", "()[B", "getInlineData", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BinaryAst extends TranslationUnit {
        private final byte[] data;
        private final byte[] inlineData;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BinaryAst(byte[] data, byte[] inlineData) {
            super(null);
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(inlineData, "inlineData");
            this.data = data;
            this.inlineData = inlineData;
        }

        public final byte[] getData() {
            return this.data;
        }

        public final byte[] getInlineData() {
            return this.inlineData;
        }
    }
}
