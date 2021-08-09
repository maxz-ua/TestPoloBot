package test.polo.tradebot;

import java.math.BigDecimal;

public class CurrencyTicker {

    private BigDecimal last;
    private BigDecimal lowestAsk;
    private BigDecimal highestBid;
    private BigDecimal percentChange;
    private BigDecimal baseVolume;
    private BigDecimal quoteVolume;

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getLowestAsk() {
        return lowestAsk;
    }

    public BigDecimal getHighestBid() {
        return highestBid;
    }

    public BigDecimal getPercentChange() {
        return percentChange;
    }

    public BigDecimal getBaseVolume() {
        return baseVolume;
    }

    public BigDecimal getQuoteVolume() {
        return quoteVolume;
    }

}