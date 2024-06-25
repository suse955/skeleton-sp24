import ngrams.*;
import org.junit.jupiter.api.Test;

public class TestRedBlackTree {
    @Test
    public void testInsert() {
        RedBlackTree t = new RedBlackTree();
        TimeSeries ts = new TimeSeries();
        ts.put(2008, 795265.0);
        t.insert("request",ts);
        t.insert("request",ts);
        t.insert("server", ts);
        t.insert("aaa", ts);
        t.remove("request");
    }
}
