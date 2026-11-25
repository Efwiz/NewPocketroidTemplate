package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.W2;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ProgramResource extends Resource {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class ByteResource implements ProgramResource {
        static final /* synthetic */ boolean e = true;
        private final Origin a;
        private final Kind b;
        private final byte[] c;
        private final Set d;

        private ByteResource(Origin origin, Kind kind, byte[] bArr, Set set) {
            if (!e && bArr == null) {
                throw new AssertionError();
            }
            this.a = origin;
            this.b = kind;
            this.c = bArr;
            this.d = set;
        }

        @Override // com.android.tools.r8.ProgramResource
        public InputStream getByteStream() throws ResourceException {
            return new ByteArrayInputStream(this.c);
        }

        @Override // com.android.tools.r8.ProgramResource
        public byte[] getBytes() throws ResourceException {
            return this.c;
        }

        @Override // com.android.tools.r8.ProgramResource
        public Set<String> getClassDescriptors() {
            return this.d;
        }

        @Override // com.android.tools.r8.ProgramResource
        public Kind getKind() {
            return this.b;
        }

        @Override // com.android.tools.r8.Resource
        public Origin getOrigin() {
            return this.a;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class FileResource implements ProgramResource {
        private final PathOrigin a;
        private final Kind b;
        private final Path c;
        private final Set d;

        private FileResource(Kind kind, Path path) {
            this.a = new PathOrigin(path);
            this.b = kind;
            this.c = path;
            this.d = null;
        }

        @Override // com.android.tools.r8.ProgramResource
        public InputStream getByteStream() throws ResourceException {
            try {
                return Files.newInputStream(this.c, new OpenOption[0]);
            } catch (IOException e) {
                throw new ResourceException(getOrigin(), e);
            }
        }

        @Override // com.android.tools.r8.ProgramResource
        public byte[] getBytes() throws ResourceException {
            try {
                return Files.readAllBytes(this.c);
            } catch (IOException e) {
                throw new ResourceException(getOrigin(), e);
            }
        }

        @Override // com.android.tools.r8.ProgramResource
        public Set<String> getClassDescriptors() {
            return this.d;
        }

        @Override // com.android.tools.r8.ProgramResource
        public Kind getKind() {
            return this.b;
        }

        @Override // com.android.tools.r8.Resource
        public Origin getOrigin() {
            return this.a;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum Kind {
        CF,
        DEX
    }

    static ProgramResource fromBytes(Origin origin, Kind kind, byte[] bArr, Set<String> set) {
        return new ByteResource(origin, kind, bArr, set);
    }

    static ProgramResource fromFile(Kind kind, Path path) {
        return new FileResource(kind, path);
    }

    InputStream getByteStream() throws ResourceException;

    default byte[] getBytes() throws ResourceException {
        try {
            return W2.a(getByteStream());
        } catch (IOException e) {
            throw new ResourceException(getOrigin(), e);
        }
    }

    Set<String> getClassDescriptors();

    Kind getKind();
}
