package wkm.common.impl

import wkm.common.intf.ISchemaValidator

import javax.xml.transform.Source

/**
 * Schema validations for OVAL Variables documents
 *
 * Created by wmunyan on 9/11/2017.
 */
class OvalVariablesSchemaValidator extends OvalBaseSchemaValidator implements ISchemaValidator {

	/**
	 * Validate the root node against the applicable schemas,
	 * and do the same for any sub-documents as well.
	 *
	 * @param root - The main document node to be validated
	 * @return any validation errors
	 */
	def validate(def root) {
		log.info "[START] OvalVariablesSchemaValidator - Validate"

		def pe = []
		def vv = calculateOvalVersion(root)

		log.info "Validating with ${vv}"
		Source[] schemas = [
			resolver.resolve("/xsd/validator-oval-variables-${vv}.xsd")
		]

		pe = validateUsingSchemas(root, schemas)
		log.info "[ END ] OvalVariablesSchemaValidator - Validate"

		return pe
	}
}
