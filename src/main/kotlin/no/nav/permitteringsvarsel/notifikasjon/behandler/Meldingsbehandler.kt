package no.nav.permitteringsvarsel.notifikasjon.behandler

import no.nav.permitteringsvarsel.notifikasjon.PermitteringsMelding


class MeldingsBehandler {

    fun behandleMelding(permitteringsMelding: PermitteringsMelding) {
        /*if (meldingErBehandlet(permitteringsmelding.)) {
            return
        }
        try {
            eregService.hentBedriftInfo(permitteringsMelding)
            norgService.hentBehandlendeEnhet(permitteringsMelding)
        } catch (e: Exception) {
            log.error("Finner ikke behandlende enhet for bedrift {}. Avbryter", permitteringsMelding.getBedriftsnummer())
            throw RuntimeException(e)
        }
        if (permitteringsMelding.getBehandlendeEnhet() == null) {
            log.error("Behandlende enhet ble ikke funnet. Behandling av melding er avbrutt. Bedrift: {}, geoOmråde {}", permitteringsMelding.getBedriftsnummer(), permitteringsMelding.getKommuneNummer())
            throw RuntimeException("Behandlende enhet ble ikke funnet. Behandling av melding er avbrutt for Bedrift " + permitteringsMelding.getBedriftsnummer())
        }
        val journalpostId = journalforMelding(permitteringsMelding)
        lagreMelding(permitteringsMelding, journalpostId)
        log.info("Ferdig behandlet melding med id {}", permitteringsMelding.getId())

         */
    }

}