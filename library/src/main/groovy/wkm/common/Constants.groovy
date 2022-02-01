package wkm.common

class Constants {
    static final String XCCDF_TESTRESULT = "TestResult"
    static final String XCCDF_BENCHMARK  = "Benchmark"

    // severity maps to high, medium, low with weights all being 10.0 from the xml
    // it doesn't really look like SCAP or SCC cares about that value, just if its high, med, or low
    static final def IMPACT_MAPPING = [
        critical: 0.9,
        high: 0.7,
        medium: 0.5,
        low: 0.3,
        na: 0.0]

    static final String CWE_REGEX = /CWE-(\d*):/
    static final String CCI_REGEX = /CCI-(\d*)/

    //static final String DEFAULT_NIST_TAG = %w{SA-11 RA-5 Rev_4}

//    static final String NA_STRING = ''.freeze
//    static final String NA_TAG = nil
//    NA_ARRAY = [].freeze
//    NA_HASH = {}.freeze
//    NA_FLOAT = 0.0
}
