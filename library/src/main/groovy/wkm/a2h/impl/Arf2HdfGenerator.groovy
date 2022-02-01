package wkm.a2h.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import wkm.a2h.intf.IHdfGenerator
import wkm.common.Utilities

class Arf2HdfGenerator implements IHdfGenerator {
    private Logger log = LoggerFactory.getLogger(Arf2HdfGenerator.class)

    /**
     * ARF to HDF generation
     * @return the array of maps to be converted to JSON.
     * This will be an array because (in theory), an ARF
     * can contain multiple XCCDF TestResult components.
     */
    def generate(def sourceNode, def supportNode = null) {
        log.info "[START] ARF-to-HDF Generator"

        def hdfResults = []

        // Extract the XCCDF <TestResult> Nodes
        def testResults = sourceNode."**".findAll { n ->
            n instanceof Node && Utilities.instance.getElementBasename(n.name()) == "TestResult"
        }

        // Extract the XCCDF <Benchmark> Nodes
        def sourceBenchmarkMap = [:]
        def benchmarks = sourceNode."**".findAll { n ->
            n instanceof Node && Utilities.instance.getElementBasename(n.name()) == "Benchmark"
        }
        benchmarks.each { b ->
            sourceBenchmarkMap[b.@id.toString()] = b
        }

        if (testResults) {
            IHdfGenerator hdfGenerator = new XccdfTestResult2HdfGenerator()
            def hdfs = []
            testResults.each { testResult ->
                def associatedBenchmark = testResult.children().find { n ->
                    n instanceof Node && Utilities.instance.getElementBasename(n.name()) == "benchmark"
                }
                if (associatedBenchmark) {
                    def benchmarkId = associatedBenchmark.@id.toString()
                    def testResultHdfs = hdfGenerator.generate(testResult, sourceBenchmarkMap[benchmarkId])
                    if (testResultHdfs && testResultHdfs.size() > 0) {
                        hdfs << testResultHdfs[0]
                    }
                } else {
                    log.error "No <benchmark> element found for <TestResult>"
                }
            }
        }

        log.info "[ END ] ARF-to-HDF Generator"
        return hdfResults
    }
}
