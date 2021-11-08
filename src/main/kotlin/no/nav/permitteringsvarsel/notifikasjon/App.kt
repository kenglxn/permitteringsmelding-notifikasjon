package no.nav.permitteringsvarsel.notifikasjon

import no.nav.permitteringsvarsel.notifikasjon.utils.log
import io.javalin.Javalin
import no.nav.permitteringsvarsel.notifikasjon.minsideklient.MinSideNotifikasjonerService
import no.nav.permitteringsvarsel.notifikasjon.minsideklient.getHttpClient
import no.nav.permitteringsvarsel.notifikasjon.minsideklient.graphql.MinSideGraphQLKlient
import no.nav.permitteringsvarsel.notifikasjon.kafka.PermitteringsskjemaConsumer
import no.nav.permitteringsvarsel.notifikasjon.kafka.consumerConfig
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import utils.Liveness
import java.io.Closeable
import kotlin.concurrent.thread

// Her implementeres all businesslogikk
class App(private val consumer: Consumer<String, String>): Closeable {

    private val webServer = Javalin.create{ config ->
        config.defaultContentType = "application/json"
    }
    private val permitteringsskjemaConsumer: PermitteringsskjemaConsumer = PermitteringsskjemaConsumer(consumer)
    fun start() {
        log.info("Starter app")

        webServer
            .get("/internal/isAlive") { if (Liveness.isAlive) it.status(200) else it.status(500) }
            .get("/internal/isReady") { it.status(200) }
            .start()

        thread { permitteringsskjemaConsumer.start() }
    }

    override fun close() {
        webServer.stop()
        permitteringsskjemaConsumer.close()
    }
}

// Brukes når appen kjører på Nais
fun main() {
    val consumer: Consumer<String, String> = KafkaConsumer<String, String>(consumerConfig())

    val httpClient = getHttpClient()
    val minSideGraphQLKlient = MinSideGraphQLKlient("localhost", httpClient)
    val minSideNotifikasjonerService = MinSideNotifikasjonerService(minSideGraphQLKlient)

    App(consumer).start()
}
