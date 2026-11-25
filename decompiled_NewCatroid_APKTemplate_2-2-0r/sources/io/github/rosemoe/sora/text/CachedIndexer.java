package io.github.rosemoe.sora.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class CachedIndexer implements Indexer, ContentListener {
    private final Content content;
    private final CharPosition startPosition = new CharPosition().toBOF();
    private final CharPosition endPosition = new CharPosition();
    private final List<CharPosition> cachedPositions = new ArrayList();
    private final int thresholdLine = 50;
    private int thresholdIndex = 50;
    private int maxCacheCount = 50;

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeReplace(Content content) {
    }

    CachedIndexer(Content content) {
        this.content = content;
        updateEnd();
    }

    public void setThresholdIndex(int i) {
        this.thresholdIndex = i;
    }

    private void updateEnd() {
        this.endPosition.index = this.content.length();
        this.endPosition.line = this.content.getLineCount() - 1;
        CharPosition charPosition = this.endPosition;
        charPosition.column = this.content.getColumnCount(charPosition.line);
    }

    private synchronized CharPosition findNearestByIndex(int i) {
        CharPosition charPosition;
        charPosition = this.startPosition;
        int i2 = i;
        int i3 = i2;
        int i4 = 0;
        int i5 = 0;
        while (i4 < this.cachedPositions.size()) {
            CharPosition charPosition2 = this.cachedPositions.get(i4);
            int iAbs = Math.abs(charPosition2.index - i);
            if (iAbs < i2) {
                i5 = i4;
                charPosition = charPosition2;
                i2 = iAbs;
            }
            if (iAbs <= this.thresholdIndex) {
                i3 = iAbs;
                break;
            }
            i4++;
            i3 = iAbs;
        }
        if (Math.abs(this.endPosition.index - i) < i3) {
            charPosition = this.endPosition;
        }
        if (charPosition != this.startPosition && charPosition != this.endPosition) {
            Collections.swap(this.cachedPositions, i5, 0);
        }
        return charPosition;
    }

    private synchronized CharPosition findNearestByLine(int i) {
        CharPosition charPosition;
        charPosition = this.startPosition;
        int i2 = i;
        int i3 = i2;
        int i4 = 0;
        int i5 = 0;
        while (i4 < this.cachedPositions.size()) {
            CharPosition charPosition2 = this.cachedPositions.get(i4);
            int iAbs = Math.abs(charPosition2.line - i);
            if (iAbs < i2) {
                i5 = i4;
                charPosition = charPosition2;
                i2 = iAbs;
            }
            if (i2 <= 50) {
                i3 = iAbs;
                break;
            }
            i4++;
            i3 = iAbs;
        }
        if (Math.abs(this.endPosition.line - i) < i3) {
            charPosition = this.endPosition;
        }
        if (charPosition != this.startPosition && charPosition != this.endPosition) {
            Collections.swap(this.cachedPositions, 0, i5);
        }
        return charPosition;
    }

    void findIndexForward(CharPosition charPosition, int i, CharPosition charPosition2) {
        if (charPosition.index > i) {
            throw new IllegalArgumentException("Unable to find backward from method findIndexForward()");
        }
        int i2 = charPosition.line;
        int i3 = charPosition.column;
        int i4 = charPosition.index;
        int columnCountUnsafe = this.content.getColumnCountUnsafe(i2) + Math.max(this.content.getLineSeparatorUnsafe(i2).getLength() - 1, 0);
        int i5 = columnCountUnsafe - i3;
        while (true) {
            i4 += i5;
            if (i4 >= i) {
                break;
            }
            i2++;
            columnCountUnsafe = this.content.getColumnCountUnsafe(i2) + Math.max(this.content.getLineSeparatorUnsafe(i2).getLength() - 1, 0);
            i5 = columnCountUnsafe + 1;
        }
        if (i4 > i) {
            columnCountUnsafe -= i4 - i;
        }
        charPosition2.column = columnCountUnsafe;
        charPosition2.line = i2;
        charPosition2.index = i;
    }

    void findIndexBackward(CharPosition charPosition, int i, CharPosition charPosition2) {
        if (charPosition.index < i) {
            throw new IllegalArgumentException("Unable to find forward from method findIndexBackward()");
        }
        int i2 = charPosition.line;
        int iMax = charPosition.column;
        int i3 = charPosition.index;
        while (i3 > i) {
            i3 -= iMax + 1;
            i2--;
            if (i2 != -1) {
                iMax = Math.max(this.content.getLineSeparatorUnsafe(i2).getLength() - 1, 0) + this.content.getColumnCountUnsafe(i2);
            } else {
                findIndexForward(this.startPosition, i, charPosition2);
                return;
            }
        }
        int i4 = i - i3;
        if (i4 > 0) {
            i2++;
            iMax = i4 - 1;
        }
        charPosition2.column = iMax;
        charPosition2.line = i2;
        charPosition2.index = i;
    }

    void findLiCoForward(CharPosition charPosition, int i, int i2, CharPosition charPosition2) {
        if (charPosition.line > i) {
            throw new IllegalArgumentException("can not find backward from findLiCoForward()");
        }
        int i3 = charPosition.line;
        int columnCountUnsafe = charPosition.index - charPosition.column;
        while (i3 < i) {
            columnCountUnsafe += this.content.getColumnCountUnsafe(i3) + this.content.getLineSeparatorUnsafe(i3).getLength();
            i3++;
        }
        charPosition2.column = 0;
        charPosition2.line = i3;
        charPosition2.index = columnCountUnsafe;
        findInLine(charPosition2, i, i2);
    }

    void findLiCoBackward(CharPosition charPosition, int i, int i2, CharPosition charPosition2) {
        if (charPosition.line < i) {
            throw new IllegalArgumentException("can not find forward from findLiCoBackward()");
        }
        int i3 = charPosition.line;
        int columnCountUnsafe = charPosition.index - charPosition.column;
        while (i3 > i) {
            int i4 = i3 - 1;
            columnCountUnsafe -= this.content.getColumnCountUnsafe(i4) + this.content.getLineSeparatorUnsafe(i4).getLength();
            i3--;
        }
        charPosition2.column = 0;
        charPosition2.line = i3;
        charPosition2.index = columnCountUnsafe;
        findInLine(charPosition2, i, i2);
    }

    private void findInLine(CharPosition charPosition, int i, int i2) {
        if (charPosition.line != i) {
            throw new IllegalArgumentException("can not find other lines with findInLine()");
        }
        charPosition.index = (charPosition.index - charPosition.column) + i2;
        charPosition.column = i2;
    }

    private synchronized void push(CharPosition charPosition) {
        if (this.maxCacheCount <= 0) {
            return;
        }
        this.cachedPositions.add(charPosition);
        if (this.cachedPositions.size() > this.maxCacheCount) {
            this.cachedPositions.remove(0);
        }
    }

    protected int getMaxCacheCount() {
        return this.maxCacheCount;
    }

    protected void setMaxCacheCount(int i) {
        this.maxCacheCount = i;
    }

    @Override // io.github.rosemoe.sora.text.Indexer
    public int getCharIndex(int i, int i2) {
        return getCharPosition(i, i2).index;
    }

    @Override // io.github.rosemoe.sora.text.Indexer
    public int getCharLine(int i) {
        return getCharPosition(i).line;
    }

    @Override // io.github.rosemoe.sora.text.Indexer
    public int getCharColumn(int i) {
        return getCharPosition(i).column;
    }

    @Override // io.github.rosemoe.sora.text.Indexer
    public CharPosition getCharPosition(int i) {
        CharPosition charPosition = new CharPosition();
        getCharPosition(i, charPosition);
        return charPosition;
    }

    @Override // io.github.rosemoe.sora.text.Indexer
    public void getCharPosition(int i, CharPosition charPosition) {
        this.content.checkIndex(i);
        this.content.lock(false);
        try {
            CharPosition charPositionFindNearestByIndex = findNearestByIndex(i);
            if (charPositionFindNearestByIndex.index == i) {
                charPosition.set(charPositionFindNearestByIndex);
            } else if (charPositionFindNearestByIndex.index < i) {
                findIndexForward(charPositionFindNearestByIndex, i, charPosition);
            } else {
                findIndexBackward(charPositionFindNearestByIndex, i, charPosition);
            }
            if (Math.abs(i - charPositionFindNearestByIndex.index) >= this.thresholdIndex) {
                push(charPosition.fromThis());
            }
        } finally {
            this.content.unlock(false);
        }
    }

    @Override // io.github.rosemoe.sora.text.Indexer
    public CharPosition getCharPosition(int i, int i2) {
        CharPosition charPosition = new CharPosition();
        getCharPosition(i, i2, charPosition);
        return charPosition;
    }

    @Override // io.github.rosemoe.sora.text.Indexer
    public void getCharPosition(int i, int i2, CharPosition charPosition) {
        this.content.checkLineAndColumn(i, i2);
        this.content.lock(false);
        try {
            CharPosition charPositionFindNearestByLine = findNearestByLine(i);
            if (charPositionFindNearestByLine.line == i) {
                charPosition.set(charPositionFindNearestByLine);
                if (charPositionFindNearestByLine.column == i2) {
                    return;
                } else {
                    findInLine(charPosition, i, i2);
                }
            } else if (charPositionFindNearestByLine.line < i) {
                findLiCoForward(charPositionFindNearestByLine, i, i2, charPosition);
            } else {
                findLiCoBackward(charPositionFindNearestByLine, i, i2, charPosition);
            }
            if (Math.abs(charPositionFindNearestByLine.line - i) > 50) {
                push(charPosition.fromThis());
            }
        } finally {
            this.content.unlock(false);
        }
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public synchronized void afterInsert(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        for (CharPosition charPosition : this.cachedPositions) {
            if (charPosition.line == i) {
                if (charPosition.column >= i2) {
                    charPosition.index += charSequence.length();
                    charPosition.line += i3 - i;
                    charPosition.column = (charPosition.column + i4) - i2;
                }
            } else if (charPosition.line > i) {
                charPosition.index += charSequence.length();
                charPosition.line += i3 - i;
            }
        }
        updateEnd();
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public synchronized void afterDelete(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        ArrayList arrayList = new ArrayList();
        for (CharPosition charPosition : this.cachedPositions) {
            if (charPosition.line == i) {
                if (charPosition.column >= i2) {
                    arrayList.add(charPosition);
                }
            } else if (charPosition.line > i) {
                if (charPosition.line < i3) {
                    arrayList.add(charPosition);
                } else if (charPosition.line == i3) {
                    arrayList.add(charPosition);
                } else {
                    charPosition.index -= charSequence.length();
                    charPosition.line -= i3 - i;
                }
            }
        }
        this.cachedPositions.removeAll(arrayList);
        updateEnd();
    }
}
