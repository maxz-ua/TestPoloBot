package test.polo.tradebot;

import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonProcessingException;

public class PossiblePairs implements Runnable {

    CurrencyTicker ticker = null;
    CurrencyBalance balance = null;
    LinkedList<String> currentPair = new LinkedList<>();
    String currencyPair = "";
    String usdtBalance = "";

    public static String[] getPossiblePairs(String currency) {
        String[] pair = null;
        switch (currency) {
            case ("USDT"):
                pair = new String[]{"ATOM", "BTC", "BNB", "BTC", "BTT", "DAI", "EOS", "ETC", "ETH", "JST", "MATIC", "PAX",
                        "STR", "TRX", "XRP", "USDC", "ZEC",};
                return pair;
            case ("USDC"):
                pair = new String[]{"ATOM", "BTC", "EOS", "ETC", "ETH", "STR", "TRX", "XRP", "USDT", "ZEC"};
                return pair;
            case ("BTC"):
                pair = new String[]{"ATOM", "EOS", "ETC", "ETH", "MATIC", "STR", "TRX", "XRP", "ZEC"};
                return pair;
            case ("TRX"):
                pair = new String[]{"BNB", "BTT", "ETH", "JST", "MATIC", "XRP"};
                return pair;
            case ("BUSD"):
                pair = new String[]{"BTC", "BNB"};
                return pair;
            case ("ETH"):
                pair = new String[]{"EOS", "ETC", "ZEC"};
                return pair;
            case ("PAX"):
                pair = new String[]{"BTC", "ETH"};
                return pair;
            case ("DAI"):
                pair = new String[]{"BTC", "ETH"};
                return pair;
            case ("BNB"):
                pair = new String[]{"BTC"};
                return pair;

        }
        return pair;
    }

    public void run() {

        while (true) {
            try {
                usdtBalance = ExchangeService.getUsdtBalance().toString();
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            for (int i = 0; i < currentPair.size(); i++) {
                try {
                    balance = ExchangeService.getBalance(currentPair.get(i)
                            .substring(currentPair.get(i).indexOf("_") + 1, currentPair.get(i).lastIndexOf("")));
                    ticker = ExchangeService.getTicker(currentPair.get(i));

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
