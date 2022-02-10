package wkm.a2h.cli

import groovy.cli.picocli.CliBuilder
import org.slf4j.LoggerFactory
import wkm.a2h.HdfGenerator
import wkm.a2h.fact.HdfGeneratorFactory
import wkm.a2h.intf.IHdfGenerator
import wkm.common.Utilities
import wkm.common.XmlParserFactory

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
            i  args: 1, argName: "INPUT-FILE",  longOpt: "input-file", "Path to file containing input file, either Asset Reporting Format (ARF), or XCCDF Test Results file"
            o  args: 1, argName: "OUTPUT-DIR",  longOpt: "output-directory", "Output directory for HDF file"
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

        if (options.i && options.o) {
            File inputFile = new File(options.i)

            // Parse the input file
            def inputNode = XmlParserFactory.getParser().parse(inputFile)

            // Get the converter implementation
            IHdfGenerator hdfGenerator =
                HdfGeneratorFactory.getGenerator(
                    Utilities.instance.getElementBasename(inputNode.name()))
            if (hdfGenerator) {
                def hdfs = hdfGenerator.generate(["source-node": inputNode, "support-node": null])
                if (hdfs.size() > 0) {
                    log.info "TODO: SAVE EACH HDF TO A FILE IN THE OUTPUT-DIR"
                } else {
                    log.info "NO HDF OBJECTS WERE GENERATED"
                }
            }
        }
    }
}
