package wkm.common.fact

import wkm.common.Utilities
import wkm.common.impl.*
import wkm.common.intf.ISchemaValidator

/**
 * Create the appropriate validator based on information
 * gathered from the root node.
 *
 * Created by wmunyan on 9/9/2017.
 */
class SchemaValidatorFactory {
    /**
     * Determine the appropriate validator implementation
     * to instantiate.
     *
     * @param root
     * @return
     */
    static ISchemaValidator getValidator(def root) {
        def rootName = Utilities.getInstance().getElementBasename(root.name())

        switch (rootName) {
            case "data-stream-collection":
                return new DatastreamCollectionSchemaValidator()
            case "Benchmark":
                return new XccdfSchemaValidator()
            case "Tailoring":
                return new XccdfSchemaValidator()
            case "cpe-list":
                return new CPEDictionarySchemaValidator()
            case "oval_definitions":
                return new OvalDefinitionsSchemaValidator()
            case "oval_variables":
                return new OvalVariablesSchemaValidator()
            case "oval_results":
                return new OvalResultsSchemaValidator()
            case "asset-report-collection":
                return new AssetReportFormatSchemaValidator()
            default:
                return null
        }
    }
}
