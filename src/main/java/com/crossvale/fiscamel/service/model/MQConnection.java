package com.crossvale.fiscamel.service.model;


import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;

public class MQConnection {
	
	//EXAMPLE https://rterp.wordpress.com/2016/02/01/using-apache-camel-and-activemq-to-implement-synchronous-requestresponse/
	public void buildProducerRoute(CamelContext context) throws Exception {
        context.addRoutes(new RouteBuilder() {
            
            @Override
            public void configure() throws Exception {
               
                Processor processor = (Exchange exchange) -> {
                    System.out.println("PRODUCER Received response: " + exchange.getIn().getBody(String.class));
                };

                from("file:///Users/RobTerpilowski/tmp/in")
                .to("jms-broker:queue:robt.test.queue?exchangePattern=InOut")
                .process(processor);

            }
        });
    }
}
