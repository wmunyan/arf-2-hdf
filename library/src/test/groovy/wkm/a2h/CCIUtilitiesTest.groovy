package wkm.a2h

import spock.lang.Shared
import spock.lang.Specification

class CCIUtilitiesTest extends Specification {
    def setupSpec() {
        CCIUtilities.getInstance()
    }
    def "ensure cci mappings are parsed and existing value is found"() {
        given:
        def cci = "CCI-001545"
        when:
        def nist = CCIUtilities.instance.cci2nist(cci)
        then:
        nist == "AC-1 b 1"
    }
    def "ensure cci mappings are parsed and non-existing value is not found"() {
        given:
        def cci = "CCI-NON-EXISTENT"
        when:
        def nist = CCIUtilities.instance.cci2nist(cci)
        then:
        nist == null
    }
}
