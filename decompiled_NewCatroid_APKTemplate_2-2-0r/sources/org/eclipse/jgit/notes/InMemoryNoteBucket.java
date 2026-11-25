package org.eclipse.jgit.notes;

/* JADX INFO: loaded from: classes13.dex */
abstract class InMemoryNoteBucket extends NoteBucket {
    NonNoteEntry nonNotes;
    final int prefixLen;

    abstract InMemoryNoteBucket append(Note note);

    InMemoryNoteBucket(int i) {
        this.prefixLen = i;
    }
}
