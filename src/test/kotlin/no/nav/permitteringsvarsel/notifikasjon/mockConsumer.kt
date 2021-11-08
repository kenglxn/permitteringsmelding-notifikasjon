package no.nav.permitteringsvarsel.notifikasjon

import no.nav.permitteringsvarsel.notifikasjon.kafka.permitteringsmeldingtopic
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.common.TopicPartition

val topic = TopicPartition(permitteringsmeldingtopic, 0)

fun mockConsumer() = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST).apply {
    schedulePollTask {
        rebalance(listOf(topic))
    }
}
