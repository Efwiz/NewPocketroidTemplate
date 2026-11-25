package antlr;

/* JADX INFO: loaded from: classes.dex */
abstract class GrammarSymbol {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    protected String f55id;

    public GrammarSymbol() {
    }

    public GrammarSymbol(String str) {
        this.f55id = str;
    }

    public String getId() {
        return this.f55id;
    }

    public void setId(String str) {
        this.f55id = str;
    }
}
