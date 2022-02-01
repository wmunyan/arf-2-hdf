package wkm.a2h.fact

import wkm.a2h.impl.Arf2HdfGenerator
import wkm.a2h.impl.XccdfTestResult2HdfGenerator
import wkm.a2h.intf.IHdfGenerator

class HdfGeneratorFactory {
    private static final String ASSET_REPORTING_FORMAT = "asset-report-collection"
    private static final String XCCDF_TEST_RESULT      = "TestResult"

    static IHdfGenerator getGenerator(def rootElementName) {
        switch (rootElementName) {
            case ASSET_REPORTING_FORMAT:
                return new Arf2HdfGenerator()
            case XCCDF_TEST_RESULT:
                return new XccdfTestResult2HdfGenerator()
            default:
                return null
        }
    }
}
