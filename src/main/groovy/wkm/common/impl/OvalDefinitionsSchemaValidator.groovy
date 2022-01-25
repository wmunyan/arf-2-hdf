package wkm.common.impl


import wkm.common.intf.ISchemaValidator

/**
 * Validations for OVAL Definitions documents.
 *
 * Created by wmunyan on 9/9/2017.
 */
class OvalDefinitionsSchemaValidator extends OvalBaseSchemaValidator implements ISchemaValidator {

    /**
     * Validate the root node against the applicable schemas,
     * and do the same for any sub-documents as well.
     *
     * @param root - The main document node to be validated
     * @return any validation errors
     */
    def validate(def root) {
        def pe = []

        log.info "[START] OvalDefinitionsSchemaValidator - Validate"
        def vv = calculateOvalVersion(root)

        log.info "Validating with ${vv}"
        def schemas = [
            resolver.resolve("/xsd/validator-oval-definitions-${vv}.xsd")
        ]

        schemas += getExtensionSchemas(vv, "definitions")
        pe = validateUsingSchemas(root, schemas)

        log.info "[ END ] OvalDefinitionsSchemaValidator - Validate"

        return pe
    }
}
