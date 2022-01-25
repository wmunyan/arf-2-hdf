package wkm.common.intf

/**
 * Schema validation interface
 *
 * Created by wmunyan on 9/9/2017.
 */
interface ISchemaValidator {
    /**
     * Validate the root node against the applicable schemas,
     * and do the same for any sub-documents as well.
     *
     * @param root - The main document node to be validated
     * @return any validation errors
     */
    def validate(def root)
}
