package com.meridian.netting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TradeListener {

    private static final Logger log = LoggerFactory.getLogger(TradeListener.class);

    @Autowired
    private NettingPublisher nettingPublisher;

    @JmsListener(destination = "TRADE.MATCHED")
    public void onMatchedTrade(String tradeJson) {
        log.info("Received matched trade from TRADE.MATCHED queue");
        // In production this would aggregate trades by counterparty and instrument
        String nettedJson = "{\"netted\":true,\"source\":" + tradeJson + "}";
        nettingPublisher.publishNettedTrade(nettedJson);
    }
}
