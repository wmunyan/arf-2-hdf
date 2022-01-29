package wkm.common

import groovy.io.FileType
import groovy.xml.XmlUtil
import org.slf4j.LoggerFactory
import org.xml.sax.ErrorHandler

import javax.xml.XMLConstants
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

/**
 * Created by wmunyan on 9/6/2017.
 */
class SchemaValidator {
	// Just a test class so stuff checks in.


    // What I'd really love to do is be able to have an "external" folder where
    // users can drop in custom schemas that are automatically included in the
    // array of schemas that are built in.

    // Then, potentially, a user can create a custom probe and/or probe factory
    // in the Assessor which can be found by the classloader and injected into
    // the Assessor, call the probe, and collect the object's system characteristics.

    // Basically, allow users to develop custom schemas, and custom probes, that
    // can automatically plug into the Assessor.

    def log = LoggerFactory.getLogger(SchemaValidator.class)
    def resolver = new SchemaValidatorUriResolver()
    def extensionsFolder

    /**
     * Validate the root node against the supplied schemas
     * @param root the root node to be validated
     * @param schemas an array of Source instances
     * @return an array of parser errors (could be empty for schema-valid XML)
     */
    def validateUsingSchemas(def root, def schemas = []) {

        schemas.each { s -> log.info s.systemId}


        def schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        def factory = SAXParserFactory.newInstance()
        factory.validating     = false
        factory.namespaceAware = true
        factory.schema         = schemaFactory.newSchema(schemas as Source[])

        // Create the XML Slurper using the configured SAX Parser.
        def slurper = new XmlSlurper(factory.newSAXParser())
        slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
        slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
        slurper.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)

        def parseErrors = []
        slurper.errorHandler = { e ->
            log.error "Error -> ${e.message}"
            parseErrors << "Error -> ${e.message}"
        } as ErrorHandler

        // We need to re-parse the XML by first serializing it to a string and then
        // sending that text through the validation parser...
        def rootAsString = XmlUtil.serialize(root)

        // Slurp the XML Document, validating along the way...
        def parsed = slurper.parseText(rootAsString)

        // Any errors will have been pushed to the list by the error handler...
        return parseErrors
    }

    /**
     * Determine the element name in case a namespace prefix is applied.
     * For example, the basename of element <oval-sc:registry_item> is "registry_item"
     *
     * @param elementName
     * @return the basename w/out the namespace prefix, if there is one
     */
    def getElementBasename(String elementName) {
        def basename = elementName
        def pos = elementName.indexOf(":")
        if (pos >= 0) {
            basename = elementName.substring(pos + 1)
        }
        return basename
    }

    /**
     * Based on the designated "extensions" folder, load up any extension
     * schemas to be included in the list
     *
     * @param validationVersion
     */
    def getExtensionSchemas(def validationVersion, def type) {
        def extensions = []

        if (extensionsFolder) {
            if (type in ["definitions", "system-characteristics"]) {
                def ovalExtensionsFolder =
                    new File("${extensionsFolder}${File.separator}oval${File.separator}${validationVersion}")
                ovalExtensionsFolder.eachFileRecurse(FileType.FILES) { f ->
                    if (f.name ==~ /x-.*-${type}-.*\.xsd/) {
                    //if (f.name.startsWith("x-") && f.name.endsWith(".xsd")) {
                        extensions << new StreamSource(f)
                    }
                }
            }
        }
        return extensions
    }
}
