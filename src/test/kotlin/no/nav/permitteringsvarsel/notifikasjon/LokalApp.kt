package no.nav.permitteringsvarsel.notifikasjon

import org.apache.kafka.clients.consumer.Consumer

// Brukes for å kjøre appen lokalt
fun main() {
    startLokalApp()
}

// Brukes for å kjøre appen i tester
fun startLokalApp(
    consumer: Consumer<String, String> = mockConsumer()
) {

    App(consumer).start()
}
