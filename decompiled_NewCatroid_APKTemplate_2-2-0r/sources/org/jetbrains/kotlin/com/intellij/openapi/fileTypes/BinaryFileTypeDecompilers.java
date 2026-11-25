package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.components.Service;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;

/* JADX INFO: loaded from: classes3.dex */
@Service
public final class BinaryFileTypeDecompilers extends FileTypeExtension<BinaryFileDecompiler> {
    private static final ExtensionPointName<KeyedLazyInstance<BinaryFileDecompiler>> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.filetype.decompiler");

    /* JADX WARN: Illegal instructions before constructor call */
    private BinaryFileTypeDecompilers() {
        ExtensionPointName<KeyedLazyInstance<BinaryFileDecompiler>> extensionPointName = EP_NAME;
        super(extensionPointName);
        extensionPointName.addChangeListener(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileTypeDecompilers$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6486xaf102b9c();
            }
        }, null);
    }

    /* JADX INFO: renamed from: notifyDecompilerSetChange, reason: merged with bridge method [inline-methods] */
    public void m6486xaf102b9c() {
        ApplicationManager.getApplication().invokeLater(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileTypeDecompilers$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FileDocumentManager.getInstance().reloadBinaryFiles();
            }
        }, ModalityState.NON_MODAL);
    }

    public static BinaryFileTypeDecompilers getInstance() {
        return (BinaryFileTypeDecompilers) ApplicationManager.getApplication().getService(BinaryFileTypeDecompilers.class);
    }
}
