package wkm.a2h

import groovy.cli.picocli.CliBuilder
import org.slf4j.LoggerFactory
import wkm.common.Utilities
import wkm.common.XmlParserFactory
import wkm.common.fact.SchemaValidatorFactory

class Application {
    // CLI:
    //  -f [arf]
    //  -d [directory]  all arfs in a directory?
    //  -out [directory]
    //  -outurl [url]   upload?
    def log = LoggerFactory.getLogger(Application.class)
    def cli = new CliBuilder(usage: 'arf2hdf.[bat|sh] -[options] <extras>', width: 100)



    /**
     * Entry point
     * @param args
     */
    static void main(String[] args) {
        new Application().start(args)
    }

    /**
     * Initialize the command line options
     */
    void initialize() {
        cli.with {
            h longOpt: 'help', 'Show usage information'
            a  args: 1, argName: "ARF",  longOpt: "arf", "Path to file containing asset reporting format (ARF) file"
            o  args: 1, argName: "OUTPUT-DIR",  longOpt: "outdir", "Output directory for HDF file"
        }

        StringWriter hdr = new StringWriter()
        hdr.println("---------------------------------------------------------------------------------------------------")
        hdr.println(" Options                                           Tip")
        hdr.println("---------------------------------------------------------------------------------------------------")

        cli.header = hdr.toString()
    }
    /*
    read the arf
    parse it with schema validation
    apply mappings in a streaming json builder
    * */

    /**
     * Start your engines
     * @param args
     */
    void start(String[] args) {
        // Initialize the CLI parser
        initialize()

        // Parse the command-line options
        def options = cli.parse(args)

        // Show help and exit if no arguments or -h
        if (args.size() == 0 || options.h) {
           cli.usage()
            log.info("Exit Code: 0")
            System.exit(0)
        }

        if (options.a && options.o) {
            File arfFile = new File(options.a)
            // Parse the ARF
            def arfNode = XmlParserFactory.getParser().parse(arfFile)

            // Extract the XCCDF <TestResult> Nodes
            def testResults = arfNode."**".findAll { n ->
                n instanceof Node && Utilities.instance.getElementBasename(n.name()) == "TestResult"
            }

            if (testResults) {
                def hdfs = []
                testResults.each { testResult ->
                    hdfs << new HdfGenerator(testResultNode: testResult).generate()
                }
            }
        }
    }
}
