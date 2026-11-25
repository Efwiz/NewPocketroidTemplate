package org.jf.dexlib2.rewriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.extra.DexMarker;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;

/* JADX INFO: loaded from: classes6.dex */
public class DexFileRewriter implements Rewriter<DexFile> {
    protected final Rewriters rewriters;

    public DexFileRewriter(Rewriters rewriters) {
        this.rewriters = rewriters;
    }

    @Override // org.jf.dexlib2.rewriter.Rewriter
    public DexFile rewrite(DexFile dexFile) {
        return new RewrittenDexFile(dexFile);
    }

    protected class RewrittenDexFile implements DexFile {
        protected final DexFile dexFile;
        private final List<DexMarker> markerList;

        public RewrittenDexFile(DexFile dexFile) {
            this.dexFile = dexFile;
            this.markerList = new ArrayList(dexFile.getMarkers());
        }

        @Override // org.jf.dexlib2.iface.DexFile
        public List<DexMarker> getMarkers() {
            return this.markerList;
        }

        @Override // org.jf.dexlib2.iface.DexFile
        public Set<? extends ClassDef> getClasses() {
            return RewriterUtils.rewriteSet(DexFileRewriter.this.rewriters.getClassDefRewriter(), this.dexFile.getClasses());
        }

        @Override // org.jf.dexlib2.iface.DexFile
        public Opcodes getOpcodes() {
            return this.dexFile.getOpcodes();
        }
    }
}
