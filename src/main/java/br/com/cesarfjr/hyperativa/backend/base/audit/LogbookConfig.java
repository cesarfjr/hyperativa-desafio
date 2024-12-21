package br.com.cesarfjr.hyperativa.backend.base.audit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.cesarfjr.hyperativa.backend.cartao.util.MascaraUtil;

@Configuration
public class LogbookConfig {

	@Bean
	public Logbook logbook() {
		
		BodyFilter bodyFilter = (contentType, body) -> {
			if (contentType != null && contentType.contains("application/json")) {
				Pattern pattern = Pattern.compile("\"pan\"\\s*:\\s*\"(.*?)\"");
				Matcher matcher = pattern.matcher(body);

				StringBuffer result = new StringBuffer();
				while (matcher.find()) {
					String sensitiveValue = matcher.group(1); // Extract the sensitive value
					String maskedValue = sensitiveValue.length() > 10 ? MascaraUtil.mascaraPan(sensitiveValue)
							: sensitiveValue; // Mask if longer than 10 characters

					matcher.appendReplacement(result, "\"pan\":\"" + maskedValue + "\"");
				}
				matcher.appendTail(result);

				return result.toString();
			}
			return body; // Return unchanged for non-JSON bodies
		};
		// Create an ObjectMapper with pretty-printing enabled
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		// Define a custom HttpLogFormatter for pretty JSON
		HttpLogFormatter formatter = new JsonHttpLogFormatter(objectMapper);
		LogstashLogbackSink logstashsink = new LogstashLogbackSink(formatter);
		Logbook logbook = Logbook.builder()
				.bodyFilter(bodyFilter)
				.sink(logstashsink)
				.build();
		return logbook;
	}

}
