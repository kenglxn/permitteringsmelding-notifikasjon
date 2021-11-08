package no.nav.permitteringsvarsel.notifikasjon.kafka

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import no.nav.permitteringsvarsel.notifikasjon.utils.Environment

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

const val permitteringsmeldingtopic = "permittering-og-nedbemanning.aapen-permittering-arbeidsgiver"

fun consumerConfig() = Properties().apply {
    put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1)
    put(ConsumerConfig.GROUP_ID_CONFIG, "permitteringsvarsel-notifikasjon-1")
    put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false)
    put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
    put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer::class.java)

    put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, Environment.get("KAFKA_BROKERS"))
    put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, Environment.get("KAFKA_SCHEMA_REGISTRY"))
    put(KafkaAvroDeserializerConfig.USER_INFO_CONFIG, "${Environment.get("KAFKA_SCHEMA_REGISTRY_USER")}:${Environment.get("KAFKA_SCHEMA_REGISTRY_PASSWORD")}")
    put(KafkaAvroDeserializerConfig.BASIC_AUTH_CREDENTIALS_SOURCE, "USER_INFO")

    put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true)

    put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL")
    put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "")
    put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, Environment.get("KAFKA_TRUSTSTORE_PATH"))
    put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, Environment.get("KAFKA_CREDSTORE_PASSWORD"))
    put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "PKCS12")
    put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, Environment.get("KAFKA_KEYSTORE_PATH"))
    put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, Environment.get("KAFKA_CREDSTORE_PASSWORD"))
}