package wkm.common.impl

import wkm.common.SchemaValidator
import wkm.common.intf.ISchemaValidator

/**
 * Created by wmunyan on 9/9/2017.
 */
class DatastreamCollectionSchemaValidator extends SchemaValidator implements ISchemaValidator {

    /**
     * Validate the root node against the applicable schemas,
     * and do the same for any sub-documents as well.
     *
     * @param root - The main document node to be validated
     * @return any validation errors
     */
    def validate(def root) {
        log.info "[START] DatastreamCollectionSchemaValidator - Validate"
        def schemas = [
            resolver.resolve("/xsd/validator-data-stream-collection.xsd")
        ]

        // Get extension schemas for definitions...
        schemas += getExtensionSchemas("5.11.2", "definitions")

        def pe = validateUsingSchemas(root, schemas)
        log.info "[ END ] DatastreamCollectionSchemaValidator - Validate"

        return pe
    }
}
