package no.nav.permitteringsvarsel.notifikasjon

import no.nav.permitteringsvarsel.notifikasjon.utils.log
import io.javalin.Javalin
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.logging.*
import no.nav.permitteringsvarsel.notifikasjon.minsideklient.MinSideNotifikasjonerService
import no.nav.permitteringsvarsel.notifikasjon.minsideklient.getHttpClient
import no.nav.permitteringsvarsel.notifikasjon.minsideklient.graphql.MinSideGraphQLKlient
import no.nav.permitteringsvarsel.notifikasjon.kafka.PermitteringsskjemaConsumer
import no.nav.permitteringsvarsel.notifikasjon.kafka.consumerConfig
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import utils.Liveness
import java.io.Closeable
import java.util.concurrent.TimeUnit

// Her implementeres all businesslogikk
class App (private val permitteringsConsumer: PermitteringsskjemaConsumer): Closeable {

    private val webServer = Javalin.create{ config ->
        config.defaultContentType = "application/json"
    }

    fun start() {
        log.info("Starter app")

        webServer
            .get("/internal/isAlive") { if (Liveness.isAlive) it.status(200) else it.status(500) }
            .get("/internal/isReady") { it.status(200) }
            .start()

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

    val httpClient = getHttpClient()
    val minSideGraphQLKlient = MinSideGraphQLKlient("localhost", httpClient)
    val minSideNotifikasjonerService = MinSideNotifikasjonerService(minSideGraphQLKlient)

    App(permitteringsskjemaConsumer).start()
}
