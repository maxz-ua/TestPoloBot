package test.polo.tradebot;

import java.math.BigDecimal;

public class TradeSettings {

    private static String currency = "TRX";
    private static String currencyPair = "USDT_TRX";
    private static BigDecimal tradeAmount = BigDecimal.valueOf(10); // ТУТ ПРВОЕРИТЬ КОЛИЧЕСТВО ВАЛЮТЫ 10.0 - рипо \ 0,02 - лайт

    public static String getCurrency() {
        return currency;
    }

    public static String getCurrencyPair() {
        return currencyPair;
    }

    public static BigDecimal getTradeAmount() {
        return tradeAmount;
    }

}



