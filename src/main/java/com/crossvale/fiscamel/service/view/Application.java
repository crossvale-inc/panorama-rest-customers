/*
 * Copyright 2005-2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.crossvale.fiscamel.service.view;

import static org.apache.camel.model.rest.RestParamType.path;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import com.crossvale.fiscamel.service.datatypes.CreditScore;
import com.crossvale.fiscamel.service.datatypes.Customer;
import com.crossvale.fiscamel.service.datatypes.CustomerBase;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*
    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
            new CamelHttpTransportServlet(), "/rest/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

*/
    @SuppressWarnings("deprecation")
    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
           /*
              restConfiguration()
            
                .contextPath("/rest").apiContextPath("/api-doc")
                    .apiProperty("api.title", "Camel REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true")
                    .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);
*/
        	
            /* The GET routes specify the binding mode to be JSON as well */
    		// rest("/customers")
    		// 	.consumes("application/json")
    		//	.produces("application/json")
    		
    			/* This GET route returns all customers by calling the CustomerController bean getAllCustomers method 
    			 * The outType is specified as an array of CustomerBase which matches the Collection<CustomerBase> 
    			 * returned by the getAllCustomers method. */
    		//	.get()
    		//	.bindingMode(RestBindingMode.json)
    		//	.outType(CustomerBase[].class)
    		//	//.enableCORS(true)
    		//	.to("bean:customerController?method=getAllCustomers")

    			/* This route gets the specific customer based on the provided customerNumber, which is extracted
    			 * from the path and put in the header as 'customerNumber' which is then provided as an argument
    			 * to the getCustomerByNumber method of the CustomerController bean */
    		//	.get("/{customerNumber}")
    		//	.bindingMode(RestBindingMode.json)
    		//	.outType(Customer.class)
    		//	.param().name("customerNumber").type(path).dataType("string").endParam()
    		//	.enableCORS(true)
    		//	.to("bean:customerController?method=getCustomerByNumber(${header.customerNumber})")
    		
    			/* This route returns all customers whose customerName contains the name specified in the path. Its a 
    			 * search by customer name implementation. The name is extracted from the path and put in the header as 
    			 * 'name' which is then provided to the searchCustomerByName method of the CustomerController bean */
    		//	.get("/search/{name}")
    		//	.bindingMode(RestBindingMode.json)
    		//	.outType(CustomerBase[].class)
    		//	.param().name("name").type(path).dataType("string").endParam()
    		//	.enableCORS(true)
    		//	.to("bean:customerController?method=searchCustomerByName(${header.name})");
    			
    		/* This POST route simply passes the exchange containing the customer to the processCustomer route */
    		/*
    		  rest("/customers")
    		 	.consumes("application/json")
    			.produces("text/plain")
    			
    			.post()
    			.enableCORS(true)
    			.to("direct:processCustomer");
    		*/

    		/* This POST route simply passes the exchange containing the customer to the processCustomer route */
    		/*
    		  rest("/customers")
    		    .consumes("application/json")
    			.produces("text/plain")
    		
    			.put()
    			.enableCORS(true)
    			.to("direct:processCustomer");
    		*/
    		rest("/creditScore")
    			.enableCORS(true)
    			.consumes("application/json")
    			.produces("text/plain")
    			.options()
    			.to("direct:processCustomer");
    		
    		rest("/creditScore")
    			.consumes("application/json")
    			.produces("application/json")
    			
    			/* This GET route returns a random credit score to be used by BPM */
    			.get()
    			.bindingMode(RestBindingMode.json)
    			.outType(CreditScore.class)
    			.enableCORS(true)
    			.to("bean:creditScoreController?method=getCreditScore");
    		
    		/* This route passes the exchange to the CustomerProcessor then sends it to the sendEvent route */
    		//from("direct:processCustomer")
    		//	.process("customerProcessor")
    		//	.to("direct:sendEvent");
    		
    		/* This route calls the EventProcesor and marshals the exchange back to JSON from plain text to then call the 
    		 * sendEventDetails method in the EventController bean */
    		//from("direct:sendEvent")
    		//	.log("Sending event to event-receiver")
    		//	.process("responseProcessor")
    		//	.marshal().json(JsonLibrary.Jackson)
    		//	.to("bean:responseController?method=sendEventDetails");
        }
    }    
}