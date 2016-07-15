package rnd.grails3.httpinvoker.producer

import grails.converters.JSON
import grails.core.GrailsApplication
import org.grails.core.DefaultGrailsDomainClass
import org.springframework.context.MessageSource

/**
 * <h1>Common Service </h1>
 * Is a Helper service to other services
 * @author yahya
 * @version 1.0
 */

class CommonService {

    MessageSource messageSource
    GrailsApplication grailsApplication

    /**
     * Custom JSON domain class marshaller
     * @param action : a controller action name used to return a default message as part of the JSON
     * @param instance : domain class instance
     * @param successMessage : if provided , will override the default message
     * @param failMessage : if provided , will override the default message
     * @return JSON String
     * @author yahya
     * TODO: transform this method to a spring bean that extends DomainClassMarshaller
     * @see org.grails.web.converters.marshaller.json.DomainClassMarshaller
     * http://manbuildswebsite.com/2010/02/15/rendering-json-in-grails-part-3-customise-your-json-with-object-marshallers/
     */
    public String getDomainClassCustomJSON(String action, Object instance, String successMessage, String failMessage = null) {
        def json = [:]
        try {
            if (!instance)
                throw new Exception("getDomainClassCustomJSON is missing required attribute [instance]")
            def g = grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.ApplicationTagLib')
            def defaultFailMessage
            def defaultSuccessMessage = messageSource.getMessage("default.saved.successfully", null, "default.saved.successfully", new Locale("ar"))
            switch (action) {
                case "select":
                    defaultFailMessage = messageSource.getMessage("default.select.error", null, "default.select.error", new Locale("ar"))

                    break;
                case "create":
                    defaultFailMessage = messageSource.getMessage("default.create.error", null, "default.create.error", new Locale("ar"))

                    break;
                case "update":
                    defaultFailMessage = messageSource.getMessage("default.update.error", null, "default.update.error", new Locale("ar"))


                    break;
                case "delete":
                    defaultFailMessage = messageSource.getMessage("default.delete.error", null, "default.delete.error", new Locale("ar"))

                    break;
                default:
                    defaultFailMessage = messageSource.getMessage("default.create.error", null, "default.create.error", new Locale("ar"))

                    break;

            }

            json.success = instance && !instance?.hasErrors();
            json.genMessage = json.success ? (successMessage ?: defaultSuccessMessage) : (failMessage ?: defaultFailMessage)
            json.message = json.success ? (successMessage ?: defaultSuccessMessage) : getDomainClassValidationErrors(instance)
            json.data = (instance as JSON) as String
            json.instanceId = instance?.id

        } catch (Exception e) {
            e.printStackTrace()
        }
        return (json as JSON).toString(true)
    }

    /**
     * Domain Class errors marshaller
     * @author yahya
     * @param instance : domain class object to fetch errors from
     * @return a flat arraylist of global, and field errors
     */
    public ArrayList getDomainClassValidationErrors(Object instance) {

        // exit if the provided object is not a domain class
        if (!grailsApplication.isDomainClass(instance.class)) {
            throw new IllegalArgumentException("The provided class $instance is not a grails domain class")
        }

        def g = grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.ApplicationTagLib')

        def globalErrors = instance?.errors?.globalErrors
        def fieldErrors = instance?.errors?.fieldErrors
        def errors = []
        if (fieldErrors) {
            def d = new DefaultGrailsDomainClass(instance.class)
            def props = d.persistentProperties

            fieldErrors.each {
                def prop = props.find { it2 -> it2.name == it.field }
                if (prop) {
                    errors.push(field: it?.field, message: g.fieldError(field: it?.field, bean: instance).toString())
                    props = props - prop
                } else {
                    errors.push(field: it?.field, message: it.code)
                }
            }
        }

        globalErrors?.each {
            errors.push(field: "global", message: messageSource.getMessage(it.code, null, it.code, new Locale("ar")))
        }

        return errors

    }

}
