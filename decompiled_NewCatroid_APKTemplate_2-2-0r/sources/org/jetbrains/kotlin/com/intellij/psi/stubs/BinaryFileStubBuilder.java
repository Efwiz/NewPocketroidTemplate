package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.stream.Stream;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;

/* JADX INFO: loaded from: classes3.dex */
public interface BinaryFileStubBuilder {
    boolean acceptsFile(VirtualFile virtualFile);

    Stub buildStubTree(FileContent fileContent);

    int getStubVersion();

    public interface CompositeBinaryFileStubBuilder<SubBuilder> extends BinaryFileStubBuilder {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fileContent", "org/jetbrains/kotlin/com/intellij/psi/stubs/BinaryFileStubBuilder$CompositeBinaryFileStubBuilder", "buildStubTree"));
        }

        Stub buildStubTree(FileContent fileContent, SubBuilder subbuilder);

        Stream<SubBuilder> getAllSubBuilders();

        SubBuilder getSubBuilder(FileContent fileContent);

        String getSubBuilderVersion(SubBuilder subbuilder);

        @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder
        default Stub buildStubTree(FileContent fileContent) {
            if (fileContent == null) {
                $$$reportNull$$$0(0);
            }
            return buildStubTree(fileContent, getSubBuilder(fileContent));
        }
    }
}
