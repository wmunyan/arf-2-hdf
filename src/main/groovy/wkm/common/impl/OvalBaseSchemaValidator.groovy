package wkm.common.impl

import wkm.common.SchemaValidator
import wkm.common.VersionComparator

/**
 * Commonality for OVAL-based schema validators
 *
 * Created by wmunyan on 9/11/2017.
 */
class OvalBaseSchemaValidator extends SchemaValidator {

    final String OVAL54   = "5.4"
    final String OVAL58   = "5.8"
    final String OVAL5101 = "5.10.1"
    final String OVAL511  = "5.11"
    final String OVAL5111 = "5.11.1"
    final String OVAL5112 = "5.11.2"

    VersionComparator vc = new VersionComparator()

    /**
     * Calculate the version of OVAL to be validated against.  This information
     * will come from the <generator> element, the value in the <schema_version>
     * element
     * @param root
     * @return the OVAL schema version against which the root element will be validated
     */
    def calculateOvalVersion(def root) {
        def vv = "5.11.2" // default to the latest version
        def pe = []

        log.info "[START] OvalBaseSchemaValidator - Validate"

        // Find the <generator> element...
        def g = root.children().find { n ->
            n instanceof Node && getElementBasename(n.name()) == "generator"
        }

        if (g) {
            // find the <schema_version> element...
            def sv = g.children().find { n ->
                n instanceof Node && getElementBasename(n.name()) == "schema_version"
            }

            if (sv) {
                log.info "Discovered <schema_version> of ${sv.text()}"

                vv = {
                    def schemaVersion = sv.text()
                    switch (schemaVersion) {
                        case OVAL5112:
                            return OVAL5112
                        case OVAL5111:
                            return OVAL5111
                        case OVAL511:
                            return OVAL511
                        case OVAL5101:
                            return OVAL5101
                        case OVAL58:
                            return OVAL58
                        case OVAL54:
                            return OVAL54
                        default:
                            // Anything up to and including 5.4 gets 5.4
                            def cr = vc.compare(schemaVersion, OVAL54)
                            if (cr in [0, -1]) {
                                return OVAL54
                            }

                            // Anything up to and including 5.8 gets 5.8
                            cr = vc.compare(schemaVersion, OVAL58)
                            if (cr in [0, -1]) {
                                return OVAL58
                            }

                            // Anything up to and including 5.10.1 gets 5.10.1
                            cr = vc.compare(schemaVersion, OVAL5101)
                            if (cr in [0, -1]) {
                                return OVAL5101
                            }

                            // Anything up to and including 5.11 gets 5.11
                            cr = vc.compare(schemaVersion, OVAL511)
                            if (cr in [0, -1]) {
                                return OVAL511
                            }

                            // Otherwise the switch cases should have picked it up, so if
                            // we get here, just use 5.11.2
                            return OVAL5112
                    }
                }.call()
            }
        }

        return vv
    }
}
