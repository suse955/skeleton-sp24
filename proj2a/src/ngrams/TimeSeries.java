package ngrams;

import java.util.*;
/**
 * 用类库中的现有工具构建自己的类，为了更好的适应下游任务
 * */
/**
 * 时间序列就是一串时间（一个放置数据的集合，树实现），
 * 在这里，我们使用树来存储每个时间信息（节点），一个时间
 * 节点由年份和数据点组成
 * 由于这个类继承了TreeMap类，所以构造时，先调用父类的构造函数
 * TreeMap本来是一个泛型类，在继承的时候，我们对指定了TreeMap的泛型参数，
 * 所有这个类不是一个泛型类，在声明实例的时候，也不需要提供泛型参数
 * TreeMap是一棵红黑树实现的
 * */

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */

    /**
     * 复制，引用和所有权问题
     * */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        if (ts == null) {
            return;
        }
        List<Integer> keys =new ArrayList<>(ts.keySet());
        Collections.sort(keys);
        for (Integer key : keys) {
            if (key >= startYear && key <= endYear) {
                this.put(key, ts.get(key));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        return List.copyOf(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        return List.copyOf(this.values());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result =(TimeSeries) this.clone();
        Set<Integer> sets = ts.keySet();
        /**
         * for+if-else 结构
         * */
        for (Integer item : sets) {
            if (result.containsKey(item)) {
                result.put(item, ts.get(item) + this.get(item));
            }
            else {
                result.put(item, ts.get(item));
            }
        }
        return result;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries result =(TimeSeries) this.clone();
        Set<Integer> keys = this.keySet();
        for (Integer item : keys) {
            if( !ts.containsKey(item)) throw new IllegalArgumentException() ;
        }
        for (Integer item : keys) {
            result.put(item, this.get(item) / ts.get(item));
        }
        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
