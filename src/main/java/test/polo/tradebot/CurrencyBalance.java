package test.polo.tradebot;

import java.math.BigDecimal;

public class CurrencyBalance {

    private BigDecimal available;
    private BigDecimal onOrders;
    private BigDecimal btcValue;

    public BigDecimal getAvailable() {
        return available;
    }

    public BigDecimal getOnOrders() {
        return onOrders;
    }

    public BigDecimal getBtcValue() {
        return btcValue;
    }
}
