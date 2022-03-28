package no.nav.permitteringsmelding.notifikasjon.utils

data class EnvironmentVariables(
    val azureADTokenEndpointUrl: String,
    val azureClientId: String,
    val azureJWK: String,
    val urlTilNotifikasjonIMiljo: String,
    val notifikasjonerScope: String,
    val kafkaBrokers: String,
    val kafkaTruststorePath: String,
    val kafkaCredstorePassword: String,
    val kafkaKeystorePath: String
)

val notifikasjonerScope = when(Cluster.current) {
    Cluster.DEV_GCP -> "api://dev-gcp.fager.notifikasjon-produsent-api/.default"
    Cluster.PROD_GCP -> "api://prod-gcp.fager.notifikasjon-produsent-api/.default"
}

val urlTilNotifikasjonIMiljo = when(Cluster.current) {
    Cluster.DEV_GCP -> "https://ag-notifikasjon-produsent-api.dev.nav.no/api/graphql"
    Cluster.PROD_GCP -> "https://ag-notifikasjon-produsent-api.intern.nav.no/api/graphql"
}

val environmentVariables = EnvironmentVariables(
    System.getenv("AZURE_OPENID_CONFIG_TOKEN_ENDPOINT"),
    System.getenv("AZURE_APP_CLIENT_ID"),
    System.getenv("AZURE_APP_JWK"),
    urlTilNotifikasjonIMiljo,
    notifikasjonerScope,
    System.getenv("KAFKA_BROKERS"),
    System.getenv("KAFKA_TRUSTSTORE_PATH"),
    System.getenv("KAFKA_CREDSTORE_PASSWORD"),
    System.getenv("KAFKA_KEYSTORE_PATH")
)