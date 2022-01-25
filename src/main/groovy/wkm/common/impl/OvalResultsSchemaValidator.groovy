package wkm.common.impl


import wkm.common.intf.ISchemaValidator

/**
 * Validations for OVAL Results documents.
 *
 * Created by wmunyan on 9/9/2017.
 */
class OvalResultsSchemaValidator extends OvalBaseSchemaValidator implements ISchemaValidator {

    /**
     * Validate the root node against the applicable schemas,
     * and do the same for any sub-documents as well.
     *
     * @param root - The main document node to be validated
     * @return any validation errors
     */
    def validate(def root) {
        log.info "[START] OvalResultsSchemaValidator - Validate"

        def pe = []
        def vv = calculateOvalVersion(root)

        log.info "Validating with ${vv}"
        def schemas = [
            resolver.resolve("/xsd/validator-oval-results-${vv}.xsd")
        ]

        // Get extension schemas for definitions...
        schemas += getExtensionSchemas(vv, "definitions")

        // Get extension schemas for system characteristics...
        schemas += getExtensionSchemas(vv, "system-characteristics")

        pe = validateUsingSchemas(root, schemas)
        log.info "[ END ] OvalResultsSchemaValidator - Validate"

        return pe
    }
}
