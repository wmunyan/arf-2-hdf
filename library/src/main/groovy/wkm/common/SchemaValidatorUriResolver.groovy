package wkm.common

import org.slf4j.LoggerFactory

import javax.xml.transform.Source
import javax.xml.transform.URIResolver
import javax.xml.transform.stream.StreamSource

/**
 * URI Resolver
 *
 * Created by wmunyan on 9/10/2017.
 */
class SchemaValidatorUriResolver implements URIResolver {
	def log = LoggerFactory.getLogger(SchemaValidatorUriResolver.class)

	/**
	 * Resolve the resource from the href
	 * @param href
	 * @return
	 */
	Source resolve(String href) {
		return resolve(href, null)
	}

	/**
	 * Resolve the base href
	 * @param href
	 * @param base
	 * @return
	 */
	@Override
	Source resolve(String href, String base) {
		URL url = resolveUrl(href, base)

		if (url == null) { return null }

		return new StreamSource(url.toString())
	}

	/**
	 * Resolve the base href to a URL
	 * @param href
	 * @param base
	 * @return
	 */
	URL resolveUrl(String href, String base) {
		Class clazz = this.class

		try {
			URI targetUri = new URI(href)
			if (!targetUri.absolute && base != null) {
				targetUri = targetUri.resolve(base)
			}

			URL url
			if (targetUri.absolute) {
				url = clazz.getResource(targetUri.toURL().toString())
			} else {
				url = clazz.getResource(href)
			}

			return url
		} catch (Exception ex) {
			log.error "Exception resolving ${href}", ex
			return null
		}
	}

	/**
	 * Resolve the href to a File
	 * @param href
	 * @return
	 */
	File resolveFile(String href) {
		try {
			URL url = this.class.getResource(href)
			return new File(url.toURI())
		} catch (Exception e) {
			return null
		}
	}
}
