package org.eclipse.jdt.internal.compiler.apt.dispatch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;

/* JADX INFO: loaded from: classes14.dex */
public class BatchFilerImpl implements Filer {
    protected final HashSet<URI> _createdFiles = new HashSet<>();
    protected final BaseAnnotationProcessorManager _dispatchManager;
    protected final BaseProcessingEnvImpl _env;
    protected final JavaFileManager _fileManager;

    public BatchFilerImpl(BaseAnnotationProcessorManager baseAnnotationProcessorManager, BatchProcessingEnvImpl batchProcessingEnvImpl) {
        this._dispatchManager = baseAnnotationProcessorManager;
        this._fileManager = batchProcessingEnvImpl._fileManager;
        this._env = batchProcessingEnvImpl;
    }

    public void addNewUnit(ICompilationUnit iCompilationUnit) {
        this._env.addNewUnit(iCompilationUnit);
    }

    public void addNewClassFile(ReferenceBinding referenceBinding) {
        this._env.addNewClassFile(referenceBinding);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.annotation.processing.FilerException */
    public JavaFileObject createClassFile(CharSequence charSequence, Element... elementArr) throws FilerException, IOException {
        JavaFileObject javaFileForOutput = this._fileManager.getJavaFileForOutput(StandardLocation.CLASS_OUTPUT, charSequence.toString(), JavaFileObject.Kind.CLASS, (FileObject) null);
        URI uri = javaFileForOutput.toUri();
        if (this._createdFiles.contains(uri)) {
            throw new FilerException("Class file already created : " + ((Object) charSequence));
        }
        this._createdFiles.add(uri);
        return new HookedJavaFileObject(javaFileForOutput, javaFileForOutput.getName(), charSequence.toString(), this);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.annotation.processing.FilerException */
    public FileObject createResource(JavaFileManager.Location location, CharSequence charSequence, CharSequence charSequence2, Element... elementArr) throws FilerException, IOException {
        validateName(charSequence2);
        FileObject fileForOutput = this._fileManager.getFileForOutput(location, charSequence.toString(), charSequence2.toString(), (FileObject) null);
        URI uri = fileForOutput.toUri();
        if (this._createdFiles.contains(uri)) {
            throw new FilerException("Resource already created : " + location + '/' + ((Object) charSequence) + '/' + ((Object) charSequence2));
        }
        this._createdFiles.add(uri);
        return fileForOutput;
    }

    private static void validateName(CharSequence charSequence) {
        int length = charSequence.length();
        if (length == 0) {
            throw new IllegalArgumentException("relative path cannot be empty");
        }
        String string = charSequence.toString();
        if (string.indexOf(92) != -1) {
            string = string.replace('\\', '/');
        }
        if (string.charAt(0) == '/') {
            throw new IllegalArgumentException("relative path is absolute");
        }
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = string.charAt(i);
            if (cCharAt == '.') {
                z = true;
            } else if (cCharAt != '/') {
                z = false;
            } else if (z) {
                throw new IllegalArgumentException("relative name " + ((Object) charSequence) + " is not relative");
            }
        }
        if (z) {
            throw new IllegalArgumentException("relative name " + ((Object) charSequence) + " is not relative");
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.annotation.processing.FilerException */
    public JavaFileObject createSourceFile(CharSequence charSequence, Element... elementArr) throws FilerException, IOException {
        JavaFileObject javaFileForOutput = this._fileManager.getJavaFileForOutput(StandardLocation.SOURCE_OUTPUT, charSequence.toString(), JavaFileObject.Kind.SOURCE, (FileObject) null);
        URI uri = javaFileForOutput.toUri();
        if (this._createdFiles.contains(uri)) {
            throw new FilerException("Source file already created : " + ((Object) charSequence));
        }
        this._createdFiles.add(uri);
        return new HookedJavaFileObject(javaFileForOutput, javaFileForOutput.getName(), charSequence.toString(), this);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.annotation.processing.FilerException */
    public FileObject getResource(JavaFileManager.Location location, CharSequence charSequence, CharSequence charSequence2) throws FilerException, IOException {
        validateName(charSequence2);
        FileObject fileForInput = this._fileManager.getFileForInput(location, charSequence.toString(), charSequence2.toString());
        if (fileForInput == null) {
            throw new FileNotFoundException("Resource does not exist : " + location + '/' + ((Object) charSequence) + '/' + ((Object) charSequence2));
        }
        URI uri = fileForInput.toUri();
        if (this._createdFiles.contains(uri)) {
            throw new FilerException("Resource already created : " + location + '/' + ((Object) charSequence) + '/' + ((Object) charSequence2));
        }
        this._createdFiles.add(uri);
        return fileForInput;
    }
}
