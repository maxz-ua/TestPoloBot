package test.polo.tradebot;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingWorker;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class RealTimeChart {

    MySwingWorker mySwingWorker;
    SwingWrapper<XYChart> sw;
    XYChart chart;

    void go() throws JsonMappingException, JsonProcessingException {

        // Create Chart
        chart = QuickChart.getChart("Real-time " + TradeSettings.getCurrency() + " token chart", "Time", "Value",
                TradeSettings.getCurrencyPair(), new double[]{0}, new double[]{0});
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setXAxisTicksVisible(true);

        // Show it
        sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();
        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        final LinkedList<Double> fifo = new LinkedList<Double>();

        public MySwingWorker() throws JsonMappingException, JsonProcessingException {
            CurrencyTicker ticker;
            ticker = ExchangeService.getTicker(TradeSettings.getCurrencyPair());
            BigDecimal transactionSpred = ticker.getHighestBid().add(ticker.getLowestAsk());
            BigDecimal transactionValue = transactionSpred.divide(BigDecimal.valueOf(2.0));
            fifo.add(transactionValue.doubleValue());
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            CurrencyTicker ticker;
            while (!isCancelled()) {
                ticker = ExchangeService.getTicker(TradeSettings.getCurrencyPair());
                BigDecimal transactionSpred = ticker.getHighestBid().add(ticker.getLowestAsk());
                BigDecimal transactionValue = transactionSpred.divide(BigDecimal.valueOf(2.0));
                fifo.add(transactionValue.doubleValue());
                //       TestMainWindow.setBuyPrice(ticker.getLowestAsk().toString());
                //       TestMainWindow.setSellPrice(ticker.getHighestBid().toString());
                if (fifo.size() > 1000) {
                    fifo.removeFirst();
                }

                double[] array = new double[fifo.size()];
                for (int i = 0; i < fifo.size(); i++) {
                    array[i] = fifo.get(i);
                }
                publish(array);

                try {
                    Thread.sleep(1488);
                } catch (InterruptedException e) {
                    // eat it. caught when interrupt is called
                    System.out.println("MySwingWorker shut down.");
                }
            }

            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {
            double[] mostRecentDataSet = chunks.get(chunks.size() - 1);
            chart.updateXYSeries(TradeSettings.getCurrencyPair(), null, mostRecentDataSet, null);
            sw.repaintChart();
            long start = System.currentTimeMillis();
            long duration = System.currentTimeMillis() - start;
            try {
                Thread.sleep(40 - duration); // 40 ms ==> 25fps
                // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
            } catch (InterruptedException e) {
                System.out.println("InterruptedException occurred.");
            }
        }
    }
}
