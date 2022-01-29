package wkm.common.impl

import wkm.common.SchemaValidator
import wkm.common.intf.ISchemaValidator

/**
 * Created by wmunyan on 9/9/2017.
 */
class XccdfSchemaValidator extends SchemaValidator implements ISchemaValidator {
    final String XCCDF13 = "/xsd/validator-xccdf13-benchmark.xsd"
    final String XCCDF12 = "/xsd/validator-xccdf12-benchmark.xsd"
    final String XCCDF11 = "/xsd/validator-xccdf11-benchmark.xsd"

    /**
     * Validate the root node against the applicable schemas,
     * and do the same for any sub-documents as well.
     *
     * @param root - The main document node to be validated
     * @return any validation errors
     */
    def validate(def root) {
        def sources = []
        log.info "[START] XccdfSchemaValidator - Validate"
        def attributes = root.attributes()
        if (attributes.containsKey("style")) {
            switch (attributes["style"]) {
                case "SCAP_1.3":
                    log.info "Found SCAP_1.3 style; Using ${XCCDF13}"
                    sources << resolver.resolve(XCCDF13)
                    break
                case "SCAP_1.2":
                    log.info "Found SCAP_1.2 style; Using ${XCCDF12}"
                    sources << resolver.resolve(XCCDF12)
                    break
                default:
                    log.info "Found ${attributes["style"]} style; Using ${XCCDF11}"
                    sources << resolver.resolve(XCCDF11)
                    break
            }
        } else {
            // Search for an XCCDF-related namespace attribute...
            if (attributes.containsValue("http://checklists.nist.gov/xccdf/1.2")) {
                log.info "Found XCCDF 1.2 namespace declaration; Using ${XCCDF12}"
                sources << resolver.resolve(XCCDF12)
            } else {
                log.info "No @style attribute, No XCCDF 1.2 namespace; Using ${XCCDF11}"
                sources << resolver.resolve(XCCDF11)
            }
        }

        def pe = []
        if (sources.size() > 0) {
            pe = validateUsingSchemas(root, sources)
        } else {
            pe << "The schema validator could not determine the appropriate schemas for validation."
        }
        log.info "[ END ] XccdfSchemaValidator - Validate"

        return pe
    }
}
