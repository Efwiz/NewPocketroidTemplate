package io.github.rosemoe.sora.text.bidi;

import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentListener;
import io.github.rosemoe.sora.util.IntPair;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class ContentBidi implements ContentListener {
    public static final int MAX_BIDI_CACHE_ENTRY_COUNT = 64;
    private boolean enabled;
    private final DirectionsEntry[] entries = new DirectionsEntry[64];
    private final Content text;

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeReplace(Content content) {
    }

    public ContentBidi(Content content) {
        Content content2 = (Content) Objects.requireNonNull(content);
        this.text = content2;
        content2.addContentListener(this);
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
        if (z) {
            return;
        }
        Arrays.fill(this.entries, (Object) null);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Directions getLineDirections(int i) {
        if (!this.enabled) {
            return new Directions(new long[]{IntPair.pack(0, 0)}, this.text.getLine(i).length());
        }
        synchronized (this) {
            int i2 = 0;
            while (true) {
                DirectionsEntry[] directionsEntryArr = this.entries;
                if (i2 < directionsEntryArr.length) {
                    DirectionsEntry directionsEntry = directionsEntryArr[i2];
                    if (directionsEntry != null && directionsEntry.line == i) {
                        return directionsEntry.dir;
                    }
                    i2++;
                } else {
                    Directions directions = TextBidi.getDirections(this.text.getLine(i));
                    synchronized (this) {
                        DirectionsEntry[] directionsEntryArr2 = this.entries;
                        System.arraycopy(directionsEntryArr2, 0, directionsEntryArr2, 1, directionsEntryArr2.length - 1);
                        this.entries[0] = new DirectionsEntry(directions, i);
                    }
                    return directions;
                }
            }
        }
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public synchronized void afterDelete(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        int i5 = i3 - i;
        int i6 = 0;
        while (true) {
            DirectionsEntry[] directionsEntryArr = this.entries;
            if (i6 < directionsEntryArr.length) {
                DirectionsEntry directionsEntry = directionsEntryArr[i6];
                if (directionsEntry != null && directionsEntry.line >= i) {
                    if (directionsEntry.line > i3) {
                        directionsEntry.line -= i5;
                    } else {
                        this.entries[i6] = null;
                    }
                }
                i6++;
            }
        }
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public synchronized void afterInsert(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        int i5 = i3 - i;
        int i6 = 0;
        while (true) {
            DirectionsEntry[] directionsEntryArr = this.entries;
            if (i6 < directionsEntryArr.length) {
                DirectionsEntry directionsEntry = directionsEntryArr[i6];
                if (directionsEntry != null) {
                    if (directionsEntry.line > i) {
                        directionsEntry.line += i5;
                    } else if (directionsEntry.line == i) {
                        this.entries[i6] = null;
                    }
                }
                i6++;
            }
        }
    }

    public void destroy() {
        this.text.removeContentListener(this);
        Arrays.fill(this.entries, (Object) null);
    }

    private static class DirectionsEntry {
        Directions dir;
        int line;

        public DirectionsEntry(Directions directions, int i) {
            this.dir = directions;
            this.line = i;
        }
    }
}
