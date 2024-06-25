package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap _ngm;
    public HistoryTextHandler(NGramMap ngm) {
        _ngm = ngm;
    }


    @Override
    public String handle(NgordnetQuery query) {
        StringBuilder response = new StringBuilder();
        for (String word : query.words()) {
            TimeSeries ts = _ngm.weightHistory(word,query.startYear(), query.endYear());
            response.append(word).append(": ").append(ts.toString());
            response.append("\n");
//            List<Integer> mutYears = new ArrayList<>(ts.years());
//            Collections.sort(mutYears);
//            Iterator<Integer> iter = mutYears.iterator();
//            response.append(": {");
//            while (iter.hasNext()) {
//                Integer year = iter.next();
//                response.append(year).append("=").append(ts.get(year));
//                if (iter.hasNext()) {
//                    response.append(", ");
//                }
//            }
        }
        return response.toString();
    }
}
