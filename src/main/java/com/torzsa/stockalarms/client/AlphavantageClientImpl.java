package com.torzsa.stockalarms.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torzsa.stockalarms.model.GlobalQuote;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlphavantageClientImpl implements AlphavantageClient {
//    https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=MSFT&apikey=demo


    private static final Logger LOGGER = LoggerFactory.getLogger(AlphavantageClientImpl.class);

    private static String ROOT_URI = "www.alphavantage.co";
    private static String GLOBAL_QUOTE = "GLOBAL_QUOTE";
    private static String API_KEY = "PDADUAF7RLZEFBRU";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public GlobalQuote getQuoteForSymbol(String symbol) {
        URIBuilder urlb = null;
        urlb = new URIBuilder()
                .setScheme("https")
                .setHost(ROOT_URI)
                .setPath("query")
                .addParameter("function", GLOBAL_QUOTE)
                .addParameter("symbol", symbol)
                .addParameter("apikey", API_KEY);

        String URL = urlb.toString();

        LOGGER.debug("Request to: " + URL);
        GlobalQuote quote = restTemplate.getForObject(URL, GlobalQuote.class);
        LOGGER.debug("Response quote from Alphavantage: \n" + quote.toString());
        return quote;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
//        rest.setErrorHandler(errorHandler());
        return rest;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(myObjectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper myObjectMapper() {
        //your custom ObjectMapper here
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        return mapper;
    }
//
//    @Bean
//    public RestTemplateResponseErrorHandler errorHandler() {
//        return new RestTemplateResponseErrorHandler();
//    }
}
