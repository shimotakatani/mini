package mini.object;

public class PageFilter {

    public static final int DEFAULT_LIMIT = 10;
    public static final int MAX_LIMIT = 500;

    private int offset;

    private int limit;

    public PageFilter(){
        this.offset = 0;
        this.limit = DEFAULT_LIMIT;
    }

    public PageFilter(int offset, int limit){
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        if (offset < 0) offset = 0;
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit < 0) limit = DEFAULT_LIMIT;
        if (limit > MAX_LIMIT) limit = MAX_LIMIT;
        this.limit = limit;
    }
}
