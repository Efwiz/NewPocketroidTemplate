package antlr;

/* JADX INFO: loaded from: classes.dex */
class BlockContext {
    int altNum;
    AlternativeBlock block;
    BlockEndElement blockEnd;

    BlockContext() {
    }

    public void addAlternativeElement(AlternativeElement alternativeElement) {
        currentAlt().addElement(alternativeElement);
    }

    public Alternative currentAlt() {
        return (Alternative) this.block.alternatives.elementAt(this.altNum);
    }

    public AlternativeElement currentElement() {
        return currentAlt().tail;
    }
}
