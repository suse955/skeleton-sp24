package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetServer;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;

import java.util.LinkedList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap _ngm;

    public HistoryHandler(NGramMap ngm) {
        _ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<TimeSeries> lts = new LinkedList<>();
        for (String word : words) {
            lts.add(_ngm.weightHistory(word, q.startYear(), q.endYear()));
        }
        return Plotter.encodeChartAsString(Plotter.generateTimeSeriesChart(words, lts));
    }
}
