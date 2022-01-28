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
            case "Benchmark":
                return new XccdfSchemaValidator()
            case "Tailoring":
                return new XccdfSchemaValidator()
            case "asset-report-collection":
                return new AssetReportFormatSchemaValidator()
            default:
                return null
        }
    }
}
