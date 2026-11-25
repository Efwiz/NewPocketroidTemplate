package com.android.tools.r8;

import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class W implements ProgramResourceProvider {
    final ProgramResourceProvider a;

    public W(ProgramResourceProvider programResourceProvider) {
        this.a = programResourceProvider;
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final DataResourceProvider getDataResourceProvider() {
        return this.a.getDataResourceProvider();
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() throws ResourceException {
        Collection<ProgramResource> programResources = this.a.getProgramResources();
        for (ProgramResource programResource : programResources) {
            if (programResource.getKind() == ProgramResource.Kind.DEX) {
                throw new ResourceException(programResource.getOrigin(), "R8 does not support compiling DEX inputs");
            }
        }
        return programResources;
    }
}
