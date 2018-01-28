package istia.st.springmvc.main;

import java.util.Locale;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Pour configurer une application Spring MVC, il faut étendre la classe
 * [WebMvcConfigurerAdapter].
 * 
 * 
 * @author Dominique Mas
 *
 */
@Configuration
@ComponentScan({ "istia.st.springmvc.controllers", "istia.st.springmvc.models" })
@EnableAutoConfiguration
public class Config extends WebMvcConfigurerAdapter {

	/**
	 * L'annotation [@Bean] introduit un composant Spring, un singleton.
	 * 
	 * On définit un bean nommé [messageSource] (le nom de la méthode). Ce bean sert
	 * à definir les fichiers de messages de l'application et il doit avoir
	 * obligatoirement ce nom.
	 * 
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("i18n/messages");
		return messageSource;
	}

	/**
	 * On crée un intercepteur de requête. Un intercepteur de requête étend
	 * l'interface [HandlerInterceptor]. Une telle classe inspecte la requête
	 * entrante avant qu'elle ne soit traitée par une action. Ici l'intercepteur
	 * [localeChangeInterceptor] va rechercher un paramètre nommé [lang] dans la
	 * requête entrante, GET ou POST et va changer la locale de l'application en
	 * fonction de ce paramètre. Ainsi si le paramètre est [lang=en_US], la locale
	 * de l'application deviendra l'anglais des USA.
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/**
	 * On redéfinit la méthode [WebMvcConfigurerAdapter.addInterceptors] pour
	 * ajouter l'intercepteur précédent.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("lang");
		localeResolver.setDefaultLocale(new Locale("fr"));
		return localeResolver;
	}

}
