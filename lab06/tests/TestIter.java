import org.junit.Test;

public class TestIter {
    @Test
    public void TestIterator() {
        BSTMap<String, String> a = new BSTMap<>();
        a.put("d", "nihao");
        a.put("b", "hello");
        a.put("f", "kongmen");
        a.put("a", "buzhidao");
        a.put("c", "niu");
        a.put("e", "666");
        a.put("g", "000");
//        a.printInOrder();
        a.remove("g");
//        a.printInOrder();
        for (String _item : a) {
            System.out.println(_item);
        }

    }
    @Test
    public void TestFindMax() {

    }
}
