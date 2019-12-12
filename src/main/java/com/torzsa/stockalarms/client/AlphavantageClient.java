package com.torzsa.stockalarms.client;

import com.torzsa.stockalarms.model.GlobalQuote;

public interface AlphavantageClient {
    GlobalQuote getQuoteForSymbol(String symbol);
}
