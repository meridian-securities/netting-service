package com.meridian.netting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NettingPublisher {

    private static final Logger log = LoggerFactory.getLogger(NettingPublisher.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishNettedTrade(String nettedJson) {
        log.info("Publishing netted trade to TRADE.NETTED queue");
        jmsTemplate.convertAndSend("TRADE.NETTED", nettedJson);
        log.info("Netted trade published successfully");
    }

    @JmsListener(destination = "REFDATA.INSTRUMENT", subscription = "NETSVC_INSTR_SUB", containerFactory = "topicListenerFactory")
    public void onInstrumentRefData(String refDataJson) {
        log.info("Received instrument reference data update from REFDATA.INSTRUMENT topic");
        // In production this would update a local cache of instrument reference data
        // used for netting calculations (e.g., contract sizes, currency pairs)
    }
}
