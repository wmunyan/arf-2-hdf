package wkm.common.impl

import wkm.common.SchemaValidator
import wkm.common.intf.ISchemaValidator

import javax.xml.transform.Source

/**
 * Schema validations for ARF content
 *
 * Created by wmunyan on 9/11/2017.
 */
class AssetReportFormatSchemaValidator extends SchemaValidator implements ISchemaValidator {

	/**
	 * Validate the root node against the applicable schemas,
	 * and do the same for any sub-documents as well.
	 *
	 * @param root - The main document node to be validated
	 * @return any validation errors
	 */
	def validate(def root) {
		log.info "[START] AssetReportFormatSchemaValidator - Validate"
		Source[] schemas = [
			resolver.resolve("/xsd/validator-asset-reporting-format.xsd")
		]
		def pe = validateUsingSchemas(root, schemas)
		log.info "[ END ] AssetReportFormatSchemaValidator - Validate"

		return pe
	}
}
