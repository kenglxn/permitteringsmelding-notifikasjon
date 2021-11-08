package no.nav.permitteringsvarsel.notifikasjon

import no.nav.permitteringsvarsel.notifikasjon.utils.log
import io.javalin.Javalin
import no.nav.permitteringsvarsel.notifikasjon.kafka.PermitteringsskjemaConsumer
import no.nav.permitteringsvarsel.notifikasjon.kafka.consumerConfig
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import utils.Liveness
import java.io.Closeable

// Her implementeres all businesslogikk
class App (private val permitteringsConsumer: PermitteringsskjemaConsumer): Closeable {

    private val webServer = Javalin.create().apply {
        config.defaultContentType = "application/json"
        routes {
            get("/internal/isAlive") { if (Liveness.isAlive) it.status(200) else it.status(500) }
            get("/internal/isReady") { it.status(200) }
        }
    }


    fun start() {
        log.info("Starter app")
        webServer.start()
        permitteringsConsumer.start()
    }

    override fun close() {
        log.info("Stopper app")
    }
}

// Brukes når appen kjører på Nais
fun main() {
    val consumer: Consumer<String, String> = KafkaConsumer<String, String>(consumerConfig())
    val permitteringsskjemaConsumer: PermitteringsskjemaConsumer = PermitteringsskjemaConsumer(consumer)
    App(permitteringsskjemaConsumer).start()
}
