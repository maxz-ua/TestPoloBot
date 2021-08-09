package test.polo.tradebot;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import com.cf.client.poloniex.PoloniexExchangeService;
import com.cf.data.model.poloniex.PoloniexChartData;
import com.cf.data.model.poloniex.PoloniexCompleteBalance;
import com.cf.data.model.poloniex.PoloniexTicker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExchangeService {
    static String apiKey = "test";
    static String apiSecret = "test";
    static boolean fillOrKill = false;
    static boolean immediateOrCancel = false;
    static boolean postOnly = false;

    static PoloniexExchangeService service = new PoloniexExchangeService(apiKey, apiSecret);

    public static CurrencyTicker getTicker(String currencyPair) throws JsonMappingException, JsonProcessingException {
        PoloniexTicker currencyTicker = service.returnTicker(currencyPair);
        String currencyString = currencyTicker.toString();
        ObjectMapper tickerMapper = new ObjectMapper();
        CurrencyTicker ticker = tickerMapper.readValue(currencyString, CurrencyTicker.class);
        return ticker;
    }

    public static CurrencyBalance getBalance(String currency)
            throws JsonMappingException, JsonProcessingException {
        PoloniexCompleteBalance balanceTicker = service.returnCurrencyBalance(currency);
        String balanceString = balanceTicker.toString();
        ObjectMapper balanceMapper = new ObjectMapper();
        CurrencyBalance balance = balanceMapper.readValue(balanceString, CurrencyBalance.class);
        return balance;
    }

    public static BigDecimal getUsdtBalance() throws JsonMappingException, JsonProcessingException {
        PoloniexCompleteBalance usdtBalanceTicker = service.returnCurrencyBalance("USDT");
        String usdtBalanceString = usdtBalanceTicker.toString();
        ObjectMapper usdtBalanceMapper = new ObjectMapper();
        CurrencyBalance usdtBalance = usdtBalanceMapper.readValue(usdtBalanceString, CurrencyBalance.class);
        return usdtBalance.getAvailable();
    }

    public static List<PoloniexChartData> returnChartData(String currencyPair, Long periodInSeconds, int perioid) {
        List<PoloniexChartData> chartData = service.returnChartData(currencyPair, periodInSeconds,
                ZonedDateTime.now(ZoneOffset.UTC).minusDays(perioid).toEpochSecond());
        return chartData;
    }

    public static void buy(String currencyPair, BigDecimal currentPrice, BigDecimal amount) {
        service.buy(currencyPair, currentPrice, amount, fillOrKill, immediateOrCancel, postOnly);
    }

    public static void sell(String currencyPair, BigDecimal currentPrice, BigDecimal amount) {
        service.sell(currencyPair, currentPrice, amount, fillOrKill, immediateOrCancel, postOnly);
    }

}
