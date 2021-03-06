/*
 * Created by: christopher
 * Date: Dec 14, 2007
 * Time: 10:51:59 AM
 *
 * <p>Copyright (C) 2007 Nature Publishing Group, Inc.</p>
 *
 * <p><a rel="license" href="http://creativecommons.org/licenses/GPL/2.0/">
 * <img alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/GPL/2.0/88x62.png" /></a><br />
 * This work is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/GPL/2.0/">Creative Commons GNU
 * General Public License License</a>.</p>
 */

package net.handle.servlet;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * <p>
 * Enumeration of mime type assignments.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 * @see <a href="http://www.iana.org/assignments/media-types/">IANA media type
 *      assignments</a>
 */
public enum Mimetype {

    /*
     * Application mime types
     */
    APPLICATION_ATOM_XML, APPLICATION_ATOMCAT_XML, APPLICATION_ATOMSVC_XML, APPLICATION_ECMASCRIPT, APPLICATION_JAVASCRIPT, APPLICATION_JSON, APPLICATION_MSWORD, APPLICATION_OCTET_STREAM, APPLICATION_PDF, APPLICATION_POSTSCRIPT, APPLICATION_RDF_XML, APPLICATION_RSS_XML, APPLICATION_RTF, APPLICATION_SBML_XML, APPLICATION_SGML, APPLICATION_VND_MOZILLA_XUL_XML, APPLICATION_VND_MS_EXCEL, APPLICATION_VND_MS_POWERPOINT, APPLICATION_VND_OASIS_OPENDOCUMENT_CHART, APPLICATION_VND_OASIS_OPENDOCUMENT_FORMULA, APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS, APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE, APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION, APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET, APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT, APPLICATION_X_BIBTEX, APPLICATION_X_RESEARCH_INFO_SYSTEMS, APPLICATION_X_SHOCKWAVE_FLASH, APPLICATION_XHTML_XML, APPLICATION_XML, APPLICATION_ZIP,

    /*
     * Audio mimetypes
     */
    AUDIO_MP4, AUDIO_MPEG, AUDIO_MPEG4_GENERIC,

    /*
     * Image mime types
     */
    IMAGE_GIF, IMAGE_JPEG, IMAGE_PNG, IMAGE_TIFF,

    /*
     * Multipart mime types
     */
    MULTIPART_ALTERNATIVE, MULTIPART_DIGEST, MULTIPART_FORM_DATA, MULTIPART_MIXED,

    /*
     * Text mime types
     */
    TEXT_CALENDAR, TEXT_CSS, TEXT_CSV, TEXT_HTML, TEXT_PLAIN, TEXT_RDF_N3, TEXT_RTF, TEXT_SGML, TEXT_TAB_SEPARATED_VALUES, TEXT_URI_LIST, TEXT_XML,

    /*
     * Video mime types
     */
    VIDEO_JPEG, VIDEO_MP4, VIDEO_MPEG, VIDEO_MPEG4_GENERIC, VIDEO_QUICKTIME;

    /**
     * <p>
     * Returns a {@link Mimetype} for the string representation of a mimetype.
     * N.B. the supplied string must match those produced by {@link #toString()}
     * in this enumeration.
     * </p>
     *
     * @param mimetype the mimetype string
     * @return the {@link Mimetype}
     */
    public static Mimetype forName(String mimetype) {
        for (Mimetype value : values()) {
            if (value.toString().equals(mimetype)) {
                return value;
            }
        }

        return null;
    }

    public static Mimetype forExtension(String extension) {
        if (isNotBlank(extension)) {
            for (Mimetype value : values()) {
                if (value.getRecommendedFileExtension() != null
                        && value.getRecommendedFileExtension()
                                .equals(extension)) {
                    return value;
                }
            }
        }

        return null;
    }

    /**
     * <p>
     * Returns the recommended file extension for this mime type, without the
     * period (e.g. "txt" for {@link #TEXT_PLAIN}), or <code>null</code> if
     * no recommended file extension is available.
     * </p>
     *
     * @return the recommended file extension or <code>null</code> if there is
     *         no recommended file extension for this mime type
     */
    public String getRecommendedFileExtension() {
        String s = null;

        switch (this) {
        case APPLICATION_ATOM_XML:
            s = "xml";
            break;
        case APPLICATION_ATOMCAT_XML:
            s = "xml";
            break;
        case APPLICATION_ATOMSVC_XML:
            s = "xml";
            break;
        case APPLICATION_ECMASCRIPT:
            s = "js";
            break;
        case APPLICATION_JAVASCRIPT:
            s = "js";
            break;
        case APPLICATION_JSON:
            s = "json";
            break;
        case APPLICATION_MSWORD:
            s = "doc";
            break;
        case APPLICATION_OCTET_STREAM:
            s = null;
            break;
        case APPLICATION_PDF:
            s = "pdf";
            break;
        case APPLICATION_POSTSCRIPT:
            s = "ps";
            break;
        case APPLICATION_RDF_XML:
            s = "rdf";
            break;
        case APPLICATION_RSS_XML:
            s = "rss";
            break;
        case APPLICATION_RTF:
            s = "rtf";
            break;
        case APPLICATION_SBML_XML:
            s = "xml";
            break;
        case APPLICATION_SGML:
            s = "sgml";
            break;
        case APPLICATION_VND_MOZILLA_XUL_XML:
            s = "xml";
            break;
        case APPLICATION_VND_MS_EXCEL:
            s = "xls";
            break;
        case APPLICATION_VND_MS_POWERPOINT:
            s = "ppt";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_CHART:
            s = "odc";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_FORMULA:
            s = "odf";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS:
            s = "odg";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE:
            s = "odi";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION:
            s = "odp";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET:
            s = "ods";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT:
            s = "odt";
            break;
        case APPLICATION_X_BIBTEX:
            s = "bib";
            break;
        case APPLICATION_X_RESEARCH_INFO_SYSTEMS:
            s = "ris";
            break;
        case APPLICATION_X_SHOCKWAVE_FLASH:
            s = "swf";
            break;
        case APPLICATION_XHTML_XML:
            s = "xhtml";
            break;
        case APPLICATION_XML:
            s = "xml";
            break;
        case APPLICATION_ZIP:
            s = "zip";
            break;
        case AUDIO_MP4:
            s = "mp4";
            break;
        case AUDIO_MPEG:
            s = "mpeg";
            break;
        case AUDIO_MPEG4_GENERIC:
            s = "mp4";
            break;
        case IMAGE_GIF:
            s = "gif";
            break;
        case IMAGE_JPEG:
            s = "jpg";
            break;
        case IMAGE_PNG:
            s = "png";
            break;
        case IMAGE_TIFF:
            s = "tiff";
            break;
        case MULTIPART_ALTERNATIVE:
            s = null;
            break;
        case MULTIPART_DIGEST:
            s = null;
            break;
        case MULTIPART_FORM_DATA:
            s = null;
            break;
        case MULTIPART_MIXED:
            s = null;
            break;
        case TEXT_CALENDAR:
            s = "ics";
            break;
        case TEXT_CSS:
            s = "css";
            break;
        case TEXT_CSV:
            s = "csv";
            break;
        case TEXT_HTML:
            s = "html";
            break;
        case TEXT_PLAIN:
            s = "txt";
            break;
        case TEXT_RDF_N3:
            s = "n3";
            break;
        case TEXT_RTF:
            s = "rtf";
            break;
        case TEXT_SGML:
            s = "sgml";
            break;
        case TEXT_TAB_SEPARATED_VALUES:
            s = "tab";
            break;
        case TEXT_URI_LIST:
            s = "uri";
            break;
        case TEXT_XML:
            s = "xml";
            break;
        case VIDEO_JPEG:
            s = "jpeg";
            break;
        case VIDEO_MP4:
            s = "mp4";
            break;
        case VIDEO_MPEG:
            s = "mpeg";
            break;
        case VIDEO_MPEG4_GENERIC:
            s = "mp4";
            break;
        case VIDEO_QUICKTIME:
            s = "qt";
            break;
        }

        return s;
    }

    /**
     * <p>
     * Returns the mime type identifier for this mime type (e.g. "text/plain"
     * for {@link #TEXT_PLAIN}).
     * </p>
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        String s = null;

        switch (this) {
        case APPLICATION_ATOM_XML:
            s = "application/atom+xml";
            break;
        case APPLICATION_ATOMCAT_XML:
            s = "application/atomcat+xml";
            break;
        case APPLICATION_ATOMSVC_XML:
            s = "application/atomsvc+xml";
            break;
        case APPLICATION_ECMASCRIPT:
            s = "application/ecmascript";
            break;
        case APPLICATION_JAVASCRIPT:
            s = "application/javascript";
            break;
        case APPLICATION_JSON:
            s = "application/json";
            break;
        case APPLICATION_MSWORD:
            s = "application/msword";
            break;
        case APPLICATION_OCTET_STREAM:
            s = "application/octet-stream";
            break;
        case APPLICATION_PDF:
            s = "application/pdf";
            break;
        case APPLICATION_POSTSCRIPT:
            s = "application/postscript";
            break;
        case APPLICATION_RDF_XML:
            s = "application/rdf+xml";
            break;
        case APPLICATION_RSS_XML:
            s = "application/rss+xml";
            break;
        case APPLICATION_RTF:
            s = "application/rtf";
            break;
        case APPLICATION_SBML_XML:
            s = "application/sbml+xml";
            break;
        case APPLICATION_SGML:
            s = "application/sgml";
            break;
        case APPLICATION_VND_MOZILLA_XUL_XML:
            s = "application/vnd.mozilla.xul+xml";
            break;
        case APPLICATION_VND_MS_EXCEL:
            s = "application/vnd.ms-excel";
            break;
        case APPLICATION_VND_MS_POWERPOINT:
            s = "application/vnd.ms-powerpoint";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_CHART:
            s = "application/vnd.oasis.opendocument.chart";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_FORMULA:
            s = "application/vnd.oasis.opendocument.formula";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS:
            s = "application/vnd.oasis.opendocument.graphics";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE:
            s = "application/vnd.oasis.opendocument.graphics";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION:
            s = "application/vnd.oasis.opendocument.presentation";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET:
            s = "application/vnd.oasis.opendocument.spreadsheet";
            break;
        case APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT:
            s = "application/vnd.oasis.opendocument.text";
            break;
        case APPLICATION_X_BIBTEX:
            s = "application/x-BibTeX";
            break;
        case APPLICATION_X_RESEARCH_INFO_SYSTEMS:
            s = "application/x-Research-Info-Systems";
            break;
        case APPLICATION_X_SHOCKWAVE_FLASH:
            s = "application/x-shockwave-flash";
            break;
        case APPLICATION_XHTML_XML:
            s = "application/xhtml+xml";
            break;
        case APPLICATION_XML:
            s = "application/xml";
            break;
        case APPLICATION_ZIP:
            s = "application/zip";
            break;
        case AUDIO_MP4:
            s = "audio/mp4";
            break;
        case AUDIO_MPEG:
            s = "audio/mpeg";
            break;
        case AUDIO_MPEG4_GENERIC:
            s = "audio/mpeg4-generic";
            break;
        case IMAGE_GIF:
            s = "image/gif";
            break;
        case IMAGE_JPEG:
            s = "image/jpeg";
            break;
        case IMAGE_PNG:
            s = "image/png";
            break;
        case IMAGE_TIFF:
            s = "image/tiff";
            break;
        case MULTIPART_ALTERNATIVE:
            s = "multipart/mixed";
            break;
        case MULTIPART_DIGEST:
            s = "multipart/digest";
            break;
        case MULTIPART_FORM_DATA:
            s = "multipart/form-data";
            break;
        case MULTIPART_MIXED:
            s = "multipart/mixed";
            break;
        case TEXT_CALENDAR:
            s = "text/calendar";
            break;
        case TEXT_CSS:
            s = "text/css";
            break;
        case TEXT_CSV:
            s = "text/csv";
            break;
        case TEXT_HTML:
            s = "text/html";
            break;
        case TEXT_PLAIN:
            s = "text/plain";
            break;
        case TEXT_RDF_N3:
            s = "text/rdf+n3";
            break;
        case TEXT_RTF:
            s = "text/rtf";
            break;
        case TEXT_SGML:
            s = "text/sgml";
            break;
        case TEXT_TAB_SEPARATED_VALUES:
            s = "text/tab-separated-values";
            break;
        case TEXT_URI_LIST:
            s = "text/uri-list";
            break;
        case TEXT_XML:
            s = "text/xml";
            break;
        case VIDEO_JPEG:
            s = "video/jpeg";
            break;
        case VIDEO_MP4:
            s = "video/mp4";
            break;
        case VIDEO_MPEG:
            s = "video/mpeg";
            break;
        case VIDEO_MPEG4_GENERIC:
            s = "video/mpeg4-generic";
            break;
        case VIDEO_QUICKTIME:
            s = "video/quicktime";
            break;
        }

        return s;
    }

}
