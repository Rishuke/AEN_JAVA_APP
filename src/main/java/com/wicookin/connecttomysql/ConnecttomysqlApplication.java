package com.wicookin.connecttomysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConnecttomysqlApplication {

    public static void main(String[] args) {



        ConfigurableApplicationContext context = SpringApplication.run(ConnecttomysqlApplication.class, args);
        MemberRepository memberRepository = context.getBean(MemberRepository.class);

        Iterable<MembersEntity> members = memberRepository.findAll();
        for (MembersEntity member : members) {
            System.out.println(member.getFirstname());
        }

        System.out.println("\n");

        SubscriptionRepository subscriptionRepository = context.getBean(SubscriptionRepository.class);
        Iterable<SubscriptionEntity> subscriber = subscriptionRepository.findAll();
        for (SubscriptionEntity subscribe : subscriber) {
            System.out.println(subscribe.getType());
        }

        System.out.println("\n");

        InvoiceRepository invoiceRepository = context.getBean(InvoiceRepository.class);
        Iterable<InvoicesEntity> invoices = invoiceRepository.findAll();
        for (InvoicesEntity invoice : invoices) {
            System.out.println(invoice.getAmount());
        }

        System.out.println("\n");

        AddresseRepository addresseRepository = context.getBean(AddresseRepository.class);
        Iterable<AddressesEntity> addresses = addresseRepository.findAll();
        for (AddressesEntity addresse : addresses) {
            System.out.println(addresse.getStreet());
        }

        System.out.println("\n");

        EventRepository eventRepository = context.getBean(EventRepository.class);
        Iterable<EventsEntity> events = eventRepository.findAll();
        for (EventsEntity event : events) {
            System.out.println(event.getDescription());
        }


        System.out.println("\n");

        ServiceRepository serviceRepository = context.getBean(ServiceRepository.class);
        Iterable<ServicesEntity> services = serviceRepository.findAll();
        for (ServicesEntity service : services) {
            System.out.println(service.getId());
        }
    }

}
